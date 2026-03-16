package com.ruoyi.system.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.Customer;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.OrderItem;
import com.ruoyi.system.domain.OrderStatusLog;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.domain.ProductionTask;
import com.ruoyi.system.domain.vo.OrderStatusRequest;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.mapper.OrderStatusLogMapper;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.mapper.ProductionTaskMapper;
import com.ruoyi.system.service.ICustomerService;
import com.ruoyi.system.service.IOrderService;
import com.ruoyi.system.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements IOrderService
{
    private static final String STATUS_DRAFT = "draft";
    private static final String STATUS_PENDING_REVIEW = "pending_review";
    private static final String STATUS_APPROVED = "approved";
    private static final String STATUS_CONTRACT_SIGNED = "contract_signed";
    private static final String STATUS_IN_PRODUCTION = "in_production";
    private static final String STATUS_PRODUCED = "produced";
    private static final String STATUS_OUTBOUND = "outbound";
    private static final String STATUS_COMPLETED = "completed";
    private static final String STATUS_CANCELED = "canceled";

    private static final String TASK_PENDING = "pending";
    private static final String TASK_IN_PROGRESS = "in_progress";
    private static final String TASK_COMPLETED = "completed";
    private static final String TASK_CANCELED = "canceled";

    private static final Map<String, String> NEXT_STATUS_MAP = new LinkedHashMap<>();

    static
    {
        NEXT_STATUS_MAP.put(STATUS_DRAFT, STATUS_PENDING_REVIEW);
        NEXT_STATUS_MAP.put(STATUS_PENDING_REVIEW, STATUS_APPROVED);
        NEXT_STATUS_MAP.put(STATUS_APPROVED, STATUS_CONTRACT_SIGNED);
        NEXT_STATUS_MAP.put(STATUS_CONTRACT_SIGNED, STATUS_IN_PRODUCTION);
        NEXT_STATUS_MAP.put(STATUS_IN_PRODUCTION, STATUS_PRODUCED);
        NEXT_STATUS_MAP.put(STATUS_PRODUCED, STATUS_OUTBOUND);
        NEXT_STATUS_MAP.put(STATUS_OUTBOUND, STATUS_COMPLETED);
    }

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ProductionTaskMapper productionTaskMapper;

    @Autowired
    private OrderStatusLogMapper orderStatusLogMapper;

    @Autowired
    private IStockService stockService;

    @Override
    public Order selectOrderByOrderId(Long orderId)
    {
        Order order = orderMapper.selectOrderByOrderId(orderId);
        if (order == null)
        {
            return null;
        }
        order.setOrderItemList(orderMapper.selectOrderItemListByOrderId(orderId));
        order.setStatusLogList(orderStatusLogMapper.selectOrderStatusLogListByOrderId(orderId));
        order.setProductionTaskList(productionTaskMapper.selectProductionTaskListByOrderId(orderId));
        return order;
    }

    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    @Transactional
    @Override
    public int insertOrder(Order order)
    {
        normalizeAndValidateOrder(order, true);
        order.setCreateTime(DateUtils.getNowDate());
        order.setOrderStatus(STATUS_DRAFT);
        order.setStockDeducted("0");
        if (isBlank(order.getOrderNumber()))
        {
            order.setOrderNumber(generateOrderNo());
        }
        int rows = orderMapper.insertOrder(order);
        insertOrderItem(order);
        insertStatusLog(order.getOrderId(), null, order.getOrderStatus(), "创建订单");
        return rows;
    }

    @Transactional
    @Override
    public int updateOrder(Order order)
    {
        Order oldOrder = getRequiredOrder(order.getOrderId());
        if (!Arrays.asList(STATUS_DRAFT, STATUS_PENDING_REVIEW).contains(oldOrder.getOrderStatus()))
        {
            throw new ServiceException("当前订单状态不允许修改订单明细");
        }
        normalizeAndValidateOrder(order, false);
        order.setOrderStatus(oldOrder.getOrderStatus());
        order.setOrderNumber(oldOrder.getOrderNumber());
        order.setProductionTaskId(oldOrder.getProductionTaskId());
        order.setStockDeducted(oldOrder.getStockDeducted());
        order.setCancelReason(oldOrder.getCancelReason());
        order.setSubmitReviewTime(oldOrder.getSubmitReviewTime());
        order.setReviewTime(oldOrder.getReviewTime());
        order.setContractTime(oldOrder.getContractTime());
        order.setProductionStartTime(oldOrder.getProductionStartTime());
        order.setProductionFinishTime(oldOrder.getProductionFinishTime());
        order.setOutboundTime(oldOrder.getOutboundTime());
        order.setCompleteTime(oldOrder.getCompleteTime());
        order.setCreateBy(oldOrder.getCreateBy());
        order.setCreateTime(oldOrder.getCreateTime());
        order.setUpdateTime(DateUtils.getNowDate());
        orderMapper.deleteOrderItemByOrderId(order.getOrderId());
        insertOrderItem(order);
        return orderMapper.updateOrder(order);
    }

    @Transactional
    @Override
    public int deleteOrderByOrderIds(Long[] orderIds)
    {
        for (Long orderId : orderIds)
        {
            Order order = getRequiredOrder(orderId);
            if (Arrays.asList(STATUS_OUTBOUND, STATUS_COMPLETED).contains(order.getOrderStatus()))
            {
                throw new ServiceException("已出库或已完成订单不允许删除");
            }
        }
        orderMapper.deleteOrderItemByOrderIds(orderIds);
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    @Transactional
    @Override
    public int deleteOrderByOrderId(Long orderId)
    {
        Order order = getRequiredOrder(orderId);
        if (Arrays.asList(STATUS_OUTBOUND, STATUS_COMPLETED).contains(order.getOrderStatus()))
        {
            throw new ServiceException("已出库或已完成订单不允许删除");
        }
        orderMapper.deleteOrderItemByOrderId(orderId);
        return orderMapper.deleteOrderByOrderId(orderId);
    }

    @Transactional
    @Override
    public int changeOrderStatus(Long orderId, OrderStatusRequest request)
    {
        if (request == null || isBlank(request.getTargetStatus()))
        {
            throw new ServiceException("目标状态不能为空");
        }

        Order order = getRequiredOrder(orderId);
        String currentStatus = order.getOrderStatus();
        String targetStatus = request.getTargetStatus();

        validateStatusTransition(currentStatus, targetStatus);

        Date now = DateUtils.getNowDate();
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setUpdateTime(now);
        order.setOrderStatus(targetStatus);

        if (STATUS_CANCELED.equals(targetStatus))
        {
            order.setCancelReason(request.getRemark());
            syncProductionTaskStatus(order, TASK_CANCELED, request.getRemark());
        }
        else if (STATUS_PENDING_REVIEW.equals(targetStatus))
        {
            order.setSubmitReviewTime(now);
        }
        else if (STATUS_APPROVED.equals(targetStatus))
        {
            order.setReviewTime(now);
            if (Boolean.TRUE.equals(request.getAutoCreateTask()))
            {
                createProductionTask(order, "auto", request.getRemark());
            }
        }
        else if (STATUS_CONTRACT_SIGNED.equals(targetStatus))
        {
            order.setContractTime(now);
        }
        else if (STATUS_IN_PRODUCTION.equals(targetStatus))
        {
            ensureProductionTaskExists(order);
            order.setProductionStartTime(now);
            syncProductionTaskStatus(order, TASK_IN_PROGRESS, request.getRemark());
        }
        else if (STATUS_PRODUCED.equals(targetStatus))
        {
            ensureProductionTaskExists(order);
            order.setProductionFinishTime(now);
            syncProductionTaskStatus(order, TASK_COMPLETED, request.getRemark());
        }
        else if (STATUS_OUTBOUND.equals(targetStatus))
        {
            deductStock(order);
            order.setStockDeducted("1");
            order.setOutboundTime(now);
        }
        else if (STATUS_COMPLETED.equals(targetStatus))
        {
            order.setCompleteTime(now);
        }

        int rows = orderMapper.updateOrder(order);
        insertStatusLog(orderId, currentStatus, targetStatus, request.getRemark());
        return rows;
    }

    @Transactional
    @Override
    public int generateProductionTask(Long orderId, String remark)
    {
        Order order = getRequiredOrder(orderId);
        createProductionTask(order, "manual", remark);
        order.setUpdateBy(SecurityUtils.getUsername());
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    private void normalizeAndValidateOrder(Order order, boolean creating)
    {
        if (order == null)
        {
            throw new ServiceException("订单参数不能为空");
        }

        if (order.getCustomerId() == null)
        {
            throw new ServiceException("请选择客户");
        }

        Customer customer = customerService.selectCustomerByCustomerId(order.getCustomerId());
        if (customer == null)
        {
            throw new ServiceException("客户不存在");
        }

        if (order.getOrderItemList() == null || order.getOrderItemList().isEmpty())
        {
            throw new ServiceException("请至少添加一个订单产品");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        long itemCount = 0L;
        for (OrderItem orderItem : order.getOrderItemList())
        {
            if (orderItem.getProductId() == null)
            {
                throw new ServiceException("请选择产品");
            }
            if (orderItem.getQuantity() == null || orderItem.getQuantity() <= 0)
            {
                throw new ServiceException("产品数量必须大于0");
            }
            Product product = productMapper.selectProductByProductId(orderItem.getProductId());
            if (product == null)
            {
                throw new ServiceException("产品不存在");
            }
            orderItem.setProductName(product.getProductName());
            orderItem.setSpecification(product.getSpecification());
            orderItem.setUnitPrice(product.getUnitPrice());
            BigDecimal amount = product.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            orderItem.setAmount(amount);
            totalAmount = totalAmount.add(amount);
            itemCount += orderItem.getQuantity();
        }

        order.setCustomerName(customer.getCustomerName());
        order.setTotalAmount(totalAmount);
        order.setItemCount(itemCount);

        if (!creating && order.getOrderId() == null)
        {
            throw new ServiceException("订单ID不能为空");
        }
    }

    private void insertOrderItem(Order order)
    {
        List<OrderItem> orderItemList = order.getOrderItemList();
        if (orderItemList != null && !orderItemList.isEmpty())
        {
            for (OrderItem orderItem : orderItemList)
            {
                orderItem.setOrderId(order.getOrderId());
            }
            orderMapper.batchOrderItem(orderItemList);
        }
    }

    private void validateStatusTransition(String currentStatus, String targetStatus)
    {
        if (Objects.equals(currentStatus, targetStatus))
        {
            throw new ServiceException("订单已经处于目标状态");
        }
        if (STATUS_CANCELED.equals(currentStatus) || STATUS_COMPLETED.equals(currentStatus))
        {
            throw new ServiceException("当前订单状态不允许继续流转");
        }
        if (STATUS_CANCELED.equals(targetStatus))
        {
            if (Arrays.asList(STATUS_OUTBOUND, STATUS_COMPLETED).contains(currentStatus))
            {
                throw new ServiceException("已出库或已完成订单不允许取消");
            }
            return;
        }
        String nextStatus = NEXT_STATUS_MAP.get(currentStatus);
        if (!Objects.equals(nextStatus, targetStatus))
        {
            throw new ServiceException("订单状态只能按既定流程逐步流转");
        }
    }

    private void deductStock(Order order)
    {
        if ("1".equals(order.getStockDeducted()))
        {
            throw new ServiceException("该订单已完成库存扣减");
        }
        List<OrderItem> orderItems = orderMapper.selectOrderItemListByOrderId(order.getOrderId());
        for (OrderItem item : orderItems)
        {
            Product product = productMapper.selectProductByProductId(item.getProductId());
            if (product == null)
            {
                throw new ServiceException("产品不存在，无法出库");
            }
            if (product.getStockQuantity() == null || product.getStockQuantity() < item.getQuantity())
            {
                throw new ServiceException("产品【" + product.getProductName() + "】库存不足");
            }
            stockService.recordOutbound(item.getProductId(), item.getQuantity(), order.getOrderNumber(), order.getProductionTaskNo(), "订单出库自动扣减库存");
        }
    }

    private void ensureProductionTaskExists(Order order)
    {
        if (order.getProductionTaskId() == null)
        {
            throw new ServiceException("请先生成生产任务单");
        }
    }

    private void createProductionTask(Order order, String createMode, String remark)
    {
        if (!Arrays.asList(STATUS_APPROVED, STATUS_CONTRACT_SIGNED, STATUS_IN_PRODUCTION, STATUS_PRODUCED).contains(order.getOrderStatus()))
        {
            throw new ServiceException("当前订单状态不允许生成生产任务单");
        }
        if (order.getProductionTaskId() != null)
        {
            throw new ServiceException("该订单已生成生产任务单");
        }

        List<OrderItem> orderItems = orderMapper.selectOrderItemListByOrderId(order.getOrderId());
        long plannedQuantity = orderItems.stream().map(OrderItem::getQuantity).filter(Objects::nonNull).mapToLong(Long::longValue).sum();

        ProductionTask productionTask = new ProductionTask();
        productionTask.setTaskNo(generateTaskNo());
        productionTask.setOrderId(order.getOrderId());
        productionTask.setOrderNumber(order.getOrderNumber());
        productionTask.setTaskStatus(TASK_PENDING);
        productionTask.setPlannedQuantity(plannedQuantity);
        productionTask.setCreateMode(createMode);
        productionTask.setCreateBy(SecurityUtils.getUsername());
        productionTask.setCreateTime(DateUtils.getNowDate());
        productionTask.setRemark(remark);
        productionTaskMapper.insertProductionTask(productionTask);

        order.setProductionTaskId(productionTask.getTaskId());
        order.setProductionTaskNo(productionTask.getTaskNo());
        order.setProductionTaskStatus(productionTask.getTaskStatus());
        insertStatusLog(order.getOrderId(), order.getOrderStatus(), order.getOrderStatus(), "已生成生产任务单：" + productionTask.getTaskNo());
    }

    private void syncProductionTaskStatus(Order order, String taskStatus, String remark)
    {
        if (order.getProductionTaskId() == null)
        {
            return;
        }
        ProductionTask productionTask = new ProductionTask();
        productionTask.setTaskId(order.getProductionTaskId());
        productionTask.setTaskStatus(taskStatus);
        productionTask.setUpdateBy(SecurityUtils.getUsername());
        productionTask.setUpdateTime(DateUtils.getNowDate());
        productionTask.setRemark(remark);
        productionTaskMapper.updateProductionTaskStatus(productionTask);
        order.setProductionTaskStatus(taskStatus);
    }

    private void insertStatusLog(Long orderId, String fromStatus, String toStatus, String remark)
    {
        OrderStatusLog orderStatusLog = new OrderStatusLog();
        orderStatusLog.setOrderId(orderId);
        orderStatusLog.setFromStatus(fromStatus);
        orderStatusLog.setToStatus(toStatus);
        orderStatusLog.setOperationRemark(remark);
        orderStatusLog.setOperationBy(SecurityUtils.getUsername());
        orderStatusLog.setOperationTime(new Date());
        orderStatusLogMapper.insertOrderStatusLog(orderStatusLog);
    }

    private Order getRequiredOrder(Long orderId)
    {
        Order order = orderMapper.selectOrderByOrderId(orderId);
        if (order == null)
        {
            throw new ServiceException("订单不存在");
        }
        return order;
    }

    private String generateOrderNo()
    {
        return "ORD" + DateUtils.dateTimeNow("yyyyMMddHHmmss") + randomSuffix();
    }

    private String generateTaskNo()
    {
        return "TASK" + DateUtils.dateTimeNow("yyyyMMddHHmmss") + randomSuffix();
    }

    private String randomSuffix()
    {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 4).toUpperCase();
    }

    private boolean isBlank(String value)
    {
        return value == null || value.trim().isEmpty();
    }
}

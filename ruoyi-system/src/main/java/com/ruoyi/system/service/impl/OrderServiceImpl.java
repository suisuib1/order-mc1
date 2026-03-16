package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.OrderItem;
import com.ruoyi.system.mapper.OrderMapper;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.service.IOrderService;

/**
 * 订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
@Service
public class OrderServiceImpl implements IOrderService 
{
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public Order selectOrderByOrderId(Long orderId)
    {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        int rows = orderMapper.insertOrder(order);
        insertOrderItem(order);
        return rows;
    }

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        orderMapper.deleteOrderItemByOrderId(order.getOrderId());
        insertOrderItem(order);
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderByOrderIds(Long[] orderIds)
    {
        orderMapper.deleteOrderItemByOrderIds(orderIds);
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteOrderByOrderId(Long orderId)
    {
        orderMapper.deleteOrderItemByOrderId(orderId);
        return orderMapper.deleteOrderByOrderId(orderId);
    }

    /**
     * 新增订单项信息
     * 
     * @param order 订单对象
     */
    public void insertOrderItem(Order order)
    {
        List<OrderItem> orderItemList = order.getOrderItemList();
        Long orderId = order.getOrderId();
        if (orderItemList != null && !orderItemList.isEmpty())
        {
            for (OrderItem orderItem : orderItemList)
            {
                orderItem.setOrderId(orderId);
            }
            orderMapper.batchOrderItem(orderItemList);
        }
    }
}

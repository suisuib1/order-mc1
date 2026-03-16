package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.vo.OrderStatusRequest;

/**
 * 订单Service接口
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public interface IOrderService 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public Order selectOrderByOrderId(Long orderId);

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(Long[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(Long orderId);

    /**
     * 流转订单状态
     *
     * @param orderId 订单ID
     * @param request 状态流转参数
     * @return 结果
     */
    public int changeOrderStatus(Long orderId, OrderStatusRequest request);

    /**
     * 生成生产任务单
     *
     * @param orderId 订单ID
     * @param remark 备注
     * @return 结果
     */
    public int generateProductionTask(Long orderId, String remark);
}

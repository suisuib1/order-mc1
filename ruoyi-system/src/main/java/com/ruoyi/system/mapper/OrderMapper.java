package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.OrderItem;

/**
 * 订单Mapper接口
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public interface OrderMapper 
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
     * 查询订单项列表
     *
     * @param orderId 订单ID
     * @return 订单项列表
     */
    public List<OrderItem> selectOrderItemListByOrderId(Long orderId);

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
     * 删除订单
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(Long orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(Long[] orderIds);

    /**
     * 批量删除订单项
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderItemByOrderIds(Long[] orderIds);
    
    /**
     * 批量新增订单项
     * 
     * @param orderItemList 订单项列表
     * @return 结果
     */
    public int batchOrderItem(List<OrderItem> orderItemList);
    

    /**
     * 通过订单主键删除订单项信息
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteOrderItemByOrderId(Long orderId);
}

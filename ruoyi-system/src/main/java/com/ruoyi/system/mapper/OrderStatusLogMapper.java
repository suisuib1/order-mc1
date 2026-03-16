package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.OrderStatusLog;

public interface OrderStatusLogMapper
{
    public int insertOrderStatusLog(OrderStatusLog orderStatusLog);

    public List<OrderStatusLog> selectOrderStatusLogListByOrderId(Long orderId);
}

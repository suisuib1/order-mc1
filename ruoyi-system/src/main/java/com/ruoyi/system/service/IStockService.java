package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.domain.StockFlow;
import com.ruoyi.system.domain.vo.StockOperateRequest;

public interface IStockService
{
    public List<Product> selectStockList(Product product);

    public List<StockFlow> selectStockFlowList(StockFlow stockFlow);

    public int stockIn(StockOperateRequest request);

    public int stockAdjust(StockOperateRequest request);

    public void recordOutbound(Long productId, Long quantity, String orderNumber, String taskNo, String remark);
}

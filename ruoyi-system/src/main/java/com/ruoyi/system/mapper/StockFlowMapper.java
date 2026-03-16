package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.StockFlow;

public interface StockFlowMapper
{
    public int insertStockFlow(StockFlow stockFlow);

    public List<StockFlow> selectStockFlowList(StockFlow stockFlow);
}

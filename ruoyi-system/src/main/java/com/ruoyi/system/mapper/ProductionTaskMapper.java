package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.ProductionTask;

public interface ProductionTaskMapper
{
    public int insertProductionTask(ProductionTask productionTask);

    public List<ProductionTask> selectProductionTaskListByOrderId(Long orderId);

    public ProductionTask selectLatestProductionTaskByOrderId(Long orderId);

    public int updateProductionTaskStatus(ProductionTask productionTask);
}

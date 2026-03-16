package com.ruoyi.web.controller.system;

import java.util.List;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.domain.StockFlow;
import com.ruoyi.system.domain.vo.StockOperateRequest;
import com.ruoyi.system.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timber/stock")
public class StockController extends BaseController
{
    @Autowired
    private IStockService stockService;

    @PreAuthorize("@ss.hasPermi('timber:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = stockService.selectStockList(product);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('timber:stock:list')")
    @GetMapping("/flow/list")
    public TableDataInfo flowList(StockFlow stockFlow)
    {
        startPage();
        List<StockFlow> list = stockService.selectStockFlowList(stockFlow);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('timber:stock:edit')")
    @Log(title = "库存管理", businessType = BusinessType.UPDATE)
    @PostMapping("/in")
    public AjaxResult stockIn(@RequestBody StockOperateRequest request)
    {
        return toAjax(stockService.stockIn(request));
    }

    @PreAuthorize("@ss.hasPermi('timber:stock:edit')")
    @Log(title = "库存管理", businessType = BusinessType.UPDATE)
    @PostMapping("/adjust")
    public AjaxResult stockAdjust(@RequestBody StockOperateRequest request)
    {
        return toAjax(stockService.stockAdjust(request));
    }
}

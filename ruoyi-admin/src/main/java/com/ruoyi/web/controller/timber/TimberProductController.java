package com.ruoyi.web.controller.timber;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.service.IProductService;

@RestController
@RequestMapping("/timber/product")
public class TimberProductController extends BaseController
{
    @Autowired
    private IProductService productService;

    @PreAuthorize("@ss.hasPermi('timber:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = productService.selectProductList(product);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('timber:product:list')")
    @GetMapping("/{productId:\\d+}")
    public AjaxResult getInfo(@PathVariable Long productId)
    {
        return success(productService.selectProductByProductId(productId));
    }

    @PreAuthorize("@ss.hasPermi('timber:product:list')")
    @Log(title = "产品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Product product)
    {
        product.setCreateBy(getUsername());
        return toAjax(productService.insertProduct(product));
    }

    @PreAuthorize("@ss.hasPermi('timber:product:list')")
    @Log(title = "产品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Product product)
    {
        product.setUpdateBy(getUsername());
        return toAjax(productService.updateProduct(product));
    }

    @PreAuthorize("@ss.hasPermi('timber:product:list')")
    @Log(title = "产品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(productService.deleteProductByProductIds(productIds));
    }
}

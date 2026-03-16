package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Customer;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.domain.vo.OrderStatusRequest;
import com.ruoyi.system.service.ICustomerService;
import com.ruoyi.system.service.IOrderService;
import com.ruoyi.system.service.IProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单Controller
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
@RestController
@RequestMapping("/timber/order")
public class OrderController extends BaseController
{
    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProductService productService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('timber:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('timber:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order)
    {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('timber:order:query')")
    @GetMapping("/baseData")
    public AjaxResult baseData()
    {
        Map<String, Object> data = new HashMap<>();
        data.put("customers", customerService.selectCustomerList(new Customer()));
        data.put("products", productService.selectProductList(new Product()));
        return success(data);
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('timber:order:query')")
    @GetMapping(value = "/{orderId:\\d+}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(orderService.selectOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('timber:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order)
    {
        order.setCreateBy(getUsername());
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('timber:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order)
    {
        order.setUpdateBy(getUsername());
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('timber:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    }

    /**
     * 订单状态流转
     */
    @PreAuthorize("@ss.hasPermi('timber:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/{orderId:\\d+}/status")
    public AjaxResult changeStatus(@PathVariable Long orderId, @RequestBody OrderStatusRequest request)
    {
        return toAjax(orderService.changeOrderStatus(orderId, request));
    }

    /**
     * 生成生产任务单
     */
    @PreAuthorize("@ss.hasPermi('timber:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PostMapping("/{orderId:\\d+}/generateTask")
    public AjaxResult generateTask(@PathVariable Long orderId, @RequestParam(required = false) String remark)
    {
        return toAjax(orderService.generateProductionTask(orderId, remark));
    }
}

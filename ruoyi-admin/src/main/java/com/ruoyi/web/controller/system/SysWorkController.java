package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysWork;
import com.ruoyi.system.service.ISysWorkService;

@RestController
@RequestMapping("/system/work")
public class SysWorkController extends BaseController
{
    @Autowired
    private ISysWorkService workService;

    @PreAuthorize("@ss.hasPermi('system:work:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWork work)
    {
        startPage();
        List<SysWork> list = workService.selectWorkList(work);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:work:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(workService.selectWorkById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:work:add')")
    @Log(title = "作品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWork work)
    {
        work.setCreateBy(getUsername());
        return toAjax(workService.insertWork(work));
    }

    @PreAuthorize("@ss.hasPermi('system:work:edit')")
    @Log(title = "作品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWork work)
    {
        work.setUpdateBy(getUsername());
        return toAjax(workService.updateWork(work));
    }

    @PreAuthorize("@ss.hasPermi('system:work:remove')")
    @Log(title = "作品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(workService.deleteWorkByIds(ids));
    }
}

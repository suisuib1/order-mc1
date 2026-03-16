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
import com.ruoyi.system.domain.SysExperience;
import com.ruoyi.system.service.ISysExperienceService;

@RestController
@RequestMapping("/system/experience")
public class SysExperienceController extends BaseController
{
    @Autowired
    private ISysExperienceService experienceService;

    @PreAuthorize("@ss.hasPermi('system:experience:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysExperience experience)
    {
        startPage();
        List<SysExperience> list = experienceService.selectExperienceList(experience);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:experience:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(experienceService.selectExperienceById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:experience:add')")
    @Log(title = "经历管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysExperience experience)
    {
        experience.setCreateBy(getUsername());
        return toAjax(experienceService.insertExperience(experience));
    }

    @PreAuthorize("@ss.hasPermi('system:experience:edit')")
    @Log(title = "经历管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysExperience experience)
    {
        experience.setUpdateBy(getUsername());
        return toAjax(experienceService.updateExperience(experience));
    }

    @PreAuthorize("@ss.hasPermi('system:experience:remove')")
    @Log(title = "经历管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(experienceService.deleteExperienceByIds(ids));
    }
}

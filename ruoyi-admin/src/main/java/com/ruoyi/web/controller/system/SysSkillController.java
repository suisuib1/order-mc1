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
import com.ruoyi.system.domain.SysSkill;
import com.ruoyi.system.service.ISysSkillService;

@RestController
@RequestMapping("/system/skill")
public class SysSkillController extends BaseController
{
    @Autowired
    private ISysSkillService skillService;

    @PreAuthorize("@ss.hasPermi('system:skill:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSkill skill)
    {
        startPage();
        List<SysSkill> list = skillService.selectSkillList(skill);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:skill:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(skillService.selectSkillById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:skill:add')")
    @Log(title = "技能管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSkill skill)
    {
        skill.setCreateBy(getUsername());
        return toAjax(skillService.insertSkill(skill));
    }

    @PreAuthorize("@ss.hasPermi('system:skill:edit')")
    @Log(title = "技能管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSkill skill)
    {
        skill.setUpdateBy(getUsername());
        return toAjax(skillService.updateSkill(skill));
    }

    @PreAuthorize("@ss.hasPermi('system:skill:remove')")
    @Log(title = "技能管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(skillService.deleteSkillByIds(ids));
    }
}

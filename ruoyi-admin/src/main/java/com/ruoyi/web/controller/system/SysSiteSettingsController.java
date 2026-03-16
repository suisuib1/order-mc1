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
import com.ruoyi.system.domain.SysSiteSettings;
import com.ruoyi.system.service.ISysSiteSettingsService;

@RestController
@RequestMapping("/system/settings")
public class SysSiteSettingsController extends BaseController
{
    @Autowired
    private ISysSiteSettingsService settingsService;

    @PreAuthorize("@ss.hasPermi('system:settings:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSiteSettings settings)
    {
        startPage();
        List<SysSiteSettings> list = settingsService.selectSiteSettingsList(settings);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:settings:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(settingsService.selectSiteSettingsById(id));
    }

    @GetMapping
    public AjaxResult get()
    {
        return success(settingsService.selectSiteSettings());
    }

    @PreAuthorize("@ss.hasPermi('system:settings:edit')")
    @Log(title = "网站设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSiteSettings settings)
    {
        SysSiteSettings existing = settingsService.selectSiteSettings();
        if (existing != null)
        {
            settings.setId(existing.getId());
            settings.setUpdateBy(getUsername());
            return toAjax(settingsService.updateSiteSettings(settings));
        }
        else
        {
            settings.setCreateBy(getUsername());
            return toAjax(settingsService.insertSiteSettings(settings));
        }
    }
}

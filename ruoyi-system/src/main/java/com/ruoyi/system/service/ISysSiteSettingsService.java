package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysSiteSettings;

public interface ISysSiteSettingsService
{
    public SysSiteSettings selectSiteSettingsById(Long id);
    public List<SysSiteSettings> selectSiteSettingsList(SysSiteSettings settings);
    public int insertSiteSettings(SysSiteSettings settings);
    public int updateSiteSettings(SysSiteSettings settings);
    public SysSiteSettings selectSiteSettings();
}

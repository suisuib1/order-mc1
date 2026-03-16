package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysSiteSettings;

public interface SysSiteSettingsMapper
{
    public SysSiteSettings selectSiteSettingsById(Long id);
    public List<SysSiteSettings> selectSiteSettingsList(SysSiteSettings settings);
    public int insertSiteSettings(SysSiteSettings settings);
    public int updateSiteSettings(SysSiteSettings settings);
    public SysSiteSettings selectSiteSettings();
}

package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysSiteSettingsMapper;
import com.ruoyi.system.domain.SysSiteSettings;
import com.ruoyi.system.service.ISysSiteSettingsService;

@Service
public class SysSiteSettingsServiceImpl implements ISysSiteSettingsService
{
    @Autowired
    private SysSiteSettingsMapper siteSettingsMapper;

    @Override
    public SysSiteSettings selectSiteSettingsById(Long id)
    {
        return siteSettingsMapper.selectSiteSettingsById(id);
    }

    @Override
    public List<SysSiteSettings> selectSiteSettingsList(SysSiteSettings settings)
    {
        return siteSettingsMapper.selectSiteSettingsList(settings);
    }

    @Override
    public int insertSiteSettings(SysSiteSettings settings)
    {
        return siteSettingsMapper.insertSiteSettings(settings);
    }

    @Override
    public int updateSiteSettings(SysSiteSettings settings)
    {
        return siteSettingsMapper.updateSiteSettings(settings);
    }

    @Override
    public SysSiteSettings selectSiteSettings()
    {
        return siteSettingsMapper.selectSiteSettings();
    }
}

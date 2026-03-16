package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysExperienceMapper;
import com.ruoyi.system.domain.SysExperience;
import com.ruoyi.system.service.ISysExperienceService;

@Service
public class SysExperienceServiceImpl implements ISysExperienceService
{
    @Autowired
    private SysExperienceMapper experienceMapper;

    @Override
    public SysExperience selectExperienceById(Long id)
    {
        return experienceMapper.selectExperienceById(id);
    }

    @Override
    public List<SysExperience> selectExperienceList(SysExperience experience)
    {
        return experienceMapper.selectExperienceList(experience);
    }

    @Override
    public int insertExperience(SysExperience experience)
    {
        return experienceMapper.insertExperience(experience);
    }

    @Override
    public int updateExperience(SysExperience experience)
    {
        return experienceMapper.updateExperience(experience);
    }

    @Override
    public int deleteExperienceById(Long id)
    {
        return experienceMapper.deleteExperienceById(id);
    }

    @Override
    public int deleteExperienceByIds(Long[] ids)
    {
        return experienceMapper.deleteExperienceByIds(ids);
    }
}

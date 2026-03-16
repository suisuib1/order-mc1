package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysExperience;

public interface ISysExperienceService
{
    public SysExperience selectExperienceById(Long id);
    public List<SysExperience> selectExperienceList(SysExperience experience);
    public int insertExperience(SysExperience experience);
    public int updateExperience(SysExperience experience);
    public int deleteExperienceById(Long id);
    public int deleteExperienceByIds(Long[] ids);
}

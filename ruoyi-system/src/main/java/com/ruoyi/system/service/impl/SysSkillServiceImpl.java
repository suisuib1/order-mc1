package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysSkillMapper;
import com.ruoyi.system.domain.SysSkill;
import com.ruoyi.system.service.ISysSkillService;

@Service
public class SysSkillServiceImpl implements ISysSkillService
{
    @Autowired
    private SysSkillMapper skillMapper;

    @Override
    public SysSkill selectSkillById(Long id)
    {
        return skillMapper.selectSkillById(id);
    }

    @Override
    public List<SysSkill> selectSkillList(SysSkill skill)
    {
        return skillMapper.selectSkillList(skill);
    }

    @Override
    public int insertSkill(SysSkill skill)
    {
        return skillMapper.insertSkill(skill);
    }

    @Override
    public int updateSkill(SysSkill skill)
    {
        return skillMapper.updateSkill(skill);
    }

    @Override
    public int deleteSkillById(Long id)
    {
        return skillMapper.deleteSkillById(id);
    }

    @Override
    public int deleteSkillByIds(Long[] ids)
    {
        return skillMapper.deleteSkillByIds(ids);
    }
}

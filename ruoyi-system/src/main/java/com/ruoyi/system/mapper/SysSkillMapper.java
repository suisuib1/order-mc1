package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysSkill;

public interface SysSkillMapper
{
    public SysSkill selectSkillById(Long id);
    public List<SysSkill> selectSkillList(SysSkill skill);
    public int insertSkill(SysSkill skill);
    public int updateSkill(SysSkill skill);
    public int deleteSkillById(Long id);
    public int deleteSkillByIds(Long[] ids);
}

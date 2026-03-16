package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysWork;

public interface ISysWorkService
{
    public SysWork selectWorkById(Long id);
    public List<SysWork> selectWorkList(SysWork work);
    public int insertWork(SysWork work);
    public int updateWork(SysWork work);
    public int deleteWorkById(Long id);
    public int deleteWorkByIds(Long[] ids);
}

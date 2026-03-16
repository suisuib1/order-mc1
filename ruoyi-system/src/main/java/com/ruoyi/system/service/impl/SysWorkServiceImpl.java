package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysWorkMapper;
import com.ruoyi.system.domain.SysWork;
import com.ruoyi.system.service.ISysWorkService;

@Service
public class SysWorkServiceImpl implements ISysWorkService
{
    @Autowired
    private SysWorkMapper workMapper;

    @Override
    public SysWork selectWorkById(Long id)
    {
        return workMapper.selectWorkById(id);
    }

    @Override
    public List<SysWork> selectWorkList(SysWork work)
    {
        return workMapper.selectWorkList(work);
    }

    @Override
    public int insertWork(SysWork work)
    {
        return workMapper.insertWork(work);
    }

    @Override
    public int updateWork(SysWork work)
    {
        return workMapper.updateWork(work);
    }

    @Override
    public int deleteWorkById(Long id)
    {
        return workMapper.deleteWorkById(id);
    }

    @Override
    public int deleteWorkByIds(Long[] ids)
    {
        return workMapper.deleteWorkByIds(ids);
    }
}

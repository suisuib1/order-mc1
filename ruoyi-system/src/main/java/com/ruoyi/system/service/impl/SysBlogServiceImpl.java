package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysBlogMapper;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.service.ISysBlogService;

@Service
public class SysBlogServiceImpl implements ISysBlogService
{
    @Autowired
    private SysBlogMapper blogMapper;

    @Override
    public SysBlog selectBlogById(Long id)
    {
        return blogMapper.selectBlogById(id);
    }

    @Override
    public List<SysBlog> selectBlogList(SysBlog blog)
    {
        return blogMapper.selectBlogList(blog);
    }

    @Override
    public int insertBlog(SysBlog blog)
    {
        return blogMapper.insertBlog(blog);
    }

    @Override
    public int updateBlog(SysBlog blog)
    {
        return blogMapper.updateBlog(blog);
    }

    @Override
    public int deleteBlogById(Long id)
    {
        return blogMapper.deleteBlogById(id);
    }

    @Override
    public int deleteBlogByIds(Long[] ids)
    {
        return blogMapper.deleteBlogByIds(ids);
    }
}

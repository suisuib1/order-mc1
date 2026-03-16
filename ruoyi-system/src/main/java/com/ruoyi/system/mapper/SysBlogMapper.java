package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysBlog;

public interface SysBlogMapper
{
    public SysBlog selectBlogById(Long id);
    public List<SysBlog> selectBlogList(SysBlog blog);
    public int insertBlog(SysBlog blog);
    public int updateBlog(SysBlog blog);
    public int deleteBlogById(Long id);
    public int deleteBlogByIds(Long[] ids);
}

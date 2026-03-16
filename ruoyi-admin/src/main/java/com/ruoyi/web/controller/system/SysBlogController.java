package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.service.ISysBlogService;

@RestController
@RequestMapping("/system/blog")
public class SysBlogController extends BaseController
{
    @Autowired
    private ISysBlogService blogService;

    @PreAuthorize("@ss.hasPermi('system:blog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBlog blog)
    {
        startPage();
        List<SysBlog> list = blogService.selectBlogList(blog);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:blog:export')")
    @Log(title = "博客管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysBlog blog)
    {
        List<SysBlog> list = blogService.selectBlogList(blog);
    }

    @PreAuthorize("@ss.hasPermi('system:blog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        return success(blogService.selectBlogById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:blog:add')")
    @Log(title = "博客管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBlog blog)
    {
        blog.setCreateBy(getUsername());
        return toAjax(blogService.insertBlog(blog));
    }

    @PreAuthorize("@ss.hasPermi('system:blog:edit')")
    @Log(title = "博客管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBlog blog)
    {
        blog.setUpdateBy(getUsername());
        return toAjax(blogService.updateBlog(blog));
    }

    @PreAuthorize("@ss.hasPermi('system:blog:remove')")
    @Log(title = "博客管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blogService.deleteBlogByIds(ids));
    }
}

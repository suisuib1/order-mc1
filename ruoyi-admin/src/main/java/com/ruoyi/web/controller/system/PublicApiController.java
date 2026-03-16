package com.ruoyi.web.controller.system;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.SysBlog;
import com.ruoyi.system.domain.SysWork;
import com.ruoyi.system.domain.SysSkill;
import com.ruoyi.system.domain.SysExperience;
import com.ruoyi.system.domain.SysSiteSettings;
import com.ruoyi.system.service.ISysBlogService;
import com.ruoyi.system.service.ISysWorkService;
import com.ruoyi.system.service.ISysSkillService;
import com.ruoyi.system.service.ISysExperienceService;
import com.ruoyi.system.service.ISysSiteSettingsService;

@RestController
@Anonymous
@RequestMapping("/api")
public class PublicApiController extends BaseController
{
    @Autowired
    private ISysBlogService blogService;
    @Autowired
    private ISysWorkService workService;
    @Autowired
    private ISysSkillService skillService;
    @Autowired
    private ISysExperienceService experienceService;
    @Autowired
    private ISysSiteSettingsService settingsService;

    @GetMapping("/blogs")
    public AjaxResult getBlogs()
    {
        SysBlog blog = new SysBlog();
        blog.setPublished(1);
        List<SysBlog> list = blogService.selectBlogList(blog);
        return success(list);
    }

    @GetMapping("/works")
    public AjaxResult getWorks()
    {
        List<SysWork> list = workService.selectWorkList(new SysWork());
        return success(list);
    }

    @GetMapping("/skills")
    public AjaxResult getSkills()
    {
        List<SysSkill> list = skillService.selectSkillList(new SysSkill());
        return success(list);
    }

    @GetMapping("/experiences")
    public AjaxResult getExperiences()
    {
        List<SysExperience> list = experienceService.selectExperienceList(new SysExperience());
        return success(list);
    }

    @GetMapping("/settings")
    public AjaxResult getSettings()
    {
        SysSiteSettings settings = settingsService.selectSiteSettings();
        return success(settings);
    }

    @GetMapping("/site")
    public AjaxResult getSite()
    {
        SysSiteSettings settings = settingsService.selectSiteSettings();
        SysBlog blog = new SysBlog();
        blog.setPublished(1);
        List<SysBlog> blogs = blogService.selectBlogList(blog);
        List<SysWork> works = workService.selectWorkList(new SysWork());
        List<SysSkill> skills = skillService.selectSkillList(new SysSkill());
        List<SysExperience> experiences = experienceService.selectExperienceList(new SysExperience());
        
        return success(java.util.Map.of(
            "settings", settings != null ? settings : new SysSiteSettings(),
            "blogs", blogs,
            "works", works,
            "skills", skills,
            "experiences", experiences
        ));
    }
}

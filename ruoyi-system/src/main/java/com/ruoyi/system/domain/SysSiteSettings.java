package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysSiteSettings extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "网站名称")
    private String siteName;

    @Excel(name = "网站描述")
    private String siteDescription;

    private String about;

    private String avatar;

    private String email;

    private String github;

    private String juejin;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public String getSiteDescription()
    {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription)
    {
        this.siteDescription = siteDescription;
    }

    public String getAbout()
    {
        return about;
    }

    public void setAbout(String about)
    {
        this.about = about;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getGithub()
    {
        return github;
    }

    public void setGithub(String github)
    {
        this.github = github;
    }

    public String getJuejin()
    {
        return juejin;
    }

    public void setJuejin(String juejin)
    {
        this.juejin = juejin;
    }
}

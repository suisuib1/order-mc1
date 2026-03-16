package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysBlog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "标题")
    private String title;

    private String excerpt;

    @Excel(name = "内容")
    private String content;

    @Excel(name = "分类")
    private String category;

    private String readTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    @Excel(name = "发布状态", readConverterExp = "0=草稿,1=已发布")
    private Integer published;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getExcerpt()
    {
        return excerpt;
    }

    public void setExcerpt(String excerpt)
    {
        this.excerpt = excerpt;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getReadTime()
    {
        return readTime;
    }

    public void setReadTime(String readTime)
    {
        this.readTime = readTime;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Integer getPublished()
    {
        return published;
    }

    public void setPublished(Integer published)
    {
        this.published = published;
    }
}

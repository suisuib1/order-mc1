package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class SysSkill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "技能名称")
    private String name;

    private String icon;

    @Excel(name = "熟练度", cellType = Excel.ColumnType.NUMERIC)
    private Integer level;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }
}

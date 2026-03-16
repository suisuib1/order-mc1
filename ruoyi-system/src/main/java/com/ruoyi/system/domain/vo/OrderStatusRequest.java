package com.ruoyi.system.domain.vo;

public class OrderStatusRequest
{
    private String targetStatus;

    private String remark;

    private Boolean autoCreateTask;

    public String getTargetStatus()
    {
        return targetStatus;
    }

    public void setTargetStatus(String targetStatus)
    {
        this.targetStatus = targetStatus;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Boolean getAutoCreateTask()
    {
        return autoCreateTask;
    }

    public void setAutoCreateTask(Boolean autoCreateTask)
    {
        this.autoCreateTask = autoCreateTask;
    }
}

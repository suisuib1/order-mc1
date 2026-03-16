package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ProductionTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long taskId;

    private String taskNo;

    private Long orderId;

    private String orderNumber;

    private String taskStatus;

    private Long plannedQuantity;

    private String createMode;

    private Long assigneeId;

    public Long getTaskId()
    {
        return taskId;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskNo()
    {
        return taskNo;
    }

    public void setTaskNo(String taskNo)
    {
        this.taskNo = taskNo;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus)
    {
        this.taskStatus = taskStatus;
    }

    public Long getPlannedQuantity()
    {
        return plannedQuantity;
    }

    public void setPlannedQuantity(Long plannedQuantity)
    {
        this.plannedQuantity = plannedQuantity;
    }

    public String getCreateMode()
    {
        return createMode;
    }

    public void setCreateMode(String createMode)
    {
        this.createMode = createMode;
    }

    public Long getAssigneeId()
    {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId)
    {
        this.assigneeId = assigneeId;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("taskNo", getTaskNo())
            .append("orderId", getOrderId())
            .append("orderNumber", getOrderNumber())
            .append("taskStatus", getTaskStatus())
            .append("plannedQuantity", getPlannedQuantity())
            .append("createMode", getCreateMode())
            .append("assigneeId", getAssigneeId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}

package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class OrderStatusLog
{
    private Long logId;

    private Long orderId;

    private String fromStatus;

    private String toStatus;

    private String operationRemark;

    private String operationBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    public Long getLogId()
    {
        return logId;
    }

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getFromStatus()
    {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus)
    {
        this.fromStatus = fromStatus;
    }

    public String getToStatus()
    {
        return toStatus;
    }

    public void setToStatus(String toStatus)
    {
        this.toStatus = toStatus;
    }

    public String getOperationRemark()
    {
        return operationRemark;
    }

    public void setOperationRemark(String operationRemark)
    {
        this.operationRemark = operationRemark;
    }

    public String getOperationBy()
    {
        return operationBy;
    }

    public void setOperationBy(String operationBy)
    {
        this.operationBy = operationBy;
    }

    public Date getOperationTime()
    {
        return operationTime;
    }

    public void setOperationTime(Date operationTime)
    {
        this.operationTime = operationTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("orderId", getOrderId())
            .append("fromStatus", getFromStatus())
            .append("toStatus", getToStatus())
            .append("operationRemark", getOperationRemark())
            .append("operationBy", getOperationBy())
            .append("operationTime", getOperationTime())
            .toString();
    }
}

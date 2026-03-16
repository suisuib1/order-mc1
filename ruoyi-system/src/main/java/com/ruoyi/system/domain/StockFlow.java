package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StockFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long flowId;

    @Excel(name = "产品ID")
    private Long productId;

    @Excel(name = "产品名称")
    private String productName;

    @Excel(name = "规格")
    private String specification;

    @Excel(name = "流水类型")
    private String flowType;

    @Excel(name = "变动数量")
    private Long changeQuantity;

    @Excel(name = "变动前库存")
    private Long beforeQuantity;

    @Excel(name = "变动后库存")
    private Long afterQuantity;

    @Excel(name = "订单单号")
    private String orderNumber;

    @Excel(name = "任务单号")
    private String taskNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date businessTime;

    public Long getFlowId()
    {
        return flowId;
    }

    public void setFlowId(Long flowId)
    {
        this.flowId = flowId;
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getSpecification()
    {
        return specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    public String getFlowType()
    {
        return flowType;
    }

    public void setFlowType(String flowType)
    {
        this.flowType = flowType;
    }

    public Long getChangeQuantity()
    {
        return changeQuantity;
    }

    public void setChangeQuantity(Long changeQuantity)
    {
        this.changeQuantity = changeQuantity;
    }

    public Long getBeforeQuantity()
    {
        return beforeQuantity;
    }

    public void setBeforeQuantity(Long beforeQuantity)
    {
        this.beforeQuantity = beforeQuantity;
    }

    public Long getAfterQuantity()
    {
        return afterQuantity;
    }

    public void setAfterQuantity(Long afterQuantity)
    {
        this.afterQuantity = afterQuantity;
    }

    public String getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }

    public String getTaskNo()
    {
        return taskNo;
    }

    public void setTaskNo(String taskNo)
    {
        this.taskNo = taskNo;
    }

    public Date getBusinessTime()
    {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime)
    {
        this.businessTime = businessTime;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("flowId", getFlowId())
            .append("productId", getProductId())
            .append("productName", getProductName())
            .append("specification", getSpecification())
            .append("flowType", getFlowType())
            .append("changeQuantity", getChangeQuantity())
            .append("beforeQuantity", getBeforeQuantity())
            .append("afterQuantity", getAfterQuantity())
            .append("orderNumber", getOrderNumber())
            .append("taskNo", getTaskNo())
            .append("businessTime", getBusinessTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .toString();
    }
}

package com.ruoyi.system.domain.vo;

public class StockOperateRequest
{
    private Long productId;

    private Long quantity;

    private Long targetQuantity;

    private String orderNumber;

    private String taskNo;

    private String remark;

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public Long getTargetQuantity()
    {
        return targetQuantity;
    }

    public void setTargetQuantity(Long targetQuantity)
    {
        this.targetQuantity = targetQuantity;
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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}

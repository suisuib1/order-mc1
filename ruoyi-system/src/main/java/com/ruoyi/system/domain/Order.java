package com.ruoyi.system.domain;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单对象 t_order
 * 
 * @author ruoyi
 * @date 2026-03-16
 */
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    private Long orderId;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNumber;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String orderStatus;

    /** 产品名称(查询用) */
    private String productName;

    /** 取消原因 */
    private String cancelReason;

    /** 是否已扣减库存 */
    private String stockDeducted;

    /** 生产任务ID */
    private Long productionTaskId;

    /** 生产任务单号 */
    private String productionTaskNo;

    /** 生产任务状态 */
    private String productionTaskStatus;

    /** 订单项数量 */
    private Long itemCount;

    /** 审核通过后是否自动生成任务单 */
    private Boolean autoCreateTask;

    /** 提交审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitReviewTime;

    /** 审核通过时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    /** 合同签订时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date contractTime;

    /** 开始生产时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productionStartTime;

    /** 完成生产时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date productionFinishTime;

    /** 出库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outboundTime;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date completeTime;

    /** 订单项信息 */
    private List<OrderItem> orderItemList;

    /** 状态流转记录 */
    private List<OrderStatusLog> statusLogList;

    /** 生产任务信息 */
    private List<ProductionTask> productionTaskList;

    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setOrderNumber(String orderNumber) 
    {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() 
    {
        return orderNumber;
    }
    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo()
    {
        return contractNo;
    }
    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName()
    {
        return productName;
    }
    public void setCancelReason(String cancelReason)
    {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason()
    {
        return cancelReason;
    }
    public void setStockDeducted(String stockDeducted)
    {
        this.stockDeducted = stockDeducted;
    }

    public String getStockDeducted()
    {
        return stockDeducted;
    }
    public void setProductionTaskId(Long productionTaskId)
    {
        this.productionTaskId = productionTaskId;
    }

    public Long getProductionTaskId()
    {
        return productionTaskId;
    }
    public void setProductionTaskNo(String productionTaskNo)
    {
        this.productionTaskNo = productionTaskNo;
    }

    public String getProductionTaskNo()
    {
        return productionTaskNo;
    }
    public void setProductionTaskStatus(String productionTaskStatus)
    {
        this.productionTaskStatus = productionTaskStatus;
    }

    public String getProductionTaskStatus()
    {
        return productionTaskStatus;
    }
    public void setItemCount(Long itemCount)
    {
        this.itemCount = itemCount;
    }

    public Long getItemCount()
    {
        return itemCount;
    }
    public void setAutoCreateTask(Boolean autoCreateTask)
    {
        this.autoCreateTask = autoCreateTask;
    }

    public Boolean getAutoCreateTask()
    {
        return autoCreateTask;
    }
    public void setSubmitReviewTime(Date submitReviewTime)
    {
        this.submitReviewTime = submitReviewTime;
    }

    public Date getSubmitReviewTime()
    {
        return submitReviewTime;
    }
    public void setReviewTime(Date reviewTime)
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime()
    {
        return reviewTime;
    }
    public void setContractTime(Date contractTime)
    {
        this.contractTime = contractTime;
    }

    public Date getContractTime()
    {
        return contractTime;
    }
    public void setProductionStartTime(Date productionStartTime)
    {
        this.productionStartTime = productionStartTime;
    }

    public Date getProductionStartTime()
    {
        return productionStartTime;
    }
    public void setProductionFinishTime(Date productionFinishTime)
    {
        this.productionFinishTime = productionFinishTime;
    }

    public Date getProductionFinishTime()
    {
        return productionFinishTime;
    }
    public void setOutboundTime(Date outboundTime)
    {
        this.outboundTime = outboundTime;
    }

    public Date getOutboundTime()
    {
        return outboundTime;
    }
    public void setCompleteTime(Date completeTime)
    {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime()
    {
        return completeTime;
    }

    public List<OrderItem> getOrderItemList()
    {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList)
    {
        this.orderItemList = orderItemList;
    }

    public List<OrderStatusLog> getStatusLogList()
    {
        return statusLogList;
    }

    public void setStatusLogList(List<OrderStatusLog> statusLogList)
    {
        this.statusLogList = statusLogList;
    }

    public List<ProductionTask> getProductionTaskList()
    {
        return productionTaskList;
    }

    public void setProductionTaskList(List<ProductionTask> productionTaskList)
    {
        this.productionTaskList = productionTaskList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderId", getOrderId())
            .append("orderNumber", getOrderNumber())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("totalAmount", getTotalAmount())
            .append("contractNo", getContractNo())
            .append("orderStatus", getOrderStatus())
            .append("productName", getProductName())
            .append("cancelReason", getCancelReason())
            .append("stockDeducted", getStockDeducted())
            .append("productionTaskId", getProductionTaskId())
            .append("productionTaskNo", getProductionTaskNo())
            .append("productionTaskStatus", getProductionTaskStatus())
            .append("itemCount", getItemCount())
            .append("autoCreateTask", getAutoCreateTask())
            .append("submitReviewTime", getSubmitReviewTime())
            .append("reviewTime", getReviewTime())
            .append("contractTime", getContractTime())
            .append("productionStartTime", getProductionStartTime())
            .append("productionFinishTime", getProductionFinishTime())
            .append("outboundTime", getOutboundTime())
            .append("completeTime", getCompleteTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("orderItemList", getOrderItemList())
            .append("statusLogList", getStatusLogList())
            .append("productionTaskList", getProductionTaskList())
            .toString();
    }
}

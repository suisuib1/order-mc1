package com.ruoyi.system.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.Product;
import com.ruoyi.system.domain.StockFlow;
import com.ruoyi.system.domain.vo.StockOperateRequest;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.mapper.StockFlowMapper;
import com.ruoyi.system.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements IStockService
{
    private static final String FLOW_IN = "in";
    private static final String FLOW_OUT = "out";
    private static final String FLOW_ADJUST = "adjust";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StockFlowMapper stockFlowMapper;

    @Override
    public List<Product> selectStockList(Product product)
    {
        return productMapper.selectProductList(product);
    }

    @Override
    public List<StockFlow> selectStockFlowList(StockFlow stockFlow)
    {
        return stockFlowMapper.selectStockFlowList(stockFlow);
    }

    @Transactional
    @Override
    public int stockIn(StockOperateRequest request)
    {
        Product product = getRequiredProduct(request.getProductId());
        Long quantity = validPositiveQuantity(request.getQuantity(), "入库数量必须大于0");
        Product update = new Product();
        update.setProductId(product.getProductId());
        update.setStockQuantity(quantity);
        int rows = productMapper.increaseProductStock(update);
        recordFlow(product, FLOW_IN, quantity, product.getStockQuantity(), product.getStockQuantity() + quantity,
            request.getOrderNumber(), request.getTaskNo(), request.getRemark());
        return rows;
    }

    @Transactional
    @Override
    public int stockAdjust(StockOperateRequest request)
    {
        Product product = getRequiredProduct(request.getProductId());
        if (request.getTargetQuantity() == null || request.getTargetQuantity() < 0)
        {
            throw new ServiceException("调整后库存不能小于0");
        }
        Product update = new Product();
        update.setProductId(product.getProductId());
        update.setStockQuantity(request.getTargetQuantity());
        int rows = productMapper.updateProductStockQuantity(update);
        recordFlow(product, FLOW_ADJUST, request.getTargetQuantity() - product.getStockQuantity(), product.getStockQuantity(),
            request.getTargetQuantity(), request.getOrderNumber(), request.getTaskNo(), request.getRemark());
        return rows;
    }

    @Transactional
    @Override
    public void recordOutbound(Long productId, Long quantity, String orderNumber, String taskNo, String remark)
    {
        Product product = getRequiredProduct(productId);
        Long outboundQty = validPositiveQuantity(quantity, "出库数量必须大于0");
        if (product.getStockQuantity() == null || product.getStockQuantity() < outboundQty)
        {
            throw new ServiceException("产品【" + product.getProductName() + "】库存不足");
        }
        Product update = new Product();
        update.setProductId(productId);
        update.setStockQuantity(outboundQty);
        int rows = productMapper.decreaseProductStock(update);
        if (rows == 0)
        {
            throw new ServiceException("产品【" + product.getProductName() + "】库存扣减失败");
        }
        recordFlow(product, FLOW_OUT, outboundQty, product.getStockQuantity(), product.getStockQuantity() - outboundQty,
            orderNumber, taskNo, remark);
    }

    private Product getRequiredProduct(Long productId)
    {
        if (productId == null)
        {
            throw new ServiceException("产品不能为空");
        }
        Product product = productMapper.selectProductByProductId(productId);
        if (product == null)
        {
            throw new ServiceException("产品不存在");
        }
        return product;
    }

    private Long validPositiveQuantity(Long quantity, String message)
    {
        if (quantity == null || quantity <= 0)
        {
            throw new ServiceException(message);
        }
        return quantity;
    }

    private void recordFlow(Product product, String flowType, Long changeQuantity, Long beforeQuantity, Long afterQuantity,
        String orderNumber, String taskNo, String remark)
    {
        StockFlow stockFlow = new StockFlow();
        stockFlow.setProductId(product.getProductId());
        stockFlow.setFlowType(flowType);
        stockFlow.setChangeQuantity(changeQuantity);
        stockFlow.setBeforeQuantity(beforeQuantity);
        stockFlow.setAfterQuantity(afterQuantity);
        stockFlow.setOrderNumber(orderNumber);
        stockFlow.setTaskNo(taskNo);
        stockFlow.setBusinessTime(new Date());
        stockFlow.setCreateBy(SecurityUtils.getUsername());
        stockFlow.setCreateTime(DateUtils.getNowDate());
        stockFlow.setRemark(remark);
        stockFlowMapper.insertStockFlow(stockFlow);
    }
}

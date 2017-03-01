package com.mockuai.itemcenter.core.service.action.itemsku;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.HookEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.constant.StockFrozenRecordStatusEnum;
import com.mockuai.itemcenter.common.domain.dto.StockFrozenRecordDTO;
import com.mockuai.itemcenter.common.domain.qto.StockFrozenRecordQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StockFrozenRecordManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 以订单为单位减去sku库存
 */

@Service
public class ResumeOrderSkuStockAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(ResumeOrderSkuStockAction.class);
    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private StoreStockManager storeStockManager;

    @Resource
    private StockFrozenRecordManager stockFrozenRecordManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        ItemResponse response = null;
        ItemRequest request = context.getRequest();

        if (request.getString("orderSn") == null) {
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "orderSn is missing");
        }


        String bizCode = (String) context.get("bizCode");

        String appKey = request.getString("appKey");

        String orderSn = request.getString("orderSn");


        StockFrozenRecordQTO query = new StockFrozenRecordQTO();
        query.setBizCode(bizCode);
        query.setOrderSn(orderSn);
        query.setStatus(StockFrozenRecordStatusEnum.CRUSHED.getStatus());

        List<StockFrozenRecordDTO> recordDTOList  = stockFrozenRecordManager.queryStockFrozenRecord(query);

        if(recordDTOList.size()==0){
            log.error("订单补库存时发现没有冻结记录: orderSn : {} bizCode {} ",orderSn,bizCode);
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"itemcenter : 该订单不存在对应的sku减扣记录");
        }

        Long sellerId = recordDTOList.get(0).getSellerId();

        List<Long> skuIdList = Lists.newArrayList();

        List<SupplierOrderStockDTO.OrderSku> skuList = Lists.newArrayList();

        for (StockFrozenRecordDTO stockFrozenRecordDTO : recordDTOList) {

            Long skuId = stockFrozenRecordDTO.getSkuId();
            Integer number = stockFrozenRecordDTO.getNumber();
            skuIdList.add(skuId);

            itemSkuManager.resumeCrushedItemSkuStock(skuId, stockFrozenRecordDTO.getSellerId(), number, bizCode);


            SupplierOrderStockDTO.OrderSku sku = new SupplierOrderStockDTO.OrderSku();

            sku.setSkuId(skuId);
            sku.setNumber(number);

            skuList.add(sku);
        }


        //冻结仓库库存,如果在这里发生异常,前面的数据库操作将回滚
        SupplierOrderStockDTO supplierOrderStockDTO = new SupplierOrderStockDTO();
        supplierOrderStockDTO.setOrderSn(orderSn);
        //supplierOrderStockDTO.setSellerId(sellerId);
        supplierOrderStockDTO.setOrderSkuList(skuList);
        storeStockManager.resumeStoreSkuStock(supplierOrderStockDTO, appKey);


        //如果前两部操作都成功,则修改冻结记录为回复状态;

        stockFrozenRecordManager.updateStockFrozenRecordStatus(orderSn,sellerId,StockFrozenRecordStatusEnum.RESUME.getStatus(),bizCode);

        context.put(HookEnum.BATCH_STOCK_CHANGE_HOOK.getHookName(), "");
        context.put("skuIdList", skuIdList);
        context.put("sellerId", sellerId);

        response = ResponseUtil.getSuccessResponse(true);
        return response;

    }

    @Override
    public String getName() {
        return ActionEnum.INCREASE_ORDER_SKU_STOCK.getActionName();
    }
}

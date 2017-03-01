package com.mockuai.itemcenter.core.service.action.itemsku;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.HookEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.message.producer.Producer;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 更新商品销售属性(ItemSku) Action
 *
 * @author chen.huang
 *
 */

@Service
public class DecreaseItemSkuStockAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(DecreaseItemSkuStockAction.class);
	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private Producer producer;

    @Resource
    private StoreStockManager storeStockManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();

		if (request.getLong("skuId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "ItemSkuID is missing");
		}
		if (request.getLong("sellerId") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "sellerId is missing");
		}
		if (request.getInteger("number") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "decreasedNumber is missing");
		}
		long skuId = request.getLong("skuId");
		long sellerId = request.getLong("sellerId");
		int decreasedNumber = request.getInteger("number");
        String bizCode = (String) context.get("bizCode");

        String appKey = request.getString("appKey");
        try {

            StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
            storeItemSkuQTO.setItemSkuId(skuId);

            List<StoreItemSkuDTO> storeItemSkuDTOList = storeStockManager.queryStoreItemSku(storeItemSkuQTO,appKey);

            if(CollectionUtils.isEmpty(storeItemSkuDTOList)){
                throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_NO_AVILABLE_REPO,"sku下没有可用的仓库");
            }


            //按优先度排序减库存
            Collections.sort(storeItemSkuDTOList, new Comparator<StoreItemSkuDTO>() {
                @Override
                public int compare(StoreItemSkuDTO o1, StoreItemSkuDTO o2) {
                    return o1.getLevel() - o2.getLevel();
                }
            });

            /* int count = storeItemSkList.size();

            for(StoreItemSkuDTO storeItemSkuDTO : storeItemSkuDTOList){


                //库存不足,选择下一个仓库 库存不控制
               if(storeItemSkuDTO.getNum() < decreasedNumber ){
                    count --;
                    continue;
                }

                try{
                    storeStockManager.reduceStoreNumAction(storeItemSkuDTO.getStoreId(),skuId,Long.valueOf(decreasedNumber),appKey);
                }catch (ItemException e){
                    count --;

                }catch (Exception e){
                    count --;
                }
            }

            if(count <= 0){
                throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_ALL_REPO_STOCK_SHORT,"所有仓库库存数量都不满足");
            }

            itemSkuManager.decreaseItemSkuStock(skuId, sellerId, decreasedNumber, bizCode);

            */
			context.put(HookEnum.STOCK_CHANGE_HOOK.getHookName(), "");
			context.put("skuId", skuId);
			context.put("sellerId", sellerId);


			response = ResponseUtil.getSuccessResponse(true);
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.DECREASE_ITEM_SKU_STOCK.getActionName();
	}
}

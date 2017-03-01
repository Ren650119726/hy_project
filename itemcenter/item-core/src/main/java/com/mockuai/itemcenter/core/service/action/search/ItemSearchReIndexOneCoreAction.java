package com.mockuai.itemcenter.core.service.action.search;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.manager.ItemSearchManager;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ResponseUtil;


/**
 * 重建单个商品索引 core action
 * 
 * @author luoyi
 *
 */
@Service("itemSearchReIndexOneCoreAction")
public class ItemSearchReIndexOneCoreAction implements Action {

	
	static Logger log = LoggerFactory.getLogger(ItemSearchReIndexOneCoreAction.class);
	
	
	@Resource
	protected ItemManager itemManager;
	@Resource
	protected ItemSearchManager itemSearchManager;
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
        String itemIdStr = (String) context.get("itemId");
        //	若bizCode为空，则会忽略商品的biz_code属性。
        //	现阶段只有韩束的商品进搜索引擎，后期其他biz_code属性的商品再说
        String bizCode = (String) context.get("bizCode");

        //	校验参数itemId合法性
        Long itemId = null;
        try {itemId = Long.parseLong(itemIdStr);}
        catch (Exception e) {return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_INVALID, e.getMessage());}
       
        //	从DB中取商品信息
        ItemQTO qto = new ItemQTO();
        qto.setId(itemId);
        //	bizCode有可能为空
        qto.setBizCode(bizCode);
        qto.setPageSize(1);
        List<ItemDTO> list = itemManager.queryItem(qto);
        if (list == null || list.isEmpty()) {
        	return ResponseUtil.getErrorResponse(
        			ResponseCode.PARAM_E_INVALID, 
        			"给出的itemId:" + itemId + " bizCode:" + bizCode + " 无法找到对应商品信息.");
        }
        //	重建搜索引擎索引
        ItemDTO item = list.get(0);
        try {
        	itemSearchManager.setItemIndex(item);
        }catch (Exception e) {
        	return ResponseUtil.getErrorResponse(
        			ResponseCode.SYS_E_SERVICE_EXCEPTION, 
        			"重建搜索引擎过程出错, 请跟日志. " + e.getMessage());
        }
        
		return ResponseUtil.getSuccessResponse("reindex success.");
	}

	@Override
	public String getName() {
		return ActionEnum.ITEM_SEARCH_REINDEX_ONE.getActionName();
	}

}

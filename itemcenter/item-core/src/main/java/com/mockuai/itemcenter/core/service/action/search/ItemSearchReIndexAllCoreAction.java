package com.mockuai.itemcenter.core.service.action.search;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ItemStatus;
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
 * 重建所有商品索引
 * 
 * @author luoyi
 *
 */
@Service("itemSearchReIndexAllCoreAction")
public class ItemSearchReIndexAllCoreAction implements Action {
	
	
	static Logger log = LoggerFactory.getLogger(ItemSearchReIndexAllCoreAction.class);
	
	
	@Resource
	protected ItemSearchManager itemSearchManager;
	@Resource
	protected ItemManager itemManager;

	@SuppressWarnings("rawtypes")
	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		//	如果bizCode为空，则将忽略商品的bizCode属性。
		//	此时会重建所有商品索引
		String bizCode = (String) context.get("bizCode");
		//	业务相关过滤条件
		String sellerIdStr = (String) context.get("sellerId");
		String brandIdStr = (String) context.get("brandId");
		
		Long sellerId = null;
		if (!StringUtils.isBlank(sellerIdStr)) {
			try {sellerId = Long.parseLong(sellerIdStr);}
			catch (Exception e) {return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_INVALID, e.getMessage());}
		}else {
			//	sellerId为0标识忽略sellerId过滤条件
			sellerId = 0l;
		}
		Long brandId = null;
		if (!StringUtils.isBlank(brandIdStr)) {
			try {brandId = Long.parseLong(brandIdStr);}
			catch (Exception e) {return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_INVALID, e.getMessage());}
		}
		
		//	需要重建索引的商品查询条件
		ItemQTO qto = new ItemQTO();
		qto.setBizCode(bizCode);
		qto.setSellerId(sellerId);
		qto.setItemBrandId(brandId);
		qto.setItemStatus(ItemStatus.ON_SALE.getStatus());
		//	重建索引
		reIndexByQto(qto);
		
		//	过滤掉下架的
		qto = new ItemQTO();
		qto.setBizCode(bizCode);
		qto.setSellerId(sellerId);
		qto.setItemBrandId(brandId);
		qto.setItemStatus(ItemStatus.WITHDRAW.getStatus());
		deleteIndexByQto(qto);
		
		//	过滤掉未上架的
		qto = new ItemQTO();
		qto.setBizCode(bizCode);
		qto.setSellerId(sellerId);
		qto.setItemBrandId(brandId);
		qto.setItemStatus(0);
		deleteIndexByQto(qto);
		
		return ResponseUtil.getSuccessResponse("reindex success.");
	}

	@Override
	public String getName() {
		return ActionEnum.ITEM_SEARCH_REINDEX_ALL.getActionName();
	}
	
	
	/**
	 * 重建所有满足条件的商品索引
	 * @throws ItemException 
	 */
	protected void reIndexByQto(ItemQTO qto) throws ItemException {
		//	每次取300条商品出来重建
		qto.setPageSize(2000);
		
		List<ItemDTO> list = null;
		do {
			list = itemManager.queryItem(qto);
			if (list == null || list.isEmpty()) {break;}
			
			//	重建每一个商品索引
			for (ItemDTO dto : list) {
				try {
					//	判断商品的delete_mark状态。如果是删除，则删除索引
					itemSearchManager.setItemIndex(dto);
					log.info("重建所有商品索引. item_id:" + dto.getId() + " delete_mark:" + dto.getDeleteMark());
				}catch (Exception e) {
					log.error("重建商品索引失败. item_id:" + dto.getId() + "   " + e.getMessage(), e);
				}
			}
			
			log.error("重建所有商品索引. 任务进度(" + qto.getCurrentPage() + "/" + qto.getTotalPage() +   ")");
			qto.setCurrentPage(qto.getCurrentPage() + 1);
		}while (qto.getCurrentPage() <= qto.getTotalPage());
	}
	
	
	/**
	 * 删掉已经被删除的商品
	 */
	protected void deleteIndexByQto(ItemQTO qto) throws ItemException {
		//	每次取300条商品出来重建
		qto.setPageSize(2000);
		
		List<ItemDTO> list = null;
		do {
			list = itemManager.queryItem(qto);
			if (list == null || list.isEmpty()) {break;}
			
			//	重建每一个商品索引
			for (ItemDTO dto : list) {
				try {
					//	判断商品的delete_mark状态。如果是删除，则删除索引
					itemSearchManager.deleteItemIndex(dto.getId(), dto.getSellerId());
					log.info("重建所有商品索引，删除索引. item_id:" + dto.getId() + " seller_id:" + dto.getSellerId() + " delete_mark:" + dto.getDeleteMark());
				}catch (Exception e) {
					log.error("重建商品索引失败. item_id:" + dto.getId() + "   " + e.getMessage(), e);
				}
			}
			
			log.error("重建所有商品索引. 任务进度(" + qto.getCurrentPage() + "/" + qto.getTotalPage() +   ")");
			qto.setCurrentPage(qto.getCurrentPage() + 1);
		}while (qto.getCurrentPage() <= qto.getTotalPage());
	}

}

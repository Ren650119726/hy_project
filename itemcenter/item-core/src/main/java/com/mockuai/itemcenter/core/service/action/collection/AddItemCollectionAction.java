package com.mockuai.itemcenter.core.service.action.collection;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCollectionManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 增加收藏商品Action
 */
@Service
public class AddItemCollectionAction extends TransAction {
	private static final Logger log = LoggerFactory.getLogger(AddItemCollectionAction.class);
	@Resource
	private ItemCollectionManager itemCollectionManager;
	

	public ItemResponse doTransaction(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemCollectionList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCollectionList is null");
		}
		List<ItemCollectionDTO> itemCollectionDTOs = (List<ItemCollectionDTO>) request.getParam("itemCollectionList");
		try {
			//TODO 商品有效性校验

			//TODO 待重构成批量添加
			for(ItemCollectionDTO itemCollectionDTO: itemCollectionDTOs){
				//如果该商品已经收藏过，则直接跳过即可
				ItemCollectionQTO itemCollectionQTO = new ItemCollectionQTO();
				itemCollectionQTO.setItemId(itemCollectionDTO.getItemId());
				itemCollectionQTO.setUserId(itemCollectionDTO.getUserId());
				//temCollectionQTO.setDistributorId(itemCollectionDTO.getDistributorId());
				itemCollectionQTO.setPageSize(1);
				//add shareUserId
				itemCollectionQTO.setShareUserId(itemCollectionDTO.getShareUserId());
				List<ItemCollectionDTO> resultList = itemCollectionManager.queryItemCollection(itemCollectionQTO);
				if(resultList!=null && resultList.isEmpty()==false){
					continue;
				}
				//新增加的itemCollectionDO
				itemCollectionDTO.setBizCode(bizCode);//填充bizCode
				itemCollectionManager.addItemCollection(itemCollectionDTO);
			}

			response = ResponseUtil.getSuccessResponse(true);

			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.ADD_ITEM_COLLECTION.getActionName();
	}
}

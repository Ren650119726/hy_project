package com.mockuai.itemcenter.core.service.action.comment;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.domain.CommentImageDO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CommentImageManager;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 根据商品ID查询商品评论Action
 * 
 * @author chen.huang
 *
 */
@Service
public class QueryItemCommentAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(QueryItemCommentAction.class);
	@Resource
	private ItemCommentManager itemCommentManager;

	@Resource
	private CommentImageManager commentImageManager;

	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		ItemResponse response = null;
		ItemRequest request = context.getRequest();
		String bizCode = (String)context.get("bizCode");
		if (request.getParam("itemCommentQTO") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCommentQTO is null");
		}
		ItemCommentQTO itemCommentQTO = (ItemCommentQTO) request.getParam("itemCommentQTO");
		itemCommentQTO.setNeedPaging(true);
		itemCommentQTO.setBizCode(bizCode);//填充bizCode

		try {
			List<ItemCommentDO> itemCommentDOList = itemCommentManager.queryItemComment(itemCommentQTO);
			List<ItemCommentDTO> itemCommentDTOList = new CopyOnWriteArrayList<ItemCommentDTO>();
			List<Long> itemCommentIdList = new CopyOnWriteArrayList<Long>();
			for(ItemCommentDO itemCommentDO: itemCommentDOList){
				ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
				BeanUtils.copyProperties(itemCommentDO, itemCommentDTO);
				itemCommentDTOList.add(itemCommentDTO);
				itemCommentIdList.add(itemCommentDO.getId());
			}

			//查询commentImage并填充到itemComment中
			if(itemCommentDOList!=null && itemCommentDOList.isEmpty()==false){
				Map<Long, List<CommentImageDO>> commentImageMap =
						commentImageManager.queryCommentImage(itemCommentIdList, itemCommentDOList.get(0).getSellerId());

				//填充commentImage列表
				if(commentImageMap!=null && commentImageMap.isEmpty()==false){
					for(ItemCommentDTO itemCommentDTO: itemCommentDTOList){
						if(commentImageMap.containsKey(itemCommentDTO.getId())){
							itemCommentDTO.setCommentImageDTOs(
									ModelUtil.genCommentImageDTOList(commentImageMap.get(itemCommentDTO.getId())));
						}
					}
				}
			}
			//TODO 这里分页查询的逻辑需要重构，totalCount不应该通过qto来回传
			response = ResponseUtil.getSuccessResponse(itemCommentDTOList, itemCommentQTO.getTotalCount());
			return response;
		} catch (ItemException e) {
			response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
			log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
			return response;
		}

	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_ITEMCOMMENT.getActionName();
	}
}

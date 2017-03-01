package com.mockuai.itemcenter.core.service.action.comment;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CommentImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CommentImageManager;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.UserManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.Action;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 增加商品评论Action
 * 
 * @author chen.huang
 *
 */
@Service
public class AddItemCommentAction implements Action {
	private static final Logger log = LoggerFactory.getLogger(AddItemCommentAction.class);
	@Resource
	private ItemCommentManager itemCommentManager;

	@Resource
	private CommentImageManager commentImageManager;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Resource
	private TransactionTemplate transactionTemplate;

    @Resource
    private UserManager userManager;


	@Override
	public ItemResponse execute(RequestContext context) throws ItemException {
		final ItemRequest request = context.getRequest();
		final String bizCode = (String)context.get("bizCode");
		// 验证DTO是否为空
		if (request.getParam("itemCommentList") == null) {
			return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING, "itemCommentList is null");
		}
		final List<ItemCommentDTO> itemCommentDTOList = (List<ItemCommentDTO>) request.getParam("itemCommentList");

        final String appKey = (String) request.getParam("appKey");

		//添加商品评论事务处理
		ItemResponse itemResponse = transactionTemplate.execute(new TransactionCallback<ItemResponse>() {
			public ItemResponse doInTransaction(TransactionStatus transactionStatus) {
				ItemResponse response = null;

				try {
					List<ItemCommentDTO> itemCommentResult = new ArrayList<ItemCommentDTO>();
					List<Long> itemSkuIdList = new ArrayList<Long>();
					for(ItemCommentDTO itemCommentDTO : itemCommentDTOList) {
						if(itemCommentDTO.getSkuId() != null) {
							itemSkuIdList.add(itemCommentDTO.getSkuId());
						}
					}

					Map<Long, ItemSkuDTO> skuDTOMap = itemSkuManager.queryItemSkuMap(itemSkuIdList, itemCommentDTOList.get(0).getSellerId(), bizCode);

                    String userName = "";

                    //获得用户名
                    if(itemCommentDTOList.size()>0){
                        Long userId = itemCommentDTOList.get(0).getUserId();
                        userName = userManager.getUserById(userId,appKey).getName();
                    }

					//TODO 重构成批量操作
					for (ItemCommentDTO itemCommentDTO : itemCommentDTOList) {
                        itemCommentDTO.setUserName(userName);
						ItemCommentDO itemCommentDO = new ItemCommentDO();
						BeanUtils.copyProperties(itemCommentDTO, itemCommentDO);
						ItemSkuDTO itemSkuDTO = skuDTOMap.get(itemCommentDTO.getSkuId());
						if(itemSkuDTO != null) {
							itemCommentDO.setSkuCode(itemSkuDTO.getSkuCode());
						}

                        //极端情况下（接口超时），存在评论已添加,但订单状态为改变为已评论。
                        // 下次评论时数据库中存在已经添加的评论，则删除之，做延迟的事务补偿

						// 查看商品订单的评价是否已经存在;
						ItemCommentQTO itemCommentQTO = new ItemCommentQTO();
						itemCommentQTO.setSellerId(itemCommentDTO.getSellerId());
						itemCommentQTO.setSkuId(itemCommentDTO.getSkuId());
						itemCommentQTO.setOrderId(itemCommentDTO.getOrderId());
						List<ItemCommentDO> itemCommentDOs = itemCommentManager.queryItemComment(itemCommentQTO);

						if(!CollectionUtils.isEmpty(itemCommentDOs)) {

                            log.warn("商品已经被评价过，但订单状态是未评价，删除数据库中已经存在的评论 sellerId :{}, skuId:{} , orderId: {} , bizCode {}",
                                    itemCommentDTO.getSellerId(),itemCommentDTO.getSkuId(),itemCommentDTO.getOrderId(),bizCode);

                            //评论应该只有一条，使用简单for循环，不用一次删除多条记录
                            for(ItemCommentDO commentDO : itemCommentDOs){
                                itemCommentManager.deleteItemCommentPhysically(commentDO.getId(),commentDO.getSellerId());
                            }
						}

						itemCommentDO.setBizCode(bizCode);//填充bizCode
						Long itemCommentId = itemCommentManager.addItemComment(itemCommentDO);// 新增加的itemCommentDTO
						itemCommentDTO.setId(itemCommentId);
						itemCommentResult.add(itemCommentDTO);

						//如果评论是带图片的，则保存评论图片
						if (itemCommentDTO.getCommentImageDTOs()!=null
								&& itemCommentDTO.getCommentImageDTOs().isEmpty()==false) {
							for(CommentImageDTO commentImageDTO: itemCommentDTO.getCommentImageDTOs()){
								commentImageDTO.setBizCode(bizCode);//填充bizCode
								commentImageDTO.setItemCommentId(itemCommentId);
								commentImageDTO.setSellerId(itemCommentDO.getSellerId());
								commentImageDTO.setUserId(itemCommentDO.getUserId());
							}
							commentImageManager.addCommentImages(
									ModelUtil.genCommentImageDOList(itemCommentDTO.getCommentImageDTOs()));
						}
					}

					response = ResponseUtil.getSuccessResponse(itemCommentResult);
					return response;
				} catch (ItemException e) {
					//回滚事务
					transactionStatus.setRollbackOnly();
					response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
					log.error("do action:" + request.getCommand() + " occur Exception:" + e.getMessage(), e);
					return response;
				} catch (Exception e) {
					//回滚事务
					transactionStatus.setRollbackOnly();
					log.error("error to add item comment", e);
					return new ItemResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION);
				}
			}
		});

		return itemResponse;

	}

	@Override
	public String getName() {
		return ActionEnum.ADD_ITEMCOMMENT.getActionName();
	}
}
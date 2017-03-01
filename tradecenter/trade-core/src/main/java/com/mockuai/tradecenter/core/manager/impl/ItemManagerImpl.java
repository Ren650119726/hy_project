package com.mockuai.tradecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.client.ItemBuyLimitClient;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemPriceClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.client.ItemSuitClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CommentImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.OrderStockDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPriceQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.suppliercenter.client.StoreOrderNumClient;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.tradecenter.common.domain.ItemCommentDTO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.ItemManager;
import com.mockuai.tradecenter.core.util.DozerBeanService;
import com.mockuai.tradecenter.core.util.JsonUtil;

public class ItemManagerImpl implements ItemManager {

	private static final Logger log = LoggerFactory.getLogger(ItemManagerImpl.class);
    @Resource
    private ItemClient itemClient;

    @Resource
    private ItemSkuClient itemSkuClient;
    
    @Resource
    private ItemBuyLimitClient itemBuyLimitClient;
    
    @Resource
    private DozerBeanService  dozerBeanService;
    
    @Resource
    ItemPriceClient itemPriceClient;
    
    @Resource
    ItemSuitClient itemSuitClient;
    
    @Resource
    StoreOrderNumClient storeOrderNumClient;    
    

    public List<ItemSkuDTO> queryItemSku(Long itemId, Long sellerId, String appKey)
            throws TradeException {
        Response itemResp = this.itemSkuClient.queryItemSku(itemId, sellerId,appKey);
        return (List) getResponseData(itemResp);
    }
    
    @Override
	public List<ItemSkuDTO> queryItemSku(Long itemSkuId, String appKey)
			throws TradeException {
    	ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
    	itemSkuQTO.setId(itemSkuId);
    	itemSkuQTO.setNeedImage(1);
    	try {
    		Response<List<ItemSkuDTO>> itemResp = this.itemSkuClient.queryItemSku(itemSkuQTO, appKey);

    		return (List<ItemSkuDTO>) getItemResponseData(itemResp,"itemSkuClient.queryItemSku");
		} catch (Exception e) {
			throw new TradeException("itemSkuClient.queryItemSku error",e);
		}
	}

	public List<ItemSkuDTO> queryItemSku(List<Long> skuIds, Long sellerId, String appKey) throws TradeException {
        Response itemResp = this.itemSkuClient.queryItemSku(skuIds, sellerId, appKey);
        return (List) getResponseData(itemResp);
    }

    @Override
	public List<ItemSkuDTO> queryItemSku(List<Long> skuIds, Long sellerId,
			Integer isNeedQueryRemoveSku, String appKey) throws TradeException {
    	ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
    	itemSkuQTO.setIdList(skuIds);
    	itemSkuQTO.setSellerId(sellerId);
    	itemSkuQTO.setIsNeedQueryRemoveSku(isNeedQueryRemoveSku);
    	Response itemResp = this.itemSkuClient.queryItemSku(itemSkuQTO, appKey);
		return (List) getResponseData(itemResp);
	}

	@Override
	public List<ItemSkuDTO> queryItemSku(List<Long> skuIds, String appKey)
			throws TradeException {
    	ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
    	itemSkuQTO.setIdList(skuIds);
    	itemSkuQTO.setNeedImage(1);
    	Response itemResp = this.itemSkuClient.queryItemSku(itemSkuQTO, appKey);
        return (List) getResponseData(itemResp);
	}

	@Override
	public List<ItemSkuDTO> queryDeletedItemSku(List<Long> skuIds, String appKey)
			throws TradeException {
		ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
		itemSkuQTO.setIdList(skuIds);
		itemSkuQTO.setNeedImage(1);
		itemSkuQTO.setIsNeedQueryRemoveSku(1);
		Response itemResp = itemSkuClient.queryItemSku(itemSkuQTO, appKey);
		return (List) getResponseData(itemResp);
	}

	public List<ItemDTO> queryItem(List<Long> itemIds, Long sellerId, String appKey) throws TradeException {
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(itemIds);
        itemQTO.setSellerId(sellerId);
        Response itemResp = this.itemClient.queryItem(itemQTO, appKey);
        return (List) getResponseData(itemResp);
    }
	
	

    @Override
	public List<ItemDTO> queryDeletedItem(List<Long> itemIds, Long sellerId,
			String appKey) throws TradeException {
		ItemQTO itemQTO = new ItemQTO();
		itemQTO.setIdList(itemIds);
		itemQTO.setSellerId(sellerId);
		itemQTO.setIsNeedQueryRemoveItem(1);
		Response itemResp = itemClient.queryItem(itemQTO, appKey);
		return (List) getResponseData(itemResp);
	}

	@Override
	public List<ItemDTO> queryItem(ItemQTO itemQTO, String appKey)
			throws TradeException {
    	Response itemResp  = null;
    	List<ItemDTO> returnList = new ArrayList<ItemDTO>();
    	try {
    		itemResp = this.itemClient.queryItem(itemQTO, appKey);
            
		} catch (Exception e) {
			log.error("itemClient.queryItem error:",e);
			throw new TradeException(e);
		}
    	if(itemResp == null){
    		log.error("itemClient.queryItem error : itemResp is null");
			throw new TradeException("itemClient.queryItem error : itemResp is null");
    	}
    	log.info(" itemResp: "+JSONObject.toJSONString(itemResp));
    	try {
    		returnList = (List) getResponseData(itemResp);
		} catch (Exception e) {
			log.error(" itemClient.queryItem (List) getResponseData(itemResp): "+e);
			throw new TradeException(e);
		}
    	if(CollectionUtils.isEmpty(returnList)){
    		log.error(" itemClient.queryItem itemidList: "+JSONObject.toJSONString(itemQTO.getIdList())+" has not result ");
    	}
    	return returnList;
	}

	public boolean addItemComment(List<ItemCommentDTO> itemCommentDTOs, String appKey) throws TradeException {
        List<com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO> itemCommentDTOList =
                new ArrayList<com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO>();
        for(ItemCommentDTO orderItemComment: itemCommentDTOs){
            com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO itemComment =
                    new com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO();
            itemComment.setItemId(orderItemComment.getItemId());
            itemComment.setOrderId(orderItemComment.getOrderId());
            itemComment.setUserId(orderItemComment.getUserId());
            itemComment.setSellerId(orderItemComment.getSellerId());
            itemComment.setTitle(orderItemComment.getTitle());
            itemComment.setContent(orderItemComment.getContent());
            itemComment.setScore(orderItemComment.getScore());
            //
            itemComment.setSkuId(orderItemComment.getSkuId());
            if(null!=orderItemComment.getCommentImageDTOList()&&orderItemComment.getCommentImageDTOList().size()>0){
            	 List<CommentImageDTO> commentImages = dozerBeanService.coverList(orderItemComment.getCommentImageDTOList(), CommentImageDTO.class);
            	 itemComment.setCommentImageDTOs(commentImages);
            }
            
            itemCommentDTOList.add(itemComment);
        }

        Response itemResp = itemClient.addItemComment(itemCommentDTOList, appKey);
        return itemResp.getCode() == ResponseCode.SUCCESS.getCode();
    }


    public String validePriceFields(ItemSkuDTO itemSkuDTO) {
        if (itemSkuDTO.getPromotionPrice() == null)
            return "promotionPrice is null";
        if (itemSkuDTO.getWirelessPrice() == null)
        	
            return "wireless price is null";
        if (itemSkuDTO.getMarketPrice() == null) {
            return "market price is null";
        }
        return null;
    }

    private <T> T getResponseData(Response<T> itemResp) throws TradeException {
        if (com.mockuai.itemcenter.common.constant.ResponseCode.SUCCESS.getCode() == itemResp.getCode()) {
            return itemResp.getModule();
        }else{
        	 log.error("itemPlatform invoke error"+itemResp.getCode()+":"+itemResp.getMessage());
        	 throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION);
        }

       
    }
    
    private <T> T getItemResponseData(Response<T> itemResp,String method) throws TradeException {
    	if( itemResp == null ){
    		log.error(method+" response return null");
    		throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_DEFAULT_ERROR,method+" response return null");
    	}
    	if(itemResp.getModule() == null){
    		log.error(method+" module return null");
    		throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_DEFAULT_ERROR,method+" module return null");
    	}
    	if(ResponseCode.SUCCESS.getCode() == itemResp.getCode()){
    		return itemResp.getModule();
    	}else{
    		log.error(" return error code : "+itemResp.getCode()+" message: "+itemResp.getMessage());
    		throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_DEFAULT_ERROR,method+"return error");
    	}
    }

	@Override
	public Integer getItemBuyLimit(long itemId, long sellerId, String appKey) throws TradeException{
		
		Response itemResp = itemBuyLimitClient.getItemBuyLimit(itemId, sellerId, appKey);
		return (Integer) getResponseData(itemResp);
	}

	@Override
	public List<ItemBuyLimitDTO> queryItemBuyLimit(List<Long> itemIdList, long sellerId, String appKey) throws TradeException {
		Response<List<ItemBuyLimitDTO>> response = itemBuyLimitClient.queryItemBuyLimit(itemIdList, sellerId, appKey);
		if (response.isSuccess() == false) {
			log.error("error to query itemBuyLimit, itemIdList:{}, code:{}, msg:{}",
					JsonUtil.toJson(itemIdList), response.getCode(), response.getMessage());
			throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_REMOTE_CALL_ERROR);
		}

		return response.getModule();
	}

	@Override
	public List<ItemCommentDTO> queryItemComment(Long itemId, Long sellerId, String appKey) throws TradeException {
		return null;
	}

	@Override
	public Boolean replyComment(com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO replyComment, String appKey) throws TradeException{
		Boolean result = false;
		try{
			@SuppressWarnings("rawtypes")
			Response response = itemClient.updateItemComment(replyComment, appKey);
			if(response.isSuccess()==false){
				throw new TradeException(response.getMessage());
			}
			result = (Boolean) response.getModule();
		}catch(Exception e){
			log.error("回复评论失败",e);
			 new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		return result;
	}


	@Override
	public List<ItemPriceDTO> queryItemServiceList(Long skuId, List<Long> serviceIds, Long userId, Long sellerId,String appkey)
			throws TradeException {
		ItemPriceQTO query = new ItemPriceQTO();
		query.setItemSkuId(skuId);
		query.setServiceIdList(serviceIds);
		query.setSellerId(sellerId);
		List<ItemPriceQTO> itemPriceQTOs = new ArrayList<ItemPriceQTO>();
		itemPriceQTOs.add(query);
		Response<?> response  = itemPriceClient.queryItemPriceDTO(itemPriceQTOs, userId, appkey);
		if(response.isSuccess()==false){
			throw new TradeException(response.getMessage());
		}
		return (List<ItemPriceDTO>) response.getModule();
	}



	@Override
	public OrderStockDTO frozenStock(String orderSn, Long userId,
													List<OrderItemDO> orderItemDOList, String appKey) throws TradeException {
		if (orderItemDOList == null || orderItemDOList.isEmpty()) {
			return null;
		}
		OrderStockDTO orderStockDTO = new OrderStockDTO();
		orderStockDTO.setOrderSn(orderSn);
		orderStockDTO.setSellerId(orderItemDOList.get(0).getSellerId());
		List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();
		orderStockDTO.setOrderSkuList(orderSkuList);
		for (OrderItemDO orderItemDO : orderItemDOList) {
			OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
			orderSku.setSkuId(orderItemDO.getItemSkuId());
			orderSku.setNumber(orderItemDO.getNumber());
			orderSku.setDistributorId(orderItemDO.getDistributorId());
			orderSkuList.add(orderSku);
		}

		log.info(" 【orderStockDTO】 "+JSONObject.toJSONString(orderStockDTO));
		
		Response response = itemSkuClient.freezeOrderSkuStock(orderStockDTO, appKey);
		if (response.isSuccess() == false) {
			if (response.getCode() == ResponseCode.BASE_STATE_E_STOCK_SHORT.getCode()) {
				throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.BIZ_E_ITEM_OUT_OF_STOCK);
			}else {
				log.error("error to frozen stock, orderItemList:{}, code:{}, message:{}",
						JsonUtil.toJson(orderItemDOList), response.getCode(), response.getMessage());
				throw new TradeException(com.mockuai.tradecenter.common.constant.ResponseCode.SYS_E_SERVICE_EXCEPTION);
			}
		}
		OrderStockDTO result = (OrderStockDTO) response.getModule();

		return result;
	}
	
	@Override
	public OrderStockDTO getSeckillSkuList(List<OrderItemDO> orderItemDOList,OrderStockDTO orderStock,String appKey) throws TradeException {
		OrderStockDTO orderStockre= new OrderStockDTO();
		List<OrderStockDTO.OrderSku> orderSkuList = new ArrayList<OrderStockDTO.OrderSku>();
		log.info(" getSeckillSkuList orderStock "+JSONObject.toJSONString(orderStock));
		// 含非秒杀商品的订单
		if(orderStock!=null){
			orderStockre = orderStock;
			orderSkuList = orderStockre.getOrderSkuList();
		}
		if (orderItemDOList == null || orderItemDOList.isEmpty()) {
			return null;
		}
		
		for (OrderItemDO orderItemDO : orderItemDOList) {
			OrderStockDTO.OrderSku orderSku = new OrderStockDTO.OrderSku();
			orderSku.setSkuId(orderItemDO.getItemSkuId());
			orderSku.setNumber(orderItemDO.getNumber());
			orderSku.setDistributorId(orderItemDO.getDistributorId());

//			log.info(" getSeckillSkuList orderItemDO "+JSONObject.toJSONString(orderItemDO));
			try {
				com.mockuai.suppliercenter.common.api.Response<List<StoreItemSkuDTO>> result = storeOrderNumClient.getStroeItemSkuList(orderItemDO.getItemSkuId(),new Long(orderItemDO.getNumber()),appKey);

				log.info(" storeOrderNumClient.getStroeItemSkuList result "+JSONObject.toJSONString(result));
				List<StoreItemSkuDTO> storeItemSkuS = new ArrayList<StoreItemSkuDTO>();
				if(result!=null){
					storeItemSkuS=result.getModule();
				}
				if(storeItemSkuS!=null && !storeItemSkuS.isEmpty()){
					if(storeItemSkuS.get(0)!=null){
						orderSku.setStoreId(storeItemSkuS.get(0).getStoreId());
						orderSku.setSupplierId(storeItemSkuS.get(0).getSupplierId());					
					}
				}
			} catch (Exception e) {
				log.info(e.getMessage());

			}
			
			orderSkuList.add(orderSku);
		}
		orderStockre.setOrderSkuList(orderSkuList);
		
		return orderStockre;
	}
	
	public StoreItemSkuDTO getStoreInfo(Long itemSkuId,Long number,String appKey) {
		
		try {
			com.mockuai.suppliercenter.common.api.Response<List<StoreItemSkuDTO>> result = storeOrderNumClient.getStroeItemSkuList(itemSkuId,number,appKey);

			log.info(" storeOrderNumClient.getStroeItemSkuList result "+JSONObject.toJSONString(result));
			List<StoreItemSkuDTO> storeItemSkuS = new ArrayList<StoreItemSkuDTO>();
			if(result!=null){
				storeItemSkuS=result.getModule();
			}
			if(storeItemSkuS!=null && !storeItemSkuS.isEmpty()){
				if(storeItemSkuS.get(0)!=null){
					return storeItemSkuS.get(0);					
				}
			}
		} catch (Exception e) {
			log.error( e.getMessage() );
		}
		
		return null;
	}
	



	@Override
	public Boolean thawStock(String orderSn, Long userId, String appKey) throws TradeException {
		Boolean result = false;
		Response<?> response =  itemSkuClient.thawOrderSkuStock(orderSn, appKey);
		log.info("thaw Stock response:"+JSONObject.toJSONString(response));
		if(response.isSuccess()==false){
			if(response.getCode()==40006){
				return true;
			}
			throw new TradeException(response.getMessage());
		}
		result = (Boolean) response.getModule();
		return result;

	}
	
	@Override
	public Boolean thawOrderSkuStockPartially(OrderStockDTO orderStockDTO, String appKey) throws TradeException {
		Boolean result = true;
		Response<?> response =  itemSkuClient.thawOrderSkuStockPartially( orderStockDTO,  appKey);
//		log.info("thaw Stock response:"+JSONObject.toJSONString(response));
		if(response.isSuccess()==false){
			
			throw new TradeException(response.getMessage());
		}
		
		return result;

	}



	@Override
	public ItemDTO getSuitItem(Long sellerId, Long itemId, String appKey) throws TradeException {
		Response response =  itemSuitClient.getSuit(sellerId, itemId, appKey);
		if(response.isSuccess()==false){
			throw new TradeException(response.getMessage());
		}
		return (ItemDTO) response.getModule();
	}



	@Override
	public Boolean crushItemSkuStock(String orderSn, Long userId, String appKey) throws TradeException {
		Boolean result = false;
		Response response =  itemSkuClient.crushOrderSkuStock(orderSn, appKey);
		if(response.isSuccess()==false){
			throw new TradeException(response.getMessage());
		}
		result = true;
		return result;
	}

	@Override
	public Boolean increaseOrderSkuStock(String orderSn, Long userId, String appKey) throws TradeException {
		Response response =  itemSkuClient.increaseOrderSkuStock(orderSn, appKey);
		if(response.isSuccess()==false){
			throw new TradeException(response.getMessage());
		}
		return true;
	}

	@Override
	public Boolean increaseOrderSkuStockPartially(OrderStockDTO orderStockDTO,
			String appKey) throws TradeException {
		Response response =  itemSkuClient.increaseOrderSkuStockPartially(orderStockDTO, appKey);
		if(response.isSuccess()==false){
			throw new TradeException(response.getMessage());
		}
		return true;
	}



	@Override
	public Boolean resumeCrushItemSkuStock(Long skuId, Long sellerId, Integer num, String appKey) throws TradeException {
		Boolean result = false;
		Response response =  itemSkuClient.resumeCrushItemSkuStock(skuId, sellerId, num, appKey);
		if(response.isSuccess()==false){
			throw new TradeException(response.getMessage());
		}
		result = true;
		return result;
	}

	@Override
	public ItemDTO getItem(long itemId, long sellerId,String appKey) throws TradeException {
		Response itemResp = this.itemClient.getItem(itemId, sellerId, false, appKey);
        return (ItemDTO) getResponseData(itemResp);
	}
}
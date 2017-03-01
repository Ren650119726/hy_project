package com.mockuai.tradecenter.core.service.action.cart;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.VirtualMark;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemPriceDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.ItemServiceDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BatchAddUserCartItemJ implements Action {
	private static final Logger log = LoggerFactory.getLogger(BatchAddUserCartItemJ.class);

	/**
	 * 购物车最大记录数
	 */
	private static final int CART_MAX_NUMBER = 50;
	
	@Resource
	private TransactionTemplate transactionTemplate;

	@Resource
	private UserCartItemManager userCartItemManager;

	@Resource
	private ItemManager itemManager;
	@Resource
    private OrderManager orderManager;
	@Resource
	private UserManager userManager;
	
	@Resource
	private SupplierManager supplierManager;
	
	@Autowired
	private MarketingManager marketingManager;

    @Resource
    private DistributionManager distributionManager;
    @Override

    public TradeResponse execute(RequestContext context) throws TradeException {
    	

        TradeRequest request = context.getRequest();
        final String appKey = (String)context.get("appKey");
        final String appDomain = (String)context.get("appDomain");

        if(request.getParam("cartItemDTOList") == null){
            log.error("cartItemDTOList is null");
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"cartItemDTOList is null");
        }
        final List<CartItemDTO> cartItemDTOs = (List<CartItemDTO>)request.getParam("cartItemDTOList");
        /**
         * bizCode不保留的话好多方法都要用到
         */
        final String bizCode = (String) context.get("bizCode");
        /**
         * 字段验证
         * number
         * itemSkuId
         * sellerId、是不是要动 若前端传来的不是sellerId
         * userId
         */
        String errorMsg = this.userCartItemManager.validateCartItemDTOsFields4Add(cartItemDTOs);

        if(!StringUtils.isEmpty(errorMsg)){
            log.error(errorMsg);
            return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,errorMsg);
        }

        String result =  transactionTemplate.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus status) {
                try {

                    for(CartItemDTO cartItemDTO:cartItemDTOs){
                        // 必要的字段
                        long itemSkuId = cartItemDTO.getItemSkuId();
                        long sellerId = cartItemDTO.getSellerId();
                        int number  = cartItemDTO.getNumber();
                        long userId = cartItemDTO.getUserId();
                        //Long distributorId = cartItemDTO.getDistributorId();
                        Long shareUserId = cartItemDTO.getShareUserId();

                        // 商品平台查询商品详细信息
                        List<Long> queryList = new ArrayList<Long>();
                        queryList.add(itemSkuId);
                        List<ItemSkuDTO>  itemSkus = null;
                        List<ItemDTO> items = null;
                        ItemSkuDTO itemSku = null;
                        try{
                            log.info(" addcart queryList : "+ JSONObject.toJSONString(queryList));
                            itemSkus = itemManager.queryItemSku(itemSkuId, appKey); // 如果是返回空将抛出异常
                            log.info(" addcart itemSkus : "+JSONObject.toJSONString(itemSkus));
                            if(itemSkus == null || itemSkus.size() == 0){
                                log.error("itemSku is null : " +itemSkuId + "," + sellerId);
                                throw  new Exception("itemSku doesn't exist");
                            }
                            itemSku = itemSkus.get(0);

                            Long itemId = itemSku.getItemId();
                            queryList.set(0, itemId);
                            items = itemManager.queryItem(queryList, sellerId, appKey); // 如果是返回空将抛出异常
                            if(items ==null || items.size() == 0){
                                log.error("item is null : " + itemId + "," + sellerId);
                                throw  new Exception("item doesn't exist");
                            }

                            ItemDTO itemDTO = items.get(0);
                            int itemType = itemDTO.getItemType();
                            if(itemType==13||itemType==14||itemType==15){
                                throw  new Exception("营销商品不能加入购物车");
                            }

                            //虚拟商品不能加入购物车
                            if(null!=itemDTO.getVirtualMark()&&itemDTO.getVirtualMark()== VirtualMark.VIRTUAL.getCode()){
                                throw  new Exception("虚拟商品不能加入购物车");
                            }

                        }catch(TradeException e){
                            log.error("getItemSku error",e);
                            throw  new Exception("getItemSku error"+e.getMessage());
                        }
                        ItemDTO item = items.get(0);

                        ItemDTO suitItemDTO = null;

                        //TODO ...
                        if (item.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()) {
                            try {
                                suitItemDTO = itemManager.getSuitItem(sellerId, item.getId(), appKey);
                            } catch (TradeException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }


                        // 可能购物车中已经存在
//                        CartItemDO cartItem = userCartItemManager.getCartItemBySkuId(itemSkuId, distributorId, userId);
                        CartItemDO cartItem = userCartItemManager.getCartItemBySkuId(itemSkuId, userId);
                        long newId = 0L,updated = 0;
                        CartItemDO newCartItem = null;
                        Date now = new Date();
                        Integer minLimitation = item.getSaleMinNum();
                        Integer maxLimitation = item.getSaleMaxNum();
                        if(minLimitation!=null && minLimitation.intValue()!=0 && number<minLimitation.intValue()){
                            throw  new Exception(
                                    "该商品最少需要购买"+minLimitation.intValue()+"件");

                        }
                        if(maxLimitation!=null && maxLimitation.intValue()!=0 && number>maxLimitation.intValue()){
                            throw  new Exception(
                                    "该商品最多只能购买"+maxLimitation.intValue()+"件");
                        }

                        Long supplierId = item.getSupplierId();

                        List<CartItemServiceDO> cartItemServiceDOList = null;
                        if (cartItemDTO.getServiceList() != null && cartItemDTO.getServiceList().size() > 0) {
                            List<Long> serviceIds = new ArrayList<Long>();
                            for (ItemServiceDTO itemServiceDTO : cartItemDTO.getServiceList()) {
                                serviceIds.add(itemServiceDTO.getServiceId());
                            }
                            List<ItemPriceDTO> serviceList = null;
                            try {
                                serviceList = itemManager.queryItemServiceList(itemSkuId,
                                        serviceIds, userId,sellerId, appKey);
                            } catch (TradeException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            if (serviceList != null) {

                                List<ValueAddedServiceDTO> valueAddedServiceList = serviceList.get(0).getValueAddedServiceDTOList();
                                if(null!=valueAddedServiceList&&valueAddedServiceList.size()!=0){
                                    cartItemServiceDOList = new ArrayList<>();
                                    for(ValueAddedServiceDTO valueAddedServiceDTO:valueAddedServiceList){
                                        CartItemServiceDO cartItemServiceDO = new CartItemServiceDO();
                                        cartItemServiceDO.setPrice(valueAddedServiceDTO.getServicePrice());
                                        cartItemServiceDO.setServiceId(valueAddedServiceDTO.getId());
                                        cartItemServiceDO.setServiceName(valueAddedServiceDTO.getServiceName());
                                        cartItemServiceDO.setServiceImageUrl(valueAddedServiceDTO.getIconUrl());
                                        cartItemServiceDO.setBizCode(bizCode);
                                        cartItemServiceDO.setSellerId(sellerId);
                                        cartItemServiceDOList.add(cartItemServiceDO);
                                    }
                                }


                            }

                        }



                        try{
                            Integer cartItemCount = userCartItemManager.getCartItemCountByUserAndSellerId(item.getId(), userId, sellerId);
                            //用户限购
                            try {
                                checkUserItemPurchase(item.getId(),userId,sellerId,number,cartItemCount, appKey);
                            }catch (Exception e){
                                log.warn("批量添加购物车，校验用户限购，校验失败:{}",e.getMessage());
                                continue;
                            }

                            Integer cartSkuCount = userCartItemManager.getCartItemCountBySkuIdAndUserId(itemSku.getId(), userId, sellerId);
                            /*try {
                                checkItemCartOutSkuStock(itemSku,number,cartSkuCount);
                            }catch (Exception e){
                                log.warn(e.getMessage());
                                continue;
                            }*/

                            List<CartItemDO> subCartItemDOList = null;

                            if (null != suitItemDTO && suitItemDTO.getSubItemList().size() > 0) {
                                subCartItemDOList = new ArrayList<>();
                                for (ItemDTO subItemDTO : suitItemDTO.getSubItemList()) {
                                    List<Long> subSkuIds = new ArrayList<>();
                                    for (ItemSkuDTO subItemSkuDTO : subItemDTO.getItemSkuDTOList()) {
                                        subSkuIds.add(subItemSkuDTO.getId());
                                    }
                                    List<ItemSkuDTO> subItemSkuDTOList = itemManager.queryItemSku(subSkuIds,
                                            subItemDTO.getSellerId(), appKey);

                                    if( null != subItemSkuDTOList && subItemSkuDTOList.size()>0 ){

                                        CartItemDO subCartItemDO = genSubCartItemDO(subItemSkuDTOList.get(0),subItemDTO,bizCode,userId);
                                        subCartItemDO.setItemType(subItemDTO.getItemType());
                                        subCartItemDO.setOriginalId(newId);

                                        subCartItemDO.setSupplierId(supplierId); //套装的子商品和套装商品是同样的supplierid
                                        cartItemCount = userCartItemManager.getCartItemCountByUserAndSellerId(subItemDTO.getId(), userId, sellerId);

                                        checkUserItemPurchase(subItemDTO.getId(),userId,sellerId,subCartItemDO.getNumber(),cartItemCount, appKey);
                                        //超出购买数则 跳过！
                                     /*   cartSkuCount = userCartItemManager.getCartItemCountBySkuIdAndUserId(subCartItemDO.getItemSkuId(), userId, sellerId);
                                        try {
                                            checkItemCartOutSkuStock(subItemSkuDTOList.get(0),subCartItemDO.getNumber(),cartSkuCount);
                                        }catch (Exception e){
                                            log.warn(e.getMessage());
                                            continue;
                                        }*/

                                        subCartItemDOList.add(subCartItemDO);
//							this.userCartItemManager.addUserCartItem(subCartItemDO);
                                    }
                                }
                            }

                            Long saleStockNum = getSaleStockNum(itemSkuId, appKey);
                            
                            // 限时购营销类型限购处理
                            MarketItemDTO marketItemDTO = genMarketItemDTO(itemSku,item,number);
                			List<MarketItemDTO> marketItemDTOs = new ArrayList<MarketItemDTO>();
                			marketItemDTOs.add(marketItemDTO);
                			List<DiscountInfo> discountInfoList = marketingManager.getCartDiscountInfo(marketItemDTOs,appKey,userId);
                			Long limitNumber = 0l;
                			log.info(" =====discountInfoList :"+JSONObject.toJSONString(discountInfoList));
                			if(CollectionUtils.isNotEmpty(discountInfoList) && discountInfoList.get(0) !=null && ToolType.TIME_RANGE_DISCOUNT.getCode().equals(discountInfoList.get(0).getActivity().getToolCode())){
                				limitNumber = discountInfoList.get(0).getItemList().get(0).getLimitNumber();
                			}

                            if(limitNumber>0 && limitNumber.longValue()<saleStockNum.longValue()){
                            	saleStockNum = limitNumber;
                            }
                			
                            if( null == cartItem ){// 如果是新加
                                Integer cartCount = userCartItemManager.getCartItemsCount(userId);
                                if(cartCount.intValue() >= CART_MAX_NUMBER){
                                    log.error("exceed the cart max limitation");
                                    throw  new Exception("购物车最多只能添加"+CART_MAX_NUMBER+"件商品");

                                }

                                //根据分销商id获取分销商的信息，用于后面填充购物车商品的来源店铺名称
                                //DistShopDTO distShopDTO = distributionManager.getDistShop(distributorId, appKey);

                                newCartItem = new CartItemDO();
                                newCartItem.setItemSkuId(itemSkuId); //
                                newCartItem.setItemName(item.getItemName());
                                newCartItem.setSellerId(sellerId); // 供应商id
                                newCartItem.setUserId(userId); //未登入时候无用户id
                                newCartItem.setItemId(item.getId());
                                newCartItem.setItemSkuDesc(StringUtils.isBlank(itemSku.getSkuCode())? "" : itemSku.getSkuCode());
                                newCartItem.setMarketPrice(itemSku.getMarketPrice()); // 市场价
                                newCartItem.setPromotionPrice(itemSku.getPromotionPrice());
                                newCartItem.setWirelessPrice(itemSku.getWirelessPrice());
                                //设置商品主图
//				newCartItem.setItemImageUrl(item.getIconUrl());
                                //设置sku图片
                                newCartItem.setItemImageUrl(itemSku.getImageUrl());                                
                                
                                if(number<=saleStockNum.intValue()){
                                	newCartItem.setNumber(number);
                                }else{
                                    newCartItem.setNumber(saleStockNum.intValue());
                                }
                                                                
                                newCartItem.setBizCode(bizCode);
                                newCartItem.setDeliveryType(item.getDeliveryType());
                                newCartItem.setItemType(item.getItemType());
                                newCartItem.setSupplierId(supplierId);
                                //移除店铺概念
                                //设置分销商id和分销商店铺名称
                                //newCartItem.setDistributorId(distributorId);
                                //newCartItem.setDistributorName(distShopDTO.getShopName());
                                //设置分享人id
                                newCartItem.setShareUserId(shareUserId);
                                newId = userCartItemManager.addUserCartItem(newCartItem,cartItemServiceDOList);


                                if(subCartItemDOList!=null&&subCartItemDOList.size()>0){
                                    for(  CartItemDO subCartItemDO :subCartItemDOList ){
                                        subCartItemDO.setOriginalId(newId);
                                        //移除店铺概念
                                        //  设置分销商id和分销商店铺名称
                                        //subCartItemDO.setDistributorId(distributorId);
                                        //subCartItemDO.setDistributorName(distShopDTO.getShopName());
                                        userCartItemManager.addUserCartItem(subCartItemDO,cartItemServiceDOList);
                                    }
                                }

                                cartItem = newCartItem ;
                                if(newId <= 0 ){
                                    log.error("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
                                    throw  new Exception("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
                                }
                            }else{//之前已经加过该商品
                                //如果多次添加购物车的总数超过商品下单上限，则提示错误信息
                                int totalNumber = cartItem.getNumber() + number;
                                if(maxLimitation!=null && maxLimitation!=0
                                        &&  totalNumber > maxLimitation.intValue()){
                                    throw new Exception("该商品最多只能购买"+maxLimitation.intValue()+"件");

                                }
                                cartItem.setUserId(userId);
                                cartItem.setId(cartItem.getId()); // id赋值用于更新条件
                                
                                if(totalNumber<=saleStockNum.intValue()){
                                	cartItem.setNumber(totalNumber);
                                }else{
                                	cartItem.setNumber(saleStockNum.intValue());
                                }
                                
                                cartItem.setBizCode(bizCode);
                                //新的分享人的id 覆盖之前的
                                if(shareUserId != null){
                                    cartItem.setShareUserId(shareUserId);
                                }
                                updated = userCartItemManager.updateUserCartItemNumber(cartItem,cartItemServiceDOList);
                                if( updated <= 0){
                                    log.error("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
                                    throw  new Exception("add cartItem error : skuId" + itemSkuId + " sellerId: "+ sellerId + " userId: " + userId);
                                }
                            }
                        }catch(TradeException e){
                            log.error("db error: ",e);
                            throw   new Exception(e.getMessage());
                        }
                        //		cartItem = this.userCartItemManager.getCartItemBySkuId(itemSkuId, sellerId, userId);
                        log.info("newId:{},updated:{} ",newId,updated);

                    }
                }catch (Exception e){
                    status.setRollbackOnly();
                    log.info("error msg:{}",e);
                    return e.getMessage();
                }
                return "";
            }
        });




        if(StringUtils.isNotBlank(result)){
            return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,result);
        }


        return ResponseUtils.getSuccessResponse(true);
    }

	private MarketItemDTO genMarketItemDTO(ItemSkuDTO itemSkuDTO,ItemDTO itemDTO,int number) {
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		marketItemDTO.setItemSkuId(itemSkuDTO.getId());
		marketItemDTO.setItemId(itemDTO.getId());
		marketItemDTO.setCategoryId(itemDTO.getCategoryId());
		marketItemDTO.setBrandId(itemDTO.getItemBrandId());
		marketItemDTO.setSellerId(itemDTO.getSellerId());
		marketItemDTO.setItemType(itemDTO.getItemType());
		marketItemDTO.setNumber(number);
		marketItemDTO.setUnitPrice(itemSkuDTO.getPromotionPrice());
		
		return marketItemDTO;
	}

    private Long getSaleStockNum(Long itemSkuId,String appKey) throws TradeException{
		StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
    	storeItemSkuQTO.setItemSkuId(itemSkuId);
    	log.info(" storeItemSkuQTO : "+JSONObject.toJSONString(storeItemSkuQTO));
    	List<StoreItemSkuDTO> storeItemSkuDTOs = supplierManager.queryStoreItemSku(storeItemSkuQTO, appKey);
    	if(storeItemSkuDTOs.get(0).getSalesNum() == null ) {
            throw new TradeException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"storeItemSkuDTOs.get(0).getSalesNum() id null");
        }
    	return storeItemSkuDTOs.get(0).getSalesNum(); 
	}

    private void checkItemCartOutSkuStock(ItemSkuDTO itemSku, Integer cartItemCount, Integer num)throws TradeException{
        Long stockNum = itemSku.getStockNum();

        if(stockNum-cartItemCount-num<0){
            throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
        }
    }

    private void checkUserItemPurchase(Long itemId,Long userId,
                                       Long sellerId,Integer num,Integer cartItemCount, String appKey)throws TradeException{

        OrderQTO query = new OrderQTO();

        query.setUserId(userId);

        if(null==itemId||null==sellerId){
            throw new TradeException(ResponseCode.PARAM_E_PARAM_INVALID, "itemId or sellerid is null");
        }


        Integer itmeBuyLimitCount = itemManager.getItemBuyLimit(itemId, sellerId, appKey);

        if(null!=itmeBuyLimitCount&& itmeBuyLimitCount!=0){
            Integer hasBuyCount = orderManager.getHasBuyCount(userId, itemId);
            if(itmeBuyLimitCount>hasBuyCount){
                Integer canBuyCount = itmeBuyLimitCount-hasBuyCount-cartItemCount;

                if(num>canBuyCount){
                    log.error("item over the purchase quantity : " + itemId);
                    throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品最多能购买"+canBuyCount+"件");
                }
            }else{
                throw new TradeException(ResponseCode.BIZ_E_ITEM_BUY_MAX_AMOUNT, "该商品已经超过最多购买量");
            }
        }
    }

    private CartItemDO genSubCartItemDO(ItemSkuDTO itemSkuDTO, ItemDTO subItemDTO, String bizCode, Long userId){
        CartItemDO newCartItem = new CartItemDO();
        newCartItem.setItemSkuId(itemSkuDTO.getId()); //
        newCartItem.setItemName(subItemDTO.getItemName());
        newCartItem.setSellerId(itemSkuDTO.getSellerId()); // 供应商id
        newCartItem.setUserId(userId); //未登入时候无用户id
        newCartItem.setItemId(subItemDTO.getId());
        newCartItem.setItemSkuDesc(StringUtils.isBlank(itemSkuDTO.getSkuCode())? "" : itemSkuDTO.getSkuCode());
        newCartItem.setMarketPrice(itemSkuDTO.getMarketPrice()); // 市场价
        newCartItem.setPromotionPrice(itemSkuDTO.getPromotionPrice());
        newCartItem.setWirelessPrice(itemSkuDTO.getWirelessPrice());
        //设置商品主图
        newCartItem.setItemImageUrl(subItemDTO.getIconUrl());
        newCartItem.setNumber(1);
        newCartItem.setBizCode(bizCode);
        newCartItem.setDeliveryType(subItemDTO.getDeliveryType());
        return newCartItem;
    }



    @Override
	public String getName() {
		return ActionEnum.BATCH_ADD_USER_CARTITEM_J.getActionName();
	}
}	

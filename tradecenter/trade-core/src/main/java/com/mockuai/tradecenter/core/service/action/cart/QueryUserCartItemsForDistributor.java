package com.mockuai.tradecenter.core.service.action.cart;

import com.mockuai.itemcenter.client.SupplierClient;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.common.domain.dto.ItemBuyLimitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketValueAddedServiceDTO;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.CartItemServiceDTO;
import com.mockuai.tradecenter.common.domain.distributor.DistributorCartDTO;
import com.mockuai.tradecenter.common.enums.EnumCartItemStatus;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.*;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.TradeRequest;
import com.mockuai.tradecenter.core.service.action.Action;
import com.mockuai.tradecenter.core.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;
import java.util.Map.Entry;

public class QueryUserCartItemsForDistributor implements Action {

	private static final Logger log = LoggerFactory.getLogger(QueryUserCartItemsForDistributor.class);


	@Resource
	private UserCartItemManager userCartItemManager;

	@Resource
	private ItemManager itemManager;

	@Resource
	private ShopManager shopManager;

	@Autowired
	private MarketingManager marketingManager;

	@Resource
	private CartItemServiceManager cartItemServiceManager;

	@Resource
	private ThreadPoolManager threadPoolManager;

	@Resource
	private DozerBeanService dozerBeanService;

	@Autowired
	SupplierClient supplierClient;

	public TradeResponse<List<DistributorCartDTO>> execute(RequestContext context) {
		TradeRequest request = context.getRequest();
		String appKey = (String) context.get("appKey");
		TradeResponse<List<DistributorCartDTO>> response = null;
		if (request.getParam("userId") == null) {
			log.error("userId is null");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING, "userId is null");
		}
		long userId = (Long) request.getParam("userId");

		List<DistributorCartDTO> module = new ArrayList<DistributorCartDTO>();
		List<CartItemDO> cartItems = null;
		// 查询购物车表的商品列表
		try {
			cartItems = this.userCartItemManager.queryUserCartItems(userId);
		} catch (TradeException e) {
			log.error("querySupplierCartItems db error:", e);
			return ResponseUtils.getFailResponse(e.getResponseCode());
		}
		final List<CartItemDTO> cartItemDTOs = new ArrayList<CartItemDTO>();
		List<CartItemDTO> returnList = new ArrayList<CartItemDTO>();
		if (cartItems == null || cartItems.size() == 0) {
			return ResponseUtils.getSuccessResponse(module);
		} else {
			Map<Long, CartItemDO> idCartItemMap = new HashMap<Long, CartItemDO>();
			for (CartItemDO cartItemDO : cartItems) {
				CartItemDTO cartItemDTO = ModelUtil.convert2CartItemDTO(cartItemDO, "");
				cartItemDTOs.add(cartItemDTO);
				idCartItemMap.put(cartItemDO.getId(), cartItemDO);
			}

			//查询购物车商品对应的商品信息
			Map<Long, ItemDTO> itemMap = getItemMap(cartItemDTOs, appKey);

			//查询购物车商品对应的sku信息
			final Map<Long, ItemSkuDTO> itemSkuMap = getItemSkuMap(cartItemDTOs, appKey);

			//异步同步购物车商品信息（与商品最新的信息做同步）
			asyncCartItem(cartItemDTOs, itemSkuMap);

			//查询商品限购信息
			Map<Long, ItemBuyLimitDTO> itemBuyLimitMap = getItemBuyLimitMap(cartItemDTOs, appKey);

			//设置购物车商品状态、限购等信息
			for (CartItemDTO cartItemDTO : cartItemDTOs) {
				if (cartItemDTO.getOriginalId() == null) {
					if (null != cartItemDTO.getItemType()
							&& cartItemDTO.getItemType().intValue() == DBConst.SUIT_ITEM.getCode()) {
						List<CartItemDTO> subCartItemList = new ArrayList<CartItemDTO>();
						for (CartItemDTO cartItemDTO1 : cartItemDTOs) {
							if (cartItemDTO1.getOriginalId() != null
									&& cartItemDTO1.getOriginalId().longValue() == cartItemDTO.getId()) {
								CartItemDTO subCartItemDTO = new CartItemDTO();
								BeanUtils.copyProperties(cartItemDTO1, subCartItemDTO);
								subCartItemList.add(subCartItemDTO);
							}
						}
						cartItemDTO.setSubCartList(subCartItemList);
					}

					if (itemSkuMap.containsKey(cartItemDTO.getItemSkuId()) == true) {
						ItemSkuDTO itemSkuDTO = itemSkuMap.get(cartItemDTO.getItemSkuId());
						ItemDTO itemDTO = itemMap.get(cartItemDTO.getItemId());

						//设置购物车商品状态
						cartItemDTO.setStatus(getCartItemStatus(itemMap.get(cartItemDTO.getItemId())));
						//设置购物车商品库存
						cartItemDTO.setStockNum(getCartItemStock(itemSkuMap.get(cartItemDTO.getItemSkuId())));
						//设置购物车商品名称和图片
						cartItemDTO.setItemName(itemMap.get(cartItemDTO.getItemId()).getItemName());
//						cartItemDTO.setItemImageUrl(itemMap.get(cartItemDTO.getItemId()).getIconUrl());
						// 查看sku图片
						cartItemDTO.setItemImageUrl(itemSkuDTO.getImageUrl());

						//添加购买限制
						if(itemDTO.getSaleMinNum() != null){
							cartItemDTO.setSaleMinNum(itemDTO.getSaleMinNum());
						}else{
							cartItemDTO.setSaleMinNum(0);
						}

						if(itemDTO.getSaleMaxNum() != null){
							cartItemDTO.setSaleMaxNum(itemDTO.getSaleMaxNum());
						}else{
							cartItemDTO.setSaleMaxNum(0);
						}
						if (itemBuyLimitMap.containsKey(cartItemDTO.getItemId())) {
							ItemBuyLimitDTO itemBuyLimitDTO = itemBuyLimitMap.get(cartItemDTO.getItemId());
							//如果账号级别的最大限购个数小于订单级别的限购个数，则将购物车商品最终的限购个数设置为账号级别的限购
							if(cartItemDTO.getSaleMaxNum()==0
									|| (itemBuyLimitDTO.getBuyCount().intValue() < cartItemDTO.getSaleMaxNum())){
								cartItemDTO.setSaleMaxNum(itemBuyLimitDTO.getBuyCount());

							}
						}
                        /**
                         *
                         * @date 2016/7/25
                         * @author guansheng
                         * cartItem 表自带 sellerId itemId itemSkuId  itemType
                         * 设置购物车的商品 brandId 和categoryId
                         * **/
                         cartItemDTO.setBrandId(itemDTO.getItemBrandId());
                         cartItemDTO.setCategoryId(itemDTO.getCategoryId());
                         cartItemDTO.setSellerId(itemDTO.getSellerId());
						//设置购物车商品价格
						cartItemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
						cartItemDTO.setMarketPrice(itemSkuDTO.getMarketPrice());
						cartItemDTO.setWirelessPrice(itemSkuDTO.getWirelessPrice());
					}else{//商品平台那边商品被删除的情况下会存在itemSku查不到的情形
						//商品不存在的话，则直接将购物车商品设置为失效状态
						cartItemDTO.setStatus(EnumCartItemStatus.WITHDRAW.getCode());
						log.warn("[CART_TRACE] itemSku not exist, itemSkuMap:{}", JsonUtil.toJson(itemSkuMap));
					}

					returnList.add(cartItemDTO);
				}

			}
		}

		Map<Long, List<DiscountInfo>> distributorDiscountsMap = new HashMap<Long, List<DiscountInfo>>();

		List<MarketItemDTO> marketItemDTOs = genMarketItemDTOs(returnList);

		//TODO 这里需要让营销平台提供查询分销商购物车折扣信息的接口
		distributorDiscountsMap = Collections.EMPTY_MAP;
        List<DiscountInfo> discountInfoList = null ;
        try {
            discountInfoList = marketingManager.getCartDiscountInfo(marketItemDTOs,appKey,userId);
        } catch (TradeException e) {
            log.error("",e);
            //接口暂时未提供， 先填下个测试的
            //discountInfoList = Collections.EMPTY_LIST;
            //return ResponseUtils.getFailResponse(ResponseCode.SYS_E_SERVICE_EXCEPTION,e.getMessage());
        }
        Map<Long, List<CartItemDTO>> distributorCartsMap = genDistributorCartItemMap(returnList);

        for (Entry<Long, List<CartItemDTO>> entry : distributorCartsMap.entrySet()) {

			DistributorCartDTO distributorCartDTO = new DistributorCartDTO();
			distributorCartDTO.setDistributorId(entry.getKey());
			distributorCartDTO.setDistributorName(entry.getValue().get(0).getDistributorName());

			List<DiscountInfo> discountInfo = distributorDiscountsMap.get(entry.getKey());

			distributorCartDTO.setDiscountInfoList(discountInfo);
            //设置标签 满减送 限时购
            BizMarkCollectionUtil.buildCartBizMarkCollection(discountInfoList,entry.getValue());
			distributorCartDTO.setCartItems(entry.getValue());

			module.add(distributorCartDTO);
		}

		response = ResponseUtils.getSuccessResponse(module);
		response.setTotalCount(returnList.size()); //购物车商品总数
		return response;
	}



	private List<Long> getItemSkuIdList(List<CartItemDTO> cartItems) {
		if (cartItems == null || cartItems.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List<Long> itemSkuIdList = new ArrayList<Long>();
		for (CartItemDTO cartItemDTO : cartItems) {
			itemSkuIdList.add(cartItemDTO.getItemId());
		}

		return itemSkuIdList;
	}

	/**
	 * 查询购物车商品对应的商品平台的sku信息列表
	 * @param cartItems
	 * @param appKey
	 * @return
	 */
	private Map<Long, ItemSkuDTO> getItemSkuMap(List<CartItemDTO> cartItems, String appKey) {
		if (cartItems == null || cartItems.isEmpty()) {
			return Collections.emptyMap();
		}

		List<Long> itemSkuIdList = new ArrayList<Long>();
		Long sellerId = cartItems.get(0).getSellerId();
		for (CartItemDTO cartItemDTO : cartItems) {
			itemSkuIdList.add(cartItemDTO.getItemSkuId());
		}

		try {
			//查询商品列表
//			List<ItemSkuDTO> itemSkuList = itemManager.queryItemSku(itemSkuIdList, sellerId, appKey);
//			List<ItemSkuDTO> itemSkuList = itemManager.queryItemSku(itemSkuIdList, appKey);
			List<ItemSkuDTO> itemSkuList = itemManager.queryDeletedItemSku(itemSkuIdList, appKey);

			//将商品列表转为map结构
			Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
			for (ItemSkuDTO itemSkuDTO : itemSkuList) {
				itemSkuMap.put(itemSkuDTO.getId(), itemSkuDTO);
			}

			if (itemSkuMap.isEmpty()) {
				log.warn("the sku map is empty, itemSkuIdList:{}", JsonUtil.toJson(itemSkuIdList));
			}

			return itemSkuMap;
		} catch (TradeException e) {
			log.error("error to query itemSku, itemSkuIdList:{}", JsonUtil.toJson(itemSkuIdList), e);
		}

		return Collections.emptyMap();
	}

	private Map<Long, ItemBuyLimitDTO> getItemBuyLimitMap(List<CartItemDTO> cartItems, String appKey) {
		if (cartItems == null || cartItems.isEmpty()) {
			return Collections.emptyMap();
		}

		List<Long> itemIdList = new ArrayList<Long>();
		Long sellerId = cartItems.get(0).getSellerId();
		for (CartItemDTO cartItemDTO : cartItems) {
			itemIdList.add(cartItemDTO.getItemId());
		}

		try {
			//查询商品限购列表
			List<ItemBuyLimitDTO> itemBuyLimitDTOs = itemManager.queryItemBuyLimit(itemIdList, sellerId, appKey);
			if (itemBuyLimitDTOs == null) {
				return Collections.emptyMap();
			}

			//将商品列表转为map结构
			Map<Long, ItemBuyLimitDTO> itemBuyLimitMap = new HashMap<Long, ItemBuyLimitDTO>();

			for (ItemBuyLimitDTO itemBuyLimitDTO : itemBuyLimitDTOs) {
				itemBuyLimitMap.put(itemBuyLimitDTO.getItemId(), itemBuyLimitDTO);
			}

			return itemBuyLimitMap;
		} catch (Exception e) {
			log.error("error to query itemBuyLimit, itemIdList:{}", JsonUtil.toJson(itemIdList), e);
		}

		return Collections.emptyMap();
	}

	/**
	 * 查询购物车商品对应的商品平台的商品信息列表
	 * @param cartItems
	 * @param appKey
	 * @return
	 */
	private Map<Long, ItemDTO> getItemMap(List<CartItemDTO> cartItems, String appKey) {
		if (cartItems == null || cartItems.isEmpty()) {
			return Collections.emptyMap();
		}

		List<Long> itemIdList = new ArrayList<Long>();
		Long sellerId = cartItems.get(0).getSellerId();
		for (CartItemDTO cartItemDTO : cartItems) {
			itemIdList.add(cartItemDTO.getItemId());
		}

		try {
			//查询商品列表
//			List<ItemDTO> itemList = itemManager.queryItem(itemIdList, sellerId, appKey);
			List<ItemDTO> itemList = itemManager.queryDeletedItem(itemIdList, sellerId, appKey);

			//将商品列表转为map结构
			Map<Long, ItemDTO> itemMap = new HashMap<Long, ItemDTO>();
			for (ItemDTO itemDTO : itemList) {
				itemMap.put(itemDTO.getId(), itemDTO);
			}


			if (itemMap.isEmpty()) {
				log.warn("the item map is empty, itemIdList:{}", JsonUtil.toJson(itemIdList));
			}

			return itemMap;
		} catch (TradeException e) {
			log.error("error to query item, itemIdList:{}", JsonUtil.toJson(itemIdList), e);
		}

		return Collections.emptyMap();
	}
    //设置分销店铺
	private Map<Long, List<CartItemDTO>> genDistributorCartItemMap(List<CartItemDTO> cartItems) {
		Map<Long, List<CartItemDTO>> distributorCartItemMap = new HashMap<Long, List<CartItemDTO>>();
		for (CartItemDTO cartItemDTO : cartItems) {
			Long distributorId = cartItemDTO.getDistributorId();
			if (distributorCartItemMap.get(distributorId) == null) {
				List<CartItemDTO> carts = new ArrayList<CartItemDTO>();
				carts.add(cartItemDTO);
				distributorCartItemMap.put(distributorId, carts);
			} else {
				distributorCartItemMap.get(distributorId).add(cartItemDTO);
			}
		}
		return distributorCartItemMap;
	}

	private List<MarketItemDTO> genMarketItemDTOs(List<CartItemDTO> cartItems) {
		List<MarketItemDTO> marketItemDTOs = Collections.EMPTY_LIST;
		if (cartItems.isEmpty() == false) {
			marketItemDTOs = new ArrayList<MarketItemDTO>();
		}
       // itemId/ skuId/ itemType/ brandId/ categoryId

        for (CartItemDTO cartItemDTO : cartItems) {
			MarketItemDTO marketItemDTO = new MarketItemDTO();
			marketItemDTO.setItemId(cartItemDTO.getItemId());
			marketItemDTO.setItemSkuId(cartItemDTO.getItemSkuId());
			marketItemDTO.setSellerId(cartItemDTO.getSellerId());

			marketItemDTO.setNumber(cartItemDTO.getNumber());
			marketItemDTO.setUnitPrice(cartItemDTO.getPromotionPrice());
			marketItemDTO.setItemType(cartItemDTO.getItemType());
            marketItemDTO.setBrandId(cartItemDTO.getBrandId());
            marketItemDTO.setCategoryId(cartItemDTO.getCategoryId());

			List<CartItemServiceDTO> cartItemServiceDTOs = cartItemDTO.getCartItemServiceList();
			if (cartItemServiceDTOs != null && cartItemServiceDTOs.isEmpty() == false) {
				List<MarketValueAddedServiceDTO> marketValueAddedServiceList = new ArrayList<MarketValueAddedServiceDTO>();
				for (CartItemServiceDTO cartItemServiceDTO : cartItemServiceDTOs) {
					MarketValueAddedServiceDTO marketValueAddedServiceDTO = new MarketValueAddedServiceDTO();
					marketValueAddedServiceDTO.setId(cartItemServiceDTO.getServiceId());
					marketValueAddedServiceDTO.setPrice(cartItemServiceDTO.getPrice());
					marketValueAddedServiceDTO.setSellerId(cartItemServiceDTO.getSellerId());
					marketValueAddedServiceList.add(marketValueAddedServiceDTO);
				}
				marketItemDTO.setServices(marketValueAddedServiceList);
			}
			marketItemDTOs.add(marketItemDTO);
		}

		return marketItemDTOs;
	}

	/**
	 * 获取购物车商品状态，需要基于实际商品的状态转个转换：
	 * 1代表商品处于上架状态；2代表商品已下架
	 * @param itemDTO
	 * @return
	 */
	private int getCartItemStatus(ItemDTO itemDTO) {
		//如果商品为null，则直接返回无效状态
		if (itemDTO == null) {
			return EnumCartItemStatus.WITHDRAW.getCode();
		}
		int itemStatus = itemDTO.getItemStatus();
		if ( (itemStatus == ItemStatus.ON_SALE.getStatus() || itemStatus == ItemStatus.PRE_SALE.getStatus() ) && itemDTO.getDeleteMark() == 0 ) {
			return EnumCartItemStatus.ON_SALE.getCode();
		} else {
			return EnumCartItemStatus.WITHDRAW.getCode();
		}
	}

	/**
	 * 获取指定sku的可用库存数量
	 * @param itemSkuDTO
	 * @return
	 */
	private int getCartItemStock(ItemSkuDTO itemSkuDTO) {
		if (itemSkuDTO == null) {
			return 0;
		}

		if(itemSkuDTO.getFrozenStockNum() == null){
			return itemSkuDTO.getStockNum().intValue();
		}else {
			return (int)(itemSkuDTO.getStockNum() - itemSkuDTO.getFrozenStockNum());
		}
	}

	private void asyncCartItem(List<CartItemDTO> cartItemList, Map<Long, ItemSkuDTO> itemSkuMap) {
		try {
			final List<CartItemDTO> changedCartItemList = new ArrayList<CartItemDTO>();
			for (CartItemDTO cartItemDTO : cartItemList) {
				ItemSkuDTO itemSkuDTO = itemSkuMap.get(cartItemDTO.getItemSkuId());

				if (itemSkuDTO == null) {//商品平台那边商品被删除的情况下会存在itemSku查不到的情形
					continue;
				}

				if (isCartItemSync(cartItemDTO, itemSkuDTO) == false) {
					changedCartItemList.add(cartItemDTO);
				}
			}

			//由于每次查询购物车列表，都会同步购物车商品信息，所以这里先基于线程池简单实现异步更新购物车商品信息。TODO 后续优化成基于异步任务
			threadPoolManager.execute(new Runnable() {
				@Override
				public void run() {
					for (CartItemDTO cartItemDTO : changedCartItemList) {
						try {
							userCartItemManager.updateCartItem(ModelUtil.convert2CartItemDO(cartItemDTO));
						} catch (TradeException e) {
							log.error("error to update cartItem, cartItemDTO:{}", JsonUtil.toJson(cartItemDTO), e);
						} catch (Exception e) {
							log.error("", e);
						}
					}
				}
			});
		} catch (Throwable t) {//FIXME 捕获系统高负载情况下，同步购物车信息出错的异常，当作降级看待，确保不影响系统主流程
			log.error("error to asyncCartItem, msg:{}", t.getMessage());
		}
	}

	/**
	 * 判断指定购物车商品跟商品库的商品sku信息是否同步。目前只校验价格信息是否同步
	 * @param cartItemDTO
	 * @param itemSkuDTO
	 * @return
	 */
	private boolean isCartItemSync(CartItemDTO cartItemDTO, ItemSkuDTO itemSkuDTO) {
		if (cartItemDTO == null || itemSkuDTO == null) {
			log.error("[CART_TRACE] cartItem:{}, itemSku:{}",
					JsonUtil.toJson(cartItemDTO), JsonUtil.toJson(itemSkuDTO));
			return true;
		}

		if (itemSkuDTO.getPromotionPrice() == null
				|| itemSkuDTO.getWirelessPrice() == null || itemSkuDTO.getMarketPrice() == null) {
			log.error("[CART_TRACE] itemSku price is null, itemSku:{}", JsonUtil.toJson(itemSkuDTO));
			return true;
		}

		try {
			//校验价格信息是否同步
			if (cartItemDTO.getPromotionPrice().longValue() != itemSkuDTO.getPromotionPrice()) {
				return false;
			}

			if (cartItemDTO.getMarketPrice().longValue() != itemSkuDTO.getMarketPrice()) {
				return false;
			}

			if (cartItemDTO.getWirelessPrice() != itemSkuDTO.getWirelessPrice()) {
				return false;
			}
		} catch (Exception e) {
			log.error("cartItemDTO:{}, itemSkuDTO:{}",
					JsonUtil.toJson(cartItemDTO), JsonUtil.toJson(itemSkuDTO), e);
		}

		return true;
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_USERCART_ITEMS_FOR_DISTRIBUTOR.getActionName();
	}

}

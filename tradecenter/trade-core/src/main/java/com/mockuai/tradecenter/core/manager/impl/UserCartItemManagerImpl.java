package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.common.domain.CartItemServiceQTO;
import com.mockuai.tradecenter.core.dao.CartItemServiceDAO;
import com.mockuai.tradecenter.core.dao.UserCartItemDao;
import com.mockuai.tradecenter.core.domain.CartItemDO;
import com.mockuai.tradecenter.core.domain.CartItemServiceDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.UserCartItemManager;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class UserCartItemManagerImpl implements UserCartItemManager{
	private static final Logger log = LoggerFactory.getLogger(UserCartItemManagerImpl.class);

	@Resource
	private UserCartItemDao userCartItemDao;
	@Resource
	private TransactionTemplate transactionTemplate;
	
	@Resource
	private CartItemServiceDAO cartItemServiceDAO;
	
	@Override
	public Long addUserCartItem(final CartItemDO carItemDO,final List<CartItemServiceDO> cartItemServiceDOList)throws TradeException{
		log.info("enter addUserCartItem ,itemSkuId " + carItemDO.getItemSkuId() + ",userId:  " + carItemDO.getUserId());
		TradeResponse<Long> result = transactionTemplate.execute(new TransactionCallback<TradeResponse<Long>>() {
			Long cartId =0l;
			@Override
			public TradeResponse<Long> doInTransaction(TransactionStatus status) {
				try{
					cartId = userCartItemDao.addUserCartItem(carItemDO);
					if(null!=cartItemServiceDOList&&cartItemServiceDOList.size()>0){
						for(CartItemServiceDO cartItemServiceDO:cartItemServiceDOList){
							cartItemServiceDO.setCartId(cartId);
							cartItemServiceDO.setItemSkuId(carItemDO.getItemSkuId());
							cartItemServiceDAO.addCartItemService(cartItemServiceDO);
						}
					}
					return ResponseUtils.getSuccessResponse(cartId);
				}catch(Exception e){
					log.error("add user cart error",e);
					status.setRollbackOnly();
				}
				return ResponseUtils.getSuccessResponse(cartId);
			}
		});
		log.info(" result: {}",result);
		
		return result.getModule();
				
	}
	
	@Override
	public int deleteUserCartItem(final CartItemDO cartItemDO)throws TradeException{
		int result =  transactionTemplate.execute(new TransactionCallback<Integer>() {
			Integer processResult =0;
			
			@Override
			public Integer doInTransaction(TransactionStatus arg0) {
				try{
					processResult = userCartItemDao.deleteUserCartItem(cartItemDO);
					
					userCartItemDao.deleteUserCartItemByOriginalId(cartItemDO.getId());
					
					CartItemServiceQTO cartItemServiceQuery = new CartItemServiceQTO();
					cartItemServiceQuery.setCartId(cartItemDO.getId());
					
					cartItemServiceDAO.deleteByCartId(cartItemServiceQuery);
					
				}catch(Exception e){
					log.error("",e);
				}
				return processResult;
			}
			
		});
		
		return result;
		
	}

	public int removeCartItem(Long userId, List<Long> itemSkuIdList) throws TradeException {
		try{
			return this.userCartItemDao.removeCartItem(userId, itemSkuIdList);
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
		}

	}

	@Override
	public int cleanUserCart(Long userId) throws TradeException{
		log.info("enter cleanUserCart : " + userId);
		int result=0;
		try{
			result = this.userCartItemDao.cleanUserCart(userId);
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e.getMessage());
		}
		log.info("exit cleanUserCart: " + result);
		return result;
	}

//	@Override
//	public int updateUserCartItem(CartItemDO cartItemDO)
//			throws TradeException {
//		return this.userCartItemDao.updateUserCartItem(cartItemDO);
//	}

	@Override
	public List<CartItemDO> queryUserCartItems(Long userId)
			throws TradeException {
		log.info("enter queryUserCartItems: " + userId);
		List<CartItemDO> result = this.userCartItemDao.queryUserCartItems(userId);
		log.info("exit queryUserCartItems ,size: " + result.size());
		return result;
	}

	@Override
	public List<Long> queryUserCartItemId(Long userId) throws TradeException {
		log.info("enter  queryUserCartItemId:" + userId);
		List<Long> result = this.userCartItemDao.queryUserCartItemId(userId);
		log.info("exit queryUserCartItemId: " + result);
		return result;
	}

	@Override
	public int addBatchUserCartItem(List<CartItemDO> cartItemDOList)
			throws TradeException {
		log.info("enter addBatchUserCartItem ,size: " + cartItemDOList.size());
		int result =  this.userCartItemDao.addCartItems(cartItemDOList);
		log.info("exit addBatchUserCartItem ,result : " + result );
		return result;
	}
	
//	@Override
//    public int deleteGiftItems(Long userId)throws TradeException{
//		log.info("enter deleteGiftItems: "+ userId);
//		int result = 0;
//		try{
//			result = this.userCartItemDao.deleteGiftItems(userId);
//		}catch(Exception e){
//			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR);
//		}
//		log.info("exit deleteGiftItems : " + result);
//		return result;
//	}

	
	@Override
	public String validateCartItemFields4Add(CartItemDTO cartItem){
		if(cartItem.getNumber() == null){
			return "number is null";
		}

		if(cartItem.getNumber() < 1){  // 数量不能小于1
			return "number must be greater than 0";
		}

		if(cartItem.getItemSkuId() == null){
			return "itemSkuId is null";
		}

		if(cartItem.getSellerId() == null){
			return "sellerId is null";
		}

		if(cartItem.getUserId() == null){
			return "userId is null";
		}

		/*if (cartItem.getDistributorId() == null) {
			return "distributorId is null";
		}*/

		return null;
	}
	
	
	@Override
	public String validateCartItemDTOsFields4Add(List<CartItemDTO> cartItemDTOs){
		for(CartItemDTO cartItemDTO:cartItemDTOs){
			if(cartItemDTO.getNumber() == null){
				return "number is null";
			}else if(cartItemDTO.getNumber() < 1){  // 数量不能小于1
				return "number must be greater than 0";
			}else if(cartItemDTO.getItemSkuId() == null){
				return "itemSkuId is null";
			}else if(cartItemDTO.getSellerId() == null){
				return "sellerId is null";
			}else if(cartItemDTO.getUserId() == null){
				return "userId is null";
			}
			return null;
		}
		return null;
		
	}
	
	@Override
	public int updateUserCartItemNumber(final CartItemDO cartItemDO,final List<CartItemServiceDO> cartItemServiceDOList)throws TradeException{
		log.info("enter updateUserCartItemNumber : "+ cartItemDO.getId() + " " + cartItemDO.getNumber());
		int result =0;
		result =  transactionTemplate.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				Integer updateUserCartItemNumberResult = 0;
				try{
					updateUserCartItemNumberResult = userCartItemDao.updateUserCartItemNumber(cartItemDO);
					if(null!=cartItemServiceDOList&&cartItemServiceDOList.size()>0){
						CartItemServiceQTO query = new CartItemServiceQTO();
						query.setCartId(cartItemDO.getId());
						cartItemServiceDAO.deleteByCartId(query);
						for(CartItemServiceDO cartItemServiceDO:cartItemServiceDOList){
							cartItemServiceDO.setCartId(cartItemDO.getId());
							cartItemServiceDO.setItemSkuId(cartItemDO.getItemSkuId());
							cartItemServiceDAO.addCartItemService(cartItemServiceDO);
						}
					}
					
				}catch(Exception e){
					log.error("update user cart error",e);
					status.setRollbackOnly();
				}
				return updateUserCartItemNumberResult;
			}
		});
		log.info("exit updateUserCartItemNumber : " + result);
		
		return result;
	}

	@Override
	public CartItemDO getCartItemBySkuId(Long itemSkuId,Long distributorId,Long userId){
		log.info("enter getCartItemBySkuId : " + itemSkuId + "," +distributorId + " ," + userId);
		CartItemDO cartItemDO = new CartItemDO();
		cartItemDO.setItemSkuId(itemSkuId);
		cartItemDO.setUserId(userId);
		cartItemDO.setDistributorId(distributorId);
		cartItemDO = this.userCartItemDao.getCartItemBySkuId(cartItemDO);
		log.info("exit getCartItemBySkuId " + (cartItemDO ==null));
		return cartItemDO ;
	}
    @Override
    public CartItemDO getCartItemBySkuId(Long itemSkuId,Long userId){
        log.info("enter getCartItemBySkuId : " + itemSkuId  + " ," + userId);
        CartItemDO cartItemDO = new CartItemDO();
        cartItemDO.setItemSkuId(itemSkuId);
        cartItemDO.setUserId(userId);
        cartItemDO = this.userCartItemDao.getCartItemBySkuId(cartItemDO);
        log.info("exit getCartItemBySkuId " + (cartItemDO ==null));
        return cartItemDO ;
    }


	@Override
	public CartItemDO getCartItemByShareSkuId(Long itemSkuId,Long shareUserId,Long userId){
		log.info("enter getCartItemBySkuId : " + itemSkuId + "," +shareUserId + " ," + userId);
		CartItemDO cartItemDO = new CartItemDO();
		cartItemDO.setItemSkuId(itemSkuId);
		cartItemDO.setUserId(userId);
		cartItemDO.setShareUserId(shareUserId);
		cartItemDO = this.userCartItemDao.getCartItemByShareSkuId(cartItemDO);
		log.info("exit getCartItemBySkuId " + (cartItemDO ==null));
		return cartItemDO ;
	}

	@Override
	public Integer getCartItemsCount(Long userId){
//		log.info("enter getCartItemsCount:  " + userId);
		Integer result = this.userCartItemDao.getCartItemsCount(userId);
//		log.info("exit getCartItemsCount: " + result);
		return result;
	}
	
	@Override
	public CartItemDO getCartItem(Long id,Long userId){
		log.info("enter getCartItem ：" + id);
		CartItemDO cartItem = new CartItemDO();
		cartItem.setId(id);
		cartItem.setUserId(userId);
		CartItemDO result = this.userCartItemDao.getCartItem(cartItem);
		return result;
	}

	@Override
	public int getCartItemCountByUserAndSellerId(Long itemId, Long userId, Long sellerId) throws TradeException{
		if(itemId == null){// 需要根据买家id进行分表查询
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,"itemId is null");
		}
		try{
			return this.userCartItemDao.getCartItemCountByUserAndSellerId(itemId, userId, sellerId);
		
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
		
	}

	@Override
	public int getCartItemCountBySkuIdAndUserId(Long skuId, Long userId,Long sellerId) throws TradeException {
		if(skuId == null || null == userId){
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,"userId or skuId is null");
		}
		
		if(null == sellerId){
			throw new TradeException(ResponseCode.PARAM_E_PARAM_MISSING,"sellerId  is null");
		}
		try{
		return this.userCartItemDao.getCartItemCountBySkuIdAndUserId(skuId, userId, sellerId);
		}catch(Exception e){
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR,e);
		}
	}

	@Override
	public int removeCartItemByOrinalSkuId(Long userId, List<Long> oriIdList) throws TradeException {
		List<CartItemDO> subCartItemList = userCartItemDao.queryCartItemsByOriSkuIdList(userId, oriIdList);
		if(null!=subCartItemList){
			List<Long> subSkuIds = new ArrayList<Long>();
			for(CartItemDO cartItemDO:subCartItemList){
				subSkuIds.add(cartItemDO.getItemSkuId());
			}
			return userCartItemDao.removeCartItem(userId,subSkuIds);
		}
		return 0;
	}

	@Override
	public List<CartItemDO> querySupplierCartItems(long userId) throws TradeException {
		return userCartItemDao.querySupplierCartItems(userId);
	}

	@Override
	public int updateCartItem(CartItemDO cartItem) throws TradeException {
		try{
			return userCartItemDao.updateCartItem(cartItem);
		}catch(Exception e){
			log.error("update user cart error, cartItem:{}", JsonUtil.toJson(cartItem), e);
			throw new TradeException(ResponseCode.SYS_E_DATABASE_ERROR);
		}
	}
}

package com.mockuai.tradecenter.client.cart;

import java.util.List;

import com.mockuai.tradecenter.common.api.Response;
import com.mockuai.tradecenter.common.domain.CartItemDTO;

/**
 * @author cwr
 */
public interface UserCartItemClient {
	
	/**
	 * 新增购物车商品
	 * @param request
	 * @return
	 */
	public Response<CartItemDTO> addUserCartItem(CartItemDTO cartItemDTO, String appKey);
	
	/**
	 * 删除购物车商品
	 * @param cartItemDTO
	 * @return
	 */
	public Response<CartItemDTO> deleteUserCartItem (Long id,Long userId, String appKey);
	
	/**
	 * 清空用户购物车
	 * @param cartItemDTO
	 * @return
	 */
	public Response<CartItemDTO> cleanUserCart(Long userId, String appKey);
	
	/**
	 * 查询
	 * @param userId
	 * @param appKey
	 * @return
	 */
//	public Response<?> queryUserCart(Long userId,String appKey);
	
	Response<Boolean> syncUserCart(List<CartItemDTO> cartItemDTOs,String appKey);
}

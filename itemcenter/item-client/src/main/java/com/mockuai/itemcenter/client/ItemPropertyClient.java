package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;

/**
 * 商品属性相关的接口
 * @author cwr
 *
 */
public interface ItemPropertyClient {
	
	/**
	 * 根据商品的类目查询商品的属性及其对应的属性值
	 * @param categoryId
	 * @param appKey
	 * @return
	 */
	public Response<List<ItemPropertyTmplDTO>> queryItemPropertyTmpl(
			ItemPropertyTmplQTO qto,Boolean needPropertyValue, String appKey);

    Response<Integer> countItemProperty(
            ItemPropertyQTO qto, String appKey);

    /**
	 * 根据类目查询商品的销售属性及其对应的属性值列表
	 * @param qto
	 * @return
	 */
	public Response<List<SkuPropertyTmplDTO>> querySkuPropertyTmpl(
			SkuPropertyTmplQTO qto,Boolean needPropertyValue, String appKey);

	/**
	 * 新增商品属性模板
	 * @param itemPropertyTmplDTO
	 * @param appKey
	 * @return
	 */
	public Response<Long> addItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO, String appKey);

	/**
	 * 修改商品属性模板
	 * @param itemPropertyTmplDTO
	 * @param appKey
	 * @return
	 */
	public Response<Boolean> updateItemPropertyTmpl(ItemPropertyTmplDTO itemPropertyTmplDTO, String appKey);

	/**
	 * 删除指定的商品属性模板
	 * @param itemPropertyTmplId
	 * @param appKey
	 * @return
	 */
	public Response<Void> deleteItemPropertyTmpl(Long itemPropertyTmplId, String appKey);

	/**
	 * 添加sku属性模板
	 * @param skuPropertyTmplDTO
	 * @param appKey
	 * @return
	 */
	public Response<Long> addSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO, String appKey);

	/**
	 * 添加sku属性模板
	 * @param skuPropertyTmplDTO
	 * @param appKey
	 * @return
	 */
	public Response<Boolean> updateSkuPropertyTmpl(SkuPropertyTmplDTO skuPropertyTmplDTO, String appKey);

	/**
	 * 删除sku属性模板
	 * @param skuPropertyTmplId
	 * @param appKey
	 * @return
	 */
	public Response<Void> deleteSkuPropertyTmpl(Long skuPropertyTmplId, String appKey);

	public Response<Long> addItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationDTO itemSkuPropertyRecommendationDTO,String appKey);

	public Response<List<ItemSkuPropertyRecommendationDTO>> queryItemSkuPropertyRecommendation(ItemSkuPropertyRecommendationQTO itemSkuPropertyRecommendationQTO,String appKey);

	public Response<Long> deleteItemSkuPropertyRecommendation(Long id,Long sellerId,String appKey);
}


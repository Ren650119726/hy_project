package com.mockuai.itemcenter.client;


import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;

public interface ItemCategoryClient {

	/**
	 * 查询类目信息
	 * @param itemCategoryQTO
	 * @return
	 */
	public Response<List<ItemCategoryDTO>> queryItemCategory(ItemCategoryQTO itemCategoryQTO, String appKey);

    /**
     * 查询类目模板信息
     *
     * @param itemCategoryTmplQTO
     * @return
     */
    public Response<List<ItemCategoryTmplDTO>> queryItemCategoryTmpl(ItemCategoryTmplQTO itemCategoryTmplQTO, String appKey);

    /**
	 * 查询叶子类目节点
	 * @return
	 */
	public Response<List<ItemCategoryDTO>> queryItemHierachyCategory(String appKey);

	/**
	 * 查询商城所有的级联类目节点
	 * @return
	 */
	public Response<List<ItemCategoryDTO>> queryItemLeafCategory(String appKey);

	/**
	 * 根据类目id获取类目信息
	 * @param categoryId
	 * @return
	 */
	public Response<ItemCategoryDTO> getItemCategory(Long categoryId, String appKey);


	/**
	 * hsq 根据itemId获取商品类目信息
	 * @param itemId
	 * @param appKey
	 * @return
	 */
	public Response<ItemCategoryDTO> getItemCategoryByItemId(Long itemId, String appKey);





	/**
	 * 新增类目信息
	 * @param itemCategoryDTO
	 * @return
	 */
	public Response<ItemCategoryDTO> addItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey);

	/**
	 * 删除指定类目信息
	 * @param categoryId
	 * @param appKey
	 * @return
	 */
	public Response<Void> deleteItemCategory(Long categoryId, String appKey);

	/**
	 * 更新指定类目信息
	 * @param itemCategoryDTO
	 * @param appKey
	 * @return
	 */
	public Response<Void> updateItemCategory(ItemCategoryDTO itemCategoryDTO, String appKey);

    public Response<Void> initMallCategory(String appKey);
}

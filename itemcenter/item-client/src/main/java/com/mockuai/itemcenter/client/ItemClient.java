package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.mop.MopBaseItemDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;

import java.util.List;

 public interface ItemClient {

    /**
     * 根据itemId和supplierId获取商品信息 ,区分是否需要详细信息
     * @param id
     * @param needDetail 表示了是否需要获取详细信息包括 销售属性 基本属性信息
     * @return
     */
     Response<ItemDTO> getItem(Long id, Long sellerId, Boolean needDetail, String appKey);

     Response<ItemDTO> getSimpleItem(Long id, Boolean needDetail, String appKey);
     Response<Void> copyItem(Long id, String appKey);

    /**
     /**
     * 根据itemId获取商品信息 ,区分是否需要详细信息
     * @param itemId
     * @param needDetail
     * @param appKey
     * @return
     */
     Response<MopBaseItemDTO> writePublishItem(Long itemId, Integer type,Boolean needDetail, String appKey);

    /**
     * 新加商品
     * @param itemDTO
     * @return
     */
     Response<ItemDTO> addItem(ItemDTO itemDTO, String appKey);

    /**
     * 更新商品信息
     * @param itemDTO
     * @param updateDetail 是否需要更新下面的属性 销售属性 图片等
     * @return
     */
     Response<Boolean> updateItem(ItemDTO itemDTO,Boolean updateDetail, String appKey);

    /**
     * 更新商品信息 特别地，当日期字段为空时表示置空这个字段，而不是不处理
     * @param itemDTO
     * @param updateDetail 是否需要更新下面的属性 销售属性 图片等
     * @return
     */
     Response<Boolean> updateItemWithBlankDate(ItemDTO itemDTO,Boolean updateDetail, String appKey);

    /**
     * 删除商品
     * @param itemId
     * @param supplierId
     * @return
     */
     Response<Boolean> deleteItem(Long itemId,Long supplierId, String appKey);



     Response<List<ItemSearchDTO>> searchItem(ItemSearchQTO itemSearchQTO,String appKey);

    /**
     * 根据复合条件查询商品列表
     * @param itemQTO
     * @return
     */
     Response<List<ItemDTO>> queryItem(ItemQTO itemQTO, String appKey);

    /**
     * 查询商品列表
     *
     * @param itemSearchQTO
     * @param appKey
     * @return
     */
     Response<List<ItemDTO>> queryItemWithDefaultSku(ItemSearchQTO itemSearchQTO, String appKey);

    /**
     * 计数商品
     * @param itemQTO
     * @return
     */
     Response<Long> countTotalItem(ItemQTO itemQTO, String appKey);

    /**
     * 根据复合条件查询商品列表
     * @param itemQTO
     * @return
     */
     Response<Integer> countGruopItem(ItemQTO itemQTO, String appKey);

    /**
     * 下架商品
     * @return
     */
     Response<Boolean> withdrawItem(Long itemId,Long supplierId, String appKey);

    /**
     * @param itemId
     * @param supplierId
     * @return
     */
     Response<Boolean> upItem(Long itemId,Long supplierId, String appKey);


    /**
     * 批量下架商品
     * @param itemIds
     * @param supplierId
     * @param appKey
     * @return
     */
     Response<Boolean> batchWithdrawItem(List<Long> itemIds,Long supplierId, String appKey);

    /**
     * 批量下架商品
     * @param itemIds
     * @param supplierId
     * @return
     */
     Response<Boolean> batchUpItem(List<Long> itemIds,Long supplierId, String appKey);

    /**
     * 批量删除商品
     *
     * @param itemIds
     * @param supplierId
     * @return
     */
     Response<Boolean> batchDeleteItem(List<Long> itemIds, Long supplierId, String appKey);

    /**
     * @param itemId
     * @param supplierId
     * @return
     */
     Response<Boolean> preSaleItem(Long itemId,Long supplierId, String appKey);

    /**
     * 解冻商品
     * @return
     */
     Response<Boolean> thawItem(Long itemId,Long supplierId, String appKey);

    /**
     * 冻结商品
     * @param itemId
     * @param supplierId
     * @return
     */
     Response<Boolean> freezeItem(Long itemId,Long supplierId, String appKey);

    /**
     * 添加商品评论
     * @param itemCommentDTOs
     * @return
     */
     Response<Boolean> addItemComment(List<ItemCommentDTO> itemCommentDTOs, String appKey);

    /**
     * 更新商品评论
     * @param itemCommentDTO
     * @return
     */
     Response<Boolean> updateItemComment(ItemCommentDTO itemCommentDTO, String appKey);

    /**
     * 查询交易评价;
     * @param itemCommentQTO
     * @return
     */
     Response<List<ItemCommentDTO>> queryItemComment(ItemCommentQTO itemCommentQTO, String appKey);

    /**
     * 删除商品评论
     * @return
     */
     Response<Boolean> deleteItemComment(Long commentId, Long sellerId, String appKey);

    /**
     * 查询分组内ID;
     * @param itemQTO
     * @return
     */
     Response<List<ItemDTO>> queryGroupItem(ItemQTO itemQTO, String appKey);


    /**
     * 查询商品评价等级
     * @param itemCommentQTO
     * @return
     */
     Response<List<ItemCommentDTO>> queryItemCommentGrade(ItemCommentQTO itemCommentQTO, String appKey);

    /**
     * 分类统计商品评价等级
     * @param itemCommentQTO
     * @return
     */
     Response<CountCommentDTO> countItemCommentGrade(ItemCommentQTO itemCommentQTO, String appKey);

    /**
     * 查询分组内ID;
     * @param groupId
     * @param sellerId
     * @return
     */
     Response<Boolean> addGroupItem(Long groupId, Long sellerId, Long itemId, String appKey);

    /**
     * 查询分组内ID;
     * @param itemId
     * @param sellerId
     * @return
     */
     Response<Boolean> removeGroupItem(Long sellerId, Long itemId, String appKey);

     Response<Boolean> removeItemToDefaultGroup(Long sellerId, Long groupId, String appKey);

     Response<Long> trashItem(Long itemId, Long sellerId, String appKey);

     Response<Long> recoveryItem(Long itemId, Long sellerId, String appKey);

     Response<Long> batchTrashItem(List<Long> itemIdList, Long sellerId, String appKey);

     Response<Long> batchRecoveryItem(List<Long> itemIdList, Long sellerId, String appKey);

     Response<Long> emptyItemRecycleBin(Long sellerId, String appKey);

     Response<Void> updateSearchIndex(Long itemId,Long stockNum, Long frozenStockNum, String appKey);




    /**
     * 根据itemId和sellerId获取商品信息 ,区分是否需要详细信息
     * @param id
     * @param sellerId
     * @param needDetail 表示了是否需要获取详细信息包括 销售属性 基本属性信息
     * @return
     */
     Response<MopItemDTO> getMopItem(Long id, Long sellerId, Boolean needDetail, String appKey);


    /**
     * 更新商品索引
     * @param itemDTO
     * @param appKey
     * @return
     */
     Response<Void> itemSearch (ItemDTO itemDTO, String appKey);
}

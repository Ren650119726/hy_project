package com.mockuai.itemcenter.client.impl;

import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.ItemService;
import com.mockuai.itemcenter.common.api.Request;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.mop.MopBaseItemDTO;
import com.mockuai.itemcenter.common.domain.mop.MopItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;

import javax.annotation.Resource;
import java.util.List;

public class ItemClientImpl implements ItemClient {

    @Resource
    private ItemService itemService;

    public Response<ItemDTO> getItem(Long id, Long supplierId,Boolean needDetail, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("supplierId", supplierId);
        request.setParam("needDetail",needDetail);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_ITEM.getActionName());
        return itemService.execute(request);
    }

    /**
     * 不读取分佣数据， 只读取商品服务的数据
     * @param id
     * @param needDetail
     * @param appKey
     * @return
     */
    @Override
    public Response<ItemDTO> getSimpleItem(Long id, Boolean needDetail, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("needDetail",needDetail);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_SIMPLE_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Void> copyItem(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COPY_ITEM.getActionName());
        return itemService.execute(request);
    }


    @Override
    public Response<MopBaseItemDTO> writePublishItem(Long itemId,Integer type, Boolean needDetail, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("type", type);
        request.setParam("needDetail",needDetail);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.WRITER_PUBLISH_ITEM.getActionName());
        return itemService.execute(request);
    }


    public Response<ItemDTO> addItem(ItemDTO itemDTO, String appKey){
        Request request = new BaseRequest();
        request.setParam("itemDTO", itemDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> updateItem(ItemDTO itemDTO,Boolean updateDetail, String appKey){
        Request request = new BaseRequest();
        request.setParam("itemDTO", itemDTO);
        request.setParam("updateDetail", updateDetail);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> updateItemWithBlankDate(ItemDTO itemDTO, Boolean updateDetail, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDTO", itemDTO);
        request.setParam("updateDetail", updateDetail);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_WITH_BLANK_DATE.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> deleteItem(Long itemId,Long supplierId, String appKey){
        Request request = new BaseRequest();
        request.setParam("ID", itemId);
        request.setParam("sellerId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemSearchDTO>> searchItem(ItemSearchQTO itemSearchQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSearchQTO",itemSearchQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SEARCH_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemDTO>> queryItem(ItemQTO itemQTO, String appKey){
        Request request = new BaseRequest();
        request.setParam("itemQTO",itemQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemDTO>> queryItemWithDefaultSku(ItemSearchQTO itemSearchQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemSearchQTO", itemSearchQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM_WITH_DEFAULT_SKU.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> countTotalItem(ItemQTO itemQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemQTO",itemQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COUNT_TOTAL_ITEM_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Integer> countGruopItem(ItemQTO itemQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemQTO",itemQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COUNT_GROUP_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> withdrawItem(Long itemId,Long supplierId, String appKey){
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.WITHDRAW_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> upItem(Long itemId,Long supplierId, String appKey){
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UP_ITEM.getActionName());
        return itemService.execute(request);
    }



    public Response<Boolean> batchWithdrawItem(List<Long> itemIds, Long supplierId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemIds", itemIds);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BATCH_WITHDRAW_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> batchUpItem(List<Long> itemIds, Long supplierId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemIds", itemIds);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BATCH_UP_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> batchDeleteItem(List<Long> itemIds, Long supplierId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemIds", itemIds);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BATCH_DELETE_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> preSaleItem(Long itemId,Long supplierId, String appKey){
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.PRE_SALE_ITEM.getActionName());
        return itemService.execute(request);
    }


    public Response<Boolean> thawItem(Long itemId, Long supplierId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.THAW_ITEM_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> freezeItem(Long itemId, Long supplierId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("supplierId", supplierId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.FREEZE_ITEM_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> addItemComment(List<ItemCommentDTO> itemCommentDTOs, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemCommentList", itemCommentDTOs);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEMCOMMENT.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> updateItemComment(ItemCommentDTO itemCommentDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemCommentDTO", itemCommentDTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.UPDATE_ITEM_COMMENT.getActionName());
        return itemService.execute(request);
    }

    public Response<List<ItemCommentDTO>> queryItemComment(ItemCommentQTO itemCommentQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemCommentQTO", itemCommentQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEMCOMMENT.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> deleteItemComment(Long commentId, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("ID", commentId);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.DELETE_ITEMCOMMENT.getActionName());
        return itemService.execute(request);
    }

    /**
     * 商品评价等级
     * @param itemCommentQTO
     * @return
     */
    public Response<List<ItemCommentDTO>> queryItemCommentGrade(ItemCommentQTO itemCommentQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemCommentQTO",itemCommentQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEMCOMMENTGRADE.getActionName());
        return itemService.execute(request);
    }

    /**
     * 分类统计商品评价等级
     * @param itemCommentQTO
     * @return
     */
    public Response<CountCommentDTO> countItemCommentGrade(ItemCommentQTO itemCommentQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemCommentQTO",itemCommentQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.COUNT_ITEMCOMMENTGRADE.getActionName());
        return itemService.execute(request);
    }


    public Response<List<ItemDTO>> queryGroupItem(ItemQTO itemQTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemQTO", itemQTO);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.QUERY_ITEM_GROUP_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> addGroupItem(Long groupId, Long sellerId, Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("groupId", groupId);
        request.setParam("itemId", itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.ADD_ITEM_GROUP_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> removeGroupItem(Long sellerId, Long itemId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("itemId", itemId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REMOVE_ITEM_GROUP_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Boolean> removeItemToDefaultGroup(Long sellerId, Long groupId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("groupId", groupId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.REMOVE_ITEM_G_TO_DEFAULT_ROUP_ACTION.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> trashItem(Long itemId, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.TRASH_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> recoveryItem(Long itemId, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.RECOVERY_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> batchTrashItem(List<Long> itemIdList, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemIdList", itemIdList);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BATCH_TRASH_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> batchRecoveryItem(List<Long> itemIdList, Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemIdList", itemIdList);
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.BATCH_RECOVERY_ITEM.getActionName());
        return itemService.execute(request);
    }

    public Response<Long> emptyItemRecycleBin(Long sellerId, String appKey) {
        Request request = new BaseRequest();
        request.setParam("sellerId", sellerId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.EMPTY_ITEM_RECYCLE_BIN.getActionName());
        return itemService.execute(request);
    }

    public Response<Void> updateSearchIndex(Long itemId,Long stockNum, Long frozenStockNum, String appKey) {

        Request request = new BaseRequest();
        request.setParam("itemId", itemId);
        request.setParam("stockNum", stockNum);
        request.setParam("appKey", appKey);
        request.setParam("frozenStockNum",frozenStockNum);
        request.setCommand(ActionEnum.UPDATE_SEARCH_INDEX.getActionName());
        return itemService.execute(request);
    }

    public Response<MopItemDTO> getMopItem(Long id, Long sellerId, Boolean needDetail, String appKey) {
        Request request = new BaseRequest();
        request.setParam("id", id);
        request.setParam("supplierId", sellerId);
        request.setParam("needDetail",needDetail);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.GET_MOP_ITEM.getActionName());
        return itemService.execute(request);
    }

    @Override
    public Response<Void> itemSearch(ItemDTO itemDTO, String appKey) {
        Request request = new BaseRequest();
        request.setParam("itemDTO",itemDTO);
        request.setParam("appKey",appKey);
        request.setCommand(ActionEnum.ITEM_SERACH.getActionName());
        return itemService.execute(request);
    }
}

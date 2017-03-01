package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ItemDAO {

    /**
     * 增加商品
     *
     * @param itemDO
     * @return
     */
    Long addItem(ItemDO itemDO);

    /**
     * 根据id获取商品
     *
     * @param id
     * @param bizCode
     * @return
     */
    ItemDO getItem(Long id, Long supplier_id, String bizCode);

    /**
     * 根据id删除商品
     *
     * @param id
     * @param bizCode
     * @return
     */
    int deleteItem(Long id, Long supplier_id, String bizCode);

    /**
     * 更新商品状态
     *
     * @param id
     * @param bizCode
     * @return
     */
    int updateItemState(Long id, Long supplier_id, String bizCode, Integer state);

    /**
     * 更新商品状态
     *
     * @param id
     * @param bizCode
     * @return
     */
    int removeItemSaleEnd(Long id, Long supplier_id, String bizCode);

    /**
     * 商品上下架更新,查询
     *
     * @return
     */
    List<ItemDO> selectItemSaleStateUp(ItemQTO itemQTO);

    List<ItemDO> selectItemSaleStateDown(ItemQTO itemQTO);

    void updateItemSaleStateUp(ItemQTO itemQTO);

    void updateItemSaleStateDown(ItemQTO itemQTO);


    /**
     * 更新商品信息
     *
     * @param itemDO
     * @return
     */
    int updateItem(ItemDO itemDO);

    int removeItemFromGroup(ItemDO itemDO);

    int removeItemToDefaultGroup(ItemDO itemDO);

    long countItem(ItemQTO itemQTO);

    /**
     * 返回商品列表
     *
     * @param itemQTO
     * @return
     */
    List<ItemDO> queryItem(ItemQTO itemQTO);

    /**
     * 判断item是否存在
     */
    Object isItemExist(ItemQTO itemQTO);

    int updateItemWithBlankDate(ItemDO itemDO);

    List<ItemDO> queryDistinctSellerId();

    int removeItemSaleBegin(Long id, Long supplierId, String bizCode);

    List<ItemDO> selectItemLoseShopId(ItemQTO query);

    Long updateItemLoseShopId(ItemQTO query);

    Long trashItem(ItemDO query);

    Long recoveryItem(ItemDO query);

    List<Long> queryCompositeItem(List<Long> idList);

    Long emptyRecycleBin(ItemDO query);

    void updateItemStockNum(ItemDO query);

    Map<Long, ItemDO> queryItemMap(ItemQTO itemQTO);

    Long updateIssueStatus(Long itemId, Integer status);

    Integer selectItemStatus(Long itemId);

    Long getItemCategoryId(Long itemId);

}
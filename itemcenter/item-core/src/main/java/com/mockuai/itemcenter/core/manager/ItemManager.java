package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ItemManager {


    Double getNormalItemGains(Long itemId, List<ItemSkuDTO> itemSkuDTOList, String appKey) throws ItemException;

    String getNormalItemIntervalGains(Long itemId, List<ItemSkuDTO> itemSkuDTOList, BigDecimal oneGains, String appKey) throws ItemException;

    Map getNormalIntervalGains(Long itemId, List<ItemSkuDTO> itemSkuDTOList, BigDecimal oneGains, String appKey) throws ItemException;

    /**
     * 添加商品
     *
     * @param itemDTO
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
    public ItemDTO addItem(ItemDTO itemDTO, String appKey) throws ItemException;

    /**
     * 添加商品
     *
     * @param itemDTO
     * @return
     * @throws ItemException
     */
    public boolean updateItem(ItemDTO itemDTO) throws ItemException;

    public boolean removeItemFromGroup(ItemDTO itemDTO) throws ItemException;

    public boolean removeItemToDefaultGroup(Long sellerId, Long groupId) throws ItemException;

    /**
     * 查看商品
     *
     * @param id
     * @return
     * @throws ItemException
     */
    public ItemDTO getItem(Long id, Long supplierId, String bizCode) throws ItemException;

    /**
     * 删除商品
     *
     * @param id
     * @return
     * @throws ItemException
     */
    public boolean deleteItem(Long id, Long supplierId, String bizCode, String appKey) throws ItemException;

    /**
     * 将商品移入回收站
     *
     * @param id
     * @return
     * @throws ItemException
     */
    public boolean removeItem(Long id, Long supplierId, String bizCode) throws ItemException;

    public boolean removeItemSaleEnd(Long id, Long supplierId, String bizCode) throws ItemException;

    public boolean removeItemSaleBegin(Long id, Long supplierId, String bizCode) throws ItemException;


    /**
     * 查询商品列表
     *
     * @param itemQTO
     * @return
     * @throws ItemException
     */
    public List<ItemDTO> queryItem(ItemQTO itemQTO) throws ItemException;

    public Long countGroupItem(ItemQTO itemQTO) throws ItemException;

    /**
     * 技术商品
     *
     * @param itemQTO
     * @return
     * @throws ItemException
     */
    public Long countItem(ItemQTO itemQTO) throws ItemException;

    /**
     * 商品上下架
     *
     * @return
     * @throws ItemException
     */
    public List<ItemDTO> selectItemSaleUp(ItemQTO itemQTO) throws ItemException;

    public List<ItemDTO> selectItemSaleDown(ItemQTO itemQTO) throws ItemException;

    public void updateItemSaleUp(ItemQTO itemQTO) throws ItemException;

    public void updateItemSaleDown(ItemQTO itemQTO) throws ItemException;

    /**
     * 商品下架
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    public int withdrawItem(Long itemId, Long supplierId, String bizCode) throws ItemException;

    /**
     * 上架商品
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    public int upItem(Long itemId, Long supplierId, String bizCode) throws ItemException;


    /**
     * 商品预售
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    public int preSaleItem(Long itemId, Long supplierId, String bizCode) throws ItemException;


    /**
     * 使商品处于因sku模板修改造成的不可用状态
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    int skuInvalidItem(Long itemId, Long supplierId, String bizCode) throws ItemException;

    /**
     * 商品解冻
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    public int thawItem(Long itemId, Long supplierId, String bizCode) throws ItemException;

    /**
     * 冻结商品
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    public int freezeItem(Long itemId, Long supplierId, String bizCode) throws ItemException;

    /**
     * 使商品失效
     *
     * @param itemId
     * @param supplierId
     * @return
     * @throws ItemException
     */
    public int disableItem(Long itemId, Long supplierId, String bizCode) throws ItemException;

    /**
     * 上传商品详情内容
     *
     * @param itemDesc
     * @return
     * @throws ItemException
     */
    public String uploadItemDesc(String itemDesc) throws ItemException;

    /**
     * 复合条件查询item是否存在 某些场景需要判断商品是否存在 比如删除品牌时候
     *
     * @param itemQTO
     * @return
     * @throws ItemException
     */
    public Boolean isItemExist(ItemQTO itemQTO) throws ItemException;

    /**
     * 区别于updateItem方法中忽略null，该方法中itemDTO中日期字段为null时表示置空数据库中字段
     *
     * @param itemDTO
     * @return
     */
    public Boolean updateItemWithBlankDate(ItemDTO itemDTO) throws ItemException;


    /**
     * 查询到itemDTO后拼接URl并返回
     *
     * @param itemQTO
     * @return
     */
    List<ItemDTO> queryItemContainsUrl(ItemQTO itemQTO) throws ItemException;

    List<ItemDTO> queryDistinctSellerId();

    List<ItemDTO> selectItemLoseShopId(String bizCode) throws ItemException;

    Long updateItemLoseShopId(ItemQTO query) throws ItemException;

    Long trashItem(Long itemId, Long sellerId, String bizCode) throws ItemException;

    Long recoveryItem(Long itemId, Long sellerId, String bizCode) throws ItemException;

    Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException;

    void updateItemStockNum(ItemDTO itemDTO) throws ItemException;

    boolean needIndex(ItemDTO itemDTO);


    Map<Long, ItemDTO> queryItemMap(List<Long> idList, String bizCode);

    List<Long> queryCompositeItem(List<Long> idList) throws ItemException;

    Integer selectItemStatus(Long itemId);

    Long updateIssueStatus(Long itemId, Integer status) throws ItemException;
}

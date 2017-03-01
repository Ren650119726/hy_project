package com.mockuai.itemcenter.core.manager;

import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by zengzhangqiang on 1/5/16.
 */
public interface HigoManager {
    /**
     * 获取商品跨境扩展信息
     * @param itemId
     * @param sellerId
     * @param appKey
     * @return
     * @throws ItemException
     */
    public ItemHigoInfoDTO getItemHigoInfo(Long itemId, Long sellerId, String appKey) throws ItemException;

    /**
     * 批量获取商品跨境扩展信息
     * @param itemId
     * @param sellerId
     * @param appKey
     * @return
     * @throws ItemException
     */
    public List<ItemHigoInfoDTO> getItemHigoInfoList(List<Long> itemIdList,
                                                     Long sellerId, String appKey) throws ItemException;

    /**
     * 新增商品跨境扩展信息
     * @param itemHigoInfoDTO
     * @param appKey
     * @return
     * @throws ItemException
     */
    public ItemHigoInfoDTO addItemHigoInfo(ItemHigoInfoDTO itemHigoInfoDTO, String appKey) throws ItemException;

    /**
     * 更新商品跨境扩展信息
     * @param itemHigoInfoDTO
     * @param appKey
     * @return
     * @throws ItemException
     */
    public boolean updateItemHigoInfo(ItemHigoInfoDTO itemHigoInfoDTO, String appKey) throws ItemException;

    SkuHigoInfoDTO getSkuHigoInfo(Long skuId,Long sellerId,String appKey) throws ItemException;

    public SkuHigoInfoDTO  addSkuHigoInfo(SkuHigoInfoDTO skuHigoInfoDTO, String appKey) throws ItemException;

    public boolean updateSkuHigoInfo(SkuHigoInfoDTO skuHigoInfoDTO, String appKey) throws ItemException;

    public List<SkuHigoInfoDTO> getSkuHigoInfoList(List<Long> skuIdList, Long sellerId, String appKey) throws ItemException;
}

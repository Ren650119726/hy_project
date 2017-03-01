package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.core.dao.*;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.domain.ItemSkuDO;
import com.mockuai.itemcenter.core.domain.ItemSuitDO;
import com.mockuai.itemcenter.core.domain.RItemSuitDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemImageManager;
import com.mockuai.itemcenter.core.manager.ItemSuitManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by yindingyu on 15/12/4.
 */

@Service
public class ItemSuitManagerImpl implements ItemSuitManager {

    private static final Logger log = LoggerFactory.getLogger(ItemSuitManagerImpl.class);

    @Resource
    private RItemSuitDAO rItemSuitDAO;

    @Resource
    private ItemDAO itemDAO;

    @Resource
    private ItemSkuDAO itemSkuDAO;

    @Resource
    private ItemSuitDAO itemSuitDAO;


    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemImageManager itemImageManager;


    @Override
    public Long addItemSuit(RItemSuitDTO rItemSuitDTO) {

        RItemSuitDO rItemSuitDO = new RItemSuitDO();

        BeanUtils.copyProperties(rItemSuitDTO, rItemSuitDO);

        return rItemSuitDAO.addRItemSuit(rItemSuitDO);
    }


    public Long addItemSuit(ItemSuitDTO itemSuitDTO, List<RItemSuitDTO> rItemSuitDTOs) throws ItemException {

        if (itemSuitDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemSuitDTO不能为空");
        }

        if (rItemSuitDTOs == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "rItemSuitDTOs不能为空");
        }

        ItemSuitDO itemSuitDO = new ItemSuitDO();
        BeanUtils.copyProperties(itemSuitDTO, itemSuitDO);

        itemSuitDAO.addItemSuit(itemSuitDO);

        for (RItemSuitDTO rItemSuitDTO : rItemSuitDTOs) {
            addItemSuit(rItemSuitDTO);
        }

        return 1L;
    }

    @Override
    public Long addItemSuit(List<RItemSuitDTO> rItemSuitDTOs) {
        for (RItemSuitDTO rItemSuitDTO : rItemSuitDTOs) {
            addItemSuit(rItemSuitDTO);
        }

        return 1L;
    }

    @Override
    public List<ItemDTO> querySuit(ItemQTO itemQTO) throws ItemException {

        itemQTO.setItemType(DBConst.SUIT_ITEM.getCode());
        List<ItemDO> itemDOList = itemDAO.queryItem(itemQTO);
        List<ItemDTO> itemDTOList = Lists.newArrayList();

        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();

        for (ItemDO itemDO : itemDOList) {

            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(itemDO, itemDTO);


            try {
                itemDTO = getSuitItemIfAvailable(itemDTO);
                itemDTOList.add(itemDTO);
            } catch (ItemException e) {
                //查询的套装非法，将错误降级处理
                System.currentTimeMillis();
            }



        }

        Collections.sort(itemDTOList, new SuitComparator());

        return itemDTOList;
    }

    /**
     * 获取正常状态的套装，子商品未被删除并且处于上架状态，库存不为0，如果不正常，将套装置为失效状态
     *
     * @param itemDTO
     * @return
     * @throws ItemException 套装非法
     */
    private ItemDTO getSuitItemIfAvailable(ItemDTO itemDTO) throws ItemException {

        if (itemDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "itemDTO不能为空");
        }

        if (itemDTO.getItemType() == null || itemDTO.getItemType() != DBConst.SUIT_ITEM.getCode()) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "不是套装商品");
        }


        try {

            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();

            itemSkuQTO.setItemId(itemDTO.getId());
            itemSkuQTO.setBizCode(itemDTO.getBizCode());
            itemSkuQTO.setSellerId(itemDTO.getSellerId());

            List<ItemSkuDO> itemSkuDOList = itemSkuDAO.queryItemSku(itemSkuQTO);

            List<ItemSkuDTO> itemSkuDTOList = Lists.newArrayList();

            for (ItemSkuDO itemSkuDO : itemSkuDOList) {
                ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
                BeanUtils.copyProperties(itemSkuDO, itemSkuDTO);
                itemSkuDTOList.add(itemSkuDTO);
            }


            itemDTO.setItemSkuDTOList(itemSkuDTOList);

            List<RItemSuitDO> rItemSuitDOList = rItemSuitDAO.queryBySuitId(itemDTO.getId(), itemDTO.getSellerId(), itemDTO.getBizCode());

            List<ItemDTO> subItemList = Lists.newArrayList();

            Long stockNum = itemSkuDTOList.get(0).getStockNum();

            for (RItemSuitDO rItemSuitDO : rItemSuitDOList) {


                ItemSkuDO itemSkuDO = itemSkuDAO.getItemSku(rItemSuitDO.getSubItemSkuId(), rItemSuitDO.getSellerId(), rItemSuitDO.getBizCode());

                if (itemSkuDO == null) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下子商品sku已经被删除");
                }

                if (itemSkuDO.getStockNum() <= 0) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_STATE_E_STOCK_INSUFFICIENT, "套装下子商品库存不足");
                }

                stockNum = itemSkuDO.getStockNum() < stockNum ? itemSkuDO.getStockNum() : stockNum;

                ItemDO itemDO1 = itemDAO.getItem(rItemSuitDO.getSubItemId(), rItemSuitDO.getSellerId(), itemDTO.getBizCode());

                if (itemDO1 == null) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下子商品已经被删除");
                }

                if (itemDO1.getItemStatus() != ItemStatus.ON_SALE.getStatus()) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下子商品已经下架");
                }

                ItemDTO itemDTO1 = new ItemDTO();

                BeanUtils.copyProperties(itemDO1, itemDTO1);

                ItemSkuDTO itemSkuDTO = fillSkuImage(itemSkuDO);

                itemDTO1.setItemSkuDTOList(Lists.newArrayList(itemSkuDTO));
                subItemList.add(itemDTO1);

            }

            itemDTO.getItemSkuDTOList().get(0).setStockNum(stockNum);

            itemDTO.setSubItemList(subItemList);
        } catch (ItemException e) {

            try {
                //如果套装非法，则使其失效
                disableItemSuit(itemDTO.getId(), itemDTO.getSellerId(), itemDTO.getBizCode());
            } catch (ItemException ex) {

            }

            //将异常重新抛出，上层方法捕捉到异常认为套装非法
            throw e;
        }


        return itemDTO;

    }

    @Override
    public List<ItemDTO> querySuitOfItem(ItemQTO itemQTO) throws ItemException {

        RItemSuitDO rItemSuitDO = new RItemSuitDO();

        rItemSuitDO.setSubItemId(itemQTO.getId());
        rItemSuitDO.setBizCode(itemQTO.getBizCode());
        rItemSuitDO.setSellerId(itemQTO.getSellerId());

        List<RItemSuitDO> rItemSuitDOList = rItemSuitDAO.queryRItemSuit(rItemSuitDO);

        if (rItemSuitDOList != null && rItemSuitDOList.size() > 0) {

            Set<Long> idSet = Sets.newHashSet();
            for (RItemSuitDO rItemSuitDOx : rItemSuitDOList) {
                Long suitId = rItemSuitDOx.getSuitId();
                idSet.add(suitId);
            }

            List<Long> idList = Lists.newArrayList(idSet);
            itemQTO.setIdList(idList);
            itemQTO.setId(null);

            List<ItemDTO> suitList = querySuit(itemQTO);

            //按照优惠力度排序
            Collections.sort(suitList, new SuitComparator());

            return suitList;
        }

        return Lists.newArrayList();
    }

    @Override
    public List<ItemDTO> querySubItems(ItemQTO itemQTO) throws ItemException {

        List<ItemDTO> subItemList = Lists.newArrayList();

        List<RItemSuitDO> rItemSuitDTOList = rItemSuitDAO.queryBySuitId(itemQTO.getId(), itemQTO.getSellerId(), itemQTO.getBizCode());


        for (RItemSuitDO rItemSuitDO : rItemSuitDTOList) {


            ItemSkuDO itemSkuDO = itemSkuDAO.getItemSku(rItemSuitDO.getSubItemSkuId(), rItemSuitDO.getSellerId(), rItemSuitDO.getBizCode());


            ItemDO itemDO1 = itemDAO.getItem(rItemSuitDO.getSubItemId(), rItemSuitDO.getSellerId(), itemQTO.getBizCode());

            try {


                if (itemSkuDO == null) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下属sku已经不存在");
                }

                if (itemDO1 == null) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下属商品已经不存在");
                }

            } catch (ItemException e) {  //如果下属商品不存在等异常发生，针对管理后台查询和其他接口做不同处理

                if (itemQTO.getItemStatus() != null && itemQTO.getItemStatus() == ItemStatus.ON_SALE.getStatus()) {

                    //使套装活动失效

                    try {

                        disableItemSuit(itemQTO.getId(), itemQTO.getSellerId(), itemQTO.getBizCode());
                    } catch (ItemException ex) {

                    }

                    //异常重新抛出
                    throw e;

                } else {
                    ItemDTO itemDTO = new ItemDTO();

                    itemDTO.setItemName("商品已删除");

                    ItemSkuDTO itemSkuDTO = new ItemSkuDTO();

                    itemSkuDTO.setStockNum(0L);
                    itemSkuDTO.setPromotionPrice(0L);
                    itemSkuDTO.setWirelessPrice(0L);
                    itemSkuDTO.setMarketPrice(0L);

                    itemDTO.setItemSkuDTOList(Lists.newArrayList(itemSkuDTO));

                    subItemList.add(itemDTO);

                    continue;
                }
            }

            ItemDTO itemDTO1 = new ItemDTO();

            BeanUtils.copyProperties(itemDO1, itemDTO1);

            ItemSkuDTO itemSkuDTO = fillSkuImage(itemSkuDO);

            BeanUtils.copyProperties(itemSkuDO, itemSkuDTO);

            //如果查询在售的套装，要对其子商品进行下架和库存的验证，如果不通过，同时结束套装活动
            if (itemQTO.getItemStatus() != null && itemQTO.getItemStatus() == ItemStatus.ON_SALE.getStatus()) {
                if (itemSkuDO.getStockNum() <= 0) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下属商品已经库存不足");
                }

                if (itemDO1.getItemStatus() != ItemStatus.ON_SALE.getStatus()) {
                    throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "套装下属商品已下架");
                }
            }

            itemDTO1.setItemSkuDTOList(Lists.newArrayList(itemSkuDTO));

            subItemList.add(itemDTO1);

        }

        return subItemList;

    }

    @Override
    public void disableItemSuit(Long itemId, Long sellerId, String bizCode) throws ItemException {

        Long result = itemSuitDAO.disableItemSuit(itemId, sellerId, bizCode);

        itemDAO.updateItemState(itemId, sellerId, bizCode, ItemStatus.WITHDRAW.getStatus());

        if (result <= 0) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "使套装失效失败");
        }
    }


    @Override
    public void increaseSuitSalesVolume(Long itemId, Long sellerId, Long num) throws ItemException {

        ItemSuitDO itemSuitDO = new ItemSuitDO();
        itemSuitDO.setSuitId(itemId);
        itemSuitDO.setSellerId(sellerId);
        itemSuitDO.setSaleVolume(num);
        itemSuitDAO.increaseSuitSalesVolume(itemSuitDO);
    }

    @Override
    public ItemSuitDTO getSuitExtraInfo(Long itemId, Long sellerId, String bizCode) {


        ItemSuitDO itemSuitDO1 = itemSuitDAO.getItemSuitByItemId(itemId, sellerId, bizCode);

        if (itemSuitDO1 == null) {
            return null;
        }

        ItemSuitDTO itemSuitDTO = new ItemSuitDTO();

        BeanUtils.copyProperties(itemSuitDO1, itemSuitDTO);

        return itemSuitDTO;
    }

    @Override
    public List<ItemSuitDTO> queryItemSuitDiscount(ItemSuitQTO itemSuitQTO) throws ItemException {

        List<ItemSuitDO> itemSuitDOList = itemSuitDAO.queryItemSuitByLifecycle(itemSuitQTO);

        List<ItemSuitDTO> itemSuitDTOList = Lists.newArrayList();

        if (null == itemSuitDOList) {
            return Collections.emptyList();
        }

        for (ItemSuitDO itemSuitDO : itemSuitDOList) {

            ItemSuitDTO itemSuitDTO = new ItemSuitDTO();
            BeanUtils.copyProperties(itemSuitDO, itemSuitDTO);

            Long sellerId = itemSuitDO.getSellerId();
            String bizCode = itemSuitDO.getBizCode();
            Long itemId = itemSuitDO.getSuitId();
            Long itemSkuId = itemSuitDO.getSuitSkuId();

            ItemSkuDO itemSkuDO = itemSkuDAO.getItemSku(itemSkuId, sellerId, itemSuitQTO.getBizCode());

            if (itemSkuDO != null) {
                itemSuitDTO.setPromotionPrice(itemSkuDO.getPromotionPrice());
            }

            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setId(itemId);
            itemQTO.setSellerId(sellerId);
            itemQTO.setBizCode(bizCode);

            List<ItemDTO> itemDTOList = querySubItems(itemQTO);

            Long stock = itemSkuDO.getStockNum();

            for (ItemDTO itemDTO : itemDTOList) {

                if (itemDTO.getItemSkuDTOList() != null && itemDTO.getItemSkuDTOList().size() > 0) {
                    Long stockNum = itemDTO.getItemSkuDTOList().get(0).getStockNum();
                    stock = stockNum < stock ? stockNum : stock;
                } else {
                    log.warn("套装内子商品的sku已失效 item_uid: {}  biz_code: {}", "" + itemDTO.getSellerId() + "_" + itemDTO.getId(), itemDTO.getBizCode());
                }
            }

            itemSuitDTO.setSubItemList(itemDTOList);

            itemSuitDTO.setStockNum(stock);

            itemSuitDTOList.add(itemSuitDTO);
        }

        return itemSuitDTOList;
    }

    private ItemSkuDTO fillSkuImage(ItemSkuDO itemSkuDO) throws ItemException {


        //拷贝基础属性
        ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
        BeanUtils.copyProperties(itemSkuDO, itemSkuDTO);

        //添加sku属性列表
        SkuPropertyQTO skuPropertyQTO = new SkuPropertyQTO();
        skuPropertyQTO.setSkuId(itemSkuDO.getId());
        skuPropertyQTO.setSellerId(itemSkuDO.getSellerId());
        List<SkuPropertyDTO> skuPropertyDTOList = skuPropertyManager.querySkuProperty(skuPropertyQTO);

        itemSkuDTO.setSkuPropertyDTOList(skuPropertyDTOList);


        //查询所有图片
        ItemImageQTO itemImageQTO = new ItemImageQTO();
        itemImageQTO.setItemId(itemSkuDO.getItemId());
        itemImageQTO.setSellerId(itemSkuDO.getSellerId());
        itemImageQTO.setBizCode(itemSkuDO.getBizCode());


        List<ItemImageDTO> itemImageDTOList = itemImageManager.queryItemImage(itemImageQTO);

        //匹配sku的一张
        Map<Long, ItemImageDTO> skuImageMap = new HashMap<Long, ItemImageDTO>();
        for (ItemImageDTO itemImageDTO : itemImageDTOList) {
            if (itemImageDTO.getPropertyValueId() != null && itemImageDTO.getPropertyValueId().longValue() > 0) {
                //商品SKU图片
                skuImageMap.put(itemImageDTO.getPropertyValueId(), itemImageDTO);
            }
        }

        if (skuPropertyDTOList != null) {
            for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
                if (skuPropertyDTO.getPropertyValueId() != null
                        && skuImageMap.containsKey(skuPropertyDTO.getPropertyValueId())) {
                    itemSkuDTO.setImageUrl(skuImageMap.get(skuPropertyDTO.getPropertyValueId()).getImageUrl());
                }
            }
        }

        return itemSkuDTO;

    }

}

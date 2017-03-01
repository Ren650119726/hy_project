package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.core.dao.*;
import com.mockuai.itemcenter.core.domain.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSettlementConfigManager;
import com.mockuai.itemcenter.core.manager.ShopManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by yindingyu on 16/1/19.
 */
@Service
public class ItemSettlementConfigManagerImpl implements ItemSettlementConfigManager {

    @Resource
    private ItemSettlementConfigDAO itemSettlementConfigDAO;

    @Resource
    private ItemSettlementConditionDAO itemSettlementConditionDAO;

    @Resource
    private RShopSettlementDAO rShopSettlementDAO;

    @Resource
    private RCategorySettlementDAO rCategorySettlementDAO;

    @Resource
    private ItemCategoryDAO itemCategoryDAO;

    @Resource
    private ShopManager shopManager;

    @Resource
    private ItemDAO itemDAO;


    private static final Logger log = LoggerFactory.getLogger(ItemSettlementConfigManagerImpl.class);

    @Override
    public ItemSettlementConfigDTO getItemSettlementConfig(Long id, String bizCode) throws ItemException {

        ItemSettlementConfigDO queryz = new ItemSettlementConfigDO();
        queryz.setId(id);
        queryz.setBizCode(bizCode);

        ItemSettlementConfigDO itemSettlementConfigDO = itemSettlementConfigDAO.getItemSettlementConfig(queryz);

        ItemSettlementConfigDTO itemSettlementConfigDTO = new ItemSettlementConfigDTO();

        if (itemSettlementConfigDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "查询不到对应的结算配置");
        }

        BeanUtils.copyProperties(itemSettlementConfigDO, itemSettlementConfigDTO);


        /**
         * 拼装结算条件
         */

        ItemSettlementConditionQTO itemSettlementConditionQTO = new ItemSettlementConditionQTO();
        itemSettlementConditionQTO.setBizCode(bizCode);
        itemSettlementConditionQTO.setParentId(id);

        List<ItemSettlementConditionDO> itemSettlementConditionDOList = itemSettlementConditionDAO.queryItemSettlementCondition(itemSettlementConditionQTO);

        List<ItemSettlementConditionDTO> itemSettlementConditionDTOList = Lists.newArrayListWithCapacity(itemSettlementConditionDOList.size());

        if (itemSettlementConditionDOList != null && itemSettlementConditionDOList.size() > 0) {
            for (ItemSettlementConditionDO itemSettlementConditionDO : itemSettlementConditionDOList) {

                ItemSettlementConditionDTO itemSettlementConditionDTO = new ItemSettlementConditionDTO();
                BeanUtils.copyProperties(itemSettlementConditionDO, itemSettlementConditionDTO);
                itemSettlementConditionDTOList.add(itemSettlementConditionDTO);
            }
        }

        itemSettlementConfigDTO.setConditionList(itemSettlementConditionDTOList);


        if (itemSettlementConfigDTO.getScope().intValue() == 2) {

            /**
             * 匹配部分类目的规则
             */
            RCategorySettlementQTO query = new RCategorySettlementQTO();
            query.setBizCode(bizCode);
            query.setSettlementId(itemSettlementConfigDTO.getId());

            List<RCategorySettlementDO> rCategorySettlementDOList = rCategorySettlementDAO.queryRCategorySettlement(query);

            if (rCategorySettlementDOList != null && rCategorySettlementDOList.size() > 0) {

                List<Long> idList = Lists.newArrayListWithCapacity(rCategorySettlementDOList.size());

                for (RCategorySettlementDO rCategorySettlementDO : rCategorySettlementDOList) {
                    idList.add(rCategorySettlementDO.getCategoryId());
                }

                ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
                itemCategoryQTO.setBizCode(bizCode);
                itemCategoryQTO.setIdList(idList);

                List<ItemCategoryDO> itemCategoryDOList = itemCategoryDAO.queryItemCategory(itemCategoryQTO);

                List<ItemCategoryDTO> itemCategoryDTOList = Lists.newArrayListWithCapacity(itemCategoryDOList.size());

                for (ItemCategoryDO itemCategoryDO : itemCategoryDOList) {
                    ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();
                    BeanUtils.copyProperties(itemCategoryDO, itemCategoryDTO);
                    itemCategoryDTOList.add(itemCategoryDTO);
                }

                itemSettlementConfigDTO.setCategoryList(itemCategoryDTOList);
            }

        } else if (itemSettlementConfigDTO.getScope().intValue() == 4) {

            /**
             * 匹配部分店铺的规则
             */
            RShopSettlementQTO query = new RShopSettlementQTO();
            query.setBizCode(bizCode);
            query.setSettlementId(itemSettlementConfigDTO.getId());

            List<RShopSettlementDO> rShopSettlementDOList = rShopSettlementDAO.queryRShopSettlement(query);

            List<RShopSettlementDTO> rShopSettlementDTOList = Lists.newArrayListWithCapacity(rShopSettlementDOList.size());

            for (RShopSettlementDO rShopSettlementDO : rShopSettlementDOList) {

                RShopSettlementDTO rShopSettlementDTO = new RShopSettlementDTO();
                BeanUtils.copyProperties(rShopSettlementDO, rShopSettlementDTO);
                rShopSettlementDTOList.add(rShopSettlementDTO);
            }

            itemSettlementConfigDTO.setShopList(rShopSettlementDTOList);
        }

        return itemSettlementConfigDTO;

    }

    @Override
    public Long enableItemSettlementConfig(Long id, String bizCode) throws ItemException {

        ItemSettlementConfigDO query = new ItemSettlementConfigDO();
        query.setId(id);
        query.setBizCode(bizCode);

        ItemSettlementConfigDO itemSettlementConfigDO = itemSettlementConfigDAO.getItemSettlementConfig(query);

        //根据配置的适用范围，寻找与其冲突的配置项，如果有，则启用失败

        Integer scope = itemSettlementConfigDO.getScope();

        switch (scope) {

            case 1: //适用于全部类目

                ItemSettlementConfigQTO query1 = new ItemSettlementConfigQTO();
                query1.setBizCode(bizCode);
                query1.setScope(1);
                query1.setEnable(1);

                List<ItemSettlementConfigDO> scope1 = itemSettlementConfigDAO.queryItemSettlementConfig(query1);

                if (!CollectionUtils.isEmpty(scope1)) {

                    ItemSettlementConfigDO conflict = scope1.get(0);

                    throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "与配置 " + conflict.getConfigName() + "（" + conflict.getId() + "）冲突，请将其先关闭后再尝试开启本配置");
                }


                break;
            case 2:

                RCategorySettlementQTO queryC = new RCategorySettlementQTO();
                queryC.setBizCode(bizCode);
                queryC.setSettlementId(id);

                List<RCategorySettlementDO> rCategorySettlementDOList = rCategorySettlementDAO.queryRCategorySettlement(queryC);

                if (!CollectionUtils.isEmpty(rCategorySettlementDOList)) {

                    List<Long> categoryIdList = Lists.newArrayListWithCapacity(rCategorySettlementDOList.size());

                    for (RCategorySettlementDO rCategorySettlementDO : rCategorySettlementDOList) {
                        categoryIdList.add(rCategorySettlementDO.getCategoryId());
                    }

                    RCategorySettlementQTO queryRC = new RCategorySettlementQTO();
                    queryRC.setBizCode(bizCode);
                    queryRC.setCategoryIdList(categoryIdList);

                    List<RCategorySettlementDO> categorySettlementDOs = rCategorySettlementDAO.queryRCategorySettlement(queryRC);

                    if (!CollectionUtils.isEmpty(categorySettlementDOs)) {

                        List<Long> idList = Lists.newArrayListWithCapacity(categorySettlementDOs.size());

                        for (RCategorySettlementDO rCategorySettlementDO : categorySettlementDOs) {
                            idList.add(rCategorySettlementDO.getSettlementId());
                        }

                        ItemSettlementConfigQTO query2 = new ItemSettlementConfigQTO();
                        query2.setBizCode(bizCode);
                        query2.setEnable(1);
                        query2.setScope(2);
                        query2.setIdList(idList);


                        List<ItemSettlementConfigDO> scope2 = itemSettlementConfigDAO.queryItemSettlementConfig(query2);

                        if (!CollectionUtils.isEmpty(scope2)) {

                            ItemSettlementConfigDO conflict = scope2.get(0);

                            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "与配置 " + conflict.getConfigName() + "（" + conflict.getId() + "）冲突，请将其先关闭或修改后再尝试开启本配置");
                        }
                    }

                }

                break;


            case 3:  //适用于全部店铺

                ItemSettlementConfigQTO query3 = new ItemSettlementConfigQTO();
                query3.setBizCode(bizCode);
                query3.setScope(3);
                query3.setEnable(1);

                List<ItemSettlementConfigDO> scope3 = itemSettlementConfigDAO.queryItemSettlementConfig(query3);

                if (!CollectionUtils.isEmpty(scope3)) {

                    ItemSettlementConfigDO conflict = scope3.get(0);

                    throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "与配置 " + conflict.getConfigName() + "（" + conflict.getId() + "）冲突，请将其先关闭后再尝试开启本配置");
                }


                break;
            case 4:

                RShopSettlementQTO queryS = new RShopSettlementQTO();
                queryS.setBizCode(bizCode);
                queryS.setSettlementId(id);

                List<RShopSettlementDO> rShopSettlementDOList = rShopSettlementDAO.queryRShopSettlement(queryS);

                if (!CollectionUtils.isEmpty(rShopSettlementDOList)) {

                    List<Long> sellerIdList = Lists.newArrayListWithCapacity(rShopSettlementDOList.size());

                    for (RShopSettlementDO rShopSettlementDO : rShopSettlementDOList) {
                        sellerIdList.add(rShopSettlementDO.getSellerId());
                    }

                    RShopSettlementQTO queryRS = new RShopSettlementQTO();
                    queryRS.setBizCode(bizCode);
                    queryRS.setSellerIdList(sellerIdList);

                    List<RShopSettlementDO> shopSettlementDOs = rShopSettlementDAO.queryRShopSettlement(queryRS);

                    if (!CollectionUtils.isEmpty(shopSettlementDOs)) {

                        List<Long> idList = Lists.newArrayListWithCapacity(shopSettlementDOs.size());

                        for (RShopSettlementDO rShopSettlementDO : shopSettlementDOs) {
                            idList.add(rShopSettlementDO.getSettlementId());
                        }

                        ItemSettlementConfigQTO query2 = new ItemSettlementConfigQTO();
                        query2.setBizCode(bizCode);
                        query2.setEnable(1);
                        query2.setScope(4);
                        query2.setIdList(idList);


                        List<ItemSettlementConfigDO> scope4 = itemSettlementConfigDAO.queryItemSettlementConfig(query2);

                        if (!CollectionUtils.isEmpty(scope4)) {

                            ItemSettlementConfigDO conflict = scope4.get(0);

                            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "与配置 " + conflict.getConfigName() + "（" + conflict.getId() + "）冲突，请将其先关闭或修改后再尝试开启本配置");
                        }
                    }

                }

                break;

            default:
                throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "不支持的适用范围类型");
        }


        return itemSettlementConfigDAO.enableItemSettlementConfig(query);
    }

    @Override
    public Long disableItemSettlementConfig(Long id, String bizCode) throws ItemException {
        ItemSettlementConfigDO query = new ItemSettlementConfigDO();
        query.setId(id);
        query.setBizCode(bizCode);
        return itemSettlementConfigDAO.disableItemSettlementConfig(query);
    }

    @Override
    public Long deleteItemSettlementConfig(Long id, String bizCode) throws ItemException {

        itemSettlementConditionDAO.deleteItemSettlementConditionByConfig(id, bizCode);

        rShopSettlementDAO.deleteRShopSettlementByConfig(id, bizCode);

        rCategorySettlementDAO.deleteRCategorySettlementByConfig(id, bizCode);

        ItemSettlementConfigDO query = new ItemSettlementConfigDO();
        query.setId(id);
        query.setBizCode(bizCode);
        return itemSettlementConfigDAO.deleteItemSettlementConfig(query);
    }

    @Override
    public List<ItemSettlementConfigDTO> queryItemSettlementConfig(ItemSettlementConfigQTO itemSettlementConfigQTO) throws ItemException {

        List<ItemSettlementConfigDO> itemSettlementConfigDOList = itemSettlementConfigDAO.queryItemSettlementConfig(itemSettlementConfigQTO);

        List<ItemSettlementConfigDTO> itemSettlementConfigDTOList = Lists.newArrayListWithCapacity(itemSettlementConfigDOList.size());

        if (!CollectionUtils.isEmpty(itemSettlementConfigDOList)) {

            for (ItemSettlementConfigDO itemSettlementConfigDO : itemSettlementConfigDOList) {

                ItemSettlementConfigDTO itemSettlementConfigDTO = new ItemSettlementConfigDTO();
                BeanUtils.copyProperties(itemSettlementConfigDO, itemSettlementConfigDTO);
                itemSettlementConfigDTOList.add(itemSettlementConfigDTO);
            }
        }

        return itemSettlementConfigDTOList;
    }

    @Override
    public Long addItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO, String appKey) throws ItemException {

        String bizCode = itemSettlementConfigDTO.getBizCode();

        ItemSettlementConfigDO itemSettlementConfigDO = new ItemSettlementConfigDO();

        BeanUtils.copyProperties(itemSettlementConfigDTO, itemSettlementConfigDO);

        Long id = itemSettlementConfigDAO.addItemSettlementConfig(itemSettlementConfigDO);


        verifyConditions(itemSettlementConfigDTO.getConditionList());

        for (ItemSettlementConditionDTO itemSettlementConditionDTO : itemSettlementConfigDTO.getConditionList()) {

            ItemSettlementConditionDO itemSettlementConditionDO = new ItemSettlementConditionDO();

            BeanUtils.copyProperties(itemSettlementConditionDTO, itemSettlementConditionDO);

            itemSettlementConditionDO.setBizCode(bizCode);
            itemSettlementConditionDO.setParentId(id);

            itemSettlementConditionDAO.addItemSettlementCondition(itemSettlementConditionDO);
        }


        if (itemSettlementConfigDTO.getScope() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "scope不能为空");
        } else if (itemSettlementConfigDTO.getScope() == 2) {

            if (CollectionUtils.isEmpty(itemSettlementConfigDTO.getCategoryIdList())) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "categoryIdList不能为空");
            }

            for (Long categoryId : itemSettlementConfigDTO.getCategoryIdList()) {

                RCategorySettlementDO rCategorySettlementDO = new RCategorySettlementDO();

                ItemCategoryDO itemCategoryDO = itemCategoryDAO.getItemCategory(categoryId);
                rCategorySettlementDO.setBizCode(bizCode);
                rCategorySettlementDO.setSettlementId(id);
                rCategorySettlementDO.setCategoryId(categoryId);
                rCategorySettlementDO.setCategoryLevel(itemCategoryDO.getCateLevel());

                rCategorySettlementDAO.addRCategorySettlement(rCategorySettlementDO);
            }

        } else if (itemSettlementConfigDTO.getScope() == 4) {

            if (CollectionUtils.isEmpty(itemSettlementConfigDTO.getSellerIdList())) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "shopIdList不能为空");
            }

            for (Long sellerId : itemSettlementConfigDTO.getSellerIdList()) {

                RShopSettlementDO rShopSettlementDO = new RShopSettlementDO();

                ShopDTO shopDTO = shopManager.getShop(sellerId, appKey);

                if (shopDTO == null) {
                    continue;
                }

                rShopSettlementDO.setBizCode(bizCode);
                rShopSettlementDO.setSettlementId(id);
                rShopSettlementDO.setSellerId(sellerId);
                rShopSettlementDO.setShopId(shopDTO.getId());

                rShopSettlementDAO.addRShopSettlement(rShopSettlementDO);
            }
        }


        return id;
    }

    @Override
    public Long updateItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO, String appKey) throws ItemException {

        String bizCode = itemSettlementConfigDTO.getBizCode();
        Long id = itemSettlementConfigDTO.getId();

        verifyConditions(itemSettlementConfigDTO.getConditionList());

        ItemSettlementConfigDO itemSettlementConfigDO = new ItemSettlementConfigDO();

        BeanUtils.copyProperties(itemSettlementConfigDTO, itemSettlementConfigDO);

        Long result = itemSettlementConfigDAO.updateItemSettlementConfig(itemSettlementConfigDO);

        itemSettlementConditionDAO.deleteItemSettlementConditionByConfig(id, bizCode);

        rShopSettlementDAO.deleteRShopSettlementByConfig(id, bizCode);

        rCategorySettlementDAO.deleteRCategorySettlementByConfig(id, bizCode);

        for (ItemSettlementConditionDTO itemSettlementConditionDTO : itemSettlementConfigDTO.getConditionList()) {

            ItemSettlementConditionDO itemSettlementConditionDO = new ItemSettlementConditionDO();

            BeanUtils.copyProperties(itemSettlementConditionDTO, itemSettlementConditionDO);

            itemSettlementConditionDO.setBizCode(bizCode);
            itemSettlementConditionDO.setParentId(id);

            itemSettlementConditionDAO.addItemSettlementCondition(itemSettlementConditionDO);
        }


        if (itemSettlementConfigDTO.getScope() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "scope不能为空");
        } else if (itemSettlementConfigDTO.getScope() == 2) {

            if (CollectionUtils.isEmpty(itemSettlementConfigDTO.getCategoryIdList())) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "categoryIdList不能为空");
            }

            for (Long categoryId : itemSettlementConfigDTO.getCategoryIdList()) {

                RCategorySettlementDO rCategorySettlementDO = new RCategorySettlementDO();

                ItemCategoryDO itemCategoryDO = itemCategoryDAO.getItemCategory(categoryId);
                rCategorySettlementDO.setBizCode(bizCode);
                rCategorySettlementDO.setSettlementId(id);
                rCategorySettlementDO.setCategoryId(categoryId);
                rCategorySettlementDO.setCategoryLevel(itemCategoryDO.getCateLevel());

                rCategorySettlementDAO.addRCategorySettlement(rCategorySettlementDO);
            }

        } else if (itemSettlementConfigDTO.getScope() == 4) {

            if (CollectionUtils.isEmpty(itemSettlementConfigDTO.getSellerIdList())) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "shopIdList不能为空");
            }

            for (Long sellerId : itemSettlementConfigDTO.getSellerIdList()) {

                RShopSettlementDO rShopSettlementDO = new RShopSettlementDO();

                ShopDTO shopDTO = shopManager.getShop(sellerId, appKey);

                if (shopDTO == null) {
                    continue;
                }

                rShopSettlementDO.setBizCode(bizCode);
                rShopSettlementDO.setSettlementId(id);
                rShopSettlementDO.setSellerId(sellerId);
                rShopSettlementDO.setShopId(shopDTO.getId());

                rShopSettlementDAO.addRShopSettlement(rShopSettlementDO);
            }
        }


        return result;
    }

    @Override
    public Long calculateCommission(List<CommissionUnitDTO> commissionUnitDTOList, String bizCode) throws ItemException {

        //查出所有的配置
        ItemSettlementConfigQTO query = new ItemSettlementConfigQTO();
        query.setBizCode(bizCode);
        query.setEnable(1);

        List<ItemSettlementConfigDO> itemSettlementConfigDOList = itemSettlementConfigDAO.queryItemSettlementConfig(query);


        Collection<ItemSettlementConfigDO> allSellerList = Collections2.filter(itemSettlementConfigDOList, new Predicate<ItemSettlementConfigDO>() {
            @Override
            public boolean apply(ItemSettlementConfigDO itemSettlementConfigDO) {
                return itemSettlementConfigDO.getScope().intValue() == 3;
            }
        });

        Collection<ItemSettlementConfigDO> allCategoryList = Collections2.filter(itemSettlementConfigDOList, new Predicate<ItemSettlementConfigDO>() {
            @Override
            public boolean apply(ItemSettlementConfigDO itemSettlementConfigDO) {
                return itemSettlementConfigDO.getScope().intValue() == 1;
            }
        });

        long result = 0L;

        label:
        for (CommissionUnitDTO commissionUnitDTO : commissionUnitDTOList) {

            long price = commissionUnitDTO.getPrice();

            Long itemId = commissionUnitDTO.getItemId();
            Long sellerId = commissionUnitDTO.getSellerId();


            ItemDO itemDO = itemDAO.getItem(itemId, sellerId, bizCode);

            if (itemDO == null) {
                log.error("计算佣金时，查询不到对应的商品 item_id :{} seller_id:{}", commissionUnitDTO.getItemId(), commissionUnitDTO.getSellerId());
                continue;
            }

            RShopSettlementQTO rShopSettlementQTO = new RShopSettlementQTO();

            rShopSettlementQTO.setBizCode(bizCode);
            rShopSettlementQTO.setSellerId(sellerId);

            List<RShopSettlementDO> rShopSettlementDOList = rShopSettlementDAO.queryRShopSettlement(rShopSettlementQTO);

            if (!CollectionUtils.isEmpty(rShopSettlementDOList)) {    //应该只有一条

                Long settlementId = rShopSettlementDOList.get(0).getSettlementId();

                //查找对应的结算配置
                for (ItemSettlementConfigDO itemSettlementConfigDO : itemSettlementConfigDOList) {

                    if (itemSettlementConfigDO.getId().longValue() == settlementId.longValue()) {
                        result += calculateCommission(commissionUnitDTO, itemSettlementConfigDO);
                        continue label;
                    }
                }
            }

            if (!CollectionUtils.isEmpty(allSellerList)) {

                result += calculateCommission(commissionUnitDTO, allSellerList.iterator().next());

                continue;
            }

            RCategorySettlementQTO rCategorySettlementQTO = new RCategorySettlementQTO();

            rCategorySettlementQTO.setBizCode(bizCode);
            rCategorySettlementQTO.setCategoryId(itemDO.getCategoryId());

            List<RCategorySettlementDO> rCategorySettlementDOList = rCategorySettlementDAO.queryRCategorySettlement(rCategorySettlementQTO);

            if (!CollectionUtils.isEmpty(rCategorySettlementDOList)) {    //应该只有一条

                Long settlementId = rCategorySettlementDOList.get(0).getSettlementId();

                //查找对应的结算配置
                for (ItemSettlementConfigDO itemSettlementConfigDO : itemSettlementConfigDOList) {

                    if (itemSettlementConfigDO.getId().longValue() == settlementId.longValue()) {
                        result += calculateCommission(commissionUnitDTO, itemSettlementConfigDO);
                        continue label;
                    }
                }

            }


            if (!CollectionUtils.isEmpty(allCategoryList)) {

                result += calculateCommission(commissionUnitDTO, allCategoryList.iterator().next());

                continue;
            }


        }

        return result;
    }

    /**
     * 根据某条结算配置算出某件商品的佣金
     *
     * @param commissionUnitDTO
     * @param itemSettlementConfigDO
     * @return
     * @throws ItemException
     */
    private Long calculateCommission(CommissionUnitDTO commissionUnitDTO, ItemSettlementConfigDO itemSettlementConfigDO) throws ItemException {


        ItemSettlementConditionQTO query = new ItemSettlementConditionQTO();
        query.setBizCode(itemSettlementConfigDO.getBizCode());
        query.setParentId(itemSettlementConfigDO.getId());

        List<ItemSettlementConditionDO> itemSettlementConditionDOList = itemSettlementConditionDAO.queryItemSettlementCondition(query);

        if (itemSettlementConditionDOList != null && itemSettlementConditionDOList.size() > 0) {

            Collections.sort(itemSettlementConditionDOList, new Comparator<ItemSettlementConditionDO>() {

                @Override
                public int compare(ItemSettlementConditionDO o1, ItemSettlementConditionDO o2) {

                    return (int) (o1.getMinPrice() - o2.getMinPrice());
                }
            });

            for (ItemSettlementConditionDO itemSettlementConditionDO : itemSettlementConditionDOList) {

                Long price = commissionUnitDTO.getPrice() * commissionUnitDTO.getNum();

                Long minPrice = itemSettlementConditionDO.getMinPrice();

                Long maxPrice = itemSettlementConditionDO.getMaxPrice();

                if (price >= minPrice && price < maxPrice) {

                    return (price * itemSettlementConditionDO.getCommissionRatio()) / 10000L;
                }
            }
        }


        return 0L;
    }

    private void verifyConditions(List<ItemSettlementConditionDTO> itemSettlementConditionDTOList) throws ItemException {

        if (CollectionUtils.isEmpty(itemSettlementConditionDTOList)) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemSettlementConditionDTOList不能为空");
        }

        Collections.sort(itemSettlementConditionDTOList, new Comparator<ItemSettlementConditionDTO>() {
            @Override
            public int compare(ItemSettlementConditionDTO o1, ItemSettlementConditionDTO o2) {
                return (int) (o1.getMinPrice() - o2.getMaxPrice());
            }
        });

        PeekingIterator<ItemSettlementConditionDTO> iterator = Iterators.peekingIterator(itemSettlementConditionDTOList.iterator());

        while (iterator.hasNext()) {

            ItemSettlementConditionDTO current = iterator.next();

            if (current.getCommissionRatio() < 0) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "commissionRatio不能为负数");
            }


            if (current.getMinPrice() < 0) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "最小值 " + current.getMinPrice() / 100.00 + " 不能为负数");
            }

            if (current.getMaxPrice() < current.getMinPrice()) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "最大值 " + current.getMaxPrice() / 100.00 + " 不能小于最小值  " + current.getMinPrice() / 100.00);
            }

            if (iterator.hasNext() && current.getMaxPrice() > iterator.peek().getMinPrice()) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "前一条的最大值 " + current.getMaxPrice() / 100.00 + " 不能大于后一条的最小值" + iterator.peek().getMinPrice() / 100.00);
            }
        }

    }
}

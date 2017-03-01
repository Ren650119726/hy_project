package com.mockuai.itemcenter.core.manager.impl;


import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.itemcenter.common.constant.*;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.util.NumberFormatUtil;
import com.mockuai.itemcenter.core.dao.ItemDAO;
import com.mockuai.itemcenter.core.dao.ItemSkuDAO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.DataManager;
import com.mockuai.itemcenter.core.manager.DistributorManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ItemManagerImpl implements ItemManager {

    private Logger log = LoggerFactory.getLogger(ItemManagerImpl.class);

    @Resource
    private ItemDAO itemDAO;
    private static final String ITEM_DESC_UPLOAD_URL = "";
    private static final String FILE_PATH = "/home/admin/upload_files/";
    private static final String TMP_PATH = "/home/admin/tmp/";


    @Resource
    private AppClient appClient;

    @Resource
    private DataManager dataManager;

    @Resource
    private ItemSkuDAO itemSkuDAO;
    @Resource
    private DistributorManager distributorManager;


    @Override
    public Double getNormalItemGains(Long itemId, List<ItemSkuDTO> itemSkuDTOList, String appKey) throws ItemException {
        Double maxGain = 0d;

        List<ItemSkuDistPlanDTO> skuDistPlanList = distributorManager.getItemSkuDistPlanList(itemId, appKey);
        Map<Long, ItemSkuDistPlanDTO> skuDistMap = new HashMap<Long, ItemSkuDistPlanDTO>(skuDistPlanList.size());
        for (ItemSkuDistPlanDTO skuDistPlan : skuDistPlanList) {
            skuDistMap.put(skuDistPlan.getItemSkuId(), skuDistPlan);
        }

        //找到最大价格
        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
            if (skuDistMap.containsKey(itemSkuDTO.getId())) {
                Double thisGain = (itemSkuDTO.getPromotionPrice() * skuDistMap.get(itemSkuDTO.getId()).getDistGainsRatio());
                if (thisGain.longValue() > maxGain) {
                    maxGain = thisGain;
                }
            }
        }
        return maxGain;
    }

    @Override
    public String getNormalItemIntervalGains(Long itemId,
                                             List<ItemSkuDTO> itemSkuDTOList, BigDecimal oneGains, String appKey)
            throws ItemException {
        String gainPercentDesc = "";
        List<ItemSkuDistPlanDTO> skuDistPlanList = distributorManager.getItemSkuDistPlanList(itemId, appKey);
        log.info(" ############ skuDistPlanList:{}  ", JsonUtil.toJson(skuDistPlanList));
        int listSize = skuDistPlanList.size();

        Collections.sort(skuDistPlanList, new Comparator<ItemSkuDistPlanDTO>() {
            public int compare(ItemSkuDistPlanDTO arg0, ItemSkuDistPlanDTO arg1) {
                return arg0.getDistGainsRatio().compareTo(arg1.getDistGainsRatio());
            }
        });

        // 收益为0不返回
        if (skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize - 1).getDistGainsRatio()) && skuDistPlanList.get(0).getDistGainsRatio().equals(new Double(0))) {
            return null;
        }

        if (skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize - 1).getDistGainsRatio())) {
            gainPercentDesc = new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(oneGains).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
        } else {
            gainPercentDesc = new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(oneGains).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "% ~ " + new BigDecimal(skuDistPlanList.get(listSize - 1).getDistGainsRatio()).multiply(oneGains).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
        }

        return gainPercentDesc;
    }

    @Override
    public Map getNormalIntervalGains(Long itemId, List<ItemSkuDTO> itemSkuDTOList, BigDecimal oneGains, String appKey) throws ItemException {
        String gainPercentDesc = "";
        String gains = "";
        List<ItemSkuDistPlanDTO> skuDistPlanList = distributorManager.getItemSkuDistPlanList(itemId, appKey);
        log.info(" ############ skuDistPlanList:{}  ", JsonUtil.toJson(skuDistPlanList));
        int listSize = skuDistPlanList.size();

        Collections.sort(skuDistPlanList, new Comparator<ItemSkuDistPlanDTO>() {
            public int compare(ItemSkuDistPlanDTO arg0, ItemSkuDistPlanDTO arg1) {
                return arg0.getDistGainsRatio().compareTo(arg1.getDistGainsRatio());
            }
        });

        // 收益为0不返回
        if (skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize - 1).getDistGainsRatio()) && skuDistPlanList.get(0).getDistGainsRatio().equals(new Double(0))) {
            return null;
        }
        if (skuDistPlanList.get(0).getDistGainsRatio().equals(skuDistPlanList.get(listSize - 1).getDistGainsRatio())) {
            gainPercentDesc =
                    new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue() + "%";


        } else {
            gainPercentDesc =
                    new BigDecimal(skuDistPlanList.get(0).getDistGainsRatio()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue()
                            + "% ~ " +
                            new BigDecimal(skuDistPlanList.get(listSize - 1).getDistGainsRatio()).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue() + "%";

        }
//        log.info("gainPercentDesc:{}",gainPercentDesc);

        //商品分享收益区间处理

        Map<Long, ItemSkuDistPlanDTO> itemSkuDistPlanMap = Maps.uniqueIndex(skuDistPlanList,
                new Function<ItemSkuDistPlanDTO, Long>() {//查询所有的收益信息
                    @Override
                    public Long apply(ItemSkuDistPlanDTO itemSkuDistPlanDTO) {
                        return itemSkuDistPlanDTO.getItemSkuId();
                    }
                });
        TreeSet<BigDecimal> gainsSet = new TreeSet<BigDecimal>();
        for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
            BigDecimal sharingGains = new BigDecimal(itemSkuDistPlanMap.get(itemSkuDTO.getId()).getDistGainsRatio()).
                    multiply(new BigDecimal(itemSkuDTO.getPromotionPrice()).multiply(oneGains)).
                    divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
            gainsSet.add(sharingGains);

        }
        if (gainsSet.isEmpty()) {
            return null;
        }

        if (gainsSet.size() == 1) {
            gains = NumberFormatUtil.formatTwoDecimalNoZero((BigDecimal) gainsSet.first()) + "元";
        } else {
            gains = NumberFormatUtil.formatTwoDecimalNoZero((BigDecimal) gainsSet.first()) + "元~"
                    + NumberFormatUtil.formatTwoDecimalNoZero((BigDecimal) gainsSet.last()) + "元";
        }
//        log.info("sharingGains:{}",gains);

        Map map = new HashMap();
        map.put("gainPercent", gainPercentDesc);
        map.put("sharingGains", gains);

        return map;
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO, String appKey) throws ItemException {
        // 验证itemDTO内的属性
        verifyNewAddedItemDTOProperty(itemDTO);
        ItemDO itemDO = new ItemDO();
        ItemUtil.copyProperties(itemDTO, itemDO);// DTO转DO
        long newInsertedId = itemDAO.addItem(itemDO);// 新增的记录返回的ID

        Long now = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        map.put("property", "1");

        dataManager.buriedPoint(itemDTO.getSellerId(), "items", map, now, appKey);

        //update by cwr 填充id 不需要重新获取一次
        //itemDTO = getItem(newInsertedId, itemDTO.getSellerId());// 新增加的记录对应的itemDO
        itemDTO.setId(newInsertedId);
        return itemDTO;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws ItemException {
        // 验证参数
        verifyUpdatedItemDTOProperty(itemDTO);
        ItemDO itemDO = new ItemDO();
        ItemUtil.copyProperties(itemDTO, itemDO);
        int num = itemDAO.updateItem(itemDO);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item error-->primary id:"
                    + itemDTO.getId() + " sellerId:" + itemDTO.getSellerId());
        }
    }

    @Override
    public Boolean updateItemWithBlankDate(ItemDTO itemDTO) throws ItemException {

        verifyUpdatedItemDTOProperty(itemDTO);

        ItemDO itemDO = new ItemDO();

        ItemUtil.copyProperties(itemDTO, itemDO);

        int num = itemDAO.updateItemWithBlankDate(itemDO);

        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item error-->primary id:"
                    + itemDTO.getId() + " sellerId:" + itemDTO.getSellerId());
        }
    }

    @Override
    public List<ItemDTO> queryItemContainsUrl(ItemQTO itemQTO) throws ItemException {

        List<ItemDO> list = itemDAO.queryItem(itemQTO);
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表

        if (list != null && list.size() > 0) {

            String bizCode = list.get(0).getBizCode();
            if (bizCode != null) {
                com.mockuai.appcenter.common.api.Response<AppInfoDTO> response = appClient.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP);
                AppInfoDTO appInfoDTO = response.getModule();

                String domain = "";
                if (appInfoDTO != null) {
                    domain = appInfoDTO.getDomainName();
                }

                for (ItemDO itemDO : list) {

                    ItemDTO dto = new ItemDTO();

                    dto.setItemName(itemDO.getItemName());
                    dto.setSellerId(itemDO.getSellerId());
                    dto.setCategoryId(itemDO.getCategoryId());
                    dto.setIconUrl(itemDO.getIconUrl());
                    dto.setMarketPrice(itemDO.getMarketPrice());
                    dto.setPromotionPrice(itemDO.getPromotionPrice());
                    dto.setWirelessPrice(itemDO.getWirelessPrice());
                    dto.setCornerIconId(dto.getCornerIconId());
                    dto.setItemBrandId(itemDO.getItemBrandId());

                    dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
                    dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));

                    StringBuilder sb = new StringBuilder("http://");
                    sb.append(domain);
                    sb.append("/detail.html?item_uid=");
                    sb.append(itemDO.getSellerId());
                    sb.append("_");
                    sb.append(itemDO.getId());
                    dto.setItemUrl(sb.toString());

                    itemDTOList.add(dto);
                }
            }

        }

        return itemDTOList;
    }

    @Override
    public List<ItemDTO> queryDistinctSellerId() {
        List<ItemDO> itemDOs = itemDAO.queryDistinctSellerId();

        List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
        for (ItemDO itemDO : itemDOs) {
            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(itemDO, itemDTO);
            itemDTOs.add(itemDTO);
        }
        return itemDTOs;
    }

    @Override
    public List<ItemDTO> selectItemLoseShopId(String bizCode) {

        ItemQTO query = new ItemQTO();
        query.setBizCode(bizCode);

        List<ItemDO> list = itemDAO.selectItemLoseShopId(query);
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
        for (ItemDO itemDO : list) {
            ItemDTO dto = new ItemDTO();
            ItemUtil.copyProperties(itemDO, dto);
            dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
            dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));
            itemDTOList.add(dto);
        }
        return itemDTOList;
    }

    @Override
    public Long updateItemLoseShopId(ItemQTO query) {
        return itemDAO.updateItemLoseShopId(query);
    }

    @Override
    public Long trashItem(Long itemId, Long sellerId, String bizCode) throws ItemException {

        ItemDO query = new ItemDO();
        query.setId(itemId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        query.setDeleteMark(DeleteMarkEnum.NORMAL.getCode());
        return itemDAO.trashItem(query);
    }

    @Override
    public Long recoveryItem(Long itemId, Long sellerId, String bizCode) throws ItemException {
        ItemDO query = new ItemDO();
        query.setId(itemId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        query.setDeleteMark(DeleteMarkEnum.RECYCLING.getCode());
        return itemDAO.recoveryItem(query);
    }

    @Override
    public Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException {
        ItemDO query = new ItemDO();
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setDeleteMark(DeleteMarkEnum.RECYCLING.getCode());
        return itemDAO.emptyRecycleBin(query);
    }

    @Override
    public void updateItemStockNum(ItemDTO itemDTO) throws ItemException {

        ItemDO query = new ItemDO();
        BeanUtils.copyProperties(itemDTO, query);
        itemDAO.updateItemStockNum(query);

    }

    @Override
    public boolean needIndex(ItemDTO itemDTO) {

        if (null == itemDTO) {
            return false;
        }

        if (itemDTO.getItemStatus() == null || itemDTO.getItemType() == null) {
            return false;
        }

        if ((itemDTO.getItemStatus().intValue() == ItemStatus.ON_SALE.getStatus() || itemDTO.getItemStatus().intValue() == ItemStatus.PRE_SALE.getStatus())
                && (itemDTO.getItemType().intValue() == ItemType.NORMAL.getType() || itemDTO.getItemType().intValue() == ItemType.TIME_LIMIT.getType())) {
            return true;
        }
        if (itemDTO.getItemType().intValue() == ItemType.COMPOSITE_ITEM.getType()) {
            return true;
        }


        return false;
    }


    @Override
    public Map<Long, ItemDTO> queryItemMap(List<Long> idList, String bizCode) {

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(idList);
        itemQTO.setBizCode(bizCode);
        Map<Long, ItemDO> map = itemDAO.queryItemMap(itemQTO);

        Map<Long, ItemDTO> result = Maps.newHashMapWithExpectedSize(map.size());

        for (Map.Entry<Long, ItemDO> entry : map.entrySet()) {

            ItemDTO itemDTO = new ItemDTO();
            BeanUtils.copyProperties(entry.getValue(), itemDTO);
            result.put(entry.getKey(), itemDTO);
        }

        return result;
    }

    @Override
    public boolean removeItemFromGroup(ItemDTO itemDTO) throws ItemException {
        ItemDO itemDO = new ItemDO();
        ItemUtil.copyProperties(itemDTO, itemDO);
        int num = itemDAO.removeItemFromGroup(itemDO);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item error-->primary id:"
                    + itemDTO.getId() + " sellerId:" + itemDTO.getSellerId());
        }
    }

    @Override
    public boolean removeItemToDefaultGroup(Long sellerId, Long groupId) throws ItemException {
        ItemDO itemDO = new ItemDO();
        itemDO.setSellerId(sellerId);
        itemDO.setGroupId(groupId);
        itemDAO.removeItemToDefaultGroup(itemDO);
        return true;
    }

    @Override
    public ItemDTO getItem(Long id, Long supplierId, String bizCode) throws ItemException {
        ItemDO itemDO = itemDAO.getItem(id, supplierId, bizCode);
        if (itemDO == null) {
            throw ExceptionUtil
                    .getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table item-->id:"
                            + id + " sellerId:" + supplierId + "bizCode :" + bizCode);
        }
        ItemDTO itemDTO = new ItemDTO();
        ItemUtil.copyProperties(itemDO, itemDTO);
        itemDTO.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
        itemDTO.setUnitName(ItemUtil.convertItemUnit(itemDO.getUnit()));
        return itemDTO;
    }

    @Override
    public boolean deleteItem(Long id, Long supplierId, String bizCode, String appKey) throws ItemException {


        int num = itemDAO.deleteItem(id, supplierId, bizCode);
        if (num > 0) {

            Long now = System.currentTimeMillis();
            Map<String, String> map = new HashMap<String, String>();
            map.put("property", "-1");

            dataManager.buriedPoint(supplierId, "items", map, now, appKey);
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
                            + id + " sellerId:" + supplierId);
        }
    }

    @Override
    public boolean removeItem(Long id, Long supplierId, String bizCode) throws ItemException {
        // TODO 验证id
        int num = itemDAO.updateItemState(id, supplierId, bizCode, DBConst.ITEM_IN_TRASH.getCode());
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
                            + id + " sellerId:" + supplierId);
        }
    }

    public boolean removeItemSaleEnd(Long id, Long supplierId, String bizCode) throws ItemException {
        // TODO 验证id
        int num = itemDAO.removeItemSaleEnd(id, supplierId, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
                            + id + " sellerId:" + supplierId);
        }
    }

    @Override
    public boolean removeItemSaleBegin(Long id, Long supplierId, String bizCode) throws ItemException {
        int num = itemDAO.removeItemSaleBegin(id, supplierId, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table item-->id:"
                            + id + " sellerId:" + supplierId);
        }
    }

    private void verifyNewAddedItemDTOProperty(ItemDTO itemDTO) throws ItemException {
        // 验证ItemDTO字段属性
        if (itemDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
        }
        if (itemDTO.getItemName() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_name is null");
        }
        //TODO商品简称］后续考虑可能去掉
//		if (itemDTO.getItemBriefName() == null) {
//			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_brief_name is null");
//		}
//        if (itemDTO.getItemBrandId() == null) {
//            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_brand_id is null");
//        }
        if (itemDTO.getItemType() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_type is null");
        }
        if (itemDTO.getIconUrl() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "icon_url is null");
        }

        if (itemDTO.getCategoryId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "category_id is null");
        }

        if (itemDTO.getFreight() == null && itemDTO.getFreightTemplate() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "固定运费和运费模板必须设置一个");
        }

        if (itemDTO.getFreight() != null && itemDTO.getFreight() < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "固定运费不能设置为负数");
        }

        if (itemDTO.getWeight() != null && itemDTO.getWeight() < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "重量不能设置为负数");
        }

        if (itemDTO.getVolume() != null && itemDTO.getVolume() < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "体积不能设置为负数");
        }

//        //验证上架下架时间是否小于当前系统时间 9.2bugfix
//        if ((itemDTO.getSaleBegin() != null)
//                && (!new Date().before(itemDTO.getSaleBegin()))) {
//            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "自动上架时间不能在当前时间之前，请重试");
//        }

        if ((itemDTO.getSaleEnd() != null)
                && (!new Date().before(itemDTO.getSaleEnd()))) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "自动下架时间不能在当前时间之前，请重试");
        }

        if ((itemDTO.getSaleBegin() != null) && (itemDTO.getSaleEnd() != null)
                && (!itemDTO.getSaleEnd().after(itemDTO.getSaleBegin()))) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "自动下架时间不能在自动上架时间之前，请重试");
        }

        // TODO 验证 CategoryId在数据库是否存在

        // TODO 验证品牌ID在数据库是否存在

        // TODO 供应商ID验证
        if (itemDTO.getSellerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
        }

    }

    private void verifyUpdatedItemDTOProperty(ItemDTO itemDTO) throws ItemException {


        // TODO 验证ItemDTO字段属性
        if (itemDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
        }
        if (itemDTO.getId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemDTO is null");
        }
        // TODO 供应商ID验证
        if (itemDTO.getSellerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
        }

//        if ((itemDTO.getSaleBegin() != null)
//                && (!new Date().before(itemDTO.getSaleBegin()))) {
//            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "自动上架时间不能在当前时间之前，请重试");
//        }


//
//        if ((itemDTO.getSaleBegin() != null) && (itemDTO.getSaleEnd() != null)
//                && (!itemDTO.getSaleEnd().after(itemDTO.getSaleBegin()))) {
//            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "自动下架时间不能在自动上架时间之前，请重试");
//        }

        if (itemDTO.getFreight() == null && itemDTO.getFreightTemplate() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "固定运费和运费模板必须设置一个");
        }

        if (itemDTO.getFreight() != null && itemDTO.getFreight() < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "固定运费不能设置为负数");
        }

        if (itemDTO.getWeight() != null && itemDTO.getWeight() < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "重量不能设置为负数");
        }

        if (itemDTO.getVolume() != null && itemDTO.getVolume() < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "体积不能设置为负数");
        }
    }

    public List<ItemDTO> queryItem(ItemQTO itemQTO) throws ItemException {
        // TODO 供应商ID验证
        if (itemQTO.getSellerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId is null");
        }
        // sellerId = 0默认BOSS
        if (itemQTO.getSellerId() == 0) {
            itemQTO.setSellerId(null);
        }

        List<ItemDO> list = itemDAO.queryItem(itemQTO);

        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }

//        // 通过商品ID批量查询商品sku数目
//        List<Long> itemIdList = new ArrayList<Long>();
//        for (ItemDO itemDO : list) {
//            itemIdList.add(itemDO.getId());
//        }

//        Map<Long, Long> skuCountMap = new HashMap<Long, Long>();
//
//        if (!CollectionUtils.isEmpty(itemIdList)) {
//
//            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
//            itemSkuQTO.setItemIdList(itemIdList);
//            itemSkuQTO.setBizCode(itemQTO.getBizCode());
//
//            List<SkuCountDO> skuCountDOs = itemSkuDAO.querySkuCount(itemSkuQTO);
//
//
//            for (SkuCountDO skuCountDO : skuCountDOs) {
//                skuCountMap.put(skuCountDO.getItemId(), skuCountDO.getCount());
//            }
//        }

        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
        for (ItemDO itemDO : list) {
            ItemDTO dto = new ItemDTO();
            ItemUtil.copyProperties(itemDO, dto);
            dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
            dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));

//            // 通过sku数目来判断是否是单sku的商品
//            Long skuCount = skuCountMap.get(dto.getId());
//
//            if (skuCount != null && skuCount > 1) {
//                dto.setSingleSku(0);
//            } else {
//                dto.setSingleSku(1);
//            }

            itemDTOList.add(dto);
        }
        return itemDTOList;
    }

    public Long countGroupItem(ItemQTO itemQTO) throws ItemException {
        if (itemQTO.getSellerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
        }
        return itemDAO.countItem(itemQTO);
    }

    @Override
    public Long countItem(ItemQTO itemQTO) throws ItemException {

        return itemDAO.countItem(itemQTO);
    }

    @Override
    public List<ItemDTO> selectItemSaleUp(ItemQTO itemQTO) throws ItemException {
        List<ItemDO> list = itemDAO.selectItemSaleStateUp(itemQTO);
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
        for (ItemDO itemDO : list) {
            ItemDTO dto = new ItemDTO();
            ItemUtil.copyProperties(itemDO, dto);
            dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
            dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));
            itemDTOList.add(dto);
        }
        return itemDTOList;
    }

    @Override
    public List<ItemDTO> selectItemSaleDown(ItemQTO itemQTO) throws ItemException {
        List<ItemDO> list = itemDAO.selectItemSaleStateDown(itemQTO);
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 需要返回的DTO列表
        for (ItemDO itemDO : list) {
            ItemDTO dto = new ItemDTO();
            ItemUtil.copyProperties(itemDO, dto);
            dto.setStatusName(ItemUtil.convertItemStatus(itemDO.getItemStatus()));//返回状态的中文显示
            dto.setItemUid(ItemUtil.genUid(itemDO.getId(), itemDO.getSellerId()));
            itemDTOList.add(dto);
        }
        return itemDTOList;
    }

    @Override
    public void updateItemSaleUp(ItemQTO itemQTO) throws ItemException {
        itemDAO.updateItemSaleStateUp(itemQTO);
    }

    @Override
    public void updateItemSaleDown(ItemQTO itemQTO) throws ItemException {
        itemDAO.updateItemSaleStateDown(itemQTO);
    }


    @Override
    public int withdrawItem(Long itemId, Long supplierId, String bizCode) throws ItemException {
        Integer itemStatus = ItemStatus.WITHDRAW.getStatus();//下架的状态
        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    @Override
    public int upItem(Long itemId, Long supplierId, String bizCode) throws ItemException {
        Integer itemStatus = ItemStatus.ON_SALE.getStatus();//上架的状态
        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    @Override
    public int preSaleItem(Long itemId, Long supplierId, String bizCode) throws ItemException {

        ItemDTO item = getItem(itemId, supplierId, bizCode);
        verifyPreSaleItemDTOProperty(item);
        Integer itemStatus = ItemStatus.PRE_SALE.getStatus();//上架的状态


        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    @Override
    public int skuInvalidItem(Long itemId, Long supplierId, String bizCode) throws ItemException {

        ItemDTO item = getItem(itemId, supplierId, bizCode);
        Integer itemStatus = ItemStatus.SKU_INVALID.getStatus();//上架的状态


        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    private void verifyPreSaleItemDTOProperty(ItemDTO item) throws ItemException {
        //验证上架时间 9.2bugfix
        if (item.getSaleBegin() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "自动上架时间不能为空");
        }
    }

    @Override
    public int thawItem(Long itemId, Long supplierId, String bizCode) throws ItemException {
        Integer itemStatus = ItemStatus.WITHDRAW.getStatus();//解冻后，直接设为下架的状态
        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    @Override
    public int freezeItem(Long itemId, Long supplierId, String bizCode) throws ItemException {
        Integer itemStatus = ItemStatus.FROZEN.getStatus();// 冻结的状态
        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    @Override
    public int disableItem(Long itemId, Long supplierId, String bizCode) throws ItemException {
        Integer itemStatus = ItemStatus.DISABLE.getStatus();// 失效的状态
        int result = this.itemDAO.updateItemState(itemId, supplierId, bizCode, itemStatus);
        return result;
    }

    public String uploadItemDesc(String itemDesc) throws ItemException {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("media_auth_key", "6r4XkF6EcE");
        File file = null;
        try {
            file = processUploadFile(itemDesc);
        } catch (Exception e) {
            //TODO error handle
        }

        //暂时只支持单文件上传
        if (file != null) {
//			String response = HttpUtil.uploadFile(ITEM_DESC_UPLOAD_URL, "images", file, paramMap);
        } else {
            //TODO error handle
        }

        return null;
    }

    // 处理上传的文件
    private File processUploadFile(String itemDesc) throws Exception {
        // 此时的文件名包含了完整的路径，得注意加工一下
        String filename = "" + System.currentTimeMillis();
        System.out.println("完整的文件名：" + filename);
        int index = filename.lastIndexOf("\\");
        filename = filename.substring(index + 1, filename.length());

        long fileSize = itemDesc.length();

        if ("".equals(filename) && fileSize == 0) {
//			log.error("文件名为空 ...");
            return null;
        }

        File uploadFile = new File(FILE_PATH + "/" + filename);
        if (!uploadFile.exists()) {
            uploadFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(uploadFile);
        byte[] data = itemDesc.getBytes();
        fos.write(data, 0, data.length);
        fos.close();
        return uploadFile;
    }

    @Override
    public Boolean isItemExist(ItemQTO itemQTO) throws ItemException {
        Object obj = this.itemDAO.isItemExist(itemQTO);
        return (obj != null);
    }


    @Override
    public List<Long> queryCompositeItem(List<Long> idList) throws ItemException {
        List<Long> obj = this.itemDAO.queryCompositeItem(idList);
        return obj;
    }

    @Override
    public Long updateIssueStatus(Long itemId, Integer status) throws ItemException {
        Long result = this.itemDAO.updateIssueStatus(itemId, status);
        return result;
    }

    @Override
    public Integer selectItemStatus(Long itemId) {
        return itemDAO.selectItemStatus(itemId);
    }

}

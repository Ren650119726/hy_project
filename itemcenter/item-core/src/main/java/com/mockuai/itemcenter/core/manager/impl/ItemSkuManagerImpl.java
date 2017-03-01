package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.dao.ItemSkuDAO;
import com.mockuai.itemcenter.core.dao.RItemSuitDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuDO;
import com.mockuai.itemcenter.core.domain.RItemSuitDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ItemSkuManagerImpl implements ItemSkuManager {

    private static final Logger log = LoggerFactory.getLogger(CommentImageManagerImpl.class);
    @Resource
    private ItemSkuDAO itemSkuDAO;

    @Resource
    private SkuPropertyManager skuPropertyManager;

    @Resource
    private ItemManager itemManager;

    @Resource
    private DataManager dataManager;

    @Resource
    private AppClient appClient;

    @Resource
    private RItemSuitDAO rItemSuitDAO;

    @Resource
    private ItemImageManager itemImageManager;

    @Override
    public ItemSkuDTO addItemSku(ItemSkuDTO itemSkuDTO, String appKey) throws ItemException {

        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemSkuDTO is null");
        }
        // 验证itemSkuDTO内的属性
        verifyNewAddedItemSkuDTOProperty(itemSkuDTO);
        // 增加ItemSku表记录
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        ItemUtil.copyProperties(itemSkuDTO, itemSkuDO);// DTO转DO
        long newInsertedId = itemSkuDAO.addItemSku(itemSkuDO);// 新增的记录返回的ID
        long sellerId = itemSkuDTO.getSellerId();
        itemSkuDTO = getItemSku(newInsertedId, sellerId, itemSkuDTO.getBizCode());// 新增加的记录对应的itemSkuDO

        Map<String, String> map = new HashMap<String, String>();
        map.put("property", "1");
        Long now = System.currentTimeMillis();

        dataManager.buriedPoint(sellerId, "skus", map, now, appKey);


        return itemSkuDTO;
    }

    @Override
    public boolean updateItemSku(ItemSkuDTO itemSkuDTO) throws ItemException {
        verifyUpdatedItemSkuDTOProperty(itemSkuDTO);
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        ItemUtil.copyProperties(itemSkuDTO, itemSkuDO);
        int num = itemSkuDAO.updateItemSku(itemSkuDO);
        if (num > 0) {
            return true;
        } else {
            log.error("updateItemSku params: {}", JsonUtil.toJson(itemSkuDTO));
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update item_sku error-->primary id:"
                    + itemSkuDTO.getId());
        }
    }

    @Override
    public ItemSkuDTO getItemSku(Long id, Long sellerId, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = itemSkuDAO.getItemSku(id, sellerId, bizCode);
        if (itemSkuDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested"
                    + " record doesn't exist from table item_sku-->id:" + id + " sellerId:" + sellerId);
        }


        ItemSkuDTO itemSkuDTO = fillSkuImage(itemSkuDO);
        return itemSkuDTO;
    }

    @Override
    public boolean deleteItemSku(Long id, Long sellerId, String bizCode, String appKey) throws ItemException {
        // 先删除skuproperty列表
        skuPropertyManager.deleteSkuPropertyListBySkuId(id, sellerId, bizCode);

        ItemSkuDTO itemSkuDTO = getItemSku(id, sellerId, bizCode);

        if (null == bizCode) {
            ItemDTO itemDTO = itemManager.getItem(itemSkuDTO.getItemId(), sellerId, bizCode);
            bizCode = itemDTO.getBizCode();
        }

        int num = itemSkuDAO.deleteItemSku(id, sellerId, bizCode);
        if (num > 0) {

            Map<String, String> map = new HashMap<String, String>();
            map.put("property", "-1");
            Long now = System.currentTimeMillis();
            dataManager.buriedPoint(sellerId, "skus", map, now, appKey);
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete item_sku error-->id:" + id
                    + " sellerId:" + sellerId);
        }
    }

    /**
     * 验证新增ItemSku属性
     *
     * @param itemSkuDTO itemSkuDTO
     * @return 是否验证通过
     * @throws ItemException
     */
    private boolean verifyNewAddedItemSkuDTOProperty(ItemSkuDTO itemSkuDTO) throws ItemException {
        /*
        if (StringUtils.isBlank(itemSkuDTO.getMaterialCode())) {
			throw ExceptionUtil.getException(ResCodeNum.PARAM_E_MISSING, "MaterialCode is null");
		}
		if (StringUtils.isBlank(itemSkuDTO.getBarCode())) {
			throw ExceptionUtil.getException(ResCodeNum.PARAM_E_MISSING, "BarCode is null");
		}
		*/
        // TODO
        // 验证供应商ID seller_id
        // 验证item_id是否合法
        if (itemSkuDTO.getSellerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId is null");
        }
        Long sellerId = itemSkuDTO.getSellerId();
        Long itemId = itemSkuDTO.getItemId();
        if (itemId == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemId is null");
        }

		/* 不需要该步骤验证
        ItemDTO itemDTO = itemManager.getItem(itemId, sellerId);
		if (itemDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "itemId is not illegle");
		}
		*/
        //updated by cwr 不需要该步骤
//		if (itemDTO.getItemStatus() != DBConst.ITEM_NORMAL.getCode()) {
//			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "item is in illegle status");
//		}
        return true;
    }

    /**
     * 验证更新的ItemSku属性
     *
     * @param itemSkuDTO itemSkuDTO
     * @throws ItemException
     */
    private void verifyUpdatedItemSkuDTOProperty(ItemSkuDTO itemSkuDTO) throws ItemException {
        if (itemSkuDTO.getId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemSkuId is null");
        }
    }

    // /**
    // * 验证SkuProperty属性
    // *
    // * @param itemSkuDTO
    // * @return
    // * @throws ItemException
    // */
    // private boolean verifySkuPropertyDTOProperty(SkuPropertyDTO
    // skuPropertyDTO) throws ItemException {
    // // TODO
    // // 验证供应商ID seller_id
    // if (StringUtils.isBlank(skuPropertyDTO.getKeyName())) {
    // throw ExceptionUtil.getException(ResCodeNum.PARAM_E_MISSING,
    // "KeyName is null");
    // }
    // if (StringUtils.isBlank(skuPropertyDTO.getVal())) {
    // throw ExceptionUtil.getException(ResCodeNum.PARAM_E_MISSING,
    // "Val is null");
    // }
    // if (skuPropertyDTO.getSort() == null) {
    // throw ExceptionUtil.getException(ResCodeNum.PARAM_E_MISSING,
    // "sort is null");
    // }
    // return true;
    // }

    public List<ItemSkuDTO> queryItemSku(ItemSkuQTO itemSkuQTO) throws ItemException {
        //TODO 入参校验
        List<ItemSkuDO> list = itemSkuDAO.queryItemSku(itemSkuQTO);
        List<ItemSkuDTO> itemSkuDTOList = new ArrayList<ItemSkuDTO>();// 需要返回的DTO列表

        for (ItemSkuDO itemSkuDO : list) {


            ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
            ItemUtil.copyProperties(itemSkuDO, itemSkuDTO);

            itemSkuDTOList.add(itemSkuDTO);
        }
        return itemSkuDTOList;
    }
    @Override
    public List<ItemSkuDTO> queryItemDynamic(ItemSkuQTO itemSkuQTO) throws ItemException {
        //TODO 入参校验
        List<ItemSkuDO> list = itemSkuDAO.queryItemDynamic(itemSkuQTO);
        List<ItemSkuDTO> itemSkuDTOList = new ArrayList<ItemSkuDTO>();// 需要返回的DTO列表

        for (ItemSkuDO itemSkuDO : list) {


            ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
            ItemUtil.copyProperties(itemSkuDO, itemSkuDTO);

            itemSkuDTOList.add(itemSkuDTO);
        }
        return itemSkuDTOList;
    }

    @Override
    public Long countItemSku(ItemSkuQTO itemSkuQTO) throws ItemException {
        return null;
    }

    @Override
    public int deleteByItemId(Long itemId, Long supplierId, String appKey, String bizCode) throws ItemException {
        return 0;
    }

    public Map<Long, ItemSkuDTO> queryItemSkuMap(List<Long> skuIdList, Long sellerId, String bizCode) throws ItemException {

        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(skuIdList);
        itemSkuQTO.setSellerId(sellerId);
        itemSkuQTO.setBizCode(bizCode);
        itemSkuQTO.setDeleteMark(0);

        try {
            List<ItemSkuDO> itemSkuDTOs = itemSkuDAO.queryItemSku(itemSkuQTO);
            Map<Long, ItemSkuDTO> itemSkuMap = new HashMap<Long, ItemSkuDTO>();
            if (itemSkuDTOs != null && !itemSkuDTOs.isEmpty()) {
                for (ItemSkuDO itemSkuDO : itemSkuDTOs) {
                    ItemSkuDTO skuDTO = new ItemSkuDTO();
                    BeanUtils.copyProperties(itemSkuDO, skuDTO);
                    itemSkuMap.put(itemSkuDO.getId(), skuDTO);
                }
            }
            return itemSkuMap;

        } catch (Exception e) {
            log.error("sellerId:{}", sellerId, e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to addCommentImages");
        }
    }

    //updated by cwr
    public int deleteByItemId(Long itemId, Long supplierId, String appKey) throws ItemException {
        ItemSkuDO itemSkuDo = new ItemSkuDO();

        itemSkuDo.setItemId(itemId);
        itemSkuDo.setSellerId(supplierId);

        int result = this.itemSkuDAO.deleteByItemId(itemSkuDo);

        if (result > 0) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("property", Integer.valueOf(0 - result).toString());
            Long now = System.currentTimeMillis();
            dataManager.buriedPoint(supplierId, "skus", map, now, appKey);
            System.currentTimeMillis();
        }


        return 0;
    }

    public boolean decreaseItemSkuStock(Long skuId, Long sellerId, Integer decreasedNumber, String bizCode) throws ItemException {
        // TODO sellerId
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        Long frozenStock = itemSkuDTO.getFrozenStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }
        if (decreasedNumber == null || decreasedNumber > currentStock - frozenStock) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "decreasedNumber:" + decreasedNumber);
        }

        int num = itemSkuDAO.decreaseItemSkuStock(skuId, sellerId, decreasedNumber, bizCode);

        List<RItemSuitDO> rItemSuitDOList = rItemSuitDAO.queryBySuitId(itemSkuDTO.getItemId(), itemSkuDTO.getSellerId(), itemSkuDTO.getBizCode());

        if (rItemSuitDOList != null && rItemSuitDOList.size() > 0) {  //套装商品减掉子商品的库存
            for (RItemSuitDO rItemSuitDO : rItemSuitDOList) {
                decreaseItemSkuStock(rItemSuitDO.getSubItemSkuId(), rItemSuitDO.getSellerId(), decreasedNumber, bizCode);
            }
        }

        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "decrease SkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " increasedNumber:" + decreasedNumber);
        }
    }

    public boolean increaseItemSkuStock(Long skuId, Long sellerId, Integer increasedNumber, String bizCode) throws ItemException {
        // TODO sellerId
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }
        if (increasedNumber == null || increasedNumber <= 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "increasedNumber:" + increasedNumber);
        }
        int num = itemSkuDAO.increaseItemSkuStock(skuId, sellerId, increasedNumber, bizCode);

        List<RItemSuitDO> rItemSuitDOList = rItemSuitDAO.queryBySuitId(itemSkuDTO.getItemId(), itemSkuDTO.getSellerId(), itemSkuDTO.getBizCode());

        if (rItemSuitDOList != null && rItemSuitDOList.size() > 0) {  //套装商品减掉子商品的库存
            for (RItemSuitDO rItemSuitDO : rItemSuitDOList) {
                increaseItemSkuStock(rItemSuitDO.getSubItemSkuId(), rItemSuitDO.getSellerId(), increasedNumber, bizCode);
            }
        }
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "increase SkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " increasedNumber:" + increasedNumber);
        }
    }

    @Override
    public boolean freezeItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException {
        // TODO sellerId
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        Long frozenStock = itemSkuDTO.getFrozenStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }

        if (number == null || number <= 0 || number > currentStock - frozenStock) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "number:" + number);
        }

        int num = itemSkuDAO.freezeItemSkuStock(skuId, sellerId, number, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "freeze SkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " number:" + number);
        }
    }

    @Override
    public boolean thawItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException {
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }
        if (number == null || number <= 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "number:" + number);
        }
        int num = itemSkuDAO.thawItemSkuStock(skuId, sellerId, number, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "thaw SkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " number:" + number);
        }
    }

    @Override
    public boolean crushItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException {
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }
        if (number == null || number <= 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "number:" + number);
        }
        int num = itemSkuDAO.crushItemSkuStock(skuId, sellerId, number, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "crushSkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " number:" + number);
        }
    }

    @Override
    public boolean resumeCrushedItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException {
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }
        if (number == null || number <= 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "number:" + number);
        }
        int num = itemSkuDAO.resumeCrushedItemSkuStock(skuId, sellerId, number, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "crushSkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " number:" + number);
        }
    }

    @Override
    public boolean updateItemSkuCodeValue(Long skuId, Long sellerId, List<SkuPropertyDTO> skuPropertyDTOList, String bizCode)
            throws ItemException {
        // 以sort字段对属性排序

        if (!CollectionUtils.isEmpty(skuPropertyDTOList)) {
            Collections.sort(skuPropertyDTOList, new Comparator<SkuPropertyDTO>() {
                @Override
                public int compare(SkuPropertyDTO o1, SkuPropertyDTO o2) {
                    return o1.getSort().compareTo(o2.getSort());
                }
            });


            StringBuilder codeValue = new StringBuilder();
            for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
                codeValue.append(skuPropertyDTO.getValue()).append("-");
            }

            //删除最后一个中划线
            if (codeValue.lastIndexOf("-") > -1) {
                codeValue.deleteCharAt(codeValue.lastIndexOf("-"));
            }
            // ItemSkuDTO updateDTO = new ItemSkuDTO();
            // updateDTO.setId(skuId);
            // updateDTO.setSellerId(sellerId);
            // updateDTO.setCodeValue(codeValue.toString());
            int num = itemSkuDAO.updateItemSkuCodeValue(skuId, sellerId, codeValue.toString(), bizCode);
            if (num > 0) {
                return true;
            } else {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, " update item_sku error item_sku-->id:"
                        + skuId + " sellerId:" + sellerId);
            }
        }

        return true;
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
//		sb.append("123").append("-").append("abc").append("-");

//		sb.deleteCharAt(sb.lastIndexOf("-"));
        System.out.println(sb.lastIndexOf("-"));
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

    @Override
    public List<ItemSkuDTO> queryStock(List<Long> itemIdList, String bizCode) throws ItemException {
        if (itemIdList.isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {

            ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
            itemSkuQTO.setItemIdList(itemIdList);
            itemSkuQTO.setBizCode(bizCode);
            List<ItemSkuDO> stocks = itemSkuDAO.queryStock(itemSkuQTO);
            if (stocks == null) {
                throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "query db error");
            }
            List<ItemSkuDTO> itemSkuDTOs = new ArrayList<ItemSkuDTO>();
            for (ItemSkuDO itemSkuDO : stocks) {
                ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
                BeanUtils.copyProperties(itemSkuDO, itemSkuDTO);
                itemSkuDTOs.add(itemSkuDTO);
            }
            return itemSkuDTOs;
        }
    }

    @Override
    public Long trashByItemId(Long itemId, Long sellerId, String bizCode) throws ItemException {

        ItemSkuDO query = new ItemSkuDO();
        query.setItemId(itemId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        return itemSkuDAO.trashByItemId(query);
    }

    @Override
    public Long recoveryByItemId(Long itemId, Long sellerId, String bizCode) throws ItemException {
        ItemSkuDO query = new ItemSkuDO();
        query.setItemId(itemId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        return itemSkuDAO.recoveryByItemId(query);
    }

    @Override
    public Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException {
        ItemSkuDO query = new ItemSkuDO();
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        return itemSkuDAO.emptyRecycleBin(query);
    }

    @Override
    public boolean updateItemSkuStock(Long skuId, Long sellerId, Long number, String bizCode) throws ItemException {
        ItemSkuDTO itemSkuDTO = getItemSku(skuId, sellerId, bizCode);
        if (itemSkuDTO == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "skuId:" + skuId + " sellerId:"
                    + sellerId);
        }
        Long currentStock = itemSkuDTO.getStockNum();
        if (currentStock == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "currentStock is null");
        }
        if (number == null || number < 0) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "number:" + number);
        }
        int num = itemSkuDAO.updateItemSkuStock(skuId, sellerId, number, bizCode);
        if (num > 0) {
            return true;
        } else {
            throw ExceptionUtil
                    .getException(ResponseCode.SYS_E_DB_UPDATE, "set SkuStock item_sku error-->skuId id:"
                            + skuId + " sellerId:" + sellerId + " number:" + number);
        }

    }

    @Override
    public Long countItemStock(Long id, Long sellerId, String bizCode) throws ItemException {
        ItemSkuDO query = new ItemSkuDO();
        query.setItemId(id);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        return itemSkuDAO.countItemStock(query);
    }
}

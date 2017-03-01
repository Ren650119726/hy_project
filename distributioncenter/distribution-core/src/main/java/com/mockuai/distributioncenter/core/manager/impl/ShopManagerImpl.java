package com.mockuai.distributioncenter.core.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.domain.dto.GoodsItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistShopQTO;
import com.mockuai.distributioncenter.core.dao.ItemSkuDistPlanDAO;
import com.mockuai.distributioncenter.core.dao.ShopDAO;
import com.mockuai.distributioncenter.core.domain.ItemSkuDistPlanDO;
import com.mockuai.distributioncenter.core.domain.DistShopDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.itemcenter.common.constant.ItemStatus;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.shopcenter.ShopClient;
import com.mockuai.shopcenter.api.Response;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.ShopItemGroupQTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by duke on 16/5/12.
 */
@Component
public class ShopManagerImpl implements ShopManager {

    private static final Logger log = LoggerFactory.getLogger(ShopManager.class);

    @Autowired
    private ShopDAO shopDAO;
    @Autowired
    private ShopClient shopClient;
    @Autowired
    private ItemClient itemClient;
    @Autowired
    private ItemSkuClient itemSkuClient;
    @Autowired
    private ItemSkuDistPlanDAO itemSkuDistPlanDAO;

    @Override
    public Long add(DistShopDTO distShopDTO) throws DistributionException {
        DistShopDO distShopDO = new DistShopDO();
        BeanUtils.copyProperties(distShopDTO, distShopDO);
        return shopDAO.add(distShopDO);
    }

    @Override
    public List<DistShopDTO> query(DistShopQTO distShopQTO) throws DistributionException {
        List<DistShopDTO> distShopDTOs = new ArrayList<DistShopDTO>();
        List<DistShopDO> distShopDOs = shopDAO.query(distShopQTO);
        if (distShopDOs != null) {
            for (DistShopDO distShopDO : distShopDOs) {
                DistShopDTO distShopDTO = new DistShopDTO();
                BeanUtils.copyProperties(distShopDO, distShopDTO);
                distShopDTOs.add(distShopDTO);
            }
        }
        return distShopDTOs;
    }

    @Override
    public DistShopDTO get(Long id) throws DistributionException {
        DistShopDO distShopDO = shopDAO.get(id);
        if (distShopDO != null) {
            DistShopDTO distShopDTO = new DistShopDTO();
            BeanUtils.copyProperties(distShopDO, distShopDTO);
            return distShopDTO;
        } else {
            log.error("shop not exists, shopId: {}", id);
            return null;
        }
    }

    @Override
    public Integer update(DistShopDTO distShopDTO) throws DistributionException {
        DistShopDO distShopDO = new DistShopDO();
        BeanUtils.copyProperties(distShopDTO, distShopDO);
        return shopDAO.update(distShopDO);
    }

    @Override
    public DistShopDTO getBySellerId(Long sellerId) throws DistributionException {
    	log.info("sellerId is not error :"+sellerId+"!!!!!!!");
        DistShopDO distShopDO = shopDAO.getBySellerId(sellerId);
        if (distShopDO != null) {
            DistShopDTO distShopDTO = new DistShopDTO();
            BeanUtils.copyProperties(distShopDO, distShopDTO);
            return distShopDTO;
        } else {
            log.error("shop not exits, sellerId: {}", sellerId);
            return null;
        }
    }

    @Override
    public void updateQrcodeUrl(DistShopQTO shop) throws DistributionException {

        try {
            shopDAO.updateQrcodeUrl(shop);
        } catch (Exception e) {
            log.error("failed when  findList : {}", JsonUtil.toJson(shop), e);
            throw new DistributionException(ResponseCode.DB_OP_ERROR.getMessage(), e);
        }
    }

    @Override
    public List<ShopItemGroupDTO> queryShopItemGroup(String appKey) throws DistributionException {
        ShopItemGroupQTO qto = new ShopItemGroupQTO();

        try {
            Response<List<ShopItemGroupDTO>> response =
                    shopClient.queryShopItemGroup(qto, appKey);
            return response.getModule();
        } catch (Exception e) {
            log.error("Action failed {}", e);
            throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getMessage(), e);
        }
    }


    public DistributionResponse getAllItem(boolean isSeller, int offset,int count,String appKey) throws DistributionException {

        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        itemSearchQTO.setAsc(0);
        itemSearchQTO.setOrderBy(1);
        itemSearchQTO.setOffset(offset);
        itemSearchQTO.setCount(count);

        com.mockuai.itemcenter.common.api.Response<List<ItemSearchDTO>>  listResponse =itemClient.searchItem(itemSearchQTO,appKey);
        if(!listResponse.isSuccess()){
            log.error("failed when searchItem ,itemClient searchItem occur failed:{}",listResponse.getMessage());
            throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getCode(),listResponse.getMessage());
        }
        DistributionResponse<List<GoodsItemDTO>> distributionResponse = new DistributionResponse(ResponseCode.SUCCESS);
        if(listResponse.getTotalCount() == 0){
            distributionResponse.setTotalCount(0);
            return distributionResponse;
        }
        distributionResponse.setTotalCount(listResponse.getTotalCount());
        List<ItemSearchDTO> itemDTOList =   listResponse.getModule();
        List<GoodsItemDTO> goodsItemDTOList = new ArrayList<GoodsItemDTO>(itemDTOList.size());
        for (ItemSearchDTO item : itemDTOList) {
            StringTokenizer itemTokenizer = new StringTokenizer(item.getItemUid(),"_");
            if(itemTokenizer.countTokens() != 2){
                log.error("failed when searchItem itemId can not found :{}",item.getItemUid());
                throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getMessage());
            }
            Long sellerId = Long.parseLong(itemTokenizer.nextToken());
            Long itemId = Long.parseLong(itemTokenizer.nextToken());
            GoodsItemDTO goodsItemDTO = new GoodsItemDTO();
            ItemSkuDTO itemSkuDTO =  getItemSkuDTO(itemId,sellerId,appKey);
            if(itemSkuDTO == null ){
                continue;
            }
            double saleDistRatio =  isSeller? getSaleDistRatio(itemSkuDTO):0;

            goodsItemDTO.setItemId(itemId);
            goodsItemDTO.setItemBrandId(item.getBrandId());
            goodsItemDTO.setImageUrl(item.getIconUrl());
            goodsItemDTO.setItemName(item.getItemName());
            goodsItemDTO.setItemUrl(item.getIconUrl());
            goodsItemDTO.setItemUid(item.getItemUid());
            goodsItemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
            goodsItemDTO.setSaleDistRatio(saleDistRatio);
            long saleDistPrice =   Math.round( itemSkuDTO.getPromotionPrice() * saleDistRatio);
            goodsItemDTO.setSaleDistPrice(saleDistPrice);
            goodsItemDTOList.add(goodsItemDTO);

        }
        distributionResponse.setModule(goodsItemDTOList);

        return distributionResponse;

    }


   private  ItemSkuDTO getItemSkuDTO(Long itemId,Long sellerId,String appKey) throws DistributionException {
       com.mockuai.itemcenter.common.api.Response<List<ItemSkuDTO>> itemSkuResponse =
               itemSkuClient.queryItemSku(itemId, sellerId, appKey);
       if (!itemSkuResponse.isSuccess()) {
           log.error("failed when  queryGoodsList ,itemSkuClient queryItemSku occur failed");
           throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getCode(),itemSkuResponse.getMessage());
       }
       //未找到对应的sku
       if(itemSkuResponse.getModule().isEmpty()){
           log.error("failed when  queryGoodsList ,,itemSkuClient queryItemSku ItemSkuDTO is empty. skip:{}_{}",sellerId,itemId);
           return null;
       }
       return findMiniSku(itemSkuResponse.getModule());

   }

    private double  getSaleDistRatio(ItemSkuDTO skuDTO) throws DistributionException {

        ItemSkuDistPlanDO itemSkuDistPlanDO = itemSkuDistPlanDAO.getByItemSkuId(skuDTO.getId());
        if(itemSkuDistPlanDO == null){
            return 0d;
        }
        return itemSkuDistPlanDO.getSaleDistRatio();
    }


    public DistributionResponse queryGoodsList(boolean isSeller,String appKey, Long sellerId, Long groupId, int offset, int count, String itemName, String orderBy , int desc ) throws DistributionException {

        Response<ShopItemGroupDTO> response = shopClient.getShopItemGroup(sellerId, groupId, "1", appKey);

        DistributionResponse<List<GoodsItemDTO>> distributionResponse = new DistributionResponse(ResponseCode.SUCCESS);
        if (response.getCode() != 10000) {
            log.error("failed when queryGoodsList ,shopClient getShopItemGroup occur failed:{}",response.getMessage());
            throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getCode(),response.getMessage());
        }
        //商品没有直接返回0
        if(response.getTotalCount() == 0){
            distributionResponse.setTotalCount(0);
            return distributionResponse;
        }
        List<Long> productIdList = response.getModule().getItemIdList();
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setIdList(productIdList);
        itemQTO.setOffset(offset);
        itemQTO.setPageSize(count);
        itemQTO.setSellerId(sellerId);
        itemQTO.setNeedPaging(true);
        //上架的商品
        itemQTO.setItemStatus(ItemStatus.ON_SALE.getStatus());
        if(StringUtils.hasText(itemName)){
            itemQTO.setItemName(itemName);
        }
        //排序字段 默认按销量排序
        if(!StringUtils.hasText(orderBy)){
            orderBy = "sales_volume";
        }
        //排序方式 默认倒序
        itemQTO.setOrderBy(orderBy);
        itemQTO.setAsc(desc);
        log.info("itemQTO:{}",JSONObject.toJSON(itemQTO).toString());
        com.mockuai.itemcenter.common.api.Response<List<ItemDTO>> listResponse = itemClient.queryItem(itemQTO, appKey);

        if (!listResponse.isSuccess()) {
            log.error("failed when  queryGoodsList ,itemClient queryItem occur failed:{}",listResponse.getMessage());
            throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getCode(),listResponse.getMessage());
        }
        distributionResponse.setTotalCount(listResponse.getTotalCount());
        List<ItemDTO> itemDTOList = listResponse.getModule();
        List<GoodsItemDTO> goodsItemDTOList = new ArrayList<GoodsItemDTO>(itemDTOList.size());
        for (ItemDTO item : itemDTOList) {

            GoodsItemDTO goodsItemDTO = new GoodsItemDTO();
            ItemSkuDTO itemSkuDTO =  getItemSkuDTO(item.getId(),sellerId,appKey);
            if(itemSkuDTO == null ){
                continue;
            }
            double saleDistRatio = isSeller ? getSaleDistRatio(itemSkuDTO):0;
            goodsItemDTO.setItemId(item.getId());
            goodsItemDTO.setItemBrandId(item.getItemBrandId());
            goodsItemDTO.setImageUrl(item.getIconUrl());
            goodsItemDTO.setItemName(item.getItemName());
            goodsItemDTO.setItemUrl(item.getItemUrl());
            goodsItemDTO.setPromotionPrice(itemSkuDTO.getPromotionPrice());
            goodsItemDTO.setSaleDistRatio(saleDistRatio);
            goodsItemDTO.setItemUid(item.getItemUid());
            long saleDistPrice =   Math.round( itemSkuDTO.getPromotionPrice() * saleDistRatio);
            goodsItemDTO.setSaleDistPrice(saleDistPrice);
            goodsItemDTOList.add(goodsItemDTO);

        }
        distributionResponse.setModule(goodsItemDTOList);

        return distributionResponse;
    }

    private ItemSkuDTO findMiniSku(List<ItemSkuDTO> data) throws DistributionException {
        if(data.isEmpty()){
            log.error("failed when  queryGoodsList ,ItemSkuDTO is empty");
            throw new DistributionException(ResponseCode.SYS_E_REMOTE_CALL_ERROR.getMessage());
        }
        ItemSkuDTO miniSkuDTO = data.get(0);
        for (ItemSkuDTO skuDTO : data) {
            if (miniSkuDTO.getPromotionPrice() > skuDTO.getPromotionPrice()) {
                miniSkuDTO = skuDTO;
            }
        }
        return miniSkuDTO;
    }

}

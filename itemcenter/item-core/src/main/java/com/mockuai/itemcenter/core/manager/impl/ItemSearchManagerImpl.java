package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Maps;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.constant.SearchOrderEnum;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.common.domain.qto.*;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.*;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

//import com.mockuai.distributioncenter.common.domain.dto.RatioDTO;

/**
 * Created by zengzhangqiang on 5/4/15.
 * TODO 索引的异步批量更新，以及索引的优化(server.optimize())
 */
@Service
public class ItemSearchManagerImpl implements ItemSearchManager {
    private static final Logger log = LoggerFactory.getLogger(ItemSearchManagerImpl.class);

    private static SolrServer solrServer;
    private static String searchServerUrl;


    public void init() {
        //TODO 上线前这里改成配置
        solrServer = new HttpSolrServer(searchServerUrl);
    }

    @Resource
    private ItemCommentManager itemCommentManager;

    @Resource
    private CornerIconManager cornerIconManager;

    @Resource
    private ItemSkuManager itemSkuManager;

    @Resource
    private ItemSalesVolumeManager itemSalesVolumeManager;

    @Resource
    private ItemCategoryManager itemCategoryManager;

    @Resource
    private SellerBrandManager sellerBrandManager;

    @Resource
    private ShopManager shopManager;

    @Resource
    private ItemMiniShopManager itemMiniShopManager;
    @Resource
    private DistributorManager distributorManager;
    @Resource
    private AppManager appManager;

    private int[]  validItemType = {ItemType.NORMAL.getType(),ItemType.COMPOSITE_ITEM.getType()};

    @Override
    public void setItemIndex(ItemDTO itemDTO) throws ItemException {

        //只有普通商品和组合商品才添加索引
        if (itemDTO.getItemType() == null
                ) {
            return;
        }
        boolean findItemType =false ;
        for(int itemType : validItemType){
            if(itemDTO.getItemType() == itemType){
                findItemType = true;
            }
        }
        if(!findItemType){
            log.info("不支持商品类型:{},type:{}",itemDTO.getItemName(),itemDTO.getItemType());
            return ;
        }
        ItemSearchDTO itemSearchDTO = new ItemSearchDTO();
        itemSearchDTO.setItemUid("" + itemDTO.getSellerId() + "_" + itemDTO.getId());
        itemSearchDTO.setItemName(itemDTO.getItemName());
        itemSearchDTO.setCategoryId(itemDTO.getCategoryId());
        itemSearchDTO.setBrandId(itemDTO.getItemBrandId());
        itemSearchDTO.setGroupId(itemDTO.getGroupId());
        itemSearchDTO.setShopId(itemDTO.getShopId());
        itemSearchDTO.setIconUrl(itemDTO.getIconUrl());
        //TODO 搜索引擎中的价格数据处理逻辑需要考虑（包括价格的更新等等）
        itemSearchDTO.setMarketPrice(itemDTO.getMarketPrice());
        itemSearchDTO.setPromotionPrice(itemDTO.getPromotionPrice());


        //按照最新排序，最新定义为按商品最后手动上架、自动上架的时间排序
        itemSearchDTO.setSaleBegin(itemDTO.getSaleBegin());
        // item catagory name
        itemSearchDTO.setCategoryName(itemDTO.getCategoryName());
        // sku barcode
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setSellerId(itemDTO.getSellerId());
        itemSkuQTO.setItemId(itemDTO.getId());
        itemSkuQTO.setBizCode(itemDTO.getBizCode());
        List<ItemSkuDTO> itemSkuDTOs = itemSkuManager.queryItemSku(itemSkuQTO);

        Long minPrice = Long.MAX_VALUE;
        Long maxPrice = 0L;
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(itemSkuDTOs)) {
            for (ItemSkuDTO itemSkuDTO : itemSkuDTOs) {
                Long price = itemSkuDTO.getPromotionPrice();

                if (price > maxPrice) {
                    maxPrice = price;
                }

                if (price < minPrice) {
                    minPrice = price;
                }
                sb.append(itemSkuDTO.getBarCode()).append(" ");
            }
        }else{
            minPrice = 0L;
        }


        //设置bizCode
        itemSearchDTO.setBizCode(itemDTO.getBizCode());
        AppInfoDTO appInfoDTO = appManager.getAppInfoByType(itemDTO.getBizCode(), AppTypeEnum.APP_WAP);
        String appKey = appInfoDTO.getAppKey();
        //查找商品佣金
        List<ItemSkuDistPlanDTO> itemSkuDistPlanDTOList =   distributorManager.getItemSkuDistPlanList(itemDTO.getId(),appKey);
        if(! CollectionUtils.isEmpty(itemSkuDistPlanDTOList )){
            Map<Long,ItemSkuDistPlanDTO> itemSkuDistPlanDTOMap = Maps.newHashMapWithExpectedSize(itemSkuDistPlanDTOList.size());
            for(ItemSkuDistPlanDTO itemSkuDistPlanDTO : itemSkuDistPlanDTOList){
                itemSkuDistPlanDTOMap.put(itemSkuDistPlanDTO.getItemSkuId(),itemSkuDistPlanDTO);
            }
            //查找商品最大佣金最小佣金
            long maxCommission  =0 ,minCommission = Integer.MAX_VALUE ;
            for(ItemSkuDTO skuDTO : itemSkuDTOs){
                if(!itemSkuDistPlanDTOMap.containsKey(skuDTO.getId())){
                    continue;
                }
                Double ratio =    itemSkuDistPlanDTOMap.get(skuDTO.getId()).getDistGainsRatio();
                long commission = (long) (skuDTO.getPromotionPrice() * ratio);
                if(maxCommission < commission){
                    maxCommission = commission;
                }
                if(minCommission > commission){
                    minCommission = commission;
                }
            }
            itemSearchDTO.setMaxDirectCommission(maxCommission);
            itemSearchDTO.setMinDirectCommission(minCommission);
        }

        //  distributorManager.getItemSkuDistPlanList(itemDTO.getId())
//        //微小店佣金
//        double ratio = itemMiniShopManager.getDirectCommissionRatio(itemSearchDTO);
//
//        itemSearchDTO.setMinDirectCommission((long) (minPrice * ratio));
//        itemSearchDTO.setMaxDirectCommission((long) (maxPrice * ratio));

        itemSearchDTO.setBarCode(sb.toString());

        //TODO 兼容无线价不存在的情况，后续需要从源头上来兼容
        if (itemDTO.getWirelessPrice() != null) {
            itemSearchDTO.setWirelessPrice(itemDTO.getWirelessPrice());
        } else {
            itemSearchDTO.setWirelessPrice(itemDTO.getPromotionPrice());
        }

        itemSearchDTO.setSellerId(itemDTO.getSellerId());

        //设置货源地
        if (itemDTO.getItemPropertyList() != null) {
            for (ItemPropertyDTO itemPropertyDTO : itemDTO.getItemPropertyList()) {
                if ("IC_APP_P_ITEM_000002".equals(itemPropertyDTO.getCode())) {//货源地
                    itemSearchDTO.setSupplyBase(itemPropertyDTO.getValue());
                }
            }
        }

        //设置角标ID
        itemSearchDTO.setCornerIconId(itemDTO.getCornerIconId());
        //itemSearchDTO.setItemType(itemDTO.getItemType());

        List<ItemSearchDTO> itemSearchDTOs = new ArrayList<ItemSearchDTO>();
        itemSearchDTOs.add(itemSearchDTO);
        try {
            this.setItem(itemSearchDTOs);
        } catch (Exception e) {
            //TODO error handle
            log.error("", e);
        }
    }


    @Override
    public ItemSearchResultDTO searchItemIndex(ItemSearchQTO itemSearchQTO) throws ItemException {
        //TODO param check
        if (itemSearchQTO == null) {
            throw new ItemException(ResponseCode.PARAM_E_MISSING, "itemSearchQTO is null");
        }

        if (StringUtils.isBlank(itemSearchQTO.getBizCode())) {
            throw new ItemException(ResponseCode.PARAM_E_MISSING, "bizCode is null");
        }

        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setQuery("*:*");

        if (StringUtils.isNotBlank(itemSearchQTO.getKeyword())) {

            String[] keywords = itemSearchQTO.getKeyword().split(",");

            if (keywords != null && keywords.length > 0) {
                solrQuery.setQuery("text:" + filterKeyword(keywords[0]));
            }

            for (String keyword : keywords) {
                if (StringUtils.isNotBlank(keyword)) {
                    solrQuery.addFilterQuery("text:" + filterKeyword(keyword));
                }
            }
        }

        solrQuery.addFilterQuery("biz_code:" + itemSearchQTO.getBizCode());

        if (itemSearchQTO.getItemUid() != null) {
            solrQuery.addFilterQuery("item_uid:" + itemSearchQTO.getItemUid());
        }

        if (itemSearchQTO.getCategoryId() != null) {
            solrQuery.addFilterQuery("category_id:" + itemSearchQTO.getCategoryId());
        }

        if (itemSearchQTO.getBrandId() != null) {
            solrQuery.addFilterQuery("brand_id:" + itemSearchQTO.getBrandId());
        }

        if (itemSearchQTO.getShopId() != null) {
            solrQuery.addFilterQuery("shop_id:" + itemSearchQTO.getShopId());
        }

        if (itemSearchQTO.getGroupId() != null) {
            solrQuery.addFilterQuery("group_id:" + itemSearchQTO.getGroupId());
        }

        if (itemSearchQTO.getItemUids() != null) {

            StringBuilder sb = new StringBuilder();

            for (String itemUid : itemSearchQTO.getItemUids()) {
                sb.append("item_uid:");
                sb.append(itemUid);
                sb.append(" OR ");
            }

            sb.delete(sb.length() - 4, sb.length());

            solrQuery.addFilterQuery(sb.toString());
        }

        if (itemSearchQTO.getCategoryIds() != null) {

            StringBuilder sb = new StringBuilder();

            for (Long categoryId : itemSearchQTO.getCategoryIds()) {
                sb.append("category_id:");
                sb.append(categoryId);
                sb.append(" OR ");
            }

            sb.delete(sb.length() - 4, sb.length());

            solrQuery.addFilterQuery(sb.toString());
        }

        if (itemSearchQTO.getBrandIds() != null) {

            StringBuilder sb = new StringBuilder();

            for (Long brandId : itemSearchQTO.getBrandIds()) {
                sb.append("brand_id:");
                sb.append(brandId);
                sb.append(" OR ");
            }

            sb.delete(sb.length() - 4, sb.length());

            solrQuery.addFilterQuery(sb.toString());

        }

//        Integer priceType = itemSearchQTO.getPriceType()==null?0:itemSearchQTO.getPriceType();
//        String priceParam = priceType==0?"wireless_price":"promotion_price";
        String priceParam = "promotion_price";

        if (itemSearchQTO.getMinPrice() != null) {
            if (itemSearchQTO.getMaxPrice() != null) {
                solrQuery.addFilterQuery(priceParam + ":[ " + itemSearchQTO.getMinPrice() + " TO " + itemSearchQTO.getMaxPrice() + "]");
            } else {
                solrQuery.addFilterQuery(priceParam + ":[ " + itemSearchQTO.getMinPrice() + " TO * ]");
            }
        } else {
            if (itemSearchQTO.getMaxPrice() != null) {
                solrQuery.addFilterQuery(priceParam + ":[ * TO " + itemSearchQTO.getMaxPrice() + " ]");
            }
        }
        if(itemSearchQTO.getMinCommission() != null){
            solrQuery.addFilterQuery("min_direct_commission:[ " + itemSearchQTO.getMinCommission() + " TO * ]");
        }
        if(itemSearchQTO.getMaxCommission() != null){
            solrQuery.addFilterQuery("max_direct_commission:[ " + itemSearchQTO.getMaxCommission() + " TO * ]");
        }
        if (itemSearchQTO.getOrderBy() != null) {
            SolrQuery.ORDER order = SolrQuery.ORDER.asc;
            if (itemSearchQTO.getAsc() != null && itemSearchQTO.getAsc().intValue() == 0) {
                order = SolrQuery.ORDER.desc;
            }

            SolrQuery.ORDER orderx = SolrQuery.ORDER.desc;

            if (itemSearchQTO.getAsc() != null && itemSearchQTO.getAsc().intValue() == 1) {
                orderx = SolrQuery.ORDER.desc;
            }

            //销量降序
            if (itemSearchQTO.getOrderBy().intValue() == SearchOrderEnum.SALE_VOLUMENEW.getCode()) {
                solrQuery.addSortField("sales_volume", order);
            }
            
            //价格排序
            if (itemSearchQTO.getOrderBy().intValue() == SearchOrderEnum.PRICE.getCode()) {
                solrQuery.addSortField(priceParam, order);
            }
            //按照最新排序;
            if (itemSearchQTO.getOrderBy().intValue() == SearchOrderEnum.LATEST.getCode()) {
                solrQuery.addSortField("sale_begin", orderx);
            }
            //评论数降序
            if (itemSearchQTO.getOrderBy().intValue() == SearchOrderEnum.COMMENTS.getCode()) {
                solrQuery.addSortField("comments", orderx);
            }

            //微小店最高直接佣金降序
            if (itemSearchQTO.getOrderBy().intValue() == SearchOrderEnum.MAX_DIRECT_COMMISSION.getCode()) {
                solrQuery.addSortField("max_direct_commission", orderx);
            }
            if(itemSearchQTO.getOrderBy().intValue() == SearchOrderEnum.COMMISSON.getCode()
                  ){
                //倒序 按照最大佣金倒序排序  正序 按最小佣金正序排序
                if (itemSearchQTO.getAsc() != null && itemSearchQTO.getAsc().intValue() == 0) {
                    solrQuery.addSortField("max_commission", orderx);
                }
                if (itemSearchQTO.getAsc() != null && itemSearchQTO.getAsc().intValue() == 1) {
                    solrQuery.addSortField("min_commission", orderx);
                }
            }

        }

        solrQuery.setStart(itemSearchQTO.getOffset());
        solrQuery.setRows(itemSearchQTO.getCount());

        try {
            QueryResponse response = solrServer.query(solrQuery);
            List<ItemSearchDTO> itemSearchDTOs = response.getBeans(ItemSearchDTO.class);


            ItemSearchResultDTO itemSearchResultDTO = new ItemSearchResultDTO();

            //需要聚合查询数量
            if (itemSearchQTO.getFacet() != null && itemSearchQTO.getFacet() == 1) {

                solrQuery.addFacetField("category_id").addFacetField("brand_id");

                QueryResponse responseX = solrServer.query(solrQuery);

                FacetField facetField = responseX.getFacetField("category_id");


                ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
                itemCategoryQTO.setBizCode(itemSearchQTO.getBizCode());
                itemCategoryQTO.setCateLevel(2);

                List<ItemCategoryDTO> itemCategoryDTOs = itemCategoryManager.queryItemCategory(itemCategoryQTO);

                List<ItemCategoryDTO> categoryDTOs = new ArrayList<ItemCategoryDTO>();


                List<FacetField.Count> counts = facetField.getValues();

                for (ItemCategoryDTO itemCategoryDTO : itemCategoryDTOs) {

                    for (FacetField.Count count : counts) {

                        try {
                            long id = Long.valueOf(count.getName());

                            if (itemCategoryDTO.getId() == id) {
                                long value = count.getCount();
                                if (value != 0L) {
                                    ItemCategoryDTO itemCategoryDTO1 = new ItemCategoryDTO();
                                    BeanUtils.copyProperties(itemCategoryDTO, itemCategoryDTO1);
                                    itemCategoryDTO1.setItemCount(value);
                                    categoryDTOs.add(itemCategoryDTO1);
                                }
                            }

                        } catch (Exception e) {
                            continue;
                        }
                    }
                }

                //添加品牌信息
                itemSearchResultDTO.setItemCategoryDTOList(categoryDTOs);


                SellerBrandQTO sellerBrandQTO = new SellerBrandQTO();
                sellerBrandQTO.setBizCode(itemSearchQTO.getBizCode());

                List<SellerBrandDTO> sellerBrandDTOs = sellerBrandManager.querySellerBrand(sellerBrandQTO);

                List<SellerBrandDTO> brandDTOs = new ArrayList<SellerBrandDTO>();

                FacetField facetField1 = responseX.getFacetField("brand_id");

                List<FacetField.Count> counts1 = facetField1.getValues();

                for (SellerBrandDTO SellerBrandDTO : sellerBrandDTOs) {
                    for (FacetField.Count count : counts1) {

                        try {
                            long id = Long.valueOf(count.getName());

                            if (SellerBrandDTO.getId() == id) {
                                long value = count.getCount();
                                if (value != 0L) {
                                    SellerBrandDTO SellerBrandDTO1 = new SellerBrandDTO();
                                    BeanUtils.copyProperties(SellerBrandDTO, SellerBrandDTO1);
                                    SellerBrandDTO1.setItemCount(value);
                                    brandDTOs.add(SellerBrandDTO1);
                                }
                            }

                        } catch (Exception e) {
                            continue;
                        }
                    }
                }


                //添加品牌信息
                itemSearchResultDTO.setSellerBrandDTOList(brandDTOs);


                System.currentTimeMillis();
            }

            //填充角标url
            fillCornerIconUrl(itemSearchDTOs);

            itemSearchResultDTO.setCount(response.getResults().getNumFound());
            itemSearchResultDTO.setItemSearchDTOList(itemSearchDTOs);

            return itemSearchResultDTO;

        } catch (Exception e) {
            //TODO error handle
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    /**
     * 获取cornerIconUrl
     *
     * @param itemSearchDTOs
     */
    private void fillCornerIconUrl(List<ItemSearchDTO> itemSearchDTOs) {
        if (itemSearchDTOs == null || itemSearchDTOs.isEmpty()) {
            return;
        }

        Map<Long, List<ItemSearchDTO>> cornerIdMap = new HashMap<Long, List<ItemSearchDTO>>();
        for (ItemSearchDTO itemSearchDTO : itemSearchDTOs) {
            if (itemSearchDTO.getCornerIconId() != null && itemSearchDTO.getCornerIconId() > 0) {
                if (cornerIdMap.containsKey(itemSearchDTO.getCornerIconId()) == false) {
                    cornerIdMap.put(itemSearchDTO.getCornerIconId(), new CopyOnWriteArrayList<ItemSearchDTO>());
                }

                cornerIdMap.get(itemSearchDTO.getCornerIconId()).add(itemSearchDTO);
            }
        }

        if (cornerIdMap.isEmpty()) {
            return;
        }

        try {
            //TODO 这里后续如果做了数据库分表之后，就不能直接这么查询了，需要重构
            //批量查询角标信息
            CornerIconQTO cornerIconQTO = new CornerIconQTO();
            cornerIconQTO.setIdList(new CopyOnWriteArrayList<Long>(cornerIdMap.keySet()));
            List<CornerIconDTO> cornerIconDTOs = cornerIconManager.queryCornerIcon(cornerIconQTO);
            if (cornerIconDTOs != null) {
                for (CornerIconDTO cornerIconDTO : cornerIconDTOs) {
                    List<ItemSearchDTO> itemSearchDTOList = cornerIdMap.get(cornerIconDTO.getId());
                    for (ItemSearchDTO itemSearchDTO : itemSearchDTOList) {
                        itemSearchDTO.setCornerIconUrl(cornerIconDTO.getIconUrl());
                    }
                }
            } else {
                //TODO error handle
            }
        } catch (ItemException e) {
            //TODO log
            log.error("", e);
        }
    }

    public boolean deleteItemIndex(Long itemId, Long sellerId) throws ItemException {
        try {
            //TODO itemUid组装逻辑封装到工具类中
            UpdateResponse updateResponse = solrServer.deleteById(sellerId + "_" + itemId);
            //TODO updateRespose处理
            solrServer.commit(true, true);
            return true;
        } catch (Exception e) {
            //TODO error handle
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }

    }

    /**
     * 更新索引
     *
     * @param itemSearchDTO
     * @return
     */
    @Override
    public boolean setItemIndex(ItemSearchDTO itemSearchDTO) {


        List<ItemSearchDTO> itemSearchDTOs = new ArrayList<ItemSearchDTO>();
        itemSearchDTOs.add(itemSearchDTO);

        try {
            this.setItem(itemSearchDTOs);
        } catch (Exception e) {
            //TODO error handle
            log.error("", e);
        }

        return true;
    }


    /**
     * 更新索引
     *
     * @param itemSearchDTOs
     * @return
     */
    @Override
    public boolean setItemIndex(List<ItemSearchDTO> itemSearchDTOs) {

        try {
            this.setItem(itemSearchDTOs);
        } catch (Exception e) {
            //TODO error handle
            log.error("", e);
        }

        return true;
    }


    public String getSearchServerUrl() {
        return searchServerUrl;
    }

    public void setSearchServerUrl(String searchServerUrl) {
        this.searchServerUrl = searchServerUrl;
    }


    private boolean setItem(List<ItemSearchDTO> itemDocs) throws ItemException {
        //TODO param check

        try {
            solrServer.addBeans(itemDocs);
            solrServer.commit(true, true);
        } catch (Exception e) {
            //TODO error handle
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }

        return true;
    }


    public boolean updateItemSalesVolume(List<OrderItemDTO> orderItemDTOs) throws ItemException {

        for (OrderItemDTO orderItemDTO : orderItemDTOs) {

            Long itemId = orderItemDTO.getItemId();
            Long sellerId = orderItemDTO.getSellerId();
            String bizCode = orderItemDTO.getBizCode();
            Integer number = orderItemDTO.getNumber();

            if (orderItemDTO.getItemType() != null && orderItemDTO.getItemType() == DBConst.NORMAL_ITEM.getCode()) {
                updateItemSalesVolume(sellerId, itemId, number, bizCode);
            }
        }

        return true;
    }

    public boolean updateItemSalesVolume(Long sellerId, Long itemId, Integer number, String bizCode) throws ItemException {


        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        itemSearchQTO.setItemUid("" + sellerId + "_" + itemId);
        itemSearchQTO.setBizCode(bizCode);


        ItemSearchResultDTO itemSearchResultDTO = searchItemIndex(itemSearchQTO);

        List<ItemSearchDTO> itemSearchDTOs = itemSearchResultDTO.getItemSearchDTOList();

        if (itemSearchResultDTO.getCount() < 1) {
            return false;
        }


        ItemSearchDTO itemSearchDTO = itemSearchDTOs.get(0);
        itemSearchDTO.setSellerId(sellerId);
        itemSearchDTO.setItemUid("" + sellerId + "_" + itemId);

        //Long salesVolume = itemSalesVolumeManager.getItemSalesVolume(itemSearchDTO);
        itemSearchDTO.setSalesVolume(number.longValue());
        setItemIndex(itemSearchDTO);

        log.info("更新了商品销量 itemId :{}");

        return true;
    }

    @Override
    public boolean updateItemComments(ItemCommentDTO commentDTO) throws ItemException {

        Long sellerId = commentDTO.getSellerId();
        Long itemId = commentDTO.getItemId();
        String bizCode = commentDTO.getBizCode();

        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
        itemSearchQTO.setItemUid("" + sellerId + "_" + itemId);
        itemSearchQTO.setBizCode(bizCode);


        ItemSearchResultDTO itemSearchResultDTO = searchItemIndex(itemSearchQTO);

        List<ItemSearchDTO> itemSearchDTOs = itemSearchResultDTO.getItemSearchDTOList();

        if (itemSearchResultDTO.getCount() < 1) {
            return false;
        }


        ItemSearchDTO itemSearchDTO = itemSearchDTOs.get(0);
        itemSearchDTO.setSellerId(sellerId);
        itemSearchDTO.setItemUid("" + sellerId + "_" + itemId);

        ItemCommentQTO itemCommentQTO = new ItemCommentQTO();

        itemCommentQTO.setItemId(itemId);
        itemCommentQTO.setBizCode(bizCode);


        Long count = itemCommentManager.countItemComment(itemCommentQTO);

        itemSearchDTO.setComments(count);

        setItemIndex(itemSearchDTO);

        log.info("更新了商品评论数 itemId :{}");

        return true;

    }


//    @Override
//    public boolean updateItemDirectCommission(List<RatioDTO> ratioDTOList) throws ItemException {
//
//
//        if (ratioDTOList == null || ratioDTOList.size() < 1) {
//            return false;
//        }
//
//        Map<Long, Double> ratioMap = Maps.newHashMap();
//
//        for (RatioDTO ratioDTO : ratioDTOList) {
//
//            if (ratioDTO.getType().intValue() == 1) {
//                ratioMap.put(ratioDTO.getCategoryId(), ratioDTO.getDirectDistRatio());
//            } else if (ratioDTO.getType().intValue() == 2) {
//                ratioMap.put(ratioDTO.getGroupId(), ratioDTO.getDirectDistRatio());
//            }
//        }
//
//        Integer type = ratioDTOList.get(0).getType().intValue();
//        String bizCode = ratioDTOList.get(0).getBizCode();
//
//        ItemSearchQTO itemSearchQTO = new ItemSearchQTO();
//        itemSearchQTO.setBizCode(bizCode); //固定每页查询五百条
//
//        int count = 500;
//        int offset = 0;
//
//        itemSearchQTO.setCount(count);
//
//        while (true) {
//
//            itemSearchQTO.setOffset(offset);
//
//            ItemSearchResultDTO itemSearchResultDTO = searchItemIndex(itemSearchQTO);
//
//            List<ItemSearchDTO> itemSearchDTOs = itemSearchResultDTO.getItemSearchDTOList();
//
//            for (ItemSearchDTO itemSearchDTO : itemSearchDTOs) {
//
//
//                String itemUid = itemSearchDTO.getItemUid();
//
//                Long itemId = Long.valueOf(itemUid.split("_")[1]);
//
//                Long sellerId = itemSearchDTO.getSellerId();
//
//                ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
//
//                itemSkuQTO.setSellerId(sellerId);
//                itemSkuQTO.setItemId(itemId);
//                itemSkuQTO.setBizCode(bizCode);
//
//                List<ItemSkuDTO> itemSkuDTOList = itemSkuManager.queryItemSku(itemSkuQTO);
//
//                Long maxPrice = 0L;
//
//                Long minPrice = Long.MAX_VALUE;
//
//                if (itemSkuDTOList.size() == 0) {
//                    minPrice = 0L;
//                } else {
//                    for (ItemSkuDTO itemSkuDTO : itemSkuDTOList) {
//
//                        Long price = itemSkuDTO.getPromotionPrice();
//
//                        if (price > maxPrice) {
//                            maxPrice = price;
//                        }
//
//                        if (price < minPrice) {
//                            minPrice = price;
//                        }
//
//                    }
//                }
//
//                Double ratio = null;
//                if (type == 1) {
//                    ratio = ratioMap.get(itemSearchDTO.getCategoryId());
//                } else if (type == 2) {
//                    ratio = ratioMap.get(itemSearchDTO.getGroupId());
//                }
//
//                double directDistRatio = (ratio == null) ? 0 : ratio;
//                itemSearchDTO.setMinDirectCommission((long) (minPrice * directDistRatio));
//                itemSearchDTO.setMaxDirectCommission((long) (maxPrice * directDistRatio));
//
//                setItemIndex(itemSearchDTOs);
//                log.info("更新了商品微小店直接佣金 itemId :{} ratio : {}",itemId,directDistRatio);
//            }
//
//            if (itemSearchDTOs.size() < count) {
//                return true;
//            }
//
//            offset += count;
//        }
//
//    }


    public static void main(String[] args) {
//        ItemSearchManagerImpl itemSearchManager = new ItemSearchManagerImpl();
//        try{
//            List<String> itemUidList = new ArrayList<String>();
//            itemUidList.add("1_502");
//            itemUidList.add("2_503");
//            SolrServer solrServer = new HttpSolrServer("http://114.215.190.87:8080/solr/item_search");
//            UpdateResponse updateResponse = solrServer.deleteById(itemUidList);
//            //TODO updateRespose处理
//            solrServer.commit(true, true);
//        }catch(Exception e){
//            //TODO error handle
//            e.printStackTrace();
//        }
        System.out.print(new StringBuilder("1234567890").delete(6, 10).toString());

    }

    private String filterKeyword(String keyword) {
        return keyword.replace("/", " ");
    }

}

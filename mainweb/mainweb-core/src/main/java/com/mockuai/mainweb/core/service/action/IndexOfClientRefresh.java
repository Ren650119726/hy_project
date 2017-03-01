package com.mockuai.mainweb.core.service.action;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.mainweb.common.domain.dto.index.*;
import com.mockuai.mainweb.common.util.JsonUtil;
import com.mockuai.mainweb.core.manager.ItemManager;
import com.mockuai.mainweb.core.manager.ShopManager;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * Created by Administrator on 2016/9/27.
 */
public class IndexOfClientRefresh {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexOfClientRefresh.class);

    private ShopManager shopManager;

    private ItemManager itemManager;

    public IndexOfClientRefresh(ShopManager shop, ItemManager item) {
        this.shopManager = shop;
        this.itemManager = item;
    }


//    private static final String indexUrl = "http://act.haiyn.com/data/{index}.json";
    private final static String OUTPUT_PREFIX = "{\"code\":10000,\"msg\":\"success\",\"data\":{\"component\":xxx}}";

    private final static String TPL = "xxx";
    private static final String VALUE = "value";
    private static final String VALUE_TYPE = "valueType";
    static {
        GsonBuilder gb = new GsonBuilder();
        gb.disableHtmlEscaping();
        gb.setFieldNamingPolicy(
                FieldNamingPolicy.IDENTITY);
        gson = gb.create();
    }
    private enum IndexValueTypeEnum {
        FOUR_ITEM_NAV(1, "fourItemNav") {
            @Override
            public List<FourItemNavDTO> parseJson(String data) {
                java.lang.reflect.Type type = new TypeToken<List<FourItemNavDTO>>() {
                }.getType();
                return gson.fromJson(data, type);
            }
        },
        DIVIDER_BLANK(2, "dividerBlank") {
            @Override
            public DividerBlankDTO parseJson(String data) {
                return gson.fromJson(data, DividerBlankDTO.class);
            }
        },
        DIVIDER_LINE(3, "dividerLine") {
            @Override
            public DividerLineDTO parseJson(String data) {
                return gson.fromJson(data, DividerLineDTO.class);
            }
        },
        COMPONENT_TITLE(4, "componentTitle") {
            @Override
            public ComponentTitleDTO parseJson(String data) {
                return gson.fromJson(data, ComponentTitleDTO.class);
            }
        },
        PRODUCT(5, "product") {
            @Override
            public ProductDTO parseJson(String data) {
                return gson.fromJson(data, ProductDTO.class);
            }
        },
        IMAGE(6, "image") {
            @Override
            public ImageDTO parseJson(String data) {
                return gson.fromJson(data, ImageDTO.class);
            }
        },
        CARD(7, "card") {
            @Override
            public CardDTO parseJson(String data) {
                return gson.fromJson(data, CardDTO.class);
            }
        },
        MARQUEE(8, "marquee") {
            @Override
            public MarqueeDTO parseJson(String data) {
                return gson.fromJson(data, MarqueeDTO.class);
            }
        },
        IMAGE_BANNER(9, "imageBanner") {
            @Override
            public List<ImageBannerDTO> parseJson(String data) {
                java.lang.reflect.Type type = new TypeToken<List<ImageBannerDTO>>() {
                }.getType();
                return gson.fromJson(data, type);
            }
        },
        HORIZONTAL_SCROLL(10, "horizontalScroll") {
            @Override
            public <T> T parseJson(String data) {
                java.lang.reflect.Type type = new TypeToken<IndexHorizontalScrollDTO>() {
                }.getType();
                return gson.fromJson(data, type);
            }
        };

        private static Map<String, IndexOfClientRefresh.IndexValueTypeEnum> map = new LinkedHashMap<>();

        static {
            for (IndexOfClientRefresh.IndexValueTypeEnum indexValueTypeEnum : IndexOfClientRefresh.IndexValueTypeEnum.values()) {
                map.put(indexValueTypeEnum.getName(), indexValueTypeEnum);
            }
        }

        private String name;
        private int value;

        IndexValueTypeEnum(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public static IndexOfClientRefresh.IndexValueTypeEnum getEnumByName(String name) {
            return map.get(name);
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public abstract <T> T parseJson(String data);

    }
    public static Gson gson;

    public String refresh(String data,String appKey) {

        String result = OUTPUT_PREFIX.replace(TPL,data);
//        String pattern = "\\{index\\}";


        StringBuilder sbRes = new StringBuilder();

        try {
//			StopWatch stopWatch = new StopWatch();
//			stopWatch.start();

//			result = com.mockuai.mop.core.common.util.HttpUtil.get(indexUrl.replaceFirst(pattern, pageTitleIdStr), params);
//            result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            String vv = result.substring(result.indexOf("[") + 1, result.lastIndexOf("]"));
//			LOGGER.info("http : {}", stopWatch.getTime() / 1000.0);
            sbRes = new StringBuilder(result.substring(0, result.indexOf('[') + 1));

            String valueType;
            String value;
            int leftCurl = 0;
            int index = 0;
            for (int i = 0; i < vv.length(); i++) {
                if ('{' == vv.charAt(i)) {
                    if (leftCurl == 0)
                        index = i;
                    leftCurl++;
                    continue;
                }
                if ('}' == vv.charAt(i)) {
                    leftCurl--;
                    if (leftCurl == 0) {
                        value = vv.substring(index, i + 1);
                        valueType = value.substring(value.indexOf("\":\"") + 3, value.indexOf("\",\""));
                        if (IndexOfClientRefresh.IndexValueTypeEnum.HORIZONTAL_SCROLL.getName().equals(valueType)) {
                            value = value.substring(value.indexOf("value\":") + 7, value.lastIndexOf("}"));
                            String prd = fillUpProductionInfo(valueType, value, appKey);
                            if (!"".equals(prd)) {
                                sbRes.append(prd);
                            }
//							LOGGER.info("product : {}", stopWatch.getTime() / 1000.0);
                        } else {
                            sbRes.append(value);
                        }
                        if (i < vv.length() - 1) {
                            sbRes.append(",");
                        } else {
                            sbRes.append("]}}");
                        }
                    }
                }
            }
//			stopWatch.stop();
        } catch (Exception e) {
            LOGGER.error("", e);
            throw new RuntimeException("编解码的地方",e);
        }
        return sbRes.toString();
    }
    public String fillUpProductionInfo(String valueType, String value, String appKey) {

//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
        Map<String, Object> valuePair = new HashMap<>();
        valuePair.put(VALUE_TYPE, valueType);

        try {
//			JsonUtil
            ItemGroupUid itemGroupUid = JsonUtil.parseJson(value, ItemGroupUid.class);
            String[] strs = itemGroupUid.getItemGroupUid().split("_");
            Long sellerId = Long.valueOf(strs[0]);
            Long groupId = Long.valueOf(strs[1]);
            ShopItemGroupDTO shopItemGroupDTO = shopManager.getShopItemGroup(sellerId, groupId, "1", appKey);
//			LOGGER.info("shopManager : {}", stopWatch.getTime() / 1000.0);
            if (shopItemGroupDTO == null
                    || shopItemGroupDTO.getItemIdList() == null
                    || shopItemGroupDTO.getItemIdList().isEmpty()) {
                LOGGER.error("error to getShopItemGroup, it's null, sellerId : {}, groupId : {}, appKey : {}",
                        sellerId, groupId, appKey);
                return gson.toJson(valuePair);
            }
            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setSellerId(0L);
            itemQTO.setIdList(shopItemGroupDTO.getItemIdList());
            List<ItemDTO> itemDTOs = itemManager.queryItem(itemQTO, appKey);
            if (itemDTOs == null || itemDTOs.size() != itemQTO.getIdList().size()) {
                LOGGER.error("error to queryItem, itemQTO : {}, appkey : {}",
                        JsonUtil.toJson(itemQTO), appKey);
                return gson.toJson(valuePair);
            }
//			LOGGER.info("itemManager : {}", stopWatch.getTime() / 1000.0);
//			LOGGER.info("appManager : {}", stopWatch.getTime());
            String domain = "http://m.haiyn.com/";
            IndexHorizontalScrollDTO indexHorizontalScrollDTO = new IndexHorizontalScrollDTO();
            List<IndexProductDTO> productDTOs = new ArrayList<>();
            IndexProductDTO indexProductDTO;
            for (ItemDTO itemDTO : itemDTOs) {
                indexProductDTO = new IndexProductDTO();
                indexProductDTO.setImageUrl(itemDTO.getIconUrl());
                indexProductDTO.setTargetUrl(domain + "detail.html?item_uid=" + itemDTO.getSellerId() + "_" + itemDTO.getId());
                indexProductDTO.setMarketPrice(itemDTO.getMarketPrice());
                indexProductDTO.setWirelessPrice(itemDTO.getWirelessPrice());
                indexProductDTO.setText(itemDTO.getItemName());
//				indexProductDTO.setSupplyPlace();
                productDTOs.add(indexProductDTO);
            }
            indexHorizontalScrollDTO.setProductList(productDTOs);
            valuePair.put(VALUE, indexHorizontalScrollDTO);
        } catch (Exception e) {
            LOGGER.error("error to parse item_group_uid, {}", value, e);
            return "";
        }
        return gson.toJson(valuePair);
    }







}

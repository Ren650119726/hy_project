package com.mockuai.marketingcenter.common.domain.qto;

import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**spu级入参
 * Created by huangsiqian on 2016/10/13.
 */
public class TimePurchaseGoodsQTO implements Serializable{
    private Long activityId;
    private Long itemId;
    private String itemName;
    //spu商品信息
    private Map map;
    //sku商品集合
    private List<TimePurchaseSKUGoodsQTO> skuInfo;
    private List<TimePurchaseGoodsQTO> goodsInfo;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<TimePurchaseGoodsQTO> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<TimePurchaseGoodsQTO> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }



    public List<TimePurchaseSKUGoodsQTO> getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(List<TimePurchaseSKUGoodsQTO> skuInfo) {
        this.skuInfo = skuInfo;
    }
}

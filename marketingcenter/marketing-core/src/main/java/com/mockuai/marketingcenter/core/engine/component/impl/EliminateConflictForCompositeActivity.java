package com.mockuai.marketingcenter.core.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.util.Context;
import com.mockuai.marketingcenter.core.engine.component.Component;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ComponentType.ELIMINATE_CONFLICT_FOR_COMPOSITE_ACTIVITY;

/**
 * 排除商城级别商品后没有符合满减送, 返回 true
 * Created by edgar.zr on 1/27/2016.
 */
@Service
public class EliminateConflictForCompositeActivity implements Component {

    public static Context wrapParams(DiscountInfo discountInfo, List<MarketItemDTO> bizItemList, List<MarketItemDTO> itemList) {
        Context context = new Context();
        context.setParam("discountInfo", discountInfo);
        context.setParam("bizItemList", bizItemList);
        context.setParam("itemList", itemList);
        context.setParam("component", ELIMINATE_CONFLICT_FOR_COMPOSITE_ACTIVITY);
        return context;
    }

    @Override
    public void init() {

    }

    /**
     * bizItemList 商城商品级别的商品列表,且符合有满减送
     * discountInfo 带判断是否与 bizItemList 冲突的店铺满减送
     * itemList 店铺订单中的所有商品列表
     *
     * @param context
     * @return
     * @throws MarketingException
     */
    @Override
    public List<MarketItemDTO> execute(Context context) throws MarketingException {
        List<MarketItemDTO> bizItemList = (List<MarketItemDTO>) context.getParam("bizItemList");
        DiscountInfo discountInfo = (DiscountInfo) context.getParam("discountInfo");
        List<MarketItemDTO> itemList = (List<MarketItemDTO>) context.getParam("itemList");

        List<MarketItemDTO> tempBiz = new ArrayList<MarketItemDTO>(bizItemList == null ? Collections.<MarketItemDTO>emptyList() : bizItemList);
        List<MarketItemDTO> tempItem = new ArrayList<MarketItemDTO>(discountInfo.getItemList().isEmpty() ? itemList : discountInfo.getItemList());
        List<MarketItemDTO> originItemList = new ArrayList<MarketItemDTO>(tempItem);

        // 店铺满减送关联的商品列表
        int count = tempItem.size();
        // 商城满减送列表与 tempItem 交集
        tempItem.retainAll(tempBiz);
        // 如果全相同,店铺满减送无效,被商城商品取代
        if (tempItem.size() == count)
            return Collections.emptyList();
        // 移除被替换商品,只有未被替换商品继续参与店铺满减送
        originItemList.removeAll(tempItem);
        return originItemList;
    }

    @Override
    public String getComponentCode() {
        return ELIMINATE_CONFLICT_FOR_COMPOSITE_ACTIVITY.getCode();
    }
}
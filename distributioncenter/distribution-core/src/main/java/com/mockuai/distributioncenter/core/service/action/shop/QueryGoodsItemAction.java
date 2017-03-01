package com.mockuai.distributioncenter.core.service.action.shop;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.GoodsItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.marketingcenter.common.domain.qto.PageQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by 冠生 on 5/20/16.
 * 查看商品
 */
@Service
public class QueryGoodsItemAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryGoodsItemAction.class);

    @Autowired
    private ShopManager shopManager;
     @Autowired
    private SellerManager sellerManager;


    /**
     * Long sellerId, Long groupId, String appKey, int currentPage, int pageSize
     * @param context
     * @return
     * @throws DistributionException
     */
    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        String appKey = request.getString("appKey");
        Long   groupId =  request.getLong("groupId");
        Long   sellerId = request.getLong("sellerId");
        Long   userId = request.getLong("userId");
        String    offsetStr = request.getString("offset");
        String    countStr = request.getString("count");
        String itemName = request.getString("itemName");
        Integer orderByInt = request.getInteger("orderBy");
        Integer desc = request.getInteger("desc");

        if (groupId == null) {
            log.error("groupId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }
        if (sellerId == null) {
            log.error("sellerId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }
        if(orderByInt == null){
            orderByInt = 1;
        }
        int offset , count ;
        if(StringUtils.hasText(offsetStr)){
           offset = Integer.parseInt(offsetStr);
        }else{
            offset = PageQTO.DEFAULT_OFFSET;
        }
        if(StringUtils.hasText(countStr)){
            count = Integer.parseInt(countStr);
        }else{
            count = PageQTO.DEFAULT_COUNT;
        }
        if(desc == null){
            desc = 1;
        }

        String orderBy;
        switch (orderByInt){
            default:
            case  1:
                orderBy =  "sales_volume";
                break;
            case 2:
                orderBy =  "sale_commission";
                break ;
            case 3:
                orderBy = "promotion_price";
                break ;
        }
        try {
            SellerDTO sellerDTO = sellerManager.getByUserId(userId);
            boolean isSeller = sellerDTO != null;
            if(groupId == 0){
                return shopManager.getAllItem(isSeller,offset,count,appKey);
            }
            return shopManager.queryGoodsList(isSeller,appKey,sellerId,groupId,offset,count,itemName,orderBy,desc);

        }catch (DistributionException e){
           return new DistributionResponse(e.getCode(),e.getMessage());
        }
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_GOODS_ITEM.getActionName();
    }
}

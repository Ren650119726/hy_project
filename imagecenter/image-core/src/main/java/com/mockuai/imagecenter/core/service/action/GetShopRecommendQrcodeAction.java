package com.mockuai.imagecenter.core.service.action;


import com.beust.jcommander.internal.Maps;
import com.mockuai.imagecenter.common.api.action.ImageRequest;
import com.mockuai.imagecenter.common.api.action.ImageResponse;
import com.mockuai.imagecenter.common.constant.ActionEnum;
import com.mockuai.imagecenter.core.service.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 冠生 on 2016/7/8.
 * 获取推荐和店铺二维码
 */
@Service
public class GetShopRecommendQrcodeAction implements   Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetShopRecommendQrcodeAction.class.getName());

    @Autowired
    private  GetQrcodeAction getQrcodeAction;
    @Autowired
    private AppContext appContext;

    @Override
    public ImageResponse execute(RequestContext context) {
        Map<String,Object> resultMap = Maps.newHashMap();
        ImageRequest request =   context.getRequest();
        RequestContext requestRecommend    ;
        RequestContext requestShop;
        ImageRequest recommendRequest = new ImageRequest();
        ImageRequest shopRequest = new ImageRequest();

        recommendRequest.setParam("type","RECOMMEND");
        recommendRequest.setParam("id",request.getParam("id"));
        shopRequest.setParam("type","SHOP");
        shopRequest.setParam("id",request.getParam("id"));
        requestRecommend = new RequestContext(appContext,recommendRequest);
        requestShop = new RequestContext(appContext,shopRequest);
        ImageResponse recommendResponse = getQrcodeAction.execute(requestRecommend);
        ImageResponse shopResponse = getQrcodeAction.execute(requestShop);
        resultMap.put("recommend",recommendResponse.getModule());
        resultMap.put("shop",shopResponse.getModule());
        return new ImageResponse<>(resultMap);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SHOP_RECOMMEND_QRCODE.getActionName();
    }
}

package com.mockuai.marketingcenter.core.service.action.activity.TimePurchase;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.LimitOderInfoDTO;
import com.mockuai.marketingcenter.common.domain.qto.ViewSalesQTO;
import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitOderInfoManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import com.mockuai.shopcenter.StoreClient;
import com.mockuai.suppliercenter.client.StoreItemSkuClient;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderDiscountInfoDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

/**查看限时购销售
 * Created by huangsiqian on 2016/11/3.
 */
@Service
public class ViewSalesAction extends TransAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewSalesAction.class);

    @Autowired
     private  LimitOderInfoManager limitOderInfoManager;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private StoreItemSkuClient storeItemSkuClient;
    @Override
    public String getName() {
        return ActionEnum.VIEW_SALES.getActionName();
    }

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        ViewSalesQTO viewSalesQTO = (ViewSalesQTO) context.getRequest().getParam("viewSalesQTO");
        String appKey = (String) context.getRequest().getParam("appKey");

        Long activityId = viewSalesQTO.getActivityId();
        LimitOderInfoDO infoDO = new LimitOderInfoDO();
        List<LimitOderInfoDO> list = (List<LimitOderInfoDO>)limitOderInfoManager.queryInfoDtoById(activityId);
        List orderList = new ArrayList();
        for(LimitOderInfoDO limitOderInfoDO: list){
            orderList.add(limitOderInfoDO.getOrderId());
        }
        if(orderList.isEmpty()){
            return MarketingUtils.getSuccessResponse(null);
        }
        Integer offset = viewSalesQTO.getOffset();
        if (offset == null) {
            offset = 0;
        }
        Integer count = viewSalesQTO.getCount();
        if (count == null || count > 20) {
            count = 20;
        }
        OrderQTO orderQTO = new OrderQTO();
        orderQTO.setOrderIds(orderList);
        orderQTO.setOffset((offset-1)*count);
        orderQTO.setCount(count);
        LOGGER.info("orderlist{}, ",orderQTO.getOrderIds());
        com.mockuai.tradecenter.common.api.Response<List<OrderDTO>> orderResponse =  this.orderClient.queryAllOrderBg(orderQTO, appKey);
        List<OrderDTO> orders = orderResponse.getModule();
        //取出订单过滤订单信息
        List<ViewSalesQTO> viewSalesList = new ArrayList<>();
        for(OrderDTO orderDTO:orders){
            List<OrderItemDTO> orderItems = orderDTO.getOrderItems();
            for(OrderItemDTO orderItem: orderItems){
                ViewSalesQTO views = new ViewSalesQTO();
                views.setOrderSn(orderDTO.getOrderSn());
                views.setPayTime(orderDTO.getPayTime());
                views.setGoodsName(orderItem.getItemName()+orderItem.getItemSkuDesc());
                views.setGoodsNum(orderItem.getNumber());
                views.setLimitedPrice(orderItem.getUnitPrice());
                com.mockuai.usercenter.common.api.Response<UserDTO> res = userClient.getUserById(orderItem.getUserId(), appKey);
                views.setUserPhone(res.getModule().getMobile());
                //如果分享人id为空，则不返回前端分享人信息
                    LOGGER.info("share userId:{}",orderItem.getShareUserId());
                if(orderItem.getShareUserId()!=null){
                    com.mockuai.usercenter.common.api.Response<UserDTO> res2 = userClient.getUserById(orderItem.getShareUserId(),appKey);
                    views.setSharePhone(res2.getModule().getMobile());
                }
                Long itemSkuId = orderItem.getItemSkuId();
                Long storeId = orderDTO.getStoreId();
                StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setStoreId(storeId);
                storeItemSkuQTO.setItemSkuId(itemSkuId);
                com.mockuai.suppliercenter.common.api.Response<StoreItemSkuDTO> itemSkuDTORes
                        = this.storeItemSkuClient.getItemSku(storeItemSkuQTO, appKey);
                views.setGoodsId(itemSkuDTORes.getModule().getSupplierItmeSkuSn());
                viewSalesList.add(views);
            }

        }
        LOGGER.info("viewSalesList INFO:{},totalCout:{}", JsonUtil.toJson(viewSalesList),(Long)orderResponse.getTotalCount());
        Integer totalCount = null;
        if((Long)orderResponse.getTotalCount()!=null) {
            totalCount = Integer.parseInt(String.valueOf(orderResponse.getTotalCount()));
        }else{
            totalCount=1;
        }

        return MarketingUtils.getSuccessResponse(viewSalesList,totalCount);
    }
}

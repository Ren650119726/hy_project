package com.mockuai.distributioncenter.core.service.action.message;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.DistributeSource;
import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.constant.DistributionType;
import com.mockuai.distributioncenter.common.constant.Operator;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionItemDTO;
import com.mockuai.distributioncenter.common.domain.dto.DistributionOrderDTO;
import com.mockuai.distributioncenter.common.domain.dto.OperationDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.AppManager;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.ImageManager;
import com.mockuai.distributioncenter.core.manager.MarketingManager;
import com.mockuai.distributioncenter.core.manager.MessageRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.manager.ShopManager;
import com.mockuai.distributioncenter.core.manager.TradeManager;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.distributioncenter.core.manager.VirtualWealthManager;
import com.mockuai.distributioncenter.core.message.MessageProducer;
import com.mockuai.distributioncenter.core.message.Topicer;
import com.mockuai.distributioncenter.core.message.msg.PaySuccessMsg;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;

/**
 * Created by duke on 16/3/7.支付成功
 */
@Service
public class FenyongOrderSuccessAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(FenyongOrderSuccessAction.class);

    private static final String inviterUrl = "http://m.haiyn.com/merchant-login.html?inviter_seller_id=%s&real_name=%s";
    private static final String shopUrl = "http://m.haiyn.com/index.html?distributor_id=%s";

    @Autowired
    private MessageRecordManager messageRecordManager;

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private VirtualWealthManager virtualWealthManager;

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private MarketingManager marketingManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private Topicer topicer;
    
    @Autowired
    private AppManager appManager;
    
    @Autowired
    private TradeManager tradeManager;
    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = new RequestAdapter(context.getRequest());
        
        
        Long orderId = request.getLong("orderId");
        String appKey = request.getString("appKey");
        Long userId = request.getLong("userId");
        PaySuccessMsg paySuccessMsg = this.parse(orderId, userId, appKey);

        
        log.info("start do pay success listener action transaction, paySuccessMsg: {}", JsonUtil.toJson(paySuccessMsg.getDistributionOrderDTO()));


        DistributionOrderDTO distributionOrderDTO = paySuccessMsg.getDistributionOrderDTO();

       
        Map<Long, DistributionItemDTO> itemSkuMap = new HashMap<Long, DistributionItemDTO>();
        for (DistributionItemDTO itemDTO : distributionOrderDTO.getItemDTOs()) {
            itemSkuMap.put(itemDTO.getItemSkuId(), itemDTO);
        }

        DistRecordQTO distRecordQTO = new DistRecordQTO();
        distRecordQTO.setOrderId(distributionOrderDTO.getOrderId());
        List<DistRecordDTO> distRecordDTOs =
                distRecordManager.query(distRecordQTO);

        if (distRecordDTOs != null && !distRecordDTOs.isEmpty()) {
        	log.info("++++++++++++++++++++++++:"+distributionOrderDTO.getPaymentId()+"qqqqqqqqq");
        	//判断支付类型是否是嗨币支付
        	if(distributionOrderDTO.getPaymentId() != 12){
        		log.info("++++++++++++++++++++++++:"+distributionOrderDTO.getPaymentId()+"ssssss");
	        	
            // 处理开店注册成功后，激活卖家
            if (distRecordDTOs.get(0).getSource().equals(DistributeSource.OPEN_SHOP_DIST.getSource())) {
                DistRecordDTO distRecordDTO = distRecordDTOs.get(0);
                // 更新关系和卖家状态
                SellerDTO sellerDTO = sellerManager.getByUserId(distRecordDTO.getBuyerId());
                sellerDTO.setStatus(1);
                sellerManager.update(sellerDTO);

                SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(distRecordDTO.getBuyerId());
                relationshipDTO.setStatus(1);
                sellerRelationshipManager.update(relationshipDTO);

                marketingManager.grantActivityCouponWithNumber(1L, sellerDTO.getUserId(), 10, appKey);

                // 更新卖家的用户权限为卖家
                userManager.updateRoleType(sellerDTO.getUserId(), 2L, appKey);

                // 生成卖家推荐二维码
                try {
                    imageManager.addRecommendImage(sellerDTO.getUserId(), String.format(inviterUrl, sellerDTO.getId(), URLEncoder.encode(sellerDTO.getRealName(), "UTF-8")), appKey);
                } catch (UnsupportedEncodingException e) {
                    log.error("unsupported encoding exception: {}", e.getMessage());
                }
                // 生成店铺推荐二维码
                imageManager.addShopImage(sellerDTO.getUserId(), String.format(shopUrl, sellerDTO.getId()), appKey);

                // 异步处理直接下级的数量和团队的数量
                OperationDTO operationDTO = new OperationDTO();
                operationDTO.setAppKey(appKey);
                operationDTO.setOperator(Operator.ADD.getOp());
                operationDTO.setUserId(relationshipDTO.getParentId());
                operationDTO.setDirectCount(1L);
                operationDTO.setGroupCount(1L);
                // 使用关系ID作为消息的唯一标识
                operationDTO.setMsgIdentify(String.valueOf(relationshipDTO.getId()));
                messageProducer.send(topicer.getValue(), "update", operationDTO.getMsgIdentify(), operationDTO);
            }
            for (DistRecordDTO dto : distRecordDTOs) {
                log.info("update record: {}", JsonUtil.toJson(dto));
                // 处理支付成功的分拥记录
                SellerDTO sellerDTO = sellerManager.get(dto.getSellerId());
                dto.setStatus(DistributionStatus.FROZEN_DISTRIBUTION.getStatus());
                distRecordManager.update(dto);
                // 发放余额
                GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
                grantedWealthDTO.setAmount(dto.getDistAmount());
                grantedWealthDTO.setOrderId(dto.getOrderId());
                grantedWealthDTO.setOrderSN(dto.getOrderSn());
                grantedWealthDTO.setReceiverId(sellerDTO.getUserId());
                grantedWealthDTO.setGranterId(0L);
                grantedWealthDTO.setStatus(GrantedWealthStatus.FROZEN.getValue());
                if (dto.getSource().equals(DistributeSource.OPEN_SHOP_DIST.getSource())) {
                    // 开店分拥
                    grantedWealthDTO.setSourceType(SourceType.SHOP.getValue());
                    SellerDTO seller = sellerManager.getByUserId(dto.getBuyerId());
                    DistShopDTO shopDTO = shopManager.getBySellerId(seller.getId());
                    grantedWealthDTO.setShopId(shopDTO.getId());
                    // 开店分拥直接进入完成状态，不会进入冻结状态
                    grantedWealthDTO.setStatus(GrantedWealthStatus.TRANSFERRED.getValue());
                    grantedWealthDTO.setText(shopDTO.getShopName());
                }
                else if (dto.getSource().equals(DistributeSource.SALE_DIST.getSource())) {
                    // 销售分拥
                    grantedWealthDTO.setSourceType(SourceType.SELL.getValue());
                    grantedWealthDTO.setItemId(dto.getItemId());
                    grantedWealthDTO.setSkuId(dto.getItemSkuId());
                    DistributionItemDTO itemDTO = itemSkuMap.get(dto.getItemSkuId());
                    grantedWealthDTO.setText(itemDTO.getItemName());
                }
                else if (dto.getSource().equals(DistributeSource.TEAM_DIST.getSource())) {
                    // 团队分拥
                    grantedWealthDTO.setSourceType(SourceType.GROUP_SELL.getValue());
                    grantedWealthDTO.setItemId(dto.getItemId());
                    grantedWealthDTO.setSkuId(dto.getItemSkuId());
                    DistributionItemDTO itemDTO = itemSkuMap.get(dto.getItemSkuId());
                    grantedWealthDTO.setText(itemDTO.getItemName());
                }
                if (dto.getType().equals(DistributionType.REAL_AMOUNT.getType())) {
                    grantedWealthDTO.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
                } else {
                    grantedWealthDTO.setWealthType(WealthType.HI_COIN.getValue());
                }
                log.info("grant virtual wealth: {}", JsonUtil.toJson(grantedWealthDTO));
                virtualWealthManager.distributorGrant(grantedWealthDTO, appKey);
            }
        }
        } else {
            // 订单不存在
            log.warn("calculate record not exists, orderSn: {}", distributionOrderDTO.getOrderSn());
            return new DistributionResponse(false);
        }

        return new DistributionResponse(true);
    }

    @Override
    public String getName() {
        return ActionEnum.FENYONG_ORDER_SUCCESS.getActionName();
    }
    
    
    private PaySuccessMsg parse(Long orderId,Long buyerId,String appKey){


        OrderDTO orderDTO;
        try {
            orderDTO = tradeManager.getOrder(orderId, buyerId, appKey);
        } catch (DistributionException e) {
            log.error("get order by orderId: {}, buyerId: {} error", orderId, buyerId);
            return null;
        }

        PaySuccessMsg paySuccessMsg = new PaySuccessMsg();
        paySuccessMsg.setAppKey(appKey);

        DistributionOrderDTO distributionOrderDTO = new DistributionOrderDTO();
        distributionOrderDTO.setOrderId(orderId);
        distributionOrderDTO.setOrderSn(orderDTO.getOrderSn());
        distributionOrderDTO.setAppKey(appKey);
        distributionOrderDTO.setUserId(buyerId);
        distributionOrderDTO.setPaymentId(orderDTO.getPaymentId());
        List<DistributionItemDTO> distributionItemDTOs =  new ArrayList<DistributionItemDTO>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            DistributionItemDTO distributionItemDTO = new DistributionItemDTO();
            distributionItemDTO.setItemId(orderItemDTO.getItemId());
            distributionItemDTO.setItemSkuId(orderItemDTO.getItemSkuId());
            distributionItemDTO.setNumber(orderItemDTO.getNumber());
            distributionItemDTO.setUnitPrice(orderItemDTO.getUnitPrice());
            distributionItemDTO.setSellerId(orderItemDTO.getDistributorId());
            distributionItemDTO.setItemName(orderItemDTO.getItemName());
            distributionItemDTOs.add(distributionItemDTO);
        }
        distributionOrderDTO.setItemDTOs(distributionItemDTOs);

        paySuccessMsg.setDistributionOrderDTO(distributionOrderDTO);

        log.info("parse paySuccess msg: {}", JsonUtil.toJson(paySuccessMsg.getDistributionOrderDTO()));
        return paySuccessMsg;
    
    }
}

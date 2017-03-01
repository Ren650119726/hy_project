package com.mockuai.tradecenter.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.OrderServiceDTO;
import com.mockuai.tradecenter.common.enums.EnumRefundStatus;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.domain.OrderConsigneeDO;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.OrderServiceDO;
import com.mockuai.tradecenter.core.domain.OrderStoreDO;
import com.mockuai.tradecenter.core.domain.OrderTogetherDTO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * @author hzmk
 */
public class TradeUtil {
    private static final Logger log = LoggerFactory.getLogger(TradeUtil.class);

    public class RefundMark {
        public static final int REFUNDING_MARK = 1;  // 被退款
        public static final int NO_REFUND = 0; //

    }

    public class DeliveryMark {
        public static final int NO_DELIVERY = 0; //
        public static final int SHIPPED = 1;
    }

    public class WithdrawType {
        public static final int SHOP = 1;
        public static final int MALL = 2;
    }

    public class ShopType {
        public static final int SINGLE_SHOP = 1;
        public static final int SUB_SHOP = 2;
        public static final int MALL = 3;
    }

    public class WithdrawAuditStatus {
        public static final int WAIT = 1;
        public static final int REFUSE = 2;
        public static final int PASS = 3;
    }

    public class FrozenType {
        public static final int FINE = 4;
    }

    public class TradeNotifyLogStatus {
        public static final int SUCCESS = 2;
        public static final int FAILED = 3;
    }

    public class TradeNotifyLogType {
        public static final int PAYMENT = 1;
        public static final int REFUND = 2;

    }

    public static Integer getCanRefundMark(Integer openRefundMark, Integer refundStatus, Date receiptTime, Long rightsTimeOutDays) {
        if (openRefundMark == 0) {
            return 0;
        }
        if (null == rightsTimeOutDays)
            rightsTimeOutDays = 15l;
        boolean canRefund = true;

//        if (refundStatus != null && (refundStatus == 1 || refundStatus == 2 || refundStatus == 4)) {
        /*订单申请退款、退快中，退款完成状态不可申请售后*/
        if (refundStatus != null && (refundStatus == Integer.parseInt(EnumRefundStatus.APPLY.getCode()) || refundStatus == Integer.parseInt(EnumRefundStatus.REFUNDING.getCode()) || refundStatus == Integer.parseInt(EnumRefundStatus.REFUND_FINISHED.getCode()))) {
        	
        	log.info(" canRefund change : "+canRefund);
            canRefund = false;
        } else {
        	// TODO 可优化
            if (receiptTime != null) {
                Date now = new Date();
                if (now.getTime() > (receiptTime.getTime() + rightsTimeOutDays * 24 * 3600 * 1000)) {
                	log.info(" canRefund change some now.getTime(): "+now.getTime()+" receiptTime.getTime():"+receiptTime.getTime()+" rightsTimeOutDays:"+rightsTimeOutDays * 24 * 3600 * 1000);
                	canRefund = false;
                    log.info(" canRefund change if : "+canRefund);
                }
                now = null;
            }
        }
        log.info(" canRefund end : "+canRefund);
        if (canRefund) {
            return 1;
        } else {
            return 0;
        }


    }


    public static Long getMallAllDiscountAmt(List<OrderDiscountInfoDO> discountInfoDOs) {
        long totalDiscountAmount = 0;
        if (null == discountInfoDOs || discountInfoDOs.isEmpty()) {
            return totalDiscountAmount;
        }
        for (OrderDiscountInfoDO orderDiscountInfoDO : discountInfoDOs) {
            if (orderDiscountInfoDO.getDiscountType() == 2) {
                totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
            }
            if (orderDiscountInfoDO.getDiscountType() == 1) {
                totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    public static WealthAccountDTO getWealthAccountDTO(List<WealthAccountDTO> wealthAccoutsDTOs, Integer wealthType) {
        for (WealthAccountDTO wealthAccountDTO : wealthAccoutsDTOs) {
            if (wealthAccountDTO.getWealthType().intValue() == wealthType.intValue()) {
                return wealthAccountDTO;
            }
        }
        return null;
    }

    public static List<OrderItemDO> genOrderItemDOList(List<OrderItemDTO> orderItemDTOs) {
        if (orderItemDTOs == null) {
            return null;
        }

        List<OrderItemDO> orderItemDOs = new ArrayList<OrderItemDO>();
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            orderItemDOs.add(genOrderItemDO(orderItemDTO));
        }

        return orderItemDOs;
    }

    public static OrderItemDO genOrderItemDO(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        }

        OrderItemDO orderItemDO = new OrderItemDO();
        try{
            BeanUtils.copyProperties(orderItemDO, orderItemDTO);
        }catch(Exception e) {
            log.error("error to copy properties", e);
        }

        return orderItemDO;
    }

    public static OrderItemDO genOrderItemDO(OrderDTO orderDTO, OrderItemDTO orderItemDTO, ItemSkuDTO itemSkuDTO,
                                             ItemDTO itemDTO, String userName) {
        OrderItemDO orderItemDO = new OrderItemDO();
        orderItemDO.setUserId(orderDTO.getUserId());
        orderItemDO.setUserName(userName);
        orderItemDO.setSellerId(itemDTO.getSellerId());

        Long itemServicePrice = TradeUtil.getItemServicePrice(orderItemDTO);
        if(null==itemServicePrice){
            itemServicePrice = 0L;
        }

        int number = orderItemDTO.getNumber();
        orderItemDO.setNumber(number);
        orderItemDO.setItemType(itemDTO.getItemType());
        orderItemDO.setItemName(itemDTO.getItemName());
        orderItemDO.setItemSkuDesc(itemSkuDTO.getSkuCode());
        orderItemDO.setUnitPrice(itemSkuDTO.getPromotionPrice());
        orderItemDO.setServiceUnitPrice(itemServicePrice);
        orderItemDO.setItemSkuId(itemSkuDTO.getId());
        orderItemDO.setItemId(itemSkuDTO.getItemId());
        orderItemDO.setItemImageUrl(itemDTO.getIconUrl());
        orderItemDO.setDeliveryType(itemDTO.getDeliveryType());
        orderItemDO.setCategoryId(itemDTO.getCategoryId());
        orderItemDO.setItemBrandId(itemDTO.getItemBrandId());
        orderItemDO.setActivityId(orderItemDTO.getActivityId());
        // 分享人id
        orderItemDO.setShareUserId(orderItemDTO.getShareUserId());
        
        orderItemDO.setHigoMark(itemDTO.getHigoMark());
        orderItemDO.setHigoExtraInfo(JsonUtil.toJson(itemDTO.getHigoExtraInfo()));//转成json格式
        if(null!=orderItemDTO.getOrderServiceList()&&orderItemDTO.getOrderServiceList().size()!=0){
            orderItemDO.setOrderServiceDOList(convert2OrderServiceDOList(orderItemDTO.getOrderServiceList()));
        }
        if(null==itemDTO.getItemType()){ //如果没有itemType当成普通商品
            orderItemDO.setItemType(1);
        }
        //设置分销商id和分销商店铺名称
        orderItemDO.setDistributorId(orderItemDTO.getDistributorId());
        orderItemDO.setDistributorName(orderItemDTO.getDistributorName());
		//这里兼容distributorShopName为空的情况
        if (StringUtils.isBlank(orderItemDTO.getDistributorName())) {
            orderItemDO.setDistributorName("default shop");
        } else {
            orderItemDO.setDistributorName(orderItemDTO.getDistributorName());
        }
        return orderItemDO;
    }

    private static List<OrderServiceDO> convert2OrderServiceDOList(List<OrderServiceDTO> orderServiceDTOs) {
        if (orderServiceDTOs == null) {
            return null;
        }

        List<OrderServiceDO> orderServiceDOs = new ArrayList<OrderServiceDO>();
        try {
            for (OrderServiceDTO orderServiceDTO : orderServiceDTOs) {
                OrderServiceDO orderServiceDO = new OrderServiceDO();
                BeanUtils.copyProperties(orderServiceDO, orderServiceDTO);
                orderServiceDOs.add(orderServiceDO);
            }
        } catch (Exception e) {
            log.error("error to copy OrderServiceDTO", e);
            return Collections.EMPTY_LIST;
        }

        return orderServiceDOs;
    }

    /**
     * 获取订单商品总数（不计换购和套装商品）
     * @param list
     * @return
     */
    public static int getOrderItemListCount(List<OrderItemDO> list){
        int orderItemCount =0;
        for(OrderItemDO orderItemDO:list){
//			if(orderItemDO.getActivityId()==null&&orderItemDO.getOriginalSkuId()==null){
            if(orderItemDO.getOriginalSkuId()==null){
                orderItemCount+=1;
            }
        }
        return orderItemCount;
    }

    public static Double getOrderSplitAmount(int orderSize, Long totalDiscountAmount, Long orderTotalAmount, Long allOrderTotalAmount) {
        if (orderSize == 1 || totalDiscountAmount == 0)
            return totalDiscountAmount.doubleValue();
        if (totalDiscountAmount.longValue() == allOrderTotalAmount.longValue())
            return orderTotalAmount.doubleValue();
        double divResult = MoneyUtil.div(orderTotalAmount + "", allOrderTotalAmount + "", 4);

        Double result = MoneyUtil.mul(divResult, totalDiscountAmount.doubleValue());
//		return result.longValue();
        return result;
    }

    public static Long getAllOrderTotalAmount(List<OrderTogetherDTO> orderTogetherList) {
        if (null == orderTogetherList)
            return 0L;
        Long totalAmount = 0L;
        for (OrderTogetherDTO orderTogether : orderTogetherList) {
            totalAmount += orderTogether.getOrderDO().getTotalAmount();
        }
        return totalAmount;
    }

    public static Long getDiscountAmount(OrderTogetherDTO orderTogetherDTO) {
        List<OrderDiscountInfoDO> orderDiscountDOs = orderTogetherDTO.getOrderDiscountInfoDOs();
        if (null == orderDiscountDOs || orderDiscountDOs.size() == 0) {
            return 0L;
        }
        long voucherDiscountAmt = getVouchersDiscountAmount(orderDiscountDOs);
        long pointDiscountAmt = getPointDiscountAmount(orderDiscountDOs);

        return voucherDiscountAmt + pointDiscountAmt;
    }

    /**
     * 获取订单商品总价
     * @param orderDTO
     * @param orderItemListMap
     * @return
     */
    public static Long getOrderTotalPrice(OrderDTO orderDTO, Map<Long, List<OrderItemDO>> orderItemListMap) {
        List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();
        long orderTotalPrice = 0L;
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            Long skuId = orderItemDTO.getItemSkuId();
            List<OrderItemDO> orderItemDOList = orderItemListMap.get(skuId);
            if (orderItemDOList != null) {
                for (OrderItemDO orderItemDO : orderItemDOList) {
                    if (null == orderItemDO.getServiceUnitPrice())
                        orderItemDO.setServiceUnitPrice(0L);
                    orderTotalPrice += (orderItemDO.getUnitPrice() * orderItemDO.getNumber());
                    Long itemServicePrice = orderItemDO.getServiceUnitPrice() * orderItemDO.getNumber();
                    orderTotalPrice += itemServicePrice;
                }
            }
        }
        return orderTotalPrice;
    }

    /**
     * 获取订单中套装商品的总价
     * @param orderDTO
     * @param orderItemListMap
     * @return
     */
    public static Long getSuitOrderItemTotalPrice(OrderDTO orderDTO, Map<Long, List<OrderItemDO>> orderItemListMap) {
        List<OrderItemDTO> orderItemDTOs = orderDTO.getOrderItems();
        long orderTotalPrice = 0L;
        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            if (orderItemDTO.getItemType() != null
                    && orderItemDTO.getItemType().intValue() == ItemType.SUIT.getType()) {
                Long skuId = orderItemDTO.getItemSkuId();
                List<OrderItemDO> orderItemDOList = orderItemListMap.get(skuId);
                if (orderItemDOList != null) {
                    for (OrderItemDO orderItemDO : orderItemDOList) {
                        orderTotalPrice += (orderItemDO.getUnitPrice() * orderItemDO.getNumber());
                        Long itemServicePrice = orderItemDO.getServiceUnitPrice() * orderItemDO.getNumber();
                        orderTotalPrice += itemServicePrice;
                    }
                }
            }

        }
        return orderTotalPrice;
    }


    public static Long getItemServicePrice(OrderItemDTO orderItemDTO) {
        if (null == orderItemDTO.getOrderServiceList() || orderItemDTO.getOrderServiceList().size() == 0) {
            return 0l;
        }
        Long valueAddedServiceTotalPrice = 0L;
        for (OrderServiceDTO orderServiceDTO : orderItemDTO.getOrderServiceList()) {
            valueAddedServiceTotalPrice += orderServiceDTO.getPrice();
        }
        return valueAddedServiceTotalPrice;
    }

    public static Long getItemServicePrice(List<OrderServiceDO> orderServiceList) {
        if (null == orderServiceList || orderServiceList.size() == 0) {
            return 0L;
        }
        Long valueAddedServiceTotalPrice = 0L;
        for (OrderServiceDO orderService : orderServiceList) {
            valueAddedServiceTotalPrice += orderService.getPrice();
        }
        return valueAddedServiceTotalPrice;
    }

    public static Long getItemServicePrice(OrderItemDO orderItemDO) {
        if (null == orderItemDO.getOrderServiceDOList() || orderItemDO.getOrderServiceDOList().size() == 0) {
            return 0l;
        }
        Long valueAddedServiceTotalPrice = 0L;
        for (OrderServiceDO orderService : orderItemDO.getOrderServiceDOList()) {
            valueAddedServiceTotalPrice += orderService.getPrice();
        }
        return valueAddedServiceTotalPrice;
    }

    /**
     * 获取总优惠金额，包含如下这些优惠组成部分：
     * （1）优惠券/优惠码优惠
     * （2）满减送优惠
     * （3）会员折扣优惠
     * （4）账户余额抵现
     * （5）积分抵现
     * @param orderDiscountInfoDOs
     * @return
     */
    public static long getTotalDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }

        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
        }
        return totalDiscountAmount;
    }

    /**
     * 获取优惠券以及满减送的优惠总金额
     * @param orderDiscountInfoDOs
     * @return
     */
    public static long getCommonDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }
        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            if (orderDiscountInfoDO.getDiscountType() == 1) {
                totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }


    /**
     * 获取余额抵现金额
     * @param orderDiscountInfoDOs
     * @return
     */
    public static Long getVouchersDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }

        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            if (orderDiscountInfoDO.getDiscountType() == 2 &&
                    orderDiscountInfoDO.getDiscountCode().equals("1")) {
                totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    /**
     * 获取会员折扣优惠金额
     * @param orderDiscountInfoDOs
     * @return
     */
    public static Long getMemberDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }

        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            if (orderDiscountInfoDO.getDiscountType() == 3) {
                totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }

    /**
     * 获取积分抵现金额
     * @param orderDiscountInfoDOs
     * @return
     */
    public static Long getPointDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
        if (orderDiscountInfoDOs == null) {
            return 0L;
        }

        long totalDiscountAmount = 0L;
        for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
            if (orderDiscountInfoDO.getDiscountType() == 2 &&
                    orderDiscountInfoDO.getDiscountCode().equals("2")) {
                totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
            }
        }
        return totalDiscountAmount;
    }


    public static OrderConsigneeDO genOrderConsignee(UserConsigneeDTO consigneeDTO) {
        OrderConsigneeDO orderConsigneeDO = new OrderConsigneeDO();
        orderConsigneeDO.setConsigneeId(consigneeDTO.getId());
        orderConsigneeDO.setConsignee(consigneeDTO.getConsignee());
        orderConsigneeDO.setAddress(consigneeDTO.getAddress());
        orderConsigneeDO.setMobile(consigneeDTO.getMobile());
        orderConsigneeDO.setPhone(consigneeDTO.getPhone());
        orderConsigneeDO.setCountryCode(consigneeDTO.getCountryCode());
        orderConsigneeDO.setProvinceCode(consigneeDTO.getProvinceCode());
        orderConsigneeDO.setCityCode(consigneeDTO.getCityCode());
        orderConsigneeDO.setAreaCode(consigneeDTO.getAreaCode());
        orderConsigneeDO.setTownCode(consigneeDTO.getTownCode());
        orderConsigneeDO.setZip(consigneeDTO.getZip());
        orderConsigneeDO.setIdCardNo(consigneeDTO.getIdCardNo());

        return orderConsigneeDO;
    }


    public static OrderConsigneeDO genOrderConsignee(OrderDTO orderDTO, StoreDTO storeDTO, UserDTO storeUserDTO) {
        OrderConsigneeDO orderConsigneeDO = new OrderConsigneeDO();
        orderConsigneeDO.setConsignee(orderDTO.getOrderConsigneeDTO().getConsignee());
        orderConsigneeDO.setAddress(storeDTO.getAddress());
        orderConsigneeDO.setMobile(orderDTO.getOrderConsigneeDTO().getMobile());
        if (orderDTO.getDeliveryId() == 3) {
            orderConsigneeDO.setMobile(storeDTO.getPhone());
        }

        orderConsigneeDO.setPhone(storeUserDTO.getPhone());
        orderConsigneeDO.setCountryCode(storeDTO.getCountryCode());
        orderConsigneeDO.setProvinceCode(storeDTO.getProvinceCode());
        orderConsigneeDO.setCityCode(storeDTO.getCityCode());
        orderConsigneeDO.setAreaCode(storeDTO.getAreaCode());
        orderConsigneeDO.setTownCode(storeDTO.getTownCode());
        return orderConsigneeDO;
    }

    public static OrderStoreDO getOrderStoreDO(OrderDTO orderDTO, StoreDTO storeDTO, UserDTO storeUserDTO) {
        OrderStoreDO orderStoreDO = new OrderStoreDO();
        orderStoreDO.setStoreAddress(storeDTO.getAddress());
        orderStoreDO.setStoreName(storeDTO.getStoreName());

        if (null != storeUserDTO) {
            orderStoreDO.setStoreMobile(storeUserDTO.getMobile());
        }

        orderStoreDO.setPickupTime(orderDTO.getPickupTime());
        orderStoreDO.setStoreId(storeDTO.getId());
        return orderStoreDO;
    }

    public static String getSumpayMchId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        BizPropertyDTO bizProperty = bizPropertyMap.get(BizPropertyKey.SUMPAY_MCH_ID);
        if (null == bizProperty) {
            log.error("TradeUtil.getSumpayMchId error");
            throw new TradeException("sumpay mch_id is null");
        }
//		return "100003657";
        return bizProperty.getValue();
    }

    public static String getSumpayCstNo(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        BizPropertyDTO bizProperty = bizPropertyMap.get(BizPropertyKey.SUMPAY_MCH_ID);
        if (null == bizProperty) {
            log.error("TradeUtil.getSumpayMchId error");
            throw new TradeException("sumpay mch_id is null");
        }
//		return "100003657";
        return bizProperty.getValue();
    }

    public static String getSumpayPrivateKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
//		return "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALfbe1z1dHmYNvReVh0pOIi4JOCluh42rB9ZxBMCOP8ZDCBHPJAs/EFw6OuoRxxI9ofJMPG2ATITUBKaL8Y5eIPzbGlssLDV935qgC6y3/m8ukXN0jqwtw8zt9ssxGo4w1FUBIpujildku3o4VDlReL8IffRAvRZoyPiDT7h1OLRAgMBAAECgYAgxRTy16j/9zpsSdgtcGhsLe4cwE0xD2uUVuqUvMOt7Cf2JNxNkkcP3vEU97Rc/UA7qYd4vYjvBPQzrJmxShMJnGKwJTeEVjzMqygtVY2WDumm5EigDE2eAsDXOnM8kZrGlZsoyuS40hvnUlLhPUnNRQYuxMcoWOqAXsnjqC/zcQJBAN0Ghidlug55/BVSTQXKtkR/Cmtt5XQ6YaUm4t75qlM1qW1MPu47RiFspuA5G7xrgR35DT9wzrd3k49eXPTQel0CQQDU81JSHv0NdwCMK3AoUW5p+G++L7o5+VnO2VY1D4V46JS3aLWd9vp90NFcuN+S/bLUPnE4kmhBnKTJPh2DeosFAkA09zBE1qrcdz0CewN8JNTC21LK5b37uVXW9tedKWU4pX5hc8kM2+V1cw0WZHkSEQ0S+rDDCCxxaNVSoQFTpXX9AkAdDi4XQl8orSoH9g1RkvrLvrgQbRatvlYAoSrgBoQhoSpZdBIDwaUf8ZP/YMIwnij+SJ7OJzvOZ/8b7ju8qsW5AkAN16MGTWoc8T7sCzxg211rvIQImoRs0ibny0DaXleKykrlQULPmkCkgjkFgIuK07C0yz6joYjQL9Fe8hku1AAX";
//		return "MIICXQIBAAKBgQDXRapE1rrpU8H2oRO9/esPv0XTQ401c4p3NS3clqZD2hUTPb3qZKUPHDZo0ivljItl4lzZDuEIHC4S+BZeVic/8o37sp1JnOS3DC7Uhceb96OnI3KbQ8d8bqXRCG54dyCzLJ0IPOig4RI1jp3QsOH3MJNuwyv4+qHqI0PJuo3O3wIDAQABAoGAex86GjMiJg8kkZVhADo49gG0wvcQzXBu0m1U9KQpJgeWJDIc6/FyQiklqWADgT+X/savyelrpULQy0KfMIdf3HOpzpCXDlWCMLgnvgbjiEi8Ud4Gr8pBm+Qqc6KTynXZCwV87Onjbp7A73sORLp0ngXuTbxgnsjrzDNsP2hWdbECQQDrsbcNvxjiP7GWsLQ/58gKSHQGZlcRaIu/9/VsAz6Sl9qOCmmjinpkv6BaDId5Cthtnf7rDIkOiIN4WfcRn4O3AkEA6dGJen3pZSqjIqT+NWdpPpQYI+9z7+vfDmcaVdnQO18eAZWL+AFkUHAEe5QKNWgUH7Yl00/talto7UEu/MmeGQJBAMn8Twf00Ppz41lkrdRmakSFuwkRA2Tj13/4m9apISK4CJDZ05ZXwnQ81MrXGfJdnQSz9haxc3OFAkZNszLjJRkCQQCUhIAqyieiFv8gVmUSTulO0oqy6Lpfvxcj8uGLfpsB210X7IHHujqZzU1LobEKU13U9sH4A2DumbmAWlMaRoahAkAlZFiAPYhdy/PQ0kobkFcK9BGS1DaaY9A0RQMDtk+aUTVp1g815Ya74x9P7jGdLuP9/IUqPjsw5dO4HfannqW1";
//		return "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANdFqkTWuulTwfahE7396w+/RdNDjTVzinc1LdyWpkPaFRM9vepkpQ8cNmjSK+WMi2XiXNkO4QgcLhL4Fl5WJz/yjfuynUmc5LcMLtSFx5v3o6cjcptDx3xupdEIbnh3ILMsnQg86KDhEjWOndCw4fcwk27DK/j6oeojQ8m6jc7fAgMBAAECgYB7HzoaMyImDySRlWEAOjj2AbTC9xDNcG7SbVT0pCkmB5YkMhzr8XJCKSWpYAOBP5f+xq/J6WulQtDLQp8wh1/cc6nOkJcOVYIwuCe+BuOISLxR3gavykGb5CpzopPKddkLBXzs6eNunsDvew5EunSeBe5NvGCeyOvMM2w/aFZ1sQJBAOuxtw2/GOI/sZawtD/nyApIdAZmVxFoi7/39WwDPpKX2o4KaaOKemS/oFoMh3kK2G2d/usMiQ6Ig3hZ9xGfg7cCQQDp0Yl6fellKqMipP41Z2k+lBgj73Pv698OZxpV2dA7Xx4BlYv4AWRQcAR7lAo1aBQftiXTT+1qW2jtQS78yZ4ZAkEAyfxPB/TQ+nPjWWSt1GZqRIW7CREDZOPXf/ib1qkhIrgIkNnTllfCdDzUytcZ8l2dBLP2FrFzc4UCRk2zMuMlGQJBAJSEgCrKJ6IW/yBWZRJO6U7SirLoul+/FyPy4Yt+mwHbXRfsgce6OpnNTUuhsQpTXdT2wfgDYO6ZuYBaUxpGhqECQCVkWIA9iF3L89DSShuQVwr0EZLUNppj0DRFAwO2T5pRNWnWDzXlhrvjH0/uMZ0u4/38hSo+OzDl07gd9qeepbU=";
        BizPropertyDTO bizProperty = bizPropertyMap.get(BizPropertyKey.SUMPAY_MCH_PRIVATE_KEY);
        if (null == bizProperty) {
            log.error("TradeUtil.getAlipayPubKey getAlipayPubKey");
            throw new TradeException("alipay Public key is null");
        }
        return bizProperty.getValue();
    }


    public static String getAlipayPubKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        BizPropertyDTO bizProperty = bizPropertyMap.get(BizPropertyKey.ALIPAY_PUBLIC_KEY);
        if (null == bizProperty) {
            log.error("TradeUtil.getAlipayPubKey getAlipayPubKey");
            throw new TradeException("alipay Public key is null");
        }
        return bizProperty.getValue();
//		return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeR56fVjpXQ2WOFKX4SVEJfzTUOMY6QWDDsKaR H88CwuxkVcmy2P5BfDy39Xke+c9feV6JW7VkaZf9jlKPHqEGYK+6ztOk0SHgevHc9q9CeRnAUJR2 pgkOUaOevjWCz7/eex+fAqwCAE9QibjgYkKNMnFR0LNynynwqaz5NzCICQIDAQAB";
//		return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeR56fVjpXQ2WOFKX4SVEJfzTUOMY6QWDDsKaR H88CwuxkVcmy2P5BfDy39Xke+c9feV6JW7VkaZf9jlKPHqEGYK+6ztOk0SHgevHc9q9CeRnAUJR2pgkOUaOevjWCz7/eex+fAqwCAE9QibjgYkKNMnFR0LNynynwqaz5NzCICQIDAQAB";
    }

    public static String getUnipayMchId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        BizPropertyDTO bizProperty = bizPropertyMap.get(BizPropertyKey.UNIONPAY_MCH_ID);
        if (null == bizProperty) {
            log.error("TradeUtil.getUnipayMchId error");
            throw new TradeException("unipay mchId is null");
        }
        return bizProperty.getValue();
    }

    public static String getMchPrivateKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        BizPropertyDTO bizProperty = bizPropertyMap.get(BizPropertyKey.ALIPAY_MCH_PRIVATE_KEY);
        if (null == bizProperty) {
            log.error("TradeUtil.getMchPrivateKey alipay mch private key get null");
            throw new TradeException("alipay mch private key is null");
        }
        return bizProperty.getValue();
    }

    public static String getAlipayPartnerId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.ALIPAY_PARTNER)) {
            throw new TradeException("alipay partnerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.ALIPAY_PARTNER).getValue();
    }

    public static String getLianlianpayBusiPartner(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_BUSI_PARTNER)) {
            throw new TradeException("lianlianpay busi partnerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_BUSI_PARTNER).getValue();
    }

    public static String getLianlianpayOidPartner(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_OID_PARTNER)) {
            throw new TradeException("lianlianpay oid partnerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_OID_PARTNER).getValue();
    }

    public static String getLianlianpayWareCategory(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_FRMS_WARE_CATEGORY)) {
            throw new TradeException("lianlianpay frms_ware_category is null");
        }
        return bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_FRMS_WARE_CATEGORY).getValue();
    }

    public static String getLianlianpayPrikey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_PRIVATE_KEY)) {
            throw new TradeException("lianlianpay private key is null");
        }
        return bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_PRIVATE_KEY).getValue();
    }

    public static String getLianlianpayPubKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_PUBLIC_KEY)) {
            throw new TradeException("lianlianpay public key is null");
        }
        return bizPropertyMap.get(BizPropertyKey.LIANLIANPAY_PUBLIC_KEY).getValue();
    }

    public static String getAlipaySellerId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.ALIPAY_ACCOUNT)) {
            throw new TradeException("alipay sellerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.ALIPAY_ACCOUNT).getValue();
//		return "18069934334@163.com";
//		return "2839656472@qq.com";
//		return "info@airpool-cap.com.cn";
    }

    public static String getWxAppPayAppId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_APP_APP_ID)) {
            log.error("get WxAppPay AppId is null");
            throw new TradeException("get WxAppPay AppId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_APP_APP_ID).getValue();

    }

    public static String getWxWapPayAppId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_H5_APP_ID)) {
            log.error("get WxWapPay AppId is null");
            throw new TradeException("get WxWapPay AppId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_H5_APP_ID).getValue();
//		return "wx1798992a7488963c";
    }

    public static String getWxPayAppId(int paymentId, Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (paymentId == 2) {
            return getWxAppPayAppId(bizPropertyMap);
        } else if (paymentId == 5) {
            return getWxWapPayAppId(bizPropertyMap);
        }
        throw new TradeException("paymentId not match wxpay");
    }

    public static String getWxPayPartnerId(int paymentId, Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (paymentId == 2) {
            return getWxAppPayPartnerId(bizPropertyMap);
        } else if (paymentId == 5) {
            return getWxWapPayPartnerId(bizPropertyMap);
        }
        throw new TradeException("paymentId not match wxpay");
    }


    public static String getWxPayPartnerKey(int paymentId, Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (paymentId == 2) {
            return getWxAppPayPartnerKey(bizPropertyMap);
        } else if (paymentId == 5) {
            return getWxWapPayPartnerKey(bizPropertyMap);
        }
        throw new TradeException("paymentId not match wxpay");
    }


    public static String getWxAppPayAppKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        return "e0cf8509911342e029ab77fa1a513aeb";
    }

    public static String getWxPayNoncestr(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        return null;
    }

    public static String getWxAppPayPartnerId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
//		return "1247585201";
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_APP_PARTNER_ID)) {
            log.error("get WxAppPay PartnerId is null");
            throw new TradeException("get WxAppPay PartnerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_APP_PARTNER_ID).getValue();
    }

    public static String getWxWapPayPartnerId(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_H5_PARTNER_ID)) {
            log.error("get WxAppPay PartnerId is null");
            throw new TradeException("get WxWapPay PartnerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_H5_PARTNER_ID).getValue();
//		return "1220845901";
    }

    public static String getWxAppPayPartnerKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
//		return "e0cf8509911342e029ab77fa1a513aeb";
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_APP_PARTNER_KEY)) {
            log.error("get WxAppPay PartnerId is null");
            throw new TradeException("get WxAppPay PartnerId is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_APP_PARTNER_KEY).getValue();
    }


    public static String getWxWapPayPartnerKey(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_H5_PARTNER_KEY)) {
            log.error("get WxAppPay PartnerId is null");
            throw new TradeException("get WxWapPay PartnerKey is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_H5_PARTNER_KEY).getValue();
    }


    public static String getWxAppPayAppsecret(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
//		return "9290c6da2d77844711101fab8dc455a8";
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_APP_APP_SECRET)) {
            log.error("get WxAppPay appSecret is null");
            throw new TradeException("get WxAppPay appSecret is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_APP_APP_SECRET).getValue();
    }

    public static String getWxWapPayAppsecret(Map<String, BizPropertyDTO> bizPropertyMap) throws TradeException {
//		return "9290c6da2d77844711101fab8dc455a8";
        if (null == bizPropertyMap.get(BizPropertyKey.WECHAT_H5_APP_SECRET)) {
            log.error("get WxAppPay appSecret is null");
            throw new TradeException("get WxWapPay appSecret is null");
        }
        return bizPropertyMap.get(BizPropertyKey.WECHAT_H5_APP_SECRET).getValue();
//		return "9290c6da2d77844711101fab8dc455a8";
    }

    public static boolean isYangdongxiRequest(String bizCode) {

        if (bizCode.equals("yangdongxi")) {
            return true;
        }

        return false;

    }

    public static String getReturnUrl(String returnUrl, OrderDO orderDO) throws TradeException {

        String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();

        Map<String, String> params = new HashMap<String, String>();
        params.put("order_sn", orderDO.getOrderSn());
        params.put("order_uid", orderUid);
        params.put("pay_amount", orderDO.getTotalAmount() + "");
        params.put("pay_type", orderDO.getPaymentId() + "");

        StringBuffer orderItemsBuffer = new StringBuffer();
//		for(OrderItemDO orderItemDO:orderItems){
//			orderItemsBuffer.append(orderItemDO.getItemId()+"_");
//		}
//		params.put("order_items", orderItemsBuffer.toString() );

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            pairs.add(nameValuePair);
        }

        try {
            String str = EntityUtils.toString(new UrlEncodedFormEntity(pairs, "utf-8"));
            return returnUrl + "?" + str;
        } catch (UnsupportedEncodingException e) {
            log.error("alipay getReturnUrl error", e);
            throw new TradeException("alipay getReturnUrl error", e);
        } catch (IOException e) {
            log.error("alipay getReturnUrl error", e);
            throw new TradeException("alipay getReturnUrl error", e);
        }

    }


    /**
     * 构造HTTP POST交易表单的方法示例
     *
     * @param action  表单提交地址
     * @param hiddens 以MAP形式存储的表单键值
     * @return 构造好的HTTP POST交易表单
     */
    public static String createHtml(String action, Map<String, String> hiddens) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + action
                + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Entry<String, String>> set = hiddens.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
                        + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    public static String getWxParamSign(Map<String, String> paramMap, String partnerKey) {
        StringBuilder signSb = new StringBuilder();
        for (Entry entry : paramMap.entrySet()) {
            signSb.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("&");
        }

        String toSignStr = new StringBuilder().append(signSb.toString()).append("key=").append(partnerKey).toString();
        System.out.println(new StringBuilder().append("signSb:").append(toSignStr).toString());

        String sign = DigestUtils.md5Hex(toSignStr).toUpperCase();
        return sign;
    }


    public static Map<String, String> signData(Map<String, ?> contentData) {
        Entry obj = null;
        Map submitFromData = new HashMap();
        for (Iterator it = contentData.entrySet().iterator(); it.hasNext(); ) {
            obj = (Entry) it.next();
            String value = (String) obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                submitFromData.put(obj.getKey(), value.trim());
                System.out.println(new StringBuilder().append((String) obj.getKey()).append("-->")
                        .append(String.valueOf(value)).toString());
            }

        }
        SDKUtil.sign(submitFromData, "UTF-8");
//		UnipayUtil.signData(contentData,certPath,
//				pwd,
//				certType);
        return submitFromData;
    }


    public static Map<String, String> signData(Map<String, ?> contentData, String certPath, String pwd, String certType) {
        Entry obj = null;
        Map submitFromData = new HashMap();
        for (Iterator it = contentData.entrySet().iterator(); it.hasNext(); ) {
            obj = (Entry) it.next();
            String value = (String) obj.getValue();
            if (StringUtils.isNotBlank(value)) {
                submitFromData.put(obj.getKey(), value.trim());
                System.out.println(new StringBuilder().append((String) obj.getKey()).append("-->")
                        .append(String.valueOf(value)).toString());
            }

        }
//		SDKUtil.sign(submitFromData, "UTF-8");
        UnipayUtil.signData(contentData, certPath,
                pwd,
                certType);
        return submitFromData;
    }


    public static Long getDiscountAmount(int orderItemSize, Long totalAmount, Long totalPrice, Long orderItemUniprice) {
        if (orderItemSize == 1 || totalAmount == 0)
            return totalAmount;
        double divResult = MoneyUtil.div(orderItemUniprice + "", totalPrice + "", 4);

        Double result = MoneyUtil.mul(divResult, totalAmount.doubleValue());

        return result.longValue();
    }

    public static Long getPointAmount(int orderItemSize, Long totalPointAmount, Long totalPrice, Long orderItemUniprice) {
        if (orderItemSize == 1 || totalPointAmount == 0)
            return totalPointAmount;
        double divResult = MoneyUtil.div(orderItemUniprice + "", totalPrice + "", 4);

        Double result = MoneyUtil.mul(divResult, totalPointAmount.doubleValue());
        return result.longValue();
    }

    public static Long getPoint(int orderItemSize, Long point, Long totalPrice, Long orderItemUniprice) {
        if (point == 0)
            return point;
        double divResult = MoneyUtil.div(orderItemUniprice + "", totalPrice + "", 4);
        Double result = MoneyUtil.mul(divResult, point.doubleValue());
        BigDecimal b = new BigDecimal(result + "");
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 1, BigDecimal.ROUND_HALF_UP).longValue();
    }


    /**
     * 获取指定订单商品分摊的运费
     * @param orderItemSize
     * @param totalDeliveryFee
     * @param orderItemUnitPrice
     * @param totalPrice
     * @return
     */
    public static Long getOrderItemDeliveryFee(int orderItemSize, Long totalDeliveryFee,
                                               Long orderItemUnitPrice, Long totalPrice) {
        if (orderItemSize == 1 || totalDeliveryFee == 0){
            return totalDeliveryFee;
        }
        double divResult = MoneyUtil.div(orderItemUnitPrice + "", totalPrice + "", 4);
        Double result = MoneyUtil.mul(divResult, totalDeliveryFee.doubleValue());
        return result.longValue();
    }

    public static class RemoveDeliveryFeeAmount {

        private Long amount;
        private Long deliveryFee;


        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Long getDeliveryFee() {
            return deliveryFee;
        }

        public void setDeliveryFee(Long deliveryFee) {
            this.deliveryFee = deliveryFee;
        }

        public void getPostRemoveDeliveryFeeAmount() {
            if (amount > deliveryFee) {
                amount = amount - deliveryFee;
                deliveryFee = 0L;
            } else {
                deliveryFee = deliveryFee - amount;
                amount = 0L;
            }
        }
    }

    public static RemoveDeliveryFeeAmount getOrderRealPointAmount(Long pointAmount, Long discountAmount, Long paymentAmount, Long deliveryFee) {
        RemoveDeliveryFeeAmount removeDeliveryFeePostPaymentAmount = getOrderRealPaymentAmount(paymentAmount, deliveryFee);
        RemoveDeliveryFeeAmount removeDeliveryFeePostDiscountAmount = new RemoveDeliveryFeeAmount();
        RemoveDeliveryFeeAmount removeDeliveryFeePostPointAmount = new RemoveDeliveryFeeAmount();
        if (removeDeliveryFeePostPaymentAmount.getDeliveryFee() != 0) {
            removeDeliveryFeePostDiscountAmount.setAmount(removeDeliveryFeePostPaymentAmount.getAmount() + discountAmount);
            removeDeliveryFeePostDiscountAmount.setDeliveryFee(removeDeliveryFeePostPaymentAmount.getDeliveryFee());
            removeDeliveryFeePostDiscountAmount.getPostRemoveDeliveryFeeAmount();
            if (removeDeliveryFeePostDiscountAmount.getDeliveryFee() != 0) {

                removeDeliveryFeePostPointAmount.setAmount(removeDeliveryFeePostDiscountAmount.getAmount() + pointAmount);
                removeDeliveryFeePostPointAmount.setDeliveryFee(removeDeliveryFeePostDiscountAmount.getDeliveryFee());
                removeDeliveryFeePostPointAmount.getPostRemoveDeliveryFeeAmount();
                return removeDeliveryFeePostPointAmount;
            } else {
                removeDeliveryFeePostDiscountAmount.setAmount(pointAmount);
            }
        } else {
            removeDeliveryFeePostDiscountAmount.setAmount(pointAmount);
        }
        return removeDeliveryFeePostDiscountAmount;
    }

    public static RemoveDeliveryFeeAmount getOrderRealDiscountAmount(Long paymentAmount, Long discountAmount, Long deliveryFee) {
        RemoveDeliveryFeeAmount removeDeliveryFeePostPaymentAmount = getOrderRealPaymentAmount(paymentAmount, deliveryFee);
        RemoveDeliveryFeeAmount removeDeliveryFeePostDiscountAmount = new RemoveDeliveryFeeAmount();
        if (removeDeliveryFeePostPaymentAmount.getDeliveryFee() != 0) {
            removeDeliveryFeePostDiscountAmount.setAmount(removeDeliveryFeePostPaymentAmount.getAmount() + discountAmount);
            removeDeliveryFeePostDiscountAmount.setDeliveryFee(removeDeliveryFeePostPaymentAmount.getDeliveryFee());
            removeDeliveryFeePostDiscountAmount.getPostRemoveDeliveryFeeAmount();
        } else {
            removeDeliveryFeePostDiscountAmount.setAmount(discountAmount);
        }

        return removeDeliveryFeePostDiscountAmount;
    }


    public static RemoveDeliveryFeeAmount getOrderRealPaymentAmount(Long paymentAmount, Long deliveryFee) {
        RemoveDeliveryFeeAmount removeDeliveryFeeAmount = new RemoveDeliveryFeeAmount();
        removeDeliveryFeeAmount.setAmount(paymentAmount);
        removeDeliveryFeeAmount.setDeliveryFee(deliveryFee);
        removeDeliveryFeeAmount.getPostRemoveDeliveryFeeAmount();
        return removeDeliveryFeeAmount;
    }


    public static Long getPaymentAmount(int orderItemSize, Long totalAmount, Long totalPrice, Long orderItemUniprice) {
        if (orderItemSize == 1 || totalAmount <= 0)
            return totalAmount;
        double divResult = MoneyUtil.div(orderItemUniprice + "", totalPrice + "", 4);

        Double result = MoneyUtil.mul(divResult, totalAmount.doubleValue());
        return result.longValue();
    }

    public static Long getSplitOrderTotalAmount(int splitOrderSize, Long totalAmount, Long totalPrice, Long splitOrderTotalPrice) {
        if (splitOrderSize == 1 || totalAmount <= 0)
            return totalAmount;

        double divResult = MoneyUtil.div(splitOrderTotalPrice + "", totalPrice + "");

        Double result = MoneyUtil.mul(divResult, totalAmount.doubleValue());
//    	BigDecimal b1 = new BigDecimal(Double.toString(result));
//		BigDecimal b2 = new BigDecimal("100");
        return result.longValue();
    }

    public static Map<String, String> genBuriedPointMapKey(String key, String value) {
        Map params = new HashMap();
//    	params.put(RequestParamTypeEnum.PROPERTY.getValue(), value);
        params.put(key, value);
        return params;
    }

    public static Map<String, String> genBuriedPointMapKey(String key, String obj, String property, String value) {
        Map params = new HashMap();
        params.put(key, obj);
        params.put(property, value);
        return params;
    }

    /**
     * 实际可退总金额
     * @param orderItem
     * @return
     */
    public static Long getRefundableTotalAmount(OrderItemDO orderItem) {
        //现金支付金额
        Long paymentAmount = 0L;
        if(null!=orderItem.getPaymentAmount()){
            paymentAmount = orderItem.getPaymentAmount();
        }

        return paymentAmount;
    }

    /**
     * 判断指定商品是否是套装子商品
     * @param oItem
     * @return
     */
    public static boolean checkIsSuitSubOrderItem(OrderItemDO oItem){
        if(oItem.getActivityId()==null&&oItem.getOriginalSkuId()!=null){
            return true;
        }
        return false;
    }


    public static boolean checkIsAllPaymentAmount(List<OrderItemDO> list,long orderTotalAmt,long deliveryFee){
        long orderItemTotalPrice =0l;
        for(OrderItemDO orderItemDO:list){
        	if(orderItemDO!=null){
        		Long unitPrice = 0l;
        		Long serviceUnitPrice = 0l;
        		Integer number = 0;
        		if(orderItemDO.getUnitPrice()!=null){
        			unitPrice = orderItemDO.getUnitPrice();
        		}
        		if(orderItemDO.getServiceUnitPrice()!=null){
        			serviceUnitPrice = orderItemDO.getServiceUnitPrice();
        		}
        		if(orderItemDO.getNumber()!=null){
        			number=orderItemDO.getNumber();
        		}
        		orderItemTotalPrice+=(unitPrice+serviceUnitPrice)*number;
        	}            
        }
        if(deliveryFee==0&&orderItemTotalPrice==orderTotalAmt){
            return true;
        }
        return false;
    }

    public static boolean checkIsAllPointAmount(List<OrderItemDO> list,long pointAmt,long deliveryFee){
        long orderItemTotalPrice =0l;
        for(OrderItemDO orderItemDO:list){
        	Long unitPrice = 0l;
    		Long serviceUnitPrice = 0l;
    		Integer number = 0;
    		if(orderItemDO.getUnitPrice()!=null){
    			unitPrice = orderItemDO.getUnitPrice();
    		}
    		if(orderItemDO.getServiceUnitPrice()!=null){
    			serviceUnitPrice = orderItemDO.getServiceUnitPrice();
    		}
    		if(orderItemDO.getNumber()!=null){
    			number=orderItemDO.getNumber();
    		}
            orderItemTotalPrice+=(unitPrice+serviceUnitPrice)*number;
//			Long itemServicePrice = TradeUtil.getItemServicePrice(orderItemDO);
//	    	orderItemTotalPrice+=itemServicePrice;
        }
        if(deliveryFee==0&&orderItemTotalPrice==pointAmt)
            return true;
        return false;
    }
}

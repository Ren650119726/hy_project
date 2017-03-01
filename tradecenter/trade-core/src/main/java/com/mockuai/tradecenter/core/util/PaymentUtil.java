package com.mockuai.tradecenter.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.tradecenter.common.enums.EnumOrderType;
import com.mockuai.tradecenter.core.domain.OrderDO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.exception.TradeException;

public class PaymentUtil {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentUtil.class);
	
	public class BizLockType{
		public static final int activity_type = 1;
	}

	public class ItemType {
		public static final int seckill = 13;
		public static final int group_buy = 14;
		public static final int suit_item =11;
	}


	public static boolean isSeckillItem(List<OrderItemDO> orderItemDOList) {
		if (orderItemDOList.size() == 1 && null != orderItemDOList.get(0).getItemType()
				&& orderItemDOList.get(0).getItemType() == ItemType.seckill) {
			return true;
		}
		return false;
	}


	public static String genRechargeFrontBackUrl(AppInfoDTO appInfoDTO,OrderDO orderDO){
		StringBuffer detailUrl = new StringBuffer();
		detailUrl.append("http://"+appInfoDTO.getDomainName() + "/recharge-success.html");
		
		
		String orderUid = "" + orderDO.getSellerId() + "_" + orderDO.getUserId() + "_" + orderDO.getId();

		Map<String, String> params = new HashMap<String, String>();
		params.put("order_sn", orderDO.getOrderSn());
		params.put("order_uid", orderUid);
		params.put("pay_amount", orderDO.getTotalAmount() + "");
		params.put("pay_type", orderDO.getPaymentId() + "");
		
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
			pairs.add(nameValuePair);
		}

		try {
			String str = EntityUtils.toString(new UrlEncodedFormEntity(pairs, "utf-8"));
			
			detailUrl.append("?");
			detailUrl.append(str);
			
		} catch (UnsupportedEncodingException e) {
			log.error("alipay genRechargeFrontBackUrl error", e);
		} catch (IOException e) {
			log.error("alipay genRechargeFrontBackUrl error", e);
		}
		return detailUrl.toString();
		
		
	}
	

	public static Long getPointDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
		if (orderDiscountInfoDOs == null) {
			return 0L;
		}
		long totalDiscountAmount = 0L;
		for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
			if (orderDiscountInfoDO.getDiscountType() == 2 && orderDiscountInfoDO.getDiscountCode().equals("2")) {
				totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
			}
		}
		return totalDiscountAmount;
	}

	// 代金券优惠额
	public static Long getVouchersDiscountAmount(List<OrderDiscountInfoDO> orderDiscountInfoDOs) {
		if (orderDiscountInfoDOs == null) {
			return 0L;
		}

		long totalDiscountAmount = 0L;
		for (OrderDiscountInfoDO orderDiscountInfoDO : orderDiscountInfoDOs) {
			if (orderDiscountInfoDO.getDiscountType() == 2 && orderDiscountInfoDO.getDiscountCode().equals("1")) {
				totalDiscountAmount += orderDiscountInfoDO.getDiscountAmount();
			}
		}
		return totalDiscountAmount;
	}

}

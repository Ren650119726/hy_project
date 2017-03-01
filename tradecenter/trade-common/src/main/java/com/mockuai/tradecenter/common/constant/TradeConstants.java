package com.mockuai.tradecenter.common.constant;

/**
 * 交易模块常量
 * @author cwr
 */
public interface TradeConstants {
	
	/**
	 * 订单状态
	 */
	public class Order_Status{
		public final static int UNPAID = 10; // 待支付
		public final static int CANCELED = 20; // 已取消
		public final static int PAID = 30; // 已支付，待推送
		public final static int UN_DELIVER = 35; // 待发货
		public final static int DELIVERIED = 40; // 已发货
		public final static int SIGN_OFF = 50;// 已签收
		public final static int COMMENTED = 60; //已评价
		public final static int REFUND_APPLY = 70;//退款中
		public final static int REFUND_FINISHED = 80; // 退款完成
		public final static int FINISHED = 90;//订单关闭
	}
	
	/**
	 * 订单状态（是否结单）
	 */
//	public class OrderStatus{
//		public final static int FINISHED = 1; // 已经结单
//		public final static int UNFINISHED = 0; // 未结单
//	}
	
	/**
	 * 支付方式
	 */
	public class PaymentType{
		public final static int ALI_PAY_FOR_APP = 1;// 支付宝APP支付
		public final static int WX_PAY_FOR_APP = 2; // 微信支付APP支付
		public final static int UNION_PAY_FOR_APP = 3; // 银联支付APP支付
		public final static int ALI_PAY_FOR_WAP = 4; // 支付宝wap支付
		public final static int WX_PAY_FOR_WAP = 5; // 微信wap支付
		public final static int UNION_PAY_FOR_WAP = 6; // 银联wap支付

		public static boolean isValidPayType(int payType){
			if(payType>=1 && payType<=8){
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 支付状态
	 */
	public class PaymentStatus{
		public final static int UNPAID = 1;// 待支付
		public final static int PARTIAL_PAID = 2;// 部分支付
		public final static int PAID= 3;// 已支付
	}
	
	/**
	 * 配送状态常量
	 */
	public class DeliveryStatus{
		public final static int NOT_DELIVERIED = 0; // 未发货
		public final static int PARTIAL_DELIVERIED = 1; // 部分发货
		public final static int DELIVERIED = 2; // 全部发货
		public final static int ALL_RECEIVAL = 3; // 全部确认收货
	}
	
	/**
	 * 退货状态
	 */
	public class ReturnStatus{
		public final static int RETURNING = 4; //退货中
		public final static int RETURNED = 1; // 已收到退货
	}
	
	/**
	 * 订单来源
	 */
	public class OrderSource{
		public static int PC = 1;
		public static int HTML5 = 5;
		public static int ANDROID = 4;
		public static int IOS = 3;
	}
	
	/**
	 * 订单操作日志状态常量
	 */
	public class OrderLog{
		public static String ADD_ORDER ="您已经提交了订单，请等待系统确认";
		public static String ALL_PAID = "已支付";
		public static String CANCEL_ORDER = "用户取消订单";
		public static String DELETE_ORDER ="用户删除订单";
		public static String ORDER_TIME_OUT ="自动将超过60分钟未支付的订单（不包括货到付款），设置为失效状态";
		public static String PARTIAL_DELIVERIED ="部分发货";
		public static String DELIVERIED = "已发货";
		public static String SIGN_OFF = "用户确认收货";
		public static String DEFAULT_TIMEOUT ="系统14天确认完成收货";
		public static String RETURN_APPLY ="退货申请";
		public static String RETURN_AGREE ="退货申请审核通过(客服)";
		public static String RETURN_REFUSE ="退货申请审核失败(客服)";
		public static String RETURN_GOODS ="退货完成";
		public static String RETURN_RECEIPT ="收到货(仓库)";
		public static String REFUND_SUCCESS ="退款成功(客服)";
	}
	
	/**
	 * 支付宝返回的交易状态
	 */
	public class AlipayStatus{
		public static String TRADE_SUCCESS ="TRADE_SUCCESS";
		public static String TRADE_FINISHED ="TRADE_FINISHED";
		public static String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";
		public static String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";
	}
	
	/**
	 * 微信支付常量
	 */
	public class WxpayStatus{
		public static int TRADE_SUCCESS = 0;
	}
	
	/**
	 * 售后状态
	 */
	public class AfterSale{
		public static int RETURN_APPLY = 1;  // 申请退货
		public static int RETURN_AGREE = 2;  // 同意退货申请
		public static int RETURN_REFUSE = 3;  // 拒绝退货申请
		public static int RETURN_GOODS = 4;  // 顾客已退货
		public static int RETURN_GOODS_CONFIRMED = 5; // 仓库收到退货
		public static int RETURN_FINSHED = 6;  // 退货退款成功
	}
	
	/**
	 * 购物车的数目限制
	 */
	public class Cart{
		public static int CART_MAX = 20 ; // 为登入购物车的最大限制
		public static int USER_CART_MAX = 40; // 已登入用户的购物车商品数最大限制 
	}
	
	/**
	 * 商品提示验证提示信息
	 * @author cwr
	 */
	public class ItemMessage{
		public static String BUY_MAX = "%s商品最多购买%d件"; // 最多购买量限制
		public static String BUY_MIN = "%s商品最少购买%d件"; // 最少购买量限制
	}
	
	/**
	 * 货到付款最大金额限制 
	 * @author cwr
	 */
	public class CodConsants{
		
	}

	public class InvoiceType{
		public static int PERSONAL_INVOICE = 1;  //个人发票
		public static int COMPANY_INVOICE = 2; //公司发票
	}
	
	/**
	 * 发票类别
	 * @author cwr
	 */
	public class InvoiceClass{
		public static int DAILY_GOODS = 1;  // 日用品
		public static int GOODS = 2; // 用品
		public static int COSMETIC = 3; // 化妆品 
	}
	
	/**
	 * 系统参数
	 */
	public class TradeSysParam{
		public final static String COD_MAX_LIMITATOIN = "COD_MAX_LIMITATION"; //  货到付款上限
		public final static String COD_MIN_LIMITATION = "COD_MIN_LIMITATION"; // 货到付款下限
	}
	
	/**
	 * 供应商类型
	 * @author cwr
	 *
	 */
	public class SupplierType{
		public final static int VE = 1; // mockuai 自营
		public final static int ENTER = 2; // 入驻
		public final static int PROXY_DELIVERY = 3; // 代发货 
		public final static int OVERSEA = 4; // 海外购
		// 保税区
	}
	
}
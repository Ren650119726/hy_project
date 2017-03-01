package com.mockuai.tradecenter.core.config;
/**
 * mockuai alipay/weixin 配置
 * @author hzmk
 *
 */
public class MockuaiConfig {
	
	public static String alipay_partner = "2088911841225245";
	
	public static String alipay_seller_id = "mockuai@aliyun.com";
	// 商户的私钥
	public static String mockuai_private_key ="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMlwXVTMPWpN0XAanjyWv9chc6iA1hMbCkyTkNTOtoRfNJv+b/Sw3mwzFp19Hb1pHYPtbmuL4F9gGyUNEG0OWOHTJlu1A9LcoEIl/dC2uDCock6zNRXqyVJiz2cSeFyNL++GK1G09ZbOvOFF3bmHEUA+XUaNXcEggg5eeeET8i5nAgMBAAECgYBP0e+Zt2iqo/VWt6V2GsIfzAiZf5vUbEWTfHHKcbLPFy6wlYzlZ+Rq+X9/nmFXebyRV1cG1KFvCZUYBGo63JGJDbykk3gZ9XhN7/UjUDHoeX/dtInDGQ38XqkUF6nNZnEcjbjSDhF25CxI1XErfirXsf4XI3hflNBdFdsJs9cLsQJBAPi083/93pz5QY+Sew4jXO5wLf0lxFchEqo7WiAeOiChyHJ41x9hLVD6x6fZarqOtnTQDWv/duZsgbjUpTPWqM8CQQDPWJJoYFPBm0b4b4qQCHGgZORi+46g7NYHK8asFgF/CKgq+lXYUAm8L8Trugd0MIqgedJOjUq81wqZcK0Gk1bpAkBL5ezpgnANN4PqbKlhBym1Gkbumjfw3wbzLGm0o4TZsIWbaYU87ksGC4IilxcbF7JWs6dk7fE7IKH15e8Egt3BAkADfVshdJWUSZe9MKtlVy8KYwhQ/ijdXn8PqdXzKX+m8q9Z4dK2DErNpwQqb3wWoYaDwukeN1SNrxTXlkTjOnGZAkABR9nVW6igVuyL+n3T/4FDl36+IWuCewgtIjhd1V9fOqG5NWHFIYxdJWHxjxyHyXAPtfssjzmj/v8rk4WfLfhy";
	
	public static String alipay_public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	                                        
	public static String mockuai_public_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJcF1UzD1qTdFwGp48lr/XIXOogNYTGwpMk5DUzraEXzSb/m/0sN5sMxadfR29aR2D7W5ri+BfYBslDRBtDljh0yZbtQPS3KBCJf3QtrgwqHJOszUV6slSYs9nEnhcjS/vhitRtPWWzrzhRd25hxFAPl1GjV3BIIIOXnnhE/IuZwIDAQAB";
	
//	public static String return_url = "http://api.mockuai.com/trade/order/payment/callback/unionpay_notify";
	
	public static String return_url = "http://192.168.31.181:8090/trade/order/payment/callback/alipay_notify";
	
	public static String mockuai_wx_partner_key="e0cf8509911342e029ab77fa1a513aeb";
	
	public static String DEFAULT_DOMAIN = "http://m.yangdongxi.com";
	
	public static String DEFAULT_DETAIL_URL = "/detail.html";
	
}

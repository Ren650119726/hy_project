package com.mockuai.toolscenter.common.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zengzhangqiang on 8/25/15.
 */
public class BizPropertyKey {
    /**
     * 企业异步回调地址
     */
    public static String BIZ_NOTIFY_URL = "biz_notify_url";
    public static String BIZ_MESSAGE_TITLE = "biz_message_title";
    /**
     * 商城主色调
     */
    public static String BIZ_MAIN_COLOR = "biz_main_color";
    /**
     * 商城客服电话
     */
    public static String BIZ_CS_TEL = "biz_cs_tel";

    /**
     * 支付方式开关
     */
    public static String IS_PAYTYPE_ALIPAY_AVAILABLE = "is_paytype_alipay_available";
    public static String IS_PAYTYPE_WEICHAT_AVAILABLE = "is_paytype_alipay_available";
    public static String IS_PAYTYPE_UNIONPAY_AVAILABLE = "is_paytype_unionpay_available";

    /**
     * 是否走魔筷支付账号
     */
    public static String IS_PAY_BY_MOCKUAI = "is_pay_by_mockuai";

    /**
     * 支付宝支付相关配置
     */
    public static String ALIPAY_PARTNER = "alipay_partner";
    public static String ALIPAY_ACCOUNT = "alipay_account";
    public static String ALIPAY_MCH_PRIVATE_KEY = "alipay_mch_private_key";
    public static String ALIPAY_PUBLIC_KEY = "alipay_public_key";
    public static String ALIPAY_RETURN_URL = "alipay_return_url";
    public static String ALIPAY_NOTIFY_URL = "alipay_notify_url";

    /**
     * 连连支付相关配置
     */
    public static String LIANLIANPAY_PUBLIC_KEY = "lianlianpay_public_key";
    public static String LIANLIANPAY_PRIVATE_KEY = "lianlianpay_private_key";
    public static String LIANLIANPAY_OID_PARTNER = "lianlianpay_oid_partner";
    public static String LIANLIANPAY_FRMS_WARE_CATEGORY = "lianlianpay_frms_ware_category";
    public static String LIANLIANPAY_BUSI_PARTNER = "lianlianpay_busi_partner";
    public static String LIANLIANPAY_RETURN_URL = "lianlianpay_return_url";
    public static String LIANLIANPAY_NOTIFY_URL = "lianlianpay_notify_url";
    // 连连退款回调	
    public static String LIANLIANPAY_REFUND_NOTIFY_URL = "lianlianpay_refund_notify_url";

    //微信支付相关配置
    public static String WECHAT_RETURN_URL = "wechat_return_url";
    public static String WECHAT_NOTIFY_URL = "wechat_notify_url";

    //微信app相关配置
    public static String WECHAT_APP_APP_ID = "wechat_app_app_id";
    public static String WECHAT_APP_APP_SECRET = "wechat_app_app_secret";
    public static String WECHAT_APP_PARTNER_ID = "wechat_app_partner_id";
    public static String WECHAT_APP_PARTNER_KEY = "wechat_app_partner_key";

    //微信h5相关配置
    public static String WECHAT_H5_APP_ID = "wechat_h5_app_id";
    public static String WECHAT_H5_APP_SECRET = "wechat_h5_app_secret";
    public static String WECHAT_H5_PARTNER_ID = "wechat_h5_partner_id";
    public static String WECHAT_H5_PARTNER_KEY = "wechat_h5_partner_key";
    //微信支付回调域名
    public static String WECHAT_H5_DOMAIN = "wechat_h5_domain";

    /**
     * 银联支付相关配置
     */
    public static String UNIONPAY_RETURN_URL = "unionpay_return_url";
    public static String UNIONPAY_NOTIFY_URL = "unionpay_notify_url";
    public static String UNIONPAY_MCH_ID = "unionpay_mch_id";

    /**
     * 微信登陆相关配置
     */
    public static String WECHAT_LOGIN_APP_APP_ID = "wechat_login_app_app_id";
    public static String WECHAT_LOGIN_APP_APP_SECRET = "wechat_login_app_app_secret";
    public static String WECHAT_LOGIN_H5_APP_ID = "wechat_login_h5_app_id";
    public static String WECHAT_LOGIN_H5_APP_SECRET = "wechat_login_h5_app_secret";
    //微信登录回调域名
    public static String WECHAT_LOGIN_H5_DOMAIN = "wechat_login_h5_domain";

    /**
     * 微信分享相关配置
     */
    public static String WECHAT_SHARE_H5_APP_ID = "wechat_share_h5_app_id";
    public static String WECHAT_SHARE_H5_APP_SECRET = "wechat_share_h5_app_secret";

    /**
     * 是否走魔筷微信登陆账号
     */
    public static String IS_WECHAT_LOGIN_BY_MOCKUAI = "is_wechat_login_by_mockuai";

    /**
     * 客服电话
     */
    public static String CS_TEL = "cs_tel";

    /**
     * 在线客服im链接
     */
    public static String CS_ONLINE_URL = "cs_online_url";

    /**
     * 无线端关于我们的链接配置
     */
    public static String ABOUT_US = "about_us";

    /**
     * 跨境标志
     */
    public static String HIGO_MARK = "higo_mark";

    /**
     * 跨境配置信息
     */
    public static String HIGO_INFO = "higo_info";

    /**
     * 是否是多店铺商城，1代表是，0代表否
     */
    public static String IS_MULTI_MALL = "is_multi_mall";

    /**
     * 统统付相关配置
     */
    public static String SUMPAY_PUBLIC_KEY = "sumpay_public_key";
    public static String SUMPAY_MCH_ID = "sumpay_mch_id";
    public static String SUMPAY_MCH_PRIVATE_KEY = "sumpay_mch_private_key";
    public static String SUMPAY_NOTIFY_URL = "sumpay_notify_url";
    public static String SUMPAY_RETURN_URL = "sumpay_return_url";
    //退款回调链接
    public static String SUMPAY_REFUND_NOTIFY_URL = "sumpay_refund_notify_url";
    //无线端导航栏
    public static String NAVIGATION_BAR = "navigation_bar";

    //分销类型，0代表未开启，1代表传统三级分销，2代表微小店
    public static String DISTRIBUTION_TYPE = "distribution_type";

    //供应商功能支持标志，0代表不支持，1代表支持；支持供应商功能的话，购物车需要基于供应商进行分组结算，商品中需要记录供应商id
    public static String IS_SUPPLIER_SUPPORT = "is_supplier_support";

    //网站名称（目前使用于微商城）
    public static String SITE_NAME = "site_name";

    //网站关键字
    public static String SITE_KEYWORDS = "site_keywords";

    //是否支持搜索功能，0代表不支持，1代表支持
    public static String IS_SEARCH_AVAILABLE = "is_search_available";

    //是否支持微信分享自定义配置
    public static String IS_WECHAT_SHARE_CONF_SUPPORT = "is_wechat_share_conf_support";
    //微信分享的标题
    public static String WECHAT_SHARE_CONF_TITLE = "wechat_share_conf_title";
    //微信分享的描述
    public static String WECHAT_SHARE_CONF_DESC = "wechat_share_conf_desc";
    //微信分享的logo配置
    public static String WECHAT_SHARE_CONF_LOGO = "wechat_share_conf_logo";
    //无线端首页数据地址
    public static String HOMEPAGE_WIRELESS_DATA_URL = "homepage_wireless_data_url";


    /**
     * 支付相关属性集
     */
    public static Set<String> PAY_PROPERTY_SET = new HashSet<String>();

    public static Set<String> WECHAT_LOGIN_PROPERTY_SET = new HashSet<String>();

    static{
        //set the pay config list

        //alipay
        PAY_PROPERTY_SET.add(ALIPAY_PARTNER);
        PAY_PROPERTY_SET.add(ALIPAY_ACCOUNT);
        PAY_PROPERTY_SET.add(ALIPAY_MCH_PRIVATE_KEY);
        PAY_PROPERTY_SET.add(ALIPAY_PUBLIC_KEY);
        PAY_PROPERTY_SET.add(ALIPAY_RETURN_URL);
        PAY_PROPERTY_SET.add(ALIPAY_NOTIFY_URL);

        //wechat app pay
        PAY_PROPERTY_SET.add(WECHAT_APP_APP_ID);
        PAY_PROPERTY_SET.add(WECHAT_APP_APP_SECRET);
        PAY_PROPERTY_SET.add(WECHAT_APP_PARTNER_ID);
        PAY_PROPERTY_SET.add(WECHAT_APP_PARTNER_KEY);

        //wechat h5 pay
        PAY_PROPERTY_SET.add(WECHAT_H5_APP_ID);
        PAY_PROPERTY_SET.add(WECHAT_H5_APP_SECRET);
        PAY_PROPERTY_SET.add(WECHAT_H5_PARTNER_ID);
        PAY_PROPERTY_SET.add(WECHAT_H5_PARTNER_KEY);
        PAY_PROPERTY_SET.add(WECHAT_RETURN_URL);
        PAY_PROPERTY_SET.add(WECHAT_NOTIFY_URL);

        //unionpay
        PAY_PROPERTY_SET.add(UNIONPAY_MCH_ID);
        PAY_PROPERTY_SET.add(UNIONPAY_RETURN_URL);
        PAY_PROPERTY_SET.add(UNIONPAY_NOTIFY_URL);

        //sumpay
        PAY_PROPERTY_SET.add(SUMPAY_PUBLIC_KEY);
        PAY_PROPERTY_SET.add(SUMPAY_MCH_PRIVATE_KEY);
        PAY_PROPERTY_SET.add(SUMPAY_MCH_ID);
        PAY_PROPERTY_SET.add(SUMPAY_NOTIFY_URL);
        PAY_PROPERTY_SET.add(SUMPAY_RETURN_URL);
        PAY_PROPERTY_SET.add(SUMPAY_REFUND_NOTIFY_URL);

        //set the wechat login config list
        WECHAT_LOGIN_PROPERTY_SET.add(WECHAT_LOGIN_APP_APP_ID);
        WECHAT_LOGIN_PROPERTY_SET.add(WECHAT_LOGIN_APP_APP_SECRET);
        WECHAT_LOGIN_PROPERTY_SET.add(WECHAT_LOGIN_H5_APP_ID);
        WECHAT_LOGIN_PROPERTY_SET.add(WECHAT_LOGIN_H5_APP_SECRET);
        WECHAT_LOGIN_PROPERTY_SET.add(WECHAT_SHARE_H5_APP_ID);
        WECHAT_LOGIN_PROPERTY_SET.add(WECHAT_SHARE_H5_APP_SECRET);
    }

}

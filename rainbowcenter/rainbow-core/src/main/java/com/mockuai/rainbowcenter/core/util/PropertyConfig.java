package com.mockuai.rainbowcenter.core.util;

public class PropertyConfig {

    /**
     * 获取管易ERP租户appkey
     *
     * @return
     */
    public static String getGyErpAppkey() {
        return PropertyHelper.getKeyValue("gyerp_appkey");
    }

    /**
     * 获取管易ERP租户sessionkey
     *
     * @return
     */
    public static String getGyErpSessionkey() {
        return PropertyHelper.getKeyValue("gyerp_sessionkey");
    }

    /**
     * 获取管易ERP请求地址
     *
     * @return
     */
    public static String getGyErpUrl() {
        return PropertyHelper.getKeyValue("gyerp_url");
    }

    /**
     * 获取管易ERP签名的密钥
     * @return
     */
    public static String getGyerpSecret() {
        return PropertyHelper.getKeyValue("gyerp_secret");
    }


    /**
     * 获取库存查询接口
     * @return
     */
    public static String getGyErpStockMenthod() {
        return PropertyHelper.getKeyValue("get_stock_menthod");
    }
}

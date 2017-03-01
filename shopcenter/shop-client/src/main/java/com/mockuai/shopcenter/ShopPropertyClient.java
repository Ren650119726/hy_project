package com.mockuai.shopcenter;

import com.mockuai.shopcenter.api.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/4.
 */
public interface ShopPropertyClient {

    /**
     * 获取商品中心配置
     * @param sellerId
     * @param key
     * @param appKey
     * @return
     */
    public Response<String> getShopProperty(Long sellerId,String key,String appKey);

    /**
     * 获取商品中心配置
     * @param sellerId
     * @param keys
     * @param appKey
     * @return
     */
    public Response<Map<String,String>> getShopProperties(Long sellerId,List<String> keys,String appKey);

    /**
     * 修改或添加商品中心配置
     * @param sellerId
     * @param key
     * @param value
     * @param appKey
     * @return
     */
    public Response<Integer> setShopProperty(Long sellerId,String key,String value,String appKey);

    /**
     * 修改或添加商品中心配置
     * @param sellerId
     * @param props
     * @param appKey
     * @return
     */
    public Response<Integer> setShopProperties(Long sellerId,Map<String,String> props,String appKey);
}

package com.mockuai.shopcenter;

import com.mockuai.shopcenter.api.Response;

import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/11/4.
 */
public interface StorePropertyClient {


//    /**
//     * 获取店铺配置
//     * @param sellerId
//     * @param key
//     * @param appKey
//     * @return
//     */
//    public Response<String> getStoreProperty(Long sellerId,Long storeId, String key, String appKey);
//
//    /**
//     * 获取店铺配置
//     * @param sellerId
//     * @param keys
//     * @param appKey
//     * @return
//     */
//    public Response<Map<String,String>> getStoreProperties(Long sellerId,Long storeId, List<String> keys, String appKey);
//
//    /**
//     * 修改或添加店铺配置
//     * @param sellerId
//     * @param key
//     * @param value
//     * @param appKey
//     * @return
//     */
//    public Response<Integer> setStoreProperty(Long sellerId,Long storeId, String key, String value, String appKey);
//
//    /**
//     * 修改或添加店铺配置
//     * @param sellerId
//     * @param props
//     * @param appKey
//     * @return
//     */
//    public Response<Integer> setStoreProperties(Long sellerId,Long storeId, Map<String, String> props, String appKey);

    /**
     * 根据某项配置的值查询门店Id列表
     * @param sellerId
     * @param key
     * @param value
     * @param appKey
     * @return
     */
    public Response<List<Long>> queryStoreIdsByProperty(Long sellerId,String key, String value, String appKey);


    public Response<Integer> batchResetStoreProperty(List<Long> storeIds,Long sellerId,String key, String value, String appKey);

}

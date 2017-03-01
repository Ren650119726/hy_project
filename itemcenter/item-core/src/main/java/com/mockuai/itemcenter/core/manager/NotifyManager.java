package com.mockuai.itemcenter.core.manager;

/**
 * Created by zengzhangqiang on 7/2/15.
 */
public interface NotifyManager {
    /**
     * 推送新增商品消息给商城客户
     * @param itemId
     * @param sellerId
     * @param bizCode
     */
    public void notifyAddItemMsg(long itemId, long sellerId, String bizCode);

    /**
     * 推送更新商品消息给商城客户
     * @param itemId
     * @param sellerId
     * @param bizCode
     */
    public void notifyUpdateItemMsg(long itemId, long sellerId, String bizCode);

    /**
     * 推送上架商品消息给商城客户
     * @param itemId
     * @param sellerId
     * @param bizCode
     */
    public void notifyUpItemMsg(long itemId, long sellerId, String bizCode);

    /**
     * 推送下架商品消息给商城客户
     * @param itemId
     * @param sellerId
     * @param bizCode
     */
    public void notifyDownItemMsg(long itemId, long sellerId, String bizCode);
}


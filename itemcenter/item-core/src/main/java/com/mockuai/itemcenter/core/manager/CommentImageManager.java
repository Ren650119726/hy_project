package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.core.domain.CommentImageDO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/17/15.
 * 商品评论图片管理器
 */
public interface CommentImageManager {

    /**
     * 批量添加商品评论图片
     * @param commentImageDOs
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
    public void addCommentImages(List<CommentImageDO> commentImageDOs) throws ItemException;

    /**
     * 查询评论图片列表
     * @param itemCommentIdList
     * @param userId
     * @return
     */
    public Map<Long, List<CommentImageDO>> queryCommentImage(List<Long> itemCommentIdList, Long sellerId)
            throws ItemException;
}

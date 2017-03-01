package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.domain.CommentImageDO;

import java.util.List;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
public interface CommentImageDAO {
    /**
     * 添加商品评论图片
     * @param commentImageDO
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
    public Long addCommentImage(CommentImageDO commentImageDO);

    /**
     * 批量添加商品评论图片
     * @param commentImageDOs
     * @return
     * @throws com.mockuai.itemcenter.core.exception.ItemException
     */
    public void addCommentImages(List<CommentImageDO> commentImageDOs);

    /**
     * 查询评论图片列表
     * @param itemCommentIdList
     * @param userId
     * @return
     */
    public List<CommentImageDO> queryCommentImage(List<Long> itemCommentIdList, Long sellerId);

    public List<Long> queryCommentIdWithPicture(ItemCommentQTO itemCommentQTO);

    public Long countCommentIdWithPicture(ItemCommentQTO itemCommentQTO);
}

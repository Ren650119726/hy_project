package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.dao.CommentImageDAO;
import com.mockuai.itemcenter.core.domain.CommentImageDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CommentImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by zengzhangqiang on 7/17/15.
 */
@Component
public class CommentImageManagerImpl implements CommentImageManager {
    private static final Logger log = LoggerFactory.getLogger(CommentImageManagerImpl.class);

    @Resource
    private CommentImageDAO commentImageDAO;

    public void addCommentImages(List<CommentImageDO> commentImageDOs) throws ItemException {
        //TODO 入参校验
        try{
            commentImageDAO.addCommentImages(commentImageDOs);
        }catch(Exception e){
            log.error("error to addCommentImages", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to addCommentImages");
        }
    }

    public Map<Long, List<CommentImageDO>> queryCommentImage(List<Long> itemCommentIdList, Long sellerId)
            throws ItemException {
        //TODO 入参校验
        try{
            List<CommentImageDO> commentImageDOs = commentImageDAO.queryCommentImage(itemCommentIdList, sellerId);
            Map<Long, List<CommentImageDO>> commentImageMap = new HashMap<Long, List<CommentImageDO>>();
            if(commentImageDOs!=null && commentImageDOs.isEmpty()==false){
                for(CommentImageDO commentImageDO: commentImageDOs){
                    if(commentImageMap.containsKey(commentImageDO.getItemCommentId()) == false){
                        commentImageMap.put(commentImageDO.getItemCommentId(),
                                new CopyOnWriteArrayList<CommentImageDO>());
                    }
                    commentImageMap.get(commentImageDO.getItemCommentId()).add(commentImageDO);
                }
            }
            return commentImageMap;

        }catch(Exception e){
            log.error("sellerId:{}", sellerId, e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to addCommentImages");
        }
    }
}

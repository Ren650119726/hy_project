package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.dao.CommentImageDAO;
import com.mockuai.itemcenter.core.domain.CommentImageDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 7/20/15.
 */
@Repository
public class CommentImageDAOImpl extends SqlMapClientDaoSupport implements CommentImageDAO {
    public Long addCommentImage(CommentImageDO commentImageDO) {
        return null;
    }

    public void addCommentImages(List<CommentImageDO> commentImageDOs) {
        getSqlMapClientTemplate().insert("comment_image.addCommentImages", commentImageDOs);
    }

    public List<CommentImageDO> queryCommentImage(List<Long> itemCommentIdList, Long sellerId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("itemCommentIdList", itemCommentIdList);
        params.put("sellerId", sellerId);
        return getSqlMapClientTemplate().queryForList("comment_image.queryCommentImage", params);
    }

    public List<Long> queryCommentIdWithPicture(ItemCommentQTO itemCommentQTO){
        List<Long> result = getSqlMapClientTemplate().queryForList("comment_image.queryCommentIdWithPicture",itemCommentQTO);
        return  result;
    }

    public Long countCommentIdWithPicture(ItemCommentQTO itemCommentQTO){
        Long result = (Long) getSqlMapClientTemplate().queryForObject("comment_image.countCommentIdWithPicture",itemCommentQTO);
        return  result;
    }
}

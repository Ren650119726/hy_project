package com.mockuai.usercenter.core.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import com.mockuai.usercenter.core.dao.FeedbackDAO;

import com.mockuai.usercenter.common.qto.FeedbackQTO;
import com.mockuai.usercenter.core.domain.FeedbackDO;

/**
 * Created by duanyytop on 15/5/13.
 */
@Service
public class FeedbackDAOImpl extends SqlMapClientDaoSupport implements FeedbackDAO {

    @Override
    public Long addFeedback(FeedbackDO feedbackDO) {
        Long id = (Long) getSqlMapClientTemplate().insert("feedback.insert",
                feedbackDO);
        return id;
    }

    @Override
    public List<FeedbackDO> getFeedbackByDate(Date startDate, Date endDate, Long offset, Integer count, String bizCode) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("offset", offset);
        map.put("count", count);
        map.put("bizCode", bizCode);
        List<FeedbackDO> feedbackDOList = (List<FeedbackDO>) getSqlMapClientTemplate().queryForList(
                "feedback.selectByDate", map);
        return feedbackDOList;
    }

    @Override
    public FeedbackDO getFeedbackById(Long id) {
        FeedbackDO feedbackDO = (FeedbackDO) getSqlMapClientTemplate().queryForObject(
                "feedback.selectById", id);
        return feedbackDO;
    }

	@Override
	public List<FeedbackDO> queryFeedback(FeedbackQTO feedbackQTO) {
		List<FeedbackDO> result = this.getSqlMapClientTemplate().queryForList("feedback.queryFeedback",feedbackQTO);
		return result;
	}
    
	@Override
	public Integer getTotalCount(FeedbackQTO feedbackQTO){
		Integer result = (Integer)this.getSqlMapClientTemplate().queryForObject("feedback.getTotalCount",feedbackQTO);
		return result;
	}
}

package com.mockuai.tradecenter.core.dao.impl;

import com.mockuai.tradecenter.common.domain.SaleRankDTO;
import com.mockuai.tradecenter.common.domain.StatisticsActivityInfoDTO;
import com.mockuai.tradecenter.core.dao.OrderDiscountInfoDAO;
import com.mockuai.tradecenter.core.domain.OrderDiscountInfoDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 5/24/15.
 */
public class OrderDiscountInfoDAOImpl extends SqlMapClientDaoSupport implements OrderDiscountInfoDAO{
    public Long addOrderDiscountInfo(OrderDiscountInfoDO orderDiscountInfoDO) {
        return (Long)this.getSqlMapClientTemplate().insert(
                "order_discount_info.addOrderDiscountInfo",orderDiscountInfoDO);
    }

    public List<OrderDiscountInfoDO> queryOrderDiscountInfo(Long orderId, Long userId) {
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("userId", userId);
        return (List<OrderDiscountInfoDO>)this.getSqlMapClientTemplate().queryForList(
                "order_discount_info.queryOrderDiscountInfo", params);
    }



    @Override
    public StatisticsActivityInfoDTO queryActivityOrder(OrderDiscountInfoDO orderDiscountInfoDO) {
        return (StatisticsActivityInfoDTO) getSqlMapClientTemplate().queryForObject("order_discount_info.queryActivityOrder",orderDiscountInfoDO);
    }
    @Override
    public List<SaleRankDTO> querySaleRank(Long activityId){
        return getSqlMapClientTemplate().queryForList("order_discount_info.querySaleRankByLimitBuy",activityId);
    }
}

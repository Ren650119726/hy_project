package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.core.dao.LimitedGoodsCorrelationDAO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangsiqian on 2016/10/19.
 */
public class LimitedGoodsCorrelationDAOImpl extends SqlMapClientDaoSupport implements LimitedGoodsCorrelationDAO {
    @Override
    public int stopActivity(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) {
        return (Integer) getSqlMapClientTemplate().update("limited_goods_correlation.stopActivity",limitedGoodsCorrelationDO);
    }

    @Override
    public Long addActivityGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) {
       return  (Long) getSqlMapClientTemplate().insert("limited_goods_correlation.addActivityGoods",limitedGoodsCorrelationDO);
    }

    @Override
    public List<LimitedGoodsCorrelationDO> selectMsgByItemId(Long itemId) {
        Map map = new HashMap<>();
        map.put("itemId",itemId);
        return (List<LimitedGoodsCorrelationDO>)getSqlMapClientTemplate().queryForList("limited_goods_correlation.selectMsgByItemID",map);
    }

    @Override
    public List<LimitedGoodsCorrelationDO> selectMsgByGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) {
        return (List<LimitedGoodsCorrelationDO>)getSqlMapClientTemplate().queryForList("limited_goods_correlation.selectMsgByGoods",limitedGoodsCorrelationDO);
    }

    @Override
    public Integer deleteGoods(Long activityId) {
        Map map = new HashMap();
        map.put("activityId",activityId);
        return (Integer)getSqlMapClientTemplate().delete("limited_goods_correlation.delteCorrelationGoods",map);
    }

    @Override
    public LimitedGoodsCorrelationDO selectCurrentActivityId(Long itemId) {
        Map map = new HashMap();
        map.put("itemId",itemId);
        return (LimitedGoodsCorrelationDO)getSqlMapClientTemplate().queryForObject("limited_goods_correlation.selectCurrentActivityId",map);
    }
}

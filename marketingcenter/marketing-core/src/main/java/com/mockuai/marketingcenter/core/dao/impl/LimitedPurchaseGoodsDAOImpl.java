package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseGoodsDAO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangsiqian on 2016/10/8.
 */
public class LimitedPurchaseGoodsDAOImpl extends SqlMapClientDaoSupport implements LimitedPurchaseGoodsDAO {
    @Override
    public Long addActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO) {

        return (Long) getSqlMapClientTemplate().insert("activity_goods.addActivityGoods", activityGoodsDO);
    }

    @Override
    public LimitedPurchaseGoodsDO selectActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) {
        return (LimitedPurchaseGoodsDO) getSqlMapClientTemplate().queryForObject("activity_goods.selectactivityGoods",limitedPurchaseGoodsDO);
    }

    @Override
    public List<LimitedPurchaseGoodsDO> activityGoodsList(LimitedPurchaseGoodsDO activityGoodsDO) {

        return (List<LimitedPurchaseGoodsDO>)getSqlMapClientTemplate().queryForList("activity_goods.activityGoodsList",activityGoodsDO);
    }

    @Override
    public Integer updateActivityGoodsNum(LimitedPurchaseGoodsDO activityGoodsDO) {

        return (Integer) getSqlMapClientTemplate().update("activity_goods.updateActivityGoodsNum",activityGoodsDO);
    }
    @Override
    public Integer updateActivityGoodsPrice(LimitedPurchaseGoodsDO activityGoodsDO) {

        return (Integer) getSqlMapClientTemplate().update("activity_goods.updateActivityGoodsPrice",activityGoodsDO);
    }

    @Override
    public Integer deleteActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO) {
        return (Integer) getSqlMapClientTemplate().update("activity_goods.deleteActivityGoods",activityGoodsDO);
    }

    @Override
    public List selectGoodsItemId(Long activityId) {
        Map map = new HashMap();
        map.put("activityId",activityId);
        return (List)getSqlMapClientTemplate().queryForList("activity_goods.selectActionItemById",map);
    }

    @Override
    public Long selectGoodsQuantityById(LimitedPurchaseGoodsDO activityGoodsDO) {
       Long num = (Long)getSqlMapClientTemplate().queryForObject("activity_goods.selectGoodsQuantityById",activityGoodsDO);
        return num;
    }

    @Override
    public List selectAllActivity() {
        return (List)getSqlMapClientTemplate().queryForList("activity_goods.selectAllActivityId");
    }

    @Override
    public List selectActivityIdByItemId(Long itemId) {
        Map map = new HashMap();
        map.put("itemId",itemId);
        return (List)getSqlMapClientTemplate().queryForObject("activity_goods.selectActivityIdByItemId",map);
    }

    @Override
    public Integer invalidateActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO) {
        return (Integer) getSqlMapClientTemplate().update("activity_goods.invalidateActivityGoods",activityGoodsDO);
    }

    @Override
    public Integer deleteGoods(Long activityId) {
        Map map = new HashMap();
        map.put("activityId",activityId);

        return (Integer) getSqlMapClientTemplate().delete("activity_goods.deleteGoods",map);
    }

    @Override
    public LimitedPurchaseGoodsDO selectMinGoodsPrice(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) {
        return (LimitedPurchaseGoodsDO)getSqlMapClientTemplate().queryForObject("activity_goods.selectMinGoodsPrice",limitedPurchaseGoodsDO);
    }

    @Override
    public List selectAllSkuId(Long activityId) {
        Map map = new HashMap();
        map.put("activityId",activityId);
        return (List)getSqlMapClientTemplate().queryForList("activity_goods.selectAllSkuId",map);
    }
}

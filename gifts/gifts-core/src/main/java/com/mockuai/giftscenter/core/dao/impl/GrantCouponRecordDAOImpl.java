package com.mockuai.giftscenter.core.dao.impl;

import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;
import com.mockuai.giftscenter.core.dao.GrantCouponRecordDAO;
import com.mockuai.giftscenter.core.domain.GrantCouponRecordDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by guansheng  15/7/16
 */
public class GrantCouponRecordDAOImpl extends SqlMapClientDaoSupport implements GrantCouponRecordDAO {



    @Override
    public Long insert(List<GrantCouponRecordDO> data) {
        return (Long) getSqlMapClientTemplate().insert("grant_coupon_record.insert", data);

    }

    @Override
    public List<GrantCouponRecordDO> queryAll(GrantCouponRecordQTO qto) {
        return  getSqlMapClientTemplate().queryForList("grant_coupon_record.queryAll", qto);
    }

   public  int queryTotalCount(GrantCouponRecordQTO qto){
       return (int) getSqlMapClientTemplate().queryForObject("grant_coupon_record.queryTotalCount", qto);

   }

}
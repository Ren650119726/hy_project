package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.core.dao.CouponCodeDAO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponCodeDAOImpl extends SqlMapClientDaoSupport implements CouponCodeDAO {

    @Override
    public void batchAddCouponCodes(List<CouponCodeDO> couponCodeDOs) throws MarketingException {
        Map<String, Object> param = new HashMap<String, Object>();
        int size = 10000;
        while (!couponCodeDOs.isEmpty()) {
            param.put("list", couponCodeDOs.subList(0, Math.min(size, couponCodeDOs.size())));
            try {
                getSqlMapClientTemplate().insert("coupon_code.batchAddCouponCodes", param);
            } catch (Exception e) {
                // duplicate entry for key 'code'
                if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
                    String entry = getEntryName(e.getCause().getCause().getMessage());
                    throw new MarketingException(ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY, entry);
                }
            }
            couponCodeDOs.subList(0, Math.min(size, couponCodeDOs.size())).clear();
        }
    }

    @Override
    public List<CouponCodeDO> queryByCode(String code) {
        return getSqlMapClientTemplate().queryForList("coupon_code.queryByCode", code);
    }

    public List<CouponCodeDO> queryCouponCode(CouponCodeQTO couponCodeQTO) {

        return getSqlMapClientTemplate().queryForList("coupon_code.queryCouponCode", couponCodeQTO);
    }

    @Override
    public Integer queryCouponCodeCount(CouponCodeQTO couponCodeQTO) {
        return (Integer) getSqlMapClientTemplate().queryForObject("coupon_code.queryCouponCodeCount", couponCodeQTO);
    }

    @Override
    public int update(CouponCodeDO couponCodeDO) {
        return getSqlMapClientTemplate().update("coupon_code.update", couponCodeDO);
    }

    private String getEntryName(String errorMsg) {

        String sub = errorMsg.substring(errorMsg.indexOf('\'') + 1);
        return sub.substring(0, sub.indexOf('\''));
    }
}
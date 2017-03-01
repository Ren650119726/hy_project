package com.mockuai.marketingcenter.core.dao.impl;

import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.dao.GrantedCouponDAO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponSumDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrantedCouponDAOImpl extends SqlMapClientDaoSupport implements GrantedCouponDAO {

	public long addGrantedCoupon(GrantedCouponDO grantedCouponDO) {

		return ((Long) getSqlMapClientTemplate().insert("granted_coupon.addGrantedCoupon", grantedCouponDO)).longValue();
	}

	public int deleteGrantedCoupon(Long grantedCouponId, Long userId) {

		GrantedCouponDO grantedCouponDO = new GrantedCouponDO();
		grantedCouponDO.setId(grantedCouponId);
		grantedCouponDO.setReceiverId(userId);
		return getSqlMapClientTemplate().update("granted_coupon.deleteGrantedCoupon", grantedCouponDO);
	}

	public int updateGrantedCoupon(GrantedCouponDTO grantedCouponDTO) {

		return getSqlMapClientTemplate().update("granted_coupon.updateGrantedCoupon", grantedCouponDTO);
	}

	@Override
	public int updateCouponReceiverId(List<Long> idList, Long fromUserId, Long toUserId) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idList", idList);
		params.put("fromUserId", fromUserId);
		params.put("toUserId", toUserId);
		return getSqlMapClientTemplate().update("granted_coupon.updateCouponReceiverId", params);
	}

	public int updateCouponStatus(List<Long> idList, long receiverId, int fromStatus, int toStatus) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idList", idList);
		params.put("receiverId", receiverId);
		params.put("fromStatus", fromStatus);
		params.put("toStatus", toStatus);
		return getSqlMapClientTemplate().update("granted_coupon.updateCouponStatus", params);
	}

	@Override
	public List<GrantedCouponSumDO> queryGrantedCouponSum(GrantedCouponQTO grantedCouponQTO) {

		return getSqlMapClientTemplate().queryForList("granted_coupon.queryGrantedCouponSum", grantedCouponQTO);
	}

	public List<GrantedCouponDO> queryGrantedCoupon(GrantedCouponQTO grantedCouponQTO) {
		grantedCouponQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("granted_coupon.queryCount", grantedCouponQTO));
		return getSqlMapClientTemplate().queryForList("granted_coupon.queryGrantedCoupon", grantedCouponQTO);
	}

	@Override
	public List<GrantedCouponDO> queryGrantedCouponForCouponCode(GrantedCouponQTO grantedCouponQTO) {
		return getSqlMapClientTemplate().queryForList("granted_coupon.queryGrantedCouponForCouponCode", grantedCouponQTO);
	}

	@Override
	public Integer queryGrantedCouponCountForCouponCode(GrantedCouponQTO grantedCouponQTO) {
		return (Integer) getSqlMapClientTemplate().queryForObject("granted_coupon.queryGrantedCouponCountForCouponCode", grantedCouponQTO);
	}

	public int countOfQueryGrantedCoupon(GrantedCouponQTO grantedCouponQTO) {
		return (int) getSqlMapClientTemplate().queryForObject("granted_coupon.queryCount", grantedCouponQTO);
	}

	@Override
	public List<GrantedCouponDO> queryGrantedCouponSecond(GrantedCouponQTO grantedCouponQTO) {

		return getSqlMapClientTemplate().queryForList("granted_coupon.queryGrantedCountSecond", grantedCouponQTO);
	}
}
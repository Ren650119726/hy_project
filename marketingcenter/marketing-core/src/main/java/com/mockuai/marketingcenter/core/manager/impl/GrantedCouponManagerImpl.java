package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.dao.GrantedCouponDAO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponSumDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GrantedCouponManagerImpl implements GrantedCouponManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrantedCouponManagerImpl.class);

	@Resource
	private GrantedCouponDAO grantedCouponDAO;

	public void addGrantedCoupon(GrantedCouponDO grantedCoupon) throws MarketingException {

		try {
			long grantedCouponId = grantedCouponDAO.addGrantedCoupon(grantedCoupon);
			grantedCoupon.setId(Long.valueOf(grantedCouponId));
		} catch (Exception e) {
			LOGGER.error("failed when adding grated coupon, grantedCouponDO : {}", JsonUtil.toJson(grantedCoupon), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int batchAddGrantedCoupon(List<GrantedCouponDO> grantedCouponList)
			throws MarketingException {

		try {
			for (GrantedCouponDO grantedCouponDO : grantedCouponList) {
				grantedCouponDAO.addGrantedCoupon(grantedCouponDO);
			}
			return grantedCouponList.size();

		} catch (Exception e) {
			LOGGER.error("failed when adding granted coupon in batch, grantedCouponList : {}",
					JsonUtil.toJson(grantedCouponList), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int updateGrantedCoupon(GrantedCouponDTO grantedCouponDTO) throws MarketingException {

		try {
			return grantedCouponDAO.updateGrantedCoupon(grantedCouponDTO);
		} catch (Exception e) {
			LOGGER.error("failed when updating granted coupon, grantedCouponDTO : {}",
					JsonUtil.toJson(grantedCouponDTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public int updateCouponReceiverId(List<Long> couponIdList,
	                                  Long fromUserId, Long toUserId) throws MarketingException {
		try {
			return grantedCouponDAO.updateCouponReceiverId(couponIdList, fromUserId, toUserId);
		} catch (Exception e) {
			LOGGER.error("failed when updating coupon receiverId, fromUserId : {}, toUserId : {}, couponIdList : {}",
					fromUserId, toUserId, JsonUtil.toJson(couponIdList), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public int updateCouponStatus(List<Long> idList, long userId,
	                              int fromStatus, int toStatus) throws MarketingException {
		try {
			return grantedCouponDAO.updateCouponStatus(idList, userId, fromStatus, toStatus);
		} catch (Exception e) {
			LOGGER.error("failed when updating coupon status, fromStatus : {}, toStatus : {}, userId : {}, idList : {}",
					fromStatus, toStatus, userId, JsonUtil.toJson(idList), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public List<GrantedCouponDO> queryGrantedCoupon(GrantedCouponQTO grantedCouponQTO)
			throws MarketingException {

		try {
			return grantedCouponDAO.queryGrantedCoupon(grantedCouponQTO);

		} catch (Exception e) {
			LOGGER.error("failed when querying grantedCoupon, grantedCouponQTO : {}",
					JsonUtil.toJson(grantedCouponQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public List<GrantedCouponDO> queryGrantedCouponForCouponCode(GrantedCouponQTO grantedCouponQTO) throws MarketingException {
		try {
			grantedCouponQTO.setTotalCount(grantedCouponDAO.queryGrantedCouponCountForCouponCode(grantedCouponQTO));
			return grantedCouponDAO.queryGrantedCouponForCouponCode(grantedCouponQTO);
		} catch (Exception e) {
			LOGGER.error("failed when queryGrantedCouponForCouponCode, grantedCouponQTO : {}", JsonUtil.toJson(grantedCouponQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public List<GrantedCouponSumDO> queryGrantedCouponSum(GrantedCouponQTO grantedCouponQTO) throws MarketingException {
		try {
			//如果未指定待查询优惠券状态，则默认只能查询可使用的优惠券
			if (grantedCouponQTO.getStatus() == null) {
				grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
			}
			return grantedCouponDAO.queryGrantedCouponSum(grantedCouponQTO);

		} catch (Exception e) {
			LOGGER.error("failed when queryGrantedCouponSum, grantedCouponQTO : {}", JsonUtil.toJson(grantedCouponQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public List<GrantedCouponDO> queryGrantedCouponSecond(GrantedCouponQTO grantedCouponQTO) throws MarketingException {

		if (grantedCouponQTO.getStatus() == null) {
			grantedCouponQTO.setStatus(UserCouponStatus.UN_USE.getValue());
		}

		try {
			return grantedCouponDAO.queryGrantedCouponSecond(grantedCouponQTO);
		} catch (Exception e) {
			LOGGER.error("failed when querying granted coupon second, grantedCouponQTO : {}",
					JsonUtil.toJson(grantedCouponQTO));
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	public int queryGrantedCouponCount(GrantedCouponQTO grantedCouponQTO)
			throws MarketingException {

		try {
			return grantedCouponDAO.countOfQueryGrantedCoupon(grantedCouponQTO);

		} catch (Exception e) {
			LOGGER.error("failed when querying granted coupon count, grantedCouponQTO : {}",
					JsonUtil.toJson(grantedCouponQTO), e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}

	@Override
	public Boolean isOutOfUserReceiveLimit(Long couponId, Long userId, Integer limit) throws MarketingException {
		try {
			GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
			grantedCouponQTO.setCouponId(couponId);
			grantedCouponQTO.setReceiverId(userId);
			Integer count = grantedCouponDAO.countOfQueryGrantedCoupon(grantedCouponQTO);
			return count >= limit.intValue() && limit.intValue() != 0;
		} catch (Exception e) {
			LOGGER.error("failed when isOutOfUserReceiveLimit, couponId : {}, userId : {}, limit : {}",
					couponId, userId, limit, e);
			throw new MarketingException(ResponseCode.DB_OP_ERROR);
		}
	}
}
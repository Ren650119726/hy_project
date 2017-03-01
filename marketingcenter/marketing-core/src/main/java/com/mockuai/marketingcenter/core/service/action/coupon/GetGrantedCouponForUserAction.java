package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.UserCouponDataDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.MarketingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 7/25/2016.
 */
@Service
public class GetGrantedCouponForUserAction implements Action {

	public static final Logger LOGGER = LoggerFactory.getLogger(GetGrantedCouponForUserAction.class);

	@Autowired
	private GrantedCouponManager grantedCouponManager;

	@Override
	public MarketingResponse execute(RequestContext context) throws MarketingException {
		GrantedCouponQTO grantedCouponQTO = (GrantedCouponQTO) context.getRequest().getParam("grantedCouponQTO");

		List<GrantedCouponDO> grantedCouponDOs = new ArrayList<>();
		List<GrantedCouponDO> temp;
		grantedCouponQTO.setCount(1000);
		grantedCouponQTO.setOffset(-1000);
		do {
			grantedCouponQTO.setOffset(grantedCouponQTO.getOffset() + grantedCouponQTO.getCount());
			temp = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);
			if (!temp.isEmpty()) {
				grantedCouponDOs.addAll(temp);
			}
		} while (!temp.isEmpty());
		UserCouponDataDTO userCouponDataDTO = new UserCouponDataDTO();
		userCouponDataDTO.setInvalidCount(0);
		userCouponDataDTO.setUnusedCount(0);
		userCouponDataDTO.setUsedCount(0);

		Date now = new Date();
		for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
			if (grantedCouponDO.getStatus() == UserCouponStatus.USED.getValue()
					    || grantedCouponDO.getStatus() == UserCouponStatus.PRE_USE.getValue()) {
				userCouponDataDTO.setUsedCount(userCouponDataDTO.getUsedCount() + 1);
			} else if (grantedCouponDO.getInvalidTime() != null &&
					           grantedCouponDO.getInvalidTime().before(now) ||
					           grantedCouponDO.getEndTime().before(now)) {
				userCouponDataDTO.setInvalidCount(userCouponDataDTO.getInvalidCount() + 1);
				continue;
			}
			if (grantedCouponDO.getStatus() == UserCouponStatus.UN_USE.getValue()) {
				userCouponDataDTO.setUnusedCount(userCouponDataDTO.getUnusedCount() + 1);
			} else {
				userCouponDataDTO.setInvalidCount(userCouponDataDTO.getInvalidCount() + 1);
			}
		}

		return MarketingUtils.getSuccessResponse(userCouponDataDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_GRANTED_COUPON_FOR_USER.getActionName();
	}
}
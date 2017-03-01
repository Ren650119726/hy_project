package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedLogQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UsedLogManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 虚拟财富由预使用转换到正式使用
 * 嗨币 更改 usedLog 对应状态
 * <p/>
 * Created by zengzhangqiang on 5/25/15.
 */
@Service
public class UseUserWealthAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(UseUserWealthAction.class);

	@Autowired
	private UsedWealthManager usedWealthManager;
	@Autowired
	private WealthAccountManager wealthAccountManager;
	@Autowired
	private UsedLogManager usedLogManager;

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		Long orderId = (Long) context.getRequest().getParam("orderId");
		AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

		if (userId == null) {
			return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "userId is null");
		}

		if (orderId == null) {
			return new VirtualWealthResponse(ResponseCode.PARAMETER_NULL, "orderId is null");
		}

		try {
			//查询虚拟财富预使用记录
			UsedWealthQTO usedWealthQTO = new UsedWealthQTO();
			usedWealthQTO.setOrderId(orderId);
			usedWealthQTO.setUserId(userId);
			List<UsedWealthDO> usedWealthDOs = usedWealthManager.queryUsedWealth(usedWealthQTO);

			//如果该订单下面没有任何预使用的虚拟财富信息，则打印日志，并直接返回成功
			if (usedWealthDOs == null || usedWealthDOs.isEmpty()) {
				LOGGER.warn("there is not any used wealth under this order, orderId:{}, userId:{}", orderId, userId);
				return ResponseUtil.getResponse(Boolean.valueOf(true));
			}

			List<Long> idList = new ArrayList<Long>();
			List<Long> wealthAccountIds = new ArrayList<>();

			//虚拟财富预使用记录状态检查
			for (UsedWealthDO usedWealthDO : usedWealthDOs) {
				if (usedWealthDO.getStatus().intValue() != WealthUseStatus.PRE_USE.getValue()) {
					LOGGER.error("invalid status of used_wealth, userId : {}, orderId : {}, usedWealthDO : {}"
							, userId
							, orderId
							, JsonUtil.toJson(usedWealthDO));
					return new VirtualWealthResponse(ResponseCode.WEALTH_USED_RECORD_STATUS_ILLEGAL);
				}
				idList.add(usedWealthDO.getId());
				wealthAccountIds.add(usedWealthDO.getWealthAccountId());
			}

			int opNum = usedWealthManager.updateWealthStatus(idList, userId,
					WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.USED.getValue());

			if (opNum != idList.size()) {
				LOGGER.error("error to update wealth status, userId:{}, fromStatus:{}, toStatus:{}",
						userId, WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.USED.getValue());
				throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
			}

			WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
			wealthAccountQTO.setIdList(wealthAccountIds);
			List<WealthAccountDO> wealthAccountDOs = wealthAccountManager.queryWealthAccount(wealthAccountQTO);
			Long hiCoinWealthAccountId = null;
			for (WealthAccountDO wealthAccountDO : wealthAccountDOs) {
				if (wealthAccountDO.getWealthType().intValue() == WealthType.HI_COIN.getValue()) {
					hiCoinWealthAccountId = wealthAccountDO.getId();
					break;
				}
			}

			for (UsedWealthDO usedWealthDO : usedWealthDOs) {
				if (hiCoinWealthAccountId != null
						    && usedWealthDO.getWealthAccountId().longValue() == hiCoinWealthAccountId
						    && usedWealthDO.getAmount().longValue() != 0) {
					UsedLogQTO usedLogQTO = new UsedLogQTO();
					usedLogQTO.setUsedWealthId(usedWealthDO.getId());
					List<UsedLogDO> usedLogDOs = usedLogManager.queryUsedLog(usedLogQTO);
					if (usedLogDOs.isEmpty()) {
						LOGGER.error("error to query used log according to usedWealthId : {}", usedWealthDO.getId());
						throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
					}
					UsedLogDO toUpdate = new UsedLogDO();
					toUpdate.setUsedWealthId(usedWealthDO.getId());
					toUpdate.setStatus(WealthUseStatus.USED.getValue());
					int optCount = usedLogManager.updateUsedLog(toUpdate);
					if (optCount != usedLogDOs.size()) {
						LOGGER.error("error to update status of usedLog, usedWealthId : {}, status : {}",
								usedWealthDO.getId(), WealthUseStatus.USED.getValue());
					}
				}
			}
			return new VirtualWealthResponse(ResponseCode.SUCCESS);
		} catch (VirtualWealthException e) {
			LOGGER.error("Action failed, {}, userId : {}, orderId : {}, bizCode : {}", getName(), userId, orderId, appInfo.getBizCode());
			return new VirtualWealthResponse(e.getCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.USE_USER_WEALTH.getActionName();
	}
}
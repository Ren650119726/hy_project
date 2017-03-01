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
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedLogManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.ResponseUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 解除虚拟财富的预使用状态
 * 一个用户可能会有多种虚拟财富类型
 * 嗨币在解除过程中需要恢复发放记录中的数值
 * <p/>
 * Created by zengzhangqiang on 5/25/15.
 */
@Service
public class ReleaseUsedWealthAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReleaseUsedWealthAction.class);

	@Autowired
	private UsedWealthManager usedWealthManager;
	@Autowired
	private WealthAccountManager wealthAccountManager;
	@Autowired
	private UsedLogManager usedLogManager;
	@Autowired
	private GrantedWealthManager grantedWealthManager;

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {

		Long userId = (Long) context.getRequest().getParam("userId");
		Long orderId = (Long) context.getRequest().getParam("orderId");
		AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

		//入参校验
		VirtualWealthPreconditions.checkNotNull(userId, "userId");
		VirtualWealthPreconditions.checkNotNull(orderId, "orderId");

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
					return ResponseUtil.getResponse(Boolean.valueOf(true));
				}
				idList.add(usedWealthDO.getId());
				wealthAccountIds.add(usedWealthDO.getWealthAccountId());
			}

			int opNum = usedWealthManager.updateWealthStatus(idList, userId,
					WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.CANCELED.getValue());

			// TODO 有小频率出现不等的情况, releaseGrantedCoupon 类似
			if (opNum != idList.size()) {
				LOGGER.error("error to update wealth status, userId:{}, fromStatus:{}, toStatus:{}",
						userId, WealthUseStatus.PRE_USE.getValue(), WealthUseStatus.CANCELED.getValue());
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

			//将取消的虚拟财富额度回补到虚拟账户中去
			for (UsedWealthDO usedWealthDO : usedWealthDOs) {
				opNum = wealthAccountManager.increaseAccountBalance(usedWealthDO.getWealthAccountId(),
						usedWealthDO.getUserId(), usedWealthDO.getAmount());
				if (opNum != 1) {
					LOGGER.error("error to increase the wealth account balance, wealthAccountIds:{}, userId:{}, userAmount:{}",
							usedWealthDO.getWealthAccountId(), usedWealthDO.getUserId(), usedWealthDO.getAmount());
					throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
				}
				if (hiCoinWealthAccountId != null
						    && usedWealthDO.getWealthAccountId() == hiCoinWealthAccountId.longValue()
						    && usedWealthDO.getAmount().longValue() != 0) { // amount 为 0 的没有详细使用记录
					// 更新 usedLog 记录状态
					UsedLogQTO usedLogQTO = new UsedLogQTO();
					usedLogQTO.setUsedWealthId(usedWealthDO.getId());
					List<UsedLogDO> usedLogDOs = usedLogManager.queryUsedLog(usedLogQTO);
					if (usedLogDOs.isEmpty()) {
						LOGGER.error("error to query used log according to usedWealthId : {}", usedWealthDO.getId());
						throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
					}
					UsedLogDO toUpdate = new UsedLogDO();
					toUpdate.setUsedWealthId(usedWealthDO.getId());
					toUpdate.setStatus(WealthUseStatus.CANCELED.getValue());
					int optCount = usedLogManager.updateUsedLog(toUpdate);
					if (optCount != usedLogDOs.size()) {
						LOGGER.error("error to update status of usedLog, usedWealthId : {}, status : {}",
								usedWealthDO.getId(), WealthUseStatus.CANCELED.getValue());
					}
					// 回滚 grantedWealth 数值
					List<GrantedWealthDO> toUpdateGrantedWealthDOs = new ArrayList<>();
					GrantedWealthDO grantedWealthDO;
					for (UsedLogDO usedLogDO : usedLogDOs) {
						grantedWealthDO = new GrantedWealthDO();
						grantedWealthDO.setId(usedLogDO.getGrantedWealthId());
						grantedWealthDO.setUsedAmount(-usedLogDO.getAmount());
						toUpdateGrantedWealthDOs.add(grantedWealthDO);
					}
					grantedWealthManager.decreaseAmount(toUpdateGrantedWealthDOs);
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
		return ActionEnum.RELEASE_USED_WEALTH.getActionName();
	}
}
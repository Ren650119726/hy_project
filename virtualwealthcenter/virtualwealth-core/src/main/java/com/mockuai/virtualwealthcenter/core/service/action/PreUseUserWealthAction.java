package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.constant.WealthUseStatus;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedLogDO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedLogManager;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 预使用用户虚拟财富
 * 针对嗨币增加详细的使用记录
 * <p/>
 * Created by zengzhangqiang on 5/25/15.
 */
@Service
public class PreUseUserWealthAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreUseUserWealthAction.class);

	@Autowired
	private WealthAccountManager wealthAccountManager;
	@Autowired
	private UsedWealthManager usedWealthManager;
	@Autowired
	private VirtualWealthManager virtualWealthManager;
	@Autowired
	private GrantedWealthManager grantedWealthManager;
	@Autowired
	private UsedLogManager usedLogManager;

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		Long orderId = (Long) context.getRequest().getParam("orderId");
//        Long sellerId = (Long) context.getRequest().getParam("sellerId");
		Integer wealthType = (Integer) context.getRequest().getParam("wealthType");
		Long useAmount = (Long) context.getRequest().getParam("useAmount");
		AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

		VirtualWealthPreconditions.checkNotNull(userId, "userId");
//        VirtualWealthPreconditions.checkNotNull(sellerId, "sellerId");
		VirtualWealthPreconditions.checkNotNull(orderId, "orderId");
		VirtualWealthPreconditions.checkNotNull(useAmount, "useAmount");

		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			//判断指定虚拟账户业务状态
			WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, wealthType, appInfo.getBizCode());
			LOGGER.info("stop1 : {}", stopWatch.getTime() / 1000.0);

			if (wealthAccountDO == null) {
				LOGGER.error("error to found wealthAccount, userId : {}, wealthType : {}, bizCode : {}, orderId : {}",
						userId, wealthType, appInfo.getBizCode(), orderId);
				return new VirtualWealthResponse(ResponseCode.WEALTH_ACCOUNT_IS_NOT_FOUND);
			}

			VirtualWealthDTO virtualWealthDTO = virtualWealthManager.getVirtualWealth(appInfo.getBizCode(), 0L, wealthType);
			LOGGER.info("stop2 : {}", stopWatch.getTime() / 1000.0);

			if (virtualWealthDTO.getTradeMark().intValue() != 1) {
				LOGGER.error("the wealthAccount cannot be in trade. wealthAccountId : {}, virtualWealthId : {}, userId : {}, wealthType : {}, orderId : {}, bizCode : {}",
						wealthAccountDO.getId(), virtualWealthDTO.getId(), userId, wealthType, orderId, appInfo.getBizCode());
				return new VirtualWealthResponse(ResponseCode.BIZ_E_VIRTUAL_WEALTH_CANNOT_BE_IN_TRADE);
			}

			// TODO 目前没有全局的虚拟财富使用控制开关，积分暂时用使用上限来做限制
//			if (virtualWealthDTO.getType().intValue() == WealthType.CREDIT.getValue()) {
//				if (virtualWealthDTO.getUpperLimit() == null || virtualWealthDTO.getUpperLimit().intValue() == 0) {
//					return new VirtualWealthResponse(ResponseCode.BIZ_E_VIRTUAL_WEALTH_CANNOT_BE_IN_TRADE);
//				}
//			}

			//判断虚拟账户额度是否足够
			if (wealthAccountDO.getAmount() < useAmount) {//虚拟账户余额不足
				return new VirtualWealthResponse(ResponseCode.ACCOUNT_BALANCE_NOT_ENOUGH);
			}

			// 记录每笔中支出的数量
			List<GrantedWealthDO> willUsedWealth = new ArrayList<>();
			// 嗨币,需要确定使用的来源,即 发放记录中对应的位置
			if (wealthType.intValue() == WealthType.HI_COIN.getValue()) {
				Long remain = useAmount;
				GrantedWealthDO tGrantedWealthDO;

				GrantedWealthQTO grantedWealthQTO = new GrantedWealthQTO();
				grantedWealthQTO.setStatus(GrantedWealthStatus.TRANSFERRED.getValue());
				grantedWealthQTO.setWealthId(wealthAccountDO.getId());
//				grantedWealthQTO.setNonZero(1);
				grantedWealthQTO.setNonEmpty(1);
				grantedWealthQTO.setOffset(-100);
				grantedWealthQTO.setCount(100);
				do {
					
					grantedWealthQTO.setOffset(grantedWealthQTO.getCount() + grantedWealthQTO.getOffset());
					LOGGER.info("A_stop1 request : {}", JsonUtil.toJson(grantedWealthQTO));
					List<GrantedWealthDO> grantedWealthDOs = grantedWealthManager.queryGrantedWealth(grantedWealthQTO);
					LOGGER.info("stop3 : {}", stopWatch.getTime() / 1000.0);
					LOGGER.info("A_stop2 数据结构长度 : {}", grantedWealthDOs.size());
					if(grantedWealthDOs.size() == 0){
						break;
					}
					for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
					
						if (grantedWealthDO.getAmount().longValue() == grantedWealthDO.getUsedAmount().longValue())
							continue;
						LOGGER.info("A_stop3 remain: {}",remain);
						LOGGER.info("A_stop3 amount: {}", grantedWealthDO.getAmount().longValue());
						LOGGER.info("A_stop4 usedamount: {}", grantedWealthDO.getUsedAmount().longValue());
						tGrantedWealthDO = new GrantedWealthDO();
						BeanUtils.copyProperties(grantedWealthDO, tGrantedWealthDO);
						if (grantedWealthDO.getAmount().longValue() < grantedWealthDO.getUsedAmount() + remain) {
							LOGGER.info("A_stop5 :进来了");
							remain -= grantedWealthDO.getAmount() - grantedWealthDO.getUsedAmount();
							tGrantedWealthDO.setAmount(grantedWealthDO.getAmount() - grantedWealthDO.getUsedAmount());
							willUsedWealth.add(tGrantedWealthDO);
						} else if (remain != 0) {
							LOGGER.info("A_stop6 :进来了");
							tGrantedWealthDO.setAmount(remain);
							willUsedWealth.add(tGrantedWealthDO);
							remain = 0L;
							break;
						}
					}
				} while (remain != 0);
				LOGGER.info("stop4 : {}", stopWatch.getTime() / 1000.0);
			}

			int opNum = wealthAccountManager.decreaseAccountBalance(wealthAccountDO.getId(), userId, useAmount);
			if (opNum != 1) {
				LOGGER.error("error to decrease the wealth account balance, wealthAccountId:{}, userId:{}, userAmount:{}",
						wealthAccountDO.getId(), userId, useAmount);
				throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
			}
			LOGGER.info("stop5 : {}", stopWatch.getTime() / 1000.0);

			//新增一条使用记录
			UsedWealthDO usedWealthDO = new UsedWealthDO();
			usedWealthDO.setWealthAccountId(wealthAccountDO.getId());
			usedWealthDO.setOrderId(orderId);
			usedWealthDO.setUserId(userId);
//            usedWealthDO.setSellerId(sellerId);
			usedWealthDO.setAmount(useAmount);
			usedWealthDO.setBizCode(appInfo.getBizCode());
			usedWealthDO.setStatus(WealthUseStatus.PRE_USE.getValue());
			Long id = usedWealthManager.addUsedWealth(usedWealthDO);
			if (wealthType.intValue() == WealthType.HI_COIN.getValue()
					    && useAmount.longValue() != 0) { // 创建使用记录
				List<UsedLogDO> usedLogDOs = new ArrayList<>();
				List<GrantedWealthDO> toDecreaseList = new ArrayList<>();
				GrantedWealthDO toDecrease;
				UsedLogDO usedLogDO;
				for (GrantedWealthDO grantedWealthDO : willUsedWealth) {
					usedLogDO = new UsedLogDO();
					usedLogDO.setAmount(grantedWealthDO.getAmount());
					usedLogDO.setOrderId(grantedWealthDO.getOrderId());
					usedLogDO.setBizCode(grantedWealthDO.getBizCode());
					usedLogDO.setGrantedWealthId(grantedWealthDO.getId());
					usedLogDO.setOrderSn(grantedWealthDO.getOrderSN());
					usedLogDO.setStatus(WealthUseStatus.PRE_USE.getValue());
					usedLogDO.setUsedWealthId(id);
					usedLogDO.setWealthAccountId(grantedWealthDO.getWealthId());
					usedLogDOs.add(usedLogDO);
					toDecrease = new GrantedWealthDO();
					toDecrease.setId(grantedWealthDO.getId());
					toDecrease.setUsedAmount(grantedWealthDO.getAmount());
					toDecreaseList.add(toDecrease);
				}
				LOGGER.info("stop6 : {}", stopWatch.getTime() / 1000.0);

				usedLogManager.batchAddUsedLog(usedLogDOs);
				LOGGER.info("stop7 : {}", stopWatch.getTime() / 1000.0);

				grantedWealthManager.decreaseAmount(toDecreaseList); // 更新发放记录中的数量
				LOGGER.info("stop8 : {}", stopWatch.getTime() / 1000.0);

			}
			return new VirtualWealthResponse(ResponseCode.SUCCESS);
		} catch (VirtualWealthException e) {
			LOGGER.info("Action failed, {}, userId : {}, orderId : {}, wealthType : {}, useAmount : {}, bizCode : {}",
					getName(), userId, orderId, wealthType, useAmount, appInfo.getBizCode());
			return new VirtualWealthResponse(e.getCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.PRE_USE_USER_WEALTH.getActionName();
	}
}
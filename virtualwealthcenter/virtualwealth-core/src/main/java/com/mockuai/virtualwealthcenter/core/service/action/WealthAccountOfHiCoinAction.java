package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.GrantedWealthStatus;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.core.domain.GrantedWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.GrantedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户嗨币详情
 * Created by edgar.zr on 5/13/2016.
 */
@Service
public class WealthAccountOfHiCoinAction extends TransAction {

	@Autowired
	private WealthAccountManager wealthAccountManager;
	@Autowired
	private GrantedWealthManager grantedWealthManager;

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		AppInfoDTO appInfoDTO = (AppInfoDTO) context.get("appInfo");

		VirtualWealthPreconditions.checkNotNull(userId, "userId");

		WealthAccountDO wealthAccountDO =
				wealthAccountManager.getWealthAccount(userId, WealthType.HI_COIN.getValue(), appInfoDTO.getBizCode());
		if (wealthAccountDO == null) {
			WealthAccountDO userWealthAccount = new WealthAccountDO();
			userWealthAccount.setBizCode(appInfoDTO.getBizCode());
			userWealthAccount.setWealthType(WealthType.HI_COIN.getValue());
			userWealthAccount.setUserId(userId);
			userWealthAccount.setAmount(0L);
			userWealthAccount.setTransitionAmount(0L);
			Long wealthAccountId = wealthAccountManager.addWealthAccount(userWealthAccount);
			userWealthAccount.setId(wealthAccountId);
			wealthAccountDO = userWealthAccount;
			WealthAccountDTO wealthAccountDTO = ModelUtil.genWealthAccountDTO(wealthAccountDO);
			wealthAccountDTO.setWillExpireAmount(0L);
			return VirtualWealthUtils.getSuccessResponse(wealthAccountDTO);
		}
		WealthAccountDTO wealthAccountDTO = ModelUtil.genWealthAccountDTO(wealthAccountDO);

		// 即将过期的
		GrantedWealthQTO grantedWealthQTO = new GrantedWealthQTO();
		grantedWealthQTO.setWealthId(wealthAccountDTO.getId());
		grantedWealthQTO.setWillExpire(1);
		grantedWealthQTO.setStatus(GrantedWealthStatus.TRANSFERRED.getValue());
		grantedWealthQTO.setBizCode(appInfoDTO.getBizCode());
		List<GrantedWealthDO> grantedWealthDOs = grantedWealthManager.queryGrantedWealth(grantedWealthQTO);
		wealthAccountDTO.setWillExpireAmount(0L);
		for (GrantedWealthDO grantedWealthDO : grantedWealthDOs) {
			wealthAccountDTO.setWillExpireAmount(wealthAccountDTO.getWillExpireAmount() + grantedWealthDO.getAmount());
		}
		return VirtualWealthUtils.getSuccessResponse(wealthAccountDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_HI_COIN.getActionName();
	}
}
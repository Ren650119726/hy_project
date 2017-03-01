package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthDO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询虚拟帐号
 * <p/>
 * Created by zengzhangqiang on 5/25/15.
 */
@Service
public class ListWealthAccountAction implements Action {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListWealthAccountAction.class.getName());

	@Resource
	private VirtualWealthManager virtualWealthManager;
	@Resource
	private WealthAccountManager wealthAccountManager;

	@Override
	public VirtualWealthResponse execute(RequestContext context) throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
		AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");

		Integer wealthType = null;
		if (context.getRequest().getParam("wealthType") != null) {
			wealthType = (Integer) context.getRequest().getParam("wealthType");
		}

		try {

			//查询用户虚拟财富账户，如果账户不存在则创建
			List<WealthAccountDTO> wealthAccountDTOs = new ArrayList<WealthAccountDTO>();
			List<WealthAccountDO> wealthAccountDOs = new ArrayList<WealthAccountDO>();
			if (wealthType != null) {
				WealthAccountDO wealthAccountDO = wealthAccountManager.getWealthAccount(userId, wealthType, appInfo.getBizCode());
				if (wealthAccountDO == null) {
					WealthAccountDO userWealthAccount = new WealthAccountDO();
					userWealthAccount.setBizCode(appInfo.getBizCode());
					userWealthAccount.setWealthType(wealthType);
					userWealthAccount.setUserId(userId);
					userWealthAccount.setAmount(0L);
					Long wealthAccountId = wealthAccountManager.addWealthAccount(userWealthAccount);
					userWealthAccount.setId(wealthAccountId);
					wealthAccountDO = userWealthAccount;
				}
				wealthAccountDOs.add(wealthAccountDO);
			} else {
				WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
				wealthAccountQTO.setBizCode(appInfo.getBizCode());
				wealthAccountQTO.setUserId(userId);
				wealthAccountDOs = wealthAccountManager.queryWealthAccount(wealthAccountQTO);
			}

			Map<Integer, WealthAccountDO> wealthAccountDOMap = new HashMap<>();
			for (WealthAccountDO wealthAccountDO : wealthAccountDOs) {
				wealthAccountDOMap.put(wealthAccountDO.getWealthType(), wealthAccountDO);
			}

			VirtualWealthQTO virtualWealthQTO = new VirtualWealthQTO();
			List<Integer> typeIn = new ArrayList<>();
			typeIn.addAll(wealthAccountDOMap.keySet());
			if (typeIn.isEmpty() == false) {
				virtualWealthQTO.setTypeIn(typeIn);
				virtualWealthQTO.setBizCode(appInfo.getBizCode());
			}
			virtualWealthQTO.setBizCode(appInfo.getBizCode());
			List<VirtualWealthDO> virtualWealthDOs = virtualWealthManager.queryVirtualWealth(virtualWealthQTO);
			for (VirtualWealthDO virtualWealthDO : virtualWealthDOs) {
				if (wealthAccountDOMap.containsKey(virtualWealthDO.getType())) {
					WealthAccountDO wealthAccountDO = wealthAccountDOMap.get(virtualWealthDO.getType());
					WealthAccountDTO wealthAccountDTO = ModelUtil.genWealthAccountDTO(wealthAccountDO, virtualWealthDO);
					wealthAccountDTOs.add(wealthAccountDTO);
				} else {
					WealthAccountDO userWealthAccount = new WealthAccountDO();
					userWealthAccount.setBizCode(appInfo.getBizCode());
					userWealthAccount.setWealthType(virtualWealthDO.getType());
					userWealthAccount.setUserId(userId);
					userWealthAccount.setAmount(0L);
					Long wealthAccountId = wealthAccountManager.addWealthAccount(userWealthAccount);
					userWealthAccount.setId(wealthAccountId);
					WealthAccountDTO wealthAccountDTO = ModelUtil.genWealthAccountDTO(userWealthAccount, virtualWealthDO);
					wealthAccountDTOs.add(wealthAccountDTO);
				}
			}
			return new VirtualWealthResponse(wealthAccountDTOs);
		} catch (VirtualWealthException e) {
			LOGGER.error("Action failed, {}, userId : {}, bizCode : {}", getName(), userId, appInfo.getBizCode());
			return new VirtualWealthResponse(e.getCode(), e.getMessage());
		}
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_WEALTH_ACCOUNT.getActionName();
	}
}
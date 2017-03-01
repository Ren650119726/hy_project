package com.mockuai.virtualwealthcenter.core.service.action.authon;
/**
 * 后面审核通过 
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;

@Service
public class AccptUserAuthon extends TransAction{
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.ACCEPT_AUDITING.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		UserAuthonAppDO userAuthonAppDO = new UserAuthonAppDO();
		Long  id =  (Long)context.getRequest().getParam("id");
		String authonStatus = (String)context.getRequest().getParam("authon_status");
		userAuthonAppDO.setId(id);
		userAuthonAppDO.setAuthonStatus(Integer.valueOf(authonStatus));
		Integer result = userAuthonAppManager.modifyAuditStatus(userAuthonAppDO);
		return new VirtualWealthResponse(result);
	}

	

}

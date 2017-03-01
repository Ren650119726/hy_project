package com.mockuai.virtualwealthcenter.core.service.action.authon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;

/**   
 * @author huangsiqian
 * @version 2016年9月12日 下午4:49:54 
 */
@Service
public class SyncPhoneNumber extends TransAction{
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.SYNC_PHONE_NUMBER.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		UserAuthonAppDO userAuthonAppDO = new UserAuthonAppDO();
		Long userId = (Long) context.getRequest().getParam("userId");
		String authonMobile =  (String) context.getRequest().getParam("authonMobile");
		userAuthonAppDO.setUserId(userId);
		userAuthonAppDO.setAuthonMobile(authonMobile);
		Long result = (Long)userAuthonAppManager.SyncPhoneNumber(userAuthonAppDO);
		return VirtualWealthUtils.getSuccessResponse();
	}

}

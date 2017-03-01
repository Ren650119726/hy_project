package com.mockuai.virtualwealthcenter.core.service.action.authon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;

@Service
public class SelectUserAuthon  extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(SelectUserAuthon.class);
	
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_SEL.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId = (Long) context.getRequest().getParam("userId");
        VirtualWealthPreconditions.checkNotNull(userId, "userId");
        
        MopUserAuthonAppDTO mopUserAuthonAppDTO =  userAuthonAppManager.selectMopUserAuthon(userId);
        
        return new VirtualWealthResponse(mopUserAuthonAppDTO);
	}

}

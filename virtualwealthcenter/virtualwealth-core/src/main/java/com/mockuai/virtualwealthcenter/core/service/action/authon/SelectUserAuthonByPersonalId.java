package com.mockuai.virtualwealthcenter.core.service.action.authon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;

@Service
public class SelectUserAuthonByPersonalId extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(SelectUserAuthonByPersonalId.class);

	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_PERSONALID_SEL.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		String authonPersonalid = (String) context.getRequest().getParam("authonPersonalid");
        VirtualWealthPreconditions.checkNotNull(authonPersonalid, "authonPersonalid");
        MopUserAuthonAppDTO mopUserAuthonAppDTO = userAuthonAppManager.selectMopUserAuthonByPersonalId(authonPersonalid);
        return new VirtualWealthResponse(mopUserAuthonAppDTO);
	}
}

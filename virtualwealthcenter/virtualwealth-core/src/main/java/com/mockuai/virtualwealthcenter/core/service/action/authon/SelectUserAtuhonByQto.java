package com.mockuai.virtualwealthcenter.core.service.action.authon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;

@Service
public class SelectUserAtuhonByQto extends TransAction{
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	@Override
	public String getName() {
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_BYQTO_SEL.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		MopUserAuthonAppQTO mopUserAuthonAppQTO = (MopUserAuthonAppQTO) context.getRequest().getParam("mopUserAuthonAppQTO");
		List<MopUserAuthonAppDTO> mopUserAuthonAppDTOlist = userAuthonAppManager.selectUserAuthonByQto(mopUserAuthonAppQTO);
        return new VirtualWealthResponse(mopUserAuthonAppDTOlist);
	}
}

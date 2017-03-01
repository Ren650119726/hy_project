package com.mockuai.virtualwealthcenter.core.service.action.authon;

import java.util.List;

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

@Service
public class SelectUserAuthonByUserIdList  extends TransAction{
	private static final Logger log = LoggerFactory.getLogger(SelectUserAuthonByUserIdList.class);

	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	@Override
	public String getName() {
		
		return com.mockuai.virtualwealthcenter.common.constant.ActionEnum.USER_AUTHON_USERIDLIST_SEL.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		List<Long> useridlist = (List<Long>) context.getRequest().getParam("userIdList");
		List<MopUserAuthonAppDTO> mopUserAuthonAppDTO = userAuthonAppManager.selectMopUserAuthonList(useridlist);
		return new VirtualWealthResponse(mopUserAuthonAppDTO);
	}

	
	
}

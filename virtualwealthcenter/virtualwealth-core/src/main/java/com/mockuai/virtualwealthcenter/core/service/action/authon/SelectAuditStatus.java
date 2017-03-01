package com.mockuai.virtualwealthcenter.core.service.action.authon;
/**
 * 查询审核状态
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UserAuthonAppManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
@Service
public class SelectAuditStatus extends TransAction{
	@Autowired
	private UserAuthonAppManager userAuthonAppManager;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ActionEnum.SELECT_AUDIT_STATUS.getActionName();
	}

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context)
			throws VirtualWealthException {
		Long userId =(Long) context.getRequest().getParam("userId");
		MopUserAuthonAppDTO mopUserAuthonAppDTO = new MopUserAuthonAppDTO();
		mopUserAuthonAppDTO	= userAuthonAppManager.selectAuditStatus(userId);
		if(null==mopUserAuthonAppDTO||mopUserAuthonAppDTO.equals("")){
			return new VirtualWealthResponse(ResponseCode.NOT_EXIST_USER);
		}
		return new VirtualWealthResponse(mopUserAuthonAppDTO);
	}

}

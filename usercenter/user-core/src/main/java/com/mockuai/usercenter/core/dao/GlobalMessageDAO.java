package com.mockuai.usercenter.core.dao;

import java.util.List;

import com.mockuai.usercenter.common.qto.GlobalMessageQTO;
import com.mockuai.usercenter.core.domain.GlobalMessageDO;

public interface GlobalMessageDAO {

	public Long addGlobalMessage(GlobalMessageDO globalMessage);
	
	
	public List<GlobalMessageDO> queryGlobalMessage(GlobalMessageQTO globalMessageQTO);
	
	
	public Long getTotalCount(GlobalMessageQTO globalMessageQTO);
	
}

package com.mockuai.virtualwealthcenter.core.dao;

import java.util.List;

import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;

public interface UserAuthonAppDAO {
	
	Long addUserAuthon(UserAuthonAppDO userAuthonAppDO);
	
	UserAuthonAppDO selectUserAuton(Long userId);
	
	UserAuthonAppDO selectPersonalId(String authonPersonalid);
	
	List<UserAuthonAppDO> selectUserAuthonByQto(MopUserAuthonAppQTO mopUserAuthonAppQTO);
	
	List<UserAuthonAppDO> selectUserAuthonAll(UserAuthonQTO usrAuthonQTO);
	
	Integer modifyAuditStatus(UserAuthonAppDO userAuthonAppDO);
	
	UserAuthonAppDO selectUserAuthonById(Long id);
	
	UserAuthonAppDO selectAuditStatus(Long userId);
	
	Long updateUserAuton(UserAuthonAppDO uAuthonAppDO);
	
	Long selectUserAuthonAllCount(UserAuthonQTO userAuthonQTO);
	
	Long syncPhoneNumber(Long userId,String authonMobile);
}

package com.mockuai.virtualwealthcenter.core.manager;

import java.util.List;

import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.core.domain.UserAuthonAppDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

public interface UserAuthonAppManager {
	Long addUserAuthon(UserAuthonAppDO UserAuthonAppDO) throws VirtualWealthException ;
	UserAuthonAppDO selectUserAuton(Long userId) throws VirtualWealthException;
	MopUserAuthonAppDTO selectMopUserAuthon(Long userId) throws VirtualWealthException;
	UserAuthonAppDO selectPersonalId (String authonPersonalId) throws VirtualWealthException;
	MopUserAuthonAppDTO selectMopUserAuthonByPersonalId (String authonPersonalId) throws VirtualWealthException;
	List<MopUserAuthonAppDTO> selectMopUserAuthonList (List<Long> userIdList) throws VirtualWealthException;
	//通用接口
	List<MopUserAuthonAppDTO> selectUserAuthonByQto(MopUserAuthonAppQTO mopUserAuthonAppQTO) throws VirtualWealthException;
	
	//审核接口
	Integer modifyAuditStatus(UserAuthonAppDO userAuthonAppDO) throws VirtualWealthException;
	
	//根据id查询用户信息
	MopUserAuthonAppDTO selectUserAuthonById(Long id) throws VirtualWealthException; 
	
	//查询所有审核用户信息
	List<BossUserAuthonDTO> selectUserAuthonAll(UserAuthonQTO userAuthonQTO) throws VirtualWealthException;
	
	//查询所有审核用户信息总数
	Long selectUserAuthonAllCount(UserAuthonQTO userAuthonQTO) throws VirtualWealthException;
	
	//根据用户id查询审核状态
	MopUserAuthonAppDTO selectAuditStatus(Long userId) throws VirtualWealthException;
	
	//审核失败后重新提交审核信息
	Long updateUserAuton(UserAuthonAppDO userAuthonAppDO)throws VirtualWealthException;
	
	//用户更改用户名时同步到实名表中
	Long SyncPhoneNumber(UserAuthonAppDO userAuthonAppDO)throws VirtualWealthException;
	
}

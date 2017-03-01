//package com.mockuai.usercenter.core.dao;
//
//import com.mockuai.usercenter.common.qto.UserExtraInfoQTO;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//import com.mockuai.usercenter.core.domain.UserExtraInfoDO;
//
//import java.util.List;
//
//@Service
//public interface UserExtraInfoDao {
//	/** 添加用户扩展信息 */
//	Long addUserExtraInfo(UserExtraInfoDO userExtraDo);
//
//	/** 根据扩展表的id，获取扩展信息 */
//	UserExtraInfoDO getUserExtraInfoById(Long id);
//
//	/** 根据用户id，获取指定用户的扩展信息 */
//	UserExtraInfoDO getUserExtraInfoByUserId(Long userId);
//
//	/** 修改用户的扩展信息 */
//	int updateUserExtraInfo(UserExtraInfoDO userExtraDo);
//
//	/**
//	 * 删除
//	 * */
//	int delUserExtraInfoByUserId(Long userId);
//
//	int restoreUserExtraInfoByUserId(Long userId);
//
//	/**
//	 * 根据第三方平台的id和平台类型，查询是否存在指定的用户
//	 * */
//	UserExtraInfoDTO getInfoByOpenIdAndAuthType(
//			UserExtraInfoDTO userExtraInfoDto);
//
//	/**
//	 * 条件查询
//	 * */
//	List<UserExtraInfoDO> queryUserExtraInfo(UserExtraInfoQTO extraInfoQTO);
//}

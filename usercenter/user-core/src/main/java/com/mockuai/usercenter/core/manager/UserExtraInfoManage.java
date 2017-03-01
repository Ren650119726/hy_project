//package com.mockuai.usercenter.core.manager;
//
//import com.mockuai.usercenter.common.qto.UserExtraInfoQTO;
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.UserExtraInfoDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//
//import java.util.List;
//
//@Service
//public interface UserExtraInfoManage {
//	/**
//	 * 添加用户扩展属性
//	 * */
//	UserExtraInfoDTO addUserExtraInfo(UserExtraInfoDTO userExtraDto)
//			throws UserException;
//
//	/**
//	 * 根据扩展表的id得到用户的扩张信息
//	 * */
//	UserExtraInfoDTO getUserExtraInfoById(Long id) throws UserException;
//
//	/**
//	 * 根据用户id获取扩展表的信息
//	 * */
//	UserExtraInfoDTO getUserExtraInfoByUserId(Long userId) throws UserException;
//
//	/**
//	 * 修改买家用户的扩展信息
//	 * */
//	int updateUserExtraInfo(UserExtraInfoDTO userExtraDto) throws UserException;
//
//	/**
//	 * 逻辑删除供应商的扩展信息
//	 * */
//	int delUserExtraInfoByUserId(Long userId) throws UserException;
//
//	/**
//	 * 将删除的买家扩展信息还原
//	 *
//	 * @param userId
//	 * @return
//	 */
//	int restoreUserExtraInfoByUserId(Long userId) throws UserException;
//
//	/**
//	 * 根据第三方平台的openid和平台类型，查询是否存在消息
//	 * */
//	UserExtraInfoDTO getInfoByOpenIdAndAuthType(
//			UserExtraInfoDTO userExtraInfoDto) throws UserException;
//
//	/**
//	 * 查询用户的额外信息
//	 * */
//	List<UserExtraInfoDTO> queryUserExtraInfo(UserExtraInfoQTO userExtraInfoQTO)
//			throws UserException;
//
//}

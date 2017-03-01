//package com.mockuai.usercenter.core.manager;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.common.dto.UserBabyInfoDTO;
//import com.mockuai.usercenter.core.exception.UserException;
//
//@Service
//public interface UserBabyInfoManager {
//
//	UserBabyInfoDTO addBabyInfo(UserBabyInfoDTO babyInfoDto)
//			throws UserException;
//
//	/**
//	 * 根据id获取baby的信息
//	 * */
//	public UserBabyInfoDTO getBabyInfoById(Long id) throws UserException;
//
//	/**
//	 * 查询指定用户的baby总数
//	 * */
//	public int getBabyCountByUserId(Long userId) throws UserException;
//
//	int deleteBabyInfo(Long userId, Long babyId) throws UserException;
//
//	int updateBabyInfo(UserBabyInfoDTO babyInfoDto) throws UserException;
//
//	List<UserBabyInfoDTO> queryBabyInfo(Long userId) throws UserException;
//
//	/**
//	 * 查看指定的用户是否有默认的baby
//	 * */
//	UserBabyInfoDTO getDefBabyInfo(Long userId) throws UserException;
//
//	/**
//	 * 将指定用户设置为非默认
//	 * */
//	int setNonDef(Long babyId) throws UserException;
//
//	/**
//	 * 删除用户所有的宝宝信息
//	 *
//	 * @param userId
//	 * @return
//	 */
//	int deleteUserAllBaby(Long userId) throws UserException;
//
//	/**
//	 * 将删除用户所有的宝宝信息还原
//	 *
//	 * @param userId
//	 * @return
//	 */
//	int restoreUserAllBaby(Long userId) throws UserException;
//}

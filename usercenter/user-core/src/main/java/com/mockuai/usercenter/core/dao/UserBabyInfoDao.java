//package com.mockuai.usercenter.core.dao;
//
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import com.mockuai.usercenter.core.domain.UserBabyInfoDO;
//
//@Service
//public interface UserBabyInfoDao {
//
//	Long addBabyInfo(UserBabyInfoDO babyInfoDo);
//
//	UserBabyInfoDO getBabyInfoById(Long id);
//
//	int getBabyCountByUserId(Long userId);
//
//	int deleteBabyInfo(Long userId, Long babyId);
//
//	int updateBabyInfo(UserBabyInfoDO userBabyInfoDo);
//
//	List<UserBabyInfoDO> queryBabyInfo(Long userId);
//
//	UserBabyInfoDO getDefBabyInfo(Long userId);
//
//	int setNonDef(Long babyId);
//
//	/**
//	 * 删除用户的所有baby
//	 *
//	 * @param userId
//	 * @return
//	 */
//	int deleteUserAllBaby(Long userId);
//
//	int restoreUserAllBaby(Long userId);
//
//}

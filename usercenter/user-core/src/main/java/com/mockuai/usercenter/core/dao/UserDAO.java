package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.domain.UserDO;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface UserDAO {
	Long addUser(UserDO userDo);

	int updatePwd(Long userId, String newPwd);

	int updateEmail(Long userId, String email);

	int updateMobile(Long userId, String mobile);

	int activeUser(Long userId);

	UserDO getUserByName(Map map);

	UserDO getUserByEmail(Map map);

	UserDO getUserByMobile(Map map);

	UserDO getUserById(Long userId);

	int freezeUser(Long userId);

	/**
	 * 解冻用户
	 * @param userId
	 * @return
	 */
	int thawUser(Long userId);

	int moveToRecycle(Long userId);

	int deleteUser(Long userId);

	/**
	 * 复合查询用户列表
	 * */
	List<UserDO> queryUser(UserQTO userQto);

	/**
	 * 查询普通用户和迁移过来的老用户
	 * */
	List<UserDO> queryNormalAndOldUser(UserQTO userQTO);

	/**
	 * 设置用户角色
	 * */
	int setUserRoleMark(Long userId, Long roleMark);

	/**
	 * 获取表中记录的总数
	 *
	 * @param userQTO*/
	Long getTotalCount(UserQTO userQTO);

	/**
	 * 普通用户和迁移用户的总量
	 * */
	Long getNormalAndOldTotalCount(UserDO userDO);

	/**
	 * 将用户从回收站还原
	 * */
	int restoreUser(Long userId);

	/**
	 * 获取指定回收站中的用户
	 * */
	UserDO getRecycleUser(Long userId);

	/**
	 * 用户登陆接口
	 * */
	UserDO userLogin(String loginName, String loginPwd);

	/**
	 * 修改用户名称
	 * */
	int updateName(Long userId, String name);

	/**某一时间段内的
	 * 修改用户的基础信息
	 * */
	int updateUser(UserDO userDo);

	/**
	 * 修改用户头像
	 * 
	 * @param userId
	 * @param headImg
	 * @return
	 */
	int updateHeadImg(Long userId, String headImg);

	/**
	 * 根据登陆名查找用户
	 * 
	 * @param loginName
	 * @param bizCode
	 * @return
	 */
	UserDO getByLoginName(String loginName);

	/**
	 * 根据邀请码查用户
	 * */
	UserDO getByInvitationCode(String invitationCode);
	
	/**
	 * 根据邀请人id查用户
	 * */
	UserDO getByInvitationId(Long invitationId);

	/**
	 * 修改用户邀请码
	 * */
	int updateInvitationCode(Long userId, String name);

	/**
	 * 通过用户注册时的设备类型查询用户
	 * */
	List<UserDO> queryUserByDevice(UserQTO userQTO);

	/**
	 * 通过给定的用户ID列表查询指定的用户
	 * */
	List<UserDO> queryFromIdList(List idList);

	/**
	 * 通过手机号批量查询用户
	 * */
	List<UserDO> queryByMobiles(List mobiles, String bizCode);

	/**
	 * 更新角色类型
	 * @param userId
	 * @param roleType
	 * @param bizCode
     */
	void updateRoleType(Long userId, Long roleType, String bizCode);

	/**
	 * 更新用户类型
	 * @param userId
	 * @param userType
	 * @param bizCode
     */
	void updateUserType(Long userId, Integer userType, String bizCode);

	/**
	 * 统计某一时间段内的有效用户,分页显示
	 * @param userQTO
	 * @return
     */
	List<UserDO> totalValidUsers(UserQTO userQTO);

	/**
	 * 统计某一时间段内的有效用户总数
	 * @param userQTO
	 * @return
     */
	long getTotalValidCount(UserQTO userQTO);
	
	/**
	 * 设置支付密码
	 * 
	 * @param userId
	 * @param pay_pwd
	 * @return
	 */
	int resetUserPayPwd(Long userId, String pay_pwd);
	
	/**
	 * 更新昵称
	 * 
	 * @param userId
	 * @param nick_name
	 * @return
	 */
	int updateNickName(Long userId, String nick_name);
	
	/**
	 * 根据用户昵称查询用户信息
	 * 
	 * @param map
	 * @return
	 */
	List<UserDO> getUserByNickName(String nick_name);
	
	/**
	 * 更新用户性别和生日
	 * 
	 * @param userId
	 * @param sex
	 * @param birthday
	 * @return
	 */
	int updateSexAndBirthday(Map<Object, Object> map);
	
	/**
	 * 更新用户邀请人id
	 * 
	 * @param userId
	 * @param invitationId
	 * @param invitationCode 
	 * @return
	 */
	int updateUserInvitationId(Long userId, Long invitationId);
	
	/**
	 * 更新用户最后一次访问分销商id
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 */
	int updateLastDistributorId(Long userId, Long sellerId);
	
	/**
	 * 更新用户邀请人id
	 * 
	 * @param userId
	 * @param inviterId
	 * @return
	 */
	int updateInviterId(Long userId, Long inviterId);


	int addFansToInviterId(Long userId, Long inviterId);

	
	/**
	 * 查询此用户下面的邀请人信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserDO> queryInviterListByUserId(UserQTO userQTO);
	
	/**
	 * 设置修改用户微信和qq号码
	 * 
	 * @param map
	 * @return
	 */
	int updateWxAndQq(Map<Object, Object> map);
}

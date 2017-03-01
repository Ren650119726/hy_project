package com.mockuai.usercenter.core.manager;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.exception.UserException;

@Service
public interface UserManager {
    /**
     * 添加用户
     */
    UserDTO addUser(UserDTO userDto) throws UserException;

    /**
     * 添加来自第三方开放平台的用户
     *
     * @param userDto
     * @return
     * @throws UserException
     */
    UserDTO addOpenUser(UserDTO userDto, String appKey) throws UserException;


    /**
     * 根据用户名查找用户
     */
    UserDTO getUserByName(String name, String bizCode) throws UserException;

    /**up
     * 根据用户email查找用户
     */
    UserDTO getUserByEmail(String email, String bizCode) throws UserException;

    /**
     * 根据用户手机查找用户
     */
    UserDTO getUserByMobile(String mobile) throws UserException;

    /**
     * 根据用于id查找用户
     */
    UserDTO getUserById(Long userId) throws UserException;

    /**
     * 更新用户密码
     * @param mFlag 
     */
    int updatePwd(Long userId, String newPwd,String mobile,String verify_code, String mFlag) throws UserException;

    /**
     * 跟新用户名
     */
    int updateName(Long userId, String name, String bizCode) throws UserException;

    /**
     * 更新用户email
     */
    int updateEmail(Long userId, String email, String bizCode) throws UserException;

    /**
     * 更新用户手机
     * @param mFlag 
     */
    int updateMobile(Long userId, String mobile, String verify_code, String mFlag) throws UserException;

    /**
     * 激活用户
     */
    int activeUser(Long userId) throws UserException;

    /**
     * 冻结用户
     */
    int freezeUser(Long userId) throws UserException;

    /**
     * 解冻用户
     */
    int thawUser(Long userId) throws UserException;

    /**
     * 将用户移入回收站
     */
    int moveToRecycle(Long userId) throws UserException;

    /**
     * 逻辑删除用户:删除不用户不能恢复
     */
    int deleteUser(Long userId) throws UserException;

    /**
     * 符合查询用户
     */
    List<UserDTO> queryUser(UserQTO userQto) throws UserException;

    /**
     * 查询普通用户和数据迁入的老用户
     */
    List<UserDTO> queryNormalAndOldUser(UserQTO userQTO) throws UserException;

    /**
     * 通过设备类型查询用户
     */
    List<UserDTO> queryUserByDevice(UserQTO userQTO) throws UserException;

    /**
     * 设置用户角色
     */
    int setUserRoleMark(Long userId, Long roleMark) throws UserException;

    /**
     * 将用户从回收站还原
     */
    int restoreUser(Long userId) throws UserException;

    /**
     * 根据id获取在回收站中的用户
     */
    UserDTO getRecycleUser(Long userId) throws UserException;

    /**
     * 用户登陆
     * @param loginFlag 
     */
    UserDTO userLogin(String loginName, String loginPwd, String loginVerifyCode, String loginFlag) throws UserException;


    /**
     * 修改用户的基本信息
     */
    Integer updateUser(UserDTO userDto) throws UserException;

    /**
     * 查询指定查询条件下的用户总数
     */
    Long getTotalCount(UserQTO userQto) throws UserException;

    /**
     * 查询普通用户和数据迁移用户的数量
     */
    Long getNormalAndOldTotalCount(UserQTO userQTO) throws UserException;

    /**
     * 修改用户的头像
     *
     * @param userId
     * @param headImg
     * @param appKey 
     * @return
     */
    int updateHeadImg(Long userId, String headImg, String appKey) throws UserException;

    /**
     * 根据登录名获取用户
     */
    UserDTO getUserByLoginName(String loginName) throws UserException;

    /**
     * 根据邀请码获取用户
     *
     * @param invitationCode
     * @return
     */
    UserDTO getUserByInvitationCode(String invitationCode) throws UserException;

    /**
     * 生成不重复的邀请码
     */
    String generateInvitationCode(String bizCode) throws UserException;

    /**
     * 更新用户邀请码
     */
    int updateInvitationCode(Long userId, String invitationCode) throws UserException;



    /**
     * 通过用户ID列表查询用户
     */
    List<UserDTO> queryFromIdList(List idList) throws UserException;

    /**
     * 通过手机号批量查询用户
     */
    List<UserDTO> queryByMobiles(List<String> mobiles, String bizCode) throws UserException;

    /**
     * 更新角色类型
     *
     * @param userId
     * @param roleType
     * @param bizCode
     */
    void updateRoleType(Long userId, Long roleType, String bizCode) throws UserException;

    /**
     * 更新用户类型
     *
     * @param userId
     * @param userType
     * @param bizCode
     */
    void updateUserType(Long userId, Integer userType, String bizCode);

    /**
     * 查找某一时间段内的有效用户,分页
     *
     * @param userQTO
     * @return
     */
    List<UserDTO> totalValidUsers(UserQTO userQTO);

    /**
     * 统计某一时间段内的有效用户总数
     *
     * @param userQTO
     * @return
     */
    long getTotalValidCount(UserQTO userQTO) throws UserException;
    
    /**
     * 检验原手机号验证码是否输入正确或原手机号丢失校验身份证时候正确
     * 
     * @param userId
     * @param mobile
     * @param verify_code
     * @param bank_no
     * @param mFlag
     * @param appKey 
     * @return
     */
	void checkUserMobile(Long userId, String mobile, String verify_code,String bank_personal_id, String mFlag, String appKey) throws UserException;
	
	/**
	 * 更新支付密码
	 * 
	 * @param userId
	 * @param pay_pwd
	 * @param new_pay_pwd
	 * @param id_card
	 * @param payFlag 
	 * @throws UserException 
	 */
	int resetUserPayPwd(Long userId, String pay_pwd, String once_pay_pwd, String id_card, String appKey, String payFlag) throws UserException;
	
	/**
	 * 修改支付密码
	 * 
	 * @param userId
	 * @param pay_pwd
	 * @param new_pay_pwd
	 * @param once_pay_pwd
	 * @return
	 * @throws UserException
	 */
	int updateUserPayPwd(Long userId, String pay_pwd, String new_pay_pwd, String once_pay_pwd) throws UserException;
	
	/**
	 * 更新昵称
	 * 
	 * @param userId
	 * @param nick_name
	 * @return
	 * @throws UserException
	 */
	int updateNickName(Long userId, String nick_name) throws UserException;
	
	/**
	 * 更新性别和生日
	 * 
	 * @param userId
	 * @param sex
	 * @param birthday
	 * @return
	 * @throws UserException 
	 */
	int updateSexAndBirthday(Long userId, String sex, String birthday) throws UserException;
	
	/**
	 * 查询用户个人资料
	 * 
	 * @param userId
	 * @return
	 * @throws UserException 
	 */
	UserDTO queryUserOneselfInfo(Long userId, String appKey) throws UserException;
	
	/**
	 * 用户注册账户
	 * 
	 * @param mobile
	 * @param verifyCode
	 * @param password
	 * @param invitationId
	 * @param invitationCode
	 * @param registerFlag
	 * @throws UserException 
	 */
	UserDTO userRegister(Map<Object, Object> userDtoMap) throws UserException;
	
	/**
	 * 微信快捷登录
	 * 
	 * @param authCode
	 * @param appKey
	 * @param appType
	 * @return
	 * @throws UserException 
	 */
	UserDTO wechatLogin(String authCode, String appKey, int appType) throws UserException;
	
	/**
	 * 更新用户登录时缺失的信息
	 * 
	 * @param userId
	 * @param mobile
	 * @param verifyCode
	 * @param invitationCode 
	 * @return
	 * @throws UserException
	 */
	
	int updateUserLoginInfoMiss(Long userId, String mobile, String password, String verifyCode, Long invitationId) throws UserException;
	/**
	 * 更新用户邀请人id
	 * 
	 * @param userId
	 * @param invitationId
	 * @return
	 * @throws UserException
	 */
	int updateUserInvitationId(Long userId, Long invitationId) throws UserException;
	
	/**
	 * 查询用户个人资料
	 * 
	 * @param userId
	 * @return
	 * @throws UserException 
	 */
	Map<Object, Object> queryUserSafeInfo(Long userId, String appKey) throws UserException;
	
	/**
	 * 更新用户最后一次访问记录分销商id
	 * 
	 * @param userId
	 * @param sellerId
	 * @return
	 * @throws UserException
	 */
	int UpdateLastDistributorId(Long userId, Long sellerId) throws UserException;
	
	/**
	 * 更新用户邀请人id
	 * 
	 * @param userId
	 * @param inviterId
	 */
	int updateInviterId(Long userId, Long inviterId) throws UserException;


    /***
     * hsq
     * 给指定inviterId 添加粉丝
     * @param userId
     * @param inviterId
     * @return
     * @throws UserException
     */
    int addFansToInviterId(Long userId, Long inviterId)throws UserException;


	/**
	 * 校验邀请人id 和被邀请人id
	 * @param userId
	 * @param inviterId
	 * @return
	 * @throws UserException
	 */
	Long validateInviterId(Long userId, Long inviterId)throws UserException;



    /**
	 * 校验旧支付密码
	 * 
	 * @param userId
	 * @param pay_pwd
	 */
	void checkUserOldPayPwd(Long userId, String pay_pwd) throws UserException;
	
	/**
	 * 查询用户昵称是否已经存在
	 * 
	 * @param trim
	 * @return
	 * @throws UserException
	 */
	UserDTO getUserByNickName(String nick_name) throws UserException;
	
	/**
	 * 查询用户通讯录所对应的卖家邀请码
	 * 
	 * @param userMobileList
	 * @param appKey
	 * @return
	 */
	Map<Object, Object> queryUserMobileDirectory(List<String> userMobileJsonList, String appKey) throws UserException;
	
	/**
	 * 查询此用户下面对应买家邀请人信息
	 * 
	 * @param userId
	 * @param appKey
	 * @return
	 * @throws UserException
	 */
	List<UserDTO> queryInviterListByUserId(UserQTO userQTO, String appKey) throws UserException;
	
	/**
	 * 判断此用户是否存在成为嗨客的条件
	 * 
	 * @param userId
	 * @param appKey
	 * @return
	 * @throws UserException
	 */
	UserDTO queryHiKeCondition(Long userId, String appKey) throws UserException;
	
	/**
	 * 设置修改微信和qq号码
	 * 
	 * @param userId
	 * @param wechat
	 * @param qqCode
	 * @return
	 * @throws UserException
	 */
	int updateWechatAndQqCode(Long userId, String wechat, String qqCode) throws UserException;
}

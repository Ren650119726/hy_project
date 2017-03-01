package com.mockuai.usercenter.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.*;
import com.mockuai.usercenter.common.qto.UserConsigneeQTO;
import com.mockuai.usercenter.common.qto.UserQTO;
public interface UserClient {

    /**
     * 激活用户
     */
    Response<Boolean> activeUser(Long userId, String appKey);

    /**
     * 删除用户
     */
    Response<Void> deletedUser(Long userId, String appKey);

    /**
     * 冻结用户
     */
    Response<Boolean> freezeUser(Long userId, String appKey);

    /**
     * 解冻用户
     */
    Response<Boolean> thawUser(Long userId,String appKey);

    /**
     * 根据邮箱获取用户
     */
    Response<UserDTO> getUserByEmail(String email, String appKey);

    /**
     * 根据id获取用户
     */
    Response<UserDTO> getUserById(Long userId, String appKey);

    /**
     * 根据电话获取用户
     */
    Response<UserDTO> getUserByMobile(String mobile, String appKey);

    /**
     * 根据用户名获取用户
     */
    Response<UserDTO> getUserByName(String name, String appKey);

    /**
     * 添加用户
     */
    Response<UserDTO> addUser(UserDTO userDto, String appKey);

    /**
     * 添加来自第三方开放平台的用户
     *
     * @param userDto
     * @return
     */
    Response<UserDTO> addOpenUser(UserDTO userDto, String appKey);

    /**
     * 将用户移入回收站
     */
    Response<Boolean> moveUserIntoRecycle(Long userId, String appKey);

    /**
     * 将用户从回收站还原
     */
    Response<Boolean> restoreUser(Long userId, String appKey);

    /**
     * 设置用户角色
     */
    Response<Boolean> setUserRole(Long userId, Byte role, String appKey);

    /**
     * 修改邮箱
     */
    Response<Boolean> updateEmail(Long userId, String email, String appKey);

    /**
     * 修改手机
     */
    Response<Boolean> updateMobile(Long userId, String mobile,String verify_code, String appKey);

    /**
     * 更新用户
     */
    Response<Boolean> updateUser(UserDTO userDTO, String appKey);

    /**
     * 修改密码
     */
    Response<Void> updatePwd(Long userId, String newPwd,String mobile,String verify_code, String appKey);

    /**
     * 修改用户头像
     */
    Response<Boolean> updateHeadImg(Long userId, String headImg, String appKey);

    /**
     * 修改邀请码
     */
    Response<Boolean> updateInvitationCode(Long userId, String invitationCode, String appKey);

    /**
     * 生成不重复的邀请码
     */
    Response<String> generateInvitationCode(String appKey);

    /**
     * 查询用户
     */
    Response<List<UserDTO>> queryUser(UserQTO userQto, String appKey);

    /**
     * 查询普通用户和数据迁入的老用户
     */
    Response<List<UserDTO>> queryNormalAndOldUser(UserQTO userQTO, String appKey);

    /**
     * 第三方用户登陆
     */
    Response<UserDTO> apiUserLogin(UserInfoDTO userInfoDto, String appKey);

    /**
     * 用户登陆
     */
    Response<UserDTO> userLogin(String loginName, String loginPwd, String appKey);

    /**
     * @param openType
     * @param openUid
     * @return
     */
    Response<UserOpenInfoDTO> getUserOpenInfo(Integer openType, String openUid, String appKey);

    /**
     * @param openId
     * @param appId
     * @param appKey
     * @return
     */
    Response<UserOpenInfoDTO> getOpenInfoByOpenId(String openId, String appId, String appKey);

    /**
     * @param userOpenInfoDTO
     * @return
     */
    Response<UserOpenInfoDTO> addUserOpenInfo(UserOpenInfoDTO userOpenInfoDTO, String appKey);

    Response<Void> bindUserOpenInfo(Long openInfoId, Integer openType, String openUid,
                                    String mobile, String password, String invitationCode, String appKey);

    /**
     * @param startTime
     * @param endTime
     * @param appType
     * @return
     */
    Response<List<UserAccountDTO>> queryUserAccountByDevice(Date startTime, Date endTime, Integer appType, String appKey);

    /**
     * 通过用户ID列表查询用户
     *
     * @param appKey
     * @param idList
     */
    Response<List<UserDTO>> queryFromIdList(List<Long> idList, String appKey);

    /**
     * 通过用户ID获得用户开放信息
     *
     * @param userId
     * @param appKey
     */
    Response<UserOpenInfoDTO> getOpenInfoByUserId(Long userId, String appKey);

    /**
     * 通过手机号批量查询用户
     *
     * @param mobiles
     * @param appKey
     */
    Response<List<UserDTO>> queryUserByMobiles(List<String> mobiles, String appKey);

    /**
     * 更新角色类型
     *
     * @param userId
     * @param newRoleType
     * @param appKey
     * @return
     */
    Response<Boolean> updateRoleType(Long userId, Long newRoleType, String appKey);

    /**
     * 更新用户类型
     *
     * @param userId
     * @param newUserType
     * @param appKey
     * @return
     */
    Response<Boolean> updateUserType(Long userId, Integer newUserType, String appKey);


    /**
     * 统计有效的用户数
     * @param start
     * @param end
     * @param offset
     * @param count
     * @param appKey
     * @return
     */
    Response<List<UserDTO>> totalValidUsers(Date start, Date end, Long offset, Integer count, String appKey);

    Response<Void> putSession(String sessionKey, Object sessionObject, String appKey);

    Response<Object> getSession(String sessionKey, String appKey);

    /**
     * 根据登录名获取用户
     */
    Response<UserDTO> getUserByLoginName(String loginName, String appKey);
    
    /**
     * 注册用户
     */
    Response<UserDTO> userRegister(Map<Object, Object> userDtoMap, String appKey);
    
    /**
     * 设置用户缺失信息
     */
    Response<Boolean> UpdateUserLoginInfoMiss(Long userId, String mobile, String password, String verifyCode, String invitationCode,String appKey);
    
    /**
     * 更新用户最后一次访问记录id
     * 
     */
    Response<Void> updateLastDistributorId(Long userId, Long sellerId, String appKey);
    
    /**
     * 更新用户邀请人id
     * 
     * @param userId
     * @param inviterId
     * @param appKey
     * @return
     */
    Response<Void> updateInviterId(Long userId, Long inviterId, String appKey);


    /**
     * hsq
     * 后台绑定粉丝和上级关系
     * @param userId
     * @param inviterId
     * @param appKey
     * @return
     */
    public Response<Void> addFansToInviterId(Long userId, Long inviterId, String appKey);


    /**
     * hsq
     * 校验邀请人id
     * @param userId
     * @param inviterId
     * @param appKey
     * @return
     */
     Response<Long> validateInviterId(Long userId, Long inviterId, String appKey);




    
    /**
     * 校验支付密码是否正确
     * 
     * @param userId
     * @param payPwd
     * @param appKey
     * @return
     */
    Response<Void> checkUserPayPwd(Long userId, String payPwd, String appKey);

    /**
     * 查询所有收货地址
     */
    Response<List<UserConsigneeDTO>> queryAllConsignee(UserConsigneeQTO userConsigneeQTO,String appKey);
    
    /**
     * 查询此用户下面买家信息数据
     * 
     * @param userQTO
     * @param appKey
     * @return
     */
	Response<List<UserDTO>> queryInviterListByUserId(UserQTO userQTO, String appKey);
	
	/**
	 * 查询用户当前所处状态
	 * 
	 * @param userId
	 * @param appKey
	 * @return
	 */
	Response<UserDTO> queryHiKeCondition(Long userId, String appKey);
}

package com.mockuai.usercenter.core.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.BindFansAndInviterActionEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.hanshu.imagecenter.client.ImageClient;
import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerQTO;
import com.mockuai.messagecenter.common.constant.HandleTypeEnum;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.domain.OrderQTO;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.constant.RoleType;
import com.mockuai.usercenter.common.constant.UserType;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;
import com.mockuai.usercenter.core.dao.UserDAO;
import com.mockuai.usercenter.core.domain.UserDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.AppManager;
import com.mockuai.usercenter.core.manager.CacheManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.util.IdCardCheckUtil;
import com.mockuai.usercenter.core.util.JsonUtil;
import com.mockuai.usercenter.core.util.UserUtil;
import com.mockuai.virtualwealthcenter.client.UserAuthonClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;

@Service
public class UserManagerImpl implements UserManager {
	private static final Logger log = LoggerFactory
			.getLogger(UserManagerImpl.class);
	private static final String PAY_PWD_ADD_SALT = "C@S#Y$";

	@Resource
	private UserDAO userDAO;

	@Resource
	private CacheManager cacheManager;

	@Resource
	private AppManager appManager;

	@Resource
	private UserAuthonClient userAuthonClient;

	@Resource
	private ImageClient imageClient;
	
	@Resource
    private DistributionClient distributionClient;
	
	@Resource
	private OrderClient orderClient;

	private static List<Character> characters;

	static {
		characters = new ArrayList<Character>();
		for (char i = '4'; i <= '9'; i++) {
			characters.add(i);
		}

		for (char i = 'A'; i <= 'Z'; i++) {
			characters.add(i);
		}
	}

	public UserDTO addUser(UserDTO userDto) throws UserException {
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userDTO is null");
		}

		String bizCode = userDto.getBizCode();

		if (bizCode == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"bizCode is null");
		}

		// 判断用户的数据是否合法
		UserUtil.userInfoIsLegal(userDto);

		// 判断邮箱是否被注册，一个邮箱只能被使用一次
		String email = userDto.getEmail();
		if (email != null) {
			if (null != getUserByEmail(email, bizCode)) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"email is already register");
			}
		}

		// 判断手机是否被注册，一个手机号只能被使用一次
		String mobile = userDto.getMobile();

		if (mobile != null) {
			if (null != getUserByMobile(mobile)) {
				log.error(
						"mobile has been registered with mobile = {}, bizCode = {}",
						mobile, bizCode);
				throw new UserException(ResponseCode.B_MOBILE_IS_EXIST,
						"手机号已经被注册");
			}
		}

		// TODO 默认命名方式待讨论，上线前务必确定下来
		String name = userDto.getName();
		// 如果NAME为空，则系统自动设置NAME
		if (StringUtils.isBlank(name)) {
			if (StringUtils.isNotBlank(mobile)) {
				userDto.setName(mobile);
			} else if (StringUtils.isNotBlank(email)) {
				userDto.setName(email);
			}
		} else {
			// 判断用户名是否被注册，不能出现相同的用户名
			if (null != getUserByName(name, bizCode)) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"username is already register");
			}
		}

		// 验证座机号的格式
		if (null != userDto.getPhone()) {
			UserUtil.checkPhoneNo(userDto.getPhone());
		}

		userDto.setInvitationCode(null);

		// 将dto转换为do
		UserDO userDo = new UserDO();

		BeanUtils.copyProperties(userDto, userDo);

		// 为新添加用户生成一个8位随机邀请码
		String myInvitationCode = generateInvitationCode(bizCode);
		userDo.setInvitationCode(myInvitationCode);

		// 设置普通用户的类型
		if (userDo.getType() == null) {
			userDo.setType(UserType.NORMAL_USER.getValue());
		}
		log.info(
				"add user, bizCode = {}, mobile = {}, userType = {}, roleMark = {}, invitationCode = {}, sourceType = {}",
				userDo.getBizCode(), userDo.getMobile(), userDo.getType(),
				userDo.getRoleMark(), userDo.getInvitationCode());
		Long userId = userDAO.addUser(userDo);

		userDto = getUserById(userId);

		return userDto;
	}

	public UserDTO addOpenUser(UserDTO userDto, String appKey) throws UserException {
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userDTO is null");
		}
		
		if(StringUtils.isBlank(appKey)){
			throw new UserException(ResponseCode.P_PARAM_NULL, "appKey is null");
		}

		// 将dto转换为do
		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userDto, userDo);

		userDo.setType(2);// 设置用户类型为2，代表来自第三方开放平台的用户
		String nick_name = nickNameRandom("wx", 9);
		userDo.setName(nick_name);// 用户账号
		userDo.setMobile(nick_name);// 用户手机号
		userDo.setSourceType(2);// 设备类型
		userDo.setBizCode("hanshu");
		userDo.setNickName(nick_name);// 用户昵称
		userDo.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex("wx_123_wx")));// 用户密码
		userDo.setRoleMark(RoleType.BUYER.getValue());// 默认买家
		userDo.setLastDistributorId(0L);// 默认无效值
		userDo.setStatus(0);// 默认非冻结

		//判断邀请人id是否是嗨客
		if(null != userDto.getInviterId()){
			UserDTO userDtIn = getUserById(userDto.getInviterId());
			
			if(null != userDtIn && null != userDtIn.getRoleMark() 
					&& RoleType.SELLER.getValue().equals(userDtIn.getRoleMark())){
				userDo.setInviterId(userDto.getInviterId());//邀请人id
			}
		}

		try {
			Long userId = userDAO.addUser(userDo);
			userDto = getUserById(userId);
			
			//调取分销接口绑定用户关系
			if(null != userDto.getInviterId() && !"".equals(userDto.getInviterId())){
				distributionClient.addRelationship(userDto.getId(), userDto.getInviterId(), appKey);
			}			
			
			return userDto;
		} catch (Exception e) {
			log.error("params:" + JsonUtil.toJson(userDto), e);
			throw new UserException(ResponseCode.B_ADD_ERROR);
		}
	}

	/**
	 * 根据账户名查询用户信息
	 * 
	 * @author csy
	 * @Date 2016-05-28
	 */
	public UserDTO getUserByName(String name, String bizCode)
			throws UserException {
		if (null == name) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"username is null");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("name", name);
		map.put("bizCode", bizCode);
		UserDO userDo = userDAO.getUserByName(map);

		UserDTO userDto = null;
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}

		return userDto;
	}

	public UserDTO getUserByEmail(String email, String bizCode)
			throws UserException {

		UserUtil.checkEamil(email);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		map.put("bizCode", bizCode);
		UserDO userDo = userDAO.getUserByEmail(map);
		UserDTO userDto = null;
		// 将do转换为dto
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	public UserDTO getUserByMobile(String mobile) throws UserException {
		UserUtil.checkMobile(mobile);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		UserDO userDo = userDAO.getUserByMobile(map);
		UserDTO userDto = null;
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	public UserDTO getUserById(Long userId) throws UserException {

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		if (userId <= 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"userId must greater than 0");
		}

		UserDO userDo = userDAO.getUserById(userId);
		UserDTO userDto = null;

		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}

		if (null != userDto) {
			// 过滤手机号
			if (StringUtils.isNotEmpty(userDto.getMobile())
					&& userDto.getMobile().contains("wx")) {
				userDto.setMobile("");
			}

			// 过滤密码
			if (StringUtils.isNotEmpty(userDto.getPassword())
					&& userDto.getPassword()
							.equals(DigestUtils.md5Hex(DigestUtils
									.md5Hex("wx_123_wx")))) {
				userDto.setPassword("");
			}
		}

		return userDto;
	}

	/**
	 * 更新用户登录密码
	 * 
	 * @author csy
	 * @Date 2016-05-12
	 * 
	 * @param userId
	 *            (用户id) newPwd(新密码) mobile(手机号) verify_code(验证码)
	 * 
	 */
	public int updatePwd(Long userId, String newPwd, String mobile,	String verify_code, String mFlag) throws UserException {
		UserDTO userDto = getUserById(userId);

		if (userDto == null) {
			throw new UserException(ResponseCode.HS_USER_INFO_NULL,	"客户信息不存在或未登录");
		}

		if (null == newPwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "输入密码不可为空");
		}

		if (null == mobile) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "填写手机号不可为空");
		}

		if (null == verify_code) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入正确的验证码");
		}
		
		if (StringUtils.isEmpty(mFlag)) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "修改密码状态值为空");
		}

		String md5Pwd = DigestUtils.md5Hex(DigestUtils.md5Hex(newPwd));

		if (null != userDto.getPassword()) {
			if (userDto.getPassword().equals(md5Pwd)) {
				throw new UserException(ResponseCode.HS_USER_PWD_TWICE_ALILE, "新密码与原密码相同,请重新设置");
			}
		}

		// 获取服务端验证码
		String serverSideVerifyCode = null;
		
		if("0".equals(mFlag)){
			serverSideVerifyCode = getVerifyCode(mobile, HandleTypeEnum.MODIFY_PASSWORD.code);
		}else if ("1".equals(mFlag)){
			serverSideVerifyCode = getVerifyCode(mobile, HandleTypeEnum.SET_PASSWORD.code);
		}		

		// 判断验证码长度是否满足
		if (!verify_code.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_FORMAT,
					"验证码最多输入6位数字");
		}

		// 如果服务端验证码不存在
		if (null == serverSideVerifyCode) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_TIMEOUT,
					"验证码已过期");
		}

		if (!serverSideVerifyCode.equals(verify_code)) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_INVALID,
					"验证码错误");
		}

		// 清除验证码
		cacheManager.remove(mobile);

		// 检查新密码的合法性
		UserUtil.checkPwd(newPwd, 6, 15);

		int result = userDAO.updatePwd(userId, md5Pwd);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 获取验证码
	 * 
	 * @author csy
	 * @Date 2016-05-12
	 * @param mobile
	 * @return
	 */
	private String getVerifyCode(String mobile, String typeCode) {
		Object obj = cacheManager.get(mobile + typeCode);
		if (obj != null) {
			return (String) obj;
		} else {
			return null;
		}
	}

	public int updateEmail(Long userId, String email, String bizCode)
			throws UserException {

		if (null == email) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "email is null");
		}

		UserUtil.checkEamil(email);

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		// 如果修改的邮箱已存在，则添加错误
		userDto = getUserByEmail(email, bizCode);
		if (userDto != null) {
			throw new UserException(ResponseCode.B_EMAIL_IS_EXIST,
					"email is exist");
		}

		int result = userDAO.updateEmail(userId, email);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 更新用户手机号
	 * 
	 * @author csy
	 * @Date 2016-05-12
	 * 
	 * @param userId
	 *            (用户id) mobile(用户手机号) verify_code(验证码)
	 */
	public int updateMobile(Long userId, String mobile, String verify_code, String mFlag)
			throws UserException {
		if (null == mobile) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入手机号");
		}

		if (null == verify_code) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "验证不可为空");
		}
		
		if (StringUtils.isEmpty(mFlag)) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "修改手机号状态值为空");
		}

		// 获取服务端验证码
		String serverSideVerifyCode = null;
		
		if("0".equals(mFlag)){
			serverSideVerifyCode = getVerifyCode(mobile, HandleTypeEnum.MODIFY_MOBILE_NEW.code);
		}else if ("1".equals(mFlag)){
			serverSideVerifyCode = getVerifyCode(mobile, HandleTypeEnum.BINDING_MOBILE.code);
		}		

		// 判断验证码长度是否满足
		if (!verify_code.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_FORMAT,
					"验证码最多输入6位数字");
		}

		// 如果服务端验证码不存在
		if (null == serverSideVerifyCode) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_TIMEOUT,
					"验证码已过期");
		}

		if (!serverSideVerifyCode.equals(verify_code)) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_INVALID,
					"验证码错误");
		}

		// 清除验证码
		cacheManager.remove(mobile);
		// 校验手机号格式
		UserUtil.checkMobile(mobile);

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		// 修改时校验是否与原来手机相同
		if (null != userDto.getMobile() && mobile.equals(userDto.getMobile())) {
			throw new UserException(ResponseCode.HS_MOBILE_NEWANDOLD_SAME,
					"新号码与原号码不能相同");
		}

		userDto = getUserByMobile(mobile);
		if (userDto != null) {
			throw new UserException(ResponseCode.B_MOBILE_IS_EXIST, "手机号已被使用");
		}

		int result = userDAO.updateMobile(userId, mobile);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	public int activeUser(Long userId) throws UserException {

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		// 如果用户的状态为激活状态，则激活失败
		if (userDto.getStatus() == 0) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"user is activity");
		}

		int result = userDAO.activeUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	public int freezeUser(Long userId) throws UserException {

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		int result = userDAO.freezeUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int thawUser(Long userId) throws UserException {
		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		int result = userDAO.thawUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	public int moveToRecycle(Long userId) throws UserException {

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		if (userDto.getDeleteMark() == 2) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"user in recycle");
		}

		int result = userDAO.moveToRecycle(userId);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}
		return result;
	}

	public int deleteUser(Long userId) throws UserException {
		// TODO Auto-generated method stub

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}

		int result = userDAO.deleteUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
		}

		return result;
	}

	public List<UserDTO> queryUser(UserQTO userQto) throws UserException {

		if (null == userQto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}

		// 判断邀请人id是否为空，如果不为空，判断是否存在指定的用户
		Long inviterId = userQto.getInviterId();
		if (inviterId != null) {
			if (getUserById(inviterId) == null) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"inviter user is not exist");
			}
		}

		if (null == userQto.getOffset() || userQto.getOffset() < 0) {
			userQto.setOffset(0L);
		}

		// 没传入每页显示总数或者每页显示的数量大于500的话，默认每页显示20条
		if (userQto.getCount() == null || userQto.getCount() > 500) {
			userQto.setCount(20);
		}

		List<UserDO> userDos = userDAO.queryUser(userQto);
		List<UserDTO> userDtos = new ArrayList<UserDTO>();

		for (UserDO userDo1 : userDos) {
			UserDTO userDto = new UserDTO();
			userDo1.setPassword(null);
			BeanUtils.copyProperties(userDo1, userDto);
			userDtos.add(userDto);
		}

		return userDtos;

	}

	public List<UserDTO> queryNormalAndOldUser(UserQTO userQTO)
			throws UserException {
		if (null == userQTO) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}

		// 判断邀请人id是否为空，如果不为空，判断是否存在指定的用户
		Long inviterId = userQTO.getInviterId();
		if (inviterId != null) {
			if (getUserById(inviterId) == null) {
				throw new UserException(ResponseCode.B_ADD_ERROR,
						"inviter user is not exist");
			}
		}

		if (null == userQTO.getOffset() || userQTO.getOffset() < 0) {
			userQTO.setOffset(0L);
		}

		// 没传入每页显示总数或者每页显示的数量大于500的话，默认每页显示20条
		if (userQTO.getCount() == null || userQTO.getCount() > 500) {
			userQTO.setCount(20);
		}

		List<UserDO> userDos = userDAO.queryNormalAndOldUser(userQTO);
		List<UserDTO> userDtos = new ArrayList<UserDTO>();

		for (UserDO userDo1 : userDos) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(userDo1, userDto);
			userDtos.add(userDto);
		}

		return userDtos;
	}

	public List<UserDTO> queryUserByDevice(UserQTO userQTO)
			throws UserException {
		if (null == userQTO) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}

		List<UserDO> userDOs = userDAO.queryUserByDevice(userQTO);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();

		for (UserDO userDO : userDOs) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userDO, userDTO);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	public int setUserRoleMark(Long userId, Long roleMark) throws UserException {

		if (null == roleMark) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"roleMark is null");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		// 用户角色只能为0或1
		if (roleMark != 0 && roleMark != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR,
					"role value is error");
		}
		int result = userDAO.setUserRoleMark(userId, roleMark);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}
		return result;
	}

	public int restoreUser(Long userId) throws UserException {

		int result = userDAO.restoreUser(userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	public UserDTO getRecycleUser(Long userId) throws UserException {

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		if (userId < 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"userId must greater than zero");
		}

		UserDO userDo = userDAO.getRecycleUser(userId);
		UserDTO userDto = null;

		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	/**
	 * 用户验证码或密码登录
	 * 
	 * @author csy
	 * @Date 2016-05-17
	 * 
	 * @param loginName
	 *            (手机号即登录账号) loginPwd(登录密码) loginVerifyCode(登录验证码)
	 *            loginFlag(登录状态:0代表密码1代表验证码)
	 * 
	 */
	public UserDTO userLogin(String loginName, String loginPwd,
			String loginVerifyCode, String loginFlag) throws UserException {
		UserDTO userDto = null;

		if (null == loginFlag) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "登录失败,请重新操作");
		}

		// 判断账号是否存在
		if (null == getUserByLoginName(loginName)) {
			throw new UserException(ResponseCode.B_ACCOUNT_NOT_EXIST,
					"您輸入的手机号还未注册");
		}

		// **************************************密码登录*****************************************//

		if ("0".equals(loginFlag)) {
			if (null == loginPwd) {
				throw new UserException(ResponseCode.P_PARAM_NULL,
						"请输入密码，最多可輸入25位字符");
			}

			// 检验密码位数(25位)
			UserUtil.checkPwd(loginPwd, null, 25);

			// 校验是否含有特殊字符
			if (!loginPwd.matches("^[a-zA-Z_0-9]+$")) {
				throw new UserException(ResponseCode.HS_PWD_MESSAGE_FORMAT,
						"输入密码错误");
			}

			log.info("login, loginName : {}, loginPwd : {}", loginName,
					loginPwd);
			UserDO userDo = userDAO.userLogin(loginName,
					DigestUtils.md5Hex(DigestUtils.md5Hex(loginPwd)));

			if (null == userDo) {
				log.error("password error, loginName : {}", loginName);
				throw new UserException(ResponseCode.B_PASSWORD_ERROR, "输入密码错误");
			}

			if (userDo != null) {
				userDto = new UserDTO();
				BeanUtils.copyProperties(userDo, userDto);
			}
		}

		// **************************************验证码登录*****************************************//

		if ("1".equals(loginFlag)) {
			if (null == loginVerifyCode) {
				throw new UserException(ResponseCode.P_PARAM_NULL, "请輸入验证码");
			}

			// 获取服务端验证码
			String serverSideVerifyCode = getVerifyCode(loginName, HandleTypeEnum.LOGIN.code);
			UserUtil.checkVerifyCode(serverSideVerifyCode, loginVerifyCode);
			// 清除验证码
			cacheManager.remove(loginName);

			// 根据手机号查询客户信息
			userDto = getUserByMobile(loginName);
		}

		if (null == userDto) {
			log.error("password error, loginName : {}", loginName);
			throw new UserException(ResponseCode.B_PASSWORD_ERROR,
					"登录失败,请检查账号密码或验证码是否正确");
		}

		if (!(userDto.getStatus() == null || userDto.getStatus() == 0)) {
			log.error("this account is freezed, loginName : {}", loginName);
			throw new UserException(ResponseCode.B_USER_FREEZE, "用户账号被冻结");
		}

		return userDto;
	}

	@SuppressWarnings("unused")
	public int updateName(Long userId, String name, String bizCode)
			throws UserException {
		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userInfoDto is null");
		}
		if (null == name) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "name is null");
		}

		UserDTO userDto = getUserById(userId);

		int result = userDAO.updateName(userId, name);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	public Integer updateUser(UserDTO userDto) throws UserException {
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userDTO is null");
		}

		// 校验手机号码的正确性
		if (userDto.getMobile() != null) {
			UserUtil.checkMobile(userDto.getMobile());
		}

		// 校验密码的合法性
		if (userDto.getPassword() != null) {
			UserUtil.checkPwd(userDto.getPassword());
		}

		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userDto, userDo);

		int result = userDAO.updateUser(userDo);
		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}
		return result;
	}

	public Long getTotalCount(UserQTO userQto) throws UserException {
		if (null == userQto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}
		Long totalCount = userDAO.getTotalCount(userQto);

		return totalCount;
	}

	public Long getNormalAndOldTotalCount(UserQTO userQTO) throws UserException {
		if (null == userQTO) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}
		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userQTO, userDo);
		Long totalCount = userDAO.getNormalAndOldTotalCount(userDo);

		return totalCount;
	}

	public int updateHeadImg(Long userId, String headImg, String appKey)
			throws UserException {

		if (null == headImg) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "传入图片为空,请重新选择");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "用户未登录或不存在");
		}

		int result = userDAO.updateHeadImg(userId, headImg);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		// 更新二维码 由于现在不需要店铺二维码 则用户更新头像，不需要通知到的二维码center
	/*	com.mockuai.imagecenter.common.api.action.Response<ImageDTO> imageDtoRe = imageClient
				.addShopImage(userId, null, appKey);

		if (null == imageDtoRe || imageDtoRe.isSuccess() == false
				|| null == imageDtoRe.getModule()) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "更新用户二维码失败");
		}*/

		return result;
	}

	/**
	 * 根据用户名判断用户是否存在
	 * 
	 * @author csy
	 * @Date 2016-05-17
	 */
	public UserDTO getUserByLoginName(String loginName) throws UserException {
		if (null == loginName) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"手机号为空,请输入正确的手机号进行登录");
		}

		if (11 != loginName.length()) {
			throw new UserException(ResponseCode.HS_LOGIN_NAME_ERROR,
					"请輸入正确的手机号");
		}

		UserDO userDo = userDAO.getByLoginName(loginName);

		if (null == userDo) {
			throw new UserException(ResponseCode.B_ACCOUNT_NOT_EXIST,
					"您輸入的手机号还未注册");
		}

		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(userDo, userDto);

		return userDto;
	}

	public UserDTO getUserByInvitationCode(String invitationCode)
			throws UserException {
		if (StringUtils.isBlank(invitationCode)) {
			return null;
		}

		// 规范化邀请码格式
		invitationCode = normalizeInvitationCode(invitationCode);
		// FIXME 根据邀请码的大写字符串来查询，以实现邀请码大小写不敏感
		UserDO userDo = userDAO.getByInvitationCode(invitationCode
				.toUpperCase());

		UserDTO userDto = null;
		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}
		return userDto;
	}

	public String generateInvitationCode(String bizCode) throws UserException {
		// 8位随机邀请码生成逻辑
		while (true) {
			StringBuilder sb = new StringBuilder();
			long timestamp = System.currentTimeMillis();
			for (int i = 0; i < 9; i++) {
				sb.append(characters.get((int) ((timestamp >> (5 * i)) & 0x1f)
						% characters.size()));
			}
			String invitationCode = sb.reverse().toString();
			if (getUserByInvitationCode(invitationCode) == null) {
				log.info("generate invitation code : {} success",
						invitationCode);
				return invitationCode;
			} else {
				log.warn("invitation code confilict : {}", invitationCode);
			}
		}
	}

	public int updateInvitationCode(Long userId, String invitationCode)
			throws UserException {
		if (null == invitationCode) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"invitationCode is null");
		}

		// 规范化邀请码格式
		invitationCode = normalizeInvitationCode(invitationCode);
		UserDTO userDto = null;
		if (null != userId) {
			userDto = getUserById(userId);
		}

		if (userDto == null) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userInfoDto is null");
		}

		int result = userDAO.updateInvitationCode(userId, invitationCode);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}
		return result;
	}

	private String normalizeInvitationCode(String invitationCode)
			throws UserException {
		if (StringUtils.isBlank(invitationCode)) {
			return null;
		}
		if (invitationCode.matches("^[0-9a-zA-Z]+$") == false) {
			throw new UserException(
					ResponseCode.B_INVITATION_CODE_FORMAT_ILLEGAL, "邀请码格式非法");
		}
		return invitationCode.toUpperCase();
	}

	@SuppressWarnings("rawtypes")
	public List<UserDTO> queryFromIdList(List idList) throws UserException {
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		List<UserDO> userDOs = userDAO.queryFromIdList(idList);
		for (UserDO userDO : userDOs) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userDO, userDTO);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	public List<UserDTO> queryByMobiles(List<String> mobiles, String bizCode)
			throws UserException {
		List<UserDO> userDOs = userDAO.queryByMobiles(mobiles, bizCode);
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		for (UserDO userDO : userDOs) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userDO, userDTO);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}
	
	/**
	 * 更新用户状态
	 * 
	 * @author csy
	 * @Date 2016-09-02
	 * 
	 */
	public void updateRoleType(Long userId, Long roleType, String bizCode) throws UserException {
		UserDTO userDto = getUserById(userId);
		
		if(null == userDto){
			throw new UserException(ResponseCode.P_PARAM_NULL, "userQto is null");
		}
		
		if(RoleType.HIKECONDITION.getValue().equals(roleType) 
				&& RoleType.HIKECONDITION.getValue().equals(userDto.getRoleMark())){
			//不做处理
		}else if(!RoleType.SELLER.getValue().equals(userDto.getRoleMark())){
			//判断用户状态(相同就不更新)		
			this.userDAO.updateRoleType(userId, roleType, bizCode);	
		}		
	}

	public void updateUserType(Long userId, Integer userType, String bizCode) {
		this.userDAO.updateUserType(userId, userType, bizCode);
	}

	public List<UserDTO> totalValidUsers(UserQTO userQTO) {

		if (null == userQTO.getOffset() || userQTO.getOffset() < 0) {
			userQTO.setOffset(0L);
		}

		// 没传入每页显示总数或者每页显示的数量大于500的话，默认每页显示20条
		if (userQTO.getCount() == null || userQTO.getCount() > 500) {
			userQTO.setCount(20);
		}

		List<UserDO> userDOs = this.userDAO.totalValidUsers(userQTO);
		List<UserDTO> userDtos = new ArrayList<UserDTO>();

		for (UserDO userDo1 : userDOs) {
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(userDo1, userDto);
			userDtos.add(userDto);
		}

		return userDtos;
	}

	public long getTotalValidCount(UserQTO userQTO) throws UserException {
		if (null == userQTO) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userQto is null");
		}
		long total = this.userDAO.getTotalValidCount(userQTO);
		return total;

	}

	/**
	 * 修改手机号校验
	 * 
	 * @author csy
	 * @throws UserException
	 * @Date 2016-05-13
	 * 
	 */
	@Override
	public void checkUserMobile(Long userId, String mobile, String verify_code,
			String bank_personal_id, String mFlag, String appKey)
			throws UserException {
		UserDTO userDto = getUserById(userId);
		if (userDto == null) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user is not exist");
		}

		if (null == mFlag) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "mFlag is null");
		}

		// ***********************原手机号验证码校验***********************//
		if ("0".equals(mFlag)) {
			if (null == mobile) {
				throw new UserException(ResponseCode.P_PARAM_NULL,
						"输入修改手机号不可为空");
			}

			if (null == verify_code) {
				throw new UserException(ResponseCode.P_PARAM_NULL, "验证码不可为空");
			}

			// 获取服务端验证码
			String serverSideVerifyCode = getVerifyCode(mobile, HandleTypeEnum.MODIFY_MOBILE_OLD.code);
			// 验证码是否合法
			UserUtil.checkVerifyCode(serverSideVerifyCode, verify_code);
			// 清除验证码
			cacheManager.remove(mobile);
		}

		// ***********************忘记手机号身份证校验***********************//
		if ("1".equals(mFlag)) {
			if (null == bank_personal_id) {
				throw new UserException(ResponseCode.P_PARAM_NULL, "身份证号不可为空");
			}

			// 校验身份证规则
			IdCardCheckUtil.IDCardValidate(bank_personal_id);

			// 根据用户id和身份证号查询
			Response<MopUserAuthonAppDTO> mopUserAuthonAppDTO = userAuthonClient
					.findWithdrawalsItem(userId, appKey);

			if (null == mopUserAuthonAppDTO
					|| mopUserAuthonAppDTO.isSuccess() == false
					|| null == mopUserAuthonAppDTO.getModule()) {
				throw new UserException(
						ResponseCode.HS_MOBILE_IDCODE_NOTEXISTS,
						"身份证验证错误，请重新输入");
			}

			if (!bank_personal_id.toUpperCase().equals(
					mopUserAuthonAppDTO.getModule().getAuthonPersonalid()
							.toUpperCase())) {
				throw new UserException(
						ResponseCode.HS_MOBILE_IDCODE_NOTEXISTS,
						"身份证验证错误，请重新输入");
			}
		}
	}

	/**
	 * 设置支付密码
	 * 
	 * @author csy
	 * @throws UserException
	 * @Date 2016-05-14
	 */
	@Override
	public int resetUserPayPwd(Long userId, String pay_pwd,
			String once_pay_pwd, String id_card, String appKey, String payFlag)
			throws UserException {
		if (null == payFlag.trim()) {
			throw new UserException(ResponseCode.B_SELECT_ERROR, "支付密码参数异常");
		}

		if (null == pay_pwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "支付密码不可为空");
		}

		if (null == once_pay_pwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "确认支付密码不可为空");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.HS_USER_INFO_NULL,
					"客户信息不存在或未登录");
		}

		if ("0".equals(payFlag)) {
			if (null == id_card) {
				throw new UserException(ResponseCode.HS_USER_IDCARD_NULL,
						"身份证号码不可为空");
			}
			// 身份证校验
			IdCardCheckUtil.IDCardValidate(id_card);

			// 根据用户id和身份证号查询
			Response<MopUserAuthonAppDTO> mopUserAuthonAppDTO = userAuthonClient
					.findWithdrawalsItem(userId, appKey);

			if (null == mopUserAuthonAppDTO
					|| mopUserAuthonAppDTO.isSuccess() == false
					|| null == mopUserAuthonAppDTO.getModule()) {
				throw new UserException(
						ResponseCode.HS_MOBILE_IDCODE_NOTEXISTS,
						"身份证验证错误，请重新输入");
			}

			if (!id_card.toUpperCase().equals(
					mopUserAuthonAppDTO.getModule().getAuthonPersonalid()
							.toUpperCase())) {
				throw new UserException(
						ResponseCode.HS_MOBILE_IDCODE_NOTEXISTS,
						"身份证验证错误，请重新输入");
			}
		}

		// 校验密码
		if (!pay_pwd.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_FORMAT,
					"密码请输入6位数字");
		}

		if (!once_pay_pwd.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_FORMAT,
					"确认密码请输入6位数字");
		}

		if (!once_pay_pwd.equals(pay_pwd)) {
			throw new UserException(ResponseCode.B_E_VERYFY_CODE_FORMAT,
					"两次输入支付密码不一样,请重新输入");
		}

		// 加密支付密码
		String md5PayPwd = DigestUtils.md5Hex(pay_pwd + PAY_PWD_ADD_SALT);

		// 更新支付密码
		int result = userDAO.resetUserPayPwd(userId, md5PayPwd);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 修改支付密码
	 * 
	 * @author csy
	 * @Date 2016-05-14
	 */
	@Override
	public int updateUserPayPwd(Long userId, String pay_pwd,
			String new_pay_pwd, String once_pay_pwd) throws UserException {
		if (null == pay_pwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "原支付密码不可为空");
		}

		if (null == once_pay_pwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "确认支付密码不可为空");
		}

		if (null == new_pay_pwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "新支付密码不可为空");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.HS_USER_INFO_NULL,
					"客户信息不存在或未登录");
		}

		// 校验密码
		if (!pay_pwd.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.HS_PAY_CODE_FORMAT,
					"原支付密码请输入6位数字");
		}

		if (!new_pay_pwd.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.HS_PAY_CODE_FORMAT,
					"新支付密码请输入6位数字");
		}

		if (!once_pay_pwd.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.HS_PAY_CODE_FORMAT,
					"确认支付密码请输入6位数字");
		}

		// 判断原支付密码是否存在
		if (null == userDto.getPayPassword()) {
			throw new UserException(ResponseCode.HS_USER_PAYPASSWORD_NULLSET,
					"未设置支付密码,不能进行修改操作");
		}

		if (!DigestUtils.md5Hex(pay_pwd + PAY_PWD_ADD_SALT).equals(
				userDto.getPayPassword())) {
			throw new UserException(ResponseCode.HS_PAY_CODE_FORMAT,
					"旧支付密码輸入错误,请重新輸入");
		}

		// 新支付密码不能与老支付密码相同
		if (pay_pwd.equals(new_pay_pwd)) {
			throw new UserException(ResponseCode.HS_USER_PAY_TWICE_ALILE,
					"新支付密码不能与原支付密码相同");
		}

		if (!once_pay_pwd.equals(new_pay_pwd)) {
			throw new UserException(ResponseCode.HS_USER_PAY_CHECK_EXP,
					"两次输入支付密码不一样,请重新输入");
		}

		// 加密支付密码
		String md5PayPwd = DigestUtils.md5Hex(new_pay_pwd + PAY_PWD_ADD_SALT);

		// 更新支付密码
		int result = userDAO.resetUserPayPwd(userId, md5PayPwd);

		if (result != 1) {
			throw new UserException(ResponseCode.HS_USER_PAY_PASSWORD);
		}

		return result;
	}

	/**
	 * 更新昵称
	 * 
	 * @author csy
	 * @Date 2016-05-15
	 * 
	 */
	@Override
	public int updateNickName(Long userId, String nick_name)
			throws UserException {
		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR, "客户信息不存在或未登录");
		}

		if (null == nick_name) {
			throw new UserException(ResponseCode.B_SELECT_ERROR, "输入修改昵称名不可为空");
		}

		// 校验昵称
		if (2 > nick_name.length() || 15 < nick_name.length()) {// 2-15个字
			throw new UserException(ResponseCode.HS_NICK_NAME_LIMIT,
					"昵称必须由2-15个汉字、英文或数字组成");
		}

		// 更新昵称
		int result = userDAO.updateNickName(userId, nick_name.trim());

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 根据用户昵称查询用户信息
	 * 
	 * @author csy
	 * @Date 2016-05-15
	 * @param nick_name
	 * @return
	 */
	public UserDTO getUserByNickName(String nick_name) throws UserException {
		if (null == nick_name) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "传入用户昵称为空");
		}

		UserDTO userDto = null;
		List<UserDO> userDoList = userDAO.getUserByNickName(nick_name);

		// 将do转换为dto
		if (null != userDoList && !userDoList.isEmpty()) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "昵称已经存在");
		}

		return userDto;
	}

	/**
	 * 
	 * 更新用户性别和生日
	 * 
	 * @param userId
	 * @param sex
	 * @param birthday
	 * @return
	 * @throws UserException
	 */
	@Override
	public int updateSexAndBirthday(Long userId, String sex, String birthday)
			throws UserException {
		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR, "客户信息不存在或未登录");
		}

		Map<Object, Object> map = new HashMap<Object, Object>();

		if (null != birthday && !"".equals(birthday)) {
			map.put("birthday", birthday);
		}

		if (null != sex && !"".equals(sex)) {
			map.put("sex", sex);
		}

		map.put("userId", userId);

		// 更新生日和性别
		int result = userDAO.updateSexAndBirthday(map);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 查询用户个人资料信息
	 * 
	 * @author csy
	 * @Date 2016-05-16
	 * @param userId
	 */
	@Override
	public UserDTO queryUserOneselfInfo(Long userId, String appKey) throws UserException {
		UserDTO userDto = getUserById(userId);

		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR, "用户信息不存在或未登录");
		}
		
		com.mockuai.distributioncenter.common.api.Response<SellerDTO> sellerDtoRe=null;
		
		//调用分销接口查询用户邀请码
		if (null != userDto.getRoleMark() && RoleType.SELLER.getValue().equals(userDto.getRoleMark())) {
			//只有嗨客存在粉丝
			UserQTO userQto = new UserQTO();
			userQto.setId(userId);
			
			List<UserDTO> userList = queryInviterListByUserId(userQto, appKey);	
			
			if(null != userList && !userList.isEmpty()){
				userDto.setFansCount((long) userList.size());//粉丝数量
			}
			
			sellerDtoRe = distributionClient.getSellerByUserId(userId, appKey);
		}	
		
		if(null != sellerDtoRe && sellerDtoRe.isSuccess()){			
			userDto.setInvitationCode(sellerDtoRe.getModule().getInviterCode());
			return userDto;		
		}
		
		return userDto;
	}

	/**
	 * 用户注册
	 * 
	 * @author csy
	 * @throws UserException
	 * @Date 2016-05-17
	 */
	@Override
	public UserDTO userRegister(Map<Object, Object> userDtoMap)	throws UserException {
		UserDTO userDto = new UserDTO();

		if (null == userDtoMap.get("mobile") || "".equals(userDtoMap.get("mobile"))) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入注册手机号");
		}

		if (null == userDtoMap.get("verifyCode") || "".equals(userDtoMap.get("verifyCode"))) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入注册验证码");
		}

		if (null == userDtoMap.get("password") || "".equals(userDtoMap.get("password"))) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入注册密码");
		}

		if (null == userDtoMap.get("registerFlag") || "".equals(userDtoMap.get("registerFlag"))) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入注册标识");
		}
		
		if(null == userDtoMap.get("appKey") || "".equals(userDtoMap.get("appKey"))){
			throw new UserException(ResponseCode.P_PARAM_NULL, "appKey数据为空");
		}

		// 加密后的密码
		String md5Pwd = DigestUtils.md5Hex(DigestUtils.md5Hex(userDtoMap.get("password").toString()));

		// 检验手机号格式
		UserUtil.checkMobile(userDtoMap.get("mobile").toString());
		if (null != getUserByMobile(userDtoMap.get("mobile").toString())) {
			log.error("mobile has been registered with mobile = {}", userDtoMap
					.get("mobile").toString());
			throw new UserException(ResponseCode.B_MOBILE_IS_EXIST, "手机号已经被注册");
		}

		// 检验验证码格式
		log.error("[{}] getVerifyCode:{},HandleTypeEnum:{}",userDtoMap.get("mobile").toString(),HandleTypeEnum.REGISTER.code);
		String serverSideVerifyCode = getVerifyCode(userDtoMap.get("mobile").toString(), HandleTypeEnum.REGISTER.code);
		log.error("[{}] serverSideVerifyCode:{}",serverSideVerifyCode);
		UserUtil.checkVerifyCode(serverSideVerifyCode, userDtoMap.get("verifyCode").toString());
		// 清除验证码
		cacheManager.remove(userDtoMap.get("mobile").toString());
		log.error("2");
		// 检验密码格式
		if (!userDtoMap.get("password").toString().matches("^[a-zA-Z0-9]+$")) {
			throw new UserException(ResponseCode.HS_PWD_MESSAGE_LIMIT,
					"密码只能设置英文或数字");
		}
		UserUtil.checkPwd(userDtoMap.get("password").toString(), 6, 15);

		// 检验设备类型是否存在
		if (null == userDtoMap.get("appType")) {
			log.error("app type is null when register user");
			throw new UserException(ResponseCode.B_APPTYPE_IS_NULL, "访问的设备类型为空");
		}

		// 昵称(非微信：hy+9位数字 微信：wx+9位数字)校验重复
		String codeStr = "hy";// 非微信：hy+9位数字
		String nick_name = nickNameRandom(codeStr, 9);
		log.error("9");

		// 邀请码校验:邀请码必须是嗨客的
		if (null != userDtoMap.get("invitationId") && !"".equals(userDtoMap.get("invitationId"))) {
			Long inviterId = getInviterId((Long) userDtoMap.get("invitationId"));

			if (null == inviterId) {
				throw new UserException(ResponseCode.P_PARAM_NULL, "请输入正确的邀请码");
			}
			
			userDto.setInviterId(inviterId);// 邀请人id
		}

		// 为dto赋值
		userDto.setName(userDtoMap.get("mobile").toString());// 用户账号
		userDto.setMobile(userDtoMap.get("mobile").toString());// 用户手机号
		userDto.setSourceType((Integer) userDtoMap.get("appType"));// 设备类型
		userDto.setType(UserType.NORMAL_USER.getValue());// 普遍用户类型
		userDto.setBizCode("hanshu");// 默认韩束
		userDto.setNickName(nick_name);// 用户昵称
		userDto.setPassword(md5Pwd);// 用户密码
		userDto.setRoleMark(RoleType.BUYER.getValue());// 默认买家
		userDto.setLastDistributorId(0L);// 默认无效值
		userDto.setStatus(0);// 默认非冻结

		//判断邀请人id是否是嗨客

		if(null != userDtoMap.get("inviterId")) {
			Long hkInviterId = (Long)userDtoMap.get("inviterId");
			UserDTO userDtIn = getUserById(hkInviterId);

			if(null != userDtIn && null != userDtIn.getRoleMark()
					&& RoleType.SELLER.getValue().equals(userDtIn.getRoleMark())){
				userDto.setInviterId(hkInviterId);//邀请人id
			}
		}

		// 将dto转换为do
		UserDO userDo = new UserDO();
		BeanUtils.copyProperties(userDto, userDo);

		// 保存注册信息
		log.error("3");
		Long userId = userDAO.addUser(userDo);
		log.error("3");

		// 查询注册用户信息
		log.error("4");
		userDto = getUserById(userId);
		log.error("4");
		
		//调取分销接口绑定用户关系
		if(null != userDto && null != userDto.getInviterId() && !"".equals(userDto.getInviterId())){
			 distributionClient.addRelationship(userDto.getId(), userDto.getInviterId(), (String) userDtoMap.get("appKey"));
		}	

		return userDto;
	}

	/**
	 * 获取邀请人的id(卖家id)
	 * 
	 * @return
	 * @throws UserException
	 */
	private Long getInviterId(Long inviterId) throws UserException {
		UserDTO userDto = null;

		if (null == inviterId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "请输入卖家正确的邀请码");
		}

		UserDO userDo = userDAO.getByInvitationId(inviterId);

		if (null != userDo) {
			userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
		}

		if (null == userDto) {
			log.error("inviterId :{}, no user own this inviterId", inviterId);
			throw new UserException(ResponseCode.B_INVITATION_CODE_INVALID,
					"请输入正确的邀请码");
		}

		// 校验邀请码是否是卖家的
		if (2 != userDto.getRoleMark()) {
			throw new UserException(ResponseCode.HS_VERIFY_CODE_SELL,
					"邀请码对应邀请人必须是卖家");
		}

		return userDto.getId();
	}

	/**
	 * 生成不重复的用户昵称
	 * 
	 * @author csy
	 * @Date 2016-05-18
	 * @return
	 * @throws UserException
	 */
	private String nickNameRandom(String codeStr, int nickSize)
			throws UserException {
		while (true) {
			String random = UserUtil.getFixLenthString(nickSize);
			String nick_name = codeStr + random;// 非微信：hy+9位数字

			if (getUserByNickName(nick_name) == null) {
				log.info("create nick_name : {} success", nick_name);
				return nick_name;
			} else {
				log.warn("create nick_name confilict : {}", nick_name);
			}
		}
	}

	/**
	 * 微信快捷登录
	 * 
	 * @author csy
	 * @Date 2016-05-18
	 * 
	 */
	@Override
	public UserDTO wechatLogin(String authCode, String appKey, int appType)
			throws UserException {
		return null;
	}

	/**
	 * 更新用户登录时缺失的 手机号、密码、邀请码
	 * 
	 * @author csy
	 * @Date 2016-05-20
	 * @throws UserException
	 */
	@Override
	public int updateUserLoginInfoMiss(Long userId, String mobile, String password, String verifyCode, Long invitationId) throws UserException {
		UserDTO userDto = getUserById(userId);

		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "更新用户登录信息时传参为空");
		}

		int result = 0;

		// **************************************更新手机号******************************************//
		if (null != mobile && !"".equals(mobile)) {
			//手机号是否注册
			if (null != getUserByMobile(mobile)) {
				log.error("mobile has been update loginUserInfoMiss with mobile = {}", mobile);
				throw new UserException(ResponseCode.B_MOBILE_IS_EXIST,	"手机号已经被注册");
			}

			result = userDAO.updateMobile(userId, mobile);

			if (result != 1) {
				throw new UserException(ResponseCode.B_UPDATE_ERROR, "设置用户缺失手机号失败");
			}
		}

		//**************************************更新密码******************************************//
		if (null != password && !"".equals(password)) {
			// md5加密密码
			String md5Pwd = DigestUtils.md5Hex(DigestUtils.md5Hex(password));

			result = userDAO.updatePwd(userId, md5Pwd);

			if (result != 1) {
				throw new UserException(ResponseCode.B_UPDATE_ERROR, "设置用户缺失密码失败");
			}
		}

		// **************************************更新邀请码******************************************//
		if (null != invitationId && !"".equals(invitationId)) {

			// 校验邀请码格式
			Long inviterId = getInviterId(invitationId);

			if (null == inviterId) {
				throw new UserException(ResponseCode.P_PARAM_NULL, "请输入正确的邀请码");
			}

			result = updateUserInvitationId(userId, inviterId);

			if (result != 1) {
				throw new UserException(ResponseCode.B_UPDATE_ERROR, "设置用户缺失邀请码失败");
			}
		}

		return result;
	}

	/**
	 * 更新用户邀请人id
	 * 
	 * @author csy
	 * @Date 2016-05-20
	 */
	@Override
	public int updateUserInvitationId(Long userId, Long invitationId)
			throws UserException {
		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user id is null when update InvitationId");
		}

		if (null == invitationId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"invitationId is null when update InvitationId");
		}

		int result = userDAO.updateUserInvitationId(userId, invitationId);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 查询用户账户安全信息
	 * 
	 * @author csy
	 * @Date 2016-05-25
	 * @param userId
	 */
	@Override
	public Map<Object, Object> queryUserSafeInfo(Long userId, String appKey) throws UserException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		try {
			UserDTO userDto = getUserById(userId);

			if (null == userDto) {
				throw new UserException(ResponseCode.B_SELECT_ERROR, "用户信息不存在或未登录");
			}
			// 查询用户实名信息
			com.mockuai.virtualwealthcenter.common.api.Response<MopUserAuthonAppDTO> mopUserAuthonAppDTO = userAuthonClient
					.findWithdrawalsItem(userId, appKey);
			log.info("authonMsg:{}",mopUserAuthonAppDTO.getModule() );
			//authonStatus:0 待审核  1、已实名 2 审核失败 -1未实名
			if (null != mopUserAuthonAppDTO	&& null != mopUserAuthonAppDTO.getModule() 
					&& null != mopUserAuthonAppDTO.getModule().getAuthonStatus()) {
				map.put("authId", mopUserAuthonAppDTO.getModule().getId());// 实名id
				map.put("authName", mopUserAuthonAppDTO.getModule().getAuthonRealname());// 实名姓名
				map.put("authIdCard", getIdCardStr(mopUserAuthonAppDTO.getModule().getAuthonPersonalid()));// 实名身份证号
				map.put("authonStatus", mopUserAuthonAppDTO.getModule().getAuthonStatus());// 实名状态
				String pictureFront = mopUserAuthonAppDTO.getModule().getPictureFront();//身份证正面
				if(pictureFront!=null&&pictureFront.contains("@")){
					pictureFront =pictureFront.substring(0,pictureFront.lastIndexOf("@"));
				}
				map.put("pictureFront",pictureFront);
				String pictureBack =mopUserAuthonAppDTO.getModule().getPictureBack();//身份证背面
				if(pictureBack!=null&&pictureBack.contains("@")){
					pictureBack = pictureBack.substring(0,pictureBack.lastIndexOf("@"));
				}
				map.put("pictureBack",pictureBack);


			}

			map.put("mobile", userDto.getMobile());// 手机号
			map.put("password", userDto.getPassword());// 登录密码
			map.put("payPassword", userDto.getPayPassword());// 支付密码
			map.put("userId", userDto.getId());// 用户id
			map.put("roleMark", userDto.getRoleMark());// 角色类型
			
			if(null == map.get("authonStatus") || "".equals(map.get("authonStatus"))){
				map.put("authonStatus", -1);// 实名状态
			}
		} catch (Exception e) {
			log.error("查询实名问题错误日志:", e);
			throw new UserException(ResponseCode.SYS_E_SERVICE_EXCEPTION);
		}
		
		return map;
	}

	/**
	 * 获取用户指定格式用户身份证信息
	 * 
	 * @param idCard
	 * @return
	 */
	private String getIdCardStr(String idCard) {
		StringBuffer idCardStr = new StringBuffer();

		if (null == idCard) {
			return null;
		}

		if (15 == idCard.length()) {
			idCardStr.append(idCard.charAt(0));
			idCardStr.append("*************");
			idCardStr.append(idCard.charAt(idCard.length() - 1));
		}

		if (18 == idCard.length()) {
			idCardStr.append(idCard.charAt(0));
			idCardStr.append("****************");
			idCardStr.append(idCard.charAt(idCard.length() - 1));
		}

		return idCardStr.toString();
	}

	/**
	 * 更新用户最后一次访问分销商id
	 * 
	 * @author csy
	 * @Date 2016-06-02
	 */
	@Override
	public int UpdateLastDistributorId(Long userId, Long sellerId)
			throws UserException {
		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userId is null when update user last seller id");
		}

		if (null == sellerId) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"sellerId is null when update user last seller id");
		}

		int result = userDAO.updateLastDistributorId(userId, sellerId);

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	/**
	 * 更新用户邀请人id
	 * 
	 * @author csy
	 * @Date 2016-06-03
	 * 
	 */
	@Override
	public int updateInviterId(Long userId, Long inviterId)
			throws UserException {
		// 校验指定的user信息
		UserDTO userResult = getUserById(userId);

		if (null == userResult) {
			throw new UserException(ResponseCode.B_ACCOUNT_NOT_EXIST,"该用户不存在");
		}

		// 如果待更新的邀请人id与原邀请人id相等，则直接返回true
		if (null != userResult.getInviterId()
				&& userResult.getInviterId() == inviterId) {
			throw new UserException(ResponseCode.REQUEST_SUCCESS,"邀请人id与原邀请人id相同");
		}

		// 校验指定的inviter信息
		UserDTO inviter = getUserById(inviterId);
		if (null == inviter) {
			log.error("inviter is null when update inviter id");
			throw new UserException(ResponseCode.HS_USER_INVITER_UPDATE_ERROR,
					"更新用户邀请人id不可为空");
		}

		int result = userDAO.updateInviterId(userId, inviterId);

		if (result != 1) {
			throw new UserException(ResponseCode.HS_USER_INVITER_UPDATE_ERROR,
					"更新用户邀请人失败");
		}

		return result;
	}

	/**
	 * hsq
	 * 给指定inviterId 添加粉丝
	 * @param userId
	 * @return
	 * @throws UserException
	 */
	@Override
	public int addFansToInviterId(Long userId, Long inviterId) throws UserException {
		// 校验指定的user信息
		UserDTO userResult = getUserById(userId);

		if (null == userResult) {
			throw new UserException(ResponseCode.B_ACCOUNT_NOT_EXIST,"该用户不存在");
		}

		// 如果待更新的邀请人id与原邀请人id相等，则直接返回true
		if (null != userResult.getInviterId()
				&& userResult.getInviterId() .equals(inviterId)) {
			throw new UserException(ResponseCode.REQUEST_SUCCESS,"邀请人id与原邀请人id相同");
		}

		// 校验指定的inviter信息
		UserDTO inviter = getUserById(inviterId);
		if (null == inviter) {
			log.error("inviter is null when UserManager  addFansToInviterId");
			throw new UserException(ResponseCode.HS_USER_INVITER_UPDATE_ERROR,
					"用户邀请人id不存在");
		}

		int result = userDAO.addFansToInviterId(userId, inviterId);

		return result;
	}

	@Override
	public Long validateInviterId(Long userId, Long inviterId) throws UserException {
		// 校验指定的user信息
		UserDTO userResult = getUserById(userId);

		//粉丝id非法
		if (null == userResult) {
//			throw new UserException(ResponseCode.B_ACCOUNT_NOT_EXIST, BindFansAndInviterActionEnum.FANS_USER_NOT_EXIST.getValue().toString());
			return BindFansAndInviterActionEnum.FANS_USER_NOT_EXIST.getValue();
		}

		// 该粉丝用户已经绑定过分享人
		if (null != userResult.getInviterId()) {
//			throw new UserException(ResponseCode.REQUEST_SUCCESS,BindFansAndInviterActionEnum.FANS_ALREADY_BIND.getValue().toString());
			return BindFansAndInviterActionEnum.FANS_ALREADY_BIND.getValue();
		}

		return BindFansAndInviterActionEnum.READY_TO_BIND.getValue();
	}

	/**
	 * 校验旧支付密码是否正确
	 * 
	 * @author csy
	 * @Date 2016-06-14
	 */
	@Override
	public void checkUserOldPayPwd(Long userId, String pay_pwd)
			throws UserException {
		if (null == pay_pwd) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "校验原支付密码不可为空");
		}

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "用户id不可为空");
		}

		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.HS_USER_INFO_NULL, "客户信息不存在");
		}

		// 校验旧支付密码
		if (!pay_pwd.matches("(\\d){6}")) {
			throw new UserException(ResponseCode.HS_PAY_CODE_FORMAT,
					"支付密码请输入6位数字");
		}

		if (!DigestUtils.md5Hex(pay_pwd + PAY_PWD_ADD_SALT).equals(
				userDto.getPayPassword())) {
			throw new UserException(ResponseCode.HS_PAY_CODE_FORMAT,
					"支付密码輸入错误,请重新輸入");
		}
	}
	
	/**
	 * 查询用户通讯录所对应的卖家邀请码
	 * 		（卖家为有嗨云有效邀请码的卖家;买家为有推荐人邀请码嗨云的买家。）
	 * 	 
	 * @author csy
	 * @Date 2016-07-11	
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> queryUserMobileDirectory(List<String> userMobileJsonList, String appKey) throws UserException {
		Map<Object, Object> userMobileListMap = new HashMap<Object, Object>();
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<Map<Object, Object>> userMobileMapList = new ArrayList<Map<Object, Object>>();
		
		if(null == userMobileJsonList || userMobileJsonList.isEmpty()){
			throw new UserException(ResponseCode.B_USER_DIRECTORY_ERROR,"您的手机通讯录中没有找到嗨云用户");
		}
		
		for(String userJosn:userMobileJsonList){
			UserDTO userDto = JsonUtil.parseJson(userJosn, UserDTO.class);
			
			if(null == userDto){
				continue;
			}
			
			userDtoList.add(userDto);
		}
		
		if(null == userDtoList || userDtoList.isEmpty()){
			throw new UserException(ResponseCode.B_USER_DIRECTORY_ERROR,"您的手机通讯录中没有找到嗨云用户");
		}
		
		for(UserDTO userDto:userDtoList){
			//手机号不可为空并符合格式
			if(null == userDto || null == userDto.getMobile() || !userDto.getMobile().matches("1(\\d){10}")){
				continue;
			}
			
			SellerDTO sellerDTO = null;
			Map<Object, Object> sellerMap = new HashMap<Object, Object>();
			
			//根据手机号查询用户信息
			UserDTO userMe = getUserByMobile(userDto.getMobile());				
			
			//三种查询情况
			if(2 == userMe.getRoleMark()){//如果是卖家,返回卖家邀请码
				com.mockuai.distributioncenter.common.api.Response<SellerDTO> sellerDtoRe = distributionClient.getSellerByUserId(userMe.getId(), appKey);
				
				if(null == sellerDtoRe || sellerDtoRe.isSuccess() == false || null == sellerDtoRe.getModule()){
					continue;
				}
				
				sellerDTO = sellerDtoRe.getModule();
			}else if(1 == userMe.getRoleMark()){//如果是买家,返回买家对应卖家邀请码
				com.mockuai.distributioncenter.common.api.Response<SellerDTO> sellerDtoRe = distributionClient.getSellerByUserId(userMe.getInviterId(), appKey);
				
				if(null == sellerDtoRe || sellerDtoRe.isSuccess() == false || null == sellerDtoRe.getModule()){
					continue;
				}
				
				sellerDTO = sellerDtoRe.getModule();
			}else if(null != userMe.getLastDistributorId() && 0 != userMe.getLastDistributorId()){//没有邀请码,返回最后一次访问记录对应邀请码
				List<Long> sellerIdList = new ArrayList<Long>();				
				sellerIdList.add(userMe.getLastDistributorId());
				
				SellerQTO sellerQTO = new SellerQTO();
				sellerQTO.setIds(sellerIdList);
				
				com.mockuai.distributioncenter.common.api.Response<List<SellerDTO>> sellerDtoReList = distributionClient.querySeller(sellerQTO, appKey);
				
				if(null == sellerDtoReList || sellerDtoReList.isSuccess() == false || null == sellerDtoReList.getModule()){
					continue;			
				}
				
				List<SellerDTO> sellerDtoList = sellerDtoReList.getModule();
				
				if(null == sellerDtoList || sellerDtoList.isEmpty()){
					continue;
				}
				
				sellerDTO = sellerDtoList.get(0);
			}
			
			if(null != sellerDTO){
				sellerMap.put("name", userDto.getName());
				sellerMap.put("mobile", userDto.getMobile());
				sellerMap.put("invitation_code", sellerDTO.getInviterCode());					
				
				userMobileMapList.add(sellerMap);
			}
		}
		
		if(null == userMobileMapList || userMobileMapList.isEmpty()){
			throw new UserException(ResponseCode.B_USER_DIRECTORY_ERROR,"您的手机通讯录中没有找到嗨云用户");
		}
		
		return (Map<Object, Object>) userMobileListMap.put("userMobileMapList", userMobileMapList);
	}
	
	/**
	 * 查询用户对应买家邀请人信息
	 * 
	 * @author csy
	 * @Date 2016-07-23
	 */
	@Override
	public List<UserDTO> queryInviterListByUserId(UserQTO userQTO, String appKey) throws UserException {
		if(null == userQTO){
			throw new UserException(ResponseCode.P_PARAM_NULL,"查询用户userId为空");
		}
		
		//根据用户id查询邀请人
		List<UserDO> userDoList = userDAO.queryInviterListByUserId(userQTO);	
		
		if(null == userDoList || userDoList.isEmpty()){
			return null;
		}
		
		//do转换为Dto
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		
		//排除已经是卖家的邀请人信息
		for(UserDO userDo:userDoList){
			//1是买家2是买家(开放式平台下面的都是粉丝)
			/*if(1 != userDo.getRoleMark()){
				continue;
			}*/
			userDo.setPassword(null);
			userDo.setPayPassword(null);			
			
			UserDTO userDto = new UserDTO();
			BeanUtils.copyProperties(userDo, userDto);
			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	/**
	 * 判断此用户是否存在成为嗨客的条件
	 * 
	 * @author csy
	 * @Date 2016-08-31
	 */
	@Override
	public UserDTO queryHiKeCondition(Long userId, String appKey) throws UserException {
		if(null == userId){
			throw new UserException(ResponseCode.P_PARAM_NULL,"用户userId传参数据为空");
		}
				
		//判断用户表中此用户状态
		UserDTO userDto = getUserById(userId);	
		
		if(null == userDto){
			throw new UserException(ResponseCode.P_PARAM_NULL,"传入userId不存在");
		}
		
		//格式化密码
		userDto.setPassword(null);
		userDto.setPayPassword(null);
		
		//如果用户已经有资格或者已经成为嗨客则直接返回状态数据
		if(RoleType.HIKECONDITION.getValue().equals(userDto.getRoleMark()) 
				|| RoleType.SELLER.getValue().equals(userDto.getRoleMark())){			
			return userDto;
		}		
		
		//如果如果用户状态不等于2、5(嗨客、有资格)则查询是否下过订单
		OrderQTO orderQTO = new OrderQTO();
		orderQTO.setUserId(userId);
		
		com.mockuai.tradecenter.common.api.Response<Integer> orderCount = orderClient.queryUserOrderCount(orderQTO, appKey);
		
		//如果用户已经下过订单则拥有资格
		if(null != orderCount && orderCount.isSuccess() != false && orderCount.getModule() > 0){
			userDto.setRoleMark(RoleType.HIKECONDITION.getValue());
			return userDto;
		} else {//以上条件不符合则返回普通用户类型
			userDto.setRoleMark(RoleType.BUYER.getValue());
			return userDto;			
		}
	}
	
	/**
	 * 设置用户微信号和qq号码
	 * 
	 * @author csy
	 * @Date 2016-09-01
	 * 
	 */
	@Override
	public int updateWechatAndQqCode(Long userId, String wechat, String qqCode)	throws UserException {
		UserDTO userDto = getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR, "客户信息不存在或未登录");
		}

		Map<Object, Object> map = new HashMap<Object, Object>();

		if (null != wechat && !"".equals(wechat)) {
			map.put("wechat", wechat);
		}

		if (null != qqCode && !"".equals(qqCode)) {
			map.put("qqCode", qqCode);
		}

		map.put("userId", userId);

		// 更新生日和性别
		int result = userDAO.updateWxAndQq(map);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}
}

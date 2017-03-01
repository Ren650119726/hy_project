package com.mockuai.usercenter.core.manager.impl;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.UserAuthType;
import com.mockuai.usercenter.common.qto.UserAuthInfoQTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserAuthInfoDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.core.dao.UserAuthInfoDAO;
import com.mockuai.usercenter.core.domain.UserAuthInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserAuthInfoManager;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.util.IdCardCheckUtil;

import java.util.List;

@Service
public class UserAuthInfoManagerImpl implements UserAuthInfoManager {
	private static final Logger log = LoggerFactory.getLogger(UserAuthInfoManagerImpl.class);

	@Resource
	private UserManager userManager;
	@Resource
	private UserAuthInfoDAO userAuthInfoDAO;

	private static final Long DEFAULT_OFFSET = 0L;
	private static final Integer DEFAULT_COUNT = 100;

	@Override
	public Long addUserAuthInfo(UserAuthInfoDO authInfoDO) throws UserException {

		if (null == authInfoDO) {
			log.error("authInfoDO is null");
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"authInfoDO is null");
		}

		//TODO 入参检查
		// 判断名字是否为空
		if (null == authInfoDO.getRealName()) {
			log.error("real name is null");
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"real name is null");
		}

		// 校验身份证信息
		if (authInfoDO.getIdcardNo() == null) {
			log.error("id card no is null");
			throw new UserException(ResponseCode.P_PARAM_NULL, "idcardno is null");
		}
		IdCardCheckUtil.IDCardValidate(authInfoDO.getIdcardNo());

		// 如果是卖家或企业认证，同一个身份证只能认证一次，如果是买家认证，同一个身份证可以认证多次
		if (authInfoDO.getType().intValue() != UserAuthType.BUYER_AUTH.getValue()) {
			//判断身份证号是否已经被卖家认证过
			UserAuthInfoDO userAuthInfoDO = userAuthInfoDAO.getAuthInfoByIdCardNo(
					authInfoDO.getIdcardNo(),
					UserAuthType.SELLER_AUTH.getValue(),
					authInfoDO.getBizCode());
			if (userAuthInfoDO == null) {
				// 判断身份证是否已经被企业认证过
				userAuthInfoDO = userAuthInfoDAO.getAuthInfoByIdCardNo(
						authInfoDO.getIdcardNo(),
						UserAuthType.ENTERPRISE_AUTH.getValue(),
						authInfoDO.getBizCode());
			}
			if (userAuthInfoDO != null) {
				throw new UserException(ResponseCode.B_ID_CARD_NO_HAS_BEEN_AUTHED, "该身份证号已经被认证过了");
			}
		}

		// 每个用户只能存在一条指定类型的认证记录,如果认证信息已存在，则返回错误信息
		UserAuthInfoDO getResult = this.getAuthInfoByUserId(authInfoDO.getUserId(),
				authInfoDO.getType(), authInfoDO.getBizCode());

		if (getResult == null) {
			// 认证信息中，一个用户可以同时具有买家认证和卖家/企业认证，但是一个用户不能同时持有卖家和企业认证
			if (authInfoDO.getType().intValue() == UserAuthType.ENTERPRISE_AUTH.getValue()) {
				// 如果认证类型是企业认证，则检查卖家认证是否存在
				getResult = getAuthInfoByUserId(authInfoDO.getUserId(), UserAuthType.SELLER_AUTH.getValue(), authInfoDO.getBizCode());
			} else if (authInfoDO.getType().intValue() == UserAuthType.SELLER_AUTH.getValue()) {
				// 如果认证类型是卖家认证，则检查企业认证是否存在
				getResult = getAuthInfoByUserId(authInfoDO.getUserId(), UserAuthType.ENTERPRISE_AUTH.getValue(), authInfoDO.getBizCode());
			}
			// 如果认证信息存在，提示错误
			if (getResult != null) {
				log.error("current user's auth info is exists with user id = " + authInfoDO.getUserId() + " and auth type = " + authInfoDO.getType());
				throw new UserException(ResponseCode.B_USER_AUTH_INFO_IS_EXIST,
						"authInfoDO is exist already");
			}
		}
		else if (getResult.getType().intValue() != UserAuthType.BUYER_AUTH.getValue()) {
			log.error("current user's auth info is exists with user id = " + authInfoDO.getUserId() + " and auth type = " + authInfoDO.getType());
			throw new UserException(ResponseCode.B_USER_AUTH_INFO_IS_EXIST,
					"authInfoDO is exist already");
		}


		//TODO 下面id_card_img的校验以及默认值逻辑需要重构，针对洋东西应用，先放开idCardImg校验
		if(authInfoDO.getIdcardFrontImg() == null){
			authInfoDO.setIdcardFrontImg("");
		}

		if(authInfoDO.getIdcardReverseImg() == null){
			authInfoDO.setIdcardReverseImg("");
		}


		try{
			Long id = userAuthInfoDAO.addAuthInfo(authInfoDO);
			return id;
		}catch(Exception e){
			log.error("error to add user auth info, userId={}", authInfoDO.getUserId(), e);
			throw new UserException(ResponseCode.B_ADD_ERROR);
		}
	}

	@Override
	public UserAuthInfoDO getAuthInfoById(Long id) throws UserException {

		UserAuthInfoDTO userAuthInfoDto = null;

		if (null == id) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
		}

		if (id < 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,
					"id must greater than zero");
		}

		try{
			UserAuthInfoDO userAuthInfoDO = userAuthInfoDAO.getAuthInfoById(id);
			return userAuthInfoDO;
		}catch(Exception e){
			log.error("error to get user auth info, id={}", id, e);
			throw new UserException(ResponseCode.B_SELECT_ERROR);
		}
	}

	public List<UserAuthInfoDO> queryAuthInfo(UserAuthInfoQTO userAuthInfoQTO) throws UserException{
		if(userAuthInfoQTO == null){
			throw new UserException(ResponseCode.P_PARAM_NULL, "userAuthInfoQTO is null");
		}

		if(StringUtils.isBlank(userAuthInfoQTO.getBizCode())){
			log.error("bizCode is null");
			throw new UserException(ResponseCode.P_PARAM_NULL, "bizCode is null");
		}

		if(userAuthInfoQTO.getOffset() == null){
			userAuthInfoQTO.setOffset(DEFAULT_OFFSET);
		}

		if(userAuthInfoQTO.getCount() == null){
			userAuthInfoQTO.setCount(DEFAULT_COUNT);
		}

		try{
			List<UserAuthInfoDO> userAuthInfoDOs = userAuthInfoDAO.queryUserAuthInfo(userAuthInfoQTO);
			return userAuthInfoDOs;
		}catch(Exception e){
			log.error("error to query user auth info", e);
			throw new UserException(ResponseCode.B_SELECT_ERROR);
		}
	}

	@Override
	public Long getTotalCount(UserAuthInfoQTO userAuthInfoQTO) throws UserException {
		Long totalCount = userAuthInfoDAO.queryUserAuthInfoTotalCount(userAuthInfoQTO);
		return totalCount;
	}

	@Override
	public UserAuthInfoDO getAuthInfoByUserId(Long userId, Integer authType, String bizCode)
			throws UserException {
		// TODO Auto-generated method stub

		UserDTO userDto = userManager.getUserById(userId);

		if (null == userDto) {
			log.error("user is not exists with user id = " + userId);
			throw new UserException(ResponseCode.B_ADD_ERROR,
					"user is not exist");
		}
		// 根据用户id，获取用户实名认证的消息
		try{
			UserAuthInfoDO userAuthInfoDO = userAuthInfoDAO.getAuthInfoByUserId(userId, authType, bizCode);
			return userAuthInfoDO;
		}catch(Exception e){
			log.error("error to get user auth info, userId={}, authType={}", userId, authType, e);
			throw new UserException(ResponseCode.B_SELECT_ERROR);
		}
	}

	@Override
	public int passUserAuth(Long userAuthId, Long userId, String remark) throws UserException {

		UserDTO userDto = userManager.getUserById(userId);
		if (null == userDto) {
			log.error("user is not exists with user id " + userId);
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		int result = 0;

		try{
			result = userAuthInfoDAO.passUserAuth(userAuthId, userId, remark);
		}catch(Exception e){
			log.error("error to update auth info, userId={}, remark={}", userId, remark, e);
			throw new UserException(ResponseCode.B_UPDATE_ERROR);
		}

		if (result != 1) {
			log.error("result != 1 , means update error");
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}
		return result;
	}

	@Override
	public int rejectUserAuth(Long userAuthId, Long userId, String remark) throws UserException {

		UserDTO userDto = userManager.getUserById(userId);
		if (null == userDto) {
			log.error("user is not exists with user id = " + userId);
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		int result = 0;
		try{
			result = userAuthInfoDAO.rejectUserAuth(userAuthId, userId, remark);
		}catch(Exception e){
			log.error("error to update user auth info, userId={}", userId, e);
		}
		if (result != 1) {
			log.error("update error when reject user auth with user id" + userId);
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}
		return result;
	}

	@Override
	public int updateUserAuthInfo(UserAuthInfoDO authInfoDO) throws UserException {
		// TODO Auto-generated method stub

		if (null == authInfoDO) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"authInfoDto is null");
		}

		if(null==authInfoDO.getId() && null==authInfoDO.getUserId()){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"id is null");
		}

		//如果身份证号不为空，则需要验证身份证号的合法性
		if(authInfoDO.getIdcardNo() != null){
			IdCardCheckUtil.IDCardValidate(authInfoDO.getIdcardNo());
		}

		UserAuthInfoDO userAuthInfoDO = null;

		if(null != authInfoDO.getId()){
			userAuthInfoDO = userAuthInfoDAO.getAuthInfoById(authInfoDO.getId());
		}else if(null != authInfoDO.getUserId()){
			userAuthInfoDO = userAuthInfoDAO.getAuthInfoByUserId(authInfoDO.getUserId(),
					authInfoDO.getType(), authInfoDO.getBizCode());
		}

		if(userAuthInfoDO == null){
			throw new UserException(ResponseCode.B_USER_AUTH_INFO_NOT_EXIST,
					"该用户未实名认证过");
		}else{
			//需要修改身份证号，则要判断新身份证号是否被别人认证了
			if(authInfoDO.getIdcardNo()!=null
					&& authInfoDO.getIdcardNo().equals(userAuthInfoDO.getIdcardNo())==false){
				// 如果是卖家或企业认证，同一个身份证只能认证一次，如果是买家认证，同一个身份证可以认证多次
				if (userAuthInfoDO.getType().intValue() != UserAuthType.BUYER_AUTH.getValue()) {
					//判断身份证号是否已经被卖家认证过
					UserAuthInfoDO infoDO = userAuthInfoDAO.getAuthInfoByIdCardNo(
							authInfoDO.getIdcardNo(),
							UserAuthType.SELLER_AUTH.getValue(),
							authInfoDO.getBizCode());
					if (infoDO == null) {
						// 判断身份证是否已经被企业认证过
						infoDO = userAuthInfoDAO.getAuthInfoByIdCardNo(
								authInfoDO.getIdcardNo(),
								UserAuthType.ENTERPRISE_AUTH.getValue(),
								authInfoDO.getBizCode());
					}
					if (infoDO != null) {
						log.error("Identifier ID [" + infoDO.getIdcardNo() + "] card has been auth");
						throw new UserException(ResponseCode.B_ID_CARD_NO_HAS_BEEN_AUTHED, "该身份证号已经被认证过了");
					}
				}
			}
		}

		// 判断名字是否为空
		if (StringUtils.isBlank(authInfoDO.getRealName())) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"real name is null");
		}

		int result = 0;
		try{
			authInfoDO.setId(userAuthInfoDO.getId());
			authInfoDO.setUserId(userAuthInfoDO.getUserId());
			authInfoDO.setBizCode(userAuthInfoDO.getBizCode());
			result = userAuthInfoDAO.updateUserAuthInfo(authInfoDO);
		}catch(Exception e){
			log.error("error to update user auth info, userId={}", authInfoDO.getUserId(), e);
			throw new UserException(ResponseCode.B_UPDATE_ERROR);
		}

		if (result != 1) {
			throw new UserException(ResponseCode.B_UPDATE_ERROR, "update error");
		}

		return result;
	}

	@Override
	public int deleteUserAuth(Long userId) throws UserException {
		UserDTO userDto = userManager.getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user is not exist");
		}

		try{
			int result = userAuthInfoDAO.deleteUserAuth(userId);
			return result;
		}catch(Exception e){
			log.error("error to update user auth info, userId={}", userId, e);
			throw new UserException(ResponseCode.B_DELETE_ERROR);
		}
	}

	@Override
	public int restoreUserAuth(Long userId) throws UserException {

		UserDTO userDto = userManager.getUserById(userId);
		if (null == userDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"user is not exist");
		}

		try{
			int result = userAuthInfoDAO.restoreUserAuth(userId);
			return result;
		}catch(Exception e){
			log.error("error to update user auth info, userId={}", userId, e);
			throw new UserException(ResponseCode.B_UPDATE_ERROR);
		}
	}

	@Override
	public Boolean passWaitForGuarantee(UserAuthInfoDTO userAuthInfoDTO) throws UserException {
		UserAuthInfoDO userAuthInfoDO = new UserAuthInfoDO();
		BeanUtils.copyProperties(userAuthInfoDTO,userAuthInfoDO);
		//TODO:userAuthInfoDAO的updateUserAuthInfo方法中,更改status = 1,有些不妥
		Integer result =  this.userAuthInfoDAO.passWaitForGuarantee(userAuthInfoDO);
		if(1 == result){
			return true;
		}else{
			throw new UserException(ResponseCode.B_UPDATE_ERROR,"update userauthinfo status to wait guarantee error");

		}
	}

	public static void main(String[] args) throws Exception{
		IdCardCheckUtil.IDCardValidate("653024198901130023");
	}
}

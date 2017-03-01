package com.mockuai.usercenter.core.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.usercenter.common.constant.MessageType;
import com.mockuai.usercenter.core.dao.GlobalMessageDAO;
import com.mockuai.usercenter.core.dao.UserDAO;
import com.mockuai.usercenter.core.domain.GlobalMessageDO;
import com.mockuai.usercenter.core.domain.UserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.GlobalMessageDTO;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.GlobalMessageQTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.core.dao.UserMessageDAO;
import com.mockuai.usercenter.core.domain.UserMessageDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserManager;
import com.mockuai.usercenter.core.manager.UserMessageManager;

@Service
public class UserMessageManagerImpl implements UserMessageManager {

	@Resource
	private UserMessageDAO userMessageDAO;

	@Resource
	private UserManager userManager;

	@Resource
	private GlobalMessageDAO globalMessageDAO;

	@Resource
	private UserDAO userDAO;

	public void addUserMessage(UserMessageDTO userMessageDto) throws UserException {

		if (null == userMessageDto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,"userMessageDto is null");
		}

		if(userMessageDto.getType()==1){
			//判断用户是否存在
			UserDTO userDto = userManager.getUserById(userMessageDto.getReceiverId());
			if (null == userDto) {
				throw new UserException(ResponseCode.B_SELECT_ERROR, "user is not exist");
			}
		}else{

		}


		if (null == userMessageDto.getTitle()) {
			throw new UserException(ResponseCode.P_PARAM_NULL,"title is null");
		}

		if (null == userMessageDto.getContent()) {
			throw new UserException(ResponseCode.P_PARAM_NULL,"content is null");
		}

		UserMessageDO userMessageDo = new UserMessageDO();
		BeanUtils.copyProperties(userMessageDto, userMessageDo);

		Long id = userMessageDAO.addUserMessage(userMessageDo);
		//userMessageDto = getUserById(id,userMessageDto.getReceiverId());

		//return userMessageDto;
	}

	public int updateUserMessageStatus(Long id,Long userId,int status)
			throws UserException {

		if (null == id) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
		}

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		UserMessageDTO userMessageDto = getUserById(id, userId);
		if (userMessageDto == null) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"id is not exist");
		}

		int result = userMessageDAO.updateUserMessageStatus(id, userId, status);

		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "update error");
		}

		return result;
	}

	
	public UserMessageDTO getUserById(Long id,Long userId) throws UserException {

		if (null == id) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "id is null");
		}

		if (null == userId) {
			throw new UserException(ResponseCode.P_PARAM_NULL, "userId is null");
		}

		if (id <= 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,"id must greater than 0");
		}

		if (userId <= 0) {
			throw new UserException(ResponseCode.P_PARAM_ERROR,"userId must greater than 0");
		}

		UserMessageDO userMessageDo = userMessageDAO.getUserById(id,userId);
		UserMessageDTO userMessageDto = null;

		if (null != userMessageDo) {
			userMessageDto = new UserMessageDTO();
			BeanUtils.copyProperties(userMessageDo, userMessageDto);
		}
		return userMessageDto;
	}

	
	public int deleteUserMessage(Long id,Long userId) throws UserException {
		// TODO Auto-generated method stub

		UserMessageDTO userMessageDto = getUserById(id,userId);
		if (null == userMessageDto) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"user not exist");
		}
		// 消息状态为1表示已经删除
		if (userMessageDto.getDeleteMark() == 1) {
			throw new UserException(ResponseCode.B_SELECT_ERROR,
					"id not in recycle");
		}

		int result = userMessageDAO.deleteUserMessage(id, userId);
		if (result != 1) {
			throw new UserException(ResponseCode.B_DELETE_ERROR, "delete error");
		}

		return result;
	}

	
	public List<UserMessageDTO> queryUserMessage(UserMessageQTO userMessageQto) throws UserException {

		if (null == userMessageQto) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userMessageQto is null");
		}
		userMessageQto .setCount(userMessageQto.getPageSize());
		userMessageQto.setOffset(userMessageQto.getStartRow());
		if(userMessageQto.getOffset()==null || userMessageQto.getOffset()<0){
			userMessageQto.setOffset(0L);
		}

		if(userMessageQto.getCount()==null || userMessageQto.getCount()>100){
			userMessageQto.setCount(100);
		}
		List<UserMessageDO> userDos=new ArrayList<UserMessageDO>();
       //按消息类型查询
		if(userMessageQto.getReceiverId() != null &&
				(userMessageQto.getType()==null||userMessageQto.getType() == MessageType.P2P_MESSAGE.getValue())){
			//先查询个人消息中从全场拷贝的最大globalId
			UserMessageDO userMessageDO=userMessageDAO.queryMaxGlobalIdMessage(userMessageQto);
			UserMessageQTO userMessageQTO=new UserMessageQTO();
			//指定用户注册时间
			userMessageQTO.setUserRegDate(userMessageQto.getUserRegDate());
			if(userMessageDO!=null){
				userMessageQTO.setGlobalId(userMessageDO.getGlobalId());
			} else {
				userMessageQTO.setGlobalId(0L);
			}
			userMessageQTO.setBizCode(userMessageQto.getBizCode());
			List<UserMessageDO> newGlobalMessageList=userMessageDAO.queryNewGlobalMessage(userMessageQTO);
			for(UserMessageDO newGlobalMessage:newGlobalMessageList){
				UserMessageDO userMessageDO1=new UserMessageDO();
				BeanUtils.copyProperties(newGlobalMessage, userMessageDO1);
				userMessageDO1.setReceiverId(userMessageQto.getReceiverId());
				userMessageDO1.setGlobalId(newGlobalMessage.getId());
				userMessageDO1.setType(MessageType.P2P_MESSAGE.getValue());
				userMessageDO1.setSendType(MessageType.P2P_MESSAGE.getValue());
				userMessageDO1.setGlobalId(newGlobalMessage.getId());
				userMessageDO1.setBizCode(newGlobalMessage.getBizCode());
				userMessageDAO.addUserMessage(userMessageDO1);
			}
			userDos = userMessageDAO.queryUserMessage(userMessageQto);

		}
		else if (userMessageQto.getType() == MessageType.P2P_MESSAGE.getValue() && userMessageQto.getReceiverId() == null) {
			// 如果接受者ID为空，则表示是管理员在查看P2P消息
			userMessageQto.setGlobalId(0L);
			userDos = userMessageDAO.queryUserMessage(userMessageQto);
		} else {
			// 查询全局消息
			userDos = userMessageDAO.queryUserMessage(userMessageQto);
		}

		List<UserMessageDTO> userDtos = new ArrayList<UserMessageDTO>();


		for (UserMessageDO userDo1 : userDos) {
			UserMessageDTO userMessageDto = new UserMessageDTO();
			BeanUtils.copyProperties(userDo1, userMessageDto);
			//get name by id
			UserDO userDO= userDAO.getUserById(userMessageDto.getSenderId());
			if(userDO!=null&&userDO.getName()!=null){
				userMessageDto.setSenderName(userDO.getName());
			}else{
			//	userMessageDto.setSenderName("name is null");
			}
			if(userMessageDto.getType()==null||userMessageDto.getType()==1){
				UserDO userDO_receiver= userDAO.getUserById(userMessageDto.getReceiverId());
				if(userDO_receiver!=null&&userDO_receiver.getName()!=null){
					userMessageDto.setReceiverName(userDO_receiver.getName());
				}else{
				//	userMessageDto.setReceiverName("name is null");
				}
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date=formatter.format(userMessageDto.getGmtCreated());
			userMessageDto.setTime(date);
			userDtos.add(userMessageDto);
		}

		return userDtos;

	}

	
	public Long getTotalCount(UserMessageQTO userMessageQTO) throws UserException {


		// TODO Auto-generated method stub
		if (null == userMessageQTO) {
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"userMessageQto is null");
		}
		Long totalCount =  userMessageDAO.getTotalCount(userMessageQTO);

		return totalCount;
	}
	
	public Long addGlobalMessage(GlobalMessageDTO globalMessage)throws UserException{
		if(globalMessage == null){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"globalMessageDTO is null");
		}
		GlobalMessageDO globalMessageDO = new GlobalMessageDO();
		BeanUtils.copyProperties(globalMessage, globalMessageDO);
		return this.globalMessageDAO.addGlobalMessage(globalMessageDO);
	}
	
	public List<GlobalMessageDTO> queryGlobalMessage(GlobalMessageQTO globalMessageQTO)throws UserException{
		if(globalMessageQTO ==null){
			throw new UserException(ResponseCode.P_PARAM_NULL,
					"globalMessageDTO is null");
		}
		List<GlobalMessageDO> list = this.globalMessageDAO.queryGlobalMessage(globalMessageQTO);
		List<GlobalMessageDTO> result =new ArrayList<GlobalMessageDTO>();
		if(list != null){
			for(GlobalMessageDO item :  list){
				GlobalMessageDTO dto = new GlobalMessageDTO();
				dto.setContent(item.getContent());
				dto.setId(item.getId());
				dto.setSendCondition(item.getSendCondition());
				dto.setSenderId(item.getSenderId());
				dto.setStatus(item.getStatus());
				dto.setTitle(item.getTitle());
				dto.setType(item.getType());
				result.add(dto);
			}
		}
		return result;
	}
	
	public Long getGlobalMessageTotalCount(GlobalMessageQTO globalMessageQTO)throws UserException{
		Long result = this.globalMessageDAO.getTotalCount(globalMessageQTO);
		return result;
	}

}
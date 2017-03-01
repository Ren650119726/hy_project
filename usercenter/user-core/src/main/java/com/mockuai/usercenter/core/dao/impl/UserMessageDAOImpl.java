package com.mockuai.usercenter.core.dao.impl;

import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.core.dao.UserMessageDAO;
import com.mockuai.usercenter.core.domain.UserMessageDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class  UserMessageDAOImpl extends SqlMapClientDaoSupport implements UserMessageDAO {
	@Override
	public Long addUserMessage(UserMessageDO userMessageDo) {
		// TODO Auto-generated method stub
		Long messsageId = (Long) getSqlMapClientTemplate().insert("user_message.insert", userMessageDo);
		return messsageId;
	}

	@Override
	public int updateUserMessageStatus(Long id, Long userId,int status) {
		// TODO Auto-generated method stub
		UserMessageDO key = new UserMessageDO();
		key.setId(id);
		key.setReceiverId(userId);
		key.setStatus(status);
		int result = getSqlMapClientTemplate().update("user_message.updateUserMessageStatus", key);
		return result;
	}

	@Override
	public int deleteUserMessage(Long id,Long userId) {

		UserMessageDO key = new UserMessageDO();
		key.setId(id);
		key.setReceiverId(userId);
		int result = getSqlMapClientTemplate().update("user_message.deleteUserMessage", key);
		return result;
	}

	@Override
	public UserMessageDO getUserById(Long id,Long userId) {
		// TODO Auto-generated method stub
		UserMessageDO key = new UserMessageDO();
		key.setId(id);
		key.setReceiverId(userId);
		UserMessageDO userMessageDo = (UserMessageDO) getSqlMapClientTemplate().queryForObject(
				"user_message.selectByUserId", key);
		return userMessageDo;
	}

	@Override
	public List<UserMessageDO> queryUserMessage(UserMessageQTO userMessageQto) {

		List<UserMessageDO> messages = getSqlMapClientTemplate().queryForList(
						"user_message.queryUserMessage", userMessageQto);
		return messages;

	}
	@Override
	public List<UserMessageDO> queryNewGlobalMessage(UserMessageQTO userMessageQto){
		List<UserMessageDO> messages = getSqlMapClientTemplate().queryForList(
				"user_message.queryGlobalMessage", userMessageQto);
		return messages;
	}

	@Override
	public UserMessageDO queryMaxGlobalIdMessage(UserMessageQTO userMessageQto){
		UserMessageDO messages =  (UserMessageDO)getSqlMapClientTemplate().queryForObject(
				"user_message.queryMaxGlobalIdMessage", userMessageQto);
		return messages;
	}

	@Override
	public Long getTotalCount(UserMessageQTO userMessageQto) {

			Long totalCount = (Long) getSqlMapClientTemplate().queryForObject(
					"user_message.totalCount", userMessageQto);
			return totalCount;

	}
}

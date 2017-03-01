package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.core.domain.UserMessageDO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMessageDAO {

	/**
	 * 添加消息
	 */
	Long addUserMessage(UserMessageDO userMessageDo);

	/**
	 * 修改消息状态
	 */
	int updateUserMessageStatus(Long id ,Long userId,int status);

	/**
	 * 删除消息
	 */
	int deleteUserMessage(Long id,Long userId);

	/**
	 * 根据ID和userID获取信息
	 */
	UserMessageDO getUserById(Long id,Long userId);
	/**
	 * 用户消息列表
	 */
	List<UserMessageDO> queryUserMessage(UserMessageQTO userMessageQto);


	/**
	 * 获取表中记录的总数
	 */
	Long getTotalCount(UserMessageQTO userMessageQto);

	/**
	 * 查询新增的全场消息
	 */
	List<UserMessageDO> queryNewGlobalMessage(UserMessageQTO userMessageQto);

	/**
	 * 查询globalId最大的个人消息
	 */
	UserMessageDO queryMaxGlobalIdMessage(UserMessageQTO userMessageQto);

}

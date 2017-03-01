package com.mockuai.usercenter.core.manager;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.usercenter.common.dto.GlobalMessageDTO;
import com.mockuai.usercenter.common.dto.UserMessageDTO;
import com.mockuai.usercenter.common.qto.GlobalMessageQTO;
import com.mockuai.usercenter.common.qto.UserMessageQTO;
import com.mockuai.usercenter.core.exception.UserException;

@Service
public interface UserMessageManager {
	/**
	 * 添加消息
	 */
	void addUserMessage(UserMessageDTO userMessageDto) throws UserException;

	/**
	 * 删除消息
	 */
	int deleteUserMessage(Long id,Long userId) throws UserException;

	/**
	 * 修改消息状态
	 */
	int updateUserMessageStatus(Long id,Long userId,int status) throws UserException;

	/**
	 * 根据ID和userid查找对应消息
	 */
	UserMessageDTO getUserById(Long id,Long userId) throws UserException;

	/**
	 * 获取消息列表
	 */
	List<UserMessageDTO> queryUserMessage(UserMessageQTO userMessageQto) throws UserException;

	/**
	 * 查询指定用户的消息总数
	 * */
	Long getTotalCount(UserMessageQTO userMessageQto) throws UserException;
	
	/**
	 * 写入全局的消息
	 * @param globalMessage
	 * @return
	 * @throws UserException
	 */
	Long addGlobalMessage(GlobalMessageDTO globalMessage)throws UserException;
	
	/**
	 * 查询全局的消息
	 * @param globalMessageQTO
	 * @return
	 * @throws UserException
	 */
	List<GlobalMessageDTO> queryGlobalMessage(GlobalMessageQTO globalMessageQTO)throws UserException;
	
	/**
	 * 查询全局消息的总条数
	 * @param globalMessageQTO
	 * @return
	 * @throws UserException
	 */
	Long getGlobalMessageTotalCount(GlobalMessageQTO globalMessageQTO)throws UserException;
	
}

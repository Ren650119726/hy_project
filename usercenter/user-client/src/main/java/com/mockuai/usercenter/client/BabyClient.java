//package com.mockuai.usercenter.client;
//
//import java.util.List;
//
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.dto.UserBabyInfoDTO;
//
//public interface BabyClient {
//
//	/**
//	 * 添加baby信息
//	 * */
//	Response<UserBabyInfoDTO> addBabyInfo(UserBabyInfoDTO userBabyInfoDto);
//
//	/**
//	 * 删除baby信息
//	 * */
//	Response<Boolean> deleteBabyInfo(Long userId, Long babyId);
//
//	/**
//	 * 查询指定用户的baby信息
//	 * */
//	Response<List> queryBabyInfo(Long userId);
//
//	/**
//	 * 修改指定baby的信息
//	 */
//	Response<Boolean> updateBabyInfo(UserBabyInfoDTO babyInfoDTO);
//}

//package com.mockuai.usercenter.client;
//
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
//import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
//
//import java.util.List;
//
///**
// * Created by yeliming on 16/1/23.
// * 用户保证金
// */
//public interface UserGuaranteeClient {
//    /**
//     * 添加用户保证金
//     * @param userGuaranteeDTO
//     * @param appKey
//     * @return
//     */
//    Response<UserGuaranteeDTO> addUserGuarantee(UserGuaranteeDTO userGuaranteeDTO, String appKey);
//
//    /**
//     * 修改用户保证金
//     * @param userGuaranteeDTO
//     * @param appKey
//     * @return
//     */
//    Response<Boolean> updateUserGuarantee(UserGuaranteeDTO userGuaranteeDTO, String appKey);
//
//    /**
//     * 查询用户保证金
//     * @param userGuaranteeQTO
//     * @param appKey
//     * @return
//     */
//    Response<List<UserGuaranteeDTO>> queryUserGuaratee(UserGuaranteeQTO userGuaranteeQTO, String appKey);
//
//    /**
//     * 删除用户保证金
//     * @param id
//     * @param appKey
//     * @return
//     */
//    Response<Boolean> deleteUserGuarantee(Long id, String appKey);
//
//    /**
//     * 通过id,获取用户保证金记录
//     * @param id
//     * @param appKey
//     * @return
//     */
//    Response<UserGuaranteeDTO> getUserGuaranteeDTOById(Long id,String appKey);
//
//
//}

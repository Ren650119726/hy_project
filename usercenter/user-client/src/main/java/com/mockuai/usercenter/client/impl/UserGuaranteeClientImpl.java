//package com.mockuai.usercenter.client.impl;
//
//import com.mockuai.usercenter.client.UserGuaranteeClient;
//import com.mockuai.usercenter.common.action.ActionEnum;
//import com.mockuai.usercenter.common.api.BaseRequest;
//import com.mockuai.usercenter.common.api.Request;
//import com.mockuai.usercenter.common.api.Response;
//import com.mockuai.usercenter.common.api.UserDispatchService;
//import com.mockuai.usercenter.common.dto.UserGuaranteeDTO;
//import com.mockuai.usercenter.common.qto.UserGuaranteeQTO;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * Created by yeliming on 16/1/23.
// */
//public class UserGuaranteeClientImpl implements UserGuaranteeClient {
//
//    @Resource
//    private UserDispatchService userDispatchService;
//
//    public Response<UserGuaranteeDTO> addUserGuarantee(UserGuaranteeDTO userGuaranteeDTO, String appKey) {
//        Request request = new BaseRequest();
//        request.setParam("userGuaranteeDTO",userGuaranteeDTO);
//        request.setParam("appKey",appKey);
//        request.setCommand(ActionEnum.ADD_USER_GUARANTEE.getActionName());
//        Response<UserGuaranteeDTO> response = this.userDispatchService.execute(request);
//        return response;
//    }
//
//    public Response<Boolean> updateUserGuarantee(UserGuaranteeDTO userGuaranteeDTO, String appKey) {
//        Request request = new BaseRequest();
//        request.setParam("userGuaranteeDTO",userGuaranteeDTO);
//        request.setParam("appKey",appKey);
//        request.setCommand(ActionEnum.UPDATE_USER_GUARANTEE.getActionName());
//        Response<Boolean> response = this.userDispatchService.execute(request);
//        return response;
//    }
//
//    public Response<List<UserGuaranteeDTO>> queryUserGuaratee(UserGuaranteeQTO userGuaranteeQTO, String appKey) {
//        Request request = new BaseRequest();
//        request.setParam("userGuaranteeQTO",userGuaranteeQTO);
//        request.setParam("appKey",appKey);
//        request.setCommand(ActionEnum.QUERY_USER_GUARANTEE.getActionName());
//        Response<List<UserGuaranteeDTO>> response = this.userDispatchService.execute(request);
//        return response;
//    }
//
//    public Response<Boolean> deleteUserGuarantee(Long id, String appKey) {
//        Request request = new BaseRequest();
//        request.setParam("id",id);
//        request.setParam("appKey",appKey);
//        request.setCommand(ActionEnum.DELETE_USER_GUARANTEE.getActionName());
//        Response<Boolean> response = this.userDispatchService.execute(request);
//        return response;
//    }
//
//    public Response<UserGuaranteeDTO> getUserGuaranteeDTOById(Long id, String appKey) {
//        Request request = new BaseRequest();
//        request.setParam("id",id);
//        request.setParam("appKey",appKey);
//        request.setCommand(ActionEnum.GET_USER_GUARANTEE_BY_ID.getActionName());
//        Response<UserGuaranteeDTO> response = this.userDispatchService.execute(request);
//        return response;
//    }
//}

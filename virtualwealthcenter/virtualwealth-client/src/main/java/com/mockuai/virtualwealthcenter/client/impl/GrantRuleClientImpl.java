package com.mockuai.virtualwealthcenter.client.impl;

import com.mockuai.virtualwealthcenter.client.GrantRuleClient;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantRuleQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by edgar.zr on 12/12/15.
 */
public class GrantRuleClientImpl implements GrantRuleClient {

    @Resource
    VirtualWealthService virtualWealthService;

    public Response<Long> addVirtualWealthGrantRule(GrantRuleDTO grantRuleDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_GRANT_RULE.getActionName());
        baseRequest.setParam("grantRuleDTO", grantRuleDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Long> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> deleteVirtualWealthGrantRule(Long grantRuleId, Long creatorId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.DELETE_GRANT_RULE.getActionName());
        baseRequest.setParam("grantRuleId", grantRuleId);
        baseRequest.setParam("creatorId", creatorId);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> updateVirtualWealthGrantRule(GrantRuleDTO grantRuleDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UPDATE_GRANT_RULE.getActionName());
        baseRequest.setParam("grantRuleDTO", grantRuleDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<GrantRuleDTO> getVirtualWealthGrantRule(Long grantRuleId, Long creatorId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_GRANT_RULE.getActionName());
        baseRequest.setParam("grantRuleId", grantRuleId);
        baseRequest.setParam("creatorId", creatorId);
        baseRequest.setParam("appKey", appKey);
        Response<GrantRuleDTO> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<List<GrantRuleDTO>> queryVirtualWealthGrantRule(Integer wealthType, Integer sourceType, Long creatorId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        GrantRuleQTO grantRuleQTO = new GrantRuleQTO();
        grantRuleQTO.setWealthType(wealthType);
        grantRuleQTO.setSourceType(sourceType);
        grantRuleQTO.setCreatorId(creatorId);
        baseRequest.setParam("grantRuleQTO", grantRuleQTO);
        baseRequest.setParam("appKey", appKey);
        baseRequest.setCommand(ActionEnum.QUERY_GRANT_RULE.getActionName());
        Response<List<GrantRuleDTO>> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> switchVirtualWealthGrantRule(Long grantRuleId, Long creatorId, Integer status, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.SWITCH_GRANT_RULE.getActionName());
        baseRequest.setParam("grantRuleId", grantRuleId);
        baseRequest.setParam("creatorId", creatorId);
        baseRequest.setParam("status", status);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Long> addGrantRule(GrantRuleDTO grantRuleDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_GRANT_RULE.getActionName());
        baseRequest.setParam("grantRuleDTO", grantRuleDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Long> response = virtualWealthService.execute(baseRequest);
        return response;

    }

    public Response<GrantRuleDTO> getGrantRuleByOwner(Long ownerId, Integer sourceType, Integer wealthType, Long creatorId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_GRANT_RULE_BY_OWNER.getActionName());
        baseRequest.setParam("ownerId", ownerId);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("sourceType", sourceType);
        baseRequest.setParam("creatorId", creatorId);
        baseRequest.setParam("appKey", appKey);
        Response<GrantRuleDTO> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> grantVirtualWealthWithGrantRule(GrantedWealthDTO grantedWealthDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GRANT_VIRTUAL_WEALTH_WITH_GRANT_RULE.getActionName());
        baseRequest.setParam("grantedWealthDTO", grantedWealthDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Void> response = virtualWealthService.execute(baseRequest);
        return response;
    }
}
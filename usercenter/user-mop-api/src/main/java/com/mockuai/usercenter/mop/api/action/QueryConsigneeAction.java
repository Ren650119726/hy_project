package com.mockuai.usercenter.mop.api.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.usercenter.common.action.ActionEnum;
import com.mockuai.usercenter.mop.api.util.MarkUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;
import com.mockuai.usercenter.common.api.BaseRequest;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserConsigneeDTO;
import com.mockuai.usercenter.mop.api.domain.MopConsigneeDTO;
import com.mockuai.usercenter.mop.api.util.MopApiUtil;

public class QueryConsigneeAction extends BaseAction{

	public MopResponse execute(Request request) {
        Long userId = (Long)request.getAttribute("user_id");
		String appKey = (String)request.getParam("app_key");
		com.mockuai.usercenter.common.api.Request userReq = new BaseRequest();
		userReq.setParam("userId", userId);
		userReq.setParam("appKey", appKey);
		userReq.setCommand(ActionEnum.QUERY_CONSIGNEE.getActionName());
		Response<List> userResp = (Response<List>)this.getUserDispatchService().execute(userReq);
		
        if(userResp.getCode() != ResponseCode.REQUEST_SUCCESS.getValue()){
            return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
        }else{
        	Map<String,Object> data = new HashMap<String, Object>();
        	List<MopConsigneeDTO> list = new ArrayList<MopConsigneeDTO>();
        	if(!CollectionUtils.isEmpty(userResp.getModule())){
        		for (int i = 0; i < userResp.getModule().size(); i++) {
        			UserConsigneeDTO consignee  = (UserConsigneeDTO)userResp.getModule().get(i);
					//身份证掩码处理
					if(!StringUtils.isBlank(consignee.getIdCardNo())){
						consignee.setIdCardNo(MarkUtil.markFormat(consignee.getIdCardNo(),"*",1));
					}
        			list.add(MopApiUtil.genMopConsignee(consignee));
				}
        	}
            data.put("consignee_list", list);
            data.put("total_count", userResp.getTotalCount());
            return new MopResponse(data);
        }
	}

	public String getName() {
		return "/user/consignee/list";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.AUTH_LOGIN;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
	
}

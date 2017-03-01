package com.mockuai.itemcenter.mop.api.action.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.mop.api.action.BaseAction;
import com.mockuai.itemcenter.mop.api.domain.MopItemDTO;
import com.mockuai.itemcenter.mop.api.util.MopApiUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;

/**
 *   手机端热词展示 
 * @author huangsiqian
 * @version 2016年9月19日 下午7:41:42 
 */
public class HotNameListAllAction extends BaseAction {

	public MopResponse execute(Request request) {
		String appKey = (String)request.getParam("app_key");
		BaseRequest req = new BaseRequest();
		req.setCommand(ActionEnum.HOTNAME_LIST.getActionName());
		req.setParam("appKey", appKey);
		Response<List<HotNameDTO>> resp = getItemService().execute(req);

		if (resp.isSuccess()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hotName",resp.getModule());
			return new MopResponse(map) ;
        }else {
        	return new MopResponse("未查到热搜词");
        }
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "/item/hotsearch/list";
	}

	public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }

}

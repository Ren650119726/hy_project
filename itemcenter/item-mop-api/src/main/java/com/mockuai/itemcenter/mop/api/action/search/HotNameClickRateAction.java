package com.mockuai.itemcenter.mop.api.action.search;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;

/**
 *   手机端点击量统计
 * @author huangsiqian
 * @version 2016年9月19日 下午7:44:23 
 */
public class HotNameClickRateAction extends BaseAction{
	static Logger log = LoggerFactory.getLogger(HotNameClickRateAction.class);

	public String getName() {
		// TODO Auto-generated method stub
		return "/item/hotsearch/clickrate";
	}

	public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.NO_AUTH;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.NO_LIMIT;
    }

	public MopResponse execute(
			com.mockuai.mop.common.service.action.Request request) {
		String appKey = (String) request.getParam("app_key");
		String hotName = (String) request.getParam("hot_name");
		String hotId = (String) request.getParam("hot_id");
		if("".equals(hotId)||null==hotId){
			return new MopResponse(MopRespCode.P_E_PARAM_ISNULL,"热搜词hot_id不能为空");
		}
		
		BaseRequest hotNameReq = new BaseRequest();
			hotNameReq.setCommand(ActionEnum.CLICKRATEHOTNAME.getActionName());
			hotNameReq.setParam("appKey", appKey);
			hotNameReq.setParam("id",hotId);
			Response<Boolean> bl =  this.getItemService().execute(hotNameReq);	
			if(bl.isSuccess()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("flag",bl.getModule());
			   return new MopResponse(map);
			}else{
			   return new MopResponse(MopRespCode.S_E_SERVICE_ERROR);
			}
			 
//		com.mockuai.itemcenter.common.api.Request req = new BaseRequest();
//        ItemList itemList = new ItemList();
//        req.setParam("offset","0");
//        req.setParam("count","20");
//        req.setParam("appKey", appKey);
//        req.setParam("keyword",hotName);
		
	}

}

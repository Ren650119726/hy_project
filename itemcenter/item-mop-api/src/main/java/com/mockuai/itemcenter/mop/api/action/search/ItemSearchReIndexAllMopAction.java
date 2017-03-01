package com.mockuai.itemcenter.mop.api.action.search;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.itemcenter.common.api.BaseRequest;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.mop.api.action.BaseAction;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.mop.common.service.action.Request;



/**
 * 閲嶅缓鎵�鏈夊晢鍝佺储寮�
 * @author luoyi
 *
 */
public class ItemSearchReIndexAllMopAction extends BaseAction {

	
	static Logger log = LoggerFactory.getLogger(ItemSearchReIndexAllMopAction.class);
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MopResponse execute(Request request) {
		//	bizCode涓虹┖鍒欒〃绀哄拷鐣ュ晢鍝乥izCode灞炴�э紝姝ゆ椂灏嗛噸寤烘墍鏈夊晢鍝佺储寮�
		String bizCode = (String) request.getParam("biz_code");
		String appKey = (String) request.getParam("appKey");
		//	鍗栧id锛屽搧鐗宨d鑻ヤ负绌轰篃涓嶅弬涓庤繃婊ゆ潯浠�
		String sellerId = (String) request.getParam("seller_id");
		String brandId = (String) request.getParam("brand_id");
		
        com.mockuai.itemcenter.common.api.Request req = new BaseRequest();
        req.setCommand(ActionEnum.ITEM_SEARCH_REINDEX_ALL.getActionName());
        req.setParam("appKey", appKey);
        req.setParam("bizCode", bizCode);
        //	涓氬姟鐩稿叧杩囨护鏉′欢
        req.setParam("sellerId", sellerId);
        req.setParam("brandId", brandId);

        Response<String> resp = getItemService().execute(req);

        if (resp.getCode() == ResponseCode.SUCCESS.getCode()) {
        	return new MopResponse<String>("reindex all success.");
        }else {
        	return new MopResponse<String>("reindex all error.");
        }
	}

	public String getName() {
		return "/item/search/reindex_all";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.NO_AUTH;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.NO_LIMIT;
	}

}

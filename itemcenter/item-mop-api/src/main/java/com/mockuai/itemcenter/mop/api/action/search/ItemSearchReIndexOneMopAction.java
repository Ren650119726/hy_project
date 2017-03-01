package com.mockuai.itemcenter.mop.api.action.search;


import org.apache.commons.lang3.StringUtils;
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
 * 重建单个商品索引
 * @author luoyi
 *
 */
public class ItemSearchReIndexOneMopAction extends BaseAction {

	
	static Logger log = LoggerFactory.getLogger(ItemSearchReIndexOneMopAction.class);
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MopResponse execute(Request request) {
		String itemId = (String) request.getParam("item_id");
        String appKey = (String) request.getParam("app_key");
        //	若biz_code为空，则会忽略商品的biz_code属性
        String bizCode = (String) request.getParam("biz_code");
        
        //	itemId不能为空
        if (StringUtils.isBlank(itemId)) {return new MopResponse<String>("商品id不能为空 in MopAction.");}
        
        com.mockuai.itemcenter.common.api.Request req = new BaseRequest();
        req.setCommand(ActionEnum.ITEM_SEARCH_REINDEX_ONE.getActionName());
        req.setParam("itemId", itemId);
        req.setParam("appKey", appKey);
        req.setParam("bizCode", bizCode);

        Response<String> resp = getItemService().execute(req);

        if (resp.getCode() == ResponseCode.SUCCESS.getCode()) {
        	return new MopResponse<String>("reindex success.");
        }else {
        	return new MopResponse<String>("reindex error.");
        }
	}

	public String getName() {
		return "/item/search/reindex_one";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.NO_AUTH;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.NO_LIMIT;
	}

}

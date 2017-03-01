package com.mockuai.seckillcenter.mop.api.action;

import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import com.mockuai.seckillcenter.common.api.BaseRequest;
import com.mockuai.seckillcenter.common.api.Request;
import com.mockuai.seckillcenter.common.api.Response;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.mop.api.domain.ItemUidDTO;
import com.mockuai.seckillcenter.mop.api.util.MopApiUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀商品详情
 * <p/>
 * Created by edgar.zr on 6/12/2016.
 */
public class DetailOfSeckill extends BaseAction {

	public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {

		String itemUidStr = (String) request.getParam("item_uid");
		String distributorIdStr = (String) request.getParam("distributor_id");
		Long userId = (Long) request.getAttribute("user_id");
		String appKey = (String) request.getParam("app_key");

		Long itemId = null;
		Long sellerId = null;
		Long distributorId = null;
		try {
			ItemUidDTO itemUidDTO = MopApiUtil.parseItemUid(itemUidStr);
			if (itemUidDTO != null) {
				itemId = itemUidDTO.getItemId();
				sellerId = itemUidDTO.getSellerId();
			}
			distributorId = Long.valueOf(distributorIdStr);
		} catch (Exception e) {
			return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID, "item_uid/distributor_id format is invalid");
		}

		Request marketReq = new BaseRequest();
		marketReq.setCommand(ActionEnum.DETAIL_OF_SECKILL_BY_ITEM.getActionName());
		marketReq.setParam("itemId", itemId);
		marketReq.setParam("distributorId", distributorId);
		marketReq.setParam("sellerId", sellerId);
		marketReq.setParam("userId", userId);
		marketReq.setParam("appKey", appKey);
		Response<Map<String, Object>> marketResp = getSeckillService().execute(marketReq);
		MopResponse response;
		if (marketResp.isSuccess()) {
			Map<String, Object> result = marketResp.getModule();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("item", result.get("item"));
			data.put("time", result.get("currentTime"));
			response = new MopResponse(data);
		} else {
			response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
		}
		return response;
	}

	public String getName() {
		return "/seckill/detail/get";
	}

	public ActionAuthLevel getAuthLevel() {
		return ActionAuthLevel.NO_AUTH;
	}

	public HttpMethodLimit getMethodLimit() {
		return HttpMethodLimit.ONLY_GET;
	}
}
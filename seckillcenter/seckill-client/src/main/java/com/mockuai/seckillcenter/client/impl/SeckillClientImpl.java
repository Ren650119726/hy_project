package com.mockuai.seckillcenter.client.impl;

import com.mockuai.seckillcenter.client.SeckillClient;
import com.mockuai.seckillcenter.common.api.BaseRequest;
import com.mockuai.seckillcenter.common.api.Response;
import com.mockuai.seckillcenter.common.api.SeckillService;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.domain.dto.OrderHistoryDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillForMopDTO;
import com.mockuai.seckillcenter.common.domain.qto.OrderHistoryQTO;
import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by edgar.zr on 12/2/15.
 */
public class SeckillClientImpl implements SeckillClient {

	@Resource
	private SeckillService seckillService;

	//添加秒杀
	public Response<Long> addSeckill(SeckillDTO seckillDTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.ADD_SECKILL.getActionName());
		baseRequest.setParam("seckillDTO", seckillDTO);
		baseRequest.setParam("appKey", appKey);
		Response<Long> response = (Response<Long>) seckillService.execute(baseRequest);
		return response;
	}

	//秒杀生效
	public Response<Void> invalidSeckill(Long seckillId, Long creatorId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.INVALIDATE_SECKILL.getActionName());
		baseRequest.setParam("seckillId", seckillId);
		baseRequest.setParam("sellerId", creatorId);
		baseRequest.setParam("appKey", appKey);
		Response<Void> response = (Response<Void>) seckillService.execute(baseRequest);
		return response;
	}

	//获取秒杀详情
	public Response<SeckillDTO> getSeckill(Long seckillId, Long creatorId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GET_SECKILL.getActionName());
		baseRequest.setParam("seckillId", seckillId);
		baseRequest.setParam("sellerId", creatorId);
		baseRequest.setParam("appKey", appKey);
		Response<SeckillDTO> response = (Response<SeckillDTO>) seckillService.execute(baseRequest);
		return response;
	}

	//秒杀列表
	public Response<List<SeckillDTO>> querySeckill(SeckillQTO seckillQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_SECKILL.getActionName());
		baseRequest.setParam("seckillQTO", seckillQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<SeckillDTO>> response = (Response<List<SeckillDTO>>) seckillService.execute(baseRequest);
		return response;
	}

	//秒杀结算
	public Response<SeckillDTO> validateForSettlement(Long skuId, Long userId, Long sellerId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.VALIDATE_FOR_SETTLEMENT.getActionName());
		baseRequest.setParam("skuId", skuId);
		baseRequest.setParam("sellerId", sellerId);
		baseRequest.setParam("userId", userId);
		baseRequest.setParam("appKey", appKey);
		Response<SeckillDTO> response = (Response<SeckillDTO>) seckillService.execute(baseRequest);
		return response;
	}

//    public Response<SeckillForMopDTO> querySeckillByItem(Long skuId, Long userId, Long sellerId, String appKey) {
//        BaseRequest baseRequest = new BaseRequest();
//        baseRequest.setCommand(ActionEnum.DETAIL_OF_SECKILL_BY_ITEM.getActionName());
//        baseRequest.setParam("skuId", skuId);
//        baseRequest.setParam("sellerId", sellerId);
//        baseRequest.setParam("userId", userId);
//        baseRequest.setParam("appKey", appKey);
//        Response<SeckillForMopDTO> response = (Response<SeckillForMopDTO>) seckillService.execute(baseRequest);
//        return response;
//    }



	public Response<List<SeckillForMopDTO>> querySeckillByItemBatch(SeckillQTO seckillQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.GET_SECKILL_BY_ITEM_BATCH.getActionName());
		baseRequest.setParam("seckillQTO", seckillQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<SeckillForMopDTO>> response = (Response<List<SeckillForMopDTO>>) seckillService.execute(baseRequest);
		return response;
	}

	public Response<List<OrderHistoryDTO>> queryOrderHistory(OrderHistoryQTO orderHistoryQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.QUERY_ORDER_HISTORY.getActionName());
		baseRequest.setParam("orderHistoryQTO", orderHistoryQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<OrderHistoryDTO>> response = seckillService.execute(baseRequest);
		return response;
	}
}
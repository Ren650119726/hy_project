package com.mockuai.tradecenter.core.service.action.settlement;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.constant.ActionEnum;
import com.mockuai.tradecenter.common.constant.ResponseCode;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogDTO;
import com.mockuai.tradecenter.common.domain.settlement.SellerTransLogQTO;
import com.mockuai.tradecenter.common.enums.EnumInOutMoneyType;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.SellerTransLogManager;
import com.mockuai.tradecenter.core.manager.ShopManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.service.action.Action;

/**
 * 查询收支明细
 * @author hzmk
 *
 */
public class QuerySellerTransLog implements Action{

	private static final Logger log = LoggerFactory.getLogger(QuerySellerTransLog.class);
	
	@Resource
	private SellerTransLogManager sellerTransLogManager;
	
	@Autowired
	private ShopManager shopManager;
	
	@Override
	public TradeResponse<?> execute(RequestContext context) throws TradeException {
		Request request = context.getRequest();
		TradeResponse<List<SellerTransLogDTO>> response = null;
		if(request.getParam("sellerTransLogQTO") == null){
			log.error("sellerTransLogQTO is null ");
			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"sellerTransLogQTO is null");
		}
		String appKey = (String) context.get("appKey");
		SellerTransLogQTO query = (SellerTransLogQTO) request.getParam("sellerTransLogQTO");
//		if( null == query.getSellerId() ){
//			log.error("sellerTransLogQTO.sellerId is null ");
//			return ResponseUtils.getFailResponse(ResponseCode.PARAM_E_PARAM_MISSING,"sellerId is null");
//		}
		String bizCode = (String) context.get("bizCode");
		query.setBizCode(bizCode);
		
		if(null!=query.getType()){
			query.setType(EnumInOutMoneyType.getByOldCode(query.getType()).getCode());
		}
		
		
		List<SellerTransLogDTO> dataList = (List<SellerTransLogDTO>) sellerTransLogManager.queryTransLog(query);
		if(null!=dataList&&dataList.isEmpty()==false){
			for(SellerTransLogDTO sellerTranslogDTO:dataList){
				if(sellerTranslogDTO.getSellerId()!=null){
					try{
						ShopDTO shopDTO = shopManager.getShopDTO(sellerTranslogDTO.getSellerId(), appKey);
						if(null!=shopDTO){
							sellerTranslogDTO.setShopName(shopDTO.getShopName());
						}
					}catch(Exception e){
						
					}catch(Throwable e){
						log.error("doOrderCommentBuriedPoint error",e);
					}
				}
			}
		}
		
		
		response = ResponseUtils.getSuccessResponse(dataList);
		Long totalCount = sellerTransLogManager.getQueryCount(query);
		response.setTotalCount(totalCount); // 总记录数 
		return response;
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SELLER_TRANS_LOG.getActionName();
	}

}

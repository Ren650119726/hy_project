package com.mockuai.deliverycenter.core.service.action.express;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.api.DeliveryResponse;
import com.mockuai.deliverycenter.common.constant.ActionEnum;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.ThirdpartyExpressInfoQTO;
import com.mockuai.deliverycenter.core.config.Kuaidi100Config;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.service.RequestContext;
import com.mockuai.deliverycenter.core.service.action.Action;
import com.mockuai.deliverycenter.core.util.HttpUtil;
import com.mockuai.deliverycenter.core.util.ResponseUtil;

/**
 * 根据物流单号、和快递公司编码查询物流信息   目前只支持快递100
 * @author hzmk
 *
 */
@Service
public class QueryThirdpartyExpress implements Action {
	
	private static final Logger log = LoggerFactory.getLogger(QueryThirdpartyExpress.class);
	
	@Autowired
	private Kuaidi100Config kuaidi100Config;

	@Override
	public DeliveryResponse<List<ThirdpartyExpressDetailDTO>> execute(RequestContext context) throws DeliveryException {
		// 获取参数
		ThirdpartyExpressInfoQTO query = (ThirdpartyExpressInfoQTO) context
						.getRequest().getParam("thirdpartyExpressInfoQTO");
				
		if(null==query){
			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
					"thirdpartyExpressInfoQTO is null");
		}
		
		if(StringUtils.isBlank(query.getExpressNo())){
			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
					"thirdpartyExpressInfoQTO.expressNo is null");
		}
		
		
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("id", kuaidi100Config.getKey()));
        params.add(new BasicNameValuePair("com", query.getDeliveryCompanyCode()));
		//nu
        params.add(new BasicNameValuePair("nu", query.getExpressNo()));
        String url = "http://api.kuaidi100.com/api";
        String jsonStr = HttpUtil.get(url, params);
        
        log.info("kuaidi 100 response message"+jsonStr);
        
		
		
		List<ThirdpartyExpressDetailDTO> expressDetailDTOs = new ArrayList<ThirdpartyExpressDetailDTO>();
		
		JSONObject  jsonObject = JSONObject.parseObject(jsonStr);
				String status = jsonObject.getString("status");//
				if(status.equalsIgnoreCase("1")){
					List<Map<String,String>> dataList = (List<Map<String,String>>) jsonObject.get("data");
					if(dataList!=null&&dataList.size()>0){
						for(Map<String,String> map:dataList){
							ThirdpartyExpressDetailDTO detail = new ThirdpartyExpressDetailDTO();
							detail.setTime(map.get("time"));
							detail.setContext(map.get("context"));
							expressDetailDTOs.add(detail);
						}
					}
				}else{
					String message = jsonObject.getString("message");
					throw new DeliveryException(message);
				}
				
		
				
				
		return ResponseUtil.getResponse(expressDetailDTOs, expressDetailDTOs.size());
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_THIRDPARTY_EXPRESS.getActionName();
	}

}

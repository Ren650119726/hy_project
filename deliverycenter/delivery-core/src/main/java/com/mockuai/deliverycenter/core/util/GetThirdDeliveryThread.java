package com.mockuai.deliverycenter.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.dto.express.ThirdpartyExpressDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.ThirdpartyExpressInfoQTO;
import com.mockuai.deliverycenter.core.domain.ThirdDeliveryInfoResult;

public class GetThirdDeliveryThread implements Callable<ThirdDeliveryInfoResult> {
	protected               Log                                 log         = LogFactory.getLog(this.getClass());
	private ThirdpartyExpressInfoQTO query;
	
	public GetThirdDeliveryThread(ThirdpartyExpressInfoQTO query){
		this.query = query;
	}

	@Override
	public ThirdDeliveryInfoResult call()  {
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("key", "252a882ccedd4f9894838ed0ff279400"));
        params.add(new BasicNameValuePair("id", query.getDeliveryCompanyCode()));
		//nu
        params.add(new BasicNameValuePair("order", query.getExpressNo()));
        params.add(new BasicNameValuePair("show", "json"));
        params.add(new BasicNameValuePair("ord", "desc"));
        log.info(" ExpressCode:"+query.getDeliveryCompanyCode()+" ExpressNo:"+query.getExpressNo());
        log.info(" params : "+JSONObject.toJSONString(params));
        ThirdDeliveryInfoResult thirdDeliveryInfoResult = new ThirdDeliveryInfoResult();
        try{
        	String url = "http://www.aikuaidi.cn/rest";
            String jsonStr = HttpUtil.get(url, params);
            log.info("aikuaidi response message"+jsonStr);
            thirdDeliveryInfoResult.setDeliveryCompanyCode(query.getDeliveryCompanyCode());
            thirdDeliveryInfoResult.setExpressNo(query.getExpressNo());
            List<ThirdpartyExpressDetailDTO> expressDetailDTOs = Collections.EMPTY_LIST;
        	
        	
        	JSONObject  jsonObject = JSONObject.parseObject(jsonStr);
        	String status = jsonObject.getString("status");//
        	if(status.equalsIgnoreCase("0")){ //查询出错
        		//TODO 容错处理
        		return thirdDeliveryInfoResult;
        	}
        	List<Map<String,String>> dataList = (List<Map<String,String>>) jsonObject.get("data");
        	if(null!=dataList&&dataList.size()>0){
        		expressDetailDTOs = new ArrayList<ThirdpartyExpressDetailDTO>();
        	}
        	for(Map<String,String> map:dataList){
				ThirdpartyExpressDetailDTO detail = new ThirdpartyExpressDetailDTO();
				detail.setTime(map.get("time"));
				detail.setContext(map.get("content"));
				expressDetailDTOs.add(detail);
			}
        	thirdDeliveryInfoResult.setExpressDetailDTOs(expressDetailDTOs);
        	
        }catch(Exception e){
        	log.error("aikuaidi response error",e);
        }
		
        return thirdDeliveryInfoResult;
	}

}

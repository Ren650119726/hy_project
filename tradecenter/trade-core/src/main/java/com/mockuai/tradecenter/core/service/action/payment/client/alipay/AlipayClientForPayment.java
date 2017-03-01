package com.mockuai.tradecenter.core.service.action.payment.client.alipay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.util.AlipaySubmit;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;
import com.mockuai.tradecenter.common.api.Request;
import com.mockuai.tradecenter.common.domain.PaymentUrlDTO;
import com.mockuai.tradecenter.common.domain.settlement.ProcessWithdrawDTO;
import com.mockuai.tradecenter.common.domain.settlement.WithdrawQTO;
import com.mockuai.tradecenter.common.util.DateUtil;
import com.mockuai.tradecenter.common.util.MoneyUtil;
import com.mockuai.tradecenter.core.base.ClientExecutor;
import com.mockuai.tradecenter.core.dao.WithdrawInfoDAO;
import com.mockuai.tradecenter.core.domain.WithdrawInfoDO;
import com.mockuai.tradecenter.core.exception.TradeException;
import com.mockuai.tradecenter.core.manager.WithdrawManager;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.action.payment.client.ClientExecutorAbstract;
import com.mockuai.tradecenter.core.util.TradeUtil;

/**
 * 
 * 批量付款
 *
 */
@Service
public class AlipayClientForPayment extends ClientExecutorAbstract  {

	private static final Logger log = LoggerFactory.getLogger(AlipayClientForPayment.class);

	@Autowired
	private WithdrawInfoDAO withdrawDAO;
	
	@Resource
	private WithdrawManager withdrawManager;

	private class DetailDataDTO {
		protected Long totalAmount;
		protected String detailData;
		protected int batchNum;
		protected List<Long> withdrawIds;

		public Long getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Long totalAmount) {
			this.totalAmount = totalAmount;
		}

		public String getDetailData() {
			return detailData;
		}

		public void setDetailData(String detailData) {
			this.detailData = detailData;
		}

		public int getBatchNum() {
			return batchNum;
		}

		public void setBatchNum(int batchNum) {
			this.batchNum = batchNum;
		}

		public List<Long> getWithdrawIds() {
			return withdrawIds;
		}

		public void setWithdrawIds(List<Long> withdrawIds) {
			this.withdrawIds = withdrawIds;
		}
		
		
	}

	private String getLine(WithdrawInfoDO dto) {
		  StringBuilder line = new StringBuilder();
		  line.append(dto.getId()+"^");
		  line.append(dto.getAccount()+"^");
		  line.append(dto.getName()+"^");
		  line.append(MoneyUtil.getMoneyStr(dto.getAmount())+"^");
		  line.append("转账");
		  return line.toString();
	}

	public DetailDataDTO getDetailData(ProcessWithdrawDTO processDTO) throws TradeException{
		DetailDataDTO detailDataDTO = new DetailDataDTO();
		WithdrawQTO query = new WithdrawQTO();
		query.setIds(processDTO.getWithdrawId());
		query.setOffset(0);
		query.setCount(processDTO.getWithdrawId().size());
		query.setStatus("WAIT");
		List<WithdrawInfoDO> list = withdrawDAO.queryWithdrawInfo(query);
		StringBuilder detailData = new StringBuilder(); 
		Long totalAmount = 0L;
		if(null==list||list.size()==0){
			throw new TradeException("withdraw detail is null");
		}
		List<Long> withdrawIds = new ArrayList<Long>();
		for (WithdrawInfoDO withdrawDTO : list) {
			detailData.append(getLine(withdrawDTO));
			detailData.append("|");
			totalAmount+=withdrawDTO.getAmount();
			withdrawIds.add(withdrawDTO.getId());
		}
		detailDataDTO.setBatchNum(list.size());
		detailDataDTO.setDetailData(detailData.toString());
		detailDataDTO.setTotalAmount(totalAmount);
		detailDataDTO.setWithdrawIds(withdrawIds);
		if (log.isInfoEnabled()) {
			log.info("AlipayClientForPayment.DetailDataDTO="+JSONObject.toJSONString(detailDataDTO));
		}
		return detailDataDTO;
	}

	public String getBatchId() throws TradeException {
		String batchId = DateUtil.getFormatDate(new Date(), "yyyyMMdd")
				+ +(System.currentTimeMillis() + 102L * 365 * 24 * 60 * 60 * 1000);
		return batchId;
	}

	private Map<String, String> getMockAlipayParams(DetailDataDTO detailDTO,Map<String, BizPropertyDTO> bizPropertyMap) throws Exception {
		BizPropertyDTO returnUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_RETURN_URL);
		BizPropertyDTO notifyUrlAppProperty = bizPropertyMap
				.get(com.mockuai.tradecenter.core.util.BizPropertyKey.ALIPAY_NOTIFY_URL);

		if (null == returnUrlAppProperty || null == notifyUrlAppProperty) {
			throw new TradeException("returnUrlAppProperty or notifyUrlAppProperty is null");
		}
		String batchId = getBatchId();
	
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "batch_trans_notify");
		sParaTemp.put("partner", "2088911841225245");
		sParaTemp.put("_input_charset", "utf-8");
		sParaTemp.put("notify_url", notifyUrlAppProperty.getValue());
		sParaTemp.put("email", "mockuai@aliyun.com");
		sParaTemp.put("account_name", "杭州魔筷科技有限公司");
		sParaTemp.put("pay_date", DateUtil.getFormatDate(new Date(), "yyyyMMdd"));
		sParaTemp.put("batch_no", batchId);
		sParaTemp.put("batch_fee", MoneyUtil.getMoneyStr(detailDTO.getTotalAmount()));
		sParaTemp.put("batch_num", detailDTO.getBatchNum()+"");
		sParaTemp.put("detail_data",detailDTO.getDetailData());
		
		return sParaTemp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentUrlDTO getPaymentUrl(RequestContext context) throws TradeException {
		String bizCode = (String) context.get("bizCode");

		Request request = context.getRequest();
		Map<String, BizPropertyDTO> bizPropertyMap = (Map<String, BizPropertyDTO>) context.get("bizPropertyMap");
		if (null == bizPropertyMap) {
			throw new TradeException(bizCode + " bizPropertyMap is null");
		}
		PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO();

		ProcessWithdrawDTO processWithdrawDTO = (ProcessWithdrawDTO) request.getParam("processWithdrawDTO");

		Map<String, String> paramMap = new HashMap<String, String>();
		try {
			DetailDataDTO detailDTO = getDetailData(processWithdrawDTO);
			
			paramMap = getMockAlipayParams(detailDTO,bizPropertyMap);
			// 建立请求
			String sHtmlText = AlipaySubmit.buildRequest(paramMap, "post", "确认", TradeUtil.getMchPrivateKey(bizPropertyMap));
			
			if(StringUtils.isNotBlank(sHtmlText)){
				processWithdrawDTO.setBatchNo(paramMap.get("batch_no"));
				processWithdrawDTO.setWithdrawId(detailDTO.getWithdrawIds());
				withdrawManager.processWithdraw(processWithdrawDTO);
			}
			
			paramMap = new HashMap<String, String>();
			paramMap.put("request_form", sHtmlText);
			paymentUrlDTO.setParams(paramMap);
		} catch (Exception e) {
			log.error("alipayClientForPayment error", e);
			throw new TradeException("alipayClientForPayment error");
		}

		return paymentUrlDTO;
	}

}

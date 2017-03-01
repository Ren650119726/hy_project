package com.mockuai.deliverycenter.core.manager.express.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryDetailDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryDetailQTO;
import com.mockuai.deliverycenter.core.domain.BaseDo;
import com.mockuai.deliverycenter.core.domain.express.DeliveryDetail;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.BaseManager;
import com.mockuai.deliverycenter.core.manager.express.DeliveryDetailManager;
import com.mockuai.deliverycenter.core.util.TransUtil;

@Service
public class DeliveryDetailManagerImpl extends BaseManager implements DeliveryDetailManager{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Boolean addDeliveryDetail(Long orderId,Long userId,List<DeliveryDetailDTO> detailList)throws DeliveryException {
		logger.debug("enter addDeliveryDetail: " + userId + " orderId " +orderId + " detaiList" +detailList.size());
		
		if(detailList == null){
			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
					"expressDTO is null");
		}
		int count =0;
		for(DeliveryDetailDTO dto : detailList){
			dto.setUserId(userId);
			dto.setOrderId(orderId);
			DeliveryDetail deliveryDetail = new DeliveryDetail();
			deliveryDetail.setOrderId(orderId);
			deliveryDetail.setUserId(userId);
			deliveryDetail.setOpTime(dto.getOpTime());
			deliveryDetail.setContent(dto.getContent());
			deliveryDetail.setDeliveryCode(dto.getDeliveryCode());
			deliveryDetail.setBizCode(dto.getBizCode());
			BaseDo saveDo = getBaseDao().insert("DELIVERYDETAIL.addDeliveryDetail",deliveryDetail);
			count++;
		}
		logger.debug("saved :" + count);
		return true;
	}
	

	@Override
	public int deleteByOrderId(Long orderId, Long userId) throws DeliveryException {
		logger.debug("enter deleteByOrderId " + orderId + " userId:" + userId);
		if(orderId == null){
			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
					"orderId is null");
		}else if(userId == null){
			throw new DeliveryException(RetCodeEnum.PARAMETER_NULL.getCode(),
					"userId is null");
		}
		
		DeliveryDetail detail =new DeliveryDetail();
		detail.setUserId(userId);
		detail.setOrderId(orderId);
		int result = this.getBaseDao().delete("DeliveryDetailDAO.deleteByOrderId",detail);
		logger.debug("deleted : " + result);
		return result;
	}


	@Override
	public List<DeliveryDetailDTO> queryDeliveryDetail(DeliveryDetailQTO deliveryDetailQTO)throws DeliveryException {
		
		List<DeliveryDetail> list = this.getBaseDao().queryForList("DeliveryDetailDAO.queryDeliveryDetail",deliveryDetailQTO);
		System.out.println(list.size());
		List<DeliveryDetailDTO> result = new ArrayList<DeliveryDetailDTO>();
		if(list != null){
			for(DeliveryDetail item: list){
				DeliveryDetailDTO dto = new DeliveryDetailDTO();
				TransUtil.trans2Dto(dto,item);
				dto.setDeliveryDetailUid(TransUtil.genUid(item.getId(), item.getUserId()));
				result.add(dto);
			}
		}
		return result;
	}
	
}

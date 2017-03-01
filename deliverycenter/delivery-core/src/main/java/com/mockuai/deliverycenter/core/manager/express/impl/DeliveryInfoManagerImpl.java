package com.mockuai.deliverycenter.core.manager.express.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.mockuai.deliverycenter.common.constant.ExpressCodeEnum;
import com.mockuai.deliverycenter.common.dto.express.DeliveryInfoDTO;
import com.mockuai.deliverycenter.common.dto.express.LogisticsCompanyDTO;
import com.mockuai.deliverycenter.common.qto.express.DeliveryInfoQTO;
import com.mockuai.deliverycenter.core.domain.express.DeliveryInfo;
import com.mockuai.deliverycenter.core.exception.DeliveryException;
import com.mockuai.deliverycenter.core.manager.BaseManager;
import com.mockuai.deliverycenter.core.manager.express.DeliveryInfoManager;
import com.mockuai.deliverycenter.core.util.LogisticsCompanyUtil;
import com.mockuai.deliverycenter.core.util.TransUtil;


@Service
public class DeliveryInfoManagerImpl extends BaseManager implements DeliveryInfoManager {

	private static final Logger log = LoggerFactory.getLogger(DeliveryInfoManagerImpl.class);
	
	@Override
	public Long addDeliveryInfo(DeliveryInfoDTO deliveryInfoDto) {
		DeliveryInfo deliveryInfo = new DeliveryInfo();
		deliveryInfo.setDeliveryId(deliveryInfoDto.getDeliveryId());
		Map<String, LogisticsCompanyDTO> map = LogisticsCompanyUtil.getLogisticsCompMaps();
		LogisticsCompanyDTO thirdpartyCompanyDTO = map.get(deliveryInfoDto.getExpressCode());
		if (null != thirdpartyCompanyDTO) {
			deliveryInfo.setExpress(thirdpartyCompanyDTO.getName());
		} else {
			// 兼容洋东西 如有没有传入expressCode的情况
			deliveryInfo.setExpress(deliveryInfoDto.getExpress());
		}
		deliveryInfo.setExpressCode(deliveryInfoDto.getExpressCode());
		deliveryInfo.setExpressNo(deliveryInfoDto.getExpressNo());
		deliveryInfo.setMemo(deliveryInfoDto.getMemo());
		deliveryInfo.setUserId(deliveryInfoDto.getUserId());
		deliveryInfo.setOrderId(deliveryInfoDto.getOrderId());
		deliveryInfo.setIsArrival(false);
		deliveryInfo.setDeliveryTime(deliveryInfoDto.getDeliveryTime());
		deliveryInfo.setBizCode(deliveryInfoDto.getBizCode());
		Long id = null;
		
		
		DeliveryInfoQTO deliveryQTO = new DeliveryInfoQTO();
		deliveryQTO.setOrderId(deliveryInfoDto.getOrderId());
		deliveryQTO.setExpressNo(deliveryInfoDto.getExpressNo());
		DeliveryInfo deliveryInfoDO = (DeliveryInfo) this.getBaseDao().getSqlMapClientTemplate().queryForObject("DeliveryInfoDAO.queryByOrderId",deliveryQTO);
		if(deliveryInfoDO!=null){
			id = deliveryInfoDO.getId();
			return id;
		}
		id = (Long) this.getBaseDao().getSqlMapClientTemplate().insert("DeliveryInfoDAO.addDeliveryInfo", deliveryInfo);
		// if(deliveryInfoDto.getSkuIdList()==null){
		// id =
		// (Long)this.getBaseDao().getSqlMapClientTemplate().insert("DeliveryInfoDAO.addDeliveryInfo",deliveryInfo);
		// }else{
		// for(Long skuId:deliveryInfoDto.getSkuIdList()){
		// deliveryInfo.setSkuId(skuId);
		// id =
		// (Long)this.getBaseDao().getSqlMapClientTemplate().insert("DeliveryInfoDAO.addDeliveryInfo",deliveryInfo);
		// }
		// }

		return id;
	}

	@Override
	public int deleteByOrderId(Long orderId, Long userId) {
		DeliveryInfo delivery = new DeliveryInfo();
		delivery.setOrderId(orderId);
		delivery.setUserId(userId);
		return this.getBaseDao().delete("DeliveryInfoDAO.deleteByOrderId", delivery);
	}

	@Override
	public List<DeliveryInfoDTO> queryDeliveryInfo(DeliveryInfoQTO deliveryInfoQto) {

		List<DeliveryInfo> list = this.getBaseDao().getSqlMapClientTemplate()
				.queryForList("DeliveryInfoDAO.queryByOrderId", deliveryInfoQto);
		
		log.info(" List<DeliveryInfo> : "+JSONObject.toJSONString(list));
		
		List<DeliveryInfoDTO> result = new ArrayList<DeliveryInfoDTO>();
		if (list != null) {
			for (DeliveryInfo item : list) {
				DeliveryInfoDTO deliveryInfo = new DeliveryInfoDTO();
				deliveryInfo.setId(item.getId());
				deliveryInfo.setDeliveryId(item.getDeliveryId());
				deliveryInfo.setExpress(item.getExpress());
				deliveryInfo.setExpressNo(item.getExpressNo());
				deliveryInfo.setMemo(item.getMemo());
				deliveryInfo.setUserId(item.getUserId());
				deliveryInfo.setOrderId(item.getOrderId());
				deliveryInfo.setDeliveryTime(item.getDeliveryTime());
				deliveryInfo.setDeliveryInfoUid(TransUtil.genUid(item.getId(), item.getUserId()));
				deliveryInfo.setExpressCode(item.getExpressCode());
				
				// 增加快递公司访问地址
				if(ExpressCodeEnum.getByCode(item.getExpressCode()) != null ){
					deliveryInfo.setExpressUrl(ExpressCodeEnum.getByCode(item.getExpressCode()).getUrl());
				}else{
					deliveryInfo.setExpressUrl(ExpressCodeEnum.OTHER.getUrl());
				}
				
				//是否可修改
				Date createTime = item.getGmtCreated();
				Date endTime = new Date();
				long times = endTime.getTime() - createTime.getTime();
				long day = 0;  
		        long hour = 0;  
		        long min = 0;
		        day = times / (24 * 60 * 60 * 1000);  
	            hour = (times / (60 * 60 * 1000) - day * 24);  
	            min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);  
	            if(min>10){
	            	deliveryInfo.setEditable(1);
	            }else{
	            	deliveryInfo.setEditable(0);
	            }
	            
				result.add(deliveryInfo);
			}
		}
		
		return result;
	}

	@Override
	public Boolean updateDeliveryInfo(DeliveryInfoDTO deliveryInfoDto) throws DeliveryException {
		DeliveryInfoQTO query = new DeliveryInfoQTO();
		query.setId(deliveryInfoDto.getId());
		DeliveryInfo deliveryInfo = (DeliveryInfo) this.getBaseDao().getSqlMapClientTemplate()
				.queryForObject("DeliveryInfoDAO.queryByOrderId", query);
		if (deliveryInfo == null) {
			throw new DeliveryException("deliveryInfo is null");
		}
		deliveryInfo.setExpressCode(deliveryInfoDto.getExpressCode());
		deliveryInfo.setExpressNo(deliveryInfoDto.getExpressNo());
		
		Map<String, LogisticsCompanyDTO> map = LogisticsCompanyUtil.getLogisticsCompMaps();
		LogisticsCompanyDTO thirdpartyCompanyDTO = map.get(deliveryInfoDto.getExpressCode());
		
		if (null != thirdpartyCompanyDTO) {
			deliveryInfo.setExpress(thirdpartyCompanyDTO.getName());
		} else {
			// 兼容洋东西 如有没有传入expressCode的情况
			deliveryInfo.setExpress(deliveryInfoDto.getExpressCode());
		}
		this.getBaseDao().update("DeliveryInfoDAO.update", deliveryInfo);
		return true;
	}

	@Override
	public DeliveryInfoDTO getDeliveryInfo(DeliveryInfoDTO deliveryInfoDto){
		DeliveryInfoQTO query = new DeliveryInfoQTO();
		query.setExpressNo(deliveryInfoDto.getExpressNo());
//		DeliveryInfo deliveryInfo = (DeliveryInfo) this.getBaseDao().getSqlMapClientTemplate().queryForObject("DeliveryInfoDAO.queryByOrderId", query);
		// 一个物流单号存在多个订单号情况 TODO
		List<DeliveryInfo> deliveryInfos = (List<DeliveryInfo>) this.getBaseDao().getSqlMapClientTemplate().queryForList("DeliveryInfoDAO.queryByOrderId", query);
		/*if (deliveryInfo == null) {
			return new DeliveryInfoDTO();
		}else{
			DeliveryInfoDTO deliveryInfoDTO = new DeliveryInfoDTO();
            BeanUtils.copyProperties(deliveryInfo, deliveryInfoDTO);
            return deliveryInfoDTO;
		}*/
		if (CollectionUtils.isEmpty(deliveryInfos)) {
			return new DeliveryInfoDTO();
		}else{
			DeliveryInfoDTO deliveryInfoDTO = new DeliveryInfoDTO();
            BeanUtils.copyProperties(deliveryInfos.get(0), deliveryInfoDTO);
            return deliveryInfoDTO;
		}
	}
}

package com.mockuai.headsinglecenter.core.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.headsinglecenter.common.constant.ResponseCode;
import com.mockuai.headsinglecenter.common.domain.dto.HeadSingleInfoDTO;
import com.mockuai.headsinglecenter.core.dao.HeadSingleInfoDAO;
import com.mockuai.headsinglecenter.core.domain.HeadSingleInfoDO;
import com.mockuai.headsinglecenter.core.exception.HeadSingleException;
import com.mockuai.headsinglecenter.core.manager.HeadSingleInfoManager;
import com.mockuai.headsinglecenter.core.util.HeadSingleUtils;


/**
 * Created by csy 2016-07-13
 */
@Service
public class HeadSingleInfoManagerImpl implements HeadSingleInfoManager {
    private static final Logger log = LoggerFactory.getLogger(HeadSingleInfoManagerImpl.class);
    
    @Resource
    private HeadSingleInfoDAO headSingleInfoDAO;
    
	@Override
	public List<Long> queryOrderIdList(String terminalType, Date payTimeStart, Date payTimeEnd) throws HeadSingleException {
		try{ 
			List<Long> orderIdList = new ArrayList<Long>();
			//平台类型转换
			Integer terType = null;		
			
			if("ios".equalsIgnoreCase(terminalType)){//ios端
				terType = 1;
			}else if("android".equalsIgnoreCase(terminalType)){//android端
				terType = 2;
			}else if("web".equalsIgnoreCase(terminalType)){//web端
				terType = 3;
			}else if("all".equalsIgnoreCase(terminalType)){
				terType = null;
			}
			
			List<HeadSingleInfoDO> headSingleInfoDOList = headSingleInfoDAO.queryHeadSingleInfos(terType, payTimeStart, payTimeEnd);
			
			if(null == headSingleInfoDOList || headSingleInfoDOList.isEmpty()){
				return orderIdList = null;
			}
			
			//获取订单idList
			for(HeadSingleInfoDO infoDo:headSingleInfoDOList){
				orderIdList.add(infoDo.getOrderId());
			}
			
			if(null == orderIdList || orderIdList.isEmpty()){
				throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_INFO_ERROR);
			}
			
			return orderIdList;
		} catch (Exception e) {
			log.error("query head single info error:"+e);
			throw new HeadSingleException(ResponseCode.QUERY_HEADSINGLE_INFO_ERROR);
		}
	}
	
	/**
	 * 新增或保存用户首单订单信息
	 * 
	 * @author csy
	 * @Date 2016-07-22
	 * 
	 */
	@Override
	public Long addHeadSingleInfo(HeadSingleInfoDTO headSingleInfoDTO) throws HeadSingleException {
		if(null == headSingleInfoDTO){
			log.error("add head single info:"+headSingleInfoDTO);
			return null;
		}
		
		//查询此订单是否已经存在
		Long orderId = headSingleInfoDTO.getOrderId();
		HeadSingleInfoDO headSingleInfoDO = headSingleInfoDAO.queryInfoDtoByOrderId(orderId);
		
		if(null != headSingleInfoDO){			
			return null;
		}
		
		//dto转换do
		headSingleInfoDO = new HeadSingleInfoDO();
		HeadSingleUtils.copyProperties(headSingleInfoDTO, headSingleInfoDO);
		
		//保存享受订单记录
		Long hsInfoId = headSingleInfoDAO.addHeadSingleInfo(headSingleInfoDO);
		
		return hsInfoId;
	}   
}
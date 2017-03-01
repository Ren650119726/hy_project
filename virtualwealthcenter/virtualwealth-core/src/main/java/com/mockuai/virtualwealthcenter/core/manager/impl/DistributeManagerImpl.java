package com.mockuai.virtualwealthcenter.core.manager.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.distributioncenter.client.DistributionClient;
import com.mockuai.distributioncenter.client.HkProtocolRecordClient;
import com.mockuai.distributioncenter.common.api.Response;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.DistributeManager;

/**
 * Created by linyue on 16/10/11.
 */

public class DistributeManagerImpl implements DistributeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributeManagerImpl.class.getName());

    @Resource
    private HkProtocolRecordClient hkProtocolRecordClient;

	@Override
	public Boolean getUserAgreeProcotolRecord(Long userId, String appKey)
			throws VirtualWealthException {
		try {
			HkProtocolRecordDTO hkProtocolRecordDTO = new HkProtocolRecordDTO();
			hkProtocolRecordDTO.setUserId(userId);
			Response<HkProtocolRecordDTO> result = hkProtocolRecordClient.getHkProtocolRecord( hkProtocolRecordDTO, appKey);
			if(result==null || result.getModule() == null || result.getModule().getUserId() == null){
				return false;
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			throw new VirtualWealthException(e);
		}
	}

    
}
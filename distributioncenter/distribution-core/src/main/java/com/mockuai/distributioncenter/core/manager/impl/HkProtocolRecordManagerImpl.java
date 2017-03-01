package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.HkProtocolDTO;
import com.mockuai.distributioncenter.core.domain.HkProtocolDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.core.dao.HkProtocolRecordDAO;
import com.mockuai.distributioncenter.core.domain.HkProtocolRecordDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolRecordManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizg on 2016/8/29.
 */

@Component
public class HkProtocolRecordManagerImpl implements HkProtocolRecordManager {

    private static final Logger log = LoggerFactory.getLogger(HkProtocolRecordManagerImpl.class);

    @Autowired
    private HkProtocolRecordDAO hkProtocolRecordDAO;

    @Override
    public Long add(HkProtocolRecordDTO hkProtocolRecordDTO) throws DistributionException {
    	try {
    		HkProtocolRecordDO hkProtocolRecordDO = new HkProtocolRecordDO();
            BeanUtils.copyProperties(hkProtocolRecordDTO,hkProtocolRecordDO);
            return hkProtocolRecordDAO.add(hkProtocolRecordDO);
		} catch (Exception e) {
			log.error("add hkProtocolRecord error!!!!!!",JsonUtil.toJson(hkProtocolRecordDTO));
		}
    	return 1L;
    }

    @Override
    public HkProtocolRecordDTO get(HkProtocolRecordDTO hkProtocolRecordDTO) throws DistributionException {
    	HkProtocolRecordDO hkProtocolRecordDO = new HkProtocolRecordDO();
        BeanUtils.copyProperties(hkProtocolRecordDTO,hkProtocolRecordDO);
        HkProtocolRecordDO hkProtocolRecord = hkProtocolRecordDAO.get(hkProtocolRecordDO);
        if(hkProtocolRecord != null){
        	HkProtocolRecordDTO hkProtocol = new HkProtocolRecordDTO();
        	BeanUtils.copyProperties(hkProtocolRecord, hkProtocol);
            return hkProtocol;
        }
        return null;
    }

    @Override
    public List<HkProtocolRecordDTO> getByUserId(HkProtocolRecordDTO hkProtocolRecordDTO) throws DistributionException {
        List<HkProtocolRecordDTO> hkProtocolRecordDTOList = new ArrayList<HkProtocolRecordDTO>();

        HkProtocolRecordDO hkProtocolRecordDO = new HkProtocolRecordDO();
        hkProtocolRecordDO.setUserId(hkProtocolRecordDTO.getUserId());
        List<HkProtocolRecordDO> hkProtocolRecordDOs = hkProtocolRecordDAO.getByUserId(hkProtocolRecordDO);
        for (HkProtocolRecordDO hkProtocolRecord : hkProtocolRecordDOs) {
            HkProtocolRecordDTO hkProtocolRecordDTO1 = new HkProtocolRecordDTO();
            BeanUtils.copyProperties(hkProtocolRecord, hkProtocolRecordDTO1);
            hkProtocolRecordDTOList.add(hkProtocolRecordDTO1);

        }

        return hkProtocolRecordDTOList;
    }


}

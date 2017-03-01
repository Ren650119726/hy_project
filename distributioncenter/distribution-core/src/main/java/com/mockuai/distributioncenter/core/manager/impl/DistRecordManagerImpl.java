package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.core.dao.DistRecordDAO;
import com.mockuai.distributioncenter.core.domain.DistRecordDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/3/11.
 */
@Component
public class DistRecordManagerImpl implements DistRecordManager {
    @Autowired
    private DistRecordDAO distRecordDAO;

    @Override
    public Long add(DistRecordDTO distRecordDTO)
            throws DistributionException {
        if (distRecordDTO != null) {
            DistRecordDO distRecordDO = new DistRecordDO();
            BeanUtils.copyProperties(distRecordDTO, distRecordDO);
            return distRecordDAO.add(distRecordDO);
        } else {
            return null;
        }
    }

    @Override
    public List<DistRecordDTO> query(DistRecordQTO distRecordQTO)
            throws DistributionException {
        List<DistRecordDTO> distRecordDTOs = new ArrayList<DistRecordDTO>();
        List<DistRecordDO> distRecordDOs = distRecordDAO.query(distRecordQTO);
        for (DistRecordDO distRecordDO : distRecordDOs) {
            DistRecordDTO distRecordDTO = new DistRecordDTO();
            BeanUtils.copyProperties(distRecordDO, distRecordDTO);
            distRecordDTOs.add(distRecordDTO);
        }
        return distRecordDTOs;
    }

    @Override
    public Boolean update(DistRecordDTO distRecordDTO) throws DistributionException {
        DistRecordDO distRecordDO = new DistRecordDO();
        BeanUtils.copyProperties(distRecordDTO, distRecordDO);
        distRecordDAO.update(distRecordDO);
        return true;
    }

    @Override
    public List<Long> queryValuableOrderIds(DistRecordQTO distRecordQTO) throws DistributionException {
        List<Long> ids = distRecordDAO.queryValuableOrderIds(distRecordQTO);
        return ids;
    }
   
    @Override
    public List<DistRecordDTO> queryStatistics(DistRecordQTO distRecordQTO) throws DistributionException{
    	List<DistRecordDTO> distRecordDTOs = new ArrayList<DistRecordDTO>();
        List<DistRecordDO> distRecordDOs = distRecordDAO.queryStatistics(distRecordQTO);
        for (DistRecordDO distRecordDO : distRecordDOs) {
            DistRecordDTO distRecordDTO = new DistRecordDTO();
            BeanUtils.copyProperties(distRecordDO, distRecordDTO);
            distRecordDTOs.add(distRecordDTO);
        }
        return distRecordDTOs;
    }
}

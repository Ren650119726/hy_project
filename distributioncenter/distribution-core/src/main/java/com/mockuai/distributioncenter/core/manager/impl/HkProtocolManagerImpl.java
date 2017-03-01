package com.mockuai.distributioncenter.core.manager.impl;

import com.mockuai.distributioncenter.common.domain.dto.HkProtocolDTO;
import com.mockuai.distributioncenter.common.domain.qto.HkProtocolQTO;
import com.mockuai.distributioncenter.core.dao.HkProtocolDAO;
import com.mockuai.distributioncenter.core.domain.HkProtocolDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizg on 2016/10/24.
 */

@Component
public class HkProtocolManagerImpl implements HkProtocolManager {

    private static final Logger log = LoggerFactory.getLogger(GainsSetManagerImpl.class);

    @Autowired
    private HkProtocolDAO hkProtocolDAO;

    @Override
    public List<HkProtocolDTO> getByProModel(HkProtocolQTO hkProtocolQTO) throws DistributionException {

        List<HkProtocolDTO> hkProtocolDTOList = new ArrayList<HkProtocolDTO>();
        List<HkProtocolDO> hkProtocolDOs = hkProtocolDAO.getByProModel(hkProtocolQTO);
        for (HkProtocolDO hkProtocolDO : hkProtocolDOs) {

            HkProtocolDTO hkProtocolDTO = new HkProtocolDTO();
            BeanUtils.copyProperties(hkProtocolDO, hkProtocolDTO);
            try {
                hkProtocolDTO.setProContent(getBlob(hkProtocolDO.getContent()));
                hkProtocolDTO.setContent(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            hkProtocolDTOList.add(hkProtocolDTO);

        }

        return hkProtocolDTOList;
    }

    @Override
    public List<HkProtocolDTO> getProModelByUserId(HkProtocolQTO hkProtocolQTO) throws DistributionException {
        List<HkProtocolDTO> hkProtocolDTOList = new ArrayList<HkProtocolDTO>();
        List<HkProtocolDO> hkProtocolDOs = hkProtocolDAO.getProModelByUserId(hkProtocolQTO);
        for (HkProtocolDO hkProtocolDO : hkProtocolDOs) {
            HkProtocolDTO hkProtocolDTO = new HkProtocolDTO();
            BeanUtils.copyProperties(hkProtocolDO, hkProtocolDTO);
            try {
                hkProtocolDTO.setProContent(getBlob(hkProtocolDO.getContent()));
                hkProtocolDTO.setContent(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            hkProtocolDTOList.add(hkProtocolDTO);
        }
        return hkProtocolDTOList;
    }

    public String getBlob(byte[] content) throws Exception {
        String StrResult = "";
        if (content != null) {
            byte[] blobBytes = content;

            StrResult = new String(blobBytes, "UTF-8");
        }
        return StrResult;
    }
}
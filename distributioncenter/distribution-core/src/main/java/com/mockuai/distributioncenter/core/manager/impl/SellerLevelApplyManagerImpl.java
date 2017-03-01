package com.mockuai.distributioncenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerLevelApplyDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerLevelApplyQTO;
import com.mockuai.distributioncenter.core.dao.SellerConfigDAO;
import com.mockuai.distributioncenter.core.dao.SellerDAO;
import com.mockuai.distributioncenter.core.dao.SellerLevelApplyDAO;
import com.mockuai.distributioncenter.core.domain.SellerConfigDO;
import com.mockuai.distributioncenter.core.domain.SellerDO;
import com.mockuai.distributioncenter.core.domain.SellerLevelApplyDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerLevelApplyManager;

/**
 * Created by yeliming on 16/5/18.
 */
@Component
public class SellerLevelApplyManagerImpl implements SellerLevelApplyManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerLevelApplyManagerImpl.class);

    @Autowired
    private SellerLevelApplyDAO sellerLevelApplyDAO;
    @Autowired
	private SellerDAO sellerDAO;
    @Autowired
    private SellerConfigDAO sellerConfigDAO;
    
	@Override
    public Long addSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO) throws DistributionException {
		try {
			SellerLevelApplyDO sellerLevelApplyDO = new SellerLevelApplyDO();
			BeanUtils.copyProperties(sellerLevelApplyDTO, sellerLevelApplyDO);
			if(sellerLevelApplyDTO != null){
				SellerDO sellerDO =  sellerDAO.getByUserId(sellerLevelApplyDTO.getApplicantId());
				sellerLevelApplyDO.setRealName(sellerDO.getRealName());
			}
			sellerLevelApplyDO.setStatus(0);
			Long id = sellerLevelApplyDAO.addSellerLevelApply(sellerLevelApplyDO);
	        return id;
		} catch (Exception e) {
			throw new DistributionException(ResponseCode.UPDATE_DB_ERROR);
		}
    }
	
	@Override
    public SellerLevelApplyDTO getSellerLevelApply(SellerLevelApplyDTO sellerLevelApplyDTO) throws DistributionException {
		try {
			SellerLevelApplyDO sellerLevelApplyDO = new SellerLevelApplyDO();
			BeanUtils.copyProperties(sellerLevelApplyDTO, sellerLevelApplyDO);
			SellerLevelApplyDO sellerLevelApply = sellerLevelApplyDAO.getSellerLevelApply(sellerLevelApplyDO);
			SellerLevelApplyDTO dto = new  SellerLevelApplyDTO();
			if(sellerLevelApply == null ){
				dto = null;
			}else{
				BeanUtils.copyProperties(sellerLevelApply, dto);
			}
	        return dto;
		} catch (Exception e) {
			throw new DistributionException(ResponseCode.UPDATE_DB_ERROR);
		}
    }
	
	@Override
    public List<SellerLevelApplyDTO> querySellerLevelApply(SellerLevelApplyQTO sellerLevelApplyQTO) throws DistributionException{
		try {
			List<SellerLevelApplyDTO>  sellerLevelApplyDTOs = new ArrayList<SellerLevelApplyDTO>();
			List<SellerLevelApplyDO> list = sellerLevelApplyDAO.querySellerLevelApply(sellerLevelApplyQTO);
			if (list != null) {
	            for (SellerLevelApplyDO sellerLevelApplyDO : list) {
	            	SellerLevelApplyDTO sellerLevelApplyDTO = new SellerLevelApplyDTO();
	                BeanUtils.copyProperties(sellerLevelApplyDO, sellerLevelApplyDTO);
	                sellerLevelApplyDTOs.add(sellerLevelApplyDTO);
	            }
	            
	            for (int i = 0; i < sellerLevelApplyDTOs.size(); i++) {
	    			
	            	SellerDO sellerDo = sellerDAO.getByUserId(sellerLevelApplyDTOs.get(i).getApplicantId());
	            	sellerLevelApplyDTOs.get(i).setGroupCount(sellerDo.getGroupCount());
	            	sellerLevelApplyDTOs.get(i).setDirectCount(sellerDo.getDirectCount());
	            	SellerConfigQTO sellerConfigQTO = new SellerConfigQTO();
	            	sellerConfigQTO.setLevel(Integer.valueOf(sellerDo.getLevelId().toString()));
	            	List<SellerConfigDO> sellerConfigDO =  sellerConfigDAO.querySellerConfig(sellerConfigQTO);
	            	sellerLevelApplyDTOs.get(i).setLevelName(sellerConfigDO.get(0).getLevelName());
	    		}
	        }else{
	        	sellerLevelApplyDTOs = null;
	        }
			return sellerLevelApplyDTOs;
		} catch (Exception e) {
			throw new DistributionException(ResponseCode.UPDATE_DB_ERROR);
		}
	}
	
	@Override
    public Boolean updateSellerConfig(SellerLevelApplyDTO sellerLevelApplyDTO) throws DistributionException{
		SellerLevelApplyDO sellerLevelApplyDO = new SellerLevelApplyDO();
		BeanUtils.copyProperties(sellerLevelApplyDTO, sellerLevelApplyDO);
		int n = sellerLevelApplyDAO.updateSellerLevelApply(sellerLevelApplyDO);
		if (n == 1) {
			if(sellerLevelApplyDO.getStatus() == 1){
				SellerDO seller = sellerDAO.getByUserId(sellerLevelApplyDO.getApplicantId());
				SellerDO sellerDO = new SellerDO();
				if(sellerLevelApplyDO.getType() == 1){
					if(seller.getLevelId() < 3){
						sellerDO.setLevelId(seller.getLevelId()+1);	
					}
				}else{
					if(seller.getLevelId() > 1){
						sellerDO.setLevelId(seller.getLevelId()-1);	
					}
				}
				sellerDAO.updateByUserId(sellerLevelApplyDO.getApplicantId(), sellerDO);
	            return true;
			}
        } else {
            throw new DistributionException(ResponseCode.UPDATE_DB_ERROR);
        }
	 return true;
	}
}

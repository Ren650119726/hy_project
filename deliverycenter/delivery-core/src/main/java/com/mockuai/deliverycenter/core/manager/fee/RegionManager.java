package com.mockuai.deliverycenter.core.manager.fee;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.deliverycenter.core.domain.fee.Region;
import com.mockuai.deliverycenter.core.exception.DeliveryException;

@Service
public interface RegionManager {

	public RegionDTO addRegion(RegionDTO RegionDTO) throws DeliveryException;

	public int updateRegion(RegionDTO RegionDTO) throws DeliveryException;

	public int deleteRegion(String regionCode) throws DeliveryException;

	public List<RegionDTO> queryRegion(RegionQTO regionQTO)
			throws DeliveryException;

	public Region getRegion(String regionCode) throws DeliveryException;

}

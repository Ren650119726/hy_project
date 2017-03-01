package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.common.domain.qto.SpecQTO;

import java.util.List;

public interface ItemSpecClient {
	

    Response<List<SpecDTO>> querySpec(SpecQTO specQTO, String appKey);
    Response<SpecDTO> getSpec(Long id, String appKey);
    Response<Void> updateSpec(SpecDTO specDTO, String appKey);
    Response<Long> addSpec(SpecDTO specDTO, String appKey);
    Response<Void> deleteSpec(Long id, String appKey);

	
}

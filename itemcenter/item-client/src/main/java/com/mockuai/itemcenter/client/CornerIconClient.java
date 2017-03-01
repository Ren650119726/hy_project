package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CornerIconDTO;
import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;

public interface CornerIconClient {
	
	public Response<CornerIconDTO> addCornerIcon(CornerIconDTO cornerIconDTO, String appKey);
	
	public Response<Boolean> deleteCornerIcon(Long id,Long sellerId, String appKey);
	
	public Response<List<CornerIconDTO>> queryCornerIcon(CornerIconQTO cornerIconQTO, String appKey);
	
	public Response<CornerIconDTO> getCornerIcon(Long id, String appKey);
	
}

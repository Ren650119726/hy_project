package com.mockuai.itemcenter.client;

import java.util.List;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月21日 上午10:54:07 
 */
public interface HotNameClient {
	 Response<List<HotNameDTO>> getHotNameList( HotNameDTO hotNameDTO, String appKey);
	
	 Response<Boolean> updateHotName(HotNameDTO hotNameDTO,String appKey);

	 Response<Boolean>  insertHotName(HotNameDTO hotNameDTO,String appKey);

	 Response<Boolean> modifySerialNumber(Long id,String climb,String appKey);

}

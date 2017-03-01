package com.mockuai.itemcenter.core.manager;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.domain.HotNameDO;
import com.mockuai.itemcenter.core.exception.ItemException;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月19日 下午1:39:43 
 */
public interface HotNameManager {
	 List<HotNameDTO> getHotNameList(HotNameDTO hotNameDTO) throws ItemException;
	 boolean updateHotName(HotNameDTO hotNameDTO) throws ItemException;
	 boolean insertHotName(HotNameDTO hotNameDTO) throws ItemException;
	 HotNameDTO getHotNameById(Long id) throws ItemException;
	 boolean clickRateHotName(HotNameDTO hotNameDTO)throws ItemException;
	 HotNameDTO    queryClimbObj(String climb ,Long indexSort)throws ItemException;
	 Long hotNameCount() throws ItemException;
	 HotNameDTO getHotNameByName(String hotName) throws ItemException;

}

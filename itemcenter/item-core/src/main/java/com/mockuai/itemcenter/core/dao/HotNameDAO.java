package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.HotNameDTO;
import com.mockuai.itemcenter.core.domain.HotNameDO;

/**
 *   
 * @author huangsiqian
 * @version 2016年9月19日 上午11:45:34 
 */
public interface HotNameDAO {
	Long addHotName(HotNameDO hotNameDO) ;
	
	Integer updateHotName(HotNameDO hotNameDO);
		
	HotNameDO selectHotNameById(Long id);
	List<HotNameDO> selectHotName(HotNameDTO hotNameDTO); 
	
	Integer clickRateHotName(HotNameDO hotNameDO);

	HotNameDO queryClimbObj(String climb, Long indexSort);

	Long hotNameCount();

	HotNameDO selectHotNameByName(String name);
}

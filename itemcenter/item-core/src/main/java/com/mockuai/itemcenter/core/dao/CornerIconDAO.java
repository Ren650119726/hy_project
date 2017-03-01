package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.common.domain.qto.CornerIconQTO;
import com.mockuai.itemcenter.core.domain.CornerIconDO;

public interface CornerIconDAO {

	public Long addCornerIcon(CornerIconDO cornerIconDO);
	
	public int deleteCornerIcon(CornerIconDO cornerIconDO);
	
	public List<CornerIconDO> queryCornerIcon(CornerIconQTO cornerIconQTO);
	
	public CornerIconDO getCornerIcon(CornerIconDO cornerIconDO);
	
}

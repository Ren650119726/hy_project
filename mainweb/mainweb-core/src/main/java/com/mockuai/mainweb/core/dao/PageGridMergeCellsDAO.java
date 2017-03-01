package com.mockuai.mainweb.core.dao;

import java.util.List;

import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.domain.PageGridMergeCellsDO;

public interface PageGridMergeCellsDAO {

	public List<PageGridMergeCellsDO> queryGridMergeCells(PageGridQTO pageGridMergeCellsQTO);
	
	public int updateGridMergeCells(PageGridQTO pageGridMergeCellsQTO);
	
	public int removeGridMergeCells(PageGridQTO pageGridMergeCellsQTO);
	
	public Long addGridMergeCells(PageGridMergeCellsDO pageGridMergeCellsDO);
	
}

package com.mockuai.mainweb.core.dao;

import java.util.List;

import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.domain.PageGridContentDO;

public interface PageGridContentDAO {

	public List<PageGridContentDO> queryGridContent(PageGridQTO pageGridContentQTO);
	
	public int updateGridContent(PageGridQTO pageGridContentQTO);
	
	public int removeGridContent(PageGridQTO pageGridContentQTO);
	
	public Long addGridContent(PageGridContentDO pageGridContentDO);
	
}

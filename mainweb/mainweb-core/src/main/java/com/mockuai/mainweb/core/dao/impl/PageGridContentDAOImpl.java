package com.mockuai.mainweb.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PageGridContentDAO;
import com.mockuai.mainweb.core.domain.PageGridContentDO;

@Service
public class PageGridContentDAOImpl extends BaseDAO implements PageGridContentDAO {

	@Override
	public List<PageGridContentDO> queryGridContent(
			PageGridQTO pageGridContentQTO) {
		
		return (List<PageGridContentDO>)this.getSqlMapClientTemplate().queryForList("pageGridContent.queryGridContent", pageGridContentQTO);
	}

	@Override
	public int updateGridContent(PageGridQTO pageGridContentQTO) {
		
		return this.getSqlMapClientTemplate().update("pageGridContent.updateGridContent", pageGridContentQTO);
	}

	@Override
	public int removeGridContent(PageGridQTO pageGridContentQTO) {

		return this.getSqlMapClientTemplate().delete("pageGridContent.deleteGridContent", pageGridContentQTO);
	}

	@Override
	public Long addGridContent(PageGridContentDO pageGridContentDO) {

		return (Long)this.getSqlMapClientTemplate().insert("pageGridContent.addGridContent", pageGridContentDO);
	}

	
	
}

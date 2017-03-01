package com.mockuai.mainweb.core.dao.impl;

import org.springframework.stereotype.Service;

import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PageGridDAO;
import com.mockuai.mainweb.core.domain.PageGridDO;

@Service
public class PageGridDAOImpl extends BaseDAO implements PageGridDAO {

	@Override
	public PageGridDO getGrid(PageGridQTO pageGridQTO) {		
		return (PageGridDO)this.getSqlMapClientTemplate().queryForObject("pageGrid.queryGrid", pageGridQTO);
	}

	@Override
	public int updateGrid(PageGridQTO pageGridQTO) {		
		return this.getSqlMapClientTemplate().update("pageGrid.updatePageGrid", pageGridQTO);
	}

	@Override
	public int removeGrid(PageGridQTO pageGridQTO) {		
		return this.getSqlMapClientTemplate().delete("pageGrid.deletePageGrid", pageGridQTO);
	}

	@Override
	public Long addGrid(PageGridDO pageGridDO) {		
		return (Long)this.getSqlMapClientTemplate().insert("pageGrid.addPageGrid", pageGridDO);
	}
	
}

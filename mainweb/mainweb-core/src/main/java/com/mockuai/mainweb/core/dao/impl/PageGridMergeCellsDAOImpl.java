package com.mockuai.mainweb.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PageGridMergeCellsDAO;
import com.mockuai.mainweb.core.domain.PageGridMergeCellsDO;

@Service
public class PageGridMergeCellsDAOImpl  extends BaseDAO implements PageGridMergeCellsDAO {

	@Override
	public List<PageGridMergeCellsDO> queryGridMergeCells(
			PageGridQTO pageGridMergeCellsQTO) {
		return (List<PageGridMergeCellsDO>)this.getSqlMapClientTemplate().queryForList("pageGridMergeCells.queryPageGridMergeCells", pageGridMergeCellsQTO);
	}

	@Override
	public int updateGridMergeCells(PageGridQTO pageGridMergeCellsQTO) {
		return this.getSqlMapClientTemplate().update("pageGridMergeCells.updatePageGridMergeCells", pageGridMergeCellsQTO);
	}

	@Override
	public int removeGridMergeCells(PageGridQTO pageGridMergeCellsQTO) {
		return this.getSqlMapClientTemplate().delete("pageGridMergeCells.deletePageGridMergeCells", pageGridMergeCellsQTO);
	}

	@Override
	public Long addGridMergeCells(PageGridMergeCellsDO pageGridMergeCellsDO) {
		return (Long)this.getSqlMapClientTemplate().insert("pageGridMergeCells.addPageGridMergeCells", pageGridMergeCellsDO);
	}
	
}

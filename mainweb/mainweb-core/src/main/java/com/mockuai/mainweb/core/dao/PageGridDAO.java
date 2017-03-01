package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.common.domain.qto.PageGridQTO;
import com.mockuai.mainweb.core.domain.PageGridDO;

public interface PageGridDAO {

	/**
	 * 获取方格块主表信息
	 * @param pageGridQTO
	 * @return
	 */
	public PageGridDO getGrid(PageGridQTO pageGridQTO);
	
	/**
	 * 更新方格块主表
	 * @param pageGridQTO
	 * @return
	 */
	public int updateGrid(PageGridQTO pageGridQTO);
	
	/**
	 * 删除方格块主表
	 * @param pageGridQTO
	 * @return
	 */
	public int removeGrid(PageGridQTO pageGridQTO);
	
	/**
	 * 新增方格块主表
	 * @param pageGridDO
	 * @return
	 */
	public Long addGrid(PageGridDO pageGridDO);
	 
}

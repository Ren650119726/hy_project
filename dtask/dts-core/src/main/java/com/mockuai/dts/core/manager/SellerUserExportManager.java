package com.mockuai.dts.core.manager;

import com.mockuai.dts.common.domain.SellerUserExportQTO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;

/**
 * 客户关系导出
 * @author hzmk
 *
 */
public interface SellerUserExportManager {
	
	public boolean exportSellerUserRelateData(SellerUserExportQTO query,ExportTaskDO exportTaskDO)throws DtsException;

}

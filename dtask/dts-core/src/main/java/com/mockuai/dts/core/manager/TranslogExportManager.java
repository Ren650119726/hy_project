package com.mockuai.dts.core.manager;

import com.mockuai.dts.common.domain.TranslogExportQTO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;

public interface TranslogExportManager {
	
	public boolean export(TranslogExportQTO query,String appkey,ExportTaskDO exportTaskDO)throws DtsException;

}

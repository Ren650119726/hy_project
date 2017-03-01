package com.mockuai.dts.core.manager;

import com.mockuai.dts.common.domain.OrderExportQTO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;

/**
 * 订单导出接口
 * @author hzmk
 *
 */
public interface OrderExportManager {
	
	public boolean exportOrders(OrderExportQTO query,ExportTaskDO exportTaskDO)throws DtsException;

}

package com.mockuai.dts.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.dts.common.domain.OrderExportQTO;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.OrderExportManager;

/**
 * 
 * @author hzmk
 *
 */
public class OrderExportTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(OrderExportTask.class);

    private OrderExportQTO query;

    private OrderExportManager orderExportManager;

    private ExportTaskDO exportTaskDO;

    private ExportTaskDAO exportTaskDAO;

    public OrderExportTask(OrderExportManager orderExportManager,ExportTaskDAO exportTaskDAO){
        this.orderExportManager = orderExportManager;
        this.exportTaskDAO = exportTaskDAO;
    }

    @Override
    public void run() {
        try {
        	orderExportManager.exportOrders(query, exportTaskDO);
        } catch (DtsException e) {
            log.error("export item error:", e);
            return;
        }
    }


    public ExportTaskDO getExportTaskDO() {
        return exportTaskDO;
    }

    public void setExportTaskDO(ExportTaskDO exportTaskDO) {
        this.exportTaskDO = exportTaskDO;
    }

    public ExportTaskDAO getExportTaskDAO() {
        return exportTaskDAO;
    }

    public void setExportTaskDAO(ExportTaskDAO exportTaskDAO) {
        this.exportTaskDAO = exportTaskDAO;
    }

	public OrderExportQTO getQuery() {
		return query;
	}

	public void setQuery(OrderExportQTO query) {
		this.query = query;
	}

	public OrderExportManager getOrderExportManager() {
		return orderExportManager;
	}

	public void setOrderExportManager(OrderExportManager orderExportManager) {
		this.orderExportManager = orderExportManager;
	}


}

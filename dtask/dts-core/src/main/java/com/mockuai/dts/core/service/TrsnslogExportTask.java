package com.mockuai.dts.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.dts.common.domain.TranslogExportQTO;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.TranslogExportManager;

public class TrsnslogExportTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(TrsnslogExportTask.class);

    private TranslogExportQTO query;


    private String appkey;
    
    private ExportTaskDO exportTaskDO;

    private ExportTaskDAO exportTaskDAO;
    
    
    TranslogExportManager translogExportManager;
    

    public TrsnslogExportTask(TranslogExportManager translogExportManager,ExportTaskDAO exportTaskDAO){
        this.translogExportManager = translogExportManager;
        this.exportTaskDAO = exportTaskDAO;
    }

    @Override
    public void run() {
        try {
        	translogExportManager.export(query,appkey, exportTaskDO);
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

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public TranslogExportQTO getQuery() {
		return query;
	}

	public void setQuery(TranslogExportQTO query) {
		this.query = query;
	}

	public TranslogExportManager getTranslogExportManager() {
		return translogExportManager;
	}

	public void setTranslogExportManager(TranslogExportManager translogExportManager) {
		this.translogExportManager = translogExportManager;
	}

    
	

}

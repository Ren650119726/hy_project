package com.mockuai.dts.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mockuai.dts.common.domain.SellerUserExportQTO;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.SellerUserExportManager;

/**
 * 
 * @author hzmk
 *
 */
public class SellerUserExportTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SellerUserExportTask.class);

    SellerUserExportQTO qto;

    private SellerUserExportManager sellerUserExportManagerImpl;

    private ExportTaskDO exportTaskDO;

    private ExportTaskDAO exportTaskDAO;

    public SellerUserExportTask(SellerUserExportManager sellerUserExportManagerImpl,ExportTaskDAO exportTaskDAO){
        this.sellerUserExportManagerImpl = sellerUserExportManagerImpl;
        this.exportTaskDAO = exportTaskDAO;
    }

    @Override
    public void run() {
        try {
        	sellerUserExportManagerImpl.exportSellerUserRelateData(qto, exportTaskDO);
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

	public SellerUserExportQTO getQto() {
		return qto;
	}

	public void setQto(SellerUserExportQTO qto) {
		this.qto = qto;
	}

	public SellerUserExportManager getSellerUserExportManagerImpl() {
		return sellerUserExportManagerImpl;
	}

	public void setSellerUserExportManagerImpl(SellerUserExportManager sellerUserExportManagerImpl) {
		this.sellerUserExportManagerImpl = sellerUserExportManagerImpl;
	}

	


}

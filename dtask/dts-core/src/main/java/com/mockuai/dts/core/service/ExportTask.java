package com.mockuai.dts.core.service;

import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.ItemExportManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by luliang on 15/7/1.
 */
public class ExportTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ExportTask.class);

    private ItemExportQTO itemExportQTO;

    private ItemExportManager itemExportManager;

    private ExportTaskDO exportTaskDO;

    private ExportTaskDAO exportTaskDAO;

    public ExportTask(ItemExportQTO itemExportQTO, ItemExportManager itemSearchManager){
        this.itemExportQTO = itemExportQTO;
        this.itemExportManager = itemSearchManager;
    }

    @Override
    public void run() {
        try {
            itemExportManager.exportItems(itemExportQTO, exportTaskDO);
        } catch (DtsException e) {
            log.error("export item error:", e);
            return;
        }
    }


    public ItemExportManager getItemExportManager() {
        return itemExportManager;
    }

    public void setItemExportManager(ItemExportManager itemExportManager) {
        this.itemExportManager = itemExportManager;
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


}

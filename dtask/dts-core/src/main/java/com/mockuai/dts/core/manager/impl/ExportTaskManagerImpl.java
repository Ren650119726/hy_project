package com.mockuai.dts.core.manager.impl;

import java.util.List;

import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.manager.ExportTaskManager;
import com.mockuai.dts.core.util.ExceptionUtil;

/**
 * Created by luliang on 15/7/23.
 */
public class ExportTaskManagerImpl implements ExportTaskManager {

    private ExportTaskDAO exportTaskDAO;

    @Override
    public List<ExportTaskDTO> queryExportTask(ExportTaskQTO exportTaskQTO) throws DtsException {
        List<ExportTaskDTO> exportTaskDTOs = null;
        try {
            exportTaskDTOs = exportTaskDAO.queryExportTask(exportTaskQTO);
        } catch (Throwable e) {
            throw  ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "query error");
        }
        return exportTaskDTOs;
    }

    @Override
    public Integer deleteExportTask(Long id, Long sellerId) throws DtsException {
        ExportTaskDO exportTaskDO = new ExportTaskDO();
        exportTaskDO.setSellerId(sellerId);
        exportTaskDO.setId(id);
        Integer count = null;
        try {
            count =  exportTaskDAO.deleteExportTask(exportTaskDO);
        } catch (Throwable e) {
            throw  ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete error");
        }
        return count;
    }

    public ExportTaskDAO getExportTaskDAO() {
        return exportTaskDAO;
    }

    public void setExportTaskDAO(ExportTaskDAO exportTaskDAO) {
        this.exportTaskDAO = exportTaskDAO;
    }

}

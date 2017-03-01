package com.mockuai.dts.core.dao;

import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;

import java.util.List;

/**
 * Created by luliang on 15/7/2.
 */
public interface ExportTaskDAO {

    public Long addExportTask(ExportTaskDO exportTaskDO) throws DtsException;

    public int deleteExportTask(ExportTaskDO exportTaskDO) throws DtsException;

    public List<ExportTaskDTO> queryExportTask(ExportTaskQTO exportTaskQTO) throws DtsException;

    public int updateTask(ExportTaskDO exportTaskDO) throws DtsException;
}

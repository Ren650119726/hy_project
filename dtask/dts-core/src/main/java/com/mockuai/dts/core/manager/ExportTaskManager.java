package com.mockuai.dts.core.manager;

import com.mockuai.dts.common.api.DtsService;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.exception.DtsException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luliang on 15/7/23.
 */
public interface ExportTaskManager {

    public List<ExportTaskDTO> queryExportTask(ExportTaskQTO exportTaskQTO) throws DtsException;

    public Integer deleteExportTask(Long id, Long sellerId) throws DtsException;
}

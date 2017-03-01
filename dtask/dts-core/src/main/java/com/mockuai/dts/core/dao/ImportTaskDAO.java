package com.mockuai.dts.core.dao;

import com.mockuai.dts.core.domain.ImportTaskDO;
import com.mockuai.dts.core.exception.DtsException;

/**
 * Created by yeliming on 16/2/9.
 */
public interface ImportTaskDAO {
    Long addImportTask(ImportTaskDO importTaskDO) throws DtsException;

    Integer updateImportTask(ImportTaskDO importTaskDO) throws DtsException;
}

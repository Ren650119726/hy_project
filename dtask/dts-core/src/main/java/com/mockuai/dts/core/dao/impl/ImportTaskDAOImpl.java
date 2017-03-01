package com.mockuai.dts.core.dao.impl;

import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.core.dao.ImportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.domain.ImportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by yeliming on 16/2/9.
 */
public class ImportTaskDAOImpl extends SqlMapClientDaoSupport implements ImportTaskDAO {
    private static final Logger log = LoggerFactory.getLogger(ImportTaskDAOImpl.class);

    @Override
    public Long addImportTask(ImportTaskDO importTaskDO) throws DtsException {
        Long insertId = null;
        try {
            ExportTaskDO exportTaskDO = new ExportTaskDO();
            BeanUtils.copyProperties(importTaskDO, exportTaskDO);
            insertId = (Long) getSqlMapClientTemplate().insert("dtask.insertExportTask", exportTaskDO);
        } catch (Throwable e) {
            log.error("insertExportTask error");
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_INSERT, "添加导入任务失败");
        }
        return insertId;
    }

    @Override
    public Integer updateImportTask(ImportTaskDO importTaskDO) throws DtsException {
        Integer count = null;

        try {
            ExportTaskDO exportTaskDO = new ExportTaskDO();
            BeanUtils.copyProperties(importTaskDO, exportTaskDO);
            count = getSqlMapClientTemplate().update("dtask.updateTask", exportTaskDO);
        } catch (Throwable e) {
            log.error("updateTaskProcess error");
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "更新导入任务失败");
        }
        return count;
    }
}

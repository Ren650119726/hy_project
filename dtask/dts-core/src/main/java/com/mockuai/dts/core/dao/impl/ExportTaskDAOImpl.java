package com.mockuai.dts.core.dao.impl;

import com.mockuai.dts.common.constant.ResponseCode;
import com.mockuai.dts.common.domain.ExportTaskDTO;
import com.mockuai.dts.common.domain.ExportTaskQTO;
import com.mockuai.dts.common.domain.ItemExportQTO;
import com.mockuai.dts.common.util.TimeUtil;
import com.mockuai.dts.core.dao.ExportTaskDAO;
import com.mockuai.dts.core.domain.ExportTaskDO;
import com.mockuai.dts.core.exception.DtsException;
import com.mockuai.dts.core.service.action.impl.ExportOrderAction;
import com.mockuai.dts.core.util.ExceptionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/7/2.
 */
public class ExportTaskDAOImpl extends SqlMapClientDaoSupport implements ExportTaskDAO {

	private static final Logger log = LoggerFactory.getLogger(ExportTaskDAOImpl.class);
	
    @Override
    public Long addExportTask(ExportTaskDO exportTaskDO) throws DtsException {
        Long insertId = null;
        try {

            insertId = (Long)getSqlMapClientTemplate().insert("dtask.insertExportTask", exportTaskDO);
        } catch (Throwable e) {
        	log.error("insertExportTask error",e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_INSERT, "添加导出任务出错");
        }
        return insertId;
    }

    @Override
    public int deleteExportTask(ExportTaskDO exportTaskDO) throws DtsException {
        Integer deleteCount = null;

        try {
            deleteCount = getSqlMapClientTemplate().delete("dtask.deleteTask", exportTaskDO);
        } catch (Throwable e) {
            log.error("delete export task failed, errMsg:{}",e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "删除导出任务出错");
        }
        return deleteCount;
    }

    @Override
    public List<ExportTaskDTO> queryExportTask(ExportTaskQTO exportTaskQTO) throws DtsException {

        if (null != exportTaskQTO.getNeedPaging() && exportTaskQTO.getNeedPaging().booleanValue()) {
            Integer totalCount = (Integer) getSqlMapClientTemplate().queryForObject("dtask.countExportTask", exportTaskQTO);// 总记录数
            exportTaskQTO.setTotalCount(totalCount);
            if (totalCount == 0) {
                return new ArrayList<ExportTaskDTO>();
            } else {
                exportTaskQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }

        List<ExportTaskDTO> exportTaskDTOs = new ArrayList<ExportTaskDTO>();
        List<ExportTaskDO> exportTaskDOs = null;
        try {
            exportTaskDOs = getSqlMapClientTemplate().queryForList("dtask.queryExportTask", exportTaskQTO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_QUERY, "queryExportTask error");
        }

        for(ExportTaskDO exportTaskDO: exportTaskDOs) {
            ExportTaskDTO exportTaskDTO = new ExportTaskDTO();
            BeanUtils.copyProperties(exportTaskDO, exportTaskDTO);
            exportTaskDTO.setCreateTime(TimeUtil.formatTimeStamp(exportTaskDO.getGmtCreated()));
            exportTaskDTOs.add(exportTaskDTO);
        }
        return exportTaskDTOs;
    }

    @Override
    public int updateTask(ExportTaskDO exportTaskDO) throws DtsException{
        Integer count = null;

        try {
            count = getSqlMapClientTemplate().update("dtask.updateTask", exportTaskDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "updateTaskProcess error");
        }
        return count;
    }
}

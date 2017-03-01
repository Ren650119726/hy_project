package com.mockuai.deliverycenter.core.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mockuai.deliverycenter.common.constant.DeliveryConstant;
import com.mockuai.deliverycenter.common.constant.RetCodeEnum;
import com.mockuai.deliverycenter.common.qto.BaseQTO;
import com.mockuai.deliverycenter.common.qto.Page;
import com.mockuai.deliverycenter.core.dao.BaseDao;
import com.mockuai.deliverycenter.core.domain.BaseDo;
import com.mockuai.deliverycenter.core.exception.DeliveryException;

public abstract class BaseManager {
	private static final Log log = LogFactory.getLog(BaseManager.class);
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<BaseDo> query(BaseQTO qto) throws DeliveryException {
		//TODO 分页参数待重构
		if (qto.getPageNo() == null) {
			qto.setPageNo(0);
		}
		if (qto.getPageNo() > DeliveryConstant.MAX_PAGE_NO) {
			qto.setPageNo(DeliveryConstant.MAX_PAGE_NO);
		}
		if (qto.getPageSize() == null) {
			qto.setPageSize(DeliveryConstant.MAX_PAGE_SIZE);
		}
		if (qto.getPageSize() > Long.valueOf(DeliveryConstant.MAX_PAGE_SIZE)) {
			qto.setPageSize(DeliveryConstant.MAX_PAGE_SIZE);
		}
		String listStatementName = getQueryStatement(qto.getClass(), "query");
		String countStatementName = getCountStatement(qto.getClass(),
				"queryCount");
		Long totalCount = (Long) getBaseDao().queryForObject(
				countStatementName, qto);
		qto.setTotalCount(totalCount);
		Page page = new Page();
		page.initPageParameter(totalCount, qto.getPageNo().toString(),
				qto.getPageSize());
		qto.setStartRow(page.getStartRow());
		return getBaseDao().queryForList(listStatementName, qto);
	}

	private String getQueryStatement(Class qtoClass, String curdName) {
		String className = qtoClass.getName()
				.substring(qtoClass.getName().lastIndexOf(".") + 1)
				.replace("QTO", "");
		return className.toUpperCase() + "." + curdName + className;
	}

	private String getCountStatement(Class qtoClass, String curdName) {
		String className = qtoClass.getName()
				.substring(qtoClass.getName().lastIndexOf(".") + 1)
				.replace("QTO", "");
		return className.toUpperCase() + "." + curdName;
	}
}

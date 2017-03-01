package com.mockuai.deliverycenter.core.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.deliverycenter.core.domain.BaseDo;

public class BaseDao extends SqlMapClientDaoSupport {

	public int delete(BaseDo bo) {
		return delete(getStatementName(bo.getClass(), "delete"), bo);
	}

	public int delete(String statementName, BaseDo bo) {
		return getSqlMapClientTemplate().delete(statementName, bo);
	}

	public BaseDo insert(BaseDo bo) {
		return insert(getStatementName(bo.getClass(), "add"), bo);
	}

	public BaseDo insert(String statementName, BaseDo bo) {
		Object result = getSqlMapClientTemplate().insert(statementName, bo);
		if (result == null) {
			return bo;
		} else {
			return get(bo);
		}
	}

	public int update(BaseDo bo) {
		return update(getStatementName(bo.getClass(), "update"), bo);
	}

	public int update(String statementName, BaseDo bo) {
		return getSqlMapClientTemplate().update(statementName, bo);
	}

	public BaseDo get(BaseDo bo) {
		return (BaseDo) getSqlMapClientTemplate().queryForObject(
				getStatementName(bo.getClass(), "get"), bo);
	}

	private String getStatementName(Class classT, String crudName) {
		String className = classT.getName().substring(
				classT.getName().lastIndexOf(".") + 1);
		return className.toUpperCase() + "." + crudName + className;
	}

	public Object queryForObject(String statementName, Object obj) {
		return getSqlMapClientTemplate().queryForObject(statementName, obj);
	}

	public List queryForList(String statementName, Object obj) {
		return getSqlMapClientTemplate().queryForList(statementName, obj);
	}

}

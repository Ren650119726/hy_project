package com.mockuai.imagecenter.core.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * Created by duke on 16/5/12.
 */
public abstract class BaseDAO extends SqlMapClientDaoSupport {
    // Default constructor
    public BaseDAO() {
    }

    @Autowired
    public BaseDAO(SqlMapClient sqlMapClient) {
        this.setSqlMapClient(sqlMapClient);
    }
}

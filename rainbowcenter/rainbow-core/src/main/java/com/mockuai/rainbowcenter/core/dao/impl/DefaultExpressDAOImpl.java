package com.mockuai.rainbowcenter.core.dao.impl;

import com.mockuai.rainbowcenter.core.dao.DefaultExpressDAO;
import com.mockuai.rainbowcenter.core.domain.DefaultExpressDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by lizg on 2016/9/6.
 */
@Repository
public class DefaultExpressDAOImpl extends SqlMapClientDaoSupport implements DefaultExpressDAO {
    @Override
    public DefaultExpressDO getDefaultExpress() {
        DefaultExpressDO defaultExpressDO = (DefaultExpressDO) getSqlMapClientTemplate().queryForObject("defaultExpress.getDefaultExpress");
        return defaultExpressDO;
    }

}

package com.mockuai.rainbowcenter.core.dao.impl;

import com.mockuai.rainbowcenter.core.dao.DuibaConfigurationDAO;
import com.mockuai.rainbowcenter.core.domain.DuibaConfigurationDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Created by lizg on 2016/9/19.
 */

@Repository
public class DuibaConfigurationDAOImpl extends SqlMapClientDaoSupport implements DuibaConfigurationDAO {

    @Override
    public DuibaConfigurationDO getConfiguration() {
        DuibaConfigurationDO duibaConfigurationDO = null;
        try {
            duibaConfigurationDO =
                    (DuibaConfigurationDO) getSqlMapClientTemplate().queryForObject("duiba_configuration.getConfiguration");
            return duibaConfigurationDO;
        } catch (DataAccessException e) {
            e.getMessage();
        }
        return duibaConfigurationDO;
    }
}

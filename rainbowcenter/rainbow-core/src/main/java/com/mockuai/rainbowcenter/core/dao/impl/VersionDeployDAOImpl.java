package com.mockuai.rainbowcenter.core.dao.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.core.dao.VersionDeployDAO;
import com.mockuai.rainbowcenter.core.domain.VersionDeployDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lizg on 2016/12/20.
 */

@Repository
public class VersionDeployDAOImpl extends SqlMapClientDaoSupport implements VersionDeployDAO {

    @Override
    public List<VersionDeployDO> getVersionDeploy() {

        try {
            return this.getSqlMapClientTemplate().queryForList("versionDeploy.selectVersionDeploy");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

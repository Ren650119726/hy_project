package com.mockuai.rainbowcenter.core.dao.impl;

import com.mockuai.rainbowcenter.common.qto.SysConfigQTO;
import com.mockuai.rainbowcenter.core.dao.SysConfigDAO;
import com.mockuai.rainbowcenter.core.domain.SysConfigDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by yeliming on 16/3/22.
 */
public class SysConfigDAOImpl extends SqlMapClientDaoSupport implements SysConfigDAO {
    @Override
    public SysConfigDO getSysConfigByValue(SysConfigQTO sysConfigQTO) {
        return (SysConfigDO) this.getSqlMapClientTemplate().queryForObject("sysConfig.getSysConfigByValue", sysConfigQTO);
    }

    @Override
    public List<SysConfigDO> querySysConfig(SysConfigQTO sysConfigQTO) {
        return this.getSqlMapClientTemplate().queryForList("sysConfig.query", sysConfigQTO);
    }

    @Override
    public List<SysConfigDO> queryConfigOfAccount(SysConfigQTO sysConfigQTO) {
        return this.getSqlMapClientTemplate().queryForList("sysConfig.queryConfigOfAccount", sysConfigQTO);
    }
}

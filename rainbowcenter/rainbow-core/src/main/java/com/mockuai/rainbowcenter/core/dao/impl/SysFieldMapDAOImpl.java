package com.mockuai.rainbowcenter.core.dao.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.common.qto.SysFieldMapQTO;
import com.mockuai.rainbowcenter.core.dao.SysFieldMapDAO;
import com.mockuai.rainbowcenter.core.domain.SysFieldMapDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by yeliming on 16/3/16.
 */
public class SysFieldMapDAOImpl extends SqlMapClientDaoSupport implements SysFieldMapDAO {
    private static final Logger log = LoggerFactory.getLogger(SysFieldMapDAOImpl.class);

    @Override
    public Long addSysFieldMap(SysFieldMapDO sysFieldMapDO) {
        try {
            return (Long) this.getSqlMapClientTemplate().insert("sysFieldMap.insert", sysFieldMapDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("addSysFieldMap exception, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            return null;
        }
    }

    @Override
    public int updateSysFieldMapByOutValue(SysFieldMapDO sysFieldMapDO) {
        try {
            return this.getSqlMapClientTemplate().update("sysFieldMap.updateByOutValue", sysFieldMapDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateSysFieldMapByOutValue exception, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            return -1;
        }
    }

    @Override
    public int updateSysFieldMapByValue(SysFieldMapDO sysFieldMapDO) {
        try {
            return this.getSqlMapClientTemplate().update("sysFieldMap.updateByValue", sysFieldMapDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateSysFieldMapByValue exception, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            return -1;
        }
    }


    @Override
    public SysFieldMapDO getSysFieldMap(SysFieldMapQTO sysFieldMapQTO) {
        try {
            return (SysFieldMapDO) this.getSqlMapClientTemplate().queryForObject("sysFieldMap.getSysFieldMap", sysFieldMapQTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getSysFieldMap exception, sysFieldMapQTO : {}", JSON.toJSONString(sysFieldMapQTO));
            return null;
        }
    }

    @Override
    public List<SysFieldMapDO> querySysFieldMap(SysFieldMapQTO sysFieldMapQTO) {
        try {
            return this.getSqlMapClientTemplate().queryForList("sysFieldMap.querySysFieldMap", sysFieldMapQTO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("querySysFieldMap exception, sysFieldMapQTO : {}", JSON.toJSONString(sysFieldMapQTO));
            return null;
        }
    }

    @Override
    public int updateRemoveByOutValue(SysFieldMapDO sysFieldMapDO) {
        try {
            return this.getSqlMapClientTemplate().update("sysFieldMap.updateRemoveByOutValue", sysFieldMapDO);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateSysFieldMapByValue exception, sysFieldMapDO: {}", JSON.toJSONString(sysFieldMapDO));
            return -1;
        }
    }
}

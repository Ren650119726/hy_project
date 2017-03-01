package com.mockuai.appcenter.core.dao.impl;

import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.core.dao.BizInfoDAO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.exception.DAOException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class BizInfoDAOImpl extends SqlMapClientDaoSupport implements BizInfoDAO {
    @Override
    public long addBizInfo(BizInfoDO bizInfoDO) throws DAOException {
        long id = (Long)this.getSqlMapClientTemplate().insert("biz_info.addBizInfo",bizInfoDO);
        return id;
    }

    @Override
    public BizInfoDO getBizInfo(String bizCode) throws DAOException {
        return (BizInfoDO)this.getSqlMapClientTemplate().queryForObject("biz_info.getBizInfo", bizCode);
    }

    @Override
    public int updateBizInfo(BizInfoDO bizInfoDO) throws DAOException {
        return this.getSqlMapClientTemplate().update("biz_info.updateBizInfo", bizInfoDO);
    }

    @Override
    public List<BizInfoDO> queryBizInfo(BizInfoQTO bizInfoQTO) throws DAOException {
        return (List<BizInfoDO>)this.getSqlMapClientTemplate().queryForList("biz_info.queryBizInfo", bizInfoQTO);
    }

    @Override
    public long queryBizInfoCount(BizInfoQTO bizInfoQTO) throws DAOException {
        return (Long)this.getSqlMapClientTemplate().queryForObject("biz_info.queryBizInfoCount", bizInfoQTO);
    }

    @Override
    public int deleteBizInfo(String bizCode) throws DAOException {
        BizInfoDO bizInfoDO = new BizInfoDO();
        bizInfoDO.setBizCode(bizCode);
        bizInfoDO.setDeleteVersion(System.currentTimeMillis());
        return this.getSqlMapClientTemplate().update("biz_info.deleteBizInfo", bizInfoDO);

    }
}

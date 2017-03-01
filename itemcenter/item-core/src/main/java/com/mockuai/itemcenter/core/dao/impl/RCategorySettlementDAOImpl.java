package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.RCategorySettlementQTO;
import com.mockuai.itemcenter.core.dao.RCategorySettlementDAO;
import com.mockuai.itemcenter.core.domain.RCategorySettlementDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RCategorySettlementDAOImpl extends SqlMapClientDaoSupport implements RCategorySettlementDAO {


    @Override
    public List<RCategorySettlementDO> queryRCategorySettlement(RCategorySettlementQTO query) {
        return getSqlMapClientTemplate().queryForList("RCategorySettlement.queryRCategorySettlement",query);
    }

    @Override
    public Long addRCategorySettlement(RCategorySettlementDO rCategorySettlementDO) {
        return (Long) getSqlMapClientTemplate().insert("RCategorySettlement.addRCategorySettlement",rCategorySettlementDO);
    }

    @Override
    public Long deleteRCategorySettlementByConfig(Long id, String bizCode) {

        RCategorySettlementDO rCategorySettlementDO = new RCategorySettlementDO();
        rCategorySettlementDO.setSettlementId(id);
        rCategorySettlementDO.setBizCode(bizCode);
        return Long.valueOf(getSqlMapClientTemplate().update("RCategorySettlement.deleteRCategorySettlementByConfig",rCategorySettlementDO));
    }
}
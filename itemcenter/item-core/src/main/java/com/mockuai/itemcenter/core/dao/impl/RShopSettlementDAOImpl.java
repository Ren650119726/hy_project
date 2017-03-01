package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.RShopSettlementQTO;
import com.mockuai.itemcenter.core.dao.RShopSettlementDAO;
import com.mockuai.itemcenter.core.domain.RShopSettlementDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RShopSettlementDAOImpl extends SqlMapClientDaoSupport implements RShopSettlementDAO {


    @Override
    public List<RShopSettlementDO> queryRShopSettlement(RShopSettlementQTO query) {
        return getSqlMapClientTemplate().queryForList("RShopSettlement.queryRShopSettlement",query);
    }

    @Override
    public Long addRShopSettlement(RShopSettlementDO rShopSettlementDO) {
        return (Long) getSqlMapClientTemplate().insert("RShopSettlement.addRShopSettlement",rShopSettlementDO);
    }

    @Override
    public Long deleteRShopSettlementByConfig(Long id, String bizCode) {
        RShopSettlementDO rShopSettlementDO = new RShopSettlementDO();
        rShopSettlementDO.setSettlementId(id);
        rShopSettlementDO.setBizCode(bizCode);
        return Long.valueOf(getSqlMapClientTemplate().update("RShopSettlement.deleteRShopSettlementByConfig",rShopSettlementDO));
    }
}
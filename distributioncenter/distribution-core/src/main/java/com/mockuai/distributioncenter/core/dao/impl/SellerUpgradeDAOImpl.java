package com.mockuai.distributioncenter.core.dao.impl;

import com.mockuai.distributioncenter.common.domain.qto.SellerUpgradeQTO;
import com.mockuai.distributioncenter.core.dao.SellerUpgradeDAO;
import com.mockuai.distributioncenter.core.domain.SellerUpgradeDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/18.
 */
@Service
public class SellerUpgradeDAOImpl extends SqlMapClientDaoSupport implements SellerUpgradeDAO {
    @Override
    public List<SellerUpgradeDO> querySellerUpgrade(SellerUpgradeQTO sellerUpgradeQTO) {
        return this.getSqlMapClientTemplate().queryForList("seller_upgrade.query_seller_upgrade", sellerUpgradeQTO);
    }

    @Override
    public int agreeSellerUpgrade(Long id, String reason) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id + "");
        map.put("reason", reason);
        return this.getSqlMapClientTemplate().update("seller_upgrade.agree_seller_upgrade", map);
    }

    @Override
    public int rejectSellerUpgrade(Long id, String reason) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id + "");
        map.put("reason", reason);
        return this.getSqlMapClientTemplate().update("seller_upgrade.reject_seller_upgrade", map);
    }

    @Override
    public Long addSellerUpgrade(SellerUpgradeDO sellerUpgradeDO) {
        return (Long) this.getSqlMapClientTemplate().insert("seller_upgrade.add_seller_upgrade",sellerUpgradeDO);
    }
}

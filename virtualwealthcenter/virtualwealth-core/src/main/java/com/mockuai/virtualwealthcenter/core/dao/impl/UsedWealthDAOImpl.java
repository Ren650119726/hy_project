package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.core.dao.UsedWealthDAO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 6/19/15.
 */
public class UsedWealthDAOImpl extends SqlMapClientDaoSupport implements UsedWealthDAO {
    @Override
    public long addUsedWealth(UsedWealthDO usedWealthDO) {
        return ((Long) getSqlMapClientTemplate().insert("used_wealth.addUsedWealth", usedWealthDO)).longValue();
    }

    @Override
    public int updateWealthStatus(List<Long> idList, Long userId, Integer fromStatus, Integer toStatus) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("idList", idList);
        params.put("fromStatus", fromStatus);
        params.put("toStatus", toStatus);
        return getSqlMapClientTemplate().update("used_wealth.updateWealthStatus", params);
    }

    @Override
    public List<UsedWealthDO> queryUsedWealth(UsedWealthQTO usedWealthQTO) {
        usedWealthQTO.setTotalCount((Integer)
                getSqlMapClientTemplate().queryForObject("used_wealth.countOfQueryUsedWealth", usedWealthQTO));
        return getSqlMapClientTemplate().queryForList("used_wealth.queryUsedWealth", usedWealthQTO);
    }

    @Override
    public UsedWealthDO getUsedWealthByWealthAccount(Long wealthAccountId, Long orderId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wealthAccountId", wealthAccountId);
        params.put("orderId", orderId);
        return (UsedWealthDO) getSqlMapClientTemplate().queryForObject("used_wealth.getUsedWealthByWealthAccount", params);
    }

    @Override
    public List<UsedWealthDO> queryUsedWealthByParentId(Long parentId) {
        return getSqlMapClientTemplate().queryForList("used_wealth.queryUsedWealthByParentId", parentId);
    }

	@Override
	public List<UsedWealthDO> findCustomerUsedPageList(
			UsedWealthQTO usedWealthQTO) {
		 usedWealthQTO.setTotalCount((Integer)
	                getSqlMapClientTemplate().queryForObject("used_wealth.findCustomerUsedCount", usedWealthQTO));
	    return getSqlMapClientTemplate().queryForList("used_wealth.findCustomerUsedPageList", usedWealthQTO);
	}
}
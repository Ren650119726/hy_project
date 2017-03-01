package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemDAO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 */
public class WithdrawalItemDAOImpl extends SqlMapClientDaoSupport implements WithdrawalsItemDAO {
    @Override
    public List<WithdrawalsItemDO> query(WithdrawalsItemQTO withdrawalsItemQTO) {
        withdrawalsItemQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("withdrawals_item.countOfQuery",withdrawalsItemQTO));
        return getSqlMapClientTemplate().queryForList("withdrawals_item.query", withdrawalsItemQTO);
    }

    @Override
    public List<WithdrawalsItemDTO> findList(WithdrawalsItemQTO item) {
        return  (List<WithdrawalsItemDTO>)  getSqlMapClientTemplate().queryForList("withdrawals_item.findList", item);
    }

   public int count(WithdrawalsItemQTO item){
       return (int) getSqlMapClientTemplate().queryForObject("withdrawals_item.count", item);
   }

    @Override
    public WithdrawalsItemDO getWithdrawalItem(String withdrawalsNumber) {
        return (WithdrawalsItemDO) getSqlMapClientTemplate().queryForObject("withdrawals_item.getWithdrawalItem", withdrawalsNumber);
    }

    @Override
    public WithdrawalsItemDO getWithdrawalItemSimple(String withdrawalsNumber) {
        return (WithdrawalsItemDO) getSqlMapClientTemplate().queryForObject("withdrawals_item.getWithdrawalItemSimple", withdrawalsNumber);
    }

    @Override
	public Integer updateRecord(WithdrawalsItemQTO withdrawalsItemQTO) throws SQLException {
	    return getSqlMapClient().update("withdrawals_item.updateRecord",withdrawalsItemQTO);

	}
}

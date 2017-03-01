package com.mockuai.virtualwealthcenter.core.dao.impl;

import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsConfigDAO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemDAO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 * 提现配置
 */
public class WithdrawalConfigDAOImpl extends SqlMapClientDaoSupport implements WithdrawalsConfigDAO {

    @Override
    public void save(WithdrawalsConfigQTO withdrawalsConfigQTO) throws SQLException {
         getSqlMapClient().insert("withdrawals_config.save",withdrawalsConfigQTO);
    }

    @Override
    public void update(WithdrawalsConfigQTO withdrawalsConfigQTO) throws SQLException {
        getSqlMapClient().update("withdrawals_config.update",withdrawalsConfigQTO);

    }

    public List<WithdrawalsConfigDTO> queryList(WithdrawalsConfigQTO withdrawalsConfigQTO )throws SQLException{
        return (List<WithdrawalsConfigDTO>) getSqlMapClient().queryForList("withdrawals_config.queryList",withdrawalsConfigQTO);

    }

}

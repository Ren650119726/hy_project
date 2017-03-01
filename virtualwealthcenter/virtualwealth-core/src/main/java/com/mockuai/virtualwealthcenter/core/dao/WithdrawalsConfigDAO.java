package com.mockuai.virtualwealthcenter.core.dao;

import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 * 提现配置
 */
public interface WithdrawalsConfigDAO {

    void save(WithdrawalsConfigQTO withdrawalsConfigQTO) throws SQLException;

    void update(WithdrawalsConfigQTO withdrawalsConfigQTO) throws  SQLException;


     List<WithdrawalsConfigDTO> queryList(WithdrawalsConfigQTO withdrawalsConfigQTO )throws SQLException ;


    }

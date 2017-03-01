package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsConfigDAO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemDAO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsConfigManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 */
public class WithdrawalsItemConfigImpl  implements WithdrawalsConfigManager{
    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawalsItemConfigImpl.class);


    @Autowired
    private WithdrawalsConfigDAO withdrawalsConfigDAO;



    @Override
    public void saveWithdrawalsConfig(WithdrawalsConfigQTO withdrawalsConfigQTO) throws VirtualWealthException {
        try {
            if(withdrawalsConfigQTO.getId() != null){
               withdrawalsConfigDAO.update(withdrawalsConfigQTO);
            }else{
                withdrawalsConfigDAO.save(withdrawalsConfigQTO);
            }
        } catch (SQLException e) {
            LOGGER.error("failed when  saveWithdrawalsConfig : {}", JsonUtil.toJson(withdrawalsConfigQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<WithdrawalsConfigDTO> queryList(WithdrawalsConfigQTO withdrawalsConfigQTO) throws VirtualWealthException {
        try {
            return  withdrawalsConfigDAO.queryList(withdrawalsConfigQTO);
        } catch (SQLException e) {
            LOGGER.error("failed when  saveWithdrawalsConfig : {}", JsonUtil.toJson(withdrawalsConfigQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }


}

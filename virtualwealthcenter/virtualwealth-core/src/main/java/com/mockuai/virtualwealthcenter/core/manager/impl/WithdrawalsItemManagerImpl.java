package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.WithdrawalsItemDAO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/18.
 */
public class WithdrawalsItemManagerImpl implements WithdrawalsItemManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(WithdrawalsItemManagerImpl.class);


    @Autowired
    private WithdrawalsItemDAO withdrawalsItemDAO;
    @Autowired
    private WealthAccountManager wealthAccountManager;

    @Override
    public WithdrawalsItemDTO getWithdrawalsItemDTO(String withdrawalsNumber) throws VirtualWealthException {
        try {
            WithdrawalsItemDO withdrawalsItemDO = withdrawalsItemDAO.getWithdrawalItemSimple(withdrawalsNumber);
            return ModelUtil.genWithdrawalsItemDTO(withdrawalsItemDO);
        }catch (Exception e){
            LOGGER.error("error to getWithdrawalsItemDTO, withdrawals number : {}", withdrawalsNumber, e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<WithdrawalsItemDTO> queryWithdrawalsItemDTO(WithdrawalsItemQTO withdrawalsItemQTO) throws VirtualWealthException {
        try {
            List<WithdrawalsItemDO> withdrawalsItemDOs = withdrawalsItemDAO.query(withdrawalsItemQTO);
            return ModelUtil.genWithdrawalsItemDTOList(withdrawalsItemDOs);
        } catch (Exception e) {
            LOGGER.error("error to queryWithdrawalsItemDTO, withdrawalsItemQTO : {}", JsonUtil.toJson(withdrawalsItemQTO),e);
            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    public List<WithdrawalsItemDTO> findList(WithdrawalsItemQTO item) throws VirtualWealthException{
        try {
            return withdrawalsItemDAO.findList(item);
        }catch (Exception e){
            LOGGER.error("failed when  findList : {}", JsonUtil.toJson(item), e);

            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public Integer updateRecord(WithdrawalsItemQTO item) throws VirtualWealthException {
        try {
            //打款
            if(item.getDotype() == 2 || item.getDotype() == 3  ){
                WithdrawalsItemDO withdrawalsItemDO = withdrawalsItemDAO.getWithdrawalItem(item.getWithdrawalsNumber());
                long userId =   withdrawalsItemDO.getUserId();
                long amount = withdrawalsItemDO.getWithdrawalsAmount();
                String bizCode =withdrawalsItemDO.getBizCode();
                //清零 冻结金额
                cleanFrozenAccount(userId,amount,bizCode);
                //拒绝 增加虚拟财富总数
                if(item.getDotype() == 2){
                   addAmount(userId,amount,bizCode);
                }
            }
            return withdrawalsItemDAO.updateRecord(item);
        } catch (SQLException e) {
            LOGGER.error("failed when  updateRecord : {}", JsonUtil.toJson(item), e);

            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    private WealthAccountDO getWealthAccount(Long userId,String bizCode) throws VirtualWealthException {
        WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
        wealthAccountQTO.setUserId(userId);
        wealthAccountQTO.setWealthType(1);
        return wealthAccountManager.getWealthAccount(userId,1,bizCode);
    }


    private  void cleanFrozenAccount(Long userId,Long account,String bizCode) throws VirtualWealthException {
        WealthAccountDO wealthAccountDO = getWealthAccount(userId, bizCode);
        //wealthAccountManager.increaseFrozenBalance(wealthAccountDO.getId(),0-account);
        //6月29日改成冻结金额为0元
        wealthAccountManager.increaseFrozenBalance(wealthAccountDO.getId(),0L);
    }

    private void addAmount(Long userId,Long account,String bizCode) throws VirtualWealthException {
        WealthAccountDO wealthAccountDO = getWealthAccount(userId, bizCode);
        wealthAccountManager.increaseAccountBalance(wealthAccountDO.getId(),userId,account);
    }


    @Override
    public int count(WithdrawalsItemQTO item) throws VirtualWealthException {
        try {
            return withdrawalsItemDAO.count(item);
        } catch (Exception e) {
            LOGGER.error("failed when  count : {}", JsonUtil.toJson(item), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }
}

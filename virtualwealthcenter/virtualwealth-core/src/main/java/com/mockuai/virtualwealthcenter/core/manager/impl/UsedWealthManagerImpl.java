package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.core.dao.UsedWealthDAO;
import com.mockuai.virtualwealthcenter.core.domain.UsedWealthDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.UsedWealthManager;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/19/15.
 */
public class UsedWealthManagerImpl implements UsedWealthManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsedWealthManagerImpl.class);

    @Resource
    private UsedWealthDAO usedWealthDAO;

    @Autowired
    private WealthAccountManager wealthAccountManager;

    @Override
    public void giveBackUsedWealth(List<UsedWealthDO> usedWealthDOs) throws VirtualWealthException {

        int opNum;
        for (UsedWealthDO usedWealthDO : usedWealthDOs) {

            try {
                addUsedWealth(usedWealthDO);

                //将取消的虚拟财富额度回补到虚拟账户中去
//                opNum = wealthAccountManager.increaseAccountBalance(usedWealthDO.getWealthAccountId(),
//                        usedWealthDO.getUserId(), usedWealthDO.getAmount());
//                if (opNum != 1) {
//                    LOGGER.error("error to increase the wealth account balance, wealthAccountId : {}, userId : {}, userAmount : {}",
//                            usedWealthDO.getWealthAccountId(), usedWealthDO.getUserId(), usedWealthDO.getAmount());
//                    throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION);
//                }
            } catch (Exception e) {
                LOGGER.error("failed when giveBackUsedWealth : {}", JsonUtil.toJson(usedWealthDO), e);
                throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
            }
        }
    }

    @Override
    public long addUsedWealth(UsedWealthDO usedWealthDO) throws VirtualWealthException {
        try {
            return this.usedWealthDAO.addUsedWealth(usedWealthDO);
        } catch (Exception e) {
            LOGGER.error("failed when adding usedWealth : {}", JsonUtil.toJson(usedWealthDO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public int updateWealthStatus(List<Long> idList, Long userId, Integer fromStatus, Integer toStatus) throws VirtualWealthException {
        try {
            return this.usedWealthDAO.updateWealthStatus(idList, userId, fromStatus, toStatus);
        } catch (Exception e) {
            LOGGER.error("failed when updating status of wealth, idList : {}, userId : {}, fromStatus : {}, toStatus : {}",
                    JsonUtil.toJson(idList), userId, fromStatus, toStatus, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<UsedWealthDO> queryUsedWealth(UsedWealthQTO usedWealthQTO) throws VirtualWealthException {
        try {
            return this.usedWealthDAO.queryUsedWealth(usedWealthQTO);
        } catch (Exception e) {
            LOGGER.error("failed when querying usedWealth, usedWealthQTO : {}", JsonUtil.toJson(usedWealthQTO), e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public UsedWealthDO getUsedWealthByWealthAccountId(Long wealthAccountId, Long orderId) throws VirtualWealthException {
        try {
            UsedWealthDO usedWealthDO = usedWealthDAO.getUsedWealthByWealthAccount(wealthAccountId, orderId);
            if (usedWealthDO == null) {
                LOGGER.error("the used wealth does not exist, wealthAccountId : {}, orderId : {}", wealthAccountId, orderId);
                throw new VirtualWealthException(ResponseCode.USED_WEALTH_NOT_EXISTS);
            }
            return usedWealthDO;
        } catch (VirtualWealthException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("failed when getUsedWealthByWealthAccountId, wealthAccountId : {}, orderId : {}", wealthAccountId, orderId, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<UsedWealthDO> queryUsedWealthByParentId(Long parentId) throws VirtualWealthException {
        try {
            return usedWealthDAO.queryUsedWealthByParentId(parentId);
        } catch (Exception e) {
            LOGGER.error("failed when queryUsedWealthByParentId, parentId : {}", parentId, e);
            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
        }
    }

    
    
	@Override
	public List<UsedWealthDO> findCustomerUsedPageList(
			UsedWealthQTO usedWealthQTO) throws VirtualWealthException {
		 try {
	         return usedWealthDAO.findCustomerUsedPageList(usedWealthQTO);
	        } catch (Exception e) {
	            LOGGER.error("failed when queryUsedWealthByParentId, parentId : {}", usedWealthQTO, e);
	            throw new VirtualWealthException(ResponseCode.DB_OP_ERROR);
	        }
	}
}
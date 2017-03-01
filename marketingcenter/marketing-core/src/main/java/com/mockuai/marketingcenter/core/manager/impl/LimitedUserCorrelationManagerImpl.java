package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedUserCorrelationDTO;
import com.mockuai.marketingcenter.core.dao.LimitedUserCorrelationDAO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedUserCorrelationManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huangsiqian on 2016/10/19.
 */
public class LimitedUserCorrelationManagerImpl implements LimitedUserCorrelationManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedUserCorrelationManagerImpl.class);
    @Autowired
    private LimitedUserCorrelationDAO limitedUserCorrelationDAO;

    @Override
    public Boolean addUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO) throws MarketingException {
        Long num = null;
        try {
            LOGGER.info("limited user add info :{}", JsonUtil.toJson(limitedUserCorrelationDO));
            num = (Long) limitedUserCorrelationDAO.addUserMsg(limitedUserCorrelationDO);
            LOGGER.info("NUM:{}",num);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "添加用户限购数量出错");
        }
        LOGGER.info("NUM2:{}",num);
        if (num < 0) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "添加用户限购数量失败");
        }
        return true;
    }

    @Override
    public Boolean updatePurchaseQuantity(LimitedUserCorrelationDO limitedUserCorrelationDO) throws MarketingException {
        Integer num = null;
        try {
            LOGGER.info("limitedUserCorrelationDO:{}",JsonUtil.toJson(limitedUserCorrelationDO));
            num = (Integer) limitedUserCorrelationDAO.updatePurchaseQuantity(limitedUserCorrelationDO);
            LOGGER.info("num:{},limitedUserCorrelationDO:{}",num,JsonUtil.toJson(limitedUserCorrelationDO));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "更新用户限购数量出错");
        }
        if (num < 1) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "更新用户限购数量失败");
        }
        return true;
    }
    @Override
    public Boolean orderCancelledgoods(LimitedUserCorrelationDO limitedUserCorrelationDO) throws MarketingException {
        Integer num = null;
        try {
            num = (Integer) limitedUserCorrelationDAO.orderCancelledgoods(limitedUserCorrelationDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "更新用户限购数量出错");
        }
        if (num < 1) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "更新用户限购数量失败");
        }
        return true;
    }

    @Override
    public LimitedUserCorrelationDO selectUserMsg(LimitedUserCorrelationDO limitedUserCorrelationDO) throws MarketingException {
        return (LimitedUserCorrelationDO)limitedUserCorrelationDAO.selectUserMsg(limitedUserCorrelationDO);
    }
}

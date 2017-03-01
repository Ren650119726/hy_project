package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.qto.MarketToolQTO;
import com.mockuai.marketingcenter.core.dao.MarketToolDAO;
import com.mockuai.marketingcenter.core.domain.MarketToolDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.MarketToolManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

public class MarketToolManagerImpl implements MarketToolManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketToolManagerImpl.class);

    @Resource
    private MarketToolDAO marketToolDAO;

    public Long addTool(MarketToolDO marketToolDO) throws MarketingException {

        try {
            marketToolDO.setStatus(0);
            marketToolDO.setDeleteMark(Integer.valueOf(0));

            return this.marketToolDAO.addTool(marketToolDO);
        } catch (Exception e) {
            LOGGER.error("failed when adding tool, tool : {}",
                    JsonUtil.toJson(marketToolDO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int updateTool(MarketToolDO marketToolDO)
            throws MarketingException {

        try {
            return this.marketToolDAO.updateTool(marketToolDO);
        } catch (Exception e) {
            LOGGER.error("failed when updating tool, tool : {}",
                    JsonUtil.toJson(marketToolDO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public List<MarketToolDO> queryTool(MarketToolQTO marketToolQTO)
            throws MarketingException {

        try {
            return this.marketToolDAO.queryTool(marketToolQTO);
        } catch (Exception e) {
            LOGGER.error("failed when query tool, marketToolQTO : {}",
                    JsonUtil.toJson(marketToolQTO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public int queryToolCount(MarketToolQTO marketToolQTO)
            throws MarketingException {

        try {
            return this.marketToolDAO.queryToolCount(marketToolQTO);
        } catch (Exception e) {
            LOGGER.error("failed when querying the count of tool, marketToolQTO : {} ",
                    JsonUtil.toJson(marketToolQTO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public MarketToolDO getTool(long id, String bizCode)
            throws MarketingException {

        try {
            return this.marketToolDAO.getTool(id, bizCode);
        } catch (Exception e) {
            LOGGER.error("failed when getting the tool, toolId : {}, bizCode : {}", id, bizCode, e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public MarketToolDO getTool(String toolCode) throws MarketingException {
        try {
            return this.marketToolDAO.getTool(toolCode);
        } catch (Exception e) {
            LOGGER.error("failed when getting the tool, toolCode : {}", toolCode);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }
}
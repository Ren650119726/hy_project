package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemBuyLimitQTO;
import com.mockuai.itemcenter.core.dao.ItemBuyLimitDAO;
import com.mockuai.itemcenter.core.domain.ItemBuyLimitDO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luliang on 15/7/17.
 */
@Service
public class ItemBuyLimitDAOImpl extends SqlMapClientDaoSupport implements ItemBuyLimitDAO {

    private static final Logger log = LoggerFactory.getLogger(ItemBuyLimitDAOImpl.class);

    @Override
    public Long addItemBuyLimit(ItemBuyLimitDO itemBuyLimitDO) throws ItemException {
        Long newInsertedId = 0L;
        try {
            newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemBuyLimit.addItemBuyLimit", itemBuyLimitDO);
        } catch (Throwable e) {
            log.error("params :{}, error :{}", JsonUtil.toJson(itemBuyLimitDO), e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemBuyLimit.addItemBuyLimit error");
        }
        return newInsertedId;
    }

    @Override
    public List<ItemBuyLimitDO> queryItemBuyLimit(Long itemId, Long sellerId) throws ItemException {
        ItemBuyLimitQTO qto = new ItemBuyLimitQTO();
        qto.setItemId(itemId);
        qto.setSellerId(sellerId);
        List<ItemBuyLimitDO> list = null;
        try {
            list = getSqlMapClientTemplate().queryForList("ItemBuyLimit.queryItemBuyLimit", qto);
        } catch (Throwable e) {
            log.error("itemId:{}, sellerId:{}", itemId, sellerId, e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemBuyLimit.queryItemBuyLimit error");
        }
        return list;
    }

    @Override
    public List<ItemBuyLimitDO> queryItemBuyLimit(List<Long> itemIdList, Long sellerId) throws ItemException {
        ItemBuyLimitQTO qto = new ItemBuyLimitQTO();
        qto.setItemIdList(itemIdList);
        qto.setSellerId(sellerId);
        List<ItemBuyLimitDO> list = null;
        try {
            list = getSqlMapClientTemplate().queryForList("ItemBuyLimit.queryItemBuyLimit", qto);
        } catch (Throwable e) {
            log.error("itemIdList:{}, sellerId:{}", JsonUtil.toJson(itemIdList), sellerId, e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemBuyLimit.queryItemBuyLimit error");
        }
        return list;
    }

    @Override
    public int deleteItemBuyLimit(ItemBuyLimitDO query) throws ItemException {

        try {
            int rows = getSqlMapClientTemplate().update("ItemBuyLimit.deleteItemBuyLimit", query);
            return rows;
        } catch (Throwable e) {
            log.error("params :{}, error :{}", JsonUtil.toJson(query), e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemBuyLimit.deleteItemBuyLimit error");
        }
    }

    @Override
    public Long trashItemBuyLimit(ItemBuyLimitDO query) throws ItemException {
        try {
            long rows = getSqlMapClientTemplate().update("ItemBuyLimit.trashItemBuyLimit", query);
            return rows;
        } catch (Throwable e) {
            log.error("params :{}, error :{}", JsonUtil.toJson(query), e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemBuyLimit.trashItemBuyLimit error");
        }
    }

    @Override
    public Long recoveryItemBuyLimit(ItemBuyLimitDO query) throws ItemException {
        try {
            long rows = getSqlMapClientTemplate().update("ItemBuyLimit.recoveryItemBuyLimit", query);
            return rows;
        } catch (Throwable e) {
            log.error("params :{}, error :{}", JsonUtil.toJson(query), e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemBuyLimit.recoveryItemBuyLimit error");
        }
    }

    @Override
    public Long emptyRecycleBin(ItemBuyLimitDO query) throws ItemException {
        try {
            long rows = getSqlMapClientTemplate().update("ItemBuyLimit.emptyRecycleBin", query);
            return rows;
        } catch (Throwable e) {
            log.error("params :{}, error :{}", JsonUtil.toJson(query), e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemBuyLimit.emptyRecycleBin error");
        }
    }
}

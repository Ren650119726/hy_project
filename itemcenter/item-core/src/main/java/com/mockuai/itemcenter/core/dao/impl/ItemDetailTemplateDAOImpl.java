package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.itemcenter.core.dao.ItemDetailTemplateDAO;
import com.mockuai.itemcenter.core.domain.ItemDetailTemplateDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by luliang on 15/7/21.
 */
@Service
public class ItemDetailTemplateDAOImpl extends SqlMapClientDaoSupport implements ItemDetailTemplateDAO {

    @Override
    public Long addItemDetailTemplate(ItemDetailTemplateDO itemDetailTemplateDO) throws ItemException {
        Long newInsertedId = 0L;
        try {
            newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemDetailTemplate.addItemDetailTemplate", itemDetailTemplateDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemDetailTemplate.addItemDetailTemplate error");
        }
        return newInsertedId;
    }

    @Override
    public ItemDetailTemplateDO getItemDetailTemplate(Long id,  Long sellerId) throws ItemException {
        ItemDetailTemplateDO itemDetailTemplateDO = new ItemDetailTemplateDO();
        itemDetailTemplateDO.setId(id);
        itemDetailTemplateDO.setSellerId(sellerId);
        itemDetailTemplateDO.setDeleteMark(0);
        ItemDetailTemplateDO itemDetailTemplateDOResult = null;
        try {
            itemDetailTemplateDOResult = (ItemDetailTemplateDO) getSqlMapClientTemplate()
                    .queryForObject("ItemDetailTemplate.getItemDetailTemplate", itemDetailTemplateDO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemDetailTemplate.getItemDetailTemplate error");
        }
        return itemDetailTemplateDOResult;
    }

    @Override
    public List<ItemDetailTemplateDO> queryItemDetailTemplate(ItemDetailTemplateQTO itemDetailTemplateQTO) throws ItemException {
        List<ItemDetailTemplateDO> itemDetailTemplateDOList = null;
        try {
            itemDetailTemplateDOList = getSqlMapClientTemplate()
                    .queryForList("ItemDetailTemplate.queryItemDetailTemplate", itemDetailTemplateQTO);
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "ItemDetailTemplate.queryItemDetailTemplate error");
        }
        return itemDetailTemplateDOList;
    }

    @Override
    public Integer updateItemDetailTemplate(ItemDetailTemplateDO itemDetailTemplateDO) throws ItemException {
        try {
            int rows = getSqlMapClientTemplate().update("ItemDetailTemplate.updateItemDetailTemplate", itemDetailTemplateDO);
            return rows;
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemDetailTemplate.updateItemDetailTemplate error");
        }
    }

    @Override
    public Integer deleteItemDetailTemplate(ItemDetailTemplateDO itemDetailTemplateDO) throws ItemException {
        try {
            int rows = getSqlMapClientTemplate().update("ItemDetailTemplate.deleteItemDetailTemplate", itemDetailTemplateDO);
            return rows;
        } catch (Throwable e) {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "ItemDetailTemplate.deleteItemDetailTemplate error");
        }
    }
}

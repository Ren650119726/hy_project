package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.FreightAreaTemplateQTO;
import com.mockuai.itemcenter.core.dao.FreightAreaTemplateDAO;
import com.mockuai.itemcenter.core.domain.FreightAreaTemplateDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yindingyu on 15/9/22.
 */
@Service
public class FreightAreaTemplateDAOImpl extends SqlMapClientDaoSupport implements FreightAreaTemplateDAO {
    @Override
    public Long addAreaTemplate(FreightAreaTemplateDO freightAreaTemplateDO) {
        return (Long)getSqlMapClientTemplate().insert("FreightAreaTemplateDAO.addAreaTemplate",freightAreaTemplateDO);
    }

    @Override
    public FreightAreaTemplateDO getAreaTemplate(int id, int sellerId) {
        return (FreightAreaTemplateDO) getSqlMapClientTemplate().queryForObject("FreightAreaTemplateDAO.getAreaTemplate",id,sellerId);
    }

    @Override
    public List<FreightAreaTemplateDO> queryAreaTemplate(FreightAreaTemplateQTO freightAreaTemplateQTO) {
        return getSqlMapClientTemplate().queryForList("FreightAreaTemplateDAO.queryAreaTemplateList",freightAreaTemplateQTO);
    }

    @Override
    public int updateAreaTemplate(FreightAreaTemplateDO freightAreaTemplateDO) {
        return getSqlMapClientTemplate().update("FreightAreaTemplateDAO.updateAreaTemplate",freightAreaTemplateDO);
    }

    @Override
    public int deleteAreaTemplateByParentId(FreightAreaTemplateQTO freightAreaTemplateQTO) {
        return getSqlMapClientTemplate().update("FreightAreaTemplateDAO.deleteAreaTemplateByParentId",freightAreaTemplateQTO);
    }

    @Override
     public int deleteAreaTemplate(FreightAreaTemplateQTO freightAreaTemplateQTO) {
        return getSqlMapClientTemplate().update("FreightAreaTemplateDAO.deleteAreaTemplate",freightAreaTemplateQTO);
    }
}

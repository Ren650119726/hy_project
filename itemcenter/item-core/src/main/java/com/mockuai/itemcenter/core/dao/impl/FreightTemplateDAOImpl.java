package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;
import com.mockuai.itemcenter.core.dao.FreightTemplateDAO;
import com.mockuai.itemcenter.core.domain.FreightTemplateDO;
import com.mockuai.itemcenter.core.domain.ItemBrandDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindingyu on 15/9/22.
 */
@Service
public class FreightTemplateDAOImpl extends SqlMapClientDaoSupport implements FreightTemplateDAO{

    @Override
    public Long addTemplate(FreightTemplateDO freightTemplateDO) {
        Long id =  (Long)getSqlMapClientTemplate().insert("FreightTemplateDAO.addTemplate",freightTemplateDO);
        return id;
    }

    @Override
    public FreightTemplateDO getTemplate(FreightTemplateQTO freightTemplateQTO) {
        return (FreightTemplateDO) getSqlMapClientTemplate().queryForObject("FreightTemplateDAO.getTemplate",freightTemplateQTO);
    }

    @Override
    public List<FreightTemplateDO> queryTemplate(FreightTemplateQTO freightTemplateQTO) {
        if (null != freightTemplateQTO.getNeedPaging()) {
            Integer totalCount = (Integer) getSqlMapClientTemplate()
                    .queryForObject("FreightTemplateDAO.countTemplate", freightTemplateQTO);// 总记录数
            freightTemplateQTO.setTotalCount(totalCount);
            if (totalCount == 0) {
                return new ArrayList<FreightTemplateDO>();
            } else {
                freightTemplateQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
            totalCount = null;
        }
        return getSqlMapClientTemplate().queryForList("FreightTemplateDAO.queryTemplateList",freightTemplateQTO);
    }

    @Override
    public int updateTemplate(FreightTemplateDO freightTemplateDO) {
        return getSqlMapClientTemplate().update("FreightTemplateDAO.updateTemplate",freightTemplateDO);
    }

    @Override
    public int deleteTemplate(FreightTemplateQTO freightTemplateQTO) {
        return getSqlMapClientTemplate().update("FreightTemplateDAO.deleteTemplate",freightTemplateQTO);
    }
}

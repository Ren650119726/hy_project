package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.common.qto.GlobalMessageQTO;
import com.mockuai.suppliercenter.core.dao.GlobalMessageDAO;
import com.mockuai.suppliercenter.core.domain.GlobalMessageDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalMessageDAOImpl extends SqlMapClientDaoSupport implements GlobalMessageDAO {

    @Override
    public Long addGlobalMessage(GlobalMessageDO globalMessage) {
        return (Long) this.getSqlMapClientTemplate().insert("global_message.addGlobalMessage", globalMessage);
    }

    @Override
    public List<GlobalMessageDO> queryGlobalMessage(
            GlobalMessageQTO globalMessageQTO) {
        return (List<GlobalMessageDO>) this.getSqlMapClientTemplate().queryForList("global_message.queryGlobalMessage", globalMessageQTO);
    }

    public Long getTotalCount(GlobalMessageQTO globalMessageQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("global_message.getTotalCount", globalMessageQTO);
    }
}

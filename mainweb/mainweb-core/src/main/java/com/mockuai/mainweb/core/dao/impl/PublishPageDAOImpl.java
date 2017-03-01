package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.PublishPageDAO;
import com.mockuai.mainweb.core.domain.PublishPageDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishPageDAOImpl extends SqlMapClientDaoSupport implements PublishPageDAO{
    @Override
    public Long addPublishPage(PublishPageDO publishPageDO) throws SQLException {
        Long newInsertId = (Long)getSqlMapClientTemplate().insert("publishPage.add",publishPageDO);
        return newInsertId;
    }

    @Override
    public PublishPageDO getPublishPage(Long id) throws SQLException {
        PublishPageDO publishDO=(PublishPageDO) getSqlMapClientTemplate().
                queryForObject("publishPage.getPublishPage",id);
        return publishDO;
    }

    @Override
    public Integer deletePublishPage(Long id) throws SQLException {

        Integer row = (Integer)getSqlMapClientTemplate().update("publishPage.deletePublishPage",id);
        return row;
    }
}

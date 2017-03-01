
package com.mockuai.mainweb.core.dao.impl;
import com.mockuai.mainweb.core.dao.PublishTabDAO;
import com.mockuai.mainweb.core.domain.PublishTabDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishTabDAOImpl extends SqlMapClientDaoSupport implements PublishTabDAO {
    @Override
    public Long addPublishTab(PublishTabDO publishTabDO) throws SQLException {
        Long newInsertId = (Long)getSqlMapClientTemplate().insert("publishTab.add",publishTabDO);
        return newInsertId;
    }

    @Override
    public PublishTabDO getPublishTab() throws SQLException {
        PublishTabDO publishDO=(PublishTabDO) getSqlMapClientTemplate().
                queryForObject("publishTab.getPublishTab");
        return publishDO;
    }

    @Override
    public Integer updatePublishTab(PublishTabDO publishTabDO) throws SQLException {

        Integer row = getSqlMapClientTemplate().update("publishTab.updatePublishTab",publishTabDO);
        return row;
    }


    @Override
    public List<PublishTabDO> queryTabNames() throws SQLException {
        List<PublishTabDO> publishTabDOs = this.getSqlMapClientTemplate().queryForList("publishTab.queryTabNames");
        return publishTabDOs;
    }

    @Override
    public Long confirmRecordExist() throws SQLException {
        Long recordCount = (Long) this.getSqlMapClientTemplate().queryForObject("publishTab.confirmRecordExist");
        return recordCount;
    }
}

package com.mockuai.mainweb.core.dao.impl.publish;

import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.publish.PublishIndexPageDAO;
import com.mockuai.mainweb.core.domain.publish.PublishIndexPageDO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishIndexPageDAOImpl extends BaseDAO implements PublishIndexPageDAO {
    @Override
    public void addPage(PublishIndexPageDO indexPageDO) throws SQLException {
          this.getSqlMapClientTemplate().insert("publish_index_page.addPage", indexPageDO);
    }

    @Override
    public void deletePage(Long id) throws SQLException {
         this.getSqlMapClientTemplate().delete("publish_index_page.deletePage",id);
    }



    @Override
    public PublishIndexPageDO getPage(Long id) throws SQLException {
        return (PublishIndexPageDO) this.getSqlMapClientTemplate().queryForObject("publish_index_page.getPage",id);
    }

    @Override
    public List<PublishIndexPageDO> queryPageNameList() throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("publish_index_page.queryPageNameList");
    }

    @Override
    public List<PublishIndexPageDO> queryPublishPageNames() throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("publish_index_page.queryPublishPageNames");
    }



}

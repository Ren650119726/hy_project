package com.mockuai.mainweb.core.dao.impl.publish;

import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.publish.PublishPageBlockDAO;
import com.mockuai.mainweb.core.domain.PageBlockDO;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishPageBlockDAOImpl extends BaseDAO implements PublishPageBlockDAO {
    @Override
    public void addPageBlock(PageBlockDO pageBlockDO) throws SQLException {
        this.getSqlMapClientTemplate().insert("publish_page_block.addPageBlock",pageBlockDO);
    }

    @Override
    public void addPageBlockList(List<PageBlockDO> pageBlockList) throws SQLException {
        this.getSqlMapClientTemplate().insert("publish_page_block.addPageBlockList",pageBlockList);

    }

    @Override
    public void deletePageBlock(Long pageId) throws SQLException {
        this.getSqlMapClientTemplate().insert("publish_page_block.deletePageBlock",pageId);

    }


    @Override
    public List<PageBlockDO> queryPageBlock(Long pageId) throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("publish_page_block.queryPageBlock",pageId);
    }



}

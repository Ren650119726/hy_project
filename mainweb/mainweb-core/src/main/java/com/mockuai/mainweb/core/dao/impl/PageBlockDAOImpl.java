package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PageBlockDAO;
import com.mockuai.mainweb.core.domain.PageBlockDO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PageBlockDAOImpl extends BaseDAO implements PageBlockDAO {
    @Override
    public void addPageBlock(PageBlockDO pageBlockDO) throws SQLException {
        this.getSqlMapClientTemplate().insert("page_block.addPageBlock",pageBlockDO);
    }

    @Override
    public void addPageBlockList(List<PageBlockDO> pageBlockList) throws SQLException {
        this.getSqlMapClientTemplate().insert("page_block.addPageBlockList",pageBlockList);

    }

    @Override
    public void deletePageBlock(Long pageId) throws SQLException {
         this.getSqlMapClientTemplate().update("page_block.deletePageBlock",pageId);

    }

    @Override
    public List<PageBlockDO> queryPageBlock(Long pageId) throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("page_block.queryPageBlock",pageId);
    }


    @Override
    public boolean existBlockOrder() throws SQLException {

        Long result = (Long) this.getSqlMapClientTemplate().queryForObject("page_block.existBlockOrder");
        return result>0;
    }

}

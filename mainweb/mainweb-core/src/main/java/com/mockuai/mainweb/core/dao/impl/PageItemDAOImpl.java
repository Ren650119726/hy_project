package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.PageItemDAO;
import com.mockuai.mainweb.core.domain.PageItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PageItemDAOImpl extends SqlMapClientDaoSupport implements PageItemDAO{
    @Override
    public void addPageItem(List<PageItemDO> pageItemList) throws SQLException {
        getSqlMapClientTemplate().insert("pageItem.addPageItem",pageItemList);

    }

    @Override
    public List<PageItemDO> queryPageItem(Long pageItemCategoryId) throws SQLException {
        List<PageItemDO> data=(List<PageItemDO>) getSqlMapClientTemplate().
                queryForList("pageItem.queryPageItem",pageItemCategoryId);
        return data;
    }

    @Override
    public Integer deleteByPageId(Long itemCategoryId) throws SQLException {

        Integer row = (Integer)getSqlMapClientTemplate().update("pageItem.deleteByPageId",itemCategoryId);
        return row;
    }

    @Override
    public void deleteByItemId(Long itemId) throws SQLException {
        getSqlMapClientTemplate().update("pageItem.deleteByItemId",itemId);
    }

    @Override
    public List<PageItemDO> queryPageByItemId(Long itemId) throws SQLException {
        return getSqlMapClientTemplate().queryForList("pageItem.queryPageByItemId",itemId);
    }

}

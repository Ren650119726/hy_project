package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.PageItemCategoryDAO;
import com.mockuai.mainweb.core.domain.PageItemCategoryDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PageItemCategoryDAOImpl extends SqlMapClientDaoSupport implements PageItemCategoryDAO {


    @Override
    public Long addPageItemCategory(PageItemCategoryDO pageItemCategoryDO) throws SQLException {
        Long newInsertId = (Long)getSqlMapClientTemplate().
                insert("pageItemCategory.add",pageItemCategoryDO);
        return newInsertId;
    }

    @Override
    public PageItemCategoryDO getPageItemCategory(Long  pageBlockId) throws SQLException {
        PageItemCategoryDO pageItemDO=(PageItemCategoryDO) getSqlMapClientTemplate().
                queryForObject("pageItemCategory.getPageItemCategory",pageBlockId);
        return pageItemDO;
    }

    @Override
    public Integer deleteByPageId(Long pageId) throws SQLException {

        Integer row = (Integer)getSqlMapClientTemplate().
                update("pageItemCategory.deleteByPageId",pageId);
        return row;
    }
}

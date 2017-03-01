package com.mockuai.mainweb.core.dao.impl.publish;

import com.mockuai.mainweb.core.dao.publish.PublishPageItemCategoryDAO;
import com.mockuai.mainweb.core.domain.PageItemCategoryDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishPageItemCategoryDAOImpl extends SqlMapClientDaoSupport implements PublishPageItemCategoryDAO {


    @Override
    public Long addPageItemCategory(PageItemCategoryDO pageItemCategoryDO) throws SQLException {
        Long newInsertId = (Long)getSqlMapClientTemplate().
                insert("publish_pageItemCategory.add",pageItemCategoryDO);
        return newInsertId;
    }

    @Override
    public PageItemCategoryDO getPageItemCategory(Long  pageBlockId) throws SQLException {
        PageItemCategoryDO pageItemDO=(PageItemCategoryDO) getSqlMapClientTemplate().
                queryForObject("publish_pageItemCategory.getPageItemCategory",pageBlockId);
        return pageItemDO;
    }

    @Override
    public Integer deleteByPageId(Long pageId) throws SQLException {

        Integer row = (Integer)getSqlMapClientTemplate().
                update("publish_pageItemCategory.deleteByPageId",pageId);
        return row;
    }
}

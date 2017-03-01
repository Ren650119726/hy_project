package com.mockuai.mainweb.core.dao.impl.publish;

import com.mockuai.mainweb.core.dao.publish.PublishPageItemDAO;
import com.mockuai.mainweb.core.domain.PageItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PublishPageItemDAOImpl extends SqlMapClientDaoSupport implements PublishPageItemDAO {
    @Override
    public void addPageItem(List<PageItemDO> pageItemList) throws SQLException {
        getSqlMapClientTemplate().insert("publish_pageItem.addPageItem",pageItemList);

    }

    @Override
    public List<PageItemDO> queryPageItem(Long pageItemCategoryId) throws SQLException {
        List<PageItemDO> data=(List<PageItemDO>) getSqlMapClientTemplate().
                queryForList("publish_pageItem.queryPageItem",pageItemCategoryId);
        return data;
    }

    @Override
    public Integer deleteByPageId(Long itemCategoryId) throws SQLException {

        Integer row = (Integer)getSqlMapClientTemplate().update("publish_pageItem.deleteByPageId",itemCategoryId);
        return row;
    }


}

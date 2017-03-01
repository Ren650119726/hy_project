package com.mockuai.mainweb.core.dao.publish;

import com.mockuai.mainweb.core.domain.PageItemCategoryDO;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PublishPageItemCategoryDAO {
    Long addPageItemCategory(PageItemCategoryDO pageItemCategoryDO) throws SQLException;
    PageItemCategoryDO getPageItemCategory(Long pageBlockId) throws SQLException;
    Integer deleteByPageId(Long pageId) throws SQLException;
}

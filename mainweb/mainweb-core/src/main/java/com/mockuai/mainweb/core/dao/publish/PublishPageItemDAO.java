package com.mockuai.mainweb.core.dao.publish;

import com.mockuai.mainweb.core.domain.PageItemDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PublishPageItemDAO {

    void addPageItem(List<PageItemDO> pageItemDOList) throws SQLException;

    List<PageItemDO> queryPageItem(Long pageItemCategoryId) throws SQLException;

    Integer deleteByPageId(Long pageId) throws SQLException;




}

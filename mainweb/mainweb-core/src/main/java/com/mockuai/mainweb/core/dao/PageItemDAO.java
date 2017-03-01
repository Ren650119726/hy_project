package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.core.domain.PageItemDO;
import com.mockuai.mainweb.core.exception.MainWebException;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PageItemDAO {

    void addPageItem(List<PageItemDO> pageItemDOList) throws SQLException;

    List<PageItemDO> queryPageItem(Long pageItemCategoryId) throws SQLException;

    Integer deleteByPageId(Long pageId) throws SQLException;

    void deleteByItemId(Long itemId) throws SQLException;

    List<PageItemDO> queryPageByItemId(Long itemId) throws SQLException;


}

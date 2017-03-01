package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.core.domain.PageGalleryDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PageGalleryDAO {

    void addPageGallery(PageGalleryDO pageGalleryDO) throws SQLException;

    void deletePageGallery(Long pageId) throws SQLException;

    List<PageGalleryDO> queryPageGallery(Long blockId) throws SQLException;
}

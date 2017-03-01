package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mockuai.mainweb.core.domain.PagePictureDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PagePictureDAO {

    void addPagePicture(PagePictureDO pagePictureDO) throws SQLException;

    void deletePagePicture(Long pageId) throws SQLException;

    List<PagePictureDO> queryPagePicture(Long blockId) throws SQLException;
}

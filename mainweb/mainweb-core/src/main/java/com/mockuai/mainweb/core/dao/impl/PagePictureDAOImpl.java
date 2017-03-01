package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PagePictureDAO;
import com.mockuai.mainweb.core.domain.PagePictureDO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by huangsiqian  on 2016/12/19.
 */
@Service
public class PagePictureDAOImpl extends BaseDAO implements PagePictureDAO {
    @Override
    public void addPagePicture(PagePictureDO pagePictureDO) throws SQLException {
        this.getSqlMapClientTemplate().insert("page_picture.addPagePicture",pagePictureDO);
    }

    @Override
    public void deletePagePicture(Long pageId) throws SQLException {
        this.getSqlMapClientTemplate().update("page_picture.deletePagePicture",pageId);
    }

    @Override
    public List<PagePictureDO> queryPagePicture(Long blockId) throws SQLException {
           return this.getSqlMapClientTemplate().queryForList("page_picture.queryPagePicture",blockId);
    }
}

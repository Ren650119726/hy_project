package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PageGalleryDAO;
import com.mockuai.mainweb.core.domain.PageGalleryDO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PageGalleryDAOImpl extends BaseDAO implements PageGalleryDAO {
    @Override
    public void addPageGallery(PageGalleryDO pageGalleryDO) throws SQLException {
        this.getSqlMapClientTemplate().insert("page_gallery.addPageGallery",pageGalleryDO);
    }

    @Override
    public void deletePageGallery(Long pageId) throws SQLException {
         this.getSqlMapClientTemplate().update("page_gallery.deletePageGallery",pageId);
    }

    @Override
    public List<PageGalleryDO> queryPageGallery(Long blockId) throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("page_gallery.queryPageGallery",blockId);
    }
}

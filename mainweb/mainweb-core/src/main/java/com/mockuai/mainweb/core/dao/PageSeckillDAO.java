package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.core.domain.PageGalleryDO;
import com.mockuai.mainweb.core.domain.PageSeckillDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface PageSeckillDAO {
    void addPageSeckill(PageSeckillDO pageSeckillDO) throws SQLException;

    void deletePageSeckill(Long pageId) throws SQLException;

    List<PageSeckillDO> getPageSeckill(Long pageId) throws SQLException;

    List<String> queryPageSeckillTitles(Long pageId) throws SQLException;
}

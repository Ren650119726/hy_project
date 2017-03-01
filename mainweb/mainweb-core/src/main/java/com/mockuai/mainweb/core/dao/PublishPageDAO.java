package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.core.domain.PublishPageDO;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PublishPageDAO {
    Long addPublishPage(PublishPageDO publishPageDO) throws SQLException;

    PublishPageDO getPublishPage(Long id) throws SQLException;

    Integer deletePublishPage(Long id) throws SQLException;

}

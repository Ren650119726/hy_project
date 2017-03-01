package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.core.domain.PublishTabDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface PublishTabDAO {
    Long addPublishTab(PublishTabDO publishTabDO) throws SQLException;

    PublishTabDO getPublishTab() throws SQLException;


    Integer updatePublishTab(PublishTabDO publishTabDO) throws SQLException;

    List<PublishTabDO> queryTabNames() throws SQLException;

    Long confirmRecordExist() throws SQLException;;



}

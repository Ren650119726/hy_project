package com.mockuai.mainweb.core.dao.publish;

import com.mockuai.mainweb.core.domain.publish.PublishIndexPageDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hsq on 2016/9/20.
 */

public interface PublishIndexPageDAO {
    void addPage(PublishIndexPageDO indexPageDO) throws SQLException;

    void deletePage(Long id) throws SQLException;


    PublishIndexPageDO getPage(Long id) throws SQLException;

    List<PublishIndexPageDO> queryPageNameList() throws SQLException;

    List<PublishIndexPageDO>  queryPublishPageNames() throws SQLException;


}

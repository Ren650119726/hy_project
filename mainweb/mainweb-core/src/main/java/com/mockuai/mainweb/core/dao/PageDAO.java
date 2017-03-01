package com.mockuai.mainweb.core.dao;

import com.mockuai.mainweb.common.domain.qto.PageQTO;
import com.mockuai.mainweb.core.domain.IndexPageDO;
import com.mockuai.mainweb.core.domain.publish.PublishIndexPageDO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hsq on 2016/9/20.
 */

public interface PageDAO {
    void addPage(IndexPageDO indexPageDO) throws SQLException;

    void deletePage(Long id) throws SQLException;

    void updatePage(IndexPageDO indexPageDO) throws SQLException;

    IndexPageDO getPage(Long id) throws SQLException;

    List<IndexPageDO> queryPageNameList() throws SQLException;

    List<IndexPageDO>  queryPublishPageNames() throws SQLException;


    boolean existPageOrder() throws SQLException;
    //页面列表
    List<IndexPageDO> showPageList(PageQTO pageQTO)throws SQLException;
    //取消页面发布
    void cancelPage(Long id )throws SQLException;
}

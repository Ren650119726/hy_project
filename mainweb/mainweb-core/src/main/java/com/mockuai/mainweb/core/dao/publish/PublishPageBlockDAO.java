package com.mockuai.mainweb.core.dao.publish;

import com.mockuai.mainweb.core.domain.PageBlockDO;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/19.
 */
@Service
public interface PublishPageBlockDAO {

    void addPageBlock(PageBlockDO pageBlockDO) throws SQLException;
    void addPageBlockList(List<PageBlockDO> pageBlockDO) throws SQLException;

    void deletePageBlock(Long pageId) throws SQLException;

    List<PageBlockDO> queryPageBlock(Long pageId) throws SQLException;

}

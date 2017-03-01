package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.common.domain.dto.IndexPageQTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;
import com.mockuai.mainweb.core.dao.BaseDAO;
import com.mockuai.mainweb.core.dao.PageDAO;
import com.mockuai.mainweb.core.domain.IndexPageDO;
import com.mockuai.mainweb.core.domain.publish.PublishIndexPageDO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service
public class PageDAOImpl extends BaseDAO implements PageDAO {
    @Override
    public void addPage(IndexPageDO indexPageDO) throws SQLException {
          this.getSqlMapClientTemplate().insert("page.addPage", indexPageDO);
    }

    @Override
    public void deletePage(Long id) throws SQLException {
         this.getSqlMapClientTemplate().delete("page.deletePage",id);
    }

    @Override
    public void updatePage(IndexPageDO indexPageDO) throws SQLException {
         this.getSqlMapClientTemplate().update("page.updatePage", indexPageDO);
    }

    @Override
    public IndexPageDO getPage(Long id) throws SQLException {
        return (IndexPageDO) this.getSqlMapClientTemplate().queryForObject("page.getPage",id);
    }

    @Override
    public List<IndexPageDO> queryPageNameList() throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("page.queryPageNameList");
    }

    @Override
    public List<IndexPageDO> queryPublishPageNames() throws SQLException {
        return this.getSqlMapClientTemplate().queryForList("page.queryPublishPageNames");
    }

    @Override
    public boolean existPageOrder() throws SQLException {

       Long result = (Long) this.getSqlMapClientTemplate().queryForObject("page.existPageOrder");
       return result>0;
    }
    @Override
    public List<IndexPageDO> showPageList(PageQTO pageQTO) throws SQLException {

        pageQTO.setTotalCount(Integer.parseInt(String.valueOf(this.getSqlMapClientTemplate().queryForObject("page.pageCount",pageQTO))));
        return  (List<IndexPageDO>)this.getSqlMapClientTemplate().queryForList("page.showPageList",pageQTO);
    }

    @Override
    public void cancelPage(Long id) throws SQLException {
        Map map = new HashMap();
        map.put("id",id);
        Integer result = this.getSqlMapClientTemplate().update("page.cancelPage",map);
        System.out.println("result:"+result);
    }
}

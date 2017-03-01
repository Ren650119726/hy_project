package com.mockuai.mainweb.core.dao.impl;

import com.mockuai.mainweb.core.dao.PageSeckillDAO;
import com.mockuai.mainweb.core.domain.PageSeckillDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
@Service
public class PageSeckillDAOImpl extends SqlMapClientDaoSupport implements PageSeckillDAO {

    @Override
    public void addPageSeckill(PageSeckillDO pageSeckillDO) throws SQLException {
        getSqlMapClientTemplate().insert("page_seckill.addPageSeckill",pageSeckillDO);
    }

    @Override
    public void deletePageSeckill(Long pageId) throws SQLException {
        getSqlMapClientTemplate().update("page_seckill.deletePageSeckill",pageId);
    }

    @Override
    public List<PageSeckillDO> getPageSeckill(Long pageId) throws SQLException {
        List<PageSeckillDO> pageSeckillDOs = (List<PageSeckillDO>) getSqlMapClientTemplate().queryForList("page_seckill.getPageSeckill",pageId);

        return pageSeckillDOs;
    }

    @Override
    public List<String> queryPageSeckillTitles(Long pageId) throws SQLException {
        List<String> pageSeckillTitles =  getSqlMapClientTemplate().queryForList("page_seckill.querySeckillTitles",pageId);
        return pageSeckillTitles;
    }
}

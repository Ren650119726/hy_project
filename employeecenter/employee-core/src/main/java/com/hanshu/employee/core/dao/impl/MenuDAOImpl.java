package com.hanshu.employee.core.dao.impl;

import com.hanshu.employee.common.qto.MenuQTO;
import com.hanshu.employee.core.dao.MenuDAO;
import com.hanshu.employee.core.domain.MenuDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/11.
 */
@Service
public class MenuDAOImpl extends SqlMapClientDaoSupport implements MenuDAO {
    @Override
    public Long addMenu(MenuDO menuDO) {
        return (Long) this.getSqlMapClientTemplate().insert("menu.addMenu", menuDO);
    }

    @Override
    public MenuDO getMenuById(Long id) {
        return (MenuDO) this.getSqlMapClientTemplate().queryForObject("menu.getMenuById", id);
    }

    @Override
    public int deleteMenu(Long menuId) {
        return this.getSqlMapClientTemplate().update("menu.deleteMenuById", menuId);
    }

    @Override
    public List<MenuDO> queryMenu(MenuQTO menuQTO) {
        return this.getSqlMapClientTemplate().queryForList("menu.queryMenu", menuQTO);
    }

    @Override
    public Long getTotalCount(MenuQTO menuQTO) {
        return (Long) this.getSqlMapClientTemplate().queryForObject("menu.getTotalCount", menuQTO);
    }

    @Override
    public int updateMenu(MenuDO menuDO) {
        return this.getSqlMapClientTemplate().update("menu.updateMenu", menuDO);
    }

    @Override
    public MenuDO getParentMenuByUrl(String url, String version) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("url",url);
        map.put("version",version);
        return (MenuDO) this.getSqlMapClientTemplate().queryForObject("menu.getParentMenuByUrl", map);
    }

    @Override
    public MenuDO getMenuByTitleMenuUrl(String title, String url) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("title",title);
        map.put("url",url);
        return (MenuDO) this.getSqlMapClientTemplate().queryForObject("menu.getMenuByTitleMenuUrl",map);
    }

    @Override
    public MenuDO getMenuByUrl(String url, String version) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("url",url);
        map.put("version",version);
        return (MenuDO) this.getSqlMapClientTemplate().queryForObject("menu.getMenuByUrl", map);
    }
}

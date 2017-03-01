package com.hanshu.employee.core.dao;

import com.hanshu.employee.common.qto.MenuQTO;
import com.hanshu.employee.core.domain.MenuDO;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public interface MenuDAO {
    Long addMenu(MenuDO menuDO);

    MenuDO getMenuById(Long id);

    int deleteMenu(Long menuId);

    List<MenuDO> queryMenu(MenuQTO menuQTO);

    Long getTotalCount(MenuQTO menuQTO);

    int updateMenu(MenuDO menuDO);

    MenuDO getParentMenuByUrl(String url, String version);

    MenuDO getMenuByTitleMenuUrl(String title, String url);

    MenuDO getMenuByUrl(String url, String version);
}

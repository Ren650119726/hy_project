package com.hanshu.employee.client;

import com.hanshu.employee.common.api.Response;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.MenuQTO;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public interface MenuClient {
    /**
     * 添加菜单
     */
    Response<MenuDTO> addMenu(MenuDTO menuDTO, String appKey);

    /**
     * 删除菜单
     */
    Response<Boolean> deleteMenu(Long menuId, String appKey);

    /**
     * 修改菜单
     */
    Response<Boolean> updateMenu(MenuDTO menuDTO, String appKey);

    /**
     * 查询菜单
     */
    Response<List<MenuDTO>> queryMenu(MenuQTO menuQTO, String appKey);

    /**
     * 获取菜单
     */
    Response<MenuDTO> getMenu(Long menuId, String appKey);

    /**
     * 通过url,获取父menu
     */
    Response<MenuDTO> getParentMenuByUrl(String url, String version, String appKey);

    /**
     * 通过url,获取menu
     */
    Response<MenuDTO> getMenuByUrl(String url,String version,String appKey);
}

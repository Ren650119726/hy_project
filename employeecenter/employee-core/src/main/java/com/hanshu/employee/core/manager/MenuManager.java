package com.hanshu.employee.core.manager;

import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.MenuQTO;
import com.hanshu.employee.core.exception.EmployeeException;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public interface MenuManager {
    Long addMenu(MenuDTO menuDTO) throws EmployeeException;

    MenuDTO getMenuById(Long id);

    Boolean deleteMenu(Long menuId) throws EmployeeException;

    List<MenuDTO> queryMenu(MenuQTO menuQTO);

    Long getTotalCount(MenuQTO menuQTO);

    Boolean updateMenu(MenuDTO menuDTO) throws EmployeeException;

    MenuDTO getParentMenuByUrl(String url, String version) throws EmployeeException;

    MenuDTO getMenuByUrl(String url, String version) throws EmployeeException;
}

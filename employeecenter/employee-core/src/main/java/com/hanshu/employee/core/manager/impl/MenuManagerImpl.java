package com.hanshu.employee.core.manager.impl;

import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.MenuQTO;
import com.hanshu.employee.core.dao.MenuDAO;
import com.hanshu.employee.core.domain.MenuDO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.MenuManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
@Service
public class MenuManagerImpl implements MenuManager {
    @Resource
    private MenuDAO menuDAO;

    @Override
    public Long addMenu(MenuDTO menuDTO) throws EmployeeException {
        MenuDO menuDO1 = this.menuDAO.getMenuByTitleMenuUrl(menuDTO.getTitle(), menuDTO.getUrl());
        if (menuDO1 != null) {
            throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST);
        }

        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(menuDTO, menuDO);
        Long id = this.menuDAO.addMenu(menuDO);
        return id;
    }

    @Override
    public MenuDTO getMenuById(Long id) {
        MenuDO menuDO = this.menuDAO.getMenuById(id);
        MenuDTO menuDTO = new MenuDTO();
        BeanUtils.copyProperties(menuDO, menuDTO);
        return menuDTO;
    }

    @Override
    public Boolean deleteMenu(Long menuId) throws EmployeeException {
        MenuDO menuDO = this.menuDAO.getMenuById(menuId);
        if (menuDO == null) {
            throw new EmployeeException(ResponseCode.B_SELECT_ERROR);
        }
        //查找以menuId为父的menu
        MenuQTO menuQTO = new MenuQTO();
        menuQTO.setParentId(menuId);
        menuQTO.setVersion(menuDO.getVersion());
        List<MenuDO> childMenu = this.menuDAO.queryMenu(menuQTO);
        if (childMenu.size() > 0) {
            throw new EmployeeException(ResponseCode.B_DELETE_ERROR, "该菜单有子菜单,不能删除");
        }

        int n = this.menuDAO.deleteMenu(menuId);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_DELETE_ERROR, "删除菜单失败");
        }
    }

    @Override
    public List<MenuDTO> queryMenu(MenuQTO menuQTO) {
        List<MenuDO> menuDOs = this.menuDAO.queryMenu(menuQTO);
        if (menuDOs != null) {
            List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
            for (MenuDO menuDO : menuDOs) {
                MenuDTO menuDTO = new MenuDTO();
                BeanUtils.copyProperties(menuDO, menuDTO);
                menuDTOs.add(menuDTO);
            }
            return menuDTOs;

        } else {
            return null;
        }
    }

    @Override
    public Long getTotalCount(MenuQTO menuQTO) {
        return this.menuDAO.getTotalCount(menuQTO);
    }

    @Override
    public Boolean updateMenu(MenuDTO menuDTO) throws EmployeeException {

        //菜单重名判断
        MenuDO menuDO1 = this.menuDAO.getMenuByTitleMenuUrl(menuDTO.getTitle(), menuDTO.getUrl());
        if (menuDO1 != null && !menuDO1.getId().equals(menuDTO.getId())) {
            throw new EmployeeException(ResponseCode.B_RECORD_IS_EXIST);
        }

        MenuDO menuDO = new MenuDO();
        BeanUtils.copyProperties(menuDTO, menuDO);
        int n = this.menuDAO.updateMenu(menuDO);
        if (n == 1) {
            return true;
        } else {
            throw new EmployeeException(ResponseCode.B_UPDATE_ERROR, "更新菜单出错");
        }
    }

    @Override
    public MenuDTO getParentMenuByUrl(String url, String version) throws EmployeeException {
        MenuDTO menuDTO = null;
        MenuDO menuDO = this.menuDAO.getParentMenuByUrl(url, version);
        if (menuDO != null) {
            menuDTO = new MenuDTO();
            BeanUtils.copyProperties(menuDO, menuDTO);
        } else {
            throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "找不到相关记录");
        }
        return menuDTO;
    }

    @Override
    public MenuDTO getMenuByUrl(String url, String version) throws EmployeeException {
        MenuDO menuDO = this.menuDAO.getMenuByUrl(url, version);
        if (menuDO != null) {
            MenuDTO menuDTO = new MenuDTO();
            BeanUtils.copyProperties(menuDO, menuDTO);
            return menuDTO;
        } else {
            throw new EmployeeException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "找不到相关记录");
        }
    }
}

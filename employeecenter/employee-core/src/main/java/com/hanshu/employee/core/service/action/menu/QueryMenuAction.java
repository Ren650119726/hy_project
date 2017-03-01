package com.hanshu.employee.core.service.action.menu;

import com.hanshu.employee.common.action.ActionEnum;
import com.hanshu.employee.common.api.EmployeeResponse;
import com.hanshu.employee.common.api.Request;
import com.hanshu.employee.common.constant.ResponseCode;
import com.hanshu.employee.common.dto.MenuDTO;
import com.hanshu.employee.common.qto.MenuQTO;
import com.hanshu.employee.core.exception.EmployeeException;
import com.hanshu.employee.core.manager.MenuManager;
import com.hanshu.employee.core.service.RequestContext;
import com.hanshu.employee.core.service.action.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yeliming on 16/5/9.
 */
@Service
public class QueryMenuAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(QueryMenuAction.class);

    @Resource
    private MenuManager menuManager;

    @Override
    public EmployeeResponse execute(RequestContext context) throws EmployeeException {
        Request request = context.getRequest();
        MenuQTO menuQTO = (MenuQTO) request.getParam("menuQTO");

        //参数判空校验
        if (menuQTO == null) {
            log.error("menuQTO is null");
            throw new EmployeeException(ResponseCode.P_PARAM_NULL, "menuQTO不能为空");
        }

        List<Long> lv2IdList = new ArrayList<Long>();

        List<MenuDTO> menuDTOs = this.menuManager.queryMenu(menuQTO);
        for (MenuDTO menuDTO : menuDTOs) {
            if (menuDTO.getParentId() == 0) {
                MenuQTO newMenuQTO = new MenuQTO();
                BeanUtils.copyProperties(menuQTO, newMenuQTO);
                newMenuQTO.setParentId(menuDTO.getId());
                menuDTO.setChildren(this.menuManager.queryMenu(newMenuQTO));
                if (menuDTO.getChildren() != null && !menuDTO.getChildren().isEmpty()) {
                    for (MenuDTO dto : menuDTO.getChildren()) {
                        lv2IdList.add(dto.getId());
                    }
                }
            }
        }

        //第三级菜单
        Map<Long, List<MenuDTO>> map = new HashMap<Long, List<MenuDTO>>();
        MenuQTO menuQTO1 = new MenuQTO();
        menuQTO1.setParentIdList(lv2IdList);
        List<MenuDTO> menuDTO1s = menuManager.queryMenu(menuQTO1);
        for (MenuDTO menuDTO1 : menuDTO1s) {
            if (map.containsKey(menuDTO1.getParentId())) {
                map.get(menuDTO1.getParentId()).add(menuDTO1);
            } else {
                List<MenuDTO> menuDTOList = new ArrayList<MenuDTO>();
                menuDTOList.add(menuDTO1);
                map.put(menuDTO1.getParentId(), menuDTOList);
            }
        }

        for (MenuDTO menuDTO : menuDTOs) {
            if (menuDTO.getChildren() != null && !menuDTO.getChildren().isEmpty()) {
                for (MenuDTO dto : menuDTO.getChildren()) {
                    if (map.containsKey(dto.getId())) {
                        dto.setChildren(map.get(dto.getId()));
                    }
                }
            }
        }

        return new EmployeeResponse(menuDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_MENU.getActionName();
    }
}

package com.mockuai.itemcenter.core.service.action.itemcategory;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ActionEnum;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.manager.ItemCategoryTmplManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.service.action.TransAction;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商城入驻时,初始化商城的类目
 *
 * @author ming qian
 */
@Service
public class InitMallCategoryAction extends TransAction {

    private static final Logger log = LoggerFactory.getLogger(InitMallCategoryAction.class);

    @Resource
    private ItemCategoryManager itemCategoryManager;

    @Resource
    private ItemCategoryTmplManager itemCategoryTmplManager;

    @Override
    protected ItemResponse doTransaction(RequestContext context) throws ItemException {
        String bizCode = (String) context.get("bizCode");


        ItemCategoryTmplQTO tmplQTO = new ItemCategoryTmplQTO();
        tmplQTO.setCateLevel(1);


        List<ItemCategoryTmplDTO> tmplDTOList = itemCategoryTmplManager.queryItemCategoryTmpl(tmplQTO);


        //添加一级类目
        for (ItemCategoryTmplDTO tmplDTO : tmplDTOList) {

            ItemCategoryDTO itemCategoryDTO = new ItemCategoryDTO();

            BeanUtils.copyProperties(tmplDTO, itemCategoryDTO);
            itemCategoryDTO.setId(null);
            itemCategoryDTO.setTmplId(itemCategoryDTO.getId());
            itemCategoryDTO.setBizCode(bizCode);

            ItemCategoryDTO parent = itemCategoryManager.addItemCategory(itemCategoryDTO);

            ItemCategoryTmplQTO tmplQTO2 = new ItemCategoryTmplQTO();
            tmplQTO2.setParentId(tmplDTO.getId());
            tmplQTO.setCateLevel(2);

            List<ItemCategoryTmplDTO> tmplDTOList2 = itemCategoryTmplManager.queryItemCategoryTmpl(tmplQTO2);


            //添加一级类目下的二级类目
            for (ItemCategoryTmplDTO tmplDTO2 : tmplDTOList2) {

                ItemCategoryDTO itemCategoryDTO2 = new ItemCategoryDTO();
                BeanUtils.copyProperties(tmplDTO2, itemCategoryDTO2);
                itemCategoryDTO2.setId(null);
                itemCategoryDTO2.setParentId(parent.getId());
                itemCategoryDTO2.setTmplId(tmplDTO2.getId());
                itemCategoryDTO2.setTopId(parent.getTopId());
                itemCategoryDTO2.setBizCode(bizCode);

                itemCategoryManager.addItemCategory(itemCategoryDTO2);

            }
        }

        return ResponseUtil.getSuccessResponse(null);
    }

    @Override
    public String getName() {
        return ActionEnum.INIT_MALL_CATEGORY.getActionName();
    }
}

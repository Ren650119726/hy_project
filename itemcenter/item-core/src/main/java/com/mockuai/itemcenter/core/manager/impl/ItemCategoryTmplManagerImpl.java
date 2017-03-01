package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryTmplQTO;
import com.mockuai.itemcenter.core.dao.ItemCategoryDAO;
import com.mockuai.itemcenter.core.dao.ItemCategoryTmplDAO;
import com.mockuai.itemcenter.core.domain.ItemCategoryDO;
import com.mockuai.itemcenter.core.domain.ItemCategoryTmplDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryTmplManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/3/1.
 */
@Service
public class ItemCategoryTmplManagerImpl implements ItemCategoryTmplManager {

    @Resource
    private ItemCategoryTmplDAO itemCategoryTmplDAO;

    @Resource
    private ItemCategoryDAO itemCategoryDAO;

    @Override
    public List<ItemCategoryTmplDTO> queryItemCategoryTmpl(ItemCategoryTmplQTO tmplQTO) throws ItemException {

        List<ItemCategoryTmplDO> tmplDOList = itemCategoryTmplDAO.queryItemCategoryTmpl(tmplQTO);

        List<ItemCategoryTmplDTO> tmplDTOList = Lists.newArrayListWithCapacity(tmplDOList.size());

        for (ItemCategoryTmplDO tmplDO : tmplDOList) {

            ItemCategoryTmplDTO tmplDTO = new ItemCategoryTmplDTO();

            BeanUtils.copyProperties(tmplDO, tmplDTO);

            tmplDTOList.add(tmplDTO);
        }

        return tmplDTOList;
    }

    @Override
    public Long addOrGetCategoryBytmplId(Long categoryTmplId, String bizCode) throws ItemException {

        ItemCategoryQTO itemCategoryQTO = new ItemCategoryQTO();
        itemCategoryQTO.setTmplId(categoryTmplId);
        itemCategoryQTO.setBizCode(bizCode);

        List<ItemCategoryDO> itemCategoryDOList = itemCategoryDAO.queryItemCategory(itemCategoryQTO);

        if (CollectionUtils.isEmpty(itemCategoryDOList)) {

            //先查询二级类目模板
            ItemCategoryTmplDO itemCategoryTmplDO = itemCategoryTmplDAO.getItemCategoryTmpl(categoryTmplId);

            //查询一级类目模板
            ItemCategoryTmplDO topCategoryTmplDO = itemCategoryTmplDAO.getItemCategoryTmpl(itemCategoryTmplDO.getTopId());

            ItemCategoryQTO itemCategoryQTO2 = new ItemCategoryQTO();
            itemCategoryQTO2.setTmplId(topCategoryTmplDO.getId());
            itemCategoryQTO2.setBizCode(bizCode);

            List<ItemCategoryDO> itemCategoryDOList2 = itemCategoryDAO.queryItemCategory(itemCategoryQTO2);

            Long topId = 0L;
            if (CollectionUtils.isEmpty(itemCategoryDOList2)) {

                ItemCategoryDO itemCategoryDO = new ItemCategoryDO();

                BeanUtils.copyProperties(topCategoryTmplDO, itemCategoryDO);
                itemCategoryDO.setId(null);
                itemCategoryDO.setTmplId(topCategoryTmplDO.getId());
                itemCategoryDO.setParentId(0L);
                itemCategoryDO.setTopId(0L);
                itemCategoryDO.setBizCode(bizCode);


                topId = itemCategoryDAO.addItemCategory(itemCategoryDO);


            } else {
                topId = itemCategoryDOList2.get(0).getId();
            }

            ItemCategoryDO itemCategoryDO = new ItemCategoryDO();

            BeanUtils.copyProperties(itemCategoryTmplDO, itemCategoryDO);
            itemCategoryDO.setId(null);
            itemCategoryDO.setTmplId(itemCategoryTmplDO.getId());
            itemCategoryDO.setParentId(topId);
            itemCategoryDO.setTopId(topId);
            itemCategoryDO.setBizCode(bizCode);

            return itemCategoryDAO.addItemCategory(itemCategoryDO);

        } else {

            //存在由类目模板创建的类目
            return itemCategoryDOList.get(0).getId();
        }
    }

    @Override
    public Long addItemCategoryTmpl(ItemCategoryTmplDTO itemCategoryTmplDTO) {

        ItemCategoryTmplDO itemCategoryTmplDO = new ItemCategoryTmplDO();

        BeanUtils.copyProperties(itemCategoryTmplDTO, itemCategoryTmplDO);

        Long id = itemCategoryTmplDAO.addItemCategoryTmpl(itemCategoryTmplDO);

        if (!CollectionUtils.isEmpty(itemCategoryTmplDTO.getSubCategorys())) {

            for (ItemCategoryTmplDTO sub : itemCategoryTmplDTO.getSubCategorys()) {

                ItemCategoryTmplDO subDO = new ItemCategoryTmplDO();

                BeanUtils.copyProperties(sub, subDO);
                subDO.setTopId(id);
                subDO.setParentId(id);

                itemCategoryTmplDAO.addItemCategoryTmpl(subDO);
            }
        }

        return id;
    }

    @Override
    public Long addItemCategoryTmpl(List<ItemCategoryTmplDTO> itemCategoryTmplDTOList) {

        if (CollectionUtils.isEmpty(itemCategoryTmplDTOList)) {
            return 0L;
        }

        long count = 0L;

        for (ItemCategoryTmplDTO itemCategoryTmplDTO : itemCategoryTmplDTOList) {

            addItemCategoryTmpl(itemCategoryTmplDTO);
            count++;
        }


        return count;
    }
}

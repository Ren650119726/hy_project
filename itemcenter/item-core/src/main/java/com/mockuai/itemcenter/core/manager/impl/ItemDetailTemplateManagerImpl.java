package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.itemcenter.core.dao.ItemDetailTemplateDAO;
import com.mockuai.itemcenter.core.domain.ItemDetailTemplateDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemDetailTemplateManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luliang on 15/7/21.
 */
@Service
public class ItemDetailTemplateManagerImpl implements ItemDetailTemplateManager {

    private static Logger logger = LoggerFactory.getLogger(ItemDetailTemplateManagerImpl.class);

    @Resource
    private ItemDetailTemplateDAO itemDetailTemplateDAO;

    @Override
    public ItemDetailTemplateDTO getItemDetailTemplate(Long sellerId, Long id) throws ItemException {
        ItemDetailTemplateDO itemDetailTemplateDO = itemDetailTemplateDAO.getItemDetailTemplate(id, sellerId);
        if(itemDetailTemplateDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "getItemDetailTemplate error");
        }
        ItemDetailTemplateDTO itemDetailTemplateDTO = new ItemDetailTemplateDTO();
        BeanUtils.copyProperties(itemDetailTemplateDO, itemDetailTemplateDTO);
        return itemDetailTemplateDTO;
    }

    @Override
    public List<ItemDetailTemplateDTO> queryItemDetailTemplate(ItemDetailTemplateQTO itemDetailTemplateQTO) throws ItemException {
        List<ItemDetailTemplateDTO> list = new ArrayList<ItemDetailTemplateDTO>();
        List<ItemDetailTemplateDO> itemDetailTemplateDOList = itemDetailTemplateDAO.queryItemDetailTemplate(itemDetailTemplateQTO);
        if(itemDetailTemplateDOList == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "queryItemDetailTemplate error");
        }
        for(ItemDetailTemplateDO itemDetailTemplateDO: itemDetailTemplateDOList) {
            ItemDetailTemplateDTO itemDetailTemplateDTO = new ItemDetailTemplateDTO();
            BeanUtils.copyProperties(itemDetailTemplateDO, itemDetailTemplateDTO);
            list.add(itemDetailTemplateDTO);
        }
        return list;
    }

    @Override
    public Long addItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ItemException {
        ItemDetailTemplateDO itemDetailTemplateDO = new ItemDetailTemplateDO();
        BeanUtils.copyProperties(itemDetailTemplateDTO, itemDetailTemplateDO);
        return itemDetailTemplateDAO.addItemDetailTemplate(itemDetailTemplateDO);
    }

    @Override
    public Integer updateItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ItemException {
        ItemDetailTemplateDO itemDetailTemplateDO = new ItemDetailTemplateDO();
        BeanUtils.copyProperties(itemDetailTemplateDTO, itemDetailTemplateDO);
        return itemDetailTemplateDAO.updateItemDetailTemplate(itemDetailTemplateDO);
    }

    @Override
    public Integer deleteItemDetailTemplate(Long sellerId, Long id) throws ItemException {
        ItemDetailTemplateDO itemDetailTemplateDO = new ItemDetailTemplateDO();
        itemDetailTemplateDO.setId(id);
        itemDetailTemplateDO.setSellerId(sellerId);
        return itemDetailTemplateDAO.deleteItemDetailTemplate(itemDetailTemplateDO);
    }


}

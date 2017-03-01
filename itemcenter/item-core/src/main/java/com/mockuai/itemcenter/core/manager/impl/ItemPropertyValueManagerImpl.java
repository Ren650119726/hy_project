package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.core.dao.ItemPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyValueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
@Service
public class ItemPropertyValueManagerImpl implements ItemPropertyValueManager{
    private static final Logger log = LoggerFactory.getLogger(ItemPropertyValueManagerImpl.class);

    @Resource
    private ItemPropertyValueDAO itemPropertyValueDAO;

    @Override
    public void addItemPropertyValues(List<ItemPropertyValueDO> itemPropertyValueDOs) throws ItemException {
        //TODO 入参检查

        try{
            itemPropertyValueDAO.addItemPropertyValues(itemPropertyValueDOs);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    @Override
    public int updateItemPropertyValue(ItemPropertyValueDO itemPropertyValueDO) throws ItemException {
        //TODO 入参检查

        try{
            return itemPropertyValueDAO.updateItemPropertyValue(itemPropertyValueDO);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    @Override
    public int deleteItemPropertyValues(ItemPropertyValueDO itemPropertyValueDO) throws ItemException {
        //TODO 入参检查

        try{
            return itemPropertyValueDAO.deleteItemPropertyValues(itemPropertyValueDO);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    @Override
    public List<ItemPropertyValueDO> queryItemPropertyValue(Long itemPropertyTmplId) throws ItemException {
        //TODO 入参检查

        try{
            return itemPropertyValueDAO.queryItemPropertyValue(itemPropertyTmplId);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }
}

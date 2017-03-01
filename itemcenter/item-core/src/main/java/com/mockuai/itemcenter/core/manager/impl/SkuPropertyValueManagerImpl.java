package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyValueDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyValueQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyValueDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.SkuPropertyValueManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 8/9/15.
 */
@Service
public class SkuPropertyValueManagerImpl implements SkuPropertyValueManager{
    private static final Logger log = LoggerFactory.getLogger(SkuPropertyValueManagerImpl.class);

    @Resource
    private SkuPropertyValueDAO skuPropertyValueDAO;

    @Override
    public void addSkuPropertyValues(List<SkuPropertyValueDO> skuPropertyValueDOs) throws ItemException {
        //TODO 入参检查

        try{
            skuPropertyValueDAO.addSkuPropertyValues(skuPropertyValueDOs);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }
    }

    @Override
    public int updateSkuPropertyValue(SkuPropertyValueDTO skuPropertyValueDTO) throws ItemException {
        //TODO 入参检查

        SkuPropertyValueDO skuPropertyValueDO = new SkuPropertyValueDO();

        BeanUtils.copyProperties(skuPropertyValueDTO,skuPropertyValueDO);

        try{
            return skuPropertyValueDAO.updateSkuPropertyValue(skuPropertyValueDO);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }

    }

    @Override
    public int deleteSkuPropertyValues(SkuPropertyValueDO skuPropertyValueDO) throws ItemException {
        //TODO 入参检查

        try{
            return skuPropertyValueDAO.deleteSkuPropertyValues(skuPropertyValueDO);
        }catch(Exception e){
            //TODO 必要入参加入到日志中
            log.error("", e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);
        }

    }

    @Override
    public List<SkuPropertyValueDTO> querySkuPropertyValue(Long tmplId, String bizCode) throws ItemException {

        SkuPropertyValueQTO skuPropertyValueQTO = new SkuPropertyValueQTO();

        skuPropertyValueQTO.setSkuPropertyTmplId(tmplId);
        skuPropertyValueQTO.setBizCode(bizCode);

        List<SkuPropertyValueDO> skuPropertyValueDOList = skuPropertyValueDAO.querySkuPropertyValue(skuPropertyValueQTO);

        List<SkuPropertyValueDTO> skuPropertyValueDTOList = Lists.newArrayListWithCapacity(skuPropertyValueDOList.size());

        for(SkuPropertyValueDO skuPropertyValueDO : skuPropertyValueDOList){

            SkuPropertyValueDTO skuPropertyValueDTO = new SkuPropertyValueDTO();

            BeanUtils.copyProperties(skuPropertyValueDO,skuPropertyValueDTO);

            skuPropertyValueDTOList.add(skuPropertyValueDTO);
        }

        return skuPropertyValueDTOList;
    }

    @Override
    public Long deleteSkuPropertyValue(Long id, String bizCode) {
        SkuPropertyValueDO query = new SkuPropertyValueDO();
        query.setId(id);
        query.setBizCode(bizCode);
        return skuPropertyValueDAO.deleteSkuPropertyValue(query);
    }
}

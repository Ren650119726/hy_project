package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.RItemServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.RItemServiceTypeQTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceQTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.dao.RItemServiceTypeDAO;
import com.mockuai.itemcenter.core.dao.RItemSuitDAO;
import com.mockuai.itemcenter.core.dao.ValueAddedServiceDAO;
import com.mockuai.itemcenter.core.dao.ValueAddedServiceTypeDAO;
import com.mockuai.itemcenter.core.domain.RItemServiceTypeDO;
import com.mockuai.itemcenter.core.domain.RItemSuitDO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceDO;
import com.mockuai.itemcenter.core.domain.ValueAddedServiceTypeDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ValueAddedServiceManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by yindingyu on 15/12/4.
 */
@Service
public class ValueAddedServiceManagerImpl implements ValueAddedServiceManager {

    @Resource
    private ValueAddedServiceDAO valueAddedServiceDAO;

    @Resource
    private ValueAddedServiceTypeDAO valueAddedServiceTypeDAO;

    @Resource
    private RItemServiceTypeDAO rItemServiceTypeDAO;


    @Override
    public List<ValueAddedServiceTypeDTO> queryValueAddedServiceByItem(ItemDTO itemDTO) throws ItemException {


        ValueAddedServiceTypeQTO query2 = new ValueAddedServiceTypeQTO();
        query2.setSellerId(itemDTO.getSellerId());
        query2.setBizCode(itemDTO.getBizCode());
        query2.setScope(1);

        List<ValueAddedServiceTypeDTO> scope1 = queryValueAddedService(query2);

        RItemServiceTypeQTO query = new RItemServiceTypeQTO();
        query.setItemId(itemDTO.getId());
        query.setSellerId(itemDTO.getSellerId());
        query.setBizCode(itemDTO.getBizCode());

        List<RItemServiceTypeDO> rItemServiceTypeDTOList = rItemServiceTypeDAO.queryRItemSuitDO(query);

        if (rItemServiceTypeDTOList != null && rItemServiceTypeDTOList.size() > 0) {

            List<Long> typeIdList = Lists.newArrayList();

            for (RItemServiceTypeDO rItemServiceTypeDO : rItemServiceTypeDTOList) {
                typeIdList.add(rItemServiceTypeDO.getTypeId());
            }

            ValueAddedServiceTypeQTO query1 = new ValueAddedServiceTypeQTO();
            query1.setSellerId(itemDTO.getSellerId());
            query1.setBizCode(itemDTO.getBizCode());
            query1.setIdList(typeIdList);
            query1.setScope(2);

            List<ValueAddedServiceTypeDTO> scope2 = queryValueAddedService(query1);
            scope1.addAll(scope2);

        }


        return scope1;

    }

    @Override
    public List<ValueAddedServiceDTO> queryValueAddedService(ValueAddedServiceQTO valueAddedServiceQTO) {


        List<ValueAddedServiceDO> valueAddedServiceDOList = valueAddedServiceDAO.queryValueAddedService(valueAddedServiceQTO);

        List<ValueAddedServiceDTO> valueAddedServiceDTOList = Lists.newArrayListWithCapacity(10);

        for (ValueAddedServiceDO valueAddedServiceDO : valueAddedServiceDOList) {
            ValueAddedServiceDTO valueAddedServiceDTO = new ValueAddedServiceDTO();
            BeanUtils.copyProperties(valueAddedServiceDO, valueAddedServiceDTO);
            valueAddedServiceDTOList.add(valueAddedServiceDTO);
        }

        return valueAddedServiceDTOList;
    }

    @Override
    public Long addValueAddedService(ValueAddedServiceTypeDTO serviceDTO) throws ItemException {

        ValueAddedServiceTypeDO serviceTypeDO = new ValueAddedServiceTypeDO();

        String bizCode = serviceDTO.getBizCode();

        BeanUtils.copyProperties(serviceDTO, serviceTypeDO);

        Long result = valueAddedServiceTypeDAO.addValueAddedServiceType(serviceTypeDO);


        if (serviceDTO.getValueAddedServiceDTOList() == null && serviceDTO.getValueAddedServiceDTOList().size() < 1) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "增值服务类型下至少要有一种增值服务");
        }


        for (ValueAddedServiceDTO valueAddedServiceDTO : serviceDTO.getValueAddedServiceDTOList()) {

            ValueAddedServiceDO valueAddedServiceDO = new ValueAddedServiceDO();

            BeanUtils.copyProperties(valueAddedServiceDTO, valueAddedServiceDO);

            valueAddedServiceDO.setBizCode(bizCode);
            valueAddedServiceDO.setTypeId(result);
            valueAddedServiceDO.setSellerId(serviceDTO.getSellerId());

            valueAddedServiceDAO.addValueAddedService(valueAddedServiceDO);
        }

        if (serviceDTO.getScope().intValue() == 2) {    //适用于部分商品，则需要在关系表中添加记录

            if (serviceDTO.getItemIdList() == null || serviceDTO.getItemIdList().size() < 1) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "适用于部分商品时，商品id列表不能为空");
            }

            for (Long itemId : serviceDTO.getItemIdList()) {
                RItemServiceTypeDO rItemServiceTypeDO = new RItemServiceTypeDO();

                rItemServiceTypeDO.setBizCode(serviceDTO.getBizCode());
                rItemServiceTypeDO.setSellerId(serviceDTO.getSellerId());
                rItemServiceTypeDO.setTypeId(result);
                rItemServiceTypeDO.setItemId(itemId);
                rItemServiceTypeDO.setBizCode(bizCode);

                rItemServiceTypeDAO.addRItemServiceType(rItemServiceTypeDO);
            }
        }


        return result;

    }

    @Override
    public Long deleteValueAddedService(Long serviceId, Long sellerId, String bizCode) throws ItemException {
        ValueAddedServiceTypeDO query = new ValueAddedServiceTypeDO();
        query.setId(serviceId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);

        ValueAddedServiceTypeDO serviceTypeDO = valueAddedServiceTypeDAO.getValueAddedServiceType(query);

        if (serviceTypeDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "要删除的记录不存在");
        }

        Long result = valueAddedServiceTypeDAO.deleteValueAddedServiceType(query);

        return result;
    }

    @Override
    public ValueAddedServiceTypeDTO getValueAddedService(Long serviceId, Long sellerId, String bizCode) throws ItemException {

        ValueAddedServiceTypeDO query = new ValueAddedServiceTypeDO();
        query.setId(serviceId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);

        ValueAddedServiceTypeDO serviceTypeDO = valueAddedServiceTypeDAO.getValueAddedServiceType(query);

        ValueAddedServiceTypeDTO serviceTypeDTO = new ValueAddedServiceTypeDTO();

        BeanUtils.copyProperties(serviceTypeDO, serviceTypeDTO);

        ValueAddedServiceQTO valueAddedServiceQTO = new ValueAddedServiceQTO();

        valueAddedServiceQTO.setBizCode(bizCode);
        valueAddedServiceQTO.setSellerId(sellerId);
        valueAddedServiceQTO.setTypeId(serviceTypeDO.getId());


        List<ValueAddedServiceDO> valueAddedServiceDOList = valueAddedServiceDAO.queryValueAddedService(valueAddedServiceQTO);

        List<ValueAddedServiceDTO> valueAddedServiceDTOList = Lists.newArrayListWithCapacity(valueAddedServiceDOList.size());

        for (ValueAddedServiceDO valueAddedServiceDO : valueAddedServiceDOList) {

            ValueAddedServiceDTO valueAddedServiceDTO = new ValueAddedServiceDTO();
            BeanUtils.copyProperties(valueAddedServiceDO, valueAddedServiceDTO);
            valueAddedServiceDTOList.add(valueAddedServiceDTO);
        }

        serviceTypeDTO.setValueAddedServiceDTOList(valueAddedServiceDTOList);

        if (serviceTypeDO.getScope().intValue() == 2) {

            RItemServiceTypeQTO rItemServiceTypeQTO = new RItemServiceTypeQTO();

            rItemServiceTypeQTO.setBizCode(bizCode);
            rItemServiceTypeQTO.setSellerId(sellerId);
            rItemServiceTypeQTO.setTypeId(serviceTypeDO.getId());


            List<RItemServiceTypeDO> rItemServiceTypeDOList = rItemServiceTypeDAO.queryRItemSuitDO(rItemServiceTypeQTO);

            List<Long> itemIdList = Lists.newArrayListWithCapacity(rItemServiceTypeDOList.size());


            for (RItemServiceTypeDO rItemServiceTypeDO : rItemServiceTypeDOList) {
                itemIdList.add(rItemServiceTypeDO.getItemId());
            }

            serviceTypeDTO.setItemIdList(itemIdList);
        }

        return serviceTypeDTO;
    }

    @Override
    public List<ValueAddedServiceTypeDTO> queryValueAddedService(ValueAddedServiceTypeQTO serviceTypeQTO) throws ItemException {

        List<ValueAddedServiceTypeDO> valueAddedServiceTypeDOList = valueAddedServiceTypeDAO.queryValueAddedServiceType(serviceTypeQTO);

        List<ValueAddedServiceTypeDTO> valueAddedServiceTypeDTOList = Lists.newArrayListWithCapacity(valueAddedServiceTypeDOList.size());

        for (ValueAddedServiceTypeDO valueAddedServiceTypeDO : valueAddedServiceTypeDOList) {
            ValueAddedServiceTypeDTO valueAddedServiceTypeDTO = new ValueAddedServiceTypeDTO();

            BeanUtils.copyProperties(valueAddedServiceTypeDO, valueAddedServiceTypeDTO);

            ValueAddedServiceQTO valueAddedServiceQTO = new ValueAddedServiceQTO();

            valueAddedServiceQTO.setSellerId(serviceTypeQTO.getSellerId());
            valueAddedServiceQTO.setBizCode(serviceTypeQTO.getBizCode());
            valueAddedServiceQTO.setTypeId(valueAddedServiceTypeDO.getId());

            List<ValueAddedServiceDO> valueAddedServiceDOList = valueAddedServiceDAO.queryValueAddedService(valueAddedServiceQTO);

            List<ValueAddedServiceDTO> valueAddedServiceDTOList = Lists.newArrayListWithCapacity(valueAddedServiceDOList.size());

            for (ValueAddedServiceDO valueAddedServiceDO : valueAddedServiceDOList) {
                ValueAddedServiceDTO valueAddedServiceDTO = new ValueAddedServiceDTO();
                BeanUtils.copyProperties(valueAddedServiceDO, valueAddedServiceDTO);
                valueAddedServiceDTOList.add(valueAddedServiceDTO);

            }

            valueAddedServiceTypeDTO.setValueAddedServiceDTOList(valueAddedServiceDTOList);
            valueAddedServiceTypeDTOList.add(valueAddedServiceTypeDTO);

        }

        return valueAddedServiceTypeDTOList;
    }

    @Override
    public Long updateValueAddedService(ValueAddedServiceTypeDTO serviceDTO) throws ItemException {

        ValueAddedServiceTypeDO serviceTypeDO = new ValueAddedServiceTypeDO();

        BeanUtils.copyProperties(serviceDTO, serviceTypeDO);

        Long result = valueAddedServiceTypeDAO.updateValueAddedServiceType(serviceTypeDO);


        if (serviceDTO.getScope().intValue() == 2) {    //适用于部分商品，则需要在关系表中添加记录

            if (serviceDTO.getItemIdList() == null && serviceDTO.getItemIdList().size() < 1) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "适用于部分商品时，商品id列表不能为空");
            }

            //删除之前所有的商品关系数据
            rItemServiceTypeDAO.deleteRItemServiceByType(serviceTypeDO);

            //重新添加
            for (Long itemId : serviceDTO.getItemIdList()) {
                RItemServiceTypeDO rItemServiceTypeDO = new RItemServiceTypeDO();

                rItemServiceTypeDO.setBizCode(serviceDTO.getBizCode());
                rItemServiceTypeDO.setSellerId(serviceDTO.getSellerId());
                rItemServiceTypeDO.setTypeId(serviceDTO.getId());
                rItemServiceTypeDO.setItemId(itemId);
                rItemServiceTypeDO.setBizCode(serviceDTO.getBizCode());

                rItemServiceTypeDAO.addRItemServiceType(rItemServiceTypeDO);
            }
        }


        if (serviceDTO.getValueAddedServiceDTOList() == null && serviceDTO.getValueAddedServiceDTOList().size() < 1) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "增值服务类型下至少要有一种增值服务");
        }

        ValueAddedServiceQTO valueAddedServiceQTO = new ValueAddedServiceQTO();

        valueAddedServiceQTO.setBizCode(serviceDTO.getBizCode());
        valueAddedServiceQTO.setSellerId(serviceDTO.getSellerId());
        valueAddedServiceQTO.setTypeId(serviceDTO.getId());

        List<ValueAddedServiceDO> valueAddedServiceDOList = valueAddedServiceDAO.queryValueAddedService(valueAddedServiceQTO);


        Set<Long> oldIdSet = Sets.newHashSet();

        Set<Long> newIdSet = Sets.newHashSet();

        for (ValueAddedServiceDO valueAddedServiceDO : valueAddedServiceDOList) {
            oldIdSet.add(valueAddedServiceDO.getId());
        }

        for (ValueAddedServiceDTO valueAddedServiceDTO : serviceDTO.getValueAddedServiceDTOList()) {

            if (valueAddedServiceDTO.getId() != null) {
                newIdSet.add(valueAddedServiceDTO.getId());
            }
        }


        //求交集
        Sets.SetView<Long> modifiedIds = Sets.intersection(oldIdSet, newIdSet);

        for (ValueAddedServiceDTO valueAddedServiceDTO : serviceDTO.getValueAddedServiceDTOList()) {


            ValueAddedServiceDO valueAddedServiceDO = new ValueAddedServiceDO();
            BeanUtils.copyProperties(valueAddedServiceDTO, valueAddedServiceDO);
            valueAddedServiceDO.setSellerId(serviceDTO.getSellerId());
            valueAddedServiceDO.setBizCode(serviceDTO.getBizCode());
            valueAddedServiceDO.setTypeId(serviceDTO.getId());

            if (valueAddedServiceDTO.getId() == null) {  //新增
                valueAddedServiceDAO.addValueAddedService(valueAddedServiceDO);
            } else if (modifiedIds.contains(valueAddedServiceDTO.getId())) { //修改
                valueAddedServiceDAO.updateValueAddedService(valueAddedServiceDO);
            }
        }

        //求差集删除
        Sets.SetView<Long> deleteIds = Sets.difference(oldIdSet, newIdSet);

        for (Long id : deleteIds) {

            ValueAddedServiceDO query = new ValueAddedServiceDO();
            query.setId(id);
            query.setBizCode(serviceDTO.getBizCode());
            query.setSellerId(serviceDTO.getSellerId());

            valueAddedServiceDAO.deleteValueAddedService(query);
        }


        return result;
    }

}

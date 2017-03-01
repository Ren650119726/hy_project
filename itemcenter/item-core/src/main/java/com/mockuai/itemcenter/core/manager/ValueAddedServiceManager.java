package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.RItemServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceDTO;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceQTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
public interface ValueAddedServiceManager {

    List<ValueAddedServiceTypeDTO> queryValueAddedServiceByItem(ItemDTO itemDTO) throws ItemException;

    List<ValueAddedServiceDTO> queryValueAddedService(ValueAddedServiceQTO valueAddedServiceQTO) throws ItemException;

    Long addValueAddedService(ValueAddedServiceTypeDTO serviceDTO) throws ItemException;

    Long deleteValueAddedService(Long serviceId, Long sellerId, String bizCode) throws ItemException;

    ValueAddedServiceTypeDTO getValueAddedService(Long serviceId, Long sellerId, String bizCode) throws ItemException;

    List<ValueAddedServiceTypeDTO> queryValueAddedService(ValueAddedServiceTypeQTO serviceTypeQTO) throws ItemException;

    Long updateValueAddedService(ValueAddedServiceTypeDTO serviceDTO) throws ItemException;

}

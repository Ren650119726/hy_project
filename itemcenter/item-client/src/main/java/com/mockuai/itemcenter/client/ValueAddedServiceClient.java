package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ValueAddedServiceTypeDTO;
import com.mockuai.itemcenter.common.domain.qto.ValueAddedServiceTypeQTO;

import java.util.List;

/**
 * Created by yindingyu on 15/12/23.
 */
public interface ValueAddedServiceClient {

    Response<ValueAddedServiceTypeDTO> getValueAddedService(Long id,Long sellerId,String appKey);

    Response<List<ValueAddedServiceTypeDTO>> queryValueAddedService(ValueAddedServiceTypeQTO itemLabelQTO,String appKey);

    Response<Long> addValueAddedService(ValueAddedServiceTypeDTO itemLabelDTO,String appKey);

    Response<Long> deleteValueAddedService(Long id,Long sellerId,String appKey);

    Response<Long> updateValueAddedService(ValueAddedServiceTypeDTO itemLabelDTO,String appKey);
}

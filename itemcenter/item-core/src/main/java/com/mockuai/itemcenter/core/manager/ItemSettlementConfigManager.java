package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConfigDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConfigQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by yindingyu on 16/1/19.
 */
public interface ItemSettlementConfigManager {

    ItemSettlementConfigDTO getItemSettlementConfig(Long id, String bizCode) throws ItemException;

    Long enableItemSettlementConfig(Long id, String bizCode) throws ItemException;

    Long disableItemSettlementConfig(Long id, String bizCode) throws ItemException;

    Long deleteItemSettlementConfig(Long id, String bizCode) throws ItemException;

    List<ItemSettlementConfigDTO> queryItemSettlementConfig(ItemSettlementConfigQTO itemSettlementConfigQTO) throws ItemException;

    Long addItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO, String appKey) throws ItemException;

    Long updateItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO,String appkey) throws ItemException;

    Long calculateCommission(List<CommissionUnitDTO> commissionUnitDTOList,String bizCode) throws ItemException;
}

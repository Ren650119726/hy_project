package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.CommissionUnitDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSettlementConfigDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSettlementConfigQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/18.
 */
public interface CommissionClient {

    /**
     * 计算佣金
     * @param commissionUnitDTOList   佣金结算单位
     * @param appKey
     * @return
     */
    public Response<Long> calculateCommission(List<CommissionUnitDTO> commissionUnitDTOList,String appKey);

    /**
     * 获取结算配置详情
     * @param id
     * @param appKey
     * @return
     */
    public Response<ItemSettlementConfigDTO> getItemSettlementConfig(Long id,String appKey);

    /**
     * 删除结算配置
     * @param id
     * @param appKey
     * @return
     */
    public Response<Long> deleteItemSettlementConfig(Long id,String appKey);

    /**
     * 启用结算配置，会禁用与之冲突的配置
     * @param id
     * @param appKey
     * @return
     */
    public Response<Long> enableItemSettlementConfig(Long id,String appKey);

    /**
     * 禁用结算配置
     * @param id
     * @param appKey
     * @return
     */
    public Response<Long> disableItemSettlementConfig(Long id,String appKey);

    /**
     * 新建结算配置
     * @param itemSettlementConfigDTO
     * @param appKey
     * @return
     */
    public Response<Long> addItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO,String appKey);

    /**
     * 修改结算配置
     * @param itemSettlementConfigDTO
     * @param appKey
     * @return
     */
    public Response<Long> updateItemSettlementConfig(ItemSettlementConfigDTO itemSettlementConfigDTO,String appKey);

    /**
     * 查询结算配置列表
     * @param itemSettlementConfigQTO
     * @param appKey
     * @return
     */
    public Response<List<ItemSettlementConfigDTO>> queryItemSettlementConfig(ItemSettlementConfigQTO itemSettlementConfigQTO,String appKey);;

}

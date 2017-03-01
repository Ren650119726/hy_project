package com.mockuai.virtualwealthcenter.client;

import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.RechargeRecordDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.RechargeRecordQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;

import java.util.List;

/**
 * Created by duke on 16/4/19.
 */
public interface RechargeClient {
    /**
     * 添加充值虚拟商品
     *
     * @param virtualWealthItemDTOs
     * @param appKey
     */
    Response<Boolean> updateVirtualItem(List<VirtualWealthItemDTO> virtualWealthItemDTOs, String appKey);

    /**
     * 查询充值虚拟商品
     *
     * @param virtualWealthItemQTO
     * @param appKey
     */
    Response<List<VirtualWealthItemDTO>> queryVirtualItem(VirtualWealthItemQTO virtualWealthItemQTO, String appKey);

    /**
     * 删除充值虚拟商品
     *
     * @param id
     * @param appKey
     */
    Response<Boolean> deleteVirtualItem(Long id, String appKey);

    /**
     * 查询充值记录
     *
     * @param rechargeRecordQTO
     * @param appKey
     */
    Response<List<RechargeRecordDTO>> queryRechargeRecord(RechargeRecordQTO rechargeRecordQTO, String appKey);
}
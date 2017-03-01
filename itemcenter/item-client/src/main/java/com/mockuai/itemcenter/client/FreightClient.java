package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.common.domain.dto.area.AreaDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;

import java.util.List;
import java.util.Map;

/**
 * 运费相关操作  运费模板管理  运费计算
 * Created by yindingyu on 15/9/25.
 */
public interface FreightClient {

    Response<Long> calculateFreight(Map<Long,Integer> itemNums,Long userId,Long consigneeId,String appKey);


//    Response<Long> calculateFeight(Map<Long,Integer> itemNums,AddressDTO addressDTO,String appKey);

    Response<Long> addFreightTemplate(FreightTemplateDTO freightTemplateDTO,String appKey);

    Response<Boolean> updateFreightTemplate(FreightTemplateDTO freightTemplateDTO,String appKey);

    Response<FreightTemplateDTO> getFreightTemplate(long templateId,long sellerId,String appKey);


    Response<Boolean> deleteFreightTemplate(long templateId,long sellerId,String appKey);

    Response<List<FreightTemplateDTO>> queryFreightTemplate(FreightTemplateQTO freightTemplateQTO,String appKey);

    Response<Long> copyFreightTemplate(long templateId,long sellerId,String appKey);

    Response<List<AreaDTO>> queryAreas(String appkey);

}

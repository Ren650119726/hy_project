package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/9/22.
 */
public interface FreightTemplateManager {

    Long calculateFreight(Map<Long,Integer> itemNums);

    Long addFeightTemplate(FreightTemplateDTO freightTemplateDTO) throws ItemException;

    Boolean updateFeightTemplate(FreightTemplateDTO freightTemplateDTO) throws ItemException;

    FreightTemplateDTO getFreightTemplate(Long templateId, Long sellerId) throws ItemException;

    List<FreightTemplateDTO> queryFeightTemplate(FreightTemplateQTO freightTemplateQTO) throws ItemException;

    Boolean deleteFreightTemplate(Long templateId, Long sellerId) throws ItemException;
}

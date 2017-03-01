package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.FreightTemplateAreaInfoQTO;
import com.mockuai.itemcenter.core.domain.FreightTemplateAreaInfoDO;

import java.util.List;

/**
 * Created by yindingyu on 15/10/22.
 */
public interface FreightTemplateAreaInfoDAO {

    Long addAreaInfo(FreightTemplateAreaInfoDO areaInfoDO);

    Integer deleteAreaInfoByTemplateId(FreightTemplateAreaInfoQTO areaInfoQTO);

    List<FreightTemplateAreaInfoDO> queryAreaTemplate(FreightTemplateAreaInfoQTO areaInfoQTO);
}

package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.FreightAreaTemplateQTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateAreaInfoQTO;
import com.mockuai.itemcenter.core.domain.FreightAreaTemplateDO;

import java.util.List;

/**
 * Created by yindingyu on 15/9/24.
 */
public interface FreightAreaTemplateDAO {


    public Long addAreaTemplate(FreightAreaTemplateDO freightAreaTemplateDO);
    public FreightAreaTemplateDO getAreaTemplate(int id, int sellerId);


    public List<FreightAreaTemplateDO> queryAreaTemplate(FreightAreaTemplateQTO freightAreaTemplateQTO);
    public int updateAreaTemplate(FreightAreaTemplateDO freightAreaTemplateDO);
    public int deleteAreaTemplateByParentId(FreightAreaTemplateQTO freightAreaTemplateQTO);
    public int deleteAreaTemplate(FreightAreaTemplateQTO freightAreaTemplateDO);


}

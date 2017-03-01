package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;
import com.mockuai.itemcenter.core.domain.FreightTemplateDO;

import java.util.List;

/**
 * Created by yindingyu on 15/9/24.
 */
public interface FreightTemplateDAO {


    public Long addTemplate(FreightTemplateDO freightTemplateDO);

    public FreightTemplateDO getTemplate(FreightTemplateQTO freightTemplateQTO);

    public List<FreightTemplateDO> queryTemplate(FreightTemplateQTO freightTemplateQTO);


    public int updateTemplate(FreightTemplateDO freightTemplateDO);


    public int deleteTemplate(FreightTemplateQTO freightTemplateQTO);
}

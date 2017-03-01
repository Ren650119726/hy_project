package com.mockuai.suppliercenter.core.dao;

import com.mockuai.suppliercenter.common.qto.GlobalMessageQTO;
import com.mockuai.suppliercenter.core.domain.GlobalMessageDO;

import java.util.List;

public interface GlobalMessageDAO {

    public Long addGlobalMessage(GlobalMessageDO globalMessage);


    public List<GlobalMessageDO> queryGlobalMessage(GlobalMessageQTO globalMessageQTO);


    public Long getTotalCount(GlobalMessageQTO globalMessageQTO);

}

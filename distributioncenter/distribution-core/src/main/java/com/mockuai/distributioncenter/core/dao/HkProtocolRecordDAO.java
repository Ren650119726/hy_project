package com.mockuai.distributioncenter.core.dao;


import com.mockuai.distributioncenter.core.domain.HkProtocolRecordDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;

import java.util.List;

/**
 * Created by lizg on 2016/8/29.
 */
public interface HkProtocolRecordDAO {


    /**
     * 添加嗨客同意书
     * @param gainsSetDO
     * */
    Long add(HkProtocolRecordDO hkProtocolRecordDO);

    /**
     * 获取嗨客同意书s
     * @return
     */
    HkProtocolRecordDO get(HkProtocolRecordDO hkProtocolRecordDO);

    List<HkProtocolRecordDO> getByUserId(HkProtocolRecordDO hkProtocolRecordDO) throws DistributionException;



}

package com.mockuai.distributioncenter.core.dao;


import com.mockuai.distributioncenter.core.domain.GainsSetDO;

/**
 * Created by lizg on 2016/8/29.
 */
public interface GainsSetDAO {


    /**
     * 添加收益设置
     * @param gainsSetDO
     * */
    Long add(GainsSetDO gainsSetDO);

    /**
     * 获取收益设置
     * @return
     */
    GainsSetDO get();

    /**
     * 更新收益设置
     * @param gainsSetDO
     * @return
     */
    Integer update(GainsSetDO gainsSetDO);


}

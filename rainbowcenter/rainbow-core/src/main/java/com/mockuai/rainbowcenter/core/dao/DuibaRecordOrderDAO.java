package com.mockuai.rainbowcenter.core.dao;

import com.mockuai.rainbowcenter.core.domain.DuibaRecordOrderDO;


/**
 * Created by lizg on 2016/7/19.
 */
public interface DuibaRecordOrderDAO {

    /**
     * 新增兑吧订单记录
     * @param duibaRecordOrderDO
     * @return
     */
    Long addRecordOrder(DuibaRecordOrderDO duibaRecordOrderDO);


    /**
     * 根据兑吧订单号查询订单记录
     * @param duibaRecordOrderDO
     * @return
     */
    DuibaRecordOrderDO getRecordByOrderNum(DuibaRecordOrderDO duibaRecordOrderDO);


    /**
     * 根据兑吧订单号更新订单记录
     * @param duibaRecordOrderDO
     * @return
     */
    int updateStatusByorderNum(DuibaRecordOrderDO duibaRecordOrderDO);


    /**
     * 根据id删除记录
     * @param duibaRecordOrderDO
     * @return
     */
    int updateRemoveById(DuibaRecordOrderDO duibaRecordOrderDO);


}

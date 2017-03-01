package com.mockuai.rainbowcenter.core.dao;

import com.mockuai.rainbowcenter.core.domain.ErpOrderDO;

/**
 * Created by lizg on 2016/6/15.
 */
public interface ErpOrderDAO {
    /**
     * 增加管易订单;
     * @param erpOrderDO
     * @return
     */
    Long addErpOrder(ErpOrderDO erpOrderDO);

    /**
     * 根据订单id查询出单据编号;
     * @param erpOrderDO
     * @return
     */
    ErpOrderDO getGyerpCode(ErpOrderDO erpOrderDO);
}

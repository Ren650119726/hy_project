package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.common.qto.SupplierOrderStockQTO;
import com.mockuai.suppliercenter.core.dao.SupplierOrderStockDAO;
import com.mockuai.suppliercenter.core.domain.SupplierOrderStockDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierOrderStockDAOImpl extends SqlMapClientDaoSupport implements
        SupplierOrderStockDAO {

    public SupplierOrderStockDO getOrderStoreId(String orderSn, Long skuId, Long distributorId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderSn", orderSn);
        map.put("itemSkuId", skuId);
        map.put("distributorId", distributorId);
        SupplierOrderStockDO supplierOrderStockDO = (SupplierOrderStockDO) getSqlMapClientTemplate().queryForObject(
                "supplier_order_stock.getOrderStoreId", map);
        return supplierOrderStockDO;

    }

    public List<SupplierOrderStockDO> getOrderSkuByOrderSn(String orderSn) {
        List<SupplierOrderStockDO> supplierOrderStockDO = getSqlMapClientTemplate().queryForList(
                "supplier_order_stock.getOrderSkuByOrderSn", orderSn);

        return supplierOrderStockDO;
    }


    /**
     * 根据订单号、状态等查询订单sku锁定情况
     *
     * @param supplierOrderStockQTO
     * @return
     */
    public List<SupplierOrderStockDO> queryOrderSkuByOrderSn(SupplierOrderStockQTO supplierOrderStockQTO) {

        List<SupplierOrderStockDO> list = getSqlMapClientTemplate().queryForList(
                "supplier_order_stock.queryOrderSkuByOrderSn", supplierOrderStockQTO);

        return list;

    }

    /**
     * 根据订单id、仓库id、skuid，解锁库存
     *
     * @param supplierOrderStockDO
     * @return
     */
    public int unlockSupplierOrderNum(SupplierOrderStockDO supplierOrderStockDO) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "supplier_order_stock.updateSupplierOrderNumStatus",
                supplierOrderStockDO);
        return result;
    }


    public void addLockOrderStockNum(SupplierOrderStockDO supplierOrderStockDO) {

        this.getSqlMapClientTemplate().insert(
                "supplier_order_stock.addLockOrderStockNum",
                supplierOrderStockDO);

    }

    /**
     * 根据订单id、仓库id、skuid，在下单后把锁定状态改为下单减库存
     *
     * @param supplierOrderStockDO
     * @return
     */
    public int updateOrderStockStatusByOrderSn(SupplierOrderStockDO supplierOrderStockDO) {

        int result = getSqlMapClientTemplate().update(
                "supplier_order_stock.updateOrderStockStatusByOrderSn",
                supplierOrderStockDO);
        return result;

    }

    /**
     * 根据订单sn，在退单后状态改为4、退单回补库存
     *
     * @param supplierOrderStockDO
     * @return
     */
    public int returnStoreSkuStock(SupplierOrderStockDO supplierOrderStockDO) {

        int result = getSqlMapClientTemplate().update(
                "supplier_order_stock.updateSupplierOrderNumStatusByOrderSn",
                supplierOrderStockDO);
        return result;

    }

    /**
     * 根据订单id、仓库id、skuid，在退单后状态改为4、退单回补库存
     *
     * @param supplierOrderStockDO
     * @return
     */
    public int returnStoreSkuStockByOrderSku(SupplierOrderStockDO supplierOrderStockDO) {
        // TODO Auto-generated method stub
        int result = getSqlMapClientTemplate().update(
                "supplier_order_stock.updateSupplierOrderNumStatus",
                supplierOrderStockDO);
        return result;
    }
}

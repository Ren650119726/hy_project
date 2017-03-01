package com.mockuai.suppliercenter.core.dao.impl;

import com.mockuai.suppliercenter.common.qto.StoreItemSkuForOrderQTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.core.dao.StoreItemSkuDAO;
import com.mockuai.suppliercenter.core.domain.StoreItemSkuDO;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreItemSkuDAOImpl extends SqlMapClientDaoSupport implements StoreItemSkuDAO {

    public Long addStoreItemSku(StoreItemSkuDO storeItemSkuDO) {

        Long id = (Long) getSqlMapClientTemplate().insert("store_item_sku.insert",
                storeItemSkuDO);
        return id;
    }

    public StoreItemSkuDO getStoreItemSku(StoreItemSkuQTO storeItemSkuQTO) {
        // TODO Auto-generated method stub
        StoreItemSkuDO storeItemSkuDO = (StoreItemSkuDO) getSqlMapClientTemplate()
                .queryForObject("store_item_sku.getStoreItemSku", storeItemSkuQTO);
        return storeItemSkuDO;
    }


    public StoreItemSkuDO getStoreItemSkuById(Long id) {
        // TODO Auto-generated method stub
        StoreItemSkuDO storeItemSkuDO = (StoreItemSkuDO) getSqlMapClientTemplate()
                .queryForObject("store_item_sku.getStoreItemSkuById", id);
        return storeItemSkuDO;
    }

    /**
     * 查询符合查询条件的StoreItemSku
     * 
     * @author csy
     */
    @SuppressWarnings("unchecked")
	public List<StoreItemSkuDO> queryStoreItemSku(StoreItemSkuQTO storeItemSkuQTO) {
        List<StoreItemSkuDO> storeItemSkus = getSqlMapClientTemplate()
        		.queryForList("store_item_sku.selectStoreItemSku", storeItemSkuQTO);

        return storeItemSkus;
    }

    /**
     * 查询符合查询条件的供应商给订单使用
     */
    public List<StoreItemSkuDO> queryStoresItemSkuInfForOrder(StoreItemSkuForOrderQTO.StoreItme storeItme) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemSkuId", storeItme.getItemSkuId());
        map.put("orderNum", storeItme.getOrderNum());
        List<StoreItemSkuDO> storeItemSkus = getSqlMapClientTemplate().queryForList(

                "store_item_sku.selectStoreItemSkuInfForOrder", map);

        return storeItemSkus;

    }

    public StoreItemSkuDO queryStoresItemSkuNumForOrderOtherSku(Long itemSkuId, Long orderNum, Long storeId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemSkuId", itemSkuId);
        map.put("orderNum", orderNum);
        map.put("storeId", storeId);

        StoreItemSkuDO storeItemSkuDO = (StoreItemSkuDO) getSqlMapClientTemplate().queryForObject(
                "store_item_sku.queryStoresItemSkuNumForOrderOtherSku", map);

        return storeItemSkuDO;


    }

    /**
     * 查询商品sku库存信息数量
     * 
     * @author csy
     * @Date 2016-09-25
     */
    public Long getTotalCount(StoreItemSkuQTO storeItemSkuQTO) {
        Long num = (Long) getSqlMapClientTemplate().queryForObject("store_item_sku.totalCount", storeItemSkuQTO);

        return num;
    }

    public Long getStoreNumByStoreSku(StoreItemSkuQTO storeItemSkuQTO) {
        // TODO Auto-generated method stub
        Long num = (Long) getSqlMapClientTemplate().queryForObject(
                "store_item_sku.getStoreNumByStoreSku", storeItemSkuQTO);
        return num;
    }

    public Long getStoreTotelItemSku(Long storeId) {
        // TODO Auto-generated method stub
        Long num = (Long) getSqlMapClientTemplate().queryForObject(
                "store_item_sku.getStoreTotelItemSku", storeId);
        return num;
    }

    public int increaseStoreNum(Long storeId, Long itemSkuId, Long storeNum) {
        // TODO Auto-generated method stub

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        map.put("itemSkuId", itemSkuId);
        map.put("storeNum", storeNum);
        int result = getSqlMapClientTemplate().update(
                "store_item_sku.increaseStoreNum", map);
        return result;
    }

    public int reduceStoreNum(Long storeId, Long itemSkuId, Long storeNum,Integer deleteMark) {
        // TODO Auto-generated method stub

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        map.put("itemSkuId", itemSkuId);
        map.put("storeNum", storeNum);
        map.put("deleteMark",deleteMark);
        int result = getSqlMapClientTemplate().update(
                "store_item_sku.reduceStoreNum", map);
        return result;
    }

    public int reduceStoreAndNum(Long storeId, Long skuId, Long storeNum) {

        // TODO Auto-generated method stub

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("storeId", storeId);
        map.put("itemSkuId", skuId);
        map.put("storeNum", storeNum);
        int result = getSqlMapClientTemplate().update(
                "store_item_sku.reduceStoreAndNum", map);
        return result;

    }

    public int updateStoreItemSku(StoreItemSkuDO storeItemSkuDO) {

        int result = getSqlMapClientTemplate().update(
                "store_item_sku.updateStoreItemSku", storeItemSkuDO);
        return result;
    }

    public int updateStoreItemSkuNum(StoreItemSkuDO storeItemSkuDO) {

        int result = getSqlMapClientTemplate().update(
                "store_item_sku.updateStoreItemSkuNum", storeItemSkuDO);
        return result;
    }

    public int reduceStoreItemSkuNum(StoreItemSkuDO storeItemSkuDO) {

        int result = getSqlMapClientTemplate().update(
                "store_item_sku.reduceStoreItemSkuNum", storeItemSkuDO);
        return result;
    }

    public int cancleStoreItemSku(StoreItemSkuDO storeItemSkuDO) {

        int result = getSqlMapClientTemplate().update(
                "store_item_sku.cancleStoreItemSku", storeItemSkuDO);
        return result;
    }


    public int cancleStoreItemSkuList(List<Long> skuIdList) {
        int result = getSqlMapClientTemplate().update(
                "store_item_sku.cancleStoreItemSkuList", skuIdList);
        return result;
    }

    public List<StoreItemSkuDO> getStoreItemSkuListByStroeId(Long storeId) {
        List<StoreItemSkuDO> skuList = getSqlMapClientTemplate().queryForList(
                "store_item_sku.getStoreItemSkuListByStroeId", storeId);

        return skuList;
    }


    public Long getItemSkuIdTotleNumExpStoreId(StoreItemSkuQTO storeItemSkuQTO) {

        Long result = (Long) getSqlMapClientTemplate().queryForObject(
                "store_item_sku.getItemSkuIdTotleNumExpStoreId", storeItemSkuQTO);
        return result;

    }

    public Long getItemSkuIdTotleNum(StoreItemSkuQTO storeItemSkuQTO) {

        Long result = (Long) getSqlMapClientTemplate().queryForObject(
                "store_item_sku.getItemSkuIdTotleNumExpStoreId", storeItemSkuQTO);
        return result;

    }

    public List<StoreItemSkuDO> getStoreByItemSkuIdList(Long itemSkuId, Long number) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("itemSkuId", itemSkuId);
        map.put("number", number);
        List<StoreItemSkuDO> skuList = getSqlMapClientTemplate().queryForList("store_item_sku.getStoreByItemSkuIdList", map);
        return skuList;
    }


    public int updateStockToGyerpBySkuSn(StoreItemSkuDO storeItemSkuDO) {
        int result = getSqlMapClientTemplate().update("store_item_sku.updateStockToGyerpBySkuSn", storeItemSkuDO);
        return result;
    }

	@Override
	public int changeStoreItemSkuNum(StoreItemSkuDO storeItemSkuDO) {
		int result = getSqlMapClientTemplate().update("store_item_sku.changeStoreItemSkuNum", storeItemSkuDO);
        return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreItemSkuDO> queryStoreItemSkuByItemId(StoreItemSkuQTO storeItemSkuQTO) {
		List<StoreItemSkuDO> storeItemSkus = getSqlMapClientTemplate()
				.queryForList("store_item_sku.selectStoreItemSkuByItemId", storeItemSkuQTO);
        return storeItemSkus;
	}

}

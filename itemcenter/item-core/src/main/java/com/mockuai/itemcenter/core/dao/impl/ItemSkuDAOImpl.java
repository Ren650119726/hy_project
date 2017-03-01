package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.dao.ItemSkuDAO;
import com.mockuai.itemcenter.core.domain.ItemSkuDO;
import com.mockuai.itemcenter.core.domain.SkuCountDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemSkuDAOImpl extends SqlMapClientDaoSupport implements ItemSkuDAO {

    public ItemSkuDAOImpl() {
        super();
    }

    public Long addItemSku(ItemSkuDO itemSkuDO) {
        Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemSkuDAO.addItemSku", itemSkuDO);
        return newInsertedId;
    }

    public ItemSkuDO getItemSku(Long id, Long sellerId, String bizCode) {
        ItemSkuDO qto = new ItemSkuDO();
        qto.setId(id);
        qto.setSellerId(sellerId);
        qto.setBizCode(bizCode);
        ItemSkuDO record = (ItemSkuDO) getSqlMapClientTemplate().queryForObject("ItemSkuDAO.getItemSku", qto);
        return record;
    }

    public int deleteItemSku(Long id, Long sellerId, String bizCode) {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setId(id);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setBizCode(bizCode);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(id);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.deleteItemSku", itemSkuDO);
        return rows;
    }

    public int updateItemSku(ItemSkuDO itemSkuDO) {
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(itemSkuDO.getId());
//		parms.setSellerId(itemSkuDO.getSellerId());
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.updateItemSku", itemSkuDO);
        return rows;
    }

    public List<ItemSkuDO> queryItemSku(ItemSkuQTO itemSkuQTO) {

        if (null != itemSkuQTO.getNeedPaging() && itemSkuQTO.getNeedPaging().booleanValue()) {
            Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("ItemSkuDAO.countItemSku", itemSkuQTO);// 总记录数
            itemSkuQTO.setTotalCount((int) totalCount.longValue());
            if (totalCount == 0) {
                return new ArrayList<ItemSkuDO>();
            } else {
                itemSkuQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
            }
        }
        List<ItemSkuDO> list = getSqlMapClientTemplate().queryForList("ItemSkuDAO.queryItemSku", itemSkuQTO);
        return list;
    }

    @Override
    public Long countItemSku(ItemSkuQTO itemSkuQTO) {
        Long count = (Long) getSqlMapClientTemplate().queryForObject("ItemSkuDAO.countItemSku", itemSkuQTO);
        return count;
    }

    @Override
    public int increaseItemSkuStock(Long skuId, Long sellerId, Integer increasedNumber, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setStockNum(increasedNumber.longValue());
        itemSkuDO.setBizCode(bizCode);

//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.increaseItemSkuStock", itemSkuDO);
        return rows;
    }

    @Override
    public int freezeItemSkuStock(Long skuId, Long sellerId, Integer freezeNumber, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setFrozenStockNum(freezeNumber.longValue());
        itemSkuDO.setBizCode(bizCode);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.freezeItemSkuStock", itemSkuDO);
        return rows;
    }

    @Override
    public int thawItemSkuStock(Long skuId, Long sellerId, Integer thawNumber, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setFrozenStockNum(thawNumber.longValue());
        itemSkuDO.setBizCode(bizCode);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.thawItemSkuStock", itemSkuDO);
        return rows;
    }

    @Override
    public int crushItemSkuStock(Long skuId, Long sellerId, Integer crushNumber, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setFrozenStockNum(crushNumber.longValue());
        itemSkuDO.setStockNum(crushNumber.longValue());
        itemSkuDO.setBizCode(bizCode);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.crushItemSkuStock", itemSkuDO);
        return rows;
    }

    @Override
    public int resumeCrushedItemSkuStock(Long skuId, Long sellerId, Integer number, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setFrozenStockNum(number.longValue());
        itemSkuDO.setStockNum(number.longValue());
        itemSkuDO.setBizCode(bizCode);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.resumeCrushedItemSkuStock", itemSkuDO);
        return rows;
    }

    @Override
    public int decreaseItemSkuStock(Long skuId, Long sellerId, Integer decreasedNumber, String bizCode) throws ItemException {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setStockNum(decreasedNumber.longValue());
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setBizCode(bizCode);
//		WhereParms parms = new WhereParms(itemSkuDO);
//		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
//		parms.setId(skuId);
//		parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.decreaseItemSkuStock", itemSkuDO);
        return rows;
    }

    public int updateItemSkuCodeValue(Long skuId, Long sellerId, String codeValue, String bizCode) {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setSkuCode(codeValue);
        itemSkuDO.setId(skuId);
        itemSkuDO.setSellerId(sellerId);
        itemSkuDO.setSkuCode(codeValue);
        itemSkuDO.setBizCode(bizCode);
        //WhereParms parms = new WhereParms(itemSkuDO);
        //parms.setIsDeleted(DBConst.UN_DELETED.getCode());
        //parms.setId(skuId);
        //parms.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.updateItemSkuCodeValue", itemSkuDO);
        return rows;
    }

    public int deleteByItemId(ItemSkuDO itemSkuDO) {
        int result = this.getSqlMapClientTemplate().update("ItemSkuDAO.deleteByItemId", itemSkuDO);
        return result;
    }

    protected class WhereParms extends ItemSkuQTO {
        private Object record;

        public WhereParms(Object record) {
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }

    @Override
    public List<ItemSkuDO> queryStock(ItemSkuQTO itemSkuQTO) {
        return this.getSqlMapClientTemplate().queryForList("ItemSkuDAO.queryStock", itemSkuQTO);
    }

    @Override
    public List<SkuCountDO> querySkuCount(ItemSkuQTO itemSkuQTO) {
        return this.getSqlMapClientTemplate().queryForList("ItemSkuDAO.querySkuCount", itemSkuQTO);
    }

    @Override
    public Long trashByItemId(ItemSkuDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSkuDAO.trashByItemId", query));
    }

    @Override
    public Long recoveryByItemId(ItemSkuDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSkuDAO.recoveryByItemId", query));
    }

    @Override
    public Long emptyRecycleBin(ItemSkuDO query) {
        return Long.valueOf(getSqlMapClientTemplate().update("ItemSkuDAO.emptyRecycleBin", query));
    }

    @Override
    public Long countItemStock(ItemSkuDO query) {
        return (Long) getSqlMapClientTemplate().queryForObject("ItemSkuDAO.countItemStock", query);
    }

    @Override
    public List<ItemSkuDO> queryItemDynamic(ItemSkuQTO query) {
        return this.getSqlMapClientTemplate().queryForList("ItemSkuDAO.queryItemDynamic", query);

    }

    @Override
    public int updateItemSkuStock(Long skuId, Long sellerId, Long number, String bizCode) {
        ItemSkuDO itemSkuDO = new ItemSkuDO();
        itemSkuDO.setStockNum(number.longValue());
        itemSkuDO.setId(skuId);
        itemSkuDO.setBizCode(bizCode);
        itemSkuDO.setSellerId(sellerId);
        int rows = getSqlMapClientTemplate().update("ItemSkuDAO.updateItemSkuStock", itemSkuDO);
        return rows;
    }
}
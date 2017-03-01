package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.core.domain.ItemBrandDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.core.dao.ItemBrandDAO;

@Service
public class ItemBrandDAOImpl extends SqlMapClientDaoSupport implements ItemBrandDAO {

	public ItemBrandDAOImpl() {
		super();
	}

	public Long addItemBrand(ItemBrandDO itemBrandDO) {
		itemBrandDO.setIsDeleted(DBConst.UN_DELETED.getCode());// 默认删标志
		itemBrandDO.setBrandStatus(DBConst.NOT_AUDITED.getCode());// 品牌待审核状态
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ItemBrandDAO.addItemBrand", itemBrandDO);
		return newInsertedId;
	}

	public ItemBrandDO getItemBrand(Long id) {
		ItemBrandQTO qto = new ItemBrandQTO();
		qto.setIsDeleted(DBConst.UN_DELETED.getCode());
		qto.setId(id);
		ItemBrandDO record = (ItemBrandDO) getSqlMapClientTemplate()
				.queryForObject("ItemBrandDAO.getItemBrand", qto);
		return record;
	}

	public int deleteItemBrand(Long id) {
		ItemBrandDO itemBrandDO = new ItemBrandDO();
		itemBrandDO.setIsDeleted(DBConst.DELETED.getCode());
		WhereParms parms = new WhereParms(itemBrandDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(id);
		int rows = getSqlMapClientTemplate().update("ItemBrandDAO.deleteItemBrand", parms);
		return rows;
	}

	public int updateItemBrand(ItemBrandDO itemBrandDO) {
		WhereParms parms = new WhereParms(itemBrandDO);
		parms.setIsDeleted(DBConst.UN_DELETED.getCode());
		parms.setId(itemBrandDO.getId());
		int rows = getSqlMapClientTemplate().update("ItemBrandDAO.updateItemBrand", parms);
		return rows;
	}

	public List<ItemBrandDO> queryItemBrand(ItemBrandQTO itemBrandQTO) {
		itemBrandQTO.setIsDeleted(DBConst.UN_DELETED.getCode());
		if (null != itemBrandQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemBrandDAO.countBrand", itemBrandQTO);// 总记录数
			itemBrandQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemBrandDO>();
			} else {
				itemBrandQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
			totalCount = null;
		}
		List<ItemBrandDO> list = getSqlMapClientTemplate()
				.queryForList("ItemBrandDAO.queryItemBrandList", itemBrandQTO);
		return list;
	}

	protected class WhereParms extends ItemBrandQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}
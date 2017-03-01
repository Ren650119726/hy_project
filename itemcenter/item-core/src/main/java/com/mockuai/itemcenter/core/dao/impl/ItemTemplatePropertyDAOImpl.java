package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemTemplatePropertyQTO;
import com.mockuai.itemcenter.core.dao.ItemTemplatePropertyDAO;
import com.mockuai.itemcenter.core.domain.ItemTemplatePropertyDO;

@Service
public class ItemTemplatePropertyDAOImpl extends SqlMapClientDaoSupport implements ItemTemplatePropertyDAO {

	public ItemTemplatePropertyDAOImpl() {
		super();
	}

	public Long addItemProperty(ItemTemplatePropertyDO itemPropertyDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("ItemTmplPropertyDAO.addItemProperty", itemPropertyDO);
		return newInsertedId;
	}

	public ItemTemplatePropertyDO getItemProperty(Long id, Long sellerId) {
		ItemPropertyQTO qto = new ItemPropertyQTO();
		qto.setSellerId(sellerId);
		qto.setId(id);
		ItemTemplatePropertyDO record = (ItemTemplatePropertyDO) getSqlMapClientTemplate()
				.queryForObject("ItemTmplPropertyDAO.getItemProperty", qto);
		return record;
	}

	public int deleteItemProperty(Long id, Long sellerId) {
		ItemTemplatePropertyDO itemPropertyDO = new ItemTemplatePropertyDO();
		itemPropertyDO.setId(id);
		itemPropertyDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().delete("ItemTmplPropertyDAO.deleteItemProperty", itemPropertyDO);
		return rows;
	}

	public int updateItemProperty(ItemTemplatePropertyDO itemPropertyDO) {
		int rows = getSqlMapClientTemplate().update("ItemTmplPropertyDAO.updateItemProperty", itemPropertyDO);
		return rows;
	}

	public List<ItemTemplatePropertyDO> queryItemProperty(ItemTemplatePropertyQTO itemPropertyQTO) {
		Boolean needPaging = itemPropertyQTO.getNeedPaging();
		if (null != needPaging && (Boolean)needPaging) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemTmplPropertyDAO.countItemProperty", itemPropertyQTO);// 总记录数
			itemPropertyQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemTemplatePropertyDO>();
			} else {
				itemPropertyQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemTemplatePropertyDO> list = getSqlMapClientTemplate()
				.queryForList("ItemTmplPropertyDAO.queryItemProperty", itemPropertyQTO);
		return list;
	}

	public int deleteByItemId(ItemTemplatePropertyQTO itemPropertyQTO){
		return this.getSqlMapClientTemplate().update("ItemTmplPropertyDAO.deleteByitemTemplateId",itemPropertyQTO);
	}
	
	protected class WhereParms extends ItemPropertyQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}
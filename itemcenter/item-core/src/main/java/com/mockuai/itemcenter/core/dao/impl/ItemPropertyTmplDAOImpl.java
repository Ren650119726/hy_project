package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.core.dao.ItemPropertyTmplDAO;
import com.mockuai.itemcenter.core.domain.ItemPropertyTmplDO;

@Service
public class ItemPropertyTmplDAOImpl extends SqlMapClientDaoSupport implements ItemPropertyTmplDAO {

	public ItemPropertyTmplDAOImpl() {
		super();
	}

	public Long addItemPropertyTmpl(ItemPropertyTmplDO itemPropertyTmplDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("ItemPropertyTmplDAO.addItemPropertyTmpl", itemPropertyTmplDO);
		return newInsertedId;
	}

	public ItemPropertyTmplDO getItemPropertyTmpl(Long id) {
		ItemPropertyTmplDO qto = new ItemPropertyTmplDO();
		qto.setId(id);
		ItemPropertyTmplDO record = (ItemPropertyTmplDO) getSqlMapClientTemplate()
				.queryForObject("ItemPropertyTmplDAO.getItemPropertyTmpl", qto);
		return record;
	}

	public int deleteItemPropertyTmpl(Long id) {
		ItemPropertyTmplDO itemPropertyTmplDO = new ItemPropertyTmplDO();
		itemPropertyTmplDO.setId(id);
		int rows = getSqlMapClientTemplate().delete("ItemPropertyTmplDAO.deleteItemPropertyTmpl", itemPropertyTmplDO);
		return rows;
	}

	public int updateItemPropertyTmpl(ItemPropertyTmplDO itemPropertyTmplDO) {
		int rows = getSqlMapClientTemplate().update("ItemPropertyTmplDAO.updateItemPropertyTmpl", itemPropertyTmplDO);
		return rows;
	}

	public List<ItemPropertyTmplDO> queryItemPropertyTmpl(ItemPropertyTmplQTO itemPropertyTmplQTO) {
		Boolean needPaging = itemPropertyTmplQTO.getNeedPaging();
		if (null != needPaging  && needPaging.booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemPropertyTmplDAO.countItemPropertyTmpl", itemPropertyTmplQTO);// 总记录数
			itemPropertyTmplQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemPropertyTmplDO>();
			} else {
				itemPropertyTmplQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemPropertyTmplDO> list = getSqlMapClientTemplate()
				.queryForList("ItemPropertyTmplDAO.queryItemPropertyTmpl", itemPropertyTmplQTO);
		return list;
	}

	public List<ItemPropertyTmplDO> queryItemPropertyTmplWithValue(ItemPropertyTmplQTO itemPropertyTmplQTO){
		Boolean needPaging = itemPropertyTmplQTO.getNeedPaging();
		if (null != needPaging  && needPaging.booleanValue()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemPropertyTmplDAO.countItemPropertyTmpl", itemPropertyTmplQTO);// 总记录数
			itemPropertyTmplQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemPropertyTmplDO>();
			} else {
				itemPropertyTmplQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemPropertyTmplDO> list = getSqlMapClientTemplate()
				.queryForList("ItemPropertyTmplDAO.queryItemPropertyTmplWithValue", itemPropertyTmplQTO);
		return list;
	}

}
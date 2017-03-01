package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.core.dao.ItemCommentDAO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;

@Service
public class ItemCommentDAOImpl extends SqlMapClientDaoSupport implements ItemCommentDAO {

	public ItemCommentDAOImpl() {
		super();
	}

	public Long addItemComment(ItemCommentDO itemCommentDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate()
				.insert("ItemCommentDAO.addItemComment", itemCommentDO);
		return newInsertedId;
	}

	public ItemCommentDO getItemComment(Long id, Long sellerId) {
		ItemCommentQTO queryQTO = new ItemCommentQTO();
		queryQTO.setId(id);
		queryQTO.setSellerId(sellerId);
		ItemCommentDO record = (ItemCommentDO) getSqlMapClientTemplate()
				.queryForObject("ItemCommentDAO.getItemComment", queryQTO);
		return record;
	}

	/**
	 *获取商品评分等级
	 */
	public List<ItemCommentDO> queryItemCommentGrade(ItemCommentQTO itemCommentQTO) {
		if (null != itemCommentQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemCommentDAO.countItemComment", itemCommentQTO);// 总记录数
			itemCommentQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemCommentDO>();
			} else {
				itemCommentQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemCommentDO> list = getSqlMapClientTemplate()
				.queryForList("ItemCommentDAO.queryItemCommentGrade", itemCommentQTO);
		return list;
	}

	@Override
	public CountCommentDTO countItemCommentGrade(ItemCommentQTO itemCommentQTO) {
		CountCommentDTO result = (CountCommentDTO) getSqlMapClientTemplate()
				.queryForObject("ItemCommentDAO.countItemCommentGrade", itemCommentQTO);
		return result;
	}

	public int updateItemComment(ItemCommentDO itemCommentDO) {
		int rows = getSqlMapClientTemplate().update("ItemCommentDAO.updateItemComment", itemCommentDO);
		return rows;
	}

	public List<ItemCommentDO> queryItemComment(ItemCommentQTO itemCommentQTO) {
		if (null != itemCommentQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ItemCommentDAO.countItemComment", itemCommentQTO);// 总记录数
			itemCommentQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemCommentDO>();
			} else {
				itemCommentQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		List<ItemCommentDO> list = getSqlMapClientTemplate()
				.queryForList("ItemCommentDAO.queryItemComment", itemCommentQTO);
		return list;
	}

	@Override
	public Long countItemComment(ItemCommentQTO itemCommentQTO) {
		return ((Integer) getSqlMapClientTemplate().queryForObject("ItemCommentDAO.countItemComment",itemCommentQTO)).longValue();
	}

	@Override
	public int deleteItemCommentPhysically(Long id, Long sellerId) {
		ItemCommentDO itemCommentDO = new ItemCommentDO();
		itemCommentDO.setId(id);
		itemCommentDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().delete("ItemCommentDAO.deleteItemCommentPhysically", itemCommentDO);
		return rows;
	}

	@Override
	public int deleteItemComment(Long id, Long sellerId) {
		ItemCommentDO itemCommentDO = new ItemCommentDO();
		itemCommentDO.setId(id);
		itemCommentDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().update("ItemCommentDAO.deleteItemComment", itemCommentDO);
		return rows;
	}

	protected class WhereParms extends ItemCommentQTO {
		private Object record;

		public WhereParms(Object record) {
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}

}
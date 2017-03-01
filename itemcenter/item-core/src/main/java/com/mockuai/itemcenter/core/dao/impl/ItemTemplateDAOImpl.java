package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.itemcenter.core.dao.ItemTemplateDAO;
import com.mockuai.itemcenter.core.domain.ItemTemplateDO;

@Service
public class ItemTemplateDAOImpl extends SqlMapClientDaoSupport implements ItemTemplateDAO{

	@Override
	public Long addItemTemplate(ItemTemplateDO itemTemplateDO) {
		return(Long) this.getSqlMapClientTemplate().insert("ItemTemplateDAO.addItemTemplate",itemTemplateDO);
	}

	@Override
	public int DeleteItemTemplate(ItemTemplateDO itemTemplateDO) {
		
		return (int)this.getSqlMapClientTemplate().update("ItemTemplateDAO.deleteItemTemplate",itemTemplateDO);
	}

	@Override
	public ItemTemplateDO getItemTemplate(ItemTemplateDO itemTemplateDO) {
		return (ItemTemplateDO)this.getSqlMapClientTemplate().queryForObject("ItemTemplateDAO.getItemTemplate",itemTemplateDO);
	}

	@Override
	public int updateItemTemplate(ItemTemplateDO itemTemplateDO) {
		return (Integer)this.getSqlMapClientTemplate().update("ItemTemplateDAO.updateItemTemplate", itemTemplateDO);
	}
	
	@Override
	public List<ItemTemplateDO> queryItemTemplate(ItemTemplateQTO itemTemplateQTO){
		Boolean needPaging = itemTemplateQTO.getNeedPaging();
		if (needPaging != null && needPaging) {
			Integer totalCount = (Integer) getSqlMapClientTemplate().queryForObject("ItemTemplateDAO.getTotalCount", itemTemplateQTO);// 总记录数
			itemTemplateQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ItemTemplateDO>();
			} else {
				itemTemplateQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		System.out.println(itemTemplateQTO.getOffset());
		System.out.println(itemTemplateQTO.getPageSize());
		return this.getSqlMapClientTemplate().queryForList("ItemTemplateDAO.queryItemTemplate",itemTemplateQTO);
	}
	
	@Override
	public Integer getTotalCount(ItemTemplateQTO itemTemplateQTO){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("ItemTemplateDAO.getTotalCount",itemTemplateQTO);
	}
	
}

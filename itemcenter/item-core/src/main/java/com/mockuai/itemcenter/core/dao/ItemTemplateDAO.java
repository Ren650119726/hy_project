package com.mockuai.itemcenter.core.dao;

import java.util.List;

import com.mockuai.itemcenter.common.domain.qto.ItemTemplateQTO;
import com.mockuai.itemcenter.core.domain.ItemTemplateDO;

/**
 * 定义itemTemplate数据库操作接口类
 * @author cwr
 *
 */
public interface ItemTemplateDAO {
	
	public Long addItemTemplate(ItemTemplateDO itemTemplateDO);
	
	public int DeleteItemTemplate(ItemTemplateDO itemTemplateDO);
	
	public ItemTemplateDO getItemTemplate(ItemTemplateDO itemTemplateDO);

	public int updateItemTemplate(ItemTemplateDO itemTemplateDO);
	
	public List<ItemTemplateDO> queryItemTemplate(ItemTemplateQTO itemTemplateQTO);
	
	public Integer getTotalCount(ItemTemplateQTO itemTemplateQTO);
	
	
}

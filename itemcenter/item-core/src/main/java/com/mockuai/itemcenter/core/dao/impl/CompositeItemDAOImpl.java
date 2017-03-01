package com.mockuai.itemcenter.core.dao.impl;

import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.core.dao.CompositeItemDAO;
import com.mockuai.itemcenter.core.domain.CompositeItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompositeItemDAOImpl extends SqlMapClientDaoSupport implements CompositeItemDAO {

	@Override
	public Long addCompositeItem(CompositeItemDO compositeItemDO) {
		return (Long)this.getSqlMapClientTemplate().insert("CompositeItemDAO.addCompositeItem",compositeItemDO);
	}

    @Override
    public void batchAddCompositeItem(List<CompositeItemDO> compositeItemDO) {
       this.getSqlMapClientTemplate().insert("CompositeItemDAO.batchAddCompositeItem",compositeItemDO);
    }

	@Override
	public int deleteCompositeItemByItemId(Long itemId) {
		CompositeItemDO compositeItemDO = new CompositeItemDO();
		compositeItemDO.setItemId(itemId);
		int result = this.getSqlMapClientTemplate().update("CompositeItemDAO.deleteByItemId",compositeItemDO);
		return result;
	}

	@Override
	public List<CompositeItemDO> getCompositeItemByItemId(Long id
			) {
		CompositeItemDO compositeItemDO = new CompositeItemDO();
        compositeItemDO.setItemId(id);
		List<CompositeItemDO> list = this.getSqlMapClientTemplate().queryForList("CompositeItemDAO.queryByItemId",compositeItemDO);
		return list;
	}

    @Override
    public List<CompositeItemDO> queryCompositeItemByItemIdList(List<Long> itemIdList) {
        List<CompositeItemDO> list = this.getSqlMapClientTemplate().queryForList("CompositeItemDAO.queryCompositeItemByItemIdList",itemIdList);
        return list;
    }

    @Override
    public List<CompositeItemDO> queryCompositeItemByItemSkuQTO(ItemSkuQTO itemSkuQTO) {
        List<CompositeItemDO> list = this.getSqlMapClientTemplate().queryForList("CompositeItemDAO.queryCompositeItemByItemSkuQTO",itemSkuQTO);
        return list;
    }

}

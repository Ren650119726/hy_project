package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.dataCenter.CategoryDateQTO;
import com.mockuai.tradecenter.core.dao.DataDAO;
import com.mockuai.tradecenter.core.dao.DataDAO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.domain.OrderItemDO;
import com.mockuai.tradecenter.core.domain.DailyDataDO;
import com.mockuai.tradecenter.core.domain.SalesRatioDO;
import com.mockuai.tradecenter.core.domain.TOPItemDO;

public class DataDAOImpl  extends SqlMapClientDaoSupport implements DataDAO{

	@Override
	public List<TOPItemDO> getTOP10Item(DataQTO dataQTO) {
		return (List<TOPItemDO>)this.getSqlMapClientTemplate().queryForList("user_order.getTop10Item",dataQTO);
	}
	
	@Override
	public Long getTotalAmount(DataQTO dataQTO) {
//			Long temp =  (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getTotalAmount", dataQTO);
//			if(temp==null)
//				System.out.println("*************************************************null");
			return  (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getTotalAmount", dataQTO);
	}


	@Override
	public long getTotalOrderCount(DataQTO dataQTO) {
		return (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getTotalOrderCount", dataQTO);
	}

	@Override
	public long getPaidOrderCount(DataQTO dataQTO) {
		return (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getPaidOrderCount", dataQTO);
	}

	@Override
	public long getTotalUserCount(DataQTO dataQTO) {
		return (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getTotalUserCount", dataQTO);
	}

	@Override
	public long getPaidUserCount(DataQTO dataQTO) {
		return (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getPaidUserCount", dataQTO);
	}

	@Override
	public long getOldUserCount(DataQTO dataQTO) {
		return (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getOldUserCount", dataQTO);
	}

	@Override
	public List<DailyDataDO> getTotalAmountDaily(DataQTO dataQTO) {
		return (List<DailyDataDO>)this.getSqlMapClientTemplate().queryForList("user_order.getTotalAmoutDaily", dataQTO);
	}

	@Override
	public List<DailyDataDO> getTotalOrderCountDaily(DataQTO dataQTO) {
		return (List<DailyDataDO>)this.getSqlMapClientTemplate().queryForList("user_order.getTotalOrderCountDaily", dataQTO);
	}

	@Override
	public List<DailyDataDO> getPaidOrderCountDaily(DataQTO dataQTO) {
		return (List<DailyDataDO>)this.getSqlMapClientTemplate().queryForList("user_order.getPaidOrderCountDaily", dataQTO);
	}

	@Override
	public List<DailyDataDO> getTotalUserCountDaily(DataQTO dataQTO) {
		return (List<DailyDataDO>)this.getSqlMapClientTemplate().queryForList("user_order.getTotalUserCountDaily", dataQTO);
	}

	@Override
	public List<DailyDataDO> getPaidUserCountDaily(DataQTO dataQTO) {
		return (List<DailyDataDO>)this.getSqlMapClientTemplate().queryForList("user_order.getPaidUserCountDaily", dataQTO);
	}

	@Override
	public List<SalesRatioDO> querySalesRatioByCategory(DataQTO query) {
		return this.getSqlMapClientTemplate().queryForList("user_order.querySalesRatioByCategory", query);
	}

	@Override
	public List<SalesRatioDO> querySalesRatioByBrand(DataQTO query) {
		return this.getSqlMapClientTemplate().queryForList("user_order.querySalesRatioByBrand", query);
	}

	@Override
	public Long getAllTotalAmount(DataQTO dataQTO) {
		return  (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getAllTotalAmount", dataQTO);
	}

	@Override
	public Long getItemCount(DataQTO query) {
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getItemCount", query);
	}

	@Override
	public Long getPaidItemCount(DataQTO query) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getPaidItemCount", query);
	}

	@Override
	public Long getPaidCountBySkuId(DataQTO query) {
		// TODO Auto-generated method stub
		return (Long) this.getSqlMapClientTemplate().queryForObject("user_order.getPaidCountBySkuId", query);
	}

	@Override
	public List<OrderItemDO> queryCategoryTopSaleItems(CategoryDateQTO query) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("order_item.queryCategoryTopSaleItems",query);
	}

	@Override
	public Long getSumOrderTotalPrice(DataQTO dataQTO) {
		return  (Long)this.getSqlMapClientTemplate().queryForObject("user_order.getTotalPrice", dataQTO);
	}

}

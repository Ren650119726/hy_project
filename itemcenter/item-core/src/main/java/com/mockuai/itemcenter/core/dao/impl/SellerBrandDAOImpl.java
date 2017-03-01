package com.mockuai.itemcenter.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.mockuai.itemcenter.core.domain.ItemDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.qto.SellerBrandQTO;
import com.mockuai.itemcenter.core.dao.SellerBrandDAO;
import com.mockuai.itemcenter.core.domain.SellerBrandDO;

@Service
public class SellerBrandDAOImpl extends SqlMapClientDaoSupport implements SellerBrandDAO {

	@Override
	public List<SellerBrandDO> querySellerBrand(SellerBrandQTO sellerBrandQTO) {

		if (null != sellerBrandQTO.getNeedPaging() && sellerBrandQTO.getNeedPaging().booleanValue()) {
			Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("SellerBrandDAO.countSellerBrand", sellerBrandQTO);// 总记录数
            sellerBrandQTO.setTotalCount((int)totalCount.longValue());
			if (totalCount == 0) {
				return new ArrayList<SellerBrandDO>();
			} else {
				sellerBrandQTO.setOffsetAndTotalPage();// 设置总页数和跳过的行数
			}
		}
		return (List<SellerBrandDO>)this.getSqlMapClientTemplate().queryForList("SellerBrandDAO.querySellerBrand",sellerBrandQTO);
	}

	@Override
	public Long addSellerBrand(SellerBrandDO sellerBrandDO) {
		return  (Long)this.getSqlMapClientTemplate().insert("SellerBrandDAO.addSellerBrand",sellerBrandDO);
	}

	@Override
	public int deleteSellerBrand(SellerBrandDO sellerBrandDO) {
		return this.getSqlMapClientTemplate().update("SellerBrandDAO.deleteSellerBrand",sellerBrandDO);
	}

	@Override
	public SellerBrandDO getSellerBrand(SellerBrandDO sellerBrandDO) {
		return (SellerBrandDO)this.getSqlMapClientTemplate().queryForObject("SellerBrandDAO.getSellerBrand",sellerBrandDO);
	}




	@Override
	public int updateSellerBrand(SellerBrandDO sellerBrandDO) {
		return this.getSqlMapClientTemplate().update("SellerBrandDAO.updateSellerBrand",sellerBrandDO);
	}
}

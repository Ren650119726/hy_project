package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopCollectionDAO;
import com.mockuai.shopcenter.core.domain.ShopCollectionDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCollectionDAOImpl extends SqlMapClientDaoSupport implements ShopCollectionDAO {

	public Long addShopCollection(ShopCollectionDO ShopCollectionDO) {
		Long newInsertedId = (Long) getSqlMapClientTemplate().insert("ShopCollection.addShopCollection", ShopCollectionDO);
		return newInsertedId;
	}

	public int deleteShopCollection(Long sellerId,Long userId) {
		ShopCollectionDO shopCollectionDO = new ShopCollectionDO();
		shopCollectionDO.setUserId(userId);
		shopCollectionDO.setSellerId(sellerId);
		int rows = getSqlMapClientTemplate().delete("ShopCollection.deleteShopCollectionByShopId", shopCollectionDO);
		return rows;
	}

	public List<ShopCollectionDO> queryShopCollection(ShopCollectionQTO ShopCollectionQTO) {
		if (null != ShopCollectionQTO.getNeedPaging()) {
			Integer totalCount = (Integer) getSqlMapClientTemplate()
					.queryForObject("ShopCollection.countShopCollection", ShopCollectionQTO);// 总记录数
			ShopCollectionQTO.setTotalCount(totalCount);
			if (totalCount == 0) {
				return new ArrayList<ShopCollectionDO>();
			}
		}
		List<ShopCollectionDO> list = getSqlMapClientTemplate()
				.queryForList("ShopCollection.queryShopCollectionList", ShopCollectionQTO);
		return list;
	}

    @Override
    public Integer countShopCollection(ShopCollectionQTO query) {
        return (Integer) getSqlMapClientTemplate().queryForObject("ShopCollection.countShopCollection",query);
    }

    @Override
	public ShopCollectionDO getShopCollection(Long sellerId, Long userId)  throws ShopException {

		ShopCollectionDO query = new ShopCollectionDO();
		query.setUserId(userId);
		query.setSellerId(sellerId);
		query.setDeleteMark(0);

		ShopCollectionDO shopCollectionDO = null;
		try {
			shopCollectionDO = (ShopCollectionDO)getSqlMapClientTemplate()
					.queryForObject("ShopCollection.getShopCollection", query);
		} catch (Throwable e) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table Shop-->sellerId:"
							+ sellerId);
		}
		return shopCollectionDO;
	}

}
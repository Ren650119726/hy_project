package com.mockuai.tradecenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.tradecenter.core.dao.RefundItemImageDAO;
import com.mockuai.tradecenter.core.domain.RefundItemImageDO;

public class RefundItemImageDAOImpl extends SqlMapClientDaoSupport implements RefundItemImageDAO {

	@Override
	public Long addRefundItemImage(RefundItemImageDO refundItemImageDO) {
		return (Long) this.getSqlMapClientTemplate().insert("refund_item_image.addRefundItemImage", refundItemImageDO);
	}

	@Override
	public List<RefundItemImageDO> queryRefundItemImage(Long refundItemId) {
		RefundItemImageDO query = new RefundItemImageDO();
		query.setRefundItemLogId(refundItemId);
		return this.getSqlMapClientTemplate().queryForList("refund_item_image.query", query);
	}

}

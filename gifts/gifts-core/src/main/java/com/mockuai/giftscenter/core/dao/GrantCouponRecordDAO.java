package com.mockuai.giftscenter.core.dao;

import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;
import com.mockuai.giftscenter.core.domain.GrantCouponRecordDO;

import java.util.List;

/**
 * Created by guansheng  15/7/16
 */
public interface GrantCouponRecordDAO {

   Long insert(List<GrantCouponRecordDO> data);

   List<GrantCouponRecordDO> queryAll(GrantCouponRecordQTO qto);

   int queryTotalCount(GrantCouponRecordQTO qto);

}
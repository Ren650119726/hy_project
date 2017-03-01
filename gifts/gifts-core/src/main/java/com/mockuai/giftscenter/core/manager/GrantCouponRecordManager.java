package com.mockuai.giftscenter.core.manager; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.common.domain.dto.GrantCouponRecordDTO;
import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;
import com.mockuai.giftscenter.core.domain.GrantCouponRecordDO;

import java.util.List;

/**
 * Created by guansheng on 2016/7/18.
 */
public interface GrantCouponRecordManager {

    List<GrantCouponRecordDTO>  queryAll(GrantCouponRecordQTO qto);


    void save(List<GrantCouponRecordDO> grantCouponRecordDO);

    int queryTotalCount(GrantCouponRecordQTO qto);
}

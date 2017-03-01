package com.mockuai.giftscenter.core.service.action.gifts;

import com.mockuai.giftscenter.common.api.GiftsResponse;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import com.mockuai.giftscenter.common.domain.dto.GrantCouponRecordDTO;
import com.mockuai.giftscenter.common.domain.dto.PageDTO;
import com.mockuai.giftscenter.common.domain.qto.GrantCouponRecordQTO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.GrantCouponRecordManager;
import com.mockuai.giftscenter.core.service.RequestContext;
import com.mockuai.giftscenter.core.service.action.TransAction;
import com.mockuai.giftscenter.core.util.GiftsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 查询有礼活动领取记录
 */
@Service
public class QueryGrantCouponAction extends TransAction {

    @Autowired
    private GrantCouponRecordManager grantCouponRecordManager;
    
    @Override
    protected GiftsResponse doTransaction(RequestContext context) throws GiftsException {
        Request request = context.getRequest();
        String appKey = (String) request.getParam("appKey");
        GrantCouponRecordQTO grantCouponRecordQTO = (GrantCouponRecordQTO) request.getParam("grantCouponRecordQTO");
        List<GrantCouponRecordDTO> grantCouponRecordDTOList =   grantCouponRecordManager.queryAll(grantCouponRecordQTO);
        int count =   grantCouponRecordManager.queryTotalCount(grantCouponRecordQTO);
        PageDTO<List<GrantCouponRecordDTO>> page = new PageDTO<>();
        page.setData(grantCouponRecordDTOList);
        page.setTotalCount((long) count);
        return GiftsUtils.getSuccessResponse(page);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_GRANT_COUPON_RECORD.getActionName();
    }
}
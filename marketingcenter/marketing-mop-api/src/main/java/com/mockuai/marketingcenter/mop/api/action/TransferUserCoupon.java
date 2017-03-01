package com.mockuai.marketingcenter.mop.api.action;

import com.mockuai.marketingcenter.common.api.BaseRequest;
import com.mockuai.marketingcenter.common.api.Request;
import com.mockuai.marketingcenter.common.api.Response;
import com.mockuai.marketingcenter.common.domain.dto.mop.UserCouponUidDTO;
import com.mockuai.marketingcenter.common.util.MopApiUtil;
import com.mockuai.marketingcenter.mop.api.util.JsonUtil;
import com.mockuai.mop.common.constant.ActionAuthLevel;
import com.mockuai.mop.common.constant.HttpMethodLimit;
import com.mockuai.mop.common.constant.MopRespCode;
import com.mockuai.mop.common.service.action.MopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mockuai.marketingcenter.common.constant.ActionEnum.TRANSFER_USER_COUPON;

/**
 * Created by zengzhangqiang on 5/31/15.
 */
public class TransferUserCoupon extends BaseAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferUserCoupon.class.getName());

    public MopResponse execute(com.mockuai.mop.common.service.action.Request request) {
        String couponUidListStr = (String) request.getParam("coupon_uid_list");
        String toUserIdStr = (String) request.getParam("to_user_id");
        String appKey = (String) request.getParam("app_key");

        List<Long> couponIdList = new ArrayList<Long>();
        Long fromUserId = null;
        Long toUserId = null;
        try {
            List<String> couponUidList = JsonUtil.parseJson(couponUidListStr, List.class);
            for (String couponUid : couponUidList) {
                UserCouponUidDTO userCouponUidDTO = MopApiUtil.parseUserCouponUid(couponUid);
                couponIdList.add(userCouponUidDTO.getUserCouponId());

                //随便取第一个userId作为fromUserId
                if (fromUserId == null) {
                    fromUserId = userCouponUidDTO.getUserId();
                }
            }
            toUserId = Long.valueOf(toUserIdStr);
        } catch (Exception e) {
            LOGGER.error("errors of parsing, couponUidList : {}, toUserId : {}", couponUidListStr, toUserIdStr);
            return new MopResponse(MopRespCode.P_E_PARAM_FORMAT_INVALID);
        }

        Request marketReq = new BaseRequest();
        marketReq.setCommand(TRANSFER_USER_COUPON.getActionName());
        marketReq.setParam("userCouponIdList", couponIdList);
        marketReq.setParam("fromUserId", fromUserId);
        marketReq.setParam("toUserId", toUserId);
        marketReq.setParam("appKey", appKey);
        Response marketResp = getMarketingService().execute(marketReq);
        MopResponse response;
        if (marketResp.isSuccess()) {
            response = new MopResponse(MopRespCode.REQUEST_SUCESS);
        } else {
            response = new MopResponse(marketResp.getResCode(), marketResp.getMessage());
        }

        return response;
    }

    public String getName() {
        return "/marketing/user_coupon/transfer";
    }

    public ActionAuthLevel getAuthLevel() {
        return ActionAuthLevel.AUTH_LOGIN;
    }

    public HttpMethodLimit getMethodLimit() {
        return HttpMethodLimit.ONLY_POST;
    }
}
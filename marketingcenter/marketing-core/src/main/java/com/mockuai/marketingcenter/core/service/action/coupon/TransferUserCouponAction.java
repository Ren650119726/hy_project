package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 转移用户优惠券，用于支持优惠券转赠场景
 */
@Service
public class TransferUserCouponAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferUserCouponAction.class);

    @Resource
    private GrantedCouponManager grantedCouponManager;

    public MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        List<Long> userCouponIdList = (List<Long>) context.getRequest().getParam("userCouponIdList");
        Long fromUserId = (Long) context.getRequest().getParam("fromUserId");
        Long toUserId = (Long) context.getRequest().getParam("toUserId");

        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setIdList(userCouponIdList);
        grantedCouponQTO.setReceiverId(fromUserId);
        List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);

        //查询到的优惠券数量与指定使用优惠券的ID数不匹配，则提示优惠券不存在
        if (grantedCouponDOs.size() != userCouponIdList.size()) {
            return new MarketingResponse(ResponseCode.COUPON_NOT_EXIST);
        }

        Date current = new Date();
        //用户优惠券状态检查，只有未使用过的优惠券才能转移
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            if (grantedCouponDO.getStatus().intValue() == UserCouponStatus.PRE_USE.getValue()) {
                return new MarketingResponse(ResponseCode.COUPON_PRE_USE_ALREADY);
            }
            if (grantedCouponDO.getStatus().intValue() == UserCouponStatus.USED.getValue()) {
                return new MarketingResponse(ResponseCode.COUPON_USED_ALREADY);
            }
            if (grantedCouponDO.getInvalidTime() != null && grantedCouponDO.getInvalidTime().before(current))
                return new MarketingResponse(ResponseCode.BIZ_ACTIVITY_COUPON_OVERDUE);
        }

        //更新指定优惠券的拥有者
        //FIXME 后续如果底层数据库做了分库处理的话，那么这里的实现需要重构
        int opNum = grantedCouponManager.updateCouponReceiverId(userCouponIdList, fromUserId, toUserId);

        if (opNum != userCouponIdList.size()) {
            LOGGER.error("error to update coupon, fromUserId:{}, toUserId:{}",
                    fromUserId, toUserId);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }

        return ResponseUtil.getResponse(Boolean.valueOf(true));
    }

    public String getName() {
        return ActionEnum.TRANSFER_USER_COUPON.getActionName();
    }
}
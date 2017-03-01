package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.CouponType;
import com.mockuai.marketingcenter.common.constant.PropertyEnum;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.CouponCodeDTO;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.ActivityCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityCouponManager;
import com.mockuai.marketingcenter.core.manager.CouponCodeManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.manager.UserManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.TransAction;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by edgar.zr on 11/5/15.
 */
@Service
public class QueryCouponCodeAction extends TransAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryCouponCodeAction.class.getName());

    @Autowired
    private ActivityCouponManager activityCouponManager;

    @Autowired
    private CouponCodeManager couponCodeManager;

    @Autowired
    private GrantedCouponManager grantedCouponManager;

    @Autowired
    private PropertyManager propertyManager;

    @Autowired
    private UserManager userManager;

    @Override
    protected MarketingResponse doTransaction(RequestContext context) throws MarketingException {

        String bizCode = (String) context.get("bizCode");
        String appKey = (String) context.get("appKey");
        CouponCodeQTO couponCodeQTO = (CouponCodeQTO) context.getRequest().getParam("couponCodeQTO");

        if (couponCodeQTO == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "couponCodeQTO is null");
        }
        if (couponCodeQTO.getCouponId() == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "couponId is null");
        }
        if (couponCodeQTO.getActivityCreatorId() == null) {
            return new MarketingResponse(ResponseCode.PARAMETER_NULL, "activityCreatorId is null");
        }

        ActivityCouponDO activityCouponDO = activityCouponManager.getActivityCoupon(couponCodeQTO.getCouponId(), couponCodeQTO.getActivityCreatorId(), bizCode);

        ActivityCouponDTO activityCouponDTO = ModelUtil.genActivityCouponDTO(activityCouponDO);

        PropertyQTO propertyQTO = new PropertyQTO();
        propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
        propertyQTO.setOwnerId(activityCouponDO.getActivityId());

        Map<String, PropertyDO> propertyNameMap = propertyManager.queryAndWrapProperty(propertyQTO);
        try {
            activityCouponDTO.setDiscountAmount(Long.parseLong(propertyNameMap.get(PropertyEnum.QUOTA.getValue()).getValue()));
        } catch (NullPointerException e) {
            LOGGER.warn("the quota property of the activity coupon does not exist, activityId : {}, activityCouponId : {}",
                    activityCouponDO.getActivityId(), activityCouponDO.getId(), e);
        }

        List<CouponCodeDTO> couponCodeDTOs = new ArrayList<CouponCodeDTO>();
        CouponCodeDTO couponCodeDTO;
        Integer totalCount = 0;

        if (couponCodeQTO.getStatus() != null && couponCodeQTO.getStatus().intValue() == 0) {
            couponCodeQTO.setStatus(null);
        }

        // 通用码
        if (activityCouponDO.getCouponType().intValue() == CouponType.TYPE_COMMON_CODE.getValue()) {

            // 全部、已领取、已使用(50，包含预使用的) 都需要通过 GrantedCoupon 查询
            if (couponCodeQTO.getStatus() == null || couponCodeQTO.getStatus() != UserCouponStatus.UN_ACTIVATE.getValue()) {
                GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
                grantedCouponQTO.setCount(couponCodeQTO.getCount());
                grantedCouponQTO.setOffset(couponCodeQTO.getOffset());
                grantedCouponQTO.setCouponId(couponCodeQTO.getCouponId());
                grantedCouponQTO.setStatus(couponCodeQTO.getStatus());

                // 先查询用户列表
                if (StringUtils.isNotEmpty(couponCodeQTO.getUserName())) {
                    try {
                        List<UserDTO> userDTOs = userManager.queryUserByUserName(couponCodeQTO.getUserName(), appKey);
                        grantedCouponQTO.setReceiverIdList(new ArrayList<Long>());
                        for (UserDTO userDTO : userDTOs) {
                            grantedCouponQTO.getReceiverIdList().add(userDTO.getId());
                        }
                    } catch (MarketingException e) {
                        LOGGER.error("error to query userName", e);
                    }
                    if (grantedCouponQTO.getReceiverIdList().isEmpty()) {
                        return ResponseUtil.getResponse(couponCodeDTOs);
                    }
                }

                List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCouponForCouponCode(grantedCouponQTO);
                totalCount = grantedCouponQTO.getTotalCount();
                Set<Long> userIdSet = new HashSet<Long>();
                List<CouponCodeDTO> toFillUserName = new ArrayList<CouponCodeDTO>();

                for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
                    couponCodeDTO = new CouponCodeDTO();
                    BeanUtils.copyProperties(grantedCouponDO, couponCodeDTO);
                    couponCodeDTO.setDiscountAmount(activityCouponDTO.getDiscountAmount());
                    couponCodeDTO.setUserId(grantedCouponDO.getReceiverId());
                    if (grantedCouponDO.getReceiverId() != null) {
                        userIdSet.add(grantedCouponDO.getReceiverId());
                        toFillUserName.add(couponCodeDTO);
                    }
                    couponCodeDTOs.add(couponCodeDTO);
                }
                couponCodeManager.fillUpUserName(toFillUserName, new ArrayList<Long>(userIdSet), appKey);
            }

            // 全部 或者 未兑换的都需要显示一条空的通用码
            if ((couponCodeQTO.getStatus() == null || couponCodeQTO.getStatus().intValue() == UserCouponStatus.UN_ACTIVATE.getValue())
                    && activityCouponDO.getTotalCount().longValue() != activityCouponDO.getActivateCount().longValue()) {

                couponCodeDTO = new CouponCodeDTO();
                couponCodeDTO.setDiscountAmount(activityCouponDTO.getDiscountAmount());
                couponCodeDTO.setGmtCreated(activityCouponDTO.getGmtCreated());
                couponCodeDTO.setActivityCreatorId(activityCouponDTO.getActivityCreatorId());
                couponCodeDTO.setActivityId(activityCouponDTO.getActivityId());
                couponCodeDTO.setCode(activityCouponDTO.getCode());
                couponCodeDTO.setCouponType(activityCouponDTO.getCouponType());
                couponCodeDTO.setStatus(UserCouponStatus.UN_ACTIVATE.getValue());
                couponCodeDTOs.add(couponCodeDTO);
                totalCount++;
            }
            // 一卡一码
        } else {
            couponCodeDTOs = couponCodeManager.queryCouponCode(activityCouponDTO, couponCodeQTO, appKey);
            totalCount = couponCodeQTO.getTotalCount();
        }

        return ResponseUtil.getResponse(couponCodeDTOs, totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.QUERY_COUPON_CODE.getActionName();
    }
}
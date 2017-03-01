package com.mockuai.marketingcenter.core.service.action.coupon;

import com.mockuai.marketingcenter.common.api.MarketingResponse;
import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.PropertyOwnerType;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.GrantedCouponGatherDTO;
import com.mockuai.marketingcenter.common.domain.dto.PropertyDTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.common.domain.qto.MarketActivityQTO;
import com.mockuai.marketingcenter.common.domain.qto.PropertyQTO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponSumDO;
import com.mockuai.marketingcenter.core.domain.MarketActivityDO;
import com.mockuai.marketingcenter.core.domain.PropertyDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.MarketActivityManager;
import com.mockuai.marketingcenter.core.manager.PropertyManager;
import com.mockuai.marketingcenter.core.service.RequestContext;
import com.mockuai.marketingcenter.core.service.action.Action;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.marketingcenter.core.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryUserGatherCouponAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(QueryUserGatherCouponAction.class);

    @Resource
    private MarketActivityManager marketActivityManager;

    @Resource
    private PropertyManager propertyManager;

    @Resource
    private GrantedCouponManager grantedCouponManager;

    public MarketingResponse execute(RequestContext context) throws MarketingException {

        GrantedCouponQTO grantedCouponQTO = (GrantedCouponQTO) context.getRequest().getParam("grantedCouponQTO");
        String bizCode = (String) context.get("bizCode");

        if (bizCode == null) {
            bizCode = "yangdongxi";
        }

        //入参检查
        if (grantedCouponQTO == null) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL.getCode(), "grantedCouponQTO is null");
        }

        if (grantedCouponQTO.getReceiverId() == null) {
            throw new MarketingException(ResponseCode.PARAMETER_NULL.getCode(), "userId is null");
        }

        grantedCouponQTO.setBizCode(bizCode);

        //查询优惠券统计信息，根据activityId进行分组，算出各组优惠券的数量（TODO 这里需要加上分页控制，可能存在优惠券比较多的情况,循环批量查询出来?上千家点的优惠券?多少可能出现性能问题?）
        List<GrantedCouponSumDO> grantedCouponSumDOs = grantedCouponManager.queryGrantedCouponSum(grantedCouponQTO);

        if (grantedCouponSumDOs == null || grantedCouponSumDOs.isEmpty()) {
            return ResponseUtil.getResponse(Collections.emptyList(), 0);
        }

        //查询优惠券信息（TODO 这里需要加上分页控制，因为存在有几千张优惠券的用户）
//        List<GrantedCouponDO> grantedCouponDOs = this.grantedCouponManager.queryGrantedCoupon(grantedCouponQTO);
//        long grantedCouponNum = this.grantedCouponManager.countOfQueryGrantedCoupon(grantedCouponQTO);

//        //过滤掉无效的优惠券信息；只有当用户优惠券所关联的优惠券源头以及营销活动记录存在时，该优惠券才有效
//        List<GrantedCouponDTO> grantedCouponDTOs = new ArrayList<GrantedCouponDTO>();
//        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
//            ActivityCouponDO activityCouponDO = this.activityCouponManager.getActivityCoupon(
//                    grantedCouponDO.getCouponId(), grantedCouponDO.getCouponCreatorId());
//
//
//            if (activityCouponDO == null){
//                log.warn("activity coupon is not exist, couponId:{}, creatorId:{}",
//                        grantedCouponDO.getCouponId(), grantedCouponDO.getCouponCreatorId());
//                continue;
//            }
//
//            MarketActivityDO marketActivityDO = this.marketActivityManager.getActivity(
//                    activityCouponDO.getActivityId().longValue(), activityCouponDO.getActivityCreatorId().longValue());
//
//
//            if (marketActivityDO == null) {
//                log.warn("market activity is not exist, activity:{}, creatorId:{}",
//                        grantedCouponDO.getActivityId(), grantedCouponDO.getActivityCreatorId());
//                continue;
//            }
//
//            PropertyQTO propertyQTO = new PropertyQTO();
//            propertyQTO.setOwnerType(Integer.valueOf(2));
//            propertyQTO.setOwnerId(activityCouponDO.getActivityId());
//            propertyQTO.setBizCode(activityCouponDO.getBizCode());
//            List propertyDOs = this.propertyManager.queryProperty(propertyQTO);
//
//            //TODO 这里为什么cashAmount直接传0？
//            GrantedCouponDTO grantedCouponDTO = ModelUtil.genGrantedCouponDTO(grantedCouponDO,
//                    ModelUtil.genPropertyDTOList(propertyDOs), Long.valueOf(0L));
//            grantedCouponDTOs.add(grantedCouponDTO);
//
//        }

        Map<Long, GrantedCouponSumDO> grantedCouponSumMap = new HashMap<Long, GrantedCouponSumDO>();
        for (GrantedCouponSumDO grantedCouponSumDO : grantedCouponSumDOs) {
//            MarketActivityDO marketActivityDO = this.marketActivityManager.getActivity(
//                    grantedCouponSumDO.getActivityId(), grantedCouponSumDO.getActivityCreatorId());

//            if (marketActivityDO == null) {
//                log.warn("market activity is not exist, activity:{}, creatorId:{}",
//                        grantedCouponSumDO.getActivityId(), grantedCouponSumDO.getActivityCreatorId());
//                continue;
//            }

            grantedCouponSumMap.put(grantedCouponSumDO.getActivityId(), grantedCouponSumDO);
        }

        MarketActivityQTO marketActivityQTO = new MarketActivityQTO();
        marketActivityQTO.setIdList(new ArrayList<Long>(grantedCouponSumMap.keySet()));
        marketActivityQTO.setBizCode(bizCode);

        List<MarketActivityDO> marketActivityDOs = marketActivityManager.queryActivity(marketActivityQTO);

        List<GrantedCouponDTO> grantedCouponDTOs = new ArrayList<GrantedCouponDTO>();
        StringBuilder sb;
        for (int i = 0; i < marketActivityDOs.size(); i++) {
            MarketActivityDO marketActivityDO = marketActivityDOs.get(i);

            // 未使用的优惠券需要去除已经过期的优惠券
            if (grantedCouponQTO.getStatus().intValue() == UserCouponStatus.UN_USE.getValue() &&
                    marketActivityDO.getEndTime() != null && marketActivityDO.getEndTime().before(new Date())) {
                continue;
            }
            PropertyQTO propertyQTO = new PropertyQTO();
            propertyQTO.setOwnerType(PropertyOwnerType.ACTIVITY.getValue());
            propertyQTO.setOwnerId(marketActivityDO.getId());

            List<PropertyDO> propertyDOs = propertyManager.queryProperty(propertyQTO);

            //TODO 临时伪造一个grantedCouponDO，解决查询优惠券性能问题。后续有时间需要重构
            GrantedCouponDO grantedCouponDO = new GrantedCouponDO();
            grantedCouponDO.setId((long) (i + 1));//FIXME 这里的优惠券id暂时使用i来伪造，因为外部依赖者拿到该列表并不会直接使用，所以没关系
            grantedCouponDO.setReceiverId(grantedCouponQTO.getReceiverId());
            grantedCouponDO.setActivityId(marketActivityDO.getId());
            grantedCouponDO.setStartTime(marketActivityDO.getStartTime());
            grantedCouponDO.setEndTime(marketActivityDO.getEndTime());
            grantedCouponDO.setStatus(marketActivityQTO.getStatus());
            GrantedCouponDTO grantedCouponDTO = ModelUtil.genGrantedCouponDTO(grantedCouponDO,
                    ModelUtil.genMarketActivityDTO(marketActivityDO));
            //FIXME 显示用户领取的优惠券, content 需要显示未 满XX减YY
            int quota;
            for (PropertyDO propertyDO : propertyDOs) {
                if (PropertyDTO.CONSUME.equals(propertyDO.getPkey())) {
                    if (StringUtils.isBlank(propertyDO.getValue())) {
                        quota = 0;
                    } else {
                        quota = Integer.valueOf(propertyDO.getValue());
                    }
                    quota = quota / 100;
                    sb = new StringBuilder("满");
                    sb.append(String.valueOf(quota)).append("即可使用");
                    grantedCouponDTO.setContent(sb.toString());
                    break;
                }
            }
            grantedCouponDTOs.add(grantedCouponDTO);
        }

        List<GrantedCouponGatherDTO> grantedCouponGatherDTOs = new ArrayList<GrantedCouponGatherDTO>();

        //用户优惠券总量
        int userCouponCount = 0;
        for (GrantedCouponDTO grantedCouponDTO : grantedCouponDTOs) {
            GrantedCouponSumDO grantedCouponSumDO = grantedCouponSumMap.get(grantedCouponDTO.getActivityId());
            if (grantedCouponSumDO == null) {
                log.error("can not find the key of activityId : {}", grantedCouponDTO.getActivityId());
                continue;
            }

            //累计用户优惠券总量
            userCouponCount += grantedCouponSumDO.getCount();

            GrantedCouponGatherDTO grantedCouponGatherDTO = new GrantedCouponGatherDTO();
            BeanUtils.copyProperties(grantedCouponDTO, grantedCouponGatherDTO);
            grantedCouponGatherDTO.setNumber(grantedCouponSumDO.getCount());
            grantedCouponGatherDTOs.add(grantedCouponGatherDTO);
        }

        return ResponseUtil.getResponse(grantedCouponGatherDTOs, userCouponCount);
    }

    public String getName() {
        return ActionEnum.QUERY_USER_GATHER_COUPON.getActionName();
    }
}
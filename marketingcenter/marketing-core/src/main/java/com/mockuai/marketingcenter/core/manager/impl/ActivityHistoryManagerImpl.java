package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ActivityHistoryStatus;
import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.ActivityHistoryDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityHistoryQTO;
import com.mockuai.marketingcenter.core.dao.ActivityHistoryDAO;
import com.mockuai.marketingcenter.core.domain.ActivityHistoryDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.ActivityHistoryManager;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 12/3/15.
 */
@Service
public class ActivityHistoryManagerImpl implements ActivityHistoryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityHistoryManagerImpl.class);

    @Autowired
    private ActivityHistoryDAO activityHistoryDAO;

    @Override
    public void addActivityHistory(ActivityHistoryDTO activityHistoryDTO) throws MarketingException {
        try {
            Long id = activityHistoryDAO.addActivityHistory(ModelUtil.genActivityHistoryDO(activityHistoryDTO));
            if (id == null) {
                LOGGER.error("error to addActivityHistory, activityHistoryDTO : {}", JsonUtil.toJson(activityHistoryDTO));
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
            }
            activityHistoryDTO.setId(id);
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to addActivityHistory, activityHistoryDTO : {}", JsonUtil.toJson(activityHistoryDTO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void updateActivityHistoryStatus(Long activityId, Long orderId, Integer status, String bizCode) throws MarketingException {
        try {
            ActivityHistoryDO activityHistoryDO = new ActivityHistoryDO(activityId, orderId, status, bizCode);
            int opNum = activityHistoryDAO.updateActivityHistoryStatus(activityHistoryDO);
            if (opNum != 0) {
                LOGGER.error("error to updateActivityHistoryStatus, activityId : {}, orderId : {}, status : {}, bizCode : {}",
                        activityId, orderId, status, bizCode);
            }
            // 不考虑由于RMQ 消息的延迟导致取消订单状态无法更新，正常情况下付款订单与取消订单之前的时间差对于RMQ 来说很大
        } catch (Exception e) {
            LOGGER.error("error to updateActivityHistoryStatus, activityId : {}, orderId : {}, status : {}, bizCode : {}",
                    activityId, orderId, status, bizCode, e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<ActivityHistoryDTO> queryActivityHistoryByActivity(Long activityId, String bizCode) throws MarketingException {
        try {
            ActivityHistoryQTO activityHistoryQTO = new ActivityHistoryQTO();
            activityHistoryQTO.setBizCode(bizCode);
            activityHistoryQTO.setStatus(ActivityHistoryStatus.PAY_DONE.getValue());
            activityHistoryQTO.setActivityId(activityId);

            List<ActivityHistoryDO> activityHistoryDOs = activityHistoryDAO.queryActivityHistory(activityHistoryQTO);
            return ModelUtil.genActivityHistoryDTOList(activityHistoryDOs);
        } catch (Exception e) {
            LOGGER.error("error to queryActivityHistoryByActivity, activityId : {}, bizCode : {}",
                    activityId, bizCode, e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public void fillUpActivityWithSales(List<MarketActivityDTO> marketActivityDTOs, String bizCode) throws MarketingException {
        if (marketActivityDTOs == null || marketActivityDTOs.isEmpty()) return;

        List<Long> activityIds = new ArrayList<Long>();
        for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
            activityIds.add(marketActivityDTO.getId());
        }

        if (activityIds.isEmpty()) return;

        ActivityHistoryQTO activityHistoryQTO = new ActivityHistoryQTO();
        activityHistoryQTO.setBizCode(bizCode);
        activityHistoryQTO.setActivityList(activityIds);
        activityHistoryQTO.setStatusList(new ArrayList<Integer>());
        activityHistoryQTO.getStatusList().add(ActivityHistoryStatus.PAY_DONE.getValue());
        activityHistoryQTO.getStatusList().add(ActivityHistoryStatus.PAYING.getValue());

        List<ActivityHistoryDO> activityHistoryDOs = activityHistoryDAO.queryActivityHistory(activityHistoryQTO);
        Map<Long, Long> activityIdKeyNumValue = new HashMap<Long, Long>();
        for (ActivityHistoryDO activityHistoryDO : activityHistoryDOs) {
            if (!activityIdKeyNumValue.containsKey(activityHistoryDO.getActivityId())) {
                activityIdKeyNumValue.put(activityHistoryDO.getActivityId(), 0L);
            }
            activityIdKeyNumValue.put(activityHistoryDO.getActivityId(), activityIdKeyNumValue.get(activityHistoryDO.getActivityId()) + activityHistoryDO.getNum());
        }
        for (MarketActivityDTO marketActivityDTO : marketActivityDTOs) {
            if (activityIdKeyNumValue.containsKey(marketActivityDTO.getId())) {
                marketActivityDTO.setSales(activityIdKeyNumValue.get(marketActivityDTO.getId()));
            } else {
                marketActivityDTO.setSales(0L);
            }
        }
    }

    @Override
    public Long getNumOfSkuBuyByUser(Long userId, Long activityId, String bizCode) throws MarketingException {
        Long num = 0L;

        ActivityHistoryQTO activityHistoryQTO = new ActivityHistoryQTO();
        activityHistoryQTO.setBizCode(bizCode);
        activityHistoryQTO.setUserId(userId);
        activityHistoryQTO.setActivityId(activityId);
        activityHistoryQTO.setStatus(ActivityHistoryStatus.PAY_DONE.getValue());

        List<ActivityHistoryDO> activityHistoryDOs = activityHistoryDAO.queryActivityHistory(activityHistoryQTO);
        if (activityHistoryDOs.isEmpty()) return num;

        for (ActivityHistoryDO activityHistoryDO : activityHistoryDOs) {
            num += activityHistoryDO.getNum();
        }

        return num;
    }

    @Override
    public List<ActivityHistoryDO> filterTradeMessage(OrderDTO orderDTO) throws MarketingException {
        if (orderDTO == null || orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty())
            return Collections.emptyList();

        Map<Long, ActivityHistoryDO> activityIdKeyHistoryValue = new HashMap<Long, ActivityHistoryDO>();
        ActivityHistoryDO activityHistoryDO;
        Long num;
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            if (!activityIdKeyHistoryValue.containsKey(orderItemDTO.getActivityId())) {
                activityHistoryDO = new ActivityHistoryDO();
                activityIdKeyHistoryValue.put(orderItemDTO.getActivityId(), activityHistoryDO);
                activityHistoryDO.setSellerId(orderItemDTO.getSellerId());
                activityHistoryDO.setBizCode(orderDTO.getBizCode());
                activityHistoryDO.setActivityId(orderItemDTO.getActivityId());
                activityHistoryDO.setItemId(orderItemDTO.getItemId());
                activityHistoryDO.setNum(0L);
                activityHistoryDO.setOrderId(orderDTO.getId());
                activityHistoryDO.setSkuId(orderItemDTO.getItemSkuId());
                activityHistoryDO.setUserId(orderDTO.getUserId());
            }
            activityHistoryDO = activityIdKeyHistoryValue.get(orderItemDTO.getActivityId());
            activityHistoryDO.setNum(activityHistoryDO.getNum() + orderDTO.getOrderItemDTO().getNumber().longValue());
        }
        return (List<ActivityHistoryDO>) activityIdKeyHistoryValue.values();
    }
}
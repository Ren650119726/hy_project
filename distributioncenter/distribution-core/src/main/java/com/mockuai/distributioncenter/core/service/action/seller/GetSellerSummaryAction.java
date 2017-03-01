package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.DistributionStatus;
import com.mockuai.distributioncenter.common.constant.DistributionType;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.dto.SummaryDTO;
import com.mockuai.distributioncenter.common.domain.qto.DistRecordQTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerRelationshipQTO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DataManager;
import com.mockuai.distributioncenter.core.manager.DistRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by duke on 16/5/16.
 */
@Service
public class GetSellerSummaryAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(GetSellerSummaryAction.class);

    @Autowired
    private DistRecordManager distRecordManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private DataManager dataManager;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        Date startTime = (Date) request.getParam("startTime");
        Date endTime = (Date) request.getParam("endTime");
        Long userId = (Long) request.getParam("userId");
        String appKey = (String) context.get("appKey");

        // 获得卖家信息
        SellerDTO seller = sellerManager.getByUserId(userId);

        if (seller == null) {
            log.error("seller is not exists, userId: {}", userId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "seller is not exists");
        }

        Map<String, SummaryDTO> summaryMap = new HashMap<String, SummaryDTO>();
        // 初始化时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        do {
            SummaryDTO summaryDTO = new SummaryDTO();
            String dateStr = df.format(calendar.getTime());
            summaryDTO.setDate(dateStr);
            summaryMap.put(dateStr, summaryDTO);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } while (calendar.getTime().before(endTime));

        // 获得订单统计
        // 获得佣金收入
        DistRecordQTO distRecordQTO = new DistRecordQTO();
        distRecordQTO.setStartTime(startTime);
        distRecordQTO.setEndTime(endTime);
        distRecordQTO.setType(DistributionType.REAL_AMOUNT.getType());
        distRecordQTO.setSellerId(seller.getId());
        List<DistRecordDTO> distRecordDTOs = distRecordManager.query(distRecordQTO);

        // 按照日期聚合
        Map<String, List<DistRecordDTO>> map = new HashMap<String, List<DistRecordDTO>>();
        for (DistRecordDTO distRecordDTO : distRecordDTOs) {
        	
            if (distRecordDTO.getStatus().equals(DistributionStatus.UNDER_DISTRIBUTION.getStatus())) {
                continue;
            }
            if (distRecordDTO.getStatus().equals(DistributionStatus.CANCEL_SUCCESS_DISTRIBUTION.getStatus())) {
                continue;
            }
            if (distRecordDTO.getStatus().equals(DistributionStatus.CANCEL_DISTRIBUTION.getStatus())) {
                continue;
            }
            
            String key = df.format(distRecordDTO.getGmtCreated());
            List<DistRecordDTO> list = map.get(key);
            if (list == null) {
                list = new ArrayList<DistRecordDTO>();
            }
            list.add(distRecordDTO);
            map.put(key, list);
        }

        Set<String> orderSnSet = new HashSet<String>();
        for (Map.Entry<String, List<DistRecordDTO>> entry : map.entrySet()) {
            SummaryDTO summaryDTO = summaryMap.get(entry.getKey());
            if (summaryDTO == null) {
                summaryDTO = new SummaryDTO();
                summaryDTO.setDate(entry.getKey());
            }
            // 统计订单数
            orderSnSet.clear();
            for (DistRecordDTO recordDTO : entry.getValue()) {
                orderSnSet.add(recordDTO.getOrderSn());
            }
            summaryDTO.setOrderCount(summaryDTO.getOrderCount() + orderSnSet.size());
            summaryMap.put(entry.getKey(), summaryDTO);

            // 计算佣金收入
            Long totalCount = 0L;
            for (DistRecordDTO distRecordDTO : entry.getValue()) {
                totalCount += distRecordDTO.getDistAmount();
            }
            summaryDTO.setInCome(summaryDTO.getInCome() + totalCount);
        }

        // 获得邀请人数
        SellerRelationshipQTO sellerRelationshipQTO = new SellerRelationshipQTO();
        sellerRelationshipQTO.setStartTime(startTime);
        sellerRelationshipQTO.setEndTime(endTime);
        sellerRelationshipQTO.setParentIds(Collections.singletonList(seller.getUserId()));
        List<SellerRelationshipDTO> sellerRelationshipDTOs = sellerRelationshipManager.query(sellerRelationshipQTO);

        // 按照日期聚合
        Map<String, List<SellerRelationshipDTO>> relationshipMap = new HashMap<String, List<SellerRelationshipDTO>>();
        for (SellerRelationshipDTO sellerRelationshipDTO : sellerRelationshipDTOs) {
            String key = df.format(sellerRelationshipDTO.getGmtCreated());
            List<SellerRelationshipDTO> list = relationshipMap.get(key);
            if (list == null) {
                list = new ArrayList<SellerRelationshipDTO>();
            }
            list.add(sellerRelationshipDTO);
            relationshipMap.put(key, list);
        }

        for (Map.Entry<String, List<SellerRelationshipDTO>> entry : relationshipMap.entrySet()) {
            SummaryDTO summaryDTO = summaryMap.get(entry.getKey());
            if (summaryDTO == null) {
                summaryDTO = new SummaryDTO();
                summaryDTO.setDate(entry.getKey());
            }
            summaryDTO.setInviterCount(summaryDTO.getInviterCount() + entry.getValue().size());
            summaryMap.put(entry.getKey(), summaryDTO);
        }

        // 获得PV数据
        /*Map<DataResultDTO, List<DataDTO>> resultMap = dataManager.getDataByTimeRange(
                seller.getId(),
                null, "mallPV",
                TimeTypeEnum.DAY.getValue(),
                startTime,
                endTime,
                appKey);
        List<DataDTO> list = new ArrayList<DataDTO>();
        for (Map.Entry<DataResultDTO, List<DataDTO>> entry : resultMap.entrySet()) {
            list.addAll(entry.getValue());
        }
        for (DataDTO dataDTO : list) {
            String date = df.format(dataDTO.getTime());
            SummaryDTO summaryDTO = summaryMap.get(date);
            if (summaryDTO != null) {
                summaryDTO.setPv(summaryDTO.getPv() + Long.valueOf(dataDTO.getValue()));
            }
        }*/

        List<SummaryDTO> summaryDTOs = new ArrayList<SummaryDTO>(summaryMap.values());
        Collections.sort(summaryDTOs);

        return new DistributionResponse(summaryDTOs);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SELLER_SUMMARY.getActionName();
    }
}

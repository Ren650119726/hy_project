package com.mockuai.giftscenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.common.collect.Lists;
import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.common.domain.dto.ActionGiftDTO;
import com.mockuai.giftscenter.common.domain.dto.ActionGiftMarketingDTO;
import com.mockuai.giftscenter.common.domain.qto.ActionGiftQTO;
import com.mockuai.giftscenter.core.dao.ActionGiftDAO;
import com.mockuai.giftscenter.core.dao.ActionGiftMarketingDAO;
import com.mockuai.giftscenter.core.domain.ActionGiftDO;
import com.mockuai.giftscenter.core.domain.ActionGiftMarketingDO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.ActionGiftManager;
import com.mockuai.giftscenter.core.manager.MarketingManager;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.qto.ActivityCouponQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guansheng on 2016/7/15.
 */
@Service
public class ActionGiftManagerImpl implements ActionGiftManager {

    @Autowired
    private ActionGiftDAO actionGiftDAO;
    @Autowired
    private ActionGiftMarketingDAO actionGiftMarketingDAO;

    @Autowired
    private MarketingManager marketingManager;

    @Override
    public void update(ActionGiftQTO actionGiftQTO) throws GiftsException {
        ActionGiftDO actionGiftDO = new ActionGiftDO();
        BeanUtils.copyProperties(actionGiftQTO, actionGiftDO);
        actionGiftDAO.update(actionGiftDO);
        long actionId = actionGiftQTO.getId();
        actionGiftMarketingDAO.deleteByActionId(actionId);
        saveActionGiftMarketingDO(actionId, actionGiftQTO.getMarketingIds());
    }


    @Override
    public long save(ActionGiftQTO actionGiftQTO) throws GiftsException {
        ActionGiftDO actionGiftDO = new ActionGiftDO();
        BeanUtils.copyProperties(actionGiftQTO, actionGiftDO);
        actionGiftDAO.insert(actionGiftDO);
        long actionId = actionGiftDO.getId();
        String marketingIdsStr = actionGiftQTO.getMarketingIds();
        saveActionGiftMarketingDO(actionId, marketingIdsStr);
        return actionId ;
    }

    private void saveActionGiftMarketingDO(long actionId, String marketingIdsStr) throws GiftsException {

        String[] marketingIds = marketingIdsStr.split(",");
        if(marketingIds.length > 10){
            throw  new GiftsException(ResponseCode.BIZ_GIFT_SAVE_GRANT_BEYOND_NUMBER);
        }
        List<ActionGiftMarketingDO> actionGiftMarketingDOList = Lists.newArrayList();
        Map<Long, Short> marketingMap = new HashMap<>();
        for (String marketingIdStr : marketingIds) {
            Long marketingId = Long.parseLong(marketingIdStr);
            Short count = marketingMap.get(marketingId);
            if (count == null) {
                marketingMap.put(marketingId, (short) 1);
            } else {
                marketingMap.put(marketingId, (short) (count + 1));
            }
        }

        for (Map.Entry<Long, Short> entry : marketingMap.entrySet()) {
            ActionGiftMarketingDO actionGiftMarketingDO = new ActionGiftMarketingDO();
            actionGiftMarketingDO.setActionId(actionId);
            actionGiftMarketingDO.setMarketingId(entry.getKey());
            actionGiftMarketingDO.setCount(entry.getValue());
            actionGiftMarketingDOList.add(actionGiftMarketingDO);
        }
        actionGiftMarketingDAO.insert(actionGiftMarketingDOList);
    }

    @Override
    public ActionGiftDTO queryByActionType(int actionType, String appKey) throws GiftsException {
        ActionGiftDTO actionGiftDTO = new ActionGiftDTO();
        ActionGiftDO actionGiftDO = actionGiftDAO.getByActionType(actionType);
        //未配置数据， 设置默认数据
        if (actionGiftDO == null) {
            actionGiftDTO.setActionType(actionType);
            actionGiftDTO.setOpenFlag(0);
            return actionGiftDTO;
        }
        BeanUtils.copyProperties(actionGiftDO, actionGiftDTO);
        long actionId = actionGiftDO.getId();
        List<ActionGiftMarketingDO> actionGiftMarketingDOList = actionGiftMarketingDAO.queryByActionId(actionId);
        List<Long> params = new ArrayList<>();
        Map<Long, Short> marketingCountMap = new HashMap<>();
        for (ActionGiftMarketingDO item : actionGiftMarketingDOList) {
            params.add(item.getMarketingId());
            marketingCountMap.put(item.getMarketingId(), item.getCount());
        }


        ActivityCouponQTO activityCouponQTO = new ActivityCouponQTO();
        activityCouponQTO.setIdList(params);
        activityCouponQTO.setLifecycle(0);
        List<ActivityCouponDTO> activityCouponDTOList = marketingManager.queryActivityCoupon(activityCouponQTO, appKey);
        List<ActionGiftMarketingDTO> actionGiftMarketingDTOList = Lists.newArrayList();
        for (ActivityCouponDTO item : activityCouponDTOList) {
            ActionGiftMarketingDTO actionGiftMarketingDTO = new ActionGiftMarketingDTO();
            actionGiftMarketingDTO.setCount(marketingCountMap.get(item.getId()));
            actionGiftMarketingDTO.setItem(item);
            actionGiftMarketingDTOList.add(actionGiftMarketingDTO);
        }
        actionGiftDTO.setItemList(actionGiftMarketingDTOList);
        return actionGiftDTO;
    }


}

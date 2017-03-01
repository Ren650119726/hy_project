package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitOderInfoDTO;
import com.mockuai.marketingcenter.core.dao.LimitOderInfoDAO;
import com.mockuai.marketingcenter.core.domain.LimitOderInfoDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitOderInfoManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangsiqian on 2016/11/3.
 */
public class LimitOderInfoManagerImpl implements LimitOderInfoManager {
    @Autowired
    private LimitOderInfoDAO limitOderInfoDAO;
    @Override
    public LimitOderInfoDO queryLimitOderInfos(LimitOderInfoDO limitOderInfoDO ) throws MarketingException {
        LimitOderInfoDO infoDO = null;
        try {
            infoDO = (LimitOderInfoDO) limitOderInfoDAO.queryLimitOderInfos(limitOderInfoDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "查询订单出错");
        }

        return infoDO;
    }

    @Override
    public List<LimitOderInfoDO> queryInfoDtoById(Long activityId) throws MarketingException {

        {
            List<LimitOderInfoDO> limitOder= new ArrayList<LimitOderInfoDO>();
            try {
                limitOder =  (List<LimitOderInfoDO>) limitOderInfoDAO.queryInfoDtoById(activityId);

            }catch (Exception e){
            e.printStackTrace();
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "读取订单信息出错");
            }
            return limitOder;
        }
    }

    @Override
    public Boolean addLimitOderInfo(LimitOderInfoDO limitOderInfoDO) throws MarketingException {
        try {
            Long num = (Long) limitOderInfoDAO.addLimitOderInfo(limitOderInfoDO);
            if (num<1) {
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "插入订单信息失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "插入订单信息出错");
        }
        return true;
    }
}

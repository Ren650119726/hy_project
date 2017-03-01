package com.mockuai.distributioncenter.core.manager.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.alibaba.dubbo.common.json.JSON;
import com.mockuai.distributioncenter.common.domain.dto.FansDistDTO;
import com.mockuai.distributioncenter.common.domain.qto.FansDistQTO;
import org.apache.noggit.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.MyFansDTO;
import com.mockuai.distributioncenter.common.domain.qto.MyFansQTO;
import com.mockuai.distributioncenter.core.dao.DistRecordDAO;
import com.mockuai.distributioncenter.core.domain.DistRecordDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.MyFansManager;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.usercenter.client.UserClient;
import com.mockuai.usercenter.common.api.Response;
import com.mockuai.usercenter.common.dto.UserDTO;
import com.mockuai.usercenter.common.qto.UserQTO;

/**
 * Created by lizg on 2016/9/1.
 */

@Component
public class MyFansManagerImpl implements MyFansManager {

    private static final Logger log = LoggerFactory.getLogger(MyFansManagerImpl.class);

    @Autowired
    private UserClient userClient;

    @Autowired
    private DistRecordDAO distRecordDAO;

    @Override
    public List<MyFansDTO> getMyFans(MyFansQTO myFansQTO, String appKey) throws DistributionException {

        List<MyFansDTO> myFansDTOs = new ArrayList<MyFansDTO>();

        //获取用户信息
        UserQTO userQTO = new UserQTO();
        //如果没有传fans_id是第一次调用，传入fans_id说明是第二次调用
        if(myFansQTO.getFansId()==null) {
            userQTO.setId(myFansQTO.getUserId());
        }else {
            userQTO.setId(myFansQTO.getFansId());
        }
        userQTO.setOffset(myFansQTO.getOffset());
        userQTO.setCount(myFansQTO.getCount());

        //默认时间升序
        if (myFansQTO.getSort() == 1 && myFansQTO.getUpdown() == 2) {
            userQTO.setSourceType(2);
        }
        Response<List<UserDTO>> userDTOList = this.userClient.queryInviterListByUserId(userQTO, appKey);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        log.info("result:{}", JsonUtil.toJson(userDTOList.isSuccess()));
        if (userDTOList.isSuccess()) {
            List<UserDTO> userDTOs = userDTOList.getModule();
             log.info("[{}] userDTOs:{}", JsonUtil.toJson(userDTOs));
            if(null == userDTOs) {
                return myFansDTOs;
            }

            //获取昨天的日期
//            Calendar   cal =Calendar.getInstance();
//            cal.add(Calendar.DATE,-1);
//            String yesterday = sdf.format(cal.getTime());
//            String today = sdf.format(new Date());

            for (UserDTO userDTO : userDTOs) {

                MyFansDTO myFansDTO = new MyFansDTO();
                myFansDTO.setUserId(userDTO.getId());
                myFansDTO.setSex(userDTO.getSex());
                myFansDTO.setHeadPortrait(userDTO.getImgUrl());
                myFansDTO.setNickname(userDTO.getNickName());
                myFansDTO.setGmtCreated(sdf.format(userDTO.getGmtCreated()));

                DistRecordDO distRecordDO = new DistRecordDO();
                distRecordDO.setSellerId(myFansQTO.getUserId()); //嗨客id
                distRecordDO.setBuyerId(userDTO.getId()); //粉丝id
                Long cumMoney = distRecordDAO.getAmountBySellerId(distRecordDO);
                //    log.info("[{}] cumMoney:{}",cumMoney);
                if (null == cumMoney) {
                    cumMoney = 0l;
                }
                myFansDTO.setCumMoney(cumMoney);
                myFansDTOs.add(myFansDTO);
            }
                log.info("myfansDTOs{}",JsonUtil.toJson(myFansDTOs));

            if (myFansQTO.getSort() == 2 && myFansQTO.getUpdown() == 1) {
                if (!CollectionUtils.isEmpty(myFansDTOs)) {
                    Collections.sort(myFansDTOs, new Comparator<MyFansDTO>() {
                        @Override
                        public int compare(MyFansDTO mf1, MyFansDTO mf2) {
                            return mf2.getCumMoney().compareTo(mf1.getCumMoney());
                        }
                    });
                }
            }

        } else {
            log.error("queryInviterListByUserId error,user id: {}", myFansQTO.getUserId());
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, userDTOList.getMessage());
        }

        return myFansDTOs;
    }

    @Override
    public Long totalCount(MyFansQTO myFansQTO, String appKey) throws DistributionException {

        //获取用户粉丝总条数
        UserQTO userQTO = new UserQTO();
        //如果没有传fans_id是第一次调用，传入fans_id说明是第二次调用
        if(myFansQTO.getFansId()==null) {
            userQTO.setId(myFansQTO.getUserId());
        }else {
            userQTO.setId(myFansQTO.getFansId());
        }
        Response<List<UserDTO>> userDTOList = this.userClient.queryInviterListByUserId(userQTO, appKey);
        Long totalCount = 0L;
        if (userDTOList.isSuccess()) {
            totalCount = userDTOList.getTotalCount();
            log.info("[{}] totalCount:{}", totalCount);
        }
        return totalCount;
    }

    @Override
    public Long totalCountByUserId(Long userId, String appKey) throws DistributionException {
        UserQTO userQTO = new UserQTO();
        userQTO.setId(userId);
        Response<List<UserDTO>> userDTOList = this.userClient.queryInviterListByUserId(userQTO, appKey);
        Long totalCount = 0L;
        if (userDTOList.isSuccess()) {
            totalCount = userDTOList.getTotalCount();
            log.info("[{}] totalCount:{}", totalCount);
        }
        return totalCount;
    }

    @Override
    public List<FansDistDTO> queryDistListFromFans(FansDistQTO fansDistQTO, String appKey) throws DistributionException {
        List<FansDistDTO> fansDistDTOS = new ArrayList<FansDistDTO>();

        UserQTO userQTO = new UserQTO();
        userQTO.setOffset(fansDistQTO.getOffset());
        userQTO.setCount(fansDistQTO.getCount());
        userQTO.setId(fansDistQTO.getInviterId());

        Response<List<UserDTO>> userDTOList = this.userClient.queryInviterListByUserId(userQTO, appKey);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (userDTOList.isSuccess()) {
            List<UserDTO> userDTOs = userDTOList.getModule();
            if(null == userDTOs) {
                return null;
            }

            for (UserDTO userDTO : userDTOs) {
                FansDistDTO fansDistDTO = new FansDistDTO();
                fansDistDTO.setUserId(userDTO.getId());
                fansDistDTO.setName(userDTO.getNickName());
                fansDistDTO.setGmtCreated(sdf.format(userDTO.getGmtCreated()));
                fansDistDTO.setMobile(userDTO.getMobile());
                fansDistDTO.setStatus(userDTO.getStatus());

                DistRecordDO distRecordDO = new DistRecordDO();
                distRecordDO.setSellerId(fansDistQTO.getInviterId());
                distRecordDO.setBuyerId(userDTO.getId());

                Long totalMoneyForUpper = distRecordDAO.getAmountBySellerId(distRecordDO);

                if (null == totalMoneyForUpper) {
                    totalMoneyForUpper = 0L;
                }
                fansDistDTO.setMoneyForUpper(totalMoneyForUpper);
                fansDistDTOS.add(fansDistDTO);
            }

                if (!CollectionUtils.isEmpty(fansDistDTOS)) {
                    Collections.sort(fansDistDTOS, new Comparator<FansDistDTO>() {
                        @Override
                        public int compare(FansDistDTO mf1, FansDistDTO mf2) {
                            return mf2.getMoneyForUpper().compareTo(mf1.getMoneyForUpper());
                        }
                    });
                }
        } else {
            log.error("queryInviterListByUserId error,user id: {}", fansDistQTO.getUserId());
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, userDTOList.getMessage());
        }

        return fansDistDTOS;
    }
}

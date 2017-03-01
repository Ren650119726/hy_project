package com.mockuai.usercenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.common.dto.UserOpenInfoDTO;
import com.mockuai.usercenter.common.qto.UserOpenInfoQTO;
import com.mockuai.usercenter.core.dao.UserOpenInfoDAO;
import com.mockuai.usercenter.core.domain.UserOpenInfoDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.UserOpenInfoManager;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/4/15.
 */
@Service
public class UserOpenInfoManagerImpl implements UserOpenInfoManager {
    private static final Logger log = LoggerFactory.getLogger(UserOpenInfoManagerImpl.class);

    @Resource
    private UserOpenInfoDAO userOpenInfoDAO;

    @Override
    public long addUserOpenInfo(UserOpenInfoDO userOpenInfoDO) throws UserException {
        if (userOpenInfoDO == null) {
            throw new UserException(ResponseCode.P_PARAM_NULL, "userOpenInfoDO is null");
        }

        try {
            long userOpenInfoId = userOpenInfoDAO.addUserOpenInfo(userOpenInfoDO);
            return userOpenInfoId;
        } catch (Exception e) {
            log.error("userOpenInfoDO:{}", JsonUtil.toJson(userOpenInfoDO), e);
            throw new UserException(ResponseCode.B_ADD_ERROR);
        }
    }

    @Override
    public int updateUserId(Long id, Long oldUserId, Long newUserId, String bizCode) throws UserException {
        try {
            int opNum = userOpenInfoDAO.updateUserId(id, oldUserId, newUserId, bizCode);
            return opNum;
        } catch (Exception e) {
            log.error("id:{}, oldUserId:{}, newUserId:{}, bizCode:{}",
                    id, oldUserId, newUserId, bizCode, e);
            throw new UserException(ResponseCode.B_UPDATE_ERROR, "error to update userId");
        }

    }

    @Override
    public UserOpenInfoDO getUserOpenInfo(Integer openType, String openUid) throws UserException {
        try {
            UserOpenInfoDO userOpenInfoDO = userOpenInfoDAO.getUserOpenInfo(openType, openUid);
            return userOpenInfoDO;
        } catch (Exception e) {
            log.error("openType:{}, openUid:{}", openType, openUid, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to get userOpenInfo");
        }

    }

    @Override
    public UserOpenInfoDO getUserOpenInfoById(long id) throws UserException {
        try {
            UserOpenInfoDO userOpenInfoDO = userOpenInfoDAO.getUserOpenInfoById(id);
            return userOpenInfoDO;
        } catch (Exception e) {
            log.error("id:{}", id, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to get userOpenInfo");
        }
    }

    @Override
    public UserOpenInfoDO getOpenInfoByOpenId(String openId, String appId, String bizCode) throws UserException {
        try {
            UserOpenInfoDO userOpenInfoDO = userOpenInfoDAO.getOpenInfoByOpenId(openId, appId, bizCode);
            return userOpenInfoDO;
        } catch (Exception e) {
            log.error("openId:{}, appId:{}, bizCode:{}", openId, appId, bizCode, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to get userOpenInfo");
        }
    }

    @Override
    public UserOpenInfoDO getUserOpenInfoByUserId(Integer openType, Long userId, String bizCode) throws UserException {
        try {
            UserOpenInfoDO userOpenInfoDO = userOpenInfoDAO.getUserOpenInfoByUserId(openType, userId, bizCode);
            return userOpenInfoDO;
        } catch (Exception e) {
            log.error("openType:{}, userId:{}, bizCode:{}",
                    openType, userId, bizCode, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to get userOpenInfo");
        }
    }

    @Override
    public int deleteUserOpenInfo(Long id, Long userId) throws UserException {
        try {
            int opNum = userOpenInfoDAO.deleteUserOpenInfo(id, userId);
            return opNum;
        } catch (Exception e) {
            log.error("id:{}, userId:{}", id, userId, e);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "error to delete userOpenInfo");
        }
    }

    @Override
    public UserOpenInfoDTO getOpenInfoByUserId(Long userId, String bizCode) throws UserException {
        try {
            UserOpenInfoDO openInfoDO = userOpenInfoDAO.getOpenInfoByUserId(userId, bizCode);
            if (openInfoDO != null) {
                UserOpenInfoDTO openInfoDTO = new UserOpenInfoDTO();
                BeanUtils.copyProperties(openInfoDO, openInfoDTO);
                return openInfoDTO;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("select by userId error, userId: {}", userId);
            throw new UserException(ResponseCode.B_SELECT_ERROR, "select by userId error");
        }
    }

    @Override
    public List<UserOpenInfoDTO> queryUserOpenInfo(UserOpenInfoQTO userOpenInfoQTO) throws UserException {
        try {
            List<UserOpenInfoDTO> userOpenInfoDTOs = new ArrayList<UserOpenInfoDTO>();
            List<UserOpenInfoDO> userOpenInfoDOs = this.userOpenInfoDAO.queryUserOpenInfo(userOpenInfoQTO);
            if (userOpenInfoDOs != null) {
                for (UserOpenInfoDO userOpenInfoDO : userOpenInfoDOs) {
                    UserOpenInfoDTO userOpenInfoDTO = new UserOpenInfoDTO();
                    BeanUtils.copyProperties(userOpenInfoDO, userOpenInfoDTO);
                    userOpenInfoDTOs.add(userOpenInfoDTO);
                }
                return userOpenInfoDTOs;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("queryUserOpenInfo error, UserOpenInfoQTO={}", JSON.toJSONString(userOpenInfoQTO));
            throw new UserException(ResponseCode.B_SELECT_ERROR, "queryUserOpenInfo error");
        }
    }

    @Override
    public Long getTotalCount(UserOpenInfoQTO userOpenInfoQTO) throws UserException {
        try {
            Long count = this.userOpenInfoDAO.getTotalCount(userOpenInfoQTO);
            return count;
        } catch (Exception e) {
            log.error("getTotalCount error, UserOpenInfoQTO={}", JSON.toJSONString(userOpenInfoQTO));
            throw new UserException(ResponseCode.B_SELECT_ERROR, "queryUserOpenInfo error");

        }
    }
}
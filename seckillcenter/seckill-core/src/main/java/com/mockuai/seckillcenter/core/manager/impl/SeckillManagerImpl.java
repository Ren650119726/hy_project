package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ActivityCouponStatus;
import com.mockuai.marketingcenter.common.constant.ActivityLifecycle;
import com.mockuai.marketingcenter.common.constant.ActivityStatus;
import com.mockuai.seckillcenter.common.constant.ResponseCode;
import com.mockuai.seckillcenter.common.constant.SeckillLifecycle;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;
import com.mockuai.seckillcenter.core.dao.SeckillDAO;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import com.mockuai.seckillcenter.core.util.ModelUtil;
import org.apache.noggit.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class SeckillManagerImpl implements SeckillManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeckillManagerImpl.class);

    @Autowired
    private SeckillDAO seckillDAO;
    @Autowired
    private ItemManager itemManager;

    @Override
    public Long addSeckill(SeckillDO seckillDO) throws SeckillException {
        try {
            Long seckillId = seckillDAO.addSeckill(seckillDO);
            return seckillId;
        } catch (Exception e) {
            LOGGER.error("error to addSeckill, seckillDO : {}", JsonUtil.toJson(seckillDO), e);
            throw new SeckillException(ResponseCode.BIZ_E_ADD_SECKILL);
        }
    }

    @Override
    public SeckillDTO getSeckill(SeckillDO seckillDO) throws SeckillException {
        try {
            SeckillDO dbSeckillDO = seckillDAO.getSeckill(seckillDO);
            if (dbSeckillDO == null) {
                LOGGER.error("error to getSeckill, seckillDO : {}", JSONUtil.toJSON(seckillDO));
                throw new SeckillException(ResponseCode.BIZ_E_SECKILL_NOT_EXIST);
            }
            SeckillDTO seckillDTO = ModelUtil.genSeckillDTO(dbSeckillDO);
            return seckillDTO;
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to getSeckill, seckillDO : {}", JsonUtil.toJson(seckillDO), e);
            throw new SeckillException(ResponseCode.BIZ_E_SECKILL_NOT_EXIST);
        }
    }

    @Override
    public int updateSeckill(SeckillDO seckillDO) throws SeckillException {
        int opNum;
        try {
            opNum = seckillDAO.updateSeckill(seckillDO);
            if (opNum != 1) {
                LOGGER.error("error to updateSeckill, seckillDO : {}",
                        JsonUtil.toJson(seckillDO));
                throw new SeckillException(ResponseCode.BIZ_E_SECKILL_NOT_EXIST);
            }
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to updateSeckill, seckillDO : {}",
                    JsonUtil.toJson(seckillDO), e);
            throw new SeckillException(ResponseCode.DB_OP_ERROR);
        }
        return opNum;
    }

    @Override
    public void invalidSeckill(SeckillDO seckillDO) throws SeckillException {
        try {
            int opNum = seckillDAO.updateSeckill(seckillDO);
            if (opNum != 1) {
                LOGGER.error("error to invalidSeckill, seckillDO : {}", JsonUtil.toJson(seckillDO));
                throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
            }
        } catch (SeckillException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to invalidSeckill, seckillDO : {}", JSONUtil.toJSON(seckillDO), e);
            throw new SeckillException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<SeckillDTO> querySeckill(SeckillQTO seckillQTO) throws SeckillException {

        //根据lifecycle字段，设置时间查询条件
        if (seckillQTO.getLifecycle() != null) {
            Date now = new Date();
            if (seckillQTO.getLifecycle().intValue() == SeckillLifecycle.LIFECYCLE_NOT_BEGIN.getValue()) {
                seckillQTO.setStartTimeLt(now);
            } else if (seckillQTO.getLifecycle().intValue() == ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue()) {
                seckillQTO.setStartTimeGe(now);
                seckillQTO.setEndTimeLe(now);
            } else if (seckillQTO.getLifecycle().intValue() == ActivityLifecycle.LIFECYCLE_ENDED.getValue()) {
                seckillQTO.setEndTimeGt(now);
            }

            // 未开始、进行中、已结束 均显示的是有效活动，无效活动只显示在全部
            if (seckillQTO.getLifecycle().intValue() != 0
		                && seckillQTO.getLifecycle().intValue() != ActivityLifecycle.LIFECYCLE_ENDED.getValue()) {
                seckillQTO.setStatus(ActivityCouponStatus.NORMAL.getValue());
            }
        }

        try {
            List<SeckillDTO> seckillDTOs = ModelUtil.genSeckillDTOList(seckillDAO.querySeckill(seckillQTO));
            return seckillDTOs;
        } catch (Exception e) {
            LOGGER.error("error to querySeckill, seckillQTO : {}", JsonUtil.toJson(seckillQTO), e);
            throw new SeckillException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public List<SeckillDO> querySeckillSimple(SeckillQTO seckillQTO) throws SeckillException {
        try {
            List<SeckillDO> seckillDOs = seckillDAO.querySeckill(seckillQTO);
            return seckillDOs;
        } catch (Exception e) {
            LOGGER.error("error to querySeckillSimple, seckillQTO : {}", JsonUtil.toJson(seckillQTO), e);
            throw new SeckillException(ResponseCode.DB_OP_ERROR);
        }
    }

//    @Override
//    public SeckillDTO getSeckillByItemId(Long itemId, String bizCode) throws SeckillException {
//        try {
//            SeckillDO seckillDO = new SeckillDO();
//            seckillDO.setItemId(itemId);
//            seckillDO.setBizCode(bizCode);
//
//            seckillDO = seckillDAO.getSeckill(seckillDO);
//            if (seckillDO == null) {
//                LOGGER.error("error to getSeckillByItemId, itemId : {}, bizCode : {}", itemId, bizCode);
//                throw new SeckillException(ResponseCode.BIZ_E_SECKILL_NOT_EXIST);
//            }
//            SeckillDTO seckillDTO = ModelUtil.genSeckillDTO(seckillDO);
//            return seckillDTO;
//        } catch (SeckillException e) {
//            throw e;
//        } catch (Exception e) {
//            LOGGER.error("error to getSeckillByItemId, sellerId : {}, skuId : {}, bizCode : {}",
//                    sellerId, skuId, bizCode, e);
//            throw new SeckillException(ResponseCode.BIZ_E_SECKILL_NOT_EXIST);
//        }
//    }

    public void fillUpSeckillDTO(List<SeckillDTO> seckillDTOs) throws SeckillException {
        if (seckillDTOs.isEmpty()) return;

        long currentTime = System.currentTimeMillis();

        for (SeckillDTO seckillDTO : seckillDTOs) {
            if (currentTime < seckillDTO.getStartTime().getTime()) {
                seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_NOT_BEGIN.getValue());
            } else if (currentTime >= seckillDTO.getStartTime().getTime()
                    && currentTime <= seckillDTO.getEndTime().getTime()) {
                seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_IN_PROGRESS.getValue());
            } else {
                seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
            }
            if (seckillDTO.getStatus().intValue() == ActivityStatus.INVALID.getValue().intValue()
                    || seckillDTO.getItemInvalidTime() != null) {
                seckillDTO.setLifecycle(ActivityLifecycle.LIFECYCLE_ENDED.getValue());
            }
        }
    }
}
package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.constant.UserCouponStatus;
import com.mockuai.marketingcenter.common.domain.dto.ActivityCouponDTO;
import com.mockuai.marketingcenter.common.domain.dto.CouponCodeDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.qto.CouponCodeQTO;
import com.mockuai.marketingcenter.common.domain.qto.GrantedCouponQTO;
import com.mockuai.marketingcenter.core.dao.CouponCodeDAO;
import com.mockuai.marketingcenter.core.domain.CouponCodeDO;
import com.mockuai.marketingcenter.core.domain.GrantedCouponDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.CouponCodeManager;
import com.mockuai.marketingcenter.core.manager.GrantedCouponManager;
import com.mockuai.marketingcenter.core.manager.UserManager;
import com.mockuai.marketingcenter.core.util.CouponCodeUtil;
import com.mockuai.marketingcenter.core.util.JsonUtil;
import com.mockuai.marketingcenter.core.util.ModelUtil;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class CouponCodeManagerImpl implements CouponCodeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponCodeManagerImpl.class.getName());

    private static int cpuNum = Runtime.getRuntime().availableProcessors();
    private static int POOL_SIZE = 6;
    private static ExecutorService executorService = Executors.newFixedThreadPool(cpuNum * POOL_SIZE);

    @Autowired
    private CouponCodeDAO couponCodeDAO;

    @Autowired
    private GrantedCouponManager grantedCouponManager;

    @Autowired
    private UserManager userManager;

    public void batchAddCouponCode(ActivityCouponDTO activityCouponDTO, MarketActivityDTO marketActivityDTO) throws MarketingException {
        try {
            executorService.execute(new CouponCodeGenerator(this, activityCouponDTO, marketActivityDTO, couponCodeDAO));
        } catch (Exception e) {
            LOGGER.error("failed when batchAddCouponCode : {}, creatorId : {}", marketActivityDTO.getBizCode(), marketActivityDTO.getCreatorId(), e);
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION);
        }
    }

    @Override
    public CouponCodeDO getByCode(String code) throws MarketingException {
        try {
            List<CouponCodeDO> couponCodeDOs = couponCodeDAO.queryByCode(code);
            if (couponCodeDOs.size() == 0) {
                throw new MarketingException(ResponseCode.BIZ_E_THE_COUPON_CODE_DOES_NOT_EXIST);
            }
            return couponCodeDOs.get(0);
        } catch (MarketingException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("failed to queryByCode, code : {}", code, e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    public List<CouponCodeDTO> queryCouponCode(ActivityCouponDTO activityCouponDTO, CouponCodeQTO couponCodeQTO, String appKey) throws MarketingException {

        // 查询用户 id
        if (StringUtils.isNotEmpty(couponCodeQTO.getUserName())) {
            List<UserDTO> userDTOs = userManager.queryUserByUserName(couponCodeQTO.getUserName(), appKey);
            if (userDTOs != null && userDTOs.size() > 0) {
                couponCodeQTO.setUserIdList(new ArrayList<Long>());
                for (UserDTO userDTO : userDTOs) {
                    couponCodeQTO.getUserIdList().add(userDTO.getId());
                }
            } else {
                couponCodeQTO.setTotalCount(0);
                return Collections.emptyList();
            }
        }

        List<CouponCodeDO> couponCodeDOs = couponCodeDAO.queryCouponCode(couponCodeQTO);
        couponCodeQTO.setTotalCount(couponCodeDAO.queryCouponCodeCount(couponCodeQTO));
        List<CouponCodeDTO> couponCodeDTOs = ModelUtil.genCouponCodeList(couponCodeDOs);

        // 找到已经兑换后的优惠券值对应信息
        GrantedCouponQTO grantedCouponQTO = new GrantedCouponQTO();
        grantedCouponQTO.setCodeList(new ArrayList<String>());
        grantedCouponQTO.setCouponId(couponCodeQTO.getCouponId());
        Map<String, CouponCodeDTO> codeKeyMap = new HashMap<String, CouponCodeDTO>();

        for (CouponCodeDTO couponCodeDTO : couponCodeDTOs) {
            couponCodeDTO.setDiscountAmount(activityCouponDTO.getDiscountAmount());
            if (couponCodeDTO.getStatus().intValue() != UserCouponStatus.UN_ACTIVATE.getValue()) {
                grantedCouponQTO.getCodeList().add(couponCodeDTO.getCode());
                codeKeyMap.put(couponCodeDTO.getCode(), couponCodeDTO);
            }
        }

        List<GrantedCouponDO> grantedCouponDOs = grantedCouponManager.queryGrantedCouponForCouponCode(grantedCouponQTO);
        Set<Long> userIdSet = new HashSet<Long>();
        List<CouponCodeDTO> toFillUserName = new ArrayList<CouponCodeDTO>();

        CouponCodeDTO couponCodeDTO;
        // 填充领取／使用信息
        for (GrantedCouponDO grantedCouponDO : grantedCouponDOs) {
            couponCodeDTO = codeKeyMap.get(grantedCouponDO.getCode());
            if (couponCodeDTO == null) {
                LOGGER.error("the activated coupon code doesn't exist in granted coupon");
                continue;
            }
            if (grantedCouponDO.getReceiverId() != null) {
                toFillUserName.add(couponCodeDTO);
                userIdSet.add(couponCodeDTO.getUserId());
            }
            couponCodeDTO.setOrderId(grantedCouponDO.getOrderId());
            couponCodeDTO.setUseTime(grantedCouponDO.getUseTime());
            couponCodeDTO.setActivateTime(grantedCouponDO.getActivateTime());
        }
        fillUpUserName(toFillUserName, new ArrayList<Long>(userIdSet), appKey);
        return couponCodeDTOs;
    }

    @Override
    public void fillUpUserName(List<CouponCodeDTO> couponCodeDTOs, List<Long> userIds, String appKey) throws MarketingException {

        List<UserDTO> userDTOs = userManager.queryByUserIdList(userIds, appKey);
        Map<Long, UserDTO> userIdKeyMap = new HashMap<Long, UserDTO>();
        for (UserDTO userDTO : userDTOs) {
            userIdKeyMap.put(userDTO.getId(), userDTO);
        }
        UserDTO userDTO;
        for (CouponCodeDTO couponCode : couponCodeDTOs) {
            userDTO = userIdKeyMap.get(couponCode.getUserId());
            if (userDTO != null) {
                couponCode.setReceiverName(userDTO.getName());
            } else {
                LOGGER.error("cannot found user who exchange the coupon code, userId : {}", couponCode.getUserId());
            }
        }
    }

    @Override
    public int updateCouponCode(CouponCodeDO couponCodeDO) throws MarketingException {
        try {
            return couponCodeDAO.update(couponCodeDO);
        } catch (Exception e) {
            LOGGER.error("failed to updateCouponCode, CouponCodeDO : {}", JsonUtil.toJson(couponCodeDO), e);
            throw new MarketingException(ResponseCode.DB_OP_ERROR);
        }
    }

    /**
     * 生成 一卡一码序列，并且处理重复优惠码值
     */
    public class CouponCodeGenerator implements Runnable {

        CouponCodeManager couponCodeManager;
        ActivityCouponDTO activityCouponDTO;
        MarketActivityDTO marketActivityDTO;
        CouponCodeDAO couponCodeDAO;

        public CouponCodeGenerator(CouponCodeManager couponCodeManager, ActivityCouponDTO activityCouponDTO, MarketActivityDTO marketActivityDTO, CouponCodeDAO couponCodeDAO) {
            this.couponCodeManager = couponCodeManager;
            this.activityCouponDTO = activityCouponDTO;
            this.marketActivityDTO = marketActivityDTO;
            this.couponCodeDAO = couponCodeDAO;
        }

        @Override
        public void run() {
            LOGGER.info("start generating the coupon code, {}", JsonUtil.toJson(activityCouponDTO));
            List<CouponCodeDO> couponCodeDOs = new ArrayList<CouponCodeDO>();

            CouponCodeDO couponCodeDO;
            Set<String> codes = null;
            codes = generateCouponCode(null, activityCouponDTO.getTotalCount());

            for (String code : codes) {
                couponCodeDOs.add(wrapCouponCodeDO(code));
            }
            LOGGER.info("the size of code list : {}", codes.size());
            batchAdd(couponCodeDOs, codes);
        }

        private void batchAdd(List<CouponCodeDO> couponCodeDOs, Set<String> codes) {
            try {
                this.couponCodeDAO.batchAddCouponCodes(couponCodeDOs);
            } catch (MarketingException e) {
                // duplicate entry for the key 'code'
                if (e.getCode() == ResponseCode.DB_OP_ERROR_OF_DUPLICATE_ENTRY.getCode()) {
                    makeUpCodeGen(couponCodeDOs, codes, e.getMessage());
                }
            }
        }

        private void makeUpCodeGen(List<CouponCodeDO> couponCodeDOs, Set<String> codes, String duplicateCode) {
            LOGGER.info("duplicate entry {} for key 'code'", duplicateCode);
            CouponCodeDO duplicateDO = null;
            for (CouponCodeDO couponCodeDO : couponCodeDOs) {
                if (couponCodeDO.getCode().equals(duplicateCode)) {
                    duplicateDO = couponCodeDO;
                    break;
                }
            }
            couponCodeDOs.remove(duplicateDO);
            codes.remove(duplicateCode);
            String code;
            do {
                code = CouponCodeUtil.genCode();
            } while (codes.contains(code));
            LOGGER.info("the new code is : {}", code);
            couponCodeDOs.add(wrapCouponCodeDO(code));
            batchAdd(couponCodeDOs, codes);
        }

        private CouponCodeDO wrapCouponCodeDO(String code) {
            CouponCodeDO couponCodeDO = new CouponCodeDO();
            couponCodeDO.setCode(activityCouponDTO.getCouponType() + code);
            couponCodeDO.setActivityId(this.marketActivityDTO.getId());
            couponCodeDO.setActivityCreatorId(this.marketActivityDTO.getCreatorId());
            couponCodeDO.setBizCode(this.marketActivityDTO.getBizCode());
            couponCodeDO.setCouponId(this.activityCouponDTO.getId());
            couponCodeDO.setCouponType(this.activityCouponDTO.getCouponType());
            couponCodeDO.setDeleteMark(0);
            couponCodeDO.setStatus(UserCouponStatus.UN_ACTIVATE.getValue());
            return couponCodeDO;
        }

        public Set<String> generateCouponCode(Set<String> codes, Long size) {

            if (codes == null) {
                codes = new HashSet<String>();
            }
            while (codes.size() < size.longValue()) {
                codes.add(CouponCodeUtil.genCode());
            }
            return codes;
        }
    }
}
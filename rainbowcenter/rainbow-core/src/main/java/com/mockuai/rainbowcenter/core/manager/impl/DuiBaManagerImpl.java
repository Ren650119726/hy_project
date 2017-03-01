package com.mockuai.rainbowcenter.core.manager.impl;

import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.DistDeductDTO;
import com.mockuai.rainbowcenter.common.dto.DuibaRecordOrderDTO;
import com.mockuai.rainbowcenter.core.dao.DuibaConfigurationDAO;
import com.mockuai.rainbowcenter.core.dao.DuibaRecordOrderDAO;
import com.mockuai.rainbowcenter.core.domain.DuibaConfigurationDO;
import com.mockuai.rainbowcenter.core.domain.DuibaRecordOrderDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.DuiBaManager;
import com.mockuai.rainbowcenter.core.manager.RecordOrderManager;
import com.mockuai.rainbowcenter.core.util.SignTool;
import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lizg on 2016/7/19.
 */
@Service
public class DuiBaManagerImpl implements DuiBaManager {

    private static final Logger log = LoggerFactory.getLogger(DuiBaManagerImpl.class);

    @Resource
    private DuibaRecordOrderDAO duibaRecordOrderDAO;

    @Resource
    private VirtualWealthClient virtualWealthClient;

    @Resource
    private DuibaConfigurationDAO duibaConfigurationDAO;

    @Resource
    private RecordOrderManager recordOrderManager;

    @Override
    public DistDeductDTO deductCredits(DuibaRecordOrderDTO duibaRecordOrderDTO, String appKey) throws RainbowException {

        DistDeductDTO distDeductDTO = new DistDeductDTO();

        //查询订单
        DuibaRecordOrderDO duibaRecordOrderDO = new DuibaRecordOrderDO();
        duibaRecordOrderDO.setOrderNum(duibaRecordOrderDTO.getOrderNum());
        DuibaRecordOrderDO recordOrderDO = duibaRecordOrderDAO.getRecordByOrderNum(duibaRecordOrderDO);
        if (null == recordOrderDO) {

            //TODO 查询用户剩余的积分 后面需要加上

            Long totalCredits = 0l;

            String userId = duibaRecordOrderDTO.getUid();
            log.info("[{}] uid:{}", userId);
            Long useAmount = duibaRecordOrderDTO.getCredits();
            String orderId = duibaRecordOrderDTO.getOrderNum();
            log.info("duiba deduct credits, userId: {},orderId:{}, useAmount: {}", userId, orderId, useAmount);

            //查询用户积分
            Response<List<WealthAccountDTO>> wealthAccount = virtualWealthClient.queryWealthAccount(Long.parseLong(userId), 3, appKey);
            log.info("[{}] wealthAccount:{}", wealthAccount.isSuccess());
            if (wealthAccount.isSuccess()) {
                List<WealthAccountDTO> wealthAccountDTOs = wealthAccount.getModule();
                for (WealthAccountDTO wealAccount : wealthAccountDTOs) {
                    totalCredits = wealAccount.getAmount();
                }
            }
            log.info("[{}] total credits:{}", totalCredits);
            if (totalCredits < useAmount) {
                distDeductDTO.setStatus("fail");
                distDeductDTO.setErrorMessage("您的嗨币余额不足购换此商品！");
                distDeductDTO.setBalanceCredits(totalCredits);
                return distDeductDTO;
            }


            String bizNum = recordOrderManager.getBizNum(Long.parseLong(userId));
            log.info("[{}] bizNum:{}", bizNum);

            //新增兑吧订单记录
            DuibaRecordOrderDO duibaRecordOrderDO1 = new DuibaRecordOrderDO();
            duibaRecordOrderDO1.setUid(userId);
            duibaRecordOrderDO1.setCredits(useAmount);
            duibaRecordOrderDO1.setOrderNum(orderId);
            duibaRecordOrderDO1.setStatus(2);   //兑换中
            duibaRecordOrderDO1.setBizNum(bizNum);
            duibaRecordOrderDO1.setDescription(duibaRecordOrderDTO.getDescription());
            duibaRecordOrderDO1.setParams(duibaRecordOrderDTO.getParams());
            duibaRecordOrderDO1.setActualPrice(duibaRecordOrderDTO.getActualPrice());
            duibaRecordOrderDO1.setFacePrice(duibaRecordOrderDTO.getFacePrice());
            duibaRecordOrderDO1.setType(duibaRecordOrderDTO.getType());
            duibaRecordOrderDO1.setIp(duibaRecordOrderDTO.getIp());
            duibaRecordOrderDO1.setWaitAudit(duibaRecordOrderDTO.getWaitAudit());
            duibaRecordOrderDO1.setExchangeTime(duibaRecordOrderDTO.getExchangeTime());
            Long id = duibaRecordOrderDAO.addRecordOrder(duibaRecordOrderDO1);
            log.info("[{}] id :{}", id);

            //预扣减积分(嗨币)
            Response<Void> preUseWealth = virtualWealthClient.preUseUserWealth(Long.parseLong(userId), 3, useAmount, id, appKey);
            log.info("[{}] preUseWealth:{}", preUseWealth.isSuccess());
            if (!preUseWealth.isSuccess()) {
                log.info("duiba deuct fail...................");
                distDeductDTO.setStatus("fail");
                distDeductDTO.setErrorMessage("系统内部错误，请重试！");
                distDeductDTO.setBalanceCredits(totalCredits);

                duibaRecordOrderDO.setId(id);
                duibaRecordOrderDAO.updateRemoveById(duibaRecordOrderDO);
                log.info("duiba deuct fail end...................");
                return distDeductDTO;

            }

            //计算剩余的嗨币
            Long overCredits = totalCredits - duibaRecordOrderDTO.getCredits();
            distDeductDTO.setStatus("ok");
            distDeductDTO.setBizId(bizNum);
            distDeductDTO.setErrorMessage("");
            distDeductDTO.setBalanceCredits(overCredits);
            log.info("duiba deuct success...................");
        }

        return distDeductDTO;
    }

    @Override
    public String exchangeResultNotice(DuibaRecordOrderDTO duibaRecordOrderDTO, String appKey) throws RainbowException {
        String result = "ok";
        Integer status = 1;
        log.info("[{}] exchange success:{}", duibaRecordOrderDTO.isSuccess());
        String bizNum = duibaRecordOrderDTO.getBizNum();
        log.info("[{}] bizNum:{}", bizNum);
        if (duibaRecordOrderDTO.isSuccess()) {

            //查询订单
            DuibaRecordOrderDO duibaRecordOrderDO1 = new DuibaRecordOrderDO();
            duibaRecordOrderDO1.setBizNum(bizNum);
            DuibaRecordOrderDO recordOrderDO = this.duibaRecordOrderDAO.getRecordByOrderNum(duibaRecordOrderDO1);
            log.info("[{}] recordOrderDO:{}",recordOrderDO);
            if (null != recordOrderDO) {

                //正式扣减积分
                Response<Void> useWealth = virtualWealthClient.useUserWealth(Long.parseLong(recordOrderDO.getUid()), recordOrderDO.getId(), appKey);
                log.info("[{}] useUserWealth success:{}", useWealth.isSuccess());
                if (useWealth.isSuccess()) {
                    status = 0;
                    log.info("deduction result success");
                }
            }

        }
        //更新兑吧订单记录
        DuibaRecordOrderDO duibaRecordOrderDO = new DuibaRecordOrderDO();
        if (status == 1) {
            duibaRecordOrderDO.setErrorMessage(duibaRecordOrderDTO.getErrorMessage());
        }
        duibaRecordOrderDO.setStatus(status);
        duibaRecordOrderDO.setBizNum(bizNum);
        int count = duibaRecordOrderDAO.updateStatusByorderNum(duibaRecordOrderDO);
        log.info("[{}] exchange result end :{}", count);

        return result;
    }

    @Override
    public String creditAutoLogin(String uid, String credits) throws RainbowException {

        DuibaConfigurationDO duibaConfigurationDO = duibaConfigurationDAO.getConfiguration();

        if (null == duibaConfigurationDO) {
            throw new RainbowException(ResponseCode.SYS_E_DATABASE_ERROR, "get duiba configuration error");
        }
        String loginUrl = duibaConfigurationDO.getLoginUrl();
        log.info("[{}] loginUrl:{}", loginUrl);
        String appKey = duibaConfigurationDO.getAppKey();
        log.info("[{}] appKey:{}", appKey);
        String appSecret = duibaConfigurationDO.getAppSecret();
        log.info("[{}] appSecret:{}", appSecret);
        Map<String, String> params = new HashMap<String, String>();
        Long timestamp = new Date().getTime();
        params.put("uid", uid);
        params.put("credits", credits + "");
        params.put("appSecret", appSecret);
        params.put("appKey", appKey);
        params.put("timestamp", timestamp + "");
        String sign = SignTool.sign(params);
        loginUrl += "uid=" + uid + "&credits=" + credits + "&appKey=" + appKey + "&sign=" + sign + "&timestamp=" + timestamp;
        log.info("[{}] loginUrl:{}", loginUrl);
        return loginUrl;
    }

}

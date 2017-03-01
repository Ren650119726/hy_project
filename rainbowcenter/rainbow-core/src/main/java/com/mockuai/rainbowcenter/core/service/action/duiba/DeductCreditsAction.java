package com.mockuai.rainbowcenter.core.service.action.duiba;

import com.mockuai.rainbowcenter.common.api.RainbowResponse;
import com.mockuai.rainbowcenter.common.api.Request;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.DistDeductDTO;
import com.mockuai.rainbowcenter.common.dto.DuibaRecordOrderDTO;
import com.mockuai.rainbowcenter.common.util.CommonUtils;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.DuiBaManager;
import com.mockuai.rainbowcenter.core.service.RequestContext;
import com.mockuai.rainbowcenter.core.service.action.Action;
import com.mockuai.rainbowcenter.core.util.PropertyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by lizg on 2016/7/19.
 */
@Controller
public class DeductCreditsAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(DeductCreditsAction.class);

    @Resource
    private DuiBaManager duiBaManager;
    @Override
    public RainbowResponse execute(RequestContext context) {
        Request request = context.getRequest();

        String uid = (String)request.getParam("uid");
        String credits = (String)request.getParam("credits");
        String timestamp = (String)request.getParam("timestamp");
        String description = (String)request.getParam("description");
        String orderNum = (String)request.getParam("orderNum");
        String type = (String)request.getParam("type");
        String facePrice = (String)request.getParam("facePrice");
        String actualPrice = (String) request.getParam("actualPrice");
        String ip = (String) request.getParam("ip");
        String waitAudit = (String) request.getParam("waitAudit");
        String params = (String) request.getParam("params");

        DistDeductDTO distDeductDTO = null;
            try {
                if (uid == null) {
                    log.error("uid is null");
                    throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "uid is null");
                }
                if (credits == null) {
                    log.error("credits is null");
                    throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "credits is null");
                }
                if (orderNum == null) {
                    log.error("orderNum is null");
                    throw new RainbowException(ResponseCode.PARAM_E_PARAM_NULL, "orderNum is null");
                }
                Integer isWaitAudit;
                DuibaRecordOrderDTO duibaRecordOrderDTO = new DuibaRecordOrderDTO();
                duibaRecordOrderDTO.setUid(uid);
                duibaRecordOrderDTO.setCredits(Long.parseLong(credits));
                Date date = CommonUtils.timestampTodate(timestamp);
                log.info("[{}] date:{}",date);
                duibaRecordOrderDTO.setExchangeTime(date);
                duibaRecordOrderDTO.setDescription(description);
                duibaRecordOrderDTO.setOrderNum(orderNum);
                duibaRecordOrderDTO.setType(type);
                duibaRecordOrderDTO.setFacePrice(Integer.parseInt(facePrice));
                duibaRecordOrderDTO.setActualPrice(Integer.parseInt(actualPrice));
                duibaRecordOrderDTO.setIp(ip);
                if (Boolean.valueOf(waitAudit)) {
                    isWaitAudit = 1;
                }else {
                    isWaitAudit = 0;
                }
                duibaRecordOrderDTO.setWaitAudit(isWaitAudit);
                duibaRecordOrderDTO.setParams(params);

                distDeductDTO = duiBaManager.deductCredits(duibaRecordOrderDTO, PropertyHelper.getKeyValue("haiyun_appkey"));



            } catch (RainbowException e) {
                return new RainbowResponse(e.getResponseCode(), e.getMessage());
            }

        return new RainbowResponse(distDeductDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.DUIBA_DEDUCT_CREDITS.getActionName();
    }
}


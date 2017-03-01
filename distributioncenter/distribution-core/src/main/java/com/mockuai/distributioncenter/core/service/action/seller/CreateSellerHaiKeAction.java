package com.mockuai.distributioncenter.core.service.action.seller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.HkProtocolRecordDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.HkProtocolRecordManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;

/**
 * Created by duke on 15/10/19.
 */
@Service
public class CreateSellerHaiKeAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CreateSellerHaiKeAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private HkProtocolRecordManager hkProtocolRecordManager; 
    @Override
    public DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        Long userId = request.getLong("userId");
        String appKey = (String) context.get("appKey");
        Integer appType = (Integer) context.get("appType");
        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        // 检查该注册的用户是不是已经是一个卖家了
        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        if (sellerDTO != null && sellerDTO.getStatus().equals(1)) {
            // 卖家已经存在，不允许创建
            log.error("seller already exists, seller_id: {}, user_id: {}", sellerDTO.getId(), sellerDTO.getUserId());
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "该用户已经是一个嗨客");
        }

        if (sellerDTO == null) {
            sellerDTO = new SellerDTO();
            sellerDTO.setRealName("");
            sellerDTO.setUserName("");
            sellerDTO.setWechatId("");
            sellerDTO.setUserId(userId);
            sellerDTO.setStatus(1);
            sellerDTO.setLevelId(1L);
            sellerDTO.setDirectCount(0L);
            sellerDTO.setGroupCount(0L);
            sellerDTO.setInviterCode("");
            Long id = sellerManager.add(sellerDTO);
            // generate invite code
            // 1000000 + id ==> inviteCode
            String inviteCode = String.valueOf(1000000L + id);
            sellerDTO.setId(id);
            sellerDTO.setInviterCode(inviteCode);
            sellerManager.update(sellerDTO);
        } else {
            // 更新卖家
        	sellerDTO.setStatus(1);
        	sellerManager.update(sellerDTO);
        }
        
        userManager.updateRoleType(userId, 2L, appKey);
        HkProtocolRecordDTO hkProtocolRecord = new HkProtocolRecordDTO();
        hkProtocolRecord.setUserId(userId);
        hkProtocolRecord.setAppType(appType);
        hkProtocolRecord.setAgreeDesc("同意协议");
        hkProtocolRecordManager.add(hkProtocolRecord);
        return new DistributionResponse("10000");
    }

    @Override
    public String getName() {
        return ActionEnum.CREATE_SELLER_HAIKE.getActionName();
    }
}

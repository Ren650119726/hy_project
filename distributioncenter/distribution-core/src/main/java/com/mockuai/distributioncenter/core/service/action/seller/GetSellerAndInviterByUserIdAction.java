package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.SellerConfigManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.SellerRelationshipManager;
import com.mockuai.distributioncenter.core.manager.UserManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 15/10/20.
 */
@Service
public class GetSellerAndInviterByUserIdAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(GetSellerAndInviterByUserIdAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Autowired
    private SellerConfigManager sellerConfigManager;

    @Autowired
    private UserManager userManager;

    @Override
    public DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        Long userId = request.getLong("userId");
        String appKey = (String) context.get("appKey");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        SellerDTO sellerDTO = sellerManager.getByUserId(userId);

        if (sellerDTO != null) {
            // 获得头像
            UserDTO user = userManager.getUserByUserId(sellerDTO.getUserId(), appKey);
            if (user != null) {
                sellerDTO.setHeadImgUrl(user.getImgUrl());
            } else {
                log.error("user not exists, userId: {}", sellerDTO.getUserId());
            }

            // 获得邀请人
            SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(userId);
            if (relationshipDTO == null) {
                log.error("user not in relationship, userId: {}", userId);
                throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "user not in relationship");
            }
            if (!relationshipDTO.getParentId().equals(0L)) {
                SellerDTO inviterSeller = sellerManager.getByUserId(relationshipDTO.getParentId());
                if (inviterSeller == null) {
                    log.error("user is not a seller, userId: {}", relationshipDTO.getParentId());
                    throw new DistributionException(ResponseCode.SERVICE_EXCEPTION);
                }

                sellerDTO.setInviterSeller(inviterSeller);
            }

            // 获得等级信息
            SellerConfigDTO sellerConfigDTO = sellerConfigManager.getSellerConfig(sellerDTO.getLevelId());
            sellerDTO.setLevelName(sellerConfigDTO.getLevelName());
        } else {
            log.error("seller not exists, userId: {}", userId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "seller not exists");
        }

        return new DistributionResponse(sellerDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SELLER_AND_INVITER_BY_USER_ID.getActionName();
    }
}

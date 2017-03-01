package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.RelationshipType;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.DistShopDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerConfigDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerRelationshipDTO;
import com.mockuai.distributioncenter.common.domain.qto.SellerConfigQTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.*;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.util.JsonUtil;
import com.mockuai.usercenter.common.dto.UserDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by duke on 15/10/19.
 */
@Service
public class CreateSellerAction extends TransAction {
    private static final Logger log = LoggerFactory.getLogger(CreateSellerAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private ShopManager shopManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private SellerRelationshipManager sellerRelationshipManager;

    @Autowired
    private SellerConfigManager sellerConfigManager;

    @Override
    public DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        Long userId = request.getLong("userId");
        String realName = request.getString("realName");
        Long inviterSellerId = request.getLong("inviterSellerId");
        String mobile = request.getString("mobile");
        String password = request.getString("password");
        String wechatId = request.getString("wechatId");
        Integer status = request.getInteger("status");
        String appKey = (String) context.get("appKey");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        if (StringUtils.isBlank(realName)) {
            log.error("realName is black");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        if (inviterSellerId == null) {
            log.error("inviterSellerId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        if (StringUtils.isBlank(wechatId)) {
            log.error("wechatId is black");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        if (status == null) {
            log.error("status is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        // 检查该注册的用户是不是已经是一个卖家了
        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        if (sellerDTO != null && sellerDTO.getStatus().equals(1)) {
            // 卖家已经存在，不允许创建
            log.error("seller already exists, seller_id: {}, user_id: {}", sellerDTO.getId(), sellerDTO.getUserId());
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "该用户已经是一个卖家");
        }

        // 查询卖家等级
        SellerConfigQTO sellerConfigQTO = new SellerConfigQTO();
        sellerConfigQTO.setLevel(1);
        sellerConfigQTO.setCount(1);
        sellerConfigQTO.setOffset(0L);
        List<SellerConfigDTO> sellerConfigDTOs = sellerConfigManager.querySellerConfig(sellerConfigQTO);
        if (sellerConfigDTOs.isEmpty()) {
            log.error("seller level not exists, level: 1");
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "seller level not exists");
        }
        SellerConfigDTO sellerConfigDTO = sellerConfigDTOs.get(0);

        // 创建用户
        UserDTO user = userManager.getUserByUserId(userId, appKey);
        if (user == null) {
            // 创建用户
            user = new UserDTO();
            user.setMobile(mobile);
            user.setPassword(password);
            user.setName(mobile);
            user = userManager.addUser(user, appKey);
        } else {
            // 检查手机号是否已经存在
            UserDTO userDTO = userManager.getUserByMobile(mobile, appKey);
            if (userDTO != null && !userDTO.getId().equals(user.getId())) {
                // 如果该手机号的用户和当前的用户不一致，则报错
                throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "手机号已经被使用");
            }
            userDTO = new UserDTO();
            userDTO.setId(user.getId());
            if (!StringUtils.isBlank(password)) {
                userDTO.setPassword(password);
            }
            if (!StringUtils.isBlank(mobile)) {
                userDTO.setMobile(mobile);
            }
            // 更新用户
            userManager.updateUser(userDTO, appKey);
        }

        // 查询卖家，如果卖家已经存在，则不创建
        sellerDTO = sellerManager.getByUserId(user.getId());
        // 获得邀请人的信息
        SellerDTO inviterSeller = sellerManager.get(inviterSellerId);
        if (inviterSeller == null) {
            log.error("inviter seller is not exists, sellerId: {}", inviterSellerId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "inviter seller is not exists");
        }

        if (sellerDTO == null) {
            sellerDTO = new SellerDTO();
            sellerDTO.setRealName(realName);
            sellerDTO.setUserName(mobile);
            sellerDTO.setWechatId(wechatId);
            sellerDTO.setUserId(user.getId());
            sellerDTO.setStatus(status);
            sellerDTO.setLevelId(sellerConfigDTO.getId());
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
            log.info("seller userId: {}, inviterSeller userId: {}", sellerDTO.getUserId(), inviterSeller.getUserId());
            userManager.updateInvitertId(sellerDTO.getUserId(), inviterSeller.getUserId(), appKey);

            // 创建店铺
            DistShopDTO distShopDTO = new DistShopDTO();
            distShopDTO.setShopName(realName + "的嗨店");
            distShopDTO.setShopDesc("欢迎光临我的小店");
            distShopDTO.setQrcodeUrl("");
            distShopDTO.setSellerId(sellerDTO.getId());
            shopManager.add(distShopDTO);
        } else {
            // 更新卖家
            sellerDTO.setRealName(realName);
            sellerDTO.setUserName(mobile);
            sellerDTO.setWechatId(wechatId);
            sellerManager.update(sellerDTO);
        }

        // 判断用户是否已经在关系中
        SellerRelationshipDTO relationshipDTO = sellerRelationshipManager.getByUserId(userId);
        if (inviterSeller.getUserId().equals(userId)) {
            // 如果邀请人是自己，则报错
            log.error("inviter is equal to current user, inviter userId: {}, current userId: {}", inviterSeller.getUserId(), userId);
            throw new DistributionException(ResponseCode.SERVICE_EXCEPTION, "邀请人和被邀请人是同一个人，关系非法");
        }
        if (relationshipDTO == null) {
            relationshipDTO = new SellerRelationshipDTO();
            relationshipDTO.setUserId(user.getId());
            relationshipDTO.setParentId(inviterSeller.getUserId());
            relationshipDTO.setType(RelationshipType.SELLER_TO_SELLER.getValue());
            // 预注册，关系状态为0
            relationshipDTO.setStatus(0);
            sellerRelationshipManager.add(relationshipDTO);
        } else {
            if (!inviterSeller.getUserId().equals(relationshipDTO.getParentId())) {
                // 如果邀请人变更了，则重新生成关系
                // 先删除原先的关系
                sellerRelationshipManager.delete(relationshipDTO.getId());

                // 生成新关系
                relationshipDTO = new SellerRelationshipDTO();
                relationshipDTO.setUserId(user.getId());
                relationshipDTO.setParentId(inviterSeller.getUserId());
                relationshipDTO.setType(RelationshipType.SELLER_TO_SELLER.getValue());
                // 预注册，关系状态为0
                relationshipDTO.setStatus(0);
                sellerRelationshipManager.add(relationshipDTO);
            }
        }

        return new DistributionResponse(sellerDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.CREATE_SELLER.getActionName();
    }
}

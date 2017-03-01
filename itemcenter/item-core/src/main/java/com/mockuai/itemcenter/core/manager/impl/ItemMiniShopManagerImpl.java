package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.distributioncenter.client.MiniShopClient;
import com.mockuai.distributioncenter.common.api.Response;
//import com.mockuai.distributioncenter.common.domain.dto.DistributorPlanDTO;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.AppManager;
import com.mockuai.itemcenter.core.manager.ItemMiniShopManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.FetchProfile;
import javax.mail.UIDFolder;
import java.util.List;

/**
 * Created by yindingyu on 16/3/16.
 */
@Service
public class ItemMiniShopManagerImpl implements ItemMiniShopManager {

//    @Resource
//    private MiniShopClient miniShopClient;
//
//    @Resource
//    private AppManager appManager;
//
//    private static final Logger log = LoggerFactory.getLogger(ItemMiniShopManagerImpl.class);
//
//    @Override
//    public double getDirectCommissionRatio(ItemSearchDTO itemSearchDTO) throws ItemException {
//
//        Response<DistributorPlanDTO> response = null;
//
//        AppInfoDTO appInfoDTO = appManager.getAppInfoByType(itemSearchDTO.getBizCode(), AppTypeEnum.APP_WAP);
//
//        try {
//            response = miniShopClient.getPlanByCategoryIdAndGroupId(itemSearchDTO.getCategoryId(), itemSearchDTO.getGroupId(), appInfoDTO.getAppKey());
//        } catch (Exception e) {
//            log.error("查询微小店分佣信息时出现异常 itemDTO : {} error : {}", JsonUtil.toJson(itemSearchDTO), e.getMessage());
//            return 0.0d;
//        }
//
//        if (response.isSuccess()) {
//
//            DistributorPlanDTO distributorPlanDTO = response.getModule();
//
//            if (distributorPlanDTO == null) {
//                return 0.0;
//            } else {
//                return distributorPlanDTO.getDirectDistRatio();
//            }
//
//        } else {
//            log.error("查询微小店分佣信息时出现异常 itemDTO : {} code : {} message : {}", JsonUtil.toJson(itemSearchDTO), response.getCode(), response.getMessage());
//            return 0.0d;
//        }
//
//
//    }
}

package com.mockuai.distributioncenter.core.service.action.seller;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.SellerCenterDTO;
import com.mockuai.distributioncenter.common.domain.dto.SellerDTO;
import com.mockuai.distributioncenter.core.api.RequestAdapter;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.DataManager;
import com.mockuai.distributioncenter.core.manager.ImageManager;
import com.mockuai.distributioncenter.core.manager.SellerManager;
import com.mockuai.distributioncenter.core.manager.VirtualWealthManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.Action;
import com.mockuai.virtualwealthcenter.common.domain.dto.TotalWealthAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by duke on 16/5/19.
 */
@Service
public class GetSellerCenterAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(GetSellerCenterAction.class);

    @Autowired
    private SellerManager sellerManager;

    @Autowired
    private VirtualWealthManager virtualWealthManager;

    @Autowired
    private ImageManager imageManager;

    @Autowired
    private DataManager dataManager;

    @Override
    public DistributionResponse execute(RequestContext context) throws DistributionException {
        RequestAdapter request = context.getRequestAdapter();
        Long userId = request.getLong("userId");
        String appKey = request.getString("appKey");

        if (userId == null) {
            log.error("userId is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL);
        }

        SellerDTO sellerDTO = sellerManager.getByUserId(userId);
        SellerCenterDTO sellerCenterDTO = null;
        if (sellerDTO != null && sellerDTO.getStatus().equals(1)) {
            sellerCenterDTO = new SellerCenterDTO();
            sellerCenterDTO.setId(sellerDTO.getId());
            sellerCenterDTO.setUserId(sellerDTO.getUserId());
            TotalWealthAccountDTO totalWealthAccountDTO = virtualWealthManager.queryVirtualWealthCombine(sellerDTO.getUserId(), appKey);
            sellerCenterDTO.setTotalInCome(totalWealthAccountDTO.getVirtualWealth().getTotal());
            sellerCenterDTO.setTodayInCome(totalWealthAccountDTO.getVirtualWealth().getAmount() + totalWealthAccountDTO.getVirtualWealth().getTransitionAmount());
            sellerCenterDTO.setTotalHiCoin(totalWealthAccountDTO.getHiCoin().getAmount() + totalWealthAccountDTO.getHiCoin().getTransitionAmount());
            // 获得总pv数据
            /*Map<DataResultDTO, DataDTO> resultMap = dataManager.getDataBySum(sellerDTO.getId(), null, "mallPV", TimeTypeEnum.ALL.getValue(), null, null, null, appKey);
            if (!resultMap.isEmpty()) {
                DataDTO dataDTO = new ArrayList<DataDTO>(resultMap.values()).get(0);
                sellerCenterDTO.setTotalAccessCount(Long.parseLong(dataDTO.getValue()));
            } else {
                sellerCenterDTO.setTotalAccessCount(0L);
            }*/
            // 获得图片
            sellerCenterDTO.setInviterCodeUrl(imageManager.getImage("RECOMMEND", userId, appKey));
            sellerCenterDTO.setShopUrl(imageManager.getImage("SHOP", userId, appKey));
        }
        return new DistributionResponse(sellerCenterDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.GET_SELLER_CENTER.getActionName();
    }
}

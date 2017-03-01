package com.mockuai.tradecenter.core.service.action.order.add.step;

import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.tradecenter.common.api.TradeResponse;
import com.mockuai.tradecenter.common.domain.HigoExtraInfoDTO;
import com.mockuai.tradecenter.core.service.RequestContext;
import com.mockuai.tradecenter.core.service.ResponseUtils;
import com.mockuai.tradecenter.core.util.ModelUtil;

/**
 * Created by zengzhangqiang on 5/22/16.
 */
public class HandleHigoExtraInfoStep extends TradeBaseStep {
    @Override
    public StepName getName() {
        return StepName.HANDLE_HIGO_EXTRA_INFO_STEP;
    }

    @Override
    public TradeResponse execute() {
        SettlementInfo settlement = (SettlementInfo) this.getAttr("settlement");
        BizInfoDTO bizInfoDTO = (BizInfoDTO) this.getAttr("bizInfo");


        //如果是跨境商城，则需要填充跨境相关信息（海关税费等等）
        if(bizInfoDTO.getBizPropertyMap().containsKey(BizPropertyKey.HIGO_MARK) &&
                "1".equals(bizInfoDTO.getBizPropertyMap().get(BizPropertyKey.HIGO_MARK).getValue())){
            HigoExtraInfoDTO higoExtraInfoDTO = ModelUtil.convert2HigoExtraInfoDTO(settlement.getHigoExtraInfo());

            //将跨境相关信息放入管道上下文中
            this.setAttr("higoExtraInfoDTO", higoExtraInfoDTO);
        }


        return ResponseUtils.getSuccessResponse();
    }
}

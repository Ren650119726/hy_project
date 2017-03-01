package com.mockuai.distributioncenter.core.service.action.gains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mockuai.distributioncenter.common.api.DistributionResponse;
import com.mockuai.distributioncenter.common.api.Request;
import com.mockuai.distributioncenter.common.constant.ActionEnum;
import com.mockuai.distributioncenter.common.constant.ResponseCode;
import com.mockuai.distributioncenter.common.domain.dto.GainsSetDTO;
import com.mockuai.distributioncenter.core.dao.GainsSetDAO;
import com.mockuai.distributioncenter.core.domain.GainsSetDO;
import com.mockuai.distributioncenter.core.exception.DistributionException;
import com.mockuai.distributioncenter.core.manager.GainsSetManager;
import com.mockuai.distributioncenter.core.service.RequestContext;
import com.mockuai.distributioncenter.core.service.action.TransAction;
import com.mockuai.distributioncenter.core.util.JsonUtil;

/**
 * Created by lizg on 2016/8/29.
 */

@Service
public class AddGainsSetAction extends TransAction{

    private static final Logger log = LoggerFactory.getLogger(AddGainsSetAction.class);

    @Autowired
    private GainsSetManager gainsSetManager;


    @Autowired
    private GainsSetDAO gainsSetDAO;

    @Override
    protected DistributionResponse doTransaction(RequestContext context) throws DistributionException {
        Request request = context.getRequest();
        GainsSetDTO gainsSetDTO = (GainsSetDTO)request.getParam("gainsSetDTO");
        log.info("[{}] gainsSetDTO:{}",JsonUtil.toJson(gainsSetDTO));
        if (null == gainsSetDTO) {
              log.error("gainsSetDTO is null");
            throw new DistributionException(ResponseCode.PARAMETER_NULL,"gainsSetDTO IS NULL");
        }

        //先获取收益设置
        GainsSetDO getGains = this.gainsSetDAO.get();
        log.info("[{}] getGains:{}",getGains);
        if (null == getGains) {
            Long id = gainsSetManager.add(gainsSetDTO);
            gainsSetDTO.setId(id);
        }else {
            log.info("start up gains set .............");
            Long id = getGains.getId();
            gainsSetDTO.setId(id);

            Integer gainsSetCount =  gainsSetManager.update(gainsSetDTO);
            log.info("[{}] gainsSetCount:{}",gainsSetCount);
        }

        return new DistributionResponse(gainsSetDTO);
    }

    @Override
    public String getName() {
        return ActionEnum.ADD_GAINS_SET.getActionName();
    }
}

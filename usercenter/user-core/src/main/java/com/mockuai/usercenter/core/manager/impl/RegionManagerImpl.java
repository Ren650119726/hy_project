package com.mockuai.usercenter.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.usercenter.common.constant.ResponseCode;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.RegionManager;
import com.mockuai.usercenter.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by duke on 15/11/6.
 */
@Service
public class RegionManagerImpl implements RegionManager {
    private static final Logger log = LoggerFactory.getLogger(RegionManagerImpl.class);

    @Resource
    private RegionClient regionClient;
    @Override
    public List<RegionDTO> queryRegion(RegionQTO regionQTO, String appKey) throws UserException {
        Response<List<RegionDTO>> response = regionClient.queryRegion(regionQTO, appKey);
        if (response.isSuccess()) {
            return response.getModule();
        } else {
            log.error("query region error, condition: {}", JsonUtil.toJson(regionQTO));
            throw new UserException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "区域查询异常");
        }
    }
}

package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.RegionManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class RegionManagerImpl implements RegionManager{

    private static final Logger LOG = LoggerFactory.getLogger(RegionManagerImpl.class);

    @Resource
    private RegionClient regionClient;

    @Override
    public List<RegionDTO> queryRegion(String parentId,String appKey) throws ShopException {

        RegionQTO regionQTO = new RegionQTO();
        regionQTO.setParentCode(parentId);

        Response<List<RegionDTO>> response = null;

        try {

            response = regionClient.queryRegion(regionQTO, appKey);

        }catch (Exception e){
            LOG.error("查询delivery_center地区列表时出现问题",e);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"查询delivery_center地区列表时出现问题");
        }

        if(response==null){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,"查询delivery_center地区列表时出现问题");
        }

        if(response.getCode()!=ResponseCode.SUCCESS.getCode()){
            throw ExceptionUtil.getException(ResponseCode.SYS_E_SERVICE_EXCEPTION,response.getMessage());
        }

        if(response.getModule()==null){
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"查询不到对应的地区列表");
        }

        List<RegionDTO> regionDTOs = response.getModule();

        return regionDTOs;
    }
}

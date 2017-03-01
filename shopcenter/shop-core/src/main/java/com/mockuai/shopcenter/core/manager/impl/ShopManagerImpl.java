package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.domain.dto.LastDaysCountDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.shopcenter.core.dao.ShopDAO;
import com.mockuai.shopcenter.core.domain.ShopDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopManager;
import com.mockuai.shopcenter.core.util.ShopStatusUtil;
import com.mockuai.shopcenter.core.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ziqi.
 */
@Service
public class ShopManagerImpl implements ShopManager {

    private static final Logger logger = LoggerFactory.getLogger(ShopManagerImpl.class);

    @Resource
    private ShopDAO shopDAO;

    @Override
    public ShopDTO addShop(ShopDTO shopDTO)  throws ShopException {
        ShopDO shopDO = new ShopDO();
        BeanUtils.copyProperties(shopDTO, shopDO);
        Long id = shopDAO.addShop(shopDO);
        shopDTO.setId(id);
        return shopDTO;
    }

    @Override
    public boolean isExistsShop(ShopDTO shopDTO) throws ShopException {
        ShopDO shopDO = new ShopDO();
        BeanUtils.copyProperties(shopDTO, shopDO);
        return shopDAO.isExistShop(shopDO);
    }

    @Override
    public List<ShopDTO> queryShop(ShopQTO shopQTO)  throws ShopException {
        List<ShopDO> shopDOList = shopDAO.queryShop(shopQTO);
        List<ShopDTO> shopDTOList = new ArrayList<ShopDTO>();
        for(ShopDO shopDO: shopDOList) {
            ShopDTO shopDTO = new ShopDTO();
            BeanUtils.copyProperties(shopDO, shopDTO);
            // 时间;
            shopDTO.setCreateTime(TimeUtil.getFormatTime(shopDO.getGmtCreated(), TimeUtil.FORMAT_TIME));
            // 状态;
            shopDTO.setShopStatusName(ShopStatusUtil.getStatusName(shopDO.getShopStatus()));
            shopDTOList.add(shopDTO);
        }
        return shopDTOList;
    }

    @Override
    public Integer countShop(ShopQTO shopQTO) throws ShopException {

        return shopDAO.countShop(shopQTO);
    }

    /**
     * @param shopQTO
     * @return
     */
    public List<LastDaysCountDTO> countLastEveryDayShop(ShopQTO shopQTO) throws ShopException {

        return shopDAO.countLastEveryDayShop(shopQTO);
    }

    @Override
    public ShopDTO getShop(Long sellerId)  throws ShopException {
        ShopDO query = new ShopDO();
        query.setSellerId(sellerId);
        query.setDeleteMark(0);
        ShopDO shopDO = shopDAO.getShop(query);
        ShopDTO result = new ShopDTO();
        BeanUtils.copyProperties(shopDO, result);
        result.setCreateTime(TimeUtil.getFormatTime(shopDO.getGmtCreated(), TimeUtil.FORMAT_TIME));
        return result;
    }

    @Override
    public Boolean updateShopStatus(Long sellerId, Integer status)  throws ShopException {

        Integer count = shopDAO.updateShopStatus(sellerId, status);
        if (count > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update shop error");
        }
    }

    public Boolean updateShop(ShopDTO shopDTO) throws ShopException {
        ShopDO shopDO = new ShopDO();
        BeanUtils.copyProperties(shopDTO, shopDO);
        Integer count =  shopDAO.updateShop(shopDO);
        if (count > 0) {
            return true;
        } else {
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update shop error");
        }
    }

    @Override
    public ShopDTO getShopById(Long shopId, String bizCode) throws ShopException  {

        ShopDO query = new ShopDO();
        query.setId(shopId);
        query.setBizCode(bizCode);

        ShopDO shopDO = shopDAO.getShop(query);

        ShopDTO shopDTO = new ShopDTO();

        BeanUtils.copyProperties(shopDO,shopDTO);
        return shopDTO;
    }

}

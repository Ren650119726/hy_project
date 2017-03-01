package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.domain.dto.LastDaysCountDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.shopcenter.core.exception.ShopException;

import java.util.List;

/**
 * Created by ziqi.
 */
public interface ShopManager {

    /**
     * 增加店铺;
     * @param shopDTO
     * @return
     */
    public ShopDTO addShop(ShopDTO shopDTO) throws ShopException;

    /**
     * 店铺唯一性判断;
     * @param shopDTO
     * @return
     */
    public boolean isExistsShop(ShopDTO shopDTO) throws ShopException;

    /**
     * 查询店铺
     * @param shopQTO
     * @return
     */
    public List<ShopDTO> queryShop(ShopQTO shopQTO) throws ShopException;

    /**
     * 查询店铺
     * @param shopQTO
     * @return
     */
    public Integer countShop(ShopQTO shopQTO) throws ShopException;

    /**
     * @param shopQTO
     * @return
     */
    public List<LastDaysCountDTO> countLastEveryDayShop(ShopQTO shopQTO) throws ShopException;

    /**
     * sellerId获取;
     * @param sellerId
     * @return
     */
    public ShopDTO getShop(Long sellerId) throws ShopException;

    /**
     * 更改店铺状态;
     * @param status
     * @return
     */
    public Boolean updateShopStatus(Long sellerId, Integer status) throws ShopException;

    /**
     * 更新店铺信息;
     * @param shopDTO
     * @return
     * @throws ShopException
     */
    public Boolean updateShop(ShopDTO shopDTO) throws ShopException;

    ShopDTO getShopById(Long shopId, String bizCode) throws ShopException;
}

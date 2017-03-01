package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.ShopDO;
import com.mockuai.shopcenter.domain.dto.LastDaysCountDTO;
import com.mockuai.shopcenter.domain.qto.ShopQTO;
import com.mockuai.shopcenter.core.exception.ShopException;

import java.util.List;

/**
 * Created by ziqi.
 */
public interface ShopDAO {

    /**
     * 增加店铺;
     * @param shopDO
     * @return
     */
    Long addShop(ShopDO shopDO) throws ShopException;

    /**
     * 根据id和卖家id获取商品;
     * @param shopDO
     * @return
     */
    ShopDO getShop(ShopDO shopDO) throws ShopException;

    /**
     * 根据id和卖家id获取商品;
     * @param shopDO
     * @return
     */
    Boolean isExistShop(ShopDO shopDO) throws ShopException;

    /**
     * 删除店铺
     * @param id
     * @param sellerId
     * @return
     */
    Integer deleteShop(Long id, Long sellerId) throws ShopException;

    /**
     * 更新店铺状态;
     * @param sellerId
     * @param status
     * @return
     */
    Integer updateShopStatus(Long sellerId, Integer status) throws ShopException;

    /**
     * 更新店铺信息;
     * @param shopDO
     * @return
     */
    Integer updateShop(ShopDO shopDO) throws ShopException;

    /**
     * 查询店铺;
     * @param shopQTO
     * @return
     */
    List<ShopDO> queryShop(ShopQTO shopQTO) throws ShopException;

    /**
     * 查询店铺;
     * @param shopQTO
     * @return
     */
    Integer countShop(ShopQTO shopQTO) throws ShopException;

    /**
     * @param shopQTO
     * @return
     */
    List<LastDaysCountDTO> countLastEveryDayShop(ShopQTO shopQTO) throws ShopException;
}

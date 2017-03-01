package com.mockuai.shopcenter.core.manager;

import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.domain.dto.BannerDTO;
import com.mockuai.shopcenter.domain.dto.ShopDTO;
import com.mockuai.shopcenter.domain.qto.BannerQTO;

import java.util.List;

/**
 * Created by yindingyu on 16/1/12.
 */
public interface BannerManager {
    List<BannerDTO> queryBanner(BannerQTO bannerQTO) throws ShopException;

    Long addBanner(BannerDTO bannerDTO) throws ShopException;

    Long batchDeleteBanner(List<Long> bannerIdList, Long shopId, String bizCode) throws ShopException;
}

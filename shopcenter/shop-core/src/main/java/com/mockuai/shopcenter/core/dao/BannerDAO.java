package com.mockuai.shopcenter.core.dao;

import com.mockuai.shopcenter.core.domain.BannerDO;
import com.mockuai.shopcenter.domain.qto.BannerQTO;

import java.util.List;

public interface BannerDAO {

    List<BannerDO> queryBanner(BannerQTO bannerQTO);

    Long addBanner(BannerDO bannerDO);

    Long batchDeleteBanner(BannerQTO bannerQTO);
}
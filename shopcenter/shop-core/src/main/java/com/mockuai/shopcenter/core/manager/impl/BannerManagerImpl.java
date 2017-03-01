package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.shopcenter.core.dao.BannerDAO;
import com.mockuai.shopcenter.core.domain.BannerDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.BannerManager;
import com.mockuai.shopcenter.domain.dto.BannerDTO;
import com.mockuai.shopcenter.domain.qto.BannerQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yindingyu on 16/1/12.
 */
@Service
public class BannerManagerImpl implements BannerManager {

    @Resource
    private BannerDAO bannerDAO;

    @Override
    public List<BannerDTO> queryBanner(BannerQTO bannerQTO) throws ShopException {

        List<BannerDO> bannerDOList = bannerDAO.queryBanner(bannerQTO);

        List<BannerDTO> bannerDTOList = Lists.newArrayListWithCapacity(bannerDOList.size());

        for(BannerDO bannerDO : bannerDOList){

            BannerDTO bannerDTO = new BannerDTO();

            BeanUtils.copyProperties(bannerDO,bannerDTO);

            bannerDTOList.add(bannerDTO);
        }

        return bannerDTOList;
    }

    @Override
    public Long addBanner(BannerDTO bannerDTO) throws ShopException {

        BannerDO bannerDO = new BannerDO();

        BeanUtils.copyProperties(bannerDTO, bannerDO);

        Long result = bannerDAO.addBanner(bannerDO);

        return result;
    }

    @Override
    public Long batchDeleteBanner(List<Long> bannerIdList, Long shopId, String bizCode) throws ShopException {

        BannerQTO bannerQTO = new BannerQTO();

        bannerQTO.setIdList(bannerIdList);
        bannerQTO.setShopId(shopId);
        bannerQTO.setBizCode(bizCode);

        Long result = bannerDAO.batchDeleteBanner(bannerQTO);

        return result;
    }
}

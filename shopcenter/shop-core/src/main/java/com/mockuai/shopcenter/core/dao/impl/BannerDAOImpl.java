package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.core.dao.BannerDAO;
import com.mockuai.shopcenter.core.domain.BannerDO;

import java.util.Collections;
import java.util.List;

import com.mockuai.shopcenter.domain.qto.BannerQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class BannerDAOImpl extends SqlMapClientDaoSupport implements BannerDAO {


    @Override
    public List<BannerDO> queryBanner(BannerQTO bannerQTO) {

        if (bannerQTO.getNeedPaging() != null && bannerQTO.getNeedPaging() == true) {
            Integer count = (Integer) getSqlMapClientTemplate().queryForObject("Banner.countBanner", bannerQTO);
            if (count > 0) {
                bannerQTO.setOffsetAndTotalPage();
            } else {
                return Collections.EMPTY_LIST;
            }
        }

        List<BannerDO> bannerDOList = getSqlMapClientTemplate().queryForList("Banner.queryBanner", bannerQTO);

        return bannerDOList;
    }

    @Override
    public Long addBanner(BannerDO bannerDO) {
        return (Long) getSqlMapClientTemplate().insert("Banner.addBanner",bannerDO);
    }

    @Override
    public Long batchDeleteBanner(BannerQTO bannerQTO) {
        return Long.valueOf(getSqlMapClientTemplate().update("Banner.batchDeleteBanner",bannerQTO));
    }
}
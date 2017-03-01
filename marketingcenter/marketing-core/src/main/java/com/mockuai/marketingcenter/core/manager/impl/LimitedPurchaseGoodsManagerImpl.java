package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseGoodsDTO;
import com.mockuai.marketingcenter.core.dao.LimitedPurchaseGoodsDAO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseGoodsDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseGoodsManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangsiqian on 2016/10/8.
 */
public class LimitedPurchaseGoodsManagerImpl implements LimitedPurchaseGoodsManager {
    @Autowired
    private LimitedPurchaseGoodsDAO activityGoodsDAO;

    @Override
    public Boolean addActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException {
        Long num = null;
        try {
            num = (Long) activityGoodsDAO.addActivityGoods(limitedPurchaseGoodsDO);
            if (num < 1) {
                throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品未添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "添加活动商品出错");
        }
        return true;
    }


    //查
    @Override
    public List<LimitedPurchaseGoodsDTO> activityGoodsList(LimitedPurchaseGoodsDO activityGoodsDO) throws MarketingException {
        List<LimitedPurchaseGoodsDTO> list = new ArrayList<LimitedPurchaseGoodsDTO>();
        try {
            List<LimitedPurchaseGoodsDO> limitedPurchaseGoodsDOs = activityGoodsDAO.activityGoodsList(activityGoodsDO);
            for (LimitedPurchaseGoodsDO mud : limitedPurchaseGoodsDOs) {
                LimitedPurchaseGoodsDTO limitedPurchaseDTO = new LimitedPurchaseGoodsDTO();
                BeanUtils.copyProperties(mud == null ? new LimitedPurchaseDO() : mud, limitedPurchaseDTO);
                list.add(limitedPurchaseDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "查询活动商品出错");
        }

        return list;
    }

    //更改活动商品 价格
    @Override
    public Boolean updateActivityGoodsPrice(LimitedPurchaseGoodsDO activityGoodsDO) throws MarketingException {
        Integer num = null;
        try {
            num = (Integer) activityGoodsDAO.updateActivityGoodsPrice(activityGoodsDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品更新出错");
        }
        if (num < 0) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品更新失败");
        }
        return true;
    }
    //更改活动商品数量

    @Override
    public Boolean updateActivityGoodsNum(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException {
        Integer num = null;
        try {
            num = (Integer) activityGoodsDAO.updateActivityGoodsNum(limitedPurchaseGoodsDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品更新出错");
        }
        if (num < 0) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品更新失败");
        }
        return true;
    }

    @Override
    public Boolean deleteActivityGoods(LimitedPurchaseGoodsDO activityGoodsDO) throws MarketingException {
        Integer num = null;
        try {
            num = (Integer) activityGoodsDAO.deleteActivityGoods(activityGoodsDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品删除出错");
        }
        if (num < 1) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品删除失败");
        }
        return true;
    }

    @Override
    public List selectGoodsItemId(Long acitivityId) throws MarketingException {
        List list = null;
        try {
            list = (List) activityGoodsDAO.selectGoodsItemId(acitivityId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "查询商品id删除出错");
        }
        if (list == null) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "查询商品id删除失败");
        }
        return list;
    }

    @Override
    public Long selectGoodsQuantityById(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException {
        Long goodsNum = null;
        try {
            goodsNum = (Long) activityGoodsDAO.selectGoodsQuantityById(limitedPurchaseGoodsDO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "查询商品限购数量出错");
        }
        return goodsNum;
    }

    @Override
    public Boolean invalidateActivityGoods(LimitedPurchaseGoodsDO limitedPurchaseGoodsDO) throws MarketingException {
        Integer num = null;
        try {
            num = (Integer) activityGoodsDAO.invalidateActivityGoods(limitedPurchaseGoodsDO);
        } catch (Exception e) {
//            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品失效出错");
        }
        if (num < 0) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品失效失败");
        }
        return true;
    }

    @Override
    public Boolean deleteGoods(Long activtiyId) throws MarketingException {
        Integer num = null;
        try {
            num = (Integer)activityGoodsDAO.deleteGoods(activtiyId);
        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品删除出错");

        }
        if(num<1){
            throw  new MarketingException(ResponseCode.SERVICE_EXCEPTION, "活动商品删除失败");
        }
        return true;
    }

    @Override
    public List selectAllSkuId(Long activityId) throws MarketingException {
        List list = null;
        try {
            list = (List) activityGoodsDAO.selectAllSkuId(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "查询商品skuid删除出错");
        }
        if (list == null) {
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION, "没有查询到商品skuid");
        }
        return list;
    }



}

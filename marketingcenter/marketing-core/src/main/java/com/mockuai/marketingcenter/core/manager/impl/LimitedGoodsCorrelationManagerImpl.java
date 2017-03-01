package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.common.constant.ResponseCode;
import com.mockuai.marketingcenter.common.domain.dto.LimitedGoodsCorrelationDTO;
import com.mockuai.marketingcenter.core.dao.LimitedGoodsCorrelationDAO;
import com.mockuai.marketingcenter.core.domain.LimitedGoodsCorrelationDO;
import com.mockuai.marketingcenter.core.domain.LimitedUserCorrelationDO;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import com.mockuai.marketingcenter.core.manager.LimitedGoodsCorrelationManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangsiqian on 2016/10/19.
 */
public class LimitedGoodsCorrelationManagerImpl implements LimitedGoodsCorrelationManager{
    @Autowired
    private LimitedGoodsCorrelationDAO limitedGoodsCorrelationDAO;
    @Override
    public Boolean stopActivity(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) throws  MarketingException{
        Integer num=null;
        try {
            num = (Integer) limitedGoodsCorrelationDAO.stopActivity(limitedGoodsCorrelationDO);
        }catch (Exception e){
            e.printStackTrace();
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"中间表活动商品失效出错");
        }
        if(num<0){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"中间表活动商品失效失败");
        }
        return true;
    }

    @Override
    public Boolean addActivityGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) throws  MarketingException{
        Long num=null;
        try {
            num = (Long) limitedGoodsCorrelationDAO.addActivityGoods(limitedGoodsCorrelationDO);
        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"关联表活动商品添加出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"关联表活动商品添加失败");
        }
        return true;
    }

    @Override
    public List<LimitedGoodsCorrelationDTO> selectMsgByItemId(Long itemId) throws  MarketingException {
            List<LimitedGoodsCorrelationDTO> msg = new ArrayList<>();
        try {
            List<LimitedGoodsCorrelationDO> list = (List<LimitedGoodsCorrelationDO>) limitedGoodsCorrelationDAO.selectMsgByItemId(itemId);
            for(LimitedGoodsCorrelationDO msgDO:list){
                LimitedGoodsCorrelationDTO activityDTO = new LimitedGoodsCorrelationDTO();
                BeanUtils.copyProperties(msgDO == null ? new LimitedGoodsCorrelationDO() : msgDO, activityDTO);
                msg.add(activityDTO);
            }

        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"关联表活动商品查询出错");
        }

        return msg;
    }

    @Override
    public List<LimitedGoodsCorrelationDTO> selectMsgByGoods(LimitedGoodsCorrelationDO limitedGoodsCorrelationDO) throws MarketingException {
        List<LimitedGoodsCorrelationDTO> msg = new ArrayList<>();
        try {
            List<LimitedGoodsCorrelationDO> list = (List<LimitedGoodsCorrelationDO>) limitedGoodsCorrelationDAO.selectMsgByGoods(limitedGoodsCorrelationDO);
            for(LimitedGoodsCorrelationDO msgDO:list){
                LimitedGoodsCorrelationDTO activityDTO = new LimitedGoodsCorrelationDTO();
                BeanUtils.copyProperties(msgDO == null ? new LimitedGoodsCorrelationDO() : msgDO, activityDTO);
                msg.add(activityDTO);
            }

        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"关联表活动商品查询出错");
        }

        return msg;
    }
//    更改商品信息时物理删除
    @Override
    public Boolean deleteGoods(Long activityId) throws MarketingException {
        Integer num=null;
        try {
            num = (Integer) limitedGoodsCorrelationDAO.deleteGoods(activityId);
        }catch (Exception e){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"删除关联表活动商品出错");
        }
        if(num<1){
            throw new MarketingException(ResponseCode.SERVICE_EXCEPTION,"删除关联表活动商品失败");
        }
        return true;
    }
}

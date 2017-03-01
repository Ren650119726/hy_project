package com.mockuai.giftscenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.giftscenter.core.dao.GiftsPacketProfitDAO;
import com.mockuai.giftscenter.core.domain.GiftsPacketDO;
import com.mockuai.giftscenter.core.domain.GiftsPacketProfitDO;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class GiftsPacketProfitDAOImpl extends SqlMapClientDaoSupport implements GiftsPacketProfitDAO {

    @Override
    public Long addGiftsPacketProfit(GiftsPacketProfitDO GiftsPacketProfitDO) {
        return ((Long) getSqlMapClientTemplate().insert("gifts_packet_profit.addGiftsPacketProfit", GiftsPacketProfitDO));
    }
    
    @Override
	public List<GiftsPacketProfitDO> queryGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO){
    	return getSqlMapClientTemplate().queryForList("gifts_packet_profit.queryGiftsPacketProfit", giftsPacketProfitDO);
    }
    
    @Override
    public  int updateGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO){
    	return getSqlMapClientTemplate().update("gifts_packet_profit.updateGiftsPacketProfit", giftsPacketProfitDO);
    }
    @Override
    public GiftsPacketProfitDO getGiftsPacketProfit(GiftsPacketProfitDO giftsPacketProfitDO){
    	return (GiftsPacketProfitDO) getSqlMapClientTemplate().queryForObject("gifts_packet_profit.getGiftsPacketProfit", giftsPacketProfitDO);
    }
}
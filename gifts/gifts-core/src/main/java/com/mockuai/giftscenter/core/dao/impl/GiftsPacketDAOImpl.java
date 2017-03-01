package com.mockuai.giftscenter.core.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.core.dao.GiftsPacketDAO;
import com.mockuai.giftscenter.core.domain.GiftsPacketDO;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class GiftsPacketDAOImpl extends SqlMapClientDaoSupport implements GiftsPacketDAO {

    @Override
    public Long addGiftsPacket(GiftsPacketDO giftsPacketDO) {
        return ((Long) getSqlMapClientTemplate().insert("gifts_packet.addGiftsPacket", giftsPacketDO));
    }
    
    @Override
    public GiftsPacketDO getGiftsPacket(GiftsPacketDO giftsPacketDO) {
        return (GiftsPacketDO) getSqlMapClientTemplate().queryForObject("gifts_packet.getGiftsPacket", giftsPacketDO);
    }

    @Override
    public int updateGiftsPacket(GiftsPacketDO giftsPacketDO) {
        return getSqlMapClientTemplate().update("gifts_packet.updateGiftsPacket", giftsPacketDO);
    }
    
    @Override
	public List<GiftsPacketDO> queryGiftsPacket(GiftsPacketQTO giftsPacketQTO){
    	giftsPacketQTO.setTotalCount((Integer) getSqlMapClientTemplate().queryForObject("gifts_packet.countOfGiftsPacket", giftsPacketQTO));
        return getSqlMapClientTemplate().queryForList("gifts_packet.queryGiftsPacket", giftsPacketQTO);
    }
    
    @Override
	public List<GiftsPacketDO> appQueryGiftsPacket(){
    	return getSqlMapClientTemplate().queryForList("gifts_packet.appQueryGiftsPacket");
    }
}
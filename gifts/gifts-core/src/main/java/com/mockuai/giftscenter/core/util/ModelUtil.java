package com.mockuai.giftscenter.core.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;
import com.mockuai.giftscenter.core.domain.GiftsPacketDO;
import com.mockuai.giftscenter.core.domain.GiftsPacketProfitDO;

public class ModelUtil {

	public static GiftsPacketDO genGiftsPacketDO(GiftsPacketDTO giftsPacketDTO) {
        if (giftsPacketDTO == null) return null;

        GiftsPacketDO giftsPacketDO = new GiftsPacketDO();
        BeanUtils.copyProperties(giftsPacketDTO, giftsPacketDO);
        return giftsPacketDO;
    }
	
	
	public static GiftsPacketDTO genGiftsPacketDTO(GiftsPacketDO giftsPacketDO) {
        if (giftsPacketDO == null) return null;

        GiftsPacketDTO giftsPacketDTO = new GiftsPacketDTO();
        BeanUtils.copyProperties(giftsPacketDO, giftsPacketDTO);
        return giftsPacketDTO;
    }
	
	public static GiftsPacketProfitDTO genGiftsPacketProfitDTO(GiftsPacketProfitDO giftsPacketProfitDO) {
        if (giftsPacketProfitDO == null) return null;

        GiftsPacketProfitDTO giftsPacketProfitDTO = new GiftsPacketProfitDTO();
        BeanUtils.copyProperties(giftsPacketProfitDO, giftsPacketProfitDTO);
        return giftsPacketProfitDTO;
    }
	
	public static List<GiftsPacketDTO> genGiftsPacketDTOList(List<GiftsPacketDO> GiftsPacketDOs) {
        List<GiftsPacketDTO> giftsPacketDTOs = new ArrayList<GiftsPacketDTO>();
        if (GiftsPacketDOs == null || GiftsPacketDOs.isEmpty()) return giftsPacketDTOs;

        for (GiftsPacketDO giftsPacketDO : GiftsPacketDOs) {
        	giftsPacketDTOs.add(ModelUtil.genGiftsPacketDTO(giftsPacketDO));
        }
        return giftsPacketDTOs;
    }
	
	
	
	
	
	public static List<GiftsPacketProfitDTO> genGiftsPacketProfitList(List<GiftsPacketProfitDO> GiftsPacketProfitDOs) {
        List<GiftsPacketProfitDTO> GiftsPacketProfitDTOs = new ArrayList<GiftsPacketProfitDTO>();
        if (GiftsPacketProfitDOs == null || GiftsPacketProfitDOs.isEmpty()) return GiftsPacketProfitDTOs;

        for (GiftsPacketProfitDO giftsPacketProfitDO : GiftsPacketProfitDOs) {
        	GiftsPacketProfitDTOs.add(ModelUtil.genGiftsPacketProfitDTO(giftsPacketProfitDO));
        }
        return GiftsPacketProfitDTOs;
    }
	
   
}
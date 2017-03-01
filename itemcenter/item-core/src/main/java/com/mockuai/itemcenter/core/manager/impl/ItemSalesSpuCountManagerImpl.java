package com.mockuai.itemcenter.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.itemcenter.common.domain.dto.ItemSalesSpuCountDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchResultDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSearchQTO;
import com.mockuai.itemcenter.core.dao.ItemSalesSpuCountDAO;
import com.mockuai.itemcenter.core.domain.ItemSalesSpuCountDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSalesSpuCountManager;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;

/**
 * Created by csy
 */
@Service
public class ItemSalesSpuCountManagerImpl implements ItemSalesSpuCountManager {
    private static final Logger log = LoggerFactory.getLogger(ItemSalesSpuCountManagerImpl.class);

    @Resource
    private AppClient appClient;

    @Resource
    private ItemSalesSpuCountDAO itemSalesSpuCountDAO;
    
    @Resource
    private ItemSearchManagerImpl itemSearchManagerImpl;

	@Override
	public Long updateItemSalesSpuCount(PaySuccessMsg paySuccessMsg, String salesParam) throws ItemException {
		log.info("spu销量统计开始");
		
		if(null == paySuccessMsg){
			return null;
		}
		
		if(null == salesParam){
			return null;
		}
		
		//获取商品spu集合
		List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs= paySuccessMsg.getItemSalesSpuCountDTOs();
		
		if(null == itemSalesSpuCountDTOs || itemSalesSpuCountDTOs.isEmpty()){
			return null;
		}
		
		Long salesCount = null;
				
		//支付成功进行销量累加
		if("add".equals(salesParam)){
			salesCount = addSalesCount(itemSalesSpuCountDTOs);
		}
		
		//退款成功进行销量减少
		if("red".equals(salesParam)){
			salesCount = redSalesCount(itemSalesSpuCountDTOs);
		}
		
		log.info("spu销量统计结束");
		return salesCount;
	}
	
	/**
	 * 处理业务逻辑(有时间可以进行优化减少与数据库的交互)
	 * 
	 * 
	 * @author csy 
	 * @return
	 * @throws ItemException 
	 */
	private Long addSalesCount(List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs) throws ItemException {
		if(null == itemSalesSpuCountDTOs || itemSalesSpuCountDTOs.isEmpty()){
			return null;
		}
		
		for(ItemSalesSpuCountDTO itemSalesSpuCountDTO:itemSalesSpuCountDTOs){
			Long sellerId = itemSalesSpuCountDTO.getSellerId();
			Long itemId = itemSalesSpuCountDTO.getItemId();
			String bizCode = itemSalesSpuCountDTO.getBizCode();
			
			//查询此商品id是否已经存在
			ItemSalesSpuCountDO itemSalesSpuCountDO= itemSalesSpuCountDAO.getItemSalesSpuByItemId(itemSalesSpuCountDTO.getItemId());
			
			if(null == itemSalesSpuCountDO){//如果为空则执行新增				
				Long itemSalesSpuId = itemSalesSpuCountDAO.addItemSalesSpuCount(itemSalesSpuCountDTO);
				
				if(null != itemSalesSpuId){
					itemSearchManagerImpl.updateItemSalesVolume(sellerId, itemId, 1, bizCode);
				}
			} else{//不为空则执行更新
				Long spuSalesCount = itemSalesSpuCountDO.getSpuSalesCount()+1;//销售数量
				itemSalesSpuCountDO.setSpuSalesCount(spuSalesCount);
				
				itemSalesSpuCountDAO.updateItemSalesSpuCount(itemSalesSpuCountDO);
				
				itemSearchManagerImpl.updateItemSalesVolume(sellerId, itemId, spuSalesCount.intValue(), bizCode);
			}		
		}
		
		return null;
	}
	
	/**
	 * 处理业务逻辑(有时间可以进行优化减少与数据库的交互)
	 * 
	 * 
	 * @author csy 
	 * @return
	 * @throws ItemException 
	 */
	private Long redSalesCount(List<ItemSalesSpuCountDTO> itemSalesSpuCountDTOs) throws ItemException {
		if(null == itemSalesSpuCountDTOs || itemSalesSpuCountDTOs.isEmpty()){
			return null;
		}
		
		for (ItemSalesSpuCountDTO itemSalesSpuCountDTO : itemSalesSpuCountDTOs) {
			Long sellerId = itemSalesSpuCountDTO.getSellerId();
			Long itemId = itemSalesSpuCountDTO.getItemId();
			String bizCode = itemSalesSpuCountDTO.getBizCode();
			
			// 查询此商品id是否已经存在
			ItemSalesSpuCountDO itemSalesSpuCountDO = itemSalesSpuCountDAO.getItemSalesSpuByItemId(itemSalesSpuCountDTO.getItemId());

			if (null != itemSalesSpuCountDO) {
				Long spuSalesCount = itemSalesSpuCountDO.getSpuSalesCount() - 1;// 销售数量
				itemSalesSpuCountDO.setSpuSalesCount(spuSalesCount);
				itemSalesSpuCountDAO.updateItemSalesSpuCount(itemSalesSpuCountDO);
				
				itemSearchManagerImpl.updateItemSalesVolume(sellerId, itemId, spuSalesCount.intValue(), bizCode);
			}
		}
		
		return null;
	}
}

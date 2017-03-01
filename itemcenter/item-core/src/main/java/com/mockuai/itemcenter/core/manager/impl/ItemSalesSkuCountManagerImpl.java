package com.mockuai.itemcenter.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.domain.dto.ItemSalesSkuCountDTO;
import com.mockuai.itemcenter.core.dao.ItemSalesSkuCountDAO;
import com.mockuai.itemcenter.core.domain.ItemSalesSkuCountDO;
import com.mockuai.itemcenter.core.manager.ItemSalesSkuCountManager;
import com.mockuai.itemcenter.core.message.msg.PaySuccessMsg;

/**
 * Created by csy
 */
@Service
public class ItemSalesSkuCountManagerImpl implements ItemSalesSkuCountManager {
    private static final Logger log = LoggerFactory.getLogger(ItemSalesSkuCountManagerImpl.class);
    
    @Resource
    private ItemSalesSkuCountDAO itemSalesSkuCountDAO;

	@Override
	public Long updateItemSalesSkuCount(PaySuccessMsg paySuccessMsg, String salesParam) {
		log.info("sku销售统计开始");
		
		if(null == paySuccessMsg){
			return null;
		}
		
		if(null == salesParam){
			return null;
		}
		
		//获取商品sku集合
		List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs= paySuccessMsg.getItemSalesSkuCountDTOs();
		
		if(null == itemSalesSkuCountDTOs || itemSalesSkuCountDTOs.isEmpty()){
			return null;
		}
		
		Long salesCount = null;
				
		//支付成功进行销量累加
		if("add".equals(salesParam)){
			salesCount = addSalesCount(itemSalesSkuCountDTOs);
		}
		
		//退款成功进行销量减少
		if("red".equals(salesParam)){
			salesCount = redSalesCount(itemSalesSkuCountDTOs);
		}	
		
		log.info("sku销售统计结束");
		return salesCount;
	}
	
	/**
	 * 处理业务逻辑(有时间可以进行优化减少与数据库的交互)
	 * 
	 * 
	 * @author csy 
	 * @return
	 */
	private Long addSalesCount(List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs) {
		if(null == itemSalesSkuCountDTOs || itemSalesSkuCountDTOs.isEmpty()){
			return null;
		}
		
		for(ItemSalesSkuCountDTO itemSalesSkuCountDTO : itemSalesSkuCountDTOs){		
			//查询此商品id是否已经存在
			ItemSalesSkuCountDO itemSalesSkuCountDO= itemSalesSkuCountDAO.getItemSalesSkuBySkuId(itemSalesSkuCountDTO.getSkuId());
			
			if(null == itemSalesSkuCountDO){//如果为空则执行新增				
				itemSalesSkuCountDAO.addItemSalesSkuCount(itemSalesSkuCountDTO);
			} else{//不为空则执行更新
				Long skuSalesCount = itemSalesSkuCountDO.getSkuSalesCount()+1;//销售数量
				itemSalesSkuCountDO.setSkuSalesCount(skuSalesCount);
				itemSalesSkuCountDAO.updateItemSalesSkuCount(itemSalesSkuCountDO);
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
	 */
	private Long redSalesCount(List<ItemSalesSkuCountDTO> itemSalesSkuCountDTOs) {
		if(null == itemSalesSkuCountDTOs || itemSalesSkuCountDTOs.isEmpty()){
			return null;
		}
		
		for (ItemSalesSkuCountDTO itemSalesSkuCountDTO : itemSalesSkuCountDTOs) {
			// 查询此商品id是否已经存在
			ItemSalesSkuCountDO itemSalesSkuCountDO = itemSalesSkuCountDAO.getItemSalesSkuBySkuId(itemSalesSkuCountDTO.getSkuId());

			if (null != itemSalesSkuCountDO) {
				Long skuSalesCount = itemSalesSkuCountDO.getSkuSalesCount() - 1;// 销售数量
				itemSalesSkuCountDO.setSkuSalesCount(skuSalesCount);
				itemSalesSkuCountDAO.updateItemSalesSkuCount(itemSalesSkuCountDO);
			}
		}
		
		return null;
	}

}

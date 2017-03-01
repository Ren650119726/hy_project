package com.mockuai.tradecenter.core.util; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.common.collect.Lists;
import com.mockuai.marketingcenter.common.constant.LimitTimeActivityStatus;
import com.mockuai.marketingcenter.common.constant.ToolType;
import com.mockuai.marketingcenter.common.domain.dto.DiscountInfo;
import com.mockuai.marketingcenter.common.domain.dto.MarketActivityDTO;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.tradecenter.common.domain.BizMarkDTO;
import com.mockuai.tradecenter.common.domain.CartItemDTO;
import com.mockuai.tradecenter.core.service.action.cart.QueryUserCartItems;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by guansheng on 2016/7/19.
 */
public class BizMarkCollectionUtil {
	private static final Logger log = LoggerFactory.getLogger(BizMarkCollectionUtil.class);
    //收集标签
    public static void buildCartBizMarkCollection(List<DiscountInfo> discountInfoList ,
                                                  List<CartItemDTO> cartItemDTOList){
          if(discountInfoList == null){
              return ;
          }
          for (CartItemDTO cartItemDTO : cartItemDTOList){
              settingBizMark(discountInfoList,cartItemDTO);
          }

    }

    private static void settingBizMark(List<DiscountInfo> discountInfoList ,CartItemDTO cartItemDTO){
        for(DiscountInfo discountInfo : discountInfoList){
            MarketActivityDTO marketActivityDTO = discountInfo.getActivity();
            if(marketActivityDTO == null){
                continue;
            }
            
            String toolCode =   marketActivityDTO.getToolCode();
            if(toolCode == null){
                continue;
            }
            
            //限时购活动中，商品才显示限时购价格
//            if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(toolCode){
//                String limitStatus = marketActivityDTO.getLimitTagStatus();
//                if(!LimitTimeActivityStatus.PROCESS.getValue().toString().equals(limitStatus)){
//                	continue;
//                }
//            }
            String limitStatus = marketActivityDTO.getLimitTagStatus();
            
            List<MarketItemDTO> marketItemList = discountInfo.getItemList();
            
            
            //空集合表示购物车的ITEM都是活动商品
            /*if( CollectionUtils.isEmpty(marketItemList)){
                setBizMarkList(cartItemDTO,toolCode);
                return;
            }*/
            //购物车符合营销类型的商品标记营销数据
            if(containItem(marketItemList,cartItemDTO,toolCode,limitStatus)){
                setBizMarkList(cartItemDTO,toolCode);
            }
        }
    }

    private static boolean containItem( List<MarketItemDTO>  marketItemList,CartItemDTO cartItemDTO,String toolCode,String limitStatus){
		if(CollectionUtils.isEmpty(marketItemList)){
			return true;
		}
		for(MarketItemDTO item : marketItemList){
			if(item != null && item.getItemSkuId()!=null){
				if(item.getItemSkuId().longValue() == cartItemDTO.getItemSkuId().longValue()){	

			    	if(ToolType.TIME_RANGE_DISCOUNT.getCode().equals(toolCode) ){ 
	                 	 //购物车商品在限时购活动中，才显示限时购价格和标签	                     
	                 	 if(LimitTimeActivityStatus.PROCESS.getValue().toString().equals(limitStatus)){                 
	  	                 	cartItemDTO.setWirelessPrice(item.getUnitPrice());            
	 	                 	cartItemDTO.setPromotionPrice(item.getUnitPrice());
	 	                 	//库存数量大于限购数量的情况下按限购数量返回
	 	                 	log.info(" cartItemDTO.getStockNum(): "+cartItemDTO.getStockNum()+" ,item.getLimitNumber().intValue():"+item.getLimitNumber().intValue());
	 	                 	if(cartItemDTO.getStockNum()>=item.getLimitNumber().intValue()){
	 	                 		cartItemDTO.setStockNum(item.getLimitNumber().intValue());
	 	                 	}	
	 	                 	// 购买数量大于限购数量按限购数量返回
	 	                 	if(cartItemDTO.getNumber()>=item.getLimitNumber().intValue()){
	 	                 		cartItemDTO.setNumber(item.getLimitNumber().intValue());
	 	                 	}
	 	                 	return true;	                 	
	                 	 }else{
	                  		return false;
	                  	 }
			    	}else{
			    		return true;
			    	}
                  }
        	 }
         }
        return false ;
    }



    private static void setBizMarkList(CartItemDTO cartItemDTO ,String toolCode){
        List<BizMarkDTO> bizMarkDTOList =   cartItemDTO.getBizMarkList();
        if(bizMarkDTOList == null){
            bizMarkDTOList = Lists.newArrayList();
        }
        BizMarkDTO bizMarkDTO = new BizMarkDTO();
        bizMarkDTO.setRemark(toolCode);
        bizMarkDTOList.add(bizMarkDTO);
        cartItemDTO.setBizMarkList(bizMarkDTOList);
    }

}

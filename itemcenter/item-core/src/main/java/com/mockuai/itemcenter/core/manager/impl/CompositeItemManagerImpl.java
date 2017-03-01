package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mockuai.distributioncenter.common.domain.dto.ItemSkuDistPlanDTO;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CompositeItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuQTO;
import com.mockuai.itemcenter.common.util.NumberFormatUtil;
import com.mockuai.itemcenter.core.dao.CompositeItemDAO;
import com.mockuai.itemcenter.core.domain.CompositeItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CompositeItemManager;
import com.mockuai.itemcenter.core.manager.DistributorManager;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.StoreStockManager;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompositeItemManagerImpl implements CompositeItemManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CompositeItemDAO compositeItemDAO;

    @Resource
    private StoreStockManager storeStockManager;
    @Resource
    private ItemSkuManager itemSkuManager;


    @Resource
    private DistributorManager distributorManager;

   private List<Long>  getCompositeSkuList(List<CompositeItemDTO> compositeItemDTOList ){
       List<Long> skuIdList = new ArrayList<Long>();
       for(CompositeItemDTO compositeItem : compositeItemDTOList){
           skuIdList.add(compositeItem.getSubSkuId());
       }
       return skuIdList;
   }

    @Override
    public Map getIntervalGain(Long itemId, BigDecimal oneGains,List<ItemSkuDTO> compositeItemSkuDTOList, String appKey) throws ItemException {
        List<CompositeItemDTO> compositeItemDTOList = getCompositeItemByItemId(itemId);
        logger.info("compositeItemDTOList:{}",JsonUtil.toJson(compositeItemDTOList));
        List<Long> skuList = getCompositeSkuList(compositeItemDTOList);
        if(CollectionUtils.isEmpty(skuList)){
            logger.error("itemId:{} 并不是一个组合商品 ",itemId);
            return null;
        }

        // 组合商品价格
        BigDecimal combPrice = new BigDecimal(compositeItemSkuDTOList.get(0).getWirelessPrice()) ;

        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(skuList);
        //查询所有skuDTO
        List<ItemSkuDTO> itemSkuDTOList =  itemSkuManager.queryItemSku(itemSkuQTO);

        Map<Long,ItemSkuDTO> itemSkuMap =Maps.uniqueIndex(itemSkuDTOList, new Function<ItemSkuDTO, Long>() {
            @Override
            public Long apply(ItemSkuDTO itemSku) {
                return itemSku.getId();
            }
        });
        logger.info("itemSkuMap:{}",JsonUtil.toJson(itemSkuMap));

        // 组合商品原所有sku价格和
        BigDecimal originalTotalPrice = new BigDecimal(getOriginalTotalPrice(itemSkuMap,compositeItemDTOList)) ;


        //查询所有的sku收益信息
        List<ItemSkuDistPlanDTO> itemSkuDistPlanList  =   distributorManager.getDistByItemSkuId(skuList,appKey);
        //查询所有的收益信息
        Map<Long,ItemSkuDistPlanDTO> itemSkuDistPlanMap =Maps.uniqueIndex(itemSkuDistPlanList, new Function<ItemSkuDistPlanDTO, Long>() {
            @Override
            public Long apply(ItemSkuDistPlanDTO itemSkuDistPlanDTO) {
                return itemSkuDistPlanDTO.getItemSkuId();
            }
        });
//        logger.info("itemSkuDistPlanMap:{}",JsonUtil.toJson(itemSkuDistPlanMap));
//
//        logger.info(" combPrice :{}",combPrice);
//        logger.info(" originalTotalPrice :{}",originalTotalPrice);

        BigDecimal gains = new BigDecimal(0);
        BigDecimal tempGains = new BigDecimal(0);
        for(CompositeItemDTO c : compositeItemDTOList){
            BigDecimal promotionPrice = new BigDecimal(itemSkuMap.get(c.getSubSkuId()).getPromotionPrice()) ;
            BigDecimal thisGain = new BigDecimal(itemSkuDistPlanMap.get(c.getSubSkuId()).getDistGainsRatio()) ;
            BigDecimal num = new BigDecimal(c.getNum());
//            logger.info(" num :{},thisGain:{},promotionPrice:{}",num,thisGain,promotionPrice);
            tempGains = promotionPrice.multiply(num).multiply(combPrice).multiply(thisGain).divide(originalTotalPrice,2,BigDecimal.ROUND_HALF_UP);
            gains = gains.add(tempGains);
//            logger.info(" @@@@@@@@@@ promotionPrice:{} ",promotionPrice);
//            logger.info(" @@@@@@@@@@ tempGains:{} ",tempGains);
//            logger.info(" @@@@@@@@@@ gains:{} ",gains);
//            gains +=  promotionPrice * thisGain * c.getNum();
        }
        if(gains.equals(new BigDecimal(0))){
            return null;
        }
        BigDecimal gainPercent = gains.multiply(new BigDecimal(100)).divide(combPrice,2,BigDecimal.ROUND_HALF_UP);
        //组合商品分享收益处理

        String sharingGains = NumberFormatUtil.formatTwoDecimalNoZero(
                gainPercent.multiply(oneGains).multiply(combPrice).divide(new BigDecimal(100*100))
                .setScale(2, BigDecimal.ROUND_HALF_UP))+"元";
        Map map = new HashMap();
        map.put("gainPercent",gainPercent.intValue()+"%");
        map.put("sharingGains",sharingGains);
        return map;

    }

    @Override
	public String getCompositeIntervalGain(Long itemId, BigDecimal oneGains,List<ItemSkuDTO> compositeItemSkuDTOList,
			String appKey) throws ItemException {
    	
    	List<CompositeItemDTO> compositeItemDTOList = getCompositeItemByItemId(itemId);
        logger.info("compositeItemDTOList:{}",JsonUtil.toJson(compositeItemDTOList));
        List<Long> skuList = getCompositeSkuList(compositeItemDTOList);
        if(CollectionUtils.isEmpty(skuList)){
            logger.error("itemId:{} 并不是一个组合商品 ",itemId);
            return null;
        }
        
    	// 组合商品价格
    	BigDecimal combPrice = new BigDecimal(compositeItemSkuDTOList.get(0).getWirelessPrice()) ;
        
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(skuList);
        //查询所有skuDTO
        List<ItemSkuDTO> itemSkuDTOList =  itemSkuManager.queryItemSku(itemSkuQTO);
                
        Map<Long,ItemSkuDTO> itemSkuMap =Maps.uniqueIndex(itemSkuDTOList, new Function<ItemSkuDTO, Long>() {
            @Override
            public Long apply(ItemSkuDTO itemSku) {
                return itemSku.getId();
            }
        });
        logger.info("itemSkuMap:{}",JsonUtil.toJson(itemSkuMap));
        
        // 组合商品原所有sku价格和
        BigDecimal originalTotalPrice = new BigDecimal(getOriginalTotalPrice(itemSkuMap,compositeItemDTOList)) ;
                

        //查询所有的sku收益信息
        List<ItemSkuDistPlanDTO> itemSkuDistPlanList  =   distributorManager.getDistByItemSkuId(skuList,appKey);
        //查询所有的收益信息
        Map<Long,ItemSkuDistPlanDTO> itemSkuDistPlanMap =Maps.uniqueIndex(itemSkuDistPlanList, new Function<ItemSkuDistPlanDTO, Long>() {
            @Override
            public Long apply(ItemSkuDistPlanDTO itemSkuDistPlanDTO) {
                return itemSkuDistPlanDTO.getItemSkuId();
            }
        });
        logger.info("itemSkuDistPlanMap:{}",JsonUtil.toJson(itemSkuDistPlanMap));

        logger.info(" combPrice :{}",combPrice);
        logger.info(" originalTotalPrice :{}",originalTotalPrice);

        BigDecimal gains = new BigDecimal(0);
        BigDecimal tempGains = new BigDecimal(0);
        for(CompositeItemDTO c : compositeItemDTOList){
            BigDecimal promotionPrice = new BigDecimal(itemSkuMap.get(c.getSubSkuId()).getPromotionPrice()) ;
            BigDecimal thisGain = new BigDecimal(itemSkuDistPlanMap.get(c.getSubSkuId()).getDistGainsRatio()) ;
            BigDecimal num = new BigDecimal(c.getNum());
            logger.info(" num :{},thisGain:{},promotionPrice:{}",num,thisGain,promotionPrice);
            tempGains = promotionPrice.multiply(num).multiply(combPrice).multiply(thisGain).multiply(oneGains).divide(originalTotalPrice,2,BigDecimal.ROUND_HALF_UP);
            gains = gains.add(tempGains); 
            logger.info(" @@@@@@@@@@ promotionPrice:{} ",promotionPrice);   
            logger.info(" @@@@@@@@@@ tempGains:{} ",tempGains);   
            logger.info(" @@@@@@@@@@ gains:{} ",gains);   
//            gains +=  promotionPrice * thisGain * c.getNum();
        }     
        if(gains.equals(new BigDecimal(0))){
        	return null;
        }
        BigDecimal gainPercent = gains.multiply(new BigDecimal(100)).divide(combPrice,2,BigDecimal.ROUND_HALF_UP);
		return gainPercent+"%";
	}
    
    private Long getOriginalTotalPrice(Map<Long,ItemSkuDTO> itemSkuMap,List<CompositeItemDTO> compositeItemDTOList){
    	Long totalPrice =0l;
    	for(CompositeItemDTO compositeItemDTO:compositeItemDTOList){
    		totalPrice +=itemSkuMap.get(compositeItemDTO.getSubSkuId()).getPromotionPrice()*compositeItemDTO.getNum();
    	}
    	return totalPrice;
    }

	@Override
    public  Double getCompositeGain(Long itemId, String appKey) throws ItemException{
        List<CompositeItemDTO> compositeItemDTOList =    getCompositeItemByItemId(itemId);
        logger.info("compositeItemDTOList:{}",JsonUtil.toJson(compositeItemDTOList));
        List<Long> skuList = getCompositeSkuList(compositeItemDTOList);
        if(CollectionUtils.isEmpty(skuList)){
            logger.error("itemId:{} 并不是一个组合商品 ",itemId);
            return 0d;
        }
        ItemSkuQTO itemSkuQTO = new ItemSkuQTO();
        itemSkuQTO.setIdList(skuList);
        //查询所有skuDTO
        List<ItemSkuDTO> itemSkuDTOList =  itemSkuManager.queryItemSku(itemSkuQTO);
        Map<Long,ItemSkuDTO> itemSkuMap =Maps.uniqueIndex(itemSkuDTOList, new Function<ItemSkuDTO, Long>() {
            @Override
            public Long apply(ItemSkuDTO itemSku) {
                return itemSku.getId();
            }
        });
        logger.info("itemSkuMap:{}",JsonUtil.toJson(itemSkuMap));

        //查询所有的sku收益信息
        List<ItemSkuDistPlanDTO> itemSkuDistPlanList  =   distributorManager.getDistByItemSkuId(skuList,appKey);
        //查询所有的收益信息
        Map<Long,ItemSkuDistPlanDTO> itemSkuDistPlanMap =Maps.uniqueIndex(itemSkuDistPlanList, new Function<ItemSkuDistPlanDTO, Long>() {
            @Override
            public Long apply(ItemSkuDistPlanDTO itemSkuDistPlanDTO) {
                return itemSkuDistPlanDTO.getItemSkuId();
            }
        });
        logger.info("itemSkuDistPlanMap:{}",JsonUtil.toJson(itemSkuDistPlanMap));

        Double gains = 0d;
        for(CompositeItemDTO c : compositeItemDTOList){
            if(!itemSkuMap.containsKey(c.getSubSkuId())){
                throw  new ItemException(ResponseCode.BASE_STATE_ITEM_NOT_EXIST);
            }
            Long promotionPrice =  itemSkuMap.get(c.getSubSkuId()).getPromotionPrice();
            Double thisGain =  itemSkuDistPlanMap.get(c.getSubSkuId()).getDistGainsRatio();
            gains +=  promotionPrice * thisGain * c.getNum();
        }

        return gains;
    }



    /**
     * 得到组合商品可销售库存
     * @param itemId
     * @param appKey
     * @return
     * @throws ItemException
     */
    @Override
    public StoreItemSkuDTO getCompositeStore(Long itemId, String appKey) throws ItemException {
        StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();

        List<CompositeItemDTO> compositeItemDTOList =    getCompositeItemByItemId(itemId);
        List<Long> skuIdList = getCompositeSkuList(compositeItemDTOList);
        storeItemSkuQTO.setItemSkuIdList(skuIdList);
        List<StoreItemSkuDTO> storeItemSkuList =   storeStockManager.queryItemStock(storeItemSkuQTO,appKey);
        Map<Long,StoreItemSkuDTO> storeSkuMap = Maps.newHashMap();
        //相同的sku 不同的store 有不同的数据
        for(StoreItemSkuDTO storeItemSku : storeItemSkuList ){
            Long skuId = storeItemSku.getItemSkuId();
            StoreItemSkuDTO item =   storeSkuMap.get(skuId);
            if(item == null){
                storeSkuMap.put(skuId,storeItemSku);
            }else{
                item.setStockNum(storeItemSku.getSalesNum() + item.getSalesNum());
                item.setFrozenStockNum(storeItemSku.getFrozenStockNum()+ item.getFrozenStockNum());
                storeSkuMap.put(skuId,item);

            }


        }

        Integer salesNumLast = 0;
        salesNumLast = getSalesNumLast(storeSkuMap, compositeItemDTOList);
        logger.info("storeSkuMap:{}", JsonUtil.toJson(storeSkuMap));
        logger.info("compositeItemDTOList:{}", JsonUtil.toJson(compositeItemDTOList));
        logger.info("salesNumLast:{}",salesNumLast);
        storeItemSkuDTO.setSalesNum(salesNumLast.longValue());
        return   storeItemSkuDTO;
    }

    /**
     * 组合商品
     * 得到最终可销售数量
     * @param storeSkuMap
     * @param compositeItemDTOList
     * @return
     */
    private  Integer getSalesNumLast(Map<Long,StoreItemSkuDTO> storeSkuMap,List<CompositeItemDTO> compositeItemDTOList){
        Integer salesNumLast = 0;
        while(true){
            for(CompositeItemDTO compositeItem : compositeItemDTOList){
                StoreItemSkuDTO storeItemSkuDTO =    storeSkuMap.get(compositeItem.getSubSkuId());
                Long salesNum =   storeItemSkuDTO.getSalesNum();
                salesNum = salesNum.longValue() - compositeItem.getNum().longValue();
                if(salesNum <0 ){
                    return salesNumLast;
                }
                storeItemSkuDTO.setSalesNum(salesNum);
                storeSkuMap.put(compositeItem.getSubSkuId(),storeItemSkuDTO);
            }
            salesNumLast++;
        }
    }


	@Override
	public Long addCompositeItem(CompositeItemDTO compositeItemDTO)
			throws ItemException {
		logger.debug("enter addCompositeItem: skuId: " + compositeItemDTO.getSkuId() +  " subSkuId : " + compositeItemDTO.getSubSkuId()); 
		CompositeItemDO compositeItemDO = new CompositeItemDO();
		BeanUtils.copyProperties(compositeItemDTO, compositeItemDO);
		Long id = this.compositeItemDAO.addCompositeItem(compositeItemDO);
		return id;
	}

    @Override
    public void batchAddCompositeItem(List<CompositeItemDTO> compositeItemData)
            throws ItemException {
        List<CompositeItemDO> params =  Lists.transform(compositeItemData, new Function<CompositeItemDTO, CompositeItemDO>() {

            @Override
            public CompositeItemDO apply(CompositeItemDTO compositeItemDTO) {
                CompositeItemDO compositeItemDO = new CompositeItemDO();

                 BeanUtils.copyProperties(compositeItemDTO,compositeItemDO);
                return compositeItemDO;
            }
        });
        compositeItemDAO.batchAddCompositeItem(params);
    }


	@Override
	public int deleteCompositeItemByItemId(Long itemId) {
		logger.debug("enter deleteCompositeItemByItemId " + itemId );
		return this.compositeItemDAO.deleteCompositeItemByItemId(itemId);
	}
	
	@Override
	public List<CompositeItemDTO> getCompositeItemByItemId(Long itemId){
		List<CompositeItemDO> list = this.compositeItemDAO.getCompositeItemByItemId(itemId);
		List<CompositeItemDTO> result = new ArrayList<CompositeItemDTO>();
		if(list != null){
			for(CompositeItemDO item : list){
				CompositeItemDTO dto =new CompositeItemDTO();
				BeanUtils.copyProperties(item, dto);
				result.add(dto);
			}
		}
		return result;
	}
    @Override
    public List<CompositeItemDTO> queryCompositeItemByItemIdList(List<Long> itemIdList){
        List<CompositeItemDO> list = this.compositeItemDAO.queryCompositeItemByItemIdList(itemIdList);
        List<CompositeItemDTO> result = new ArrayList<CompositeItemDTO>();
        if(list != null){
            for(CompositeItemDO item : list){
                CompositeItemDTO dto =new CompositeItemDTO();
                BeanUtils.copyProperties(item, dto);
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public List<CompositeItemDTO> queryCompositeItemByItemSkuQTO(ItemSkuQTO itemSkuQTO){
        List<CompositeItemDO> list = this.compositeItemDAO.queryCompositeItemByItemSkuQTO(itemSkuQTO);
        List<CompositeItemDTO> result = new ArrayList<CompositeItemDTO>();
        if(list != null){
            for(CompositeItemDO item : list){
                CompositeItemDTO dto =new CompositeItemDTO();
                BeanUtils.copyProperties(item, dto);
                result.add(dto);
            }
        }
        return result;
    }


	private void validateField4Add(CompositeItemDTO dto)throws ItemException{
		if(dto ==null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"compositeItemDTO is null");
		}
		if(dto.getSkuId() == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"skuId is null");
		}else if(dto.getNum() == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"num is null");
		}else if(dto.getItemId() == null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"itemId is null");
		}else if(dto.getSubSkuId() ==null){
			throw new ItemException(ResponseCode.PARAM_E_MISSING,"subSkuId is null");
		}
	}


	
}

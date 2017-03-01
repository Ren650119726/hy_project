package com.mockuai.giftscenter.core.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mockuai.giftscenter.common.constant.ResponseCode;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketDTO;
import com.mockuai.giftscenter.common.domain.dto.GiftsPacketProfitDTO;
import com.mockuai.giftscenter.common.domain.qto.GiftsPacketQTO;
import com.mockuai.giftscenter.core.dao.GiftsPacketDAO;
import com.mockuai.giftscenter.core.dao.GiftsPacketProfitDAO;
import com.mockuai.giftscenter.core.domain.GiftsPacketDO;
import com.mockuai.giftscenter.core.domain.GiftsPacketProfitDO;
import com.mockuai.giftscenter.core.exception.GiftsException;
import com.mockuai.giftscenter.core.manager.GiftsPacketManager;
import com.mockuai.giftscenter.core.manager.ItemManager;
import com.mockuai.giftscenter.core.util.DateUtils;
import com.mockuai.giftscenter.core.util.JsonUtil;
import com.mockuai.giftscenter.core.util.ModelUtil;
import com.mockuai.itemcenter.common.constant.ItemType;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class GiftsPacketManagerImpl implements GiftsPacketManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiftsPacketManagerImpl.class);

    @Autowired
    private GiftsPacketDAO giftsPacketDAO;
    
    @Autowired
    private GiftsPacketProfitDAO giftsPacketProfitDAO;

    @Autowired
    private ItemManager itemManager;
    
    @Override
    public void addGifts(GiftsPacketDTO giftsPacketDTO , String appKey, Long sellerId) throws GiftsException {
    	try{
    		
    		//模拟商品信息
    		ItemDTO itemDTO = new  ItemDTO();
    		//修改商品类型
    		itemDTO.setId(giftsPacketDTO.getGoodsId());
    		itemDTO.setSellerId(sellerId);
            itemDTO.setItemType(ItemType.GIFT_PACKS.getType());
            itemDTO.setFreight(0L);
            itemDTO.setCategoryId(1L);
            Boolean  item = itemManager.updateItem(itemDTO, appKey);
            
            if (item == false) throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
            
            //修改商品价格
            ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
            itemSkuDTO.setId(giftsPacketDTO.getGoodsSkuId());
            itemSkuDTO.setPromotionPrice(giftsPacketDTO.getGiftsDealPrice());
            itemSkuDTO.setWirelessPrice(giftsPacketDTO.getGiftsDealPrice());
            itemSkuDTO.setSellerId(sellerId);
    		Boolean ad = itemManager.updateItemSku(itemSkuDTO, appKey);
    		if (!ad) {
                LOGGER.error("error to updateItemSku, itemSkuDTO : {}",
                		JsonUtil.toJson(itemSkuDTO));
                throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
    		}
    		giftsPacketDTO.setItemId(sellerId);
    		giftsPacketDTO.setSkuId(sellerId);
    		Long id = giftsPacketDAO.addGiftsPacket(ModelUtil.genGiftsPacketDO(giftsPacketDTO));
        	if(id == null){
        		LOGGER.error("error to addGifts, giftsPacketDTO : {}", JsonUtil.toJson(giftsPacketDTO));
                throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        	}
        	Long id1 = null;
        	String level[] = giftsPacketDTO.getLevelMoneyScore().split(",");
        	if(level.length > 0){
        		for (int i = 0; i < level.length; i++) {
            		String libao[] = level[i].split("_");
            		String jibie = libao[0];
            		String money = libao[1];
            		String score = libao[2];
            		GiftsPacketProfitDO giftsPacketProfitDO = new GiftsPacketProfitDO();
                	giftsPacketProfitDO.setGiftsPacketId(id);
                	giftsPacketProfitDO.setLevelId(Long.valueOf(jibie));
                	giftsPacketProfitDO.setLevelMoney(Long.valueOf(money));
                	giftsPacketProfitDO.setLevelScore(Long.valueOf(score));
                	id1 = giftsPacketProfitDAO.addGiftsPacketProfit(giftsPacketProfitDO);
                	if(id1 == null){
                		LOGGER.error("error to addGiftsPacketProfit, giftsPacketDTO : {}", JsonUtil.toJson(giftsPacketDTO));
                        throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
                	}
    			}
        	}
        	
    	}catch(GiftsException e){
    		throw e;
    	}
    	
    }
  
    
    @Override
    public GiftsPacketDTO getGifts(Long id) throws GiftsException {
    	
    		GiftsPacketDO giftsPacketDO = new GiftsPacketDO();
        	giftsPacketDO.setId(id);
        	GiftsPacketDO giftsPacket = giftsPacketDAO.getGiftsPacket(giftsPacketDO);
        	GiftsPacketDTO giftsPacketDTO = ModelUtil.genGiftsPacketDTO(giftsPacket);
        	
        	GiftsPacketProfitDO giftsPacketProfitDO = new GiftsPacketProfitDO();
        	giftsPacketProfitDO.setGiftsPacketId(id);
        	List<GiftsPacketProfitDO>  list = giftsPacketProfitDAO.queryGiftsPacketProfit(giftsPacketProfitDO);
        	giftsPacketDTO.setProfitList(ModelUtil.genGiftsPacketProfitList(list));
        	
    	return giftsPacketDTO;
    }
    
    @Override
    public int updateGiftsPacket(GiftsPacketDTO giftsPacketDTO,String appKey, Long sellerId) throws GiftsException {
    	int opNum;
    	try{
    			giftsPacketDTO.setItemId(sellerId);
    			giftsPacketDTO.setSkuId(sellerId);
    		    opNum = giftsPacketDAO.updateGiftsPacket(ModelUtil.genGiftsPacketDO(giftsPacketDTO));
        	if (opNum != 1) {
                LOGGER.error("error to updateGiftsPacket, giftsPacketDO : {}",
                		JsonUtil.toJson(giftsPacketDTO));
                throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
            }else{
            	//修改虚拟商品的价格

            	ItemDTO itemDTO = new  ItemDTO();
            	itemDTO.setId(giftsPacketDTO.getGoodsId());
        		itemDTO.setSellerId(sellerId);
                itemDTO.setItemType(ItemType.GIFT_PACKS.getType());
                itemDTO.setFreight(0L);
                itemDTO.setCategoryId(1L);
                Boolean  item = itemManager.updateItem(itemDTO, appKey);
                
                if (item == false) throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
                
                //修改商品价格
                ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
                itemSkuDTO.setId(giftsPacketDTO.getGoodsSkuId());
                itemSkuDTO.setPromotionPrice(giftsPacketDTO.getGiftsDealPrice());
                itemSkuDTO.setWirelessPrice(giftsPacketDTO.getGiftsDealPrice());
                itemSkuDTO.setSellerId(sellerId);
        		Boolean ad = itemManager.updateItemSku(itemSkuDTO, appKey);
        		if (!ad) {
                    LOGGER.error("error to updateItemSku, itemSkuDTO : {}",
                    		JsonUtil.toJson(itemSkuDTO));
                    throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
        		}
            	
            	int id1 ;
            	String level[] = giftsPacketDTO.getLevelMoneyScore().split(",");
            	if(level.length > 0){
            		for (int i = 0; i < level.length; i++) {
                		String libao[] = level[i].split("_");
                		String jibie = libao[0];
                		String money = libao[1];
                		String score = libao[2];
                		GiftsPacketProfitDO giftsPacketProfitDO = new GiftsPacketProfitDO();
                    	giftsPacketProfitDO.setGiftsPacketId(giftsPacketDTO.getId());
                    	giftsPacketProfitDO.setLevelId(Long.valueOf(jibie));
                    	giftsPacketProfitDO.setLevelMoney(Long.valueOf(money));
                    	giftsPacketProfitDO.setLevelScore(Long.valueOf(score));
                    	id1 = giftsPacketProfitDAO.updateGiftsPacketProfit(giftsPacketProfitDO);
                    	if(id1 != 1){
                    		LOGGER.error("error to updateGiftsPacketProfit, giftsPacketDTO : {}", JsonUtil.toJson(giftsPacketDTO));
                            throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
                    	}
        			}
            	}
            }
    	} catch (GiftsException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("error to updateGiftsPacket, giftsPacketDO : {}",
                    JsonUtil.toJson(giftsPacketDTO), e);
            throw new GiftsException(ResponseCode.DB_OP_ERROR);
        }
    	return opNum;
    }
    
    @Override
    public int deleteGiftsPacket(GiftsPacketDTO giftsPacketDTO,String appKey) throws GiftsException{
    	try{
    		GiftsPacketDO  giftsPacketDO = giftsPacketDAO.getGiftsPacket(ModelUtil.genGiftsPacketDO(giftsPacketDTO));
    		giftsPacketDTO.setDeleteMark(1);
    		int opNum = giftsPacketDAO.updateGiftsPacket(ModelUtil.genGiftsPacketDO(giftsPacketDTO));
    		if(opNum == 1){
    			
    			Boolean bl = itemManager.deleteItem(giftsPacketDO.getGoodsId(), giftsPacketDO.getItemId(), appKey);
    		    if(!bl){
    		    	LOGGER.error("error to deleteItem, giftsPacketDO : {}",
                    		JsonUtil.toJson(giftsPacketDO));
                    throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
    		    }
    		}else{
    			LOGGER.error("error to deleteGiftsPacket, giftsPacketDO : {}",
                		JsonUtil.toJson(giftsPacketDTO));
                throw new GiftsException(ResponseCode.SERVICE_EXCEPTION);
    		}
        	return opNum;
    	}catch (Exception e) {
            LOGGER.error("error to deleteGiftsPacket, giftsPacketDTO : {}", JsonUtil.toJson(giftsPacketDTO), e);
            throw new GiftsException(ResponseCode.DB_OP_ERROR);
        }
    }

    @Override
    public List<GiftsPacketDTO> queryGiftsPacket(GiftsPacketQTO giftsPacketQTO) throws GiftsException{
    	try{
        	List<GiftsPacketDTO> list = ModelUtil.genGiftsPacketDTOList(giftsPacketDAO.queryGiftsPacket(giftsPacketQTO));
        	//是否有效和无效
        	if(list.size() > 0 ){
        		for (int i = 0; i < list.size(); i++) {
        			
        			GiftsPacketDO giftsPacketDO = new GiftsPacketDO();
					Long start = list.get(i).getGiftsStartTime().getTime();
					Long end = list.get(i).getGiftsEndTime().getTime()+86400000L;
					Long curr = DateUtils.getCurrentDate().getTime();
					if(start < curr && curr <= end){
						list.get(i).setGiftsValidate(1);
						giftsPacketDO.setGiftsValidate(1);
					}else{
						giftsPacketDO.setGiftsValidate(0);
						list.get(i).setGiftsValidate(0);
					}
						giftsPacketDO.setId(list.get(i).getId());
						giftsPacketDAO.updateGiftsPacket(giftsPacketDO);
        		}
        	}
        	return list;
    	} catch (Exception e) {
            LOGGER.error("error to queryGiftsPacket, giftsPacketQTO : {}", JsonUtil.toJson(giftsPacketQTO), e);
            throw new GiftsException(ResponseCode.DB_OP_ERROR);
        }
    }
    
    @Override
    public GiftsPacketDTO itemGifts(GiftsPacketDTO giftsPacketDTO) throws GiftsException {
    	
    		GiftsPacketDO giftsPacketDO = new GiftsPacketDO();
        	giftsPacketDO.setGoodsId(giftsPacketDTO.getGoodsId());
        	giftsPacketDO.setGoodsSkuId(giftsPacketDTO.getGoodsSkuId());
        	GiftsPacketDO giftsPacket = giftsPacketDAO.getGiftsPacket(giftsPacketDO);
        	GiftsPacketDTO giftsPacketDTOs = ModelUtil.genGiftsPacketDTO(giftsPacket);
        	
        	GiftsPacketProfitDO giftsPacketProfitDO = new GiftsPacketProfitDO();
        	giftsPacketProfitDO.setGiftsPacketId(giftsPacket.getId());
        	List<GiftsPacketProfitDO>  list = giftsPacketProfitDAO.queryGiftsPacketProfit(giftsPacketProfitDO);
        	giftsPacketDTOs.setProfitList(ModelUtil.genGiftsPacketProfitList(list));
        	
    	return giftsPacketDTOs;
    }
    
    @Override
    public List<GiftsPacketDTO> appQueryGiftsPacket(String appKey) throws GiftsException{
    	try{
        	List<GiftsPacketDTO> list = ModelUtil.genGiftsPacketDTOList(giftsPacketDAO.appQueryGiftsPacket());
        	
        	if(list.size() > 0){
        		list = this.giftsStockNum(list, list.get(0).getItemId(), appKey);
        	}
        	return list;
    	} catch (Exception e) {
            throw new GiftsException(ResponseCode.DB_OP_ERROR);
        }
    }
    
    @Override
    public GiftsPacketProfitDTO giftsPoints (Long itemID,Long levelId) throws GiftsException{
    	
    	GiftsPacketDO giftsPacketDO = new GiftsPacketDO();
    	giftsPacketDO.setGoodsId(itemID);
    	GiftsPacketDO giftsPacket = giftsPacketDAO.getGiftsPacket(giftsPacketDO);
    	
    	GiftsPacketProfitDO giftsPacketProfitDO = new GiftsPacketProfitDO();
    	giftsPacketProfitDO.setGiftsPacketId(giftsPacket.getId());
    	giftsPacketProfitDO.setLevelId(levelId);
    	GiftsPacketProfitDO  list = giftsPacketProfitDAO.getGiftsPacketProfit(giftsPacketProfitDO);
    	GiftsPacketProfitDTO  giftsPacketProfitDTOs = ModelUtil.genGiftsPacketProfitDTO(list);
    	return giftsPacketProfitDTOs;
    }
    
    
    //过滤库存为0的礼包
    private List<GiftsPacketDTO> giftsStockNum(List<GiftsPacketDTO> list,Long sellerId,String appKey) throws GiftsException{
    	for (int i = 0; i < list.size(); i++) {
    		ItemSkuDTO itemSkuDTO = itemManager.getItemSku(list.get(i).getGoodsSkuId(), sellerId, appKey);
            if(itemSkuDTO.getStockNum() == null || itemSkuDTO.getStockNum() == 0){
            	list.remove(i);
            	i--;
            }else{
            	StringBuffer sb = new StringBuffer();
    			sb.append(list.get(i).getItemId());
    			sb.append("_");
    			sb.append(list.get(i).getGoodsId());
    			list.get(i).setItemUid(String.valueOf(sb));
    			StringBuffer sb1 = new StringBuffer();
    			sb1.append(list.get(i).getSkuId());
    			sb1.append("_");
    			sb1.append(list.get(i).getGoodsSkuId());
    			list.get(i).setItemSkuUid(String.valueOf(sb1));
            }
		}
    	return list;
    }
}
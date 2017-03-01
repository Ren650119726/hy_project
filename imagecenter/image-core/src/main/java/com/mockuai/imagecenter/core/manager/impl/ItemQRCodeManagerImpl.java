package com.mockuai.imagecenter.core.manager.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.alibaba.fastjson.JSON;
import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.imagecenter.common.constant.ResponseCode;
import com.mockuai.imagecenter.common.domain.dto.ImageItemDTO;
import com.mockuai.imagecenter.core.domain.BizType;
import com.mockuai.imagecenter.core.domain.ImageDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDO;
import com.mockuai.imagecenter.core.domain.QrcodeConfigDetailDO;
import com.mockuai.imagecenter.core.exception.ImageException;
import com.mockuai.imagecenter.core.manager.QRCodeManager;
import com.mockuai.imagecenter.core.manager.QrCodeItemGenerator;
import com.mockuai.imagecenter.core.util.ImageUtil;
import com.mockuai.itemcenter.client.ItemClient;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 冠生 on 2016/5/26.
 */
@Service
public class ItemQRCodeManagerImpl  extends QRCodeManager implements QrCodeItemGenerator {

    private static final Logger log = LoggerFactory.getLogger(ItemQRCodeManagerImpl.class);


    @Autowired
    private AppClient appClient;

    @Autowired
    ItemClient itemClient;





    /**
     * 移除店铺  没有distributorId
      * @param id
     * @param sellerId
     * @param userId
     * @param bizcode
     * @param appKey
     * @return
     * @throws ImageException
     */
    public ImageDO genQRCode(String id, String sellerId, Long userId, String bizcode, String appKey) throws ImageException {
        log.info("getItem itemId:{},sellerId:{},userId:{}",id,sellerId,userId);
        long itemId = Long.parseLong(id)  ;
        ItemQTO itemQTO  = new ItemQTO();
        itemQTO.setId(itemId);
        Response<ItemDTO> itemDTOResponse =  itemClient.getItem(Long.parseLong(id),Long.parseLong(sellerId),false ,appKey);
        if(!itemDTOResponse.isSuccess()){
            log.error("error to itemClient, getItem , itemId:{},sellerId:{},shareUserId:{}   appKey : {}, code:{}, msg:{}",
                    id,sellerId,userId, appKey, itemDTOResponse.getCode(), itemDTOResponse.getMessage());
            throw new ImageException(ResponseCode.SYS_ITEM_SERVICE_EXCEPTION,itemDTOResponse.getMessage());
        }
        try {
            log.info("occur    itemClient  getItem :  itemId : {}, sellerId:{}, shareUserId:{},itemDTO.name {} ",
                    id,sellerId,userId, itemDTOResponse.getModule().getItemName());
        }catch (Exception e){
            log.error("read itemClient getItem ",e);
        }
        ItemDTO itemDTO =  itemDTOResponse.getModule();
        Integer itemType = itemDTO.getItemType();
      /*  DistShopDTO shopDTO = getShopBySellerId(distributorId,appKey);
        String shopName =  shopDTO.getShopName();*/
        String itemName = itemDTO.getItemName();
        String imageUrl =  getPrimaryImage(itemDTO.getItemImageDTOList());
        Long promotionPrice =    itemDTO.getPromotionPrice();
        ImageItemDTO imageItemDTO = new ImageItemDTO();
        imageItemDTO.setSellerId(Long.parseLong(sellerId));
        imageItemDTO.setItemId(itemId);
        imageItemDTO.setShareUserId(userId);
        imageItemDTO.setSeckill(itemType.intValue() == 13);
        String content  = ImageUtil.getItemUrlTpl(imageItemDTO);
        QrcodeConfigDO config = null;
        try {
            config = qrcodeConfigDAO.queryByBizType(BizType.ITEM.name());
            List<QrcodeConfigDetailDO> qrcodeConfigDetailDOList =
                    JSON.parseArray(config.getConfigInfo(), QrcodeConfigDetailDO.class);
            ItemRender itemRender = new ItemRender(id+"_"+userId,qrcodeConfigDetailDOList,content,config.getBgImage(),true);
            log.info("render image parameter url:{},itemName:{},price:{}",imageUrl,itemName,promotionPrice);

            itemRender.render(imageUrl,"长",itemName,String.format("%.2f",promotionPrice.doubleValue()/100));
            return generateImageDO(itemRender,userId,itemId,ITEM,content);

        } catch (SQLException e) {
            log.error(ResponseCode.SYS_E_DB_INSERT.getComment(),e);
            throw  new ImageException(ResponseCode.SYS_E_DB_INSERT,e);
        }catch (Exception e){
            log.error(ResponseCode.GEN_IMAGE_ERROR.getComment(),e);
            throw  new ImageException(ResponseCode.GEN_IMAGE_ERROR,e);
        }

    }


    @Override
    public void genQRCode(Long itemId, String bizcode, String appKey) throws ImageException {

        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setId(itemId);
        itemQTO.setSellerId(0L);
        Response<List<ItemDTO>> response =    itemClient.queryItem(itemQTO,appKey);
        if(!response.isSuccess()){
            log.error("error to itemClient, queryItem  itemId:{} appKey : {}, code:{}, msg:{}",
                    itemId, appKey, response.getCode(), response.getMessage());
            throw new ImageException(ResponseCode.SYS_E_SERVICE_EXCEPTION,response.getMessage());
        }
        List<ItemDTO> itemDTOList =   response.getModule();
        for(ItemDTO itemDTO : itemDTOList){
            try {
                Integer itemType = itemDTO.getItemType();
                ImageItemDTO imageItemDTO = new ImageItemDTO();
                imageItemDTO.setSellerId(itemDTO.getSellerId());
                imageItemDTO.setItemId(itemId);
                imageItemDTO.setShareUserId(0L);
                imageItemDTO.setSeckill(itemType.intValue() == 13);
                String urlTpl  = ImageUtil.getItemUrlTpl(imageItemDTO);
                String url = String.format(urlTpl,itemDTO.getSellerId(),itemId,itemDTO.getDistributorId());
                genQRCode(itemId+"",itemDTO.getSellerId()+"", itemDTO.getDistributorId(),url,appKey);
            }catch (Exception e){
                log.error("genQRCode ",e);
            }

        }
    }

    /**
     * 查找主图
     * @param itemList
     * @return
     */
    private String getPrimaryImage(List<ItemImageDTO> itemList){
            for(ItemImageDTO item : itemList){
                if(item.getImageType() == 1){
                    return item.getImageUrl() ;
                }
            }
        return null;
    }

    private String getUrlTpl(String code){
        String domain = appClient.getAppInfoByType(code, AppTypeEnum.APP_WAP).getModule().getDomainName();

        //例子  http://m.haiyun.com/detail.html?item_uid=1841254_29065&distributor_id=19

        String url = "http://" + domain + "/detail.html?item_uid=%s_%s&distributor_id=%s";
        return url ;
    }

}

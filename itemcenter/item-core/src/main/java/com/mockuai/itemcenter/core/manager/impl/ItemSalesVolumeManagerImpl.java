package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSearchDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.dao.ItemSalesVolumeDAO;
import com.mockuai.itemcenter.core.domain.ItemSalesVolumeDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSalesVolumeManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import com.mockuai.tradecenter.client.TradeDataReportClient;
import com.mockuai.tradecenter.common.domain.DataQTO;
import com.mockuai.tradecenter.common.domain.OrderItemDTO;
import com.mockuai.tradecenter.common.domain.SalesTotalDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/10/16.
 */
@Service
public class ItemSalesVolumeManagerImpl implements ItemSalesVolumeManager {


    @Resource
    private AppClient appClient;

    @Resource
    private TradeDataReportClient tradeDataReportClient;

    @Resource
    private ItemSalesVolumeDAO itemSalesVolumeDAO;


    private static final Logger LOG = LoggerFactory.getLogger(ItemSalesVolumeManagerImpl.class);


    @Override
    public Long getItemSalesVolume(ItemSearchDTO itemSearchDTO) throws ItemException {

        DataQTO query = new DataQTO();

        try {

            Long sellerId = Long.valueOf(itemSearchDTO.getItemUid().split("_")[0]);
            Long itemId = Long.valueOf(itemSearchDTO.getItemUid().split("_")[1]);


            return getItemSalesVolume(itemId, sellerId, itemSearchDTO.getBizCode());

        } catch (Exception e) {
            LOG.error("初始化商品销量数据失败 itemUId:{} bizCode:{}", itemSearchDTO.getItemUid(), itemSearchDTO.getBizCode());
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "初始化商品销量数据失败");
        }

    }

    @Override
    public Long getItemSalesVolume(Long itemId, String bizCode) throws ItemException {
        ItemSalesVolumeDO query = new ItemSalesVolumeDO();
        query.setItemId(itemId);
        query.setBizCode(bizCode);
        ItemSalesVolumeDO itemSalesVolumeDO = itemSalesVolumeDAO.getItemSalesVolumeByItemId(query);

        if (itemSalesVolumeDO == null) {
            return 0L;
        } else {
            return itemSalesVolumeDO.getSalesVolume();
        }
    }

    @Override
    public Long getItemSalesVolume(Long itemId, Long sellerId, String bizCode) throws ItemException {

        DataQTO query = new DataQTO();

        try {


            query.setItemId(itemId);
            query.setSeller_id(sellerId);

            com.mockuai.appcenter.common.api.Response<AppInfoDTO> response = appClient.getAppInfoByType(bizCode, AppTypeEnum.APP_WAP);

            AppInfoDTO appInfoDTO = response.getModule();

            com.mockuai.tradecenter.common.api.Response<SalesTotalDTO> response1 = tradeDataReportClient.querySalesRatio(query, appInfoDTO.getAppKey());

            SalesTotalDTO salesTotalDTO = response1.getModule();

            Integer salesVolumes = salesTotalDTO.getSalesVolumes();

            return salesVolumes.longValue();

        } catch (Exception e) {
            LOG.error("初始化商品销量数据失败 itemUId:{}_{} bizCode:{}", sellerId, itemId, bizCode);
            throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, "初始化商品销量数据失败");
        }

    }

    @Override
    public List<OrderItemDTO> updateItemSalesVolume(List<OrderItemDTO> orderItemDTOs) throws ItemException {

        for (OrderItemDTO orderItemDTO : orderItemDTOs) {

            ItemSalesVolumeDO query = new ItemSalesVolumeDO();

            query.setItemId(orderItemDTO.getItemId());
            query.setBizCode(orderItemDTO.getBizCode());
            query.setSellerId(orderItemDTO.getSellerId());


            ItemSalesVolumeDO itemSalesVolumeDO = itemSalesVolumeDAO.getItemSalesVolumeByItemId(query);

            Long salesVolume = getItemSalesVolume(orderItemDTO.getItemId(), orderItemDTO.getSellerId(), orderItemDTO.getBizCode());



            if (itemSalesVolumeDO == null) {
                //没有销量纪录则添加销量纪录
                query.setSalesVolume(salesVolume);
                itemSalesVolumeDAO.addItemSalesVolume(query);

            } else {

                //有销量记录则更新销量记录
                itemSalesVolumeDO.setSalesVolume(salesVolume);
                itemSalesVolumeDAO.updateSalesVolume(itemSalesVolumeDO);
            }

        }

        return orderItemDTOs;
    }


}

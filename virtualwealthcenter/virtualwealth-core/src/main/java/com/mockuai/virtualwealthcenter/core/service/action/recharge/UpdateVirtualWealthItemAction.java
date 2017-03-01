//package com.mockuai.virtualwealthcenter.core.service.action.recharge;
//
//import com.mockuai.appcenter.common.domain.AppInfoDTO;
//import com.mockuai.itemcenter.common.constant.VirtualMark;
//import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
//import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
//import com.mockuai.virtualwealthcenter.common.api.Request;
//import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
//import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
//import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
//import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
//import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;
//import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
//import com.mockuai.virtualwealthcenter.core.manager.ItemManager;
//import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthItemManager;
//import com.mockuai.virtualwealthcenter.core.service.RequestContext;
//import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by duke on 16/4/18.
// */
//@Service
//public class UpdateVirtualWealthItemAction extends TransAction {
//    private static final Logger log = LoggerFactory.getLogger(UpdateVirtualWealthItemAction.class);
//
//    // 最大的配置项数
//    private static final int MAX_CONFIGS = 6;
//
//    @Resource
//    private VirtualWealthItemManager virtualWealthItemManager;
//
//    @Resource
//    private ItemManager itemManager;
//
//    @Override
//    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//        Request request = context.getRequest();
//        List<VirtualWealthItemDTO> virtualWealthItemDTOs = (List<VirtualWealthItemDTO>) request.getParam("virtualWealthItemDTOs");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (virtualWealthItemDTOs == null) {
//            log.error("virtualWealthItemDTOs is null, bizCode: {}", appInfo.getBizCode());
//            throw new VirtualWealthException(ResponseCode.PARAMETER_NULL, "virtualWealthItemDTOs is null");
//        }
//
//        List<VirtualWealthItemDTO> newItems = new ArrayList<>();
//        List<VirtualWealthItemDTO> updateItems = new ArrayList<>();
//        List<VirtualWealthItemDTO> deleteItems = new ArrayList<>();
//
//        VirtualWealthItemQTO virtualWealthItemQTO = new VirtualWealthItemQTO();
//        virtualWealthItemQTO.setBizCode(appInfo.getBizCode());
//        List<VirtualWealthItemDTO> oldItems = virtualWealthItemManager.queryItems(virtualWealthItemQTO);
//        Map<Long, VirtualWealthItemDTO> map = new HashMap<>();
//        for (VirtualWealthItemDTO itemDTO : oldItems) {
//            map.put(itemDTO.getId(), itemDTO);
//        }
//
//        for (VirtualWealthItemDTO itemDTO : virtualWealthItemDTOs) {
//            if (itemDTO.getId() == null) {
//                newItems.add(itemDTO);
//            } else {
//                VirtualWealthItemDTO dto = map.remove(itemDTO.getId());
//                if (!dto.getAmount().equals(itemDTO.getAmount()) || !dto.getDiscount().equals(itemDTO.getDiscount())) {
//                    itemDTO.setItemId(dto.getItemId());
//                    itemDTO.setSellerId(dto.getSellerId());
//                    itemDTO.setSkuId(dto.getSkuId());
//                    updateItems.add(itemDTO);
//                }
//            }
//        }
//
//        for (Map.Entry<Long, VirtualWealthItemDTO> entry : map.entrySet()) {
//            deleteItems.add(entry.getValue());
//        }
//
//        if (newItems.size() + updateItems.size() > MAX_CONFIGS) {
//            log.error("超过了最大配置项数目");
//            throw new VirtualWealthException(ResponseCode.SERVICE_EXCEPTION, "最大配置项数目为6条");
//        }
//
//        // 添加商品
//        for (VirtualWealthItemDTO dto : newItems) {
//            dto.setBizCode(appInfo.getBizCode());
//            ItemDTO itemDTO = addItem(dto.getAmount(),
//                    dto.getDiscount(), dto.getSellerId(), appInfo.getAppKey());
//            dto.setSkuId(itemDTO.getItemSkuDTOList().get(0).getId());
//            dto.setItemId(itemDTO.getId());
//            dto.setItemType(itemDTO.getItemType());
//            Long id = virtualWealthItemManager.addItem(dto);
//            dto.setId(id);
//        }
//
//        // 更新商品
//        for (VirtualWealthItemDTO dto : updateItems) {
//            updateItem(dto.getSkuId(), dto.getSellerId(), (long) (dto.getAmount() * dto.getDiscount()), dto.getAmount(), appInfo.getAppKey());
//            virtualWealthItemManager.update(dto);
//        }
//
//        // 删除商品
//        for (VirtualWealthItemDTO dto : deleteItems) {
//            deleteItem(dto.getItemId(), dto.getSellerId(), appInfo.getAppKey());
//            virtualWealthItemManager.deleteItem(dto.getId());
//        }
//        return new VirtualWealthResponse(true);
//    }
//
//    private ItemDTO addItem(Long amount, Double discount, Long sellerId, String appKey) throws VirtualWealthException {
//        ItemDTO itemDTO = new ItemDTO();
//        itemDTO.setSellerId(sellerId);
//        itemDTO.setItemName("虚拟财富余额");
//        itemDTO.setCategoryId(0L);
//        itemDTO.setItemBrandId(0L);
//        itemDTO.setIconUrl("");
//        // 标记为虚拟商品
//        itemDTO.setVirtualMark(VirtualMark.VIRTUAL.getCode());
//        // 上架
//        itemDTO.setItemStatus(4);
//        //itemDTO.setItemType(ItemType.VIRTUAL_WEALTH.getType());
//        itemDTO.setFreight(0L);
//        ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
//        itemSkuDTO.setSellerId(itemDTO.getSellerId());
//        itemSkuDTO.setStockNum(Long.MAX_VALUE >> 1);
//        itemSkuDTO.setPromotionPrice((long) (amount * discount));
//        itemSkuDTO.setMarketPrice(amount);
//        itemSkuDTO.setWirelessPrice(amount);
//        itemDTO.setItemSkuDTOList(Arrays.asList(itemSkuDTO));
//
//        return itemManager.addItem(itemDTO, appKey);
//    }
//
//    private Boolean updateItem(Long id, Long sellerId, Long realAmount, Long amount, String appKey) throws VirtualWealthException {
//        ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
//        itemSkuDTO.setId(id);
//        itemSkuDTO.setSellerId(sellerId);
//        itemSkuDTO.setPromotionPrice(realAmount);
//        itemSkuDTO.setMarketPrice(amount);
//        itemSkuDTO.setWirelessPrice(amount);
//        return itemManager.updateItemSku(itemSkuDTO, appKey);
//    }
//
//    private Boolean deleteItem(Long itemId, Long sellerId, String appKey) throws VirtualWealthException {
//        return itemManager.deleteItem(itemId, sellerId, appKey);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.UPDATE_VIRTUAL_WEALTH_ITEM.getActionName();
//    }
//}
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
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by duke on 16/4/19.
// */
//@Service
//public class QueryVirtualWealthItemAction extends TransAction {
//    private static final Logger log = LoggerFactory.getLogger(QueryVirtualWealthItemAction.class);
//
//    @Resource
//    private VirtualWealthItemManager virtualWealthItemManager;
//
//    @Resource
//    private ItemManager itemManager;
//
//    @Override
//    public VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
//        Request request = context.getRequest();
//        VirtualWealthItemQTO virtualWealthItemQTO = (VirtualWealthItemQTO) request.getParam("virtualWealthItemQTO");
//        AppInfoDTO appInfo = (AppInfoDTO) context.get("appInfo");
//
//        if (virtualWealthItemQTO == null) {
//            log.error("virtualWealthItemQTO is null");
//            throw new VirtualWealthException(ResponseCode.PARAMETER_NULL, "virtualWealthItemQTO is null");
//        }
//
//        virtualWealthItemQTO.setBizCode(appInfo.getBizCode());
//
//        List<VirtualWealthItemDTO> virtualWealthItemDTOs = virtualWealthItemManager.queryItems(virtualWealthItemQTO);
//
//        if (virtualWealthItemQTO.getSellerId() != null) {
//            if (virtualWealthItemDTOs.isEmpty()) {
//                // 如果为空，则默认创建6条记录
//                long[] defaultAmount = {10L, 20L, 50L, 100L, 200L, 500L};
//                for (long amount : defaultAmount) {
//                    ItemDTO itemDTO = addItem(amount, virtualWealthItemQTO.getSellerId(), appInfo.getAppKey());
//                    VirtualWealthItemDTO virtualWealthItemDTO = new VirtualWealthItemDTO();
//                    virtualWealthItemDTO.setBizCode(appInfo.getBizCode());
//                    // 单位为分
//                    virtualWealthItemDTO.setAmount(amount * 100);
//                    virtualWealthItemDTO.setDiscount(1.0);
//                    virtualWealthItemDTO.setItemId(itemDTO.getId());
//                    virtualWealthItemDTO.setSellerId(virtualWealthItemQTO.getSellerId());
//                    virtualWealthItemDTO.setSkuId(itemDTO.getItemSkuDTOList().get(0).getId());
//                    virtualWealthItemDTO.setItemType(itemDTO.getItemType());
//                    Long id = virtualWealthItemManager.addItem(virtualWealthItemDTO);
//                    virtualWealthItemDTO.setId(id);
//                    virtualWealthItemDTOs.add(virtualWealthItemDTO);
//                }
//            }
//        }
//
//        return new VirtualWealthResponse(virtualWealthItemDTOs);
//    }
//
//    private ItemDTO addItem(Long amount, Long sellerId, String appKey) throws VirtualWealthException {
//        ItemDTO itemDTO = new ItemDTO();
//        itemDTO.setSellerId(sellerId);
//        itemDTO.setItemName("虚拟财富余额");
//        itemDTO.setCategoryId(0L);
//        itemDTO.setItemBrandId(0L);
//        itemDTO.setIconUrl("");
//        itemDTO.setVirtualMark(VirtualMark.VIRTUAL.getCode());
//        // 上架
//        itemDTO.setItemStatus(4);
//        //itemDTO.setItemType(ItemType.VIRTUAL_WEALTH.getType());
//        itemDTO.setFreight(0L);
//        ItemSkuDTO itemSkuDTO = new ItemSkuDTO();
//        itemSkuDTO.setSellerId(itemDTO.getSellerId());
//        itemSkuDTO.setStockNum(Long.MAX_VALUE >> 1);
//        itemSkuDTO.setPromotionPrice(amount);
//        itemSkuDTO.setMarketPrice(amount);
//        itemSkuDTO.setWirelessPrice(amount);
//        itemDTO.setItemSkuDTOList(Arrays.asList(itemSkuDTO));
//
//        return itemManager.addItem(itemDTO, appKey);
//    }
//
//    @Override
//    public String getName() {
//        return ActionEnum.QUERY_VIRTUAL_WEALTH_ITEM.getActionName();
//    }
//}
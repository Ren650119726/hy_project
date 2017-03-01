package com.mockuai.distributioncenter.mop.api.util;

import com.mockuai.distributioncenter.common.domain.dto.*;
import com.mockuai.distributioncenter.mop.api.domain.*;
import org.springframework.beans.BeanUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 15/10/28.
 */
public class MopApiUtil {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static MopSellerDTO getMopSellerDTO(SellerDTO sellerDTO) {
        MopSellerDTO mopSellerDTO = new MopSellerDTO();
        mopSellerDTO.setId(sellerDTO.getId());
        mopSellerDTO.setUserName(sellerDTO.getRealName());
        mopSellerDTO.setLevelName(sellerDTO.getLevelName());
        mopSellerDTO.setDirectCount(sellerDTO.getDirectCount());
        mopSellerDTO.setGroupCount(sellerDTO.getGroupCount());
        mopSellerDTO.setHeadImgUrl(sellerDTO.getHeadImgUrl());
        if (sellerDTO.getInviterSeller() != null) {
            mopSellerDTO.setInviterName(sellerDTO.getInviterSeller().getRealName());
        } else {
            mopSellerDTO.setInviterName("default");
        }
        return mopSellerDTO;
    }

    public static List<MopSellerDTO> getMopSellerDTOs(List<SellerDTO> sellerDTOs) {
        List<MopSellerDTO> mopSellerDTOs = new ArrayList<MopSellerDTO>();
        for (SellerDTO sellerDTO : sellerDTOs) {
            MopSellerDTO mopSellerDTO = new MopSellerDTO();
            mopSellerDTO.setUserId(sellerDTO.getUserId());
            mopSellerDTO.setUserName(sellerDTO.getRealName());
            mopSellerDTO.setLevelName(sellerDTO.getLevelName());
            mopSellerDTO.setInCome(sellerDTO.getInCome());
            mopSellerDTO.setMobile(sellerDTO.getUserName());
            mopSellerDTO.setWechatId(sellerDTO.getWechatId());
            mopSellerDTO.setInviterCount(sellerDTO.getInviterCount());
            mopSellerDTOs.add(mopSellerDTO);
        }
        return mopSellerDTOs;
    }

    public static MopShopDTO getMopShopDTO(DistShopDTO distShopDTO) {
        MopShopDTO mopShopDTO = new MopShopDTO();
        mopShopDTO.setId(distShopDTO.getId());
        mopShopDTO.setShopName(distShopDTO.getShopName());
        mopShopDTO.setShopDesc(distShopDTO.getShopDesc());
        if (distShopDTO.getSellerDTO() != null) {
            mopShopDTO.setInviterCode(distShopDTO.getSellerDTO().getInviterCode());
            mopShopDTO.setWechatId(distShopDTO.getSellerDTO().getWechatId());
            mopShopDTO.setHeadImgUrl(distShopDTO.getSellerDTO().getHeadImgUrl());
        }
        return mopShopDTO;
    }

    public static List<MopCollectionShopDTO> getMopCollectionShopDTOs(List<CollectionShopDTO> collectionShopDTOs) {
        List<MopCollectionShopDTO> mopCollectionShopDTOs = new ArrayList<MopCollectionShopDTO>();
        for (CollectionShopDTO collectionShopDTO : collectionShopDTOs) {
            MopCollectionShopDTO mopCollectionShopDTO = new MopCollectionShopDTO();
            mopCollectionShopDTO.setId(collectionShopDTO.getId());
            mopCollectionShopDTO.setShopId(collectionShopDTO.getShopId());
            mopCollectionShopDTO.setShopName(collectionShopDTO.getShopName());
            mopCollectionShopDTO.setShopDesc(collectionShopDTO.getShopDesc());
            mopCollectionShopDTOs.add(mopCollectionShopDTO);
        }
        return mopCollectionShopDTOs;
    }

    public static List<MopOrderDTO> getMopOrderDTOs(List<SellerOrderDTO> sellerOrderDTOs) {
        List<MopOrderDTO> mopOrderDTOs = new ArrayList<MopOrderDTO>();
        for (SellerOrderDTO sellerOrderDTO : sellerOrderDTOs) {
            MopOrderDTO mopOrderDTO = new MopOrderDTO();
            mopOrderDTO.setOrderId(sellerOrderDTO.getOrderId());
            mopOrderDTO.setOrderSn(sellerOrderDTO.getOrderSn());
            mopOrderDTO.setStatus(sellerOrderDTO.getStatus());
            mopOrderDTO.setOrderTime(df.format(sellerOrderDTO.getOrderTime()));
            mopOrderDTO.setRealDistAmount(sellerOrderDTO.getRealDistAmount());
            mopOrderDTO.setVirtualDistAmount(sellerOrderDTO.getVirtualDistAmount());

            List<MopOrderDTO.MopOrderItem> mopOrderItems = new ArrayList<MopOrderDTO.MopOrderItem>();
            for (SellerOrderDTO.OrderItem orderItem : sellerOrderDTO.getOrderItemList()) {
                MopOrderDTO.MopOrderItem mopOrderItem = new MopOrderDTO.MopOrderItem();
                mopOrderItem.setItemId(orderItem.getItemId());
                mopOrderItem.setItemName(orderItem.getItemName());
                mopOrderItem.setItemSkuId(orderItem.getItemSkuId());
                mopOrderItem.setNumber(orderItem.getNumber());
                mopOrderItem.setUnitPrice(orderItem.getUnitPrice());
                mopOrderItem.setItemSkuDesc(orderItem.getItemSkuDesc());
                mopOrderItem.setItemImgUrl(orderItem.getItemImgUrl());
                mopOrderItem.setHigoMark(orderItem.getHigoMark());
                mopOrderItems.add(mopOrderItem);
            }
            mopOrderDTO.setMopOrderItemList(mopOrderItems);
            mopOrderDTOs.add(mopOrderDTO);
        }
        return mopOrderDTOs;
    }

    public static MopOrderDetailDTO getMopOrderDetailDTO(SellerOrderDTO sellerOrderDTO) {
        MopOrderDetailDTO mopOrderDetailDTO = new MopOrderDetailDTO();
        mopOrderDetailDTO.setOrderId(sellerOrderDTO.getOrderId());
        mopOrderDetailDTO.setStatus(sellerOrderDTO.getStatus());
        mopOrderDetailDTO.setOrderSn(sellerOrderDTO.getOrderSn());
        List<MopOrderDetailDTO.MopOrderItem> mopOrderItems = new ArrayList<MopOrderDetailDTO.MopOrderItem>();
        for (SellerOrderDTO.OrderItem orderItem : sellerOrderDTO.getOrderItemList()) {
            MopOrderDetailDTO.MopOrderItem mopOrderItem = new MopOrderDetailDTO.MopOrderItem();
            mopOrderItem.setItemId(orderItem.getItemId());
            mopOrderItem.setItemSkuId(orderItem.getItemSkuId());
            mopOrderItem.setItemName(orderItem.getItemName());
            mopOrderItem.setShopId(orderItem.getShopId());
            mopOrderItem.setShopName(orderItem.getShopName());
            mopOrderItem.setItemImgUrl(orderItem.getItemImgUrl());
            mopOrderItem.setItemSkuDesc(orderItem.getItemSkuDesc());
            mopOrderItem.setUnitPrice(orderItem.getUnitPrice());
            mopOrderItem.setNumber(orderItem.getNumber());
            mopOrderItem.setHigoMark(orderItem.getHigoMark());
            mopOrderItem.setSellerId(orderItem.getSellerId());
            mopOrderItem.setItemUid(orderItem.getItemUid());
            mopOrderItems.add(mopOrderItem);
        }
        mopOrderDetailDTO.setOrderItemList(mopOrderItems);
        mopOrderDetailDTO.setRealDistAmount(sellerOrderDTO.getRealDistAmount());
        mopOrderDetailDTO.setVirtualDistAmount(sellerOrderDTO.getVirtualDistAmount());
        mopOrderDetailDTO.setOrderTime(sellerOrderDTO.getOrderTime());
        mopOrderDetailDTO.setPayTime(sellerOrderDTO.getPayTime());
        mopOrderDetailDTO.setDeliveryTime(sellerOrderDTO.getDeliveryTime());
        mopOrderDetailDTO.setConfirmTime(sellerOrderDTO.getConfirmTime());
        mopOrderDetailDTO.setShutdownTime(sellerOrderDTO.getShutdownTime());

        SellerOrderDTO.DeliveryInfo deliveryInfo = sellerOrderDTO.getDeliveryInfo();
        if (deliveryInfo != null) {
            MopOrderDetailDTO.MopDeliveryInfo mopDeliveryInfo = new MopOrderDetailDTO.MopDeliveryInfo();
            mopDeliveryInfo.setDeliveryCompany(deliveryInfo.getDeliveryCompany());
            mopOrderDetailDTO.setDeliveryInfo(mopDeliveryInfo);
        }
        SellerOrderDTO.ConsigneeInfo consigneeInfo = sellerOrderDTO.getConsigneeInfo();
        if (consigneeInfo != null) {
            MopOrderDetailDTO.MopConsigneeInfo mopConsigneeInfo = new MopOrderDetailDTO.MopConsigneeInfo();
            mopConsigneeInfo.setAddress(consigneeInfo.getAddress());
            mopConsigneeInfo.setProvinceName(consigneeInfo.getProvinceName());
            mopConsigneeInfo.setCityName(consigneeInfo.getCityName());
            mopConsigneeInfo.setAreaName(consigneeInfo.getAreaName());
            mopConsigneeInfo.setConsignee(consigneeInfo.getConsignee());
            mopConsigneeInfo.setMobile(consigneeInfo.getMobile());
            mopConsigneeInfo.setIdCardId(consigneeInfo.getIdCardId());
            mopOrderDetailDTO.setConsigneeInfo(mopConsigneeInfo);
        }
        return mopOrderDetailDTO;
    }

    public static List<MopMyFansDTO> getMyFansDTOs(List<MyFansDTO> myFansDTOs) {

        List<MopMyFansDTO> mopMyFansDTOs = new ArrayList<MopMyFansDTO>();
        for (MyFansDTO myFansDTO : myFansDTOs) {
            MopMyFansDTO mopMyFansDTO = new MopMyFansDTO();
            mopMyFansDTO.setUserId(myFansDTO.getUserId());
            mopMyFansDTO.setSex(myFansDTO.getSex());
            mopMyFansDTO.setHeadPortrait(myFansDTO.getHeadPortrait());
            mopMyFansDTO.setNickname(myFansDTO.getNickname());
            mopMyFansDTO.setGmtCreated(myFansDTO.getGmtCreated());
            mopMyFansDTO.setCumMoney(myFansDTO.getCumMoney());
            mopMyFansDTOs.add(mopMyFansDTO);
        }
        return mopMyFansDTOs;
    }

    public static List<MopHkProtocolDTO> getMopHkPosterityDTOs(List<HkProtocolDTO> hkProtocolDTOs) {
        List<MopHkProtocolDTO> mopHkProtocolDTOs = new ArrayList<MopHkProtocolDTO>();
        for (HkProtocolDTO hkProtocolDTO : hkProtocolDTOs) {
            MopHkProtocolDTO mopHkProtocolDTO = new MopHkProtocolDTO();
            mopHkProtocolDTO.setId(hkProtocolDTO.getId());
            mopHkProtocolDTO.setProName(hkProtocolDTO.getProName());
            mopHkProtocolDTO.setProContent(hkProtocolDTO.getProContent());
            mopHkProtocolDTO.setProModel(hkProtocolDTO.getProModel());
            mopHkProtocolDTO.setType(hkProtocolDTO.getType());
            mopHkProtocolDTOs.add(mopHkProtocolDTO);
        }
        return mopHkProtocolDTOs;
    }
}

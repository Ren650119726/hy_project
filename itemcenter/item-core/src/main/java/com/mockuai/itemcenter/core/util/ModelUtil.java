package com.mockuai.itemcenter.core.util;

import com.mockuai.higocenter.common.domain.ItemHigoInfoDTO;
import com.mockuai.higocenter.common.domain.SkuHigoInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.*;
import com.mockuai.itemcenter.core.domain.CommentImageDO;
import com.mockuai.itemcenter.core.domain.ItemPropertyValueDO;
import com.mockuai.itemcenter.core.domain.SkuPropertyValueDO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by zengzhangqiang on 7/18/15.
 */
public class ModelUtil {
    public static List<CommentImageDO> genCommentImageDOList(List<CommentImageDTO> commentImageDTOs){
        if(commentImageDTOs == null){
            return null;
        }

        List<CommentImageDO> commentImageDOs = new ArrayList<CommentImageDO>();
        for(CommentImageDTO commentImageDTO: commentImageDTOs){
            commentImageDOs.add(genCommentImageDO(commentImageDTO));
        }
        return commentImageDOs;
    }

    public static CommentImageDO genCommentImageDO(CommentImageDTO commentImageDTO){
        if(commentImageDTO == null){
            return null;
        }

        CommentImageDO commentImageDO = new CommentImageDO();
        BeanUtils.copyProperties(commentImageDTO, commentImageDO);
        return commentImageDO;
    }

    public static List<CommentImageDTO> genCommentImageDTOList(List<CommentImageDO> commentImageDOs){
        if(commentImageDOs == null){
            return null;
        }

        List<CommentImageDTO> commentImageDTOs = new ArrayList<CommentImageDTO>();
        for(CommentImageDO commentImageDO: commentImageDOs){
            commentImageDTOs.add(genCommentImageDTO(commentImageDO));
        }
        return commentImageDTOs;
    }

    public static CommentImageDTO genCommentImageDTO(CommentImageDO commentImageDO){
        if(commentImageDO == null){
            return null;
        }

        CommentImageDTO commentImageDTO = new CommentImageDTO();
        BeanUtils.copyProperties(commentImageDO, commentImageDTO);
        return commentImageDTO;
    }

    public static ItemPropertyValueDO genItemPropertyValueDO(ItemPropertyValueDTO itemPropertyValueDTO){
        if(itemPropertyValueDTO == null){
            return null;
        }

        ItemPropertyValueDO itemPropertyValueDO = new ItemPropertyValueDO();
        BeanUtils.copyProperties(itemPropertyValueDTO, itemPropertyValueDO);
        return itemPropertyValueDO;
    }

    public static List<ItemPropertyValueDO> genItemPropertyValueDOList(
            List<ItemPropertyValueDTO> itemPropertyValueDTOs){
        if(itemPropertyValueDTOs == null){
            return null;
        }

        List<ItemPropertyValueDO> itemPropertyValueDOs = new ArrayList<ItemPropertyValueDO>();
        for(ItemPropertyValueDTO itemPropertyValueDTO: itemPropertyValueDTOs){
            itemPropertyValueDOs.add(genItemPropertyValueDO(itemPropertyValueDTO));
        }

        return itemPropertyValueDOs;
    }

    public static SkuPropertyValueDO genSkuPropertyValueDO(SkuPropertyValueDTO skuPropertyValueDTO){
        if(skuPropertyValueDTO == null){
            return null;
        }

        SkuPropertyValueDO skuPropertyValueDO = new SkuPropertyValueDO();
        BeanUtils.copyProperties(skuPropertyValueDTO, skuPropertyValueDO);
        return skuPropertyValueDO;
    }

    public static List<SkuPropertyValueDO> genSkuPropertyValueDOList(
            List<SkuPropertyValueDTO> skuPropertyValueDTOs){
        if(skuPropertyValueDTOs == null){
            return null;
        }

        List<SkuPropertyValueDO>  skuPropertyValueDOs = new ArrayList<SkuPropertyValueDO>();
        for(SkuPropertyValueDTO skuPropertyValueDTO: skuPropertyValueDTOs){
            skuPropertyValueDOs.add(genSkuPropertyValueDO(skuPropertyValueDTO));
        }

        return skuPropertyValueDOs;
    }

    public static HigoExtraInfoDTO genHigoExtraInfoDTO(ItemHigoInfoDTO itemHigoInfoDTO){
        if(itemHigoInfoDTO == null){
            return null;
        }

        HigoExtraInfoDTO higoExtraInfoDTO = new HigoExtraInfoDTO();
        higoExtraInfoDTO.setTaxRate(itemHigoInfoDTO.getTaxRate());
        higoExtraInfoDTO.setTaxNumber(itemHigoInfoDTO.getTaxNumber());
        higoExtraInfoDTO.setDeliveryType(itemHigoInfoDTO.getDeliveryType());
        higoExtraInfoDTO.setSupplyBase(itemHigoInfoDTO.getSupplyBase());
        higoExtraInfoDTO.setTaxThreshold(itemHigoInfoDTO.getTaxThreshold());

        return higoExtraInfoDTO;
    }

    public static ItemHigoInfoDTO genItemHigoInfoDTO(HigoExtraInfoDTO higoExtraInfoDTO){
        if(higoExtraInfoDTO == null){
            return null;
        }

        ItemHigoInfoDTO itemHigoInfoDTO = new ItemHigoInfoDTO();
        itemHigoInfoDTO.setTaxRate(higoExtraInfoDTO.getTaxRate());
        itemHigoInfoDTO.setSupplyBase(higoExtraInfoDTO.getSupplyBase());
        itemHigoInfoDTO.setTaxNumber(higoExtraInfoDTO.getTaxNumber());
        itemHigoInfoDTO.setDeliveryType(higoExtraInfoDTO.getDeliveryType());
        itemHigoInfoDTO.setTaxRate(higoExtraInfoDTO.getTaxRate());
        itemHigoInfoDTO.setTaxThreshold(higoExtraInfoDTO.getTaxThreshold());
        return itemHigoInfoDTO;
    }

    public static SkuHigoExtraInfoDTO genSkuHigoExtraInfoDTO(SkuHigoInfoDTO skuHigoInfoDTO) {
        if(skuHigoInfoDTO==null){
            return null;
        }

        SkuHigoExtraInfoDTO skuHigoExtraInfoDTO = new SkuHigoExtraInfoDTO();
        skuHigoExtraInfoDTO.setCustomsCode(skuHigoExtraInfoDTO.getCustomsCode());

        return skuHigoExtraInfoDTO;
    }
}

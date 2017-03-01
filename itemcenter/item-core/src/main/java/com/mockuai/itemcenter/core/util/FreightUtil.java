package com.mockuai.itemcenter.core.util;

import com.mockuai.itemcenter.common.domain.dto.area.AreaDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplate;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;

/**
 * Created by yindingyu on 15/9/23.
 */
public class FreightUtil {

    //TODO入参待定，逻辑先写好了

    /**
     * 计算运费
     * 算法参考出租车计价方式
     * @param freightTemplate
     * @param count
     * @return
     */
    public static Long calculateFreight(FreightTemplate freightTemplate,int count){


        Integer baseCount = freightTemplate.getBasicCount();
        Long baseCharge = freightTemplate.getBasicCharge();

        //不足首重（首体积）,收基础运费
        if(count<=baseCount){
            return baseCharge;
        }

        Integer extraCount = freightTemplate.getExtraCount();
        Long extraCharge = freightTemplate.getExtraCharge();
        Integer realExtraCount = count - baseCount;


        return baseCharge + (extraCharge * ((realExtraCount - 1)/ extraCount + 1));
    }

    public static Long calculateFreight(FreightTemplateDTO freightTemplateDTO,AreaDTO areaDTO,int count){

        //获得具体地区
        FreightTemplate freightTemplate = getFreightTemplate(freightTemplateDTO,areaDTO);

        return  calculateFreight(freightTemplate,count);
    }


    //TODO  以下均待实现
    private static FreightTemplate getFreightTemplate(FreightTemplateDTO freightTemplateDTO, AreaDTO areaDTO) {


        return null;
    }

}

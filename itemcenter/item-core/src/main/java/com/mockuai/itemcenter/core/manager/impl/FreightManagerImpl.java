package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.FreightAreaTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplate;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.area.AddressDTO;
import com.mockuai.itemcenter.common.domain.qto.FreightAreaTemplateQTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateAreaInfoQTO;
import com.mockuai.itemcenter.common.domain.qto.FreightTemplateQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.dao.FreightAreaTemplateDAO;
import com.mockuai.itemcenter.core.dao.FreightTemplateAreaInfoDAO;
import com.mockuai.itemcenter.core.dao.FreightTemplateDAO;
import com.mockuai.itemcenter.core.dao.ItemDAO;
import com.mockuai.itemcenter.core.domain.FreightAreaTemplateDO;
import com.mockuai.itemcenter.core.domain.FreightTemplateAreaInfoDO;
import com.mockuai.itemcenter.core.domain.FreightTemplateDO;
import com.mockuai.itemcenter.core.domain.ItemDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.FreightManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/10/20.
 */
@Service
public class FreightManagerImpl implements FreightManager {

    private static final Logger LOG = LoggerFactory.getLogger(FreightManagerImpl.class);
    ;
    @Resource
    private ItemDAO itemDAO;

    @Resource
    private FreightTemplateDAO freightTemplateDAO;

    @Resource
    private FreightAreaTemplateDAO freightAreaTemplateDAO;

    @Resource
    private FreightTemplateAreaInfoDAO freightTemplateAreaInfoDAO;

    @Override
    public Long calculate(Map<Long, Integer> itemNums, AddressDTO addressDTO) throws ItemException {

        if (itemNums == null || itemNums.size() == 0) {
            return 0L;
        }
        Long result = 0L;

        try {

            //查询出所有的商品
            List<Long> itemIds = new ArrayList<Long>();
            itemIds.addAll(itemNums.keySet());

            ItemQTO itemQTO = new ItemQTO();
            itemQTO.setIdList(itemIds);

            List<ItemDO> itemDOs = itemDAO.queryItem(itemQTO);
            int size = itemDOs.size();

            //多个商品使用同个模板时，直接计费属性累加，不同模板计算结果累加
            if (itemDOs != null) {

                //每个模板的ID和计算出的数值记录到map中
                Map<Long, Long> resultMap = new HashMap<Long, Long>();

                for (int i = 0; i < size; i++) {

                    ItemDO itemDO = itemDOs.get(i);

                    Long templateId = itemDO.getFreightTemplate();
                    Long sellerId = itemDO.getSellerId();


                    if (templateId == null) {

                        if (itemDO.getFreight() != null) {  //没有设置运费模板有设置固定运费的情况
                            result += itemDO.getFreight();
                        }

                        //没有设置和运费模板，则认为该商品无运费，继续计算下一个商品
                        continue;

                    }

                    //如果该商品已经计算过，则跳过
                    if (resultMap.containsKey(templateId)) {
                        continue;
                    }

                    FreightTemplateQTO templateQTO = new FreightTemplateQTO();

                    templateQTO.setId(templateId);
                    templateQTO.setSellerId(sellerId);

                    FreightTemplateDO templateDO = freightTemplateDAO.getTemplate(templateQTO);

                    if (templateDO == null) {
                        LOG.error("查询不到对应的运费模板，该商品运费按0处理,sellerId:{},templateId{}", sellerId, templateId);
                    }

                    //0 计件  1计重    2计体积
                    int feeType = templateDO.getPricingMethod();

                    long count = getPricingCount(itemDO, feeType, itemNums.get(itemDO.getId()));

                    //需要查找与这个商品使用同一个模板的商品
                    if (i < size - 1) {
                        for (int j = i + 1; j < size; j++) {
                            ItemDO itemDOx = itemDOs.get(j);
                            Long templateIdx = itemDOx.getFreightTemplate();

                            //同类模板累加
                            if (templateId.equals(templateIdx)) {
                                count += getPricingCount(itemDOx, feeType, itemNums.get(itemDOx.getId()));
                            }
                        }
                    }

                    FreightTemplateDTO templateDTO = new FreightTemplateDTO();
                    BeanUtils.copyProperties(templateDO, templateDTO);
                    long temlateFee = calculateFreight(templateDTO, addressDTO, count);
                    resultMap.put(templateId, temlateFee);
                }

                for (Long key : resultMap.keySet()) {
                    result += resultMap.get(key);
                }
            }


        } catch (Exception e) {

        }

        return result;
    }

    /**
     * 得到某个商品参与计算运费属性的总量
     *
     * @param itemDO
     * @param feeType
     * @param nums
     * @return
     */
    private long getPricingCount(ItemDO itemDO, int feeType, int nums) {


        switch (feeType) {

            case 0:
                return nums;
            case 1:
                return nums * getItemWeight(itemDO);
            case 2:
                return nums * getItemSize(itemDO);
        }

        return 0;
    }

    /**
     * 获取商品重量
     */
    private long getItemSize(ItemDO itemDO) {

        if (itemDO.getVolume() != null) {
            return itemDO.getVolume().longValue();
        }
        return 0;
    }

    /**
     * 获取商品体积
     *
     * @return
     */
    private long getItemWeight(ItemDO itemDO) {
        if (itemDO.getWeight() != null) {
            return itemDO.getWeight().longValue();
        }
        return 0;
    }

    /**
     * 计算运费
     * 算法参考出租车计价方式
     *
     * @param freightTemplate
     * @param count
     * @return
     */
    private Long calculateFreight(FreightTemplate freightTemplate, long count) {


        long baseCount = freightTemplate.getBasicCount();
        long baseCharge = freightTemplate.getBasicCharge();

        //不足首重（首体积）,收基础运费
        if (count <= baseCount) {
            return baseCharge;
        }

        long extraCount = freightTemplate.getExtraCount();
        long extraCharge = freightTemplate.getExtraCharge();
        long realExtraCount = count - baseCount;


        return baseCharge + (extraCharge * ((realExtraCount - 1) / extraCount + 1));
    }

    /**
     * 根据运费模板和地址信息、计价属性值计算运费
     *
     * @param freightTemplateDTO
     * @param addressDTO
     * @param count
     * @return
     */
    private Long calculateFreight(FreightTemplateDTO freightTemplateDTO, AddressDTO addressDTO, long count) {

        //获得具体地区
        FreightTemplate freightTemplate = getFreightTemplate(freightTemplateDTO, addressDTO);

        //计算
        Long result = calculateFreight(freightTemplate, count);
        return result;
    }

    /**
     * 根据地址信息将运费模板适配到具体计算的模板（地区模板还是默认模板）
     *
     * @param freightTemplateDTO
     * @param addressDTO
     * @return
     */
    private FreightTemplate getFreightTemplate(FreightTemplateDTO freightTemplateDTO, AddressDTO addressDTO) {

        String provinceCode = addressDTO.getProvinceCode();
        String CityCode = addressDTO.getCityCode();

        FreightAreaTemplateQTO areaTemplateQTO = new FreightAreaTemplateQTO();
        areaTemplateQTO.setParentId(freightTemplateDTO.getId());

        List<FreightAreaTemplateDO> areaTemplateDOs = freightAreaTemplateDAO.queryAreaTemplate(areaTemplateQTO);

        //判断地址是否在地区模板的配送范围
        if (areaTemplateDOs != null) {

            for (FreightAreaTemplateDO freightAreaTemplateDO : areaTemplateDOs) {

                FreightTemplateAreaInfoQTO areaInfoQTO = new FreightTemplateAreaInfoQTO();
                areaInfoQTO.setTemplateId(freightTemplateDTO.getId());
                areaInfoQTO.setAreaTemplateId(freightAreaTemplateDO.getId());

                List<FreightTemplateAreaInfoDO> areaInfoDOs = freightTemplateAreaInfoDAO.queryAreaTemplate(areaInfoQTO);

                if (areaInfoDOs != null) {
                    for (FreightTemplateAreaInfoDO areaInfoDO : areaInfoDOs) {

                        if ((areaInfoDO.getLevel().intValue() == 2) &&      //匹配到省份
                                (areaInfoDO.getCode().equals(addressDTO.getCityCode()))
                                ||       //或者匹配到地级市
                                (areaInfoDO.getLevel().intValue() == 1) &&
                                        (areaInfoDO.getCode().equals(addressDTO.getProvinceCode()))) {

                            FreightAreaTemplateDTO dto = new FreightAreaTemplateDTO();

                            BeanUtils.copyProperties(freightAreaTemplateDO, dto);
                            return dto;
                        }

                    }
                }

            }
        }

        //没有匹配到地区模板，就使用默认模板
        return freightTemplateDTO;

    }

    @Override
    public Long calculateItemDefaultFreight(ItemDTO itemDTO) {

        if (itemDTO == null) {
            return 0L;
        }

        if (itemDTO.getFreightTemplate() == null) {
            if (itemDTO.getFreight() == null) {
                return 0L;
            }

            return itemDTO.getFreight();
        }

        FreightTemplateQTO query = new FreightTemplateQTO();
        query.setId(itemDTO.getFreightTemplate());
        query.setSellerId(itemDTO.getSellerId());
        query.setBizCode(itemDTO.getBizCode());


        FreightTemplateDO freightTemplateDO = freightTemplateDAO.getTemplate(query);

        if (freightTemplateDO == null) {
            return 0L;
        }

        ItemDO itemDO = new ItemDO();

        BeanUtils.copyProperties(itemDTO, itemDO);

        long count = getPricingCount(itemDO, freightTemplateDO.getPricingMethod(), 1);

        FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();

        BeanUtils.copyProperties(freightTemplateDO, freightTemplateDTO);

        long freight = calculateFreight(freightTemplateDTO, count);

        return freight;
    }

}

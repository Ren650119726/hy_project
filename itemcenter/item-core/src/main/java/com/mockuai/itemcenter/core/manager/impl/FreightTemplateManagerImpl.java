package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.FreightAreaTemplateDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplate;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateAreaInfoDTO;
import com.mockuai.itemcenter.common.domain.dto.FreightTemplateDTO;
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
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.FreightTemplateManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yindingyu on 15/9/22.
 */
@Service
public class FreightTemplateManagerImpl implements FreightTemplateManager{


    @Resource
    private FreightTemplateDAO freightTemplateDAO;

    @Resource
    private FreightAreaTemplateDAO freightAreaTemplateDAO;

    @Resource
    private FreightTemplateAreaInfoDAO freightTemplateAreaInfoDAO;

    @Resource
    private ItemDAO itemDAO;

    @Override
    public Long calculateFreight(Map<Long, Integer> itemNums) {

        //TODO 根据商品列表结算
        return 213L;
    }

    @Override
    public Long addFeightTemplate(FreightTemplateDTO freightTemplateDTO) throws ItemException {

        FreightTemplateDO freightTemplateDO = new FreightTemplateDO();

        verifyFreightTemplateDTO(freightTemplateDTO);

        String bizCode = freightTemplateDTO.getBizCode();

        BeanUtils.copyProperties(freightTemplateDTO, freightTemplateDO);

        //添加默认模板
        freightTemplateDO.setId(null);
        Long parentId = freightTemplateDAO.addTemplate(freightTemplateDO);
        Long sellerId = freightTemplateDTO.getSellerId();


        //插于下属的所有地区模板
        if(freightTemplateDTO.getFreightAreaTemplateList()!=null){

            for(FreightAreaTemplateDTO freightAreaTemplateDTO
                    : freightTemplateDTO.getFreightAreaTemplateList()){
                FreightAreaTemplateDO freightAreaTemplateDO = new FreightAreaTemplateDO();

                BeanUtils.copyProperties(freightAreaTemplateDTO, freightAreaTemplateDO);
                freightAreaTemplateDO.setId(null);
                freightAreaTemplateDO.setParentId(parentId);
                freightAreaTemplateDO.setSellerId(sellerId);
                freightAreaTemplateDO.setBizCode(bizCode);

                List<FreightTemplateAreaInfoDTO> areas = freightAreaTemplateDTO.getAreas();

                Long areaTemplateId = freightAreaTemplateDAO.addAreaTemplate(freightAreaTemplateDO);

                //保存地区模板中的地址信息
                if(areas!=null&&areas.size()>0) {

                    for (FreightTemplateAreaInfoDTO area : areas) {

                        FreightTemplateAreaInfoDO areaInfoDO = new FreightTemplateAreaInfoDO();
                        BeanUtils.copyProperties(area,areaInfoDO);
                        areaInfoDO.setId(null);
                        areaInfoDO.setSellerId(sellerId);
                        areaInfoDO.setTemplateId(parentId);
                        areaInfoDO.setAreaTemplateId(areaTemplateId);
                        areaInfoDO.setBizCode(bizCode);
                        freightTemplateAreaInfoDAO.addAreaInfo(areaInfoDO);
                    }
                }else{
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"地区模板中的地址不能为空");
                }
            }
        }

        return  parentId;
    }

    private void verifyFreightTemplateDTO(FreightTemplateDTO freightTemplateDTO) throws ItemException{

        if(freightTemplateDTO.getPricingMethod()==null){
             freightTemplateDTO.setPricingMethod(0);
        }

        int pricingMethod = freightTemplateDTO.getPricingMethod();


        String basicCountText = "首件数量";
        String basicChargeText = "首件价格";
        String extraCountText = "续件数量";
        String extraChargeText = "续件价格";

        if(pricingMethod==0) {
            //do nothing,all right
        }else if(pricingMethod==1){
            basicCountText = "首重";
            basicChargeText = "首重价格";
            extraCountText = "续重";
            extraChargeText = "续重价格";
        }else if(pricingMethod==2){
            basicCountText = "首体积";
            basicChargeText = "首体积价格";
            extraCountText = "续体积";
            extraChargeText = "续体积价格";
        }else{
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,"不支持的计价方式"+pricingMethod);
        }

        if(freightTemplateDTO.getBasicCount()==null){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,basicCountText+"不能为空");
        }
        if(freightTemplateDTO.getBasicCount()<0){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,basicCountText+"不能为负数");
        }

        if(freightTemplateDTO.getBasicCharge()==null){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,basicChargeText+"不能为空");
        }
        if(freightTemplateDTO.getBasicCharge()<0){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,basicChargeText+"不能为负数");
        }

        if(freightTemplateDTO.getExtraCount()==null){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,extraCountText+"不能为空");
        }
        if(freightTemplateDTO.getExtraCount()<=0){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,extraCountText+"不能为零或负数");
        }

        if(freightTemplateDTO.getExtraCharge()==null){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,extraChargeText+"不能为空");
        }
        if(freightTemplateDTO.getExtraCharge()<0){
            throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,extraChargeText+"不能为负数");
        }

        //继续验证子模板
        if(freightTemplateDTO.getFreightAreaTemplateList()!=null){

            for(FreightAreaTemplateDTO freightAreaTemplateDTO : freightTemplateDTO.getFreightAreaTemplateList()){

                if(freightAreaTemplateDTO.getBasicCount()==null){
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,basicCountText+"不能为空");
                }
                if(freightAreaTemplateDTO.getBasicCount()<0){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,basicCountText+"不能为负数");
                }

                if(freightAreaTemplateDTO.getBasicCharge()==null){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,basicChargeText+"不能为空");
                }
                if(freightAreaTemplateDTO.getBasicCharge()<0){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,basicChargeText+"不能为负数");
                }

                if(freightAreaTemplateDTO.getExtraCount()==null){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,extraCountText+"不能为空");
                }
                if(freightAreaTemplateDTO.getExtraCount()<=0){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,extraCountText+"不能为零或负数");
                }

                if(freightAreaTemplateDTO.getExtraCharge()==null){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,extraChargeText+"不能为空");
                }
                if(freightAreaTemplateDTO.getExtraCharge()<0){
                    throw  ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,extraChargeText+"不能为负数");
                }
            }
        }
    }



    @Override
    public Boolean updateFeightTemplate(FreightTemplateDTO freightTemplateDTO)  throws ItemException{

        verifyFreightTemplateDTO(freightTemplateDTO);

        //修改模板
        FreightTemplateDO freightTemplateDO = new FreightTemplateDO();
        BeanUtils.copyProperties(freightTemplateDTO, freightTemplateDO);

        freightTemplateDAO.updateTemplate(freightTemplateDO);


        if(freightTemplateDTO.getFreightAreaTemplateList()!=null) {

            Long sellerId = freightTemplateDTO.getSellerId();
            Long templateId = freightTemplateDTO.getId();

            String bizCode = freightTemplateDTO.getBizCode();

            //删除模板下所有地区模板
            FreightAreaTemplateQTO templateQTO = new FreightAreaTemplateQTO();
            templateQTO.setParentId(templateId);
            templateQTO.setSellerId(sellerId);
            templateQTO.setBizCode(bizCode);

            freightAreaTemplateDAO.deleteAreaTemplateByParentId(templateQTO);

            //删除模板下所有地区模板下的地区信息
            FreightTemplateAreaInfoQTO areaInfoQTO = new FreightTemplateAreaInfoQTO();
            areaInfoQTO.setTemplateId(templateId);
            areaInfoQTO.setSellerId(sellerId);
            areaInfoQTO.setBizCode(bizCode);

            freightTemplateAreaInfoDAO.deleteAreaInfoByTemplateId(areaInfoQTO);

            //重建地区模板
            for (FreightAreaTemplateDTO freightAreaTemplateDTO
                    : freightTemplateDTO.getFreightAreaTemplateList()){

                FreightAreaTemplateDO freightAreaTemplateDO = new FreightAreaTemplateDO();
                BeanUtils.copyProperties(freightAreaTemplateDTO, freightAreaTemplateDO);
                freightAreaTemplateDO.setParentId(templateId);
                freightAreaTemplateDO.setSellerId(sellerId);
                freightAreaTemplateDO.setBizCode(bizCode);



                Long areaTemplateId = freightAreaTemplateDAO.addAreaTemplate(freightAreaTemplateDO);


                List<FreightTemplateAreaInfoDTO> areas = freightAreaTemplateDTO.getAreas();


                if(areas!=null&&areas.size()>0) {

                    for (FreightTemplateAreaInfoDTO area : areas) {

                        FreightTemplateAreaInfoDO areaInfoDO = new FreightTemplateAreaInfoDO();
                        BeanUtils.copyProperties(area,areaInfoDO);
                        areaInfoDO.setSellerId(sellerId);
                        areaInfoDO.setTemplateId(templateId);
                        areaInfoDO.setAreaTemplateId(areaTemplateId);
                        areaInfoDO.setBizCode(bizCode);
                        freightTemplateAreaInfoDAO.addAreaInfo(areaInfoDO);
                    }
                }else{
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"地区模板中的地址不能为空");
                }

            }
        }

        return true;
    }

    @Override
    public FreightTemplateDTO getFreightTemplate(Long templateId, Long sellerId) throws ItemException {


        //默认模板信息
        FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();

        FreightTemplateQTO freightTemplateQTO = new FreightTemplateQTO();
        freightTemplateQTO.setId(templateId);
        freightTemplateQTO.setSellerId(sellerId);
        FreightTemplateDO freightTemplateDO = freightTemplateDAO.getTemplate(freightTemplateQTO);

        BeanUtils.copyProperties(freightTemplateDO, freightTemplateDTO);


        //地区模板信息
        FreightAreaTemplateQTO freightAreaTemplateQTO = new FreightAreaTemplateQTO();
        freightAreaTemplateQTO.setParentId(templateId);
        freightAreaTemplateQTO.setSellerId(sellerId);

        List<FreightAreaTemplateDO> freightAreaTemplateDOs = freightAreaTemplateDAO.queryAreaTemplate(freightAreaTemplateQTO);

        if(freightAreaTemplateDOs!=null&&freightAreaTemplateDOs.size()>0){

            List<FreightAreaTemplateDTO> freightAreaTemplateDTOs = new ArrayList<FreightAreaTemplateDTO>();

            for(FreightAreaTemplateDO freightAreaTemplateDO : freightAreaTemplateDOs){
                FreightAreaTemplateDTO freightAreaTemplateDTO = new FreightAreaTemplateDTO();
                BeanUtils.copyProperties(freightAreaTemplateDO, freightAreaTemplateDTO);
                freightAreaTemplateDTOs.add(freightAreaTemplateDTO);
                Long areaTemplateId = freightAreaTemplateDTO.getId();


                //地区模板中的地址信息
                FreightTemplateAreaInfoQTO areaInfoQTO = new FreightTemplateAreaInfoQTO();
                areaInfoQTO.setSellerId(sellerId);
                areaInfoQTO.setTemplateId(templateId);
                areaInfoQTO.setAreaTemplateId(areaTemplateId);

                List<FreightTemplateAreaInfoDO> areaInfoDOs = freightTemplateAreaInfoDAO.queryAreaTemplate(areaInfoQTO);
                List<FreightTemplateAreaInfoDTO> areaInfoDTOs = new ArrayList<FreightTemplateAreaInfoDTO>();

                for(FreightTemplateAreaInfoDO areaInfoDO : areaInfoDOs){
                    FreightTemplateAreaInfoDTO areaInfoDTO = new FreightTemplateAreaInfoDTO();
                    BeanUtils.copyProperties(areaInfoDO,areaInfoDTO);
                    areaInfoDTOs.add(areaInfoDTO);
                }

                freightAreaTemplateDTO.setAreas(areaInfoDTOs);

            }

            freightTemplateDTO.setFreightAreaTemplateList(freightAreaTemplateDTOs);
        }

        return freightTemplateDTO;
    }

    @Override
    public List<FreightTemplateDTO> queryFeightTemplate(FreightTemplateQTO freightTemplateQTO) throws ItemException {

        List<FreightTemplateDTO> freightTemplateDTOs = new ArrayList<FreightTemplateDTO>();
        List<FreightTemplateDO> freightTemplateDOs = freightTemplateDAO.queryTemplate(freightTemplateQTO);

        for(FreightTemplateDO freightTemplateDO :freightTemplateDOs){

            Long sellerId = freightTemplateDO.getSellerId();
            Long templateId = freightTemplateDO.getId();

            //默认模板信息
            FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();
            BeanUtils.copyProperties(freightTemplateDO, freightTemplateDTO);
//

            //地区模板信息
            FreightAreaTemplateQTO freightAreaTemplateQTO = new FreightAreaTemplateQTO();
            freightAreaTemplateQTO.setParentId(templateId);
            freightAreaTemplateQTO.setSellerId(sellerId);

            List<FreightAreaTemplateDO> freightAreaTemplateDOs = freightAreaTemplateDAO.queryAreaTemplate(freightAreaTemplateQTO);

            if(freightAreaTemplateDOs!=null&&freightAreaTemplateDOs.size()>0){

                List<FreightAreaTemplateDTO> freightAreaTemplateDTOs = new ArrayList<FreightAreaTemplateDTO>();

                for(FreightAreaTemplateDO freightAreaTemplateDO : freightAreaTemplateDOs){
                    FreightAreaTemplateDTO freightAreaTemplateDTO = new FreightAreaTemplateDTO();
                    BeanUtils.copyProperties(freightAreaTemplateDO, freightAreaTemplateDTO);
                    freightAreaTemplateDTOs.add(freightAreaTemplateDTO);
                    Long areaTemplateId = freightAreaTemplateDTO.getId();


                    //地区模板中的地址信息
                    FreightTemplateAreaInfoQTO areaInfoQTO = new FreightTemplateAreaInfoQTO();
                    areaInfoQTO.setSellerId(sellerId);
                    areaInfoQTO.setTemplateId(templateId);
                    areaInfoQTO.setAreaTemplateId(areaTemplateId);

                    List<FreightTemplateAreaInfoDO> areaInfoDOs = freightTemplateAreaInfoDAO.queryAreaTemplate(areaInfoQTO);
                    List<FreightTemplateAreaInfoDTO> areaInfoDTOs = new ArrayList<FreightTemplateAreaInfoDTO>();

                    for(FreightTemplateAreaInfoDO areaInfoDO : areaInfoDOs){
                        FreightTemplateAreaInfoDTO areaInfoDTO = new FreightTemplateAreaInfoDTO();
                        BeanUtils.copyProperties(areaInfoDO,areaInfoDTO);
                        areaInfoDTOs.add(areaInfoDTO);
                    }

                    freightAreaTemplateDTO.setAreas(areaInfoDTOs);

                }


                freightTemplateDTO.setFreightAreaTemplateList(freightAreaTemplateDTOs);
            }

            freightTemplateDTOs.add(freightTemplateDTO);
        }


        return  freightTemplateDTOs;
    }

    @Override
    public Boolean deleteFreightTemplate(Long templateId, Long sellerId)  throws ItemException{

        //TODO 是否有商品在使用此模板
        ItemQTO itemQTO = new ItemQTO();
        itemQTO.setFreightTemplate(templateId);
        itemQTO.setSellerId(sellerId);

        Long count = itemDAO.countItem(itemQTO);

        if(count>0){
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID,"要删除的运费模板仍然有商品在使用，请检查后重试");
        }

        //删除模板下所有地区模板
        FreightAreaTemplateQTO templateQTO = new FreightAreaTemplateQTO();
        templateQTO.setParentId(templateId);
        templateQTO.setSellerId(sellerId);


        freightAreaTemplateDAO.deleteAreaTemplateByParentId(templateQTO);

        //删除模板下所有地区模板的地区信息
        FreightTemplateAreaInfoQTO areaInfoQTO = new FreightTemplateAreaInfoQTO();
        areaInfoQTO.setTemplateId(templateId);
        areaInfoQTO.setSellerId(sellerId);

        freightTemplateAreaInfoDAO.deleteAreaInfoByTemplateId(areaInfoQTO);


        //删除模板
        FreightTemplateQTO qto = new FreightTemplateQTO();
        qto.setId(templateId);
        qto.setSellerId(sellerId);
        freightTemplateDAO.deleteTemplate(qto);
        return true;
    }


}

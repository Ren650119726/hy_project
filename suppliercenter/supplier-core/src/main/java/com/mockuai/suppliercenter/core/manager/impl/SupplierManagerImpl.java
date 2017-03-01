package com.mockuai.suppliercenter.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.SupplierDTO;
import com.mockuai.suppliercenter.common.qto.SupplierQTO;
import com.mockuai.suppliercenter.core.dao.StoreDAO;
import com.mockuai.suppliercenter.core.dao.SupplierDAO;
import com.mockuai.suppliercenter.core.domain.StoreDO;
import com.mockuai.suppliercenter.core.domain.SupplierDO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierManager;
import com.mockuai.suppliercenter.core.util.SupplierUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierManagerImpl implements SupplierManager {
    private static final Logger log = LoggerFactory.getLogger(SupplierManagerImpl.class);

    @Resource
    private SupplierDAO supplierDAO;


    @Resource
    private StoreDAO storeDAO;


    @Resource
    private RegionClient regionClient;


    public SupplierDTO addSupplier(SupplierDTO supplierDTO) throws SupplierException {

        if (null == supplierDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierDTO is null");
        }

        String bizCode = supplierDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }


        SupplierUtil.supplierInfoIsLegal(supplierDTO);


        //将Dto 转换为D
        SupplierDO supplierDo = new SupplierDO();
        BeanUtils.copyProperties(supplierDTO, supplierDo);


        if (StringUtils.isNotBlank(supplierDTO.getName())) {

            Long i = supplierDAO.countSupplierByName(supplierDo);
            if (i > 0) {
                throw new SupplierException(ResponseCode.B_ADD_ERROR,
                        "该供应商名称已经存在");
            }
        }

        log.info("add supplier, bizCode = {}, name = {}, contacts = {},phone = {},  address = {}",
                supplierDo.getBizCode(), supplierDo.getName(), supplierDo.getContacts(), supplierDo.getPhone());
        Long supplierId = supplierDAO.addSuppler(supplierDo);
        supplierDTO = getSupplierById(supplierId);

        return supplierDTO;


    }


    public SupplierDTO getSupplierById(Long supplierId) throws SupplierException {

        if (null == supplierId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "supplierId is null");
        }

        if (supplierId <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "supplierId must greater than 0");
        }

        SupplierDO supplierDo = supplierDAO.getSupplierById(supplierId);

        SupplierDTO supplierDto = null;

        if (null != supplierDo) {
            supplierDto = new SupplierDTO();
            BeanUtils.copyProperties(supplierDo, supplierDto);
        }
        return supplierDto;
    }

    /**
     * 查询符合查询条件的供应商
     */
    public List<SupplierDTO> querySupplier(SupplierQTO supplierQto, String appkey)
            throws SupplierException {
        if (null == supplierQto) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierQto is null");
        }


        List<SupplierDO> supplierDos = supplierDAO.querySupplier(supplierQto);
        List<SupplierDTO> supplierDtos = new ArrayList<SupplierDTO>();
        Long i = 0L;
        SupplierDTO supplierDto = null;
        for (SupplierDO supplierDo1 : supplierDos) {
            supplierDto = new SupplierDTO();
            BeanUtils.copyProperties(supplierDo1, supplierDto);

            supplierDto = getDetailAddress(supplierDto, appkey);

            i = storeDAO.getSupplierTotalStore(supplierDo1.getId());


            supplierDto.setStoreNum(i == null ? 0L : i);

            supplierDtos.add(supplierDto);
        }

        return supplierDtos;
    }

    /**
     * 拼详细地址返回
     *
     * @param supplierDto
     * @param appkey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */

    public SupplierDTO getDetailAddress(SupplierDTO supplierDto, String appkey) throws SupplierException {
        String address = supplierDto.getAddress();
        String detailAddress = "";
        List<String> list = new ArrayList();
        list.add(supplierDto.getProvinceCode());
        list.add(supplierDto.getCityCode());
        list.add(supplierDto.getAreaCode());
        RegionQTO qto = new RegionQTO();
        qto.setRegionCodes(list);
//		 qto.setRegionCodes(storeDTO.getProvinceCode());

        Response<List<RegionDTO>> response = regionClient.queryRegion(qto, appkey);
        if (response.getModule() == null) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR, "查询区域信息返回为空");
        }
        List<RegionDTO> dtos = response.getModule();

        for (RegionDTO regionDTO : dtos) {
            if (regionDTO.getCode().equals(supplierDto.getCityCode())) {
                supplierDto.setCityName(regionDTO.getName());
            }
            if (regionDTO.getCode().equals(supplierDto.getProvinceCode())) {
                supplierDto.setProvinceName(regionDTO.getName());
            }
            if (regionDTO.getCode().equals(supplierDto.getAreaCode())) {
                supplierDto.setAreaName(regionDTO.getName());
            }

            detailAddress += regionDTO.getName();
        }
        detailAddress = detailAddress + supplierDto.getAddress();

        supplierDto.setDetailAddress(detailAddress);

        return supplierDto;

    }


    /**
     * 查询指定查询条件下的供应商总数
     */
    public Long getTotalCount(SupplierQTO supplierQto) throws SupplierException {
        if (null == supplierQto) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierQto is null");
        }
        Long totalCount = supplierDAO.getTotalCount(supplierQto);

        return totalCount;
    }

    /**
     * 修改供应商信息
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public int updateSupplier(SupplierDTO supplierDTO) throws SupplierException {
        if (null == supplierDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierDTO is null");
        }

        String bizCode = supplierDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }


        SupplierUtil.supplierInfoIsLegal(supplierDTO);

        //TODO 判断供应商是否被注册，不能出现相同的供应商名称

        //将Dto 转换为D
        SupplierDO supplierDo = new SupplierDO();
        BeanUtils.copyProperties(supplierDTO, supplierDo);

        if (StringUtils.isNotBlank(supplierDTO.getName())) {

            Long i = supplierDAO.countSupplierByName(supplierDo);
            if (i > 0) {
                throw new SupplierException(ResponseCode.B_ADD_ERROR,
                        "name is already register");
            }

        }


        log.info("update supplier, bizCode = {},  name = {}, contacts = {},phone = {},  address = {}",
                supplierDo.getBizCode(), supplierDo.getName(), supplierDo.getContacts(), supplierDo.getPhone(), supplierDo.getAddress());
        int result = supplierDAO.updateSupplier(supplierDo);
        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "update error");
        } else {

            StoreDO storeDO = new StoreDO();
            storeDO.setSupplierName(supplierDo.getName());
            storeDO.setSupplierId(supplierDo.getId());
            int i = storeDAO.updateStoreSupplierName(storeDO);


            return i;
        }


    }


}

package com.mockuai.suppliercenter.core.manager.impl;

import com.mockuai.deliverycenter.client.RegionClient;
import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.itemcenter.client.ItemSkuClient;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.core.dao.StoreDAO;
import com.mockuai.suppliercenter.core.dao.StoreItemSkuDAO;
import com.mockuai.suppliercenter.core.domain.StoreDO;
import com.mockuai.suppliercenter.core.domain.StoreItemSkuDO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.StoreManager;
import com.mockuai.suppliercenter.core.util.SupplierUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreManagerImpl implements StoreManager {

    private static final Logger log = LoggerFactory
            .getLogger(StoreManagerImpl.class);

    @Resource
    private StoreDAO storeDAO;

    @Resource
    private ItemSkuClient itemSkuClient;


    @Resource
    private RegionClient regionClient;

    @Resource
    private StoreItemSkuDAO storeItemSkuManagerDAO;

    public StoreDTO addStore(StoreDTO storeDTO) throws SupplierException {

        if (null == storeDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeDTO is null");
        }

        String bizCode = storeDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }

        SupplierUtil.storeInfoIsLegal(storeDTO);

        // TODO 判断供应商是否被注册，不能出现相同的供应商编码
        Long supplierId = storeDTO.getSupplierId();

        if (supplierId > 0) {
            if (countSupplierStore(supplierId) > 10000) {
                throw new SupplierException(ResponseCode.B_ADD_ERROR,
                        "supplierId have the nuber of store more than 1000");
            }
        } else {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    " supplierId param error ");
        }

        //TODO 判断供应商是否被注册，不能出现相同的供应商名称
        String name = storeDTO.getName();


        if (checkSupplierStoreName(name, storeDTO.getSupplierId()) > 0) {
            throw new SupplierException(ResponseCode.B_ADD_ERROR,
                    "仓库名已经注册");
        }
        // 将Dto 转换为D
        StoreDO storeDo = new StoreDO();
        BeanUtils.copyProperties(storeDTO, storeDo);
        log.info(
                "add store, bizCode = {}, supplierId = {}, name = {},type ={}  address = {}",
                storeDo.getBizCode(), storeDo.getSupplierId(),
                storeDo.getName(), storeDo.getType(), storeDo.getAddress());


        Long id = storeDAO.addStore(storeDo);
        storeDTO = getStoreById(id);

        return storeDTO;
    }

    public StoreDTO getStoreById(Long id) throws SupplierException {

        if (null == id) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "id is null");
        }

        if (id <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "id must greater than 0");
        }

        StoreDO storeDO = storeDAO.getStoreById(id);
        StoreDTO storeDTO = null;

        if (null != storeDO) {
            storeDTO = new StoreDTO();
            BeanUtils.copyProperties(storeDO, storeDTO);
        }
        return storeDTO;
    }

    public Long countSupplierStore(Long id) throws SupplierException {
        return storeDAO.getSupplierTotalStore(id);
    }

    /**
     * 查询符合查询条件的仓库
     */
    public List<StoreDTO> queryStore(StoreQTO storeQTO, String appKey)
            throws SupplierException {
        if (null == storeQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeQTO is null");
        }


        List<StoreDO> storeDOs = storeDAO.queryStore(storeQTO);
        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();
        StoreDTO storeDTO = null;
        Long i = 0L;
        for (StoreDO storeDO1 : storeDOs) {
            storeDTO = new StoreDTO();

            BeanUtils.copyProperties(storeDO1, storeDTO);
            storeDTO = getDetailAddress(storeDTO, appKey);
            i = storeItemSkuManagerDAO.getStoreTotelItemSku(storeDO1.getId());
            storeDTO.setItemSkuNum(i == null ? 0L : i);

            storeDTOs.add(storeDTO);
        }

        return storeDTOs;
    }

    /**
     * 拼详细地址返回
     *
     * @param supplierDto
     * @param appkey
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public StoreDTO getDetailAddress(StoreDTO storeDTO, String appkey) throws SupplierException {
        String address = storeDTO.getAddress();
        String detailAddress = "";
        List<String> list = new ArrayList();
        list.add(storeDTO.getProvinceCode());
        list.add(storeDTO.getCityCode());
        list.add(storeDTO.getAreaCode());
        RegionQTO qto = new RegionQTO();
        qto.setRegionCodes(list);
//		 qto.setRegionCodes(storeDTO.getProvinceCode());

        Response<List<RegionDTO>> response = regionClient.queryRegion(qto, appkey);
        if (response.getModule() == null) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR, "查询区域信息返回为空");
        }

        List<RegionDTO> dtos = response.getModule();

        for (RegionDTO regionDTO : dtos) {

            if (regionDTO.getCode().equals(storeDTO.getCityCode())) {
                storeDTO.setCityName(regionDTO.getName());
            }
            if (regionDTO.getCode().equals(storeDTO.getProvinceCode())) {
                storeDTO.setProvinceName(regionDTO.getName());
            }
            if (regionDTO.getCode().equals(storeDTO.getAreaCode())) {
                storeDTO.setAreaName(regionDTO.getName());
            }


            detailAddress += regionDTO.getName();
        }
        detailAddress = detailAddress + storeDTO.getAddress();
        storeDTO.setDetailAddress(detailAddress);
        return storeDTO;

    }

    /**
     * 查询指定查询条件下的仓库总数
     */
    public Long getTotalCount(StoreQTO storeQTO) throws SupplierException {
        if (null == storeQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeQTO is null");
        }
        Long totalCount = storeDAO.getTotalCount(storeQTO);

        return totalCount;
    }

    public List<StoreDTO> queryStoreForOrder(StoreQTO storeQTO, String appKey)
            throws SupplierException {
        if (null == storeQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeQTO is null");
        }


        List<StoreDO> storeDOs = storeDAO.queryStoreForOrder(storeQTO);
        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();
        StoreDTO storeDTO = null;

        Long i = 0L;
        for (StoreDO storeDO1 : storeDOs) {
            storeDTO = new StoreDTO();
            BeanUtils.copyProperties(storeDO1, storeDTO);
            storeDTO = getDetailAddress(storeDTO, appKey);
            i = storeItemSkuManagerDAO.getStoreTotelItemSku(storeDO1.getId());
            storeDTO.setItemSkuNum(i == null ? 0L : i);
            storeDTOs.add(storeDTO);
        }

        return storeDTOs;
    }

    public Long getTotalCountForOrder(StoreQTO storeQTO)
            throws SupplierException {
        if (null == storeQTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeQTO is null");
        }
        Long totalCount = storeDAO.getTotalCountForOrder(storeQTO);

        return totalCount;
    }


    /* (non-Javadoc)
    * @see com.mockuai.suppliercenter.core.manager.StoreManager#updateStore(com.mockuai.suppliercenter.common.dto.StoreDTO, java.lang.String)
    */
    public int updateStoreSupplierName(String supplierName, Long supplierId) throws SupplierException {
        if (null == supplierName) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "供应商名称不能为空");
        }
        if (null == supplierId) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "供应商id不能为空");
        }

        StoreDO storeDO = new StoreDO();

        storeDO.setSupplierId(supplierId);
        storeDO.setSupplierName(supplierName);

        int result = storeDAO.updateStoreSupplierName(storeDO);

        return result;

    }

    /**
     * 修改仓库信息
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public int updateStore(StoreDTO storeDTO, String appKey) throws SupplierException {


        if (null == storeDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeDTO is null");
        }

        String bizCode = storeDTO.getBizCode();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }


        SupplierUtil.storeInfoIsLegal(storeDTO);

        //TODO 判断供应商是否被注册，不能出现相同的供应商名称
        String name = storeDTO.getName();


        if (checkSupplierStoreName(name, storeDTO.getSupplierId()) > 1) {
            throw new SupplierException(ResponseCode.B_ADD_ERROR,
                    "仓库名已经注册");
        }

        //将Dto 转换为D
        StoreDO storeDO = new StoreDO();
        BeanUtils.copyProperties(storeDTO, storeDO);
        log.info(
                "edit  store, bizCode = {}, supplierId = {}, name = {},type ={}  address = {}",
                storeDO.getBizCode(), storeDO.getSupplierId(),
                storeDO.getName(), storeDO.getType(), storeDO.getAddress());

        int result = storeDAO.updateStore(storeDO);
        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "update error");
        }
        return result;

    }

    public Long checkSupplierStoreName(String name, Long id) throws SupplierException {
        return storeDAO.checkSupplierStoreName(name, id);
    }

    /**
     * 仓库禁用
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public int forbiddenStore(StoreDTO storeDTO, String appKey) throws SupplierException {

        if (null == storeDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeDTO is null");
        }

        String bizCode = storeDTO.getBizCode();
        Long id = storeDTO.getId();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }
        if (id <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR, "id is error");
        }
        //获取禁用仓库下的商品skulist
        List<StoreItemSkuDO> list = storeItemSkuManagerDAO.getStoreItemSkuListByStroeId(id);


        int result = storeDAO.forbiddenStore(storeDTO.getId());

        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "forbidden Store error");
        }

        StoreQTO storeQTO = new StoreQTO();
        //状态正常的仓库
        storeQTO.setStatus(1);
        List<Long> idList = storeDAO.getStoreIdList(storeQTO);

        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        if (idList != null && !idList.isEmpty()) {
            storeItemSkuQTO.setStoreIdList(idList);
        }
        for (StoreItemSkuDO storeItemSkuDO : list) {

            //统计除了该storeId下的skuId库存，然后调用商品接口置总库存
            storeItemSkuQTO.setStoreId(storeDTO.getId());
            storeItemSkuQTO.setItemSkuId(storeItemSkuDO.getItemSkuId());

            Long cont = storeItemSkuManagerDAO.getItemSkuIdTotleNumExpStoreId(storeItemSkuQTO);
            itemSkuClient.setItemSkuStock(storeItemSkuDO.getItemSkuId(), storeItemSkuDO.getSellerId(), cont == null ? 0L : cont, appKey);

        }
        return result;

    }


    /**
     * 仓库启用
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public int enableStore(StoreDTO storeDTO, String appKey) throws SupplierException {


        if (null == storeDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeDTO is null");
        }

        String bizCode = storeDTO.getBizCode();
        Long id = storeDTO.getId();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }
        if (id <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR, "id is error");
        }


        int result = storeDAO.enableStore(storeDTO.getId());

        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "enable Store error");
        }
        //获取启用仓库下的商品skulist
        List<StoreItemSkuDO> list = storeItemSkuManagerDAO.getStoreItemSkuListByStroeId(id);

        StoreQTO storeQTO = new StoreQTO();
        //状态正常的仓库
        storeQTO.setStatus(1);
        List<Long> idList = storeDAO.getStoreIdList(storeQTO);

        StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        if (idList != null && !idList.isEmpty()) {
            storeItemSkuQTO.setStoreIdList(idList);
        }
        for (StoreItemSkuDO storeItemSkuDO : list) {

            //统计未禁用的skuId总库存，然后调用商品接口置总库存
            storeItemSkuQTO.setItemSkuId(storeItemSkuDO.getItemSkuId());
            Long cont = storeItemSkuManagerDAO.getItemSkuIdTotleNum(storeItemSkuQTO);
            itemSkuClient.setItemSkuStock(storeItemSkuDO.getItemSkuId(), storeItemSkuDO.getSellerId(), cont == null ? 0L : cont, appKey);

        }
        return result;


    }

    /**
     * 仓库删除
     *
     * @param supplierDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public int DeleteStore(StoreDTO storeDTO, String appKey)
            throws SupplierException {

        if (null == storeDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "storeDTO is null");
        }

        String bizCode = storeDTO.getBizCode();
        Long id = storeDTO.getId();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }
        if (id <= 0) {
            throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                    "id is error");
        }
        // 获取禁用仓库下的商品skulist
        Long i = storeItemSkuManagerDAO.getStoreTotelItemSku(id);
        if (i > 0) {
            throw new SupplierException(ResponseCode.B_DELETE_ERROR,
                    " 删除失败 ,仓库有商品库存");
        }

        int result = storeDAO.deleteStore(id);

        if (result != 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                    "delete Store error");
        }
        return result;

    }


}

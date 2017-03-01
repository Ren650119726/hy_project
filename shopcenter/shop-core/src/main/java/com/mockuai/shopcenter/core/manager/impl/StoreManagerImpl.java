package com.mockuai.shopcenter.core.manager.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mockuai.shopcenter.constant.PropertyConsts;
import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopPropertyDAO;
import com.mockuai.shopcenter.core.dao.StoreDAO;
import com.mockuai.shopcenter.core.dao.StorePropertyDAO;
import com.mockuai.shopcenter.core.domain.ShopPropertyDO;
import com.mockuai.shopcenter.core.domain.StoreDO;
import com.mockuai.shopcenter.core.domain.StorePropertyDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.StoreManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.domain.dto.StoreDTO;
import com.mockuai.shopcenter.domain.qto.StoreQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yindingyu on 15/11/3.
 */
@Service
public class StoreManagerImpl implements StoreManager {

    private static final double MAX_LONGITUDE = 180;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LATITUDE = -90;

    @Resource
    private StoreDAO storeDAO;

    @Resource
    private StorePropertyDAO storePropertyDAO;

    @Resource
    private ShopPropertyDAO shopPropertyDAO;


    @Override
    public Long addStore(StoreDTO storeDTO) throws ShopException {

        if (storeDTO.getOwnerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "ownerId不能为空");
        }

        verifyStoreDTO(storeDTO);

        StoreQTO query = new StoreQTO();
        query.setStoreNumber(storeDTO.getStoreNumber());
        query.setBizCode(storeDTO.getBizCode());
        query.setSellerId(storeDTO.getSellerId());

        List<StoreDO> storeDOs = storeDAO.queryStore(query);

        if (storeDOs != null && storeDOs.size() > 0) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "门店编号不能重复");
        }

        StoreDO storeDO = new StoreDO();
        BeanUtils.copyProperties(storeDTO, storeDO);

        return storeDAO.addStore(storeDO);
    }

    @Override
    public Long updateStore(StoreDTO storeDTO) throws ShopException {

        if (storeDTO.getId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "id不能为空");
        }

        verifyStoreDTO(storeDTO);

        StoreDO storeDO = new StoreDO();
        BeanUtils.copyProperties(storeDTO, storeDO);

        return storeDAO.UpdateStore(storeDO);

    }

    @Override
    public StoreDTO getStore(Long id, Long seller_id, String bizCode) throws ShopException {

        StoreDO query = new StoreDO();
        query.setId(id);
        query.setSellerId(seller_id);
        query.setBizCode(bizCode);

        StoreDO storeDO = storeDAO.getStore(query);

        if (storeDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "没有查询到对应的记录");
        }

        StoreDTO storeDTO = new StoreDTO();
        BeanUtils.copyProperties(storeDO, storeDTO);

        return storeDTO;

    }

    @Override
    public Long deleteStore(Long id, Long seller_id, String bizCode) throws ShopException {
        StoreDO query = new StoreDO();
        query.setId(id);
        query.setSellerId(seller_id);
        query.setBizCode(bizCode);

        Long lineNums = storeDAO.deleteStore(query);


        if (lineNums < 1) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "没有需要删除的记录");
        }

        return lineNums;
    }

    @Override
    public List<StoreDTO> queryStore(StoreQTO storeQTO) throws ShopException {

        if (Strings.isNullOrEmpty(storeQTO.getStoreName())) {
            storeQTO.setStoreName(null);
        }

        if (Strings.isNullOrEmpty(storeQTO.getStoreNumber())) {
            storeQTO.setStoreNumber(null);
        }

        if (storeQTO.getSupportRecovery() != null && storeQTO.getSupportRecovery() == 1) {

        } else if (storeQTO.getSupportRepair() != null && storeQTO.getSupportRepair() == 1) {

        }

        List<StoreDO> storeDOs = storeDAO.queryStore(storeQTO);


        if (storeQTO.getSupportDelivery() != null &&
                !Strings.isNullOrEmpty(storeQTO.getLatitude()) && !Strings.isNullOrEmpty(storeQTO.getLongitude())) {

            double longitude = 0d;
            double latitude = 0d;

            try {

                longitude = Double.parseDouble(storeQTO.getLongitude());

                if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE) {
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
                }

            } catch (Exception e) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
            }

            try {
                latitude = Double.parseDouble(storeQTO.getLatitude());

                if (latitude > MAX_LATITUDE || latitude < MIN_LATITUDE) {
                    throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "latitude的值非法");
                }

            } catch (Exception e) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
            }

            //1角度=？弧度
            final double RAD = Math.PI / 180.0d;

            //地球半径，单位米
            final double EARTH_RADIUS = 6378.137d;

            final double latitude1 = latitude;

            final double longitude1 = longitude;

            Iterable<StoreDO> storeDOIterable = Iterables.filter(storeDOs, new Predicate<StoreDO>() {

                @Override
                public boolean apply(StoreDO storeDO) {

                    double longitude2 = Double.parseDouble(storeDO.getLongitude());
                    double latitude2 = Double.parseDouble(storeDO.getLatitude());

                    double radLat1 = latitude1 * RAD;
                    double radLat2 = latitude2 * RAD;

                    double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) +
                            Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((longitude1 - longitude2) * RAD / 2), 2))));

                    return distance <= storeDO.getDeliveryRange();
                }

            });

            List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>(storeQTO.getPageSize());

            for (StoreDO storeDO : storeDOIterable) {
                StoreDTO storeDTO = new StoreDTO();
                BeanUtils.copyProperties(storeDO, storeDTO);
                storeDTOs.add(storeDTO);
            }

            return storeDTOs;

        }

        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>(storeQTO.getPageSize());

        for (StoreDO storeDO : storeDOs) {
            StoreDTO storeDTO = new StoreDTO();
            BeanUtils.copyProperties(storeDO, storeDTO);
            storeDTOs.add(storeDTO);
        }

        return storeDTOs;
    }

    @Override
    public StoreDTO getStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException {

        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setBizCode(bizCode);
        storeQTO.setSellerId(sellerId);
        storeQTO.setSupportDelivery(1);

        List<StoreDO> storeDOs = storeDAO.queryStore(storeQTO);

        StoreDO storeDO = getNearestStore(storeDOs, longitude, latitude);

        if (storeDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "没有支持配送的门店");
        }

        StoreDTO storeDTO = new StoreDTO();

        BeanUtils.copyProperties(storeDO, storeDTO);
        return storeDTO;

    }

    @Override
    public StoreDTO getStoreByOwnerId(Long ownerId, String bizCode) throws ShopException {
        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setBizCode(bizCode);
        storeQTO.setOwnerId(ownerId);

        List<StoreDO> storeDOs = storeDAO.queryStore(storeQTO);


        if (storeDOs != null && storeDOs.size() > 0) {
            StoreDTO storeDTO = new StoreDTO();

            BeanUtils.copyProperties(storeDOs.get(0), storeDTO);
            return storeDTO;
        }


        return null;
    }

    @Override
    public StoreDTO getRecoveryStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException {


        StoreQTO storeQTO = new StoreQTO();

        ShopPropertyDO query = new ShopPropertyDO();

        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setpKey(PropertyConsts.SUPPORT_HOME_RECOVERY);

        ShopPropertyDO shopPropertyDO = shopPropertyDAO.getShopProperty(query);

        if (shopPropertyDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "该店铺还没有配置是否支持上门回收");
        } else if (shopPropertyDO.getValue().equals("2")) {
            StorePropertyDO query1 = new StorePropertyDO();
            query1.setSellerId(sellerId);
            query1.setBizCode(bizCode);
            query1.setpKey(PropertyConsts.SUPPORT_HOME_RECOVERY);
            query1.setValue("1");

            List<StorePropertyDO> storePropertyDOList = storePropertyDAO.queryStoreProperties(query1);


            if (storePropertyDOList == null || storePropertyDOList.size() < 1) {
                return null;
            }

            List<Long> storeIds = Lists.newArrayList();

            for (StorePropertyDO storePropertyDO : storePropertyDOList) {
                storeIds.add(storePropertyDO.getStoreId());
            }
            storeQTO.setIdList(storeIds);
        }

        storeQTO.setSellerId(sellerId);
        storeQTO.setBizCode(bizCode);

        List<StoreDO> storeDOList = storeDAO.queryStore(storeQTO);

        ShopPropertyDO query1 = new ShopPropertyDO();
        query1.setBizCode(bizCode);
        query1.setSellerId(sellerId);
        query1.setpKey(PropertyConsts.RECOVERY_RANGE);

        ShopPropertyDO shopPropertyDOx = shopPropertyDAO.getShopProperty(query1);

        if (shopPropertyDOx == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "还未设置回收上门服务范围");
        }

        StoreDO storeDO = getNearestStore(storeDOList, longitude, latitude, Double.valueOf(shopPropertyDOx.getValue()));

        if (storeDO == null) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        BeanUtils.copyProperties(storeDO, storeDTO);
        return storeDTO;

    }

    @Override
    public StoreDTO getRepairStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException {


        StoreQTO storeQTO = new StoreQTO();

        ShopPropertyDO query = new ShopPropertyDO();

        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setpKey(PropertyConsts.SUPPORT_HOME_REPAIR);

        ShopPropertyDO shopPropertyDO = shopPropertyDAO.getShopProperty(query);

        if (shopPropertyDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "该店铺还没有配置是否支持上门回收");
        } else if (shopPropertyDO.getValue().equals("2")) {
            StorePropertyDO query1 = new StorePropertyDO();
            query1.setSellerId(sellerId);
            query1.setBizCode(bizCode);
            query1.setpKey(PropertyConsts.SUPPORT_HOME_REPAIR);
            query1.setValue("1");

            List<StorePropertyDO> storePropertyDOList = storePropertyDAO.queryStoreProperties(query1);


            if (storePropertyDOList == null || storePropertyDOList.size() < 1) {
                return null;
            }

            List<Long> storeIds = Lists.newArrayList();

            for (StorePropertyDO storePropertyDO : storePropertyDOList) {
                storeIds.add(storePropertyDO.getStoreId());
            }
            storeQTO.setIdList(storeIds);
        }

        storeQTO.setSellerId(sellerId);
        storeQTO.setBizCode(bizCode);

        List<StoreDO> storeDOList = storeDAO.queryStore(storeQTO);

        ShopPropertyDO query1 = new ShopPropertyDO();
        query1.setBizCode(bizCode);
        query1.setSellerId(sellerId);
        query1.setpKey(PropertyConsts.REPAIR_RANGE);


        ShopPropertyDO shopPropertyDOx = shopPropertyDAO.getShopProperty(query1);

        if (shopPropertyDOx == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "还未设置维修上门服务范围");
        }

        StoreDO storeDO = getNearestStore(storeDOList, longitude, latitude, Double.valueOf(shopPropertyDOx.getValue()));

        if (storeDO == null) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        BeanUtils.copyProperties(storeDO, storeDTO);
        return storeDTO;
    }

    @Override
    public List<StoreDTO> queryStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException {
        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setBizCode(bizCode);
        storeQTO.setSellerId(sellerId);
        storeQTO.setSupportDelivery(1);

        List<StoreDO> storeDOs = storeDAO.queryStore(storeQTO);


        //1角度=？弧度
        final double RAD = Math.PI / 180.0d;

        //地球半径，单位米
        final double EARTH_RADIUS = 6378.137d;

        final double latitude1 = latitude;

        final double longitude1 = longitude;

        //在配送范围的门店
        Iterable<StoreDO> storeDOIterable = Iterables.filter(storeDOs, new Predicate<StoreDO>() {

            @Override
            public boolean apply(StoreDO storeDO) {


                double longitude2 = Double.parseDouble(storeDO.getLongitude());
                double latitude2 = Double.parseDouble(storeDO.getLatitude());

                double radLat1 = latitude1 * RAD;
                double radLat2 = latitude2 * RAD;

                double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) +
                        Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((longitude1 - longitude2) * RAD / 2), 2))));

                return distance <= storeDO.getDeliveryRange();
            }

        });

        List<StoreDTO> storeDTOList = Lists.newArrayList();

        for (StoreDO storeDO : storeDOIterable) {

            StoreDTO storeDTO = new StoreDTO();
            BeanUtils.copyProperties(storeDO, storeDTO);
            storeDTOList.add(storeDTO);
        }

        return storeDTOList;


    }

    @Override
    public List<StoreDTO> queryRepairStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException {
        StoreQTO storeQTO = new StoreQTO();

        ShopPropertyDO query = new ShopPropertyDO();

        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setpKey(PropertyConsts.SUPPORT_HOME_REPAIR);

        ShopPropertyDO shopPropertyDO = shopPropertyDAO.getShopProperty(query);

        if (shopPropertyDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "该店铺还没有配置是否支持上门回收");
        } else if (shopPropertyDO.getValue().equals("2")) {
            StorePropertyDO query1 = new StorePropertyDO();
            query1.setSellerId(sellerId);
            query1.setBizCode(bizCode);
            query1.setpKey(PropertyConsts.SUPPORT_HOME_REPAIR);
            query1.setValue("1");

            List<StorePropertyDO> storePropertyDOList = storePropertyDAO.queryStoreProperties(query1);


            if (storePropertyDOList == null || storePropertyDOList.size() < 1) {
                return Collections.EMPTY_LIST;
            }

            List<Long> storeIds = Lists.newArrayList();

            for (StorePropertyDO storePropertyDO : storePropertyDOList) {
                storeIds.add(storePropertyDO.getStoreId());
            }
            storeQTO.setIdList(storeIds);
        }

        storeQTO.setSellerId(sellerId);
        storeQTO.setBizCode(bizCode);

        List<StoreDO> storeDOList = storeDAO.queryStore(storeQTO);

        ShopPropertyDO query1 = new ShopPropertyDO();
        query1.setBizCode(bizCode);
        query1.setSellerId(sellerId);
        query1.setpKey(PropertyConsts.REPAIR_RANGE);

        final ShopPropertyDO shopPropertyDOx = shopPropertyDAO.getShopProperty(query1);

        if (shopPropertyDOx == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "还未设置回收上门服务范围");
        }

        final long range = Long.parseLong(shopPropertyDOx.getValue());

        //1角度=？弧度
        final double RAD = Math.PI / 180.0d;

        //地球半径，单位米
        final double EARTH_RADIUS = 6378.137d;

        final double latitude1 = latitude;

        final double longitude1 = longitude;

        //在配送范围的门店
        Iterable<StoreDO> storeDOIterable = Iterables.filter(storeDOList, new Predicate<StoreDO>() {

            @Override
            public boolean apply(StoreDO storeDO) {


                double longitude2 = Double.parseDouble(storeDO.getLongitude());
                double latitude2 = Double.parseDouble(storeDO.getLatitude());

                double radLat1 = latitude1 * RAD;
                double radLat2 = latitude2 * RAD;

                double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) +
                        Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((longitude1 - longitude2) * RAD / 2), 2))));

                return distance <= range;
            }

        });

        List<StoreDTO> storeDTOList = Lists.newArrayList();

        for (StoreDO storeDO : storeDOIterable) {

            StoreDTO storeDTO = new StoreDTO();
            BeanUtils.copyProperties(storeDO, storeDTO);
            storeDTOList.add(storeDTO);
        }

        return storeDTOList;
    }

    @Override
    public List<StoreDTO> queryRecoveryStoreByCoordinates(Long sellerId, double longitude, double latitude, String bizCode) throws ShopException {
        StoreQTO storeQTO = new StoreQTO();

        ShopPropertyDO query = new ShopPropertyDO();

        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        query.setpKey(PropertyConsts.SUPPORT_HOME_RECOVERY);

        ShopPropertyDO shopPropertyDO = shopPropertyDAO.getShopProperty(query);

        if (shopPropertyDO == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "该店铺还没有配置是否支持上门回收");
        } else if (shopPropertyDO.getValue().equals("2")) {
            StorePropertyDO query1 = new StorePropertyDO();
            query1.setSellerId(sellerId);
            query1.setBizCode(bizCode);
            query1.setpKey(PropertyConsts.SUPPORT_HOME_RECOVERY);
            query1.setValue("1");

            List<StorePropertyDO> storePropertyDOList = storePropertyDAO.queryStoreProperties(query1);


            if (storePropertyDOList == null || storePropertyDOList.size() < 1) {
                return Collections.EMPTY_LIST;
            }

            List<Long> storeIds = Lists.newArrayList();

            for (StorePropertyDO storePropertyDO : storePropertyDOList) {
                storeIds.add(storePropertyDO.getStoreId());
            }
            storeQTO.setIdList(storeIds);
        }

        storeQTO.setSellerId(sellerId);
        storeQTO.setBizCode(bizCode);

        List<StoreDO> storeDOList = storeDAO.queryStore(storeQTO);

        ShopPropertyDO query1 = new ShopPropertyDO();
        query1.setBizCode(bizCode);
        query1.setSellerId(sellerId);
        query1.setpKey(PropertyConsts.RECOVERY_RANGE);

        final ShopPropertyDO shopPropertyDOx = shopPropertyDAO.getShopProperty(query1);

        if (shopPropertyDOx == null) {
            throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_IS_EXIST, "还未设置回收上门服务范围");
        }

        final long range = Long.parseLong(shopPropertyDOx.getValue());

        //1角度=？弧度
        final double RAD = Math.PI / 180.0d;

        //地球半径，单位米
        final double EARTH_RADIUS = 6378.137d;

        final double latitude1 = latitude;

        final double longitude1 = longitude;

        //在配送范围的门店
        Iterable<StoreDO> storeDOIterable = Iterables.filter(storeDOList, new Predicate<StoreDO>() {

            @Override
            public boolean apply(StoreDO storeDO) {


                double longitude2 = Double.parseDouble(storeDO.getLongitude());
                double latitude2 = Double.parseDouble(storeDO.getLatitude());

                double radLat1 = latitude1 * RAD;
                double radLat2 = latitude2 * RAD;

                double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) +
                        Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((longitude1 - longitude2) * RAD / 2), 2))));

                return distance <= range;
            }

        });

        List<StoreDTO> storeDTOList = Lists.newArrayList();

        for (StoreDO storeDO : storeDOIterable) {

            StoreDTO storeDTO = new StoreDTO();
            BeanUtils.copyProperties(storeDO, storeDTO);
            storeDTOList.add(storeDTO);
        }

        return storeDTOList;
    }

    public StoreDO getNearestStore(List<StoreDO> storeDOs, double longitude1, double latitude1) {

        StoreDO nearest = null;

        double nearestDistance = Double.MAX_VALUE;

        for (StoreDO storeDO : storeDOs) {

            //1角度=？弧度
            final double RAD = Math.PI / 180.0d;

            //地球半径，单位米
            final double EARTH_RADIUS = 6378.137d;

            double longitude2 = Double.parseDouble(storeDO.getLongitude());
            double latitude2 = Double.parseDouble(storeDO.getLatitude());

            double radLat1 = latitude1 * RAD;
            double radLat2 = latitude2 * RAD;

            double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) +
                    Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((longitude1 - longitude2) * RAD / 2), 2))));

            if (storeDO.getDeliveryRange() != null) {
                double range = storeDO.getDeliveryRange();

                if (distance < range) {
                    //在配送范围

                    if (distance < nearestDistance) {
                        nearest = storeDO;
                        nearestDistance = distance;
                    }
                }
            }

        }

        return nearest;
    }

    public StoreDO getNearestStore(List<StoreDO> storeDOs, double longitude1, double latitude1, double range) {

        StoreDO nearest = null;

        double nearestDistance = Double.MAX_VALUE;

        for (StoreDO storeDO : storeDOs) {

            //1角度=？弧度
            final double RAD = Math.PI / 180.0d;

            //地球半径，单位米
            final double EARTH_RADIUS = 6378.137d;

            double longitude2 = Double.parseDouble(storeDO.getLongitude());
            double latitude2 = Double.parseDouble(storeDO.getLatitude());

            double radLat1 = latitude1 * RAD;
            double radLat2 = latitude2 * RAD;

            double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2) / 2), 2) +
                    Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((longitude1 - longitude2) * RAD / 2), 2))));

            if (distance < range) {
                //在配送范围

                if (distance < nearestDistance) {
                    nearest = storeDO;
                    nearestDistance = distance;
                }
            }


        }

        return nearest;
    }

//    /**
//     * 计算某坐标点是否在门店配送范围内
//     * @param storeDO
//     * @param longitude
//     * @param latitude
//     * @return
//     */
//    private boolean inDeliveryRange(StoreDO storeDO, Integer longitude, Integer latitude) {
//
//        //1角度=？弧度
//        final double RAD = Math.PI / 180.0d;
//
//        //地球半径，单位米
//        final double EARTH_RADIUS = 6378137.0d;
//
//        double longitude1 = longitude * 1.0d / (10 ^ 6);
//        double latitude1 = latitude * 1.0d / (10 ^ 6);
//
//        double longitude2 = storeDO.getLongitude() * 1.0d / (10 ^ 6);
//        double latitude2 = storeDO.getLatitude() * 1.0d / (10 ^ 6);
//
//        double radLat1 = latitude1 * RAD;
//        double radLat2 = latitude2 * RAD;
//
//        double distance = Math.abs(2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.pow(Math.sin((radLat1 - radLat2)/2),2)+
//                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin((longitude1 - latitude2)*RAD/2),2))));
//
//        double range = storeDO.getDeliveryRange();
//
//        if(distance<=range){
//            return true;
//        }else{
//            return false;
//        }
//
//    }

    private void verifyStoreDTO(StoreDTO storeDTO) throws ShopException {

        if (storeDTO.getAddress() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "address不能为空");
        }

        if (storeDTO.getSellerId() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId不能为空");
        }


        if (storeDTO.getLongitude() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "longitude不能为空");
        }

        try {

            double longitude = Double.parseDouble(storeDTO.getLongitude());

            if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
            }

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
        }


        if (storeDTO.getLatitude() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "latitude不能为空");
        }

        try {
            double latitude = Double.parseDouble(storeDTO.getLatitude());

            if (latitude > MAX_LATITUDE || latitude < MIN_LATITUDE) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "latitude的值非法");
            }

        } catch (Exception e) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "longitude的值非法");
        }

        if (storeDTO.getSupportPickUp() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supportPickUp不能为空");
        }

        if (storeDTO.getSupportDelivery() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supportDelivery不能为空");
        } else if (storeDTO.getSupportDelivery() == 1) {
            if (storeDTO.getDeliveryRange() == null) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "deliveryRange不能为空");
            } else if (storeDTO.getDeliveryRange() <= 0) {
                throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "deliveryRange不能为零或负数");
            }
        }


        if (storeDTO.getStoreName() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "storeName不能为空");
        }

        if (storeDTO.getStoreImage() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "storeImage不能为空");
        }

        if (storeDTO.getCountryCode() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "countryCode不能为空");
        }

        if (storeDTO.getProvinceCode() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "provinceCode不能为空");
        }

        if (storeDTO.getCityCode() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "cityCode不能为空");
        }

        if (storeDTO.getAreaCode() == null) {
            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "areaCode不能为空");
        }

//        if(storeDTO.getTownCode()==null){
//            throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING,"townCode不能为空");
//        }
    }


}

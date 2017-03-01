package com.mockuai.suppliercenter.core.manager.impl;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mockuai.suppliercenter.common.constant.ResponseCode;
import com.mockuai.suppliercenter.common.constant.SupplierOrderStockStatusEnum;
import com.mockuai.suppliercenter.common.dto.OrderStockDTO;
import com.mockuai.suppliercenter.common.dto.StoreItemSkuDTO;
import com.mockuai.suppliercenter.common.dto.SupplierNewOrderStockDTO;
import com.mockuai.suppliercenter.common.dto.SupplierOrderStockDTO;
import com.mockuai.suppliercenter.common.qto.StoreItemSkuQTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;
import com.mockuai.suppliercenter.common.qto.SupplierOrderStockQTO;
import com.mockuai.suppliercenter.core.dao.StoreDAO;
import com.mockuai.suppliercenter.core.dao.StoreItemSkuDAO;
import com.mockuai.suppliercenter.core.dao.SupplierOrderStockDAO;
import com.mockuai.suppliercenter.core.domain.StoreDO;
import com.mockuai.suppliercenter.core.domain.StoreItemSkuDO;
import com.mockuai.suppliercenter.core.domain.SupplierOrderStockDO;
import com.mockuai.suppliercenter.core.exception.SupplierException;
import com.mockuai.suppliercenter.core.manager.SupplierOrderStockManager;
import com.mockuai.suppliercenter.core.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SupplierOrderStockManagerImpl implements SupplierOrderStockManager {
    private static final Logger log = LoggerFactory.getLogger(SupplierOrderStockManagerImpl.class);

    @Resource
    private StoreItemSkuDAO storeItemSkuManagerDAO;

    @Resource
    private StoreDAO storeDAO;

    @Resource
    private SupplierOrderStockDAO supplierOrderStockDAO;
    
    @Resource
    private SupplierOrderStockManager supplierOrderStockManager;


    /**
     * 根据订单编号orderSn查询订单sku 仓库关系返回给管易erp使用
     *
     * @param orderSn
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public SupplierOrderStockDTO getOrderStoreSkuByOrderSn(String orderSn, String bizCode) throws SupplierException {

        if (null == orderSn) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "getOrderStoreSkuByOrderSn ,orderSn is null");
        }

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "bizCode is null");
        }
        List<SupplierOrderStockDO> supplierOrderStockDOs = supplierOrderStockDAO.getOrderSkuByOrderSn(orderSn);

        List<SupplierOrderStockDTO.OrderSku> list = new ArrayList<SupplierOrderStockDTO.OrderSku>();

        SupplierOrderStockDTO.OrderSku orderSku;

        for (SupplierOrderStockDO supplierOrderStockDO : supplierOrderStockDOs) {
            orderSku = new SupplierOrderStockDTO.OrderSku();

            orderSku.setStoreId(supplierOrderStockDO.getStoreId());
            orderSku.setSkuId(supplierOrderStockDO.getItemSkuId());
            orderSku.setNumber(supplierOrderStockDO.getNum().intValue());

            list.add(orderSku);
        }

        SupplierOrderStockDTO supplierOrderStockDTO = new SupplierOrderStockDTO();
        supplierOrderStockDTO.setOrderSn(orderSn);
        supplierOrderStockDTO.setBizCode(bizCode);
        if (list.size() > 0)
            supplierOrderStockDTO.setOrderSkuList(list);

        return supplierOrderStockDTO;

    }


    /**
     * 根据订单产生锁定库存
     *
     * @param orderStockDTO
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public OrderStockDTO lockSkuOrderNum(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException {

        if (null == bizCode || "".equals(bizCode)) {
            bizCode = "hanshu";
        }

        List<OrderStockDTO.OrderSku> orderSkuList = orderStockDTO.getOrderSkuList();

        // 查出所有有效的仓库
        StoreQTO storeQTO = new StoreQTO();
        storeQTO.setStatus(1);
        List<StoreDO> stores = storeDAO.getStoreList(storeQTO);

        SupplierOrderStockDO supplierOrderStockDO;

        List<OrderStockDTO.OrderSku> orderSkuArrayList = Lists.newArrayList();

        List<StoreItemSkuDO> storeItemSkuDOList = Lists.newArrayList();

        for (OrderStockDTO.OrderSku orderSkus : orderSkuList) {

            if (orderSkus.getNumber() < 0) {
                throw new SupplierException(ResponseCode.P_PARAM_ERROR,
                        "下单数量不能为负数", orderSkus.getSkuId());
            }

            int i = 0;

            Boolean b = false;

            // 根据skuid获取满足的仓库库存,按照优先级排序
            List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO
                    .getStoreByItemSkuIdList(orderSkus.getSkuId(), Long.valueOf(orderSkus.getNumber()));

            int size = storeItemSkuDOs.size();
            log.info("[{}] size:{}",size);
            if (size == 0) {
                throw new SupplierException(ResponseCode.B_ORDERNUMBER_ERROR,
                        "无满足库存条件的仓库，库存小于订单量", orderSkus.getSkuId());
            }

            for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {

                // 已经插入锁定记录，退出本次循环，去找下个sku的库存
                if (true == b) {
                    break;
                }

                // 在有效仓库中减库存
                for (StoreDO storeDO : stores) {

                    if (storeDO.getId().equals(storeItemSkuDO.getStoreId())) {
                        orderSkus.setStoreId(storeDO.getId());
                        orderSkus.setSupplierId(storeItemSkuDO.getSupplierId());

                        supplierOrderStockDO = new SupplierOrderStockDO();
                        supplierOrderStockDO.setBizCode(bizCode);
                        supplierOrderStockDO.setOrderSn(orderStockDTO.getOrderSn());
                        supplierOrderStockDO.setSupplierId(orderSkus.getSupplierId());
                        supplierOrderStockDO.setStoreId(storeDO.getId());
                        supplierOrderStockDO.setItemSkuId(orderSkus.getSkuId());
                        supplierOrderStockDO.setNum(Long.valueOf(orderSkus.getNumber()));
                        supplierOrderStockDO.setStatus(SupplierOrderStockStatusEnum.LOCK.getStatus());

                        // 生成订单sku锁定库存记录
                        supplierOrderStockDAO.addLockOrderStockNum(supplierOrderStockDO);

                        storeItemSkuDO.setFrozenStockNum(Long.valueOf(orderSkus.getNumber()));
                        storeItemSkuDO.setSellerId(orderSkus.getDistributorId());

                        b = true;

                        storeItemSkuDOList.add(storeItemSkuDO);
                        orderSkuArrayList.add(orderSkus);
                        break;

                    }

                }
                if (b != true) {
                    i = i + 1;
                }

                if (i >= size) {
                    // 某一个sku在仓库中找不到库存，返回
                    throw new SupplierException(ResponseCode.B_ORDERNUMBER_ERROR, " 无满足库存条件的仓库，库存小于订单量", orderSkus.getSkuId());
                }
            }
        }


        Collections.sort(storeItemSkuDOList, new Comparator<StoreItemSkuDO>() {
            @Override
            public int compare(StoreItemSkuDO o1, StoreItemSkuDO o2) {

                if (o1.getId() > o2.getId()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        //冻结库存
        for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOList) {
            int result = storeItemSkuManagerDAO
                    .updateStoreItemSkuNum(storeItemSkuDO);
            if (result != 1) {
                throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "unlock error");
            }

        }
        orderStockDTO.setOrderSkuList(orderSkuArrayList);

        return orderStockDTO;

    }

    /**
     * 根据失效订单，解锁库存
     *
     * @param supplierOrderStockDTO
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public Boolean unlockSupplierOrderNum(SupplierOrderStockDTO supplierOrderStockDTO) throws SupplierException {
        log.info("[{}] supplierOrderStockDTO:{}", JsonUtil.toJson(supplierOrderStockDTO));

        List<SupplierOrderStockDTO.OrderSku> supplierOrderStockDOList = supplierOrderStockDTO.getOrderSkuList();

        for (SupplierOrderStockDTO.OrderSku orderSku : supplierOrderStockDOList) {

            SupplierOrderStockDO supplierOrderStockDO = new SupplierOrderStockDO();
            supplierOrderStockDO.setOrderSn(supplierOrderStockDTO.getOrderSn());
            supplierOrderStockDO.setItemSkuId(orderSku.getSkuId());
            supplierOrderStockDO.setStatus(SupplierOrderStockStatusEnum.UNLOCK.getStatus());

            //订单库存状态由1改为2 解锁
            int result = supplierOrderStockDAO.updateOrderStockStatusByOrderSn(supplierOrderStockDO);

            log.info("[{}] result:{2}",result);

            if (result < 1) {
                throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                        "updateOrderStockStatusByOrderSn, update status error,OrderSn:" + supplierOrderStockDO.getOrderSn());
            }

            StoreItemSkuDO storeItemSkuDO = new StoreItemSkuDO();
            if (null == orderSku.getStoreId()) {
                throw new SupplierException(ResponseCode.P_PARAM_NULL, "store Id is null");
            }
            storeItemSkuDO.setStoreId(orderSku.getStoreId());
            storeItemSkuDO.setItemSkuId(orderSku.getSkuId());
            Long frozenStockNum = orderSku.getNumber().longValue();
            storeItemSkuDO.setFrozenStockNum(frozenStockNum);

            result = storeItemSkuManagerDAO.reduceStoreItemSkuNum(storeItemSkuDO);
             log.info("[{}] result:{re}",result);
            if (result < 1) {
                throw new SupplierException(ResponseCode.B_UPDATE_ERROR,
                        "unlockSupplierOrderNum reduceStoreItemSkuNum error,OrderSn:" + supplierOrderStockDTO.getOrderSn()+",ItemSkuId:" + orderSku.getSkuId()+
                                ",StoreId:" + orderSku.getStoreId());
            }
        }
        return true;


    }

    /**
     * 订单生效，减掉库存
     *
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public Boolean removeStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) throws SupplierException {


        if (null == orderStockDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierOrderStockDTO is null");
        }
        String bizCode = orderStockDTO.getBizCode();
        String orderSn = orderStockDTO.getOrderSn();


        log.info(" entrance  removeStoreSkuStock manager , OrderSn = {}", orderSn);

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }
        if (orderSn == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "orderSn is null");
        }

        SupplierOrderStockQTO supplierOrderStockQTO = new SupplierOrderStockQTO();

        supplierOrderStockQTO.setOrderSn(orderSn);

        supplierOrderStockQTO.setStatus(SupplierOrderStockStatusEnum.REMOVE.getStatus());

        List<SupplierOrderStockDO> list = supplierOrderStockDAO.queryOrderSkuByOrderSn(supplierOrderStockQTO);

        log.info(" entrance  removeStoreSkuStock manager , OrderSn = {}", orderSn);

//	      第二次调用的时候直接返回成功,商品那边是消息实现的,所以可能会有重复调用

        if (!CollectionUtils.isEmpty(list)) {
            log.info(" exit  removeStoreSkuStock manager , OrderSn = {}", orderSn + "第二次调用的时候直接返回成功");
            return true;
        }


        SupplierOrderStockDO supplierOrderStockDO = new SupplierOrderStockDO();
        supplierOrderStockDO.setOrderSn(orderStockDTO.getOrderSn());

        supplierOrderStockDO.setStatus(SupplierOrderStockStatusEnum.REMOVE.getStatus());


        //1、把订单库存状态由1下单减掉库存 改为3、退单回补库存
        int result = supplierOrderStockDAO.updateOrderStockStatusByOrderSn(supplierOrderStockDO);


        if (result < 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "removeStoreSkuStock, update status error,OrderSn:" + supplierOrderStockDO.getOrderSn());
        } else {

            List<SupplierOrderStockDO> supplierOrderStockDO1 = supplierOrderStockDAO.getOrderSkuByOrderSn(orderStockDTO.getOrderSn());


            StoreItemSkuQTO storeItemSkuQTO;
            List<StoreItemSkuDO> storeItemSkuDOList = new ArrayList<StoreItemSkuDO>();


            for (SupplierOrderStockDO orderSku : supplierOrderStockDO1) {

                storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setStoreId(orderSku.getStoreId());
                storeItemSkuQTO.setItemSkuId(orderSku.getItemSkuId());


                log.info(" removeStoreSkuStock, OrderSn = {}, ItemSkuId = {},StoreId = {},  Num = {}",
                        orderSku.getOrderSn(), orderSku.getItemSkuId(), orderSku.getStoreId(), orderSku.getNum());

                StoreItemSkuDO storeItemSkuDO = storeItemSkuManagerDAO.getStoreItemSku(storeItemSkuQTO);
                //设置减sku库存量
                storeItemSkuDO.setFrozenStockNum(orderSku.getNum());
                //用卖家id区分先后顺序
                storeItemSkuDO.setSellerId(orderSku.getDistributorId());
                storeItemSkuDOList.add(storeItemSkuDO);

            }

            Collections.sort(storeItemSkuDOList, new Comparator<StoreItemSkuDO>() {
                @Override
                public int compare(StoreItemSkuDO o1, StoreItemSkuDO o2) {

                    if (o1.getId() > o2.getId()) {
                        return 1;
                    } else if (o1.getId() < o2.getId()) {
                        return -1;
                    } else {
                        if (o1.getSellerId() > o2.getSellerId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            });

            for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOList) {
                result = storeItemSkuManagerDAO.reduceStoreAndNum(storeItemSkuDO.getStoreId(), storeItemSkuDO.getItemSkuId(), storeItemSkuDO.getFrozenStockNum());

                if (result != 1) {
                    throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "removeStoreSkuStock  error,StoreId:" + storeItemSkuDO.getStoreId() + " ,ItemSkuId:" + storeItemSkuDO.getItemSkuId());
                }
            }

            log.info(" exit  removeStoreSkuStock manager , OrderSn = {}", orderSn);
            return true;
        }
    }

    /**
     * 退单，增加库存
     *
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public Boolean returnStoreSkuStock(SupplierOrderStockDTO orderStockDTO, String appKey) throws SupplierException {


        if (null == orderStockDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierOrderStockDTO is null");
        }
        String bizCode = orderStockDTO.getBizCode();
        String orderSn = orderStockDTO.getOrderSn();

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }
        if (orderSn == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "orderSn is null");
        }
        log.info(" entrance  returnStoreSkuStock manager , OrderSn = {}", orderSn);

        SupplierOrderStockQTO supplierOrderStockQTO = new SupplierOrderStockQTO();

        supplierOrderStockQTO.setOrderSn(orderSn);

        supplierOrderStockQTO.setStatus(SupplierOrderStockStatusEnum.RETURN.getStatus());

        List<SupplierOrderStockDO> list = supplierOrderStockDAO.queryOrderSkuByOrderSn(supplierOrderStockQTO);


//     第二次调用的时候直接返回成功,商品那边是消息实现的,所以可能会有重复调用

        if (!CollectionUtils.isEmpty(list)) {
            log.info(" exit  returnStoreSkuStock manager , OrderSn = {}", orderSn + "第二次调用的时候直接返回成功");
            return true;
        }

        SupplierOrderStockDO supplierOrderStockDO = new SupplierOrderStockDO();
        supplierOrderStockDO.setOrderSn(orderStockDTO.getOrderSn());
        supplierOrderStockDO.setStatus(SupplierOrderStockStatusEnum.RETURN.getStatus());

        //1、把订单库存状态由3下单减掉库存 改为4、退单回补库存
        int result = supplierOrderStockDAO.returnStoreSkuStock(supplierOrderStockDO);

        if (result < 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "returnStoreSkuStock update status error,OrderSn:" + supplierOrderStockDO.getOrderSn());
        } else {

            List<SupplierOrderStockDO> supplierOrderStockDO1 = supplierOrderStockDAO.getOrderSkuByOrderSn(orderStockDTO.getOrderSn());

            StoreItemSkuQTO storeItemSkuQTO;
            List<StoreItemSkuDO> storeItemSkuDOList = new ArrayList<StoreItemSkuDO>();


            for (SupplierOrderStockDO orderSku : supplierOrderStockDO1) {

                storeItemSkuQTO = new StoreItemSkuQTO();
                storeItemSkuQTO.setStoreId(orderSku.getStoreId());
                storeItemSkuQTO.setItemSkuId(orderSku.getItemSkuId());


                log.info(" removeStoreSkuStock, OrderSn = {}, ItemSkuId = {},StoreId = {},  Num = {}",
                        orderSku.getOrderSn(), orderSku.getItemSkuId(), orderSku.getStoreId(), orderSku.getNum());

                StoreItemSkuDO storeItemSkuDO = storeItemSkuManagerDAO.getStoreItemSku(storeItemSkuQTO);
                //设置减sku库存量
                storeItemSkuDO.setFrozenStockNum(orderSku.getNum());
                //用卖家id区分先后顺序
                storeItemSkuDO.setSellerId(orderSku.getDistributorId());
                storeItemSkuDOList.add(storeItemSkuDO);

            }

            Collections.sort(storeItemSkuDOList, new Comparator<StoreItemSkuDO>() {
                @Override
                public int compare(StoreItemSkuDO o1, StoreItemSkuDO o2) {

                    if (o1.getId() > o2.getId()) {
                        return 1;
                    } else if (o1.getId() < o2.getId()) {
                        return -1;
                    } else {
                        if (o1.getSellerId() > o2.getSellerId()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
            });


            for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOList) {

                result = storeItemSkuManagerDAO.increaseStoreNum(storeItemSkuDO.getStoreId(), storeItemSkuDO.getItemSkuId(), storeItemSkuDO.getFrozenStockNum());

                if (result != 1) {
                    throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "returnStoreSkuStock increaseStoreNum error,StoreId:" + storeItemSkuDO.getStoreId() + " ,ItemSkuId:" + storeItemSkuDO.getItemSkuId());
                }
            }
            log.info(" exit  returnStoreSkuStock manager , OrderSn = {}", orderSn);
            return true;
        }

    }


    /**
     * 当部分退单等情况下，返回库存、把订单sku锁定关系置为返还库存
     *
     * @param orderStockDTO
     * @param appKey
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    public Boolean returnStoreSkuStockBySku(SupplierOrderStockDTO orderStockDTO,
                                            String appKey) throws SupplierException {

        if (null == orderStockDTO) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "supplierOrderStockDTO is null");
        }
        String bizCode = orderStockDTO.getBizCode();
        String orderSn = orderStockDTO.getOrderSn();
        if (null == orderSn) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL,
                    "orderSn is null");
        }

        if (bizCode == null) {
            throw new SupplierException(ResponseCode.P_PARAM_NULL, "bizCode is null");
        }

        log.info(" entrance  returnStoreSkuStockBySku manager , OrderSn = {}", orderSn);

        SupplierOrderStockQTO supplierOrderStockQTO;


        List<SupplierOrderStockDTO.OrderSku> orderSku1 = orderStockDTO.getOrderSkuList();
        for (SupplierOrderStockDTO.OrderSku orderSku : orderSku1) {
            if (null == orderSku.getSkuId()) {
                throw new SupplierException(ResponseCode.P_PARAM_NULL,
                        "订单商品sku id  is null");
            }
//            if (null == orderSku.getDistributorId()) {
//                throw new SupplierException(ResponseCode.P_PARAM_NULL,
//                        "订单销售店铺distributor id  is null");
//            }

            supplierOrderStockQTO = new SupplierOrderStockQTO();

            supplierOrderStockQTO.setOrderSn(orderSn);
            supplierOrderStockQTO.setItemSkuId(orderSku.getSkuId());
            //   supplierOrderStockQTO.setDistributorId(orderSku.getDistributorId());

            supplierOrderStockQTO.setStatus(SupplierOrderStockStatusEnum.RETURN.getStatus());

            List<SupplierOrderStockDO> list = supplierOrderStockDAO.queryOrderSkuByOrderSn(supplierOrderStockQTO);

//		     第二次调用的时候直接返回成功,商品那边是消息实现的,所以可能会有重复调用

            if (!CollectionUtils.isEmpty(list)) {
                log.info(" exit  returnStoreSkuStockBySku manager , OrderSn = {}", orderSn + "第二次调用的时候直接返回成功");

                throw new SupplierException(ResponseCode.B_RETURNSTORESKUSTOCKBYSKU_ERROR, "直接返回 returnStoreSkuStock error,setOrderSn:" + orderSn + "ItemSkuId:" + orderSku.getSkuId());

            }

        }


        Collections.sort(orderSku1, new Comparator<SupplierOrderStockDTO.OrderSku>() {
            @Override
            public int compare(SupplierOrderStockDTO.OrderSku o1, SupplierOrderStockDTO.OrderSku o2) {

                if (o1.getSkuId() > o2.getSkuId()) {
                    return 1;
                } else if (o1.getSkuId() < o2.getSkuId()) {
                    return -1;
                }
                return 0;
            }

        });

        SupplierOrderStockDO supplierOrderStockDO;
        int result = 0;
        for (SupplierOrderStockDTO.OrderSku orderSku : orderSku1) {

            if (null == orderSku.getSkuId()) {
                throw new SupplierException(ResponseCode.P_PARAM_NULL,
                        "订单商品sku id  is null");
            }
//            if (null == orderSku.getDistributorId()) {
//                throw new SupplierException(ResponseCode.P_PARAM_NULL,
//                        "订单销售店铺distributor id  is null");
//            }


            supplierOrderStockDO = new SupplierOrderStockDO();

            supplierOrderStockDO.setOrderSn(orderStockDTO.getOrderSn());
            supplierOrderStockDO.setItemSkuId(orderSku.getSkuId());
            //   supplierOrderStockDO.setDistributorId(orderSku.getDistributorId());
            supplierOrderStockDO.setStatus(SupplierOrderStockStatusEnum.RETURN.getStatus());
            //1、把订单库存状态由3下单减掉库存 改为4、退单回补库存


            result = supplierOrderStockDAO.returnStoreSkuStockByOrderSku(supplierOrderStockDO);
            if (result != 1) {
                throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "returnStoreSkuStock error,setOrderSn:" + supplierOrderStockDO.getOrderSn() + "ItemSkuId:" + supplierOrderStockDO.getItemSkuId());
            } else {
                try {
                    //2、根据storeId、skuid和num，把sku表库存改过来
                    supplierOrderStockDO = new SupplierOrderStockDO();
                    supplierOrderStockDO = supplierOrderStockDAO.getOrderStoreId(orderStockDTO.getOrderSn(), orderSku.getSkuId(), 0L);
                    log.info(" returnStoreSkuStockBySku, OrderSn = {}, ItemSkuId = {},StoreId = {},  Num = {}",
                            orderSn, orderSku.getSkuId(), supplierOrderStockDO.getStoreId(), supplierOrderStockDO.getNum());
                    result = storeItemSkuManagerDAO.increaseStoreNum(supplierOrderStockDO.getStoreId(), orderSku.getSkuId(), supplierOrderStockDO.getNum());
                    if (result != 1) {
                        throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "increaseStoreNum error,setOrderSn:" + supplierOrderStockDO.getOrderSn() + "ItemSkuId:" + supplierOrderStockDO.getItemSkuId());
                    }
                } catch (SupplierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "increaseStoreNum error,setOrderSn:" + supplierOrderStockDO.getOrderSn() + "ItemSkuId:" + supplierOrderStockDO.getItemSkuId());
                }
            }
        }
        return true;


    }

    @Override
    public List<StoreItemSkuDTO> getStroeItemSkuListByItemSkuId(Long itemSkuId, Long number) throws SupplierException {

        List<StoreItemSkuDO> storeItemSkuDOs = storeItemSkuManagerDAO.getStoreByItemSkuIdList(itemSkuId, number);

        List<StoreItemSkuDTO> storeItemSkuDTOs = new ArrayList<StoreItemSkuDTO>();
        StoreItemSkuDTO storeItemSkuDTO = null;
        for (StoreItemSkuDO storeItemSkuDO : storeItemSkuDOs) {
            storeItemSkuDTO = new StoreItemSkuDTO();
            BeanUtils.copyProperties(storeItemSkuDO, storeItemSkuDTO);

            storeItemSkuDTOs.add(storeItemSkuDTO);
        }
        return storeItemSkuDTOs;
    }

    @Override
    public List<SupplierNewOrderStockDTO> queryOrderSkuByOrderSn(SupplierOrderStockQTO supplierOrderStockQTO) throws SupplierException {

        List<SupplierOrderStockDO> supplierOrderStockDOs = supplierOrderStockDAO.queryOrderSkuByOrderSn(supplierOrderStockQTO);

        if (CollectionUtils.isEmpty(supplierOrderStockDOs)) {
            return Collections.EMPTY_LIST;

        }

        List<SupplierNewOrderStockDTO> orderStockDTOList = Lists.newArrayListWithExpectedSize(supplierOrderStockDOs.size());

        for (SupplierOrderStockDO supplierOrderStockDO : supplierOrderStockDOs) {
            SupplierNewOrderStockDTO supplierNewOrderStockDTO = new SupplierNewOrderStockDTO();
            BeanUtils.copyProperties(supplierOrderStockDO, supplierNewOrderStockDTO);
            orderStockDTOList.add(supplierNewOrderStockDTO);
        }

        return orderStockDTOList;
    }
    
    /**
     * 根据订单编号和库存动作状态校验是否已经存在数据
     * 
     * @author csy
     * @param orderSn
     * @param statsSup
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
    private OrderStockDTO checkItemSkusByOrderIdAndStats(String orderSn, Integer statsSup, 
    		OrderStockDTO orderStockDTO) throws SupplierException{
    	SupplierOrderStockQTO supplierOrderStockQTO = new SupplierOrderStockQTO();
        supplierOrderStockQTO.setOrderSn(orderSn);
        supplierOrderStockQTO.setStatus(statsSup);
        
        //查询所有动作商品sku数据
        List<SupplierNewOrderStockDTO> orderStockDTOList = supplierOrderStockManager.queryOrderSkuByOrderSn(supplierOrderStockQTO);
        
        if(null == orderStockDTOList || orderStockDTOList.isEmpty()){
            log.error("[{}] supplierOrderStockQTO:{}",JsonUtil.toJson(supplierOrderStockQTO));
        	throw new SupplierException(ResponseCode.P_PARAM_NULL, "查询库存变动数据为空");
        }
        
        Multimap<Long,SupplierNewOrderStockDTO>  recordSkuMap = LinkedListMultimap.create();
        
        for  (SupplierNewOrderStockDTO recordSkuDTO : orderStockDTOList) {
            recordSkuMap.put(recordSkuDTO.getItemSkuId(),recordSkuDTO);
        }
        
        //校验传过来的skuid是否存在
        for (OrderStockDTO.OrderSku orderSku : orderStockDTO.getOrderSkuList()) {
            Long skuId = orderSku.getSkuId();
            
            if (!recordSkuMap.keySet().contains(skuId)) {
                throw new SupplierException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST,"sku:" + skuId+"对应订单数据不存在");
            }
        }
        
        return orderStockDTO;
    }

    /**
     * 支付完成实现库存预扣
     * 
     * @param orderStockDTO
     * @param bizCode
     * @return
     * @throws com.mockuai.suppliercenter.core.exception.SupplierException
     */
	@Override
	public OrderStockDTO reReduceItemSkuSup(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException {
		if(null == orderStockDTO || "".equals(orderStockDTO)){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "预扣库存orderStockDTO为空");
		}
		
		//获取订单商品sku集合
		List<OrderStockDTO.OrderSku> orderSkuList = orderStockDTO.getOrderSkuList();
		
		if(null == orderSkuList || orderSkuList.isEmpty()){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "预扣库存orderSkuList为空");
		}
		
		String orderSn = orderStockDTO.getOrderSn();//订单编号
		
		if(StringUtils.isBlank(orderSn)){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "预扣库存orderSn为空");
		}
		
		//检验此订单对应sku数据是否已经锁定(没有锁定无法实现预扣)
		Integer statsSup = SupplierOrderStockStatusEnum.LOCK.getStatus();
		orderStockDTO = checkItemSkusByOrderIdAndStats(orderSn, statsSup, orderStockDTO);
		
        //预扣库存(1.修改库存变动记录 2.更新sku表中库存数据)
        for (OrderStockDTO.OrderSku orderSku : orderSkuList) {
        	//1.修改库存变动记录
        	updateOrderStockSup(orderStockDTO.getOrderSn(),orderSku.getSkuId(), SupplierOrderStockStatusEnum.REREDUCE.getStatus());
            
            //2.查询最新sku库存信息(根据供应商仓库id和skuid)            
        	StoreItemSkuDTO storeItemSkuDto = getStoreItemSkuDTO(orderSku.getSkuId(), orderSku.getStoreId()); 
            
            //3.更新sku表中库存数据
            updateStoreItemSkuNum(orderSku, SupplierOrderStockStatusEnum.REREDUCE.getStatus(), storeItemSkuDto);  
        }
        
        return orderStockDTO;
	}
	
	/**
	 * 更新商品skuid对应库存信息数据
	 * 			
	 * 
	 * @param orderSku
	 * @return
	 */
	private int updateStoreItemSkuNum(OrderStockDTO.OrderSku orderSku, Integer statsSup, 
			StoreItemSkuDTO StoreItemSkuDto) throws SupplierException{
		if(null == orderSku || "".equals(orderSku)){
			throw new SupplierException(ResponseCode.P_PARAM_NULL, "更新sku库存数量参数为空");
		}
		
		//变动数量
		Long numBer = orderSku.getNumber().longValue();
		Long stockNum= null;
		
		StoreItemSkuDO storeItemSkuDO = new StoreItemSkuDO();
        storeItemSkuDO.setStoreId(orderSku.getStoreId());
        storeItemSkuDO.setItemSkuId(orderSku.getSkuId());
        
        //预扣(锁定库存减少预扣库存增加)
        if(null != statsSup && SupplierOrderStockStatusEnum.REREDUCE.getStatus() == statsSup){
        	storeItemSkuDO.setFrozenStockNum(StoreItemSkuDto.getFrozenStockNum()-numBer);
            storeItemSkuDO.setSoldNum(StoreItemSkuDto.getSoldNum()+numBer);
            
            stockNum = storeItemSkuDO.getFrozenStockNum()+storeItemSkuDO.getSoldNum()+StoreItemSkuDto.getSalesNum();
        }
        
        //反扣(直接把库存数量返回可用库存里面)
        if(null != statsSup && SupplierOrderStockStatusEnum.BACKREDUCE.getStatus() == statsSup){
            storeItemSkuDO.setSoldNum(StoreItemSkuDto.getSoldNum()-numBer);
            storeItemSkuDO.setSalesNum(StoreItemSkuDto.getSalesNum()+numBer);
            
            stockNum = StoreItemSkuDto.getFrozenStockNum()+storeItemSkuDO.getSoldNum()+storeItemSkuDO.getSalesNum();
        }
        
        //实扣(预扣库存量减少总库存量减少)
        if(null != statsSup && SupplierOrderStockStatusEnum.REALREDUCE.getStatus() == statsSup){
            storeItemSkuDO.setSoldNum(StoreItemSkuDto.getSoldNum()-numBer);
            storeItemSkuDO.setStockNum(StoreItemSkuDto.getStockNum()-numBer);
            
            stockNum = StoreItemSkuDto.getStockNum();
        }
        
      /*  //检验总库存数量(总库存数量=锁定量+可用数量+预扣量)      
        if((null == stockNum || stockNum-StoreItemSkuDto.getStockNum() != 0)){
        	throw new SupplierException(ResponseCode.P_PARAM_NULL, "变动库存量与总库存量不相等");
        }*/
        
        //更新变动后数据
        int result = storeItemSkuManagerDAO.changeStoreItemSkuNum(storeItemSkuDO);

        if (result < 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "reReduce sup error");
        }
        
        return result;
	} 
	
	/**
	 * 根据订单编码和变动库存状态更新数据
	 * 
	 * @param orderSn
	 * @param statsSup
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	private int updateOrderStockSup(String orderSn,Long itemSkuId, Integer statsSup) throws SupplierException{
		if(null == orderSn || "".equals(orderSn)){
			throw new SupplierException(ResponseCode.P_PARAM_NULL, "更新库存记录信息参数为空");
		}
		
		if(null == statsSup || "".equals(statsSup)){
			throw new SupplierException(ResponseCode.P_PARAM_NULL, "更新库存记录信息参数为空");
		}
		
		SupplierOrderStockDO supplierOrderStockDO = new SupplierOrderStockDO();
        supplierOrderStockDO.setOrderSn(orderSn);
        supplierOrderStockDO.setItemSkuId(itemSkuId);
        supplierOrderStockDO.setStatus(statsSup);

        //订单库存变更状态
        int result = supplierOrderStockDAO.updateOrderStockStatusByOrderSn(supplierOrderStockDO);
        
        if (result < 1) {
            throw new SupplierException(ResponseCode.B_UPDATE_ERROR, "reReduce sup error");
        }
        
        return result;
	}
	
	/**
	 * 根据仓库id和skuid查询sku库存信息
	 *
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	private StoreItemSkuDTO getStoreItemSkuDTO(Long skuId, Long storeId) throws SupplierException{
		if(null == skuId || "".equals(skuId)){
			throw new SupplierException(ResponseCode.P_PARAM_NULL, "查询sku库存信息参数为空");
		}
		
		if(null == storeId || "".equals(storeId)){
			throw new SupplierException(ResponseCode.P_PARAM_NULL, "查询sku库存信息参数为空");
		}
		
		StoreItemSkuQTO storeItemSkuQTO = new StoreItemSkuQTO();
        storeItemSkuQTO.setItemSkuId(skuId);
        storeItemSkuQTO.setStoreId(storeId);
		
        //查询商品sku库存
		StoreItemSkuDO storeItemSkuDO = storeItemSkuManagerDAO.getStoreItemSku(storeItemSkuQTO);
		
		if(null == storeItemSkuDO || "".equals(storeItemSkuDO)){
			throw new SupplierException(ResponseCode.P_PARAM_NULL, "查询sku库存信息参数为空");
		}
		
		//复制属性
		StoreItemSkuDTO storeItemSkuDTO = new StoreItemSkuDTO();
		BeanUtils.copyProperties(storeItemSkuDO, storeItemSkuDTO);
		
		return storeItemSkuDTO;
	}

	/**
	 * 退货退款实现反扣
	 * 
	 * @param orderStockDTO
	 * @param bizCode
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	@Override
	public OrderStockDTO backReduceItemSkuSup(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException {
		if(null == orderStockDTO || "".equals(orderStockDTO)){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "反扣库存orderStockDTO为空");
		}
		
		//获取订单商品sku集合
		List<OrderStockDTO.OrderSku> orderSkuList = orderStockDTO.getOrderSkuList();
		
		if(null == orderSkuList || orderSkuList.isEmpty()){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "反扣库存orderSkuList为空");
		}
		
		String orderSn = orderStockDTO.getOrderSn();//订单编号
		
		if(StringUtils.isBlank(orderSn)){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "预扣库存orderSn为空");
		}
		
		//检验此订单对应sku数据是否已经预扣(没有预扣无法实现反扣)
		Integer statsSup = SupplierOrderStockStatusEnum.REREDUCE.getStatus();
		orderStockDTO = checkItemSkusByOrderIdAndStats(orderSn, statsSup, orderStockDTO);
		
       //反扣库存(1.修改库存变动记录 2.更新sku表中库存数据)
       for (OrderStockDTO.OrderSku orderSku : orderSkuList) {
       	   //1.修改库存变动记录
    	   updateOrderStockSup(orderStockDTO.getOrderSn(),orderSku.getSkuId(),SupplierOrderStockStatusEnum.BACKREDUCE.getStatus());
           
           //2.查询最新sku库存信息(根据供应商仓库id和skuid)            
       	   StoreItemSkuDTO storeItemSkuDto = getStoreItemSkuDTO(orderSku.getSkuId(), orderSku.getStoreId()); 
           
           //3.更新sku表中库存数据
           updateStoreItemSkuNum(orderSku, SupplierOrderStockStatusEnum.BACKREDUCE.getStatus(), storeItemSkuDto);  
       }
       
       return orderStockDTO;
	}

	/**
	 * 物流发送实现实扣
	 * 
	 * @param orderStockDTO
	 * @param bizCode
	 * @return
	 * @throws com.mockuai.suppliercenter.core.exception.SupplierException
	 */
	@Override
	public OrderStockDTO realReduceItemSkuSup(OrderStockDTO orderStockDTO, String bizCode) throws SupplierException {
		if(null == orderStockDTO || "".equals(orderStockDTO)){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "实扣库存orderStockDTO为空");
		}
		
		//获取订单商品sku集合
		List<OrderStockDTO.OrderSku> orderSkuList = orderStockDTO.getOrderSkuList();
		
		if(null == orderSkuList || orderSkuList.isEmpty()){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "实扣库存orderSkuList为空");
		}
		
		String orderSn = orderStockDTO.getOrderSn();//订单编号
		
		if(StringUtils.isBlank(orderSn)){
			 throw new SupplierException(ResponseCode.P_PARAM_NULL, "实扣库存orderSn为空");
		}
		
		//检验此订单对应sku数据是否已经预扣(没有预扣无法实现实扣)
		Integer statsSup = SupplierOrderStockStatusEnum.REREDUCE.getStatus();
		orderStockDTO = checkItemSkusByOrderIdAndStats(orderSn, statsSup, orderStockDTO);
		
       //实扣库存(1.修改库存变动记录 2.更新sku表中库存数据)
       for (OrderStockDTO.OrderSku orderSku : orderSkuList) {
       	   //1.修改库存变动记录
       	   updateOrderStockSup(orderStockDTO.getOrderSn(), orderSku.getSkuId(),SupplierOrderStockStatusEnum.REALREDUCE.getStatus());
           
           //2.查询最新sku库存信息(根据供应商仓库id和skuid)            
       	   StoreItemSkuDTO storeItemSkuDto = getStoreItemSkuDTO(orderSku.getSkuId(), orderSku.getStoreId()); 
           
           //3.更新sku表中库存数据
           updateStoreItemSkuNum(orderSku, SupplierOrderStockStatusEnum.REALREDUCE.getStatus(), storeItemSkuDto);  
       }
       
       return orderStockDTO;
	}


}

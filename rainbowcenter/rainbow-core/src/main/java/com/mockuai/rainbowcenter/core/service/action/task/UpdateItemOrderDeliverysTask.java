package com.mockuai.rainbowcenter.core.service.action.task;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.common.constant.ERPOrderStatus;
import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.common.constant.SysFieldValueTypeEnum;
import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.common.dto.SysFieldMapDTO;
import com.mockuai.rainbowcenter.common.qto.SysFieldMapQTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.HsOrderManager;
import com.mockuai.rainbowcenter.core.manager.SysFieldMapManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by lizg on 2016/6/7.
 */
public class UpdateItemOrderDeliverysTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(UpdateItemOrderDeliverysTask.class);

    private Map<String, Map<String, Object>> cutItemsOrderMap;
    private SysFieldMapManager sysFieldMapManager;
    private HsOrderManager hsOrderManager;
    private String appKey;

    public void setCutItemsOrderMap(Map<String, Map<String, Object>> cutItemsOrderMap) {
        this.cutItemsOrderMap = cutItemsOrderMap;
    }

    public void setSysFieldMapManager(SysFieldMapManager sysFieldMapManager) {
        this.sysFieldMapManager = sysFieldMapManager;
    }

    public void setHsOrderManager(HsOrderManager hsOrderManager) {
        this.hsOrderManager = hsOrderManager;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public void run() {

        //一条MC订单,循环一次
        for (Map.Entry<String, Map<String, Object>> entry : cutItemsOrderMap.entrySet()) {
            try {
                logger.info("[{}] delivery status:{}", entry.getValue().get("deliveryStatus"));
                if (String.valueOf(entry.getValue().get("deliveryStatus")).equals(ERPOrderStatus.SHIPPED.getValue())) {
                    logger.info("start to record deliver goods");

                    // 添加发货的记录
                    List<Map<String, String>> items = ((List<Map<String, String>>) entry.getValue().get("items"));
                    List<String> itemCodes = new ArrayList<String>();
                    for (Map<String, String> item : items) {
                        itemCodes.add(item.get("itemCode"));
                    }
                    Collections.sort(itemCodes);
                    String key = String.valueOf(entry.getValue().get("orderSn"));
                    logger.info("[{}] key:{}", key);
                    String userId = String.valueOf(entry.getValue().get("userId"));
                    logger.info("[{}] userId:{}", userId);
                    String value = JSON.toJSONString(itemCodes);
                    logger.info("[{}] value:{}", value);
                    SysFieldMapQTO erpOrderBarCodeSysFieldMapQTO = new SysFieldMapQTO();
                    erpOrderBarCodeSysFieldMapQTO.setFieldName(SysFieldNameEnum.ERP_ITEM_CODE.getValue());
                    erpOrderBarCodeSysFieldMapQTO.setType("gyerpGetorder");
                    erpOrderBarCodeSysFieldMapQTO.setBizCode("hanshu");
                    erpOrderBarCodeSysFieldMapQTO.setOutValue(key);
                    erpOrderBarCodeSysFieldMapQTO.setValue(value);
                    SysFieldMapDTO result = this.sysFieldMapManager.getSysFieldMap(erpOrderBarCodeSysFieldMapQTO, true);
                    logger.info("[{}] result:{}", result);
                    if (result == null) {

                        //调用发货接口
                        logger.info("start to invoke deliveryGoods interface");
                        this.hsOrderManager.deliveryItems(entry.getValue(),appKey);

                        //添加发货记录
                        SysFieldMapDTO edbOrderBarCodeSysFieldMapDTO = new SysFieldMapDTO();
                        edbOrderBarCodeSysFieldMapDTO.setFieldName(SysFieldNameEnum.ERP_ITEM_CODE.getValue());
                        edbOrderBarCodeSysFieldMapDTO.setBizCode("hanshu");
                        edbOrderBarCodeSysFieldMapDTO.setType("gyerpGetorder");
                        edbOrderBarCodeSysFieldMapDTO.setOutValue(key);
                        edbOrderBarCodeSysFieldMapDTO.setOutValueType(SysFieldValueTypeEnum.TYPE_STRING.getValue());
                        edbOrderBarCodeSysFieldMapDTO.setValue(value);
                        edbOrderBarCodeSysFieldMapDTO.setValueType(SysFieldValueTypeEnum.TYPE_STRING.getValue());
                        this.sysFieldMapManager.addSysFieldMap(edbOrderBarCodeSysFieldMapDTO);
                        logger.info("end add  delivery goods success...........");

                    }


                }

                //延时2s
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (RainbowException e) {
                logger.error("errCode:{}, errMsg:{}", e.getResponseCode(), e.getMessage());
            }
        }
    }

}

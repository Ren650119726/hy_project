package com.mockuai.rainbowcenter.core.service.job;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.rainbowcenter.common.constant.SysFieldNameEnum;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.util.GyERPUtils;
import com.mockuai.rainbowcenter.core.util.PropertyConfig;
import org.json.JSONArray;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lizg on 2016/6/6.
 * 订单轮询
 */
public class ItemDeliverysQuartzJob extends BaseJob {

    private static final Logger logger = LoggerFactory.getLogger(ItemDeliverysQuartzJob.class);

    private Integer pageSize;

    private Integer day;

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public void orderPoll() throws RainbowException {

        logger.info("start to poll item order quartz job");

        //拼接入参
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.trade.deliverys.get");

        Date endTime = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endTime);
        calendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf("-" + day));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonObject.put("start_delivery_date", df.format(calendar.getTime()));
        jsonObject.put("end_delivery_date", df.format(endTime));

        jsonObject.put("page_no", 1);
        jsonObject.put("page_size", pageSize);

        Integer totalResults = 0;//请求获取的条数
        Long totalOrders = 0L; //总条数
        Integer currentPageNum = 1;
        Double n = 0D;
        do {
            Map<String, Map<String, Object>> itemsOrderMap = new HashMap<String, Map<String, Object>>();

            String response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject);
            if (null != response) {
                JSONObject fastjson = JSONObject.parseObject(response);
                String result = fastjson.getString("success");
                if (result.equals("true")) {
                    String deliverys = fastjson.getString("deliverys");
                    JSONArray jsonArray = new JSONArray(deliverys);
                    totalResults = jsonArray.length();
                    //   logger.info("[{}] totalResults{}", totalResults);
                    Long total = fastjson.getLong("total");
                    totalOrders = total;
                    n = Math.ceil(totalOrders / (pageSize * 1.0D));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        org.json.JSONObject jsonObj = jsonArray.getJSONObject(i);
                        if (!jsonObj.has("platform_code")) {
                            continue;
                        }
                        if (!jsonObj.has("express_no")) {
                            continue;
                        }
                        String orderSn = jsonObj.getString("platform_code");
                        String vipCode = jsonObj.getString("vip_code");
                        String expressCode = jsonObj.getString("express_code");
                        String expressName = jsonObj.getString("express_name");
                        String warehouseCode = jsonObj.getString("warehouse_code");
                        String warehouseName = jsonObj.getString("warehouse_name");

                        //TODO 对补单做处理 如果后面订单sn生成改变 这块需要优化
                        if (orderSn.length() < 28) {
                            continue;
                        }

                        //不做处理的仓库
                        if (warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_WSCODE.getValue()) ||
                                warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_MZXCODE.getValue()) ||
                                warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_LLXCODE.getValue()) ||
                                warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_WEISCODE.getValue())) {
                            continue;
                        }
                        String expressNo = jsonObj.getString("express_no");
                        Integer refundStatus = jsonObj.getInt("refund"); //退款状态
                        if (refundStatus != 0) {  //如果是退款的订单不做操作
                            continue;
                        }
                        String deliveryStatusInfo = jsonObj.get("delivery_statusInfo").toString(); //发货状态明细
                        JSONObject deliveryStatusJson = JSONObject.parseObject(deliveryStatusInfo);
                        Integer deliveryStatus = deliveryStatusJson.getInteger("delivery");

                        Map<String, Object> erpOrderMap = new HashMap<String, Object>();
                        erpOrderMap.put("orderSn", orderSn);
                        erpOrderMap.put("userId", vipCode);
                        erpOrderMap.put("deliveryStatus", deliveryStatus);
                        erpOrderMap.put("expressName", expressName);
                        erpOrderMap.put("expressCode", expressCode); //物流公司代码
                        erpOrderMap.put("expressNo", expressNo);  //物流单号
                        erpOrderMap.put("warehouseCode", warehouseCode);
                        erpOrderMap.put("warehouseName", warehouseName);
                        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
                        String details = jsonObj.get("details").toString(); //商品明细
                        JSONArray itemsJson = new JSONArray(details);
                        for (int j = 0; j < itemsJson.length(); j++) {
                            org.json.JSONObject jsonObjItems = itemsJson.getJSONObject(j);
                            Map<String, String> itemMap = new HashMap<String, String>();
                            itemMap.put("itemName", jsonObjItems.getString("item_name"));
                            itemMap.put("itemCode", jsonObjItems.getString("item_code"));
                            itemMap.put("itemNum", jsonObjItems.get("qty").toString());

                            items.add(itemMap);
                        }
                        erpOrderMap.put("items", items);
                        itemsOrderMap.put(orderSn, erpOrderMap);


                    }


                }

            }
            if (itemsOrderMap.size() > 0) {
                //   logger.info("start to update  order ..........");
                //异步调用修改订单
                this.hsThreadPoolManager.itemsOrderDeliverys(itemsOrderMap);
            }

            jsonObject.put("page_no", ++currentPageNum); //分页
            jsonObject.remove("sign");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (totalOrders > pageSize && totalResults >= pageSize && currentPageNum <= n);
    }


    @Override
    protected void poll(JobExecutionContext context) throws JobExecutionException {
        try {
            orderPoll();
        } catch (RainbowException e) {
            e.printStackTrace();
        }

    }
}

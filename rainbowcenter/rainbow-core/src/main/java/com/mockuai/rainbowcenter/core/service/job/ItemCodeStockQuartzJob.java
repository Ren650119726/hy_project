package com.mockuai.rainbowcenter.core.service.job;

import com.alibaba.fastjson.JSONObject;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizg on 2016/9/26.
 * 库存同步轮询
 */
public class ItemCodeStockQuartzJob extends BaseJob{

    private static final Logger log = LoggerFactory.getLogger(ItemCodeStockQuartzJob.class);

    private Integer pageSize;
    private String startTime;

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void poll() throws RainbowException {

        log.info("start to poll itemCode stock quartz job");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
        jsonObject.put("method", "gy.erp.new.stock.get");

        Date endTime = new Date();

        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonObject.put("start_date",startTime);
        jsonObject.put("end_date",sdf.format(endTime));

        jsonObject.put("page_no", "1");//分页
        jsonObject.put("page_size", pageSize + "");//页数量

         Long totalItemCodesStock = 0L;
        Integer totalResults = 0;
        Integer currentPageNum = 1;
        Double n = 0D;

        Map<String, Map<String, String>> itemCodesStockMap = new HashMap<String, Map<String, String>>();
         do {
             String response = GyERPUtils.sendPostStock(PropertyConfig.getGyErpUrl(), jsonObject);

             if (null == response) {
                 throw new RainbowException(ResponseCode.GYERP_E_SERVICE_EXCEPTION);
             }

             JSONObject fastjson = JSONObject.parseObject(response);
             String result = fastjson.getString("success");
             if (result.equals("true")) {
                 String stocks = fastjson.getString("stocks");
                 JSONArray jsonArray = new JSONArray(stocks);

                 totalResults = jsonArray.length();

             //    log.info("[{}] stock totalResults{}", totalResults);

                 Long total = fastjson.getLong("total");

                 totalItemCodesStock = total;

                 n = Math.ceil(totalItemCodesStock / (pageSize * 1.0D));

                 for (int i=0;i<jsonArray.length();i++) {
                     org.json.JSONObject jsonObj = jsonArray.getJSONObject(i);

                     String warehouseCode = jsonObj.optString("warehouse_code");
                        log.info("[{}] warehouseCode:{}",warehouseCode);
                     //不做处理的仓库
                     if (warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_WSCODE.getValue()) ||
                             warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_MZXCODE.getValue()) ||
                             warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_LLXCODE.getValue()) ||
                             warehouseCode.equals(SysFieldNameEnum.WAREHOUSE_WEISCODE.getValue()) ||
                             warehouseCode.equals(SysFieldNameEnum.NEIGOU_WEISCODE.getValue())) {
                         continue;
                     }

                     Boolean del = jsonObj.getBoolean("del");  //true-停用；false-未停用
                       log.info("[{}] del:{}",del);
                     if (del) {
                         continue;
                     }
                     String itemCode = jsonObj.optString("item_code");

                     String salableQty = jsonObj.optString("salable_qty"); //可销售数
                     log.info("[{}] salableQty:{},itemCode:{}，warehouseCode：{}",salableQty,itemCode,warehouseCode);
                     String salableNum = salableQty.substring(0, salableQty.lastIndexOf("."));
                     if (Integer.parseInt(salableNum) < 0) {
                         log.error("stock num less than 0, stockNum: {}", salableQty);
                         salableNum = "0";
                     }

                     Map<String, String> itemCodeMap = new HashMap<String, String>();
                     itemCodeMap.put("itemCode",itemCode);
                     itemCodeMap.put("warehouseCode",warehouseCode);
                     itemCodeMap.put("salableQty",salableNum);

                     itemCodesStockMap.put("itemCode",itemCodeMap);

                 }

                 if (itemCodesStockMap.size() > 0) {
                  // log.info("itemCodesStock size: {}", itemCodesStockMap.size());

                     // 异步调用修改商品sku库存
                     this.hsThreadPoolManager.itemCodesStockSubmite(itemCodesStockMap);
                 }

                 jsonObject.put("page_no", ++currentPageNum); //分页
                 jsonObject.remove("sign");
                 try {
                     Thread.sleep(3000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }

         }while (totalResults >= pageSize && totalItemCodesStock > pageSize && currentPageNum <= n);

    }


    @Override
    protected void poll(JobExecutionContext context) throws JobExecutionException {
        try {
            poll();
        } catch (RainbowException e) {
            e.printStackTrace();
        }

    }
}

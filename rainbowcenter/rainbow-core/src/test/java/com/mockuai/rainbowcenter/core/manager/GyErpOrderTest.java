package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.rainbowcenter.UnitTestUtils;
import com.mockuai.rainbowcenter.common.api.BaseRequest;
import com.mockuai.rainbowcenter.common.constant.ActionEnum;
import com.mockuai.rainbowcenter.common.dto.DuibaRecordOrderDTO;
import com.mockuai.rainbowcenter.common.dto.ErpOrderDTO;
import com.mockuai.rainbowcenter.common.dto.ErpStockDTO;
import com.mockuai.rainbowcenter.common.qto.DuibaRecordOrderQTO;
import com.mockuai.rainbowcenter.core.dao.DefaultExpressDAO;
import com.mockuai.rainbowcenter.core.dao.DuibaConfigurationDAO;
import com.mockuai.rainbowcenter.core.dao.DuibaRecordOrderDAO;
import com.mockuai.rainbowcenter.core.domain.DefaultExpressDO;
import com.mockuai.rainbowcenter.core.domain.DuibaConfigurationDO;
import com.mockuai.rainbowcenter.core.domain.DuibaRecordOrderDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizg on 2016/6/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})

public class GyErpOrderTest {

    @Resource
    private ErpOrderManager erpOrderManager;


    @Resource
    private DefaultExpressDAO defaultExpressDAO;

    @Resource
    private DuibaConfigurationDAO duibaConfigurationDAO;

    @Resource
    private RecordOrderManager recordOrderManager;

    @Resource
    private DuibaRecordOrderDAO duibaRecordOrderDAO;

    @Resource
    private GyErpManage gyErpManage;

    @Test
    public void addGyerpOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String code = "4544444777";
        Long orderId = 44555669L;
        String createTime = "2016-06-08 12:10:12";
        ErpOrderDTO erpOrderDTO = new ErpOrderDTO();
        erpOrderDTO.setOrderId(String.valueOf(orderId));
        erpOrderDTO.setGyerpCode(code);
        try {
            erpOrderDTO.setCreateTime(sdf.parse(createTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            ErpOrderDTO erpOrderDTO1 = erpOrderManager.addErpOrder(erpOrderDTO);
            System.out.print("id:{}" + erpOrderDTO1.getId());
        } catch (RainbowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getGyerpOrder() {

        String orderId = "44555669";
        try {
            ErpOrderDTO erpOrderDTO1 = erpOrderManager.getGyerpCode(orderId);
            System.out.print("code:{}" + erpOrderDTO1.getGyerpCode());
        } catch (RainbowException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateErpStatus() {
//        //拼接入参
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("appkey", PropertyConfig.getGyErpAppkey());
//        jsonObject.put("sessionkey", PropertyConfig.getGyErpSessionkey());
//        jsonObject.put("method", "gy.erp.trade.refund.update");
//         jsonObject.put("tid", "56845");
//         jsonObject.put("oid", "56845");
//        jsonObject.put("refund_state", 1); //退款状态	0-取消退款 1-标识退款
//        System.out.print("gyerp gy.erp.trade.refund.update sign  ago param {}:" + jsonObject.toString());
//
//        //签名
//        String sign = GyERPSignUtil.sign(jsonObject.toString(), PropertyConfig.getGyerpSecret());
//        System.out.print("gyerp gy.erp.trade.refund.update sign {}" + sign);
//        jsonObject.put("sign", sign);
//        System.out.print("gyerp gy.erp.trade.refund.update sign end param {}:" + jsonObject.toString());
//
//        //修改订单退款状态
//        String response = null;
//        try {
//            response = GyERPUtils.sendPost(PropertyConfig.getGyErpUrl(), jsonObject.toString());
//            System.out.print("gyerp gy.erp.trade.refund.update response: {}"+response);
//        } catch (RainbowException e) {
//            e.printStackTrace();
//        }
        Long orderId = 57048L;
        //获取erp单据编号
        ErpOrderDTO erpOrderDTO = null;
        try {
            erpOrderDTO = erpOrderManager.getGyerpCode(Long.toString(orderId));
            System.out.print("gyerp code:{}" + erpOrderDTO.getGyerpCode());

        } catch (RainbowException e) {
            e.printStackTrace();
        }
        if (null == erpOrderDTO) {
            System.out.print("erpCode is null ................");
        }

    }

    @Test
    public void getDefaultExpress() throws RainbowException {


        DefaultExpressDO erpOrderDTO1 = defaultExpressDAO.getDefaultExpress();
        System.out.print("code:{}" + erpOrderDTO1.getExpressNo());

    }

    @Test
    public void getConfiguration() throws RainbowException {

        DuibaConfigurationDO duibaConfigurationDO = duibaConfigurationDAO.getConfiguration();

        System.out.print("loginUrl:{}" + duibaConfigurationDO.getLoginUrl());

    }


    @Test
    public void getRecordByOrderNum() throws RainbowException {

        DuibaRecordOrderDO duibaRecordOrderQTO = new DuibaRecordOrderDO();
        duibaRecordOrderQTO.setOrderNum("12121");
        DuibaRecordOrderDO duibaRecordOrderDTO = duibaRecordOrderDAO.getRecordByOrderNum(duibaRecordOrderQTO);

        System.out.print("orderNum:{}" + duibaRecordOrderDTO);

    }


    @Test
    public void addRecord() throws RainbowException {

        DuibaRecordOrderDO duibaRecordOrderDO = new DuibaRecordOrderDO();
        duibaRecordOrderDO.setUid("1");
        duibaRecordOrderDO.setBizNum("4565666");
        duibaRecordOrderDO.setCredits(500L);
        duibaRecordOrderDO.setType("alipay");
        duibaRecordOrderDO.setFacePrice(400);
        duibaRecordOrderDO.setActualPrice(300);
        duibaRecordOrderDO.setWaitAudit(0);
        duibaRecordOrderDO.setStatus(2);
        duibaRecordOrderDO.setDescription("wewew");
        duibaRecordOrderDO.setOrderNum("12121");
        duibaRecordOrderDO.setExchangeTime(new Date());
        Long id = duibaRecordOrderDAO.addRecordOrder(duibaRecordOrderDO);

        System.out.print("id:{}" + id);

    }

    @Test
    public void testUpdateStatus() throws Exception {
        DuibaRecordOrderDO duibaRecordOrderDO = new DuibaRecordOrderDO();
        duibaRecordOrderDO.setOrderNum("order-for-test-1474338368865");
        duibaRecordOrderDO.setStatus(1);
        duibaRecordOrderDO.setErrorMessage("wewewew");
       int count =  duibaRecordOrderDAO.updateStatusByorderNum(duibaRecordOrderDO);
        System.out.print("count:{}" + count);
    }

    @Test
    public void testGyErpStock() throws Exception{
        String skuItemSn = "LDZ0002";
        Long storeId = 3L;

        ErpStockDTO erpStockDTO =  gyErpManage.getStock(skuItemSn, storeId);
        System.out.print("erpStockDTO:{}" + JsonUtil.toJson(erpStockDTO));
    }
}

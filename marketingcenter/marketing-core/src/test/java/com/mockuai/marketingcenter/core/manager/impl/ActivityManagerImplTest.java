package com.mockuai.marketingcenter.core.manager.impl;

import com.mockuai.marketingcenter.client.LimitedPurchaseClient;
import com.mockuai.marketingcenter.common.domain.dto.LimitedPurchaseDTO;
import com.mockuai.marketingcenter.common.domain.qto.TimePurchaseQTO;
import com.mockuai.marketingcenter.core.domain.LimitedPurchaseDO;
import com.mockuai.marketingcenter.core.manager.LimitedPurchaseManager;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by Administrator on 2016/9/23.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })

public class ActivityManagerImplTest {
    @Resource
    private LimitedPurchaseManager limitedPurchaseManager;
    @Resource
    private LimitedPurchaseClient limitedPurchaseClient;
//    @Resource
//    private ActivityDAO activityDAO;

    @Test
    public void testAddActivity() throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        ActivityDO activityDO = new ActivityDO();
//        activityDO.setActivityName("测试活动");
//        activityDO.setStartTime(format.parse("2016-10-09 20-30-00"));
//        activityDO.setEndTime(format.parse("2016-10-10 20-30-00"));
//        activityDO.setActivityTag("123");
//        activityDAO.addActivities(activityDO);

        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        timePurchaseQTO.setActivityName("测试活动7");
        timePurchaseQTO.setStartTime(format.parse("2016-10-12 20:30:00"));
        timePurchaseQTO.setEndTime(format.parse("2016-10-15 20:30:00"));
        timePurchaseQTO.setActivityTag("124");
        Long num = limitedPurchaseManager.addActivities(timePurchaseQTO);
        System.out.println("NUM :" + num);
    }

    @Test
    public void testActivityList() throws Exception {
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
        timePurchaseQTO.setActivityName("双");
        timePurchaseQTO.setOffset(1 - 1);
        timePurchaseQTO.setCount(20);
        //timePurchaseQTO.setStatus(0L);
        List<LimitedPurchaseDTO> list = limitedPurchaseManager.activityList(timePurchaseQTO);
    }

    @Test
    public void testCountActivity() throws Exception {
        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();


        //timePurchaseQTO.setRunStatus(0L);
        Integer count = limitedPurchaseManager.activityCount(timePurchaseQTO);
        System.out.println("COUNT:" + count);
    }

    @Test
    public void testDeleteActivity() throws Exception {
//        TimePurchaseQTO timePurchaseQTO = new TimePurchaseQTO();
//        timePurchaseQTO.setStatus(1L);
        LimitedPurchaseDO activityDO = new LimitedPurchaseDO();
        activityDO.setId(34L);
        //activityDO.setRunStatus(2L);
        Boolean flag = limitedPurchaseManager.updateActivity(activityDO);
        System.out.println(flag);
    }

    @Test
    public void testStartActivity() throws Exception {
        LimitedPurchaseDO activityDO = new LimitedPurchaseDO();
        activityDO.setId(36L);

        Boolean flag = limitedPurchaseManager.startLimtedPurchase(activityDO);
        System.out.println(flag);
    }

    @Test
    public void testModifyActivity() throws Exception {
        LimitedPurchaseDO activityDO = new LimitedPurchaseDO();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        activityDO.setId(33L);
        activityDO.setActivityName("修改活动名2");
        activityDO.setStartTime(format.parse("2016-10-08 20:30:00"));
        activityDO.setEndTime(format.parse("2016-10-18 20:30:00"));
        activityDO.setActivityTag("125");
        limitedPurchaseManager.modifyActivity(activityDO);
    }

    @Test
    public void testActivityById() throws Exception {
       LimitedPurchaseDTO activityDTO = limitedPurchaseManager.activityById(32L);
        System.out.println(activityDTO.getActivityName());
    }
    @Test
    public void testUpdateActivityStatus() throws Exception{
        String appKey = "27c7bc87733c6d253458fa8908001eef";
        limitedPurchaseClient.updateLimitedPurchaseStatus(appKey);
    }
}








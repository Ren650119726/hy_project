package com.mockuai.giftscenter.core;

import com.mockuai.giftscenter.common.api.BaseRequest;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.api.Response;
import com.mockuai.giftscenter.common.api.GiftsService;
import com.mockuai.giftscenter.core.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeTest;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by edgar.zr on 8/12/15.
 */
public abstract class BaseActionTest extends BaseTest {

    private final Logger LOGGER;

    @Autowired
    protected GiftsService seckillService;
    protected Request request;
    protected Response response;
    protected Random random = new Random();
    protected SimpleDateFormat format = new SimpleDateFormat(DateUtils.DATETIME12_FORMAT2);

    public BaseActionTest(String className) {
        LOGGER = LoggerFactory.getLogger(className);
    }

    @BeforeTest
    protected void beforeTest() {
        request = new BaseRequest();
        //mockuai_demo
        request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
        //yangdongxi
//        request.setParam("appKey", "eb1b83c003bb6f2a938a5815e47e77f7");

        // liutao
//        request.setParam("appKey", "56ff3a489e4553987a56ea2e999a187f");
        request.setCommand(getCommand());
    }

    protected void doExecute() {

        response = seckillService.execute(request);
    }

    protected abstract String getCommand();
}
package com.mockuai.virtualwealthcenter.core;

import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.api.Request;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.core.util.DateUtils;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
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
    protected VirtualWealthService virtualWealthService;
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
//        request.setParam("appKey", "5b036edd2fe8730db1983368a122fb45");
        // hanshu
        request.setParam("appKey", "27c7bc87733c6d253458fa8908001eef");
        //yangdongxi
//        request.setParam("appKey", "eb1b83c003bb6f2a938a5815e47e77f7");

//        request.setParam("appKey", "mockuai_boss");
        // haodada
//        request.setParam("appKey", "1d9063c234c83687ee2b153423c05d0f");
        request.setCommand(getCommand());
    }

    protected void doExecute() {

        response = virtualWealthService.execute(request);
        LOGGER.info("\n {}", JsonUtil.toJson(response));
    }

    protected abstract String getCommand();
}
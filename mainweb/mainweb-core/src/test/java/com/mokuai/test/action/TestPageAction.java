package com.mokuai.test.action; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;
import com.mockuai.mainweb.core.util.JsonUtil;
import com.mokuai.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by hy on 2016/9/22.
 */
public class TestPageAction extends BaseTest {

    @Autowired
    private MainWebService mainWebService;

    private String appKey = "27c7bc87733c6d253458fa8908001eef";


    @Test
    public void testDeletePage(){
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("pageId",169L);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.GET_PUBLISH_PAGE.getActionName());
        Response response =  mainWebService.execute(baseRequest);
        log.info("result:{}", JsonUtil.toJson(response.getModule()));
    }

    private static Gson gson;
    private final static String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    private final static DateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STR);

    static{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.disableHtmlEscaping();//禁止html转义
        gson = gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
    @Test
    public void testUpdatePage(){
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setParam("pageId",23L);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.GET_PAGE.getActionName());
        Response<IndexPageDTO> response = mainWebService.execute(baseRequest);
        log.info("response:{}",gson.toJson(response.getModule()));
    }
    @Test
    public void testShowList(){
        BaseRequest baseRequest = new BaseRequest();
        PageQTO pageQTO = new PageQTO();
        pageQTO.setOffset(1);
        pageQTO.setOffset(20);
        baseRequest.setParam("pageQTO",pageQTO);
        baseRequest.setParam("appKey",appKey);
        baseRequest.setCommand(ActionEnum.SHOW_PAGE_LIST.getActionName());
        Response<IndexPageDTO> response = mainWebService.execute(baseRequest);
        log.info("response:{}",gson.toJson(response.getModule()));

    }
}

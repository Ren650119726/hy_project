package com.mokuai.test;

import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.core.dao.PageDAO;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.manager.PublishPageManager;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class PublishPageTest extends BaseTest{
    private static final Logger log = LoggerFactory.getLogger(PublishPageTest.class);

    @Autowired
    PageDAO pageDAO;
    @Autowired
    private PageManager pageManager;
    @Autowired
    private MainWebService mainWebService;
    @Autowired
    private PublishPageManager publishPageManager;


    @Test
    public void testGetPublishPage(){

        String appKey = "27c7bc87733c6d253458fa8908001ee9";

        Long pageId = 67L;

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_PUBLISH_PAGE.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageId",pageId);
        MainWebResponse componentResponse = (MainWebResponse) mainWebService.execute(request);
//        PublishPageDTO contentSTO = (PublishPageDTO) componentResponse.getModule();
        System.out.println("++++++++++++++++++++++"+componentResponse.getCode()+"+++++++++++++");
        System.out.println("++++++++++++++++++++++"+componentResponse.getMessage()+"+++++++++++++");
        System.out.println("++++++++++++++++++++++"+componentResponse.getModule()+"+++++++++++++");


    }



}

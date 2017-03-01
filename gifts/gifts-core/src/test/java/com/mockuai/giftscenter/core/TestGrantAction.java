package com.mockuai.giftscenter.core; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.common.api.BaseRequest;
import com.mockuai.giftscenter.common.api.GiftsService;
import com.mockuai.giftscenter.common.api.Request;
import com.mockuai.giftscenter.common.constant.ActionEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hy on 2016/7/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestGrantAction    {

    @Autowired
    protected GiftsService giftsService;

    private String appKey = "27c7bc87733c6d253458fa8908001eef";

    @Test
    public void test(){

        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GRANT_ACTION_GIFT.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("actionType",1);
        request.setParam("appType",3);
        request.setParam("receiverId",321758L);
        request.setParam("mobile","15906813673");

        giftsService.execute(request);
    }
}

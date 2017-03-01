package com.mokuai.test;

import com.mockuai.mainweb.common.domain.dto.publish.ContentDTO;
import com.mockuai.mainweb.common.domain.dto.publish.PublishDTO;
import com.mockuai.mainweb.core.util.HttpUtil;
import com.mockuai.mainweb.core.util.HttpUtil_q;
import com.mockuai.mainweb.core.util.JsonUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class HttpUtilTest extends BaseTest{
    private static final Logger log = LoggerFactory.getLogger(HttpUtilTest.class);


    @Test
    public void test1(){

        //http://act.haiyn.com/data/tuijian.json

        String tmsUrl ="http://act.haiyn.com/data/tuijian.json";



        String tmsResponse = HttpUtil_q.sendGet(tmsUrl,"utf-8");

        PublishDTO publishDTO = JsonUtil.parseJson(tmsResponse, PublishDTO.class);

        System.out.println("++++++++++++++++++++++++++++++++"+publishDTO.getData().getComponent().get(0).getValueType());
        List<ContentDTO> contentDTOs = publishDTO.getData().getComponent();
        for (ContentDTO contentDTO : contentDTOs){
            System.out.println("valueType::"+contentDTO.getValueType()+"value:::::"+contentDTO.getValue());
        }




    }


}

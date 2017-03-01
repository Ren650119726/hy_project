package com.mockuai.rainbowcenter.core.manager.impl;

import com.alibaba.fastjson.JSON;
import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.common.dto.DuibaRecordOrderDTO;
import com.mockuai.rainbowcenter.common.qto.DuibaRecordOrderQTO;
import com.mockuai.rainbowcenter.core.dao.DuibaRecordOrderDAO;
import com.mockuai.rainbowcenter.core.domain.DuibaRecordOrderDO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;
import com.mockuai.rainbowcenter.core.manager.RecordOrderManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by lizg on 2016/7/19.
 */

@Service
public class RecordOrderManagerImpl implements RecordOrderManager {

    private static DateFormat DATE_FORMAT =new SimpleDateFormat("yyyyMMddHHmmssS");


    @Override
    public String getBizNum(Long userId) throws RainbowException {
        String bizNum = StringUtils.rightPad(DATE_FORMAT.format(new Date()), 17, "0")+StringUtils.leftPad("" + userId % 1000000,6, "0")
                +genRandomNumber(3);

        return bizNum;
    }

    private String genRandomNumber(int count){
        StringBuffer sb = new StringBuffer();
        String str = "0123456789";
        Random r = new Random();
        for(int i=0;i<count;i++){
            int num = r.nextInt(str.length());
            if(i==0){
                num = r.nextInt(9);
            }

            sb.append(str.charAt(num));
            str = str.replace((str.charAt(num)+""), "");
        }
        return sb.toString();
    }

}

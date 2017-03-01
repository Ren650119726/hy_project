package com.mockuai.rainbowcenter.core.util;

import com.mockuai.rainbowcenter.common.constant.ResponseCode;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by zengzhangqiang on 12/23/15.
 */
public class ParamUtil {
    public static Long parseLong(String value) throws RainbowException{
        if(value == null){
            return null;
        }

        try{
            return Long.valueOf(value);
        }catch(Exception e){
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
                    "param format invalid, value:"+value);
        }
    }

    public static Integer parseInteger(String value) throws RainbowException{
        if(value == null){
            return null;
        }

        try{
            return Integer.valueOf(value);
        }catch(Exception e){
            throw new RainbowException(ResponseCode.PARAM_E_PARAM_FORMAT_INVALID,
                    "param format invalid, value:"+value);
        }
    }
}

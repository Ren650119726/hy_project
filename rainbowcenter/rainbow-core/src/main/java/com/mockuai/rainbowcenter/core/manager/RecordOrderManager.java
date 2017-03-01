package com.mockuai.rainbowcenter.core.manager;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by lizg on 2016/7/19.
 */
public interface RecordOrderManager {


    /**
     * 生成订单号
     * @param userId
     * @return
     */
    public String getBizNum(Long userId) throws RainbowException;


}

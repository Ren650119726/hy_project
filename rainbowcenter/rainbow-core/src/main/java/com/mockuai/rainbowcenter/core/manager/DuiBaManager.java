package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.DistDeductDTO;
import com.mockuai.rainbowcenter.common.dto.DuibaRecordOrderDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

/**
 * Created by lizg on 2016/7/19.
 */
public interface DuiBaManager {

    /**
     * 扣除积分
     * @param duibaRecordOrderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public  DistDeductDTO deductCredits(DuibaRecordOrderDTO duibaRecordOrderDTO, String appKey) throws RainbowException;


    /**
     * 兑换结果通知
     * @param duibaRecordOrderDTO
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public String exchangeResultNotice(DuibaRecordOrderDTO duibaRecordOrderDTO, String appKey) throws RainbowException;


    /**
     * 生成免登录url
     * @param uid
     * @param credits
     * @return
     * @throws com.mockuai.rainbowcenter.core.exception.RainbowException
     */
    public String creditAutoLogin(String uid, String credits) throws RainbowException;



}

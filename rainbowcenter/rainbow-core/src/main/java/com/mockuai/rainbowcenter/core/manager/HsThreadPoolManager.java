package com.mockuai.rainbowcenter.core.manager;

import com.mockuai.rainbowcenter.common.dto.SysConfigDTO;
import com.mockuai.rainbowcenter.core.exception.RainbowException;

import java.util.Map;

/**
 * Created by lizg on 2016/6/6.
 */
public interface HsThreadPoolManager {

    public void itemsOrderDeliverys(Map<String, Map<String, Object>> itemsOrderMap) throws RainbowException;

    public void itemCodesStockSubmite(Map<String, Map<String, String>> itemCodesStockMap) throws RainbowException;

    public  void updateLimitedPurchaseStatus() throws RainbowException;
}

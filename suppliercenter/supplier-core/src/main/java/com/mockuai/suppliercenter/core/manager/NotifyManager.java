package com.mockuai.suppliercenter.core.manager;


/**
 * Created by zengzhangqiang on 7/2/15.
 */
public interface NotifyManager {

    public void notifyBindUserMsg(String mobile, int openType, String openUid, String bizCode);

    public void notifyAddUserMsg(long userId, String bizCode);
}


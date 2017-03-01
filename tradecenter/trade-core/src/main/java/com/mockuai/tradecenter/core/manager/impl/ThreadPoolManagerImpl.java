package com.mockuai.tradecenter.core.manager.impl;

import com.mockuai.tradecenter.core.manager.ThreadPoolManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by zengzhangqiang on 6/23/15.
 */
public class ThreadPoolManagerImpl implements ThreadPoolManager {
    private ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolManagerImpl(){
        threadPoolExecutor = new ThreadPoolExecutor(32, 64, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(128));
    }

    public void execute(Runnable runnable){
        threadPoolExecutor.execute(runnable);
    }
}

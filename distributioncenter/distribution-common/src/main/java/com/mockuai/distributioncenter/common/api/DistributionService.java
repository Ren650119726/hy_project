package com.mockuai.distributioncenter.common.api;

/**
 * Created by duke on 15/10/28.
 */
public interface DistributionService<T> {
    /**
     * 业务处理接口
     * */
    Response<T> execute(Request request);
}

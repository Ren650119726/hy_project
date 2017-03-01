package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemTemplateDTO;

/**
 * Created by yindingyu on 16/2/25.
 */
public interface MigratingClient {

    /**
     * 单店铺升级成多店铺的数据迁移
     *
     * @param sellerId 商城用户的id
     * @param appkey
     * @return
     */
    public Response<Void> single2MultiShopMigrate(Long sellerId, String appkey);
}

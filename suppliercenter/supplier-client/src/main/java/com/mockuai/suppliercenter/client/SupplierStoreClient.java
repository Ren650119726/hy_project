package com.mockuai.suppliercenter.client;

import com.mockuai.suppliercenter.common.api.Response;
import com.mockuai.suppliercenter.common.dto.StoreDTO;
import com.mockuai.suppliercenter.common.qto.StoreQTO;

import java.util.List;

public interface SupplierStoreClient {

    /**
     * 添加仓库
     */
    Response<StoreDTO> addStore(StoreDTO storeDTO, String appKey);

    /**
     * 查询符合查询条件的仓库
     */
    Response<List<StoreDTO>> queryStore(StoreQTO storeQTO, String appKey);

    /**
     * 给订单提供使用，
     * 3、根据商品itemSkuId、supplier_name、store_name获取supplier和store的信息，暂定全表，库存数量一定要放出来
     */

    Response<List<StoreDTO>> queryStoreForOrder(StoreQTO storeQTO, String appKey);

    /**
     * 编辑仓库
     */
    Response<Boolean> updateStore(StoreDTO storeDTO, String appKey);


    /**
     * 禁用仓库
     */
    Response<Boolean> forbiddenStore(Long id, String appKey);


    /**
     * 启用仓库
     */
    Response<Boolean> enableStore(Long id, String appKey);

    /**
     * 删除仓库
     */
    Response<Boolean> deleteStore(StoreDTO storeDTO, String appKey);

}

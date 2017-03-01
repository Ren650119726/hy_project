package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.MemberExportQTO;

/**
 * Created by duke on 15/12/7.
 */
public interface MemberExportClient {
    /**
     * 导出会员
     * @param memberExportQTO
     * @param appKey
     * */
    Response<Boolean> exportMembers(MemberExportQTO memberExportQTO, String appKey);
}

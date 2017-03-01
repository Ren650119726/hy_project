package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.LabelExportQTO;

/**
 * Created by duke on 15/12/7.
 */
public interface LabelExportClient {
    /**
     * 导出标签
     * @param labelExportQTO
     * @param appKey
     * */
    Response<Boolean> exportLabels(LabelExportQTO labelExportQTO, String appKey);
}

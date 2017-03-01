package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.DistributionStatisticsExportQTO;
import com.mockuai.dts.common.domain.DistributionWithdrawRecordExportQTO;

/**
 * Created by duke on 15/12/26.
 */
public interface DistributionExportClient {
    /**
     * 导出分拥统计
     * @param distributionStatisticsExportQTO
     * @param appKey
     * */
    Response<Boolean> exportStatistics(DistributionStatisticsExportQTO distributionStatisticsExportQTO, String appKey);

    /**
     * 导出微小店提现记录
     */
    Response<Boolean> exportWithdrawRecord(DistributionWithdrawRecordExportQTO distributionWithdrawRecordExportQTO,String appKey);
}

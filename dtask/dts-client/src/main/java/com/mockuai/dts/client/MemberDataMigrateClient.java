package com.mockuai.dts.client;

import com.mockuai.dts.common.api.action.Response;
import com.mockuai.dts.common.domain.MemberDataMigrateDTO;

/**
 * Created by duke on 15/12/30.
 */
public interface MemberDataMigrateClient {
    Response<Boolean> dataMigrate(MemberDataMigrateDTO memberDataMigrateDTO, String appKey);
}

package com.mockuai.appcenter.client;

import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.AppTypeEnum;
import com.mockuai.appcenter.common.constant.ValueTypeEnum;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppPropertyDTO;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizPropertyDTO;

/**
 * Created by zengzhangqiang on 6/8/15.
 */
public interface AppClient {
    public Response<AppInfoDTO> addAppInfo(AppInfoDTO appInfoDTO);

    public Response<BizInfoDTO> addBizInfo(BizInfoDTO bizInfoDTO);

    public Response<AppInfoDTO> getAppInfo(String appKey);

    /**
     * 根据域名查看应用信息
     * @param domainName
     * @return
     */
    public Response<AppInfoDTO> getAppInfoByDomain(String domainName, AppTypeEnum appTypeEnum);

    /**
     * 根据应用类型获取指定应用
     * @param bizCode
     * @param appTypeEnum
     * @return
     */
    public Response<AppInfoDTO> getAppInfoByType(String bizCode, AppTypeEnum appTypeEnum);

    /**
     * 更新应用信息
     * @param appInfoDTO
     * @return
     */
    public Response<Void> updateAppInfo(AppInfoDTO appInfoDTO);

    public Response<BizInfoDTO> getBizInfo(String bizCode);

    /**
     * 更新企业信息
     * @param bizInfoDTO
     * @return
     */
    public Response<Void> updateBizInfo(BizInfoDTO bizInfoDTO);

    /**
     * 根据appKey
     * @param appKey
     * @return
     */
    public Response<BizInfoDTO> getBizInfoByAppKey(String appKey);

    public Response<Void> addAppProperty(AppPropertyDTO appPropertyDTO);

    public Response<Void> addBizProperty(BizPropertyDTO bizPropertyDTO);

    public Response<Void> deleteAppProperty(String appKey, String pKey);

    public Response<Void> deleteBizProperty(String bizCode, String pKey);

    public Response<Void> updateBizProperty(String bizCode, String pKey, String value, ValueTypeEnum valueTypeEnum);
}

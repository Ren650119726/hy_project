package com.mockuai.appcenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.AppInfoQTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.appcenter.core.dao.AppInfoDAO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.exception.DAOException;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.CacheManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class AppInfoManagerImpl implements AppInfoManager{
    private static final Logger log = LoggerFactory.getLogger(AppInfoManagerImpl.class);

    @Resource
    private AppInfoDAO appInfoDAO;

    @Override
    public long addAppInfo(AppInfoDO appInfoDO) throws AppException {
        if(appInfoDO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appInfoDO is null");
        }

        if(StringUtils.isBlank(appInfoDO.getBizCode())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(appInfoDO.getAppType() == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appType is null");
        }

        if(StringUtils.isBlank(appInfoDO.getAppKey())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
        }

        if(StringUtils.isBlank(appInfoDO.getAppPwd())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appPwd is null");
        }

        if(StringUtils.isBlank(appInfoDO.getAppName())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appName is null");
        }

        if(StringUtils.isBlank(appInfoDO.getAppVersion())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appVersion is null");
        }

        if(StringUtils.isBlank(appInfoDO.getAdministrator())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "administrator is null");
        }

        if(StringUtils.isBlank(appInfoDO.getMobile())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "mobile is null");
        }

        if(StringUtils.isBlank(appInfoDO.getEmail())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "email is null");
        }

        try{
            return appInfoDAO.addAppInfo(appInfoDO);
        }catch(DAOException e){
            log.error("appInfoDO:{}", JsonUtil.toJson(appInfoDO), e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appInfoDO:{}", JsonUtil.toJson(appInfoDO), e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public AppInfoDO getAppInfo(String appKey) throws AppException {
        if(StringUtils.isBlank(appKey)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appKey is null");
        }

        try{
            AppInfoDO appInfoDO = appInfoDAO.getAppInfo(appKey);
            return appInfoDO;
        }catch(DAOException e){
            log.error("appKey:{}", appKey, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appKey:{}", appKey, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public int updateAppInfo(AppInfoDO appInfoDO) throws AppException {
        if(appInfoDO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appInfoDO is null");
        }

        try{
            return appInfoDAO.updateAppInfo(appInfoDO);
        }catch(DAOException e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public List<AppInfoDO> queryAppInfo(AppInfoQTO appInfoQTO) throws AppException {
        if(appInfoQTO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appInfoQTO is null");
        }

        try{
            return appInfoDAO.queryAppInfo(appInfoQTO);
        }catch(DAOException e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public AppInfoDO getAppInfoByDomain(String domainName) throws AppException {
        if(StringUtils.isBlank(domainName)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "domainName is null");
        }

        try{
            AppInfoDO appInfoDO = appInfoDAO.getAppInfoByDomain(domainName);

            return appInfoDO;
        }catch(DAOException e){
            log.error("domainName:{}", domainName, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("domainName:{}", domainName, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public AppInfoDO getAppInfoByType(String bizCode, int appType) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }
        try{
            AppInfoDO appInfoDO = appInfoDAO.getAppInfoByType(bizCode, appType);

            return appInfoDO;
        }catch(DAOException e){
            log.error("bizCode:{}, appType:{}", bizCode, appType, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("bizCode:{}, appType:{}", bizCode, appType, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public int deleteAppInfo(long appId) throws AppException {
        try{
            //TODO 清理缓存

            return appInfoDAO.deleteAppInfo(appId);
        }catch(DAOException e){
            log.error("appId:{}", appId, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public int deleteAppInfoByBizCode(String bizCode) throws AppException {
        try{
            //TODO 清理缓存

            return appInfoDAO.deleteAppInfoByBizCode(bizCode);
        }catch(DAOException e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    /**
     * 生成appInfo信息的缓存key
     * @param appKey
     * @return
     */
    private String genAppCacheKeyByAppKey(String appKey){
        if(appKey == null){
            return null;
        }

        return "appCache_"+appKey;
    }

    private String genAppCacheKeyByDomain(String domainName){
        if(domainName == null){
            return null;
        }

        return "appCache_"+domainName;
    }

    private String genAppCacheKeyByAppType(String bizCode, int appType){
        if(bizCode == null){
            return null;
        }

        return "appCache_"+bizCode+"_"+appType;
    }
}

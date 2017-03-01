package com.mockuai.appcenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.appcenter.core.dao.AppPropertyDAO;
import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.exception.DAOException;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class AppPropertyManagerImpl implements AppPropertyManager{
    private static final Logger log = LoggerFactory.getLogger(AppInfoManagerImpl.class);

    @Resource
    private AppPropertyDAO appPropertyDAO;

    @Override
    public long addAppProperty(AppPropertyDO appPropertyDO) throws AppException {
        if(appPropertyDO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appPropertyDO is null");
        }

        try{
            return appPropertyDAO.addAppProperty(appPropertyDO);
        }catch(DAOException e){
            log.error("appPropertyDO:{}", JsonUtil.toJson(appPropertyDO), e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appPropertyDO:{}", JsonUtil.toJson(appPropertyDO), e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public List<AppPropertyDO> getAppPropertyList(Long appId) throws AppException {
        if(appId == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appId is null");
        }

        try{
            return appPropertyDAO.getAppPropertyList(appId);
        }catch(DAOException e){
            log.error("appId:{}", appId, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appId:{}", appId, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public AppPropertyDO getAppProperty(Long appId, String pKey) throws AppException {
        if(appId == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appId is null");
        }

        if(StringUtils.isBlank(pKey)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }


        try{
            return appPropertyDAO.getAppProperty(appId, pKey);
        }catch(DAOException e){
            log.error("appId:{}, pKey:{}", appId, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appId:{}, pKey:{}", appId, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public int deleteAppProperty(Long appId, String pKey) throws AppException {
        if(appId == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appId is null");
        }

        if(StringUtils.isBlank(pKey)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }

        try{
            return appPropertyDAO.deleteAppProperty(appId, pKey);
        }catch(DAOException e){
            log.error("appId:{}, pKey:{}", appId, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appId:{}, pKey:{}", appId, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    @Override
    public int deleteAppPropertyByAppId(Long appId) throws AppException {
        if(appId == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "appId is null");
        }

        try{
            return appPropertyDAO.deleteAppPropertyByAppId(appId);
        }catch(DAOException e){
            log.error("appId:{}", appId, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("appId:{}", appId, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public int deleteAppPropertyByBizCode(String bizCode) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        try{
            return appPropertyDAO.deleteAppPropertyByBizCode(bizCode);
        }catch(DAOException e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }
}

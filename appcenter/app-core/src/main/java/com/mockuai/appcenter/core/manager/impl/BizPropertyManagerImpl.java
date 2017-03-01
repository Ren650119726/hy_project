package com.mockuai.appcenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.appcenter.core.dao.BizPropertyDAO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.exception.DAOException;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class BizPropertyManagerImpl implements BizPropertyManager{
    private static final Logger log = LoggerFactory.getLogger(BizInfoManagerImpl.class);

    @Resource
    private BizPropertyDAO bizPropertyDAO;

    @Override
    public long addBizProperty(BizPropertyDO bizPropertyDO) throws AppException {
        if(bizPropertyDO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizPropertyDO is null");
        }

        try{
            return bizPropertyDAO.addBizProperty(bizPropertyDO);
        }catch(DAOException e){
            log.error("bizPropertyDO:{}", JsonUtil.toJson(bizPropertyDO), e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        } catch(Exception e){
            log.error("bizPropertyDO:{}", JsonUtil.toJson(bizPropertyDO), e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public List<BizPropertyDO> getBizPropertyList(String bizCode) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }


        try{
            return bizPropertyDAO.getBizPropertyList(bizCode);
        }catch(DAOException e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        } catch(Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public BizPropertyDO getBizProperty(String bizCode, String pKey) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(StringUtils.isBlank(pKey)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }

        try{
            return bizPropertyDAO.getBizProperty(bizCode, pKey);
        }catch(DAOException e){
            log.error("bizCode:{}, pKey:{}", bizCode, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        } catch(Exception e){
            log.error("bizCode:{}, pKey:{}", bizCode, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public int deleteBizProperty(String bizCode, String pKey) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(StringUtils.isBlank(pKey)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }

        try{
            return bizPropertyDAO.deleteBizProperty(bizCode, pKey);
        }catch(DAOException e){
            log.error("bizCode:{}, pKey:{}", bizCode, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch(Exception e){
            log.error("bizCode:{}, pKey:{}", bizCode, pKey, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public int updateBizProperty(BizPropertyDO bizPropertyDO) throws AppException {
        if(bizPropertyDO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizPropertyDO is null");
        }
        if(StringUtils.isBlank(bizPropertyDO.getBizCode())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        if(StringUtils.isBlank(bizPropertyDO.getpKey())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "pKey is null");
        }

        try{
            return bizPropertyDAO.updateBizProperty(bizPropertyDO);
        }catch(DAOException e){
            log.error("bizCode:{}, pKey:{}", bizPropertyDO.getBizCode(), bizPropertyDO.getpKey(), e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch(Exception e){
            log.error("bizCode:{}, pKey:{}", bizPropertyDO.getBizCode(), bizPropertyDO.getpKey(), e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public int deleteBizProperties(String bizCode) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        try{
            return bizPropertyDAO.deleteBizProperties(bizCode);
        }catch(DAOException e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch(Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }
}

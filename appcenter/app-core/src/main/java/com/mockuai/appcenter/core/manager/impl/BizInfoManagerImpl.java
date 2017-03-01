package com.mockuai.appcenter.core.manager.impl;

import com.mockuai.appcenter.common.constant.ResponseCode;
import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.common.util.JsonUtil;
import com.mockuai.appcenter.core.dao.BizInfoDAO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.exception.AppException;
import com.mockuai.appcenter.core.exception.DAOException;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public class BizInfoManagerImpl implements BizInfoManager{
    private static final Logger log = LoggerFactory.getLogger(BizInfoManagerImpl.class);

    @Resource
    private BizInfoDAO bizInfoDAO;

    @Override
    public long addBizInfo(BizInfoDO bizInfoDO) throws AppException {
        if(bizInfoDO == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizInfoDO is null");
        }

        if(StringUtils.isBlank(bizInfoDO.getBizCode())){
            bizInfoDO.setBizCode(genBizCode());
        }

        if(bizInfoDO.getBizType() == null){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizType is null");
        }

        if(StringUtils.isBlank(bizInfoDO.getBizName())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizName is null");
        }

        if(StringUtils.isBlank(bizInfoDO.getAdministrator())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "administrator is null");
        }

        if(StringUtils.isBlank(bizInfoDO.getMobile())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "mobile is null");
        }

        if(StringUtils.isBlank(bizInfoDO.getEmail())){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "email is null");
        }

        try{
            //判断指定bizCode是否被已有业务占用
            BizInfoDO getResult = bizInfoDAO.getBizInfo(bizInfoDO.getBizCode());
            if(getResult != null){
                throw new AppException(ResponseCode.BIZ_E_BIZ_CODE_CONFLICT_WITH_OTHER_BIZ);
            }

            return bizInfoDAO.addBizInfo(bizInfoDO);
        }catch(DAOException e){
            log.error("bizInfoDO:{}", JsonUtil.toJson(bizInfoDO), e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("bizInfoDO:{}", JsonUtil.toJson(bizInfoDO), e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public List<BizInfoDO> queryBizInfo(BizInfoQTO bizInfoQTO) throws AppException {
        try{
            return bizInfoDAO.queryBizInfo(bizInfoQTO);
        }catch(DAOException e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public long queryBizInfoCount(BizInfoQTO bizInfoQTO) throws AppException {
        try{
            return bizInfoDAO.queryBizInfoCount(bizInfoQTO);
        }catch(DAOException e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public int updateBizInfo(BizInfoDO bizInfoDO) throws AppException {
        try{
            return bizInfoDAO.updateBizInfo(bizInfoDO);
        }catch(DAOException e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("", e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }

    @Override
    public BizInfoDO getBizInfo(String bizCode) throws AppException {
        if(StringUtils.isBlank(bizCode)){
            throw new AppException(ResponseCode.PARAM_E_PARAM_MISSING, "bizCode is null");
        }

        try{
            return bizInfoDAO.getBizInfo(bizCode);
        }catch(DAOException e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }

    }

    private String genBizCode(){
        String bizCode = DigestUtils.md5Hex(""+System.currentTimeMillis());
        return bizCode;
    }

    @Override
    public int deleteBizInfo(String bizCode) throws AppException {
        try{
            return bizInfoDAO.deleteBizInfo(bizCode);
        }catch(DAOException e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DATABASE_ERROR);
        }catch (Exception e){
            log.error("bizCode:{}", bizCode, e);
            throw new AppException(ResponseCode.SYS_E_DEFAULT_ERROR);
        }
    }
}

package com.mockuai.appcenter.core.manager;

import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.AppException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface BizPropertyManager {
    /**
     *新增业务点属性
     * @param bizPropertyDO
     * @return
     * @throws com.mockuai.appcenter.core.exception.AppException
     */
    public long addBizProperty(BizPropertyDO bizPropertyDO) throws AppException;

    /**
     * 查询指定业务点的属性列表
     * @param bizCode
     * @return
     * @throws com.mockuai.appcenter.core.exception.AppException
     */
    public List<BizPropertyDO> getBizPropertyList(String bizCode) throws AppException;

    /**
     * 查询指定业务点的指定属性
     * @param bizCode
     * @param pKey
     * @return
     * @throws com.mockuai.appcenter.core.exception.AppException
     */
    public BizPropertyDO getBizProperty(String bizCode, String pKey) throws AppException;

    /**
     * 删除指定业务点的指定属性
     * @param bizCode
     * @param pKey
     * @return
     * @throws com.mockuai.appcenter.core.exception.AppException
     */
    public int deleteBizProperty(String bizCode, String pKey) throws AppException;


    /**
     * 更新企业配置信息
     * @param bizPropertyDO
     * @return
     * @throws AppException
     */
    public int updateBizProperty(BizPropertyDO bizPropertyDO) throws AppException;

    /**
     *
     * @param bizCode
     * @return
     * @throws AppException
     */
    public int deleteBizProperties(String bizCode) throws AppException;
}

package com.mockuai.appcenter.core.dao;

import com.mockuai.appcenter.core.domain.AppPropertyDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.exception.DAOException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface BizPropertyDAO {

    /**
     *新增biz属性
     * @param bizPropertyDO
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public long addBizProperty(BizPropertyDO bizPropertyDO) throws DAOException;

    /**
     * 查询指定biz的属性列表
     * @param bizCode
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public List<BizPropertyDO> getBizPropertyList(String bizCode) throws DAOException;

    /**
     * 查询指定biz的指定属性
     * @param bizCode
     * @param pKey
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public BizPropertyDO getBizProperty(String bizCode, String pKey) throws DAOException;

    /**
     * 删除指定biz的指定属性
     * @param bizCode
     * @param pKey
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public int deleteBizProperty(String bizCode, String pKey) throws DAOException;

    /**
     * 更新企业配置信息
     * @param bizPropertyDO
     * @return
     * @throws DAOException
     */
    public int updateBizProperty(BizPropertyDO bizPropertyDO) throws DAOException;

    /**
     * 删除指定biz的所有属性
     * @param bizCode
     * @return
     * @throws DAOException
     */
    public int deleteBizProperties(String bizCode) throws DAOException;
}

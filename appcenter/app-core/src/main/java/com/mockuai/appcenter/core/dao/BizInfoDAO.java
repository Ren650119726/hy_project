package com.mockuai.appcenter.core.dao;

import com.mockuai.appcenter.common.domain.BaseQTO;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.exception.DAOException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface BizInfoDAO {
    /**
     * 新增业务信息
     * @param bizInfoDO
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public long addBizInfo(BizInfoDO bizInfoDO) throws DAOException;

    /**
     * 根据bizCode查询业务信息
     * @param bizCode
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public BizInfoDO getBizInfo(String bizCode) throws DAOException;

    /**
     * 更新企业信息
     * @param bizInfoDO
     * @return
     * @throws DAOException
     */
    public int updateBizInfo(BizInfoDO bizInfoDO) throws DAOException;

    /**
     * 查询企业信息
     * @param bizInfoQTO
     * @return
     * @throws DAOException
     */
    public List<BizInfoDO> queryBizInfo(BizInfoQTO bizInfoQTO) throws DAOException;

    /**
     * 查询指定条件的企业信息总数
     * @param bizInfoQTO
     * @return
     * @throws DAOException
     */
    public long queryBizInfoCount(BizInfoQTO bizInfoQTO) throws DAOException;

    /**
     * 删除业务信息
     * @param bizCode
     * @return
     * @throws com.mockuai.appcenter.core.exception.DAOException
     */
    public int deleteBizInfo(String bizCode) throws DAOException;
}

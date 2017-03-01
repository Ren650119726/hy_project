package com.mockuai.appcenter.core.manager;

import com.mockuai.appcenter.common.domain.BizInfoDTO;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.exception.AppException;

import java.util.List;

/**
 * Created by zengzhangqiang on 6/7/15.
 */
public interface BizInfoManager {
    /**
     * 新增业务信息
     * @param bizInfoDO
     * @return
     * @throws com.mockuai.appcenter.core.exception.AppException
     */
    public long addBizInfo(BizInfoDO bizInfoDO) throws AppException;

    /**
     * 查询企业信息
     * @param bizInfoQTO
     * @return
     * @throws AppException
     */
    public List<BizInfoDO> queryBizInfo(BizInfoQTO bizInfoQTO) throws AppException;

    /**
     * 查询指定条件的企业信息总数
     * @param bizInfoQTO
     * @return
     * @throws AppException
     */
    public long queryBizInfoCount(BizInfoQTO bizInfoQTO) throws AppException;

    /**
     * 更新指定企业信息
     * @param bizInfoDO
     * @return
     * @throws AppException
     */
    public int updateBizInfo(BizInfoDO bizInfoDO) throws AppException;

    /**
     * 根据bizCode查询业务信息
     * @param bizCode
     * @return
     * @throws AppException
     */
    public BizInfoDO getBizInfo(String bizCode) throws AppException;

    /**
     * 删除业务信息
     * @param bizCode
     * @return
     * @throws AppException
     */
    public int deleteBizInfo(String bizCode) throws AppException;

}

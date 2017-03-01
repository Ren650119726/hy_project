package com.mockuai.usercenter.core.manager;

import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.exception.UserException;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/5/15.
 */
public interface EnterpriseAuthExtendManager {
    /**
     * 新增企业认证扩展信息
     * @param enterpriseAuthExtendDO
     * @return
     * @throws UserException
     */
    public Long addEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO) throws UserException;

    /**
     * 获取企业认证扩展信息
     * @param userId
     * @return
     * @throws UserException
     */
    public EnterpriseAuthExtendDO getEnterpriseAuthExtend(long userId) throws UserException;

    /**
     * 查询企业认证扩展信息
     * @param userIdList
     * @return
     * @throws UserException
     */
    public List<EnterpriseAuthExtendDO> queryEnterpriseAuthExtend(List<Long> userIdList) throws UserException;

    /**
     * 更新企业认证扩展信息
     *
     * @param enterpriseAuthExtendDO
     * @throws UserException
     * */
    public int updateEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO) throws UserException;
}

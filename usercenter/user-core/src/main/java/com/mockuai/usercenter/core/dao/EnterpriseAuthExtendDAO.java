package com.mockuai.usercenter.core.dao;

import com.mockuai.usercenter.core.domain.EnterpriseAuthExtendDO;
import com.mockuai.usercenter.core.exception.UserException;
import com.mockuai.usercenter.core.manager.EnterpriseAuthExtendManager;

import java.util.List;

/**
 * Created by zengzhangqiang on 8/6/15.
 */
public interface EnterpriseAuthExtendDAO {
    /**
     * 新增企业认证扩展信息
     * @param enterpriseAuthExtendDO
     * @return
     * @throws com.mockuai.usercenter.core.exception.UserException
     */
    public Long addEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO);

    /**
     * 获取企业认证扩展信息
     * @param userId
     * @return
     * @throws UserException
     */
    public EnterpriseAuthExtendDO getEnterpriseAuthExtend(long userId);

    /**
     * 查询企业认证扩展信息
     * @param userIdList
     * @return
     * @throws UserException
     */
    public List<EnterpriseAuthExtendDO> queryEnterpriseAuthExtend(List<Long> userIdList);

    /**
     * 更新企业认证扩展信息
     *
     * @param enterpriseAuthExtendDO
     * @throws UserException
     * */
    public int updateEnterpriseAuthExtend(EnterpriseAuthExtendDO enterpriseAuthExtendDO);
}

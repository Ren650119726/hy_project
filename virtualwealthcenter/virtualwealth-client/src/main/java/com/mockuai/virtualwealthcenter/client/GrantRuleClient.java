package com.mockuai.virtualwealthcenter.client;

import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;

import java.util.List;

/**
 * 虚拟财富发放规则相关
 * <p/>
 * Created by edgar.zr on 12/12/15.
 */
public interface GrantRuleClient {

    /**
     * 创建发放放规则
     *
     * @param grantRuleDTO
     * @param appKey
     * @return
     */
    Response<Long> addGrantRule(GrantRuleDTO grantRuleDTO, String appKey);

    /**
     * 查看财富发放规则
     *
     * @param ownerId
     * @param sourceType
     * @param wealthType
     * @param creatorId
     * @param appKey
     * @return
     */
    Response<GrantRuleDTO> getGrantRuleByOwner(Long ownerId, Integer sourceType, Integer wealthType, Long creatorId, String appKey);

    /**
     * 根据发放规则发放财富
     *
     * @param grantedWealthDTO
     * @param appKey
     * @return
     */
    Response<Void> grantVirtualWealthWithGrantRule(GrantedWealthDTO grantedWealthDTO, String appKey);

    /**
     * 添加虚拟财富发放规则
     *
     * @param grantRuleDTO
     * @param appKey
     * @return
     */
    Response<Long> addVirtualWealthGrantRule(GrantRuleDTO grantRuleDTO, String appKey);

    /**
     * 删除虚拟财富发放规则
     *
     * @param grantRuleId
     * @param creatorId
     * @param appKey
     * @return
     */
    Response<Boolean> deleteVirtualWealthGrantRule(Long grantRuleId, Long creatorId, String appKey);

    /**
     * 更新虚拟财富发放规则
     *
     * @param grantRuleDTO
     * @param appKey
     * @return
     */
    Response<Boolean> updateVirtualWealthGrantRule(GrantRuleDTO grantRuleDTO, String appKey);

    /**
     * 查看虚拟财富发放规则
     *
     * @param grantRuleId
     * @param creatorId
     * @param appKey
     * @return
     */
    Response<GrantRuleDTO> getVirtualWealthGrantRule(Long grantRuleId, Long creatorId, String appKey);

    /**
     * 查询虚拟财富发放规则
     *
     * @param wealthType
     * @param sourceType
     * @param creatorId
     * @param appKey
     * @return
     */
    Response<List<GrantRuleDTO>> queryVirtualWealthGrantRule(Integer wealthType, Integer sourceType, Long creatorId, String appKey);

    /**
     * 开关虚拟财富发放规则
     *
     * @param grantRuleId
     * @param creatorId
     * @param status
     * @param appKey
     * @return
     */
    Response<Boolean> switchVirtualWealthGrantRule(Long grantRuleId, Long creatorId, Integer status, String appKey);
}
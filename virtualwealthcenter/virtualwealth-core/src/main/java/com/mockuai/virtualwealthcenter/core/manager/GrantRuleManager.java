package com.mockuai.virtualwealthcenter.core.manager;

import com.mockuai.virtualwealthcenter.common.domain.dto.GrantRuleDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantRuleQTO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;

import java.util.List;

/**
 * Created by edgar.zr on 11/11/15.
 */
public interface GrantRuleManager {

    /**
     * 新增财富发放规则
     *
     * @param grantRuleDTO
     * @throws VirtualWealthException
     */
    void addGrantRule(GrantRuleDTO grantRuleDTO) throws VirtualWealthException;

    /**
     * 查看财富发放规则
     *
     * @param bizCode
     * @param grantRuleId
     * @param creatorId
     * @return
     * @throws VirtualWealthException
     */
    GrantRuleDTO getGrantRule(String bizCode, Long grantRuleId, Long creatorId) throws VirtualWealthException;

    /**
     * 只获取开启的发放规则
     * 发放组合
     * 比如：
     * 根据订单发放积分，有一个积分集合，通过 wealthType/sourceType 来确定集合中 status == 1 的发放规则
     *
     * @param bizCode
     * @param wealthType
     * @param sourceType
     * @return
     * @throws VirtualWealthException
     */
    GrantRuleDTO getGrantRuleStatusOn(String bizCode, Integer wealthType, Integer sourceType) throws VirtualWealthException;

    /**
     * 通过 ownerId 查找发放规则
     *
     * @param bizCode
     * @param ownerId
     * @param wealthType
     * @param sourceType
     * @return
     * @throws VirtualWealthException
     */
    GrantRuleDTO getGrantRuleByOwnerId(String bizCode, Long ownerId, Integer wealthType, Integer sourceType) throws VirtualWealthException;

    /**
     * 查看财富发放规则列表
     * wealthType/sourceType/bizCode 查询一个属主的多个发放规则
     *
     * @param grantRuleQTO
     * @return
     * @throws VirtualWealthException
     */
    List<GrantRuleDTO> queryGrantRule(GrantRuleQTO grantRuleQTO) throws VirtualWealthException;

    /**
     * 删除财富发放规则
     *
     * @param grantRuleId
     * @param bizCode
     * @param creatorId   只做记录，不做实际操作
     * @return
     * @throws VirtualWealthException
     */
    void deleteGrantRule(Long grantRuleId, String bizCode, Long creatorId) throws VirtualWealthException;

    /**
     * 更新财富发放规则
     *
     * @param grantRuleDTO
     * @throws VirtualWealthException
     */
    void updateGrantRule(GrantRuleDTO grantRuleDTO) throws VirtualWealthException;

    /**
     * 打开或者关闭财富发送规则
     * 一种财富类型下同一种数据来源同时只能有一个开着的发送规则
     *
     * @param bizCode
     * @param grantRuleId
     * @param creatorId
     * @param status
     * @throws VirtualWealthException
     */
    void switchGrantRule(String bizCode, Long grantRuleId, Long creatorId, Integer status) throws VirtualWealthException;

    /**
     * fake 一个财富发放规则
     *
     * @param wealthType
     * @param sourceType
     * @return
     * @throws VirtualWealthException
     */
    List<GrantRuleDTO> fakeGrantRule(Integer wealthType, Integer sourceType) throws VirtualWealthException;
}
package com.mockuai.virtualwealthcenter.client;

import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.TotalWealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.UsedWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WealthAccountDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsConfigDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBalanceItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBalanceItemDetailDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossBankInfoItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossUserAuthonDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossVirtualItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossVirtualItemDetailDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.boss.BossWithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopUserAuthonAppDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.BankInfoQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.GrantedWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UsedWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.UserAuthonQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsConfigQTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemQTO;

import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 12/21/15.
 */
public interface VirtualWealthClient {

    Response<List<WithdrawalsItemDTO>> findWithdrawalsItem(WithdrawalsItemQTO qto, String appKey);

    Response<Boolean> updateWithdrawalsItem(WithdrawalsItemQTO qto, String appKey);

    Response<Boolean> AddWithdrawalsConfig(WithdrawalsConfigQTO qto, String appKey);

    Response<List<WithdrawalsConfigDTO>> findWithdrawalsConfig(WithdrawalsConfigQTO qto, String appKey);

    /**
     * 为用户添加账号
     *
     * @param wealthAccountDTO
     * @param appKey
     * @return
     */
    Response<Boolean> addWealthAccount(WealthAccountDTO wealthAccountDTO, String appKey);

    /**
     * 查询用户账号
     *
     * @param userId
     * @param wealthType
     * @param appKey
     * @return
     */
    Response<List<WealthAccountDTO>> queryWealthAccount(Long userId, Integer wealthType, String appKey);

    /**
     * 更新虚拟财富
     *
     * @param virtualWealthDTO
     * @param appKey
     * @return
     */
    Response<Boolean> updateVirtualWealth(VirtualWealthDTO virtualWealthDTO, String appKey);

    /**
     * 查看虚拟财富
     *
     * @param creatorId
     * @param wealthType
     * @param appKey
     * @return
     */
    Response<VirtualWealthDTO> getVirtualWealth(Long creatorId, Integer wealthType, String appKey);

    /**
     * 查询虚拟财富
     *
     * @param virtualWealthQTO
     * @param appKey
     * @return
     */
    Response<List<VirtualWealthDTO>> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO, String appKey);

    /**
     * 预使用用户虚拟财富
     *
     * @param userId
     * @param wealthType
     * @param useAmount
     * @param orderId
     * @param appKey
     * @return
     */
    Response<Void> preUseUserWealth(Long userId, Integer wealthType, Long useAmount, Long orderId,
//                                    Long sellerId,
                                    String appKey);

    /**
     * 批量预使用虚拟财富
     *
     * @param userId
     * @param usedWealthDTOs wealthType, useAmount, orderId
     * @param appKey
     * @return
     */
    Response<Void> preUseMultiUserWealth(Long userId, List<UsedWealthDTO> usedWealthDTOs, String appKey);

    /**
     * 使用用户虚拟财富
     *
     * @param userId
     * @param orderId
     * @param appKey
     * @return
     */
    Response<Void> useUserWealth(Long userId, Long orderId, String appKey);

    /**
     * 批量使用虚拟财富
     *
     * @param orderIds
     * @param userId
     * @param appKey
     * @return
     */
    Response<Void> useMultiUserWealth(List<Long> orderIds, Long userId, String appKey);

    /**
     * 释放用户虚拟财富
     *
     * @param userId
     * @param orderId
     * @param appKey
     * @return
     */
    Response<Void> releaseUsedWealth(Long userId, Long orderId, String appKey);

    /**
     * 批量释放用户虚拟财富
     *
     * @param orderIds
     * @param userId
     * @param appKey
     * @return
     */
    Response<Void> releaseMultiUsedWealth(List<Long> orderIds, Long userId, String appKey);

    /**
     * 退回使用掉的财富值
     * 每次退一个商品的财富
     *
     * @param userId
     * @param orderId
     * @param itemId
     * @param amounts key：财富类型， value 对应的退还量
     * @param appKey
     * @return
     */
    Response<Void> returnUsedWealth(Long userId,
//                                    Long sellerId,
                                    Long orderId,
                                    Long itemId,
//                                    Long skuId,
//                                    String itemName,
                                    Map<Integer, Long> amounts, String appKey);

    /**
     * 发放虚拟财富, 带 orderId
     *
     * @param creatorId
     * @param wealthType  财富类型
     * @param sourceType  发放来源
     * @param grantAmount 发放总量
     * @param receiverId  接收者
     * @param appKey
     * @return
     */
    Response<Void> grantVirtualWealth(Long creatorId, Integer wealthType, Integer sourceType,
                                      Long grantAmount, Long receiverId, Long orderId, String appKey);

    /**
     * 发放虚拟财富
     *
     * @param creatorId
     * @param wealthType
     * @param sourceType
     * @param grantAmount
     * @param receiverId
     * @param appKey
     * @return
     */
    Response<Void> grantVirtualWealth(Long creatorId, Integer wealthType, Integer sourceType,
                                      Long grantAmount, Long receiverId, String appKey);

    /**
     * 初始化平台虚拟财富
     *
     * @param sellerId
     * @param appKey
     * @return
     */
    Response<Void> initVirtualWealth(Long sellerId, String appKey);

    /**
     * 分佣发放
     *
     * @param grantedWealthDTO
     * @param appKey
     * @return
     */
    Response<Boolean> distributorGrant(GrantedWealthDTO grantedWealthDTO, String appKey);

    /**
     * 更新分佣下发放的虚拟财富
     *
     * @param orderId
     * @param sourceType
     * @param status
     * @param appKey
     * @return
     */
    Response<Boolean> updateStatusOfVirtualWealthDistributorGranted(Long orderId, Long skuId, Integer sourceType, Integer status, String appKey);

    /**
     * 查询累计收入,可用嗨币+冻结嗨币
     *
     * @param userId
     * @param appKey
     * @return
     */
    Response<TotalWealthAccountDTO> queryTotalVirtualWealthCombine(Long userId, String appKey);

    /**
     * 查询指定用户总累计
     *
     * @param userIds
     * @param wealthType
     * @param appKey
     * @return
     */
    Response<List<WealthAccountDTO>> listTotalVirtualWealth(List<Long> userIds, Integer wealthType, String appKey);
    
    /**
     * 客户管理 获取提现记录
     * @author Administrator
     *
     */
    Response<List<BossWithdrawalsItemDTO>> FindCustomerWithdrawalsPageList(WithdrawalsItemQTO withdrawalsItemQTO,String appKey);
    
    
    /**
     * 客户管理 余额支出
     * @author Administrator
     */
    Response<List<BossBalanceItemDTO>> FindCustomerBUsedPagePageList(UsedWealthQTO usedWealthQTO,String appKey);
    
    
    /**
     * 客户管理 嗨币支出
     * @param usedWealthQTO
     * @return
     */
    Response<List<BossVirtualItemDTO>>FindCustomerVUsedPagePageList(UsedWealthQTO usedWealthQTO,String appKey);
    
    /**
     * 客户管理 余额收入
     * @author Administrator
     *
     */
    Response<List<BossBalanceItemDTO>> FindCustomerBGrantedPageList(GrantedWealthQTO grantedWealthQTO,String appKey);
    
    /**
     * 客户管理 嗨币收入
     * @param grantedWealthQTO
     * @return
     */
    Response<List<BossVirtualItemDTO>> FindCustomerVGrantedPageList(GrantedWealthQTO grantedWealthQTO,String appKey);
    
    /**
     * 客户管理 -银行卡管理 -流水
     * @author Administrator
     *
     */
    Response<List<BossBankInfoItemDTO>> FindCustomerBankInfoPageList(BankInfoQTO bankInfoQTO,String appKey);
    

    /**
     * 客户管理 嗨币 详情  overTime = 当前时间-10个月
     */
    Response<BossVirtualItemDetailDTO> FindCustomerVirtualDetailAction(Long userId,String overTime,String appKey);
    
    /**
     * 客户管理 余额详情
     */
    Response<BossBalanceItemDetailDTO> FindCustomerBalanceDetailAction(Long userId,String appKey);
    /**
     * 查询审核列表和总页数
     */
    Response<List<BossUserAuthonDTO>> ManualAuditListAction(UserAuthonQTO userAuthonQTO,String appKey);
    /**
     * 通过实名认证
     * @return 
     */
    Response<Integer> AcceptAuditing(Long id,String authonStatus,String appKey);
    /**
     * 拒绝实名认证
     */
    Response<Integer> RefuseAuditing(Long id,String authonStatus,String authonText,String appKey);
    /**
     * 审核状态查询
     * @param userId
     * @param appKey
     * @return
     */
    Response<MopUserAuthonAppDTO>  SelectAuditStatus(String userId,String appKey); 
    /**
     * 用户更改用户名时，同步到实名认证表中
     * @param userId
     * @param authonMobile
     * @return
     */
    Response<Long> SyncPhoneNumber (Long userId,String authonMobile);
}
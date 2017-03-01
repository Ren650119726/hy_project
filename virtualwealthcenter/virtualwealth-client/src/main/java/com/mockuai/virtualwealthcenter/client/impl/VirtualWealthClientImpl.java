package com.mockuai.virtualwealthcenter.client.impl;

import com.mockuai.virtualwealthcenter.client.VirtualWealthClient;
import com.mockuai.virtualwealthcenter.common.api.BaseRequest;
import com.mockuai.virtualwealthcenter.common.api.Response;
import com.mockuai.virtualwealthcenter.common.api.VirtualWealthService;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
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

import javax.annotation.Resource;


import java.util.List;
import java.util.Map;

/**
 * Created by edgar.zr on 12/21/15.
 */
public class VirtualWealthClientImpl implements VirtualWealthClient {

    @Resource
    VirtualWealthService virtualWealthService;

    /**
     * 提现记录
     *
     * @param qto
     * @param appKey
     * @return
     */
    public Response<List<WithdrawalsItemDTO>> findWithdrawalsItem(WithdrawalsItemQTO qto, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_WITHDRAWALS_ITEM.getActionName());
        baseRequest.setParam("withdrawalsItem", qto);
        baseRequest.setParam("appKey", appKey);

        return virtualWealthService.execute(baseRequest);
    }

    /**
     * 同意，拒绝，打款。主要做提现状态的一些变更和修改。
     *
     * @param qto
     * @param appKey
     * @return
     */
    public Response<Boolean> updateWithdrawalsItem(WithdrawalsItemQTO qto, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UPDATE_WITHDRAWALS_ITEM.getActionName());
        baseRequest.setParam("withdrawalsItem", qto);
        baseRequest.setParam("appKey", appKey);
        return virtualWealthService.execute(baseRequest);
    }

    /**
     * 保存提现配置
     *
     * @param qto
     * @param appKey
     * @return
     */
    public Response<Boolean> AddWithdrawalsConfig(WithdrawalsConfigQTO qto, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_WITHDRAWALS_CONFIG.getActionName());
        baseRequest.setParam("withdrawalsConfig", qto);
        baseRequest.setParam("appKey", appKey);
        return virtualWealthService.execute(baseRequest);
    }

    public Response<List<WithdrawalsConfigDTO>> findWithdrawalsConfig(WithdrawalsConfigQTO qto, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_WITHDRAWALS_CONFIG.getActionName());
        baseRequest.setParam("withdrawalsConfig", qto);
        baseRequest.setParam("appKey", appKey);
        return virtualWealthService.execute(baseRequest);
    }

    public Response<Boolean> addWealthAccount(WealthAccountDTO wealthAccountDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.ADD_WEALTH_ACCOUNT.getActionName());
        baseRequest.setParam("wealthAccountDTO", wealthAccountDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<List<WealthAccountDTO>> queryWealthAccount(Long userId, Integer wealthType, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.QUERY_WEALTH_ACCOUNT.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("appKey", appKey);
        Response<List<WealthAccountDTO>> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> updateVirtualWealth(VirtualWealthDTO virtualWealthDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UPDATE_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("virtualWealthDTO", virtualWealthDTO);
        baseRequest.setParam("appKey", appKey);
        Response<Boolean> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<VirtualWealthDTO> getVirtualWealth(Long creatorId, Integer wealthType, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GET_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("creatorId", creatorId);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("appKey", appKey);
        Response<VirtualWealthDTO> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<List<VirtualWealthDTO>> queryVirtualWealth(VirtualWealthQTO virtualWealthQTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.QUERY_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("virtualWealthQTO", virtualWealthQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<VirtualWealthDTO>> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> preUseUserWealth(Long userId, Integer wealthType, Long useAmount, Long orderId,
//                                           Long sellerId,
                                           String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.PRE_USE_USER_WEALTH.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("useAmount", useAmount);
        baseRequest.setParam("orderId", orderId);
//        baseRequest.setParam("sellerId", sellerId);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> preUseMultiUserWealth(Long userId, List<UsedWealthDTO> usedWealthDTOs, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.PRE_USE_MULTI_USER_WEALTH.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("usedWealthDTOs", usedWealthDTOs);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> useUserWealth(Long userId, Long orderId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.USE_USER_WEALTH.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> useMultiUserWealth(List<Long> orderIds, Long userId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.USE_MULTI_USER_WEALTH.getActionName());
        baseRequest.setParam("orderIds", orderIds);
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> releaseUsedWealth(Long userId, Long orderId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.RELEASE_USED_WEALTH.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> releaseMultiUsedWealth(List<Long> orderIds, Long userId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.RELEASE_MULTI_USED_WEALTH.getActionName());
        baseRequest.setParam("orderIds", orderIds);
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> returnUsedWealth(Long userId,
//                                           Long sellerId,
                                           Long orderId,
                                           Long itemId,
//                                           Long skuId,
//                                           String itemName,
                                           Map<Integer, Long> amounts, String appKey) {

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GIVE_BACK_PARTIAL_USED_WEALTH.getActionName());
        baseRequest.setParam("userId", userId);
//        baseRequest.setParam("sellerId", sellerId);
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("itemId", itemId);
//        baseRequest.setParam("skuId", skuId);
//        baseRequest.setParam("itemName", itemName);
        baseRequest.setParam("amounts", amounts);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> grantVirtualWealth(Long creatorId, Integer wealthType, Integer sourceType,
                                             Long grantAmount, Long receiverId, Long orderId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GRANT_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("wealthCreatorId", creatorId);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("sourceType", sourceType);
        baseRequest.setParam("grantAmount", grantAmount);
        baseRequest.setParam("receiverId", receiverId);
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("appKey", appKey);
        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> grantVirtualWealth(Long creatorId,
                                             Integer wealthType,
                                             Integer sourceType,
                                             Long grantAmount,
                                             Long receiverId,
                                             String appKey) {

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.GRANT_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("wealthCreatorId", creatorId);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("sourceType", sourceType);
        baseRequest.setParam("grantAmount", grantAmount);
        baseRequest.setParam("receiverId", receiverId);
        baseRequest.setParam("appKey", appKey);
        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Void> initVirtualWealth(Long sellerId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.INIT_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("sellerId", sellerId);
        baseRequest.setParam("appKey", appKey);

        Response<Void> response = (Response<Void>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> distributorGrant(GrantedWealthDTO grantedWealthDTO, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.DISTRIBUTOR_GRANT.getActionName());
        baseRequest.setParam("grantedWealthDTO", grantedWealthDTO);
        baseRequest.setParam("appKey", appKey);

        Response<Boolean> response = (Response<Boolean>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<Boolean> updateStatusOfVirtualWealthDistributorGranted(Long orderId,
                                                                           Long skuId,
                                                                           Integer sourceType,
                                                                           Integer status,
                                                                           String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.UPDATE_STATUS_OF_VIRTUAL_WEALTH_DISTRIBUTOR_GRANTED.getActionName());
        baseRequest.setParam("orderId", orderId);
        baseRequest.setParam("skuId", skuId);
        baseRequest.setParam("sourceType", sourceType);
        baseRequest.setParam("status", status);
        baseRequest.setParam("appKey", appKey);

        Response<Boolean> response = (Response<Boolean>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<TotalWealthAccountDTO> queryTotalVirtualWealthCombine(Long userId, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.QUERY_TOTAL_VIRTUAL_WEALTH_COMBINE.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);

        Response<TotalWealthAccountDTO> response =
                (Response<TotalWealthAccountDTO>) virtualWealthService.execute(baseRequest);
        return response;
    }

    public Response<List<WealthAccountDTO>> listTotalVirtualWealth(List<Long> userIds, Integer wealthType, String appKey) {
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.LIST_TOTAL_VIRTUAL_WEALTH.getActionName());
        baseRequest.setParam("userIds", userIds);
        baseRequest.setParam("wealthType", wealthType);
        baseRequest.setParam("appKey", appKey);

        Response<List<WealthAccountDTO>> response = virtualWealthService.execute(baseRequest);
        return response;
    }

    
    
	public Response<List<BossWithdrawalsItemDTO>> FindCustomerWithdrawalsPageList(
			WithdrawalsItemQTO withdrawalsItemQTO, String appKey) {
		 BaseRequest baseRequest = new BaseRequest();
         baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_WD_PAGELIST.getActionName());
         baseRequest.setParam("withdrawalsItem", withdrawalsItemQTO);
         baseRequest.setParam("appKey", appKey);
         Response<List<BossWithdrawalsItemDTO>> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<List<BossBalanceItemDTO>> FindCustomerBUsedPagePageList(
			UsedWealthQTO usedWealthQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_USED_PAGELIST.getActionName());
        baseRequest.setParam("usedWealthQTO", usedWealthQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<BossBalanceItemDTO>> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<List<BossVirtualItemDTO>> FindCustomerVUsedPagePageList(
			UsedWealthQTO usedWealthQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_USED_PAGELIST.getActionName());
        baseRequest.setParam("usedWealthQTO", usedWealthQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<BossVirtualItemDTO>> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<List<BossBalanceItemDTO>> FindCustomerBGrantedPageList(
			GrantedWealthQTO grantedWealthQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_GRANTED_PAGELIST.getActionName());
        baseRequest.setParam("grantedWealthQTO", grantedWealthQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<BossBalanceItemDTO>> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<List<BossVirtualItemDTO>> FindCustomerVGrantedPageList(
			GrantedWealthQTO grantedWealthQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_GRANTED_PAGELIST.getActionName());
        baseRequest.setParam("grantedWealthQTO", grantedWealthQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<BossVirtualItemDTO>> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<List<BossBankInfoItemDTO>> FindCustomerBankInfoPageList(
			BankInfoQTO bankInfoQTO, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_BANKINFO_PAGELIST.getActionName());
        baseRequest.setParam("bankInfoQTO", bankInfoQTO);
        baseRequest.setParam("appKey", appKey);
        Response<List<BossBankInfoItemDTO>> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<BossVirtualItemDetailDTO> FindCustomerVirtualDetailAction(
			Long userId, String overTime, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_Virtual_DETAIL.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("overTime", overTime);
        baseRequest.setParam("appKey", appKey);
        Response<BossVirtualItemDetailDTO> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<BossBalanceItemDetailDTO> FindCustomerBalanceDetailAction(
			Long userId, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.FIND_CUSTOMER_BALANCE_DETAIL.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        Response<BossBalanceItemDetailDTO> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<List<BossUserAuthonDTO>> ManualAuditListAction(UserAuthonQTO userAuthonQTO,String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.USER_AUTHON_ALL.getActionName());
		baseRequest.setParam("userAuthonQTO", userAuthonQTO);
		baseRequest.setParam("appKey", appKey);
		Response<List<BossUserAuthonDTO>> response = virtualWealthService.execute(baseRequest);
		return response;
	}

	public Response<Integer> AcceptAuditing(Long id,String authonStatus, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.ACCEPT_AUDITING.getActionName());
		baseRequest.setParam("id",id);
		baseRequest.setParam("authon_status", authonStatus);
		baseRequest.setParam("appKey", appKey);
		Response<Integer> response = (Response<Integer>) virtualWealthService.execute(baseRequest);
        return response;
	}
	public Response<Integer> RefuseAuditing(Long id,String authonStatus,String authonText, String appKey) {
		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setCommand(ActionEnum.REFUES_AUDITING.getActionName());
		baseRequest.setParam("id",id);
		baseRequest.setParam("authon_status", authonStatus);
		baseRequest.setParam("authon_text", authonText);
		baseRequest.setParam("appKey", appKey);
		Response<Integer> response = (Response<Integer>) virtualWealthService.execute(baseRequest);
        return response;
	}

	public Response<MopUserAuthonAppDTO> SelectAuditStatus(String userId,
			String appKey) {
		BaseRequest baseRequest = new BaseRequest();
        baseRequest.setCommand(ActionEnum.SELECT_AUDIT_STATUS.getActionName());
        baseRequest.setParam("userId", userId);
        baseRequest.setParam("appKey", appKey);
        Response<MopUserAuthonAppDTO> response = virtualWealthService.execute(baseRequest);
		 return response;
	}

	public Response<Long> SyncPhoneNumber(Long userId, String appKey) {
		BaseRequest request = new BaseRequest();
        request.setParam("userId", userId);
        request.setParam("appKey", appKey);
        request.setCommand(ActionEnum.SYNC_PHONE_NUMBER.getActionName());
        Response<Long> response = virtualWealthService.execute(request);
        return response;
	}

	

	
	
	

}
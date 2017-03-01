package com.mockuai.virtualwealthcenter.core.service.action.withdrawals;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ResponseCode;
import com.mockuai.virtualwealthcenter.common.domain.dto.WithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.dto.mop.MopWithdrawalsItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.WithdrawalsItemLogQTO;
import com.mockuai.virtualwealthcenter.core.domain.BankInfoAppDO;
import com.mockuai.virtualwealthcenter.core.domain.WithdrawalsItemLogDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.BankInfoAppManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemLogManager;
import com.mockuai.virtualwealthcenter.core.manager.WithdrawalsItemManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.service.action.TransAction;
import com.mockuai.virtualwealthcenter.core.util.JsonUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.mockuai.virtualwealthcenter.common.constant.ActionEnum.DETAIL_OF_WITHDRAWALS;

/**
 * Created by edgar.zr on 5/21/2016.
 */
@Service
public class DetailOfWithdrawalsAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetailOfWithdrawalsAction.class);

	@Autowired
	private WithdrawalsItemManager withdrawalsItemManager;
	@Autowired
	private WithdrawalsItemLogManager withdrawalsItemLogManager;
	@Autowired
	private BankInfoAppManager bankInfoAppManager;

	@Override
	protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
		String withdrawalsNumber = (String) context.getRequest().getParam("withdrawalsNumber");
		VirtualWealthPreconditions.checkNotNull(withdrawalsNumber, "withdrawalsNumber");

		WithdrawalsItemDTO withdrawalsItemDTO = withdrawalsItemManager.getWithdrawalsItemDTO(withdrawalsNumber);

		if (withdrawalsItemDTO == null) {
			LOGGER.error("the withdrawalsItem does not exist, withdrawalsNumber : {}", withdrawalsNumber);
			throw new VirtualWealthException(ResponseCode.WITHDRAWALS_ITEM_NOT_EXISTS);
		}
		LOGGER.info("wi, {}", JsonUtil.toJson(withdrawalsItemDTO));
		MopWithdrawalsItemDTO mopWithdrawalsItemDTO = new MopWithdrawalsItemDTO();
		mopWithdrawalsItemDTO.setType(withdrawalsItemDTO.getWithdrawalsType());
		mopWithdrawalsItemDTO.setRefusalReason(withdrawalsItemDTO.getWithdrawalsRefuse());
		mopWithdrawalsItemDTO.setAmount(withdrawalsItemDTO.getWithdrawalsAmount());
		if (StringUtils.isBlank(withdrawalsItemDTO.getWithdrawalsNo()) || withdrawalsItemDTO.getWithdrawalsNo().length() < 4) {
//            mopWithdrawalsItemDTO.setBankNo("0000");
		} else {
			mopWithdrawalsItemDTO.setBankNo(withdrawalsItemDTO.getWithdrawalsNo().substring(withdrawalsItemDTO.getWithdrawalsNo().length() - 4));
		}
		mopWithdrawalsItemDTO.setNumber(withdrawalsItemDTO.getWithdrawalsNumber());
		if (mopWithdrawalsItemDTO.getType() == 1) {
			BankInfoAppDO bankInfoAppDO = bankInfoAppManager.getBankInfoExists(withdrawalsItemDTO.getWithdrawalsNo());
			mopWithdrawalsItemDTO.setBankName(bankInfoAppDO.getBankName());
		}

		mopWithdrawalsItemDTO.setStatusList(new ArrayList<MopWithdrawalsItemDTO.StatusItem>());

		WithdrawalsItemLogQTO withdrawalsItemLogQTO = new WithdrawalsItemLogQTO();
		withdrawalsItemLogQTO.setWdLogNumber(withdrawalsItemDTO.getWithdrawalsNumber());
		List<WithdrawalsItemLogDO> withdrawalsItemLogDOs =
				withdrawalsItemLogManager.queryWithdrawalsItemLog(withdrawalsItemLogQTO);
		MopWithdrawalsItemDTO.StatusItem statusItem;
		for (WithdrawalsItemLogDO withdrawalsItemLogDO : withdrawalsItemLogDOs) {
			statusItem = new MopWithdrawalsItemDTO.StatusItem();
			statusItem.setStatus(withdrawalsItemLogDO.getWdLogStatus());
			statusItem.setTime(DateFormatUtils.format(withdrawalsItemLogDO.getWdLogTime(), "yyyy-MM-dd HH:mm:ss"));
			mopWithdrawalsItemDTO.getStatusList().add(statusItem);
		}
		LOGGER.info("wi2, {}", JsonUtil.toJson(mopWithdrawalsItemDTO));
		return VirtualWealthUtils.getSuccessResponse(mopWithdrawalsItemDTO);
	}

	@Override
	public String getName() {
		return DETAIL_OF_WITHDRAWALS.getActionName();
	}
}
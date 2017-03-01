package com.mockuai.virtualwealthcenter.core.service.action;

import com.mockuai.virtualwealthcenter.common.api.VirtualWealthResponse;
import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.domain.qto.WealthAccountQTO;
import com.mockuai.virtualwealthcenter.core.domain.WealthAccountDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.WealthAccountManager;
import com.mockuai.virtualwealthcenter.core.service.RequestContext;
import com.mockuai.virtualwealthcenter.core.util.ModelUtil;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthPreconditions;
import com.mockuai.virtualwealthcenter.core.util.VirtualWealthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by edgar.zr on 5/18/2016.
 */
@Service
public class ListTotalVirtualWealthAction extends TransAction {

    @Autowired
    private WealthAccountManager wealthAccountManager;

    @Override
    protected VirtualWealthResponse doTransaction(RequestContext context) throws VirtualWealthException {
        List<Long> userIds = (List<Long>) context.getRequest().getParam("userIds");
        Integer wealthType = (Integer) context.getRequest().getParam("wealthType");

        VirtualWealthPreconditions.checkNotEmpty(userIds, "userIds");
        VirtualWealthPreconditions.checkNotNull(wealthType, "wealthType");

        WealthAccountQTO wealthAccountQTO = new WealthAccountQTO();
        wealthAccountQTO.setUserIdList(userIds);
        wealthAccountQTO.setWealthType(wealthType);

        List<WealthAccountDO> wealthAccountDOs = wealthAccountManager.queryWealthAccount(wealthAccountQTO);

        return VirtualWealthUtils.getSuccessResponse(ModelUtil.genWealthAccountDTOList(wealthAccountDOs));
    }

    @Override
    public String getName() {
        return ActionEnum.LIST_TOTAL_VIRTUAL_WEALTH.getActionName();
    }
}
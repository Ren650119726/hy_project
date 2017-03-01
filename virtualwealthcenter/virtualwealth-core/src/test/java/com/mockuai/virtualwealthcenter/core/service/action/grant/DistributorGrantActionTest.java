package com.mockuai.virtualwealthcenter.core.service.action.grant;

import com.mockuai.virtualwealthcenter.common.constant.ActionEnum;
import com.mockuai.virtualwealthcenter.common.constant.SourceType;
import com.mockuai.virtualwealthcenter.common.constant.WealthType;
import com.mockuai.virtualwealthcenter.common.domain.dto.GrantedWealthDTO;
import com.mockuai.virtualwealthcenter.core.BaseActionTest;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 5/17/2016.
 */
public class DistributorGrantActionTest extends BaseActionTest {

    public DistributorGrantActionTest() {
        super(DistributorGrantActionTest.class.getName());
    }

    @Override
    protected String getCommand() {
        return ActionEnum.DISTRIBUTOR_GRANT.getActionName();
    }

    @Test
    public void testShop() {
        request.setParam("grantedWealthDTO", genShop());
        doExecute();
    }

    @Test
    public void testSell() {
        request.setParam("grantedWealthDTO", genSell());
        doExecute();
    }

    @Test
    public void testSellGroup() {
        request.setParam("grantedWealthDTO", genSellGroup());
        doExecute();
    }

    @Test
    public void testRefund() {
        request.setParam("grantedWealthDTO", genGiveBack());
        doExecute();
    }

    /**
     * 开店
     *
     * @return
     */
    public GrantedWealthDTO genShop() {
        GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
        grantedWealthDTO.setSourceType(SourceType.SHOP.getValue());
        grantedWealthDTO.setText("谁谁谁的店");
        grantedWealthDTO.setAmount(20L);
        grantedWealthDTO.setGranterId(38699L);
        grantedWealthDTO.setReceiverId(38699L);
        grantedWealthDTO.setShopId(11L);
        grantedWealthDTO.setWealthType(WealthType.HI_COIN.getValue());
        return grantedWealthDTO;
    }

    /**
     * 销售
     *
     * @return
     */
    public GrantedWealthDTO genSell() {
        GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
        grantedWealthDTO.setSourceType(SourceType.SELL.getValue());
        grantedWealthDTO.setText("西瓜霜");
        grantedWealthDTO.setAmount(20L);
        grantedWealthDTO.setGranterId(38699L);
        grantedWealthDTO.setReceiverId(38699L);
        grantedWealthDTO.setOrderId(11L);
        grantedWealthDTO.setOrderSN("xxxxxxxx");
        grantedWealthDTO.setItemId(2222L);
        grantedWealthDTO.setSkuId(3333L);
        grantedWealthDTO.setWealthType(WealthType.HI_COIN.getValue());
        return grantedWealthDTO;
    }

    /**
     * 团队销售
     *
     * @return
     */
    public GrantedWealthDTO genSellGroup() {
        GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
        grantedWealthDTO.setSourceType(SourceType.GROUP_SELL.getValue());
        grantedWealthDTO.setText("西瓜霜");
        grantedWealthDTO.setAmount(30L);
        grantedWealthDTO.setGranterId(38699L);
        grantedWealthDTO.setReceiverId(38699L);
        grantedWealthDTO.setItemId(1111L);
        grantedWealthDTO.setSkuId(222L);
        grantedWealthDTO.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
        return grantedWealthDTO;
    }

    /**
     * 退款
     *
     * @return
     */
    public GrantedWealthDTO genGiveBack() {
        GrantedWealthDTO grantedWealthDTO = new GrantedWealthDTO();
        grantedWealthDTO.setSourceType(SourceType.REFUND.getValue());
        grantedWealthDTO.setText("西瓜霜");
        grantedWealthDTO.setAmount(30L);
        grantedWealthDTO.setGranterId(38699L);
        grantedWealthDTO.setReceiverId(38699L);
        grantedWealthDTO.setItemId(1111L);
        grantedWealthDTO.setSkuId(222L);
        grantedWealthDTO.setWealthType(WealthType.VIRTUAL_WEALTH.getValue());
        return grantedWealthDTO;
    }
}
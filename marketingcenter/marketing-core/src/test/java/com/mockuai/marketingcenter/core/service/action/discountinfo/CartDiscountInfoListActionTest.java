package com.mockuai.marketingcenter.core.service.action.discountinfo;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 12/8/15.
 */
public class CartDiscountInfoListActionTest extends BaseActionTest {

	public CartDiscountInfoListActionTest() {
		super(CartDiscountInfoListActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.CART_DISCOUNT_INFO_LIST.getActionName();
	}

	@Test
	public void test() {

		List<MarketItemDTO> cartItemList = new ArrayList<MarketItemDTO>();
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		marketItemDTO.setItemType(1);
		marketItemDTO.setItemId(28954L);
		marketItemDTO.setSellerId(1841254L);
		cartItemList.add(marketItemDTO);

		request.setParam("cartItemList", cartItemList);

		doExecute();
	}
}
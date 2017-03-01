package com.mockuai.marketingcenter.core.service.action.discountinfo;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 7/22/2016.
 */
public class DiscountInfoOfItemListActionTest extends BaseActionTest {

	public DiscountInfoOfItemListActionTest() {
		super(DiscountInfoOfItemListActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.DISCOUNT_INF_OF_ITEM_LITE.getActionName();
	}

	@Test
	public void test() {

		List<MarketItemDTO> marketItemDTOs = new ArrayList<>();
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		request.setParam("marketItemDTOs", marketItemDTOs);

		marketItemDTO.setVirtualMark(0);
		marketItemDTO.setItemType(1);
		marketItemDTO.setItemId(28954L);
		marketItemDTO.setSellerId(1841254L);
		marketItemDTOs.add(marketItemDTO);

		doExecute();
	}
}
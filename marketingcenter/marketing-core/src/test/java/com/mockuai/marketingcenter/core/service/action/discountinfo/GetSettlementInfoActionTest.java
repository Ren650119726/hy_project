package com.mockuai.marketingcenter.core.service.action.discountinfo;

import com.mockuai.marketingcenter.common.constant.ActionEnum;
import com.mockuai.marketingcenter.common.constant.ItemType;
import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.core.BaseActionTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 8/8/15.
 */
public class GetSettlementInfoActionTest extends BaseActionTest {

	public GetSettlementInfoActionTest() {
		super(GetSettlementInfoActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.GET_SETTLEMENT_INFO.getActionName();
	}

	@Test
	public void getSettlementInfo() {

		request.setParam("userId", 1L);
		//38785
		List<MarketItemDTO> itemDTOList = new ArrayList<MarketItemDTO>();
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		marketItemDTO.setItemType(ItemType.COMMON.getValue());
		marketItemDTO.setItemSkuId(43237L);
		marketItemDTO.setNumber(200);
		marketItemDTO.setSellerId(1841254L);
		marketItemDTO.setDistributorId(19L);

		itemDTOList.add(marketItemDTO);

		request.setParam("itemList", itemDTOList);
//        request.setParam("consigneeId", 257L);
		doExecute();
	}
}
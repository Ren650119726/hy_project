package com.mockuai.marketingcenter.core.dao.impl.engine.component.impl;

import com.mockuai.marketingcenter.common.domain.dto.MarketItemDTO;
import com.mockuai.marketingcenter.common.domain.dto.SettlementInfo;
import com.mockuai.marketingcenter.core.BaseTest;
import com.mockuai.marketingcenter.core.engine.component.ComponentHelper;
import com.mockuai.marketingcenter.core.engine.component.impl.ValidateSettlementOfSeckill;
import com.mockuai.marketingcenter.core.exception.MarketingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edgar.zr on 7/22/2016.
 */
public class ValidateSettlementOfSeckillTest extends BaseTest {

	@Autowired
	private ComponentHelper componentHelper;

	@Test
	public void test() {
		SettlementInfo settlementInfo = new SettlementInfo();
		List<MarketItemDTO> marketItemDTOs = new ArrayList<>();
		MarketItemDTO marketItemDTO = new MarketItemDTO();
		marketItemDTO.setItemId(29042L);
		marketItemDTO.setItemSkuId(43333L);
		marketItemDTO.setSellerId(1841254L);
		marketItemDTOs.add(marketItemDTO);

		Long userId = 11L;
		String appKey = "27c7bc87733c6d253458fa8908001eef";

		try {
			componentHelper.execute(ValidateSettlementOfSeckill.wrapParams(settlementInfo, marketItemDTOs, userId, null, appKey));
		} catch (MarketingException e) {
			System.err.println(e);
		}
	}
}
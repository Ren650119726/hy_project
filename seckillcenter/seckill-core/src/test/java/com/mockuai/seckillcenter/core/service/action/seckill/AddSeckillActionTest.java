package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.BaseActionTest;
import org.apache.commons.lang3.time.DateUtils;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * Created by edgar.zr on 12/13/15.
 */
public class AddSeckillActionTest extends BaseActionTest {

	public AddSeckillActionTest() {
		super(AddSeckillActionTest.class.getName());
	}

	@Override
	protected String getCommand() {
		return ActionEnum.ADD_SECKILL.getActionName();
	}

	@Test()
	public void test() {

		SeckillDTO seckillDTO = new SeckillDTO();
		seckillDTO.setSellerId(1841254L);
		seckillDTO.setSkuId(43261L);
		seckillDTO.setItemId(29000L);
		seckillDTO.setLimit(1);
		seckillDTO.setPrice(100L);
		seckillDTO.setStartTime(DateUtils.addMinutes(new Date(), -5));
		seckillDTO.setEndTime(DateUtils.addMinutes(new Date(), 100));
		seckillDTO.setStockNum(1L);
		request.setParam("seckillDTO", seckillDTO);

		doExecute();
	}
}
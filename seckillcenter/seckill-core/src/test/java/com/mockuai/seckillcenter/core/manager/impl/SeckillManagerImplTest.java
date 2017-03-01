package com.mockuai.seckillcenter.core.manager.impl;

import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.BaseTest;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Created by edgar.zr on 12/4/15.
 */
public class SeckillManagerImplTest extends BaseTest {

	@Autowired
	private SeckillManager seckillManager;

	@Test
	public void testGetSeckill() throws Exception {
		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setSkuId(43437L);
		SeckillDTO record = seckillManager.getSeckill(seckillDO);
		System.err.println(JsonUtil.toJson(record));
	}

}
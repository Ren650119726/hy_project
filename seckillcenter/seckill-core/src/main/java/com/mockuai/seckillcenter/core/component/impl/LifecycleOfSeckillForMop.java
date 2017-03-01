package com.mockuai.seckillcenter.core.component.impl;

import com.mockuai.seckillcenter.common.constant.SeckillStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.Component;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.util.Context;

import static com.mockuai.seckillcenter.common.constant.ComponentType.LIFECYCLE_OF_SECKILL_FOR_MOP;

/**
 * 为前端活动封装活动生命周期
 * <p/>
 * Created by edgar.zr on 6/15/2016.
 */
@org.springframework.stereotype.Component
public class LifecycleOfSeckillForMop implements Component {

	public static Context wrapParams(SeckillDTO seckillDTO) {

		Context context = new Context();
		context.setParam("seckillDTO", seckillDTO);
		context.setParam("component", LIFECYCLE_OF_SECKILL_FOR_MOP);
		return context;
	}

	@Override
	public void init() {

	}

	@Override
	public <T> T execute(Context context) throws SeckillException {
		SeckillDTO seckillDTO = (SeckillDTO) context.getParam("seckillDTO");

		seckillDTO.setLifecycle(seckillDTO.getLifecycle().intValue() == 3
				                        || seckillDTO.getStatus().intValue() == SeckillStatus.INVALID.getValue()
				                        ? 4 : seckillDTO.getLifecycle());

		if (seckillDTO.getLifecycle().intValue() == 2) {
			if (seckillDTO.getCurrentStockNum().longValue() != 0
					    && seckillDTO.getCurrentStockNum().longValue() == seckillDTO.getCurrentStockNum().longValue())
				seckillDTO.setLifecycle(3);
			if (seckillDTO.getCurrentStockNum().longValue() == 0)
				seckillDTO.setLifecycle(4);
		}
		return null;

	}

	@Override
	public String getComponentCode() {
		return LIFECYCLE_OF_SECKILL_FOR_MOP.getCode();
	}
}
package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.TransAction;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 后台数据获取秒杀
 * 
 * <p/>
 * Created by edgar.zr on 12/4/15.
 */
@Service
public class GetSeckillAction extends TransAction {

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ItemManager itemManager;

	@Override
	protected SeckillResponse doTransaction(RequestContext context) throws SeckillException {
		Long seckillId = (Long) context.getRequest().getParam("seckillId");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(seckillId, "seckillId");
		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setId(seckillId);

		SeckillDTO seckillDTO = seckillManager.getSeckill(seckillDO);
		seckillManager.fillUpSeckillDTO(Arrays.asList(seckillDTO));
		itemManager.fillUpItem(Arrays.asList(seckillDTO), appKey);

		return SeckillUtils.getSuccessResponse(seckillDTO);
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SECKILL.getActionName();
	}
}
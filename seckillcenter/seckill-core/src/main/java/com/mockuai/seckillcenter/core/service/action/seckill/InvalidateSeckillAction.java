package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.constant.SeckillStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.core.component.ComponentHelper;
import com.mockuai.seckillcenter.core.component.impl.LoadSeckillCache;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.TransAction;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使秒杀失效,同时处理相应的活动数据,但不能移除数据,将状态更改即可
 * <p/>
 * Created by edgar.zr on 12/4/15.
 */
@Service
public class InvalidateSeckillAction extends TransAction {

	private static final Logger LOGGER = LoggerFactory.getLogger(InvalidateSeckillAction.class);

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ComponentHelper componentHelper;

	@Override
	protected SeckillResponse doTransaction(RequestContext context) throws SeckillException {

		Long seckillId = (Long) context.getRequest().getParam("seckillId");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(seckillId, "seckillId");
		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setId(seckillId);
		seckillDO.setBizCode(bizCode);
		seckillDO.setStatus(SeckillStatus.INVALID.getValue());

		seckillManager.invalidSeckill(seckillDO);

		updateCache(seckillId, bizCode, appKey);

		return SeckillUtils.getSuccessResponse();
	}

	public final void haha(){


	}

	/**
	 * 移除失效活动的缓存数据
	 *
	 * @param seckillId
	 * @param bizCode
	 * @param appKey
	 * @throws SeckillException
	 */
	public void updateCache(Long seckillId, String bizCode, String appKey) throws SeckillException {

		SeckillDO seckillDO = new SeckillDO();
		seckillDO.setId(seckillId);
		seckillDO.setBizCode(bizCode);
		SeckillDTO seckillDTO = seckillManager.getSeckill(seckillDO);

		// 强制更新缓存
		componentHelper.execute(LoadSeckillCache.wrapParams(seckillDTO.getItemId(), null, appKey));
	}

	@Override
	public String getName() {
		return ActionEnum.INVALIDATE_SECKILL.getActionName();
	}
}
package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.common.domain.dto.SeckillForMopDTO;
import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.BaseAction;
import com.mockuai.seckillcenter.core.util.ModelUtil;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * lifecycle, offset, count
 * <p/>
 * Created by edgar.zr on 12/29/15.
 */
@Service
public class QuerySeckillByItemBatchAction extends BaseAction {

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ItemManager itemManager;

	@Override
	public SeckillResponse execute(RequestContext context) throws SeckillException {

		SeckillQTO seckillQTO = (SeckillQTO) context.getRequest().getParam("seckillQTO");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(seckillQTO, "seckillQTO");
		seckillQTO.setBizCode(bizCode);

		List<SeckillDTO> seckillDTOs = seckillManager.querySeckill(seckillQTO);
		seckillManager.fillUpSeckillDTO(seckillDTOs);
		itemManager.fillUpItem(seckillDTOs, appKey);

		List<SeckillForMopDTO> seckillForMopDTOs = ModelUtil.genSeckillForMopDTOList(seckillDTOs);

		return new SeckillResponse(seckillForMopDTOs, seckillQTO.getTotalCount());
	}

	@Override
	public String getName() {
		return ActionEnum.GET_SECKILL_BY_ITEM_BATCH.getActionName();
	}
}
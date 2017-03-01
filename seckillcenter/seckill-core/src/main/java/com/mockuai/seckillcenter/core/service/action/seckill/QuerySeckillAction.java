package com.mockuai.seckillcenter.core.service.action.seckill;

import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.seckillcenter.common.api.SeckillResponse;
import com.mockuai.seckillcenter.common.constant.ActionEnum;
import com.mockuai.seckillcenter.common.constant.SeckillStatus;
import com.mockuai.seckillcenter.common.domain.dto.SeckillDTO;
import com.mockuai.seckillcenter.common.domain.qto.SeckillQTO;
import com.mockuai.seckillcenter.core.domain.SeckillDO;
import com.mockuai.seckillcenter.core.exception.SeckillException;
import com.mockuai.seckillcenter.core.manager.ItemManager;
import com.mockuai.seckillcenter.core.manager.SeckillManager;
import com.mockuai.seckillcenter.core.service.RequestContext;
import com.mockuai.seckillcenter.core.service.action.TransAction;
import com.mockuai.seckillcenter.core.util.SeckillPreconditions;
import com.mockuai.seckillcenter.core.util.SeckillUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台查询秒杀活动，在活动结束时32min后还商品库存，并记录下销售情况
 * Created by edgar.zr on 12/4/15.
 */
@Service
public class QuerySeckillAction extends TransAction {

	@Autowired
	private SeckillManager seckillManager;
	@Autowired
	private ItemManager itemManager;

	@Override
	protected SeckillResponse doTransaction(RequestContext context) throws SeckillException {
		SeckillQTO seckillQTO = (SeckillQTO) context.getRequest().getParam("seckillQTO");
		String bizCode = (String) context.get("bizCode");
		String appKey = (String) context.get("appKey");

		SeckillPreconditions.checkNotNull(seckillQTO, "seckillQTO");
		seckillQTO.setBizCode(bizCode);

		List<SeckillDTO> seckillDTOs = seckillManager.querySeckill(seckillQTO);
		seckillManager.fillUpSeckillDTO(seckillDTOs);
		itemManager.fillUpItem(seckillDTOs, appKey);
		SeckillDO seckillDO;
		for (SeckillDTO seckillDTO : seckillDTOs) {
			if (seckillDTO.getGiveBack().intValue() == 0
					    && (new Date().after(DateUtils.addMinutes(seckillDTO.getEndTime(), 32))
//					    && (new Date().after(DateUtils.addMinutes(seckillDTO.getEndTime(), 3))
							        || seckillDTO.getStatus().intValue() == SeckillStatus.INVALID.getValue()
//									           && new Date().after(DateUtils.addMinutes(seckillDTO.getGmtModified(), 3))
									           && new Date().after(DateUtils.addMinutes(seckillDTO.getGmtModified(), 32))
			)) { // 活动结束后 2 hour 退还库存
				LOGGER.info("return in query, seckillId : {}, skuId : {}", seckillDTO.getId(), seckillDTO.getSkuId());
				ItemSkuDTO itemSkuDTO = itemManager.getItemSku(seckillDTO.getSkuId(), seckillDTO.getItemSellerId(), appKey);
				if (itemSkuDTO == null) {
					LOGGER.error("error to get sku for returning, seckillId : {}, skuId : {}"
							, seckillDTO.getId(), seckillDTO.getSkuId());
					continue;
				}
				seckillDO = new SeckillDO();
				seckillDO.setId(seckillDTO.getId());
				seckillDO.setGiveBack(1);
				seckillDO.setCurrentStockNum(itemSkuDTO.getStockNum());

				itemManager.copySkuStockReturn(seckillDTO.getOriginSkuId(), seckillDTO.getSkuId(), appKey);

				seckillManager.updateSeckill(seckillDO);
			}
		}

		return SeckillUtils.getSuccessResponse(seckillDTOs, seckillQTO.getTotalCount());
	}

	@Override
	public String getName() {
		return ActionEnum.QUERY_SECKILL.getActionName();
	}
}
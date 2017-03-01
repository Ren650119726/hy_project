package com.mockuai.itemcenter.client;

import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuPropertyRecommendationDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyTmplDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyTmplQTO;
import com.mockuai.itemcenter.common.domain.qto.ItemSkuPropertyRecommendationQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyTmplQTO;

import java.util.List;

/**
 * 商品属性相关的接口
 * @author cwr
 *
 */
public interface SkuPropertyClient {
	
	/**
	 * 查询sku属性
	 * @param qto 查询对象
	 * @param appKey
	 * @return
	 */
	public Response<List<SkuPropertyDTO>> querySkuProperty(
			SkuPropertyQTO qto, String appKey);

}


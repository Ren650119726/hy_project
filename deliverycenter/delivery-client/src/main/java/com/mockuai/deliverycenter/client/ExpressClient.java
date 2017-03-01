package com.mockuai.deliverycenter.client;

import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.express.ExpressDTO;
import com.mockuai.deliverycenter.common.dto.express.ExpressPropertyDTO;
import com.mockuai.deliverycenter.common.dto.express.ExpressRegionDTO;
import com.mockuai.deliverycenter.common.qto.express.ExpressQTO;

public interface ExpressClient {
	/**
	 * 新增快递
	 * 
	 * @param expressDTO
	 * @return
	 */
	Response<ExpressDTO> addExpress(ExpressDTO expressDTO,String appkey);

	/**
	 * 新增快递属性
	 * 
	 * @param expressPropertyDTOList
	 * @return
	 */
	public Response<List<ExpressPropertyDTO>> addExpressProperty(
			List<ExpressPropertyDTO> expressPropertyDTOList,String appkey);

	/**
	 * 校验快递是否支持配送
	 * 
	 * @param expressRegionDTO
	 * @return
	 */
	public Response<Boolean> checkRegionSupported(
			ExpressRegionDTO expressRegionDTO,String appkey);

	/**
	 * 删除快递
	 * 
	 * @param id
	 * @return
	 */
	public Response<Boolean> deleteExpress(Integer id,String appkey);

	/**
	 * 删除快递属性
	 * 
	 * @param expressPropertyIdList
	 * @return
	 */
	public Response<Boolean> deleteExpressProperty(
			List<Integer> expressPropertyIdList,String appkey);

	/**
	 * 查询快递
	 * 
	 * @param expressQTO
	 * @return
	 */
	public Response<List<ExpressDTO>> queryExpress(ExpressQTO expressQTO,String appkey);

	/**
	 * 修改快递
	 * 
	 * @param expressDTO
	 * @return
	 */
	public Response<Boolean> updateExpress(ExpressDTO expressDTO,String appkey);

}

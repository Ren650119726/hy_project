package com.mockuai.deliverycenter.client;

import java.util.List;

import com.mockuai.deliverycenter.common.api.Response;
import com.mockuai.deliverycenter.common.dto.fee.RegionDTO;
import com.mockuai.deliverycenter.common.dto.fee.RegionFeeDTO;
import com.mockuai.deliverycenter.common.dto.fee.StdFeeDTO;
import com.mockuai.deliverycenter.common.qto.fee.DeliveryFeeQTO;
import com.mockuai.deliverycenter.common.qto.fee.RegionQTO;
import com.mockuai.deliverycenter.common.qto.fee.StdFeeQTO;

public interface RegionClient {
	/**
	 * 新增区域
	 * 
	 * @param regionDTO
	 * @return
	 */
	public Response<RegionDTO> addRegion(RegionDTO regionDTO,String appkey);

	/**
	 * 新增区域运费
	 * 
	 * @param regionFeeDTOList
	 * @return
	 */
	public Response<List<RegionFeeDTO>> addRegionFee(
			List<RegionFeeDTO> regionFeeDTOList,String appkey);

	/**
	 * 新增运费标准
	 * 
	 * @param stdFeeDTO
	 * @return
	 */
	public Response<StdFeeDTO> addStdFee(StdFeeDTO stdFeeDTO,String appkey);

	/**
	 * 删除区域
	 * 
	 * @param id
	 * @return
	 */
	public Response<Boolean> deleteRegion(Integer id,String appkey);

	/**
	 * 删除区域运费
	 * 
	 * @param regionFeeIdList
	 * @return
	 */
	public Response<Boolean> deleteRegionFee(List<Integer> regionFeeIdList,String appkey);

	/**
	 * 删除运费标准
	 * 
	 * @param id
	 * @return
	 */
	public Response<Boolean> deleteStdFee(Integer id,String appkey);

	/**
	 * 根据ID查询区域
	 * 
	 * @param id
	 * @return
	 */
	public Response<RegionDTO> getRegion(Integer id,String appkey);

	/**
	 * 查询运费
	 * 
	 * @param deliveryFeeQTO
	 * @return
	 */
	public Response<Long> queryDeliveryFee(DeliveryFeeQTO deliveryFeeQTO,String appkey);

	/**
	 * 查询区域
	 * 
	 * @param regionQTO
	 * @return
	 */
	public Response<List<RegionDTO>> queryRegion(RegionQTO regionQTO,String appkey);

	/**
	 * 查询运费标准
	 * 
	 * @param stdFeeQTO
	 * @return
	 */
	public Response<List<StdFeeDTO>> queryStdFee(StdFeeQTO stdFeeQTO,String appkey);

	/**
	 * 修改区域
	 * 
	 * @param regionDTO
	 * @return
	 */
	public Response<Boolean> updateRegion(RegionDTO regionDTO,String appkey);

	/**
	 * 修改运费标准
	 * 
	 * @param stdFeeDTO
	 * @return
	 */
	public Response<Boolean> updateStdFee(StdFeeDTO stdFeeDTO,String appkey);

}

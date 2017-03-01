package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemSkuDTO;
import com.mockuai.itemcenter.common.domain.dto.SkuPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.SkuPropertyQTO;
import com.mockuai.itemcenter.core.dao.SkuPropertyDAO;
import com.mockuai.itemcenter.core.domain.SkuPropertyDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemSkuManager;
import com.mockuai.itemcenter.core.manager.SkuPropertyManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class SkuPropertyManagerImpl implements SkuPropertyManager {
	@Resource
	private SkuPropertyDAO skuPropertyDAO;

	@Resource
	private ItemSkuManager itemSkuManager;

	@Override
	public List<SkuPropertyDTO> addSkuProperty(Long itemId, Long skuId, Long sellerId, List<SkuPropertyDTO> skuPropertyDTOList, String bizCode)
			throws ItemException {
		if (skuId == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuId is null");
		}
		if (skuPropertyDTOList == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuPropertyDTOList is null");
		}
		if (skuPropertyDTOList.size() == 0) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "the size of skuPropertyDTOList is 0");
		}
		// 返回的SkuPropertyDTO列表
		List<SkuPropertyDTO> returnSkuPropertyDTOList = new ArrayList<SkuPropertyDTO>();
		// TODO 1.验证sellerId
		// 2.验证skuId
		itemSkuManager.getItemSku(skuId, sellerId, bizCode);
		
		for (SkuPropertyDTO skuPropertyDTO : skuPropertyDTOList) {
			// 验证skuPropertyDTO内的属性
			verifyNewAddedSkuPropertyDTOProperty(skuPropertyDTO);
			skuPropertyDTO.setId(null);
			skuPropertyDTO.setSkuId(skuId);
			skuPropertyDTO.setSellerId(sellerId);
			skuPropertyDTO.setItemId(itemId);
			skuPropertyDTO.setBizCode(bizCode);
			SkuPropertyDO skuPropertyDO = new SkuPropertyDO();
			ItemUtil.copyProperties(skuPropertyDTO, skuPropertyDO);// DTO转DO
			long newInsertedId = skuPropertyDAO.addSkuProperty(skuPropertyDO);// 新增的记录返回的ID
			skuPropertyDTO = getSkuProperty(newInsertedId, sellerId, bizCode);// 新增加的记录对应的skuPropertyDO
			returnSkuPropertyDTOList.add(skuPropertyDTO);
		}
		return returnSkuPropertyDTOList;
	}

	@Override
	public boolean updateSkuProperty(SkuPropertyDTO skuPropertyDTO) throws ItemException {
		verifyUpdatedSkuPropertyDTOProperty(skuPropertyDTO);
		SkuPropertyDO skuPropertyDO = new SkuPropertyDO();
		ItemUtil.copyProperties(skuPropertyDTO, skuPropertyDO);
		int num = skuPropertyDAO.updateSkuProperty(skuPropertyDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update SkuProperty error-->primary id:"
					+ skuPropertyDTO.getId());
		}
	}

	@Override
	public SkuPropertyDTO getSkuProperty(Long id, Long sellerId, String bizCode) throws ItemException {
		SkuPropertyDO skuPropertyDO = skuPropertyDAO.getSkuProperty(id, sellerId, bizCode);
		if (skuPropertyDO == null) {
			throw ExceptionUtil.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested"
                    + " record doesn't exist from table skuProperty-->id:" + id + " sellerId:" + sellerId + "bizCode:" + bizCode);
        }
		SkuPropertyDTO skuPropertyDTO = new SkuPropertyDTO();
		ItemUtil.copyProperties(skuPropertyDO, skuPropertyDTO);
		return skuPropertyDTO;
	}

	@Override
	public boolean deleteSkuProperty(Long id, Long sellerId, String bizCode) throws ItemException {
		// TODO 验证id
		SkuPropertyDO skuPropertyDO = new SkuPropertyDO();
		skuPropertyDO.setId(id);
		skuPropertyDO.setSellerId(sellerId);
		int num = skuPropertyDAO.deleteSkuProperty(id, sellerId, bizCode);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table sku_property-->id:->id:"
							+ id + " sellerId:" + sellerId);
		}
	}

	/**
	 * 验证新增SkuProperty
	 * 
	 * @param skuPropertyDTO
	 * @throws ItemException
	 */
	private void verifyNewAddedSkuPropertyDTOProperty(SkuPropertyDTO skuPropertyDTO) throws ItemException {
		if (skuPropertyDTO.getName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "keyname is null");
		}
		if (skuPropertyDTO.getValue() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "val is null");
		}
		if (skuPropertyDTO.getSort() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sort is null");
		}
	}

	/**
	 * 验证更新SkuProperty
	 * 
	 * @param skuPropertyDTO
	 * @throws ItemException
	 */
	private void verifyUpdatedSkuPropertyDTOProperty(SkuPropertyDTO skuPropertyDTO) throws ItemException {
		if (skuPropertyDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuPropertyId is null");
		}
		if (skuPropertyDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "seller_id is null");
		}
		// TODO 验证seller_id的合法性
	}

	public List<SkuPropertyDTO> querySkuProperty(SkuPropertyQTO skuPropertyQTO) throws ItemException {
		List<SkuPropertyDO> list = skuPropertyDAO.querySkuProperty(skuPropertyQTO);
		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();// 需要返回的DTO列表
		for (SkuPropertyDO skuPropertyDO : list) {
			SkuPropertyDTO dto = new SkuPropertyDTO();
			ItemUtil.copyProperties(skuPropertyDO, dto);
			skuPropertyDTOList.add(dto);
		}
		return skuPropertyDTOList;
	}

	public List<SkuPropertyDTO> querySkuPropertyWithValue(SkuPropertyQTO skuPropertyQTO) throws ItemException{
		List<SkuPropertyDO> list = skuPropertyDAO.querySkuPropertyWithValue(skuPropertyQTO);
		List<SkuPropertyDTO> skuPropertyDTOList = new ArrayList<SkuPropertyDTO>();// 需要返回的DTO列表
		for (SkuPropertyDO skuPropertyDO : list) {
			SkuPropertyDTO dto = new SkuPropertyDTO();
			ItemUtil.copyProperties(skuPropertyDO, dto);
			skuPropertyDTOList.add(dto);
		}
		return skuPropertyDTOList;
	}

	
	@Override
	public int deleteSkuPropertyListBySkuId(Long skuId, Long sellerId, String bizCode) throws ItemException {
		// TODO 验证sellerId
		if (skuId == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "skuId is null");
		}
		ItemSkuDTO itemSkuDTO = itemSkuManager.getItemSku(skuId, sellerId, bizCode);
		if (itemSkuDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "itemSkuId is not illegle");
		}
		int num = skuPropertyDAO.deleteSkuPropertyBySkuId(skuId, sellerId, bizCode);
		return num;
		// if (num > 0) {
		// return true;
		// } else {
		// throw ExceptionUtil
		// .getException(ResCodeNum.SYS_E_DB_DELETE,
		// "delete from sku_property error-->skuId id:" + skuId
		// + " sellerId:" + sellerId);
		// }
	}

	public int deleteByItemId(Long itemId,Long supplierId)throws ItemException{
		SkuPropertyQTO skuPropertyQTO= new SkuPropertyQTO(); 
		skuPropertyQTO.setItemId(itemId);
		skuPropertyQTO.setSellerId(supplierId);
		return this.skuPropertyDAO.deleteByItemId(skuPropertyQTO);
	}

	@Override
	public void batchUpdateSkuProperty(SkuPropertyDO skuPropertyDO) throws ItemException {
		skuPropertyDAO.batchUpdateSkuProperty(skuPropertyDO);

	}

	@Override
	public Long trashByItemId(Long itemId, Long sellerId) {

		SkuPropertyDO query = new SkuPropertyDO();
		query.setItemId(itemId);
		query.setSellerId(sellerId);
		return skuPropertyDAO.trashByItemId(query);
	}

	@Override
	public Long recoveryByItemId(Long itemId, Long sellerId) {
		SkuPropertyDO query = new SkuPropertyDO();
		query.setItemId(itemId);
		query.setSellerId(sellerId);
		return skuPropertyDAO.recoveryByItemId(query);
	}

	@Override
	public Long emptyRecycleBin(Long sellerId, String bizCode) {
		SkuPropertyDO query = new SkuPropertyDO();
		query.setBizCode(bizCode);
		query.setSellerId(sellerId);
		return skuPropertyDAO.emptyRecycleBin(query);
	}


}

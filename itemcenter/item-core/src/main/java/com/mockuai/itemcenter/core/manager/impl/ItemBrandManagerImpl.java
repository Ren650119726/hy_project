package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.mockuai.itemcenter.core.dao.ItemBrandDAO;
import com.mockuai.itemcenter.core.domain.ItemBrandDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemBrandManager;
import com.mockuai.itemcenter.core.util.ItemUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.DBConst;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemBrandDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemBrandQTO;
import com.mockuai.itemcenter.core.util.ExceptionUtil;

@Service
public class ItemBrandManagerImpl implements ItemBrandManager {
	@Resource
	private ItemBrandDAO itemBrandDAO;

	@Override
	public ItemBrandDTO addItemBrand(ItemBrandDTO itemBrandDTO) throws ItemException {
		try {
			// 验证itemBrandDTO内的属性
			verifyNewAddedItemBrandDTOProperty(itemBrandDTO);
			ItemBrandDO itemBrandDO = new ItemBrandDO();
			ItemUtil.copyProperties(itemBrandDTO, itemBrandDO);// DTO转DO
			long newInsertedId = itemBrandDAO.addItemBrand(itemBrandDO);// 新增的记录返回的ID
			itemBrandDTO = getItemBrand(newInsertedId);// 新增加的记录对应的itemBrandDO
			return itemBrandDTO;
		} catch (Exception e) {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
		}
	}

	@Override
	public boolean updateItemBrand(ItemBrandDTO itemBrandDTO) throws ItemException {
		// 验证更新时的参数
		verifyUpdatedItemBrandDTOProperty(itemBrandDTO);
		ItemBrandDO itemBrandDO = new ItemBrandDO();
		ItemUtil.copyProperties(itemBrandDTO, itemBrandDO);
		int num = itemBrandDAO.updateItemBrand(itemBrandDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update ItemBrand error-->primary id:"
					+ itemBrandDTO.getId());
		}
	}

	@Override
	public ItemBrandDTO getItemBrand(Long id) throws ItemException {
		ItemBrandDO itemDO = itemBrandDAO.getItemBrand(id);
		if (itemDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table item_brand-->id:"
							+ id);
		}
		ItemBrandDTO itemBrandDTO = new ItemBrandDTO();
		ItemUtil.copyProperties(itemDO, itemBrandDTO);
		return itemBrandDTO;
	}

	@Override
	public boolean deleteItemBrand(Long id) throws ItemException {
		int num = itemBrandDAO.deleteItemBrand(id);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete ItemBrand error-->primary id:"
					+ id);
		}
	}

	public List<ItemBrandDTO> queryItemBrand(ItemBrandQTO itemBrandQTO) throws ItemException {
		if (itemBrandQTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemBrandQTO is null");
		}
		itemBrandQTO.setIsDeleted(DBConst.UN_DELETED.getCode());
		List<ItemBrandDO> list = itemBrandDAO.queryItemBrand(itemBrandQTO);
		List<ItemBrandDTO> itemBrandDTOList = new ArrayList<ItemBrandDTO>();// 需要返回的DTO列表
		for (ItemBrandDO itemBrandDO : list) {
			ItemBrandDTO dto = new ItemBrandDTO();
			ItemUtil.copyProperties(itemBrandDO, dto);
			itemBrandDTOList.add(dto);
		}
		return itemBrandDTOList;
	}

	/**
	 * 新增时 验证 itemBrandDTO 字段属性
	 * 
	 * @param itemBrandDTO
	 * @return
	 * @throws ItemException
	 */
	private void verifyNewAddedItemBrandDTOProperty(ItemBrandDTO itemBrandDTO) throws ItemException {
		if (itemBrandDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemBrandDTO is null");
		}
		if (StringUtils.isBlank(itemBrandDTO.getCname())) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemBrand cname is null");
		}
	}

	/**
	 * 更新时 验证 itemBrandDTO 字段属性
	 * 
	 * @param itemBrandDTO
	 * @return
	 * @throws ItemException
	 */
	private void verifyUpdatedItemBrandDTOProperty(ItemBrandDTO itemBrandDTO) throws ItemException {
		// 验证参数
		if (itemBrandDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemBrandDTO is null");
		}
		if (itemBrandDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemBrandDTO ID is null");
		}
	}

}

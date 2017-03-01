package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCollectionDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCollectionQTO;
import com.mockuai.itemcenter.core.dao.ItemCollectionDAO;
import com.mockuai.itemcenter.core.domain.ItemCollectionDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCollectionManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCollectionManagerImpl implements ItemCollectionManager {
	@Resource
	private ItemCollectionDAO itemCollectionDAO;

	public ItemCollectionDTO addItemCollection(ItemCollectionDTO itemCollectionDTO) throws ItemException {
		try {
			// 验证itemBrandDTO内的属性
			verifyNewAddedItemCollectionDTOProperty(itemCollectionDTO);
			ItemCollectionDO itemCollectionDO = new ItemCollectionDO();
			ItemUtil.copyProperties(itemCollectionDTO, itemCollectionDO);// DTO转DO
			long newInsertedId = itemCollectionDAO.addItemCollection(itemCollectionDO);// 新增的记录返回的ID
			itemCollectionDTO = getItemCollection(newInsertedId, itemCollectionDTO.getUserId());// 新增加的记录对应的itemCollectionDO
			return (ItemCollectionDTO) itemCollectionDTO;
		} catch (Exception e) {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
		}
	}

	@Override
	public boolean deleteItemCollectionByItemId(Long itemId ,Long userId,Long shareUserId) throws ItemException {

		int num = itemCollectionDAO.deleteItemCollectionByItemId(itemId,userId,shareUserId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete ItemCollection error-->item id:"
					+ itemId);
		}
	}

	@Override
	public List<ItemCollectionDTO> queryItemCollection(ItemCollectionQTO itemCollectionQTO) throws ItemException {
		if (itemCollectionQTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemCollectionQTO is null");
		}
		if (itemCollectionQTO.getUserId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "user_id is null");
		}
		List<ItemCollectionDO> list = itemCollectionDAO.queryItemCollection(itemCollectionQTO);
		List<ItemCollectionDTO> itemCollectionDTOList = new ArrayList<ItemCollectionDTO>();// 需要返回的DTO列表
		for (ItemCollectionDO itemCollectionDO : list) {
			ItemCollectionDTO dto = new ItemCollectionDTO();
			ItemUtil.copyProperties(itemCollectionDO, dto);
			itemCollectionDTOList.add(dto);
		}
		return itemCollectionDTOList;
	}

	/**
	 * 新增时 验证 itemCollectionDTO 字段属性
	 * 
	 * @param itemCollectionDTO
	 * @return
	 * @throws ItemException
	 */
	private void verifyNewAddedItemCollectionDTOProperty(ItemCollectionDTO itemCollectionDTO) throws ItemException {
		if (itemCollectionDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemCollectionDTO is null");
		}
		if (itemCollectionDTO.getItemId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_id is null");
		}
		if (itemCollectionDTO.getUserId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "user_id is null");
		}
	}

	@Override
	public ItemCollectionDTO getItemCollection(Long id, Long userId) throws ItemException {
		// TODO Auto-generated method stub
		ItemCollectionDO itemCollectionDO = itemCollectionDAO.getItemCollection(id, userId);
		if (itemCollectionDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table item-->id:"
							+ id );
		}
		ItemCollectionDTO itemCollectionDTO = new ItemCollectionDTO();
		ItemUtil.copyProperties(itemCollectionDO, itemCollectionDTO);
		return itemCollectionDTO;
	}

}

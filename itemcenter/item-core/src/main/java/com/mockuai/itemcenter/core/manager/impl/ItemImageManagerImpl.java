package com.mockuai.itemcenter.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemImageDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemImageQTO;
import com.mockuai.itemcenter.core.dao.ItemImageDAO;
import com.mockuai.itemcenter.core.domain.ItemImageDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemImageManager;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;

@Service
public class ItemImageManagerImpl implements ItemImageManager {
	@Resource
	private ItemImageDAO itemImageDAO;

	@Resource
	private ItemManager itemManager;

	@Override
	public List<ItemImageDTO> addItemImage(Long itemId, Long sellerId, List<ItemImageDTO> itemImageDTOList)
			throws ItemException {
		List<ItemImageDTO> returnArrayList = new ArrayList<ItemImageDTO>();
		// TODO 验证供应商ID
		if (sellerId == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
		// 验证商品ID
		if (itemId == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_id is null");
		}
		
		/* updated by cwr 不需要该不验证
		ItemDTO itemDTO = itemManager.getItem(itemId, sellerId);
		if (itemDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "itemId  is invalid");
		}
		*/
		
		try {
			for (ItemImageDTO itemImageDTO : itemImageDTOList) {
				// 验证itemImageDTO内的属性
				verifyNewAddedItemImageDTOProperty(itemImageDTO);
				itemImageDTO.setItemId(itemId);
				itemImageDTO.setId(null);
				itemImageDTO.setSellerId(sellerId);
				ItemImageDO itemImageDO = new ItemImageDO();
				ItemUtil.copyProperties(itemImageDTO, itemImageDO);// DTO转DO
				long newInsertedId = itemImageDAO.addItemImage(itemImageDO);// 新增的记录返回的ID
				itemImageDTO = getItemImage(newInsertedId, sellerId);// 新增加的记录对应的itemImageDO
				returnArrayList.add(itemImageDTO);
			}
			return returnArrayList;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}

	@Override
	public boolean updateItemImage(ItemImageDTO itemImageDTO) throws ItemException {
		// 验证参数
		if (itemImageDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemImageDTO is null");
		}
		ItemImageDO itemImageDO = new ItemImageDO();
		ItemUtil.copyProperties(itemImageDTO, itemImageDO);
		int num = itemImageDAO.updateItemImage(itemImageDO);
		return num > 0 ? true : false;
	}

	@Override
	public ItemImageDTO getItemImage(Long id, Long sellerId) throws ItemException {
		ItemImageDO itemImageDO = itemImageDAO.getItemImage(id, sellerId);
		if (itemImageDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "Cannot find requested record from item_image id:"
							+ id + " sellerId:" + sellerId);
		}
		ItemImageDTO itemImageDTO = new ItemImageDTO();
		ItemUtil.copyProperties(itemImageDO, itemImageDTO);
		return itemImageDTO;
	}

	@Override
	public int deleteItemImageList(List<ItemImageDTO> itemImageDTOList) throws ItemException {
		int deletedNum = 0;
		int totalCount = 0;
		for (ItemImageDTO itemImageDTO : itemImageDTOList) {
			// TODO 验证供应商ID
			Long sellerId = itemImageDTO.getSellerId();
			Long id = itemImageDTO.getId();
			if (id == null) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "id is null");
			}
			if (sellerId == null) {
				throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
			}
			deletedNum = itemImageDAO.deleteItemImage(id, sellerId);
			if (deletedNum != 1) {
				throw ExceptionUtil
						.getException(ResponseCode.PARAM_E_INVALID, "Cannot find requested record from item_image id:"
								+ id + " sellerId:" + sellerId);
			}
			totalCount++;
		}
		return totalCount;
	}

	@Override
	public int deleteItemImageListByItemId(Long itemId, Long sellerId) throws ItemException {
		verifyDeletedItemImageDTOProperty(itemId, sellerId);
		// 总共删除的条数
		// int totalCount = 0;
		// for (ItemImageDTO itemImageDTO : deletedImageDTOList) {
		// verifyDeletedItemImageDTOProperty(itemImageDTO);
		// ItemImageDO itemImageDO = new ItemImageDO();
		// itemImageDO.setId(id);
		// itemImageDO.setIsDeleted(DBConst.DELETED.getCode());
		// int num = itemImageDAO.deleteItemImageList(id, sellerId);
		// totalCount += num;
		// }
		int num = itemImageDAO.deleteItemImageListByItemId(itemId, sellerId);
		return num;
		// if (num > 0) {
		// return true;
		// } else {
		// throw ExceptionUtil.getException(ResCodeNum.PARAM_E_INVALID,
		// "delete item error-->itemId id:" + itemId
		// + " sellerId:" + sellerId);
		// }
	}

	private void verifyDeletedItemImageDTOProperty(Long itemId, Long sellerId) throws ItemException {
		// TODO 供应商ID
		if (sellerId == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
		// 验证商品ID
		if (itemId == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_id is null");
		}
		
		/*不需要该步骤
		ItemDTO itemDTO = itemManager.getItem(itemId, sellerId);
		if (itemDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "itemId  is invalid");
		}
		*/
	}

	private void verifyNewAddedItemImageDTOProperty(ItemImageDTO itemImageDTO) throws ItemException {
		if (itemImageDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemImageDTO is null");
		}
		if (itemImageDTO.getImageUrl() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "img_url is null");
		}
	}

	public List<ItemImageDTO> queryItemImage(ItemImageQTO itemImageQTO) throws ItemException {
		List<ItemImageDO> list = itemImageDAO.queryItemImage(itemImageQTO);
		List<ItemImageDTO> itemImageDTOList = new ArrayList<ItemImageDTO>();// 需要返回的DTO列表
		for (ItemImageDO itemImageDO : list) {
			ItemImageDTO dto = new ItemImageDTO();
			ItemUtil.copyProperties(itemImageDO, dto);
			itemImageDTOList.add(dto);
		}
		return itemImageDTOList;
	}

    @Override
    public Long trashItemImageListByItemId(Long itemId, Long sellerId) throws ItemException {

        ItemImageDO query = new ItemImageDO();
        query.setItemId(itemId);
        query.setSellerId(sellerId);
        return itemImageDAO.trashItemImageListByItemId(query);
    }

    @Override
    public Long recoveryItemImageListByItemId(Long itemId, Long sellerId) throws ItemException {

        ItemImageDO query = new ItemImageDO();
        query.setItemId(itemId);
        query.setSellerId(sellerId);
        return itemImageDAO.recoveryItemImageListByItemId(query);
    }

    @Override
    public Long emptyRecycleBin(Long sellerId, String bizCode) throws ItemException {

        ItemImageDO query = new ItemImageDO();
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        return itemImageDAO.emptyRecycleBin(query);
    }

}

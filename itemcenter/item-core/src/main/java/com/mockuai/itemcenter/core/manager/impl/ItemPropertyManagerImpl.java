package com.mockuai.itemcenter.core.manager.impl;

import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemPropertyDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemPropertyQTO;
import com.mockuai.itemcenter.core.dao.ItemPropertyDAO;
import com.mockuai.itemcenter.core.domain.ItemPropertyDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemPropertyManager;
import com.mockuai.itemcenter.core.service.action.item.AddItemAction;
import com.mockuai.itemcenter.core.util.ExceptionUtil;
import com.mockuai.itemcenter.core.util.ItemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemPropertyManagerImpl implements ItemPropertyManager {

    private static final Logger log = LoggerFactory.getLogger(AddItemAction.class);

    @Resource
	private ItemPropertyDAO itemPropertyDAO;

	//@Resource
	//private ItemManager itemManager;

	@Override
	public ItemPropertyDTO addItemProperty(ItemPropertyDTO itemPropertyDTO) throws ItemException {
		try {
			// 验证itemPropertyDTO内的属性
			verifyNewAddedItemPropertyDTOProperty(itemPropertyDTO);
			ItemPropertyDO itemPropertyDO = new ItemPropertyDO();
			ItemUtil.copyProperties(itemPropertyDTO, itemPropertyDO);// DTO转DO
			long newInsertedId = itemPropertyDAO.addItemProperty(itemPropertyDO);// 新增的记录返回的ID
			long sellerId = itemPropertyDTO.getSellerId();
			itemPropertyDTO.setId(newInsertedId);
			//itemPropertyDTO = getItemProperty(newInsertedId, sellerId);// 新增加的记录对应的itemPropertyDO
			return itemPropertyDTO;
		} catch (Exception e) {
			throw new ItemException(ResponseCode.SYS_E_DEFAULT_ERROR, e);
		}
	}
    public void addItemProperty(List<ItemPropertyDTO> itemPropertyList) throws ItemException {
        try {
            itemPropertyDAO.addItemPropertyList(itemPropertyList);
        }catch (Exception e){
            log.error("cause error addItemProperty",e);
            throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, e);

        }
    }


        @Override
	public boolean updateItemProperty(ItemPropertyDTO itemPropertyDTO) throws ItemException {
		// 验证参数
		verifyUpdatedItemPropertyDTOProperty(itemPropertyDTO);
		ItemPropertyDO itemPropertyDO = new ItemPropertyDO();
		ItemUtil.copyProperties(itemPropertyDTO, itemPropertyDO);
		int num = itemPropertyDAO.updateItemProperty(itemPropertyDO);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_UPDATE, "update itemProperty error-->primary id:"
					+ itemPropertyDTO.getId() + " sellerId:" + itemPropertyDTO.getSellerId());
		}
	}

	@Override
	public ItemPropertyDTO getItemProperty(Long id, Long sellerId) throws ItemException {
		ItemPropertyDO itemPropertyDO = itemPropertyDAO.getItemProperty(id, sellerId);
		if (itemPropertyDO == null) {
			throw ExceptionUtil
					.getException(ResponseCode.BASE_PARAM_E_RECORD_NOT_EXIST, "requested record doesn't exist from table itemProperty-->id:"
							+ id + " sellerId:" + sellerId);
		}
		ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
		ItemUtil.copyProperties(itemPropertyDO, itemPropertyDTO);
		return itemPropertyDTO;
	}

	@Override
	public boolean deleteItemProperty(Long id, Long sellerId) throws ItemException {
		int num = itemPropertyDAO.deleteItemProperty(id, sellerId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil
					.getException(ResponseCode.SYS_E_DB_DELETE, "requested record doesn't exist from table itemProperty-->id:"
							+ id + " sellerId:" + sellerId);
		}
	}

	private void verifyNewAddedItemPropertyDTOProperty(ItemPropertyDTO itemPropertyDTO) throws ItemException {
		// 1.验证supplier_id
		// 2.验证item_id
		if (itemPropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemPropertyDTO is null");
		}
		if (itemPropertyDTO.getName() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "name is null");
		}
		if (itemPropertyDTO.getValue() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "value is null");
		}
		// 验证item_id
		if (itemPropertyDTO.getItemId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemId is null");
		}
		// TODO 验证supplier_id
		if (itemPropertyDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "sellerId is null");
		}
		
		// update by cwr 不需要该验证
		/*
		Long sellerId = itemPropertyDTO.getSellerId();// 供应商ID
		Long itemId = itemPropertyDTO.getItemId();
		ItemDTO itemDTO = itemManager.getItem(itemId, sellerId);
		
		if (itemDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_INVALID, "itemId  is invalid");
		}
		*/

	}

	private void verifyUpdatedItemPropertyDTOProperty(ItemPropertyDTO itemPropertyDTO) throws ItemException {
		if (itemPropertyDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "itemPropertyDTO is null");
		}
		if (itemPropertyDTO.getId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "item_property id is null");
		}
		// TODO 验证supplier_id
		if (itemPropertyDTO.getSellerId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "supplier_id is null");
		}
	}

	public List<ItemPropertyDTO> queryItemProperty(ItemPropertyQTO itemPropertyQTO) throws ItemException {
		List<ItemPropertyDO> list = itemPropertyDAO.queryItemProperty(itemPropertyQTO);
		List<ItemPropertyDTO> itemPropertyDTOList = new ArrayList<ItemPropertyDTO>();// 需要返回的DTO列表
		for (ItemPropertyDO itemPropertyDO : list) {
			ItemPropertyDTO dto = new ItemPropertyDTO();
			ItemUtil.copyProperties(itemPropertyDO, dto);
			itemPropertyDTOList.add(dto);
		}
		return itemPropertyDTOList;
	}
	
	public int deleteByItemId(Long itemId,Long supplierId)throws ItemException{
		ItemPropertyDO qto = new ItemPropertyDO();
		qto.setItemId(itemId);
		qto.setSellerId(supplierId);
		return this.itemPropertyDAO.deleteByItemId(qto);
	}

    @Override
    public Long trashByItemId(Long itemId, Long sellerId) {
        ItemPropertyDO query = new ItemPropertyDO();
        query.setItemId(itemId);
        query.setSellerId(sellerId);
        return itemPropertyDAO.trashByItemId(query);
    }

    @Override
    public Long recoveryByItemId(Long itemId, Long sellerId) {
        ItemPropertyDO query = new ItemPropertyDO();
        query.setItemId(itemId);
        query.setSellerId(sellerId);
        return itemPropertyDAO.recoveryByItemId(query);
    }

    @Override
    public Long emptyRecycleBin(Long sellerId, String bizCode) {
        ItemPropertyDO query = new ItemPropertyDO();
        query.setBizCode(bizCode);
        query.setSellerId(sellerId);
        return itemPropertyDAO.emptyRecycleBin(query);
    }

    @Override
    public Integer countBy(ItemPropertyQTO itemPropertyQTO){


        return itemPropertyDAO.countBy(itemPropertyQTO);
    }

}

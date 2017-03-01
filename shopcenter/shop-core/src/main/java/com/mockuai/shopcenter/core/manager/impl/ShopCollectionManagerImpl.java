package com.mockuai.shopcenter.core.manager.impl;

import com.mockuai.shopcenter.constant.ResponseCode;
import com.mockuai.shopcenter.core.dao.ShopCollectionDAO;
import com.mockuai.shopcenter.core.domain.ShopCollectionDO;
import com.mockuai.shopcenter.core.exception.ShopException;
import com.mockuai.shopcenter.core.manager.ShopCollectionManager;
import com.mockuai.shopcenter.core.util.ExceptionUtil;
import com.mockuai.shopcenter.core.util.TimeUtil;
import com.mockuai.shopcenter.domain.dto.ShopCollectionDTO;
import com.mockuai.shopcenter.domain.qto.ShopCollectionQTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCollectionManagerImpl implements ShopCollectionManager {

	@Resource
	private ShopCollectionDAO shopCollectionDAO;

	public ShopCollectionDTO addShopCollection(ShopCollectionDTO shopCollectionDTO) throws ShopException {
		try {
			verifyNewAddedShopCollectionDTOProperty(shopCollectionDTO);
			ShopCollectionDO shopCollectionDO = new ShopCollectionDO();
			BeanUtils.copyProperties(shopCollectionDTO, shopCollectionDO);
			long newInsertedId = shopCollectionDAO.addShopCollection(shopCollectionDO);// 新增的记录返回的ID
			shopCollectionDTO = getShopCollection(newInsertedId, shopCollectionDTO.getUserId());// 新增加的记录对应的ShopCollectionDO
			return shopCollectionDTO;
		} catch (Exception e) {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DEFAULT_ERROR, e.getMessage());
		}
	}

	@Override
	public boolean deleteShopCollection(Long sellerId ,Long userId) throws ShopException {

		int num = shopCollectionDAO.deleteShopCollection(sellerId, userId);
		if (num > 0) {
			return true;
		} else {
			throw ExceptionUtil.getException(ResponseCode.SYS_E_DB_DELETE, "delete ShopCollection error-->sellerId:"
					+ sellerId);
		}
	}

	@Override
	public List<ShopCollectionDTO> queryShopCollection(ShopCollectionQTO shopCollectionQTO) throws ShopException {
		if (shopCollectionQTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "ShopCollectionQTO is null");
		}
		shopCollectionQTO.setDeleteMark(0);
		List<ShopCollectionDO> list = shopCollectionDAO.queryShopCollection(shopCollectionQTO);
		List<ShopCollectionDTO> shopCollectionDTOList = new ArrayList<ShopCollectionDTO>();// 需要返回的DTO列表
		for (ShopCollectionDO shopCollectionDO : list) {
			ShopCollectionDTO dto = new ShopCollectionDTO();
			BeanUtils.copyProperties(shopCollectionDO, dto);
			dto.setCreateTime(TimeUtil.getFormatTime(shopCollectionDO.getGmtCreated(), TimeUtil.FORMAT_TIME));
			shopCollectionDTOList.add(dto);
		}
		return shopCollectionDTOList;
	}

	@Override
	public Integer countShopCollection(ShopCollectionQTO query) throws ShopException {
		return shopCollectionDAO.countShopCollection(query);
	}

	/**
	 * 新增时 验证 ShopCollectionDTO 字段属性
	 * 
	 * @param shopCollectionDTO
	 * @return
	 * @throws ShopException
	 */
	private void verifyNewAddedShopCollectionDTOProperty(ShopCollectionDTO shopCollectionDTO) throws ShopException {
		if (shopCollectionDTO == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "ShopCollectionDTO is null");
		}
		if (shopCollectionDTO.getShopId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "ShopId is null");
		}
		if (shopCollectionDTO.getUserId() == null) {
			throw ExceptionUtil.getException(ResponseCode.PARAM_E_MISSING, "user_id is null");
		}
	}

	@Override
	public ShopCollectionDTO getShopCollection(Long sellerId, Long userId) throws ShopException {
		// TODO Auto-generated method stub
		ShopCollectionDO shopCollectionDO = shopCollectionDAO.getShopCollection(sellerId, userId);
		if (shopCollectionDO == null) {
			return null;
		}
		ShopCollectionDTO shopCollectionDTO = new ShopCollectionDTO();
		BeanUtils.copyProperties(shopCollectionDAO, shopCollectionDTO);
		shopCollectionDTO.setCreateTime(TimeUtil.getFormatTime(shopCollectionDO.getGmtCreated(), TimeUtil.FORMAT_TIME));
		return shopCollectionDTO;
	}
}

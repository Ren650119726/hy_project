package com.mockuai.itemcenter.core.util;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.constant.ItemUnit;
import org.springframework.beans.BeanUtils;

import com.mockuai.itemcenter.common.constant.ItemStatus;

public class ItemUtil {

	private static final String UNDERLINE = "_";
	
	/**
	 * 使用Spring框架下的BeanUtil方法copy属性， 将obj1的属性的值拷到obj2的属性
	 * 
	 * @param obj1
	 * @param obj2
	 */

	public static void copyProperties(Object fromObj, Object ToObj) {
		BeanUtils.copyProperties(fromObj, ToObj);
	}

	/**
	 * 商品状态转化
	 * @param status
	 * @return
	 */
	public static String convertItemStatus(Integer status){
		if(status == null){
			return null;
		}
		int val = status.intValue();
		if(val == ItemStatus.PENDING_AUDIT.getStatus())
			return ItemStatus.PENDING_AUDIT.getStatusName();
		if(val == ItemStatus.AUDIT_SUCCESS.getStatus())
			return ItemStatus.AUDIT_SUCCESS.getStatusName();
		if(val == ItemStatus.AUDIT_FAIL.getStatus())
			return ItemStatus.AUDIT_FAIL.getStatusName();
		if(val == ItemStatus.ON_SALE.getStatus())
			return ItemStatus.ON_SALE.getStatusName();
		if(val== ItemStatus.WITHDRAW.getStatus())
			return ItemStatus.WITHDRAW.getStatusName();
		if(val == ItemStatus.FROZEN.getStatus())
			return ItemStatus.FROZEN.getStatusName();
		if(val == ItemStatus.PRE_SALE.getStatus())
			return ItemStatus.PRE_SALE.getStatusName();
		
		return null;
	}
	
	/**
	 * 生产uid通用类
	 * @param id
	 * @param sellerId
	 * @return
	 */
	public static String genUid(Long id,Long sellerId){
		if(id ==null || sellerId  == null){
			return null;
		}
		return String.valueOf(sellerId) + UNDERLINE + String.valueOf(id);
	}

	public static String convertItemUnit(Integer unit) {
		ItemUnit itemUnit =  ItemUnit.getItemUnit(unit);
		return itemUnit == null ? null : itemUnit.getName();
	}

	public static ItemUidDTO parseUid(String itemUid) {

		if (Strings.isNullOrEmpty(itemUid)) {
			return null;
		}
		try {
			ItemUidDTO itemUidDTO = new ItemUidDTO();
			String[] strs = itemUid.split("_");
			if (strs.length != 2) {
				return null;
			}

			long sellerId = Long.parseLong(strs[0]);
			long itemId = Long.parseLong(strs[1]);
			itemUidDTO.setSellerId(sellerId);
			itemUidDTO.setItemId(itemId);

			return itemUidDTO;
		} catch (Exception e) {
			//TODO error handle
		}
		return null;
	}
}

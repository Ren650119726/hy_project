package com.mockuai.tradecenter.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mockuai.tradecenter.common.domain.refund.RefundReasonDTO;

public enum EnumRefundReason {


	DESCRIPTION_DOES_NOT_MATCH(2,"与商品描述不符"),
    
	BOUGHT_THE_WRONG(3,"拍错/不喜欢/效果不好"),
	
	SELLER_SEND_THE_WRONG_GOODS(4,"卖家发错货"),
	
	DAMAGE_OR_STAIN(5,"破损或污渍"),
	
	OTHER(1,"其他"),
	;

	EnumRefundReason(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	private Integer code;

	private String description;


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static EnumRefundReason getByCode(Integer code) {
		for (EnumRefundReason type : values()) {
			if (type.getCode()==code) {
				return type;
			}
		}
		return null;
	}
	
	public static Map<Integer,String> toMap(){
		Map<Integer,String> map = new HashMap<Integer,String>();
		for (EnumRefundReason institution : values()) {
			map.put(institution.getCode(), institution.getDescription());
		}
		return map;
	}


	public static List<RefundReasonDTO> toList() {
		List<RefundReasonDTO> list = new ArrayList<RefundReasonDTO>();
		for (EnumRefundReason institution : values()) {
			RefundReasonDTO dto = new RefundReasonDTO();
			dto.setRefundReasonId(institution.getCode());
			dto.setRefundReason(institution.getDescription());
			list.add(dto);
		}
		return list;
	}

}

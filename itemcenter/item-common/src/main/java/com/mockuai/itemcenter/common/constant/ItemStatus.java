package com.mockuai.itemcenter.common.constant;


public enum ItemStatus {
	
	PENDING_AUDIT(1,"待审核"),
	
	AUDIT_SUCCESS(2,"审核通过"),
	
	AUDIT_FAIL(3,"审核不通过"),
	
	ON_SALE(4,"上架"),
	
	WITHDRAW(5,"下架"),
	
	FROZEN(6,"冻结"),

	PRE_SALE(7, "预售"),

    //特殊商品的失效状态，该状态与下架状态表现一致，但不可再被上架（包括手动和自动）
	DISABLE(8,"失效"),

    //主营类目SKU模板修改造成的商品异常状体，该状态与下架状态表现一致，，但不可再被直接上架（包括手动和自动），但将sku编辑为可用之后，恢复为下架状态
    SKU_INVALID(9,"SKU不可用");

	private int status;
	
	private String statusName;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	private ItemStatus(int status, String statusName) {
		this.status = status;
		this.statusName = statusName;
	}
}

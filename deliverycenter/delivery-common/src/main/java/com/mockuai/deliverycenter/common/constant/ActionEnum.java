package com.mockuai.deliverycenter.common.constant;

public enum ActionEnum {
	/************************************ 快递相关操作接口 ********************************************/
	
	QUERY_DELIVERY_COMPANY("queryDeliveryCompany"),
	
	/**
	 * 添加快递
	 */
	ADD_EXPRESS("AddExpress"),
	/**
	 * 删除快递
	 */
	DELETE_EXPRESS("DeleteExpress"),
	/**
	 * 修改快递
	 */
	UPDATE_EXPRESS("UpdateExpress"),
	/**
	 * 查询快递
	 */
	QUERY_EXPRESS("QueryExpress"),
	/**
	 * 新增快递属性
	 */
	ADD_EXPRESS_PROPERTY("AddExpressProperty"),
	/**
	 * 删除快递属性
	 */
	DELETE_EXPRESS_PROPERTY("DeleteExpressProperty"),
	/**
	 * 校验快递是否支持配送
	 */
	CHECK_REGION_SUPPORTED("CheckRegionSupported"),
	/************************************ 运费相关操作接口 ********************************************/
	/**
	 * 添加运费标准
	 */
	ADD_STDFEE("AddStdFee"),
	/**
	 * 删除运费标准
	 */
	DELETE_STDFEE("DeleteStdFee"),
	/**
	 * 修改运费标准
	 */
	UPDATE_STDFEE("UpdateStdFee"),
	/**
	 * 查询运费标准
	 */
	QUERY_STDFEE("QueryStdFee"),
	/**
	 * 查询运费
	 */
	QUERY_DELIVERYFEE("QueryDeliveryFee"),
	/**
	 * 新增区域运费
	 */
	ADD_REGION_FEE("AddRegionFee"),
	/**
	 * 删除区域运费
	 */
	DELETE_REGION_FEE("DeleteRegionFee"),
	/**
	 * 添加区域
	 */
	ADD_REGION("AddRegion"),
	/**
	 * 删除区域
	 */
	DELETE_REGION("DeleteRegion"),
	/**
	 * 修改区域
	 */
	UPDATE_REGION("UpdateRegion"),
	/**
	 * 查询区域
	 */
	QUERY_REGION("QueryRegion"),
	/**
	 * 根据ID查询区域
	 */
	GET_REGION("GetRegion"),
	/************************************ 仓库相关操作接口 ********************************************/
	/**
	 * 添加仓库
	 */
	ADD_STORAGE("AddStorage"),
	/**
	 * 删除仓库
	 */
	DELETE_STORAGE("DeleteStorage"),
	/**
	 * 修改仓库
	 */
	UPDATE_STORAGE("UpdateStorage"),
	/**
	 * 查询仓库
	 */
	QUERY_STORAGE("QueryStorage"),
	/**
	 * 添加发货仓库
	 */
	ADD_STORAGE_SEND("AddStorageSend"),
	/**
	 * 删除发货仓库
	 */
	DELETE_STORAGE_SEND("DeleteStorageSend"),
	/**
	 * 修改发货仓库
	 */
	UPDATE_STORAGE_SEND("UpdateStorageSend"),
	/**
	 * 查询发货仓库
	 */
	QUERY_STORAGE_SEND("QueryStorageSend") ,
	
	/**
	 * 新增物流信息
	 */
	ADD_DELIVERY_DETAIL("AddDeliveryDetail"),
	
	/**
	 * 获取物流信息
	 */
	QUERY_DELIVERY_DETAIL("QueryDeliveryDetail"),
	
	/**
	 * 新增物流单信息
	 */
	ADD_DELIVERY_INFO("AddDeliveryInfo"),
	
	BATCH_ADD_DELIVERY_INFO("batchAddDeliveryInfoAction"),
	
	/**
	 * 查询物流单信息
	 */
	QUERY_DELIVERY_INFO("QueryDeliveryInfo"),
	
	GET_DELIVERY_INFO("GetDeliveryInfo"),
	
	QUERY_THIRDPARTY_EXPRESS("query_thirdparty_express"),
	
	UPATE_DELIVERY_INFO("updateDeliveryInfo"),
	;
	private String actionName;

	private ActionEnum(String actionName) {
		this.actionName = actionName;
	}

	public static ActionEnum getActionEnum(String actionName) {
		for (ActionEnum ae : ActionEnum.values()) {
			if (ae.actionName.equals(actionName)) {
				return ae;
			}
		}
		return null;
	}

	public String getActionName() {
		return actionName;
	}
}

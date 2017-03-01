package com.mockuai.dts.common.constant;

public enum ActionEnum {
	
	EXPORT_TRANSLOG("exportTransLogAction"),

	EXPORT_ORDER("exportOrderAction"),
	
	//导出客户关系
	EXPORT_SELLER_USER("exportSellerUser"),
	
	// 导出商品
	EXPORT_ITEM("exportItemAction"),

	QUERY_EXPORT_TASK("queryExportTask"),

	LABEL_EXPORT_TASK("labelExportTask"),

	MEMBER_EXPORT_TASK("memberExportTask"),

	DISTRIBUTION_STATISTICS_EXPORT_TASK("distributionStatisticsExportTask"),

	DISTRIBUTION_WITHDRAW_RECORD_EXPROT_TASK("distributionWithdrawRecordExportTask"),

	DELETE_EXPORT_TASK("deleteExportTask"),

	MEMBER_DATA_MIGRATE_TASK("memberDataMigrateTask"),

	EXPORT_MOBILE_SEGMENT_TASK("exportMobileSegmentTask"),

	EXPORT_MOBILE_VIRTUAL_ITEM_TASK("exportMobileVirtualItemTask"),

	EXPORT_RECHARGE_ORDER_TASK("exportRechargeOrderTask"),

	IMPORT_MOBILE_SEGMENT_TASK("importMobileSegmentTask");

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

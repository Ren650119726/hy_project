package com.mockuai.imagecenter.common.constant;

public enum ActionEnum {
    GET_SHOP_RECOMMEND_QRCODE("getShopRecommendQrcode"),
	GET_ITEM("getItemAction"),
	BATCH_GENAERATE_ITEM_QRCODE("batchGenerateItemQrcodeAction"),
    GET_QRCODE("getQrcode"),
	GENERATE_ITEM_QRCODE("generateItemQrcode"),
	GENERATE_SHOP_QRCODE("generateShopQrcode"),
	GENERATE_RECOMMEND_QRCODE("generateRecommendQrcode"),
	DELETE_ITEM("DeleteItemAction"),

	EXPORT_TRANSLOG("exportTransLogAction"),

	INVITE_REGISTER_QRCODE("inviteRegisterQrcode"),


	EXPORT_VIRTUAL_WEALTH_RECORD_TASK("exportVirtualWealthRecordTask");

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

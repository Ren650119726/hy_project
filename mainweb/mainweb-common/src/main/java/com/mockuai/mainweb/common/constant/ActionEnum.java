package com.mockuai.mainweb.common.constant;

public enum ActionEnum {
	PREVIEW_PAGE("previewPage"),
	CANCEL_PAGE("cancellPage"),
	PAGE_PREVIEW("pagePreview"),
	SHOW_PAGE_LIST("showPageList"),
	ADD_PAGE("addPage"),
	DELETE_PAGE("deletePage"),
	UPDATE_PAGE("updatePage"),
	QUERY_PAGE("queryPage"),
	QUERY_PAGE_NAMES("queryPageNames"),
	QUERY_TAB_NAMES("queryTabNames"),
	GET_PAGE("getPage"),
	ADD_PUBLISH_PAGE("addPublishPage"),
	DELETE_THEN_PUBLISH("deleteThenPublish"),
	ADD_SECKILL_THEN_PUBLISH("addSeckillThenPublish"),
	DELETE_SECKILL_THEN_PUBLISH("deleteSeckillThenPublish"),
	GET_PUBLISH_PAGE("getPublishPage");











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

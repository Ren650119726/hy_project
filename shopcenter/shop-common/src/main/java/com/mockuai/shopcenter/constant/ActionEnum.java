package com.mockuai.shopcenter.constant;

public enum ActionEnum {

	/**
	 * ----店铺相关---------------------------------------
	 */

	ADD_SHOP("addShop"),

	GET_SHOP("getShop"),

	QUERY_SHOP("queryShop"),

	UPDATE_SHOP("updateShop"),

	ADD_SHOP_ITEM_GROUP("addShopItemGroup"),

	DELETE_SHOP_ITEM_GROUP("deleteShopItemGroup"),

	GET_SHOP_ITEM_GROUP("getShopItemGroup"),

	UPDATE_SHOP_ITEM_GROUP("updateShopItemGroup"),

	QUERY_SHOP_ITEM_GROUP("queryShopItemGroup"),

    QUERY_SHOP_ITEM_GROUP_BY_SHOP("queryShopItemGroupByShop"),

	CANCEL_SHOP_COLLECTION("cancelShopCollection"),

	QUERY_SHOP_COLLECTION_USER("queryShopCollectionUser"),

	QUERY_USER_COLLECTION_SHOP("queryUserCollectionShop"),

	CHECK_SHOP_COLLECTION("checkShopCollection"),

	GET_SHOP_STATUS("getShopStatus"),

	FREEZE_SHOP("freezeShop"),

	THAW_SHOP("thawShop"),

	COUNT_ALL_SHOP("countShop"),

	COUNT_LAST_DAYS_SHOP("countLastDaysShop"),


    /** 门店相关 **/

	ADD_STORE("addStore"),

	UPDATE_STORE("updateStore"),

	GET_STORE("getStore"),

    DELETE_STORE("deleteStore"),

	QUERY_STORE("queryStore"),

	QUERY_STORE_BY_COORDINATES("queryStoreByCoordinates"),

	GET_STORE_BY_COORDINATES("getStoreByCoordinates"),

	GET_STORE_BY_ADDRESS("getStoreByAddress"),

	GET_STORE_BY_OWNER_ID("getStoreByOwnerId"),

    QUERY_STORE_IDS_BY_PROPERTY("queryStoreIdsByProperty"),

    BATCH_RESET_STORE_PROPERTY("batchResetStoreProperty"),

    /**  店铺配置  **/

    SET_SHOP_PROPERTY("setShopProperty"),

    GET_SHOP_PROPERTY("getShopProperty"),

    SET_SHOP_PROPERTIES("setShopProperties"),

    GET_SHOP_PROPERTIES("getShopProperties"),
    /**  地区信息   **/

    QUERY_REGION("queryRegion"),

	/** 店铺详情页 **/
    GET_SHOP_DETAILS("getShopDetail"),

    /** 设置店铺详情页 **/
    SET_SHOP_DETAILS("setShopDetail"),


	DELETE_SHOP_COLLECTION("deleteShopCollection"),

	ADD_SHOP_COLLECTION("addShopCollection");

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

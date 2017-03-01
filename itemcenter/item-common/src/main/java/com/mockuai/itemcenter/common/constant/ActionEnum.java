package com.mockuai.itemcenter.common.constant;

public enum ActionEnum {

	/**
	 * ----商品相关---------------------------------------
	 */

	// 增加商品
	ADD_ITEM("addItemAction"),
	COPY_ITEM("copyItemAction"),

	// 更新商品
	UPDATE_ITEM("updateItemAction"),

	GET_ITEM_STOCK("getItemStockAction"),

	//更新商品，特别地，日期字段为null时表示置空
	UPDATE_ITEM_WITH_BLANK_DATE("updateItemWithBlankDateAction"),

	// 查看商品
	GET_ITEM("getItemAction"),
	GET_ITEM_DYNAMIC("getItemDynamicAction"),
	GET_SIMPLE_ITEM("getSimpleItemAction"),

	ITEM_SERACH("itemSearchAction"),

	WRITER_PUBLISH_ITEM("writePublishItemAction"),

	READ_PUBLISH_ITEM("readPublishItemAction"),

	//查看商品  ,直接返回mop所需的格式
	GET_MOP_ITEM("getMopItem"),

	// 查看商品限购
	GET_ITEM_BUY_LIMIT("getItemBuyLimit"),

	//批量查询商品限购
	QUERY_ITEM_BUY_LIMIT("queryItemBuyLimit"),

	// 删除商品
	DELETE_ITEM("deleteItemAction"),

    //将商品加入回收站
    TRASH_ITEM("trashItem"),
    QUERY_COMPOSITE_SKU_ID("QueryCompositeBySkuIdAction"),

    //恢复商品
    RECOVERY_ITEM("recoveryItem"),

    //清空商品回收站
    EMPTY_ITEM_RECYCLE_BIN("emptyItemRecycleBin"),
    UPDATE_SEARCH_INDEX("updateSearchIndex"),

    //下架商品
	WITHDRAW_ITEM("withdrawItemAction"),
	
	//上架商品
	UP_ITEM("upItemAction"),
    DISABLE_ITEM("disableItemAction"),

	//批量下架商品
	BATCH_WITHDRAW_ITEM("batchWithdrawItemAction"),

	//批量上架商品
	BATCH_UP_ITEM("batchUpItemAction"),

	//批量删除商品
	BATCH_DELETE_ITEM("batchDeleteItemAction"),

	BATCH_TRASH_ITEM("batchTrashItem"),

	BATCH_RECOVERY_ITEM("batchRecoveryItem"),

	//上下架查询
	UP_DOWN_STATE("selectItemSaleStateAction"),

	//上架商品
	PRE_SALE_ITEM("preSaleItemAction"),

	// 商品列表查询
	QUERY_ITEM("queryItemAction"),

	//特殊商品列表查询（营销活动，具有生命周期的）
	QUERY_SPECIAL_ITEM("querySpecialItemAction"),

	COUNT_GROUP_ITEM("countGroupItemAction"),

	// 搜索商品
	SEARCH_ITEM("searchItemAction"),

	// 导出商品
	EXPORT_ITEM("exportItemAction"),

	// 增加商品扩展信息
	ADD_ITEM_EXTRA_INFO("addItemExtraInfoAction"),

	// 更新商品扩展信息
	UPDATE_ITEM_EXTRA_INFO("updateItemExtraInfoAction"),

	// 查看商品扩展信息
	GET_ITEM_EXTRA_INFO("getItemExtraInfoAction"),

	// 删除商品扩展信息
	DELETE_ITEM_EXTRA_INFO("deleteItemExtraInfoAction"),

	// 商品列表查询扩展信息
	QUERY_ITEM_EXTRA_INFO("queryItemExtraInfoAction"),

	// 将商品移入回收站
	REMOVE_ITEM("removeItemAction"),

	// 增加商品图片
	ADD_ITEM_IMAGE("addItemImageAction"),

	// 查看商品图片
	GET_ITEM_IMAGE("getItemImageAction"),

	// 根据商品ID删除商品图片
	DELETE_ITEM_IMAGE_BY_ITEMID("deleteItemImageByItemIdAction"),

	// 删除商品图片
	DELETE_ITEM_IMAGELIST("deleteItemImageListAction"),

	// 查询商品图片列表
	QUERY_ITEM_IMAGE("queryItemImageTmplAction"),

	// 增加商品品牌
	ADD_ITEMBRAND("addItemBrandAction"),

	// 更新商品品牌
	UPDATE_ITEMBRAND("updateItemBrandAction"),

	// 查看商品品牌
	GET_ITEMBRAND("getItemBrandAction"),

	// 删除商品品牌
	DELETE_ITEMBRAND("deleteItemBrandAction"),

	// 商品品牌列表查询
	QUERY_ITEMBRAND("queryItemBrandAction"),

	// 增加商品评论
	ADD_ITEMCOMMENT("addItemCommentAction"),

	UPDATE_ITEM_COMMENT("updateItemCommentAction"),

	// 删除商品评论
	DELETE_ITEMCOMMENT("deleteItemCommentAction"),

	// 获得商品评分等级
	QUERY_ITEMCOMMENTGRADE("queryItemCommentGradeAction"),

	// 分类统计商品评分等级
	COUNT_ITEMCOMMENTGRADE("countItemCommentGradeAction"),

	// 根据商品ID查询所有评论
	QUERY_ITEMCOMMENT("queryItemCommentAction"),

	// 增加商品属性模板
	ADD_ITEM_PROPERTY_TMPL("addItemPropertyTmplAction"),

	// 修改商品属性模板
	UPDATE_ITEM_PROPERTY_TMPL("updateItemPropertyTmplAction"),

	// 删除商品属性模板
	DELETE_ITEM_PROPERTY_TMPL("deleteItemPropertyTmplAction"),

	// 查看商品属性模板
	GET_ITEM_PROPERTY_TMPL("getItemPropertyTmplAction"),

	// 查询商品属性模板列表
	QUERY_ITEM_PROPERTY_TMPL("queryItemPropertyTmplAction"),

	// 增加商品属性
	ADD_ITEM_PROPERTY("addItemPropertyAction"),

	// 修改商品属性
	UPDATE_ITEM_PROPERTY("updateItemPropertyAction"),

	// 删除商品属性模板
	DELETE_ITEM_PROPERTY("deleteItemPropertyAction"),

    // count商品属性模板
    COUNT_ITEM_PROPERTY("countItemPropertyAction"),


	// 查看商品属性模板

    GET_ITEM_PROPERTY("getItemPropertyAction"),

	// 查询商品属性列表
	QUERY_ITEM_PROPERTY("queryItemPropertyAction"),

	// 增加商品类目
	ADD_ITEM_CATEGORY("addItemCategoryAction"),

	// 修改商品类目
	UPDATE_ITEM_CATEGORY("updateItemCategoryAction"),

	// 删除商品类目
	DELETE_ITEM_CATEGORY("deleteItemCategoryAction"),

	// 查看商品类目
	GET_ITEM_CATEGORY("getItemCategoryAction"),

	//根据itemId商品类目 hsq
	GET_ITEM_CATEGORY_BY_ITEM_ID("getItemCategoryByItemId"),

	// 查询商品类目
    QUERY_ITEM_CATEGORY_TMPL("queryItemCategoryTmplAction"),

    //查询商品类目模板
    QUERY_ITEM_CATEGORY("queryItemCategoryAction"),

	// 查询商品类目
	QUERY_ITEM_LEAF_CATEGORY("queryItemLeafCategoryAction"),
	
	//新增商品模版
	ADD_ITEM_TEMPLATE("addItemTemplateAction"),
	
	//查看商品模版
	GET_ITEM_TEMPLATE("getItemTemplateAction"),
	
	//符合条件查询商品模版
	QUERY_ITEM_TEMPLATE("queryItemTemplateAction"),
	
	//更新商品的模版
	UPDATE_ITEM_TEMPLATE("updateItemTemplateAction"),
	
	//删除商品模版
	DELETE_ITEM_TEMPLATE("deleteItemTemplateAction"),


    /****  查询商品优惠信息 ***/
    QUERY_ITEM_DISCOUNT_INFO("queryItemDiscountInfo"),
	
	/**
	 * ----商品相关--------------------------------------
	 */


    /**
     * ----类目相关--------------------------------------
     */
    QUERY_HIERARCHY_CATOGARY("queryHierarchyCatogaryAction"),

	/**
	 * ----专场相关--------------------------------------
	 */

	// 增加专场
	ADD_SALES_FIELD("addSalesFieldItemAction"),

	// 更新专场
	UPDATE_SALES_FIELD("updateSalesFieldAction"),

	// 查看专场
	GET_SALES_FIELD("getSalesFieldAction"),

	// 删除专场
	DELETE_SALES_FIELD("deleteSalesFieldAction"),

	// 专场列表查询
	QUERY_SALES_FIELD("querySalesFieldAction"),

	/**
	 * ----专场相关--------------------------------------
	 */

	/**
	 * ----SKU相关--------------------------------------
	 */

	// 增加SKU属性模板
	ADD_SKU_PROPERTY_TMPL("addSkuPropertyTmplAction"),

	// 修改SKU属性模板
	UPDATE_SKU_PROPERTY_TMPL("updateSkuPropertyTmplAction"),

	// 删除SKU属性模板
	DELETE_SKU_PROPERTY_TMPL("deleteSkuPropertyTmplAction"),

	// 查看SKU属性模板
	GET_SKU_PROPERTY_TMPL("getSkuPropertyTmplAction"),

	// 查询SKU模板列表
	QUERY_SKU_PROPERTY_TMPL("querySkuPropertyTmplAction"),

	// 增加SKU属性
	ADD_SKU_PROPERTY("addSkuPropertyAction"),

	// 修改SKU属性
	UPDATE_SKU_PROPERTY("updateSkuPropertyAction"),

	// 删除SKU属性
	DELETE_SKU_PROPERTY("deleteSkuPropertyAction"),

	// 查看SKU属性
	GET_SKU_PROPERTY("getSkuPropertyAction"),

	// 查询SKU
	QUERY_SKU_PROPERTY("querySkuPropertyAction"),

	// 增加商品销售属性(ItemSku)
	ADD_ITEM_SKU("addItemSkuAction"),

	// 修改商品销售属性(ItemSku)
	UPDATE_ITEM_SKU("updateItemSkuAction"),

	// 删除商品销售属性(ItemSku)
	DELETE_ITEM_SKU("deleteItemSkuAction"),

	// 查看商品销售属性(ItemSku)
	GET_ITEM_SKU("getItemSkuAction"),

	// 查询商品销售属性(ItemSku)
	QUERY_ITEM_SKU("queryItemSkuAction"),

	// 查询商品销售属性(ItemSku)
	QUERY_ITEM_SKU_DYNAMIC("queryItemSkuDynamicAction"),

	// 增加SKU库存
	INCREASE_ITEM_SKU_STOCK("increaseItemSkuStockAction"),

	// 减少SKU库存
	DECREASE_ITEM_SKU_STOCK("decreaseItemSkuStockAction"),

	//冻结商品库存
	FREEZE_ITEM_SKU_STOCK("freezeItemSkuStockAction"),

	//解冻商品库存
	THAW_ITEM_SKU_STOCK("thawItemSkuStockAction"),

	//将冻结的商品库存减掉
	CRUSH_ITEM_SKU_STOCK("crushItemSkuStockAction"),

	FREEZE_ORDER_SKU_STOCK("freezeOrderSkuStockAction"),

	THAW_ORDER_SKU_STOCK("thawOrderSkuStockAction"),
    THAW_ORDER_SKU_STOCK_PARTIALLY("thawOrderSkuStockPartiallyAction"),

	CRUSH_ORDER_SKU_STOCK("crushOrderSkuStockAction"),

	INCREASE_ORDER_SKU_STOCK("increaseOrderSkuStockAction"),

	INCREASE_ORDER_SKU_STOCK_PARTIALLY("increaseOrderSkuStockPartiallyAction"),

	//crush反操作
	RESUME_CRUSHED_ITEM_SKU_STOCK("resumeCrushItemSkuStockAction"),

    SET_ITEM_SKU_STOCK("setItemSkuStockAction"),

    SET_ITEM_SKU_STOCK_BY_BARCODE("setItemSkuStockByBarCodeAction"),

	/**
	 * ----SKU相关--------------------------------------
	 */

	/**
	 * ----SPU相关--------------------------------------
	 */

	// 增加SPU属性模板
	ADD_SPU_PROPERTY_TMPL("addSpuPropertyTmplAction"),

	// 修改SPU属性模板
	UPDATE_SPU_PROPERTY_TMPL("updateSpuPropertyTmplAction"),

	// 删除SPU属性模板
	DELETE_SPU_PROPERTY_TMPL("deleteSpuPropertyTmplAction"),

	// 查看SPU属性模板
	GET_SPU_PROPERTY_TMPL("getSpuPropertyTmplAction"),

	// 查询SPU模板列表
	QUERY_SPU_PROPERTY_TMPL("querySpuPropertyTmplAction"),

	// 增加SPU属性
	ADD_SPU_PROPERTY("addSpuPropertyAction"),

	// 修改SPU属性
	UPDATE_SPU_PROPERTY("updateSpuPropertyAction"),

	// 删除SPU属性
	DELETE_SPU_PROPERTY("deleteSpuPropertyAction"),

	// 查看SPU属性
	GET_SPU_PROPERTY("getSpuPropertyAction"),

	// 查询SPU属性
	QUERY_SPU_PROPERTY("querySpuPropertyAction"),

	// 增加SPU_Info信息
	ADD_SPU_INFO("addSpuInfoAction"),

	// 修改SPU_Info信息
	UPDATE_SPU_INFO("updateSpuInfoAction"),

	// 删除SPU_Info信息
	DELETE_SPU_INFO("deleteSpuInfoAction"),

	// 查看SPU_Info信息
	GET_SPU_INFO("getSpuInfoAction"),

	// 查询SPU_Info信息
	QUERY_SPU_INFO("querySpuInfoAction"),

	/**
	 * ----SPU相关--------------------------------------
	 */

    /**
     * ----商品套装--------------------------------------
     */

    QUERY_SUITS_BY_ITEM("querySuitsByItem"),

    QUERY_SUIT("querySuit"),

    ADD_R_ITEM_SUIT("addRItemSuit"),

    ADD_ITEM_SUIT("addItemSuit"),

	DISABLE_ITEM_SUIT("disableItemSuit"),

    QUERY_SUIT_DISCOUNT("queryItemSuitDiscount"),

    GET_SUIT_EXTRA_INFO("querySuitExtraInfo"),

    /**
     * ----商品套装--------------------------------------
     */
    /**
     * -----热搜词相关-------------------------------
     */
    ADD_HOTNAME("addHotName"),
    HOTNAME_LIST("hotNameList"),
    //修改序号（上移下移）
    MODIFILED_SORT_HOTNAME("modifiedSortHotName"),
    //删除热搜词
    DELETE_HOTNAME("deleteHotName"),
    //手机端点击量统计
    HOTNAME_CLICK_RATE("hotNameClickRate"),
    //电脑端点击量更新到后台
    CLICKRATEHOTNAME("clickRateHotName"),

    
    /**
     * ------热搜词相关-------------------------------
     */

    /**
     * ------计算商品价格-------------------------------
     */

    QUERY_ITEMS_PRICE("queryItemsPriceAction"),
    /**
     * ------计算商品价格--------------------------------
     */

	/**
	 * ----全局属性--------------------------------------
	 */
	// 增加全局属性
	ADD_GLOBAL_PROPERTY("addGlobalPropertyAction"),

	// 修改全局属性
	UPDATE_GLOBAL_PROPERTY("updateGlobalPropertyAction"),

	// 删除全局属性
	DELETE_GLOBAL_PROPERTY("deleteGlobalPropertyAction"),

	// 查看全局属性
	GET_GLOBAL_PROPERTY("getGlobalPropertyAction"),

	// 查询全局属性
	QUERY_GLOBAL_PROPERTY("queryGlobalPropertyAction"),

	// 增加全局属性值域
	ADD_GLOBAL_PROPERTY_VALUE("addGlobalPropertyValueAction"),

	// 修改全局属性值域
	UPDATE_GLOBAL_PROPERTY_VALUE("updateGlobalPropertyValueAction"),

	// 删除全局属性值域
	DELETE_GLOBAL_PROPERTY_VALUE("deleteGlobalPropertyValueAction"),

	// 查看全局属性值域
	GET_GLOBAL_PROPERTY_VALUE("getGlobalPropertyValueAction"),

	// 查询全局属性值域
	QUERY_GLOBAL_PROPERTY_VALUE("queryGlobalPropertyValueAction"),
	/**
	 * ----全局属性--------------------------------------
	 */

	//查询供应商品牌   某一个供应商关联的品牌
	QUERY_SELLER_BRAND("querySellerBrandAction"),

	//查询单个品牌
	GET_BRAND("getBrandAction"),

	//新增商品关联到某一个供应商下的品牌
	ADD_SELLER_BRAND("addSellerBrand"),

	//删除品牌
	DELETE_SELLER_BRAND("deleteSellerBrand"),

	//获取品牌信息
	GET_SELLER_BRAND("getSellerBrand"),

	//修改品牌信息
	UPDATE_SELLER_BRAND("updateSellerBrand"),

	/************************ 角标管理 *******************/
	//新增角标
	ADD_CORNER_ICON("addCornerIcon"),

	//删除角标
	DELETE_CORNER_ICON("deleteCornerIcon"),

	//查询角标
	QUERY_CORNER_ICON("queryCornerIcon"),

	//根据id获取角标
	GET_CORNER_ICON("getCornerIcon"),

	/************************ 角标管理 *******************/
	//添加收藏商品
	ADD_ITEM_COLLECTION("addItemCollection"),

	//删除收藏商品
	DELETE_ITEM_COLLECTION("deleteItemCollection"),

	// 创建商品详情模板;
	ADD_ITEM_DETAIL_TEMPLATE("addItemDetailTemplate"),

	// 获取模板;
	GET_ITEM_DETAIL_TEMPLATE("getItemDetailTemplate"),

	// 查询用户的模板;
	QUERY_ITEM_DETAIL_TEMPLATE("queryItemDetailTemplate"),

	// 更新商品详情模板;
	UPDATE_ITEM_DETAIL_TEMPLATE("updateItemDetailTemplate"),

	// 删除商品详情模板;
	DELETE_ITEM_DETAIL_TEMPLATE("deleteItemDetailTemplate"),

	//获取收藏列表
	QUERY_ITEM_COLLECTION("queryItemCollection"),

	// 添加商品进分组;
	ADD_ITEM_GROUP_ACTION("addItemInGroupAction"),

	// 查询分组中的商品;
	QUERY_ITEM_GROUP_ACTION("queryItemInGroupAction"),

	// 商品从分组中移走;
	REMOVE_ITEM_GROUP_ACTION("removeItemFromGroupAction"),

	// 删除分组时移到默认分组;
	REMOVE_ITEM_G_TO_DEFAULT_ROUP_ACTION("removeItemToDefaultGroup"),

	// 冻结商品;
	FREEZE_ITEM_ACTION("freezeItemAction"),

	// 解冻商品;
	THAW_ITEM_ACTION("thawItemAction"),

	COUNT_TOTAL_ITEM_ACTION("countTotalItemAction"),

	COUNT_TOTAL_SKU_ACTION("countTotalSkuAction"),

    /**运费模板**/


    ADD_FREIGHT_TEMPLATE_ACTION("addFreightTemplateAction"),

    DELETE_FREIGHT_TEMPLATE_ACTION("deleteFreightTemplateAction"),

    GET_FREIGHT_TEMPLATE_ACTION("getFreightTemplateAction"),

    QUERY_FREIGHT_TEMPLATE_ACTION("queryFreightTemplateAction"),

    UPDATE_FREIGHT_TEMPLATE_ACTION("updateFreightTemplateAction"),

    COPY_FREIGHT_TEMPLATE_ACTION("copyFreightTemplateAction"),

	CALCULATE_FREIGHT_ACTION("calculateFeightAction"),

	GET_STORE_SUPPORT_CONFIG("getStoreSupportConfig"),


	/*******************商品标签***************************/

    GET_ITEM_LABEL("getItemLabel"),

    QUERY_ITEM_LABEL("queryItemLabel"),

    ADD_ITEM_LABEL("addItemLabel"),

    UPDATE_ITEM_LABEL("updateItemLabel"),

    DELETE_ITEM_LABEL("deleteItemLabel"),

    /*******************商品参数****************/
    GET_SPEC("getSpec"),
    ADD_SPEC("addSpec"),
    QUERY_SPEC("querySpec"),
    UPDATE_SPEC("updateSpec"),
    DELETE_SPEC("deleteSpec"),

    /********************
     * 商品详情模板
     **************************/
    GET_ITEM_DESC_TMPL("getItemDescTmpl"),

    ADD_ITEM_DESC_TMPL("addItemDescTmpl"),

    UPDATE_ITEM_DESC_TMPL("updateItemDescTmpl"),

    DELETE_ITEM_DESC_TMPL("deleteItemDescTmpl"),

    QUERY_ITEM_DESC_TMPL("queryItemDescTmpl"),

    /******************增值服务***************************/

    GET_VALUE_ADDED_SERVICE("getValueAddedLabelService"),

    QUERY_VALUE_ADDED_SERVICE("queryValueAddedLabelService"),

    ADD_VALUE_ADDED_SERVICE("addValueAddedLabelService"),

    UPDATE_VALUE_ADDED_SERVICE("updateValueAddedLabelService"),

    DELETE_VALUE_ADDED_SERVICE("deleteValueAddedLabelService"),

    /******************支持特殊类型商品信息查询，比如拍卖秒杀团购***************************/
    GET_SPECIAL_ITEM_EXTRA_INFO("getSpecialItemExtraInfo"),


    /******单店铺、流量宝sku输入栏的推荐项****/

    ADD_ITEM_SKU_RECOMMENDATION("addItemSkuRecommendation"),

    ADD_ITEM_SKU_PROPERTY_RECOMMENDATION("addItemSkuPropertyRecommendation"),

    QUERY_ITEM_SKU_RECOMMENDATION("queryItemSkuRecommendation"),

    QUERY_ITEM_SKU_PROPERTY_RECOMMENDATION("queryItemSkuPropertyRecommendation"),

    DELETE_ITEM_SKU_RECOMMENDATION("deleteItemSkuRecommendation"),

    DELETE_ITEM_SKU_PROPERTY_RECOMMENDATION("deleteItemSkuPropertyRecommendation"),


    /**********商城佣金相关*************/
    CALCULATE_COMMISSION("calculateCommission"),


    /************商城结算配置**********************/


    ADD_ITEM_SETTLEMENT_CONFIG("addItemSettlementConfig"),

    GET_ITEM_SETTLEMENT_CONFIG("getItemSettlementConfig"),

    ENABLE_ITEM_SETTLEMENT_CONFIG("enableItemSettlementConfig"),

    DISABLE_ITEM_SETTLEMENT_CONFIG("disableItemSettlementConfig"),

    UPDATE_ITEM_SETTLEMENT_CONFIG("updateItemSettlementConfig"),

    DELETE_ITEM_SETTLEMENT_CONFIG("deleteItemSettlementConfig"),

    QUERY_ITEM_SETTLEMENT_CONFIG("queryItemSettlementConfig"),

    QUERY_ITEM_WITH_DEFAULT_SKU("queryItemWithDefaultSku"),

    /********************************************/


    /**********
     * 供应商支持
     **************/
    GET_SUPPLIER("getSupplier"),

    QUERY_SUPPLIER("querySupplier"),

    ADD_SUPPLIER("addSupplier"),

    UPDATE_SUPPLIER("updateSupplier"),

    DELETE_SUPPLIER("deleteSupplier"),


    /***************************************/

    /*** 带有UNSAFE后缀的接口表示同样前缀名的不需要BizCode版本  ***/
    QUERY_ITEM_UNSAFE("queryItemActionUnsafe"),

    QUERY_AREAS_ACTION("queryAreasAction"),


    /***********
     * 初始化类接口
     ***************/
    INIT_MALL_CATEGORY("initMallCategory"),

    /***********
     * 迁移类接口
     *****************/

    SINGLE_2_MULTI_SHOP_MIGRATE("single2MultiShopMigrate"),
	
	
    
	
	/************************************************
	 * 搜索相关
	 ************************************************/
	ITEM_SEARCH_REINDEX_ONE("itemSearchReIndexOneCoreAction"),		//	重建单个商品索引
	ITEM_SEARCH_REINDEX_ALL("itemSearchReIndexAllCoreAction"),	    //	重建所有商品索引
	
	/**
	 * 商品销量统计
	 */
	ITEM_SALESCOUNT("itemSalesCount"),

	/**
	 * 其他平台接口包装转发
	 */
	GET_ITEM_GROUP("getItemGroup");

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

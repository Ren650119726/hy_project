package com.mockuai.itemcenter.common.constant;

public enum ResponseCode {
	
	SUCCESS(10000,"系统调用成功"),

    /**
     *参数异常
     */
    PARAM_E_INVALID(20001,"参数不正确"),
    PARAM_E_MISSING(20002,"缺少参数"),

    /**
     *业务异常
     */
    BASE_PARAM_E_RECORD_NOT_EXIST(30001,"请求的记录不存在"),
    BASE_STATE_E_NO_PERMISSION(30002,"没有权限"),
    BASE_STATE_E_ACTION_NO_EXIST(30003,"访问的接口不存在"),
    BASE_STATE_E_REQUEST_FORBIDDEN(30004, "请求被禁止"),
    BASE_STATE_E_USER_NO_EXIST(30005,"用户不存在"),
    BASE_STATE_E_STOCK_INSUFFICIENT(30006,"库存不足"),
    BASE_STATE_E_NOT_ALLOW_CATEGORY_DELETED(30007,"该类目下包含子类目，不允许直接删除"),
    BASE_STATE_E_ITEM_WITHDRAW_TIME(30008,"当前时间大于下架时间"),
    BASE_STATE_E_COMMENT_EXISTS(30009, "不能重复评价"),
    BASE_APP_NOT_EXIST(30010, "the specified app is not exist"),
    BASE_ITEM_CATEGORY_NOT_EXIST(30011, "类目不存在"),
    BASE_STATE_E_THERE_ARE_ITEM_IN_CATEGORY(30012, "该类目下包含商品，不允许直接删除"),
    BASE_STATE_E_SALE_BEGIN_TIME_IS_INVALID(30013, "商品上架时间未设置或无效，请检查"),
    BASE_STATE_E_FREIGHT_TEMPLATE_NO_EXIST(30014,"运费模板不存在"),
    BASE_STATE_E_BARCODE_NO_EXIST(30015, "条形码对应的sku不存在"),
    BASE_STATE_E_BARCODE_DUPLICATE(30016, "条形码重复"),
    BASE_STATE_E_NO_AVILABLE_REPO(30017, "没有可用的仓库"),
    BASE_STATE_E_ALL_REPO_STOCK_SHORT(30018, "所有仓库库存都不足"),
    BASE_STATE_E_BRAND_NAME_DUPLICATE(30019, "品牌名"),
    BASE_STATE_E_STOCK_SHORT(30020,"商品sku可用库存不足,冻结失败"),
    BASE_STATE_ITEM_NOT_EXIST(30021,"商品不存在或者已下架"),

    /**
	 *系统异常 
	 */
    SYS_E_DEFAULT_ERROR(40001,"系统开小差中，请稍后再试"),
    SYS_E_SERVICE_EXCEPTION(40002,"服务端异常"),
    SYS_E_SERVICE_UNAVAILABLE(40003, "服务端不可用"),
    SYS_E_SERVICE_TIMEOUT(40004,"服务端超时"),
    SYS_E_DB_DELETE(40005,"数据库删除异常"),
    SYS_E_DB_UPDATE(40006,"数据库更新异常"),
    SYS_E_CONFIG_CONFLICT_ACTION_NAME(40007,"存在冲突的ActionName"),
    SYS_E_DEPENDENT_SERVICE_EXCEPTION(40008,"底层依赖的服务异常");

    private int code;
    private String comment;
    private ResponseCode(int code, String comment){
		this.code = code;
        this.comment = comment;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

package com.hanshu.employee.common.constant;

/**
 * Created by yeliming on 16/5/19.
 */
public enum OpActionEnum {
    /**
     * 新增
     */
    ADD(1, "新增"),
    /**
     * 修改
     */
    UPDATE(2, "修改"),
    /**
     * 删除
     */
    DELETE(3, "删除"),
    /**
     * 冻结
     */
    FREZZ(4, "冻结"),
    /**
     * 解冻
     */
    THAW(5, "解冻"),
    /**
     * 登录
     */
    LOGIN(6, "登录"),
    /**
     * 登出
     */
    LOGOUT(7, "登出"),
    /**
     * 同意
     */
    AGREE(8, "同意"),
    /**
     * 拒绝
     */
    REJECT(9, "拒绝"),
    /**
     * 生效
     */
    ENABLE(10, "生效"),
    /**
     * 失效
     */
    DISABLE(11, "失效"),
    /**
     * 移入回收站
     */
    TRASH(20, "移入回收站"),
    /**
     * 恢复商品
     */
    RECOVERY(13, "恢复商品"),
    /**
     * 上架
     */
    UP(14, "上架"),
    /**
     * 下架
     */
    WITHDRAW(15, "下架"),
    /**
     * 推送订单
     */
    PUSH(16, "推送订单"),
    /**
     * 取消
     */
    CANCEL(17, "取消"),
    /**
     * 发货
     */
    DELIVERY(18, "发货"),
    /**
     * 回滚
     */
    ROLLBACK(19, "回滚"),
    /**
     * 预售
     */
    PRESALE(20, "预售"),
    /**
     * 更换
     */
    CHANGE(21, "更换"),
    /**
     * 批量删除
     */
    BATDELETE(22, "批量删除"),
    /**
     * 批量上架
     */
    BATUP(23, "批量上架"),
    /**
     * 批量下架
     */
    BATWITHDRAW(24, "批量下架"),
    /**
     * 批量移入回收站
     */
    BATTRASH(25, "批量移入回收站"),
    /**
     * 批量恢复
     */
    BATRECOVERY(26, "批量恢复"),
    /**
     * 打款
     */
    TRANSFER(27, "打款"),
    /**
     * 设置
     */
    SET(28,"设置"),
    /**
     * 发放
     */
    GRANT(29,"发放")
    ;

    private int code;
    private String msg;

    OpActionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsg(Integer code) {
        if (code != null) {
            for (OpActionEnum opActionEnum : OpActionEnum.values()) {
                if (code.equals(opActionEnum.getCode())) {
                    return opActionEnum.getMsg();
                }
            }
        }
        return null;
    }
}

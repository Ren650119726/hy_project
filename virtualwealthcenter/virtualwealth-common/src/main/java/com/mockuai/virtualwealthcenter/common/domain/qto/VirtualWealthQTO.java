package com.mockuai.virtualwealthcenter.common.domain.qto;

import java.util.List;

/**
 * Created by zengzhangqiang on 5/25/15.
 */
public class VirtualWealthQTO {
    private Long id;
    private Long userId;
    private String bizCode;
    private Long creatorId;
    /**
     * 虚拟财富类型
     */
    private Integer type;
    private List<Integer> typeIn;
    /**
     * 交易标记，0代表不可以在交易中使用，1代表可以在交易中使用
     */
    private Integer tradeMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<Integer> getTypeIn() {
        return typeIn;
    }

    public void setTypeIn(List<Integer> typeIn) {
        this.typeIn = typeIn;
    }

    public Integer getTradeMark() {
        return tradeMark;
    }

    public void setTradeMark(Integer tradeMark) {
        this.tradeMark = tradeMark;
    }
}

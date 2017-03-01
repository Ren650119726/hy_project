package com.mockuai.distributioncenter.common.domain.qto;

/**
 * Created by lizg on 2016/10/24.
 */
public class HkProtocolQTO extends BaseQTO{

    private Long userId;

    private Integer proModel;

    public Integer getProModel() {
        return proModel;
    }

    public void setProModel(Integer proModel) {
        this.proModel = proModel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

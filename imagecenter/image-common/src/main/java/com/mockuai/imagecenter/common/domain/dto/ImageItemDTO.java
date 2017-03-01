package com.mockuai.imagecenter.common.domain.dto; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import java.io.Serializable;

/**
 * Created by hy on 2016/10/24.
 */
public class ImageItemDTO implements Serializable {

    private Long itemId;
    private Long sellerId;
    private Boolean isSeckill;
    private Long shareUserId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Boolean getSeckill() {
        return isSeckill;
    }

    public void setSeckill(Boolean seckill) {
        isSeckill = seckill;
    }

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }
}

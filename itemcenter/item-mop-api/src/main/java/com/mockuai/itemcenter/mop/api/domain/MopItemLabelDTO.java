package com.mockuai.itemcenter.mop.api.domain;

/**
 * Created by yindingyu on 15/12/23.
 */
public class MopItemLabelDTO {

    private String labelUid;

    private String labelName;

    private String labelDesc;

    private String iconUrl;

    public String getLabelUid() {
        return labelUid;
    }

    public void setLabelUid(String labelUid) {
        this.labelUid = labelUid;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelDesc() {
        return labelDesc;
    }

    public void setLabelDesc(String labelDesc) {
        this.labelDesc = labelDesc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}

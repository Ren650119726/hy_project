package com.mockuai.tradecenter.common.domain; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

/**
 * Created by guansheng on 2016/7/19.
 * 标签
 */
public class BizMarkDTO extends   BaseDTO {

    private String remark;

    private String icon;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

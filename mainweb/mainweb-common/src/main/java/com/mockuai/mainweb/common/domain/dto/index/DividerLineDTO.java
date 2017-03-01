package com.mockuai.mainweb.common.domain.dto.index;

/**
 * Created by edgar.zr on 3/28/2016.
 */
public class DividerLineDTO {
    private String type;
    private Integer height;
    private String bgColor;
    private Integer topPadding;
    private Integer bottomPadding;
    private Integer leftPadding;
    private Integer rightPadding;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public Integer getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(Integer topPadding) {
        this.topPadding = topPadding;
    }

    public Integer getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(Integer bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public Integer getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(Integer leftPadding) {
        this.leftPadding = leftPadding;
    }

    public Integer getRightPadding() {
        return rightPadding;
    }

    public void setRightPadding(Integer rightPadding) {
        this.rightPadding = rightPadding;
    }
}
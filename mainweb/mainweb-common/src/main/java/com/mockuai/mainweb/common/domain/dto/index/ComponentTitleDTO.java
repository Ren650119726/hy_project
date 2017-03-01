package com.mockuai.mainweb.common.domain.dto.index;

/**
 * Created by edgar.zr on 3/28/2016.
 */
public class ComponentTitleDTO {
    private String title;
    private String borderColor;
    private String description;
    private String targetUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
package com.mockuai.distributioncenter.mop.api.domain;


/**
 * Created by lizg on 2016/10/24.
 */
public class MopHkProtocolDTO {

    private Long id;

    private String proName;

    private String proContent;

    private Integer proModel;

    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProContent() {
        return proContent;
    }

    public void setProContent(String proContent) {
        this.proContent = proContent;
    }

    public Integer getProModel() {
        return proModel;
    }

    public void setProModel(Integer proModel) {
        this.proModel = proModel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

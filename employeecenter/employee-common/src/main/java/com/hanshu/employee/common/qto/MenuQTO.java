package com.hanshu.employee.common.qto;

import java.util.List;

/**
 * Created by yeliming on 16/5/11.
 */
public class MenuQTO extends BaseQTO {
    private String version;
    private Long parentId;
    private List<Long> idList;
    private List<Long> parentIdList;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(List<Long> parentIdList) {
        this.parentIdList = parentIdList;
    }
}

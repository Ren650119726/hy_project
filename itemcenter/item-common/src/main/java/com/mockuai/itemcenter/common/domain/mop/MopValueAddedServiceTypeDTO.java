package com.mockuai.itemcenter.common.domain.mop;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yindingyu on 15/12/23.
 */
public class MopValueAddedServiceTypeDTO implements Serializable {

    private String serviceTypeUid;

    private String typeName;

    private List<MopValueAddedServiceDTO> serviceList;

    public String getServiceTypeUid() {
        return serviceTypeUid;
    }

    public void setServiceTypeUid(String serviceTypeUid) {
        this.serviceTypeUid = serviceTypeUid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<MopValueAddedServiceDTO> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<MopValueAddedServiceDTO> serviceList) {
        this.serviceList = serviceList;
    }
}

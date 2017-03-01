package com.hanshu.employee.common.qto;

import java.util.List;

/**
 * Created by yeliming on 16/5/12.
 */
public class UserRoleQTO extends BaseQTO {
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}

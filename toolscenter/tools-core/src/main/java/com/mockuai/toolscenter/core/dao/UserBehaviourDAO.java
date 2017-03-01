package com.mockuai.toolscenter.core.dao;

import com.mockuai.toolscenter.core.domain.UserBehaviourDO;

import java.sql.SQLException;

/**
 * Created by jiguansheng on 6/7/15.
 */
public interface UserBehaviourDAO {

    void addBeHaviour(UserBehaviourDO userBehaviourDO) throws SQLException;

}

package com.mockuai.toolscenter.core.dao.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.toolscenter.core.dao.UserBehaviourDAO;
import com.mockuai.toolscenter.core.domain.UserBehaviourDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.sql.SQLException;

/**
 * Created by guansheng on 2016/10/18.
 */
public class UserBehaviourDAOImpl  extends SqlMapClientDaoSupport implements UserBehaviourDAO {
    @Override
    public void addBeHaviour(UserBehaviourDO userBehaviourDO) throws SQLException {
        getSqlMapClientTemplate().insert("user_behaviour.insert",userBehaviourDO);
    }
}

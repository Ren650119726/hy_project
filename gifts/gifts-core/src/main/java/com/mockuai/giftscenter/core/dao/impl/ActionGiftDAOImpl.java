package com.mockuai.giftscenter.core.dao.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.core.dao.ActionGiftDAO;
import com.mockuai.giftscenter.core.domain.ActionGiftDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

/**
 * Created by guansheng on 2016/7/15.
 */
@Service
public class ActionGiftDAOImpl extends SqlMapClientDaoSupport implements ActionGiftDAO {

    @Override
    public Long insert(ActionGiftDO actionGiftDO) {
         return (Long) getSqlMapClientTemplate().insert("action_gift.insert", actionGiftDO);
    }

    @Override
    public ActionGiftDO getByActionType(int actionType) {
        return (ActionGiftDO) getSqlMapClientTemplate().queryForObject("action_gift.getByActionType",actionType);
    }

    @Override
    public void update(ActionGiftDO actionGiftDO) {
        getSqlMapClientTemplate().update("action_gift.update",actionGiftDO);
    }
}

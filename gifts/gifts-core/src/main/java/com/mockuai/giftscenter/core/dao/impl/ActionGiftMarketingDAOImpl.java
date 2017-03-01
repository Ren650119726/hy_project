package com.mockuai.giftscenter.core.dao.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.giftscenter.core.dao.ActionGiftMarketingDAO;
import com.mockuai.giftscenter.core.domain.ActionGiftMarketingDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guansheng on 2016/7/15.
 */
@Service
public class ActionGiftMarketingDAOImpl extends SqlMapClientDaoSupport implements ActionGiftMarketingDAO {


    @Override
    public void insert(List<ActionGiftMarketingDO> data) {
        getSqlMapClientTemplate().insert("action_gift_marketing.insert", data);
    }

    @Override
    public List<ActionGiftMarketingDO> queryByActionId(long actionId) {
        return getSqlMapClientTemplate().queryForList("action_gift_marketing.queryByActionId", actionId);
    }

    @Override
    public void deleteByActionId(long actionId) {
        getSqlMapClientTemplate().update("action_gift_marketing.deleteByActionId", actionId);

    }
}

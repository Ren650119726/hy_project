package com.mockuai.shopcenter.core.dao.impl;

import com.mockuai.shopcenter.core.dao.RItemGroupDAO;
import com.mockuai.shopcenter.core.domain.RItemGroupDO;
import com.mockuai.shopcenter.core.domain.RItemGroupDOExample;
import java.util.List;
import java.util.Map;

import com.mockuai.shopcenter.core.dao.RItemGroupDAO;
import com.mockuai.shopcenter.core.domain.RItemGroupDO;
import com.mockuai.shopcenter.domain.dto.RItemGroupDTO;
import com.mockuai.shopcenter.domain.dto.ShopItemGroupDTO;
import com.mockuai.shopcenter.domain.qto.RItemGroupQTO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class RItemGroupDAOImpl extends SqlMapClientDaoSupport implements RItemGroupDAO {


    @Override
    public Long addRItemGroup(RItemGroupDO rItemGroupDO) {
        return (Long) getSqlMapClientTemplate().insert("r_item_group.addRItemGroup",rItemGroupDO);
    }

    @Override
    public Long deleteRItemGroup(RItemGroupDO rItemGroupDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("r_item_group.deleteRItemGroup",rItemGroupDO));
    }

    @Override
    public Long getRItemGroup(RItemGroupDO rItemGroupDO) {
        return (Long) getSqlMapClientTemplate().queryForObject("r_item_group.getRItemGroup",rItemGroupDO);
    }

    @Override
    public List<RItemGroupDO> queryRItemGroupByGroupId(RItemGroupDO rItemGroupDO) {
        return getSqlMapClientTemplate().queryForList("r_item_group.queryRItemGroupByGroupId", rItemGroupDO);
    }

    @Override
    public Long deleteRItemGroupByGroupId(RItemGroupDO rItemGroupDO) {
        return Long.valueOf(getSqlMapClientTemplate().update("r_item_group.deleteRItemGroupByGroupId", rItemGroupDO));
    }

    @Override
    public Map<Long, ShopItemGroupDTO> queryGroupItemCount(List<Long> groupIdList, Long sellerId, String bizCode) {

        RItemGroupQTO rItemGroupQTO = new RItemGroupQTO();
        rItemGroupQTO.setGroupIdList(groupIdList);
        rItemGroupQTO.setSellerId(sellerId);
        rItemGroupQTO.setBizCode(bizCode);
        return (Map<Long, ShopItemGroupDTO>) getSqlMapClientTemplate().queryForMap("r_item_group.queryGroupItemCount", rItemGroupQTO, "id");
    }
}
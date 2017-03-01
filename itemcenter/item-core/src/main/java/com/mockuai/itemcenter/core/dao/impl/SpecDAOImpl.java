package com.mockuai.itemcenter.core.dao.impl; /**
 * create by 冠生
 *
 * @date #{DATE}
 **/

import com.mockuai.itemcenter.common.domain.qto.SpecQTO;
import com.mockuai.itemcenter.core.dao.SpecDAO;
import com.mockuai.itemcenter.core.domain.SpecDO;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

/**
 * Created by guansheng on 2016/8/30.
 */
public class SpecDAOImpl extends SqlMapClientDaoSupport implements SpecDAO {
    @Override
    public Long addSpec(SpecDO specDO) {
      return (Long) getSqlMapClientTemplate().insert("spec.addSpec",specDO);
    }

    @Override
    public List<SpecDO> querySpec(SpecQTO specQTO) {
        return getSqlMapClientTemplate().queryForList("spec.querySpec",specQTO);
    }

    @Override
    public Integer updateSpec(SpecDO specDO) {

        return getSqlMapClientTemplate().update("spec.updateSpec",specDO);
    }

    @Override
    public long countSpec(SpecQTO itemQTO) {
        Long totalCount = (Long) getSqlMapClientTemplate().queryForObject("spec.countSpec", itemQTO);
        return totalCount;
    }


    @Override
    public void deleteSpec(Long id) {
         getSqlMapClientTemplate().update("spec.deleteSpec",id);
    }
}

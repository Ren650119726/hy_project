package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.SpecQTO;
import com.mockuai.itemcenter.core.domain.SpecDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品参数
 */
@Service
public interface SpecDAO {


    Long addSpec(SpecDO specDO);

    List<SpecDO> querySpec(SpecQTO specQTO);

    Integer updateSpec(SpecDO specDO);

    long countSpec(SpecQTO itemQTO);


    void deleteSpec(Long id);
}
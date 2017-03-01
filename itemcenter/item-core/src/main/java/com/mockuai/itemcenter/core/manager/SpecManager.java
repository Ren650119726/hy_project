package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.SpecDTO;
import com.mockuai.itemcenter.common.domain.qto.SpecQTO;
import com.mockuai.itemcenter.core.exception.ItemException;

import java.util.List;

/**
 * Created by guansheng on 16/8/30.
 */
public interface SpecManager {


    Long addSpec(SpecDTO specDO) throws ItemException;

    List<SpecDTO> querySpec(SpecQTO specQTO) throws ItemException;

    Integer updateSpec(SpecDTO specDO) throws ItemException;

    void deleteSpec(Long id) throws ItemException;

    SpecDTO getSpec(Long id) throws ItemException;

    Long countSpec(SpecQTO specQTO) throws ItemException;
}

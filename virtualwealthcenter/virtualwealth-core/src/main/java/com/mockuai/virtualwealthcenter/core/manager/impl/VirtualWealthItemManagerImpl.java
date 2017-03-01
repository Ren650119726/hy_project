package com.mockuai.virtualwealthcenter.core.manager.impl;

import com.mockuai.virtualwealthcenter.common.domain.dto.VirtualWealthItemDTO;
import com.mockuai.virtualwealthcenter.common.domain.qto.VirtualWealthItemQTO;
import com.mockuai.virtualwealthcenter.core.dao.VirtualWealthItemDAO;
import com.mockuai.virtualwealthcenter.core.domain.VirtualWealthItemDO;
import com.mockuai.virtualwealthcenter.core.exception.VirtualWealthException;
import com.mockuai.virtualwealthcenter.core.manager.VirtualWealthItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by duke on 16/4/18.
 */
public class VirtualWealthItemManagerImpl implements VirtualWealthItemManager {

    private static final Logger log = LoggerFactory.getLogger(VirtualWealthItemManagerImpl.class);

    @Resource
    private VirtualWealthItemDAO virtualWealthItemDAO;

    @Override
    public Long addItem(VirtualWealthItemDTO virtualWealthItemDTO) throws VirtualWealthException {
        VirtualWealthItemDO virtualWealthItemDO = new VirtualWealthItemDO();
        BeanUtils.copyProperties(virtualWealthItemDTO, virtualWealthItemDO);
        Long id = virtualWealthItemDAO.add(virtualWealthItemDO);
        return id;
    }

    @Override
    public List<VirtualWealthItemDTO> queryItems(VirtualWealthItemQTO virtualWealthItemQTO) throws VirtualWealthException {
        List<VirtualWealthItemDTO> virtualWealthItemDTOs = new ArrayList<>();
        List<VirtualWealthItemDO> virtualWealthItemDOs = virtualWealthItemDAO.query(virtualWealthItemQTO);
        if (!virtualWealthItemDOs.isEmpty()) {
            for (VirtualWealthItemDO virtualWealthItemDO : virtualWealthItemDOs) {
                VirtualWealthItemDTO virtualWealthItemDTO = new VirtualWealthItemDTO();
                BeanUtils.copyProperties(virtualWealthItemDO, virtualWealthItemDTO);
                virtualWealthItemDTOs.add(virtualWealthItemDTO);
            }
        }
        return virtualWealthItemDTOs;
    }

    @Override
    public int deleteItem(Long id) throws VirtualWealthException {
        return virtualWealthItemDAO.delete(id);
    }

    @Override
    public Long totalCount(VirtualWealthItemQTO virtualWealthItemQTO) throws VirtualWealthException {
        return virtualWealthItemDAO.totalCount(virtualWealthItemQTO);
    }

    @Override
    public VirtualWealthItemDTO get(Long id) throws VirtualWealthException {
        VirtualWealthItemDO virtualWealthItemDO = virtualWealthItemDAO.get(id);
        if (virtualWealthItemDO != null) {
            VirtualWealthItemDTO virtualWealthItemDTO = new VirtualWealthItemDTO();
            BeanUtils.copyProperties(virtualWealthItemDO, virtualWealthItemDTO);
            return virtualWealthItemDTO;
        } else {
            return null;
        }
    }

    @Override
    public int update(VirtualWealthItemDTO virtualWealthItemDTO) throws VirtualWealthException {
        VirtualWealthItemDO virtualWealthItemDO = new VirtualWealthItemDO();
        BeanUtils.copyProperties(virtualWealthItemDTO, virtualWealthItemDO);
        return virtualWealthItemDAO.update(virtualWealthItemDO);
    }
}

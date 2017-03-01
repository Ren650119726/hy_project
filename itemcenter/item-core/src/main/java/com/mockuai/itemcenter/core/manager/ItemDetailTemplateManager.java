package com.mockuai.itemcenter.core.manager;

import com.mockuai.itemcenter.common.domain.dto.ItemDetailTemplateDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luliang on 15/7/21.
 */
@Service
public interface ItemDetailTemplateManager {

    ItemDetailTemplateDTO getItemDetailTemplate(Long sellerId, Long id) throws ItemException;

    List<ItemDetailTemplateDTO> queryItemDetailTemplate(ItemDetailTemplateQTO itemDetailTemplateQTO) throws ItemException;

    Long addItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ItemException;

    Integer updateItemDetailTemplate(ItemDetailTemplateDTO itemDetailTemplateDTO) throws ItemException;

    Integer deleteItemDetailTemplate(Long sellerId, Long id) throws ItemException;
}

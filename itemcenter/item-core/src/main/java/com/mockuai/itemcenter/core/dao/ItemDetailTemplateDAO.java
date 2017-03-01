package com.mockuai.itemcenter.core.dao;

import com.mockuai.itemcenter.common.domain.qto.ItemDetailTemplateQTO;
import com.mockuai.itemcenter.core.domain.ItemDetailTemplateDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luliang on 15/7/21.
 */
@Service
public interface ItemDetailTemplateDAO {

    Long addItemDetailTemplate(ItemDetailTemplateDO itemDetailTemplateDO) throws ItemException;

    ItemDetailTemplateDO getItemDetailTemplate(Long id, Long sellerId) throws ItemException;

    List<ItemDetailTemplateDO> queryItemDetailTemplate(ItemDetailTemplateQTO ItemDetailTemplateQTO) throws ItemException;

    Integer updateItemDetailTemplate(ItemDetailTemplateDO itemDetailTemplateDO) throws ItemException;

    Integer deleteItemDetailTemplate(ItemDetailTemplateDO itemDetailTemplateDO) throws ItemException;
}

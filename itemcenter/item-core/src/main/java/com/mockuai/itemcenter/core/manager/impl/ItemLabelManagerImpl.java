package com.mockuai.itemcenter.core.manager.impl;

import com.google.common.collect.Lists;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemLabelDTO;
import com.mockuai.itemcenter.common.domain.dto.RItemLabelDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemLabelQTO;
import com.mockuai.itemcenter.common.domain.qto.RItemLabelQTO;
import com.mockuai.itemcenter.core.dao.ItemLabelDAO;
import com.mockuai.itemcenter.core.dao.RItemLabelDAO;
import com.mockuai.itemcenter.core.domain.ItemLabelDO;
import com.mockuai.itemcenter.core.domain.RItemLabelDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemLabelManager;
import com.mockuai.itemcenter.core.service.action.item.AddItemAction;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yindingyu on 15/12/4.
 */
@Service
public class ItemLabelManagerImpl implements ItemLabelManager {
    private static final Logger log = LoggerFactory.getLogger(AddItemAction.class);

    @Resource
    private ItemLabelDAO itemLabelDAO;

    @Resource
    private RItemLabelDAO rItemLabelDAO;

    @Override
    public List<ItemLabelDTO> queryItemLabelsByItem(ItemDTO itemDTO) throws ItemException {

         //updated by jiguansheng V1.2.2版本 仅仅显示关联的服务标签
        //ItemLabelQTO query1 = new ItemLabelQTO();
        //query1.setScope(1);
        //query1.setBizCode(itemDTO.getBizCode());
        //query1.setSellerId(itemDTO.getSellerId());
        List<ItemLabelDTO> scope1 = new ArrayList<ItemLabelDTO>();
        //List<ItemLabelDTO> scope1 = queryItemLabel(query1);

        RItemLabelQTO rItemLabelQTO = new RItemLabelQTO();

        rItemLabelQTO.setItemId(itemDTO.getId());
        rItemLabelQTO.setBizCode(itemDTO.getBizCode());
        //rItemLabelQTO.setSellerId(itemDTO.getSellerId());

        List<RItemLabelDO> rItemLabelDOList = rItemLabelDAO.queryRItemLabel(rItemLabelQTO);

        List<RItemLabelDTO> rItemLabelDTOList = Lists.newArrayListWithCapacity(rItemLabelDOList.size());

        if (rItemLabelDOList.size() > 0) {

            List<Long> idList = Lists.newArrayListWithCapacity(rItemLabelDOList.size());

            for (RItemLabelDO rItemLabelDO : rItemLabelDOList) {
                idList.add(rItemLabelDO.getLabelId());

                RItemLabelDTO rItemLabelDTO = new RItemLabelDTO();
                BeanUtils.copyProperties(rItemLabelDO, rItemLabelDTO);

                rItemLabelDTOList.add(rItemLabelDTO);
            }


            ItemLabelQTO query2 = new ItemLabelQTO();
            //query2.setScope(2);
            query2.setBizCode(itemDTO.getBizCode());
            //query2.setSellerId(itemDTO.getSellerId());
            query2.setIdList(idList);

            List<ItemLabelDTO> scope2 = queryItemLabel(query2);


            scope1.addAll(scope2);
        }

        return scope1;

    }
    @Override
    public Long countRItemLabel(RItemLabelQTO rItemLabelQTO){
        return rItemLabelDAO.countRItemLabel(rItemLabelQTO);
    }





    public Long addRItemLabelList(ItemDTO itemDTO) throws  ItemException{
          try {
              //删除对应的标签
              rItemLabelDAO.deleteByItem(itemDTO.getId());
              Long itemId = itemDTO.getId();
              Long sellerId = itemDTO.getSellerId();
              //label集合是个空集合则不管
              if(CollectionUtils.isEmpty(itemDTO.getItemLabelDTOList())){
                  return null;
              }
              List<ItemLabelDTO> data =   itemDTO.getItemLabelDTOList();
              List<RItemLabelDO> rItemLabelList = new ArrayList<RItemLabelDO>(data.size());
              for(ItemLabelDTO itemLabel : data){
                  RItemLabelDO rItemLabel = new RItemLabelDO();
                  rItemLabel.setItemId(itemId);
                  rItemLabel.setBizCode(itemDTO.getBizCode());
                  rItemLabel.setSellerId(sellerId);
                  rItemLabel.setLabelId(itemLabel.getId());
                  rItemLabelList.add(rItemLabel);
              }
              return  rItemLabelDAO.addRList(rItemLabelList);
          }catch (Exception e){
              log.error("error to addRList", e);
              throw new ItemException(ResponseCode.SYS_E_SERVICE_EXCEPTION, "error to addRList");
          }

    }

    @Override
    public Long addItemLabel(ItemLabelDTO itemLabelDTO) throws ItemException {

        ItemLabelDO itemLabelDO = new ItemLabelDO();
        BeanUtils.copyProperties(itemLabelDTO, itemLabelDO);
        Long result = itemLabelDAO.addItemLabel(itemLabelDO);

        if (itemLabelDTO.getScope().intValue() == 2) {

            List<Long> idList = itemLabelDTO.getItemIdList();

            for (Long itemId : idList) {

                RItemLabelDO rItemLabelDO = new RItemLabelDO();
                rItemLabelDO.setLabelId(result);
                rItemLabelDO.setItemId(itemId);
                rItemLabelDO.setBizCode(itemLabelDTO.getBizCode());
                rItemLabelDO.setSellerId(itemLabelDTO.getSellerId());

                rItemLabelDAO.addRItemLabel(rItemLabelDO);
            }

        }

        return result;
    }

    @Override
    public Long updateItemLabel(ItemLabelDTO itemLabelDTO) throws ItemException {
        ItemLabelDO itemLabelDO = new ItemLabelDO();
        BeanUtils.copyProperties(itemLabelDTO, itemLabelDO);
        Long result = itemLabelDAO.updateItemLabel(itemLabelDO);


        rItemLabelDAO.deleteByLabel(itemLabelDO);

        if (itemLabelDTO.getScope().intValue() == 2) {


            List<Long> idList = itemLabelDTO.getItemIdList();

            for (Long itemId : idList) {

                RItemLabelDO rItemLabelDO = new RItemLabelDO();
                rItemLabelDO.setItemId(itemId);
                rItemLabelDO.setBizCode(itemLabelDTO.getBizCode());
                rItemLabelDO.setSellerId(itemLabelDTO.getSellerId());
                rItemLabelDO.setLabelId(itemLabelDTO.getId());

                rItemLabelDAO.addRItemLabel(rItemLabelDO);
            }

        }

        return result;
    }

    @Override
    public ItemLabelDTO getItemLabel(Long itemLabelId, Long sellerId, String bizCode) throws ItemException {

        ItemLabelDO query = new ItemLabelDO();
        query.setId(itemLabelId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        ItemLabelDO itemLabelDO = itemLabelDAO.getItemLabel(query);

        ItemLabelDTO itemLabelDTO = new ItemLabelDTO();

        BeanUtils.copyProperties(itemLabelDO, itemLabelDTO);

        if (itemLabelDO != null && itemLabelDO.getScope().intValue() == 2) {

            RItemLabelQTO rItemLabelQTO = new RItemLabelQTO();

            rItemLabelQTO.setLabelId(itemLabelId);
            rItemLabelQTO.setSellerId(sellerId);
            rItemLabelQTO.setBizCode(bizCode);

            List<RItemLabelDO> rItemLabelDOList = rItemLabelDAO.queryRItemLabel(rItemLabelQTO);

            List<Long> idList = Lists.newArrayListWithCapacity(rItemLabelDOList.size());
            for (RItemLabelDO rItemLabelDO : rItemLabelDOList) {
                idList.add(rItemLabelDO.getItemId());
            }

            itemLabelDTO.setItemIdList(idList);
        }

        return itemLabelDTO;
    }

    @Override
    public Long deleteItemLabel(Long itemLabelId, Long sellerId, String bizCode) throws ItemException {
        ItemLabelDO query = new ItemLabelDO();
        query.setId(itemLabelId);
        query.setSellerId(sellerId);
        query.setBizCode(bizCode);
        Long result = itemLabelDAO.deleteItemLabel(query);

        return result;
    }

    @Override
    public List<ItemLabelDTO> queryItemLabel(ItemLabelQTO itemLabelQTO) throws ItemException {

        List<ItemLabelDO> itemLabelDOList = itemLabelDAO.queryItemLabel(itemLabelQTO);

        List<ItemLabelDTO> itemLabelDTOList = Lists.newArrayListWithCapacity(itemLabelDOList.size());

        for (ItemLabelDO itemLabelDO : itemLabelDOList) {

            ItemLabelDTO itemLabelDTO = new ItemLabelDTO();

            BeanUtils.copyProperties(itemLabelDO, itemLabelDTO);

            itemLabelDTOList.add(itemLabelDTO);
        }

        return itemLabelDTOList;
    }
}

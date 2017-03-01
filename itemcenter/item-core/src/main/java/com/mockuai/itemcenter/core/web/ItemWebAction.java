package com.mockuai.itemcenter.core.web;


import com.mockuai.itemcenter.common.api.Response;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemManager;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yindingyu on 15/9/25.
 */

@Controller("unsafeItemWebAction")
@RequestMapping("/item")
public class ItemWebAction{

    @Resource
    private ItemManager itemManager;


    @RequestMapping(value = "/queryItems.do")
    public Response<List<ItemDTO>> queryItems(HttpServletRequest request){

        if(null==request.getParameter("itemQTO")){
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING,"itemQTO为空");
        }


        ItemQTO itemQTO = JsonUtil.parseJson(request.getParameter("itemQTO"), ItemQTO.class);

         if(itemQTO.getIdList()==null||itemQTO.getIdList().size()==0){
            return ResponseUtil.getErrorResponse(ResponseCode.PARAM_E_MISSING,"itemIdList为空");
        }
//        System.out.println();

        try {
//            ItemQTO qto = new ItemQTO();
//            qto.setIdList(new ArrayList<Long>(){{add(101049L);}});

            List<ItemDTO> itemDTOList = itemManager.queryItemContainsUrl(itemQTO);

            return ResponseUtil.getSuccessResponse(itemDTOList);

        }catch (ItemException e){

            return ResponseUtil.getErrorResponse(e.getResponseCode(), e.getMessage());
        }


    }


}

package com.mockuai.itemcenter.core.pc;

import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.ItemCategoryDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCategoryQTO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.ItemCategoryManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yindingyu on 15/11/23.
 */
@Controller
@RequestMapping("item/category")
public class CategoryWebAction extends  BaseWebAction {

    @Resource
    private ItemCategoryManager itemCategoryManager;



    @RequestMapping("/query.do")
    @ResponseBody
    public String queryCategory(HttpServletRequest request){


        try {

            String appKey = getAppkey(request);

            ItemCategoryQTO itemCategoryQTO = getParameter(request, "itemCategoryQTO", ItemCategoryQTO.class);


            List<ItemCategoryDTO> ItemCategoryDTOList = itemCategoryManager.queryItemCategory(itemCategoryQTO);


            ItemResponse<List<ItemCategoryDTO>> response = ResponseUtil.getSuccessResponse(ItemCategoryDTOList, itemCategoryQTO.getTotalCount());
            return JsonUtil.toJson(response);

        } catch (ItemException e) {

            ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
            return JsonUtil.toJson(response);

        }
    }

}

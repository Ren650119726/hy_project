package com.mockuai.itemcenter.core.pc;

import com.google.common.base.Strings;
import com.mockuai.itemcenter.common.api.ItemResponse;
import com.mockuai.itemcenter.common.constant.ResponseCode;
import com.mockuai.itemcenter.common.domain.dto.CountCommentDTO;
import com.mockuai.itemcenter.common.domain.dto.ItemCommentDTO;
import com.mockuai.itemcenter.common.domain.qto.ItemCommentQTO;
import com.mockuai.itemcenter.core.domain.CommentImageDO;
import com.mockuai.itemcenter.core.domain.ItemCommentDO;
import com.mockuai.itemcenter.core.exception.ItemException;
import com.mockuai.itemcenter.core.manager.CommentImageManager;
import com.mockuai.itemcenter.core.manager.ItemCommentManager;
import com.mockuai.itemcenter.core.manager.OrderManager;
import com.mockuai.itemcenter.core.service.ItemRequest;
import com.mockuai.itemcenter.core.service.RequestContext;
import com.mockuai.itemcenter.core.util.JsonUtil;
import com.mockuai.itemcenter.core.util.ModelUtil;
import com.mockuai.itemcenter.core.util.ResponseUtil;
import com.mockuai.tradecenter.client.OrderClient;
import com.mockuai.tradecenter.common.domain.OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by yindingyu on 15/11/23.
 */
@Controller
@RequestMapping("/item/comment")
public class CommentWebAction extends BaseWebAction{

    @Resource
    private ItemCommentManager itemCommentManager;

    @Resource
    private CommentImageManager commentImageManager;

    @Resource
    private OrderManager orderManager;


    @RequestMapping("query.do")
    @ResponseBody
    public String queryComment(HttpServletRequest request){

        try {

            String appKey = getAppkey(request);

            ItemCommentQTO itemCommentQTO = getParameter(request, "itemCommentQTO", ItemCommentQTO.class);
            itemCommentQTO.setNeedPaging(true);

            try {
                List<ItemCommentDO> itemCommentDOList = itemCommentManager.queryItemComment(itemCommentQTO);
                List<ItemCommentDTO> itemCommentDTOList = new CopyOnWriteArrayList<ItemCommentDTO>();
                List<Long> itemCommentIdList = new CopyOnWriteArrayList<Long>();
                for (ItemCommentDO itemCommentDO : itemCommentDOList) {
                    ItemCommentDTO itemCommentDTO = new ItemCommentDTO();
                    BeanUtils.copyProperties(itemCommentDO, itemCommentDTO);


                    OrderDTO orderDTO = orderManager.getOrderDTO(itemCommentDO.getOrderId(), itemCommentDO.getUserId(), appKey);

                    if(orderDTO!=null){
                        itemCommentDTO.setOrderTime(orderDTO.getOrderTime());
                    }
                    if(!Strings.isNullOrEmpty(itemCommentDTO.getUserName())){

                        String userName = itemCommentDTO.getUserName();

                        if(userName.length()>=11){
                            String prefix = userName.substring(0,3);
                            String suffix = userName.substring(7,userName.length());
                            String safeName = prefix + "****" +suffix;
                            itemCommentDTO.setUserName(safeName);
                        }
                    }

                    itemCommentDTOList.add(itemCommentDTO);
                    itemCommentIdList.add(itemCommentDO.getId());
                }

                //查询commentImage并填充到itemComment中
                if (itemCommentDOList != null && itemCommentDOList.isEmpty() == false) {
                    Map<Long, List<CommentImageDO>> commentImageMap =
                            commentImageManager.queryCommentImage(itemCommentIdList, itemCommentDOList.get(0).getSellerId());

                    //填充commentImage列表
                    if (commentImageMap != null && commentImageMap.isEmpty() == false) {
                        for (ItemCommentDTO itemCommentDTO : itemCommentDTOList) {
                            if (commentImageMap.containsKey(itemCommentDTO.getId())) {
                                itemCommentDTO.setCommentImageDTOs(
                                        ModelUtil.genCommentImageDTOList(commentImageMap.get(itemCommentDTO.getId())));
                            }
                        }
                    }
                }

                Map<String,Object> result = new HashMap<String, Object>();


                CountCommentDTO countCommentDTO = itemCommentManager.countItemCommentGrade(itemCommentQTO);

                result.put("code",10000);
                result.put("module",itemCommentDTOList);
                result.put("total_count",itemCommentQTO.getTotalCount());
                result.put("good",countCommentDTO.getGood());
                result.put("mid",countCommentDTO.getMid());
                result.put("bad",countCommentDTO.getBad());
                result.put("has_picture",countCommentDTO.getHasPicture());


                return JsonUtil.toJson(result);
            } catch (ItemException e) {
                ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(), e.getMessage());
                return JsonUtil.toJson(response);
            }


        }catch (ItemException e){
            ItemResponse response = ResponseUtil.getErrorResponse(e.getCode(),e.getMessage());
            return JsonUtil.toJson(response);
        }

    }

    public static void main(String[] args){

        String userName = "12345678900";

        if(userName.length()>=11){
            String prefix = userName.substring(0,3);
            String suffix = userName.substring(7,userName.length());
            String safeName = prefix + "****" +suffix;
        }
    }
}

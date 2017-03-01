package com.hanshu.mainweb.client.impl;

import com.hanshu.mainweb.client.MainWebClient;
import com.mockuai.mainweb.common.api.MainWebService;
import com.mockuai.mainweb.common.api.action.BaseRequest;
import com.mockuai.mainweb.common.api.action.Request;
import com.mockuai.mainweb.common.api.action.Response;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.PageSeckillDTO;
import com.mockuai.mainweb.common.domain.dto.PublishPageDTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */

public class MainWebClientImpl implements MainWebClient {

    @Resource
    MainWebService mainWebService;

    public Response<IndexPageDTO> addPage(IndexPageDTO indexPageDTO, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_PAGE.getActionName());
        request.setParam("indexPageDTO", indexPageDTO);
        request.setParam("appKey",appKey);
        Response<IndexPageDTO> response = mainWebService.execute(request);
        return response;
    }

    public Response<IndexPageDTO> getPage(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.GET_PAGE.getActionName());
        request.setParam("pageId",id);
        request.setParam("appKey",appKey);
        Response<IndexPageDTO> response = mainWebService.execute(request);
        return response;
    }

    public Response<Boolean> updatePage(IndexPageDTO indexPageDTO, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.UPDATE_PAGE.getActionName());
        request.setParam("indexPageDTO", indexPageDTO);
        request.setParam("appKey",appKey);
        Response<Boolean> response = mainWebService.execute(request);
        return response;
    }

    public Response<Boolean> deletePage(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.DELETE_PAGE.getActionName());
        request.setParam("id",id);
        request.setParam("appKey",appKey);
        Response<Boolean> response = mainWebService.execute(request);
        return response;
    }


    public Response<List<IndexPageDTO>>  queryPageNames(String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.QUERY_PAGE_NAMES.getActionName());
        request.setParam("appKey",appKey);
        Response<List<IndexPageDTO>> response = mainWebService.execute(request);
        return response;
    }

    public Response<PublishPageDTO> addPublishPage(Long pageId, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_PUBLISH_PAGE.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageId",pageId);
        Response<PublishPageDTO> response = mainWebService.execute(request);
        return response;
    }

    public Response<Void> deleteThenPublish(Long pageId, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.DELETE_THEN_PUBLISH.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageId",pageId);
        Response<Void> response = mainWebService.execute(request);
        return response;
    }

    public Response<Void> addSeckillThenPublish(List<PageSeckillDTO> pageSeckillDTOs,Boolean isMainPage, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.ADD_SECKILL_THEN_PUBLISH.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("isMainPage",isMainPage);
        request.setParam("pageSeckillDTOs",pageSeckillDTOs);
        Response<Void> response = mainWebService.execute(request);
        return response;
    }

    public Response<Void> deleteSeckillThenPublish(PageSeckillDTO pageSeckillDTO,Boolean isMainPage ,String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.DELETE_SECKILL_THEN_PUBLISH.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("isMainPage",isMainPage);
        request.setParam("pageSeckillDTO",pageSeckillDTO);
        Response<Void> response = mainWebService.execute(request);
        return response;
    }

    public Response<List<IndexPageDTO>> getPageList(PageQTO pageQTO ,String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.SHOW_PAGE_LIST.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageQTO",pageQTO);
        Response<List<IndexPageDTO>> response = mainWebService.execute(request);
        return response;
    }

    public Response<Void> cancelPage(Long id,String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.CANCEL_PAGE.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("id",id);
        Response<Void> response = mainWebService.execute(request);
        return response;
    }

    public Response<PublishPageDTO> PreviewPage(Long id, String appKey) {
        Request request = new BaseRequest();
        request.setCommand(ActionEnum.PREVIEW_PAGE.getActionName());
        request.setParam("appKey",appKey);
        request.setParam("pageId",id);
        Response<PublishPageDTO> response = mainWebService.execute(request);
        return response;
    }
}

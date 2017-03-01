package com.mockuai.mainweb.core.service.action.page;

import com.mockuai.mainweb.common.api.action.MainWebResponse;
import com.mockuai.mainweb.common.constant.ActionEnum;
import com.mockuai.mainweb.common.domain.dto.IndexPageDTO;
import com.mockuai.mainweb.common.domain.dto.IndexPageQTO;
import com.mockuai.mainweb.common.domain.qto.PageQTO;
import com.mockuai.mainweb.core.exception.MainWebException;
import com.mockuai.mainweb.core.manager.PageManager;
import com.mockuai.mainweb.core.service.action.Action;
import com.mockuai.mainweb.core.service.action.RequestContext;
import com.mockuai.mainweb.core.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangsiqian on 2016/11/23.
 */
@Service
public class ShowPageListAction implements Action {
    @Resource
    private PageManager pageManager;
    @Override
    public MainWebResponse execute(RequestContext context) throws MainWebException {
        PageQTO pageQTO = (PageQTO)context.getRequest().getParam("pageQTO");
        Integer offset = pageQTO.getOffset();
        if (offset == null) {
            offset = 1;
        }
        Integer count = pageQTO.getCount();
        if (count == null || count > 20) {
            count = 20;
        }


        pageQTO.setOffset((offset-1)*count);
        pageQTO.setCount(count);
        List<IndexPageDTO>  list = new ArrayList<>();
        list = pageManager.showPageList(pageQTO);
        Integer totalCount = pageQTO.getTotalCount();
        return ResponseUtil.getSuccessResponse(list,totalCount);
    }

    @Override
    public String getName() {
        return ActionEnum.SHOW_PAGE_LIST.getActionName();
    }
}

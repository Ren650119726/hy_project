package com.mockuai.appcenter.web.action;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zengzhangqiang on 9/1/15.
 */
@Controller
public class StaticPageAction {
    @Resource
    private AppClient appClient;
    @Resource
    private BizInfoManager bizInfoManager;

    /**
     *
     * @throws Exception
     */
    @RequestMapping("/static/{page}.html")
    public ModelAndView queryBizInfo(HttpServletRequest request,
                                     HttpServletResponse response, @PathVariable("page") String page) throws Exception {
        BizInfoQTO bizInfoQTO = new BizInfoQTO();
        List<BizInfoDO> bizInfoList = bizInfoManager.queryBizInfo(bizInfoQTO);
        request.setAttribute("bizInfoList", bizInfoList);
        return new ModelAndView("screen/static/"+page);
    }
}

package com.mockuai.appcenter.web.action;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.domain.AppInfoDTO;
import com.mockuai.appcenter.common.domain.AppInfoQTO;
import com.mockuai.appcenter.common.domain.BizInfoQTO;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by zengzhangqiang on 9/1/15.
 */
@Controller
public class AppInfoAction {
    @Resource
    private AppClient appClient;
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private AppPropertyManager appPropertyManager;

    /**
     * 查询应用信息
     * @throws Exception
     */
    @RequestMapping(value = "/app/manager.html", produces = "application/json; charset=utf-8")
     public ModelAndView appInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        AppInfoQTO appInfoQTO = new AppInfoQTO();
        appInfoQTO.setOffset(0);
        appInfoQTO.setCount(100);
        List<AppInfoDO> appInfoList = appInfoManager.queryAppInfo(appInfoQTO);
        request.setAttribute("appInfoList", appInfoList);
        return new ModelAndView("screen/app_manager");
    }

    @RequestMapping(value = "/app/query.do", produces = "application/json; charset=utf-8")
    public ModelAndView queryAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizCode = request.getParameter("biz_code");
        String appKey = request.getParameter("app_key");
        String mobile = request.getParameter("mobile");

        //参数校验
        AppInfoQTO appInfoQTO = new AppInfoQTO();
        if(StringUtils.isNotBlank(bizCode)){
            appInfoQTO.setBizCode(bizCode);
        }

        if(StringUtils.isNotBlank(appKey)){
            appInfoQTO.setAppKey(appKey);
        }

        if(StringUtils.isNotBlank(mobile)){
            appInfoQTO.setMobile(mobile);
        }
        appInfoQTO.setOffset(0);
        appInfoQTO.setCount(100);
        List<AppInfoDO> appInfoList = appInfoManager.queryAppInfo(appInfoQTO);
        request.setAttribute("appInfoList", appInfoList);
        return new ModelAndView("screen/app_manager");
    }

    @RequestMapping(value = "/app/add.do", produces = "application/json; charset=utf-8")
    public ModelAndView addAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String appName = request.getParameter("app_name");
        String bizCode = request.getParameter("biz_code");
        String appTypeStr = request.getParameter("app_type");
        String appVersion = request.getParameter("app_version");
        String domainName = request.getParameter("domain_name");
        String admin = request.getParameter("admin");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String appDesc = request.getParameter("app_desc");

        //参数校验

        int appType = Integer.valueOf(appTypeStr);

        //新增appInfo
        AppInfoDTO appInfo = new AppInfoDTO();
        appInfo.setBizCode(bizCode);
        appInfo.setAppType(appType);
        appInfo.setAppName(appName);
        appInfo.setAppVersion(appVersion);
        appInfo.setDomainName(domainName);
        appInfo.setAdministrator(admin);
        appInfo.setMobile(mobile);
        appInfo.setEmail(email);
        appInfo.setAppDesc(appDesc);

        Response<AppInfoDTO> addResp = appClient.addAppInfo(appInfo);
        if(addResp.isSuccess() == false){
            request.setAttribute("errorMsg", addResp.getMessage());
            return new ModelAndView("/screen/error");
        }

        //查询appInfo列表
        AppInfoQTO appInfoQTO = new AppInfoQTO();
        List<AppInfoDO> appInfoList = appInfoManager.queryAppInfo(appInfoQTO);
        request.setAttribute("appInfoList", appInfoList);

        //跳转到app管理页面
        return new ModelAndView("redirect:/app/manager.html");
    }

    @RequestMapping(value = "/app/add.html", produces = "application/json; charset=utf-8")
    public ModelAndView addAppInfoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("screen/app_info");
    }

    @RequestMapping(value = "/app/get.html", produces = "application/json; charset=utf-8")
    public ModelAndView getAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String appKey = request.getParameter("app_key");
        if(StringUtils.isBlank(appKey)){
            //TODO error handle
        }
        Response<AppInfoDTO> appInfoResp = appClient.getAppInfo(appKey);

        if(appInfoResp.isSuccess() == false){
            //TODO error handle
        }

        request.setAttribute("appInfo", appInfoResp.getModule());
        return new ModelAndView("screen/app_info");
    }

    @RequestMapping(value = "/app/update.do", produces = "application/json; charset=utf-8")
    public ModelAndView updateAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String appId = request.getParameter("id");
        String appName = request.getParameter("app_name");
        String bizCode = request.getParameter("biz_code");
        String appKey = request.getParameter("app_key");
        String appTypeStr = request.getParameter("app_type");
        String appVersion = request.getParameter("app_version");
        String domainName = request.getParameter("domain_name");
        String admin = request.getParameter("admin");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String appDesc = request.getParameter("app_desc");

        //参数校验
        int appType = Integer.valueOf(appTypeStr);

        //更新appInfo
        AppInfoDTO appInfo = new AppInfoDTO();
        appInfo.setId(Long.valueOf(appId));
        appInfo.setAppKey(appKey);
        appInfo.setAppType(appType);
        appInfo.setAppName(appName);
        appInfo.setAppVersion(appVersion);
        appInfo.setDomainName(domainName);
        appInfo.setAdministrator(admin);
        appInfo.setMobile(mobile);
        appInfo.setEmail(email);
        appInfo.setAppDesc(appDesc);

        Response<Void> updateResp = appClient.updateAppInfo(appInfo);
        if(updateResp.isSuccess() == false){
            request.setAttribute("errorMsg", updateResp.getMessage());
            return new ModelAndView("/screen/error");
        }else{
            Response<AppInfoDTO> appInfoResp = appClient.getAppInfo(appKey);
            request.setAttribute("appInfo", appInfoResp.getModule());
            return new ModelAndView("redirect:/app/get.html?app_key="+appKey);
        }
    }

    @RequestMapping(value = "/app/delete.do", produces = "application/json; charset=utf-8")
    public ModelAndView deleteAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //参数提取
        String appKey = request.getParameter("app_key");

        if(StringUtils.isBlank(appKey)){
            //TODO error handle
        }


        //参数校验
        AppInfoDO appInfoDO = appInfoManager.getAppInfo(appKey);

        if(appInfoDO == null){
            //TODO error handle
        }

        //删除应用属性
        appPropertyManager.deleteAppPropertyByAppId(appInfoDO.getId());
        //删除应用信息
        appInfoManager.deleteAppInfo(appInfoDO.getId());

        //查询appInfo列表
        AppInfoQTO appInfoQTO = new AppInfoQTO();
        List<AppInfoDO> appInfoList = appInfoManager.queryAppInfo(appInfoQTO);
        request.setAttribute("appInfoList", appInfoList);

        //跳转到app管理页面
        return new ModelAndView("redirect:/app/manager.html");
    }
}

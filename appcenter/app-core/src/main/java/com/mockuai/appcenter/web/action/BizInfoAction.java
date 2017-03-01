package com.mockuai.appcenter.web.action;

import com.mockuai.appcenter.client.AppClient;
import com.mockuai.appcenter.common.api.AppService;
import com.mockuai.appcenter.common.api.Request;
import com.mockuai.appcenter.common.api.Response;
import com.mockuai.appcenter.common.constant.BizPropertyKey;
import com.mockuai.appcenter.common.constant.BizTypeEnum;
import com.mockuai.appcenter.common.domain.*;
import com.mockuai.appcenter.core.api.impl.AppServiceImpl;
import com.mockuai.appcenter.core.domain.AppInfoDO;
import com.mockuai.appcenter.core.domain.BizInfoDO;
import com.mockuai.appcenter.core.domain.BizPropertyDO;
import com.mockuai.appcenter.core.manager.AppInfoManager;
import com.mockuai.appcenter.core.manager.AppPropertyManager;
import com.mockuai.appcenter.core.manager.BizInfoManager;
import com.mockuai.appcenter.core.manager.BizPropertyManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengzhangqiang on 9/1/15.
 */
@Controller
public class BizInfoAction {
    @Resource
    private AppClient appClient;
    @Resource
    private BizInfoManager bizInfoManager;
    @Resource
    private BizPropertyManager bizPropertyManager;
    @Resource
    private AppInfoManager appInfoManager;
    @Resource
    private AppPropertyManager appPropertyManager;
//    @Resource
//    private UserClient userClient;

    /**
     *
     * @throws Exception
     */
    @RequestMapping(value = "/biz/manager.html", produces = "application/json; charset=utf-8")
    public ModelAndView bizInfoList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BizInfoQTO bizInfoQTO = new BizInfoQTO();
        List<BizInfoDO> bizInfoList = bizInfoManager.queryBizInfo(bizInfoQTO);
        request.setAttribute("bizInfoList", bizInfoList);
        return new ModelAndView("screen/biz_manager");
    }

    /**
     *
     * @throws Exception
     */
    @RequestMapping(value = "/biz/query.do", produces = "application/json; charset=utf-8")
    public ModelAndView queryBizInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizCode = request.getParameter("biz_code");
        String mobile = request.getParameter("mobile");

        BizInfoQTO bizInfoQTO = new BizInfoQTO();
        if(StringUtils.isNotBlank(bizCode)){
            bizInfoQTO.setBizCode(bizCode);
        }

        if(StringUtils.isNotBlank(mobile)){
            bizInfoQTO.setMobile(mobile);
        }
        bizInfoQTO.setOffset(0);
        bizInfoQTO.setCount(100);
        List<BizInfoDO> bizInfoList = bizInfoManager.queryBizInfo(bizInfoQTO);
        request.setAttribute("bizInfoList", bizInfoList);
        return new ModelAndView("screen/biz_manager");
    }

    @RequestMapping(value = "/biz/add.html", produces = "application/json; charset=utf-8")
    public ModelAndView addAppInfoPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("screen/biz_info");
    }

    @RequestMapping(value = "/biz/add.do", produces = "application/json; charset=utf-8")
    public ModelAndView addAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //参数提取
        //企业基本信息
        String bizCode = request.getParameter("biz_code");
        String bizName = request.getParameter("biz_name");
        String admin = request.getParameter("admin");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String bizDesc = request.getParameter("biz_desc");

        BizInfoDTO bizInfoDTO = new BizInfoDTO();
        bizInfoDTO.setBizType(BizTypeEnum.BIZ_ENTERPRISE.getValue());
        bizInfoDTO.setBizCode(bizCode);
        bizInfoDTO.setBizName(bizName);
        bizInfoDTO.setAdministrator(admin);
        bizInfoDTO.setMobile(mobile);
        bizInfoDTO.setEmail(email);
        bizInfoDTO.setBizDesc(bizDesc);

        Map<String, BizPropertyDTO> bizPropertyMap = genBizPropertyMapFromReq(request, bizCode);
        bizInfoDTO.setBizPropertyMap(bizPropertyMap);


        Response<BizInfoDTO> addResp = appClient.addBizInfo(bizInfoDTO);
        if(addResp.isSuccess() == false){
            request.setAttribute("errorMsg", addResp.getMessage());
            return new ModelAndView("/screen/error");
        }


        //查询bizInfo列表
        BizInfoQTO bizInfoQTO = new BizInfoQTO();
        List<BizInfoDO> bizInfoList = bizInfoManager.queryBizInfo(bizInfoQTO);
        request.setAttribute("bizInfoList", bizInfoList);
        return new ModelAndView("redirect:/biz/manager.html");
    }

    @RequestMapping(value = "/biz/get.html", produces = "application/json; charset=utf-8")
    public ModelAndView getAppInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String bizCode = request.getParameter("biz_code");
        if(StringUtils.isBlank(bizCode)){
            //TODO error handle
        }
        Response<BizInfoDTO> bizInfoResp = appClient.getBizInfo(bizCode);
        if(bizInfoResp.isSuccess() == false){
            //TODO error handle
        }
        request.setAttribute("bizInfo", bizInfoResp.getModule());
        return new ModelAndView("screen/biz_info");
    }

    @RequestMapping(value = "/biz/update.do", produces = "application/json; charset=utf-8")
    public ModelAndView updateBizInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //参数提取
        //企业基本信息
        String bizIdStr = request.getParameter("biz_id");
        String bizCode = request.getParameter("biz_code");
        String bizName = request.getParameter("biz_name");
        String admin = request.getParameter("admin");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String bizDesc = request.getParameter("biz_desc");

        BizInfoDTO bizInfoDTO = new BizInfoDTO();
        bizInfoDTO.setId(Long.valueOf(bizIdStr));
        bizInfoDTO.setBizCode(bizCode);
        bizInfoDTO.setBizName(bizName);
        bizInfoDTO.setAdministrator(admin);
        bizInfoDTO.setMobile(mobile);
        bizInfoDTO.setEmail(email);
        bizInfoDTO.setBizDesc(bizDesc);

        Map<String, BizPropertyDTO> bizPropertyMap = genBizPropertyMapFromReq(request, bizCode);
        bizInfoDTO.setBizPropertyMap(bizPropertyMap);

        //更新bizInfo
        Response<Void> updateResp = appClient.updateBizInfo(bizInfoDTO);
        if(updateResp.isSuccess() == false){
            request.setAttribute("errorMsg", updateResp.getMessage());
            return new ModelAndView("/screen/error");
        }else{
            Response<BizInfoDTO> bizInfoResp = appClient.getBizInfo(bizCode);
            request.setAttribute("bizInfo", bizInfoResp.getModule());
            //重定向到该企业详情页面
            return new ModelAndView("redirect:/biz/get.html?biz_code="+bizCode);
        }
    }

//    @RequestMapping(value = "/biz/delete.do", produces = "application/json; charset=utf-8")
//    public ModelAndView deleteBizInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //参数提取
//        String bizCode = request.getParameter("biz_code");
//
//        if(StringUtils.isBlank(bizCode)){
//            //TODO error handle
//        }
//
//        //参数校验
//
//        //TODO 删除应用配置信息的事务保证
//
//        //删除应用配置信息
//        appPropertyManager.deleteAppPropertyByBizCode(bizCode);
//
//        //删除应用信息
//        appInfoManager.deleteAppInfoByBizCode(bizCode);
//
//        //删除企业配置信息
//        bizPropertyManager.deleteBizProperties(bizCode);
//
//        //删除企业信息
//        bizInfoManager.deleteBizInfo(bizCode);
//
//
//        //查询bizInfo列表
//        BizInfoQTO bizInfoQTO = new BizInfoQTO();
//        List<BizInfoDO> bizInfoList = bizInfoManager.queryBizInfo(bizInfoQTO);
//        request.setAttribute("bizInfoList", bizInfoList);
//        return new ModelAndView("screen/biz_manager");
//    }

    private Map<String, BizPropertyDTO> genBizPropertyMapFromReq(HttpServletRequest request,
                                                                 String bizCode) throws Exception{
        Map<String, BizPropertyDTO> bizPropertyMap = new HashMap<String, BizPropertyDTO>();

        //企业基本配置信息
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.BIZ_MESSAGE_TITLE, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.BIZ_MAIN_COLOR, bizCode);
        //是否支持使用魔筷支付账户
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.IS_PAY_BY_MOCKUAI, bizCode);
        //是否使用魔筷登录账户
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.IS_WECHAT_LOGIN_BY_MOCKUAI, bizCode);
        //是否启用搜索
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.IS_SEARCH_AVAILABLE, bizCode);
        //商城名称
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.SITE_NAME, bizCode);
        //商城关键字
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.SITE_KEYWORDS, bizCode);
        //无线端首页配置信息地址
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.HOMEPAGE_WIRELESS_DATA_URL, bizCode);


        //支付配置信息
        //支付宝支付配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.ALIPAY_PARTNER, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.ALIPAY_ACCOUNT, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.ALIPAY_MCH_PRIVATE_KEY, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.ALIPAY_PUBLIC_KEY, bizCode);
        //微信h5支付配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_H5_APP_ID, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_H5_APP_SECRET, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_H5_PARTNER_ID, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_H5_PARTNER_KEY, bizCode);
        //微信app支付配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_APP_APP_ID, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_APP_APP_SECRET, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_APP_PARTNER_ID, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_APP_PARTNER_KEY, bizCode);
        //银联支付配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.UNIONPAY_MCH_ID, bizCode);


        //登录配置信息
        //微信h5登录配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_LOGIN_H5_APP_ID, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_LOGIN_H5_APP_SECRET, bizCode);
        //微信app登录配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_LOGIN_APP_APP_ID, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_LOGIN_APP_APP_SECRET, bizCode);


        //微信分享配置信息
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.IS_WECHAT_SHARE_CONF_SUPPORT, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_SHARE_CONF_TITLE, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_SHARE_CONF_DESC, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.WECHAT_SHARE_CONF_LOGO, bizCode);


        //客服信息配置
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.CS_TEL, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.CS_ONLINE_URL, bizCode);
        fillBizPropertyMapFromReq(bizPropertyMap, request, BizPropertyKey.ABOUT_US, bizCode);

        return bizPropertyMap;
    }

    private void fillBizPropertyMapFromReq(Map<String, BizPropertyDTO> bizPropertyMap,
                                           HttpServletRequest request, String pKey, String bizCode) throws Exception{
        if(bizPropertyMap == null){
            return;
        }
        BizPropertyDTO bizPropertyDTO = genBizPropertyFromReq(request, pKey, bizCode);
        if(bizPropertyDTO != null) {
            bizPropertyMap.put(bizPropertyDTO.getpKey(), bizPropertyDTO);
        }
    }

    private BizPropertyDTO genBizPropertyFromReq(HttpServletRequest request, String pKey, String bizCode) throws Exception {
        if (StringUtils.isBlank(pKey)) {
            return null;
        }
        String value = request.getParameter(pKey);
        if(StringUtils.isBlank(value)){
            return null;
        }
        return genBizProperty(pKey, value, bizCode);
    }


    private BizPropertyDTO genBizProperty(String pKey, String pValue, String bizCode){
        BizPropertyDTO bizPropertyDTO = new BizPropertyDTO();
        bizPropertyDTO.setBizCode(bizCode);
        bizPropertyDTO.setpKey(pKey);
        bizPropertyDTO.setValue(pValue);
        bizPropertyDTO.setValueType(1);//TODO 待重构成枚举常量

        return bizPropertyDTO;
    }
}

package com.mockuai.appcenter.web.filter;

import com.mockuai.appcenter.core.constant.UserLevel;
import com.mockuai.appcenter.core.domain.UserDO;
import com.mockuai.appcenter.web.action.LoginAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录状态拦截器
 */
public class AuthFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    private Map<UserLevel, List<String>> authCommandMap;
    private List<String> ignoreUrlList;




    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.info("Enter auth filter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getRequestURL().toString();
        HttpSession session = req.getSession();

        if (session==null || session.getAttribute(LoginAction.USER_INFO)==null) {
            chain.doFilter(request, response);
            return;
        }

        UserDO userDO = (UserDO)session.getAttribute(LoginAction.USER_INFO);
        if (verifyAuth(userDO, url) == false) {
            response.setContentType("text/json; charset=utf-8");
            PrintWriter pw = resp.getWriter();
            pw.write("无操作权限！");
            if (pw != null) {
                pw.close();
            }
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        authCommandMap = new HashMap<UserLevel, List<String>>();
        List<String> devAuthList = new ArrayList<String>();
        devAuthList.add("/biz/manager.html");
        devAuthList.add("/biz/get.html");
        devAuthList.add("/biz/query.do");
        devAuthList.add("/app/manager.html");
        devAuthList.add("/app/get.html");
        devAuthList.add("/app/query.do");
        List<String> managerAuthList = new ArrayList<String>();
        managerAuthList.addAll(devAuthList);
        managerAuthList.add("/biz/add.html");
        managerAuthList.add("/biz/add.do");
        managerAuthList.add("/biz/update.do");
        managerAuthList.add("/app/add.html");
        managerAuthList.add("/app/add.do");
        managerAuthList.add("/app/update.do");
        List<String> adminAuthList = new ArrayList<String>();
        adminAuthList.addAll(managerAuthList);
//        adminAuthList.add("/biz/delete.do");
//        adminAuthList.add("/app/delete.do");

        authCommandMap.put(UserLevel.DEVELOPER, devAuthList);
        authCommandMap.put(UserLevel.MANAGER, managerAuthList);
        authCommandMap.put(UserLevel.ADMIN, adminAuthList);

        ignoreUrlList = new ArrayList<String>();
        ignoreUrlList.add("/login.html");
        ignoreUrlList.add("/login.do");
        ignoreUrlList.add("/logout.do");
    }

    /**
     * 不需要过滤的url
     *
     * @param url
     * @return
     */
    private boolean verifyAuth(UserDO userDO, String url) {
        for (String ignoreUrl : ignoreUrlList) {
            if (url.endsWith(ignoreUrl)) {
                return true;
            }
        }

        if(userDO == null){
            return false;
        }

        UserLevel userLevel = UserLevel.getUserLevel(userDO.getLevel());

        if(userLevel == null){
            return false;
        }

        List<String> authCommandList = authCommandMap.get(userLevel);
        for (String authCommand : authCommandList) {
            if (url.endsWith(authCommand)) {
                return true;
            }
        }

        return false;
    }

    public void destroy() {

    }
}
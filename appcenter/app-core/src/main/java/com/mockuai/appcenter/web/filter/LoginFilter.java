package com.mockuai.appcenter.web.filter;

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

/**
 * 登录状态拦截器
 */
public class LoginFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    /**
     * 部分的url不需要过滤 比如验证码的url
     */
    private static String[] IGNORE_URIS = {
            "/login.do",
            "/logout.do",
            "/login.html"
    };

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        logger.info("Enter login filter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String url = req.getRequestURL().toString();
        logger.info("Request URL = {}, check ignored = [{}] ", url, isIgnored(url));

        if (!isIgnored(url)) {
            HttpSession session = req.getSession();
            if (session==null || session.getAttribute(LoginAction.USER_INFO) == null) {
                resp.sendRedirect("/appcenter/login.html");
                return;
            }else{
                //如果是已登录状态，则往request中存放user信息，方便前端页面基于user信息做操作入口的控制
                request.setAttribute("userDO", session.getAttribute(LoginAction.USER_INFO));
            }
        }
        chain.doFilter(request, response);
        logger.info("Exit session filter");
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    /**
     * 不需要过滤的url
     *
     * @param url
     * @return
     */
    private boolean isIgnored(String url) {
        for (String ignoreUrl : IGNORE_URIS) {
            if (url.endsWith(ignoreUrl)) {
                return true;
            }
        }
        return false;
    }
}
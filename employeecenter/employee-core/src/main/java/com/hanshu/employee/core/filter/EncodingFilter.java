/**
 * @author jinfeng.hu 创建时间： 2010-6-20 下午08:20:41 文件名：EncodingFilter.java
 */
package com.hanshu.employee.core.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    protected String encoding = "utf-8";

    protected boolean ignore = true;

    protected boolean setContentType = true;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String paramValue = filterConfig.getInitParameter("encoding");

        if (paramValue != null) {
            this.encoding = paramValue;
        }

        String value = filterConfig.getInitParameter("ignore");

        if (value == null) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("true")) {
            this.ignore = true;
        } else if (value.equalsIgnoreCase("yes")) {
            this.ignore = true;
        } else {
            this.ignore = false;
        }

        String contentType = filterConfig.getInitParameter("response-head");
        if ("false".equals(contentType)) {
            setContentType = false;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);

            if (encoding != null) {
                request.setCharacterEncoding(encoding);
            }
        }
        if (setContentType) {
            // 设置响应头信息
            response.setContentType("text/html;charset=UTF-8");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }
}

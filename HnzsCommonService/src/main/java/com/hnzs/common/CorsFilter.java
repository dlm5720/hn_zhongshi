package com.hnzs.common;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域处理
 *
 * 前端发送请求时需要加上：xhrFields: {  withCredentials: true },
 *
 */
@Configuration
@WebFilter(filterName = "crosFilter", urlPatterns = "/*")
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //当业务网关启用跨域时，这里需要注释掉，避免二次设置跨域从而出现问题
        /*
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
        //servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //servletResponse.setHeader("Cache-Controller", "no-cache");
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        servletResponse.setHeader("Access-Control-Max-Age", "3600");
        servletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        */
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }


}

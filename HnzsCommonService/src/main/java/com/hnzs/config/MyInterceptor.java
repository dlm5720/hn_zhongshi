package com.hnzs.config;

import com.hnzs.exception.BaseException;
import com.hnzs.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class MyInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(com.hnzs.config.VerificationTokenConfig.class);

    @Autowired
    private StringRedisTemplate redisTemplate;//spring封装好的

    @Value("${token-expiration}")
    private int tokenExpiration;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //String token = request.getHeader("token");

        ///忽略所有非org.chen.controller的请求
        if (handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod) handler;
            if (h.getBean().getClass().getName().toString().contains("com.hnzs.controller")) {
                //String paramToken = request.getParameter("token");
                String paramToken = request.getParameter("token")==null?request.getHeader("token"):request.getParameter("token");
                System.out.println("param token:" + request.getParameter("token") + "\t");
                if(StringUtil.isNull(paramToken)){
                    response.setContentType("application/json;charset=utf-8");
                    String loginTimeOut="{"
                            + "\"data\":\"\","
                            + "\"total\":\"0\","
                            + "\"code\":\"99\","
                            + "\"msg\":\"没有有效的登录令牌参数，请重新登录！\""
                            + "}";
                    response.getWriter().print(loginTimeOut);
                    return false;
                }
                String redisToken = redisTemplate.opsForValue().get(paramToken);
                if (StringUtil.isNull(redisToken)) {
                    System.out.println("token已过期。。。。。");
                    throw new BaseException("登录信息已失效，请重新登录", "003");
                } else {
                    System.out.println("**************");
                    redisTemplate.opsForValue().set(paramToken, redisToken, tokenExpiration, TimeUnit.MINUTES);
                }
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //System.out.println("================= jin ru  after ===============");
    }


    private void setCorsMappings(HttpServletRequest request, HttpServletResponse response){
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
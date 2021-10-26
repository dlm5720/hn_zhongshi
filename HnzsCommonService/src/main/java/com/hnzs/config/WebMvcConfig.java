package com.hnzs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {

    /**
     * 这里需要先将拦截器入住，不然无法获取到拦截器中的redistemplate
     * @return
     */
    @Bean
    public MyInterceptor getMyInterceptor(){
        return new MyInterceptor();
    }

    @Bean
    public AccessParameterInterceptor getAccessParameterInterceptor(){
        return new AccessParameterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/LoginController/login.action");

        registry.addInterceptor(getAccessParameterInterceptor())
                .addPathPatterns("/**");
    }


}

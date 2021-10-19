package com.hnzs.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决MyBatis-plus中的Page出现返回total总为0的问题
 */
@Configuration
public class MybatisPlusConfig {
    /**
     *   mybatis-plus分页插件
     *
     *   设置断言mysql
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}
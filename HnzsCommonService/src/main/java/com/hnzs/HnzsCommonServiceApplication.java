package com.hnzs;

import com.hnzs.tasks.TasksServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

//@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.hnzs.*")
@MapperScan(basePackages= {"com.*.mapper"})
public class HnzsCommonServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(HnzsCommonServiceApplication.class, args);

        //我比较懒自行控制调度程序启动 仅关闭是随容器
        TasksServer ts=new TasksServer();
        ts.start();
    }

}

package com.hnzs.tasks;


import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class TasksServerShutdownHook {

    @PreDestroy
    public void destory() {
        System.out.println("--------------------------------------");
        System.out.println("调度主程序关闭中......");
        TasksServer.shutdown();
        System.out.println("调度主程序关闭完成!");
    }
}

package com.hnzs.service.common;


import java.util.HashMap;

public interface CreateTasksService {

    /*
     * 临时任务创建
     */
    public HashMap CreateTempTask(String jsons);
    /*
     * 周期任务创建
     */
    public HashMap CreatePeriodicTask(String jsons);
    /*
     * 7*24任务创建
     */
    public HashMap CreateContinuousTask(String jsons);



}

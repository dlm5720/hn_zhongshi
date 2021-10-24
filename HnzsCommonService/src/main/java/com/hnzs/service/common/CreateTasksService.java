package com.hnzs.service.common;


import java.util.HashMap;

public interface CreateTasksService {

    /*
     * 临时任务创建
     */
    public int CreateTempTask(String jsons);
    /*
     * 周期任务创建
     */
    public int CreatePeriodicTask(String jsons);
    /*
     * 7*24任务创建
     */
    public int CreateContinuousTask(String jsons);



}

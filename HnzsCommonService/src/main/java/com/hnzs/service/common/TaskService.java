package com.hnzs.service.common;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskService {

    /**
     * 获取任务信息列表
     * @param task_status
     * @param task_type
     * @param task_name
     * @return
     */
    public HashMap getTaskList(String task_status, String task_type, String task_name);

    /**
     * 删除任务
     * @param taskId
     * @return
     */
    public String deleteTaskByTaskId(String taskId,String userId);

    /**
     * 编辑任务
     * @param rows
     * @return
     */
    public String editTaskByTaskId(String rows,String userId);


    /**
     * 任务重试
     * @param rows
     * @return
     */
    public String tryAgainTask(String rows,String userId);
}

package com.hnzs.controller.common;

import com.hnzs.service.common.TaskService;
import com.hnzs.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/task/TaskController")
public class TaskController {
    /**
     * 任务
     */

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TaskService taskService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 获取任务列表
     * @return
     */
    @RequestMapping(value = "/getTaskList.action" )
    public String getTaskList(){
        String result="";
        try{
            int pageIndex = Integer.parseInt(request.getParameter("pageIndex")==null?"1":request.getParameter("pageIndex"));//页数
            int pageSize = Integer.parseInt(request.getParameter("pageSize")==null?"99999":request.getParameter("pageSize"));//每页数据量
            System.out.println("pageIndex:"+pageIndex);
            System.out.println("pageSize:"+pageSize);
            int start=(pageIndex-1)*pageSize;

            String task_status = request.getParameter("task_status")==null?"":request.getParameter("task_status");//任务状态
            String task_type = request.getParameter("task_type")==null?"":request.getParameter("task_type");//任务类型
            String task_name = request.getParameter("task_name")==null?"":request.getParameter("task_name");//任务名称 用户名称
            HashMap map=taskService.getTaskList(task_status,task_type,task_name,start,pageSize);
            map.put("pageIndex",pageIndex);
            map.put("pageSize",pageSize);
            result = JSON.Encode(map);
            System.out.println("result:"+result);
        }catch (Exception e){
            e.printStackTrace();
            HashMap hmp = new HashMap();
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("total",0);
            hmp.put("count", 0);
            hmp.put("data", "");
            //this.responseResult(request,response, JSON.Encode(hmp));
            result = JSON.Encode(hmp);

        }
        return result;
    }


    /**
     * 删除任务
     * @return
     */
    @RequestMapping(value = "/deleteTaskByTaskId.action" )
    public String deleteTaskByTaskId(){
        String result="";
        HashMap hmp = new HashMap();
        try{
//            String token = request.getParameter("token")==null?request.getHeader("token"):request.getParameter("token");
//            String retom=(String) redisTemplate.opsForValue().get(token);
//            HashMap map=(HashMap)JSON.Decode(retom);
//            String userId= map.get("id")+"";
//            System.out.println("id:"+userId);
//            String loginname= map.get("loginname")+"";
//            System.out.println("loginname:"+loginname);

            String row = request.getParameter("row");
            System.out.println("row:"+row);
            String res=taskService.deleteTaskByTaskId(row,"");
            hmp.put("code", 0);
            hmp.put("msg", res);
            hmp.put("total",0);
            hmp.put("count", 0);
            hmp.put("data", res);
        }catch (Exception e){
            e.printStackTrace();
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("total",0);
            hmp.put("count", 0);
            hmp.put("data", "");
        }
        result = JSON.Encode(hmp);

        return result;
    }

    /**
     * 编辑任务
     * @return
     */
    @RequestMapping(value = "/editTaskByTaskId.action" )
    public String editTaskByTaskId(){
        String result="";
        HashMap hmp = new HashMap();
        try{
            /*String token = request.getParameter("token")==null?request.getHeader("token"):request.getParameter("token");
            String retom=(String) redisTemplate.opsForValue().get(token);
            HashMap map=(HashMap)JSON.Decode(retom);
            String userId= map.get("id")+"";
            System.out.println("id:"+userId);
            String loginname= map.get("loginname")+"";
            System.out.println("loginname:"+loginname);*/


            String row = request.getParameter("row");
            String res=taskService.editTaskByTaskId(row,"");
            if(res.contains("失败")){
                hmp.put("code", 10000);
                hmp.put("msg", res);
                hmp.put("total",0);
                hmp.put("count", 0);
                hmp.put("data", res);
            }else{
                hmp.put("code", 0);
                hmp.put("msg", res);
                hmp.put("total",0);
                hmp.put("count", 0);
                hmp.put("data", res);
            }

        }catch (Exception e){
            e.printStackTrace();
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("total",0);
            hmp.put("count", 0);
            hmp.put("data", "");
        }
        result = JSON.Encode(hmp);

        return result;
    }

    /**
     * 任务重试
     * @return
     */
    @RequestMapping(value = "/tryAgainTask.action" )
    public String tryAgainTask() {
        String result = "";
        HashMap hmp = new HashMap();
        try {
            /*String token = request.getParameter("token") == null ? request.getHeader("token") : request.getParameter("token");
            String retom = (String) redisTemplate.opsForValue().get(token);
            HashMap map = (HashMap) JSON.Decode(retom);
            String userId = map.get("id") + "";
            System.out.println("id:" + userId);
            String loginname = map.get("loginname") + "";
            System.out.println("loginname:" + loginname);*/
            String rows = request.getParameter("rows"); //选择列表要重试的任务信息，可能多行，可能单行
            String res = taskService.tryAgainTask(rows, "");
            hmp.put("code", 0);
            hmp.put("msg", res);
            hmp.put("total", 0);
            hmp.put("count", 0);
            hmp.put("data", res);
        } catch (Exception e) {
            e.printStackTrace();
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("total", 0);
            hmp.put("count", 0);
            hmp.put("data", "");
        }
        result = JSON.Encode(hmp);

        return result;
    }

}

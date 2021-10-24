package com.hnzs.controller.common;

import  com.hnzs.service.common.CreateTasksService;
import com.hnzs.util.JSON;
import com.hnzs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;


@RestController
@RequestMapping(value = "/common/TasksController")
public class TasksController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CreateTasksService createtasksService;
    /**
     *新建临时任务
     */
    @RequestMapping(value = "/CreateTempTask.action" )
    public String CreateTempTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HashMap datamap = new HashMap();
        String result="";
        try {
            String jsons=request.getParameter("json");

            int n= createtasksService.CreateTempTask(jsons);
            // String result="";
            if(n>0){
                result="保存成功";
            }else{
                result="保存失败";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  result;

    }

    /**
     *新建临时任务
     */
    @RequestMapping(value = "/CreatePeriodicTask.action" )
    public String CreatePeriodicTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HashMap datamap = new HashMap();
        String result="";
        try {
            String jsons=request.getParameter("json");

            int n= createtasksService.CreatePeriodicTask(jsons);
            // String result="";
            if(n>0){
                result="保存成功";
            }else{
                result="保存失败";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  result;

    }

    /**
     *新建临时任务
     */
    @RequestMapping(value = "/CreateContinuousTask.action" )
    public String CreateContinuousTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HashMap datamap = new HashMap();
        String result="";
        try {
            String jsons=request.getParameter("json");

            int n= createtasksService.CreateContinuousTask(jsons);
            // String result="";
            if(n>0){
                result="保存成功";
            }else{
                result="保存失败";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  result;

    }

}

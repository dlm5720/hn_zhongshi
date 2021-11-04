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
        HashMap hmp = new HashMap();
        String result="";
        try {
            String jsons=request.getParameter("json");
            System.out.println("jsons:"+jsons);
            String type=request.getParameter("type");//1临时。2周期，3.7*24
            String res="";
            if("1".equals(type)){
                hmp= createtasksService.CreateTempTask(jsons);
            }
            if("2".equals(type)){
                hmp= createtasksService.CreatePeriodicTask(jsons);
            }
            if("3".equals(type)){
                hmp= createtasksService.CreateContinuousTask(jsons);
            }
//            // String result="";
//            if(n==0){
//                result="保存成功";
//            }else{
//                result="保存失败";
//            }
        }catch(Exception e){
            e.printStackTrace();
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("total",0);
            hmp.put("count", 0);
            hmp.put("data", e.getMessage());
        }
        return  JSON.Encode(hmp);

    }

//    /**
//     *新建临时任务
//     */
//    @RequestMapping(value = "/CreatePeriodicTask.action" )
//    public String CreatePeriodicTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        HashMap hmp = new HashMap();
//        String result="";
//        try {
//            String jsons=request.getParameter("json");
//
//            int n= createtasksService.CreatePeriodicTask(jsons);
//            // String result="";
//            if(n==0){
//                result="保存成功";
//            }else{
//                result="保存失败";
//            }
//            hmp.put("code", 0);
//            hmp.put("msg", result);
//            hmp.put("total",0);
//            hmp.put("count", 0);
//            hmp.put("data", result);
//        }catch(Exception e){
//            e.printStackTrace();
//            hmp.put("code", 10000);
//            hmp.put("msg", e.getMessage());
//            hmp.put("total",0);
//            hmp.put("count", 0);
//            hmp.put("data", e.getMessage());
//        }
//        return  JSON.Encode(hmp);
//
//    }
//
//    /**
//     *新建临时任务
//     */
//    @RequestMapping(value = "/CreateContinuousTask.action" )
//    public String CreateContinuousTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        HashMap hmp = new HashMap();
//        String result="";
//        try {
//            String jsons=request.getParameter("json");
//
//            int n= createtasksService.CreateContinuousTask(jsons);
//            // String result="";
//            if(n==0){
//                result="保存成功";
//            }else{
//                result="保存失败";
//            }
//            hmp.put("code", 0);
//            hmp.put("msg", result);
//            hmp.put("total",0);
//            hmp.put("count", 0);
//            hmp.put("data", result);
//        }catch(Exception e){
//            e.printStackTrace();
//            hmp.put("code", 10000);
//            hmp.put("msg", e.getMessage());
//            hmp.put("total",0);
//            hmp.put("count", 0);
//            hmp.put("data", e.getMessage());
//        }
//        return  JSON.Encode(hmp);
//
//    }

}

package com.hnzs.service.common;

import com.hnzs.dao.common.CommonDao;
import com.hnzs.tasks.TasksServer;
import com.hnzs.util.DateUtils;
import com.hnzs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.hnzs.util.JSON;

@Service
@SuppressWarnings("all")
public class CreateTasksServiceImpl implements CreateTasksService{

    @Autowired
    private CommonDao commonDao;

    //@Value("${RecruitStartUrl}")
    private String RecruitStartUrl="https://www.baidu.com";
    @Value("${RecruitStopUrl}")
    private String RecruitStopUrl;


    @Override
    //临时任务创建
    public HashMap CreateTempTask(String jsons) {
        String sql = "";
        String res="";
        int result = 1,ls_temp=0;
        HashMap hmp=new HashMap();
        String add_double="";
        ArrayList ayList=new ArrayList();
        try{
            HashMap row = (HashMap) JSON.Decode(jsons);
            System.out.println("row:"+row);
            String task_name=row.get("task_name")==null?"":row.get("task_name").toString();
            String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();
            String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();
            String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();
            String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();
            String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();
            String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();
            String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();
            String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();
            String insert_time= DateUtils.formatDateTimeByDate(new Date());
            String task_status="待收录";
            String task_type="临时任务";
            String uuid=StringUtil.getUuid();
            HashMap taskData=new HashMap();
            taskData.put("taskType","1");//1代表临时任务
            taskData.put("taskID",uuid);
            taskData.put("taskName",task_name);
            taskData.put("taskBelogFlow",Belong_source);
            taskData.put("taskStartTime",task_start_time);
            taskData.put("taskEndTime",task_end_time);
            //taskData.put("taskCrossDay","0");
            String cycle="";
            if(!StringUtil.isNull(cycle_period)){
                if(cycle_period.contains("一")){
                    cycle="1";
                }

                if(cycle_period.contains("二")){
                    cycle="2";
                }
                if(cycle_period.contains("三")){
                    cycle="3";
                }
                if(cycle_period.contains("四")){
                    cycle="4";
                }
                if(cycle_period.contains("五")){
                    cycle="5";
                }if(cycle_period.contains("六")){
                    cycle="6";
                }
                if(cycle_period.contains("日")){
                    cycle="7";
                }
            }
            taskData.put("taskCycleTime",cycle);//周期时间[1,2,3,4,5,6,7]
            taskData.put("taskCycleEndTime",cycle_end_time);
            taskData.put("taskIsTranscode",is_Transcoding);
            taskData.put("taskStorageLocation",storage_location);

            //创建开启收录xml
            StringBuilder sb = new StringBuilder();
            sb.append("<? xml version=\"1.0\" ?>");
            sb.append("    <record>");
            sb.append("        <id>"+uuid+"</id>");
            sb.append("        <state>tryAgainTask</state>");
            sb.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
            sb.append("        <input>/opt/testggx/1.mp4</input>");
            sb.append("        <output>"+storage_location+"</output>");
            sb.append("        <name>save</name>");
            sb.append("        <format>mp4</format>");
            sb.append("        <starttime>"+task_start_time+"</starttime>");
            sb.append("        <duration>"+task_duration+"</duration>");
            sb.append("        <dividetime>0</dividetime>");
            sb.append("        <copy>1</copy>");
            sb.append("    </record>");
            taskData.put("startXml",sb.toString());

            //创建关闭收录xml
            StringBuilder sb1 = new StringBuilder();
            sb1.append("<? xml version=\"1.0\" ?>");
            sb1.append("    <transcode>");
            sb1.append("        <id>"+uuid+"</id>");
            sb1.append("        <state>recordstop</state>");
            sb1.append("    </transcode>");
            taskData.put("stopXml",sb1.toString());
            taskData.put("recruitStartUrl",RecruitStartUrl);
            taskData.put("recruitStopUrl",RecruitStopUrl);
            System.out.println("taskData："+taskData);
            String suresult= TasksServer.addTask(JSON.Encode(taskData));
            System.out.println("ssssssss："+suresult);
            HashMap hmap=(HashMap) JSON.Decode(suresult);
            String code=hmap.get("code")+"";
            String msg=hmap.get("msg")+"";
            if("0".equals(code)){
                String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                        "task_start_time,task_end_time,task_duration,is_Transcoding,storage_location,"+
                        "task_status,insert_time)" +
                        " VALUES (";
                sqll += " '"+uuid+"','"+task_type+"','"+task_name+"','"+Belong_source+"','"+task_start_time+"','"+task_end_time+"'," +
                        "'"+task_duration+"','"+is_Transcoding+"','"+storage_location+"','"+task_status+"','"+insert_time+"' )";
                ayList.add(sqll);
                System.out.println("ayList:"+ayList);
                if(!StringUtil.isNullList(ayList)) {
                    int n = commonDao.addUpdateDeleteExecute(ayList);
                    if(n>0){
                        result=0;
                        res="临时任务新增成功";
                        hmp.put("data", res);
                        hmp.put("total", 0);
                        hmp.put("code", 0);
                        hmp.put("msg", res);
                        hmp.put("count", 0);
                    }else{
                        result=1;
                        res="临时任务新增失败";
                        hmp.put("data", res);
                        hmp.put("total", 0);
                        hmp.put("code", 10000);
                        hmp.put("msg", res);
                        hmp.put("count", 0);
                    }
                }else{
                    result=1;
                    res="临时任务新增失败";
                    hmp.put("data", res);
                    hmp.put("total", 0);
                    hmp.put("code", 10000);
                    hmp.put("msg", res);
                    hmp.put("count", 0);
                }
            }else{
                result=1;
                res=msg;
                hmp.put("data", res);
                hmp.put("total", 0);
                hmp.put("code", 10000);
                hmp.put("msg", res);
                hmp.put("count", 0);
            }

        }catch (Exception e){
            e.printStackTrace();
            hmp.put("data", e.getMessage());
            hmp.put("total", 0);
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("count", 0);
        }
        return hmp;
    }

    //周期任务创建
    public HashMap CreatePeriodicTask(String jsons) {
        HashMap hmp=new HashMap();
        String sql = "";
        String res="";
        int result = 1,ls_temp=0;
        String add_double="";
        ArrayList ayList=new ArrayList();
        try{

            HashMap row = (HashMap) JSON.Decode(jsons);
            String task_name=row.get("task_name")==null?"":row.get("task_name").toString();
            String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();
            String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();
            String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();
            String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();
            String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();
            String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();
            String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();
            String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();
            String insert_time= DateUtils.formatDateTimeByDate(new Date());
            String task_status="待收录";
            String task_type="周期任务";
            String uuid=StringUtil.getUuid();
            HashMap taskData=new HashMap();
            taskData.put("taskType","2");//2代表周期任务
            taskData.put("taskID",uuid);
            taskData.put("taskName",task_name);
            taskData.put("taskBelogFlow",Belong_source);
            taskData.put("taskStartTime",task_start_time);
            taskData.put("taskEndTime",task_end_time);
            //taskData.put("taskCrossDay","0");
            String cycle="";
            if(!StringUtil.isNull(cycle_period)){
                if(cycle_period.contains("一")){
                    cycle="1";
                }

                if(cycle_period.contains("二")){
                    cycle="2";
                }
                if(cycle_period.contains("三")){
                    cycle="3";
                }
                if(cycle_period.contains("四")){
                    cycle="4";
                }
                if(cycle_period.contains("五")){
                    cycle="5";
                }if(cycle_period.contains("六")){
                    cycle="6";
                }
                if(cycle_period.contains("日")){
                    cycle="7";
                }
            }
            taskData.put("taskCycleTime",cycle);//周期时间[1,2,3,4,5,6,7]
            taskData.put("taskCycleEndTime",cycle_end_time);
            taskData.put("taskIsTranscode",is_Transcoding);
            taskData.put("taskStorageLocation",storage_location);

            //创建xml
            StringBuilder sb = new StringBuilder();
            sb.append("<? xml version=\"1.0\" ?>");
            sb.append("    <record>");
            sb.append("        <id>"+uuid+"</id>");
            sb.append("        <state>tryAgainTask</state>");
            sb.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
            sb.append("        <input>/opt/testggx/1.mp4</input>");
            sb.append("        <output>"+storage_location+"</output>");
            sb.append("        <name>save</name>");
            sb.append("        <format>mp4</format>");
            sb.append("        <starttime>"+task_start_time+"</starttime>");
            sb.append("        <duration>"+task_duration+"</duration>");
            sb.append("        <dividetime>0</dividetime>");
            sb.append("        <copy>1</copy>");
            sb.append("    </record>");
            taskData.put("startXml",sb.toString());

            //创建关闭收录xml
            StringBuilder sb1 = new StringBuilder();
            sb1.append("<? xml version=\"1.0\" ?>");
            sb1.append("    <transcode>");
            sb1.append("        <id>"+uuid+"</id>");
            sb1.append("        <state>recordstop</state>");
            sb1.append("    </transcode>");
            taskData.put("stopXml",sb1.toString());
            taskData.put("recruitStartUrl",RecruitStartUrl);
            taskData.put("recruitStopUrl",RecruitStopUrl);


            String suresult= TasksServer.addTask(JSON.Encode(taskData));
            HashMap hmap=(HashMap) JSON.Decode(suresult);
            String code=hmap.get("code")+"";
            String msg=hmap.get("msg")+"";
            if("0".equals(code)){
                String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                        "task_start_time,task_end_time,task_duration,cycle_period,cycle_end_time,is_Transcoding,storage_location,"+
                        "task_status,insert_time)" +
                        " VALUES (";
                sqll += " '"+uuid+"','"+task_type+"','"+task_name+"','"+Belong_source+"','"+task_start_time+"'," +
                        "'"+task_end_time+"'," +
                        "'"+task_duration+"','"+cycle_period+"','"+cycle_end_time+"'," +
                        "'"+is_Transcoding+"','"+storage_location+"','"+task_status+"','"+insert_time+"' )";
                ayList.add(sqll);
                System.out.println("ayList:"+ayList);
                if(!StringUtil.isNullList(ayList)) {
                    int n = commonDao.addUpdateDeleteExecute(ayList);
                    if(n>0){
                        n=0;
                        res="周期任务新增成功";
                        hmp.put("data", res);
                        hmp.put("total", 0);
                        hmp.put("code", 0);
                        hmp.put("msg", res);
                        hmp.put("count", 0);
                    }else{
                        result=1;
                        res="周期任务新增失败";
                        hmp.put("data", res);
                        hmp.put("total", 0);
                        hmp.put("code", 10000);
                        hmp.put("msg", res);
                        hmp.put("count", 0);
                    }
                }else{
                    result=1;
                    res="周期任务新增失败";
                    hmp.put("data", res);
                    hmp.put("total", 0);
                    hmp.put("code", 10000);
                    hmp.put("msg", res);
                    hmp.put("count", 0);
                }
            }else{
                result=1;
                res=msg;
                hmp.put("data", res);
                hmp.put("total", 0);
                hmp.put("code", 10000);
                hmp.put("msg", res);
                hmp.put("count", 0);
            }

        }catch (Exception e){
            e.printStackTrace();
            hmp.put("data", e.getMessage());
            hmp.put("total", 0);
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("count", 0);
        }
        return hmp;
    }

    //7*24任务创建
    public HashMap CreateContinuousTask(String jsons) {
        HashMap hmp=new HashMap();
        String sql = "";
        String res="";
        int result = 1,ls_temp=0;
        String add_double="";
        ArrayList ayList=new ArrayList();
        try{
            HashMap row = (HashMap) JSON.Decode(jsons);
            String task_name=row.get("task_name")==null?"":row.get("task_name").toString();
            String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();
            String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();
             String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();
            String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();
            String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();
            String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();
            String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();
            String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();
            String insert_time= DateUtils.formatDateTimeByDate(new Date());
            task_end_time="2099-12-30 00:00:00";
            String task_status="待收录";
            String task_type="7*24任务";
            String uuid=StringUtil.getUuid();
            HashMap taskData=new HashMap();
            taskData.put("taskType","3");//3代表7*24任务
            taskData.put("taskID",uuid);
            taskData.put("taskName",task_name);
            taskData.put("taskBelogFlow",Belong_source);
            taskData.put("taskStartTime",task_start_time);
            taskData.put("taskEndTime",task_end_time);
            //taskData.put("taskCrossDay","0");
            String cycle="";
            if(!StringUtil.isNull(cycle_period)){
                if(cycle_period.contains("一")){
                    cycle="1";
                }

                if(cycle_period.contains("二")){
                    cycle="2";
                }
                if(cycle_period.contains("三")){
                    cycle="3";
                }
                if(cycle_period.contains("四")){
                    cycle="4";
                }
                if(cycle_period.contains("五")){
                    cycle="5";
                }if(cycle_period.contains("六")){
                    cycle="6";
                }
                if(cycle_period.contains("日")){
                    cycle="7";
                }
            }
            taskData.put("taskCycleTime",cycle);//周期时间[1,2,3,4,5,6,7]
            taskData.put("taskCycleEndTime",cycle_end_time);
            taskData.put("taskIsTranscode",is_Transcoding);
            taskData.put("taskStorageLocation",storage_location);

            //创建xml
            StringBuilder sb = new StringBuilder();
            sb.append("<? xml version=\"1.0\" ?>");
            sb.append("    <record>");
            sb.append("        <id>"+uuid+"</id>");
            sb.append("        <state>tryAgainTask</state>");
            sb.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
            sb.append("        <input>/opt/testggx/1.mp4</input>");
            sb.append("        <output>"+storage_location+"</output>");
            sb.append("        <name>save</name>");
            sb.append("        <format>mp4</format>");
            sb.append("        <starttime>"+task_start_time+"</starttime>");
            sb.append("        <duration>"+task_duration+"</duration>");
            sb.append("        <dividetime>0</dividetime>");
            sb.append("        <copy>1</copy>");
            sb.append("    </record>");
            taskData.put("startXml",sb.toString());

            //创建关闭收录xml
            StringBuilder sb1 = new StringBuilder();
            sb1.append("<? xml version=\"1.0\" ?>");
            sb1.append("    <transcode>");
            sb1.append("        <id>"+uuid+"</id>");
            sb1.append("        <state>recordstop</state>");
            sb1.append("    </transcode>");
            taskData.put("stopXml",sb1.toString());
            System.out.println("RecruitStartUrl:"+RecruitStartUrl);
            taskData.put("recruitStartUrl",RecruitStartUrl);
            taskData.put("recruitStopUrl",RecruitStopUrl);
            System.out.println("taskData："+taskData);
            String suresult= TasksServer.addTask(JSON.Encode(taskData));
            HashMap hmap=(HashMap) JSON.Decode(suresult);
            String code=hmap.get("code")+"";
            String msg=hmap.get("msg")+"";
            if("0".equals(code)){
                String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                        "task_start_time,task_end_time,task_duration,is_Transcoding,storage_location,"+
                        "task_status,insert_time)" +
                        " VALUES (";
                sqll += " '"+uuid+"','"+task_type+"','"+task_name+"','"+Belong_source+"','"+task_start_time+"'," +
                        "'"+task_end_time+"'," +
                        "'"+task_duration+"',"+
                        "'"+is_Transcoding+"','"+storage_location+"','"+task_status+"','"+insert_time+"' )";
                ayList.add(sqll);
                System.out.println("ayList:"+ayList);
                if(!StringUtil.isNullList(ayList)) {
                    int n = commonDao.addUpdateDeleteExecute(ayList);
                    if(n>0){
                        result=0;
                        res="7*24任务新增成功";
                        hmp.put("data", res);
                        hmp.put("total", 0);
                        hmp.put("code", 0);
                        hmp.put("msg", res);
                        hmp.put("count", 0);
                    }else{
                        result=1;
                        res="7*24任务新增失败";
                        hmp.put("data", res);
                        hmp.put("total", 0);
                        hmp.put("code", 10000);
                        hmp.put("msg", res);
                        hmp.put("count", 0);
                    }
                }else{
                    result=1;
                    res="7*24任务新增失败";
                    hmp.put("data", res);
                    hmp.put("total", 0);
                    hmp.put("code", 10000);
                    hmp.put("msg", res);
                    hmp.put("count", 0);
                }
            }else{
                result=1;
                res=msg;
                hmp.put("data", res);
                hmp.put("total", 0);
                hmp.put("code", 10000);
                hmp.put("msg", res);
                hmp.put("count", 0);
            }

        }catch (Exception e){
            e.printStackTrace();
            hmp.put("data", e.getMessage());
            hmp.put("total", 0);
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("count", 0);
        }
        return hmp;
    }

}

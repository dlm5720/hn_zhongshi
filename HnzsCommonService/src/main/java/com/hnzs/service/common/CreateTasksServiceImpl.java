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

    @Value("${RecruitStartUrl}")
    private String RecruitStartUrl;
    @Value("${RecruitStopUrl}")
    private String RecruitStopUrl;


    @Override
    //临时任务创建
    public int CreateTempTask(String jsons) {
        String sql = "";
        int result = 1,ls_temp=0;
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
            taskData.put("taskType",task_type);
            taskData.put("taskID",uuid);
            taskData.put("taskName",task_name);
            taskData.put("taskBelogFlow",Belong_source);
            taskData.put("taskStartTime",task_start_time);
            taskData.put("taskEndTime",task_end_time);
            //taskData.put("taskCrossDay","0");
            taskData.put("taskCycleTime",cycle_period);
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
            taskData.put(" recruitStartUrl",RecruitStartUrl);
            taskData.put(" recruitStopUrl",RecruitStopUrl);
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
                    }else{
                        result=1;
                    }
                }else{
                    result=1;
                }
            }else{
                result=1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //周期任务创建
    public int CreatePeriodicTask(String jsons) {
        String sql = "";
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
            taskData.put("taskType",task_type);
            taskData.put("taskID",uuid);
            taskData.put("taskName",task_name);
            taskData.put("taskBelogFlow",Belong_source);
            taskData.put("taskStartTime",task_start_time);
            taskData.put("taskEndTime",task_end_time);
            //taskData.put("taskCrossDay","0");
            taskData.put("taskCycleTime",cycle_period);
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
            taskData.put(" recruitStartUrl",RecruitStartUrl);
            taskData.put(" recruitStopUrl",RecruitStopUrl);


            String suresult= TasksServer.addTask(JSON.Encode(taskData));
            HashMap hmap=(HashMap) JSON.Decode(suresult);
            String code=hmap.get("code")+"";
            String msg=hmap.get("msg")+"";
            if("0".equals(code)){
                String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                        "task_start_time,task_end_time,task_duration,cycle_period,cycle_end_time,is_Transcoding,storage_location,"+
                        "task_status,insert_time)" +
                        " VALUES (";
                sqll += " '"+uuid+"','"+task_type+"','"+Belong_source+"','"+task_start_time+"','"+task_end_time+"'," +
                        "'"+task_duration+"','"+cycle_period+"','"+cycle_end_time+"','"+is_Transcoding+"','"+storage_location+"','"+task_status+"','"+insert_time+"' )";
                ayList.add(sqll);
                System.out.println("ayList:"+ayList);
                if(!StringUtil.isNullList(ayList)) {
                    int n = commonDao.addUpdateDeleteExecute(ayList);
                    if(n>0){
                        result=0;
                    }else{
                        result=1;
                    }
                }else{
                    result=1;
                }
            }else{
                result=1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //7*24任务创建
    public int CreateContinuousTask(String jsons) {
        String sql = "";
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
            String task_type="7*24任务";
            String uuid=StringUtil.getUuid();
            HashMap taskData=new HashMap();
            taskData.put("taskType",task_type);
            taskData.put("taskID",uuid);
            taskData.put("taskName",task_name);
            taskData.put("taskBelogFlow",Belong_source);
            taskData.put("taskStartTime",task_start_time);
            taskData.put("taskEndTime",task_end_time);
            //taskData.put("taskCrossDay","0");
            taskData.put("taskCycleTime",cycle_period);
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
            taskData.put(" recruitStartUrl",RecruitStartUrl);
            taskData.put(" recruitStopUrl",RecruitStopUrl);

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
                        "'"+is_Transcoding+"','"+storage_location+"','"+task_status+"','"+insert_time+"' )";
                ayList.add(sqll);
                System.out.println("ayList:"+ayList);
                if(!StringUtil.isNullList(ayList)) {
                    int n = commonDao.addUpdateDeleteExecute(ayList);
                    if(n>0){
                        result=0;
                    }else{
                        result=1;
                    }
                }else{
                    result=1;
                }
            }else{
                result=1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}

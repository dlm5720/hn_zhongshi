package com.hnzs.service.common;

import com.hnzs.dao.common.CommonDao;
import com.hnzs.tasks.TasksServer;
import com.hnzs.util.DateUtils;
import com.hnzs.util.HttpRequest;
import com.hnzs.util.JSON;
import com.hnzs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service("TaskService")
@PropertySource(value = {"classpath:recruit.properties"})
@Transactional
public class TaskServiceImpl implements TaskService {

    @Value("${RecruitUrl}")
    private String RecruitUrl;

    @Autowired
    private CommonDao commonDao;

    @Override
    public HashMap getTaskList(String task_status, String task_type, String task_name) {
        HashMap hmp=new HashMap();
        ArrayList list=new ArrayList();
        try{
            String sql=" select id,task_type,task_name,Belong_source,task_start_time," +
                    "task_end_time,task_duration,cycle_period,cycle_end_time," +
                    "is_Transcoding,storage_location,task_status," +
                    "create_admin,insert_time,is_delete " +
                    " from zs_tb_task_detail_info " +
                    " where is_delete !='1'";
            if(!StringUtil.isNull(task_status)){
                sql += " and task_status ='"+task_status+"' ";
            }
            if(!StringUtil.isNull(task_type)){
                sql += " and task_type ='"+task_type+"' ";
            }
            if(!StringUtil.isNull(task_name)){
                sql += " and CONCAT(task_name,create_admin) like '%"+task_name+"%'";
            }
            list=commonDao.selectExecute(sql);
            if(!StringUtil.isNullList(list)){
                hmp.put("data", list);
                hmp.put("total", list.size());
                hmp.put("code", 0);
                hmp.put("msg", "查询成功");
                hmp.put("count", list.size());
            }

        }catch (Exception e){
            e.printStackTrace();
            hmp.put("data", "");
            hmp.put("total", 0);
            hmp.put("code", 10000);
            hmp.put("msg", e.getMessage());
            hmp.put("count", 0);
        }
        return hmp;
    }

    @Override
    public String deleteTaskByTaskId(String rows,String userId) {
        String result="";
        HashMap hmp=new HashMap();
        ArrayList alist=new ArrayList();
        try{
            //获取系统当前时间
            String currenttime = DateUtils.formatDateTimeByDate(new Date());
            ArrayList list = (ArrayList) JSON.Decode(rows);
            if(!StringUtil.isNullList(list)) {
                for (int i = 0, l = list.size(); i < l; i++) {
                    HashMap row = (HashMap) list.get(i);
                    String id = row.get("id") == null ? "" : row.get("id").toString();//id
                    String task_type = row.get("task_type") == null ? "" : row.get("task_type").toString();//任务类型
                    String task_name = row.get("task_name") == null ? "" : row.get("task_name").toString();//任务名称
                    String Belong_source = row.get("Belong_source") == null ? "" : row.get("Belong_source").toString();//id
                    String task_start_time = row.get("task_start_time") == null ? "" : row.get("task_start_time").toString();//任务类型
                    String task_end_time = row.get("task_end_time") == null ? "" : row.get("task_end_time").toString();//任务名称
                    String task_duration = row.get("task_duration") == null ? "" : row.get("task_duration").toString();//id
                    String cycle_period = row.get("cycle_period") == null ? "" : row.get("cycle_period").toString();//任务类型
                    String cycle_end_time = row.get("cycle_end_time") == null ? "" : row.get("cycle_end_time").toString();//任务名称
                    String is_Transcoding = row.get("is_Transcoding") == null ? "" : row.get("is_Transcoding").toString();//id
                    String storage_location = row.get("storage_location") == null ? "" : row.get("storage_location").toString();//任务类型
                    String task_status = row.get("task_status") == null ? "" : row.get("task_status").toString();//任务名称
                    //先调用接口进行判断，再删除
                    HashMap taskData = new HashMap();
                    taskData.put("taskType", task_type);
                    taskData.put("taskID", id);
                    taskData.put("taskName", task_name);
                    taskData.put("taskBelogFlow", Belong_source);
                    taskData.put("taskStartTime", task_start_time);
                    taskData.put("taskEndTime", task_end_time);
                    //taskData.put("taskCrossDay","0");
                    taskData.put("taskCycleTime", cycle_period);
                    taskData.put("taskCycleEndTime", cycle_end_time);
                    taskData.put("taskIsTranscode", is_Transcoding);
                    taskData.put("taskStorageLocation", storage_location);
                    taskData.put(" requestURL", RecruitUrl);
                    //创建xml
                    StringBuilder sb = new StringBuilder();
                    sb.append("<? xml version=\"1.0\" ?>");
                    sb.append("    <record>");
                    sb.append("        <id>" + id + "</id>");
                    sb.append("        <state>tryAgainTask</state>");
                    sb.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
                    sb.append("        <input>/opt/testggx/1.mp4</input>");
                    sb.append("        <output>" + storage_location + "</output>");
                    sb.append("        <name>save</name>");
                    sb.append("        <format>mp4</format>");
                    sb.append("        <starttime>" + task_start_time + "</starttime>");
                    sb.append("        <duration>" + task_duration + "</duration>");
                    sb.append("        <dividetime>0</dividetime>");
                    sb.append("        <copy>1</copy>");
                    sb.append("    </record>");
                    taskData.put("requestParam", sb.toString());
                    //String suresult = TasksServer.addTask(JSON.Encode(taskData));
                    String suresult = TasksServer.delTask(JSON.Encode(taskData));
                    HashMap hmap = (HashMap) JSON.Decode(suresult);
                    String code = hmap.get("code") + "";
                    String msg = hmap.get("msg") + "";
                    if("0".equals(code)){
                        String sql = " update zs_tb_task_detail_info " +
                                " set is_delete='1' ,delete_time='" + currenttime + "'," +
                                " delete_admin='" + userId + "' " +
                                " where id='" + id + "' and is_delete !='1'";
                        alist.add(sql);
                    }else{
                        result="该任务删除失败";
                    }

                }
            }
            if(!StringUtil.isNullList(alist)){
                int n=commonDao.addUpdateDeleteExecute(alist);
                if(n>0){
                    result="该任务删除成功";
                }else{
                    result="该任务删除失败";
                }

            }else{
                result="没有要删除的任务";
            }
        }catch (Exception e){
            e.printStackTrace();
            result=e.getMessage();
        }
        return result;
    }

    @Override
    public String editTaskByTaskId(String rows,String userId) {
        String result="";
        HashMap hmp=new HashMap();
        try{
            //获取系统当前时间
            String currenttime = DateUtils.formatDateTimeByDate(new Date());
            ArrayList list = (ArrayList) JSON.Decode(rows);
            if(!StringUtil.isNullList(list)){
                for(int i=0,l=list.size(); i<l; i++){
                    HashMap row = (HashMap)list.get(i);
                    String id=row.get("id")==null?"":row.get("id").toString();//id
                    String task_type=row.get("task_type")==null?"":row.get("task_type").toString();//任务类型
                    String task_name=row.get("task_name")==null?"":row.get("task_name").toString();//任务名称
                    String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();//id
                    String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();//任务类型
                    String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();//任务名称
                    String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();//id
                    String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();//任务类型
                    String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();//任务名称
                    String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();//id
                    String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();//任务类型
                    String task_status=row.get("task_status")==null?"":row.get("task_status").toString();//任务名称


                    //先根据id查询出数据库原有已保存的任务
                    String sql1=" select id,task_type,task_name,Belong_source,task_start_time," +
                            "task_end_time,task_duration,cycle_period,cycle_end_time," +
                            "is_Transcoding,storage_location,task_status," +
                            "create_admin,insert_time,is_delete " +
                            " from zs_tb_task_detail_info " +
                            " where is_delete !='1' and id='"+id+"' ";
                    ArrayList alist=commonDao.selectExecute(sql1);
                    if(!StringUtil.isNullList(alist)){
                        for(int t=0;t<alist.size();t++){
                            HashMap tmap=(HashMap)alist.get(t);

                            String task_type1=row.get("task_type")==null?"":row.get("task_type").toString();//任务类型
                            String task_name1=row.get("task_name")==null?"":row.get("task_name").toString();//任务名称
                            String Belong_source1=row.get("Belong_source")==null?"":row.get("Belong_source").toString();//id
                            String task_start_time1=row.get("task_start_time")==null?"":row.get("task_start_time").toString();//任务类型
                            String task_end_time1=row.get("task_end_time")==null?"":row.get("task_end_time").toString();//任务名称
                            String task_duration1=row.get("task_duration")==null?"":row.get("task_duration").toString();//id
                            String cycle_period1=row.get("cycle_period")==null?"":row.get("cycle_period").toString();//任务类型
                            String cycle_end_time1=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();//任务名称
                            String is_Transcoding1=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();//id
                            String storage_location1=row.get("storage_location")==null?"":row.get("storage_location").toString();//任务类型
                            String task_status1=row.get("task_status")==null?"":row.get("task_status").toString();//任务名称

                            //先调用接口进行判断，再删除
                            HashMap taskData = new HashMap();
                            taskData.put("taskType", task_type1);
                            taskData.put("taskID", id);
                            taskData.put("taskName", task_name1);
                            taskData.put("taskBelogFlow", Belong_source1);
                            taskData.put("taskStartTime", task_start_time1);
                            taskData.put("taskEndTime", task_end_time1);
                            //taskData.put("taskCrossDay","0");
                            taskData.put("taskCycleTime", cycle_period1);
                            taskData.put("taskCycleEndTime", cycle_end_time1);
                            taskData.put("taskIsTranscode", is_Transcoding1);
                            taskData.put("taskStorageLocation", storage_location1);
                            taskData.put(" requestURL", RecruitUrl);
                            //创建xml
                            StringBuilder sb = new StringBuilder();
                            sb.append("<? xml version=\"1.0\" ?>");
                            sb.append("    <record>");
                            sb.append("        <id>" + id + "</id>");
                            sb.append("        <state>tryAgainTask</state>");
                            sb.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
                            sb.append("        <input>/opt/testggx/1.mp4</input>");
                            sb.append("        <output>" + storage_location1 + "</output>");
                            sb.append("        <name>save</name>");
                            sb.append("        <format>mp4</format>");
                            sb.append("        <starttime>" + task_start_time1 + "</starttime>");
                            sb.append("        <duration>" + task_duration1 + "</duration>");
                            sb.append("        <dividetime>0</dividetime>");
                            sb.append("        <copy>1</copy>");
                            sb.append("    </record>");
                            taskData.put("requestParam", sb.toString());
                            //String suresult = TasksServer.addTask(JSON.Encode(taskData));
                            String suresult = TasksServer.delTask(JSON.Encode(taskData));
                            HashMap hmap = (HashMap) JSON.Decode(suresult);
                            String code = hmap.get("code") + "";
                            String msg = hmap.get("msg") + "";
                            if("0".equals(code)){
                                //删除成功之后再进行添加
                                //先调用接口进行判断，再删除
                                HashMap taskData1 = new HashMap();
                                taskData1.put("taskType", task_type);
                                taskData1.put("taskID", id);
                                taskData1.put("taskName", task_name);
                                taskData1.put("taskBelogFlow", Belong_source);
                                taskData1.put("taskStartTime", task_start_time);
                                taskData1.put("taskEndTime", task_end_time);
                                //taskData.put("taskCrossDay","0");
                                taskData1.put("taskCycleTime", cycle_period);
                                taskData1.put("taskCycleEndTime", cycle_end_time);
                                taskData1.put("taskIsTranscode", is_Transcoding);
                                taskData1.put("taskStorageLocation", storage_location);
                                taskData1.put(" requestURL", RecruitUrl);
                                //创建xml
                                StringBuilder sb1 = new StringBuilder();
                                sb1.append("<? xml version=\"1.0\" ?>");
                                sb1.append("    <record>");
                                sb1.append("        <id>" + id + "</id>");
                                sb1.append("        <state>tryAgainTask</state>");
                                sb1.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
                                sb1.append("        <input>/opt/testggx/1.mp4</input>");
                                sb1.append("        <output>" + storage_location + "</output>");
                                sb1.append("        <name>save</name>");
                                sb1.append("        <format>mp4</format>");
                                sb1.append("        <starttime>" + task_start_time + "</starttime>");
                                sb1.append("        <duration>" + task_duration + "</duration>");
                                sb1.append("        <dividetime>0</dividetime>");
                                sb1.append("        <copy>1</copy>");
                                sb1.append("    </record>");
                                taskData1.put("requestParam", sb1.toString());
                                //String suresult = TasksServer.addTask(JSON.Encode(taskData));
                                String suresult1 = TasksServer.addTask(JSON.Encode(taskData1));
                                HashMap hmap1 = (HashMap) JSON.Decode(suresult1);
                                String code1 = hmap1.get("code") + "";
                                String msg1 = hmap1.get("msg") + "";
                                if("0".equals(code1)){
                                    String sql =" update zs_tb_task_detail_info " +
                                            " set task_type='"+task_type+"' ,task_name='"+task_name+"'," +
                                            " Belong_source='"+Belong_source+"',task_start_time='"+task_start_time+"'," +
                                            " task_end_time='"+task_end_time+"',task_duration='"+task_duration+"'," +
                                            " cycle_period='"+cycle_period+"',cycle_end_time='"+cycle_end_time+"'," +
                                            " is_Transcoding='"+is_Transcoding+"',storage_location='"+storage_location+"'," +
                                            " task_status='"+task_status+"',update_admin='"+userId+"'," +
                                            " update_time='"+currenttime+"' ";
                                    sql += " where id='"+id+"' and is_delete !='1'";
                                    ArrayList arrayList=new ArrayList();
                                    arrayList.add(sql);
                                    if(!StringUtil.isNullList(arrayList)){
                                        int n=commonDao.addUpdateDeleteExecute(arrayList);
                                        if(n>0){
                                            result="该任务编辑成功";
                                        }else{
                                            result="该任务编辑失败";
                                        }

                                    }else{
                                        result="没有要编辑的任务";
                                    }
                                }else{
                                    result="该任务编辑失败";
                                }
                            }else{
                                result="该任务编辑失败";
                            }

                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result=e.getMessage();
        }
        return result;
    }

    @Override
    public String tryAgainTask(String rows, String userId) {
        String result="";
        HashMap hmp=new HashMap();
        try {
            //获取系统当前时间
            String currenttime = DateUtils.formatDateTimeByDate(new Date());
            ArrayList list = (ArrayList) JSON.Decode(rows);
            if (!StringUtil.isNullList(list)) {
                for (int i = 0, l = list.size(); i < l; i++) {
                    HashMap row = (HashMap) list.get(i);
                    String id=row.get("id")==null?"":row.get("id").toString();//id
                    String task_type=row.get("task_type")==null?"":row.get("task_type").toString();//任务类型
                    String task_name=row.get("task_name")==null?"":row.get("task_name").toString();//任务名称
                    String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();//id
                    String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();//任务类型
                    String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();//任务名称
                    String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();//id
                    String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();//任务类型
                    String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();//任务名称
                    String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();//id
                    String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();//任务类型
                    String task_status=row.get("task_status")==null?"":row.get("task_status").toString();//任务名称

                    //创建xml
                    StringBuilder sb = new StringBuilder();
                    sb.append("<? xml version=\"1.0\" ?>");
                    sb.append("    <record>");
                    sb.append("        <id>"+id+"</id>");
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
                    HashMap map=HttpRequest.sendPostXml(RecruitUrl,sb.toString());
                    String rescruitResult=map.get("result")+"";
                    String rescruitFlag=map.get("flag")+"";
                    String state="";
                    String message="";
                    if("ok".equals(rescruitFlag)){
                        HashMap rmap=(HashMap) JSON.Decode(rescruitResult);
                        state=rmap.get("state")+"";
                        message=rmap.get("message")+"";

                        if("0".equals(state)){
                            // 说明开始收录成功
                            result+=task_name+"任务已重试成功;";
                            //重试成功之后，需要将任务状态修改为进行中
                            String u_sql="update zs_tb_task_detail_info " +
                                    " set task_status='进行中' ,update_admin='"+userId+"'," +
                                    " update_time='"+currenttime+"' ";
                            u_sql += " where id='"+id+"' and is_delete !='1'";
                            list.add(u_sql);
                        }else{
                            // 说明开始收录失败
                            result+=task_name+"任务重试失败;";
                        }

                    }

                    //将该操作记录保存到数据库
                    String uuid=StringUtil.getUuid();
                    String sql =" insert into t_try_again_recruit(id,taskId,tryAgainTime,recruit_state," +
                            " recruit_message,create_time,create_admin)" +
                            " VALUES （";
                    sql +=" '"+uuid+"','"+id+"','"+currenttime+"','"+state+"'," +
                            " '"+message+"','"+currenttime+"','"+userId+"' )";
                    list.add(sql);
                }
            }
            if(!StringUtil.isNullList(list)){
                int n=commonDao.addUpdateDeleteExecute(list);
//                if(n>0){
//                    result=n+"条任务已重试";
//                }else{
//                    result="该任务编辑失败";
//                }
            }

        }catch (Exception e){
            e.printStackTrace();
            result=e.getMessage();
        }


        return result;
    }
}

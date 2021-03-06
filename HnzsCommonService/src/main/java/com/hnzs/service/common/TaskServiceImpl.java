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

import javax.sound.midi.Soundbank;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service("TaskService")
@PropertySource(value = {"classpath:recruit.properties"})
@Transactional
public class TaskServiceImpl implements TaskService {

    @Value("${RecruitStartUrl}")
    private String RecruitStartUrl;

    @Value("${RecruitStopUrl}")
    private String RecruitStopUrl;



    @Autowired
    private CommonDao commonDao;

    @Override
    public HashMap getTaskList(String task_status, String task_type, String task_name,int paseIndex,int paseSize) {
        HashMap hmp=new HashMap();
        try{
            //System.out.println("type:"+task_type);
            // 第一步 先查询出该条件下的总记录数 count(*) 即可
            String tsql="select count(*) as total FROM  ";
            tsql +=" (";
            tsql +=" select id,task_type,task_name,Belong_source,task_start_time," +
                    "task_end_time,task_duration,cycle_period,cycle_end_time," +
                    "is_Transcoding,storage_location,task_status," +
                    "create_admin,insert_time,is_delete " +
                    " from zs_tb_task_detail_info " +
                    " where is_delete !='1'";
            if(!StringUtil.isNull(task_status)){
                //模糊查询
                tsql += " and task_status like'%"+task_status+"%' ";
            }
            if(!StringUtil.isNull(task_type)){
                //模糊查询
                tsql += " and task_type like'%"+task_type+"%' ";
            }
            if(!StringUtil.isNull(task_name)){
                //sql += " and CONCAT(task_name,create_admin) like '%"+task_name+"%'";
                tsql += " and task_name like'%"+task_name+"%' ";
            }
            tsql +=" )a ";
            System.out.println("tsql:"+tsql);
            ArrayList tlist=commonDao.selectExecute(tsql);
            BigInteger total = new BigInteger("0");
            if(tlist.size()>0){
                HashMap map = (HashMap) tlist.get(0);
                total = (BigInteger) map.get("total");
            }
            System.out.println("total:"+total);
            //第二步 查询
            String sql=" select id,task_type,task_name,Belong_source,task_start_time," +
                    "task_end_time,task_duration,cycle_period,cycle_end_time," +
                    "is_Transcoding,storage_location,task_status," +
                    "create_admin,insert_time,is_delete " +
                    " from zs_tb_task_detail_info " +
                    " where is_delete !='1'";
            if(!StringUtil.isNull(task_status)){
                //模糊查询
                sql += " and task_status like'%"+task_status+"%' ";
            }
            if(!StringUtil.isNull(task_type)){
                //模糊查询
                sql += " and task_type like'%"+task_type+"%' ";
            }
            if(!StringUtil.isNull(task_name)){
                //sql += " and CONCAT(task_name,create_admin) like '%"+task_name+"%'";
                sql += " and task_name like'%"+task_name+"%' ";
            }
            System.out.println("sql:"+sql);
            List list=commonDao.selectExecute(sql,paseIndex,paseSize);//查询那一页的数据

            System.out.println("ll:"+list);
            if(!StringUtil.isNullList(list)){
                hmp.put("data", list);
                hmp.put("total", total);
                hmp.put("code", 0);
                hmp.put("msg", "查询成功");
                hmp.put("count", list.size());
            }else{
                hmp.put("data", list);
                hmp.put("total", 0);
                hmp.put("code", 0);
                hmp.put("msg", "查询为空");
                hmp.put("count", 0);
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
    public HashMap deleteTaskByTaskId(String rows,String userId) {
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
                    String taskType="";
                    if(task_type.contains("临时")){
                        taskType="1";
                    }
                    if(task_type.contains("周期")){
                        taskType="2";
                    }
                    if(task_type.contains("7*24")){
                        taskType="3";
                    }
                    taskData.put("taskType", taskType);
                    taskData.put("taskID", id);
                    taskData.put("taskName", task_name);
                    taskData.put("taskBelogFlow", Belong_source);
                    taskData.put("taskStartTime", task_start_time);
                    taskData.put("taskEndTime", task_end_time);
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
                    taskData.put("taskCycleEndTime", cycle_end_time);
                    taskData.put("taskIsTranscode", is_Transcoding);
                    taskData.put("taskStorageLocation", storage_location);

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
                    taskData.put("startXml",sb.toString());

                    //创建关闭收录xml
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append("<? xml version=\"1.0\" ?>");
                    sb1.append("    <transcode>");
                    sb1.append("        <id>"+id+"</id>");
                    sb1.append("        <state>recordstop</state>");
                    sb1.append("    </transcode>");
                    taskData.put("stopXml",sb1.toString());
                    taskData.put("recruitStartUrl",RecruitStartUrl);
                    taskData.put("recruitStopUrl",RecruitStopUrl);
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
                        if(!StringUtil.isNullList(alist)){
                            int n=commonDao.addUpdateDeleteExecute(alist);
                            if(n>0){
                                result="该任务删除成功";
                                hmp.put("data", result);
                                hmp.put("total", 0);
                                hmp.put("code", 0);
                                hmp.put("msg", result);
                                hmp.put("count", 0);
                            }else{
                                result="该任务删除失败";
                                hmp.put("data", result);
                                hmp.put("total", 0);
                                hmp.put("code", 10000);
                                hmp.put("msg", result);
                                hmp.put("count", 0);
                            }

                        }else{
                            result="没有要删除的任务";
                            hmp.put("data", result);
                            hmp.put("total", 0);
                            hmp.put("code", 10000);
                            hmp.put("msg", result);
                            hmp.put("count", 0);
                        }
                    }else{
                        result=msg;
                        hmp.put("data", result);
                        hmp.put("total", 0);
                        hmp.put("code", 10000);
                        hmp.put("msg", result);
                        hmp.put("count", 0);

                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
            result=e.getMessage();
            hmp.put("data", result);
            hmp.put("total", 0);
            hmp.put("code", 10000);
            hmp.put("msg", result);
            hmp.put("count", 0);
        }
        return hmp;
    }

    @Override
    public HashMap editTaskByTaskId(String rows,String userId) {
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
                    System.out.println("task_start_time:"+task_start_time);
                    String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();//任务名称
                    System.out.println("task_end_time:"+task_end_time);
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
                            String taskType1="";
                            if(task_type1.contains("临时")){
                                taskType1="1";
                            }
                            if(task_type1.contains("周期")){
                                taskType1="2";
                            }
                            if(task_type1.contains("7*24")){
                                taskType1="3";
                            }
                            taskData.put("taskType", taskType1);
                            taskData.put("taskID", id);
                            taskData.put("taskName", task_name1);
                            taskData.put("taskBelogFlow", Belong_source1);
                            taskData.put("taskStartTime", task_start_time1);
                            taskData.put("taskEndTime", task_end_time1);
                            //taskData.put("taskCrossDay","0");
                            String cycle1="";
                            if(!StringUtil.isNull(cycle_period1)){
                                if(cycle_period1.contains("一")){
                                    cycle1="1";
                                }

                                if(cycle_period1.contains("二")){
                                    cycle1="2";
                                }
                                if(cycle_period1.contains("三")){
                                    cycle1="3";
                                }
                                if(cycle_period1.contains("四")){
                                    cycle1="4";
                                }
                                if(cycle_period1.contains("五")){
                                    cycle1="5";
                                }if(cycle_period1.contains("六")){
                                    cycle1="6";
                                }
                                if(cycle_period1.contains("日")){
                                    cycle1="7";
                                }
                            }
                            taskData.put("taskCycleTime",cycle1);//周期时间[1,2,3,4,5,6,7]
                            taskData.put("taskCycleEndTime", cycle_end_time1);
                            taskData.put("taskIsTranscode", is_Transcoding1);
                            taskData.put("taskStorageLocation", storage_location1);

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
                            taskData.put("startXml",sb.toString());

                            //创建关闭收录xml
                            StringBuilder sb1 = new StringBuilder();
                            sb1.append("<? xml version=\"1.0\" ?>");
                            sb1.append("    <transcode>");
                            sb1.append("        <id>"+id+"</id>");
                            sb1.append("        <state>recordstop</state>");
                            sb1.append("    </transcode>");
                            taskData.put("stopXml",sb1.toString());
                            taskData.put("recruitStartUrl",RecruitStartUrl);
                            taskData.put("recruitStopUrl",RecruitStopUrl);
                            //String suresult = TasksServer.addTask(JSON.Encode(taskData));
                            String suresult = TasksServer.delTask(JSON.Encode(taskData));
                            System.out.println("sssss:"+suresult);
                            HashMap hmap = (HashMap) JSON.Decode(suresult);
                            String code = hmap.get("code") + "";
                            String msg = hmap.get("msg") + "";
                            if("0".equals(code)){
                                //删除成功之后再进行添加
                                //先调用接口进行判断，再删除
                                HashMap taskData1 = new HashMap();
                                String taskType="";
                                if(task_type.contains("临时")){
                                    taskType="1";
                                }
                                if(task_type.contains("周期")){
                                    taskType="2";
                                }
                                if(task_type.contains("7*24")){
                                    taskType="3";
                                }
                                taskData1.put("taskType", taskType);
                                taskData1.put("taskID", id);
                                taskData1.put("taskName", task_name);
                                taskData1.put("taskBelogFlow", Belong_source);
                                taskData1.put("taskStartTime", task_start_time);
                                taskData1.put("taskEndTime", task_end_time);
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
                                taskData1.put("taskCycleEndTime", cycle_end_time);
                                taskData1.put("taskIsTranscode", is_Transcoding);
                                taskData1.put("taskStorageLocation", storage_location);

                                //创建xml
                                StringBuilder sb11 = new StringBuilder();
                                sb11.append("<? xml version=\"1.0\" ?>");
                                sb11.append("    <record>");
                                sb11.append("        <id>" + id + "</id>");
                                sb11.append("        <state>tryAgainTask</state>");
                                sb11.append("        <callback>http://lyadm.zgynet.cn/wapi/v1/VideoTemplte/CallBack </callback>");
                                sb11.append("        <input>/opt/testggx/1.mp4</input>");
                                sb11.append("        <output>" + storage_location + "</output>");
                                sb11.append("        <name>save</name>");
                                sb11.append("        <format>mp4</format>");
                                sb11.append("        <starttime>" + task_start_time + "</starttime>");
                                sb11.append("        <duration>" + task_duration + "</duration>");
                                sb11.append("        <dividetime>0</dividetime>");
                                sb11.append("        <copy>1</copy>");
                                sb11.append("    </record>");
                                taskData.put("startXml",sb11.toString());

                                //创建关闭收录xml
                                StringBuilder sb12 = new StringBuilder();
                                sb1.append("<? xml version=\"1.0\" ?>");
                                sb1.append("    <transcode>");
                                sb1.append("        <id>"+id+"</id>");
                                sb1.append("        <state>recordstop</state>");
                                sb1.append("    </transcode>");
                                taskData.put("stopXml",sb12.toString());
                                taskData.put("recruitStartUrl",RecruitStartUrl);
                                taskData.put("recruitStopUrl",RecruitStopUrl);
                                //String suresult = TasksServer.addTask(JSON.Encode(taskData));
                                String suresult1 = TasksServer.addTask(JSON.Encode(taskData1));
                                System.out.println("ssasasa:"+suresult1);
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
                                            hmp.put("data", result);
                                            hmp.put("total", 0);
                                            hmp.put("code", 0);
                                            hmp.put("msg", result);
                                            hmp.put("count", 0);
                                        }else{
                                            result="该任务编辑失败";
                                            hmp.put("data", result);
                                            hmp.put("total", 0);
                                            hmp.put("code", 10000);
                                            hmp.put("msg", result);
                                            hmp.put("count", 0);
                                        }

                                    }else{
                                        result="没有要编辑的任务";
                                        hmp.put("data", result);
                                        hmp.put("total", 0);
                                        hmp.put("code", 10000);
                                        hmp.put("msg", result);
                                        hmp.put("count", 0);
                                    }
                                }else{
                                    result=msg1;
                                    hmp.put("data", result);
                                    hmp.put("total", 0);
                                    hmp.put("code", 10000);
                                    hmp.put("msg", result);
                                    hmp.put("count", 0);
                                }
                            }else{
                                result=msg;
                                hmp.put("data", result);
                                hmp.put("total", 0);
                                hmp.put("code", 10000);
                                hmp.put("msg", result);
                                hmp.put("count", 0);
                            }

                        }
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result=e.getMessage();
            hmp.put("data", result);
            hmp.put("total", 0);
            hmp.put("code", 10000);
            hmp.put("msg", result);
            hmp.put("count", 0);
        }
        return hmp;
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
                    HashMap map=HttpRequest.sendPostXml(RecruitStartUrl,sb.toString());
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
                if(n>0){
                    result=n+"条任务已重试";
                }else{
                    result="该任务重试失败";
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            result=e.getMessage();

        }


        return result;
    }


    public static void main(String argsp[]) throws Exception{
        String time="2010-11-20 11:10:10";
        String ttmm="2021-10-27 17:22:00";
        String tttt="2021-10-27 17:22:00";
        Date date=null;
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date=formatter.parse(ttmm);
        System.out.println(date);
        StringBuilder sb1 = new StringBuilder();
        sb1.append("<? xml version=\"1.0\" ?>");
        sb1.append("    <transcode>");
        sb1.append("        <state>recordstop</state>");
        sb1.append("    </transcode>");
        HashMap map=HttpRequest.sendPostXml("https://www.baidu.com",sb1.toString());
        System.out.println("mapp:"+map);
    }
}

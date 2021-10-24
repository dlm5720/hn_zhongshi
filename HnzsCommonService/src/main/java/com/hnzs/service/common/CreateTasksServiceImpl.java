package com.hnzs.service.common;

import com.hnzs.dao.common.CommonDao;
import com.hnzs.util.DateUtils;
import com.hnzs.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    //临时任务创建
    public int CreateTempTask(String jsons) {
        String sql = "";
        int result = 0,ls_temp=0;
        String add_double="";
        ArrayList ayList=new ArrayList();
        try{
            HashMap row = (HashMap) JSON.Decode(jsons);
            String task_name=row.get("task_name")==null?"":row.get("task_name").toString();
            String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();
            String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();
            String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();
            String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();
            //String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();
            //String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();
            String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();
            String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();
            String insert_time= DateUtils.formatDateTimeByDate(new Date());
            String task_status="待收录";
            String task_type="临时任务";
            String uuid=StringUtil.getUuid();
            String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                        "task_start_time,task_end_time,task_duration,is_Transcoding,storage_location,"+
                         "task_status,insert_time)" +
                        " VALUES (";
                sqll += " '"+uuid+" '"+task_type+" '"+task_name+"','"+Belong_source+"','"+task_start_time+"','"+task_end_time+"','" +
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //周期任务创建
    public int CreatePeriodicTask(String jsons) {
        String sql = "";
        int result = 0,ls_temp=0;
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
            String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                    "task_start_time,task_end_time,task_duration,cycle_period,cycle_end_time,is_Transcoding,storage_location,"+
                    "task_status,insert_time)" +
                    " VALUES (";
            sqll += " '"+uuid+" '"+task_type+"','"+Belong_source+"','"+task_start_time+"','"+task_end_time+"','" +
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //7*24任务创建
    public int CreateContinuousTask(String jsons) {
        String sql = "";
        int result = 0,ls_temp=0;
        String add_double="";
        ArrayList ayList=new ArrayList();
        try{
            HashMap row = (HashMap) JSON.Decode(jsons);
            String task_name=row.get("task_name")==null?"":row.get("task_name").toString();
            String Belong_source=row.get("Belong_source")==null?"":row.get("Belong_source").toString();
            String task_start_time=row.get("task_start_time")==null?"":row.get("task_start_time").toString();
           // String task_end_time=row.get("task_end_time")==null?"":row.get("task_end_time").toString();
            //String task_duration=row.get("task_duration")==null?"":row.get("task_duration").toString();
            //String cycle_period=row.get("cycle_period")==null?"":row.get("cycle_period").toString();
            //String cycle_end_time=row.get("cycle_end_time")==null?"":row.get("cycle_end_time").toString();
            String is_Transcoding=row.get("is_Transcoding")==null?"":row.get("is_Transcoding").toString();
            String storage_location=row.get("storage_location")==null?"":row.get("storage_location").toString();
            String insert_time= DateUtils.formatDateTimeByDate(new Date());
            String task_status="待收录";
            String task_type="7*24任务";
            String uuid=StringUtil.getUuid();
            String sqll =" insert into zs_tb_task_detail_info (id,task_type,task_name,Belong_source," +
                    "task_start_time,task_end_time,task_duration,is_Transcoding,storage_location,"+
                    "task_status,insert_time)" +
                    " VALUES (";
            sqll += " '"+uuid+" '"+task_type+" '"+task_name+"','"+Belong_source+"','"+task_start_time+"','" +
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}

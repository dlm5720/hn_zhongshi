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
@Service("RecruitService")
@Transactional
public class RecruitServiceImpl implements RecruitService{

    @Autowired
    private CommonDao commonDao;
    @Override
    public String saveOpenCollectionInfos(HashMap map) {
        ArrayList list=new ArrayList();
        String result="";
        try{
            if(map.size()>0){
                String id=map.get("id")+""; //线程uuid手动关闭/查询时可用到
                String state=map.get("state")+"";//接口名
                String callback=map.get("callback")+"";//消息回调接口
                String input=map.get("input")+"";//输入路径（rtmp，m3u8地址）
                String output=map.get("output")+"";//保存位置
                String name=map.get("name")+"";//必填 流地址没有统一的地址
                String format=map.get("format")+"";//暂时为MP4，方便后期可拓展
                String starttime=map.get("starttime")+"";//开始录制的时间，时间上应小于2037年。0直接开始定时开始格式 YYYYMMDDHHmmss，int
                String duration=map.get("duration")+"";//录制持续的时长： 0不限制，其他指定时间（单位秒，int）
                String dividetime=map.get("dividetime")+"";//单个视频最长时限：0不限制，其他指定时间（单位秒，int）

                String current= DateUtils.formatDateTimeByDate(new Date());

                String uuid=StringUtil.getUuid();
                String sql=" insert into t_open_collection (id,open_id,state," +
                        "callback,input,output,name,format,starttime,duration,dividetime,create_time)" +
                        " VALUES (";
                sql += " '"+uuid+"','"+id+"','"+state+"','"+callback+"','"+input+"','"+output+"'," +
                        "'"+name+"','"+format+"','"+starttime+"','"+duration+"','"+dividetime+"'," +
                        "'"+current+"' )";
                list.add(sql);
                if(!StringUtil.isNullList(list)) {
                    int n = commonDao.addUpdateDeleteExecute(list);
                    if(n>0){
                        result="开启收录数据保存成功";
                    }else{
                        result="开启收录数据保存失败";
                    }
                }else{
                    result="没有要保存的开启收录数据";
                }
            }
        }catch (Exception e){
            result=e.getMessage();
        }
        return result;
    }

    @Override
    public String saveCloseCollectionInfos(HashMap map) {
        ArrayList list=new ArrayList();
        String result="";
        try{
            if(map.size()>0){
                String id=map.get("id")+""; //线程uuid手动关闭/查询时可用到
                String state=map.get("state")+"";//接口名
                String help_para=map.get("help_para")+"";//在关闭的时候帮后台传递标志

                String current= DateUtils.formatDateTimeByDate(new Date());

                String uuid=StringUtil.getUuid();
                String sql=" insert into t_close_collection (id,close_id,state,help_para,create_time)" +
                        " VALUES (";
                sql += " '"+uuid+"','"+id+"','"+state+"','"+help_para +"','"+current+"' )";
                list.add(sql);
                if(!StringUtil.isNullList(list)) {
                    int n = commonDao.addUpdateDeleteExecute(list);
                    if(n>0){
                        result="关闭收录数据保存成功";
                    }else{
                        result="关闭收录数据保存失败";
                    }
                }else{
                    result="没有要保存的关闭收录数据";
                }
            }
        }catch (Exception e){
            result=e.getMessage();
        }
        return result;
    }
}

package com.hnzs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * 此类中封装 解析 gps中 实时位置信息数据
 * 
 * @since 0.1
 */
public class GpsInfo {
	/*
	 * 
	 * 解析 gps中实时位置信息的
	 * 入参:字符串
	 * 样例:[{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 21:01:15, speed=0, canData=null, state=1, alarmExtra=26,51|12770|4.15|100|01001:31:17|210100;D2,6|460|0|14093|23179|146|14093|23173|144|14161|41649|143|14093|23171|139|14093|28453|135|14142|43048|133, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 21:01:17}]
	 * 
	 * 返回:json 字符串
	 * 样例:{gps_gv55=210100, gps_jz_wzdm=14093, lpn=, car_num=13436051193, lon=113631896, gpsTime=2020-03-08 21:01:15, signalState=0, gps_jz_ydgjm=460, speed=0, gps_jz_xqdm=23179, gps_v_dc=4.15, gps_dl_dc=100%, gps_gs=01001:31:17, gps_jz_ydwlm=0, gps_xhqd=51, dir=308, gps_jz_xhqd=146, alt=112, gps_v_wj=12.77, mile=1603, fuel=0, gps_jz_gs=6, lat=34706268, collect_time=2020-03-08 21:01:17}
	 */
	public static String getGPSInfo(String strText) {
		if (strText == null || strText.trim().length() == 0)
			return "";
		HashMap hm=new HashMap();
		strText="["+strText+"]";
		ArrayList rows = (ArrayList) JSON.Decode(strText);
		if(!StringUtil.isNullList(rows)&&rows.size()>0){
    		HashMap row = (HashMap)rows.get(0);
    		//System.out.println(row);
    		/*
    		 * row的数据样例
    		 * {datas=null, errMsg=null, rc=0, info=[{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 19:36:44, speed=0, canData=null, state=1, alarmExtra=26,55|12761|4.15|100|01000:06:47|210100;D2,6|460|0|14093|23173|148|14093|28453|142|14161|41649|142|14093|23171|142|14093|28451|135|14142|43048|134, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 19:36:46}]}
    		 */
    		
    		ArrayList rows2 = (ArrayList) row.get("info");
    		System.out.println("gps数据:"+rows2);
    		if(!StringUtil.isNullList(rows2)&&rows2.size()>0){
        		/*
        		 * 解析gps数据样例
        		 * [{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 19:36:44, speed=0, canData=null, state=1, alarmExtra=26,55|12761|4.15|100|01000:06:47|210100;D2,6|460|0|14093|23173|148|14093|28453|142|14161|41649|142|14093|23171|142|14093|28451|135|14142|43048|134, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 19:36:46}]
        		 */
        		for(int j=0;j<rows2.size();j++){
        			HashMap row2 = (HashMap)rows2.get(j);
          			//System.out.println("------------------------");
        			//车牌号
        			String lpn =  row2.get("lpn")==null?"":row2.get("lpn").toString();
        			hm.put("lpn", lpn);
        			//System.out.println("lpn:"+lpn);
        			//sim卡号
        			String car_num =  row2.get("car_num")==null?"":row2.get("car_num").toString();
        			hm.put("car_num", car_num);
        			//System.out.println("car_num:"+car_num);
        			//gps时间
        			String collect_time =  row2.get("collect_time")==null?"":row2.get("collect_time").toString();
        			hm.put("collect_time", collect_time);
        			//System.out.println("collect_time:"+collect_time);
        			//服务器接收时间
        			String gpsTime =  row2.get("gpsTime")==null?"":row2.get("gpsTime").toString();
        			hm.put("gpsTime", gpsTime);
        			//System.out.println("gpsTime:"+gpsTime);
        			//经度
        			String lon =  row2.get("lon")==null?"0":row2.get("lon").toString();
        			hm.put("lon", lon);
        			//System.out.println("lon:"+lon);
        			//纬度
        			String lat =  row2.get("lat")==null?"0":row2.get("lat").toString();
        			hm.put("lat", lat);
        			//System.out.println("lat:"+lat);
        			//速度
        			String speed =  row2.get("speed")==null?"0":row2.get("speed").toString();
        			hm.put("speed", speed+"km/h");
        			//System.out.println("speed:"+speed);
        			//方向
        			String dir =  row2.get("dir")==null?"":row2.get("dir").toString();
        			hm.put("dir", getDirectionDescr(Integer.parseInt(dir)));
        			//System.out.println("dir:"+dir);
        			//高度
        			String alt =  row2.get("alt")==null?"0":row2.get("alt").toString();
        			hm.put("alt", alt);
        			//System.out.println("alt:"+alt);
        			//公里数
        			String mile =  row2.get("mile")==null?"0":row2.get("mile").toString();
        			hm.put("mile", mile);
        			//System.out.println("mile:"+mile);
        			//油量
        			String fuel =  row2.get("fuel")==null?"0":row2.get("fuel").toString();
        			hm.put("fuel", fuel);
        			//System.out.println("fuel:"+fuel);
        			//行驶记录仪状态 -- 20200308预留
        			String signalState =  row2.get("signalState")==null?"":row2.get("signalState").toString();
        			hm.put("signalState", signalState);
        			//System.out.println("signalState:"+signalState);
        			//System.out.println("------------------------");
        			//---------复杂参数解析-------------
        			//报警状态
        			String alarm =  row2.get("alarm")==null?"0":row2.get("alarm").toString();
        			String alarm_str=getAlarmDescr(alarm);
        			hm.put("alarm", alarm_str);
//        			System.out.println("alarm:"+alarm);
        			//gps状态
        			String state =  row2.get("state")==null?"0":row2.get("state").toString();
        			String state_str=getStatusDescr(state);
        			hm.put("state", state_str);
        			if(!StringUtil.isNull(state_str)){
        				//ACC关|GPS定位|运营|油路正常|电路正常|车门解锁,
        				String [] al_str=state_str.split("\\|");
        				if(al_str.length>0){
        					hm.put("state_acc", al_str[0].replace("ACC", ""));
        				}
        				if(al_str.length>1){
        					hm.put("state_dwfs", al_str[1]);
        				}
        				if(al_str.length>2){
        					hm.put("state_yyzt", al_str[2]);
        				}
        				if(al_str.length>3){
        					hm.put("state_yl", al_str[3]);
        				}
        				if(al_str.length>4){
        					hm.put("state_dl", al_str[4]);
        				}
        				if(al_str.length>5){
        					hm.put("state_cm", al_str[5]);
        				}
        			}
//        			System.out.println("state:"+state);
        			//其他状态参数
        			String alarmExtra = row2.get("alarmExtra")==null?"":row2.get("alarmExtra").toString();
        			System.out.println("--------开始解析alarmExtra数---------");
        			//System.out.println("alarmExtra:"+alarmExtra);
        			if(!StringUtil.isNull(alarmExtra)){
        				String [] ae=alarmExtra.split(";");
        				if(ae.length>0){
        					String ae0=ae[0];
        					System.out.println("ae0:"+ae0);
        					System.out.println("--------开始解析ae0---------");
        					if(!StringUtil.isNull(ae0)&&ae0.length()>0){
        						String[] stb=ae0.split("\\|");
        						//System.out.println("aestr:"+stb[0]);
        						String str_temp="";
        						if(stb.length>0){ str_temp=stb[0]; }//无线通讯网络信号强度
        						if(!StringUtil.isNull(str_temp)&&str_temp.length()>0){
        							String[] t1=str_temp.split(",");
        							if(stb.length>1){ str_temp=t1[1]; }
        							hm.put("gps_xhqd", str_temp);
        						}
        						str_temp="";
        						if(stb.length>1){ str_temp=stb[1]; }//GPS外接电源电压
        						double v_wj=0;
        						if(!StringUtil.isNull(str_temp))
        							v_wj=Double.parseDouble(str_temp)/1000;
        						hm.put("gps_v_wj", v_wj+"V");str_temp="";
        						if(stb.length>2){ str_temp=stb[2]; }//GPS内电池电压
        						hm.put("gps_v_dc", str_temp+"V");str_temp="";
        						if(stb.length>3){ str_temp=stb[3]; }//GPS内电池电量剩余百分比
        						hm.put("gps_dl_dc", str_temp+"%");str_temp="";
        						if(stb.length>4){ str_temp=stb[4]; }//车辆累计工作时间
        						hm.put("gps_gs", str_temp);str_temp="";
        						if(stb.length>5){ str_temp=stb[5]; }//GV55输入输出状态(预留)
        						hm.put("gps_gv55", str_temp);
        					}
        				}
        				if(ae.length>1){
        					String ae1=ae[1];
        					System.out.println("ae1:"+ae1);
        					System.out.println("--------开始解析ae1---------");
        					if(!StringUtil.isNull(ae1)&&ae1.length()>0){
        						String[] stb=ae1.split("\\|");
        						String str_temp="";
        					
        						if(stb.length>0){ str_temp=stb[0]; }//基站个数
        						if(!StringUtil.isNull(str_temp)&&str_temp.length()>0){
        							String[] t1=str_temp.split(",");
        							if(stb.length>1){ str_temp=t1[1]; }
        							hm.put("gps_jz_gs", str_temp);
        						}
        						str_temp="";
        						if(stb.length>1){ str_temp=stb[1]; }//移动国家码
        						hm.put("gps_jz_ydgjm", str_temp);str_temp="";
        						if(stb.length>2){ str_temp=stb[2]; }//移动网络码
        						hm.put("gps_jz_ydwlm", str_temp);str_temp="";
        						if(stb.length>3){ str_temp=stb[3]; }//基站位置代码
        						hm.put("gps_jz_wzdm", str_temp);str_temp="";
        						if(stb.length>4){ str_temp=stb[4]; }//基站小区代码
        						hm.put("gps_jz_xqdm", str_temp);str_temp="";
        						if(stb.length>5){ str_temp=stb[5]; }//基站信号强度
        						hm.put("gps_jz_xhqd", str_temp);
        					}
        				}
        				
        			}
        		}
    		}

		}
		System.out.println("------最后输出结果-----");
		System.out.println(hm.toString());
		
		return JSON.Encode(hm);
	
	}
	
	/*
	 * 
	 * 解析 gps中实时位置信息的
	 * 入参:字符串
	 * 样例:[{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 21:01:15, speed=0, canData=null, state=1, alarmExtra=26,51|12770|4.15|100|01001:31:17|210100;D2,6|460|0|14093|23179|146|14093|23173|144|14161|41649|143|14093|23171|139|14093|28453|135|14142|43048|133, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 21:01:17}]
	 * 
	 * 返回:json 字符串
	 * 样例:{gps_gv55=210100, gps_jz_wzdm=14093, lpn=, car_num=13436051193, lon=113631896, gpsTime=2020-03-08 21:01:15, signalState=0, gps_jz_ydgjm=460, speed=0, gps_jz_xqdm=23179, gps_v_dc=4.15, gps_dl_dc=100%, gps_gs=01001:31:17, gps_jz_ydwlm=0, gps_xhqd=51, dir=308, gps_jz_xhqd=146, alt=112, gps_v_wj=12.77, mile=1603, fuel=0, gps_jz_gs=6, lat=34706268, collect_time=2020-03-08 21:01:17}
	 */
	public static HashMap getGPSInfoAcc(String strText) {
		if (strText == null || strText.trim().length() == 0)
			return null;
		HashMap hm=new HashMap();
		strText="["+strText+"]";
		ArrayList rows = (ArrayList) JSON.Decode(strText);
		if(!StringUtil.isNullList(rows)&&rows.size()>0){
    		HashMap row = (HashMap)rows.get(0);

    		ArrayList rows2 = (ArrayList) row.get("info");
    		if(!StringUtil.isNullList(rows2)&&rows2.size()>0){
        		for(int j=0;j<rows2.size();j++){
        			HashMap row2 = (HashMap)rows2.get(j);

        			//---------复杂参数解析-------------
        			//报警状态
        			String alarm =  row2.get("alarm")==null?"0":row2.get("alarm").toString();
        			String alarm_str=getAlarmDescr(alarm);
        			hm.put("alarm", alarm_str);
//        			System.out.println("alarm:"+alarm);
        			//gps状态
        			String state =  row2.get("state")==null?"0":row2.get("state").toString();
        			String state_str=getStatusDescr(state);
        			//hm.put("state", state_str);
        			if(!StringUtil.isNull(state_str)){
        				//ACC关|GPS定位|运营|油路正常|电路正常|车门解锁,
        				String [] al_str=state_str.split("\\|");
        				if(al_str.length>0){
        					hm.put("state_acc", al_str[0].replace("ACC", ""));
        				}
        				if(al_str.length>1){
        					hm.put("state_dwfs", al_str[1]);
        				}
        				if(al_str.length>2){
        					hm.put("state_yyzt", al_str[2]);
        				}
        				if(al_str.length>3){
        					hm.put("state_yl", al_str[3]);
        				}
        				if(al_str.length>4){
        					hm.put("state_dl", al_str[4]);
        				}
        				if(al_str.length>5){
        					hm.put("state_cm", al_str[5]);
        				}
        			}
        		}
    		}
		}

//		System.out.println("------acc最后输出结果-----");
//		System.out.println(hm.toString());
		return hm;
	
	}
	/*
	 * 
	 * 解析 gps中 车辆累计工时
	 * 入参:字符串
	 * 样例:[{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 21:01:15, speed=0, canData=null, state=1, alarmExtra=26,51|12770|4.15|100|01001:31:17|210100;D2,6|460|0|14093|23179|146|14093|23173|144|14161|41649|143|14093|23171|139|14093|28453|135|14142|43048|133, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 21:01:17}]
	 * 
	 * 返回:long
	 \*/
	public static  long getLjgs(String strText) {
		if (strText == null || strText.trim().length() == 0)
			return 0;
		HashMap hm=new HashMap();
		strText="["+strText+"]";
		String gps_gs="0";
		ArrayList rows = (ArrayList) JSON.Decode(strText);
		System.out.println(rows);
		if(!StringUtil.isNullList(rows)&&rows.size()>0){
    		HashMap row = (HashMap)rows.get(0);
    		//System.out.println(row);
    		/*
    		 * row的数据样例
    		 * {datas=null, errMsg=null, rc=0, info=[{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 19:36:44, speed=0, canData=null, state=1, alarmExtra=26,55|12761|4.15|100|01000:06:47|210100;D2,6|460|0|14093|23173|148|14093|28453|142|14161|41649|142|14093|23171|142|14093|28451|135|14142|43048|134, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 19:36:46}]}
    		 */
    		ArrayList rows2 = (ArrayList) row.get("info");
    		System.out.println("gps数据:"+rows2);
    		if(!StringUtil.isNullList(rows2)){
        		/*
        		 * 解析gps数据样例
        		 * [{car_num=13436051193, lpn=, lon=113631896, signalState=0, gpsTime=2020-03-08 19:36:44, speed=0, canData=null, state=1, alarmExtra=26,55|12761|4.15|100|01000:06:47|210100;D2,6|460|0|14093|23173|148|14093|28453|142|14161|41649|142|14093|23171|142|14093|28451|135|14142|43048|134, alarm=0, dir=308, alt=112, speed1=0, mile=1603, fuel=0, lat=34706268, collect_time=2020-03-08 19:36:46}]
        		 */
        		for(int j=0;j<rows2.size();j++){
        			HashMap row2 = (HashMap)rows2.get(j);
          			//System.out.println("------------------------");
        			//其他状态参数
        			String alarmExtra = row2.get("alarmExtra")==null?"":row2.get("alarmExtra").toString();
        			System.out.println("--------开始解析alarmExtra数---------");
        			//System.out.println("alarmExtra:"+alarmExtra);
        			if(!StringUtil.isNull(alarmExtra)){
        				String [] ae=alarmExtra.split(";");
        				if(ae.length>0){
        					String ae0=ae[0];
        					System.out.println("ae0:"+ae0);
        					System.out.println("--------开始解析ae0---------");
        					if(!StringUtil.isNull(ae0)&&ae0.length()>0){
        						String[] stb=ae0.split("\\|");
        						if(stb.length>4){ gps_gs=stb[4]; }//车辆累计工作时间
        					}
        				}
        			}
        		}
    		}
		}
		System.out.println("------最后输出结果-----");
		long ll_gs=0;
		if(!StringUtil.isNull(gps_gs)&&gps_gs.length()>0){
			String[] stb=gps_gs.split(":");
			if(stb.length>0){ gps_gs=stb[0]; }//车辆累计工作时间
			ll_gs = gps_gs==null?0:Long.parseLong(gps_gs);
		}
		System.out.println(ll_gs);
		
		
		return ll_gs;
	
	}
	
	
	/*
	 * 
	 * 解析 gps中实时位置信息的
	 * 入参:字符串
	   * 样例:[{datas=null, hasMore=true, vehicleGpsList=[{lpn=测D15245, gpsTime=2019-12-07 10:22:46, signalStateStr=, id=6608903438220660738, alarm64=72057594037927936, alt=211, mile=15, speed1=707, fuel=47, tid=null, vid=0, lat=38.904401, direct=24, netState=null, lon=121.637601, signalState=null, speed=298, status=3, numIndex=1, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:23:16, signalStateStr=, id=6608903564053975043, alarm64=72057594037927936, alt=778, mile=24, speed1=520, fuel=47, tid=null, vid=0, lat=38.904401, direct=161, netState=null, lon=121.637601, signalState=null, speed=835, status=3, numIndex=2, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:23:46, signalStateStr=, id=6608903689895677956, alarm64=72057594037927936, alt=280, mile=27, speed1=827, fuel=47, tid=null, vid=0, lat=38.904401, direct=79, netState=null, lon=121.637601, signalState=null, speed=979, status=3, numIndex=3, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:24:16, signalStateStr=, id=6608903815724797957, alarm64=72057594037927936, alt=565, mile=33, speed1=419, fuel=47, tid=null, vid=0, lat=38.904401, direct=387, netState=null, lon=121.637601, signalState=null, speed=738, status=3, numIndex=4, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:24:46, signalStateStr=, id=6608903941558112262, alarm64=72057594037927936, alt=102, mile=34, speed1=683, fuel=46, tid=null, vid=0, lat=38.904401, direct=243, netState=null, lon=121.637601, signalState=null, speed=293, status=3, numIndex=5, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:25:16, signalStateStr=, id=6608904067395620871, alarm64=72057594037927936, alt=257, mile=35, speed1=417, fuel=46, tid=null, vid=0, lat=38.904401, direct=343, netState=null, lon=121.637601, signalState=null, speed=410, status=3, numIndex=6, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:25:46, signalStateStr=, id=6608904193228935176, alarm64=72057594037927936, alt=909, mile=37, speed1=696, fuel=46, tid=null, vid=0, lat=38.904401, direct=28, netState=null, lon=121.637601, signalState=null, speed=269, status=3, numIndex=7, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:26:16, signalStateStr=, id=6608904319066443785, alarm64=72057594037927936, alt=474, mile=44, speed1=357, fuel=45, tid=null, vid=0, lat=38.904401, direct=154, netState=null, lon=121.637601, signalState=null, speed=159, status=3, numIndex=8, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:26:46, signalStateStr=, id=6608904444899758090, alarm64=72057594037927936, alt=828, mile=53, speed1=636, fuel=45, tid=null, vid=0, lat=38.904401, direct=192, netState=null, lon=121.637601, signalState=null, speed=6, status=3, numIndex=9, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}, {lpn=测D15245, gpsTime=2019-12-07 10:27:16, signalStateStr=, id=6608904570728878091, alarm64=72057594037927936, alt=780, mile=62, speed1=837, fuel=44, tid=null, vid=0, lat=38.904401, direct=60, netState=null, lon=121.637601, signalState=null, speed=952, status=3, numIndex=10, alarmName=null, did=null, attr=null, alarm=0, loadNum=null, attrName=null, posit=null}], totalCount=0, numIndex=10, errMsg=null, lastTime=2019-12-07 10:27:16, rc=0}]
 
	 * 返回:json 字符串
	 * 样例:[{lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:22:46, signalState=, speed=298km/h, numIndex=1, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=东偏北24度, alt=211, mile=15, fuel=47, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:23:16, signalState=, speed=835km/h, numIndex=2, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=西偏北19度, alt=778, mile=24, fuel=47, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:23:46, signalState=, speed=979km/h, numIndex=3, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=东偏北79度, alt=280, mile=27, fuel=47, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:24:16, signalState=, speed=738km/h, numIndex=4, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=, alt=565, mile=33, fuel=47, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:24:46, signalState=, speed=293km/h, numIndex=5, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=西偏南63度, alt=102, mile=34, fuel=46, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:25:16, signalState=, speed=410km/h, numIndex=6, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=东偏南17度, alt=257, mile=35, fuel=46, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:25:46, signalState=, speed=269km/h, numIndex=7, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=东偏北28度, alt=909, mile=37, fuel=46, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:26:16, signalState=, speed=159km/h, numIndex=8, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=西偏北26度, alt=474, mile=44, fuel=45, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:26:46, signalState=, speed=6km/h, numIndex=9, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=西偏南12度, alt=828, mile=53, fuel=45, lat=38.904401}, {lpn=测D15245, car_num=15200000045, lon=121.637601, gpsTime=2019-12-07 10:27:16, signalState=, speed=952km/h, numIndex=10, state=ACC关|基站定位|运营|油路正常|电路正常|车门解锁, alarm=, dir=东偏北60度, alt=780, mile=62, fuel=44, lat=38.904401}]
	 */
	public static String getGPSInfoHistory(String strText,String car_num) {
		if (strText == null || strText.trim().length() == 0)
			return "";
		LinkedList  alist = new LinkedList ();
		strText="["+strText+"]";
		ArrayList rows = (ArrayList) JSON.Decode(strText);
		System.out.println(rows);
		if(!StringUtil.isNullList(rows)&&rows.size()>0){
    		HashMap row = (HashMap)rows.get(0);
    		//System.out.println(row);

    		ArrayList rows2 = (ArrayList) row.get("vehicleGpsList");
    		System.out.println("gps历史数据:"+rows2);
    		if(!StringUtil.isNullList(rows2)){

        		for(int j=0;j<rows2.size();j++){
        			HashMap hm=new HashMap();
        			
        			HashMap row2 = (HashMap)rows2.get(j);
          			//System.out.println("------------------------");
        			//车牌号
        			String numIndex =  row2.get("numIndex")==null?"0":row2.get("numIndex").toString();
        			hm.put("numIndex", numIndex);
        			//车牌号
        			String lpn =  row2.get("lpn")==null?"":row2.get("lpn").toString();
        			hm.put("lpn", lpn);
        			//System.out.println("lpn:"+lpn);
        			//sim卡号
        			hm.put("car_num", car_num);
        			//System.out.println("car_num:"+car_num);
        			//服务器接收时间
        			String gpsTime =  row2.get("gpsTime")==null?"":row2.get("gpsTime").toString();
        			hm.put("gpsTime", gpsTime);
        			//System.out.println("gpsTime:"+gpsTime);
        			//经度
        			String lon =  row2.get("lon")==null?"0":row2.get("lon").toString();
        			hm.put("lon", lon);
        			//System.out.println("lon:"+lon);
        			//纬度
        			String lat =  row2.get("lat")==null?"0":row2.get("lat").toString();
        			hm.put("lat", lat);
        			//System.out.println("lat:"+lat);
        			//速度
        			String speed =  row2.get("speed")==null?"0":row2.get("speed").toString();
        			hm.put("speed", speed+"km/h");
        			//System.out.println("speed:"+speed);
        			//方向
        			String dir =  row2.get("direct")==null?"0":row2.get("direct").toString();
        			
        			hm.put("dir", getDirectionDescr(Integer.parseInt(dir)));
        			//System.out.println("dir:"+dir);
        			//高度
        			String alt =  row2.get("alt")==null?"0":row2.get("alt").toString();
        			hm.put("alt", alt);
        			//System.out.println("alt:"+alt);
        			//公里数
        			String mile =  row2.get("mile")==null?"0":row2.get("mile").toString();
        			hm.put("mile", mile);
        			//System.out.println("mile:"+mile);
        			//油量
        			String fuel =  row2.get("fuel")==null?"0":row2.get("fuel").toString();
        			hm.put("fuel", fuel);
        			//System.out.println("fuel:"+fuel);
        			//行驶记录仪状态 -- 20200308预留
        			String signalState =  row2.get("signalState")==null?"":row2.get("signalState").toString();
        			hm.put("signalState", signalState);
        			//System.out.println("signalState:"+signalState);
        			//System.out.println("------------------------");
        			//---------复杂参数解析-------------
        			//报警状态
        			String alarm =  row2.get("alarm")==null?"0":row2.get("alarm").toString();
        			String alarm_str=getAlarmDescr(alarm);
        			hm.put("alarm", alarm_str);
//        			System.out.println("alarm:"+alarm);
        			//gps状态
        			String state =  row2.get("STATUS")==null?"0":row2.get("STATUS").toString();
        			String state_str=getStatusDescr(state);
        			hm.put("state", state_str);
        			if(!StringUtil.isNull(state_str)){
        				//ACC关|GPS定位|运营|油路正常|电路正常|车门解锁,
        				String [] al_str=state_str.split("\\|");
        				if(al_str.length>0){
        					hm.put("state_acc", al_str[0].replace("ACC", ""));
        				}
        				if(al_str.length>1){
        					hm.put("state_dwfs", al_str[1]);
        				}
        				if(al_str.length>2){
        					hm.put("state_yyzt", al_str[2]);
        				}
        				if(al_str.length>3){
        					hm.put("state_yl", al_str[3]);
        				}
        				if(al_str.length>4){
        					hm.put("state_dl", al_str[4]);
        				}
        				if(al_str.length>5){
        					hm.put("state_cm", al_str[5]);
        				}
        			}
//        			System.out.println("state:"+state);
        			
        			alist.add(hm);
        		}
    		}

		}
		
		
		
		
		System.out.println("------最后输出结果-----");
		System.out.println(alist.toString());
		
		return JSON.Encode(alist);
	
	}
	
	
	//车辆方向转化
	public static String getDirectionDescr(int direction) {
		String descr = "";
		if (direction == 0) {
			descr = "正东";
		} else if (direction == 90) {
			descr = "正北";
		} else if (direction == 180) {
			descr = "正西";
		} else if (direction == 270) {
			descr = "正南";
		} else if (direction == 45) {
			descr = "东北";
		} else if (direction == 135) {
			descr = "西北";
		} else if (direction == 225) {
			descr = "西南";
		} else if (direction == 315) {
			descr = "东南";
		} else if (direction < 90) {
			descr = "东偏北" + direction + "度";
		} else if (direction > 90 && direction < 180) {
			descr = "西偏北" + (180 - direction) + "度";
		} else if (direction > 180 && direction < 270) {
			descr = "西偏南" + (direction - 180) + "度";
		} else if (direction > 270 && direction < 360) {
			descr = "东偏南" + (360 - direction) + "度";
		}
		return descr;
	}

	//处理车辆状态
	public static String getAlarmDescr(String alarm) {
		//十进制转换为32位二进制
		alarm = toBin(Integer.parseInt(alarm));
		//开始编码转换
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNullOrEmpty(alarm) == false) {
			char[] ch = alarm.toCharArray();
			if (ch.length == 32) {
				int m = 31;
				int c = ch[m - 0] - 48;
				sb.append(c == 1 ? "紧急报瞥触动报警开关后触发" : "").append("|");
				c = ch[m - 1] - 48;
				sb.append(c == 1 ? "超速报警" : "").append("|");
				c = ch[m - 2] - 48;
				sb.append(c == 1 ? "疲劳驾驶" : "").append("|");
				c = ch[m - 3] - 48;
				sb.append(c == 1 ? "预警" : "").append("|");
				c = ch[m - 4] - 48;
				sb.append(c == 1 ? "GNSS模块发生故障" : "").append("|");
				c = ch[m - 5] - 48;
				sb.append(c == 1 ? "GNSS天线未接或被剪断" : "").append("|");
				
				c = ch[m - 6] - 48;
				sb.append(c == 1 ? "GNSS天线短路" : "").append("|");
				c = ch[m - 7] - 48;
				sb.append(c == 1 ? "终端主电源欠压" : "").append("|");
				c = ch[m - 8] - 48;
				sb.append(c == 1 ? "终端主电源掉电" : "").append("|");
				c = ch[m - 9] - 48;
				sb.append(c == 1 ? "终端LCD或显示器故障" : "").append("|");
				c = ch[m - 10] - 48;
				sb.append(c == 1 ? "TTS模块故障" : "").append("|");
				c = ch[m - 11] - 48;
				sb.append(c == 1 ? "摄像头故障" : "").append("|");
				
				
				c = ch[m - 18] - 48;
				sb.append(c == 1 ? "当天累计驾驶超时" : "").append("|");
				c = ch[m - 19] - 48;
				sb.append(c == 1 ? "超时停车" : "").append("|");
				c = ch[m - 20] - 48;
				sb.append(c == 1 ? "进出区域" : "").append("|");
				c = ch[m - 21] - 48;
				sb.append(c == 1 ? "进出路线" : "").append("|");
				c = ch[m - 22] - 48;
				sb.append(c == 1 ? "路段行驶时间不足/过长" : "").append("|");
				c = ch[m - 23] - 48;
				sb.append(c == 1 ? "路线偏离报警" : "").append("|");
				c = ch[m - 24] - 48;
				sb.append(c == 1 ? "车辆VSS故障" : "").append("|");
				c = ch[m - 25] - 48;
				sb.append(c == 1 ? "车辆油量异常" : "").append("|");
				c = ch[m - 26] - 48;
				sb.append(c == 1 ? "车辆被盗(通过车辆防盗器)" : "").append("|");
				c = ch[m - 27] - 48;
				sb.append(c == 1 ? "车辆非法点火" : "").append("|");
				c = ch[m - 28] - 48;
				sb.append(c == 1 ? "车辆非法位移" : "").append("|");

			}
		}

		String result=sb.toString().replace("||", "");
		if("|".equals(result)) result="";
		if(result.length()>0){
			//去除第一个|
			if("|".equals(result.substring(0,1))){ 
				result=result.substring(1,result.length());
//				System.out.println(result);
			}
		}
		if(result.length()>0){
			//去除最后一个|
			if("|".equals(result.substring(result.length()-1,result.length()))){ 
				result=result.substring(0,result.length()-1);
//				System.out.println(result);
			}
		}
		return result;
	}

	

	
	//处理车辆状态
	public static String getStatusDescr(String status) {
		//十进制转换为32位二进制
		status = toBin(Integer.parseInt(status));
		//开始编码转换
		StringBuilder sb = new StringBuilder();
		if (StringUtil.isNullOrEmpty(status) == false) {
			char[] ch = status.toCharArray();
			if (ch.length == 32) {
				int m = 31;
				int c = ch[m - 0] - 48;
				sb.append(c == 1 ? "ACC开" : "ACC关").append("|");
				c = ch[m - 1] - 48;
				sb.append(c == 1 ? "GPS定位" : "基站定位").append("|");
				c = ch[m - 4] - 48;
				sb.append(c == 1 ? "停运" : "运营").append("|");
				c = ch[m - 10] - 48;
				sb.append(c == 1 ? "油路断开" : "油路正常").append("|");
				c = ch[m - 11] - 48;
				sb.append(c == 1 ? "电路断开" : "电路正常").append("|");
				c = ch[m - 12] - 48;
				sb.append(c == 1 ? "车门加锁" : "车门解锁").append("|");
			}
		}
		String result=sb.toString().replace("||", "");
		if("|".equals(result)) result="";
		if(result.length()>0){
			//去除第一个|
			if("|".equals(result.substring(0,1))){ 
				result=result.substring(1,result.length());
//				System.out.println(result);
			}
		}
		if(result.length()>0){
			//去除最后一个|
			if("|".equals(result.substring(result.length()-1,result.length()))){ 
				result=result.substring(0,result.length()-1);
//				System.out.println(result);
			}
		}
		return result;
	}
	//十进制转32位二进制
    public static String toBin(int num) {
        char[] chs = new char[Integer.SIZE];
        for (int i = 0; i < Integer.SIZE; i++) {
            chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
        }
        return new String(chs);
    }
    
}

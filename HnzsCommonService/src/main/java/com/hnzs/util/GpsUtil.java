package com.hnzs.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 此类中封装一些常用的字符串操作。 所有方法都是静态方法，不需要生成此类的实例， 为避免生成此类的实例，构造方法被申明为private类型的。
 * 
 * @since 0.1
 */
public class GpsUtil {
	private static String strChineseFirstPY = "";// 内容太长请到http://jshuwei.org.cn上下载

	/**
	 * 计算百分数
	 */
	public static HashMap getGpsDataAnalysis(HashMap map1,ArrayList shoplist) {
    			//HashMap map1 = (HashMap)alist.get(j);
    			String simno=map1.get("sim").toString()!=null  ? map1.get("sim").toString() : "";//sim卡号
    			String dw_time=map1.get("gps_time").toString()!=null  ? map1.get("gps_time").toString() : "";//定位时间
    			String js_time=map1.get("ct").toString()!=null  ? map1.get("ct").toString() : "";//服务器接收时间
    			//f服务器接收时间与当前时间相比大于12分钟则为离线状态
    			//服务器时间 2019-12-13 15:07:16
    			//当前时间
    			Date collecttime= DateUtils.StrToDate2(js_time,"yyyy-MM-dd HH:mm:ss");
    			Date newDate=new Date();
    			long diff = newDate.getTime() - collecttime.getTime();// 这样得到的差值是微秒级别
    			long minutes = diff / (1000 * 60);//相差的分钟数
    			String onlinestate="";//在线状态
    			if(minutes>12){
    				onlinestate="离线";
    			}else{
    				onlinestate="在线";
    			}
    			String mile=map1.get("mile").toString()!=null  ? map1.get("mile").toString() : "";//里程
    			String lon=map1.get("lon").toString()!=null  ? map1.get("lon").toString() : "";//经度
    			String lat=map1.get("lat").toString()!=null  ? map1.get("lat").toString() : "";//维度
    			String jwd="经度:"+lon+",维度:"+lat;
    			//System.out.println("sss:"+String.valueOf(map1.get("alarmExtra")));
    			String alarmTxt=String.valueOf(map1.get("alarm_extra"))!=null ? String.valueOf(map1.get("alarm_extra")) : "";
    			String gps_out_voltage="";//GPS外接电源电压
    			String gps_in_voltage="";//GPS内电池电压
    			String gps_battery="";//GPS内电池电量剩余百分比
    			String gps_work_time="";//车辆累计工作时间
    			String acc_state="";//acc状态 
    			String lock_car_state="";//锁车状态
    			String country_code="";//基站国家码
    			String network_code="";//移动网络码
    			String location_code="";//基站位置码
    			String area_code="";//基站小区码
    			String xh_code="";//基站信号强度
    			if(!StringUtil.isNull(alarmTxt)){
    				String[] gpsdatas=alarmTxt.split(";");
    				//26,18|12068|4.10|95|00007:46:53|210100;D2,1|460|0|9375|19533|113
	    			for(int t=0;t<gpsdatas.length;t++){
	    				String str=gpsdatas[t];
	    				String index=str.substring(0,str.indexOf(","));
	    				//System.out.println("lin:"+str.indexOf(","));
	    				if("26".equals(index)){
	    					String str2=str.substring(str.indexOf(",")+1);//GPS电压信息
	    					//解析18|12068|4.10|95|00007:46:53|210100
	    					//System.out.println("ssss:"+str2.indexOf("|"));
	    					str2=str2.replace("|", ",");
	    					String[] dys=str2.split(",");
	    					String xhqd=dys[0].toString();//BYTE 无线通讯网络信号强度
	    					gps_out_voltage=dys[1].toString();//GPS外接电源电压
	    					gps_in_voltage=dys[2].toString();//GPS内电池电压
	    					gps_battery=dys[3].toString();//GPS内电池电量剩余百分比
	    					gps_work_time=dys[4].toString();//车辆累计工作时间
	    					String accs=dys[5].toString();//210100状态。acc状态 
	    					//中间两位为ACC状态 00和02 是关，01和03是开
	    					String acc=accs.substring(2,4);
	    					if("00".equals(acc)||"02".equals(acc)){
	    						acc_state="关";
	    					}else if("01".equals(acc)||"03".equals(acc)){
	    						acc_state="开";
	    					}
	    					
	    				}
	    				if("D2".equals(index)){
	    					String str3=str.substring(str.indexOf(",")+1);//基站信息
	    					//解析  1|460|0|9375|19533|113
	    					//D2,6|460|0|14161|41649|140|14161|41647|137|14093|28453|132|14161|45792|129|14093|23171|128|14093|23173|125
	    					String[] jzinfo=str3.replace("|", ",").split(",");
	    					int n=Integer.parseInt(jzinfo[0].toString());//基站个数
	    					country_code=jzinfo[1].toString();//基站国家码
	    					if("460".equals(country_code)){
	    						country_code=country_code+"中国";
	    					}
	    					network_code=jzinfo[2].toString();//移动网络码  00:中国移动，01:中国联通,02:中国移动,03:中国电信,04:中国卫通,05:中国电信(CDMA2000)06:中国联通(UMTS 2100),07:中国移动(TD-SCDMA),20:中国铁通
	    					if(n>0){
	    						for(int k=3;k<(n+1)*3;k++){
    	    						if(k % 3 == 0){
    	    							location_code +=jzinfo[k].toString()+",";
    	    						}
    	    						if(k % 3 == 1){
    	    							area_code +=jzinfo[k].toString()+",";//基站小区码
    	    						}
    	    						if(k % 3 == 2){
    	    							xh_code +=jzinfo[k].toString()+",";//基站小区码
    	    						}
    	    					}
    	    					location_code=location_code.substring(0,location_code.lastIndexOf(","));//基站位置码
    	    					area_code = area_code.substring(0,area_code.lastIndexOf(","));//基站小区码
    	    					xh_code=xh_code.substring(0,xh_code.lastIndexOf(","));//基站信号强度
	    					}
	    				}
	    			}
    			}
    			int vehicle_state=Integer.parseInt(map1.get("state").toString()!=null  ? map1.get("state").toString() : "");//锁车状态808编码
    			String v_state=StringUtil.binaryToDecimal(vehicle_state);//32位
    			String yl_status=v_state.substring(v_state.length()-10, v_state.length()-9);//车辆油路状态 0:车辆油路正常:1:车辆油路断开   第10位
    			if("0".equals(yl_status)){
    				yl_status="车辆油路正常";
    			}else{
    				yl_status="车辆油路断开";
    			}
    			String dl_status=v_state.substring(v_state.length()-11, v_state.length()-10);//车辆电路状态 0:车辆电路正常:1:车辆电路断开   第11位
    			if("0".equals(dl_status)){
    				dl_status="车辆电路正常";
    			}else{
    				dl_status="车辆电路断开 ";
    			}
    			String lock_status=v_state.substring(v_state.length()-12, v_state.length()-11);//车门锁机状态 0:车门解锁；1：车门加锁  第十二位
    			if("0".equals(lock_status)){
    				lock_status="车门解锁";
    			}else{
    				lock_status="车门加锁 ";
    			}
    			
    			String location_status=v_state.substring(v_state.length()-2, v_state.length()-1);//定位状态 0:0:未定位;1:定位  第二位
    			if("0".equals(location_status)){
    				location_status="未定位";
    			}else{
    				location_status="定位 ";
    			}
    			
    			String acc_status=v_state.substring(v_state.length()-1, v_state.length());//ACC状态 0: ACC关;1:ACC开 第一位
    			if("0".equals(acc_status)){
    				acc_status="关";
    			}else{
    				acc_status="开 ";
    			}
    			//根据sim卡号获取门店信息
    			String shop_name="";//门店信息
    			String vehicle_no="";//车牌号
    			String equipment_no="";//设备号
    			//ArrayList shoplist=getShopBySimNo(simno);
    			if(shoplist.size()>0){
    				HashMap smap=(HashMap) shoplist.get(0);
	    			shop_name=smap.get("shop_name").toString();
	    			vehicle_no=smap.get("vehicle_no").toString();
	    			equipment_no=smap.get("vehicle_xlh").toString();
    			}
    			HashMap nmap=new HashMap();
    			nmap.put("vehicle_no", vehicle_no);
    			nmap.put("equipment_no", equipment_no);
    			nmap.put("sim_no", simno);
    			nmap.put("shop_name", shop_name);
    			nmap.put("jwd", jwd);
    			nmap.put("dw_time", dw_time);
    			nmap.put("js_time", js_time);
    			nmap.put("gps_out_voltage", gps_out_voltage);
    			nmap.put("gps_in_voltage", gps_in_voltage);
    			nmap.put("gps_battery", gps_battery);
    			nmap.put("gps_work_time", gps_work_time);
    			if(StringUtil.isNull(acc_state)){
    				nmap.put("acc_state", acc_status);
    			}else{
    				nmap.put("acc_state", acc_state);
    			}
    			
    			nmap.put("lock_car_state", lock_car_state);
    			nmap.put("country_code", country_code);
    			nmap.put("network_code", network_code);
    			nmap.put("location_code", location_code);
    			nmap.put("area_code", area_code);
    			nmap.put("mile", mile);
    			nmap.put("yl_status", yl_status);
    			nmap.put("dl_status", dl_status);
    			nmap.put("lock_car_state", lock_status);
    			nmap.put("location_status", location_status);
    			nmap.put("online_state", onlinestate);
    			
    			//list.add(nmap);
		return nmap;
	}

	
	
    public static void main(String[] args) {
//    	String aa = "{\"fbrandName\":\"茅台\",\"fbrief\":\"酱香酒\","
//    			+ "\"fatrr\":[{\"color\":\"red\",\"size\":\"5m\"}],\"fsku\" :[{\"version\":\"1.0\",\"color\":\"red\"}],"
//    			+ "\"forderId\":\"22321232323\",\"cost\":\"1200\"}";
//    		System.out.println(aa);		
//    	String sendPost = sendPost("http://admin.kemei.henancatv.com/admin/api/createGoods", aa);
    	//String sendPost = sendPost("http://127.0.0.1:8081/mongo3/springmvc/hello/name", "");
    	//System.out.println(sendPost);
	}
	
}

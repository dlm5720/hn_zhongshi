package com.hnzs.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 * 地图工具类，计算两个经纬度之间的直线距离
 * @author 杨轩
 *
 */
public class MapUtil {
	//地球半径
	private static double EARTH_RADIUS = 6371;  
	//角度转换成弧度
    private static double rad(double d){  
    	return d * Math.PI / 180.0;  
    }  
  
    /** 
     * 计算两个经纬度之间的距离 
     * @param lng1 经度
     * @param lat1 纬度
     * @param lng2 目标经度
     * @param lat2 目标纬度
     * @return 距离
     */  
    public static double getDistance(double lng1, double lat1, double lng2, double lat2){  
    	//角度转换为弧度
    	double radLat1 = rad(lat1);  
    	double radLat2 = rad(lat2);
    	double radLng1 = rad(lng1);  
    	double radLng2 = rad(lng2);  
    	double a = radLat1 - radLat2;//两点纬度之差    
    	double b = radLng1 - radLng2;//两点经度之差  
    	double s = 2 * Math.asin(Math.sqrt(Math.abs(Math.pow(Math.sin(a/2),2) +   
    			Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))));  
    	s = s * EARTH_RADIUS;//弧长*地球半径 (千米)
    	//s = Math.round(s * 1000);  
    	return s;  
    }
    
    @SuppressWarnings("rawtypes")
	public static List getBestVehicleShopList(List<String> list,int typeCount){
		List<String> list2 = new ArrayList<>();
	    Map<String, Integer> map = new HashMap<>();
		int ct = 2;
	    //如果有多重设备，重复的加入list2集合
	    for (int i = 0; i < list.size(); i++) {
	      for (int j = i + 1; j < list.size(); j++) {
	        if (list.get(i).equals(list.get(j))) {
	          list2.add(list.get(i));
	          break;
	        }
	      }
	    }
	    if(list2.size()==0){
	    	list2 = list;
	    	ct = 1;
	    }
	    //System.out.println(list2);
	    //统计list2集合中重复数据出现次数,对应放入Map集合
	    for (String obj : list2) {
	      if (map.containsKey(obj)) {
	        map.put(obj, map.get(obj) + 1);
	      } else {
	        map.put(obj, ct);
	      }
	    }
	    list2.clear();
	    //int count = 0;
	    Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry<String, Integer> entry = it.next();
	    	Integer value = entry.getValue();
	    	String key = entry.getKey();
	    	if (value == typeCount) {
	    		list2.add(key);
	    		//count = value;
	    		//String result =String.format("%s 出现的次数是 %d 次",key,value);
	    		//System.out.println(result);
	    	}
	    }
	    if(list2.size() == 0){
	    	//System.out.println("设备类型一共有:" + typeCount + "种，没有能满足合同条件的门店。");
	    }
	    if (list2.size() > 1) {
			//System.out.println("设备类型一共有:" + count + "种，满足条件的门店有多个" + list2);
		}
		if (list2.size() == 1) {
			//System.out.println("设备类型一共有:" + count + "种，满足条件的门店是:" + list2);
		}
		return list2;
	}
	
	public static String convertListToString(List<String> strlist){
		StringBuffer sb = new StringBuffer();
			for (int i=0;i<strlist.size();i++) {
				if(i==0){
					sb.append("'").append(strlist.get(i)).append("'");
				}else{
					sb.append(",").append("'").append(strlist.get(i)).append("'");
				}
			}
		return sb.toString();
	}
	
	public static double round(double v,int scale){         
		if(scale<0){         
			throw new IllegalArgumentException("The scale must be a positive integer or zero");         
		}         
		BigDecimal b = new BigDecimal(Double.toString(v));         
		BigDecimal one = new BigDecimal("1");         
		return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();         
	} 
	
	// 传一个json 对象数组 和 一个要排序的值,升序
	@SuppressWarnings("rawtypes")
	public static JSONArray sortJSONArray(ArrayList datajson,String sortStr) {
		JSONArray jsonArr = JSONArray.fromObject(datajson);
		JSONObject jObject = null;
		String istr="",jstr="";
		long l=0,nl=0;
		for (int i = 0; i < jsonArr.size(); i++) {
			for (int j = i + 1; j < jsonArr.size(); j++) {
				istr=jsonArr.getJSONObject(i).get(sortStr).toString();
				if(istr.indexOf("%")>0||istr.indexOf(".")>0) {
					istr=istr.replace("%", "");
					istr=istr.replace(".", "");
				}
				l = Long.parseLong(istr);
				jstr=jsonArr.getJSONObject(j).get(sortStr).toString();
				if(jstr.indexOf("%")>0||jstr.indexOf(".")>0){
					jstr=jstr.replace("%", "");
					jstr=jstr.replace(".", "");
				}
				nl = Long.parseLong(jstr);
				if (l > nl) {
					jObject = jsonArr.getJSONObject(j);
					jsonArr.set(j, jsonArr.getJSONObject(i));
					jsonArr.set(i, jObject);
				}else{
				}
			}
		}
		//设置排序号
		for (int i = 0; i < jsonArr.size(); i++) {
			jsonArr.getJSONObject(i).remove("sort_num");
			jsonArr.getJSONObject(i).put("sort_num", i+1);
		}
		return jsonArr;
	}
}

package com.hnzs.util;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class ObjectTransformationUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Utils.setFiled(json, instance_bean, doMethod, param, value);
		
		Map<String,String> mapped=new HashMap<String,String>();
		mapped.put("tvn", "tvnbn");
		mapped.put("bn", "tvnbn");
		mapped.put("productCode", "productId");
		mapped.put("orderTime", "orderTime");
		mapped.put("effectiveTime", "startTime");
		mapped.put("failureTime", "endTime");
		mapped.put("operatorId", "operatorId");
		mapped.put("openId", "operatorId");
		mapped.put("sourceSystemId", "sourceFlag");
		
		JSONObject x=JSONObject.fromObject(mapped);
		System.out.println(x.toString());
		
		String request="{\"tvn\":\"20008d\",\"bn\":\"\",\"productCode\":\"productcode\",\"effectiveTime\":\"2015-06-09 10:19:11\",\"failureTime\":\"2015-06-09 10:19:11\",\"operatorId\":\"operatorid\",\"openId\":\"openid\",\"sourceSystemId\":\"sourcesystemid\"}";
		/**		
		Map<String,String> o=Utils.change(request);
		if(o!=null){
			OdTbProductOrderSubLog t=new OdTbProductOrderSubLog();
			Set s=o.keySet();
			Iterator i=s.iterator();
			while(i.hasNext()){
				String k=i.next().toString();
				Class[] c=new Class[1];
				c[0]=String.class;
				Object[] v=new Object[1];
				v[0]=o.get(k).toString();
				String f=mapped.get(k);
				String mf=f.substring(0,1);
				String me=f.substring(1);
				doSet(t,"set"+mf.toUpperCase()+me,c,v);
			}
			
			System.out.println(t);
		}
		*/
		/**
		OdTbProductOrderSubLog t=new OdTbProductOrderSubLog();
		Object o=setFiled(mapped,t,request);
		System.out.println(o);
		*/
	}
	
	public static void write(String filename,String char_code,String text){
		try{
			FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter osw = new OutputStreamWriter(fos, char_code);
			
			osw.write(text);
			osw.flush();
			osw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String mapToJson(Map<Object,Object> dataMap){
		try{
			JSONObject o=JSONObject.fromObject(dataMap);
			return o.toString();
		}
		catch(Exception e){
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static Map<Object,Object> jsonToMap(String json){
		JSONObject o=JSONObject.fromObject(json);
		return (Map<Object,Object>)o;
	}
	@SuppressWarnings("unchecked")
	public static Map<Object,Object> xmlToJson(String xml){
		try{
			JSONObject json= (JSONObject) new XMLSerializer().read(xml);
			return json;
		}
		catch(Exception e){	
		}
		return null;
	}
	
	public static Object setFiled(Map<String,String> mapped,Object bean,String json){
		Map<String,String> o= ObjectTransformationUtils.change(json);
		if(o!=null){
			Set s=o.keySet();
			Iterator i=s.iterator();
			while(i.hasNext()){
				String k=i.next().toString();
				Class[] c=new Class[1];
				c[0]=String.class;
				Object[] v=new Object[1];
				v[0]=o.get(k).toString();
				String f=mapped.get(k);
				String mf=f.substring(0,1);
				String me=f.substring(1);
				doSet(bean,"set"+mf.toUpperCase()+me,c,v);
			}
			//System.out.println(bean);
			return bean;
		}
		else{
			return null;
		}
	}
	
	public static Map<String,String> change(String json){
		try{
			Map<String,String> map=new HashMap<String, String>();
			JSONObject o=JSONObject.fromObject(json);
			Set<?> s=o.keySet();
			Iterator<?> i=s.iterator();
			while(i.hasNext()){
				Object temp=i.next();
				map.put(temp.toString(), o.get(temp).toString());
			}
			return map;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static String change(Map<String,Object> dataMap){
		try{
			JSONObject o=JSONObject.fromObject(dataMap);
			return o.toString();
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static Object doSet(Object object,String doMethod,Class<?>[] param,Object[] value){
		try{
			Method method=object.getClass().getMethod(doMethod,param);
			return method.invoke(object,value);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static Object doGet(String doMethod,Object object){
		try{
			Method method=object.getClass().getMethod(doMethod,null);
			return method.invoke(object,null);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}

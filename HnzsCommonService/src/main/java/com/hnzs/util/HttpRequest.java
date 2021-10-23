package com.hnzs.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类型描述：HTTP请求
 * @author 杨康
 * @date 日期：2016-5-3  时间：下午3:15:31
 * @version 1.0
 */
public class HttpRequest {
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("unused")
	public static String sendGet(String url, String param,String headjson) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            if(!StringUtil.isNull(headjson)){
            	connection.setRequestProperty("X-USER-INFO", headjson);
            }
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            //设置相应请求时间
            connection.setConnectTimeout(30000);
            //设置读取超时时间
            connection.setReadTimeout(30000);
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }*/
            //System.out.println("响应时间--->" + map.get(null));
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	System.out.println(e);
            return "发送GET请求出现异常！";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("unused")
	public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            //设置相应请求时间
            connection.setConnectTimeout(30000);
            //设置读取超时时间
            connection.setReadTimeout(30000);
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }*/
            //System.out.println("响应时间--->" + map.get(null));
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	System.out.println(e);
            return "exception";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static HashMap<String, String> sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("flag", "error");
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置相应请求时间
            conn.setConnectTimeout(30000);
            //设置读取超时时间
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            map.put("flag", "ok");
            map.put("result", result);
        } catch (Exception e) {
            System.out.println(e);
            map.put("result", "发送 POST 请求出现异常！");
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return map;
    }
    
    /**
     * 向指定 URL 发送POST方法的请求(将参数放在body中)
     * 
     * @param stringurl
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static HashMap<String, String> sendPostBody(String stringurl, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("flag", "error");
        try {
        	URL url = new URL(stringurl); 
    		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
    		connection.setRequestMethod("POST");//请求post方式
    		connection.setDoInput(true); 
    		connection.setDoOutput(true); 
    		//header内的的参数在这里set    connection.setRequestProperty("健, "值");
    		connection.setRequestProperty("Content-Type", "application/json");
    		
    		connection.connect(); 
    		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 

    		//body参数在这里put到JSONObject中
    		writer.write(param); 
    		writer.flush();
    		InputStream is = connection.getInputStream(); 
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
    		String strRead = "";
    		StringBuffer sbf = new StringBuffer(); 
    		while ((strRead = reader.readLine()) != null) { 
    			sbf.append(strRead); 
    		}
    		reader.close(); 
    		connection.disconnect();
    		result = sbf.toString();
        	
            map.put("flag", "ok");
            map.put("result", result);
        } catch (Exception e) {
            System.out.println(e);
            map.put("result", "发送 POST 请求出现异常！");
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return map;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("unused")
	public static String sendGetForProcess(String url, String param,String headjson) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if(!StringUtil.isNull(param)){
            	urlNameString += "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            if(!StringUtil.isNull(headjson)){
            	connection.setRequestProperty("X-USER-INFO", URLEncoder.encode(headjson,"UTF-8"));
            }
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            //设置相应请求时间
            connection.setConnectTimeout(30000);
            //设置读取超时时间
            connection.setReadTimeout(30000);
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	System.out.println(e);
            return "发送GET请求出现异常！";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostForProcess(String url, String param,String headjson) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            if(!StringUtil.isNull(headjson)){
            	conn.setRequestProperty("X-USER-INFO", URLEncoder.encode(headjson,"UTF-8"));
            }
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置相应请求时间
            conn.setConnectTimeout(30000);
            //设置读取超时时间
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            System.out.println("post报错："+e);
            return "";
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * 向指定 URL 发送POST方法的请求(将参数放在body中)
     * 
     * @param
     *             stringurl
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPostBodyForProcess(String stringurl, String param,String headjson) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	URL url = new URL(stringurl); 
    		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
    		connection.setRequestMethod("POST");//请求post方式
    		connection.setDoInput(true); 
    		connection.setDoOutput(true); 
    		if(!StringUtil.isNull(headjson)){
    			connection.setRequestProperty("X-USER-INFO", URLEncoder.encode(headjson,"UTF-8"));
            }
    		//header内的的参数在这里set    connection.setRequestProperty("健, "值");
    		connection.setRequestProperty("Content-Type", "application/json");
    		connection.setRequestProperty("Charsert", "UTF-8");
    		
    		connection.connect(); 
    		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8"); 

    		//body参数在这里put到JSONObject中
    		writer.write(param); 
    		writer.flush();
    		InputStream is = connection.getInputStream(); 
    		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
    		String strRead = "";
    		StringBuffer sbf = new StringBuffer(); 
    		while ((strRead = reader.readLine()) != null) { 
    			sbf.append(strRead); 
    		}
    		reader.close(); 
    		connection.disconnect();
    		result = sbf.toString();
        	
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    
    
    
    
    
    /**
     * 向指定URL发送GET方法的请求 获取字节流
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    @SuppressWarnings("unused")
	public static byte[]  sendGetForProcess2Byte(String url, String param,String headjson) {
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        try {
            String urlNameString = url;
            if(!StringUtil.isNull(param)){
            	urlNameString += "?" + param;
            }
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            if(!StringUtil.isNull(headjson)){
            	connection.setRequestProperty("X-USER-INFO", URLEncoder.encode(headjson,"UTF-8"));
            }
            // 设置通用的请求属性
            connection.setRequestProperty("content-type", "application/octet-stream");
            // 建立实际的连接
            connection.connect();
            //设置相应请求时间
            connection.setConnectTimeout(30000);
            //设置读取超时时间
            connection.setReadTimeout(30000);
            
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[2014];
            int read = -1;
            int length = 0;
            while ((read = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, read);
                length += read;
            }
        } catch (Exception e) {
        	System.out.println(e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    public static byte[] sendPostForProcess2Byte(String url, String param,String headjson) {
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
    	PrintWriter out = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            if(!StringUtil.isNull(headjson)){
            	conn.setRequestProperty("X-USER-INFO", URLEncoder.encode(headjson,"UTF-8"));
            }
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // 设置发送数据的格式
            conn.setRequestProperty("Charsert", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //设置相应请求时间
            conn.setConnectTimeout(30000);
            //设置读取超时时间
            conn.setReadTimeout(30000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            
            InputStream inputStream = conn.getInputStream();
            byte[] buffer = new byte[2014];
            int read = -1;
            while ((read = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, read);
            }
        } catch (Exception e) {
            System.out.println("post报错："+e);
        }
        return byteArrayOutputStream.toByteArray();
    }
    
    /**
     *获取百度地图瓦片
     */
    public static void main(String[] args) {
    	BufferedInputStream bis = null;
    	FileOutputStream fos = null;
    	int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
        	ArrayList<String> list = new ArrayList<String>();
        	list.add("http://api1.map.bdimg.com/customimage/tile?&x=5&y=2&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=4&y=2&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api2.map.bdimg.com/customimage/tile?&x=6&y=2&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=5&y=1&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api2.map.bdimg.com/customimage/tile?&x=5&y=3&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api2.map.bdimg.com/customimage/tile?&x=3&y=2&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=7&y=2&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api2.map.bdimg.com/customimage/tile?&x=4&y=1&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api1.map.bdimg.com/customimage/tile?&x=4&y=3&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api1.map.bdimg.com/customimage/tile?&x=6&y=1&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=6&y=3&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api2.map.bdimg.com/customimage/tile?&x=5&y=0&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api1.map.bdimg.com/customimage/tile?&x=3&y=1&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=3&y=3&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api2.map.bdimg.com/customimage/tile?&x=7&y=1&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api1.map.bdimg.com/customimage/tile?&x=7&y=3&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api1.map.bdimg.com/customimage/tile?&x=4&y=0&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=6&y=0&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api0.map.bdimg.com/customimage/tile?&x=3&y=0&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
            list.add("http://api1.map.bdimg.com/customimage/tile?&x=7&y=0&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
        	for(String url : list){
                URL realUrl = new URL(url);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                connection.setRequestProperty("Content-Type", "image/png");
                // 建立实际的连接
                connection.connect();
                //设置相应请求时间
                connection.setConnectTimeout(30000);
                //设置读取超时时间
                connection.setReadTimeout(30000);
                InputStream inputStream = connection.getInputStream();
                
                UrlUtil.UrlEntity entity = UrlUtil.parse(url);
                Map<String, String> params = entity.params;
                
                String aa = params.get("x")+"_"+params.get("y")+"_"+params.get("z")+"_"+params.get("udt")+"_"+params.get("scale")+".png";
                
                bis = new BufferedInputStream(inputStream);
                
                fos  = new FileOutputStream("C:\\Users\\Lenovo\\Desktop\\mapdata\\"+aa);
                
                while ((size = bis.read(buf)) != -1) {
                    fos.write(buf, 0, size);
                }
                fos.flush();
                

				inputStream.close();
				fos.close();
                
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
            try {
            	
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
	}


    public static HashMap<String, String> sendPostXml(String stringurl, String param) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("flag", "error");
        try {
            URL url = new URL(stringurl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");//请求post方式
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //header内的的参数在这里set    connection.setRequestProperty("健, "值");
            connection.setRequestProperty("Pragma:", "no-cache");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestProperty("Content-Type", "text/xml");

            connection.connect();
            out = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");

            //body参数在这里put到JSONObject中
            out.write(new String(param.getBytes("ISO-8859-1")));
            out.flush();
            out.close();
            InputStream is = connection.getInputStream();
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = "";
            StringBuffer sbf = new StringBuffer();
            while ((strRead = in.readLine()) != null) {
                sbf.append(strRead);
            }
            in.close();
            connection.disconnect();
            result = sbf.toString();

            map.put("flag", "ok");
            map.put("result", result);
        } catch (Exception e) {
            System.out.println(e);
            map.put("result", "发送 POST 请求出现异常！");
            map.put("flag","fail");
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return map;
    }
    
}

package com.hnzs.util;

import com.alibaba.fastjson.serializer.SerializerFeature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 此类中封装一些常用的字符串操作。 所有方法都是静态方法，不需要生成此类的实例， 为避免生成此类的实例，构造方法被申明为private类型的。
 *
 * @since 0.1
 */
public class StringUtil {
	private static String strChineseFirstPY = "";// 内容太长请到http://jshuwei.org.cn上下载

	/**
	 * 获取十六进制的颜色代码.例如 "#6E36B4" , For HTML ,
	 * @return String
	 */
	public static String getRandColorCode(){
		String r,g,b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length()==1 ? "0" + r : r ;
		g = g.length()==1 ? "0" + g : g ;
		b = b.length()==1 ? "0" + b : b ;

		return r+g+b;
	}

	/**
	 * 计算百分数
	 */
	public static String getPercent(int x, int total) {
		String result = "";// 接受百分比的值
		double x_double = x * 1.0;
		double tempresult = (double) x / total;
		// NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("0.00%"); // ##.00%
		// 百分比格式，后面不足2位的用0补齐
		// result=nf.format(tempresult);
		result = df1.format(tempresult);
		return result;
	}

	/**
	 * 将一字符串转换成拼音首字母
	 *
	 * @since 1.1
	 * @param strText
	 *            字符串
	 * @return 字符串对应的拼音首字母
	 */
	public static String getFirstPY(String strText) {
		if (strText == null || strText.trim().length() == 0)
			return "";
		String ret = "";
		for (int i = 0; i < strText.length(); i++) {
			char ch = strText.charAt(i);
			if ('\u4E00' <= ch && '\u9FA5' >= ch)
				ret = ret + strChineseFirstPY.charAt(ch - 19968);
			else
				ret = ret + ch;
		}

		return ret;
	}
	/*
	 *判断字符串是否是数字
	 *
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}// 用ascii码判断整型 ...

	/**
	 * 替换字符串
	 *
	 * @since 1.1
	 * @param strSc
	 *            需要进行替换的字符串
	 * @param oldStr
	 *            源字符串
	 * @param newStr
	 *            替换后的字符串
	 * @return 替换后对应的字符串
	 */
	public static String replace(String strSc, String oldStr, String newStr) {
		String ret = strSc;
		if (ret != null && oldStr != null && newStr != null) {
			ret = strSc.replaceAll(oldStr, newStr);
		}
		return ret;
	}

	/**
	 * 替换字符串，修复java.lang.String类的replaceAll方法时第一参数是字符串常量正则时(如："address".
	 * replaceAll("dd","$");)的抛出异常：java.lang.StringIndexOutOfBoundsException:
	 * String index out of range: 1的问题。
	 *
	 * @since 1.2
	 * @param strSc
	 *            需要进行替换的字符串
	 * @param oldStr
	 *            源字符串
	 * @param newStr
	 *            替换后的字符串
	 * @return 替换后对应的字符串
	 */
	public static String replaceAll(String strSc, String oldStr, String newStr) {
		int i = -1;
		while ((i = strSc.indexOf(oldStr)) != -1) {
			strSc = new StringBuffer(strSc.substring(0, i)).append(newStr)
					.append(strSc.substring(i + oldStr.length())).toString();
		}
		return strSc;
	}

	/**
	 * 将字符串转换成HTML格式的字符串
	 *
	 * @since 1.1
	 * @param str
	 *            需要进行转换的字符串
	 * @return 转换后的字符串
	 */
	public static String toHtml(String str) {
		String html = str;
		if (str == null || str.length() == 0) {
			return "";
		} else {
			html = replace(html, "&", "&amp;");
			html = replace(html, "<", "&lt;");
			html = replace(html, ">", "&gt;");
			html = replace(html, "\r\n", "\n");
			html = replace(html, "\n", "<br>\n");
			html = replace(html, "\"", "&quot;");
			html = replace(html, " ", "&nbsp;");
			return html;
		}
	}

	/**
	 * 将HTML格式的字符串转换成常规显示的字符串
	 *
	 * @since 1.1
	 * @param str
	 *            需要进行转换的字符串
	 * @return 转换后的字符串
	 */
	public static String toText(String str) {
		String text = str;
		if (str == null || str.length() == 0) {
			return "";
		} else {
			text = replace(text, "&amp;", "&");
			text = replace(text, "&lt;", "<");
			text = replace(text, "&gt;", ">");
			text = replace(text, "<br>\n", "\n");
			text = replace(text, "<br>", "\n");
			text = replace(text, "&quot;", "\"");
			text = replace(text, "&nbsp;", " ");
			return text;
		}
	}

	/**
	 * 将一字符串数组以某特定的字符串作为分隔来变成字符串
	 *
	 * @since 1.0
	 * @param strs
	 *            字符串数组
	 * @param token
	 *            分隔字符串
	 * @return 以token为分隔的字符串
	 */
	public static String join(String[] strs, String token) {
		if (strs == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			if (i != 0)
				sb.append(token);
			sb.append(strs[i]);
		}
		return sb.toString();
	}

	/**
	 * 将一字符串以某特定的字符串作为分隔来变成字符串数组
	 *
	 * @since 1.0
	 * @param str
	 *            需要拆分的字符串("@12@34@56")
	 * @param token
	 *            分隔字符串("@")
	 * @return 以token为分隔的拆分开的字符串数组
	 */
	public static String[] split(String str, String token) {
		// String temp = str.substring(1, str.length());
		String temp = str;
		return temp.split(token);
	}

	/**
	 * 验证字符串合法性
	 *
	 * @since 1.0
	 * @param str
	 *            需要验证的字符串
	 * @param test
	 *            非法字符串（如："~!#$%^&*()',;:?"）
	 * @return true:非法;false:合法
	 */
	public static boolean check(String str, String test) {
		if (str == null || str.equals(""))
			return true;
		boolean flag = false;
		for (int i = 0; i < test.length(); i++) {
			if (str.indexOf(test.charAt(i)) != -1) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 将数值型字符串转换成Integer型
	 *
	 * @since 1.0
	 * @param str
	 *            需要转换的字符型字符串
	 * @param ret
	 *            转换失败时返回的值
	 * @return 成功则返回转换后的Integer型值；失败则返回ret
	 */
	public static Integer String2Integer(String str, Integer ret) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return ret;
		}
	}

	// 判断字符串是否是数字
	// public static boolean isNumeric(String str) {
	// if (str.matches("\\d *")) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	/**
	 * 将数值型转换成字符串
	 *
	 * @since 1.0
	 * @param it
	 *            需要转换的Integer型值
	 * @param ret
	 *            转换失败的返回值
	 * @return 成功则返回转换后的字符串；失败则返回ret
	 */
	public static String Integer2String(Integer it, String ret) {
		try {
			return Integer.toString(it);
		} catch (NumberFormatException e) {
			return ret;
		}
	}

	/**
	 * 比较两字符串大小(ASCII码顺序)
	 *
	 * @since 1.1
	 * @param str1
	 *            参与比较的字符串1
	 * @param str2
	 *            参与比较的字符串2
	 * @return str1>str2:1;str1<str2:-1;str1=str2:0
	 */
	public static int compare(String str1, String str2) {//
		if (str1.equals(str2)) {
			return 0;
		}
		int str1Length = str1.length();
		int str2Length = str2.length();
		int length = 0;
		if (str1Length > str2Length) {
			length = str2Length;
		} else {
			length = str1Length;
		}
		for (int i = 0; i < length; i++) {
			if (str1.charAt(i) > str2.charAt(i)) {
				return 1;
			}
		}
		return -1;
	}

	/**
	 * 将阿拉伯数字的钱数转换成中文方式
	 *
	 * @since 1.1
	 * @param num
	 *            需要转换的钱的阿拉伯数字形式
	 * @return 转换后的中文形式
	 */
	public static String num2Chinese(double num) {
		String result = "";
		String str = Double.toString(num);
		if (str.contains(".")) {
			String begin = str.substring(0, str.indexOf("."));
			String end = str.substring(str.indexOf(".") + 1, str.length());
			byte[] b = begin.getBytes();
			int j = b.length;
			for (int i = 0, k = j; i < j; i++, k--) {
				result += getConvert(begin.charAt(i));
				if (!"零".equals(result.charAt(result.length() - 1) + "")) {
					result += getWei(k);
				}
				System.out.println(result);

			}
			for (int i = 0; i < result.length(); i++) {
				result = result.replaceAll("零零", "零");
			}
			if ("零".equals(result.charAt(result.length() - 1) + "")) {
				result = result.substring(0, result.length() - 1);
			}
			result += "元";
			byte[] bb = end.getBytes();
			int jj = bb.length;
			for (int i = 0, k = jj; i < jj; i++, k--) {
				result += getConvert(end.charAt(i));
				if (bb.length == 1) {
					result += "角";
				} else if (bb.length == 2) {
					result += getFloat(k);
				}
			}
		} else {
			byte[] b = str.getBytes();
			int j = b.length;
			for (int i = 0, k = j; i < j; i++, k--) {
				result += getConvert(str.charAt(i));
				result += getWei(k);
			}
		}
		return result;
	}

	private static String getConvert(char num) {
		if (num == '0') {
			return "零";
		} else if (num == '1') {
			return "一";
		} else if (num == '2') {
			return "二";
		} else if (num == '3') {
			return "三";
		} else if (num == '4') {
			return "四";
		} else if (num == '5') {
			return "五";
		} else if (num == '6') {
			return "六";
		} else if (num == '7') {
			return "七";
		} else if (num == '8') {
			return "八";
		} else if (num == '9') {
			return "九";
		} else {
			return "";
		}
	}

	private static String getFloat(int num) {
		if (num == 2) {
			return "角";
		} else if (num == 1) {
			return "分";
		} else {
			return "";
		}
	}

	private static String getWei(int num) {
		if (num == 1) {
			return "";
		} else if (num == 2) {
			return "十";
		} else if (num == 3) {
			return "百";
		} else if (num == 4) {
			return "千";
		} else if (num == 5) {
			return "万";
		} else if (num == 6) {
			return "十";
		} else if (num == 7) {
			return "百";
		} else if (num == 8) {
			return "千";
		} else if (num == 9) {
			return "亿";
		} else if (num == 10) {
			return "十";
		} else if (num == 11) {
			return "百";
		} else if (num == 12) {
			return "千";
		} else if (num == 13) {
			return "兆";
		} else {
			return "";
		}
	}

	/**
	 * 将字符串的首字母改为大写
	 *
	 * @since 1.2
	 * @param str
	 *            需要改写的字符串
	 * @return 改写后的字符串
	 */
	public static String firstToUpper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 非空检验 is==true:表示不为空
	 */
	public static boolean isNull(String str) {
		if (str == null || str.equals("") || str.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * list非空检验 is==true:表示不为空
	 */
	public static boolean isNullList(List list) {
		if (list == null || list.isEmpty() || list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	public static String toString(Object obj) {
		if (obj == null)
			return "null";
		return obj.toString();
	}

	public static String join(Collection s, String delimiter) {
		StringBuffer buffer = new StringBuffer();
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			buffer.append(iter.next());
			if (iter.hasNext()) {
				buffer.append(delimiter);
			}
		}
		return buffer.toString();
	}

	// 获取uuid
	public static String getUuid() {
		String str = "";
		UUID uuid = UUID.randomUUID();
		str = uuid.toString().replaceAll("-", "");

		return str;
	}
	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * @param s 原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 发送HttpPost请求
	 *
	 * @param strURL
	 *            服务地址
	 * @param params
	 *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
	 * @return 成功:返回json字符串<br/>
	 */
	public static String doPostSomeThing(String strURL, String params) {
//		System.out.print("url:"+strURL+"--");
//		System.out.println("params:"+params);
		String result = "";
		@SuppressWarnings("unused")
		String currenttime= DateUtils.formatDateTimeByDate(new Date());
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("GET"); // 设置请求方式POST
//			System.out.println(connection.toString());
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GBK"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				result = new String(data, "GBK"); // utf-8编码
//				System.out.println(result);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			result=e.toString();
		}
		System.out.println(result);
		return result;
	}


	/**
	 * Author：  周凌天
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static  String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
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
		return result;
	}

	//获取两个json对象的变化
	@SuppressWarnings("rawtypes")
	public static String  getDefOfTwoJson(String obj_json_new,String obj_json_old){
		String result="";
		try{
			//新的对象
			//JSONObject jsonObject = new JSONObject(obj_json_new) ;
			JSONObject jsonObject = JSONObject.fromObject(obj_json_new.replace("[", "").replace("]", ""));
			//原来的对象
			ArrayList rows = (ArrayList) JSON.Decode(obj_json_old);
			HashMap row = (HashMap) rows.get(0);
			//对比之后变化的信息存储在这里
			HashMap<String, String> map = getComperRes(jsonObject, row);
			result = JSON.Encode(map);
		}catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}

	/**
	 * 比较两种纯json 内容
	 * @param obj_json_new
	 * @param obj_json_old
	 * @param type    object  表示存对象格式的json;     list  表示 集合格式的json。  集合中包含对象  e.g:[{aa:aa,bb:bb},{...}]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getDefOfTwoJsonBytype(String obj_json_new,String obj_json_old,String type){
		String result = "";
		try{
			if("object".equals(type)){
				JSONObject fromObject = JSONObject.fromObject(obj_json_new);
				HashMap old_map = (HashMap) JSON.Decode(obj_json_old);
				HashMap<String, String> map = getComperRes(fromObject,old_map);
				result = JSON.Encode(map);
			}else if("list".equals(type)){
				JSONArray parseArray = JSONArray.fromObject(obj_json_new);
				ArrayList rows = (ArrayList) JSON.Decode(obj_json_old);
				//先比较数量。 如果数量不一样，则直接显示新增数据。
				//数量一样，则比较每一项的值。
				//这里有可能因为顺序不一样，造成整个内容都不一样   这种可能很大，而前不可控。
				//所以这里   不用这种比较。 换另一种方式。（可根据miniui表格 _state字段来判断）
				if(parseArray.size() != rows.size()){
					result =obj_json_new;
				}else{
					HashMap<String, String> maptotal =  new HashMap<String, String>();
					for(int i=0;i<parseArray.size();i++){
						JSONObject obj = (JSONObject) parseArray.get(i);
						HashMap row = (HashMap) rows.get(i);
						//去掉不比较的key
						obj.remove("ct_eq_id");

						HashMap<String, String> map = getComperRes(obj,row);
						maptotal.putAll(map);
					}
					result = JSON.Encode(maptotal);
				}
			}
		}catch (Exception e){
			result = "{\"errmsg\":\"对比异常\"}";
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	private static HashMap<String, String> getComperRes(JSONObject jsonObject,HashMap row){
		HashMap<String, String> map = new HashMap<String, String>();
		String key = "";
		String value = "";
		String change_value="";
		String temp_str="";
		//去掉不比较的key
		jsonObject.remove("ct_eq_id");
		jsonObject.remove("created_time");
		jsonObject.remove("created_admin");
		jsonObject.remove("updated_time");
		jsonObject.remove("updated_admin");
		//循环获取新对象的 所有key和对应的值,然后去对比
		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			//对比,如果存在并且值不相等则保存对象
			temp_str =row.get(key) == null ? "-xyz12345zyz" : row.get(key).toString();
			if(!"-xyz12345zyz".equals(temp_str)){
				if(!value.equals(temp_str)){
					change_value = "【"+temp_str+"】修改成【"+value+"】";
					map.put(key, change_value);
				}
			}
		}
		return map;
	}

	@SuppressWarnings("rawtypes")
	public static HashMap<String, String> getComperRes1(JSONObject jsonObject,HashMap row){
		HashMap<String, String> map = new HashMap<String, String>();
		String key = "";
		String value = "";
		String change_value = "";
		String temp_str = "";
		String kh_type = row.get("kh_type") == null ? "" : row.get("kh_type").toString();
		//去掉不比较的key
		jsonObject.remove("kh_bh_gr");
		jsonObject.remove("kh_bh_qy");
		jsonObject.remove("created_time");
		jsonObject.remove("created_admin");
		jsonObject.remove("updated_time");
		jsonObject.remove("updated_admin");
		//循环获取新对象的 所有key和对应的值,然后去对比
		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			if("个人客户".equals(kh_type)){
				if(key.contains("_gr")){
					key = key.replaceAll("_gr", "");
				}
			}else if("企业客户".equals(kh_type)){
				if(key.contains("_qy")){
					key = key.replaceAll("_qy", "");
				}
			}else{

			}
			//对比,如果存在并且值不相等则保存对象
			temp_str =row.get(key) == null ? "-xyz12345zyz" : row.get(key).toString();
			if(!"-xyz12345zyz".equals(temp_str)){
				if(!value.equals(temp_str)){
					change_value = "【"+temp_str+"】修改成【"+value+"】";
					map.put(key, change_value);
				}
			}
		}
		return map;
	}
	//十进制转换二进制
	public static String  binaryToDecimal(int num){
		char[] chs = new char[Integer.SIZE];
		for (int i = 0; i < Integer.SIZE; i++) {
			chs[Integer.SIZE - 1 - i] = (char) ((num >> i & 1) + '0');
		}
		return new String(chs);
	}

	public static  HashMap getPagesDataHashMap(ArrayList alist, int pageIndex, int pageSize) throws Exception{
		ArrayList data = new ArrayList();
		int start = (pageIndex - 1) * pageSize, end = start + pageSize;
//    	int start = pageIndex * pageSize, end = start + pageSize; // 此处传过来的是0
		for (int i = 0, l = alist.size(); i < l; i++){
			HashMap record = (HashMap)alist.get(i);
			if (record == null) continue;
			if (start <= i && i < end){
				data.add(record);
			}
		}
//        HashMap result = new HashMap();
//        result.put("list", data);
//        result.put("total", alist.size());
//        result.put("pageIndex", pageIndex);
//        result.put("pageSize", pageSize);
		HashMap result = new HashMap();
		result.put("data", data);
		result.put("total", alist.size());
		//兼容layui
//		result.put("code", 0);
//		result.put("msg", "");
//		result.put("count", alist.size());

		return result;
	}

	/**
	 * 统计中是汉字的个数
	 * @param context
	 * @return
	 */
	public static int getChineseNum(String context){    ///统计context中是汉字的个数
		int lenOfChinese=0;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");    //汉字的Unicode编码范围
		Matcher m = p.matcher(context);
		while(m.find()){
			lenOfChinese++;
		}
		return lenOfChinese;
	}

	/**
	 * 判断字符串是否是数值
	 * @param str
	 * @return
	 */
	public static boolean isNumeric1(String str){
//		   for (int i = str.length();--i>=0;){
//		       if (!Character.isDigit(str.charAt(i))){
//		           return false;
//		       }
//		   }
//		   return true;
		String reg="-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";
		boolean flag=str.matches(reg);
		if(flag ) {
			return true;
		} else {
			return false;
		}
	}

	/**
	/* * 自定义的json对象转化为json字符串
	 * @param
	 * @return
	 *//*
	public static String jsonObjectToJsonString(Json json){
		String jsonData = com.alibaba.fastjson.JSONArray.toJSONString(json, SerializerFeature.WriteMapNullValue);
		//Object obj = JSONArray.toJSON(json);
		//String jsonData = obj.toString();
		return jsonData;
	}*/


    public static HashMap<String, String> readStringXml(String xml) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        Element books = doc.getRootElement();
        System.out.println("rootNode:" + books.getName());
        Iterator Elements = books.elementIterator();
        HashMap<String, String> map = new HashMap<>();
        while (Elements.hasNext()) {
            Element user = (Element) Elements.next();
            System.out.println("node=" + user.getName() + "\ttext=" + user.getText());
            map.put(user.getName(), user.getText());
        }
        return map;
    }

	public static void main(String[] args) {
//    	String aa = "{\"fbrandName\":\"茅台\",\"fbrief\":\"酱香酒\","
//    			+ "\"fatrr\":[{\"color\":\"red\",\"size\":\"5m\"}],\"fsku\" :[{\"version\":\"1.0\",\"color\":\"red\"}],"
//    			+ "\"forderId\":\"22321232323\",\"cost\":\"1200\"}";
//    		System.out.println(aa);
//    	String sendPost = sendPost("http://admin.kemei.henancatv.com/admin/api/createGoods", aa);
		String sendPost = sendPost("http://127.0.0.1:8081/mongo3/springmvc/hello/name", "");
		System.out.println(sendPost);
	}

}

package com.hnzs.util;

import java.util.HashMap;
import java.util.Map;

public class UrlUtil {

	public static class UrlEntity {
		/**
		 * 基础url
		 */
		public String baseUrl;
		/**
		 * url参数
		 */
		public Map<String, String> params;
	}

	/**
	 * 解析url
	 *
	 * @param url
	 * @return
	 */
	public static UrlEntity parse(String url) {
		UrlEntity entity = new UrlEntity();
		if (url == null) {
			return entity;
		}
		url = url.trim();
		if (url.equals("")) {
			return entity;
		}
		String[] urlParts = url.split("\\?");
		entity.baseUrl = urlParts[0];
		//没有参数
		if (urlParts.length == 1) {
			return entity;
		}
		//有参数
		String[] params = urlParts[1].split("&");
		entity.params = new HashMap<>();
		for (String param : params) {
			String[] keyValue = param.split("=");
			if(keyValue.length>1){
				entity.params.put(keyValue[0], keyValue[1]);
			}
		}

		return entity;
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		UrlEntity entity = parse(null);
		System.out.println(entity.baseUrl + "\n" + entity.params);
		entity = parse("http://www.123.com");
		System.out.println(entity.baseUrl + "\n" + entity.params);
		entity = parse("http://www.123.com?id=1");
		System.out.println(entity.baseUrl + "\n" + entity.params);
		entity = parse("http://api0.map.bdimg.com/customimage/tile?&x=6&y=3&z=5&udt=20191217&scale=2&ak=L89o1XNzltht9jIG6B2H4LabgGutwRR2&styles=t%3Awater%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aland%7Ce%3Aall%7Cc%3A%23f3f3f3%2Ct%3Arailway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Ahighway%7Ce%3Aall%7Cc%3A%23fdfdfd%2Ct%3Ahighway%7Ce%3Al%7Cv%3Aoff%2Ct%3Aarterial%7Ce%3Ag%7Cc%3A%23fefefe%2Ct%3Aarterial%7Ce%3Ag.f%7Cc%3A%23fefefe%2Ct%3Apoi%7Ce%3Aall%7Cv%3Aoff%2Ct%3Agreen%7Ce%3Aall%7Cv%3Aoff%2Ct%3Asubway%7Ce%3Aall%7Cv%3Aoff%2Ct%3Amanmade%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alocal%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Aarterial%7Ce%3Al%7Cv%3Aoff%2Ct%3Aboundary%7Ce%3Aall%7Cc%3A%23fefefe%2Ct%3Abuilding%7Ce%3Aall%7Cc%3A%23d1d1d1%2Ct%3Alabel%7Ce%3Al.t.f%7Cc%3A%23999999");
		System.out.println(entity.baseUrl + "\n" + entity.params);
	}
}

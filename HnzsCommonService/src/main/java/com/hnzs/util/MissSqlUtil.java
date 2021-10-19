package com.hnzs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MissSqlUtil {

	//过滤 '
	//ORACLE 注解 --  /**/
	//关键字过滤 update,delete
	static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
				+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
	 
	static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);//表示忽略大小写
	 
	/***************************************************************************
	 * 参数校验
	 * 
	 * @param str ep: "or 1=1"
	 */
	public static boolean isSqlValid(String str) {
		Matcher matcher = sqlPattern.matcher(str);
		if (matcher.find()) {
			//System.out.println("参数存在非法字符，请确认："+matcher.group());//获取非法字符：or
			return false;
		}
		return true;
	}
	
	public static String toSqlValid(String str) {
		Matcher matcher = sqlPattern.matcher(str);
		if (matcher.find()) {
			//System.out.println("参数存在非法字符，请确认："+matcher.group());//获取非法字符：or
			return str.replaceAll(matcher.group(), "");
		}
		return str;
	}
	
	public static void main(String[] args) {
		String aa = "{'bn':'tvnbn','operatorId':'operatorId','failureTime':'endTime','productCode':'productId','tvn':'tvnbn','sourceSystemId':'sourceFlag'}";
		System.out.println(toSqlValid(aa));
	}
	
}

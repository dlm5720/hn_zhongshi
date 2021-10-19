/*
 * Created on 2004-7-14
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.hnzs.util;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocaleConvert {
    
	public static String ISO8859toGB2312(String str) {
		try {
			byte[] bytesStr = str.getBytes("ISO8859_1");
			return new String(bytesStr,"gb2312");
		}catch(Exception e) {
			return str;
		}
	}
	public static String ISO8859toGBK(String str) {
		try {
			byte[] bytesStr = str.getBytes("ISO8859_1");
			return new String(bytesStr,"GBK");
		}catch(Exception e) {
			return str;
		}
	}
	public static String GB2312toISO8859(String str) {
		try {
			byte[] bytesStr = str.getBytes("gb2312");
			return new String(bytesStr,"ISO8859_1");
		}catch(Exception e) {
			return str;
		}
	}
	public static String GBKtoISO8859(String str) {
		try {
			byte[] bytesStr = str.getBytes("gbk");
			return new String(bytesStr,"ISO8859_1");
		}catch(Exception e) {
			return str;
		}
	}
	public static String GBKtoUTF8(String str) {
		try {
			byte[] bytesStr = str.getBytes("gbk");
			return new String(bytesStr,"utf-8");
		}catch(Exception e) {
			return str;
		}
	}
	public static String ISO8859toUTF8(String str) {
		try {
			byte[] bytesStr = str.getBytes("ISO8859_1");
			return new String(bytesStr,"utf-8");
		}catch(Exception e) {
			return str;
		}
	}
}


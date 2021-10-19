package com.hnzs.util;

import java.util.Random;

public class GetOrredingIdUUID {

	/**
	 * 将uuid 转化成16位唯一订单号
	 * @param uuid
	 * @return
	 */
	public static String getOrderIdByUUId(String uuid) {
        int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = uuid.hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return first + String.format("%015d", hashCodeV);
    }
	
	
	 public static void main(String[] args) {
	    String orderingID= getOrderIdByUUId("91ef4c5c39c44881ac966655051dc5da");
	    System.out.println(orderingID);
	}
}

package com.hnzs.util;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class CreateWldbhUtil {
	
    //注意：primaryKey.properties文件放在src/config/目录下
    // 如果keyFilePath写"config/primaryKey.properties",src/config下的primaryKey.properties修改的值的动态看不到
    //只能在bluid\classes\config下可以看到primaryKey.properties的key值发生了变化
//    public static String keyFilePath = "resources/wldbh.properties";
    public static String keyFilePath = null;
    public static Properties props = new Properties();
    public static File file=null;
    
    static {
        try {
        	String path = CreateWldbhUtil.class.getResource("/").getPath();
        	System.out.println("**************************************"+path);
        	boolean flag = isWinOrlinux();
        	if(flag){
        		keyFilePath = path.substring(1, path.length()) + "wldbh.properties"; //获取配置文件的路径
        	}else{
        		keyFilePath = path + "wldbh.properties";
        	}
        	System.out.println(keyFilePath);
            file = new File(keyFilePath);
            //props 读取文件内容
            props.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //获取文件里的某个或多个key对应的value值
    public static int getWldbhIDIndex(){
        int oldStaffID=0;
        try {
            //获取staff_id对应的value值
            oldStaffID = Integer.parseInt(props.getProperty ("now_wldbh"));
            // System.out.println(oldStaffID);
//				          in.close();
            return oldStaffID;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    //update 文件里key对应的value值
    public static void updateWldbhIDIndex(int staffid){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //update staff_id的value
            props.setProperty("now_wldbh",String.valueOf(staffid+1));
            //添加注释： 修改了staff_id
            props.store(fos, "update now_wldbh");
            //fos.flush();
            fos.close();// 关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //生成物流单编号
    public static synchronized String createWldbh(){
    	String nowWldbh = "";
    	int maxIndex = 0;
    	int oriIndex = getWldbhIDIndex();
    	if(oriIndex >= 999999){
    		updateWldbhIDIndex(0);
    		oriIndex = 0;
    	}
//    	System.out.println("************"+oriIndex);
    	updateWldbhIDIndex(oriIndex);
    	maxIndex = getWldbhIDIndex();
    	String wldbhSuffix = getWldbhSuffix(maxIndex);
    	String time = DateUtils.formatDateTimeByDate_f2(new Date());
    	nowWldbh = time.substring(2,8) + wldbhSuffix;
    	return nowWldbh;
    }
    
    //生成物流单编号后缀
    public static String getWldbhSuffix(int maxIndex){
    	String wldbhSuffix = "";
    	int len = 6 - String.valueOf(maxIndex).length();
    	String ostr = "";
    	for (int i = 0; i < len; i++) {
			ostr += "0";
		}
    	wldbhSuffix = ostr + maxIndex;
    	return wldbhSuffix;
    	
    }
    
    //判断操作系统，如果是true说明是win，false说明是linux
	public static boolean isWinOrlinux(){
		boolean flag = false;
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){  
		  //System.out.println(os + " can't gunzip");  
		  flag = true;
		}
		return flag;
	}
    
    public static void main(String[] args) {
//        int oriIndex=getWldbhIDIndex();
//        System.out.println("前一个员工号:"+oriIndex);
//        updateWldbhIDIndex(oriIndex);
//        System.out.println("最新的员工号:"+getWldbhIDIndex());
    	
//    	for (int i = 0; i < 1000; i++) {
//    		String nowWldbh = createWldbh();
//        	System.out.println(nowWldbh);
//		}
    	System.out.println(isWinOrlinux());
    }

    
    
}


package com.hnzs.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {
	String driver = null;
	String url = null;
	String user = null;
	String password = null;
	
	public ConnectToDB(String driver,String url,String user,String password){
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public Connection getConnection(){
		try
		{
			Class.forName(driver);
			try
			{
				Connection con=DriverManager.getConnection(url,user,password);
				if(!con.isClosed()){
					//System.out.println("打开数据库成功");
				    return con;
				}else{
					return null;
				}
			}
			catch(SQLException SE)
			{
				System.out.print("打开数据库失败");
				return null;
			}
		}
		catch(Exception E)
		{
			System.out.print("无法加载驱动："+driver);
			return null;
		}
	}
}

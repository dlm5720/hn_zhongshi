package com.hnzs.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class MysqlUtil {
	
	public static Connection getConn() throws IOException {
		Connection conn = null;
		Properties properties = new Properties();
	    // 使用ClassLoader加载properties配置文件生成对应的输入流
	    InputStream in = MysqlUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
	    // 使用properties对象加载输入流
	    properties.load(in);
	    //获取key对应的value值
	    String driver = properties.getProperty("mysql.driver");
	    String url = properties.getProperty("mysql.url");
	    String username = properties.getProperty("mysql.username");
	    String password = properties.getProperty("mysql.password");
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static PreparedStatement prepareStmt(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pstmt;
	}

	public static ResultSet executeQuery(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void close(Connection conn, Statement stmt,
			PreparedStatement preStatement, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
		if (preStatement != null) {
			try {
				preStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			preStatement = null;
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}
}

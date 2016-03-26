package com.jiyi.joke.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static String driver;
	private static String dburl;
	private static String user;
	private static String password;
	private static final ConnectionFactory factory=new ConnectionFactory();
	
	private Connection conn;
	
	static{
		Properties pro=new Properties();
		try {
			InputStream in=ConnectionFactory.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("=================配置文件读取错误=================");
			e.printStackTrace();
		}
		driver=pro.getProperty("driver");
		dburl=pro.getProperty("dburl");
		user=pro.getProperty("user");
		password=pro.getProperty("password");
	}
	
	private ConnectionFactory(){
		
	}
	
	public static ConnectionFactory getInstance(){
		return factory;
	}
	
	public Connection makeConnection(){
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(dburl,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Conn success");
		return conn;
	}
	
}

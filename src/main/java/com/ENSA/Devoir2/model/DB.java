package com.ENSA.Devoir2.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	private Connection connection;
	public void conn(String DBName, String userName, String Pwd) {
		connection = null;
		String connectionUrl = "jdbc:mysql://localhost:3307/" + DBName;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUrl, userName, Pwd);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		return connection;
	}
}

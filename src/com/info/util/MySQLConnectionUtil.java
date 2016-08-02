package com.info.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionUtil {
	private static MySQLConnectionUtil instance = null;
	protected MySQLConnectionUtil() {
	}
	public static MySQLConnectionUtil getInstance() {
		if (instance == null) {
			instance = new MySQLConnectionUtil();
		}
		return instance;
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/info", "root", "root");
		} catch (ClassNotFoundException classNotFoundException) {
			// TODO Auto-generated catch block
			classNotFoundException.printStackTrace();
		} catch (SQLException sqlException) {
			// TODO: handle exception
			sqlException.printStackTrace();
		}

		return connection;
	}
}

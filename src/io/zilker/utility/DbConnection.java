package io.zilker.utility;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DbConnection {
	Connection con = null;

	public void closeConnection(Connection con, ResultSet result, PreparedStatement statement) {
		try {
			con.close();
			if (result != null) {
				result.close();
			}
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createconnection() {
		try {
			DataSource dataSource = null;
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
           dataSource = (DataSource) envContext.lookup("jdbc/worldDB");
	//		Class.forName("com.mysql.jdbc.Driver");
    //		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet", "root", "ztech@123");
           con=dataSource.getConnection();
		} catch (Exception e) {
			System.out.println("error");
		}

	}

	public Connection getConnection() {
		return con;
	}
}

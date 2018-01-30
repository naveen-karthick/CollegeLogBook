package io.zilker.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.zilker.bean.LoginDetails;
import io.zilker.utility.Constants;
import io.zilker.utility.GetConnection;

public class ValidationOfCredentials {
	
	static Connection con=null;
	public boolean verifyEmail(String emailId)
	{
		//validating email
		Pattern pattern=Pattern.compile("^[a-zA-Z0-9]+([.][a-zA-Z0-9]+)?@[a-zA-Z0-9]+([.][a-zA-Z]{2,3}){1,2}$");
		Matcher match=pattern.matcher(emailId);
	
		while(match.find())
		{
			return true;
		}
		
		
		return false;
		
	}
	public int verifyLoginCredentials(LoginDetails user)
	{
		GetConnection connection=new GetConnection();
		//create connection
		connection.createconnection();
		
		//get the connection obj
		
		con=connection.getConnection();
		PreparedStatement statement=null;
		ResultSet result=null;
		try {
			statement=con.prepareStatement(Constants.LOGIN);
			statement.setString(1, user.getEmailId());
			statement.setString(2, user.getPasword());
		 result=statement.executeQuery();
			
			if(!result.next())
			{
				return -1;
				
			}
			else
			{	
				return result.getInt(1);
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -2;
			}
		finally
		{
			
		connection.closeConnection(con, result, statement);
		}
	}
}


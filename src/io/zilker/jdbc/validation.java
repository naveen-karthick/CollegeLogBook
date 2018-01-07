package io.zilker.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.zilker.bean.login_details;
import io.zilker.utility.connection;
import io.zilker.utility.queries;

public class validation {
	
	static Connection con=null;
	public boolean check_email(String email_id)
	{
		//validating email
		Pattern pattern=Pattern.compile("^[a-zA-Z0-9]+(.[a-zA-Z0-9]+)?+@[a-zA-Z0-9]+.[a-zA-Z]{2,3}");
		Matcher match=pattern.matcher(email_id);
	
		while(match.find())
		{
			return true;
		}
		
		
		return false;
		
	}
	public int check_login(login_details log)
	{
		connection obj=new connection();
		
		
		//create connection
		obj.createconnection();
		
		//get the connection obj
		con=obj.con();

		//create object to fetch different types of queries
		queries query=new queries();
		//adding queries to the map
		query.initialisE_queries();
		try {
			PreparedStatement st=con.prepareStatement(query.fetch_query().get("login"));
			st.setString(1, log.getEmail_id());
			st.setString(2, log.getPass());
			ResultSet result=st.executeQuery();
			if(!result.next())
			{
				return -1;
				
			}
			else
				return result.getInt(1);
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return -2;
		}
		
	}
}


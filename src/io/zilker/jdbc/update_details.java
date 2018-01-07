package io.zilker.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import io.zilker.bean.report_details;
import io.zilker.utility.connection;
import io.zilker.utility.queries;

public class update_details {
	 Connection con=null;
	  connection con1;
	  queries query;
	
	  
	  public boolean update_hod_report(report_details report)
	  {
			try {
				con1=new connection();
				con1.createconnection();
				con=con1.con();
				query=new queries();
				query.initialisE_queries();
				PreparedStatement st=con.prepareStatement(query.fetch_query().get("Hod_report"));
				st.setString(1, report.getDate_on());
				st.setInt(2, report.getDepartment());
				st.setInt(3, report.getStaff_id());
				st.setInt(4, report.getStatus());
				st.setString(5, report.getUpdated_on());
				
				
			
			st.executeUpdate();
			
			return true;
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
			
		  
	  }
	public boolean update_report(report_details report)
	{
		try {
			con1=new connection();
			con1.createconnection();
			con=con1.con();
			query=new queries();
			query.initialisE_queries();
			PreparedStatement st=con.prepareStatement(query.fetch_query().get("submit_report"));
			st.setString(1, report.getDate_on());
			st.setInt(2, report.getHour_of_class());
			st.setInt(3, report.getStaff_id());
			st.setString(4, report.getWork_done());
			st.setString(5, report.getUpdated_on());
			st.setInt(6, report.getYear_of_department());
			st.setInt(7, report.getDepartment());
		
		st.executeUpdate();
		
		return true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		
		
		
		
		
		return false;
	}
	
}
}

package io.zilker.jdbc;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import io.zilker.bean.report_details;
import io.zilker.bean.staff_details;
import io.zilker.utility.connection;
import io.zilker.utility.queries;

public class fetch_details {
	Connection con=null;
	connection con1;
	queries query;
	public Map<Integer,report_details> fetch_hod_report(report_details report)
	{
		con1.createconnection();
		con=con1.con();
		query=new queries();
		query.initialisE_queries();
		
		Map<Integer,report_details> reports=new HashMap<Integer,report_details>();
		
		try {
			PreparedStatement st=con.prepareStatement(query.fetch_query().get("fetch_hod_report"));
			for(int dept=1;dept<=6;dept++)
			{
			st.setInt(1, dept);
			st.setString(2, report.getDate_on());
			ResultSet hod_report=st.executeQuery();
			if(hod_report.next())
			{
			report_details sample_report =new report_details();
			sample_report.setDepartment_name(hod_report.getString(1));
			sample_report.setStatus(hod_report.getInt(2));
			sample_report.setUpdated_on(hod_report.getString(3));
			reports.put(dept, sample_report);
			}
			else
			{
				reports.put(dept,null);
			}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return reports;
	}
	public staff_details get_staff_details(int staff_id)
	{
		staff_details staff=new staff_details();
		staff.setStaff_id(staff_id);
		con1=new connection();
		con1.createconnection();
		con=con1.con();
		query=new queries();
		query.initialisE_queries();
	try {
		PreparedStatement st=con.prepareStatement(query.fetch_query().get("staff"));
		
		st.setInt(1, staff_id);
		ResultSet result=st.executeQuery();
		result.next();
		staff.setDept_id(result.getInt(3));
		staff.setStaff_name(result.getString(1));
		staff.setDesig_id(result.getInt(2));
		staff.setDepartment(result.getString(4));
		staff.setDesignation(result.getString(5));
		return staff;
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return staff;
	}

	public Map<Integer,report_details> fetch_report(report_details report)
	{
		Map<Integer,report_details> reports=new HashMap<Integer,report_details>();
		con=con1.con();
		query=new queries();
		query.initialisE_queries();
		try {
			
			for(int hour=1;hour<=7;hour++)
			{
			PreparedStatement st=con.prepareStatement(query.fetch_query().get("fetch_report"));
			st.setString(1, report.getDate_on());
			st.setInt(2, hour);
			st.setInt(3, report.getYear_of_department());
			st.setInt(4, report.getDepartment());
			ResultSet report_details =st.executeQuery();
			if(report_details.next())
			{
			report_details sample_report =new report_details();
			sample_report.setStaff_id(report_details.getInt(1));
			sample_report.setStaff_name(report_details.getString(2));
			sample_report.setWork_done(report_details.getString(3));
			sample_report.setUpdated_on(report_details.getString(4));
			reports.put(hour, sample_report);
			}
			else
			{
				reports.put(hour,null);
			}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		
		 return reports;
	}
	
}

package io.zilker.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import io.zilker.bean.ReportDetails;
import io.zilker.utility.Constants;
import io.zilker.utility.GetConnection;

public class UpdateDetails {
	Connection con = null;
	GetConnection connection;
	Logger log=Logger.getLogger(UpdateDetails.class.getName());

	
	
	
	
	
	
	public String updateHodReport(ReportDetails report) {
		
		PreparedStatement statement=null;
		
		
		
		try {
			connection = new GetConnection();
			connection.createconnection();
			con = connection.getConnection();
		 statement = con.prepareStatement(Constants.HODREPORT);
			statement.setString(1, report.getDateOn());
			statement.setInt(2, report.getDepartment());
			statement.setInt(3, report.getStaffId());
			statement.setInt(4, report.getStatus());
			statement.setString(5, report.getUpdatedOn());
			statement.setInt(6, report.getYearOfDepartment());
			statement.executeUpdate();
			return "Successfully Submitted Your Report to the Database";
		} 
			catch (MySQLIntegrityConstraintViolationException e) {
			return "Duplicate report Entry please verify the details you have entered";
		}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "There was some error in Submitting your report to the database";
		} finally {
			connection.closeConnection(con,null,statement);
		}

	}
	public String editPersonalReport(ReportDetails report,int currentHour) {
		PreparedStatement statement=null;
		
		
		try {
			connection = new GetConnection();
			connection.createconnection();
			con = connection.getConnection();
			statement = con.prepareStatement(Constants.EDITPERSONALREPORT);
			statement.setInt(1, report.getHourOfClass());
			statement.setString(2, report.getWorkDone());
			statement.setString(3, report.getUpdatedOn());
			statement.setInt(4, report.getYearOfDepartment());
			statement.setInt(5, report.getDepartment());
			statement.setString(6, report.getDateOn());
			statement.setInt(7, currentHour);
			statement.setInt(8, report.getStaffId());
			statement.executeUpdate();

			return "Successfully Edited Your Report on "+report.getDateOn()+" during hour "+report.getHourOfClass()+" in the database";

		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "There was some error in editing the report";
		} finally {
			
			connection.closeConnection(con,null,statement);
		}

	}
	public String deletePersonalReport(ReportDetails report) {
		PreparedStatement statement=null;
		
		
		try {
			connection = new GetConnection();
			connection.createconnection();
			con = connection.getConnection();
			statement = con.prepareStatement(Constants.DELETEPERSONALREPORT);
			statement.setString(1, report.getDateOn());
			statement.setInt(2, report.getHourOfClass());
			statement.setInt(3, report.getStaffId());
			statement.executeUpdate();

			return "Successfully Deleted Your Report on "+report.getDateOn()+" during hour "+report.getHourOfClass()+" in the database";

		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "There was some error in deleting the report";
		} finally {
			
			connection.closeConnection(con,null,statement);
		}
	}
	
	
	public String updateReport(ReportDetails report) {
		
		
		PreparedStatement statement=null;
		
		
		try {
			System.out.println(report.getWorkDone());
			connection = new GetConnection();
			connection.createconnection();
			con = connection.getConnection();
			statement = con.prepareStatement(Constants.SUBMITREPORT);
			statement.setString(1, report.getDateOn());
			statement.setInt(2, report.getHourOfClass());
			statement.setInt(3, report.getStaffId());
			statement.setString(4, report.getWorkDone());
			statement.setString(5, report.getUpdatedOn());
			statement.setInt(6, report.getYearOfDepartment());
			statement.setInt(7, report.getDepartment());
			statement.executeUpdate();

			return "Successfully Submitted Your Report to the Database";

		} catch (MySQLIntegrityConstraintViolationException e) {
			log = Logger.getLogger(UpdateDetails.class.getName());
			log.warning("Duplicate report Entry please verify the details you have entered");
			return "Duplicate report Entry please verify the details you have entered";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "There was some error in updating your report please check if you have entered all the details in the right format";
		} finally {
			
			connection.closeConnection(con,null,statement);
		}

	}
}

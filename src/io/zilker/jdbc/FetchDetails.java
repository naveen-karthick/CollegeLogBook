package io.zilker.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import io.zilker.bean.ReportDetails;
import io.zilker.bean.StaffDetails;
import io.zilker.utility.Constants;
import io.zilker.utility.DbConnection;

public class FetchDetails {
	Connection con = null;
	DbConnection connection;
	
public boolean checkDoubleEntry(ReportDetails report) {
	connection=new DbConnection();
	connection.createconnection();
	con = connection.getConnection();

	
	PreparedStatement statement=null;
	ResultSet hodReport=null;
	

	try {
		 statement = con.prepareStatement(Constants.CHECKDOUBLEENTRY);	
			statement.setInt(1, report.getStaffId());
			statement.setInt(2, report.getHourOfClass());
			statement.setString(3, report.getDateOn());
			 hodReport = statement.executeQuery();
			if (hodReport.next()) {
				return true;
			} else {
				return false;
			}

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		connection.closeConnection(con,hodReport,statement);
	}
	
	
	
	return false;	
	}
	
	public ArrayList<ReportDetails> fetchDeptReport(ReportDetails report) {
		connection=new DbConnection();
		connection.createconnection();
		con = connection.getConnection();
		ArrayList<ReportDetails> reports = new ArrayList<ReportDetails>();
		PreparedStatement statement=null;
		ResultSet hodReport=null;
		
		try {
			 statement = con.prepareStatement(Constants.FETCHDEPTREPORT);
			for (int year = 1; year <= 4; year++) {
				statement.setInt(1, report.getDepartment());
				statement.setString(2, report.getDateOn());
				statement.setInt(3,year);
				 hodReport = statement.executeQuery();
				if (hodReport.next()) {
					ReportDetails sampleReport = new ReportDetails();
					sampleReport.setYearOfDepartment(year);
					sampleReport.setStatus(hodReport.getInt(1));
					sampleReport.setUpdatedOn(hodReport.getString(2));
					reports.add(sampleReport);
				}
				else {
					ReportDetails sampleReport = new ReportDetails();
					sampleReport.setYearOfDepartment(year);
					sampleReport.setStatus(-1);
					sampleReport.setUpdatedOn("-");
					reports.add(sampleReport);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.closeConnection(con,hodReport,statement);
		}

		return reports;
	}
	
	public ArrayList<ReportDetails> fetchPersonalReport(StaffDetails staff,ReportDetails report) {
		connection=new DbConnection();
		connection.createconnection();
		con = connection.getConnection();

		ArrayList<ReportDetails> reports = new ArrayList<ReportDetails>();
		PreparedStatement statement=null;
		ResultSet personalReport=null;
		
		try {
			 statement = con.prepareStatement(Constants.FETCHPERSONALREPORT);
				statement.setInt(1, staff.getStaffId());
				statement.setString(2, report.getDateOn());
				 personalReport = statement.executeQuery();
				while (personalReport.next()) {
					ReportDetails sampleReport = new ReportDetails();
					sampleReport.setHourOfClass(personalReport.getInt(1));
					sampleReport.setWorkDone(personalReport.getString(2));
					sampleReport.setUpdatedOn(personalReport.getString(3));
					sampleReport.setYearOfDepartment(personalReport.getInt(4));
					sampleReport.setDepartmentName(personalReport.getString(5));
					reports.add(sampleReport);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.closeConnection(con,personalReport,statement);
		}

		return reports;
		
		
		
		
	}
	
	public StaffDetails getStaffDetails(int staffId) {
		StaffDetails staff = new StaffDetails();
		staff.setStaffId(staffId);
		connection = new DbConnection();
		connection.createconnection();
		con = connection.getConnection();
		PreparedStatement statement=null;
		ResultSet result=null;
		
		try {
			 statement = con.prepareStatement(Constants.STAFF);

			statement.setInt(1, staffId);
			 result = statement.executeQuery();
			result.next();
			staff.setDeptId(result.getInt(3));
			staff.setStaffName(result.getString(1));
			staff.setDesigId(result.getInt(2));
			staff.setDepartment(result.getString(4));
			staff.setDesignation(result.getString(5));
			return staff;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.closeConnection(con,result,statement);
		}
		return staff;
	}

	public ArrayList<ReportDetails> fetchReport(ReportDetails report) {
		ArrayList<ReportDetails> reports = new ArrayList<ReportDetails>();
		connection=new DbConnection();
		connection.createconnection();
		con = connection.getConnection();
		PreparedStatement statement=null;
		PreparedStatement staffStatement=null;
		ResultSet reportDetails=null;
		ResultSet staffDetails=null;
		try {

			for (int hour = 1; hour <= 7; hour++) {
				 statement = con.prepareStatement(Constants.FETCHREPORT);
				statement.setString(1, report.getDateOn());
				statement.setInt(2, hour);
				statement.setInt(3, report.getYearOfDepartment());
				statement.setInt(4, report.getDepartment());
				 reportDetails = statement.executeQuery();
				if (reportDetails.next()) {
					ReportDetails sampleReport = new ReportDetails();
					sampleReport.setStaffId(reportDetails.getInt(1));
					sampleReport.setStaffName(reportDetails.getString(2));
					sampleReport.setWorkDone(reportDetails.getString(3));
					sampleReport.setUpdatedOn(reportDetails.getString(4));
					reports.add(sampleReport);
				} else {
					staffStatement=con.prepareStatement(Constants.FETCHSTAFFINCHARGE);
					staffStatement.setInt(1,report.getDayOfWeek());
					staffStatement.setInt(2, report.getDepartment());
					staffStatement.setInt(3, report.getYearOfDepartment());
					staffStatement.setInt(4, hour);
					
					staffDetails = staffStatement.executeQuery();
					ReportDetails sampleReport = new ReportDetails();
						if(staffDetails.next()) {
						sampleReport.setStaffId(staffDetails.getInt(1)*-1);
						sampleReport.setStaffName("Staff "+staffDetails.getString(2)+" Hasn't reported");
						sampleReport.setWorkDone("-");
						sampleReport.setUpdatedOn("-");
						reports.add(sampleReport);	
						}
						else {
							sampleReport.setStaffName("no one has reported");
							sampleReport.setWorkDone("-");
							sampleReport.setUpdatedOn("-");
							reports.add(sampleReport);
				}
			}
			}		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.closeConnection(con,reportDetails,statement);
		}

		return reports;
	}

}

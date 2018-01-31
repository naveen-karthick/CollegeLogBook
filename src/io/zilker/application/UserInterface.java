package io.zilker.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;

import io.zilker.bean.LoginDetails;
import io.zilker.bean.ReportDetails;
import io.zilker.bean.StaffDetails;
import io.zilker.ejb.EjbBean;
import io.zilker.jdbc.FetchDetails;
import io.zilker.jdbc.UpdateDetails;
import io.zilker.jdbc.ValidationOfCredentials;

public class UserInterface {
	static Logger log = Logger.getLogger(UserInterface.class.getName());
	static Scanner input = new Scanner(System.in);

	static public StaffDetails printStaffDetails(int staffId, FetchDetails details) {

		StaffDetails staff = details.getStaffDetails(staffId);

		return staff;
	}

	static public String fetchReportServlet(StaffDetails staff,HttpServletRequest request) {
		ReportDetails report=new ReportDetails();
		FetchDetails fetchDetails =new FetchDetails();
		report.setDateOn(request.getParameter("date"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date submittedDate=null;
		try {
			submittedDate = format.parse(report.getDateOn());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(submittedDate);
		report.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
		report.setDepartment(Integer.parseInt(request.getParameter("department")));
		report.setYearOfDepartment(Integer.parseInt(request.getParameter("year")));
		ArrayList<ReportDetails> sample_report = fetchDetails.fetchReport(report);
		JSONArray json=new JSONArray(sample_report);
		return json.toString();
	}
	
	static public String fetchPersonalReport(StaffDetails staff,HttpServletRequest request) {
		
		FetchDetails fetchDetails= new FetchDetails();
		ReportDetails report = new ReportDetails();
		report.setDateOn(request.getParameter("date"));
		ArrayList<ReportDetails> reportDetails = fetchDetails.fetchPersonalReport(staff, report);
		JSONArray json=new JSONArray(reportDetails);
		return json.toString();
	
	}
	
	

	static public String submitReportServlet(EjbBean ejbBean,HttpServletRequest request) {

		StaffDetails staff=ejbBean.getStaffDetails();
		ReportDetails report=ejbBean.getReportDetails();
		FetchDetails fetch=new FetchDetails();
		report.setDateOn(request.getParameter("date"));
		report.setDepartment(Integer.parseInt(request.getParameter("department")));
		report.setYearOfDepartment(Integer.parseInt(request.getParameter("year")));
		report.setHourOfClass(Integer.parseInt(request.getParameter("hour")));
		report.setDateOn(request.getParameter("date"));
		report.setDepartment(Integer.parseInt(request.getParameter("department")));
		report.setWorkDone(request.getParameter("lecture"));
		UpdateDetails updateDetails=new UpdateDetails();
		report.setStaffId(staff.getStaffId());
		SimpleDateFormat check=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat updated_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		try {
			Date submittedDate=check.parse(request.getParameter("date"));
			
			if(submittedDate.after(currentDate)) {
				return "Cannot submit for future Date";
			}
			long diff=(currentDate.getTime()-submittedDate.getTime())/(1000*60*60*24);
			if(diff>3) {
				return "You have crossed the deadline to submit the report Please contact your Dean for further query";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(fetch.checkDoubleEntry(report)) {
			return "You have already submitted your report for that hour!.";
		}
		
		
		report.setUpdatedOn(updated_on.format(currentDate));
		return  updateDetails.updateReport(report);
}



	public static String submitStatusServlet(EjbBean ejbBean,HttpServletRequest request) {
		// Update Hod status
		UpdateDetails updateDetails=new UpdateDetails();
		ReportDetails report = ejbBean.getReportDetails();
		StaffDetails staff = ejbBean.getStaffDetails();
		int status = Integer.parseInt(request.getParameter("status"));
		report.setDepartment(staff.getDeptId());
		report.setStaffId(staff.getStaffId());
		report.setStatus(status);
		report.setDateOn(request.getParameter("date"));
		report.setYearOfDepartment(Integer.parseInt(request.getParameter("year")));

		// Fetch the updated on date with time
		Date currentDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat check = new SimpleDateFormat("yyyy-MM-dd");
		report.setUpdatedOn(format.format(currentDate));
		try {
			Date submittedDate=check.parse(request.getParameter("date"));
			if(submittedDate.after(currentDate)) {
				return "Cannot submit for future Date";
			}
			long diff=(currentDate.getTime()-submittedDate.getTime())/(1000*60*60*24);
			if(diff>3) {
				return "You have crossed the deadline to submit the report Please contact your Dean for further query";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		staff.setStatus(status);
		return updateDetails.updateHodReport(report);
			
	}
	 public static String personalReportDelete(HttpServletRequest request,StaffDetails staff) {
		 ReportDetails report=new ReportDetails();
		 UpdateDetails updateDetails=new UpdateDetails();
		 report.setDateOn(request.getParameter("date"));
		 report.setHourOfClass(Integer.parseInt(request.getParameter("hour")));
		 report.setStaffId(staff.getStaffId());
		 
		 return updateDetails.deletePersonalReport(report);
		 
		 
	 }
	 public static String personalReportEdit(HttpServletRequest request,StaffDetails staff) {
		 log.info("something");
		 ReportDetails report=new ReportDetails();
		 UpdateDetails updateDetails=new UpdateDetails();
		 report.setDateOn(request.getParameter("date"));
		 report.setDepartment(Integer.parseInt(request.getParameter("department")));
		 report.setHourOfClass(Integer.parseInt(request.getParameter("hour")));
		 report.setStaffId(staff.getStaffId());
		 SimpleDateFormat updated_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date current_date = new Date();
			report.setUpdatedOn(updated_on.format(current_date));

		 report.setWorkDone(request.getParameter("lecture"));
		 report.setYearOfDepartment(Integer.parseInt(request.getParameter("year")));
		 
		 return updateDetails.editPersonalReport(report, Integer.parseInt(request.getParameter("currentHour")));		 
	 }
	 public static String fetchHodServlet(StaffDetails staff,HttpServletRequest request) {
		 ReportDetails report = new ReportDetails();
		 FetchDetails fetchDetails=new FetchDetails();
			report.setDateOn(request.getParameter("date"));
			report.setDepartment(Integer.parseInt(request.getParameter("department")));
			ArrayList<ReportDetails> reports = fetchDetails.fetchDeptReport(report);
			JSONArray json=new JSONArray(reports);
			return json.toString();
	 }
	
	
	public static EjbBean getStaffDetails(int staffId) {
		EjbBean ejbBean=new EjbBean();
		FetchDetails fetchDetails = new FetchDetails();
		// printing Welcome message
		// staff_details staff=print_staff_details(staffId,Fetch_details);
		StaffDetails staff = printStaffDetails(staffId, fetchDetails);
		ejbBean.setStaffDetails(staff);
		return ejbBean;
	}
	
	public static int checkLogin(HttpServletRequest request) {
		ValidationOfCredentials validation = new ValidationOfCredentials();
		LoginDetails user = new LoginDetails();
		user.setEmailId(request.getParameter("email"));
		user.setPasword(request.getParameter("password"));
		// checking for valid login id and password
		int staffId = validation.verifyLoginCredentials(user);
		return staffId;
	}



}

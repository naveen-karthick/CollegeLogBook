package io.zilker.application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

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

		// Fetch Staff details and store it in the staff bean class
		StaffDetails staff = details.getStaffDetails(staffId);

		// Printing the welcome message
		log.info("Welcome " + staff.getStaffName() + "," + staff.getDesignation() + " of the " + staff.getDepartment()
				+ " Department");
		return staff;
	}

	static public String fetchReportServlet(ReportDetails report,HttpServletRequest request) {
		
		FetchDetails fetchDetails =new FetchDetails();
		report.setDateOn(request.getParameter("date"));
		report.setDepartment(Integer.parseInt(request.getParameter("department")));
		report.setYearOfDepartment(Integer.parseInt(request.getParameter("year")));
		Map<Integer, ReportDetails> sample_report = fetchDetails.fetchReport(report);
		String s="[";
		for (int hour = 1; hour <= 7; hour++) {
			if (sample_report.get(hour) == null) {
				
					
					if(hour!=1) {
						s+=","; }
					
					s+="{ \"workDone\": \"-\" ,\"staff\": \"no one has reported\",\"updatedOn\" :\"-\",\"staffId\":\"-\" }";
				} 

				
			 else {
				 System.out.println("hello");
					if(hour!=1) {
					s+=","; }
					s+="{ \"workDone\": \""+sample_report.get(hour).getWorkDone()+"\", \"staff\" : \""+sample_report.get(hour).getStaffName()+"\",\"updatedOn\":\""+sample_report.get(hour).getUpdatedOn()+"\",\"staffId\":\""+sample_report.get(hour).getStaffId()+"\"}";	

			
					}
			
		}
		s+="]";
		return s;
	}
	
	static public String fetchPersonalReport(StaffDetails staff,HttpServletRequest request) {
		
		FetchDetails fetchDetails= new FetchDetails();
		ReportDetails report = new ReportDetails();
		report.setDateOn(request.getParameter("date"));
		ArrayList<ReportDetails> reportDetails = fetchDetails.fetchPersonalReport(staff, report);
		String s="[";
		for(int i=0;i<reportDetails.size();i++) {
			if(i>0) {
				s+=",";
			}
			s+="{ \"hour\": \""+reportDetails.get(i).getHourOfClass()+"\", \"workDone\" : \""+reportDetails.get(i).getWorkDone()+"\",\"updatedOn\":\""+reportDetails.get(i).getUpdatedOn()+"\",\"year\":\""+reportDetails.get(i).getYearOfDepartment()+"\",\"department\":\""+reportDetails.get(i).getDepartmentName()+"\"}";	
				
		}
		s+="]";
		return s;
	
	}
	
	
	static public void fetchReport(FetchDetails fetchDetails) {
		// Fetching class report
		ReportDetails report = new ReportDetails();
		log.info("please Enter the date on which you want the report on in the format yyyy-mm-dd");
		report.setDateOn(input.next());
		log.info("Enter the department of which you want the report of"
				+ "\n1-ECE\n2-CSE\n3-IT\n4-MECH\n5-CIVIL\n6-EEE");
		report.setDepartment(input.nextInt());
		log.info("Enter the year of the department");
		report.setYearOfDepartment(input.nextInt());
		// Mapping every hour status to a report_details object
		Map<Integer, ReportDetails> sample_report = fetchDetails.fetchReport(report);
		String s="{";
		for (int hour = 1; hour <= 7; hour++) {
			if (sample_report.get(hour) == null) {
				FileWriter writer;
				try {
					writer = new FileWriter("C:\\Users\\Naveen Karthick\\Desktop\\report_" + report.getDepartment()
							+ "_" + report.getDateOn() + ".txt", true);

					BufferedWriter bw = new BufferedWriter(writer);
					bw.newLine();
					bw.write("Hour : " + hour + " -- no one has reported");
					bw.flush();
					bw.close();
					
					if(hour!=1) {
						s+=","; }
					
					s+=" \"hour"+ hour+"\":"+"{ \"workDone\": ,\"staff\": \"no one has reported\" }";
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				log.info("Hour : " + hour + " -- no one has reported");
			} else {
				FileWriter writer;
				try {
					writer = new FileWriter("C:\\Users\\Naveen Karthick\\Desktop\\report_" + report.getDepartment()
							+ "_" + report.getDateOn() + ".txt", true);

					BufferedWriter bw = new BufferedWriter(writer);
					bw.newLine();
					bw.write("Hour : " + hour + "\n" + "		Reported by : " + sample_report.get(hour).getStaffName()
							+ "\n		Lecture on : " + sample_report.get(hour).getWorkDone() + "\n	Updated on : "
							+ sample_report.get(hour).getUpdatedOn() + "\n	Staff_id : "
							+ sample_report.get(hour).getStaffId());
					bw.flush();
					bw.close();
					if(hour!=1) {
					s+=","; }
					s+="\"hour"+ hour+"\":"+"{ \"workDone\": \""+sample_report.get(hour).getWorkDone()+"\", \"staff\" : \""+sample_report.get(hour).getStaffName()+"\"}";	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("Hour : " + hour + "\n" + "Reported by : " + sample_report.get(hour).getStaffName()
						+ "\nLecture on : " + sample_report.get(hour).getWorkDone() + "\nUpdated on : "
						+ sample_report.get(hour).getUpdatedOn() + "\nStaff_id : "
						+ sample_report.get(hour).getStaffId());
			}
			
		}
		s+="}";
		System.out.println(s);
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

	
	static public String submitReport(StaffDetails staff, UpdateDetails updateDetails) {
		ReportDetails report = new ReportDetails();
		report.setStaffId(staff.getStaffId());
		log.info("please Enter the department you took a lecture in" + "\n1-ECE\n2-CSE\n3-IT\n4-MECH\n5-CIVIL\n6-EEE");
		report.setDepartment(input.nextInt());
		log.info("Enter the year of the department");
		report.setYearOfDepartment(input.nextInt());
		log.info("Enter the hour of class");
		report.setHourOfClass(input.nextInt());
		input.nextLine();
		log.info("Please Enter sufficient info about the lecture");
		report.setWorkDone(input.nextLine());
		SimpleDateFormat Date_of_work = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat updated_on = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date current_date = new Date();

		report.setDateOn(Date_of_work.format(current_date));
		report.setUpdatedOn(updated_on.format(current_date));

		return updateDetails.updateReport(report);
		
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
	 public static String fetchHodServlet(ReportDetails report,HttpServletRequest request) {
		 
		 FetchDetails fetchDetails=new FetchDetails();
			report.setDateOn(request.getParameter("date"));
			report.setDepartment(Integer.parseInt(request.getParameter("department")));
			Map<Integer, ReportDetails> reports = fetchDetails.fetchDeptReport(report);
			String s="[";
			for (int year = 1; year <= 4; year++) {
				if(year!=1) {
					s+=",";
				}
				
				
				if (!(reports.get(year)== null)) {
					String status = reports.get(year).getStatus() == 1 ? "Validated" : "Pending";
					s+="{\"statusReport\":\""+status+"\",\"updatedOn\":\""+reports.get(year).getUpdatedOn()+"\"}";
					
				}
				else {
					s+="{\"statusReport\":\"No Entry\",\"updatedOn\":\"-\"}";
				}
			}
			s+="]";
			System.out.println(s);
			return s;
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

	
	
	public static void main(String[] args) {

		while (true) {
			String emailId;
			String password;

			// Get the user_id and password to log in

			if (args.length == 0) {
				log.info("Enter the email_id and Password to login to your portal");
				emailId = input.next();
				password = input.next();
			} else {
				emailId = args[0];
				password = args[1];
			}
			// creating object for validation for validation of email and user-id with
			// password is authentic
			ValidationOfCredentials validation = new ValidationOfCredentials();
			if (!validation.verifyEmail(emailId)) {
				log.warning("The email-id is not in the right format");
				continue;
			}
			// creating object for storing login credentials
			LoginDetails user = new LoginDetails();
			user.setEmailId(emailId);
			user.setPasword(password);

			// checking for valid login id and password
			int staffId = validation.verifyLoginCredentials(user);
			if (staffId < 1) {
				log.warning("Invalid email_id and password combination please try again");
				continue;
			}

			UpdateDetails updateDetails = new UpdateDetails();
			FetchDetails fetchDetails = new FetchDetails();
			// printing Welcome message
			// staff_details staff=print_staff_details(staffId,Fetch_details);
			StaffDetails staff = printStaffDetails(staffId, fetchDetails);

			switch (staff.getDesigId()) {

			// Assisstant Professor Functionality
			case 3: {
				while (true) {
					log.info("Press \n0 - To  Log out \n1 - To submit a report");
					if (input.nextInt() == 0) {
						break;
					}
					submitReport(staff, updateDetails);

				}
				break;
			}

			// HOD functionality
			case 2: {
				boolean logIn = true;
				while (true) {
					log.info(
							"Do want to \n0-To Log out \n1-view the log report of a class for the day?\n2-update your report\n3-update status");
					switch (input.nextInt()) {
					case 0: {
						logIn = false;
					}
					case 1: {
						fetchReport(fetchDetails);
						break;
					}
					case 2: {
						submitReport(staff, updateDetails);
						break;
					}
					case 3: {
						// Update Hod status

						ReportDetails report = new ReportDetails();

						log.info("Type\n1 - If you have viewed every class under your department's log"
								+ "\n0 - if the work is bending ");
						int status = input.nextInt();
						report.setDepartment(staff.getDeptId());
						report.setStaffId(staff.getStaffId());
						report.setStatus(status);
						log.info("Enter the date of your report in yyyy-mm-dd format");
						report.setDateOn(input.next());

						// Fetch the updated on date with time
						Date date = new Date();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						report.setUpdatedOn(format.format(date));

						staff.setStatus(status);
						log.info(updateDetails.updateHodReport(report));
						break;
					}
					}
					if (!logIn) {
						break;
					}
				}

			}

			// DEEN functionality
			case 1: {
				boolean logIn = true;
				while (true) {
					log.info("Welcome " + staff.getStaffName() + " Deen of the college");

					log.info(
							"Do want to \n0-To log out \n1-view the log report of a class for the day?\n2-view Hod Report");
					int n = input.nextInt();
					switch (n) {
					case 0: {
						logIn = false;
						break;
					}
					case 1: {
						fetchReport(fetchDetails);
						break;
					}
					case 2: {
						// Fetch Hod report
						log.info("please Enter the date in the format yyyy-mm-dd on which you want the hod report");
						ReportDetails report = new ReportDetails();
						report.setDateOn(input.next());
						Map<Integer, ReportDetails> reports = fetchDetails.fetchHodReport(report);
						for (int dept = 1; dept <= 6; dept++) {
							if (!(reports.get(dept) == null)) {
								String status = reports.get(dept).getStatus() == 1 ? "Validated" : "Pending";
								log.info("Department : " + reports.get(dept).getDepartmentName() + ""
										+ "\n Status report : " + status + "" + "\nUpdated on : "
										+ reports.get(dept).getUpdatedOn());
							}
						}
						break;
					}
					}
					if (!logIn) {
						break;
					}
				}

			}
			}
		}
	}

}

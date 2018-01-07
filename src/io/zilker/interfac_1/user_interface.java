package io.zilker.interfac_1;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import io.zilker.bean.login_details;
import io.zilker.bean.report_details;
import io.zilker.bean.staff_details;
import io.zilker.jdbc.fetch_details;
import io.zilker.jdbc.update_details;
import io.zilker.jdbc.validation;

public class user_interface {
	static Logger log=Logger.getLogger(user_interface.class.getName());
	static Scanner input=new Scanner(System.in);

	
	static public staff_details print_staff_details(int id,fetch_details details)
	{
		
		staff_details staff=details.get_staff_details(id);
		
		log.info("Welcome "+staff.getStaff_name()+","+staff.getDesignation()+" of the "+staff.getDepartment()+" Department");
		return staff;
	}
	static public void fetch_report(fetch_details Fetch_details)
	{
		report_details report=new report_details();
		log.info("please Enter the date on which you want the report on in the format yyyy-mm-dd");
		report.setDate_on(input.next());
		log.info("Enter the department of which you want the report of"
				+"\n1-ECE\n2-CSE\n3-IT\n4-MECH\n5-CIVIL\n6-EEE");
		report.setDepartment(input.nextInt());
		log.info("Enter the year of the department");
		report.setYear_of_department(input.nextInt());
		
		
		Map<Integer,report_details> sample_report=Fetch_details.fetch_report(report);
		for(int hour=1;hour<=7;hour++)
		{
		if(sample_report.get(hour)==null)
		{
			log.info("Hour : "+hour+" -- no one has reported");
		}
		else
		{
			log.info("Hour : "+hour+"\n"
					+ "Reported by : "+sample_report.get(hour).getStaff_name()+
					"\nLecture on : "+sample_report.get(hour).getWork_done()
					+"\nUpdated on : "+sample_report.get(hour).getUpdated_on()
					+"\nStaff_id : "+sample_report.get(hour).getStaff_id());
		}
		
		}
		
		
	}
	static public boolean submit_report(staff_details staff,update_details Update_details)
	{
		report_details report=new report_details();
		report.setStaff_id(staff.getStaff_id());
		log.info("please Enter the department you took a lecture in"
				+ "\n1-ECE\n2-CSE\n3-IT\n4-MECH\n5-CIVIL\n6-EEE");
		report.setDepartment(input.nextInt());
		log.info("Enter the year of the department");
		report.setYear_of_department(input.nextInt());
		log.info("Enter the hour of class");
		report.setHour_of_class(input.nextInt());
		input.nextLine();
		log.info("Please Enter sufficient info about the lecture");
		report.setWork_done(input.nextLine());
		SimpleDateFormat Date_of_work=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat updated_on=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date current_date=new Date();
		
		report.setDate_on(Date_of_work.format(current_date));
		report.setUpdated_on(updated_on.format(current_date));
		
		boolean updation_successful=Update_details.update_report(report);
		if(updation_successful)
		{
			log.info("Succesfully submmitted your report to the database");
			
		}
		else
		{
			log.warning("There was some error while submitting your report please bare with us" );
			
		}
		return updation_successful;
	}
	public static void main(String[] args)
	{
		while(true)
		{
			
			//Get the user_id and password to log in
		log.info("Enter the email_id and Password to login to your portal");
		String email_id=input.next();
		String password=input.next();
		
		//creating object for validation for  validation of email and user-id with password is authentic
		validation val=new validation();
		if(!val.check_email(email_id))
		{
			log.warning("The email-id is not in the right format");
			continue;
		}
		//creating object for storing login credentials
		login_details log1=new login_details();
		log1.setEmail_id(email_id);
		log1.setPass(password);
		
		//checking for valid login id and password
		int id=val.check_login(log1);
		if(id<1)
		{
			log.warning("Invalid email_id and password combination please try again");
			break;
		}
			update_details Update_details=new update_details();
		fetch_details Fetch_details=new fetch_details();
		staff_details staff=print_staff_details(id,Fetch_details);
		switch(staff.getDesig_id())
		{
		
		
		
		case 3 :
		{	
		
		
				submit_report(staff,Update_details);
				
				
				break;
			}
		case 2 :		
		{
			log.info("Do want to \n1-view the log report of a class for the day?\n2-update your report\n3-update status");
			switch(input.nextInt())
			{
			case 1:
			{
						fetch_report(Fetch_details);
						break;
			}
			case 2 :
			{
				submit_report(staff,Update_details);
				break;
			}
			case 3 :
			{
				report_details report=new report_details();
				
				log.info("Type\n1 - If you have viewed every class under your department's log"
						+ "0 - if the work is bending ");
				int status=input.nextInt();
				report.setDepartment(staff.getDept_id());
				report.setStaff_id(staff.getStaff_id());
				report.setStatus(status);
				log.info("Enter the date of your report in yyyy-mm-dd format");
				report.setDate_on(input.next());
				Date date=new Date();
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				report.setUpdated_on(format.format(date));
				
				staff.setStatus(status);
						if(Update_details.update_hod_report(report))
						{
							log.info("Report successfully submitted to the database");
						}
						else
						{
							log.info("There was an error in the updation please bare with us");
			
						}
						break;
			}
			
			}
		break;
		}
		case 1:
		{
			log.info("Welcome "+staff.getStaff_name()+" Deen of the college" );
		
			log.info("Do want to \n1-view the log report of a class for the day?\n2-view Hod Report");
			int n=input.nextInt();
			switch(n)
			{
			case 1 :
			{
				fetch_report(Fetch_details);
				break;
			}
			case 2 :
			{
				log.info("please Enter the date in the format yyyy-mm-dd on which you want the hod report");
				report_details report=new report_details();
				report.setDate_on(input.next());
				Map<Integer,report_details> reports =Fetch_details.fetch_hod_report(report);
				for(int dept=1;dept<=6;dept++)
				{
					if(!(reports.get(dept)==null))
				{
					String status=reports.get(dept).getStatus()==1?"Validated":"Pending";
					log.info("Department : "+reports.get(dept).getDepartment_name()+""
							+ "\n Status report : "+status+""
							+"\nUpdated on : "+reports.get(dept).getUpdated_on());
				}
					
				}
				break;
			}
			
			
			}
		}
		break;
		}	
		break;
		}
		}
		
	}
	
	
	


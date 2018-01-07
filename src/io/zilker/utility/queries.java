package io.zilker.utility;

import java.util.HashMap;
import java.util.Map;

public class queries {

	Map<String, String >queries;
	public Map<String,String> fetch_query() {
		
		
		return queries;
	}
	public void initialisE_queries()
	{
		queries=new HashMap<String,String>();
		queries.put("login", "select staff_id from login where email_id=? and pass=?");
		queries.put("staff", "select  staff_details.staff_name,\r\n" + 
				"staff_details.designation,staff_details.dept_id,\r\n" + 
				"departments.dept_name,designations.designation from \r\n" + 
				"staff_details join departments join designations on staff_details.dept_id=departments.dept_id and staff_details.designation=designations.designation_id \r\n" + 
				"where staff_details.college_id=?");
		queries.put("submit_report", "INSERT INTO `college_logbook`.`staff_report` (`date_on`, `hour_on`, `staff_id`, `work_done`, `updated_on`, `year_1`, `dept_id`) VALUES (?, ?, ?, ?, ?, ?, ?)");
		queries.put("fetch_report", "select staff_report.staff_id,staff_details.staff_name,staff_report.work_done,staff_report.updated_on from staff_details join staff_report on staff_details.college_id=staff_report.staff_id where staff_report.date_on=? and staff_report.hour_on=? and staff_report.year_1=? and staff_report.dept_id=?");
		queries.put("Hod_report", "INSERT INTO `college_logbook`.`hod_report` (`date_on`, `dept_id`, `Hod_id`, `status_report`, `updated_on`) VALUES (?, ?, ?, ?, ?)");
		queries.put("fetch_hod_report", "SELECT  departments.dept_name,hod_report.status_report,hod_report.updated_on FROM hod_report join departments on hod_report.dept_id=departments.dept_id where hod_report.dept_id=? and hod_report.date_on=?");
	}
	

	
}

package io.zilker.utility;

public class Constants {
	
public static final String LOGIN="select staff_id from login where email_id=? and pass=?";
public static final String STAFF="select  staff_details.staff_name,staff_details.designation,staff_details.dept_id,departments.dept_name,designations.designation from staff_details join departments join designations on staff_details.dept_id=departments.dept_id and staff_details.designation=designations.designation_id where staff_details.staff_id=?";
public static final String SUBMITREPORT="INSERT INTO `college_logbook`.`staff_report` (`date_on`, `hour_on`, `staff_id`, `work_done`, `updated_on`, `year_1`, `dept_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
public static final String FETCHREPORT="select staff_report.staff_id,staff_details.staff_name,staff_report.work_done,staff_report.updated_on from staff_details join staff_report on staff_details.staff_id=staff_report.staff_id where staff_report.date_on=? and staff_report.hour_on=? and staff_report.year_1=? and staff_report.dept_id=?";
public static final String HODREPORT="INSERT INTO `college_logbook`.`hod_report` (`date_on`, `dept_id`, `staff_id`, `status_report`, `updated_on`,`year_1`) VALUES (?, ?, ?, ?, ?,?)";
public static final String FETCHHODREPORT="SELECT  departments.dept_name,hod_report.status_report,hod_report.updated_on FROM hod_report join departments on hod_report.dept_id=departments.dept_id where hod_report.dept_id=? and hod_report.date_on=?";
public static final String CHECKDOUBLEENTRY="SELECT * FROM staff_report where staff_id=? and hour_on=? and date_on=?";
public static final String FETCHDEPARTMENT="select dept_name from departments where dept_id=?";
public static final String FETCHDEPTREPORT="SELECT status_report,updated_on from hod_report where dept_id=? and date_on=? and year_1=?";
public static final String FETCHPERSONALREPORT= "SELECT staff_report.hour_on,staff_report.work_done,staff_report.updated_on,staff_report.year_1,departments.dept_name FROM staff_report join departments on departments.dept_id=staff_report.dept_id  where staff_id=? and date_on=? ";
public static final String DELETEPERSONALREPORT="DELETE FROM `college_logbook`.`staff_report` WHERE `date_on`=? and`hour_on`=? and `staff_id`=?";
public static final String EDITPERSONALREPORT="UPDATE `college_logbook`.`staff_report` SET `hour_on`=?, `work_done`=?,`updated_on`=? , `year_1`=?, `dept_id`=? WHERE `date_on`=? and`hour_on`=? and `staff_id`=?";
public static final String FETCHSTAFFINCHARGE ="SELECT distinct timetable_class.staff_id , staff_details.staff_name from timetable_class join timetable join staff_details  on timetable.timetable_id=timetable_class.timetable_id and staff_details.staff_id=timetable_class.staff_id where timetable.day_of_week=? and timetable.dept_id =? and timetable.year_1 =? and timetable_class.hour_1=?"; 
	
}

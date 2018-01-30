package io.zilker.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import io.zilker.bean.LoginDetails;
import io.zilker.bean.ReportDetails;
import io.zilker.bean.StaffDetails;

/**
 * Session Bean implementation class EjbBean
 */
@Stateless
@LocalBean
public class EjbBean {

	private LoginDetails loginDetails;
	private ReportDetails reportDetails;
	private StaffDetails staffDetails;
	public LoginDetails getLoginDetails() {
		return loginDetails;
	}
	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}
	public ReportDetails getReportDetails() {
		return reportDetails;
	}
	public void setReportDetails(ReportDetails reportDEetails) {
		this.reportDetails = reportDEetails;
	}
	public StaffDetails getStaffDetails() {
		return staffDetails;
	}
	public void setStaffDetails(StaffDetails staffDetails) {
		this.staffDetails = staffDetails;
	}
	
    public EjbBean() {
        // TODO Auto-generated constructor stub
    }

}

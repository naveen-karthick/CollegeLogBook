package io.zilker.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.zilker.application.UserInterface;
import io.zilker.bean.ReportDetails;
import io.zilker.bean.StaffDetails;
import io.zilker.ejb.EjbBean;

/**
 * Servlet implementation class ReportServlet
 */
@WebServlet("/reportservlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().println("hello from report "+request.getParameter("date")+" "+request.getParameter("year")+" "+request.getParameter("department"));
		HttpSession session = request.getSession();
		
		if(request.getParameter("id")==null) {
			
			if(session.getAttribute("staff")==null) {
			response.sendRedirect("login.jsp");
			}
			else {
				StaffDetails staff=(StaffDetails)session.getAttribute("staff");
				if(staff.getDesigId()==2) {
					response.sendRedirect("hod.jsp");
				}
			}
		}
		else {
		EjbBean ejbBean =new EjbBean();		
			if(request.getParameter("id").equals("fetch")) {
		ejbBean.setReportDetails(new ReportDetails());
		response.getWriter().println(UserInterface.fetchReportServlet(ejbBean.getReportDetails(),request));
			}
			else if(request.getParameter("id").equals("submit")) {
			ejbBean.setReportDetails(new ReportDetails());
			ejbBean.setStaffDetails((StaffDetails) session.getAttribute("staff"));
			response.getWriter().print(UserInterface.submitReportServlet(ejbBean,request));
			
			}
			else if(request.getParameter("id").equals("status")) {
			ejbBean.setReportDetails(new ReportDetails());
			ejbBean.setStaffDetails((StaffDetails) session.getAttribute("staff"));
			response.getWriter().print(UserInterface.submitStatusServlet(ejbBean,request));
			
			
			}
			else if(request.getParameter("id").equals("fetchHod")) {
			ejbBean.setReportDetails(new ReportDetails());
			response.getWriter().print(UserInterface.fetchHodServlet(ejbBean.getReportDetails(),request));
			
			
			}
			else if(request.getParameter("id").equals("set")) {
			session.setAttribute("language", request.getParameter("language"));
			System.out.println(session.getAttribute("language"));
			}
			else if(request.getParameter("id").equals("fetchPersonal")) {
			
				response.getWriter().print(UserInterface.fetchPersonalReport((StaffDetails) session.getAttribute("staff"), request));
					
			}
			else if(request.getParameter("id").equals("delete")) {
				
				response.getWriter().print(UserInterface.personalReportDelete(request, (StaffDetails) session.getAttribute("staff")));
					
			}
			else if(request.getParameter("id").equals("edit")) {
				
				response.getWriter().print(UserInterface.personalReportEdit(request, (StaffDetails) session.getAttribute("staff")));
					
			}
			else if(request.getParameter("id").equals("logout")) {
			session.removeAttribute("staff");
			session.invalidate();
			response.sendRedirect("login.jsp");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

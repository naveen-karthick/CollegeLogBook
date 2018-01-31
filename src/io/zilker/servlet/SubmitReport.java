package io.zilker.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class SubmitReport
 */
@WebServlet("/submit")
public class SubmitReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
HttpSession session = request.getSession();
		
		if(session.getAttribute("staff")==null) {
			RequestDispatcher dispatcher =request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			}
		else if(request.getParameter("id")==null) {
				RequestDispatcher dispatcher =request.getRequestDispatcher("jsps/hod.jsp");
				dispatcher.forward(request, response);
				
			}
		else {
			EjbBean ejbBean =new EjbBean();
			 if(request.getParameter("id").equals("lectureReport")) {
			ejbBean.setReportDetails(new ReportDetails());
			ejbBean.setStaffDetails((StaffDetails) session.getAttribute("staff"));
			response.getWriter().print(UserInterface.submitReportServlet(ejbBean,request));
			
			}
			 else if(request.getParameter("id").equals("hodReport")) {
			ejbBean.setReportDetails(new ReportDetails());
			ejbBean.setStaffDetails((StaffDetails) session.getAttribute("staff"));
			response.getWriter().print(UserInterface.submitStatusServlet(ejbBean,request));
			
			
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

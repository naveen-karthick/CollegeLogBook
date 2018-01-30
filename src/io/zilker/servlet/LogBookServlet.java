package io.zilker.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.zilker.application.UserInterface;
import io.zilker.bean.StaffDetails;
import io.zilker.ejb.EjbBean;

/**
 * Servlet implementation class LogBookServlet
 */
@WebServlet("/log")
public class LogBookServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LogBookServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession(true);
//		System.out.println("hello");
		int staffId;
		if(session.getAttribute("staff")==null) {
		 staffId=UserInterface.checkLogin(request);
		if(staffId>0) {
			EjbBean ejbBean = UserInterface.getStaffDetails(staffId);
			session.setAttribute("staff", ejbBean.getStaffDetails());
			RequestDispatcher dispatcher= request.getRequestDispatcher("hod.jsp");
			dispatcher.forward(request, response);			
		}
		else {
			response.getWriter().println("Error In Logging in");
			RequestDispatcher dispatcher= request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		}
		else {
				RequestDispatcher dispatcher= request.getRequestDispatcher("hod.jsp");
				dispatcher.forward(request, response);		
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




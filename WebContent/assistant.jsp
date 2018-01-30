<%@page import="io.zilker.bean.StaffDetails"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<jsp:useBean id="ejbBean" scope="request" class="io.zilker.ejb.EjbBean" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
<link rel="stylesheet" type="text/css" href="hod.css">
<%
HttpServletResponse httpResponse = (HttpServletResponse)response;

httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); 
response.addHeader("Cache-Control", "post-check=0, pre-check=0");
httpResponse.setHeader("Pragma","no-cache"); 
httpResponse.setDateHeader ("Expires", 0); 
StaffDetails staff=(StaffDetails) session.getAttribute("staff");
if(staff==null) { 
	response.sendRedirect("login.jsp");
	} 
else if(staff.getDesigId()==2) {
	response.sendRedirect("hod.jsp");
	}
else if(staff.getDesigId()==1) {
	response.sendRedirect("deen.jsp");
	}

	%>
	<fmt:setLocale value="${sessionScope.language}"/>
      <fmt:bundle basename = "io.zilker.internationalization.Example" >
         <fmt:message key = "welcome" var="welcome"/>
         <fmt:message key = "college" var="college"/>
         <fmt:message key = "logbook" var="logbook"/>
         <fmt:message key = "monitoring" var="monitoring"/>
         <fmt:message key = "department" var="department"/>
         <fmt:message key = "filldetails" var="details"/>
      </fmt:bundle>
</head>
<body>
<h1>${college} ${logbook} ${monitoring} </h1>
<header>
<div class="header">
		<button class="selected" id="submitReport-button">Submit Lecture report</button>
		<a href="reportservlet?id=logout"><button id="log-out">Log out</button></a>
</div>
<h3>${welcome} ${sessionScope.staff.getStaffName() } ${sessionScope.staff.getDesignation()} of the ${college} </h3>
</header>
<div id="check-report" class="report-section">
<div class="submitReport report" id="submitReport">
<h2>Please fill in the details for the report </h2>
	<label>Date of Report  : </label>
	<input type="text" name="date" id="submit-date" placeholder="YYYY-MM-DD"><br><br>
	<label>Department Year : </label>
	<select name="year" id="submit-year">
  		<option value="1">1</option>
  		<option value="2">2</option>
  		<option value="3">3</option>
  		<option value="4">4</option>
  	</select><br><br>
	<label>Department Id : </label>
	<select name="department" id="submit-department">
  		<option value="1">ECE</option>
  		<option value="2">CSE</option>
  		<option value="3">IT</option>
  		<option value="4">MECH</option>
  		<option value="5">CIVIL</option>
  		<option value="6">EEE</option>
	</select><br><br>
	<label>Hour on : </label>
	<select name="hour" id="submit-hour">
  		<option value="1">1</option>
  		<option value="2">2</option>
  		<option value="3">3</option>
  		<option value="4">4</option>
  		<option value="2">5</option>
  		<option value="3">6</option>
  		<option value="4">7</option>
  	</select><br><br>
	<label>Lecture on : </label>
	<input type="text" name="lecture" id="submit-lecture"><br><br>
	<button id="submit-Report" value="Submit">Submit</button>
</div>
<div id="submitReport-response" class="table display-none"></div>
</div>
<script src="assistant.js"></script>
</body>


</html>	
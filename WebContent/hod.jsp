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
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Welcome</title>
<link rel="stylesheet" type="text/css" href="hod.css">
<%
HttpServletResponse httpResponse = (HttpServletResponse)response;

httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); 
response.addHeader("Cache-Control", "post-check=0, pre-check=0");
httpResponse.setHeader("Pragma","no-cache"); 
httpResponse.setDateHeader ("Expires", 0); 
StaffDetails staff=(StaffDetails) session.getAttribute("staff");
if(request.getAttribute("language")==null) {
	request.setAttribute("language", "en_US");
}
if(staff==null) { 
	response.sendRedirect("login.jsp");
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
<jsp:include page="header.jsp">
	<jsp:param name="college" value="${college}"/>
	<jsp:param name="logbook" value="${logbook}"/>
	<jsp:param name="monitoring" value="${monitoring}"/>
</jsp:include>
    <nav id="navigator" class="navigation">
        <ul>
        <li><a class="selected" id="homePage-button">Home Page</a></li>
        	<c:if test="${staff.getDesigId()==1 }">
        		<li><a  id="fetchStatus-button">Fetch Hod report</a></li>
        	</c:if>
       		 <c:if test="${staff.getDesigId()>=2 }">
        		<li><a  id="submitReport-button">Submit Lecture report</a></li>
        	</c:if>
        	<c:if test="${staff.getDesigId()<=2}">
        		<li><a id="fetchReport-button">Fetch  Lecture report</a></li>
        	</c:if>
        	<c:if test="${staff.getDesigId()>=2}">
        		<li><a id="fetchPersonalReport-button">Fetch  Personal report</a></li>
        	</c:if>
        	<c:if test="${staff.getDesigId()==2}">
        		<li><a id="submitStatus-button">Submit Hod Status</a></li>
        	</c:if>
        		<li><a href="reportservlet?id=logout" id="log-out">Log out</a></li>
        </ul>
    </nav>    
<div id="check-report" class="report-section">
		<div class="homePage report" id="homePage">
		<c:choose>
			<c:when test="${staff.getDesigId()!=1}">
				<h2>${welcome} ${sessionScope.staff.getStaffName() } , ${sessionScope.staff.getDesignation()} of the ${sessionScope.staff.getDepartment()} department</h2>
			</c:when>
			<c:otherwise>
				<h2>${welcome} ${sessionScope.staff.getStaffName() } , ${sessionScope.staff.getDesignation()} of the ${college } </h2>
			</c:otherwise>
		</c:choose>
		</div>
		<div id="homePage-response"></div>
		<c:if test="${staff.getDesigId()==1 }">
		<div class="fetchStatus report display-none" id="fetchStatus">
<h2>Please fill in the details for the report </h2>
	<label>Date of Report  : </label>
	<input type="date" name="date" id="fetch-status-date" placeholder="YYYY-MM-DD"><br><br>
	<label>Department : </label>
		<select name="department" id="fetch-status-department">
  				<option value="1">ECE</option>
  				<option value="2">CSE</option>
  				<option value="3">IT</option>
  				<option value="4">MECH</option>
  				<option value="5">CIVIL</option>
  				<option value="6">EEE</option>
			</select><br><br>
			<button id="fetch-Status" value="Submit">Submit</button>
		</div>
		<div id="fetchStatus-response" class="table display-none"></div>
		</c:if>
		<c:if test="${staff.getDesigId()>=2 }">
		<div class="fetchPersonalReport report display-none" id="fetchPersonalReport">
			<h2>Please fill in the details to check your report </h2>
			<label>Date of Report  : </label>
			<input type="date" name="date" id="fetch-personal-report-date" placeholder="YYYY-MM-DD"><br><br>
			<button id="fetch-Personal-Report" value="Submit">Submit</button>
		</div>
		<div id="fetchPersonalReport-response" class="table response display-none"></div>
		<div id="editPersonalReport" class="editReport report display-none">
		<div class="form">
		<span class="close">&times;</span>
			<h2>Make the changes to your report</h2>
		            <label>Department Year : </label>
            <select name="year" id="edit-year">
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
              </select><br><br>
            <label>Department Id : </label>
            <select name="department" id="edit-department">
                  <option value="1">ECE</option>
                  <option value="2">CSE</option>
                  <option value="3">IT</option>
                  <option value="4">MECH</option>
                  <option value="5">CIVIL</option>
                  <option value="6">EEE</option>
            </select><br><br>
            <label>Hour on : </label>
            <select name="hour" id="edit-hour">
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                  <option value="6">6</option>
                  <option value="7">7</option>
              </select><br><br>
            <label for="submit-lecture">Lecture on : </label>
            <textarea  rows="3" cols="30" name="lecture" id="edit-lecture"></textarea><br><br>
            <button id="edit-Report" value="Submit">Submit Changes</button>
		</div>
		</div>
		</c:if>
		<c:if test="${staff.getDesigId()>=2 }">
        <div class="submitReport report display-none" id="submitReport">
        <h2>${details} </h2>
            <label>Date of Report  : </label>
            <input type="date" name="date" id="submit-date" placeholder="YYYY-MM-DD"><br><br>
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
                  <option value="5">5</option>
                  <option value="6">6</option>
                  <option value="7">7</option>
              </select><br><br>
            <label for="submit-lecture">Lecture on : </label>
            <textarea  rows="3" cols="30" name="lecture" id="submit-lecture"></textarea><br><br>
            <button id="submit-Report" value="Submit">Submit</button>
        </div>
        <div id="submitReport-response" class="table display-none"></div>
        </c:if>
        <c:if test="${staff.getDesigId()<=2}">
        <div class="fetchReport display-none report" id="fetchReport">
            <h2>${details } </h2>
                <label>Date of Report  : </label>
                <input type="date" name="date" id="fetch-date" placeholder="YYYY-MM-DD"><br><br>
                <label>Department Year : </label>
                <select name="year" id="fetch-year">
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                  </select><br><br>
                <label>Department Id : </label>
                <select name="department" id="fetch-department">
                      <option value="1">ECE</option>
                      <option value="2">CSE</option>
                      <option value="3">IT</option>
                      <option value="4">MECH</option>
                      <option value="5">CIVIL</option>
                      <option value="6">EEE</option>
                </select><br><br>
                <button id="fetch-Report" value="Submit">Submit</button>
            </div>
            <div id="fetchReport-response" class="table display-none"></div>
            </c:if>
            <c:if test="${staff.getDesigId()==2}">
            <div class="submitStatus report display-none" id="submitStatus">
            <h2>${details } </h2>
                <label>Date of Report  : </label>
                <input type="date" name="date" id="submit-status-date" placeholder="YYYY-MM-DD"><br><br>
                <label>Department Year : </label>
                <select name="year" id="submit-status-year">
                      <option value="1">1</option>
                      <option value="2">2</option>
                      <option value="3">3</option>
                      <option value="4">4</option>
                  </select><br><br>
                <label>Status : </label>
                <select name="status" id="submit-status-on">
                    <option value="1">Completed</option>
                    <option value="0">Pending</option>
                </select><br><br>
                <button id="submit-Status" value="Submit">Submit</button>
            </div>
            <div id="submitStatus-response" class="table display-none"></div>
        	</c:if>
        </div>
    <script src="hod.js"></script>
</body>
</html>	
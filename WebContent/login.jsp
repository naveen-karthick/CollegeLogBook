<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ejbBean" scope="request" class="io.zilker.ejb.EjbBean" />    
    
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" type="text/Css" href="logBook.css">
<%if(request.getSession().getAttribute("staff")!=null) { 
	response.sendRedirect("log");
	} %>
</head>
<body>
<div class="main-container">
    <div class="left-side">
        <h1>MSEC</h1>
        <h2><em> - 363, Arcot Road, Kodambakkam, Chennai - 24</em></h2>
        <img class="zilker-img" src="msec.png">
        <p><em>To impart state-of- the art technical education, inculcating sterling values and shining character, producing engineers who contribute to nation building
             thereby achieving our ultimate objective of sustained development of an unparalleled society, nation and world at large.</em></p>
        <img class="social-media" src="social_media.png">     
    </div>
    <div class="right-side">
        <div class="form">    
            <form action="log" method="post">     
                <h1>College Log Book Maintainer</h1>
                <h2>Enter Your Login Credentials</h2>
                <label >Email-id : </label>
                <input type="email" name="email" class="login-credentials"  placeholder="Enter your Email-id...." required><br><br>
                <label >Password : </label>
                <input type="password" name="password" class="login-credentials" placeholder="Enter your Password...." required><br><br>
                <input type="submit" value="Login">
            </form>
        </div>
    </div>
</div>



<script src="log.js"></script>
</body>
</html>
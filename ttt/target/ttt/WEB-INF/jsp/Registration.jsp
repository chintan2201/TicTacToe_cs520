<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<style>
table, td, th
{
border:1px solid #CC9900;
color:white;
}
th
{
background-color:#CC9900;
color:white;
}
</style>
</head>
<body bgcolor="black">
<div style="margin-top:10%;margin-left:40%;">
<h3 ><font face="Cursive" color="#CC9900" size="9">Registration</font></h3>
<form:form modelAttribute="Play" method="POST">
    <table border="1">
    	<tr>
    		<th colspan="3"><font face="Cursive">Tic Tac Toe</font></th>
    	</tr>
        <tr>
            <td><font face="Cursive">UserName:</font></td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td><font face="Cursive">Password:</font></td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td><font face="Cursive">Email:</font></td>
            <td><form:input path="email" /></td>
        </tr>
        <tr>
            <td colspan="3">
                <input type="submit" name="register" value="Register" style="color:white;background-color:#CC9900;width:100%;font-family:Cursive;font-size: 18px; "/>
            </td>
        </tr>
    </table>
    <br/>
    <a href="login1.html"><font color="#CC9900" face="Cursive">Login Page</font></a>
</form:form>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tic Tac Toe Login</title>
<style>
table, td, th
{
border:1px solid #CC9900;
}
th
{
background-color:#CC9900;
color:white;
}
</style>
</head>
<body>
<form:form modelAttribute="player" method='POST'>
    <table border="1">
    	<tr>
    		<th colspan="2">Tic Tac Toe</th>
    	</tr>
        <tr>
            <td>UserName:</td>
            <td><form:input path="username" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="password" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" name="login" value="Login" style="color:white;background-color:#CC9900;width:100%; "/>
            </td>
        </tr>
    </table>
</form:form>
<br/>
<a href="Registration.html"><font color="#CC9900">New Registration</font></a>
</body>
</html>
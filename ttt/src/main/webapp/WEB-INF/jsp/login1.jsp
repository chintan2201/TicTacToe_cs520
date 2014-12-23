<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Login</title>
<style>

table,td,th {
	border: 1px solid #CC9900;
	color: white;
}

th {
	background-color: #CC9900;
	color: white;
}

.errorblock {
	color: #ff0000;
	background-color: #ffEEEE;
	border: 3px solid #CC9900;
	padding: 8px;
	margin-right:70%;
	
}
</style>

</head>
<body onload='document.f.j_username.focus();' bgcolor="black">
<div style="margin-top:10%;margin-left:40%;">
<h3 style=" margin-left:8%;"><font face="Cursive" color="#CC9900" size="9">Login</font></h3>

	<c:if test="${not empty error}">
		<div class="errorblock">
			<font face="Cursive">Login failed due to wrong username or password.</font>
		</div>
	</c:if>
	<br/>
	<form name='f' action="<c:url value='j_spring_security_check' />"
		method='post'>

		<table>
			<tr>
	    		<th colspan="2"><font face="Cursive">Tic Tac Toe</font></th>
	    	</tr>
			<tr>
				<td><font face="Cursive">Username:</font></td>
				<td><input type='text' name='j_username' value=''/>
				</td>
			</tr>
			<tr>
				<td><font face="Cursive">Password:</font></td>
				<td><input type='password' name='j_password' />
				</td>
			</tr>
			<tr>
				<td colspan="2"><input name="submit" type="submit"
					value="Login" style="color:white;background-color:#CC9900;width:100%;font-family:Cursive;font-size: 18px;"/>
				</td>
			</tr>
		</table>

	</form>
	
<br/>
<br/>
<a href="Registration.html" style=""><font face="Cursive" color="#CC9900">New Registration</font></a>
<br/>
<br/>
<a href="DisplayGame.html"><font face="Cursive" color="#CC9900">Play Game With AI without Login</font></a>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Two Player Game Home</title>
</head>
<body bgcolor="black">
<div align="center" style="margin-top:7%;">
<h3><font face="Cursive" color="#CC9900">Hello , ${un}</font></h3>
<br/>
<sec:authorize access="hasRole('ROLE_USER')">
<div>
	<a href="HostGame.html"><font face="Cursive" color="#CC9900">Host a Game</font></a><br/> 
	<a href="JoinGame.html"><font face="Cursive" color="#CC9900">Join Game</font></a><br/>
</div>
<br/>
<a href="GameHome.html"><font face="Cursive" color="#CC9900">Game Home</font></a> <font face="Cursive" color="#CC9900">|</font>
<a href="logout.html?m=NewGame&type=FirstTime"><font face="Cursive" color="#CC9900">Logout</font></a>
</sec:authorize>
</div>
</body>
</html>
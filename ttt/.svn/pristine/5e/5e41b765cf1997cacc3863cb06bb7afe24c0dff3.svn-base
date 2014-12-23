<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tic Tac Toe</title>
</head>
<body style="width:100%; " bgcolor="black">
<div align="center" style="margin-top:10%;">
<h3><font face="Cursive" color="#CC9900 ">Hello , ${player}</font></h3>
<sec:authorize access="hasRole('ROLE_USER')">
<div align="center">
	<a href="GameWithAI.html?m=NewGame"><font face="Cursive" color="#CC9900">Play Game Against AI Player</font></a><br/>
	<a href="TwoPlayerHome.html"><font face="Cursive" color="#CC9900">Two Player Game</font></a><br/>
	<a href="GameHistory.html"><font face="Cursive" color="#CC9900">Game History</font></a><br/><br/>
</div>

<a href="logout.html?m=NewGame&type=FirstTime"><font face="Cursive" color="#CC9900">Logout</font></a>
</sec:authorize>
</div>
</body>
</html>
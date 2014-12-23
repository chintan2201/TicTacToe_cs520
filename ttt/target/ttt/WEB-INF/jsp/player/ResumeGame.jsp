<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resume Saved Games</title>
</head>
<body bgcolor="black">
<div align="center" style="margin-top:7%;">
<font  face="Cursive" color="#CC9900">hi...., ${uname}</font>
<br/><br/>
<sec:authorize access="hasRole('ROLE_USER')">
<div>
<c:forEach items="${ga}" var="g">
	<font  face="Cursive" color="#CC9900">Game Saved on </font> <a href="ResumeGame.html?date=${g.getGame_id()}"> <font  face="Cursive" color="#CC9900">${g.getSavetime()}</font></a><br/>
</c:forEach>
</div>
<br/>
<a href="GameWithAI.html?m=NewGame"><font color="#CC9900">Back</font></a>
</sec:authorize>
</div>
</body>
</html>
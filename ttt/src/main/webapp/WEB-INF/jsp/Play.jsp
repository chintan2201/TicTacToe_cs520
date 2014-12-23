<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tic Tac Toe</title>
<style>
table,td,th {
	 border: 1px solid #CC9900; 
	color:white;
	font-family: cursive;
	
}
td{
	width:30%;
	align:center;
}
th {
	background-color: #CC9900;
	color: white;
}
</style>
</head>
<body bgcolor="black">
<div style="margin-top:7%;margin-left:40%;">
	<h1><font face="Cursive" color="#CC9900" size="9">Tic Tac Toe</font></h1>
	<font face="Cursive" color="#CC9900" >Please make your own move</font>
	<p><font face="Cursive" color="#CC9900" >Player 1 =></font> <font face="Cursive" color="blue"> <b> X </b></font> <font face="Cursive" color="#CC9900" >(Human) |  Player 2 => </font><font face="Cursive" color="red"> <b> O </b></font> <font face="Cursive" color="#CC9900" >(Computer)</font></p>
	<br />
	<c:choose>
		<c:when test="${message ne null}">	
		<font face="Cursive" color="#CC9900">${message}</font>
		
			<table border="1"  cellpadding="10">

				<c:forEach begin="1" end="3" varStatus="st">
					<tr>
						<c:forEach begin="1" end="3" varStatus="a">
							<c:set var="str" value="${st.index}-${a.index}" />

							<c:choose>
								<c:when test="${lp1.contains(str)}">
									<td align="center"><font face="Cursive" color="blue" size="20"> <b> X </b>
									</font></td>
								</c:when>
								<c:when test="${lp2.contains(str)}">
									<td align="center"><font face="Cursive" color="red" size="20"><b> O </b></font></td>
								</c:when>
								<c:otherwise>
									<td><font face="Cursive" color="blue" color="#CC9900" size="20">__</font></td>
								</c:otherwise>
							</c:choose>


						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<table border="1" cellpadding="10">

				<c:forEach begin="1" end="3" varStatus="st">
					<tr>
						<c:forEach begin="1" end="3" varStatus="a">
							<c:set var="str" value="${st.index}-${a.index}" />

							<c:choose>
								<c:when test="${lp1.contains(str)}">
									<td align="center"><font face="Cursive" color="blue" size="20"> <b> X </b>
									</font></td>
								</c:when>
								<c:when test="${lp2.contains(str)}">
									<td align="center"><font face="Cursive" color="red" size="20"><b> O </b></font></td>
								</c:when>
								<c:otherwise>
									<td><a href="Play.html?r=${st.index}&c=${a.index}"><font face="Cursive"
											color="blue" color="#CC9900" size="20">__</font></a></td>
								</c:otherwise>
							</c:choose>


						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>

	</c:choose>
	<br/>
	<br/>
	<a href="DisplayGame.html"><font face="Cursive" color="#CC9900" >New Game</font></a> |
	<a href="login1.html"><font face="Cursive" color="#CC9900" >Login Page</font></a>
	</div>
</body>
</html>
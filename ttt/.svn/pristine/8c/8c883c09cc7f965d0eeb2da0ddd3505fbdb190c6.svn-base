<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Game With AI Player</title>
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
<div align="center" style="margin-top:7%;">
<font face="Cursive" color="#CC9900">Hi , ${player}</font>
<sec:authorize access="hasRole('ROLE_USER')">
<br/><br/>
<a href="ResumeGame.html?date=0"><font  face="Cursive" color="#CC9900">Resume Saved Games</font></a>
<font  face="Cursive" color="#CC9900">|</font>
<a href="GameHome.html"><font face="Cursive" color="#CC9900">Game Home</font></a>
<font  face="Cursive" color="#CC9900">|</font>
<a href="logout.html?m=NewGame&type=FirstTime"><font face="Cursive" color="#CC9900">Logout</font></a>
<br/>
<h1><font face="Cursive" color="#CC9900">Tic Tac Toe</font></h1>
	Please make your own move	
	<p><font face="Cursive" color="#CC9900" >Player 1 =></font> <font face="Cursive" color="blue"> <b> X </b></font> <font face="Cursive" color="#CC9900" >(Human) |  Player 2 => </font><font face="Cursive" color="red"> <b> O </b></font> <font face="Cursive" color="#CC9900" >(Computer)</font></p>  
	<br />
	<table border="1" cellpadding="10">
		<c:forEach begin="1" end="3" varStatus="st">
			<tr>

				<c:forEach begin="1" end="3" varStatus="a">
					<td><a href="PlayWithAI.html?r=${st.index}&c=${a.index}"><font face="Cursive" color="#CC9900" size="20">__</font></a></td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	<br/>
	<br/>
	</sec:authorize>
	</div>
</body>

</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Game History</title>
<style>
table,td,th {
	border: 1px solid #CC9900;
	color: white;
}

th {
	background-color: #CC9900;
	color: white;
}
</style>
</head>
<body bgcolor="black">
<div style="margin-top:10%;margin-left:25%;">
<h3><font face="Cursive" color="#CC9900">hi... , ${uname}</font></h3>
<sec:authorize access="hasRole('ROLE_USER')">
<a href="GameHome.html"><font face="Cursive" color="#CC9900">Game Home</font></a> <font face="Cursive" color="#CC9900">|</font>
<a href="logout.html?m=NewGame&type=FirstTime"><font face="Cursive" color="#CC9900">Logout</font></a> 
<br/>
<h2><font face="Cursive" color="#CC9900">Game History</font></h2>
<div align="left">
	<font face="Cursive" color="#CC9900">The number of games completed (i.e. excluding the saved games) : ${allcompletedgames}</font><br/>
	<font face="Cursive" color="#CC9900">The number of 1-player games completed : ${oneplayercompletedgames}</font><br/>
	<font face="Cursive" color="#CC9900">The number of 2-player games completed : ${twoplayercompletedgames}</font><br/>
	<font face="Cursive" color="#CC9900">The win rate against AI : ${rateagainst_ai} %</font><br/>
	<font face="Cursive" color="#CC9900">The win rate against human players : ${rateagainst_hu} %</font><br/>
</div>	

<h4><font face="Cursive" color="#CC9900">The List of games played this month (Games list is sorted on the bases of End time of game):</font></h4>
<table border="1">
<tr>
	<th>Time Interval(Start Time - End Time)</th>
	<th>Opponent</th>
	<th>Game length</th>
	<th>Outcome</th>
</tr>
   <c:forEach items="${thismonth}" var="g">
  	
  			
  		
  		 <tr>
  		 	<td>
  				<font face="Cursive" color="#CC9900">${g.getStarttime()} -  ${g.getEndtime()}</font>
  			</td>
  			
  		 	<c:choose>
  		 		<c:when test="${g.player1.getUsername() eq uname}">
					<c:choose>
						<c:when test="${g.player2.getUsername() eq null}">
							<td><font face="Cursive" color="#CC9900">AI</font></td>
						</c:when>
						<c:otherwise> <td><font face="Cursive" color="#CC9900">${g.player2.getUsername()}</font></td></c:otherwise>
					</c:choose>
  		 		</c:when>
  		 		<c:when test="${g.player2.getUsername() eq uname}">
					 <td><font face="Cursive" color="#CC9900">${g.player1.getUsername()}</font></td>	 		
  		 		</c:when>
  		 		
  		 	</c:choose>
  		 	<c:choose>
  		 		<c:when test="${gamelength.containsKey(g.getGame_id())}">
  		 			<td><font face="Cursive" color="#CC9900">${gamelength.get(g.getGame_id())}</font></td>
  		 		</c:when>	
  		 	</c:choose>
  		 	
  		 	<c:choose>
  		 		<c:when test="${g.win_player.getUsername() eq uname}">
  		 			<td><font face="Cursive" color="#CC9900">Won</font></td>
  		 		</c:when>
  		 		<c:when test="${g.loss_player.getUsername() eq uname}">
  		 				<td><font face="Cursive" color="#CC9900">lost</font></td>
  		 		</c:when>
  		 		<c:otherwise>
  		 			<td><font face="Cursive" color="#CC9900">Tie</font></td>
  		 		</c:otherwise>
  		 	</c:choose>
  		 	
  		 </tr>
   </c:forEach>
	
</table>
</sec:authorize>
</div>
</body>
</html>
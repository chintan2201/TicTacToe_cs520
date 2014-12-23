<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Two Player Game Board</title>
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
<script type="text/javascript" src="../js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	var check = null;
	var scheck = null;
	var pcheck = null;
	var hostcheck = null
	var joincheck = null;
	var gu = null;
	var r = "${resultmsg}";
	
	
	//this jquery is for Gamelist maintainance
	$(function() {
		$
				.ajax({
					url : "gamelist.json",
					cache : false,
					success : function(data) {
						if (check != true && scheck != true) {
							for (var i = 0; i < data.games.length; ++i) {

								var stringgame = data.games[i];
								var players = stringgame.split('-');
								var user = "${un}";
								var p1 = players[0];
								var p2 = players[1];

								if (players[0] === user) {
									//	document.write(player[1]+"has joined the game. Please make your move.");
									$('#Messages')
											.append(
													"<h3><font color='#CC9900'>"
															+ p2
															+ " has joined the game. Please make your move.</font></h3>");
									$('#info #font').empty();
									$('#heading').append("<h3><font color='#CC9900'>"+p1+" -vs- "+p2+"</font></h3>");
									for (var i = 1; i <= 3; i++) {
										$('#gboard')
												.append("<tr id=" + i + ">");
										for (var j = 1; j <= 3; j++) {
											$("#" + i)
													.append(
															"<td><a href='StartTwoPlayer.html?r="+i+"&c="+j+"'><font color='#CC9900' size='20'>__</font></a></td> ");
										}

									}

									check = true;
									scheck = true;
									break;

								} else if (players[1] === user) {
									//document.write("Waiting for (Player A)"+p1+"'s move"); 
									$('#Messages').append(
											"<h3><font color='#CC9900'> Waiting for (Player A) "
													+ p1
													+ "'s move</font></h3>");
									$('#info #font').empty();
									$('#heading').append("<h3><font color='#CC9900'>"+p1+" -vs- "+p2+"</font></h3>");
									for (var i = 1; i <= 3; i++) {
										$('#gboard')
												.append("<tr id=" + i + ">");
										for (var j = 1; j <= 3; j++) {
											$("#" + i)
													.append(
															'<td><font color="#CC9900" size="20">__</font></td> ');
										}

									}
									check = true;
									scheck = true;
									break;

								}
							}

						}

					}
				});
		updateGame();
	});

	function updateGame() {
		$.ajax({
					url : "gamelist.deferred.json",
					cache : false,
					success : function(data) {
						
						if (check != true && scheck != true) {
							for (var i = 0; i < data.length; ++i) {

								var stringgame = data[i];
								var players = stringgame.split('-');
								var user = "${un}";
								var p1 = players[0];
								var p2 = players[1];

								if (players[0] === user) {

									$('#Messages')
											.append(
													"<h3><font color='#CC9900'>"
															+ p2
															+ " has joined the game. Please make your move.</font></h3>");
									$('#info #font').empty();
									$('#heading').append("<h3><font color='#CC9900'>"+p1+" -vs- "+p2+"</font></h3>");
									for (var i = 1; i <= 3; i++) {
										$('#gboard')
												.append("<tr id=" + i + ">");
										for (var j = 1; j <= 3; j++) {
											$("#" + i)
													.append(
															"<td><a href='StartTwoPlayer.html?r="+i+"&c="+j+"'><font color='#CC9900' size='20'>__</font></a></td> ");
										}

									}
									check = true;
									scheck = true;
									break;
								} else if (players[1] === user) {

									$('#Messages').append(
											"<h3><font color='#CC9900'> Waiting for (Player A) "
													+ p1
													+ "'s move</font></h3>");
									$('#info #font').empty();
									$('#heading').append("<h3><font color='#CC9900'>"+p1+" -vs- "+p2+"</font></h3>");
									for (var i = 1; i <= 3; i++) {
										$('#gboard')
												.append("<tr id=" + i + ">");
										for (var j = 1; j <= 3; j++) {
											$("#" + i)
													.append(
															'<td><font color="#CC9900" size="20">__</font></td> ');
										}

									}
									check = true;
									scheck = true;
									break;
								}

							}
							
							updateGame();
						}

					}
				
				});
		
	}
	
	$(function() {
		$.ajax({
					url : "gameplaydata.json",
					cache : false,
					success : function(data) {
						if(scheck === true)
						{
						//$('#Messages').empty();	
						 gu = "${gamenamewithunder}";
						//document.write(gu);
						$.each(data.gamedata, function(key, value){
							if(key === gu)
						   {
						   		var x = value["gamename"].split("-");
						   		var hn = x[0];
						   		var jn = x[1];
						   		var uname = "${un}";
						   		
						   		$('#info #font').empty();
						   		$('#heading').empty();
								$('#heading').append("<h3><font color='#CC9900'>"+hn+" --vs-- "+jn+"</font></h3>");
								
								var hp = value["hostposition"];
								var jp = value["joinposition"];
								var ishostturn = value["isHostTurn"];
								//document.write(hp +"/"+ jp );
								 r = value["result"];
								$('#gboard').empty();
								////////////
							for (var i = 1; i <= 3; i++) {
									$('#gboard')
											.append("<tr id=" + i + ">");
									for (var j = 1; j <= 3; j++) {
										
										var pos = i+"-"+j;
										
										var hpos = $.inArray(pos,hp) ;
										var jpos = $.inArray(pos,jp) ;
										
										//document.write(hpos+"/"+jpos);
										/////////
										
									
							if(hn === uname && ishostturn === true && r === null)
							{
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><a href='StartTwoPlayer.html?r="+i+"&c="+j+"'><font color='#CC9900' size='20'>__</font></a></td> ");
				
							}
							if(hn === uname && ishostturn === false && r === null)
							{
								
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'>__</font></td> ");
				
							}
							if(jn === uname && ishostturn === true && r === null)
							{
								
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'>__</font></td> ");
				
							}
							if(jn === uname && ishostturn === false && r === null)
							{
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><a href='StartTwoPlayer.html?r="+i+"&c="+j+"'><font color='#CC9900' size='20'>__</font></a></td> ");
				
							}
							if(r != null)
							{
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'>__</font></td> ");
				
							}
									
										////////
									}

								} 
						
							$('#move').empty();
							//$('#Messages').append("<h3>"+hn+"/"+jn+"/"+uname+"/"+ishostturn+"</h3>");
							$('#Messages').empty();	
							if(hn === uname && ishostturn === true)
							{
								$('#move').append("<h3><font color='#CC9900'>Please make your move</font></h3>");
							}
							if(hn === uname && ishostturn === false)
							{
								
								$('#move').append("<h3><font color='#CC9900'>wait for other player's move</font></h3>");
							}
							if(jn === uname && ishostturn === true)
							{
								
								$('#move').append("<h3><font color='#CC9900'>wait for other player's move</font></h3>");
							}
							if(jn === uname && ishostturn === false)
							{
								$('#move').append("<h3><font color='#CC9900'>Please make your move</font></h3>");
							}
								//////////
						   }
						});
						
					}
						if(r != null)
						{
							$('#move').empty();
							$('#result').append("<h3><font color='#CC9900'>"+r+"</font></h3>");
							r = null;
						}
					}
				});
	
		
		
		update();
	});
	 function update() {
		$.ajax({
			url : "gameplaydata.deferred.json",
			cache : false,
			success : function(data) {
				//document.write("scheck:"+scheck +"/ check :"+check  );
				if(scheck === true )
				{
					$('#Messages').empty();
					 gu = "${gamenamewithunder}";
					//document.write(gu);
					$.each(data, function(key, value){
					var id = -1; 
					//
					 
						 var un = "${un}";
						 id = key.indexOf(un);
						 //document.write(id);
					 
						if(key === gu || id >-1)
					   {
						   var x = value["gamename"].split("-");
					   		var hn = x[0];
					   		var jn = x[1];
					   		var uname = "${un}";
					   		$('#info #font').empty();
					   		$('#heading').empty();
							$('#heading').append("<h3><font color='#CC9900'>"+hn+" --vs-- "+jn+"</font></h3>");
							
							var hp = value["hostposition"];
							var jp = value["joinposition"];
							var ishostturn = value["isHostTurn"];
							 r = value["result"];
							$('#gboard').empty();
							////////////
						for (var i = 1; i <= 3; i++) {
								$('#gboard')
										.append("<tr id=" + i + ">");
								for (var j = 1; j <= 3; j++) {
									
									var pos = i+"-"+j;
									
									var hpos = $.inArray(pos,hp) ;
									var jpos = $.inArray(pos,jp) ;
									
									//document.write(hpos+"/"+jpos);
									/////////
							
									
							if(hn === uname && ishostturn === true && r === null)
							{
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><a href='StartTwoPlayer.html?r="+i+"&c="+j+"'><font color='#CC9900' size='20'>__</font></a></td> ");
				
							}
							if(hn === uname && ishostturn === false && r === null)
							{
								
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'>__</font></td> ");
				
							}
							if(jn === uname && ishostturn === true && r === null)
							{
								
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'>__</font></td> ");
				
							}
							if(jn === uname && ishostturn === false && r === null)
							{
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><a href='StartTwoPlayer.html?r="+i+"&c="+j+"'><font color='#CC9900' size='20'>__</font></a></td> ");
				
							}
									
							if(r != null)
							{
								if(hpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> X </font></td> ");
								
								if(jpos > -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'> O </font></td> ");
								
								if(hpos === -1 && jpos === -1)
									$("#" + i).append("<td align='center'><font color='#CC9900' size='20'>__</font></td> ");
				
							}		
									////////
									}

								}
						$('#move').empty();
						//$('#Messages').append("<h3>"+hn+"/"+jn+"/"+uname+"/"+ishostturn+"</h3>");
							if(hn === uname && ishostturn === true)
							{
								$('#move').append("<h3><font color='#CC9900'>Please make your move</font></h3>");
							}
							if(hn === uname && ishostturn === false)
							{
								
								$('#move').append("<h3><font color='#CC9900'>wait for other player's move</font></h3>");
							}
							if(jn === uname && ishostturn === true)
							{
								
								$('#move').append("<h3><font color='#CC9900'>wait for other player's move</font></h3>");
							}
							if(jn === uname && ishostturn === false)
							{
								$('#move').append("<h3><font color='#CC9900'>Please make your move</font></h3>");
							}
						//scheck = false;
								
							//////////
					   }
						
					});
				
				
			}
				if(r != null)
				{
					$('#move').empty();
					$('#result').append("<h3><font color='#CC9900'>"+r+"</font></h3>");
					r = null;
				}
				update();
				
				
			}
			
		});
		
	} 

</script>
</head>
<body bgcolor="black">
<div align="center" style="margin-top:7%;">
	<h3 id="uname">
		<font face="Cursive" color="#CC9900">Hello , ${un}</font>
	</h3>
	<sec:authorize access="hasRole('ROLE_USER')">
	<a id="ghome" href="ExitGame.html?c=gh"><font face="Cursive" color="#CC9900" >Game Home</font></a>
	<font face="Cursive" color="#CC9900">|</font>
	<a  href="ExitGame.html?c=th"><font face="Cursive" color="#CC9900">New Two Player Game</font></a><font face="Cursive" color="#CC9900"> |</font>
	<a  href="ExitGame.html?c=lo"><font face="Cursive" color="#CC9900">Logout</font></a>

		<h1>
			<font face="Cursive" color="#CC9900">Tic Tac Toe</font>
		</h1>
	<div id="Messages">
	</div>
	
	<div>	
		<c:choose>
			<c:when test="${infomsg ne null}">
				<h3 id="info">
					<font face="Cursive" color="#CC9900" id="font">${infomsg}</font>
				</h3>
			</c:when>
		</c:choose>
	</div>
	<div>
		<p id="heading"></p>
		<div id="move">
		</div>
		<table id="gboard" border="1" cellpadding="10" >

		</table>
	</div>
	<div id ="result">
	</div>
	<!-- <div style="border: solid #CC9900; width: 50%;">
		<h2>Joining Player List</h2>
		<ul id="join">
		</ul>
		<p>-----------------------------------------------</p>
		<h2>Host Player List</h2>
		<ul id="host">
		</ul>

	</div> -->
	</sec:authorize>
	</div>
</body>
</html>
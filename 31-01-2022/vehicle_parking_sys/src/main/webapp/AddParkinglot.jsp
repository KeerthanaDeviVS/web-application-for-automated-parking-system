<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ page import="java.sql.*" %>
    <% Class.forName("com.mysql.jdbc.Driver"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>ADDING PARKINGLOT</title>
<style>
* {
   box-sizing: border-box;
   margin: 0;
}
body
{
background-image: url("adminop.jpg");
height: 500px; 
 background-position: center;
  background-size: cover;
}
.box {
  padding: 70px;
  width: 50%;
  position:absolute;
  top:15%;
  left:25%;
  background-color:rgb(255,255,255,0.7);
}
.button {
  background-color:#d3d3d3;
}
div {text-align: center;}
</style>
</head>
<body>
<%
if(session.getAttribute("user_name")==null)
{
  response.sendRedirect("AdminLogin.jsp");
}
%>
<div class="box">  
Enter Parking Lot Name:<input type="text" id="name" name="parkinglot_name" placeholder="Parkinglot Name"><br><br> 
Click:<button onclick="booklot()">ADD</button><br><br>
<a href="AdminOperations.jsp">
<input type="button" class="button" value="BACK"></a><br>

</div>

</body>

<script type="text/javascript">
function booklot()
{
	var func="addlot";
	var lotname=$("#name").val(); 
	
	$.post("flooroperations",{
		param:func,
		lot_name:lotname
	},function(data,status)
	{
		if(data=="success")
		{
		alert("PARKINGLOT ADDED SUCCESSFULLY");
		}
	});
	
	
	}

</script>
</html>
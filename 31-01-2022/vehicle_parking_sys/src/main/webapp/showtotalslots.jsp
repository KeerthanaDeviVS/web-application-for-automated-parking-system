<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
    <% Class.forName("com.mysql.jdbc.Driver"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>VIEW OF TOTAL SLOTS</title>
<style>
body
{
background-image: url("adminop.jpg");
height: 500px; 
 background-position: center;
  background-size: cover;
}
.center {
  margin-left: auto;
  margin-right: auto;
}
table {
  width: 75%;
  
}
th, td {
  text-align: left;
  padding: 16px;
}
</style>
</head>
<body >
<%

if(session.getAttribute("user_name")==null)
{
  response.sendRedirect("AdminLogin.jsp");
}
String url="jdbc:mysql://localhost:3306/parkingsystem";
String user_name="root";
String pass="Magicalkd@11";
Connection con=DriverManager.getConnection(url, user_name, pass);
Statement st=con.createStatement();
ResultSet rs=st.executeQuery("select * from floors");
%>
<table class="center" border="1">
<tr> 
<th style="background-color:rgb(255,255,255,0.5)" >FLOOR ID</th>
<th style="background-color:rgb(255,255,255,0.5)" >FLOOR NAME</th>
<th style="background-color:rgb(255,255,255,0.5)" >PARKING LOT ID</th>
<th style="background-color:rgb(255,255,255,0.5)" >VEHICLE TYPE</th>
<th style="background-color:rgb(255,255,255,0.5)" >CAPACITY</th>
<th style="background-color:rgb(255,255,255,0.5)" >AVAILABILITY  (0-AVAILABLE/1-FULL)</th>
</tr>
<%while(rs.next()){%>
<tr>
<td><%=rs.getInt(1) %></td>
<td><%=rs.getString(2) %></td>
<td><%=rs.getInt(3) %></td>
<td><%=rs.getString(4) %> 
<td><%=rs.getInt(5) %></td>
<td><%=rs.getInt(6) %></td>
</tr>
<%} %>
</table><br><br>
<a href="AdminOperations.jsp">
<input type="button" value="BACK"></a><br>
</body>
</html>
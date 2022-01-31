<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>USER</title>
<style>
* {
   box-sizing: border-box;
   margin: 0;
}
body
{
background-image: url("loginpage.jpg");
height: 500px; 
 background-position: center;
  background-size: cover;
}
.box {
  padding: 70px;
  width: 50%;
  position:absolute;
  top:25%;
  left:25%;
  background-color:rgb(255,255,255,0.7);
}
div {text-align: center;}
</style>
</head>

<body>

<div class="box">
<a href="park.jsp">
<input type="submit" value="PARK"><br><br></a> 
<a href="regexit.jsp">
<input type="submit" value="EXIT"><br><br></a>
<a href="alreadyanuser.jsp">
<input type="button" value="BACK"></a><br>
</div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>USER TYPE</title>
<style>
* {
   box-sizing: border-box;
   margin: 0;
   padding:15px;
}
body
{
background-image: url("customer.jpg");
height: 500px; 
 background-position: center;
  background-size: cover;
}
.button {
  background-color:#ffe4e1;
  border: none;
  color: black;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
  margin: 4px 2px;
}
.box {
  padding: 70px;
  width: 50%;
  position:absolute;
  top:15%;
  left:25%;
  background-color:rgb(0,0,0,0.5);
}
div {text-align: center;}
</style>
</head>
<body>

<div class="box">
<a href="newuser.jsp">
<input type="submit" value="SIGN-UP"></a><br><br>
<a href="alreadyanuser.jsp">
<input type="submit" value="SIGN-IN"><br><br></a>
<a href="customeroperations.jsp">
<input type="button" value="VISITOR"></a><br><br> 
<a href="welcomepage.html">
<input type="button" value="GO TO HOMEPAGE"></a><br><br>
</div>

</body>
</html>
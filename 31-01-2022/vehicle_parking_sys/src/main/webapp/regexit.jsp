<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<title>EXIT</title>
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
Enter Your Vehicle_num : <input type="text" id="vehic_num" name="vehic_num"><br><br>
Click:<button onclick="exitvehic()">EXIT VEHICLE</button><br><br> 
<!-- Click:<input type="submit" value="EXIT VEHICLE"><br><br>-->
<a href="user.jsp">
<input type="button" value="BACK"></a><br>
</div>

</body>

<script>

function exitvehic()
{
	var action="exitvehicle";
	var vehic_num=$("#vehic_num").val();
	var parking_no=0;
	$.post("vehicleoperations",{
		func:action,
		vehic_num:vehic_num
	},
	function(data,status)
	{
		if(data=="failure")
		{
		alert("INVALID VEHICLE NUMBER.USE VALID NUMBER");
		}
	else
		{
		 window.location.assign("customerbill.jsp?parking_id="+data);
		}
	})
	
	}

</script>
</html>
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
<title>PARK</title>
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
<% String username = (String)request.getSession().getAttribute("cus_id"); %>
<div class="box"> 
Enter User ID:<input type="number" id="user_id" name="email" required><br><br>
Enter Vehicle Number:<input type="text" id="vehic_num" name="vehic_num"><br><br> 
Select Vehicle Type<br><br>
<select  id="vehicle">
<option value="Bike">Bike</option>
<option value="Car">Car</option>
</select>
<br><br>
Click:<button onclick="park()">PARK</button> <br><br>
<a href="user.jsp">
<input type="submit" value="BACK"></a>
</div>
<script>
function park()
{
	var action="parkuser";
	var email=$("#user_id").val();
	var vehic_num=$("#vehic_num").val();
	var vehic_type=$("#vehicle").val(); 
	$.post("vehicleoperations",{
		func:action,
		email:email,
		vehic_num:vehic_num,
		vehic_typ:vehic_type
	},
	function(data,status)
	{
		if(data=="failure")
			alert("NO SPACE");
		else
			alert("VEHICLE PARKED SUCCESSFULLY ON SLOT:"+data);
	})
	
	}
</script>
</body>
</html>
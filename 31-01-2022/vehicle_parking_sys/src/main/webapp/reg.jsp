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
<title>ADD NEW SLOTS</title>
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
Enter User ID:<input type="number" id="user_id" name="email"  required><br><br>
Select Vehicle Type<br><br>
<select  id="vehicle">
<option value="Bike">Bike</option>
<option value="Car">Car</option>

</select>
<br><br> 
<button onclick="register()">SUBMIT</button> 
<a href="customer_type.jsp">
<input type="button" value="BACK"></a>
</div>

</body>
<script>
function register()
{
	var action="addnewslots";
	var email=$("#user_id").val();
	var vehic=$("#vehicle").val();
	$.post("vehicleoperations",{
		func:action,
		usr_id:email,
		vehicle:vehic
	},
	function(data,status)
	{
		if(data=="failure")
			alert("YOU HAVE EXCEEDED YOUR LIMIT");
		else if(data=="failure1")
			alert("FLOOR OR SLOTS CURRENTLY NOT AVAILABLE");
		else
			alert("NEW SLOT ADDED SUCCESSFULLY!!! YOUR REGISTERED SLOT:"+data);
	})
	
	}
</script>
</html>
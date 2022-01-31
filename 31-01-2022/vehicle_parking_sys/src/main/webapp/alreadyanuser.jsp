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
Enter User ID:<input type="number" id="user_id" name="email" required><br><br>
Enter Password:<input type="password"  id="pass" name="passwd" placeholder="Password"><br><br>  
<select  id="method">
<option value="register">Register</option>
<option value="bookings">Bookings</option>

</select>
<br><br>
Click:<button onclick="signin()">PROCEED</button> <br><br>
<a href="customer_type.jsp">
<input type="submit" value="BACK"></a>
</div>
  
</body>
<script>
function signin()
{
	var action="signin";
	var user_name=$("#user_id").val();
	var user_passwd=$("#pass").val();
	var vehicle_num=$("#vehic_num").val();
	var vehic_type=$("#vehicle").val();
	var method=$("#method").val(); 
	$.post("vehicleoperations",{
		func:action,
		user_id:user_name,
		passwd:user_passwd,
		vehic_num:vehicle_num,
		vehic_typ:vehic_type,
		method:method
	},
	function(data,status)
	{
		if(data=="success1")
			window.location.assign("reg.jsp");
		else if(data=="success2")
			window.location.assign("user.jsp");
		else
			alert("INVALID USER ID OR PASSWORD");
	})
	console.log(user_name);
}

</script>

</html>
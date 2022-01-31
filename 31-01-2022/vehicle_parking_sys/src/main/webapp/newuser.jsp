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
<title>REGISTRATION</title>
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
<h1 style="text-align:center;font-size:100px;color:white">REGISTRATION FORM</h1> 
<div class="box">
Enter your Name:<input type="text" id="name" name="usr_name" placeholder="Name"><br><br> 
Enter your Email-ID: <input type="email" id="user_id" name="email" placeholder="abc@gmail.com" title="abc@gmail.com" required><br><br> 
Enter your Password:<input type="password" id="pass1" name="pass1" placeholder="Password"><br><br> 
Re-enter Password:<input type="password" id="pass2" name="pass2" placeholder="Confirm Password"><br><br> 
Enter your Contact Number:<input type="tel" id="phone" name="contact"  pattern="[0-9]{10}" placeholder="Phone" title="Ten digits code" required  ><br><br>
Select Vehicle Type<br><br>
<select  id="vehicle">
<option value="Bike">Bike</option>
<option value="Car">Car</option>

</select>
<br><br> 
<button onclick="register()">SUBMIT</button><br><br>
<a href="welcomepage.html">
<input type="button" value="BACK"></a>
</div>
</body>
<script>

function register()
{
	var action="register";
	var name=$("#name").val();
	var user_id=$("#user_id").val();
	var pass1=$("#pass1").val();
	var pass2=$("#pass2").val();
	var telephone=$("#phone").val(); 
	var vehic=$("#vehicle").val();
	if(pass1==pass2)
		{
		$.post("vehicleoperations",{
			func:action,
			user_name:name,
			usr_id:user_id,
			passwd:pass1,
			phone:telephone,
			vehicle:vehic
		},
		function(data,status){
			console.log(data);
			if(data=="failure")
				alert("SPACE NOT AVAILABLE");
			else 
				alert("REGISTERED SUCCESSFULLY!!! YOUR USER ID & REGISTERED SLOT:"+data);
		})
		}
	else
		{
		alert("PASSWORD MISMATCH");
		}
	
	console.log(action);
	console.log(name);
}

</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
<link href="css/bootstrap.css" rel="stylesheet"/>
</head>
<body>

<div class="container">

	<h1>Login</h1>
	<form action="loginAction" method="post">
		UserId:
		<input name="userId" class="form-control"/>
		
		Password:
		<input name="password" type="password"  class="form-control"/>
		
		LoginAs: <br>
		<label><input name="identity" type="radio" value="patient" checked> Patient  </label> 
		<label><input name="identity" type="radio" value="doctor"> Doctor  </label>
		<label><input name="identity" type="radio" value="hospital"> Hospital  </label>
		<button class="btn btn-primary btn-block">Login</button>
	</form>	
</div>
	

</body>
</html>
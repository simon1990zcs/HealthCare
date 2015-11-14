<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cs5200.doctor.*, java.util.*, cs5200.record.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Patient Profile Page</title>
<link href="css/bootstrap.css" rel="stylesheet"/>
</head>
<body>

	<%
	int id = Integer.parseInt(request.getParameter("id"));
	DoctorDao dao = DoctorDao.getInstance();
	Doctor p = dao.selectInfoFromDoctorId(id);
	%>

<div class="container">

	<h1>welcome to <%= p.name %>'s personal profile page</h1>
	
		<div class="tab-content" id = "profile">
			<h3>Basic Info</h3>
			<table class="table" width="400">
				<tr><td width="200">user Id:</td><td><%= p.id %></td></tr>
				<tr><td>Name:</td><td><%= p.name %></td></tr>
				<tr><td>Age:</td><td><%= p.age %></td></tr>
				<tr><td>Gender:</td><td><%= p.gender %></td></tr>
				<tr><td>SSN:</td><td><%= p.ssn %></td></tr>
				<tr><td>DriverLisense:</td><td><%= p.driverLisense %></td></tr>
				<tr><td>Phone Number:</td><td><%= p.phoneNumber %></td></tr>								
				<tr><td>Address:</td><td><%= p.address %></td></tr>
			</table>
			<h3>Doctor Info</h3>
			<table class="table" width="400">
				<tr><td width="200">StartDate:</td><td><%= p.startDate %></td></tr>
				<tr><td>Specialty:</td><td><%= p.specialties %></td></tr>
				<tr><td>Belonged Hospital:</td><td><a href="hospitalPro.jsp?id=<%= p.belongs %>"> <%= p.hospitalName %></a></td></tr>
			</table>
		</div>
</div>	
	
</body>
</html>
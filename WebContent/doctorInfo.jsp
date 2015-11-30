<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cs5200.doctor.*, java.util.*, cs5200.Enum"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Doctor Info Page</title>
<link href="css/bootstrap.css" rel="stylesheet"/>
</head>
<body>

	<%
	int id = Integer.parseInt(request.getParameter("id"));
	DoctorDao dao = DoctorDao.getInstance();
	Doctor p = dao.selectInfoFromDoctorId(id);
	String sHospitalId = request.getParameter("hospitalId");
	boolean update = true;
	if (p == null){
		// insert a new doctor
		p = new Doctor();
		p.id = id;
		update = false;
		
	} 
	%>

<div class="container">

	<%
		if (update) {
			%>
	<h1>Editing <%= p.name %>'s profile page</h1>
			<%
		} else {
			%>
	<h1>Inserting a new doctor page</h1>
			<%
		}
	%>
			<div class="tab-content" id = "profile">
			<h3>Basic Info</h3>
			<form action="refresh" method=post">
			<table class="table" width="400">
				<tr><td width="200">user Id:</td><td><input name="id" value="<%= p.id%>" readonly/></td></tr>
				<tr><td width="200">Name:</td><td><input name="name" value="<%= p.name%>"/></td></tr>
				<tr><td width="200">Age:</td><td><input name="age" value="<%= p.age%>"/></td></tr>
				<tr><td width="200">Gender:</td><td>
						<div >
								<%
								  String selected = "selected";
								  String checked = "checked";
								  String se = "";
								  if ( update && Enum.Gender.male.equals(p.gender)) { se = checked;} else {se ="";}	
									  %>
								<input type="radio" name="gender" value="male" <%= se %>> Male            							  
									  <%
								  if ( update && Enum.Gender.female.equals(p.gender)) { se = checked;} else {se ="";}	
									  %>
								<input type="radio" name="gender" value="female" <%= se %>> Female   								  
						</div>
					</td>  
				</tr>
				
				<tr><td width="200">SSN:</td><td><input name="ssn" value="<%= p.ssn%>"/></td></tr>
				<tr><td width="200">Driver License:</td><td><input name="DriverLicense" value="<%= p.driverLisense%>"/></td></tr>
				<tr><td width="200">Phone Number:</td><td><input name="phoneNumber" value="<%= p.phoneNumber%>"/></td></tr>							
				<tr><td width="200">Address:</td><td><input name="address" value="<%= p.address%>"/></td></tr>
				</table>
				<h3>Doctor Info</h3>
				<table class="table" width="400">
				<tr><td width="200">Start Date:</td><td><input type="date" name="date" value="<%= p.startDate%>"/></td></tr>
				<tr><td width="200">Specialities:</td><td width="">
						<div class="selectContainer">
							<select name="speciality" class="form-control">
								<%
								  for (Enum.Specialties s : Enum.Specialties.values()){
									  if ( update && s.equals(p.specialties)) {se = selected;} else se="";
									  %>
								<option value="<%= s.toString()%>" <%= se %> ><%= s.toString() %></option>									  
									  <%
								  }
								%>
							</select>
						</div>
					</td>  
				</tr>
				<tr><td width="200">belonged Hospital:</td><td width="">
						<div class="selectContainer">
							<select name="belongs" class="form-control">
								<%
									  HashMap<Integer,String> hospital = dao.getHospitalInfo();
									  for (Map.Entry<Integer, String> e : hospital.entrySet()){
										  if (update && e.getKey() == p.belongs) {se = selected;} else se ="";
										  if (update ||e.getKey() == Integer.parseInt(sHospitalId)){
										  %>
									<option value="<%= e.getKey()%>" <%= se %> ><%= e.getValue()%></option>									  
										  <%
										  }
									  }
								%>
							</select>
						</div>
					</td>  
				</tr>
			</table>
			<br />
			<button class="btn btn-primary" name="action" value="doctorInfo">Submit</button>
			</div>
			</form>
					
</div>	
	
</body>
</html>
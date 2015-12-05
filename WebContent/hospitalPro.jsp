<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cs5200.hospital.*, java.util.*, cs5200.record.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hospital Profile Page</title>
<link href="css/bootstrap.css" rel="stylesheet"/>
<script type="text/javascript">
	
	function initial(){
		var n = document.getElementById('newComment');
		var b = document.getElementById('commentSection');
		if (b.value == 1){
			n.className='container';
		}
		var alertShown = document.getElementById('tab');
		if (alertShown.value > 0){
			window.alert("your operation succeed");
		}
	}

</script>
</head>
<body onload='initial()'>

	<%
	int id = Integer.parseInt(request.getParameter("id"));
	HospitalDao dao = HospitalDao.getInstance();
	Hospital p = dao.selectInfoFromHospitalId(id);
	String sPatientId = "";
	int n = 0;
	if (request.getParameter("patientId") != null){
		sPatientId = request.getParameter("patientId");
	}
	int commentShown = 0;
	if (!sPatientId.isEmpty()){
		commentShown = 1;
	}
	
	if (request.getParameter("comment") != null){
		String comment = request.getParameter("comment");
		Date date= new Date(System.currentTimeMillis());
		int patientId = Integer.parseInt(sPatientId);
		n = dao.insertHospitalComment(id, patientId, comment);
		p = dao.selectInfoFromHospitalId(id);
	}
	%>

<div class="container">
	<input type="hidden" id="commentSection" value="<%= commentShown%>"/>
	<input type="hidden" id="tab" value="<%= n%>"/>
	<h1>welcome to <%= p.name %>'s profile page</h1>
	
		<div id="myTabContent" >
		<!-- my profile info session -->
		<div class="tab-content" id = "profile">
			<h3>Basic Info</h3>
				<table class="table" width="400">
					<tr><td width="200">user Id:</td><td><p><%= p.id%></p></td></tr>
					<tr><td>Name:</td><td><%= p.name %></td></tr>
					<tr><td>Phone Number:</td><td class="edit"><p><%= p.phoneNumber %></p><input class="hide" name="phoneNumber" value="<%= p.phoneNumber%>"/></td></tr>								
					<tr><td>Address:</td><td class="edit"><p><%= p.address %></p><input class="hide" name="address" value="<%= p.address%>"/></td></tr>
					<tr><td>introduction:</td><td class="edit"><p><%= p.Introduction %></p><input class="hide" name="introduction" value="<%= p.Introduction%>"/></td></tr>
				</table>
			<h3>Patients' Comments</h3>
			<table class="table" width="400">
				<%
					List<Comment> coms = p.getComments();
					for(Comment c : coms){
						%>
						<tr><td><%= c.date %></td><td width="200"><a href="patientPro.jsp?id=<%= c.patientId %>" ><%= c.patientName %>:</a></td><td><%= c.comment %></td></tr>
						<%
					}
				%>
			</table>
			<div id="newComment" class="container hide">
			<h3>Your Comments:</h3>
			<form action="hospitalPro.jsp">
			<input type="hidden" name="id"  value="<%= p.id%>"/>
			<input type="hidden" name="patientId"  value="<%= sPatientId%>"/>
			<textarea rows="4" cols="50" name="comment"></textarea>
			<br/>
			<button class="btn btn-primary" type=submit>Submit</button>
			</form>
			</div>
		</div>
</div>	
	
</body>
</html>
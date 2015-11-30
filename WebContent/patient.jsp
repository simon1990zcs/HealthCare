<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cs5200.patient.*, java.util.*, cs5200.record.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Patient Page</title>
<link href="css/bootstrap.css" rel="stylesheet"/>

<script type="text/javascript">
    //<![CDATA[

    var tabLinks = new Array();
    var contentDivs = new Array();

    function init() {

      // Grab the tab links and content divs from the page
      var tabListItems = document.getElementById('tabs').childNodes;
      for ( var i = 0; i < tabListItems.length; i++ ) {
        if ( tabListItems[i].nodeName == "LI" ) {
          var tabLink = getFirstChildWithTagName( tabListItems[i], 'A' );
          var id = getHash( tabLink.getAttribute('href') );
          tabLinks[id] = tabLink;
          contentDivs[id] = document.getElementById( id );
        }
      }

      // Assign onclick events to the tab links, and
      // highlight the first tab
      var i = 0;

      for ( var id in tabLinks ) {
        tabLinks[id].onclick = showTab;
        tabLinks[id].onfocus = function() { this.blur() };
        if ( i == 0 ) tabLinks[id].parentNode.className = 'active';
        i++;
      }

      // Hide all content divs except the first
      var i = 0;

      for ( var id in contentDivs ) {
        if ( i != 0 ) contentDivs[id].className = 'tabContent hide';
        i++;
      }
    }

    function showTab() {
      var selectedId = getHash( this.getAttribute('href') );

      // Highlight the selected tab, and dim all others.
      // Also show the selected content div, and hide all others.
      for ( var id in contentDivs ) {
        if ( id == selectedId ) {
          tabLinks[id].parentNode.className = 'active';
          contentDivs[id].className = 'tabContent';
        } else {
          tabLinks[id].parentNode.className = '';
          contentDivs[id].className = 'tabContent hide';
        }
      }

      // Stop the browser following the link
      return false;
    }

    function getFirstChildWithTagName( element, tagName ) {
      for ( var i = 0; i < element.childNodes.length; i++ ) {
        if ( element.childNodes[i].nodeName == tagName ) return element.childNodes[i];
      }
    }

    function getHash( url ) {
      var hashPos = url.lastIndexOf ( '#' );
      return url.substring( hashPos + 1 );
    }

    //]]>
    </script>
</head>

<body onload="init()">

<div class="container">
	<%
	
		Patient p = (Patient) request.getAttribute("user");
		String name = "";
		if (p != null){
			name = p.name+",";
		}
	%>
	<h1><%= name %> welcome to the healthcare system</h1>
	
	<ul class="nav nav-tabs" id="tabs">
		<li><a href="#profile" >My Profile</a></li>
		<li><a href="#records" >My Medical Records</a></li>
	</ul>
	

	<div id="myTabContent" >
		<!-- my profile info session -->
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
			<h3>Medical Info</h3>
			<table class="table" width="400">
				<tr><td width="200">Emergency Contact:</td><td><%= p.emergencyNumber %></td></tr>
				<tr><td>Blood Type:</td><td><%= p.bloodType %></td></tr>
				<%
				PatientDao dao = PatientDao.getInstance();
				List<String> allergys = dao.getAllergyListFromPatientId(p.id);
				List<DiseaseRecord> DRs = dao.getAllDiseaseConfirmRecordsBasedOnPatientID(p.id);
				%>
				<tr><td>Allergy List:</td><td> <% for(String a : allergys){ %>  <%= a+", " %> <% } %> </td></tr>
				<tr><td>Disease History:</td><td> <% for(DiseaseRecord r : DRs){ %>  <%= r.disease+", " %> <% } %> </td></tr>
				
			</table>
		</div>
		
		
		<div class="tab-content" id = "records">
			<h3>Blood Pressure History</h3>
			<table class="table">
				<tr style="font-weight:bold"><td>Record Id</td><td>Date</td><td>Doctor</td><td>Blood Pressure</td></tr>
				<%
				List<BloodPressureRecord> BPR = dao.getAllBloodPressureRecordsBasedOnPatientID(p.id);
				for(BloodPressureRecord r : BPR){
					%>
					<tr><td><%= r.id %></td><td><%= r.date %></td><td><a href="doctorPro.jsp?id=<%= r.doctorId %>&patientId=<%= p.id%>" ><%= r.doctorName %></a></td><td><%= r.bloodPressure %></td></tr>
					<%
				}
				%>
			</table>
			
			<h3>Disease Confirm History</h3>
			<table class="table">
				<tr style="font-weight:bold"><td>Record Id</td><td>Date</td><td>Doctor</td><td>Disease Name</td></tr>
				<%
				for(DiseaseRecord r : DRs){
					%>
					<tr><td><%= r.id %></td><td><%= r.date %></td><td><a href="doctorPro.jsp?id=<%= r.doctorId %>&patientId=<%= p.id%>" ><%= r.doctorName %></a></td><td><%= r.disease %></td></tr>
					<%
				}
				%>
			</table>
		</div>
	</div>

</div>


</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cs5200.hospital.*,cs5200.doctor.*,java.util.*, cs5200.record.*, cs5200.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hospital Page</title>
<link href="css/bootstrap.css" rel="stylesheet"/>
<link href="css/selection.css" rel="stylesheet"/>

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
      // highlight the specific tab and hide others
      var t = document.getElementById('tab').value;
      var i = 0;
      for ( var id in tabLinks ) {
        tabLinks[id].onclick = showTab;
        tabLinks[id].onfocus = function() { this.blur() };
        if ( i == t ) tabLinks[id].parentNode.className = 'active';
        i++;
      }
      i = 0;
      for ( var id in contentDivs ) {
        if ( i != t ) contentDivs[id].className = 'tabContent hide';
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

    function editInfo(){
    	var es = document.getElementsByClassName('edit');
    	var i;
    	console.log(es);
    	for(i=0; i< es.length; i++){
    		es[i].firstElementChild.className="hide";
    		es[i].lastElementChild.className="";
    	}
    	var s = document.getElementById('editSubmit');
    	s.className="btn btn-warning";
    }
    

    
    //]]>
    </script>
</head>

<body onload="init()">

<div class="container">
	<%
		Integer tab = 0;
		String name = "";
		Hospital p = (Hospital) request.getAttribute("user");
		if (request.getAttribute("tab") != null) 
			tab = (Integer) request.getAttribute("tab");
		if (p != null){
			name = p.name;
		}
	%>
	<h1>Administrator of <%= name %>, welcome to the healthcare system</h1>
	<input type="hidden" id="tab" value="<%= tab.intValue() %>"/>
	
	<ul class="nav nav-tabs" id="tabs">
		<li><a href="#profile" >My Profile</a></li>
		<li><a href="#doctors" >My Doctors</a></li>
		<!-- <li><a href="#patients" >My Current Patients</a></li> -->
	</ul>
	
	<!-- first tab: My Profile -->
	<div id="myTabContent" >
		<!-- my profile info session -->
		<div class="tab-content" id = "profile">
			<h3>Basic Info</h3>
			<p class="btn btn-warning" style="float:right" onclick="editInfo()">Edit</p>
			<form action="refresh" method=post>
				<input type="hidden" name="identity" value="hospital"/>
				<input type="hidden" name="id" value="<%= p.id%>"/>
				<table class="table" width="400">
					<tr><td width="200">user Id:</td><td><p><%= p.id%></p></td></tr>
					<tr><td>Name:</td><td><%= p.name %></td></tr>
					<tr><td>Phone Number:</td><td class="edit"><p><%= p.phoneNumber %></p><input class="hide" name="phoneNumber" value="<%= p.phoneNumber%>"/></td></tr>								
					<tr><td>Address:</td><td class="edit"><p><%= p.address %></p><input class="hide" name="address" value="<%= p.address%>"/></td></tr>
					<tr><td>introduction:</td><td class="edit"><p><%= p.Introduction %></p><input class="hide" name="introduction" value="<%= p.Introduction%>"/></td></tr>
				</table>
				<button id="editSubmit" class="hide" name="action" value="hpInfoEdit">Submit</button>
			</form>
			<h3>Comments Section</h3>
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
		</div>
		
		
		<!-- second tab: My Doctors Records -->
		<div class="tab-content" id = "doctors">
		
			<h3>My Current Doctors</h3>
			<%
				HospitalDao hdao = HospitalDao.getInstance();
				int newId = hdao.getNewAvailableDoctorId();
			%>
			<a href="doctorInfo.jsp?id=<%= newId %>&hospitalId=<%= p.id%>" class="btn btn-primary" style="float: right">Insert</a>
			<br/>
			<%
				List<Doctor> dls = hdao.getAllDoctorsByHospitalId(p.id);
				for(Doctor d : dls){
					%>
					<h4 style="font-weight:bold"><%= d.name %></h4>
					<table class="table">
						<tr><td>ID: <%= d.id %></td><td>Address: <%= d.address %></td><td>SSN: <%= d.ssn %></td></tr>
						<tr><td>Age: <%= d.age %></td><td>DriverLisense: <%= d.driverLisense %></td><td>StartDate: <%= d.startDate %></td></tr>
						<tr><td>Gender: <%= d.gender %></td><td>PhoneNumber: <%= d.phoneNumber %></td><td>Specialty: <%= d.specialties %></td></tr>
						<tr><td></td><td></td><td><a class="btn btn-warning" href="doctorInfo.jsp?id=<%= d.id%>" style="float:right">Edit</a></td></tr>
					</table>			
					<br/>
					<%
				}
			%>
			
			
<%--			<form action="refresh" method=post> 
 			<input type="hidden" name="identity" value="doctor"/>
			<input type="hidden" name="id" value="<%= p.id%>"/>
 			<table id="BloodRecords" class="table">
				<tr style="font-weight:bold"><td>Record Id</td><td>Date</td><td>Patient</td><td>Blood Pressure</td>
						<td><input type="text" name="bloodPressure" placeholder="eg.100"/>
							<input class="recordId" type="hidden" name="recordId" value="0"/></td>  						
				</tr>
				<%
				DoctorDao dao = DoctorDao.getInstance();
				List<BloodPressureRecord> BPR = dao.getAllBloodPressureRecordsBasedOnDoctorID(p.id);
				for(BloodPressureRecord r : BPR){
					%>
					<tr><td><%= r.id %></td><td><%= r.date %></td><td><a href="patientPro.jsp?id=<%= r.patientId %>" ><%= r.patientName %></a></td><td><%= r.bloodPressure %></td>
						<td><button class="btn btn-warning" name="action" value="update" onclick="updateId(this)">Update</button> 
							<button class="btn btn-danger" name="action" value="delete" onclick="updateId(this)" >Delete</button></td>
					</tr>
					<%
				}
				%>
			</table>
			</form>  --%>
			
		</div>
	
		
		
	</div>

</div>


</body>
</html>
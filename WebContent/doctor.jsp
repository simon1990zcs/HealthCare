<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="cs5200.doctor.*, java.util.*, cs5200.record.*, cs5200.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Doctor Page</title>
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

    function updateId(ele){
    	var ta = ele.parentNode.parentNode.parentNode;
    	var rid = ta.getElementsByClassName('recordId');
    	if (rid[0]) rid[0].value = ele.parentNode.parentNode.firstChild.innerText;
    	return true;
    }
    
    //]]>
    </script>
</head>

<body onload="init()">

<div class="container">
	<%
	

		Integer tab = 0;
		String name = "";
		Doctor p = (Doctor) request.getAttribute("user");
		if (request.getAttribute("tab") != null) 
			tab = (Integer) request.getAttribute("tab");
		if (p != null){
			name = p.name;
		}
	%>
	<h1>Doctor <%= name %>, welcome to the healthcare system</h1>
	<input type="hidden" id="tab" value="<%= tab.intValue() %>"/>
	
	<ul class="nav nav-tabs" id="tabs">
		<li><a href="#profile" >My Profile</a></li>
		<li><a href="#records" >My Existing Records</a></li>
		<li><a href="#patients" >My Current Patients</a></li>
	</ul>
	
	<!-- first tab: My Profile -->
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
			<h3>Doctor Info</h3>
			<table class="table" width="400">
				<tr><td width="200">StartDate:</td><td><%= p.startDate %></td></tr>
				<tr><td>Specialty:</td><td><%= p.specialties %></td></tr>
				<tr><td>Belonged Hospital:</td><td><a href="hospitalPro.jsp?id=<%= p.belongs %>"> <%= p.hospitalName %></a></td></tr>
			</table>
		</div>
		
		
		<!-- second tab: My Existing Records -->
		<div class="tab-content" id = "records">
		
			<h3>Blood Pressure Records</h3>
			<form action="refresh" method=post> 
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
			</form> 
			
			<h3>Disease Confirm History</h3>
			<form action="refresh" method=post>
				<input type="hidden" name="identity" value="doctor"/>
			 	<input type="hidden" name="id" value="<%= p.id%>"/>
 				<table id="DiseaseHistory" class="table">
				<tr style="font-weight:bold"><td><input class="recordId" type="hidden" name="recordId" value="0"/>Record Id</td><td>Date</td><td>Patient</td><td>Disease Name</td>
						<td><!-- <input type="text" name="diseaseName" placeholder="disease name"/> -->
						<div class="selectContainer">
							<select name="diseaseId" class="form-control">
								<option >disease name</option>
								<%
								  List<Disease> dls = dao.getDiseaseList();
								  for (Disease d : dls){
									  %>
								<option value="<%= d.id%>"><%= d.name %></option>									  
									  <%
								  }
								%>
							</select>
						</div>
					</td>  
				</tr>
				<%
				List<DiseaseRecord> DRs = dao.getAllDiseaseConfirmRecordsBasedOnDoctorID(p.id);
				for(DiseaseRecord r : DRs){
					%>
					<tr><td><%= r.id %></td><td><%= r.date %></td><td><a href="patientPro.jsp?id=<%= r.patientId %>" ><%= r.patientName %></a></td><td><%= r.disease %></td>
						<td><button class="btn btn-warning" name="action" value="update" onclick="updateId(this)">Update</button> 
							<button class="btn btn-danger" name="action" value="delete" onclick="updateId(this)" >Delete</button></td>
					</tr>
					<%
				}
				%>
				
				</table>

				</form>
		</div>
		
		<!-- third tab: My Patients -->
		<div class="tab-content" id = "patients">
			<h3>My Current Patients</h3>
			<table class="table">
				<tr style="font-weight:bold"><td>Patient Id</td><td>Patient Name</td><td>Start Date</td><td>Actions</td></tr>
				<%
				List<DoctorPatientEntry> DPEls = dao.getCurrentPatientsByDoctorId(p.id);
				for (DoctorPatientEntry entry : DPEls){
					if (entry.endDate == null){
						%>
						<tr><td><%= entry.patientId %></td><td><%= entry.patientName %></td><td><%= entry.startDate %></td>
							<td><p class="btn btn-primary">Blood Pressure Check</p>
								<p class="btn btn-primary">Disease Confirm</p>
							</td>
						</tr>
						<%
					}
				}
				%>
			</table>
			<div class="insert">
				<p>Patient Id: </p><input name="patientId" value="001" disabled/>
				<div class="insert">
				<p>Blood Pressure: </p><input name="bloodPressure" value="99"/>
				</div>
				<div class="insert">
				<p>Disease</p>						
				<div class="insert">
						<select name="diseaseId" class="form-control">
							<option > </option>
							<%
							  for (Disease d : dls){
								  %>
							<option value="<%= d.id%>"><%= d.name %></option>									  
								  <%
							  }
							%>
						</select>
				</div>
				</div> 
				<button class="btn btn-primary">Insert</button>
			</div>
			
			
			<h3>My Past Patients</h3>
			<table class="table">
				<tr style="font-weight:bold"><td>Patient Id</td><td>Patient Name</td><td>Start Date</td><td>End Date</td></tr>
				<%
				for (DoctorPatientEntry entry : DPEls){
					if (entry.endDate != null){
						%>
						<tr><td><%= entry.patientId %></td><td><%= entry.patientName %></td><td><%= entry.startDate %></td><td><%= entry.endDate %></td></tr>
						<%
					}
				}
				%>
			</table>
		</div>
		
		
		
	</div>

</div>


</body>
</html>
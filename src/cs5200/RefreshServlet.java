package cs5200;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs5200.Enum.Gender;
import cs5200.Enum.Specialties;
import cs5200.doctor.Doctor;
import cs5200.doctor.DoctorDao;
import cs5200.hospital.Hospital;
import cs5200.hospital.HospitalDao;

/**
 * Servlet implementation class RefreshServlet
 */
@WebServlet("/RefreshServlet")
public class RefreshServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RefreshServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String identity = request.getParameter("identity");
		String action = request.getParameter("action");

		Integer tab = 0; // indicate which tab it should go
		int operationResult = 0; //magic number, indicated operation success
		
		if (action.equals("doctorInfo")){
			doctorInfo(request, response);
			
		} else if (identity.equals("doctor")){
			doctorOperation(request, response);
			
		} else if (identity.equals("hospital")){
			
			int id = Integer.parseInt(request.getParameter("id"));
			HospitalDao hdao = HospitalDao.getInstance();
			
			if (action.equals("hpInfoEdit")){
				String phoneNumber = request.getParameter("phoneNumber");
				String saddress = request.getParameter("address");
				String sintroduction = request.getParameter("introduction");
				
				operationResult = hdao.updateHospitalInfo(id, phoneNumber, saddress, sintroduction);
				
			}
			
			Hospital h = hdao.selectInfoFromHospitalId(id);
			tab = tab + operationResult * 10;
			System.out.println(tab);
			request.setAttribute("user", h);
			request.setAttribute("tab", tab);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/hospital.jsp");
			dispatcher.forward(request, response);
						
		}
		
	}
	
	private void doctorOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		Integer tab = 0; // indicate which tab it should go
		int operationResult = 0; // indicated operation success
		
		int id = Integer.parseInt(request.getParameter("id"));
		String sRecordId = request.getParameter("recordId");
		String sPatientId = request.getParameter("patientId");
		
		DoctorDao ddao = DoctorDao.getInstance();
		Doctor d = ddao.selectInfoFromDoctorId(id);
		
		String sBlood = request.getParameter("bloodPressure");
		String sDisease = request.getParameter("diseaseId");
		
		switch (action){
		case "update":
			tab = 1;
			int recordId = Integer.parseInt(sRecordId);
			if (sBlood != null){
				double blood = Double.parseDouble(sBlood);
				operationResult = ddao.updateBloodPressureByRecordIdAndValue(recordId, blood);
			} else if (sDisease != null) {
				int diseaseId = Integer.parseInt(sDisease);
				operationResult = ddao.updateDiseaseRecordsByRecordIdAndDiseaseId(recordId, diseaseId);
			} 
			break;
		case "delete":
			tab = 1;
			int recordId1 = Integer.parseInt(sRecordId);
			operationResult = ddao.deleteRecordById(recordId1);
			break;
		case "insert":
			tab = 2;
			int patientId = Integer.parseInt(sPatientId);
			if (sBlood != null && !sBlood.isEmpty()){
				double blood = Double.parseDouble(sBlood);
				operationResult = ddao.insertBloodPressureByIdsAndValue(patientId, id, blood);
			} else if (sBlood!= null && !sDisease.isEmpty()) {
				int diseaseId = Integer.parseInt(sDisease);
				operationResult = ddao.insertDiseaseRecordsByIdsAndDiseaseId(patientId, id, diseaseId);
			} 
			
		}
		
		tab = tab + operationResult * 10;
		request.setAttribute("user", d);
		request.setAttribute("tab", tab);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/doctor.jsp");
		dispatcher.forward(request, response);
		response.sendRedirect("/doctor.jsp");
	}
	
	private void doctorInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tab = 1;
		int operationResult = 0;
		
		int did = Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		Gender gender = Gender.valueOf(request.getParameter("gender"));
		String ssn=request.getParameter("ssn");
		String driverlicense=request.getParameter("driverLicense");
		String phone=request.getParameter("phoneNumber");
		String address=request.getParameter("address");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(00);
		try {
			date = new Date(format.parse(request.getParameter("date")).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Specialties special=Specialties.valueOf(request.getParameter("speciality"));
		int belongs=Integer.parseInt(request.getParameter("belongs"));
		
		
		DoctorDao ddao = DoctorDao.getInstance();
		Doctor d = ddao.selectInfoFromDoctorId(did);
		int returnHospitalId = 0;
		
		if ( d == null) {
			// insert a new doctor
			returnHospitalId = belongs;
			Doctor nd = new Doctor(did, name, age, ssn, gender, driverlicense, phone, address,
					 "12345", date, special, belongs);
			operationResult = ddao.updateDoctorInfo(nd, true);
		} else {
			// update a existing doctor
			Doctor nd = new Doctor(did, name, age, ssn, gender, driverlicense, phone, address,
					 d.password, date, special, belongs);
			operationResult = ddao.updateDoctorInfo(nd, false);
			returnHospitalId = d.belongs;
		}
		
		// TODO: keep current hospital page
		HospitalDao hdao = HospitalDao.getInstance();
		Hospital h = hdao.selectInfoFromHospitalId(returnHospitalId);
		tab = tab + operationResult * 10;
		request.setAttribute("user", h);
		request.setAttribute("tab", tab);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/hospital.jsp");
		dispatcher.forward(request, response);
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

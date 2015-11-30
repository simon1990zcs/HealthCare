package cs5200;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs5200.doctor.Doctor;
import cs5200.doctor.DoctorDao;
import cs5200.hospital.Hospital;
import cs5200.hospital.HospitalDao;
import cs5200.patient.Patient;
import cs5200.patient.PatientDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String identity = request.getParameter("identity");
		if (userId == null || password == null || !userId.matches("[0-9]+")){
			// stay in login page
			response.sendRedirect("/Healthcare/login.jsp");
			return;
		}
		
		System.out.println("Hello from LoginServlet.java " + userId + " "  + password + " " + identity);
		
		boolean success = false;
		switch (identity){
		case "patient":
			PatientDao pdao = PatientDao.getInstance();
			Patient p = pdao.selectFromPatientId(Integer.parseInt(userId));
			if (p != null && p.password.equals(password)){
				success = true;
				request.setAttribute("user", p);
				request.setAttribute("identity", identity);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/patient.jsp");
				dispatcher.forward(request, response);
				response.sendRedirect("/patient.jsp");
				return;
			}
			break;
		case "doctor":
			DoctorDao ddao = DoctorDao.getInstance();
			Doctor d = ddao.selectInfoFromDoctorId(Integer.parseInt(userId));
			if (d != null && d.password.equals(password)){
				success = true;
				request.setAttribute("user", d);
				request.setAttribute("identity", identity);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/doctor.jsp");
				dispatcher.forward(request, response);
//				response.sendRedirect("/doctor.jsp");
				return;
			}
			break;
		case "hospital":
			HospitalDao hdao = HospitalDao.getInstance();
			Hospital h = hdao.selectInfoFromHospitalId(Integer.parseInt(userId));
			if (h != null && h.password.equals(password)){
				success = true;
				request.setAttribute("user", h);
				request.setAttribute("identity", identity);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/hospital.jsp");
				dispatcher.forward(request, response);
				return;
			}
			break;
		}
		
		if (!success){
			// stay in login page, invalid login information
			request.setAttribute("error", 1);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

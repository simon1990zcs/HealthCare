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
		int id = Integer.parseInt(request.getParameter("id"));
		String action = request.getParameter("action");
		int recordId = Integer.parseInt(request.getParameter("recordId"));
		Integer tab = 0;
		
		if (identity.equals("doctor")){
			
			DoctorDao ddao = DoctorDao.getInstance();
			Doctor d = ddao.selectInfoFromDoctorId(id);
			
			String sBlood = request.getParameter("bloodPressure");
			String sDisease = request.getParameter("diseaseId");
			
			int operationResult = 0;
			
			switch (action){
			case "update":
				tab = 1;
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
				ddao.deleteRecordById(recordId);
				break;
			case "insert":
			}
			
			request.setAttribute("user", d);
			request.setAttribute("tab", tab);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/doctor.jsp");
			dispatcher.forward(request, response);
			response.sendRedirect("/doctor.jsp");
			
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

package cs5200.doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.Statement;

import java.sql.Date;

import cs5200.Dao;
import cs5200.Disease;
import cs5200.Enum.Gender;
import cs5200.Enum.Specialties;
import cs5200.patient.Patient;
import cs5200.record.BloodPressureRecord;
import cs5200.record.DiseaseRecord;

public class DoctorDao extends Dao {

	// Singleton Design Pattern
	private static DoctorDao instance = null;
	protected DoctorDao() {}
	public static DoctorDao getInstance() {
		if(instance == null)
			instance = new DoctorDao();
		return instance;
	}	
	
	
	public Doctor selectInfoFromDoctorId(int id) {
		Doctor ws = null;
		
		String sql = "select b.*, d.startDate, d.specialties, d.belongs, h.name as hospital "
				   + "from BasicInfo b, Doctor d, Hospital h "
				   + "where b.id=d.id And d.id=? and d.belongs=h.id";
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			results = statement.executeQuery();
			while(results.next()) {
				id = results.getInt("id");
				int age = results.getInt("age");
				String name = results.getString("name");
				String ssn = results.getString("ssn");
				Gender gender = Gender.valueOf(results.getString("gender"));
				String driverLisense = results.getString("driverLisense");
				String phoneNumber = results.getString("phoneNumber");
				String address = results.getString("address");
				String password = results.getString("password");
				Date startDate = results.getDate("startDate");
				Specialties specialty = Specialties.valueOf(results.getString("specialties"));
				int belongs = results.getInt("belongs");
				ws = new Doctor(id, name, age, ssn, gender, driverLisense, phoneNumber, address, password, startDate, specialty, belongs);
				ws.hospitalName = results.getString("hospital");

			}
			results.close();
			results = null;
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(results != null) {
				try {
					results.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ws;
	}

	
	public List<BloodPressureRecord> getAllBloodPressureRecordsBasedOnDoctorID(int id){

		// DoctorName here is a false value;
		String sql = "select r.*, d.name as doctorName, d.name as patientName, b.bloodPressure "
				   + "from BloodPressureCheck b, Records r, BasicInfo d "
				   + "where b.id=r.id and r.doctorId=? and r.patientId=d.id "
				   + "order by r.date DESC";
		
		return super.getBloodPressureRecordBySqlAndId(sql, id);
	}
	
	public List<DiseaseRecord> getAllDiseaseConfirmRecordsBasedOnDoctorID(int id){

		// doctorName here is false value
		String sql = "select r.*, b.name as doctorName, b.name as patientName, d.name as diseaseName "
				   + "from DiseaseConfirm c, Records r, BasicInfo b, Disease d "
				   + "where c.id=r.id and r.patientId=b.id and r.doctorId=? and d.id=c.diseaseId "
				   + "order by r.date DESC";
		return super.getAllDiseaseConfirmRecordsBasedBySqlAndId(sql, id);
		
	}
	
	public List<Disease> getDiseaseList(){
		List<Disease> ls = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rs = null;
		String sql = "select d.* from Disease d";
		
		try {
			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				ls.add(new Disease(id,name));
			}
			rs.close();
			rs = null;
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return ls;
	}
	
	public int updateDiseaseRecordsByRecordIdAndDiseaseId(int rid, int did){
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int n = 0;
		
		String sql = "update DiseaseConfirm set diseaseId=? where id=?";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, did);
			statement.setInt(2, rid);
			n = statement.executeUpdate();
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return n;
	}


	public int updateBloodPressureByRecordIdAndValue(int rid, double value){
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int n = 0;
		
		String sql = "update BloodPressureCheck set bloodPressure=? where id=?";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, value);
			statement.setInt(2, rid);
			n = statement.executeUpdate();
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return n;
	}
	
	public int insertBloodPressureByIdsAndValue(int pid, int did, double value){
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		Date date = new Date(System.currentTimeMillis());
//		Date date = new Date(Calendar.getInstance().getTime().getTime());
		int n = 0;
		
		String sql1 = "insert into Records(date, patientId, doctorId) value(?,?,?) ";
		String sql2 = "insert into BloodPressureCheck(id, bloodPressure) value(?,?) ";
		
		try {
			statement = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, date);
			statement.setInt(2, pid);
			statement.setInt(3, did);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.first();
			int rid = rs.getInt(1); // get inserted record id; 
			statement.close();

			statement = connection.prepareStatement(sql2);
			statement.setInt(1, rid);
			statement.setDouble(2, value);
			n = statement.executeUpdate();
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return n;
	}
	
	public int insertDiseaseRecordsByIdsAndDiseaseId(int pid, int did, int value){
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		Date date = new Date(System.currentTimeMillis());
//		Date date = new Date(Calendar.getInstance().getTime().getTime());
		int n = 0;
		
		String sql1 = "insert into Records(date, patientId, doctorId) value(?,?,?) ";
		String sql2 = "insert into DiseaseConfirm(id, DiseaseId) value(?,?) ";
		
		try {
			statement = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, date);
			statement.setInt(2, pid);
			statement.setInt(3, did);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			rs.first();
			int rid = rs.getInt(1); // get inserted record id; 
			statement.close();

			statement = connection.prepareStatement(sql2);
			statement.setInt(1, rid);
			statement.setInt(2, value);
			n = statement.executeUpdate();
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return n;
	}
	
	public boolean deleteRecordById(int id){
		String sql = "delete from Records where id=?";
		return super.deleteRecordBySql(sql, id);
	}
	
	public List<DoctorPatientEntry> getCurrentPatientsByDoctorId(int id){
		List<DoctorPatientEntry> ls = new ArrayList<>();
		
		String sql = "select dp.*, p.name "
				   + "from DoctorPatient dp, BasicInfo p "
				   + "where dp.doctorId=? and dp.patientId=p.id "
				   + "order by dp.startDate ASC ";
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			results = statement.executeQuery();
			while(results.next()) {
				int doctorId = results.getInt("doctorId");
				int patientId = results.getInt("patientId");
				Date startDate = results.getDate("startDate");
				Date endDate = results.getDate("endDate");
				DoctorPatientEntry entry = new DoctorPatientEntry(doctorId, patientId, startDate, endDate);
				entry.patientName = results.getString("name");
				ls.add(entry);
			}
			results.close();
			results = null;
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(results != null) {
				try {
					results.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ls;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

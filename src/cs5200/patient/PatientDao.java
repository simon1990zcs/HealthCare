package cs5200.patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200.Dao;
import cs5200.Enum.BloodType;
import cs5200.Enum.Gender;
import cs5200.record.BloodPressureRecord;
import cs5200.record.DiseaseRecord;

public class PatientDao extends Dao {

	// Singleton Design Pattern
	private static PatientDao instance = null;
	protected PatientDao() {}
	public static PatientDao getInstance() {
		if(instance == null)
			instance = new PatientDao();
		return instance;
	}	
	
	public List<String> getAllergyListFromPatientId(int id){
		List<String> al = new ArrayList<String>();
		String sql = "select a.name from Allergy a, PatientAllergy p where p.patientId=? and p.AllergyId=a.id";
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			results = statement.executeQuery();
			while(results.next()) {
				String allergy = results.getString("name");
				al.add(allergy);
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
		
		return al;
	}
	
	public List<DiseaseRecord> getAllDiseaseConfirmRecordsBasedOnPatientID(int id){

		// patientName here is false value
		String sql = "select r.*, b.name as doctorName, b.name as patientName, d.name as diseaseName "
				   + "from DiseaseConfirm c, Records r, BasicInfo b, Disease d "
				   + "where c.id=r.id and r.patientId=? and r.doctorId=b.id and d.id=c.diseaseId ";
		return super.getAllDiseaseConfirmRecordsBasedBySqlAndId(sql, id);
		
	}
	
	public List<BloodPressureRecord> getAllBloodPressureRecordsBasedOnPatientID(int id){

		// patientName here is a false value;
		String sql = "select r.*, d.name as doctorName, d.name as patientName, b.bloodPressure "
				   + "from BloodPressureCheck b, Records r, BasicInfo d "
				   + "where b.id=r.id and r.patientId=? and r.doctorId=d.id ";
		
		return super.getBloodPressureRecordBySqlAndId(sql, id);
	}
	
	public Patient selectFromPatientId(int id) {
		Patient ws = null;
		
		String sql = "select b.*,p.emergencyNumber,p.bloodType from BasicInfo b, Patient p where b.id=p.id And p.id=?;";
		
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
				String emergencyNumber = results.getString("emergencyNumber");
				BloodType bloodType = BloodType.valueOf(results.getString("bloodType"));
				
				ws = new Patient(id, name, age, ssn, gender, driverLisense, phoneNumber, address, password, emergencyNumber, bloodType);
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PatientDao pDao = PatientDao.getInstance();
		Patient pa = pDao.selectFromPatientId(1);
		System.out.println(pa.name+ " " + pa.bloodType);
	}
	
}

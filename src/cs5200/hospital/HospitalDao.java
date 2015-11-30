package cs5200.hospital;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs5200.Dao;
import cs5200.Disease;
import cs5200.Enum.Gender;
import cs5200.Enum.Specialties;
import cs5200.doctor.Doctor;

public class HospitalDao extends Dao{

	// Singleton Design Pattern
	private static HospitalDao instance = null;
	protected HospitalDao() {}
	public static HospitalDao getInstance() {
		if(instance == null)
			instance = new HospitalDao();
		return instance;
	}	
	
	public Hospital selectInfoFromHospitalId(int id) {
		Hospital ws = null;
		
		String sql1 = "select h.* "
				   + "from Hospital h "
				   + "where h.id=? ";
		
		String sql2 = "select c.hospitalId, c.patientId, c.comments,c.date, p.name  "
				    + "from HospitalComments c, BasicInfo p "
				    + "where c.hospitalId=? and c.patientId=p.id order by c.date desc";
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			statement = connection.prepareStatement(sql1);
			statement.setInt(1, id);
			results = statement.executeQuery();
			while(results.next()) {
				id = results.getInt("id");
				String name = results.getString("name");
				String introduction = results.getString("introduction");
				String phoneNumber = results.getString("phoneNumber");
				String address = results.getString("address");
				String password = results.getString("password");
				ws = new Hospital(id, name, address, phoneNumber, introduction, password);
			}
			statement.close();
			results.close();
			statement = connection.prepareStatement(sql2);
			statement.setInt(1, id);
			results = statement.executeQuery();
			List<Comment> comments = new ArrayList<>();
			while (results.next()){
				int hid = results.getInt("hospitalId");
				int pid = results.getInt("patientId");
				String c = results.getString("comments");
				Date date=results.getDate("date");
				Comment com = new Comment(hid, pid, c,date);
				com.patientName = results.getString("name");
				comments.add(com);
			}
			ws.setComments(comments);
			
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
	
	public int updateHospitalInfo(int id, String phoneNumber, String address, String introduction){
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int n = 0;
		
		String sql = "update Hospital set phoneNumber=?, address=?,introduction=? where id=?";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, phoneNumber);
			statement.setString(2, address);
			statement.setString(3, introduction);
			statement.setInt(4, id);
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
	
	public List<Doctor> getAllDoctorsByHospitalId(int hospitalId){
		List<Doctor> ls = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select b.*, d.startDate, d.specialties, d.belongs "
				   + "from BasicInfo b, Doctor d "
				   + "where b.id=d.id and d.belongs=?";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, hospitalId);
			results = statement.executeQuery();
			while (results.next()){
				int id = results.getInt("id");
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
				Doctor ws = new Doctor(id, name, age, ssn, gender, driverLisense, phoneNumber, address, password, startDate, specialty, belongs);
				ls.add(ws);
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
		
		return ls;
	}
	
	public int insertHospitalComment(int id,int pid,String comment){
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int n = 0;
		
		String sql = "insert into HospitalComments(patientid,hospitalid,comments,date) value (?,?,?,?)";
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, pid);
			statement.setInt(2, id);
			statement.setString(3, comment);
			statement.setDate(4, new Date(System.currentTimeMillis()));
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
	
	public int getNewAvailableDoctorId(){
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		int n = 0;
		
		String sql = "select max(id) as id from Doctor ";
		
		try {
			statement = connection.prepareStatement(sql);
			results = statement.executeQuery();
			while(results.next()){
				n = results.getInt("id") + 1;
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
		return n;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

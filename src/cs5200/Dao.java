package cs5200;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs5200.record.BloodPressureRecord;
import cs5200.record.DiseaseRecord;

public class Dao {
	
	protected Connection getConnection(){
		String url = "jdbc:mysql://localhost:3306/healthcare";
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, "root", "3170");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
		
	}
	
	protected void closeConnection(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected List<BloodPressureRecord> getBloodPressureRecordBySqlAndId(String sql, int id){
		List<BloodPressureRecord> ls = new ArrayList<BloodPressureRecord>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			results = statement.executeQuery();
			while(results.next()) {
				int rId = results.getInt("id");
				Date date = results.getDate("date");
				int patientId = results.getInt("patientId");
				int doctorId = results.getInt("doctorId");
				double bloodPressure = results.getDouble("bloodPressure"); 
				BloodPressureRecord r = new BloodPressureRecord(rId, date, patientId, doctorId, bloodPressure);
				r.patientName = results.getString("patientName");
				r.doctorName = results.getString("doctorName");
				ls.add(r);
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
	
	
	protected List<DiseaseRecord> getAllDiseaseConfirmRecordsBasedBySqlAndId(String sql, int id){
		List<DiseaseRecord> ls = new ArrayList<DiseaseRecord>();
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet results = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			results = statement.executeQuery();
			while(results.next()) {
				int rId = results.getInt("id");
				Date date = results.getDate("date");
				int patientId = results.getInt("patientId");
				int doctorId = results.getInt("doctorId");
				String dieseaseName = results.getString("diseaseName");
				DiseaseRecord r = new DiseaseRecord(rId, date, patientId, doctorId, dieseaseName);
				r.doctorName = results.getString("doctorName");
				r.patientName = results.getString("patientName");
				ls.add(r);
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
	
	protected int deleteRecordBySql(String sql, int id){
		int r = 0;
		
		Connection connection = getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			r = statement.executeUpdate();
			statement.close();
			statement = null;
			connection.close();
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		
		return r;
	}
	



public HashMap<Integer,String> getHospitalInfo(){
	HashMap<Integer,String> ls = new HashMap<Integer,String>();
	
	Connection connection = getConnection();
	PreparedStatement statement = null;
	ResultSet results = null;
	
	String sql = "select h.id, h.name from Hospital h";
	
	try {
		statement = connection.prepareStatement(sql);
		results = statement.executeQuery();
		while(results.next()) {
			int rId = results.getInt("id");
			String name = results.getString("name");
			ls.put(rId, name);
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
}

package cs5200.record;

import java.sql.Date;

public class Records {
	public int id;
	public Date date;
	public int patientId;
	public String patientName; // patient name base on patientId
	public int doctorId;
	public String doctorName;// doctor Name based on doctorId
	public Records(int id, Date date, int patientId, int doctorId) {
		super();
		this.id = id;
		this.date = date;
		this.patientId = patientId;
		this.doctorId = doctorId;
	}
	public Records() {
		super();
	}
	
	
}

package cs5200.doctor;

import java.sql.Date;

public class DoctorPatientEntry {
	public int doctorId;
	public int patientId;
	public String patientName; // get patient name based on patient id
	public Date startDate;
	public Date endDate;
	public DoctorPatientEntry(int doctorId, int patientId, Date startDate, Date endDate) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public DoctorPatientEntry(int doctorId, int patientId, Date startDate) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.startDate = startDate;
	}
	public DoctorPatientEntry() {
		super();
	}
	
	
}
